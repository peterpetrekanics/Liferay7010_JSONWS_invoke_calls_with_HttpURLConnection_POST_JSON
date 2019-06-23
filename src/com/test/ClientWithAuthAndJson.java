package com.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jodd.io.FileUtil;

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
//			String boundary = "---------------------------" + System.currentTimeMillis();
//			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary );
//			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("userId", 20155);
//			JSONObject jsonObjectParent = new JSONObject();
//			jsonObjectParent.put("/user/get-user-by-id", jsonObject);
			
			//user/get-current-user
			// Only started to work once I have visited this on the UI:
			// Liferay server UI / Control Panel / Configuration / Service Access Policy
			// and set up the com.liferay.portal.kernel.service.UserService  +  getUserByScreenName
			// method as "allowed service". Otherwise there is a 403 error
//			JSONObject jsonObject = new JSONObject();
//			JSONObject jsonObjectParent = new JSONObject();
//			jsonObjectParent.put("/user/get-current-user", jsonObject);
			
			//journal.journalarticle/get-articles
			// This also requires enablement in the Service Access Policies
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("groupId", 20142);
//			jsonObject.put("folderId", 0);
//			jsonObject.put("start", -1);
//			jsonObject.put("end", -1);
//			jsonObject.put("odb", "");
//			JSONObject jsonObjectParent = new JSONObject();
//			jsonObjectParent.put("/journal.journalarticle/get-articles", jsonObject);

			//ddl.ddlrecord/add-record
			// This also requires enablement in the Service Access Policies
			// Plus, I received this error after that:
			// User 20119 must have ADD_RECORD permission for com.liferay.dynamic.data.lists.model.DDLRecordSet 40745
			// As a workaround I used the omniadmin user to start this app which solved the permission issue.
			// To check how to fill the fieldsMap, pls check the database:  DDMContent.data_  column
//	        String fieldsMap = "{\"b\":\"c\"}";
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("groupId", 20142);
//			jsonObject.put("recordSetId", 40745);
//			jsonObject.put("displayIndex", 0);
//			jsonObject.put("fieldsMap", fieldsMap);
//			JSONObject jsonObjectParent = new JSONObject();
//			jsonObjectParent.put("/ddl.ddlrecord/add-record", jsonObject);

			//trashentry/delete-entries
			// Looks like this service does not need to be enabled in the Service Access Policies
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("groupId", 20142);
//			JSONObject jsonObjectParent = new JSONObject();
//			jsonObjectParent.put("/trashentry/delete-entries", jsonObject);
			
			//dlapp/get-folders-and-file-entries-and-file-shortcuts
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("repositoryId", 20142);
//			jsonObject.put("folderId", 0);
//			jsonObject.put("status", 0);
//			jsonObject.put("includeMountFolders", false);
//			jsonObject.put("start", -1);
//			jsonObject.put("end", -1);
//			JSONObject jsonObjectParent = new JSONObject();
//			jsonObjectParent.put("/dlapp/get-folders-and-file-entries-and-file-shortcuts", jsonObject);
			
			//dlapp/update-file-entry  <-- Partly working - the file uploading part is not working
			// Executing this code part made it clear that the boolean does not need to be wrapped in double quotes
			// However specifying a File as a json object did not work yet, only 50 bytes get transfered
			File myFile = new File("/home/peterpetrekanics/Pictures/inProgressLesa.png");
	        
			String serviceContext = "{\"addGroupPermissions\":false" +
	                ",\"addGuestPermissions\":false" +
	                ", \"scopeGroupId\":20142\"}";
	    	
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fileEntryId", 40905);
			jsonObject.put("sourceFileName", "inProgressLesa.png");
			jsonObject.put("mimeType", "image/png");
			jsonObject.put("title", "inProgressLesa.png");
			jsonObject.put("description", "");
			jsonObject.put("changeLog", "");
			jsonObject.put("majorVersion", true);
			jsonObject.put("file", myFile);
			jsonObject.put("serviceContext ", serviceContext);
			JSONObject jsonObjectParent = new JSONObject();
			jsonObjectParent.put("/dlapp/update-file-entry", jsonObject);
			
			
			//dlapp/add-file-entry  <-- NOT working, I believe a multipart request has to be used for file uploading
//			File myFile = new File("/home/peterpetrekanics/Pictures/inProgressLesa.png");
//			System.out.println("size: " + myFile.length());
//	        byte[] bytes = FileUtil.readBytes(myFile);
//
//			String serviceContext = "{\"addGroupPermissions\":false" +
//	                ",\"addGuestPermissions\":false" +
//	                ", \"scopeGroupId\":20142\"}";
//	    	
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("repositoryId", 20142);
//			jsonObject.put("folderId", 40901);
//			jsonObject.put("sourceFileName", "inProgressLesa.png");
//			jsonObject.put("mimeType", "image/png");
//			jsonObject.put("title", "inProgressLesa.png");
//			jsonObject.put("description", "");
//			jsonObject.put("changeLog", "");
//			jsonObject.put("bytes", bytes);
//			jsonObject.put("serviceContext ", serviceContext);
//			JSONObject jsonObjectParent = new JSONObject();
//			jsonObjectParent.put("/dlapp/add-file-entry", jsonObject);

			//dlapp/add-file-entry  <-- NOT working, I believe a multipart request has to be used for file uploading
			// Maybe whenever a file is included in my method I need to use the multipart header
