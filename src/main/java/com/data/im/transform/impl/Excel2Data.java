package com.data.im.transform.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.data.im.config.InputConfig;
import com.data.im.config.impl.ReadElement;
import com.data.im.entity.DataBlock;
import com.data.im.util.CommonUtil;

public class Excel2Data extends InputStream2Data{
	private Log log = LogFactory.getLog(Excel2Data.class);
	@Override
	public void process(InputConfig configIn, InputStream in,DataBlock block) {
		ExcelReader reader = new ExcelReader(block,configIn.getSkip()) {
			@Override
			public void transfer(DataBlock block, List<String> rowList) {
				block.add(parseRecord(configIn, rowList));
			}
		};
		try {
			reader.process(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public Map parseRecord(InputConfig config, Object record) {
		if(record instanceof List){
			List<String> data = (List<String>) record;
			Map map = new HashMap();
			List<ReadElement> list =  config.getElements();
			for (ReadElement e : list) {
				map.put(e.getKey(), CommonUtil.convertToJdbcType(data.get(e.getIndex()), e.getType(), e.getPattern()));
			}
			return map;
		}
		return null;
	}  
	

}

abstract class ExcelReader extends DefaultHandler {  
	private DataBlock block;
	private Integer skip;
	public ExcelReader(DataBlock block,Integer skip) {
		super();
		this.block = block;
		this.skip = skip;
	}
	
	// 共享字符串表  
	private SharedStringsTable sst;  
	// 上一次的内容  
	private String lastContents;  
	private boolean nextIsString;  
	
	private int sheetIndex = -1;  
	private List<String> rowList = new ArrayList<String>();  
	
	// 当前行  
	private int curRow = 1;  
	// 当前列  
	private int curCol = 0;  
	// 日期标志  
	private boolean dateFlag;  
	// 数字标志  
	private boolean numberFlag;  
	
	private boolean isTElement;  
	
	private Integer total;
	
	/** 
	 * 遍历工作簿中所有的电子表格 
	 * @param filename 
	 * @throws Exception 
	 */  
	public void process(String filename) throws Exception {  
		OPCPackage pkg = OPCPackage.open(filename);  
		XSSFReader r = new XSSFReader(pkg);  
		SharedStringsTable sst = r.getSharedStringsTable();  
		XMLReader parser = fetchSheetParser(sst);  
		Iterator<InputStream> sheets = r.getSheetsData();  
		while (sheets.hasNext()) {  
			curRow = 0;  
			sheetIndex++;  
			InputStream sheet = sheets.next();  
			InputSource sheetSource = new InputSource(sheet);  
			parser.parse(sheetSource);  
			sheet.close();  
		}  
	}  
	/** 
	 * 遍历工作簿中所有的电子表格 
	 * @param filename 
	 * @throws Exception 
	 */  
	public void process(InputStream in) throws Exception {  
		OPCPackage pkg = OPCPackage.open(in);  
		XSSFReader r = new XSSFReader(pkg);  
		SharedStringsTable sst = r.getSharedStringsTable();  
		XMLReader parser = fetchSheetParser(sst);  
		Iterator<InputStream> sheets = r.getSheetsData();  
		while (sheets.hasNext()) {  
			curRow = 0;  
			sheetIndex++;  
			InputStream sheet = sheets.next();  
			InputSource sheetSource = new InputSource(sheet);  
			parser.parse(sheetSource);  
			sheet.close();  
		}  
	}  
	
	/** 
	 * 只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3 
	 * @param filename 
	 * @param sheetId 
	 * @throws Exception 
	 */  
	public void process(String filename, int sheetId) throws Exception {  
		OPCPackage pkg = OPCPackage.open(filename);  
		XSSFReader r = new XSSFReader(pkg);  
		SharedStringsTable sst = r.getSharedStringsTable();  
		XMLReader parser = fetchSheetParser(sst);  
		// 根据 rId# 或 rSheet# 查找sheet  
		InputStream sheet2 = r.getSheet("rId" + sheetId);  
		sheetIndex++;  
		InputSource sheetSource = new InputSource(sheet2);  
		parser.parse(sheetSource);  
		sheet2.close();  
	}  
	
	public XMLReader fetchSheetParser(SharedStringsTable sst)  
			throws SAXException {  
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");  
		this.sst = sst;  
		parser.setContentHandler(this);  
		return parser;  
	}  
	
	public void startElement(String uri, String localName, String name,  
			Attributes attributes) throws SAXException {  
		if ("dimension".equals(name)) { 
			//获得总计录数 
			String d = attributes.getValue("ref"); 
			this.total =Integer.parseInt((d.substring(d.indexOf(":")+1,d.length()).toUpperCase().replaceAll("[A-Z]", ""))); 
		} 		
		// c => 单元格  
		if ("c".equals(name)) {  
			// 如果下一个元素是 SST 的索引，则将nextIsString标记为true  
			String cellType = attributes.getValue("t");  
			if ("s".equals(cellType)) {  
				nextIsString = true;  
			} else {  
				nextIsString = false;  
			}  
			// 日期格式  
			String cellDateType = attributes.getValue("s");  
			if ("1".equals(cellDateType)) {  
				dateFlag = true;  
			} else {  
				dateFlag = false;  
			}  
			String cellNumberType = attributes.getValue("s");  
			if ("2".equals(cellNumberType)) {  
				numberFlag = true;  
			} else {  
				numberFlag = false;  
			}  
			
		}  
		// 当元素为t时  
		if ("t".equals(name)) {  
			isTElement = true;  
		} else {  
			isTElement = false;  
		}  
		
		// 置空  
		lastContents = "";  
	}  
	
	public void endElement(String uri, String localName, String name)  
			throws SAXException {  
		
//	      System.out.println("endElement: " + localName + ", " + name);  
		
		// 根据SST的索引值的到单元格的真正要存储的字符串  
		// 这时characters()方法可能会被调用多次  
		if (nextIsString) {  
			try {  
				int idx = Integer.parseInt(lastContents);  
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx))  
				.toString();  
			} catch (Exception e) {  
				
			}  
		}  
		// t元素也包含字符串  
		if (isTElement) {  
			String value = lastContents.trim();  
			rowList.add(curCol, value);  
			curCol++;  
			isTElement = false;  
			// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引  
			// 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符  
		} else if ("v".equals(name)) {  
			String value = lastContents.trim();  
			value = value.equals("") ? " " : value;  
			try {  
				// 日期格式处理  
				if (dateFlag) {  
					Date date = HSSFDateUtil.getJavaDate(Double.valueOf(value));  
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
					value = dateFormat.format(date);  
				}  
				// 数字类型处理  
				if (numberFlag) {  
					BigDecimal bd = new BigDecimal(value);  
					value = bd.setScale(3, BigDecimal.ROUND_UP).toString();  
				}  
			} catch (Exception e) {  
				// 转换失败仍用读出来的值  
			}  
			rowList.add(curCol, value);  
			curCol++;  
		} else {  
			// 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法  
			if (name.equals("row")) {  
				curRow++;  
				curCol = 0;  
				System.out.println(curRow);
				if(curRow > skip){
					transfer(block, rowList);
				}
				if(total == curRow){
					block.storeRest();
				}
				rowList.clear();  
			}  
		}  
		
	}  
	public void characters(char[] ch, int start, int length)  
			throws SAXException {  
		// 得到单元格内容的值  
		lastContents += new String(ch, start, length);  
	}  
	public abstract void transfer(DataBlock block,List<String> rowList);
	
}