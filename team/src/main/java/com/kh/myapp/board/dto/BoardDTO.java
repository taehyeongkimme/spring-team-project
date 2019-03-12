package com.kh.myapp.board.dto;

import java.security.Timestamp;
import java.sql.Date;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

import org.springframework.ui.Model;

import com.kh.myapp.util.PageCriteria;
import com.kh.myapp.util.SecureLink;

import lombok.Data;

@Entity
@Data
public class BoardDTO {

	// Left Join
	private int target_srl;
	private String type;
	private String sourcename;
	private String originname;
	
	private String best;
	
	private int idx;	
	private int bnum;					// 글번호
	@Size(min=4, max=30, message="제목은 4~100자내로 입력가능합니다.")
	private String btitle;		// 글제목
	private String bid;				// 작성자아이디
	private String bnickname;	// 작성자닉네임
	private Timestamp bcdate;			// 작성일
	private Timestamp budate;			// 수정일
	private int bgood;				// 추천
	private int bbad;					// 비추천
	private int bhit;					// 조회수
	private String bcontent;	// 글내용
	private String btype;	    // 게시판 타입

	public boolean isThubmanilExist() {
			if (sourcename == null) {
				return false;
			}
		
			return true;
	}
	public String getImage() {
		if (sourcename == null) {
			return "".toString();
		} else {
			int timeout = 5000;
			String uploadedImage = "/resources/upload/" + sourcename;
			
			String key = "34x378";
			String plainKey = uploadedImage;
			String prefix = SecureLink.get(key, plainKey, timeout);
			
			return uploadedImage + "?t=" + prefix + "&c=" + timeout;
		}
	}
	
	public String getLink(PageCriteria pc) {
		return "/board/view?bnum=" + bnum + "&" + pc.makeSearchURL(pc.recordCriteria.reqPage);
	}
	
}
