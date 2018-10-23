package com.data.im.worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.data.im.BatchContext;
import com.data.im.task.TaskIn;
import com.data.im.task.TaskOut;

public class Factory<T> {
	protected ExecutorService poolIn;
	protected ExecutorService poolOut;
	protected BatchContext context;
	
	public Future<T> submitIn(TaskIn task){
		task.setContext(context);
		task.setPoolOut(poolOut);
		return poolIn.submit(task);
	}
	public Future<T> submitOut(TaskOut task){
		return  poolOut.submit(task);
	}
	public ExecutorService getPoolIn() {
		return poolIn;
	}
	public void setPoolIn(ExecutorService poolIn) {
		this.poolIn = poolIn;
	}
	public ExecutorService getPoolOut() {
		return poolOut;
	}
	public void setPoolOut(ExecutorService poolOut) {
		this.poolOut = poolOut;
	}
	public Factory(ExecutorService poolIn, ExecutorService poolOut,
			BatchContext context) {
		super();
		this.poolIn = poolIn;
		this.poolOut = poolOut;
		this.context = context;
	}
	public Factory() {
		super();
	}
	public BatchContext getContext() {
		return context;
	}
	public void setContext(BatchContext context) {
		this.context = context;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
