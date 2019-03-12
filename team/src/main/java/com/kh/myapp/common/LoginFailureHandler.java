package com.kh.myapp.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class LoginFailureHandler implements AuthenticationFailureHandler{

	private String defaultFailureUrl;

	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {


		
		request.setAttribute("errMsg", "아이디와 비밀번호가 일치하지 않습니다.");
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);

		
	}


	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}


	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}
	
}
