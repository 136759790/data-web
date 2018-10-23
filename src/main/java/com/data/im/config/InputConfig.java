package com.data.im.config;

import java.io.InputStream;
import java.util.List;

import com.data.im.config.impl.ReadElement;
/**
 * 
 * @author zxt
 *
 */
public interface InputConfig extends Config{
	InputStream getIn();
	Integer getBlockCount();
	List<ReadElement> getElements();
	String getType();
	Integer getSkip();
	String getPolicy();
	String getTable();
	String getStore_type();
}
