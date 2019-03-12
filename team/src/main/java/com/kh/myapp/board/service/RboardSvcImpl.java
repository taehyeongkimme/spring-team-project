package com.kh.myapp.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kh.myapp.board.dao.RboardDAO;
import com.kh.myapp.board.dto.RboardDTO;

@Service
public class RboardSvcImpl implements RboardSvc {

	@Inject
	@Qualifier("RboardDAOImplXML")
	RboardDAO rbdao;
	
	@Override
	public int write(RboardDTO rbDTO) throws Exception {
		int cnt = 0;
		cnt = rbdao.write(rbDTO);
		return cnt;
	}

	@Override
	public List<RboardDTO> list(String bnum) throws Exception {
		List<RboardDTO> list = null;
		list = rbdao.list(bnum);
		return list;
	}

	@Override
	public List<RboardDTO> list(String bnum, int startRec, int endRec) throws Exception {
		List<RboardDTO> list = null;
		list = rbdao.list(bnum, startRec, endRec);
		return list;
	}

	@Override
	public int modify(RboardDTO rbDTO) throws Exception {
		int cnt = 0;
		cnt = rbdao.modify(rbDTO);
		return cnt;
	}

	@Override
	public int delete(String rnum) throws Exception {
		int cnt = 0;
		cnt = rbdao.delete(rnum);
		return cnt;
	}

	@Override
	public int reply(RboardDTO rbDTO) throws Exception {
		int cnt = 0;
		cnt = rbdao.reply(rbDTO);
		return cnt;
	}
	

	@Override
	public int replyTotalRec(String bnum) throws Exception {
		int cnt = 0;
		cnt = rbdao.replyTotalRec(bnum);
		return cnt;
	}


}
