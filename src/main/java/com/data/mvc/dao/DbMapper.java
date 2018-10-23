package com.data.mvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.data.mvc.model.Db;
import com.data.mvc.model.DbParam;
@Mapper
public interface DbMapper {
	void insert(Db db);
	void update(Db db);
	void delete(Integer id);
	Db selectOne(Integer id);
	Db selectOneByImid(Integer id);
	List<Db> selectList(DbParam db);
}
