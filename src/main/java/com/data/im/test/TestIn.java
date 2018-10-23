//package com.data.im.test;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Future;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Unmarshaller;
//
//import org.apache.commons.dbcp.BasicDataSource;
//import org.omg.CORBA.portable.ApplicationException;
//
//import com.data.im.BatchContext;
//import com.data.im.config.Config;
//import com.data.im.config.impl.DataSourceConfig;
//import com.data.im.config.impl.DefaultInputConfig;
//import com.data.im.config.impl.DefaultOutConfig;
//import com.data.im.config.impl.SqlConfig;
//import com.data.im.worker.Factory;
//import com.data.im.worker.WorkerIn;
//
//public class TestIn {
//	public static void main(String[] args) {
//		/******************准备工作**********************/
//		ExecutorService poolIn = 
//				new ThreadPoolExecutor(5, 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
//						 new ThreadPoolExecutor.CallerRunsPolicy());
//		//数据存储线程池
//		ExecutorService poolOut = 
//				new ThreadPoolExecutor(5, 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100),
//						 new ThreadPoolExecutor.CallerRunsPolicy());
//		
//		String dbconf_path="d:/config/dataSource.xml";
//		String in_path="d:/config/in.xml";
//		String out_path="d:/config/out.xml";
//		String file_path="d:/d";
//		DefaultInputConfig cin = getConfig(in_path, DefaultInputConfig.class);
//		DefaultOutConfig cout = getConfig(out_path, DefaultOutConfig.class);
//		BasicDataSource dataSource =getDs(dbconf_path);
//		BatchContext context = new BatchContext();
//		context.setDataSource(dataSource);
//		context.setConfigIn(cin);
//		context.setConfigOut(cout);
//		/******************准备工作**********************/
//		File dir = new File(file_path);
//		long st=System.currentTimeMillis();
//		Factory<String> factory =new Factory<String>(poolIn, poolOut,context);
//		List<Future<String>> result = new ArrayList<Future<String>>();
//		if(dir.isDirectory()){
//			File[] ff = dir.listFiles();
//			for (File f : ff) {
//				WorkerIn wi = new WorkerIn();
//				try {
//					wi.setIn(new FileInputStream(f));
//					result.add(factory.submitIn(wi));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		for (Future<String> f : result) {
//			try {
//				f.get();
//			} catch (Exception e) {
//				e.printStackTrace();
//			} 
//		}
//		poolIn.shutdown();
//		poolOut.shutdown();
//		long et = System.currentTimeMillis();
//		System.out.println("耗时："+(et -st)/1000);
//	}
//	
//	
//	public static <T> T getConfig(String url,Class<? extends Config> clazz){
//		JAXBContext context;
//		try {
//			context = JAXBContext.newInstance(clazz);
//			Unmarshaller unmarshaller = context.createUnmarshaller();
//			Object obj =  unmarshaller.unmarshal(new File(url));
//			return (T)obj;
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	public static SqlConfig getSqlConfig(String url){
//		JAXBContext context;
//		try {
//			context = JAXBContext.newInstance(SqlConfig.class);
//			Unmarshaller unmarshaller = context.createUnmarshaller();
//			SqlConfig m = (SqlConfig) unmarshaller.unmarshal(new File(url));
//			return m;
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	public static  BasicDataSource getDs(String url){
//		JAXBContext context;
//		try {
//			context = JAXBContext.newInstance(DataSourceConfig.class);
//			Unmarshaller unmarshaller = context.createUnmarshaller();
//			DataSourceConfig m = (DataSourceConfig) unmarshaller.unmarshal(new File(url));
//			BasicDataSource ds = new BasicDataSource();
//			ds.setUrl(m.getUrl());
//			ds.setUsername(m.getUsername());
//			ds.setPassword(m.getPassword());
//			ds.setMaxActive(m.getMaxactive());
//			ds.setDriverClassName(m.getDriverclassname());
//			ds.setInitialSize(m.getInitialsize());
//			return ds;
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return null;
//		
//		
//	}
//}
