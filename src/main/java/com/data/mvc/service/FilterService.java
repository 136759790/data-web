package com.data.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.mvc.dao.FilterMapper;
import com.data.mvc.model.Filter;
import com.data.mvc.model.FilterParam;
import com.zxt.base.result.PageInfo;
import com.zxt.base.utils.UtilPage;

@Service
public class FilterService implements IFilterService{
	
	@Autowired
	FilterMapper mapper;
	
	@Override
	public void insert(Filter filter) {
		mapper.insert(filter);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(Filter filter) {
		mapper.update(filter);
	}

	@Override
	public Filter selectOne(Integer id) {
		return mapper.selectOne(id);
	}

	@Override
	public List<Filter> selectList(FilterParam param) {
		return mapper.selectList(param);
	}

	@Override
	public PageInfo<Filter> selectPage(FilterParam param) {
		UtilPage.startPage(param);
		return new PageInfo<Filter>(mapper.selectList(param));
	}

	@Override
	public void deleteByElementId(Integer elementId) {
		mapper.deleteByElementId(elementId);
		
	}

	@Override
	@Transactional
	public void relation(Integer elementId, Integer[] filterIds) {
		mapper.deleteByElementId(elementId);
		if(filterIds != null){
			for (Integer id : filterIds) {
				mapper.insertRelation(id, elementId);
			}
		}
	}

}
