package com.data.mvc.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data.im.BatchContext;
import com.data.im.config.impl.DefaultInputConfig;
import com.data.im.config.impl.ReadElement;
import com.data.im.filter.Filter;
import com.data.im.filter.impl.FilterRegExp;
import com.data.im.log.LogContainer;
import com.data.im.worker.Factory;
import com.data.im.worker.WorkerIn;
import com.data.mvc.dao.DbMapper;
import com.data.mvc.dao.ImportMapper;
import com.data.mvc.dao.InMapper;
import com.data.mvc.dao.OutMapper;
import com.data.mvc.dao.ThreadMapper;
import com.data.mvc.exception.ConfigNotFoundException;
import com.data.mvc.model.ConfigIn;
import com.data.mvc.model.ConfigOut;
import com.data.mvc.model.Db;
import com.data.mvc.model.Import;
import com.data.mvc.model.ImportParam;
import com.data.mvc.model.InElement;
import com.data.mvc.model.OutElement;
import com.data.mvc.model.ThreadModel;
import com.data.mvc.param.ImportBaseParam;
import com.data.mvc.param.ImportFromFtpParam;
import com.data.mvc.param.ImportFromLocalPathParam;
import com.data.mvc.thread.ImportFromFtpThread;
import com.data.mvc.thread.ImportFromLocalPathThread;
import com.zxt.base.result.PageInfo;
import com.zxt.base.utils.UtilFtp;
import com.zxt.base.utils.UtilPage;
@Service(value="importService")
public class ImportService implements IImportService{
	private final Lock lock = new   ReentrantLock();
	@Autowired
	ImportMapper mapper;
	
	@Autowired
	DbMapper dbMapper;
	@Autowired
	InMapper inMapper;
	@Autowired
	OutMapper outMapper;
	
	@Autowired
	ThreadMapper thMapper;
	
	@Override
	public void insert(Import im) {
		mapper.insert(im);
	}

	@Override
	public void update(Import im) {
		mapper.update(im);
	}

	@Override
	public Import selectOne(Integer id) {
		return mapper.selectOne(id);
	}

	@Override
	public List<Import> selectList(ImportParam param) {
		return mapper.selectList(param);
	}

