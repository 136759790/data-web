package com.data.im.transform.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.data.im.BatchContext;
import com.data.im.config.Config;
import com.data.im.config.InputConfig;
import com.data.im.config.impl.ReadElement;
import com.data.im.entity.DataBlock;
import com.data.im.transform.TransForm;
import com.data.im.worker.WorkerOut;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public class Json2Data implements TransForm{
	private Logger log = Logger.getLogger(Json2Data.class);
	@Override
	public String transform(InputStream in, BatchContext context,ExecutorService pool) {
		ObjectMapper mapper = new ObjectMapper();
		InputConfig cf = null;
		Integer blockCount = 5000;
		Config config =context.getConfigIn();
		if(config instanceof InputConfig){
			cf = (InputConfig) config;
			blockCount = cf.getBlockCount();
		}
		DataBlock block = new DataBlock(cf.getBlockCount()); 
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String s="";
		List<Future<String>> list_future = new ArrayList<Future<String>>();
		try {
			while ((s= reader.readLine())!=null) {
				if(Strings.isNullOrEmpty(s)){
					log.warn("appear a empty json");
					continue;
				}
				Map origin = mapper.readValue(s, HashMap.class);
				Map target = new HashMap();
				List<ReadElement> list =  cf.getElements();
				for (ReadElement e : list) {
					target.put(e.getKey(), target.get(e.getValue()));
				}
				block.add(target);
				List list_data= block.getData();
				if(list_data.size() != 0 && list_data.size() % blockCount ==0){
					WorkerOut wo = new WorkerOut();
					wo.setContext(context);
					wo.setData(block.getData());
					list_future.add(pool.submit(wo));
					block = new DataBlock(blockCount);
				}
			}
			if(block.getData() != null && block.getData().size() > 0){
				WorkerOut wo = new WorkerOut();
				wo.setContext(context);
				wo.setData(block.getData());
				list_future.add(pool.submit(wo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
