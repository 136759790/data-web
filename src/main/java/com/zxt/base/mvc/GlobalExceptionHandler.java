package com.zxt.base.mvc;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.zxt.base.result.Result;
import com.zxt.base.result.ResultEnum;


@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger =LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public Result defaultErrorHandler(HttpServletRequest req,Exception e)throws Exception{
		logger.error("springmvc错误---------------->",e);
		Result result=new Result();
		result.setResult_msg(e.getMessage());
		if(e instanceof NoHandlerFoundException){
			result.setResult_code("404");
		}else{
			result.setResult_code("500");
		}
		result.setResult_status(ResultEnum.ERROR.getValue());
		return result;
	}
}
