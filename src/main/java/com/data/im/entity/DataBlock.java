package com.data.im.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * 数据块
 * @author zxt
 *
 */
public class DataBlock extends Observable{
	private Integer count;
	private String fileName;
	private List<Map> data;//数据集
	
	public void initData(){
		this.data= new ArrayList<Map>(count);
	}
	
	public DataBlock(Integer count) {
		data = new ArrayList<Map>(count);
		this.count = count;
	}
	public void add(Map map){
		data.add(map);
		if(data != null && data.size() > 0 && data.size()%count ==0){
			setChanged();
			notifyObservers(data);
			this.initData();
		}
	}
	
	
	
	
	public List<Map> getData() {
		return data;
	}
	public void setData(List<Map> data) {
		this.data = data;
	}
	public DataBlock() {
		super();
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	
	public void storeRest(){
		if(data !=null && data.size() > 0){
			setChanged();
			notifyObservers(data);
			this.initData();
		}
	}
	
	
	
	
	
}
