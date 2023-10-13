package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {

		private static final String serviceKey = "idkcb%2FM7tkvgBFa9N3YFJ%2BZhsMQSSDKCIFiVEzIlHfAWvqVpuPsuAN8ERIUiJiyYjUoN5d2vB3KqF624EbVHEg%3D%3D";
												
		//json
		
		@ResponseBody
		@RequestMapping(value="air.do", produces="application/json; charset=UTF-8")
		public String airPollution(String location) throws IOException {
			
			String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";;
			
			url += "?serviceKey=" + serviceKey;
			url += "&sidoName=" + URLEncoder.encode(location,"UTF-8");
			url += "&returnType=json";
			url += "&numOfRows=50";
			
			//System.out.println(url);
			
			URL requestUrl = new URL(url);
			
			HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
			urlConnection.setRequestMethod("GET");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String responseText = "";
			String line;

			while((line = br.readLine()) != null) {
				responseText += line;
			}
			br.close();
			urlConnection.disconnect();
			
			System.out.println(responseText);
			//{"response":{"body":{"totalCount":40,"items":[{"so2Grade":"1","coFlag":null,~~
			return responseText;
		}
		/*
		//xml
		@ResponseBody
		@RequestMapping(value="air.do", produces="text/xml; charset=UTF-8")
		public String airPollution(String location) throws IOException {
			
			String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";;
			
			url += "?serviceKey=" + serviceKey;
			url += "&sidoName=" + URLEncoder.encode(location,"UTF-8");
			url += "&returnType=xml";
			url += "&numOfRows=50";
			
			//System.out.println(url);
			
			URL requestUrl = new URL(url);
			
			HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
			urlConnection.setRequestMethod("GET");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String responseText = "";
			String line;

			while((line = br.readLine()) != null) {
				responseText += line;
			}
			br.close();
			urlConnection.disconnect();
			
			System.out.println(responseText);
			//{"response":{"body":{"totalCount":40,"items":[{"so2Grade":"1","coFlag":null,~~
			return responseText;
		}
		*/
		
		@ResponseBody
		@RequestMapping(value="disaster.do",produces="text/xml; charset=UTF-8")
			public String disasterShelter() throws IOException {
			
			String url ="https://apis.data.go.kr/1741000/TsunamiShelter3/getTsunamiShelter1List";
			url += "?serviceKey=" + serviceKey;
			url += "&numOfRows=5";
			url += "&returnType=xml";
			
			
			URL requestUrl = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
			urlConnection.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String responseText = "";
			String line;
			
			while((line=br.readLine()) != null) {
				responseText += line;
				
			}
			br.close();
			urlConnection.disconnect();
			return responseText;
		}
		
		@ResponseBody
		@RequestMapping(value="smoke.do",produces="text/xml; charset=UTF-8")
		public String smokingArea() throws IOException {
			
			String url ="https://apis.data.go.kr/6480000/gnPolcCntVsCrmnlOccrrncRtNdArrstRtDataService/getgnPolcCntVsCrmnlOccrrncRtNdArrstRtData";
			url += "?serviceKey=" + serviceKey;
			url += "&resultType=xml";
			url += "&numOfRows=10";
			
			
			URL requestUrl = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
			urlConnection.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String responseText = "";
			String line;
			
			while((line=br.readLine()) != null) {
				responseText += line;
				
			}
			br.close();
			urlConnection.disconnect();
			
			return responseText;
		}
		
		
		@ResponseBody
		@RequestMapping(value="terr.do",produces="application/json; charset=UTF-8")
		public String terr() throws IOException {
			String url ="https://apis.data.go.kr/6480000/gnPolcCntVsCrmnlOccrrncRtNdArrstRtDataService/getgnPolcCntVsCrmnlOccrrncRtNdArrstRtData";
						
			url += "?serviceKey=" + serviceKey;
			url += "&resultType=json";
			url += "&numOfRows=10";
			
			URL requestUrl = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
			urlConnection.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String responseText = "";
			String line;
			
			while((line=br.readLine()) != null) {
				responseText += line;
				
			}
			br.close();
			urlConnection.disconnect();
			return responseText;
		}
	}
