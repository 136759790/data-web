package com.zxt.base.utils;

import java.util.HashMap;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.zxt.base.result.Param;

public class UtilPage {
	public static void startPage(Map map){
		int pageNum=1;
		int pageSize=10;
		if(map == null){
			map =new HashMap();
		}
		if(map.containsKey("page")){
			try {
				pageNum=Integer.valueOf(map.get("page").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(map.containsKey("rows")){
			try {
				pageSize=Integer.valueOf(map.get("rows").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		PageHelper.startPage(pageNum, pageSize);
	}
	public static void startPage(Param param){
		PageHelper.startPage(param.getPage(),param.getRows());
	}
}
