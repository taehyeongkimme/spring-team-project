package com.kh.myapp.member.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.kh.myapp.board.dto.BoardDTO;
import com.kh.myapp.member.dto.MemberDTO;

public interface MemberDAO {

	// 회원가입
	public boolean insert(MemberDTO memberDTO);
	
	// 회원 정보 수정
	public boolean modify(MemberDTO memberDTO);
	
	// 회원 탈퇴
	public boolean delete(String id, String pw);
	
	// 회원 조회
	public MemberDTO getMember(String id);
	
	// 회원 목록 조회
	public List<MemberDTO> getMemberList();
	List<MemberDTO> list(int startRec, int endRec) throws Exception;
	
	// 회원 검색 목록, 페이징
	List<MemberDTO> flist(int startRecord, int endRecord, String searchType, String keyword) throws Exception;
	
	// 회원 총계
	int totalRec() throws Exception;
	
	// 회원 검색 총계
	int searchTotalRec(String searchType, String keyword) throws Exception;
	
	// 회원 활동 정지
	public boolean memberDenied(MemberDTO memberDTO);

	
}
