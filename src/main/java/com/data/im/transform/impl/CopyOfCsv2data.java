package com.data.im.transform.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.data.im.BatchContext;
import com.data.im.config.Config;
import com.data.im.config.InputConfig;
import com.data.im.config.impl.ReadElement;
import com.data.im.entity.DataBlock;
import com.data.im.transform.TransForm;
import com.data.im.worker.WorkerOut;

/**
 * 从csv文件中读取数据
 * @author zxt
 *
 */
public class CopyOfCsv2data implements TransForm{
	private Log log = LogFactory.getLog(CopyOfCsv2data.class);

	@Override
	public String transform(InputStream in, BatchContext context, ExecutorService pool) {
		log.debug("start parse file...");
		try {
			InputConfig cf = null;
			Integer blockCount = 5000;
			Config config =context.getConfigIn();
			if(config instanceof InputConfig){
				cf = (InputConfig) config;
				blockCount = cf.getBlockCount();
			}
			DataBlock block = new DataBlock(cf.getBlockCount()); 
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(new InputStreamReader(in));
			List<Future<String>> list_future = new ArrayList<Future<String>>();
			for (CSVRecord record : records) {
				Map map = new HashMap();
				List<ReadElement> list =  cf.getElements();
				for (ReadElement e : list) {
					map.put(e.getKey(), record.get(e.getValue()));
				}
				block.add(map);
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
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "success";
	}

}
