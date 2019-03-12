package com.kh.myapp;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.myapp.login.LoginDAO;
import com.kh.myapp.member.dto.MemberDTO;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
class LoginTest {

	private static Logger logger = LoggerFactory.getLogger(LoginTest.class);
	
	@Inject
	LoginDAO ldao;
	int cnt;
	
	// 회원 정보 존재 유무
	@Test	@Disabled
	void isExist() {
		boolean success = ldao.isExist("admin@kh.com");
		logger.info("회원 정보 존재?"+success);
	}
	
	// 정상회원 체크
	@Test	@Disabled
	void isMember() {
		boolean success = ldao.isMember("admin@kh.com", "admin");
		logger.info("정상입력?"+success);
	}

	// 로그인
	@Test	@Disabled
	void login() {
		MemberDTO mdto = ldao.login("admin@kh.com", "admin");
		logger.info(mdto.toString());
	}
	
	// 아이디 찾기
	@Test	@Disabled
	void findId() {
		MemberDTO mdto = ldao.findId("관리자", "010-0000-0000");
		logger.info(mdto.toString());
	}
	
	// 비밀번호 찾기
	@Test @Disabled
	void findPw() {
		MemberDTO mdto = ldao.findPw("1@1.1", "010-1111-1111", "2019-02-01");
		logger.info(mdto.toString());
	}
}
























