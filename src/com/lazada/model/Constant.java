package com.lazada.model;

import com.lazada.util.IpMacUtil;

public class Constant { 
 
	public static String name="";
	public static String password="";
	public static int areadycatchnum=0;
	public static String totalnum="0";
	public static String MACADDDRESS=IpMacUtil.getMACAddress();
	
	public  static final  String WEBSITE="http://ylfcoding.cn";
	public  static final  String HTTPADDR="http://ylfcoding.cn";//http://localhost:8080
	public  static final  String ADVERTISEMENT="http://ylfcoding.cn";
	public final static String VERSION="V2.0";
	public final static String SOFTWARENAME="LAZADA�����ռ���";
	
	//http����
	public final static String GETUSER = HTTPADDR+"/user";
	public final static String ADDUSER = HTTPADDR+"/user";
	public final static String GETVERSION = HTTPADDR+"/version";
	public final static String UPDATEUSER = HTTPADDR+"/user/update";
	public final static String GETRECORD = HTTPADDR+"/isExisTestrecord";
	public final static String SAVERECORD = HTTPADDR+"/saveTestrecord";
	
	public static final String KEYWORDCATCH="KEYWORDCATCH";
	public static final String STORECATCH="STORECATCH";
	public static final String PRODUCTCATCH="PRODUCTCATCH";
	
	public static final String LAZADA="LAZADA";
	public static final String SHOPEE="SHOPEE";
	public static final String AMAZON="AMAZON";
	public static final String ALIEXPRESS="ALIEXPRESS";
	
	public final static String LAZADAKEYWORDSIDE="https://www.lazada.com.my/catalog/?sort=popularity&q="; 
	public final static String ALIEXPRESSKEYWORDSIDE="https://www.aliexpress.com/wholesale?SearchText=";
	
	public final static String SHOPEEITEMSSITE="https://shopee.com.my/api/v1/search_items/?by=pop&order=desc&limit=50&keyword="; 
	
	public static final String TESTUSER="user";
	public static final String TESTPASSWORD="123456";
	
}
