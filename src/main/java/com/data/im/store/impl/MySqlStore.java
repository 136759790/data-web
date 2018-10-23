package com.data.im.store.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.data.im.BatchContext;
import com.data.im.config.Config;
import com.data.im.config.impl.DefaultInputConfig;
import com.data.im.config.impl.ReadElement;
import com.data.im.entity.DataBlock;
import com.data.im.log.LogContainer;
import com.data.im.store.Store;

public class MySqlStore implements Store{
	private Logger logger = Logger.getLogger(MySqlStore.class);

	public void store(DataBlock block, Config config,BasicDataSource dataSource) {
		Map result = createSql(config);
		String sql = (String) result.get("sql");
		logger.debug("sql:"+sql);
		List<Map> list = block.getData();
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for (Map m : list) {
				Set<String> set=m.keySet();
				for (String key : set) {
					ps.setObject(Integer.valueOf(result.get(key).toString()), m.get(key));
				}
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			LogContainer.setLog("插入数据成功："+list.size());
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			LogContainer.setLog("插入数据失败进入重试："+list.size());
			e.printStackTrace();
			throw new RuntimeException("持久化数据失败");
		}finally{
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	private static Map createSql(Config config){
		DefaultInputConfig conf = (DefaultInputConfig) config;
		Map map =new HashMap();
		String table=conf.getTable();
		List<ReadElement> list=conf.getElements();
		StringBuilder sb=new StringBuilder();
		StringBuilder sb_values=new StringBuilder();
		sb_values.append("(");
		sb.append("INSERT INTO ").append(table).append("(");
		for (int i = 0; i < list.size(); i++) {
			ReadElement  s=list.get(i);
			map.put(s.getKey(), i+1);
			if(i==0){
				sb.append(s.getKey());
				sb_values.append("?");
			}else{
				sb.append(",");
				sb.append(s.getKey());
				sb_values.append(",?");
			}
		}
		sb.append(")").append("values").append(sb_values).append(")");
		map.put("sql", sb.toString());
		return map;
	}



	@Override
	public void store(List<Map> data, Config config, BatchContext context) {
		DefaultInputConfig df = (DefaultInputConfig)config;
		DataSource ds = context.getDataSource();
		Map result = createSql(df);
		String sql = (String) result.get("sql");
		List<Map> list = data;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for (Map m : list) {
				Set<String> set=m.keySet();
				for (String key : set) {
					ps.setObject(Integer.valueOf(result.get(key).toString()), m.get(key));
				}
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			LogContainer.sendLog("成功导入一个数据块，数据量为："+list.size());
		} catch (Exception e) {
			e.printStackTrace();
			//异常了回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
