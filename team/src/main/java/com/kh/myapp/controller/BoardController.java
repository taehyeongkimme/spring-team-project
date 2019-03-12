package com.kh.myapp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kh.myapp.board.dto.BoardDTO;
import com.kh.myapp.board.service.BoardSvc;
import com.kh.myapp.member.dto.MemberDTO;
import com.kh.myapp.product.dto.filesDTO;
import com.kh.myapp.util.RecordCriteria;

@Controller
@RequestMapping("/board")
public class BoardController {

	// 업로드 경로
	public static final String ROOT = "C:/upload-dir/";
	
	private static Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	BoardSvc boardSvc;
	
	// 글작성 페이지 이동
	@RequestMapping("/writeForm")
	public String writeForm(Model model) {
		logger.info("/board/writeForm()호출됨!");
		model.addAttribute("boardDTO", new BoardDTO());
		return "/board/writeForm";
	}
	
	// 글작성 처리
	@RequestMapping("/writeOk")
	public String writeOk(HttpServletRequest request, @RequestParam("file") MultipartFile file,
							@ModelAttribute("boardDTO") BoardDTO boardDTO, BindingResult result, Model model, HttpSession session) {
		logger.info("/board/writeOk 호출됨");
		
		if (result.hasErrors()) {
			logger.info(result.toString());
			return "/board/writeForm";
		}
		
		MemberDTO mdto = MemberDTO.current();
		
		boardDTO.setBid(mdto.getId());
		boardDTO.setBnickname(mdto.getNickname());
		//boolean success = boardSvc.write(boardDTO);
		//logger.info("게시글 작성 성공:"+success);
		boardSvc.write(boardDTO);

		int currentIdx = boardDTO.getIdx();
		logger.info("currentIdx" + currentIdx);

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
					filesdto.setType("board");
					filesdto.setTarget_srl(currentIdx);
					filesdto.setSourcename(filename + "." + extension);
					filesdto.setOriginname(source_filename);
					boolean success = boardSvc.insertInsertedFile(filesdto);
					logger.info("파일 입력 성공:"+success);
					logger.info(appPath);
					// 파일 업로드
					Files.copy(file.getInputStream(), Paths.get(appPath, filename + "." + extension));
				}
				
			} catch (IOException | RuntimeException e) {
				
			}
		}
		
		
		return "redirect:/board/list";
	}
	
	// 글수정 처리
	@RequestMapping("/modifyOk")
	public String modifyOk(@ModelAttribute BoardDTO boardDTO,
			BindingResult result) {
		logger.info("/board/modifyOk 호출됨!");
		
		if (result.hasErrors()) {
			return "/board/read";
		}
		boardSvc.modify(boardDTO);
		
		return "forward:/board/view";
	}
	
	// 글삭제 처리
	@RequestMapping("/delete")
	public String delete(@RequestParam("bnum") String bnum,
			@RequestParam("reqPage") String reqPage) {
		logger.info("/board/delete 호출됨!");
		boardSvc.delete(bnum);
		
		return "redirect:/board/list?reqPage="+reqPage;
	}
	
	// 글보기
	@RequestMapping("/view")
	public String view(@ModelAttribute("boardDTO") BoardDTO boardDTO, Model model, HttpServletRequest request) {
		logger.info("/board/view 호출됨!");
		
		RecordCriteria rc = null;
		
		rc = new RecordCriteria(Integer.parseInt(request.getParameter("reqPage")));
		boardDTO = boardSvc.view(request.getParameter("bnum"));
		
		model.addAttribute("boardDTO", boardDTO);
		model.addAttribute("rc", rc);
		return "/board/read";
	}
	
	// 추천 비추천
	@ResponseBody
	@RequestMapping(value = "/posts/{goodOrBad}/{bnum}", method = RequestMethod.PUT)
	public ResponseEntity<String> goodOrBad(@PathVariable("goodOrBad") String goodOrBad, @PathVariable("bnum") String bnum, HttpSession session){
		MemberDTO mdto = MemberDTO.current();

		// 로그에 데이터가 존재하는지 확인한다, 존재하면 return
		ResponseEntity<String> resCode = null;
		
		Boolean isNumberParaterExists = (bnum == null || bnum.length() == 0) ? true : false;
		
		if (isNumberParaterExists) {
			resCode = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
			return resCode;
		}
		
		int hasVoted = boardSvc.goodOrBadLog("b", bnum, mdto.getId().toString(), goodOrBad);
		if (hasVoted > 0) {
			resCode = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
			return resCode;
		}
		
		try {
			boardSvc.goodOrBad(bnum, goodOrBad);

			boardSvc.hitLog("b".toString(), Integer.parseInt(bnum), mdto.getId().toString());
			
			
			resCode = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			resCode = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		
		//추천에 성공하면 다시는 추천을 못하도록 로그에 데이터를 삽입한다.
		
		return resCode;
	}
	
	@RequestMapping(value = "/rereply", method = RequestMethod.GET)
	public String rereply() {
		return "/board/reReply";
	}
	
	
	// 글목록 출력 페이징
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		logger.info("/list호출");
		
		try {
			boardSvc.list(request, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/board/list";
	}
	
	
	
}
