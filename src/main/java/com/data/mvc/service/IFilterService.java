package com.data.mvc.service;

import java.util.List;

import com.data.mvc.model.Filter;
import com.data.mvc.model.FilterParam;
import com.zxt.base.result.PageInfo;

public interface IFilterService {
	void insert(Filter filter);
	void delete(Integer id);
	void deleteByElementId(Integer elementId);
	void relation(Integer elementId,Integer[] filterIds);
	void update(Filter filter);
	Filter selectOne(Integer id);
	List<Filter> selectList(FilterParam param);
	PageInfo<Filter> selectPage(FilterParam param);
}
