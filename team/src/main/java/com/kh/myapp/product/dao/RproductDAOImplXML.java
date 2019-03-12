package com.kh.myapp.product.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kh.myapp.board.dao.RboardDAOImplXML;
import com.kh.myapp.product.dto.RproductDTO;

@Repository(value="RproductDAOImplXML")
public class RproductDAOImplXML implements RproductDAO {

	private static Logger logger = LoggerFactory.getLogger(RboardDAOImplXML.class);

	@Inject
	SqlSession sqlsession;
	
	//댓글 등록	
	@Override
	public int write(RproductDTO rpDTO) throws Exception {
		return sqlsession.insert("mappers.rproduct.write", rpDTO);
	}
	
	//댓글 목록
	@Override
	public List<RproductDTO> list(String pnum) throws Exception {
		return sqlsession.selectList("mappers.rproduct.listOld", pnum);
	}

	@Override
	public List<RproductDTO> list(String pnum, int startRec, int endRec) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("pnum", pnum);
		map.put("startRec", startRec);
		map.put("endRec", endRec);
		return sqlsession.selectList("mappers.rproduct.list", map);
	}

	//댓글 수정	
	@Override
	public int modify(RproductDTO rpDTO) throws Exception {
		return sqlsession.update("mappers.rproduct.modify", rpDTO);
	}

	//댓글 삭제	
	@Override
	public int delete(String rnum) throws Exception {
		return sqlsession.delete("mappers.rproduct.delete", rnum);
	}

	//댓글 총계
	@Override
	public int replyTotalRec(String pnum) throws Exception {
		int cnt = 0;
		cnt =  sqlsession.selectOne("mappers.rproduct.replyTotalRec", pnum);
		return cnt;
	}

}
