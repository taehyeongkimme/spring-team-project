package com.kh.myapp.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.myapp.board.service.BoardSvc;
import com.kh.myapp.login.LoginCmd;
import com.kh.myapp.login.LoginSvc;
import com.kh.myapp.member.dto.MemberDTO;
import com.kh.myapp.member.service.MemberSvc;
import com.kh.myapp.util.Code;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private static Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberSvc memberSvc;
	@Inject
	BoardSvc boardSvc;
	@Inject
	private LoginSvc loginSvc;
	
	// 회원가입  양식으로 이동
	@RequestMapping("/joinForm")
	public String joinForm(Model model) {
		logger.info("/member/joinForm() 호출됨!");	
		model.addAttribute("join", new MemberDTO());
		
		return "/member/joinForm";
	}
	
	// 성별 저장
	@ModelAttribute
	public void initData(Model model) {
		List<Code> gender = new ArrayList<>();
		gender.add(new Code("M","남자"));
		gender.add(new Code("W","여자"));
		model.addAttribute("gender", gender);
		
		
		// 회원 활동 정지
		List<Code> denied = new ArrayList<>();
		denied.add(new Code("Y","활동정지"));
		denied.add(new Code("N","활동"));
		model.addAttribute("denied", denied);
		
	}
	
	// 아이디 중복 검사(ajax)
	@RequestMapping("/checkId")
	@ResponseBody
	public Map<Object,Object> checkId(@RequestBody String id){
		Map<Object,Object> map = new HashMap<>();
		
	// 아이디가 이미 사용중인 경우
		boolean success = loginSvc.isExist(id);
		map.put("success", success);
		return map;
	}
	

	// 회원가입 처리
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join (@Valid @ModelAttribute("join") MemberDTO mdto, 
			BindingResult result, Model model) {
		logger.info("join() 호출됨");
		logger.info("mdto:"+mdto);
		
		// 회원가입 중 오류가 발생한 경우
		if(result.hasErrors()) {
			logger.info("회원 가입 처리 중 오류!!");
			logger.info(result.toString());
			model.addAttribute("failed", "회원가입 실패");
			return "/member/joinForm";
		}
		
		// 회원가입 성공
		boolean success = memberSvc.insert(mdto);
		model.addAttribute("result", success);
		return "redirect:/login/loginForm";
	}
	
	// 회원 수정 페이지
	@RequestMapping(value="/memberModifyForm/{id:.+}")
	public String memberModifyForm(@PathVariable String id, Model model) {
		logger.info("/memberModifyForm");
		
		MemberDTO mdto = memberSvc.getMember(id);
		model.addAttribute("mdto", mdto);
		return "/member/memberModifyForm";
	}
	
	// 회원 수정 처리
	@RequestMapping(value="/memberModify", method=RequestMethod.POST)
	public String memberModify(
			@Valid @ModelAttribute("mdto") MemberDTO mdto, BindingResult result,
			Model model, HttpSession session) {
		logger.info("/memberModify호출"+mdto);
		boolean success = false;
		 
		if(result.hasErrors()) {
			model.addAttribute("failed", "제대로 입력해주세요.");
			return "/member/memberModifyForm";
		}
		success = memberSvc.modify(mdto);
		if(success) {
			session.removeAttribute("user");
			mdto = loginSvc.login(mdto.getId(), mdto.getPw());
			session.setAttribute("user", mdto);
		}
		
		logger.info("수정처리 결과:" + success);
		return "redirect:/";
	}
	
	// 회원 탈퇴 페이지
	@RequestMapping(value="/deleteForm/{id:.+}")
	public String deleteForm(@PathVariable String id, Model model) {
		logger.info("/deleteForm");
		
		MemberDTO mdto = memberSvc.getMember(id);
		mdto.setPw("");
		model.addAttribute("mdto", mdto);
		return "/member/deleteForm";
	}
	
	// 회원 탈퇴 처리
	@RequestMapping(value="/memberDelete", method=RequestMethod.POST)
	public String memberDelete(@RequestParam String id, @RequestParam String pw, 
			HttpSession session, Model model) {
		logger.info("/memberDelete");
		
		boolean success = false;
		
		logger.info("id:"+id+"/pw:"+pw);
	
		success = memberSvc.delete(id, pw);
		logger.info("삭제처리 결과:" + success);
		if(success) {
			return "redirect:/login/logout";
		}else {
			return "forward:/member/deleteForm/"+id;
		}
	}
	
//	// 회원 탈퇴 처리
//	@RequestMapping("/memberDelete/{id:.+}")
//	public String memberDelete(@PathVariable String id, @PathVariable String pw, 
//			HttpSession session, Model model) {
//		logger.info("/memberDelete/{id:.+}");
//		boolean success = false;
//		
//		logger.info(id+pw);
//		success = memberSvc.delete(id, pw);
//		logger.info("삭제처리 결과:" + success);
//		if(success) {
//			session.removeAttribute("user");
//		}
//		model.addAttribute("login", new LoginCmd());
//		return "/member/memberDelete";
//	}
	
	
	// 회원 목록 조회
	@RequestMapping("/memberList")
	public String list(HttpServletRequest request, Model model) {
		logger.info("/memberList호출");
		
		try {
			memberSvc.list(request, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/member/memberList";
	}
	
	// 회원 활동 정지(관리자,ajax)
	@RequestMapping(value="/memberDenied/{denied}/{id:.+}", method=RequestMethod.PUT)
	@ResponseBody
	public String memberDenied(@PathVariable("denied") String denied, @PathVariable("id") String id/*, @PathVariable String id*/) {
		
		try {
			id = URLDecoder.decode(id, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info(id);
		Map<Object,Object> map = new HashMap<>();
		MemberDTO mdto = memberSvc.getMember(id);
		mdto.setDenied(denied);
		boolean success = memberSvc.memberDenied(mdto);
		map.put("success", success);
		
		
		return "success";
		//return map;
	}
	
	// 내가 작성한 글보기
	@RequestMapping("/myList")
	public String myList(HttpServletRequest request, Model model) {
		logger.info("/member/myList 호출");
		try {
			boardSvc.getMyList(request, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/member/myList";
	}
	
	// 마이페이지 이동
	@RequestMapping(value="/myPage")
	public String myPage() {
		
		return "/member/myPage";
	}
	
}
