package com.data.im.worker;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import com.data.im.BatchContext;
import com.data.im.config.Config;
import com.data.im.config.InputConfig;
import com.data.im.task.TaskIn;
import com.data.im.transform.TransForm;
import com.data.im.transform.TransFormFactory;

public class WorkerIn  extends TaskIn{
	@Override
	public Object call() throws Exception {
		CountDownLatch latch = super.getLatch();
		try {
			InputStream in = super.getIn();
			ExecutorService poolOut = super.getPoolOut();
			BatchContext context = super.getContext();
			Config cin = context.getConfigIn();
			TransForm ts = null;
			if(cin instanceof InputConfig){
				InputConfig inc = (InputConfig) cin;
				ts = TransFormFactory.instance.getTransForm(inc.getType());
			}
			return ts.transform(in, context, poolOut);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			latch.countDown();
		}
		
	}

	
	

}
