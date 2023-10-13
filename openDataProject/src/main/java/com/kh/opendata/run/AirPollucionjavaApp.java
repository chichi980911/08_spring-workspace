package com.kh.opendata.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.opendata.model.vo.AirVO;



public class AirPollucionjavaApp {

	//발급받은 인증키 정보 변수에 담아두기
	public static final String serviceKey = "idkcb%2FM7tkvgBFa9N3YFJ%2BZhsMQSSDKCIFiVEzIlHfAWvqVpuPsuAN8ERIUiJiyYjUoN5d2vB3KqF624EbVHEg%3D%3D";
	
	public static void main(String[] args) throws IOException {
		
		
		//openAPI 서버로 요청하고자 하는 URL 만들기
		
		String url="https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		//url += "?serviceKey=서비스키"; //서비스 키가 제대로 부여되지 않았을 경우 =>SERVICE_KEY_IS_NOT_REGISTERED_ERROR
		
		url += "?servicekey=" + serviceKey;
		url += "&sidoName=" + URLEncoder.encode("서울","UTF-8");//요청시 전달값 중 한글이 있을경우 인코딩 처리를 해야한다.
		url += "&returnType=json";
		
		System.out.println(url);
		
		//**HTTPURLConnection 객체를 활용해서 OpenAPI 요청 절차 **
		// 1. 요청하고자 하는 url 전달하면서 java.net.URL 객체 생성
		URL requestUrl = new URL(url);
		
		// 2. 1번과정으로 생성된 URL객체를 가지고 HttpURLConnection 객체 생성
		HttpsURLConnection urlConnection = (HttpsURLConnection)requestUrl.openConnection(); 
		
		// 3. 요청에 필요한 Header설정하기
		urlConnection.setRequestMethod("GET");
		
		// 4. 해당 openAPI 서버로 요청 보낸 후 입력 스트림을 통해 응답 데이터 읽어오기
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		//보조 =>기반
		
		String responseText ="";
		String line;
		while((line = br.readLine())!= null) {
			//System.out.println(line);
			responseText += line;
			System.out.println(responseText);
		}
		
		//jsonObject,JSONArray,JSONElement 이용해서 파싱 할 수 있다(gson 라이브 러리)=> 내가 원하는 데이터만을 추출할 수 있음
		//각각의 item 정보를 =>airVO => 객체에 담고 =>ArrayList에 담기
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
		JsonObject responseObj = totalObj.getAsJsonObject("response"); //response 속성 접근 => {} JsonObject 
		JsonObject bodyObj = responseObj.getAsJsonObject("body"); //body속성 접근 = > {} JsonObject
		System.out.println(bodyObj);
		int totalCount = bodyObj.get("totalCount").getAsInt(); //totalCount 속성 접근 =>40 int
		JsonArray itemArr = bodyObj.getAsJsonArray("items"); //items 속성접근 => [] JsonArray
		
		ArrayList<AirVO> list = new ArrayList<AirVO>();//[]
		
		
		for(int i=0; i<itemArr.size(); i++) {
			JsonObject item = itemArr.get(i).getAsJsonObject();
			
			AirVO air = new AirVO();
			air.setStationName(item.get("stationName").getAsString());
			air.setDataTime(item.get("dataTime").getAsString());
			air.setKhaiValue(item.get("khaiValue").getAsString());
			air.setSo2Value(item.get("so2Value").getAsString());
			air.setCoValue(item.get("coValue").getAsString());
			air.setPm10Value(item.get("pm10Value").getAsString());
			air.setO3Value(item.get("o3Value").getAsString());
		
			list.add(air);
		}
		for(AirVO a : list) {
			System.out.println(a);
		}
		
		
		
		
		
		/*
		 * {
			"response":
				{
					"body":
						{
							"totalCount":40,
							"items":
								[
									{
										"so2Grade":"1",
										"coFlag":null,
										"khaiValue":"75",
										"so2Value":"0.003",
										"coValue":"0.4",
										"pm10Flag":null,
										"o3Grade":"2",
										"pm10Value":"26",
										"khaiGrade":"2",
										"sidoName":"서울",
										"no2Flag":null,
										"no2Grade":"1",
										"o3Flag":null,
										"so2Flag":null,
										"dataTime":"2023-10-12 14:00",
										"coGrade":"1","no2Value":"0.017",
										"stationName":"중구",
										"pm10Grade":"1",
										"o3Value":"0.060"
										},
		 * */
		
		// 5. 다 사용한 스트림 반납
		
				br.close();
				urlConnection.disconnect();
	}

}
