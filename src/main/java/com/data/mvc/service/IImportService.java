package com.data.mvc.service;

import java.util.List;

import com.data.mvc.model.Import;
import com.data.mvc.model.ImportParam;
import com.data.mvc.param.ImportFromFtpParam;
import com.data.mvc.param.ImportFromLocalPathParam;
import com.zxt.base.result.PageInfo;

public interface IImportService {
	void insert(Import im);
	void update(Import im);
	void delete(Integer id);
	Import selectOne(Integer id);
	List<Import> selectList(ImportParam param);
	PageInfo<Import> selectPage(ImportParam param);
	
	
	
	void insertConfig(Integer imid,Integer inid,Integer outid,Integer dbid,Integer thinid,Integer thoutid);
	
	
	
	void importData(ImportFromLocalPathParam param);
	
	void importFtp(ImportFromFtpParam param);
	
	void start(ImportFromLocalPathParam param);
	
	void start(ImportFromFtpParam param);
}
