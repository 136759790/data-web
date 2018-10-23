package dataop;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


public class TestExcel {
	
	
	@Test
	public void testRead(){
		OPCPackage pkg;
		SheetHandler handler = null;
		try {
			pkg = OPCPackage.open("D:/data/excel/person.xlsx");
			XSSFReader reader = new XSSFReader(pkg);
			SharedStringsTable sst = reader.getSharedStringsTable();
			handler = new SheetHandler(sst);
			XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
			parser.setContentHandler(handler);
			Iterator<InputStream> sheets = reader.getSheetsData();
			while (sheets.hasNext()) {
				InputStream sheet = (InputStream) sheets.next();
				InputSource sources= new InputSource(sheet);
				parser.parse(sources);
				sheet.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(handler.getRowList());
	}
}

class SheetHandler extends DefaultHandler{
	private SharedStringsTable sst;
	private String lastContents;
	private boolean nextIsString;
	private List<String> rowList = new ArrayList<String>();
	private int curRow= 0;
	private int curCol= 0;
	private boolean dateFlag;
	private boolean numberFlag;
	private boolean isTElement;

	public SheetHandler(SharedStringsTable sst) {
		super();
		this.sst = sst;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(qName.equals("c")){//=>cell
			String cellType=attributes.getValue("t");
			if("s".equals(cellType)){
				nextIsString = true;
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
        if ("t".equals(qName)) {  
            isTElement = true;  
        } else {  
            isTElement = false;  
        }  
  
        // 置空  
        lastContents = ""; 
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if(nextIsString){
			try {
				int idx = Integer.parseInt(lastContents);  
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString(); 
			} catch (Exception e) {
//				e.printStackTrace();
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
//                getRows(sheetIndex + 1, curRow, rowList);  
            	System.out.println(rowList);
                rowList.clear();  
                curRow++;  
                curCol = 0;  
            }  
        }  
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		lastContents += new String(ch, start, length);  
	}

	public List<String> getRowList() {
		return rowList;
	}

	public void setRowList(List<String> rowList) {
		this.rowList = rowList;
	}
	
}
