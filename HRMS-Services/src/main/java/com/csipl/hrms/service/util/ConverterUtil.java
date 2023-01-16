package com.csipl.hrms.service.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConverterUtil {
private static DateFormat DBdateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
private static final SimpleDateFormat df_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

public static BigDecimal getBigDecimal( Object value ) {
   BigDecimal ret = null;
   if( value != null ) {
       if( value instanceof BigDecimal ) {
           ret = (BigDecimal) value;
       } else if( value instanceof String ) {
           ret = new BigDecimal( (String) value );
       } else if( value instanceof BigInteger ) {
           ret = new BigDecimal( (BigInteger) value );
       } else if( value instanceof Number ) {
           ret = new BigDecimal( ((Number)value).doubleValue() );
       } else {
           throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");
       }
   }
   return ret;
}

public static Long getLong( Object value ) { 
Long ret = null;
 if( value != null ) { 
 String stringToConvert = String.valueOf( value );
 ret =  Long.parseLong( stringToConvert );

 }
 return ret;
}

public static String getString( Object value ) { 
String ret = null ;
 if( value != null ) { 
 ret = String.valueOf( value );

 }
 return ret;
}

public static Date getDate( String value ) {
Date date = null ;
 if( value != null ) { 
 date = Date.valueOf(value);
 }
 return date;
}


}
