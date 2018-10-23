package com.zxt.base.result;

public enum ResultEnum {
	SUCCESS(1),
	ERROR(0);
	
	private int value;
	
	private ResultEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
}
