package com.data.im.store;

import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;

import com.data.im.BatchContext;
import com.data.im.config.Config;
import com.data.im.entity.DataBlock;

public interface Store {
	void store(DataBlock block , Config config ,BasicDataSource dataSource);
//	void store(DataBlock block , Config config,BatchContext context);
	void store(List<Map> data , Config config,BatchContext context);
}
