package com.data.im.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CommonUtil {
	/**
	 * 
	 * @param data
	 * @param type
	 * @return 
	 * 	types
	     1 JDBC Type           Java Type  
	     
		 2 CHAR                String  
		 3 VARCHAR             String  
		 4 LONGVARCHAR         String  
		 5 NUMERIC             java.math.BigDecimal  
		 6 DECIMAL             java.math.BigDecimal  
		 7 BIT                 boolean  
		 8 BOOLEAN             boolean  
		 9 TINYINT             byte  
		10 SMALLINT            short  
		11 INTEGER             INTEGER  
		12 BIGINT              long  
		13 REAL                float  
		14 FLOAT               double  
		15 DOUBLE              double  
		16 BINARY              byte[]  
		17 VARBINARY           byte[]  
		18 LONGVARBINARY       byte[]  
		19 DATE                java.sql.Date  
		20 TIME                java.sql.Time  
		21 TIMESTAMP           java.sql.Timestamp  
		22 CLOB                Clob  
		23 BLOB                Blob  
		24 ARRAY               Array  
		25 DISTINCT            mapping of underlying type  
		26 STRUCT              Struct  
		27 REF                 Ref  
		28 DATALINK            java.net.URL[color=red][/color] 
	 */
	public static Object convertToJdbcType(Object obj,String type,String pattern){
		SimpleDateFormat sdf = null;
		Date date = null;
		String data = obj.toString();
		switch (type) {
		case "CHAR":
			return data;
		case "VARCHAR":
			return data;
		case "LONGVARCHAR":
			return data;
		case "NUMERIC":
			return new BigDecimal(data);
		case "DECIMAL":
			return new BigDecimal(data);
		case "BIT":
			return new Boolean(data);
		case "BOOLEAN":
			return new Boolean(data);
		case "TINYINT":
			return new Byte(data);
		case "SMALLINT":
			return new Short(data);
		case "INTEGER":
			return new Integer(data);
		case "BIGINT":
			return new Long(data);
		case "REAL":
			return new Float(data);
		case "FLOAT":
			return new Double(data);
		case "DOUBLE":
			return new Double(data);
		case "BINARY":
			return  data.getBytes();
		case "VARBINARY":
			return  data.getBytes();
		case "LONGVARBINARY":
			return  data.getBytes();
		case "DATE":
			sdf = new SimpleDateFormat(pattern);
			try {
				date = sdf.parse(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return  new java.sql.Date(date.getTime());
		case "TIME":
			sdf = new SimpleDateFormat(pattern);
			try {
				date = sdf.parse(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return  new java.sql.Time(date.getTime());
		case "TIMESTAMP":
			sdf = new SimpleDateFormat(pattern);
			try {
				date = sdf.parse(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return  new java.sql.Timestamp(date.getTime());
		default:
			return data;
		}
	}
}
