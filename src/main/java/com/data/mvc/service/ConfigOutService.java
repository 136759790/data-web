package com.data.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.mvc.dao.OutMapper;
import com.data.mvc.model.ConfigOut;
import com.data.mvc.model.ConfigOutParam;
import com.data.mvc.model.OutElement;
import com.zxt.base.result.PageInfo;
import com.zxt.base.utils.UtilPage;
@Service
public class ConfigOutService implements IConfigOutService{
	@Autowired
	OutMapper outMapper;
	@Override
	public void insert(ConfigOut out) {
		outMapper.insert(out);
	}

	@Override
	public void update(ConfigOut out) {
		outMapper.update(out);
	}

	@Override
	public void delete(Integer id) {
		outMapper.delete(id);
	}

	@Override
	public ConfigOut selectOne(Integer id) {
		return outMapper.selectOne(id);
	}

	@Override
	public List<ConfigOut> selectList(ConfigOutParam param) {
		return outMapper.selectList(param);
	}

	@Override
	public PageInfo<ConfigOut> selectPage(ConfigOutParam param) {
		UtilPage.startPage(param);
		return new PageInfo<ConfigOut>(outMapper.selectList(param));
	}

	@Override
	public void deleteEle(Integer id) {
		outMapper.deleteEle(id);
		
	}

	@Override
	public List<OutElement> selectListEle(Integer id) {
		return outMapper.selectListEle(id);
	}

	@Override
	public void insertEle(OutElement ele) {
		outMapper.insertEle(ele);
	}

}