	@Override
	public PageInfo<Import> selectPage(ImportParam param) {
		UtilPage.startPage(param);
		return new PageInfo<Import>(mapper.selectList(param));
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	@Transactional
	public void insertConfig(Integer imid, Integer inid, Integer outid,
			Integer dbid,Integer thinid,Integer thoutid) {
		mapper.deleteIn(imid);
		mapper.deleteOut(imid);
		mapper.deleteDb(imid);
		mapper.deleteTh(imid);
		mapper.insertDb(imid, dbid);
		mapper.insertIn(imid, inid);
		mapper.insertOut(imid, outid);
		mapper.insertTh(imid, thinid);
		mapper.insertTh(imid, thoutid);
	}

	@Override
	public void importData(ImportFromLocalPathParam param) {
		try {
			lock.tryLock(15, TimeUnit.SECONDS);
			LogContainer.setLog("成功获取导入锁，开始初始化");
			Factory<String> factory =prepareFactory(param);
			LogContainer.setLog("初始化数据完成，开始导入数据");
			long st=System.currentTimeMillis();
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			try {
				Resource[] ress= resolver.getResources(param.getPath());
				LogContainer.setLog("在["+param.getPath()+"]路径下文件数量为："+ress.length);
				List<InputStream> tasks = new ArrayList<InputStream>();
				for (Resource r : ress) {
					tasks.add(r.getInputStream());
				}
				work(tasks, factory);
			} catch (IOException e1) {
				LogContainer.setLog("文件路径["+param.getPath()+"]解析错误，请检查是否符合ant表达式");
				e1.printStackTrace();
			}catch (Exception e2) {
				LogContainer.setLog("出现异常："+e2.getMessage());
			}
			long et = System.currentTimeMillis();
			LogContainer.setLog("耗时："+(et -st)/1000);
		} catch (InterruptedException e1) {
			LogContainer.setLog("其他用户正在加工。");
			e1.printStackTrace();
		}catch (ConfigNotFoundException e) {
			LogContainer.setLog("导入的配置信息不全-》"+e.getMessage());
			e.printStackTrace();
		}finally{
			lock.unlock();
			LogContainer.setLog("END->导入结束");
		}
		
	}
	
	private Factory<String> prepareFactory(ImportBaseParam param){
		Import im = prepaterImport(param.getImid());
		BasicDataSource ds = prepareDataSource(param.getImid());
		DefaultInputConfig cin=prepareConfigIn(param.getImid());
		cin.setPolicy(im.getPolicy());
//		cout.setPolicy(im.getPolicy());
		BatchContext context = new BatchContext();
		context.setDataSource(ds);
		context.setConfigIn(cin);
//		context.setConfigOut(cout);
		context.setType(param.getType());
		ExecutorService poolIn = prepareInPool(param.getImid());
		ExecutorService poolOut = prepareOutPool(param.getImid());
		Factory<String> factory =new Factory<String>(poolIn, poolOut,context);
		return factory;
	}
	
	private Import prepaterImport(Integer id){
		return mapper.selectOne(id);
	}
	
	private void work(List<InputStream> list,Factory<String> factory){
		try {
			CountDownLatch latch = new CountDownLatch(list.size());
			int i = 0;
			for (InputStream in : list) {
				LogContainer.setLog("解析数据第"+(++i)+"个文件");
				WorkerIn wi = new WorkerIn();
				wi.setIn(in);
				wi.setLatch(latch);
				factory.submitIn(wi);
			}
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			factory.getPoolIn().shutdown();
			factory.getPoolOut().shutdown();
			DataSource ds= factory.getContext().getDataSource();
			try {
				ds.getClass().getMethod("close").invoke(ds);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	
	private DefaultInputConfig prepareConfigIn(Integer imid){
		ConfigIn in = inMapper.selectOneByImid(imid);
		if(in == null){
			throw new ConfigNotFoundException("未发现匹配的解析配置信息");
		}
		return transfer(in);
	}
	private BasicDataSource prepareDataSource(Integer imid){
		Db db = dbMapper.selectOneByImid(imid);
		if(db ==null ){
			throw new ConfigNotFoundException("未发现匹配的数据库连接池信息");
		}
		return transfer(db);
	}
//	private DefaultOutConfig prepareConfigOut(Integer imid){
//		ConfigOut out = outMapper.selectOneByImid(imid);
//		if(out == null){
//			throw new ConfigNotFoundException("未发现匹配的导入配置信息");
//		}
//		return transfer(out);
//	}
	
	private ExecutorService prepareInPool(Integer imid){
		ThreadModel thin = thMapper.selectOneInByImid(imid);
		if(thin == null){
			throw new ConfigNotFoundException("未发现匹配的解析配置线程池信息");
		}
		LinkedBlockingQueue<Runnable> queueIn= null;
		if(thin.getQueuesize() != null ){
			queueIn = new LinkedBlockingQueue<Runnable>(thin.getQueuesize());
		}else{
			queueIn = new LinkedBlockingQueue<Runnable>();
		}
		ExecutorService poolIn = 
				new ThreadPoolExecutor(thin.getCore(), thin.getMax(), thin.getKeepalive(), TimeUnit.SECONDS, queueIn,
						new ThreadPoolExecutor.CallerRunsPolicy());
		return poolIn;
	}
	private ExecutorService prepareOutPool(Integer imid){
		ThreadModel thout = thMapper.selectOneInByImid(imid);
		if(thout == null){
			throw new ConfigNotFoundException("未发现匹配的导入配置线程池信息");
		}
		LinkedBlockingQueue<Runnable> queueOut= null;
		if(thout.getQueuesize() != null ){
			queueOut = new LinkedBlockingQueue<Runnable>(thout.getQueuesize());
		}else{
			queueOut = new LinkedBlockingQueue<Runnable>();
		}
		ExecutorService poolOut = 
				new ThreadPoolExecutor(thout.getCore(), thout.getMax(), thout.getKeepalive(), TimeUnit.SECONDS, queueOut,
						new ThreadPoolExecutor.CallerRunsPolicy());
		return poolOut;
	}
	private DefaultInputConfig transfer(ConfigIn in){
		DefaultInputConfig cin = new DefaultInputConfig();
		cin.setBlockCount(in.getBlockcount());
		cin.setType(in.getType());
		cin.setSkip(in.getSkip());
		cin.setTable(in.getTable());
		cin.setStore_type(in.getStore_type());
		List<InElement> list = in.getElements();
		List<ReadElement> listt = new ArrayList<ReadElement>();
		for (InElement e : list) {
			ReadElement r = new ReadElement();
			r.setIndex(e.getIndex());
			r.setKey(e.getKey());
			r.setValue(e.getValue());
			r.setType(e.getType());
			List<com.data.mvc.model.Filter> filter_origin = e.getFilters();
			List<Filter> filters = new ArrayList<Filter>();
			for (com.data.mvc.model.Filter f : filter_origin) {
				if("reg".equals(f.getType())){//正则过滤器
					filters.add(new FilterRegExp(f.getPattern()));
				}else if("self".equals(f.getType())){
					try {
						Filter ff= (Filter) Class.forName(f.getClazz()).newInstance();
						filters.add(ff);
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
				}
			}
			r.setFilters(filters);
			listt.add(r);
		}
		cin.setElements(listt);
		return cin;
	}
//	private DefaultOutConfig transfer(ConfigOut out){
//		DefaultOutConfig cout = new DefaultOutConfig();
//		cout.setTable(out.getTable());
//		cout.setType(out.getType());
//		List<OutElement> list = out.getElements();
//		List<SqlColumn> listt = new ArrayList<SqlColumn>();
//		for (OutElement e : list) {
//			SqlColumn r = new SqlColumn();
//			r.setName(e.getName());
//			r.setType(e.getType());
//			listt.add(r);
//		}
//		cout.setColumn(listt);
//		return cout;
//	}
	
	private BasicDataSource transfer(Db db){
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl(db.getUrl());
		ds.setUsername(db.getUsername());
		ds.setPassword(db.getPassword());
		ds.setDriverClassName(db.getDriver());
		ds.setMaxActive(db.getMax());
		ds.setInitialSize(db.getInit());
		return ds;
	}


	@Override
	public void importFtp(ImportFromFtpParam param) {
		UtilFtp ftp =new UtilFtp();
		try {
			if(lock.tryLock(15, TimeUnit.SECONDS)){
				LogContainer.setLog("成功获取导入锁，开始初始化");
				Factory<String> factory =prepareFactory(param);
				LogContainer.setLog("初始化数据完成，开始从ftp导入数据");
				long st=System.currentTimeMillis();
				ftp.setHost(param.getHost());
				ftp.setPassword(param.getPassword());
				ftp.setPort(param.getPort());
				ftp.setUsername(param.getUsername());
				List<InputStream> tasks = ftp.getStream(param.getTarget());
				LogContainer.setLog("在["+param.getTarget()+"]路径下文件数量为："+tasks.size());
				work(tasks, factory);
				long et = System.currentTimeMillis();
				LogContainer.setLog("耗时："+(et -st)/1000);
			}
		} catch (InterruptedException e1) {
			LogContainer.setLog("其他用户正在加工。");
			e1.printStackTrace();
		}finally{
			lock.unlock();
			LogContainer.setLog("导入结束");
			ftp.close();
		}
	}

	@Override
	public void start(ImportFromLocalPathParam param) {
		new Thread(new ImportFromLocalPathThread(param)).start();
	}

	@Override
	public void start(ImportFromFtpParam param) {
		new Thread(new ImportFromFtpThread(param)).start();
		
	}

}
