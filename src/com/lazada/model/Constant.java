package com.lazada.model;

import com.lazada.util.IpMacUtil;

public class Constant {

	public static String name="";
	public static String password="";
	public static int areadycatchnum=0;
	public static String totalnum="0";
	public static String MACADDDRESS=IpMacUtil.getMACAddress();
	
	public  static final  String WEBSITE="http://ylfcoding.cn";
	public  static final  String HTTPADDR="http://ylfcoding.cn";
	public  static final  String ADVERTISEMENT="http://ylfcoding.cn";
	public final static String VERSION="V1.0";
	
	
	//http«Î«Û
	public final static String GETUSER = HTTPADDR+"/V1/user";
	public final static String ADDUSER = HTTPADDR+"/V1/user";
	public final static String GETVERSION = HTTPADDR+"/version";
	public final static String UPDATEUSER = HTTPADDR+"/V1/user/update";
	public final static String GETRECORD = HTTPADDR+"/testrecord";
	
}
