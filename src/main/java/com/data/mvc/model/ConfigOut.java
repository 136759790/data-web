package com.data.mvc.model;

import java.util.List;

import com.zxt.base.result.Result;

public class ConfigOut extends Result{
	private Integer id;
	private String name;
	private String type;
	private String table;
	private List<OutElement> elements;
	
	
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
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<OutElement> getElements() {
		return elements;
	}
	public void setElements(List<OutElement> elements) {
		this.elements = elements;
	}
}
