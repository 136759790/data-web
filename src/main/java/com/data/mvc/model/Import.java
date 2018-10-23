package com.data.mvc.model;

import com.zxt.base.result.Result;

public class Import extends Result{
	private Integer id;
	private String name;
	private String policy;
	private String description;
	private Db db;
	private ConfigIn in;
	private ConfigOut out;
	private ThreadModel threadIn;
	private ThreadModel threadOut;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Db getDb() {
		return db;
	}
	public void setDb(Db db) {
		this.db = db;
	}
	public ConfigIn getIn() {
		return in;
	}
	public void setIn(ConfigIn in) {
		this.in = in;
	}
	public ConfigOut getOut() {
		return out;
	}
	public void setOut(ConfigOut out) {
		this.out = out;
	}
	public ThreadModel getThreadIn() {
		return threadIn;
	}
	public void setThreadIn(ThreadModel threadIn) {
		this.threadIn = threadIn;
	}
	public ThreadModel getThreadOut() {
		return threadOut;
	}
	public void setThreadOut(ThreadModel threadOut) {
		this.threadOut = threadOut;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
}
