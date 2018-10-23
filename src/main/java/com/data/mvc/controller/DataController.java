package com.data.mvc.controller;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.data.im.filter.Filter;
import com.data.im.log.LogContainer;
import com.data.mvc.constant.Constant;
import com.data.mvc.model.ConfigIn;
import com.data.mvc.model.ConfigInParam;
import com.data.mvc.model.ConfigOut;
import com.data.mvc.model.ConfigOutParam;
import com.data.mvc.model.Db;
import com.data.mvc.model.DbParam;
import com.data.mvc.model.Import;
import com.data.mvc.model.ImportParam;
import com.data.mvc.model.InElement;
import com.data.mvc.model.OutElement;
import com.data.mvc.model.ThreadModel;
import com.data.mvc.model.ThreadModelParam;
import com.data.mvc.service.IConfigInService;
import com.data.mvc.service.IConfigOutService;
import com.data.mvc.service.IDbService;
import com.data.mvc.service.IImportService;
import com.data.mvc.service.IThreadService;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.math.IntMath;
import com.zxt.base.result.PageInfo;
import com.zxt.base.result.Result;
import com.zxt.base.utils.UtilFtp;
import com.zxt.base.utils.UtilResult;

@Controller
@RequestMapping("/data/")
public class DataController {
	
	@Autowired
	IImportService imService;
	@Autowired
	IDbService dbService;
	@Autowired
	IConfigInService inService;
	@Autowired
	IConfigOutService outService;
	@Autowired
	IThreadService thService;
	@Value("${import.path}")
	String uploadPath;
	@Autowired
	ApplicationContext app;
	
	
	@RequestMapping("ims")
	@ResponseBody
	public PageInfo<Import> ims(ImportParam param){
		return imService.selectPage(param);
	}
	
	@GetMapping("log")
	@ResponseBody
	public String getLog(){
		return LogContainer.getLog();
	}
	
	@PutMapping("im")
	@ResponseBody
	public Result updateIm(Import im){
		imService.update(im);
		return UtilResult.success("修改成功");
	}
	@GetMapping("im/{id}")
	@ResponseBody
	public Import findOneIm(@PathVariable Integer id){
		return imService.selectOne(id);
	}
	
	@RequestMapping("addIm")
	@ResponseBody
	public Result addIm(Import im){
		imService.insert(im);
		return UtilResult.success("新增成功");
	}
	@RequestMapping("delIm")
	@ResponseBody
	public Result deleteIm(Integer id){
		imService.delete(id);
		return UtilResult.success("删除成功");
	}
	
	
	/**************数据源*****************/
	
	@RequestMapping("dbs")
	@ResponseBody
	public PageInfo<Db> dbs(DbParam param){
		return dbService.selectPage(param);
	}
	@PostMapping("db")
	@ResponseBody
	public Result db(Db db){
		dbService.insert(db);
		return UtilResult.success("操作成功");
	}
	@GetMapping("db/{id}")
	@ResponseBody
	public Db db(@PathParam("id") Integer id){
		return dbService.selectOne(id);
	}
	@DeleteMapping("db/{id}")
	@ResponseBody
	public Result delete(@PathVariable("id") Integer id){
		dbService.delete(id);
		return UtilResult.success();
	}
	@ResponseBody
	@PostMapping("")
	public Result update(Db db){
		dbService.update(db);
		return UtilResult.success();
	}
	
