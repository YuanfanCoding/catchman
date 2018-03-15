package com.lazada.model;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.HashMap;

import org.openqa.selenium.Cookie;

import com.gargoylesoftware.htmlunit.javascript.host.Set;
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
	public final static String SOFTWARENAME="袋鼠LAZADA采集软件";
	
	//http请求
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
	public final static String ALIEXPRESSKEYWORDSIDE="https://www.aliexpress.com/wholesale?g=y&SearchText=";
	
	public final static String SHOPEEITEMSSITE="https://shopee.com.my/api/v1/search_items/?by=pop&order=desc&limit=50&keyword="; 
	public final static String SHOPEEITEMSSITEBYSHOPID="https://shopee.com.my/api/v1/search_items/?by=pop&order=desc&limit=30&page_type=shop"; 
	public final static String SHOPEEKEYWORDSITE="https://shopee.com.my/search/?subcategory&keyword=";
	public final static String SHOPEEPOSTSITE="https://shopee.com.my/api/v1/items/";
	public final static String SHOPEEITEMSITE="https://shopee.com.my/api/v1/item_detail/?";
	public final static String SHOPEEPICSITE="https://cfshopeecommy-a.akamaihd.net/file/";
	public final static String SHOPEECATEGORYSITE="https://shopee.com.my/api/v1/category_list/";
	public final static String SHOPEESHIDBYNAMESITE="https://shopee.com.my/api/v1/shop_ids_by_username/";
	
	
	
	public final static String LAZADASIDE="www.lazada.com.my";
	public final static String SHOPEESIDE="https://shopee.com.my/";
	public final static String ALIEXPRESSSIDE="www.aliexpress.com";
	
	public static final String TESTUSER="user";
	public static final String TESTPASSWORD="123456";
	
	
	public static  HashMap<Integer,String> calMap=null;//shopee分类哈希map
	
	public static String shopeecookies="";
	
	public static String shopeecsrftoken="";
	
	public static String WORKPATH="";
	
	
}
