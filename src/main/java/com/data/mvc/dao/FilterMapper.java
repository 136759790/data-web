package com.data.mvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.data.mvc.model.Filter;
import com.data.mvc.model.FilterParam;

@Mapper
public interface FilterMapper {
	void insert(Filter filter);
	void delete(Integer id);
	void update(Filter filter);
	Filter selectOne(Integer id);
	List<Filter> selectList(FilterParam param);
	void deleteByElementId(Integer elementId);
	void insertRelation(@Param("filterId") Integer filterId, @Param("elementId")Integer elementId);
}
