package com.kh.myapp;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.myapp.board.dao.RboardDAO;
import com.kh.myapp.board.dto.RboardDTO;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
class RboardTest {
	
	private Logger logger = LoggerFactory.getLogger("RboardTest.class");
	
	@Inject
	RboardDAO rbdao;
	
	RboardDTO rbdto;
	int cnt1,cnt2;

	@Test @Disabled
	void insert() {
		rbdto = new RboardDTO();
		rbdto.setBnum(2);
		rbdto.setRid("test2@test.com");
		rbdto.setRnickname("테스터2");
		rbdto.setRcontent("2글에 대한 답글");

		try {
			cnt1 = rbdao.write(rbdto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("처리 건수 : " + cnt1);
	}
	
	@Test @Disabled
	void modify() {
		rbdto = new RboardDTO();
		rbdto.setRcontent("2글에 대한 답글 수정");
		rbdto.setRnum(9);
		
		try {
			cnt1 = rbdao.modify(rbdto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("수정 건수 : " + cnt1);
	}
	
	@Test @Disabled
	void delete() {
		rbdto = new RboardDTO();
		rbdto.setRnum(7);
		
		try {
			cnt1 = rbdao.delete("7");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("삭제건수 :" + cnt1);
	}
	
	@Test @Disabled
	void reply() {
		rbdto = new RboardDTO();
		rbdto.setBnum(8);
		rbdto.setRid("test3@test.com");
		rbdto.setRnickname("테스터3");
		rbdto.setRcontent("8번글에 대한 대댓글");
		rbdto.setRgroup(8);
		rbdto.setRstep(0+1);
		rbdto.setRindent(0+1);
		rbdto.setRrnum(8);
		
		try {
			cnt1 = rbdao.reply(rbdto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("대댓글 등록 : " + cnt1);
	}
	
	@Test @Disabled
	void replyTotalRec() {
		
		try {
			cnt1=rbdao.replyTotalRec("2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("대댓글 총계" + cnt1);
	}
	
	@Test	//@Disabled
	void list() {
		List<RboardDTO> list = null;
		
		try {
			list = rbdao.list("100");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info(list.toString());
	}
}
