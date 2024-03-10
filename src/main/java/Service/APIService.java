package Service;

import java.net.URL;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.google.gson.JsonArray;

import java.io.IOException;

import static DAO.WiFiDAO.insertWiFi;
import static DAO.WiFiDAO.getCountFromDatabase;

public class APIService {
	private static String API = "http://openapi.seoul.go.kr:8088/586d796a6f6b67753535566e767a68/json/TbPublicWifiInfo/";
	private static OkHttpClient okHttpClient = new OkHttpClient();
	
	public static int countTotalWiFi() throws IOException {
		int count = 0;
		URL url = new URL(API + "1/5/");
		
		Request.Builder builder = new Request.Builder().url(url).get();
		Response response = okHttpClient.newCall(builder.build()).execute();
		
		try {
			if (response.isSuccessful()) {
				ResponseBody responseBody = response.body();
				
				if (responseBody != null) {
					JsonElement jsonElement = JsonParser.parseString(responseBody.string());
					
					count = jsonElement.getAsJsonObject().get("TbPublicWifiInfo")
													   .getAsJsonObject().get("list_total_count")
													   .getAsInt();
				} else {
					System.out.println("실패");
				}
			} 
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		return count;
	}
	
	public static int getPublicWiFiJson() throws IOException {
		int totalCount = countTotalWiFi();
		int start = 1;
		int end = 1;
		int count = 0;
		
		try {
			 int existingCount = getCountFromDatabase();
		        if (existingCount > 0) {
		            return -1; // 이미 저장되어 있음을 나타내는 값
		        }
		        
			for (int i = 0; i <= totalCount / 1000; i++) {
				start = 1 + (1000 * i);
				end = (i + 1) * 1000;
				
				URL url = new URL(API + start + "/" + end);
				 
				Request.Builder builder = new Request.Builder().url(url).get();
				Response response = okHttpClient.newCall(builder.build()).execute();
				
				if (response.isSuccessful()) {
					ResponseBody responseBody = response.body();
					
					if (responseBody != null) {
						JsonElement jsonElement = JsonParser.parseString(responseBody.string());

                        JsonArray jsonArray = jsonElement.getAsJsonObject().get("TbPublicWifiInfo")
                                                         						  .getAsJsonObject().get("row")
                                                         						  .getAsJsonArray();

                        count += insertWiFi(jsonArray);
					} else {
						System.out.println("실패");
					}
				} else {
					System.out.println("실패");
				}
			}
		} catch( Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
}
