package com.data.mvc.service;

import java.util.List;

import com.data.mvc.model.ThreadModel;
import com.data.mvc.model.ThreadModelParam;
import com.zxt.base.result.PageInfo;


public interface IThreadService {
	void insert(ThreadModel ThreadModel);
	void update(ThreadModel ThreadModel);
	void delete(Integer id);
	ThreadModel selectOne(Integer id);
	List<ThreadModel> selectList(ThreadModelParam param);
	PageInfo<ThreadModel> selectPage(ThreadModelParam param);
}
