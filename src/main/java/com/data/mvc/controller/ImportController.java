package com.data.mvc.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.data.mvc.param.ImportFromFtpParam;
import com.data.mvc.param.ImportFromLocalPathParam;
import com.data.mvc.service.IConfigInService;
import com.data.mvc.service.IConfigOutService;
import com.data.mvc.service.IDbService;
import com.data.mvc.service.IImportService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.zxt.base.result.Result;
import com.zxt.base.utils.UtilResult;
@Controller
@RequestMapping("/import/")
public class ImportController {
	@Autowired
	IImportService imService;
	@Autowired
	IDbService dbService;
	@Autowired
	IConfigInService inService;
	@Autowired
	IConfigOutService outService;
	@Value("${import.path}")
	String uploadPath;
	
	@RequestMapping("upload")
	@ResponseBody
	public void upload(MultipartFile file){
		createFolders(uploadPath);
		try {
			file.transferTo(new File(uploadPath+file.getOriginalFilename()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("import")
	@ResponseBody
	public Result importData(String version,Integer imid,String type){
		try {
			ImportFromLocalPathParam param = new ImportFromLocalPathParam();
			param.setImid(imid);
			param.setPath("file:"+uploadPath+"*");
			param.setType(type);
			imService.start(param);
			return UtilResult.success("开始导入。");
		} catch (Exception e) {
			return UtilResult.success("导入失败："+e.getMessage());
		}
	}
	@RequestMapping("importFile")
	@ResponseBody
	public Result importFileData(ImportFromLocalPathParam param){
		try {
			param.setPath("file:"+param.getPath());
			imService.start(param);
			return UtilResult.success("开始导入。");
		} catch (Exception e) {
			return UtilResult.success("导入失败："+e.getMessage());
		}
	}
	@RequestMapping("importFtp")
	@ResponseBody
	public Result importFtp(ImportFromFtpParam param){
		try {
			imService.start(param);
			return UtilResult.success("开始导入。");
		} catch (Exception e) {
			return UtilResult.success("导入失败："+e.getMessage());
		}
	}
	
	private void  createFolders(String path){
		File folder = new File(path);
		if(!folder.exists()){
			folder.mkdirs();
		}
	}
	private String getPath(String p){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date tody = new Date();
		return "D:/upload/"+sdf.format(tody)+"/"+p+"/";
		
	}
	
	
	
	
	
	
}
