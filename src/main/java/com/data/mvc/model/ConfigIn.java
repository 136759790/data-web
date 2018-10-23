package com.data.mvc.model;

import java.util.List;

import com.data.im.config.impl.ReadElement;
import com.zxt.base.result.Result;
/**
 * 解析配置
 * @author zxt
 *
 */
public class ConfigIn extends Result{
	private Integer id;
	private String name;
	private Integer blockcount;
	private List<InElement> elements;
	private String type;
	private String store_type;
	private String table;
	private Integer skip;
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBlockcount() {
		return blockcount;
	}
	public void setBlockcount(Integer blockcount) {
		this.blockcount = blockcount;
	}
	public List<InElement> getElements() {
		return elements;
	}
	public void setElements(List<InElement> elements) {
		this.elements = elements;
	}
	public Integer getSkip() {
		return skip;
	}
	public void setSkip(Integer skip) {
		this.skip = skip;
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
