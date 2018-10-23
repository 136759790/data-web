package com.data.mvc.service;

import java.util.List;

import com.data.mvc.model.Db;
import com.data.mvc.model.DbParam;
import com.zxt.base.result.PageInfo;

public interface IDbService {
	void insert(Db db);
	void update(Db db);
	void delete(Integer id);
	Db selectOne(Integer id);
	List<Db> selectList(DbParam param);
	PageInfo<Db> selectPage(DbParam param);
}
