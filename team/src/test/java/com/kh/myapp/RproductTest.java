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

import com.kh.myapp.product.dao.RproductDAO;
import com.kh.myapp.product.dto.RproductDTO;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
class RproductTest {

	private Logger logger = LoggerFactory.getLogger("RboardTest.class");
	
	@Inject
	RproductDAO rpdao;
	
	RproductDTO rpdto;
	int cnt;
	
	@Test @Disabled
	void write() {
		rpdto = new RproductDTO();
		rpdto.setPnum(4);
		rpdto.setId("test4@test.com");
		rpdto.setNickname("테스터4");
		rpdto.setContent("꿀맛");
		
		try {
			cnt = rpdao.write(rpdto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("삽입 건수 : " + cnt); 
	}
	
	@Test @Disabled
	void modify() {
		rpdto = new RproductDTO();
		rpdto.setContent("다시 먹으니 노맛");
		rpdto.setRnum(4);
		
		try {
			cnt = rpdao.modify(rpdto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("수정 건수 : " + cnt);
	}	
	
	@Test @Disabled
	void delete() {
		
		try {
			cnt = rpdao.delete("2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("삭제 건수 : " + cnt);
	}

	@Test @Disabled
	void list() {
		List<RproductDTO> list = null;
		
		try {
			list = rpdao.list("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info(list.toString());
	}
	 
	@Test @Disabled
	void replyTotalRec() {
		
		try {
			cnt = rpdao.replyTotalRec("3");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("댓글 총계 : " + cnt);
	}
}
