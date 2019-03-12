package com.kh.myapp.product.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kh.myapp.product.dao.RproductDAO;
import com.kh.myapp.product.dto.RproductDTO;

@Service
public class RproductSvcImpl implements RproductSvc {

	@Inject
	@Qualifier("RproductDAOImplXML")
	RproductDAO rpdao;
	
	@Override
	public int write(RproductDTO rpDTO) throws Exception {
		int cnt = 0;
		cnt = rpdao.write(rpDTO);
		return cnt;
	}

	@Override
	public List<RproductDTO> list(String pnum) throws Exception {
		List<RproductDTO> list = null;
		list = rpdao.list(pnum);
		return list;
	}

	@Override
	public List<RproductDTO> list(String pnum, int startRec, int endRec) throws Exception {
		List<RproductDTO> list = null;
		list = rpdao.list(pnum, startRec, endRec);
		return list;
	}

	@Override
	public int modify(RproductDTO rpDTO) throws Exception {
		int cnt =0;
		cnt = rpdao.modify(rpDTO);
		return cnt;
	}

	@Override
	public int delete(String rnum) throws Exception {
		int cnt =0;
		cnt = rpdao.delete(rnum);
		return cnt;
	}

	@Override
	public int replyTotalRec(String pnum) throws Exception {
		int cnt =0;
		cnt = rpdao.replyTotalRec(pnum);
		return cnt;
	}

}
