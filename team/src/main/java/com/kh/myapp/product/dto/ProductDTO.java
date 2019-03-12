package com.kh.myapp.product.dto;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.kh.myapp.util.Resizer;
import com.kh.myapp.util.SecureLink;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
	
	@Autowired
    ServletContext context;

	private int startRecord;
	private int endRecord;
	// Left Join
	private int target_srl;
	private String type;
	private String sourcename;
	private String originname;
	
	private char ptype;
	private int idx;
	private int pnum;
	private String pname;
	private String ptitle;
	private String pid;
	private String pnickname;
	private Date pcdate;
	private Date pudate;
	private int pgood;
	private int pbad;
	private int phit;
	private String pcontent;
	private String pgroup;
	private String pstore;
	private int price;
	private int regdate;
	private String allergy;

	public String getLink() {
		return "/product/view/" + pnum;
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
	
	public String getTitle() {
		if (ptitle == null) {
			return "제목없음";
		} else {
			String title = null;
			if (ptitle.length() > 30) {
				title = ptitle.substring(0, 27) + "...";
			} else {
				title = ptitle;
			}
			
			if (pgroup != null) {
				title = "[" + pgroup + "] " + title;
			}
			
			return title;
		}
	}

	public String getContent() {
		if (pcontent == null) {
			return "내용없음";
		} else {
			String content = null;
			if (pcontent.length() > 50) {
				content = pcontent.substring(0, 47) + "...";
			} else {
				content = pcontent;
			}
			
			return content;
		}
	}
	
	public ProductDTO(char ptype, int pnum, String pname, String ptitle, String pid, String pnickname, Date pcdate, Date pudate, int pgood, int pbad, int phit, String pcontent, String pgroup, String pstore, int price, int regdate, String allergy) {
		this.ptype = ptype;
		this.pnum = pnum;
		this.pname = pname;
		this.ptitle = ptitle;
		this.pid = pid;
		this.pnickname = pnickname;
		this.pcdate = pcdate;
		this.pudate = pudate;
		this.pgood = pgood;
		this.pbad = pbad;
		this.phit = phit;
		this.pcontent = pcontent;
		this.pgroup = pgroup;
		this.pstore = pstore;
		this.price = price;
		this.regdate = regdate;
		this.allergy = allergy;
	}
	
}
