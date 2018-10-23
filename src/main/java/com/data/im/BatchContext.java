package com.data.im;

import javax.sql.DataSource;

import com.data.im.config.Config;

public class BatchContext {
	private DataSource dataSource;
	private Config configIn;
	private String type;//导入种类，区别那种方式解析文件
	private String policy;//异常策略

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Config getConfigIn() {
		return configIn;
	}

	public void setConfigIn(Config configIn) {
		this.configIn = configIn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	
}
