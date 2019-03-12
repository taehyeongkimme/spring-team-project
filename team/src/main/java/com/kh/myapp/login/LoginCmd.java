package com.kh.myapp.login;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class LoginCmd {

	// 로그인시
	@Pattern(regexp="^\\w+@\\w+\\.\\w+(\\.\\w+)?$", message="ex)aaa@bbb.com")
	private String id;		// 로긴 아이디
	@Size(min=4,max=30, message="비밀번호는 4~30자리로 입력바랍니다.")
	private String pw;		// 로긴 비밀번호
	
	// 아이디 비밀번호 찾기시
	private String name;	// 이름
	@Pattern(regexp="^(02|010)-\\d{3,4}-\\d{4}$",message="ex)010-1234-5678")
	private String phone;	// 전화번호
	@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="ex)xxxx-xx-xx")
	private String birth;	// 생년월일
	
	
}
