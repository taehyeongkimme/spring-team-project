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

import com.kh.myapp.product.dto.RproductDTO;
import com.kh.myapp.product.service.RproductSvc;
import com.kh.myapp.util.PageCriteria;
import com.kh.myapp.util.RecordCriteria;

@RestController
@RequestMapping("/rproduct")
public class RproductController {
	
	private static Logger logger = LoggerFactory.getLogger(RproductController.class);
	
	@Inject
	RproductSvc rpSvc;
	
	//댓글 등록
	@RequestMapping(value="/posts", method=RequestMethod.POST)
	public ResponseEntity<String> write(@RequestBody RproductDTO rpdto) {
		   ResponseEntity<String> resCode = null;
		   if(rpdto == null) {
			   resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
			   return resCode;
		   }
		   	try {
				rpSvc.write(rpdto);
				resCode = new ResponseEntity<String>("success",HttpStatus.OK);
			} catch (Exception e) {
				resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
				e.printStackTrace();
			}
		   	return resCode;
		
		}
	
	//댓글 수정
	@RequestMapping(value="/posts", method=RequestMethod.PUT)
	public ResponseEntity<String> modify(@RequestBody RproductDTO rpdto) {
		   ResponseEntity<String> resCode = null;
			if(rpdto == null) {
				resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
				return resCode;
			}
			
			  	try {
					rpSvc.modify(rpdto);
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
					rpSvc.delete(rnum);
					resCode = new ResponseEntity<String>("success",HttpStatus.OK);
				} catch (Exception e) {
					resCode = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
					e.printStackTrace();
				}
					
		return resCode;	
		
	}
	
	//댓글 목록 : map 반환
	@RequestMapping(value="/posts/map/{pnum}/{rereqPage}", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<Map<String,Object>> map(
			@PathVariable("pnum") String pnum,
			@PathVariable("rereqPage") Integer rereqPage){
				
		ResponseEntity<Map<String,Object>> responseEntity = null;
		Map<String,Object> map = new HashMap<>();
		RecordCriteria rc = new RecordCriteria(rereqPage, 10);
		
		try {
			// 페이지 처리 //
			PageCriteria pc =
					new PageCriteria(rc, rpSvc.replyTotalRec(pnum), 10);
			// ------- //
			map.put("item", rpSvc.list(pnum, rc.getStartRecord(), rc.getEndRecord()));
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