//			File myFile = new File("/home/peterpetrekanics/Pictures/inProgressLesa.png");
//			System.out.println("size: " + myFile.length());
//	        byte[] bytes = FileUtil.readBytes(myFile);
//
//			String serviceContext = "{\"addGroupPermissions\":false" +
//	                ",\"addGuestPermissions\":false" +
//	                ", \"scopeGroupId\":20142\"}";
//	    	
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("repositoryId", 20142);
//			jsonObject.put("folderId", 40901);
//			jsonObject.put("sourceFileName", "inProgressLesa.png");
//			jsonObject.put("mimeType", "image/png");
//			jsonObject.put("title", "inProgressLesa.png");
//			jsonObject.put("description", "");
//			jsonObject.put("changeLog", "");
//			jsonObject.put("file", new FileBody(myFile));
//			jsonObject.put("serviceContext ", serviceContext);
//			JSONObject jsonObjectParent = new JSONObject();
//			jsonObjectParent.put("/dlapp/add-file-entry", jsonObject);

			
			//journal.journalarticle/add-article  <-- NOT working, I believe a multipart request has to be used for file uploading
//	        String titleMap = "{\"en_US\":\"Title SG\"}";
//	        String descriptionMap = "{\"en_US\":\"Description SG\"}";
//	        String uuid = "79c257d5-05b4-3c95-fa61-5c5c4710ee9d";
//	        String content = "<root available-locales=\"en_US\" default-locale=\"en_US\"><dynamic-element "
//	                + "instance-id=\"random\" language-id=\"en_US\"  name=\"image\" type=\"image\" index-type=\"text\"><dynamic-content "
//	                + "id=\"38897\">"
//	                + "</dynamic-content></dynamic-element></root>";
//	        String content = "<root available-locales=\"en_US\" default-locale=\"en_US\"><dynamic-element "
//	        		+ "instance-id=\"random\" language-id=\"en_US\"  name=\"image\" type=\"image\" index-type=\"text\"><dynamic-content "
//	        		+ "id=\"38897\">documents/" + 20142 + "/"+ 0 + "/"+ "screenshot.png" + "/" + uuid
//	        		+ "</dynamic-content></dynamic-element></root>";

//	        Calendar calendar = Calendar.getInstance();
//	        int displayDateMonth = calendar.get(Calendar.MONTH);
//	        int displayDateDay = calendar.get(Calendar.DAY_OF_MONTH-1);
//	        int displayDateYear = calendar.get(Calendar.YEAR);
//	        int displayDateHour = calendar.get(Calendar.HOUR_OF_DAY);
//	        int displayDateMinute = calendar.get(Calendar.MINUTE);
//	        
//	        String images = "{\"b\":\"c\"}";
//	        String serviceContext = "{\"addGroupPermissions\":false" +
//	                ",\"addGuestPermissions\":false" +
//	                ", \"scopeGroupId\":20142\"}";
	        
//	        JSONObject jsonObject = new JSONObject();
//			jsonObject.put("groupId", 20142);
//			jsonObject.put("folderId", 0);
//			jsonObject.put("classNameId", 0);
//			jsonObject.put("classPK", 0);
//			jsonObject.put("articleId", 0);
//			jsonObject.put("autoArticleId", true);
//			jsonObject.put("titleMap", titleMap);
//			jsonObject.put("descriptionMap", descriptionMap);
//			jsonObject.put("content", content);
//			jsonObject.put("type", "general");
//			jsonObject.put("ddmStructureKey", 36675);
//			jsonObject.put("ddmTemplateKey", 36679);
//			jsonObject.put("layoutUuid", "");
//			jsonObject.put("displayDateMonth", displayDateMonth);
//			jsonObject.put("displayDateDay", displayDateDay);
//			jsonObject.put("displayDateYear", displayDateYear);
//			jsonObject.put("displayDateHour", displayDateHour);
//			jsonObject.put("displayDateMinute", displayDateMinute);
//			jsonObject.put("expirationDateMonth", 0);
//			jsonObject.put("expirationDateDay", 0);
//			jsonObject.put("expirationDateYear", 0);
//			jsonObject.put("expirationDateHour", 0);
//			jsonObject.put("expirationDateMinute", 0);
//			jsonObject.put("neverExpire", true);
//			jsonObject.put("reviewDateMonth", 0);
//			jsonObject.put("reviewDateDay", 0);
//			jsonObject.put("reviewDateYear", 0);
//			jsonObject.put("reviewDateHour", 0);
//			jsonObject.put("reviewDateMinute", 0);
//			jsonObject.put("neverReview", true);
//			jsonObject.put("indexable", true);
//			jsonObject.put("smallImage", false);
//			jsonObject.put("smallImageURL", "");
//			jsonObject.put("smallFile", "");
//			jsonObject.put("images", images);
//			jsonObject.put("articleURL",0);
//			jsonObject.put("serviceContext", serviceContext);
//			JSONObject jsonObjectParent = new JSONObject();
//			jsonObjectParent.put("/journal.journalarticle/add-article", jsonObject);

			
			
			
			
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