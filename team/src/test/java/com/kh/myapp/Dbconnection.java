package com.kh.myapp;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
class Dbconnection {
	
	private static Logger logger = LoggerFactory.getLogger(Dbconnection.class);

	@Inject
	DataSource dataSource;
	@Inject
	SqlSession sqlSession;
	
	@Test
	void test() {
		logger.info("데이터소스:"+dataSource);
		try {
			Connection conn = dataSource.getConnection();
			logger.info("Db연결성공"+conn);
		} catch (SQLException e) {
			logger.info("db연결실패");
			e.printStackTrace();
		}
	}
	
	@Test
	void testJdbcTemplate() {
		logger.info("sqlSession:"+sqlSession);
			Connection conn = sqlSession.getConnection();
			logger.info("db연결 성공!!:"+conn);
	}
}
