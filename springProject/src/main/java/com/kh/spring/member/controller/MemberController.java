package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller
//Controller 타입의 어노테이션을 붙여주면 빈스캐닝을 통해 자동으로 빈 등록
public class MemberController {
	
	@Autowired  //DI (Dependency Injection) 특징
	private MemberServiceImpl mService;
	
	/*
	@RequestMapping(value="login.me") //requestMapping 타입의 어노테이션을 붙여줌으로써 handlerMapping 등록
	public void loginMember() {
		
	}
	
	@RequestMapping(value="insert.me")
	public void insertMember() {
		
	}
	@RequestMapping(value="update.me")
	public void updateMember() {
		
	}*/
	
	
	/*파라미터(사용자 요청시 전달값)을 처리방법
	 * 
	 * 1.HttpServletRequest를 이용해서 전달받기 (기존의 jsp/servlet 방식)
	 * 	해당메서드의 매개변수로 HttpServletRequest를 작성해 두면
	 * 스프링 컨테이너가 해당 메서드 호출시 자동으로 해당객체를 생성해서 인자로 추가
	 * 
	@RequestMapping("login.me")
	public String loginMember(HttpServletRequest request) {
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pwd");
	
		System.out.println("ID : " +userId);
		System.out.println("Pwd : " +userPwd);
		
		return "main";
	}
	*/
	
	
	/*
	 * 2.RequestParam 어노테이션을 이용하는 방법
	 * request.getParameter("키") : 벨류의 역할을 대신해주는 어노테이션
	 * 
	@RequestMapping("login.me")
	public String loginMember(@RequestParam(value="id",defaultValue="aaa") String userId,
							  @RequestParam(value="pwd") String userPwd){
		System.out.println("ID : " +userId);
		System.out.println("Pwd : " +userPwd);
		
		return "main";
	}
	*/
	
	/*
	 * 3.@RequestParam 어노테이션 생략하는 방법
	 *   단 , 매개변수명 name값(요청시전달값의 키값) 과 동일하게 세팅해둬야 자동으로 값이 주입됨
	 * 
	@RequestMapping("login.me")
	public String loginMember(String id, String pwd) {
		
		System.out.println("ID : " +id);
		System.out.println("Pwd : " +pwd);
		
		Member m = new Member();
		m.setUserId(id);
		m.setUserPwd(pwd);
		
		//service 호출시 m 전달
		return "main";
	}	
	*/
	
	/*
	 * 4.커맨드 객체 방식
	 * 해당메서드 매개변수로 요청시 전달값을 담고자하는 vo클래스 타입을 세팅 후 
	 * 요청시 전달값의 키값(jsp상의 name값)을 vo클래스에 담고자 하는 필드명으로 작성
	 * 
	 * 스프링컨테이너가 해당객첼르 기본생성자로 생성 후 setter 메서드 찾아서 
	 * 요청시 전달값을 해당 필드에 담아주는 내부적인 원리 
	 * 
	 * *반드시 name속성값 (키값)과 담고자 하는 필드명 동일해야함
	 * *요청 처리 후 응답페이지로 포워딩 또는 url 재요청, 응답데이터 담는 방법
	 * 
	 * 1.스프링에서 제공하는 Model 객체를 사용하는 방법
	 * 	 포워딩할 뷰로 전달하고자 하는 데이터를 맵형식(key-value)으로 담을 수 있는 영역
	 *   Model 객체는 requestScope이다. 
	 *   단, setAttribute가 아닌 addAttribute 메서드 이용
	 * */
	@RequestMapping("login.me")
	public String loginMember(Member m , Model model, HttpSession session) {
		Member loginMember =  mService.loginMember(m);
		if(loginMember == null) {
			//로그인실패 =>에러페이지 requestScope에 담아서 포워딩
			model.addAttribute("errorMsg","로그인실패");
			return "common/errorPage"; //WEB-INF/views xxx.jsp > servlet-context.xml prefix
			
		}else {
			//로그인성공 => 메인페이지 url 재용청 =>loginMember를 sessionScope에 담아
			session.setAttribute("loginMember", loginMember);
			return "redirect:/";
		}
	}
	
	@RequestMapping("myPage.me")
	public String myPage() {
		return "member/myPage";
	}
}
