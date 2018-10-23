package com.data.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestCreateFile {
	public static void main(String[] args) throws Exception {
		createSeFile();
	}
	
	public static void createSeFile()throws Exception{//构建分隔符的文件，暂定逗号隔开
		ObjectMapper mapper =new ObjectMapper();
		for (int j = 0; j < 1; j++) {
			File de=new File("D://d/personJson"+j+".txt");
			FileWriter writer =new FileWriter(de);
			for (int i = 0; i < 1000000; i++) {
				if(!de.exists()){
					de.createNewFile();
				}
				Person p =new Person();
				p.setId(i+(j*1000000));
				p.setAge(20);
				p.setAddress("北京市");
				p.setClazz("六年级");
				p.setEmail("123@qq.com");
				p.setPhonenum("123123123");
				p.setSex("男");
				writer.write(mapper.writeValueAsString(p));
				writer.write(System.getProperty("line.separator"));
				
//				StringBuilder sb=new StringBuilder();
//				sb.append(i+(j*1000000)).append(",")
//				.append("20").append(",")
//				.append("name"+j).append(",")
//				.append("男a").append(",")
//				.append("三年级a").append(",")
//				.append("北京市a").append(",")
//				.append("13822222222a").append(",")
//				.append("123456789a@qq.com");
//				writer.write(sb.toString());
//				writer.write(System.getProperty("line.separator"));
			}
			writer.flush();
			writer.close();
			
		}
		
	}
	public static void createJsonFile() throws Exception{//构建一行一个json的文件
	}
}
class Person{
	
	
	int id;
	int age;
	String name;
	String sex;
	String clazz;
	String address;
	String phonenum;
	String email;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}




