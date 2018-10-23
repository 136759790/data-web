package com.data.im.transform;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;

import com.data.im.BatchContext;

public interface TransForm {
	/**
	 * 将文件流分割为数个数据块
	 * @param in
	 * @param context
	 * @param pool
	 * @return
	 */
	String transform(InputStream in, BatchContext context,ExecutorService pool);
}
