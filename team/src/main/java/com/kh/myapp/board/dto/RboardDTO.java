package com.kh.myapp.board.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RboardDTO {
	
	private int rnum;
	
	private int bnum;
	
	private String rid;
	
	private String rnickname;
	
	private Timestamp rcdate;
	
	private Timestamp rudate;
	
	private String rcontent;
	
	private int rgroup;
	
	private int rstep;
	
	private int rindent;
	
	private String isdel;
	
	private int rrnum;
	
	private RboardDTO rbdto;

}
