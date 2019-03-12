package com.kh.myapp.product.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.kh.myapp.board.dao.BoardDAOImplXML;
import com.kh.myapp.product.dto.ProductDTO;
import com.kh.myapp.product.dto.filesDTO;
import com.kh.myapp.util.FindCriteria;
import com.kh.myapp.util.PageCriteria;
import com.kh.myapp.util.RecordCriteria;;

@Repository
public class ProductDAOImplXML implements ProductDAO {

	private static Logger logger = LoggerFactory.getLogger(ProductDAOImplXML.class);
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void getProductList(Model model, HttpServletRequest request) throws Exception {
		int NUM_PER_PAGE = 10;			// 한페이지에 보여줄 레코드수
		int PAGE_NUM_PER_PAGE = 10;	// 한페이지에 보여줄 페이지수
		int reqPage = 0;						// 요청페이지
		int totalRec = 0;						// 전체레코드수
			
		String searchType = null;		// 검색타입
		String keyword = null;			// 검색어

		String searchType2 = null;		// 검색타입
		String keyword2 = null;			// 검색어
		
		PageCriteria pc = null;			
		RecordCriteria rc = null;		// 검색조건이 없는 경우의 레코드 시작,종료페이지
		FindCriteria fc = null;			// 검색조건이 있는 경우의 레코드 시작,종료페이지
			
		List<ProductDTO> alist = null;
		// 요청페이지가 없는경우 1페이지로 이동
		if (request.getParameter("reqPage")==null || request.getParameter("reqPage")=="") {
			reqPage = 1;
		} else {
			reqPage = Integer.parseInt(request.getParameter("reqPage"));
		}
		
		// 검색 매개값 체크(searchType, keyword)
		searchType = request.getParameter("sold");
		keyword = request.getParameter("type");
		
		searchType2 = request.getParameter("searchType");
		keyword2 = request.getParameter("keyword");
		
		Boolean isFilterEmpty = (keyword == null || keyword.trim().isEmpty()) && (searchType == null || searchType.trim().isEmpty());
		Boolean isSearchEmpty = (keyword2 == null || keyword2.trim().isEmpty()) && (searchType2 == null || searchType2.trim().isEmpty());
		if (isFilterEmpty && isSearchEmpty) {
			// 검색조건이 없는 경우
			
			rc = new RecordCriteria(reqPage,NUM_PER_PAGE);
			totalRec = this.totalrec();
			
			pc = new PageCriteria(rc,totalRec,PAGE_NUM_PER_PAGE);
			alist = this.list(rc.getStartRecord(), rc.getEndRecord());
			
			request.setAttribute("list", alist);
			request.setAttribute("pc", pc);
			
		} else if (!isFilterEmpty && isSearchEmpty) {
			// 검색조건이 있는 경우
			rc = new RecordCriteria(reqPage, NUM_PER_PAGE);
	
			ProductDTO productdto = new ProductDTO();
			if (!searchType.isEmpty()) {
				productdto.setPstore(searchType);
			}
			
			if (!keyword.isEmpty()) {
				productdto.setPgroup(keyword);
			}
			
			productdto.setStartRecord(rc.getStartRecord());
			productdto.setEndRecord(rc.getEndRecord());
			
			logger.info(productdto.toString());
			
			// 검색목록 총레코드수 반환
			totalRec = this.selectProductByOptionCount(productdto);
			
			logger.info(totalRec + "니미");
			
			pc = new PageCriteria(rc,totalRec,PAGE_NUM_PER_PAGE);
			
			alist = this.selectProductByOption(productdto);
		} else if(!isSearchEmpty) {
			// 검색조건이 있는 경우
			rc = new RecordCriteria(reqPage, NUM_PER_PAGE);
	
			ProductDTO productdto = new ProductDTO();
			if (searchType2.equals("ptitle")) {
				//productdto.setPtitle(keyword2);
			}
			
			if (searchType2.equals("pcontent")) {
				//productdto.setPcontent(keyword2);
			}
			
			productdto.setPcontent(keyword2);
			
			productdto.setStartRecord(rc.getStartRecord());
			productdto.setEndRecord(rc.getEndRecord());
			
			logger.info(productdto.toString());
			
			// 검색목록 총레코드수 반환
			totalRec = this.selectProductByOptionCount(productdto);
			
			logger.info(totalRec + "니미sdfsdf");
			
			pc = new PageCriteria(rc,totalRec,PAGE_NUM_PER_PAGE);
			
			alist = this.selectProductByOption(productdto);
		}
		
		model.addAttribute("list", alist);
		model.addAttribute("pc", pc);
	}

	@Override
	public int insertProduct(ProductDTO productdto) {
		//int cnt = 0;
		return sqlSession.insert("productDAO.insertProduct", productdto);
		//if (cnt > 0) {
		//	return true;
		//}
		
		//return false;
	}

	@Override
	public boolean deleteProduct(int product_srl) {
		int cnt = 0;
		cnt = sqlSession.delete("productDAO.deleteProduct", product_srl);
		if (cnt > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean modifyProduct(ProductDTO productdto) {
		int cnt = 0;
		cnt = sqlSession.update("productDAO.modifyProduct", productdto);
		if (cnt > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public ProductDTO dispProduct(ProductDTO productdto) {
		ProductDTO pdto = sqlSession.selectOne("productDAO.dispProduct", productdto);
		
		return pdto;
	}

	@Override
	public List<ProductDTO> list(int startRec, int endRec) {
		Map<String,Object> map = new HashMap<>();
		map.put("startRec", startRec);
		map.put("endRec", endRec);
		
		return sqlSession.selectList("productDAO.getProductList", map);
	}

	@Override
	public int totalrec() {
		return sqlSession.selectOne("productDAO.totalRec");
	}

	@Override
	public boolean insertInsertedFile(filesDTO filesdto) {
		// TODO Auto-generated method stub
		int cnt = 0;
		cnt = sqlSession.insert("productDAO.insertInsertedFile", filesdto);
		
		if (cnt > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<ProductDTO> productList(ProductDTO productdto) {
		return sqlSession.selectList("productDAO.productdto", productdto);
	}

	@Override
	public List<ProductDTO> selectProductByOption(ProductDTO productdto) {
		return sqlSession.selectList("productDAO.selectProductByOption", productdto);
	}

	@Override
	public int selectProductByOptionCount(ProductDTO productdto) {
		return sqlSession.selectOne("productDAO.selectProductByOptionCount", productdto);
	}

}
