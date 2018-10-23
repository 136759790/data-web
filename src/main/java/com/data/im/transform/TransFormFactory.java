package com.data.im.transform;

import com.data.im.transform.impl.Csv2data;
import com.data.im.transform.impl.Excel2Data;
import com.data.im.transform.impl.Txt2Data;
import com.data.mvc.constant.Constant;

public enum TransFormFactory {
	instance;
	
	public TransForm getTransForm(String type){
		switch (type) {
		case Constant.TYPE_TXT:
			return new Txt2Data();
		case Constant.TYPE_CSV:
			return new Csv2data();
		case Constant.TYPE_EXCEL:
			return new Excel2Data();
		default:
			throw new RuntimeException("无法识别种类："+type);
		}
	}
}
