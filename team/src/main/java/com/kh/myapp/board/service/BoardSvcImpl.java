package com.kh.myapp.board.service;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kh.myapp.board.dao.BoardDAO;
import com.kh.myapp.board.dto.BoardDTO;
import com.kh.myapp.member.dto.MemberDTO;
import com.kh.myapp.product.dto.filesDTO;
import com.kh.myapp.util.FindCriteria;
import com.kh.myapp.util.PageCriteria;
import com.kh.myapp.util.RecordCriteria;

@Service
public class BoardSvcImpl implements BoardSvc {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardSvcImpl.class);


	@Inject
	BoardDAO bdao;
	
	// 글작성
	@Override
	public int write(BoardDTO boardDTO) {
		return bdao.write(boardDTO);
	}

	// 글수정
	@Override
	public boolean modify(BoardDTO boardDTO) {
		return bdao.modify(boardDTO);
	}

	// 글삭제
	@Override
	public boolean delete(String bnum) {
		return bdao.delete(bnum);
	}

	@Override
	public boolean insertInsertedFile(filesDTO filesdto) {
		return bdao.insertInsertedFile(filesdto);
	}

	// 글보기
	@Override
	public BoardDTO view(String bnum) {
		return bdao.view(bnum);
	}

	// 게시글 목록(기본)
	@Override
	public List<BoardDTO> list() {
		return bdao.list();
	}

	// 게시글 목록(페이징)
	@Override
	public List<BoardDTO> list(int startRec, int endRec, String best) {
		return bdao.list(startRec, endRec, best);
	}

	// 게시글 총계
	@Override
	public int totalRec() {
		return bdao.totalRec();
	}

	// 검색 목록
	@Override
	public List<BoardDTO> searchList(int startRecord, int endRecord, String searchType, String keyword) {
		return bdao.searchList(startRecord, endRecord, searchType, keyword);
	}
	
	// 글목록 페이징, 검색
	@Override
	public void list(HttpServletRequest request, Model model) {
		int NUM_PER_PAGE = 9;			// 한페이지에 보여줄 레코드수
		int PAGE_NUM_PER_PAGE = 10;	// 한페이지에 보여줄 페이지수
		int reqPage = 0;						// 요청페이지
		int totalRec = 0;	// 전체레코드수
		int bestCount = 0;
		
		
		String searchType = null;		// 검색타입
		String keyword = null;			// 검색어
		
		PageCriteria pc = null;			
		RecordCriteria rc = null;		// 검색조건이 없는 경우의 레코드 시작,종료페이지
		FindCriteria fc = null;			// 검색조건이 있는 경우의 레코드 시작,종료페이지
			
		List<BoardDTO> alist = null;
//		String id = ((MemberDTO)request.getSession().getAttribute("user")).getId();
		// 요청페이지가 없는경우 1페이지로 이동
		if (request.getParameter("reqPage")==null || request.getParameter("reqPage")=="") {
			reqPage = 1;
		} else {
			reqPage = Integer.parseInt(request.getParameter("reqPage"));
		}
		
		logger.info("reqPage:"+reqPage);
		// 검색 매개값 체크(searchType, keyword)
		searchType = request.getParameter("searchType");
		keyword = request.getParameter("keyword");
		
		logger.info("검색어타입"+searchType);
		logger.info("검색어"+keyword);
		
		if ((keyword == null || keyword.trim().isEmpty()) && request.getParameter("best")==null) {
			// 검색조건이 없는 경우
			
			rc = new RecordCriteria(reqPage,NUM_PER_PAGE);
			totalRec = bdao.totalRec();
			
			pc = new PageCriteria(rc,totalRec,PAGE_NUM_PER_PAGE);
			alist = bdao.list(rc.getStartRecord(), rc.getEndRecord(), "");
			
			request.setAttribute("list", alist);
			request.setAttribute("pc", pc);
		} else if (request.getParameter("best") != null) {

			logger.info("검wer색어타입"+searchType);

			rc = new RecordCriteria(reqPage,NUM_PER_PAGE);
			totalRec = bdao.totalRec();
			
			pc = new PageCriteria(rc,totalRec,PAGE_NUM_PER_PAGE);
			alist = bdao.list(rc.getStartRecord(), rc.getEndRecord(), request.getParameter("best"));
			
			request.setAttribute("list", alist);
			request.setAttribute("pc", pc);
			
		} else {
			// 검색조건이 있는 경우
			
			rc = new FindCriteria(reqPage,NUM_PER_PAGE,searchType,keyword);

			// 검색목록 총레코드수 반환
			totalRec = bdao.searchTotalRec(
					((FindCriteria)rc).getSearchType(),
					((FindCriteria)rc).getKeyword()
					);
			
			pc = new PageCriteria(rc,totalRec,PAGE_NUM_PER_PAGE);
			
			alist = bdao.searchList(
					rc.getStartRecord(), 
					rc.getEndRecord(),
					((FindCriteria)rc).getSearchType(),
					((FindCriteria)rc).getKeyword()
					);
	
		}
		
		model.addAttribute("list", alist);
		model.addAttribute("pc", pc);
	}

	// 검색 총계
	@Override
	public int searchTotalRec(String searchType, String keyword) {
		return bdao.searchTotalRec(searchType, keyword);
	}

	// 추천 비추천
	@Override
	public int goodOrBad(String bnum, String goodOrBad) {
		return bdao.goodOrBad(bnum, goodOrBad);
	}

	// 인기글
	@Override
	public List<BoardDTO> best() {
		return bdao.best();
	}

	// 조회수 로그
	@Override
	public void hitLog(String type, int num, String id) {
		bdao.hitLog(type, num, id);
	}

	// 추천 비추천 로그
	@Override
	public int goodOrBadLog(String type, String num, String id, String goodOrBad) {
		return bdao.goodOrBadLog(type, num, id, goodOrBad);
	}

	@Override
	public void getMyList(HttpServletRequest request, Model model) {
		int NUM_PER_PAGE = 10;			// 한페이지에 보여줄 레코드수
		int PAGE_NUM_PER_PAGE = 10;	// 한페이지에 보여줄 페이지수
		int reqPage = 0;						// 요청페이지
		int totalRec = 0;						// 전체레코드수
			
		String searchType = null;		// 검색타입
		String keyword = null;			// 검색어
		
		PageCriteria pc = null;			
		RecordCriteria rc = null;		// 검색조건이 없는 경우의 레코드 시작,종료페이지
		FindCriteria fc = null;			// 검색조건이 있는 경우의 레코드 시작,종료페이지
			
		List<BoardDTO> alist = null;
		String id = ((MemberDTO)request.getSession().getAttribute("user")).getId();
		// 요청페이지가 없는경우 1페이지로 이동
		if (request.getParameter("reqPage")==null || request.getParameter("reqPage")=="") {
			reqPage = 1;
		} else {
			reqPage = Integer.parseInt(request.getParameter("reqPage"));
		}
		
		logger.info("reqPage:"+reqPage);
		// 검색 매개값 체크(searchType, keyword)
		searchType = "I";
		keyword = id;
		
		logger.info("검색어타입"+searchType);
		logger.info("검색어"+keyword);
		
		if (keyword == null || keyword.trim().isEmpty()) {
			// 검색조건이 없는 경우
			
			rc = new RecordCriteria(reqPage,NUM_PER_PAGE);
			totalRec = bdao.totalRec();
			
			pc = new PageCriteria(rc,totalRec,PAGE_NUM_PER_PAGE);
			alist = bdao.list(rc.getStartRecord(), rc.getEndRecord(), "");
			
			request.setAttribute("list", alist);
			request.setAttribute("pc", pc);
			
		} else {
			// 검색조건이 있는 경우
			
			rc = new FindCriteria(reqPage,NUM_PER_PAGE,searchType,keyword);

			// 검색목록 총레코드수 반환
			totalRec = bdao.searchTotalRec(
					((FindCriteria)rc).getSearchType(),
					((FindCriteria)rc).getKeyword()
					);
			
			pc = new PageCriteria(rc,totalRec,PAGE_NUM_PER_PAGE);
			
			alist = bdao.searchList(
					rc.getStartRecord(), 
					rc.getEndRecord(),
					((FindCriteria)rc).getSearchType(),
					((FindCriteria)rc).getKeyword()
			);
	
		}	
		
		model.addAttribute("list", alist);
		model.addAttribute("pc", pc);
	}

	
	
}
