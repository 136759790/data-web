package com.data.mvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.data.mvc.model.ConfigOut;
import com.data.mvc.model.ConfigOutParam;
import com.data.mvc.model.OutElement;
@Mapper
public interface OutMapper {
	void insert(ConfigOut out);
	void update(ConfigOut out);
	void delete(Integer id);
	ConfigOut selectOne(Integer id);
	ConfigOut selectOneByImid(Integer id);
	List<ConfigOut> selectList(ConfigOutParam out);
	
	void insertEle(OutElement ele);
	void deleteEle(Integer id);
	List<OutElement> selectListEle(Integer id);
	
}
