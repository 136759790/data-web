package com.data.mvc.model;

import com.zxt.base.result.Result;

public class ThreadModel extends Result{
	private Integer id;
	private String type;//in or out
	private String name;
	private Integer core;
	private Integer max;
	private Integer keepalive;
	private Integer queuesize;
	
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getKeepalive() {
		return keepalive;
	}
	public void setKeepalive(Integer keepalive) {
		this.keepalive = keepalive;
	}
	public Integer getQueuesize() {
		return queuesize;
	}
	public void setQueuesize(Integer queuesize) {
		this.queuesize = queuesize;
	}
	@Override
	public String toString() {
		return "ThreadModel [id=" + id + ", type=" + type + ", name=" + name
				+ ", core=" + core + ", max=" + max + ", keepalive="
				+ keepalive + ", queuesize=" + queuesize + "]";
	}
	
	
	
	
	
	
}
