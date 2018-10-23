package com.data.im.transform.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.data.im.BatchContext;
import com.data.im.config.Config;
import com.data.im.config.InputConfig;
import com.data.im.entity.DataBlock;
import com.data.im.entity.DataBlockObserver;
import com.data.im.transform.TransForm;

public abstract class InputStream2Data implements TransForm{
	private Log log = LogFactory.getLog(InputStream2Data.class);
	@Override
	public String transform(InputStream in, BatchContext context,ExecutorService pool) {
		log.debug("start parse file...");
		try {
			InputConfig cf = null;
			Integer blockCount = 5000;
			Config config =context.getConfigIn();
			if(config instanceof InputConfig){
				cf = (InputConfig) config;
				blockCount = cf.getBlockCount();
			}
			List<Future<String>> list_future = new ArrayList<Future<String>>();
			DataBlock block = new DataBlock(blockCount); 
			DataBlockObserver observer = new DataBlockObserver();
			observer.setContext(context);
			observer.setPool(pool);
			observer.setList_future(list_future);
			block.addObserver(observer);
			process(cf, in, block);
			for (Future<String> f : list_future) {
				f.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "success";
	}
	
	public abstract void process(InputConfig configIn,InputStream in,DataBlock block);
	
	public  abstract  Map parseRecord(InputConfig config,Object record);
}
