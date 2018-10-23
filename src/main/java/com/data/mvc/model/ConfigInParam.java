package com.data.mvc.model;


import com.zxt.base.result.Param;

public class ConfigInParam extends Param{
	private String name;
	private Integer blockcount;
	private String type;
	private String table;
	private String store_type;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getBlockcount() {
		return blockcount;
	}
	public void setBlockcount(Integer blockcount) {
		this.blockcount = blockcount;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getStore_type() {
		return store_type;
	}
	public void setStore_type(String store_type) {
		this.store_type = store_type;
	}
	
	
}
