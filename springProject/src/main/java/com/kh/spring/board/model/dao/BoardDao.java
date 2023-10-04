package com.kh.spring.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

@Repository
public class BoardDao {
	public int selectListcount(SqlSession sqlSession) {
		return sqlSession.selectOne("boardMapper.selectListCount");
	}
	public ArrayList<Board> selectListcount (SqlSession sqlSession,PageInfo pi){
		return sqlSession.se
	}
	

}
