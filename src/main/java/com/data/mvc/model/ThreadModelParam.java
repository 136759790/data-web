package com.data.mvc.model;

import com.zxt.base.result.Param;

public class ThreadModelParam extends Param{
	private Integer id;
	private String name;
	private String type;
	private Integer core;
	private Integer max;
	private Integer keepAlive;
	private Integer queueSize;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCore() {
		return core;
	}
	public void setCore(Integer core) {
		this.core = core;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getKeepAlive() {
		return keepAlive;
	}
	public void setKeepAlive(Integer keepAlive) {
		this.keepAlive = keepAlive;
	}
	public Integer getQueueSize() {
		return queueSize;
	}
	public void setQueueSize(Integer queueSize) {
		this.queueSize = queueSize;
	}
	@Override
	public String toString() {
		return "ThreadModel [id=" + id + ", name=" + name + ", core=" + core
				+ ", max=" + max + ", keepAlive=" + keepAlive + ", queueSize="
				+ queueSize + "]";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
