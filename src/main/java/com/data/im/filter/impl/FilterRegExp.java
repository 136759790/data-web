package com.data.im.filter.impl;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.data.im.exception.FilterException;
import com.data.im.filter.Filter;

/**
 * 正则表达式过滤器
 * @author zxt
 *
 */
@Component
public class FilterRegExp implements Filter{
	private String reg;

	@Override
	public Object doFilter(Object data) {
		if(!Pattern.matches(reg, data.toString())){
			throw new FilterException("data["+data+"]不符合正则表达式-》"+reg);
		}
		return data;
	}

	@Override
	public String getName() {
		return "根据正则验证数据";
	}

	public FilterRegExp() {
		super();
	}
	
	public FilterRegExp(String reg) {
		super();
		this.reg = reg;
	}
}
