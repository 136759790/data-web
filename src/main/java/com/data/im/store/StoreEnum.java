package com.data.im.store;
/**
 * 储存种类
 * @author zxt
 *
 */
public enum StoreEnum {
	MYSQL("mysql"),
	ORACLE("oracle"),
	MONGODB("mongodb"),
	ELASTICSEARCH("elasticsearch");
	
	
	private String key;

	private StoreEnum(String key) {
		this.key = key;
	}
	
	public StoreEnum getStore(String key){
		return  StoreEnum.valueOf(key);
	}
	
	
	
	
}
