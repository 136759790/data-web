package com.zxt.base.result;

import java.io.Serializable;
/**
 * 参数基础类
 * @author zxt
 *
 */
public class Param implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 排序字段 **/
	private String orderby;
	/** 排序方向 **/
	private String sort="asc";
	/** 当前页数 **/
	private Integer page = 1;
	/** 每页条数 **/
	private Integer rows = 10;
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
	
}
