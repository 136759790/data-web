package com.data.mvc.thread;

import com.data.mvc.param.ImportFromLocalPathParam;
import com.data.mvc.service.IImportService;
import com.zxt.base.utils.UtilSpring;

public class ImportFromLocalPathThread implements Runnable{
	private ImportFromLocalPathParam param;
	@Override
	public void run() {
		IImportService importService = UtilSpring.getBean(IImportService.class);
		importService.importData(param);
	}
	public ImportFromLocalPathThread(ImportFromLocalPathParam param) {
		super();
		this.param = param;
	}
	
	

}
