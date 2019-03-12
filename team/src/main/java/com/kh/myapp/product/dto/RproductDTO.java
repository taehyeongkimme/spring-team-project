package com.kh.myapp.product.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RproductDTO {
	
	private int rnum;
	
	private int pnum;
	
	private String id;
	
	private String nickname;
	
	private Timestamp cdate;
	
	private Timestamp udate;
	
	private String content;
	
}
