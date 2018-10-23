package com.data.mvc.constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	public static final String TYPE_TXT = "txt";
	public static final String TYPE_CSV = "csv";
	public static final String TYPE_EXCEL = "excel";
	public static final String POLICY_CONTINUE = "continue";
	public static final String POLICY_BREAK = "break";
	
	public static Map<String, String> TYPES = new HashMap<String, String>();
	
	static{
		TYPES.put(TYPE_TXT, "txt类型文件，以逗号分开，一行一条数据");
		TYPES.put(TYPE_CSV, "csv类型文件，以逗号分开，一行一条数据");
		TYPES.put(TYPE_EXCEL, "excel类型文件，以逗号分开，一行一条数据");
	}
}
