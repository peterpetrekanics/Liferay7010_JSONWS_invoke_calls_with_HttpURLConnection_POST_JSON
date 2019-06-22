package com.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Working version. To import dependenies: right-click on the Eclipse project, select:
// Build Path / configure build path / libraries / add jars  and select your .jar files.
public class ClientWithAuthAndJson {

	private static final String UTF_8 = "UTF-8";

	public static void main(String[] args) throws JSONException, IOException {
		try {
			String username = "test@liferay.com";
			String password = "test";

			String authCredentials = new String(username + ":" + password);
			byte[] authCredentialsInBytes = authCredentials.getBytes(UTF_8);
			String base64AuthCredentials = Base64.getEncoder().withoutPadding().encodeToString(authCredentialsInBytes);

			String query = "http://localhost:8080/api/jsonws/invoke";
			// String urlBasePath = "http://localhost:8080/api/jsonws/journal.journalarticle/add-article";
			// String urlBasePath =
			// "http://localhost:8080/api/jsonws/dlapp/add-file-entry";
			// String query =
			// "http://localhost:8080/api/jsonws/company/get-companies";
			// String query =
			// "http://localhost:8080/api/jsonws/user/get-user-id-by-screen-name";

			// companyId = 20115

			// create the connection
			URL url = new URL(query);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Authorization", "Basic " + base64AuthCredentials);
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("charset", "utf-8");

			// set up the data to send
			
			//user/get-user-id-by-screen-name
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("companyId", 20115);
//			jsonObject.put("screenName", "test");
//			JSONObject jsonObjectParent = new JSONObject();
//			jsonObjectParent.put("/user/get-user-id-by-screen-name", jsonObject);
			
			//user/get-group-users
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("groupId", 20142);
//			JSONObject jsonObjectParent = new JSONObject();
//			jsonObjectParent.put("/user/get-group-users", jsonObject);

			//user/get-user-by-id
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId", 20155);
			JSONObject jsonObjectParent = new JSONObject();
			jsonObjectParent.put("/user/get-user-by-id", jsonObject);
			
			
			
			
			
			// Wrapping the json parameters in a parent json object according to:
			// https://portal.liferay.dev/docs/7-0/tutorials/-/knowledge_base/t/json-web-services-invoker#simple-invoker-calls
			// Note: the p_auth parameter does not need to be added because we
			// used the auth.token.check.enabled=false property
			String jsonObjectParentString = jsonObjectParent.toString();
			// Double quotes need to be replaced with %22 according to the documentation:
			String encodedJson = jsonObjectParentString.replaceAll("\"", "%22");
			System.out.println("Will be sending this json: " + encodedJson);

			String urlParameters = "cmd=" + encodedJson; // <-- use this one for Liferay's api/jsonws/invoke calls
			byte[] postData = urlParameters.getBytes("UTF-8");
			int postDataLength = postData.length;
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			conn.connect();

			// write the postdata to the connection.
			OutputStream osw = conn.getOutputStream();
			osw.write(postData);
			osw.flush();
			osw.close();

			// read the response
			InputStream in = new BufferedInputStream(conn.getInputStream());

			String result = convertStreamToString(in);
			System.out.println("Plain results (no json): " + result);
			if (result.charAt(0) == '[') {
				result = result.substring(1, result.length() - 1);
			}
			if (result.charAt(0) == '{') {
				JSONObject jsonObjectres = new JSONObject(result);
				// System.out.println("results " + jsonObjectres);
				 HashMap<String, Object> hashMap = new
				 HashMap<>(Utility.jsonToMap(jsonObjectres));
				 Iterator it = hashMap.entrySet().iterator();
				 while (it.hasNext()) {
				 Map.Entry pair = (Map.Entry) it.next();
				 System.out.println(pair.getKey() + " = " + pair.getValue());
				 it.remove(); // avoids a ConcurrentModificationException
				 }
			}

			in.close();
			conn.disconnect();
		} finally {
		}
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}