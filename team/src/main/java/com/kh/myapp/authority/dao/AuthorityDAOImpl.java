package com.kh.myapp.authority.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.myapp.authority.dto.AuthorityDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AuthorityDAOImpl implements AuthorityDAO {
	
	@Inject
	SqlSession sqlSession;
	
	// 사용자 권한 등록
	@Override
	public boolean insert(AuthorityDTO authorityDTO) {
		boolean success = false;
		int cnt = sqlSession.insert("mappers.authority.insert",authorityDTO);
		if(cnt > 0) {success = true;}
		return success;
	}

	// 사용자 권한 수정
	@Override
	public boolean update(AuthorityDTO authorityDTO) {
		boolean success = false;
		int cnt = sqlSession.update("mappers.authority.update",authorityDTO);
		if(cnt > 0) {success = true;}
		return success;
	}
	
	// 사용자 권한 삭제
	@Override
	public boolean delete(String id, String roleId) {
		boolean success = false;
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("roleId", roleId);
		int cnt = sqlSession.delete("mappers.authority.delete",map);
		if(cnt > 0) {success = true;}
		return success;
	}
	
	// 사용자 권한 조회
	@Override
	public List<AuthorityDTO> selectOne(String id) {
		List<AuthorityDTO> list = null;
		list = sqlSession.selectList("mappers.authority.selectOne",id);
		return list;
	}
	
	// 사용자 전체 권한 조회
	@Override
	public List<AuthorityDTO> list() {
		return sqlSession.selectList("mappers.authority.list");
	}

	@Override
	public boolean deleteMem(String id) {
		boolean success = false;
		int cnt = sqlSession.delete("mappers.authority.deleteMem",id);
		if(cnt > 0) {success = true;}
		return success;
	}

}
