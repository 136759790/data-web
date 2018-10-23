package com.data.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.mvc.dao.ThreadMapper;
import com.data.mvc.model.ThreadModel;
import com.data.mvc.model.ThreadModelParam;
import com.zxt.base.result.PageInfo;
import com.zxt.base.utils.UtilPage;
@Service
public class ThreadService implements IThreadService{
	@Autowired
	ThreadMapper mapper;
	
	@Override
	public void insert(ThreadModel ThreadModel) {
		mapper.insert(ThreadModel);
	}

	@Override
	public void update(ThreadModel ThreadModel) {
		mapper.update(ThreadModel);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public ThreadModel selectOne(Integer id) {
		return mapper.selectOne(id);
	}

	@Override
	public List<ThreadModel> selectList(ThreadModelParam param) {
		return mapper.selectList(param);
	}

	@Override
	public PageInfo<ThreadModel> selectPage(ThreadModelParam param) {
		UtilPage.startPage(param);
		return new PageInfo<ThreadModel>(mapper.selectList(param));
	}

}