	@GetMapping("upload/files")
	@ResponseBody
	public Result uploadFiles(){
		File dir = new File(uploadPath);
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		File[] ff= dir.listFiles();
		for (File f : ff) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", f.getName());
			list.add(map);
		}
		return UtilResult.success(list);
	}
	@DeleteMapping("upload/file")
	@ResponseBody
	public Result deleteUploadFiles(String name){
		File target = new File(uploadPath+name);
		if(target.exists()){
			if(target.delete()){
				return UtilResult.success("删除成功");
			}
			return UtilResult.error("删除失败");
		}else{
			return UtilResult.success("目的文件不存在");
		}
	}
	
	/**************解析配置*****************/
	@RequestMapping("ins")
	@ResponseBody
	public PageInfo<ConfigIn> ins(ConfigInParam param){
		return inService.selectPage(param);
	}
	@PostMapping("in")
	@ResponseBody
	public Result in( ConfigIn in){
		inService.insert(in);
		return UtilResult.success("操作成功");
	}
	@PutMapping("in")
	@ResponseBody
	public Result updateConfigIn(ConfigIn in){
		inService.update(in);
		return UtilResult.success("修改成功");
	}
	@PostMapping("ele")
	@ResponseBody
	public Result ele( InElement ele){
		inService.insert(ele);
		return UtilResult.success("操作成功");
	}
	@GetMapping("in/{id}")
	@ResponseBody
	public ConfigIn getOneIn(@PathVariable("id") Integer id){
		return inService.selectOne(id);
	}
	@GetMapping("eles")
	@ResponseBody
	public List<InElement> eles(Integer id){
		return inService.selectListEle(id);
	}
	@DeleteMapping("in/{id}")
	@ResponseBody
	public Result deleteIn(@PathVariable("id") Integer id){
		inService.delete(id);
		return UtilResult.success();
	}
	@DeleteMapping("ele/{id}")
	@ResponseBody
	public Result deleteEle(@PathVariable("id") Integer id){
		inService.deleteEle(id);
		return UtilResult.success();
	}
	/**************解析配置*****************/
	/**************导入配置*****************/
	@PostMapping("out/ele")
	@ResponseBody
	public Result addOutEle( OutElement ele){
		outService.insertEle(ele);
		return UtilResult.success("操作成功");
	}
	@GetMapping("out/eles")
	@ResponseBody
	public List<OutElement> outEles( Integer id){
		return outService.selectListEle(id);
	}
	@RequestMapping("outs")
	@ResponseBody
	public PageInfo<ConfigOut> outs(ConfigOutParam param){
		return outService.selectPage(param);
	}
	@PostMapping("out")
	@ResponseBody
	public Result out(ConfigOut out){
		outService.insert(out);
		return UtilResult.success("操作成功");
	}
	@GetMapping("out/{id}")
	@ResponseBody
	public ConfigOut getOneOut(@PathParam("id") Integer id){
		return outService.selectOne(id);
	}
	@DeleteMapping("out/{id}")
	@ResponseBody
	public Result deleteOut(@PathVariable("id") Integer id){
		outService.delete(id);
		return UtilResult.success();
	}
	@DeleteMapping("out/ele/{id}")
	@ResponseBody
	public Result deleteOutEle(@PathVariable("id") Integer id){
		outService.deleteEle(id);
		return UtilResult.success();
	}
	/**************导入配置*****************/
	@GetMapping("/select/ins")
	@ResponseBody
	public List<ConfigIn> selectIns(){
		return inService.selectList(null);
	}
	@GetMapping("/select/ths")
	@ResponseBody
	public List<ThreadModel> selectThs(ThreadModelParam param){
		return thService.selectList(param);
	}
	@GetMapping("/select/outs")
	@ResponseBody
	public List<ConfigOut> selectOuts(){
		return outService.selectList(null);
	}
	@GetMapping("/select/dbs")
	@ResponseBody
	public List<Db> selectDbs(){
		return dbService.selectList(null);
	}
	
	@PostMapping("/config")
	@ResponseBody
	public Result config(Integer imid,Integer inid,Integer outid,Integer dbid,Integer thinid,Integer thoutid){
		
		imService.insertConfig(imid, inid, outid, dbid,thinid,thoutid);
		return UtilResult.success();
	}
	
	
	
	@GetMapping("/thread/{id}")
	@ResponseBody
	public ThreadModel getOneThread(@PathVariable Integer id){
		return thService.selectOne(id);
	}
	@GetMapping("/thread")
	@ResponseBody
	public PageInfo<ThreadModel> getPageThread(ThreadModelParam param){
		return thService.selectPage(param);
	}
	@PutMapping("/thread")
	@ResponseBody
	public Result updateThread(ThreadModel th){
		thService.update(th);
		return UtilResult.success();
	}
	@DeleteMapping("/thread/{id}")
	@ResponseBody
	public Result deleteTh(@PathVariable Integer id){
		thService.delete(id);
		return UtilResult.success();
	}
	@PostMapping("/thread")
	@ResponseBody
	public Result addTh(ThreadModel th){
		thService.insert(th);
		return UtilResult.success();
	}
	
	/**
	 * 检查路径
	 */
	@RequestMapping("check")
	@ResponseBody
	public Result check(String type,String path,String target,String host,Integer port,String username,String password){
		if("file".equals(type)){
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			try {
				Resource[] resources = resolver.getResources("file:"+path);
				List<String> list = new ArrayList<String>(resources.length);
				for (Resource r : resources) {
					list.add(r.getFilename());
				}
				return UtilResult.success(list);
			} catch (IOException e) {
				e.printStackTrace();
				return UtilResult.error("查找本地路径异常："+e.getMessage());
			}
		}else if("ftp".equals(type)){
			UtilFtp ftp = new UtilFtp();
			ftp.setHost(host);
			ftp.setPort(port);
			ftp.setUsername(username);
			ftp.setPassword(password);
			return UtilResult.success(ftp.getFileDetails(target));
		}else{
			return UtilResult.error("检查路径发现未知的导入种类："+type);
		}
	} 
	@GetMapping("import/types")
	@ResponseBody
	public Result getTypes(){
		Map<String,String> map  = Constant.TYPES;
		List<Map<String,String>> list =new ArrayList<Map<String,String>>();
		Set<String> set = map.keySet();
		for (String s : set) {
			Map<String, String> m =new HashMap<String, String>();
			m.put("value",s);
			m.put("label", map.get(s));
			list.add(m);
		}
		return UtilResult.success(list);
	}
	
}
