package com.data.im.entity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.data.im.BatchContext;
import com.data.im.worker.WorkerOut;
import com.google.common.base.Objects;
/**
 * 当数据块满了之后将数据块提交给下一个线程池
 * @author zxt
 *
 */
public class DataBlockObserver implements Observer{
	private Log log = LogFactory.getLog(Observer.class);
	private ExecutorService pool;
	private BatchContext context; 
	private List<Future<String>> list_future;
	
	
	@Override
	public void update(Observable o, Object data) {
		log.error("come in observer...");
		if(data instanceof List){
			WorkerOut wo = new WorkerOut();
			wo.setContext(context);
			wo.setData((List) data);
			list_future.add(pool.submit(wo));
		}
	}
	
	
	
	
	
	
	public ExecutorService getPool() {
		return pool;
	}
	public void setPool(ExecutorService pool) {
		this.pool = pool;
	}
	public BatchContext getContext() {
		return context;
	}
	public void setContext(BatchContext context) {
		this.context = context;
	}






	public List<Future<String>> getList_future() {
		return list_future;
	}






	public void setList_future(List<Future<String>> list_future) {
		this.list_future = list_future;
	}
	
	
	

}
