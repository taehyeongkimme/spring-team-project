package com.kh.myapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.myapp.board.dao.BoardDAO;
import com.kh.myapp.board.dto.BoardDTO;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
class BoardTest {

private static Logger logger = LoggerFactory.getLogger(LoginTest.class);
	
	@Inject
	BoardDAO bdao;
	
	// 글작성
//	@Test	//@Disabled
//	void write() {
//			BoardDTO boardDTO = new BoardDTO();
//			boardDTO.setBid("admin@kh.com");
//			boardDTO.setBnickname("관리자");
//			boardDTO.setBtitle("테스트");
//			boardDTO.setBcontent("테스트중입니다");
//			boolean success = bdao.write(boardDTO);
//			logger.info("글작성 성공?"+success);
//	}

	// 글수정
	@Test	@Disabled
	void modify() {
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBnum(10);
		boardDTO.setBtitle("글작성 테스트^^");
		boardDTO.setBcontent("글작성테스트중입니다^^*");
		boolean success = bdao.modify(boardDTO);
		logger.info("글수정 성공?"+success);
	}
	
	// 글삭제
	@Test @Disabled
	void delete() {
		boolean success = bdao.delete("10");
		logger.info("글삭제 성공?"+success);
	}
	
	// 글보기
	@Test //@Disabled
	void view() {
		BoardDTO boardDTO = bdao.view("1002");
		logger.info(boardDTO.toString());
	}
	
//	// 글목록 페이징
//	@Test @Disabled
//	void list() {
//		List<BoardDTO> list = bdao.list(1, 5);
//		logger.info(list.toString());
//	}
	
	// 게시글 총계
	@Test @Disabled
	void totalRec() {
		int cnt = bdao.totalRec();
		logger.info("총게시글수:"+cnt);
	}
	
	// 검색 목록
	@Test @Disabled
	void searchList() {
//		int startRecord = 1;
//		int endRecord = 7;
//		String searchType = "N";
//		String keyword = "터21";
		
		List<BoardDTO> slist = bdao.searchList(1, 20, "I", "test2");
		
		logger.info("목록:"+slist);
	}
	
	// 검색 총계
	@Test @Disabled
	void searchTotalRec() {
		int cnt = bdao.searchTotalRec("TC", "트22");
		logger.info("검색 총계:"+cnt);
	}
	
	// 추천 비추천
	@Test @Disabled
	void goodOrBad() {
		BoardDTO bdto = new BoardDTO();
		bdto.setBnum(1002);
		int cnt = bdao.goodOrBad("1002", "good");
		logger.info("추천"+cnt);
	}
	
	@Test @Disabled
	void goodOrBadLog() {
		boolean success = false;
		int cnt = bdao.goodOrBadLog("B", "1", "admin@kh.com", "G");
		if(cnt>0) {success = true;}
		
		
		logger.info("처리건수"+cnt);
	}
}




















