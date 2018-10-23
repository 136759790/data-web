package com.data.mvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.data.mvc.model.ConfigIn;
import com.data.mvc.model.ConfigInParam;
import com.data.mvc.model.InElement;
@Mapper
public interface InMapper {
	void insert(ConfigIn in);
	void insertEle(InElement e);
	void update(ConfigIn in);
	void delete(Integer id);
	void deleteEle(Integer id);
	ConfigIn selectOne(Integer id);
	List<ConfigIn> selectList(ConfigInParam param);
	List<InElement> selectListEle(Integer inid);
	
	
	ConfigIn selectOneByImid(Integer id);
}
