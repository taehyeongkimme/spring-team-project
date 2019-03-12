package com.kh.myapp.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kh.myapp.board.dto.RboardDTO;

@Repository(value="RboardDAOImplXML")
public class RboardDAOImplXML implements RboardDAO {

	private static Logger logger = LoggerFactory.getLogger(RboardDAOImplXML.class);

	@Inject
	SqlSession sqlsession;
	
	//댓글 등록
	@Override
	public int write(RboardDTO rboardDTO) throws Exception {
		return sqlsession.insert("mappers.rboard.write", rboardDTO);

	}

	//댓글 목록
	@Override
	public List<RboardDTO> list(String bnum) throws Exception {
		return sqlsession.selectList("mappers.rboard.listOld", bnum);
	}

	
	@Override
	public List<RboardDTO> list(String bnum, int startRec, int endRec) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("bnum", bnum);
		map.put("startRec", startRec);
		map.put("endRec", endRec);
		return sqlsession.selectList("mappers.rboard.list", map);
	}

	//댓글 수정
	@Override
	public int modify(RboardDTO rboardDTO) throws Exception {
		return sqlsession.update("mappers.rboard.modify", rboardDTO);
	}

	//댓글 삭제
	@Override
	public int delete(String rnum) throws Exception {
		int cnt = 0;
		//답글존재유무 판단
		if(isReply(rnum)) {
			//답글존재
			cnt = sqlsession.update("mappers.rboard.update_isdel", rnum);
		}else {
			//답글미존재
			cnt = sqlsession.delete("mappers.rboard.delete", rnum);
		}
		return cnt;
	}

	private boolean isReply(String rnum) {
		boolean isYN = false;
		int cnt = sqlsession.selectOne("mappers.rboard.isReply", rnum);
		if(cnt > 0) {
			isYN = true;
		}
		return isYN;
	}
	
	@Override
	public int reply(RboardDTO rboardDTO) throws Exception {
		int cnt1=0, cnt2=0;
		//댓글대상 정보 읽어오기
		logger.info("참조글:"+rboardDTO.getRrnum());
		RboardDTO originDTO = replyView(rboardDTO.getRrnum());

		//이전 답글 step 업데이트(원글그룹에 대한 세로정렬 재정의)
		cnt1 = updateStep(originDTO.getRgroup(), originDTO.getRstep());
		
		Map<String,Object> map = new HashMap<>();
		map.put("originDTO", originDTO);
		map.put("rboardDTO", rboardDTO);
		
		cnt2 = sqlsession.insert("mappers.rboard.reply", map);
		return cnt2;
	}
	
	//동일그룹의 댓글중에 동일스템의 글이 있으면 +1갱신
	private int updateStep(int rgroup, int rstep) {
		Map<String,Object> map = new HashMap<>();
		map.put("rgroup", rgroup);
		map.put("rstep", rstep);
		return sqlsession.update("mappers.rboard.updateStep", map);
	}
	
	// 댓글대상 읽어오기
	private RboardDTO replyView(int rrnum) {
		RboardDTO rboardDTO = null;
		rboardDTO = sqlsession.selectOne("mappers.rboard.replyView", rrnum);
		return rboardDTO;
	}

	//대댓글 총계
	@Override
	public int replyTotalRec(String bnum) throws Exception {
		int cnt = 0;
		cnt =  sqlsession.selectOne("mappers.rboard.replyTotalRec", bnum);
		return cnt;
	}


}
