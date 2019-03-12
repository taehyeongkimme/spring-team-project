package com.kh.myapp.login;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kh.myapp.member.dto.MemberDTO;

@Service
public class LoginSvcImpl implements LoginSvc {

	private static Logger logger = LoggerFactory.getLogger(LoginSvcImpl.class);
	
	@Inject
	LoginDAO loginDAO;
	
	@Override
	public boolean isExist(String id) {
		return loginDAO.isExist(id);
	}

	@Override
	public boolean isMember(String id, String pw) {
		return loginDAO.isMember(id, pw);
	}

	@Override
	public MemberDTO login(String id, String pw) {
		return loginDAO.login(id, pw);
	}

	@Override
	public MemberDTO findId(String name, String phone) {
		return loginDAO.findId(name, phone);
	}

	@Override
	public MemberDTO findPw(String id, String phone, String birth) {
		return loginDAO.findPw(id, phone, birth);
	}

}
