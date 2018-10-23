package com.zxt.base.enums;

public enum BaseEnum {
	RESULT_STATUS_SUCCESS(1,"success","成功状态"),
	RESULT_STATUS_EXCEPTION(0,"exception","异常状态"),
	RESULT_STATUS_TIMEOUT(2,"timeout","超时状态");
	
	
	private Integer id;
	private String code;
	private String desc;
	
	
	
	
	
	private BaseEnum(Integer id, String code, String desc) {
		this.id = id;
		this.code = code;
		this.desc = desc;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	
}
