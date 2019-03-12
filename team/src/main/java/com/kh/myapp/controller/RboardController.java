package com.kh.myapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kh.myapp.board.dto.RboardDTO;
import com.kh.myapp.board.service.RboardSvc;
import com.kh.myapp.util.PageCriteria;
import com.kh.myapp.util.RecordCriteria;

@RestController
@RequestMapping("/rboard")
public class RboardController {
	
	private static Logger logger = LoggerFactory.getLogger(RboardController.class);
	
	@Inject
	RboardSvc rbSvc;
	
	//댓글 등록
	@RequestMapping(value="/posts", method=RequestMethod.POST)
	public ResponseEntity<String> write(@RequestBody RboardDTO rdto) {
		   ResponseEntity<String> resCode = null;
		   if(rdto == null) {
			   resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
			   return resCode;
		   }
		   	try {
				rbSvc.write(rdto);
				resCode = new ResponseEntity<String>("success",HttpStatus.OK);
			} catch (Exception e) {
				resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
				e.printStackTrace();
			}
		   	return resCode;
		
		}
	
	
	//댓글 수정
	@RequestMapping(value="/posts", method=RequestMethod.PUT)
	public ResponseEntity<String> modify(@RequestBody RboardDTO rdto) {
		   ResponseEntity<String> resCode = null;
			if(rdto == null) {
				resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
				return resCode;
			}
			
			  	try {
					rbSvc.modify(rdto);
					resCode = new ResponseEntity<String>("success",HttpStatus.OK);
				} catch (Exception e) {
					resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
					e.printStackTrace();
				}
		
			return resCode;
		
	}
	
	
	//댓글 삭제
	@RequestMapping(value="/posts/{rnum}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("rnum") String rnum) {
			ResponseEntity<String> resCode = null;
			if(rnum == null || rnum.length() == 0) {
				resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
				return resCode;
			}	
				try {
					rbSvc.delete(rnum);
					resCode = new ResponseEntity<String>("success",HttpStatus.OK);
				} catch (Exception e) {
					resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
					e.printStackTrace();
				}
				
		
		
		return resCode;
		
		
	}
	
	
	//대댓글 등록
	@RequestMapping(value="/rposts", method=RequestMethod.POST)
	public ResponseEntity<String> reply(@RequestBody RboardDTO rdto) {
		   ResponseEntity<String> resCode = null;
		   if(rdto == null) {
			   resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
			   return resCode;
		   }
		   	try {
				rbSvc.reply(rdto);
				resCode = new ResponseEntity<String>("success",HttpStatus.OK);
			} catch (Exception e) {
				resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
				e.printStackTrace();
			}
		   	return resCode;
		
		}
	
	
	//댓글 목록 : Map반환
	@RequestMapping(value="/posts/map/{bnum}/{rereqPage}", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<Map<String,Object>> map(
			@PathVariable("bnum") String bnum,
			@PathVariable("rereqPage") Integer rereqPage){
				
		ResponseEntity<Map<String,Object>> responseEntity = null;
		Map<String,Object> map = new HashMap<>();
		RecordCriteria rc = new RecordCriteria(rereqPage, 10);
		
		try {
			// 페이지 처리 //
			PageCriteria pc =
					new PageCriteria(rc, rbSvc.replyTotalRec(bnum), 10);
			// ------- //
			map.put("item", rbSvc.list(bnum, rc.getStartRecord(), rc.getEndRecord()));
			map.put("pagecriteria", pc);
			logger.info(map.toString());
			responseEntity = new ResponseEntity<>(map,HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		
		
		
		return responseEntity;
		
		
		
	}
	
	
	
	
	
	
	
}
