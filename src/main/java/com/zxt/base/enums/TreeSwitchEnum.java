package com.zxt.base.enums;
/**
 * 转义树结构的时候用
 * @author Jason
 *
 */
public enum TreeSwitchEnum {
	ID("id","主键"),
	PID("pid","父节点主键"),
	TEXT("text","显示内容");
	
	private String code;
	private String value;
	
	
	private TreeSwitchEnum(String code, String value) {
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
