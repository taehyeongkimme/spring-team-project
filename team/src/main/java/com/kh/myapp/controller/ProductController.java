package com.kh.myapp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.myapp.board.dto.BoardDTO;
import com.kh.myapp.member.dto.MemberDTO;
import com.kh.myapp.product.dao.ProductDAO;
import com.kh.myapp.product.dto.ProductDTO;
import com.kh.myapp.product.dto.filesDTO;
import com.kh.myapp.util.Code;
import com.kh.myapp.util.storeData;

@Controller
@RequestMapping("/product")
public class ProductController {

	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	// 업로드 경로
	public static final String ROOT = "C:/upload-dir/";
	
	@Autowired
	@Qualifier("productDAOImplXML")
	private ProductDAO pdo;
	
	// 제품목록
	@RequestMapping(value = {"/list"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String List(Model model, HttpServletRequest request) throws Exception {
		logger.info("/list호출");

		storeData storedata = new storeData();
		
		model.addAttribute("product", storedata.getProductList());
		model.addAttribute("store", storedata.getStoreList());
		
		try {
			pdo.getProductList(model, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/product/list";
	}

	// 제품등록
	@RequestMapping(value = {"/writeForm"}, method = {RequestMethod.GET})
	public String Write(Model model, HttpServletRequest request, HttpSession session) throws Exception {

		storeData storedata = new storeData();
		
		model.addAttribute("product", storedata.getProductList());
		model.addAttribute("store", storedata.getStoreList());

		model.addAttribute("ProductDTO", new ProductDTO());
		return "/product/writeForm";
	}
	
	// 등록처리
	@RequestMapping(value = {"/writeOk"}, method = {RequestMethod.POST})
	public String List(HttpServletRequest request, @RequestParam("file") MultipartFile file, 
			@ModelAttribute("ProductDTO") ProductDTO productDTO, BindingResult result, HttpSession session) throws Exception {
		if(result.hasErrors()) {
			logger.info(result.toString());
			return "/product/writeForm";
		}
		
		MemberDTO mdto = MemberDTO.current();
		
		productDTO.setPid(mdto.getId());
		productDTO.setPnickname(mdto.getNickname());
		productDTO.setPname(mdto.getName());
		pdo.insertProduct(productDTO);
		int currentIdx = productDTO.getIdx();
		
		// 멀티파트 업로드 파일이 존재한다면
		if (!file.isEmpty()) {
			try {
				// 경로가 존재하지 않는다면 폴더 생성
				if (!Files.exists(Paths.get(ROOT))) {
					try {
						Files.createDirectories(Paths.get(ROOT));
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
				
				// 원본 파일 이름
				String source_filename = file.getOriginalFilename();
				
				// 이미지 파일만 허가
				if (source_filename.matches(".+(jpg|jpeg|png|gif)$")) {
					
					// 확장자
					String extension = FilenameUtils.getExtension(source_filename);

					ServletContext context = request.getSession().getServletContext();
				    String appPath = context.getRealPath("/resources/upload");
				    
				    logger.info(appPath);
		            ServletContext sc = session.getServletContext();
		            String x = sc.getRealPath("/");
		            logger.info(x);
				    
					// 파일 이름 생성
					UUID uuid = UUID.randomUUID();
					String filename = uuid.toString();
					File fs = new File(appPath + filename + "." + extension);
					while(fs.isFile()) {
						fs = new File(appPath + filename + "." + extension);
					}
					
					filesDTO filesdto = new filesDTO();
					filesdto.setType("product");
					filesdto.setTarget_srl(currentIdx);
					filesdto.setSourcename(filename + "." + extension);
					filesdto.setOriginname(source_filename);
					boolean success = pdo.insertInsertedFile(filesdto);
					logger.info("파일 입력 성공:"+success);
					logger.info(appPath);
					// 파일 업로드
					Files.copy(file.getInputStream(), Paths.get(appPath, filename + "." + extension));
				}
				
			} catch (IOException | RuntimeException e) {
				
			}
		}
		
		return "redirect:/product/list";
	}

	// 제품보기
	@RequestMapping(value = {"/view/{pnum}"}, method = {RequestMethod.GET})
	public String View(@PathVariable("pnum") String pnum, Model model, HttpServletRequest request, HttpSession session) {
		if (!pnum.isEmpty()) {
			
			ProductDTO productDTO = new ProductDTO();
			productDTO.setPnum(Integer.parseInt(pnum));
			model.addAttribute("productList", pdo.dispProduct(productDTO));
			
			
			ProductDTO docDTO = (ProductDTO) model.asMap().get("productList");
			if (!docDTO.getPgroup().isEmpty()) {
					model.addAttribute("relatedList", pdo.productList(docDTO));
			}
		
		}

		storeData storedata = new storeData();
		
		model.addAttribute("product", storedata.getProductList());
		model.addAttribute("store", storedata.getStoreList());
		
		model.addAttribute("ProductDTO", new ProductDTO());
		return "/product/read";
	}
	
	@RequestMapping(value = {"/delete"}, method = {RequestMethod.GET})
	public String Delete(HttpServletRequest request) {
		int pnum = Integer.parseInt(request.getParameter("pnum"));
		
		boolean isDeleted = pdo.deleteProduct(pnum);
		
		if (isDeleted) {
			return "redirect:/product/list";
		} else {
			return "redirect:/product/view/" + pnum;
		}
	}

	@RequestMapping(value = {"/modifyOk"}, method = {RequestMethod.POST})
	public String Modify(HttpServletRequest request, @ModelAttribute("ProductDTO") ProductDTO productDTO) {
		int pnum = Integer.parseInt(request.getParameter("pnum"));
		
		logger.info(pnum + "pnum");
		productDTO.setPnum(pnum);
		pdo.modifyProduct(productDTO);

		return "redirect:/product/list";
	}

	@RequestMapping(value = {"/comment/list"}, method = {RequestMethod.POST})
	public String getCommentList(HttpServletRequest request) {
		int pnum = Integer.parseInt(request.getParameter("pnum"));
		
		return "";
	}
	
}