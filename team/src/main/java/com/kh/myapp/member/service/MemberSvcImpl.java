package com.kh.myapp.member.service;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kh.myapp.authority.dto.AuthorityDTO;
import com.kh.myapp.authority.svc.AuthoritySvc;
import com.kh.myapp.board.dto.BoardDTO;
import com.kh.myapp.member.dao.MemberDAO;
import com.kh.myapp.member.dto.MemberDTO;
import com.kh.myapp.util.FindCriteria;
import com.kh.myapp.util.PageCriteria;
import com.kh.myapp.util.RecordCriteria;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberSvcImpl implements MemberSvc {

	private static Logger logger = LoggerFactory.getLogger(MemberSvcImpl.class);
	
	@Inject
	MemberDAO mdao;
	
	@Inject
	AuthoritySvc authoritySvc;

	
	// 회원가입
	@Override
	public boolean insert(MemberDTO memberDTO) {
		boolean success = false;
		
		success = mdao.insert(memberDTO);
		
		if(success) {
			AuthorityDTO authority = new AuthorityDTO();
			authority.setId(memberDTO.getId());
			authority.setRoleId("ROLE_MEMBER");
			success = authoritySvc.insert(authority);
		}
		return success;
	}
	
	// 회원 활동 정지
	@Override
	public boolean memberDenied(MemberDTO memberDTO) {
		boolean success = false;

		switch(memberDTO.getDenied()) {
		
		case "Y" : 
			success = mdao.memberDenied(memberDTO);
			if(success) {
				AuthorityDTO authority = new AuthorityDTO();
				authority.setId(memberDTO.getId());
				authority.setRoleId("ROLE_DENIED");
				authoritySvc.insert(authority);
			}
			break;
		case "N" :
			
			success = mdao.memberDenied(memberDTO);
			if(success) {
				authoritySvc.delete(memberDTO.getId(), "ROLE_DENIED");
				
			}
			break;
		}
		return success;
	}
	
	
	// 회원 정보 수정
	@Override
	public boolean modify(MemberDTO memberDTO) {
		boolean success = mdao.modify(memberDTO);
		return success;
	}
	
	// 회원 탈퇴
	@Override
	public boolean delete(String id, String pw) {
		boolean success = false;

		MemberDTO mdto = mdao.getMember(id);
		success = authoritySvc.deleteMem(id);
		if(success) {
			mdao.delete(id,pw);
		}
		return success;
	}
	
	// 회원 조회
	@Override
	public MemberDTO getMember(String id) {
		MemberDTO memberDTO = mdao.getMember(id);
		return memberDTO;
	}
	
	// 회원 목록 조회
	@Override
	public List<MemberDTO> getMemberList() {
		List<MemberDTO> list = mdao.getMemberList();
		return list;
	}
	

	@Override
	public void list(HttpServletRequest request, Model model) throws Exception {
		int NUM_PER_PAGE = 10;			// 한페이지에 보여줄 레코드수
		int PAGE_NUM_PER_PAGE = 10;	// 한페이지에 보여줄 페이지수
		int reqPage = 0;						// 요청페이지
		int totalRec = 0;						// 전체레코드수
			
		String searchType = null;		// 검색타입
		String keyword = null;			// 검색어
		
		PageCriteria pc = null;			
		RecordCriteria rc = null;		// 검색조건이 없는 경우의 레코드 시작,종료페이지
		FindCriteria fc = null;			// 검색조건이 있는 경우의 레코드 시작,종료페이지
			
		List<MemberDTO> alist = null;
		// 요청페이지가 없는경우 1페이지로 이동
		if(request.getParameter("reqPage")==null || request.getParameter("reqPage")=="") {
			reqPage = 1;
		}else {
			reqPage = Integer.parseInt(request.getParameter("reqPage"));
		}
		logger.info("reqPage:"+reqPage);
		// 검색 매개값 체크(searchType, keyword)
		searchType = request.getParameter("searchType");
		keyword = request.getParameter("keyword");
		
		logger.info("검색어타입"+searchType);
		logger.info("검색어"+keyword);
		
		if(keyword == null || keyword.trim().isEmpty()) {
			// 검색조건이 없는 경우
			
			rc = new RecordCriteria(reqPage,NUM_PER_PAGE);
			totalRec = mdao.totalRec();
			
			pc = new PageCriteria(rc,totalRec,PAGE_NUM_PER_PAGE);
			alist = mdao.list(rc.getStartRecord(), rc.getEndRecord());
			
			request.setAttribute("list", alist);
			request.setAttribute("pc", pc);
			
		}else {
			// 검색조건이 있는 경우
			
			rc = new FindCriteria(reqPage,NUM_PER_PAGE,searchType,keyword);

			// 검색목록 총레코드수 반환
			totalRec = mdao.searchTotalRec(
					((FindCriteria)rc).getSearchType(),
					((FindCriteria)rc).getKeyword()
					);
			
			pc = new PageCriteria(rc,totalRec,PAGE_NUM_PER_PAGE);
			
			alist = mdao.flist(
					rc.getStartRecord(), 
					rc.getEndRecord(),
					((FindCriteria)rc).getSearchType(),
					((FindCriteria)rc).getKeyword()
					);
	
		}		
			model.addAttribute("list", alist);
			model.addAttribute("pc", pc);
		
	}

	@Override
	public List<MemberDTO> list(int startRec, int endRec) throws Exception {
		List<MemberDTO> list = null;
		list = mdao.list(startRec, endRec);
		return list;
	}

	// 회원 검색 목록
	@Override
	public List<MemberDTO> flist(int startRecord, int endRecord, String searchType, String keyword) throws Exception {
		List<MemberDTO> list = null;
		list = mdao.flist(startRecord, endRecord, searchType, keyword);
		return list;
	}

	// 회원 총계
	@Override
	public int totalRec() throws Exception {
		int cnt = 0;
		cnt = mdao.totalRec();
		return cnt;
	}

	// 검색 총계
	@Override
	public int searchTotalRec(String searchType, String keyword) throws Exception {
		int totalRec = 0;
		totalRec = mdao.searchTotalRec(searchType, keyword);
		return totalRec;
	}

	// 조회
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		MemberDTO mdto = mdao.getMember(id);
		if(mdto == null) {
			throw new UsernameNotFoundException("Invalid username/password");
		}
		List<AuthorityDTO> authorities = authoritySvc.selectOne(mdto.getId());
		mdto.setAuthorities(authorities);
		log.info("msi_mdto" + mdto);
		log.info("msi_mdto" + mdto.getUsername());
		log.info("msi_mdto" + id);
		
		return mdto;
	}


}
