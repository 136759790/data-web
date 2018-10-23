package com.zxt.base.result;

public class Result {
	private Integer result_status=1;
	private String result_msg;
	private String result_code;
	private Object data;
	
	
	public Integer getResult_status() {
		return result_status;
	}
	public void setResult_status(Integer result_status) {
		this.result_status = result_status;
	}
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
	
	
}
