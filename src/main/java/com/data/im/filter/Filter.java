package com.data.im.filter;
/**
 * 数据集过滤器
 * @author zxt
 *
 */
public interface Filter {
	Object doFilter(Object data);
	String getName();
}
