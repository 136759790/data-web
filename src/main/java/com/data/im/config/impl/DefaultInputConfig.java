package com.data.im.config.impl;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.data.im.config.InputConfig;
@XmlRootElement(name="config")
public class DefaultInputConfig implements InputConfig{
	private InputStream in;
	private Integer blockCount;
	private List<ReadElement> elements;
	private String type;
	private Integer skip;
	private String policy;//异常策略
	private String table;
	private String store_type;
	
	
	public InputStream getIn() {
		return in;
	}
	public void setIn(InputStream in) {
		this.in = in;
	}
	public Integer getBlockCount() {
		return blockCount;
	}
	public void setBlockCount(Integer blockCount) {
		this.blockCount = blockCount;
	}
	public List<ReadElement> getElements() {
		return elements;
	}
	public void setElements(List<ReadElement> elements) {
		this.elements = elements;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getSkip() {
		return skip;
	}
	public void setSkip(Integer skip) {
		this.skip = skip;
	}
	@Override
	public String getPolicy() {
		return this.policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
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
