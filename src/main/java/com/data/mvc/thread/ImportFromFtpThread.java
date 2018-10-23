package com.data.mvc.thread;

import com.data.mvc.param.ImportFromFtpParam;
import com.data.mvc.service.IImportService;
import com.zxt.base.utils.UtilSpring;

public class ImportFromFtpThread implements Runnable{
	private ImportFromFtpParam param;
	@Override
	public void run() {
		IImportService importService = UtilSpring.getBean(IImportService.class);
		importService.importFtp(param);
	}
	public ImportFromFtpThread(ImportFromFtpParam param) {
		super();
		this.param = param;
	}
	
	

}
