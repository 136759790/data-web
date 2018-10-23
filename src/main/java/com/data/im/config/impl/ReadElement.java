package com.data.im.config.impl;

import java.util.List;



import com.data.im.filter.Filter;
import com.data.im.util.CommonUtil;


public class ReadElement {
	private String key;
	private String value;
	private Integer index;
	/**
	 * @see CommonUtil
	 */
	private String type;
	/**
	 * 日期格式需要指定pattern
	 */
	private String pattern;
	
	private List<Filter> filters;
	
	
	
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public List<Filter> getFilters() {
		return filters;
	}
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	
	
	
}
