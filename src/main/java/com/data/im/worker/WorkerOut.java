package com.data.im.worker;


import java.util.List;
import java.util.Map;

import com.data.im.config.InputConfig;
import com.data.im.entity.DataBlock;
import com.data.im.store.Store;
import com.data.im.store.StoreEnum;
import com.data.im.store.StoreFactory;
import com.data.im.task.TaskOut;


public class WorkerOut  extends TaskOut{
	
	private List<Map> data;

	@Override
	public Object call() throws Exception {
		InputConfig cf = (InputConfig) super.getContext().getConfigIn();
		String type = cf.getStore_type();
		Store store=StoreFactory.getStore(type);
		store.store(data, cf,super.getContext());
		return "success";
	}

	public List<Map> getData() {
		return data;
	}

	public void setData(List<Map> data) {
		this.data = data;
	}

	
	
	
	

}
