package com.kh.ajax.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Member;

@Controller
public class AjaxController {
	/*
	 * 1.HttpServletResponse 객체로 응답데이터 응답하기(기존의 jsp,servlet 때 했던 stream 이용한 방식)
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age,HttpServletResponse response) throws IOException {
		System.out.println(name);
		System.out.println(age);
		
		//요청처리를 위해 서비스 호출
		
		//요청 처리가 다 됐다는 가정하에 요청한 그 페이지에 응답할 데이터가 있을경우
		String responseData = "응답문자열 : " +  name + "은(는)" + age+ "살 입니다.";
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(responseData);
	}
	*/
	
	
	/*
	 * 2.응답할 데이터로 문자열로 리턴
	 * =>HttpServletResponse 를 안쓸 수 있음
	 * 단 문자열을 리턴하면 return은 원래는 포워딩 방식 => 그래서 응답뷰 페이지를 찾고있음
	 * 따라서 내가 리턴하는 문자열이 응답뷰가 아닌 응답데이터라는걸 선언해야하낟.
	 * 어노테이션 @responsebody
	 * */
	
	/*
	@ResponseBody
	@RequestMapping(value="ajax1.do",produces="text/html; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		String responseData = "응답문자열 : " +  name + "은(는)" + age+ "살 입니다.";
		return responseData;
	}
	*/
	
	/*
	//다수의 응답데이터가 있을 경우?
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age,HttpServletResponse response) throws IOException {
		//요청처리가 다 됐다는 가정하에 데이터 응답
		
		
		//response.setContentType("text/html; charset=UTF-8");
		//response.getWriter().print(name);
		//response.getWriter().print(age);
	    //연이어서 출력하는 데이터가 하나의 문자열로 연이어져 있음
	    
		
		//JSON(javaScipt object Notation) 형태로 담아서 응답
		//JSONArray => [값,값,값,,] 인덱스 개념  	=> 자바에서의 ArrayList 와 유사
		//JSONobject => {키:값, 키:값 , 키:값} 인덱스개념 x 키 밸류로 이어져 있음 =>  자바에서의 HashMap과 유사
		
		//첫번째 방법. jsonArray로 담아서 응답
		
		//JSONArray jArr = new JSONArray(); //[]
		//jArr.add(name); //["차은우"]
		//jArr.add(age); //["차은우", 20]
		
		//response.setContentType("application/json; charset=UTF-8");
		//response.getWriter().print(jArr);
		
		
		//두번째 방법. jsonObject로 담아서 응답
		//JSONObject jObj = new JSONObject(); //{}
		//jObj.put("name", name); //{name:'차은우'}
		//jObj.put("age", age); //{name:'차은우',age:26}
		
		//response.setContentType("application/json; charset=UTF-8");
		//response.getWriter().print(jObj);
	
	}
	*/
	
	@ResponseBody
	@RequestMapping(value= "ajax1.do", produces= "application/json; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		JSONObject jObj = new JSONObject(); //{}
		
		jObj.put("name", name); //{name:'차은우'}
		jObj.put("age", age); //{name:'차은우',age:10}
		
		return jObj.toJSONString(); // "{name:'차은우',age:10}"
	}
	/*
	@ResponseBody
	@RequestMapping(value="ajax2.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod2(int num) {
		
		//Member m = mService.selectMember(num);
		
		Member m = new Member("user01", "pass01", "차은우", 20, "01011112222");
		
		//JSON형태로 만들어서 응답
		JSONObject jObj = new JSONObject(); //{}
		jObj.put("userId", m.getUserId());
		jObj.put("userName", m.getUserName());
		jObj.put("age", m.getAge()); //{name:'차은우',age:10}
		jObj.put("phone", m.getPhone());
		
		return jObj.toJSONString();
	
	}
	*/
	
	@ResponseBody
	@RequestMapping(value="ajax2.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod2(int num) {
		
		//Member m = mService.selectMember(num);
		
		Member m = new Member("user01", "pass01", "차은우", 20, "01011112222");
		
		
		
		return new Gson().toJson(m); //{userId:'user01',userPwd:'pass01',...}
	}
	
	@ResponseBody
	@RequestMapping(value="ajax3.do",produces="application/json; charset=UTF-8")
	public String ajaxMethod3() {
		
//		ArrayList<Member> list = new mService.selectList();
		ArrayList<Member> list = new ArrayList<Member>(); // []
		
		list.add(new Member("user01","pass01","차은우",20,"01011112222")); //[{차은우}]
		list.add(new Member("user02","pass02","정우영",30,"01033332222")); //[{차은우},{정우영}]
		list.add(new Member("user03","pass03","엄원상",40,"01044442222")); //[{차은우},{정우영},{엄원상}]
	
		return new Gson().toJson(list);
	}
	

}
