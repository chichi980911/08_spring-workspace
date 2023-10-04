package com.kh.spring.board.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {
	
	@Autowired
	private BoardServiceImpl bservice;
	
	
	//메뉴바 클릭시 			/list.bo
	//페이징바 클릭시 		/list.bo?cage=요청하는 페이지수
	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value="cpage",defaultValue = "1") int currentPage) {
		
//		System.out.println(currentPage);
		int listCount = bservice.selectListcount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		
		ArrayList<Board> list = bservice.selectList(pi);
	}
	

}
