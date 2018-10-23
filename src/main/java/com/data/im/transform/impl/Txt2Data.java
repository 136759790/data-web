package com.data.im.transform.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.data.im.BatchContext;
import com.data.im.config.Config;
import com.data.im.config.InputConfig;
import com.data.im.config.impl.ReadElement;
import com.data.im.entity.DataBlock;
import com.data.im.exception.FilterException;
import com.data.im.filter.FilterChain;
import com.data.im.log.LogContainer;
import com.data.im.transform.TransForm;
import com.data.im.util.CommonUtil;
import com.data.im.worker.WorkerOut;
import com.data.mvc.constant.Constant;
import com.google.common.io.Files;
/**
 * txt文件
 * 一行一条记录
 * 以逗号分隔
 * @author pactera
 *
 */
public class Txt2Data extends InputStream2Data{
	private Logger log = Logger.getLogger(Txt2Data.class);

	@Override
	public void process(InputConfig configIn, InputStream in, DataBlock block) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String s="";
		try {
			while ((s= reader.readLine())!=null) {
				try {
					block.add(parseRecord(configIn, s));
				} catch (FilterException e2) {
					if(Constant.POLICY_CONTINUE.equals(configIn.getPolicy())){
						LogContainer.sendLog("数据校验失败，即将跳过此条数据继续执行。失败原因："+e2.getMessage());
						continue ;
					}else{
						LogContainer.sendLog("数据校验失败，即将终止执行。失败原因："+e2.getMessage());
						break ;
					}
				}
			}
			block.storeRest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map parseRecord(InputConfig config, Object record) {
		Map map = new HashMap();
		String[] arr= record.toString().split(",");
		List<ReadElement> list =  config.getElements();
		loop:for (ReadElement e : list) {
			Object value = arr[Integer.valueOf(e.getIndex())];
			value = FilterChain.doFilter(value,e.getFilters());
			map.put(e.getKey(),  CommonUtil.convertToJdbcType(value, e.getType(), e.getPattern()));
		}
		return map;
	}
	
	
	

}
