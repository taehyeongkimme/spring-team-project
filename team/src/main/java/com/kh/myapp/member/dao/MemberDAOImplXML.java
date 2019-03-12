package com.kh.myapp.member.dao;

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
import com.kh.myapp.member.dto.MemberDTO;

@Repository
public class MemberDAOImplXML implements MemberDAO {

	private static Logger logger = LoggerFactory.getLogger(MemberDAOImplXML.class);
	
	@Inject
	SqlSession sqlSession;
	
	// 회원가입
	@Override
	public boolean insert(MemberDTO memberDTO) {
		logger.info("MemberDAOImplXML.insert 호출됨");
		boolean success = false;
		int cnt = sqlSession.insert("mappers.member.memberInsert", memberDTO);
		if(cnt > 0) { success = true; }
		return success;
	}

	// 회원 정보 수정
	@Override
	public boolean modify(MemberDTO memberDTO) {
		logger.info("MemberDAOImplXML.modify 호출됨");
		boolean success = false;
		int cnt = sqlSession.update("mappers.member.memberUpdate", memberDTO);
		if(cnt > 0) { success = true; }
		return success;
	}

	// 회원 탈퇴
	@Override
	public boolean delete(String id, String pw) {
		logger.info("MemberDAOImplXML.delete 호출됨");
		boolean success = false;
		Map<String,String> map = new HashMap<>();
		map.put("id", id);
		map.put("pw", pw);
		int cnt = sqlSession.delete("mappers.member.memberDelete", map);
		if(cnt > 0) { success = true; }
		return success;

	}

	// 회원 조회
	@Override
	public MemberDTO getMember(String id) {
		logger.info("MemberDAOImplXML.getMember 호출됨");
		MemberDTO memberDTO = null;
		memberDTO = sqlSession.selectOne("mappers.member.memberSelectOne", id);
		return memberDTO;
	}

	// 회원 목록 조회
	@Override
	public List<MemberDTO> getMemberList() {
		List<MemberDTO> list = null;
		list = sqlSession.selectList("mappers.member.memberSelectList");
		return list;
	}

	// 회원 활동 정지
	@Override
	public boolean memberDenied(MemberDTO memberDTO) {
		logger.info("MemberDAOImplXML.memberDenied 호출됨");
		boolean success = false;
		int cnt = sqlSession.update("mappers.member.memberDenied", memberDTO);
		if(cnt > 0) { success = true; }
		return success;
	}

	

	// 회원 목록 요청페이지
	@Override
	public List<MemberDTO> list(int startRec, int endRec) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("startRec", startRec);
		map.put("endRec", endRec);
		
		return sqlSession.selectList("mappers.member.list", map);
	}

	// 회원 검색 목록
	@Override
	public List<MemberDTO> flist(int startRecord, int endRecord, String searchType, String keyword) throws Exception {
		Map<String,Object> map = new HashMap<>();
		
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		
		return sqlSession.selectList("mappers.member.flist", map);
	}

	// 회원 총계
	@Override
	public int totalRec() throws Exception {
		return sqlSession.selectOne("mappers.member.totalRec");
	}

	// 검색 총계
	@Override
	public int searchTotalRec(String searchType, String keyword) throws Exception {
		Map<String,Object> map = new HashMap<>();
		
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		
		return sqlSession.selectOne("mappers.member.searchTotalRec", map);
	}



}
