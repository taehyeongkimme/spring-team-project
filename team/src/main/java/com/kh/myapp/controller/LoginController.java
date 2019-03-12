package com.kh.myapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.myapp.login.LoginCmd;
import com.kh.myapp.login.LoginSvc;
import com.kh.myapp.member.dto.MemberDTO;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Inject
	LoginSvc loginSvc;
	
	
//	@RequestMapping("/loginForm")
//	public void loginForm(@RequestParam(value = "error", required = false) String error, 
//			@RequestParam(value = "logout", required = false) String logout, Model model) {
//		model.addAttribute("login", new LoginCmd());
//		
//		logger.info("error : "+error);
//		logger.info("logout : "+logout);
//		
//		if(error != null) {
//			model.addAttribute("error", "로그인 실패");
//		}
//		if(logout != null) {
//			model.addAttribute("logout", "로그아웃 성공");
//		}
//	}
	
	
	// 로긴 페이지로 이동
	@RequestMapping("/loginForm")
	public void loginForm(Model model) {
		logger.info("/loginForm 호출");
		model.addAttribute("login", new LoginCmd());
	}

	// 자체 아이디 로그인 처리
//	@RequestMapping(value="/loginOk", method=RequestMethod.POST, produces = "application/json")
//	@ResponseBody
//	public Map<String, Object> loginOk(@ModelAttribute ("login") LoginCmd login, BindingResult result, Model model, HttpSession session) {
//		logger.info("/loginOk 호출");
//		
//		MemberDTO mdto = null;
//
//		if(result.hasErrors()) {
//			logger.info(result.toString());
//
//			HashMap<String, Object> map = new HashMap<String, Object>();
//		    map.put("success", "false");
//		    map.put("msg", "확인되지 않은 오류.");
//		    return map;
//		}
//		
//		// 1) 회원 정보 존재 유무
//		if(loginSvc.isExist(login.getId())) {
//			
//			// 1-1) 정상회원 체크
//			if(loginSvc.isMember(login.getId(), login.getPw())) {
//				mdto = loginSvc.login(login.getId(), login.getPw());
//				session.setAttribute("user", mdto);
//				logger.info("로그인성공:"+login.getId());
//				
//
//
//				HashMap<String, Object> map = new HashMap<String, Object>();
//			    map.put("success", "true");
////			    map.put("msg", "로그인 성공");
//			    map.put("redirect", "/");
//			    return map;
//			}else {
//
//
//				HashMap<String, Object> map = new HashMap<String, Object>();
//			    map.put("success", "false");
//			    map.put("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
//			    return map;
//			}
//			
//		}else {
//			model.addAttribute("loginFailed", "등록되지 않은 아이디입니다. 회원가입 후 이용해주세요.");
//			logger.info("회원정보가 없음!!");
//			
//
//
//			HashMap<String, Object> map = new HashMap<String, Object>();
//		    map.put("success", "false");
//		    map.put("msg", "등록되지 않은 아이디입니다. 회원가입 후 이용해주세요.");
//		    return map;
//		}
//	}
	
	// 로그아웃
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/login/loginForm";
	}
	
	// 아이디 찾기 페이지
	@RequestMapping("/findIdForm")
	public String findIdForm(Model model) {
		logger.info("String findIdForm 호출됨!");
		model.addAttribute("findID", new MemberDTO());
		return "/login/findIdForm";
	}
	
	// 아이디 찾기 처리
	@RequestMapping(value="/findId",method=RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> findId(@ModelAttribute("findID") LoginCmd login, BindingResult result, Model model) {
		logger.info("String findId 호출됨!");
		MemberDTO mdto = null;
		
		if(result.hasErrors()) {
			logger.info("result"+result.toString());
			
			HashMap<String, Object> map = new HashMap<String, Object>();
		    map.put("success", "false");
		    map.put("msg", "확인되지 않은 오류");
		    return map;
		}
		
		logger.info(login.getPhone());
		// 아이디 찾기 실패했을떄
		mdto = loginSvc.findId(login.getName(), login.getPhone());
		if(mdto == null) {
			logger.info("아이디 찾기 실패");
			model.addAttribute("findFailed", "찾으시는 회원정보가 없습니다.");
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("success", "false");
		    map.put("msg", "찾으시는 회원정보가 없습니다.");
		    return map;
		}else {
			
			// 아이디 찾기 성공했을때
			model.addAttribute("mdto", mdto);
			logger.info("mdto:"+mdto);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("success", "true");
		    map.put("msg", "아이디는 " + mdto.getId() + "입니다.");
		    return map;
			
		}
	}
	
//	@RequestMapping(value="/findId",method=RequestMethod.POST)
//	public ResponseEntity<String> findId(@RequestParam("name") String name, @RequestParam("phone") String phone ){
//		ResponseEntity<String> resCode = null;
//		logger.info("이름"+name+" 전화번호"+phone);
//		
//		if((name.trim().length()==0 || name ==  null) || (phone.trim().length()==0  || phone == null)) {
//			resCode = new ResponseEntity<String>("error",HttpStatus.BAD_REQUEST);
//			return resCode;
//		}
//		
//		MemberDTO mdto = loginSvc.findId(name, phone);
//		if( mdto == null) {
//			resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
//		}
//		resCode = new ResponseEntity<String>(mdto.getId(),HttpStatus.OK);
//		logger.info("회원 아이디:"+mdto.getId());
//		return resCode;
//	}
	
//	@RequestMapping(value="/findId",method=RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<String> findId(@RequestBody MemberDTO mdto ,BindingResult result){
//		ResponseEntity<String> resCode = null;
//		
//		HttpHeaders responseHeaders = new HttpHeaders();
//		responseHeaders.add("Content-Type", "text/html; charset = UTF-8");
//		logger.info("이름"+mdto);
//		
//		if((mdto.getName().trim().length()==0 || mdto.getName() ==  null) || (mdto.getPhone().trim().length()==0  || mdto.getPhone() == null)) {
//			resCode = new ResponseEntity<String>("입력값이 없습니다.",responseHeaders,HttpStatus.BAD_REQUEST);
//			return resCode;
//		}
//		
//	if(result.hasErrors()) {
//		resCode = new ResponseEntity<String>("입력값오류",responseHeaders,HttpStatus.BAD_REQUEST);
//		return resCode;
//  }
//		
//		MemberDTO mdto2 = loginSvc.findId(mdto.getName(), mdto.getPhone());
//		if( mdto2 == null) {
//			resCode = new ResponseEntity<String>("회원정보가 없습니다.",responseHeaders,HttpStatus.BAD_REQUEST);
//		}else {
//			resCode = new ResponseEntity<String>(mdto2.getId(),responseHeaders,HttpStatus.OK);
//		}
//		//logger.info("회원 아이디:"+mdto2.getId());
//		return resCode;
//	}	
	
	
	// 비밀번호 찾기 페이지
	@RequestMapping("/findPwForm")
	public String findPwForm(Model model) {
		logger.info("String findPwForm 호출됨!");
		model.addAttribute("findPW", new MemberDTO());
		return "/login/findPwForm";
	}
	
	// 비밀번호 찾기 처리
	@RequestMapping(value="/findPw",method=RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> findPw(@ModelAttribute("findPW") LoginCmd login, BindingResult result, Model model) {
		logger.info("String findPw 호출됨!");
		MemberDTO mdto = null;
		
		if(result.hasErrors()) {
			logger.info(result.toString());

			HashMap<String, Object> map = new HashMap<String, Object>();
		    map.put("success", "false");
		    map.put("msg", "확인되지 않은 오류");
		    return map;
		}
		
		mdto = loginSvc.findPw(login.getId(), login.getPhone(), login.getBirth());
		// 비밀번호 찾기 실패시
		if(mdto == null) {
			logger.info("비밀번호 찾기 실패");
			model.addAttribute("findFailed", "입력한 정보가 일치하지 않습니다.");

			HashMap<String, Object> map = new HashMap<String, Object>();
		    map.put("success", "false");
		    map.put("msg", "입력한 정보가 일치하지 않습니다.");
		    return map;
		}else {
			
			// 비밀번호 찾기 성공시
			model.addAttribute("mdto", mdto);
			logger.info("찾은 비밀번호"+mdto.getPw());

			HashMap<String, Object> map = new HashMap<String, Object>();
		    map.put("success", "true");
		    map.put("msg", "찾은 비밀번호"+mdto.getPw());
		    return map;
		}
		
	}
	
	
	
}


