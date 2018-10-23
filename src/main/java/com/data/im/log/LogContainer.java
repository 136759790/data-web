package com.data.im.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.data.websocket.WebSocketServer;

public class LogContainer {
	private static final BlockingQueue<String> logs = new LinkedBlockingDeque<String>();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getLog(){
		try {
			return logs.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static   void setLog(String s){
		s = sdf.format(new Date())+":"+s;
		try {
			WebSocketServer.sendInfo(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static  void sendLog(String s){
		s = sdf.format(new Date())+":"+s;
		try {
			WebSocketServer.sendInfo(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
