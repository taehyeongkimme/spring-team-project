package com.kh.myapp.product.dao;

import java.util.List;

import com.kh.myapp.board.dto.RboardDTO;
import com.kh.myapp.product.dto.RproductDTO;

public interface RproductDAO {
	
		//댓글 등록
		int write(RproductDTO rpDTO) throws Exception;
			
		//댓글 목록
		List<RproductDTO> list(String pnum) throws Exception;
		List<RproductDTO> list(String pnum, int startRec, int endRec) throws Exception;
			
		//댓글 수정
		int modify(RproductDTO rpDTO) throws Exception;
			
		//댓글 삭제
		int delete(String rnum) throws Exception;
				
		//대댓글 총계
		int replyTotalRec(String pnum) throws Exception;
}
