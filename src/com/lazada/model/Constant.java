package com.lazada.model;

import com.lazada.util.IpMacUtil;

public class Constant {

	public static String name="";
	public static String password="";
	public static int areadycatchnum=0;
	public static String totalnum="0";
	public static String MACADDDRESS=IpMacUtil.getMACAddress();
	
	public  static final  String WEBSITE="http://ylfcoding.cn";
	public  static final  String ADVERTISEMENT="http://ylfcoding.cn";
	public final static String VERSION="V1.0";
	
	
	//http«Î«Û
	public final static String GETUSER = "http://localhost:8080/V1/user";
	public final static String ADDUSER = "http://localhost:8080/V1/user";
	public final static String GETVERSION = "http://localhost:8080/version";
	public final static String UPDATEUSER = "http://localhost:8080/V1/user/update";
}
