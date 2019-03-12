package com.kh.myapp;

import java.util.ArrayList;
import java.util.List;

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

import com.kh.myapp.member.dao.MemberDAO;
import com.kh.myapp.member.dto.MemberDTO;
import com.kh.myapp.member.service.MemberSvc;


@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
class MemberTest {

	private static Logger logger = LoggerFactory.getLogger(MemberTest.class);
	
	@Inject
	MemberDAO memberDAO;
	MemberSvc memberSvc;
	MemberDTO memberDTO;
	int cnt;
	
	@BeforeEach
	void beforeEach() {
		memberDTO = new MemberDTO();
	}
	
	// 회원가입
	@Test @Disabled
	void insert() {
		
		for(int i=1; i<10; i++) {
			memberDTO = new MemberDTO();
			memberDTO.setId("test"+i+"@test.com");
			memberDTO.setPw("test"+i);
			memberDTO.setName("테스트"+i);
			memberDTO.setNickname("테스터_"+i);
			memberDTO.setPhone("010-1111-1111");
			memberDTO.setGender("M");
			memberDTO.setBirth("1999-09-0"+i);
			
			boolean success = memberSvc.insert(memberDTO);
			logger.info("회원가입 처리:"+success);
		}
	}
	
	// 회원 정보 수정
	@Test	@Disabled
	void modify() {
		memberDTO.setId("test1@test.com");
		memberDTO.setPw("test1");
		memberDTO.setName("일테스트");
		memberDTO.setNickname("테스터일번");
		memberDTO.setGender("W");
		memberDTO.setPhone("010-1111-1111");
		memberDTO.setBirth("1999-01-01");
		
		boolean success = memberDAO.modify(memberDTO);
		logger.info("회원 정보 수정 처리:"+success);
	}
	
	// 회원 탈퇴
	@Test	@Disabled
	void delete() {
		boolean success = memberDAO.delete("test4@test.com", "test4");
		logger.info("회원 탈퇴 처리:"+success);
	}
	
	// 회원 조회
	@Test	@Disabled
	void getMember() {
		memberDTO = memberDAO.getMember("test1@test.com");
		logger.info("회원 정보:"+memberDTO);
	}
	
	// 회원 목록 조회
	@Test	@Disabled
	void getMemberList() {
		List<MemberDTO> list = new ArrayList<>();
		list = memberDAO.getMemberList();
		logger.info("회원 목록:"+list);
	}
	
	// 회원 활동 정지
	@Test	@Disabled
	void memberDenied() {
		memberDTO.setDenied("Y");
//		boolean success =	memberDAO.memberDenied("test2@test.com");
//		logger.info("회원 활동 정지:"+success);
	}

}
