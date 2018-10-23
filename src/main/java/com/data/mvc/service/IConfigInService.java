package com.data.mvc.service;

import java.util.List;

import com.data.mvc.model.ConfigIn;
import com.data.mvc.model.ConfigInParam;
import com.data.mvc.model.InElement;
import com.zxt.base.result.PageInfo;

public interface IConfigInService {
	void insert(ConfigIn in);
	void insert(InElement ele);
	void insert(ConfigIn in,List<InElement> elements);
	void update(ConfigIn in);
	void delete(Integer id);
	void deleteEle(Integer id);
	ConfigIn selectOne(Integer id);
	List<ConfigIn> selectList(ConfigInParam param);
	List<InElement> selectListEle(Integer inid);
	PageInfo<ConfigIn> selectPage(ConfigInParam param);
}
