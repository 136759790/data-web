package com.data.im.store;

import com.data.im.store.impl.MySqlStore;


public enum StoreFactory {
	INSTANCE;
	
	private static  MySqlStore mySqlStore = new MySqlStore();
	public static  Store getStore(String store){
		if("MYSQL".equals(store.toUpperCase())){
			return mySqlStore;
		}
		throw new RuntimeException("can't find right store");
	}
	
}
