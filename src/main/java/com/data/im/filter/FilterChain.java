package com.data.im.filter;

import java.util.List;

public class FilterChain {
	
	public static Object doFilter(Object record,List<Filter>filters){
		for (Filter filter : filters) {
			record = filter.doFilter(record);
		}
		return record;
	}
	
}
