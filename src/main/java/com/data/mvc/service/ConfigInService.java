package com.data.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.mvc.dao.InMapper;
import com.data.mvc.model.ConfigIn;
import com.data.mvc.model.ConfigInParam;
import com.data.mvc.model.InElement;
import com.zxt.base.result.PageInfo;
import com.zxt.base.utils.UtilPage;


@Service
public class ConfigInService implements IConfigInService{
	@Autowired
	InMapper inMapper;
	@Override
	public void insert(ConfigIn in) {
		inMapper.insert(in);
	}

	@Override
	public void update(ConfigIn in) {
		inMapper.update(in);
	}

	@Override
	public void delete(Integer id) {
		inMapper.delete(id);
	}

	@Override
	public ConfigIn selectOne(Integer id) {
		return inMapper.selectOne(id);
	}

	@Override
	public List<ConfigIn> selectList(ConfigInParam param) {
		return inMapper.selectList(param);
	}

	@Override
	public PageInfo<ConfigIn> selectPage(ConfigInParam param) {
		UtilPage.startPage(param);
		return new PageInfo<ConfigIn>(inMapper.selectList(param));
	}

	@Override
	@Transactional
	public void insert(ConfigIn in, List<InElement> elements) {
		inMapper.insert(in);
		Integer inId = in.getId();
		for (InElement e : elements) {
			e.setInid(inId);
			inMapper.insertEle(e);
		}
		
	}

	@Override
	public void insert(InElement ele) {
		inMapper.insertEle(ele);
	}

	@Override
	public List<InElement> selectListEle(Integer inid) {
		return inMapper.selectListEle(inid);
	}

	@Override
	public void deleteEle(Integer id) {
		inMapper.deleteEle(id);
	}

}
