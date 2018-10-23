package com.data.im.transform.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.data.im.config.InputConfig;
import com.data.im.config.impl.ReadElement;
import com.data.im.entity.DataBlock;
import com.data.im.filter.FilterChain;
import com.data.im.util.CommonUtil;

/**
 * 从csv文件中读取数据
 * @author zxt
 *
 */
public class Csv2data extends InputStream2Data{
	private Log log = LogFactory.getLog(Csv2data.class);

	@Override
	public void process(InputConfig configIn, InputStream in, DataBlock block) {
		try {
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(new InputStreamReader(in));
			for(Iterator<CSVRecord> i = records.iterator();i.hasNext();){
				block.add(this.parseRecord(configIn, i.next()));
			}
			block.storeRest();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(in);
		}

	}

	@Override
	public Map parseRecord(InputConfig config, Object record) {
		CSVRecord record2 = (CSVRecord) record;
		Map map = new HashMap();
		List<ReadElement> list =  config.getElements();
		for (ReadElement e : list) {
			Object value = record2.get(e.getValue());
			value = FilterChain.doFilter(value,e.getFilters());
			map.put(e.getKey(),  CommonUtil.convertToJdbcType(value, e.getType(), e.getPattern()));
		}
		return map;
	}

}
