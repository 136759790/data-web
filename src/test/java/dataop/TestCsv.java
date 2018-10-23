package dataop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

public class TestCsv {
	@Test
	public void createCsvFile(){
		FileWriter writer = null;
		CSVPrinter printer = null;
		String[] header = {"id","name","age","address","sex"};
		CSVFormat format = CSVFormat.DEFAULT.withRecordSeparator("\n");
		try {
			writer = new FileWriter(new File("d:/data/csv/person.csv"));
			printer = new CSVPrinter(writer, format);
			printer.printRecord(header);
			for (int i = 0; i < 1000000; i++) {
				String[] record = {i+"","name"+i,i+"","address"+i,"sex"+i};
				printer.printRecord(record);
			}
		} catch (Exception e) {
			
		}finally{
			try {
				printer.flush();
				printer.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	@Test
	public void testRead(){
		Reader in = null;
		try {
			in = new FileReader("d:/data/csv/person.csv");
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			for (CSVRecord record : records) {
//				System.out.println("123");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
