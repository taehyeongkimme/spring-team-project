package com.kh.myapp.login;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kh.myapp.member.dto.MemberDTO;

@Repository
public class LoginDAOImplXML implements LoginDAO {

	private static Logger logger = LoggerFactory.getLogger(LoginDAOImplXML.class);
	
	@Inject
	SqlSession sqlSession;
	
	// 회원 정보 존재 유무
	@Override
	public boolean isExist(String id) {
		boolean success = false;
		int cnt = sqlSession.selectOne("mappers.login.isExist", id);
		if(cnt>0) {success = true;}
		return success;
	}

	// 정상회원 체크
	@Override
	public boolean isMember(String id, String pw) {
		boolean success = false;
		Map<String,String> map = new HashMap<>();
		map.put("id", id);
		map.put("pw", pw);
		int cnt = sqlSession.selectOne("mappers.login.isMember", map);
		if(cnt>0) {success = true;}
		return success;
	}

	// 로그인
	@Override
	public MemberDTO login(String id, String pw) {
		Map<String,String> map = new HashMap<>();
		map.put("id", id);
		map.put("pw", pw);
		MemberDTO mdto = sqlSession.selectOne("mappers.login.login", map);
		return mdto;
	}

	// 아이디 찾기
	@Override
	public MemberDTO findId(String name, String phone) {
		Map<String,String> map = new HashMap<>();
		map.put("name", name);
		map.put("phone", phone);
		MemberDTO mdto = sqlSession.selectOne("mappers.login.findId", map);
		return mdto;
	}

	// 비밀번호 찾기
	@Override
	public MemberDTO findPw(String id, String phone, String birth) {
		Map<String,String> map = new HashMap<>();
		map.put("id", id);
		map.put("phone", phone);
		map.put("birth", birth);
		MemberDTO mdto = sqlSession.selectOne("mappers.login.findPw", map);
		return mdto;
	}

}
