package com.zxt.base.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
@Component
public class String2DateConverter implements Converter<String, Date>{
	Logger logger =LoggerFactory.getLogger(String2DateConverter.class);
	@Override
	public Date convert(String source) {
		String pattern="";
		if(!StringUtils.isEmpty(source)){
			if(source.contains(":")){
				pattern="yyyy-MM-dd HH:mm:ss";
			}else if(source.contains("/")){
				pattern="yyyy/MM/dd";
			}else{
				pattern="yyyy-MM-dd";
			}
		}
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		logger.error("日期转换----------------------->"+source);
		try {
			return sdf.parse(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
