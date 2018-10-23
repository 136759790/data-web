package com.data.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.mvc.dao.DbMapper;
import com.data.mvc.model.Db;
import com.data.mvc.model.DbParam;
import com.zxt.base.result.PageInfo;
import com.zxt.base.utils.UtilPage;
@Service
public class DbService implements IDbService{
	@Autowired
	DbMapper mapper;
	
	@Override
	public void insert(Db db) {
		mapper.insert(db);
	}

	@Override
	public void update(Db db) {
		mapper.update(db);
	}

	@Override
	public Db selectOne(Integer id) {
		return mapper.selectOne(id);
	}

	@Override
	public List<Db> selectList(DbParam param) {
		return mapper.selectList(param);
	}

	@Override
	public PageInfo<Db> selectPage(DbParam param) {
		UtilPage.startPage(param);
		return new PageInfo<Db>(mapper.selectList(param));
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

}
