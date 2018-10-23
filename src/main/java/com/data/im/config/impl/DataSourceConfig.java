package com.data.im.config.impl;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="config")
public class DataSourceConfig {
	
	private String url;
	private String username;
	private String password;
	private String driverclassname;
	private Integer maxactive;
	private Integer initialsize;
	
	
	
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
	public String getDriverclassname() {
		return driverclassname;
	}
	public void setDriverclassname(String driverclassname) {
		this.driverclassname = driverclassname;
	}
	public Integer getMaxactive() {
		return maxactive;
	}
	public void setMaxactive(Integer maxactive) {
		this.maxactive = maxactive;
	}
	public Integer getInitialsize() {
		return initialsize;
	}
	public void setInitialsize(Integer initialsize) {
		this.initialsize = initialsize;
	}
	
	
	
	
	
	
}
