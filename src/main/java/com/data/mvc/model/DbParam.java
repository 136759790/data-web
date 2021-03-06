package com.data.mvc.model;

import com.zxt.base.result.Param;

/**
 * 连接池
 * @author zxt
 *
 */
public class DbParam extends Param{
	private Integer id;
	private String name;
	private String url;
	private String username;
	private String password;
	private String driver;
	private Integer max;
	private Integer init;
	
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getInit() {
		return init;
	}
	public void setInit(Integer init) {
		this.init = init;
	}
	
	
	
}
