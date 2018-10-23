package com.data.mvc.service;

import java.util.List;

import com.data.mvc.model.ConfigOut;
import com.data.mvc.model.ConfigOutParam;
import com.data.mvc.model.OutElement;
import com.zxt.base.result.PageInfo;

public interface IConfigOutService {
	void insert(ConfigOut out);
	void update(ConfigOut out);
	void delete(Integer id);
	ConfigOut selectOne(Integer id);
	List<ConfigOut> selectList(ConfigOutParam param);
	PageInfo<ConfigOut> selectPage(ConfigOutParam param);
	
	
	void deleteEle(Integer id);
	List<OutElement> selectListEle(Integer id);
	void insertEle(OutElement ele);
}
