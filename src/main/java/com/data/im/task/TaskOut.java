package com.data.im.task;

import java.util.concurrent.Callable;

import com.data.im.BatchContext;

public abstract class TaskOut implements Callable{
	private BatchContext context;

	
	
	public BatchContext getContext() {
		return context;
	}

	public void setContext(BatchContext context) {
		this.context = context;
	}
	
	
	

}
