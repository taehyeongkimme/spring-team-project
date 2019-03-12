package com.kh.myapp.board.service;

import java.util.List;

import com.kh.myapp.board.dto.RboardDTO;

public interface RboardSvc {
	
		//댓글 등록
		int write(RboardDTO rbDTO) throws Exception;
		
		//댓글 목록
		List<RboardDTO> list(String bnum) throws Exception;
		List<RboardDTO> list(String bnum, int startRec, int endRec) throws Exception;
		
		//댓글 수정
		int modify(RboardDTO rbDTO) throws Exception;
		
		//댓글 삭제
		int delete(String rnum) throws Exception;
		
		//대댓글 등록
		int reply(RboardDTO rbDTO) throws Exception;
			
		//대댓글 총계
		int replyTotalRec(String bnum) throws Exception;

}
