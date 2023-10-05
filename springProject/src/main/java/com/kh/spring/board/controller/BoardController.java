package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.service.BoardServiceImpl;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.PageNation;





@Controller
public class BoardController {
	
	@Autowired
	private BoardServiceImpl bservice;
	
	
	//메뉴바 클릭시 			/list.bo
	//페이징바 클릭시 		/list.bo?cage=요청하는 페이지수
	//@RequestMapping("list.bo")
	//public String selectList(@RequestParam(value="cpage",defaultValue = "1") int currentPage, Model model) {
		
//		System.out.println(currentPage);
//		int listCount = bservice.selectListcount();
//		
//		PageInfo pi = PageNation.getPageInfo(listCount, currentPage, 10, 5);
//		
//		ArrayList<Board> list = bservice.selectList(pi);
//		
//		model.addAttribute("pi",pi);
//		model.addAttribute("list",list);
//		return "board/boardListView";
		
	//}
	
	@RequestMapping("list.bo")
	public ModelAndView selectList(@RequestParam(value="cpage",defaultValue = "1") int currentPage, ModelAndView mv) {
		
		System.out.println(currentPage);
		int listCount = bservice.selectListcount();
		
		PageInfo pi = PageNation.getPageInfo(listCount, currentPage, 10, 5);
		
		ArrayList<Board> list = bservice.selectList(pi);
		
		/*
		mv.addObject("pi",pi);
		mv.addObject("list",list);
		mv.setViewName("board/boardListView");
		*/
		
		mv.addObject("pi",pi).addObject("list",list).setViewName("board/boardListView");
		
		return mv;
	}
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		
		//WEB-INF/views/board/boardEnrollForm.jsp
		return "board/boardEnrollForm";
	}
	
	
	//다중 파일 업로드 기능시?
	//여러개의 input type="file" 요소에 동일한 키값 으로 부여 
	//받아올 때 배열 형식으로 받아오면 된다.  MultipartFile[]
	@RequestMapping("insert.bo")
	public String insertBoard(Board b,MultipartFile upfile,HttpSession session,Model model) {
		
//		System.out.println(b);
//		System.out.println(upfile); 첨부파일을 선택했든 안했든 생성되는 객체이다(다만 filename에 원본명 여부 와 size = 0 으로 파일유무확인가능)
		
		//전달된 파일이 있을 경우 => 파일명 수정 작업 후 서버 업로드 => 원본명, 서버업로드 된 경로를 b에 이어서 담기
		if(!upfile.getOriginalFilename().equals("")) {
			
			//파일명 수정 작업 후 서버에 업로드 시키기("flower.png" => "20231004121530.png")
			/*String originName = upfile.getOriginalFilename(); // 파일원본명

			//"20231004154607" (년월일시분초)
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); //"20231004154708"
			int ranNum = (int)(Math.random() *90000+10000); //5가지 랜덤값 생성
			String ext = originName.substring(originName.lastIndexOf("."));
			
			String changeName = currentTime + ranNum + ext;//20231004154607+5가지 랜덤값 생성+.확장자
		
			//업로드 시키고자 하는 폴더의 물리적인 경로를 알아내기
			String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
			
			try {
				upfile.transferTo(new File(savePath + changeName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}*/
			
			//리팩토링
			String changeName = saveFile(upfile, session);
			//원본명,서버업로드된 경로를 Board 객체 b에 이어서 담기
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		//넘어온 첨부파일 있을 경우 b : 제목,작성자,내용,파일원본명,파일 저장경로
		//넘어온 첨부파일 없을 경우 b : 제목,작성자,내용
		int result = bservice.insertBoard(b);
			
		if(result>0) {//성공=> 게시글 리스트페이지 (list.bo url재요청)
			session.setAttribute("alertMsg", "성공적으로 게시글이 등록되었습니다.");
			return "redirect:list.bo";
		}else {//실패 => 에러페이지 포워딩
			model.addAttribute("errorMsg","게시글 등록에 실패했습니다.");
			return "common/errorPage";
		}
	}
	
	//현재 넘어온 첨부파일 그 자체를 서버의 폴더에 저장시키는 역할
	public String saveFile(MultipartFile upfile, HttpSession session) {
		String originName = upfile.getOriginalFilename(); // 파일원본명

		//"20231004154607" (년월일시분초)
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); //"20231004154708"
		int ranNum = (int)(Math.random() *90000+10000); //5가지 랜덤값 생성
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime + ranNum + ext;//20231004154607+5가지 랜덤값 생성+.확장자
	
		//업로드 시키고자 하는 폴더의 물리적인 경로를 알아내기
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
		System.out.println(savePath);
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}return changeName;
	}
	
	@RequestMapping("detail.bo")
	public String selectBoard(int bno, Model model,HttpSession session) {
		//bno에는 상세조회하고자 하는 게시글 번호가 담겨있다. 
		//해당 게시글 조회수 증가 서비스 호출결과 받기(update 하고 옴)
		
		int result = bservice.increaseCount(bno);
		
		if(result>0) {
			Board b = bservice.selectBoard(bno);
			
			session.setAttribute("b", b);
			return "board/boardDetailView";
			
		}else {
			model.addAttribute("errorMsg","게시물 조회에 실패했습니다.");
			return "common/errorPage";
		}
		
		
		//>> 성공적으로 조회수 증가 
			//>boardDetailView.jsp 살에 필요한 데이터 조회 
			//>board/boardDetailView.jsp로 포워딩
		
		//>> 조회수 증가 실패
		//에러문구담아서 에러페이지 포워딩
		//
	}
	
	@RequestMapping("delete.bo")
	public String deleteBoard(int bno,String filePath,Model model,HttpSession session){//filepath = changeNmae
		int result = bservice.deleteBoard(bno);
		
		//삭제성공
		if(result>0) {
		
			//첨부파일이 있을 경우 => 파일 삭제
			if(!filePath.equals("")){
				new File(session.getServletContext().getRealPath(filePath)).delete();
				}
				//게시판 리스트 페이지 list.bo url 재요청
			session.setAttribute("alertMsg", "성공적으로 게시글이 삭제되었습니다");
			return "redirect:list.bo";
		}else{
			model.addAttribute("errorMsg","게시글삭제실패");
			return "common/errorPage";
		}
	}
	
	@RequestMapping("updateForm.bo")
	public String updateForm(int bno,Model model){
		model.addAttribute("b",bservice.selectBoard(bno));
		return "board/boardUpdateForm";
	}
	
	@RequestMapping("update.bo")
	public String updateBoard(@ModelAttribute Board b,MultipartFile reupfile,HttpSession session,Model model){
		
		//새로넘어온 첨부파일이 있을 경우
		if(!reupfile.getOriginalFilename().equals("")) {
			//기존 첨부파일이 있었을 경우 => 기존 첨부파일 지우기
			if(b.getOriginName()!= null) {
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}
			//새로 넘어온 첨부파일 서버 업로드 시키기
			String changeName = saveFile(reupfile, session);
			
			//b에 넘어온 첨부파일에 대한 원본명, 저장경로 담기
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
			
		}
			/*
			 * b에 boardNo,boardTitle, boardContent 무조건 담겨있음
			 * 
			 * 1.새로 첨부된 파일 x, 기존 첨부파일 x
			 * =>originName:null changeName :null
			 * 
			 * 2.새로 첨부된 파일 x, 기존 첨부파일 o
			 * =>originName: 기존첨파원본명, changeName : 기존첨파저장경로
			 * 
			 * 3.새로 첨부된 파일 o, 기존 첨부파일 x
			 * =>새로 첨부된 파일을 서버 업로드 saveFile로
			 * =>originName : 새로운 첨부파일 원본명, changeName:새로운 첨부파일 저장경로
			 * 
			 * 4.새로 첨부된 파일 o ,기존 첨부파일 o
			 * =>기존 첨부파일 삭제 
			 * =>새로 전달된 파일 서버에 업로드
			 * =>originName : 새로운 첨부파일 원본명, changeName : 새로운 첨부파일 저장경로
			 * 
			 * */
			
			int result = bservice.updateBoard(b);
			
			if(result > 0) {//수정 성공 => 상세페이지
				session.setAttribute("alertMsg", "수정 성공");
				return "redirect:detail.bo?bno=" + b.getBoardNo();
			}else {//수정 실패 => 에러페이지
				model.addAttribute("errorMsg","수정 실패");
				return "common/errorPage";
		}
	}
}
	

