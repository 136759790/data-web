package com.zxt.base.utils;

import java.util.UUID;

import com.zxt.base.result.Result;
import com.zxt.base.result.ResultEnum;


public class UtilResult {
	public static Result success(){
		Result r=new Result();
		r.setResult_msg("操作成功");
		r.setResult_status(ResultEnum.SUCCESS.getValue());
		return r;
	}
	public static Result success(Object data){
		Result r=new Result();
		r.setData(data);
		r.setResult_msg("操作成功");
		r.setResult_status(ResultEnum.SUCCESS.getValue());
		return r;
	}
	public static Result success(String msg){
		Result r=new Result();
		r.setResult_msg(msg);
		r.setResult_status(ResultEnum.SUCCESS.getValue());
		return r;
	}
	public static Result error(){
		Result r=new Result();
		r.setResult_msg("操作失败请联系管理员");
		r.setResult_status(ResultEnum.ERROR.getValue());
		return r;
	}
	public static Result error(String msg){
		Result r=new Result();
		r.setResult_msg(msg);
		r.setResult_status(ResultEnum.ERROR.getValue());
		return r;
	}
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
