package com.data.im.filter.impl;

import org.springframework.stereotype.Component;

import com.data.im.exception.FilterException;
import com.data.im.filter.Filter;
@Component
public class FilterNotNull implements Filter{

	@Override
	public Object doFilter(Object data) {
		if(data == null){
			throw new FilterException("data can't be null");
		}
		return data;
	}

	@Override
	public String getName() {
		return "验证非空";
	}

}
