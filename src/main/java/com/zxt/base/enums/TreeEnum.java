package com.zxt.base.enums;

public enum TreeEnum {
	ORGANIZATION("organization","组织机构"),
	RESOURCE_LINK("link","连接"),
	RESOURCE_MODULE("module","模块"),
	RESOURCE_BUTTON("button","按钮");
	
	private String code;
	private String value;
	
	
	
	private TreeEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
