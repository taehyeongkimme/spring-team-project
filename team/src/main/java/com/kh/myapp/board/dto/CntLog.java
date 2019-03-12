package com.kh.myapp.board.dto;

import lombok.Data;

/*
 * 		TYPE	게시판타입
			 NUM	타겟글번호
		    ID	아이디
COUNT_TYPE	추천 / 비추천
		 HITYN	조회수 유무

 */

@Data
public class CntLog {

	private String type; 
	private int num;
	private String id;
	private String count_type;
	private String hitYn;
	
	BoardDTO bdto;
	
}
