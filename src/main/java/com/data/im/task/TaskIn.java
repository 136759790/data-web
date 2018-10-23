package com.data.im.task;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import com.data.im.BatchContext;

public abstract class TaskIn implements Callable{
	private BatchContext context;
	private InputStream in;
	private ExecutorService poolOut;
	private CountDownLatch latch;
	private String fileName;
	
	public BatchContext getContext() {
		return context;
	}

	public void setContext(BatchContext context) {
		this.context = context;
	}

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public ExecutorService getPoolOut() {
		return poolOut;
	}

	public void setPoolOut(ExecutorService poolOut) {
		this.poolOut = poolOut;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
