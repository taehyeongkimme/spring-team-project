package com.kh.myapp.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.kh.myapp.board.dto.BoardDTO;
import com.kh.myapp.board.dto.CntLog;
import com.kh.myapp.product.dto.filesDTO;

@Repository
public class BoardDAOImplXML implements BoardDAO {

	private static Logger logger = LoggerFactory.getLogger(BoardDAOImplXML.class);
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public boolean insertInsertedFile(filesDTO filesdto) {
		int cnt = 0;
		cnt = sqlSession.insert("productDAO.insertInsertedFile", filesdto);
		
		if (cnt > 0) {
			return true;
		}
		
		return false;
	}
	
	// 글작성
	@Override
	public int write(BoardDTO boardDTO) {
		/*boolean success = false;
		int cnt = sqlSession.insert("mappers.board.write", boardDTO);
		if (cnt > 0) {
			success = true;
		}
		
		return success;*/
		return sqlSession.insert("mappers.board.write", boardDTO);
	}

	// 글수정
	@Override
	public boolean modify(BoardDTO boardDTO) {
		boolean success = false;
		int cnt = sqlSession.update("mappers.board.modify", boardDTO);
		if (cnt > 0) {
			success = true;
		}
		
		return success;
	}

	// 글삭제
	@Override
	public boolean delete(String bnum) {
		boolean success = false;
		int cnt = sqlSession.delete("mappers.board.delete", bnum);
		if (cnt > 0) {
			success = true;
		}
		
		return success;
	}
	
	// 글보기
	@Override
	public BoardDTO view(String bnum) {
		BoardDTO boardDTO = null;
		boardDTO = sqlSession.selectOne("mappers.board.view", bnum);
		
		// 조회수 로그 체크(게시판 타입,문서번호, 사용자아이디)
		int cnt = 0;
		cnt = hitLogChk(boardDTO.getBtype(),boardDTO.getBnum(), boardDTO.getBid(),"Y");
		
		if (cnt == 0) {
			// 조회수 증가
			updateHit(boardDTO.getBnum());
			
			// 조회수 로그 입력(게시판 타입,문서번호, 사용자아이디)
			hitLog(boardDTO.getBtype(),boardDTO.getBnum(), boardDTO.getBid());
			return boardDTO;
		} else {
			return boardDTO;
			
		}
	}
	
	// 조회수 로그 체크(게시판 타입,문서번호, 사용자아이디)
	private int hitLogChk(String type, int num, String id, String hitYn) {
		Map<String,Object> map = new HashMap<>();
		map.put("type", type);
		map.put("num", num);
		map.put("id", id);
		map.put("hitYn", hitYn);
		
		return sqlSession.selectOne("hitLogChk", map);
	}

	// 조회수 증가
	private void updateHit(int bnum) {
		sqlSession.update("mappers.board.updateHit", bnum);
	}

	//조회수 로그 등록
	@Override
	public void hitLog(String type, int num, String id) {
		Map<String,Object> map = new HashMap<>();
		map.put("type", type);
		map.put("num", num);
		map.put("id", id);
		sqlSession.insert("mappers.board.hitLog", map);
	}
	
	// 글목록 기본
	@Override
	public List<BoardDTO> list() {
		return sqlSession.selectList("mappers.board.listOld");
	}

	// 글목록 페이징
	@Override
	public List<BoardDTO> list(int startRec, int endRec, String best) {
		Map<String,Object> map = new HashMap<>();
		map.put("startRec", startRec);
		map.put("endRec", endRec);
		if (best != null) {
			map.put("best", best);
		}
		
		return sqlSession.selectList("mappers.board.list", map);
	}

	// 게시글 총계
	@Override
	public int totalRec() {
		return sqlSession.selectOne("mappers.board.totalRec");
	}

	// 검색목록
	@Override
	public List<BoardDTO> searchList(int startRecord, int endRecord, String searchType, String keyword) {
		Map<String,Object> map = new HashMap<>();
		
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		
		return sqlSession.selectList("mappers.board.searchList", map);
	}

	// 검색 총계
	@Override
	public int searchTotalRec(String searchType, String keyword) {
		Map<String,Object> map = new HashMap<>();
		
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		
		return sqlSession.selectOne("mappers.board.searchTotalRec", map);
	}

	// 추천 비추천
	@Override
	public int goodOrBad(String bnum, String goodOrBad) {
		Map<String,Object> map = new HashMap<>();
		map.put("bnum", bnum);
		map.put("goodOrBad", goodOrBad);
		
		return sqlSession.update("mappers.board.goodOrBad", map);
	}
	
	// 인기글
	@Override
	public List<BoardDTO> best() {
		return sqlSession.selectList("mappers.board.best");
	}

	// 추천 비추천 로그
	@Override
	public int goodOrBadLog(String type, String num, String id, String goodOrBad) {
		Map<String,Object> map = new HashMap<>();
		map.put("type", type);
		map.put("num", num);
		map.put("id", id);
		map.put("count_type", goodOrBad);
		return sqlSession.update("mappers.board.goodOrBadLog", map);
	}

}
