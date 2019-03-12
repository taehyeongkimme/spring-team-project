package com.kh.myapp.board.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.kh.myapp.board.dto.BoardDTO;
import com.kh.myapp.board.dto.CntLog;
import com.kh.myapp.product.dto.filesDTO;

public interface BoardDAO {

	// 글작성
	public int write(BoardDTO boardDTO);
	
	public boolean insertInsertedFile(filesDTO filesdto);
	
	// 글수정
	public boolean modify(BoardDTO boardDTO);
	
	// 글삭제
	public boolean delete(String bnum);

	// 글보기
	public BoardDTO view(String bnum);
	
	// 추천 비추천
	public int goodOrBad(String bnum, String goodOrBad);
	
	// 인기글 
	public List<BoardDTO> best(); 
	
	// 조회수 로그
	public void hitLog(String type, int num, String id);
	
	// 추천 비추천 로그
	public int goodOrBadLog(String type, String num, String id, String goodOrBad);
	
	// 글목록
	public List<BoardDTO> list();
	public List<BoardDTO> list(int startRec, int endRec, String string);
	
	// 게시글 총계
	public int totalRec();

	// 검색 목록
	public List<BoardDTO> searchList(int startRecord, int endRecord, String searchType, String keyword);
	
	// 검색 총계
	public int searchTotalRec(String searchType, String keyword);	
	
}
