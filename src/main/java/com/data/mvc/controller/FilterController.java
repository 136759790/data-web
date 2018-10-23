package com.data.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.data.mvc.model.Filter;
import com.data.mvc.model.FilterParam;
import com.data.mvc.service.IFilterService;
import com.zxt.base.result.PageInfo;
import com.zxt.base.result.Result;
import com.zxt.base.utils.UtilResult;

@Controller
@RequestMapping("/filter/")
public class FilterController {

	@Autowired
	IFilterService filterService;
	
	@PostMapping("filter")
	@ResponseBody
	public Result addFilter(Filter filter){
		filterService.insert(filter);
		return UtilResult.success("新增成功");
	}
	@DeleteMapping("{id}")
	@ResponseBody
	public Result deleteFilter(@PathVariable Integer id){
		filterService.delete(id);
		return UtilResult.success("删除成功");
	}
	@PutMapping("filter")
	@ResponseBody
	public Result updateFilter(Filter filter){
		filterService.update(filter);
		return UtilResult.success("修改成功");
	}
	@GetMapping("{id}")
	@ResponseBody
	public Filter getOneFilter(@PathVariable Integer id){
		return filterService.selectOne(id);
	}
	@GetMapping("filters")
	@ResponseBody
	public PageInfo<Filter> getOneFilter(FilterParam param){
		return filterService.selectPage(param);
	}
	@GetMapping("filters/all")
	@ResponseBody
	public Result getAllFilter(FilterParam param){
		return UtilResult.success(filterService.selectList(param));
	}
	
	@PostMapping("relation")
	@ResponseBody
	public Result relation(Integer elementId,Integer[] filterIds){
		filterService.relation(elementId, filterIds);
		return UtilResult.success("操作成功");
	}
	
	
	
	
	
	
	
	
	
	
	
}
