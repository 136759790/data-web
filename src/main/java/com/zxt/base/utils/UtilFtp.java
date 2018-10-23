package com.zxt.base.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class UtilFtp {
	private  final Log log = LogFactory.getLog(UtilFtp.class);
	private  FTPClient client;
	private String host;
	private Integer port;
	private String username;
	private String password;
	
	public  void download(String originPath,String targetPath){
		try {
			initClient(host, port, username, password);
			client.changeWorkingDirectory(originPath);
			FTPFile[] files = client.listFiles(originPath);
			for (FTPFile f : files) {
				File targetFile = new File(targetPath+"/"+f.getName());
				OutputStream os = null;
				try {
					os =new FileOutputStream(targetFile);
					client.retrieveFile(f.getName(), os);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					os.flush();
					os.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				client.logout();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void close(){
		try {
			this.client.logout();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public  List<InputStream> getStream(String path){
		try {
			initClient(host, port, username, password);
			client.changeWorkingDirectory(path);
			List<InputStream> list = new ArrayList<InputStream>();
			FTPFile[] files = client.listFiles(path);
			for (FTPFile f : files) {
				InputStream in = null;
				try {
					in = client.retrieveFileStream(f.getName());
					list.add(in);
//					client.completePendingCommand();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
//			try {
//				client.logout();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		
	}
	public  List<String> getFileDetails(String path){
		initClient(host, port, username, password);
		List<String> list =new ArrayList<String>();
		try {
			FTPFile[] files= client.listFiles(path);
			for (FTPFile f : files) {
				list.add(f.getName());
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				client.logout();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	private  void initClient(String host,Integer port,String username,String password){
		log.debug("init client...");
		client = new FTPClient();
		client.setControlEncoding("UTF-8");
		try {
			client.connect(host, port);
			client.login(username, password);
			int replyCode = client.getReplyCode();
			if(!FTPReply.isPositiveCompletion(replyCode)){
				throw new RuntimeException("client init failed");
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Log getLog() {
		return log;
	}
}
