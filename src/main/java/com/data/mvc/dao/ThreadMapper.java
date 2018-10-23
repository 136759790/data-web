package com.data.mvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.data.mvc.model.ThreadModel;
import com.data.mvc.model.ThreadModelParam;

@Mapper
public interface ThreadMapper {
	void insert(ThreadModel th);
	void update(ThreadModel th);
	void delete(Integer id);
	ThreadModel selectOne(Integer id);
	ThreadModel selectOneInByImid(Integer id);
	ThreadModel selectOneOutByImid(Integer id);
	List<ThreadModel> selectList(ThreadModelParam th);
}
