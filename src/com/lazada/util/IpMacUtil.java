package com.lazada.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class IpMacUtil {
	
	public static void  main(String args[]) {
		System.out.println(getMACAddress());
	}
	
	 //获取MAC地址的方法  
    public static String getMACAddress(){  
    	
    	
		try {
			InetAddress ia = InetAddress.getLocalHost();//获取本地IP对象  
			byte[] mac= NetworkInterface.getByInetAddress(ia).getHardwareAddress(); //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。  
			//下面代码是把mac地址拼装成String  
	        StringBuffer sb = new StringBuffer();  
	          
	        for(int i=0;i<mac.length;i++){  
	            if(i!=0){  
	                sb.append("-");  
	            }  
	            //mac[i] & 0xFF 是为了把byte转化为正整数  
	            String s = Integer.toHexString(mac[i] & 0xFF);  
	            sb.append(s.length()==1?0+s:s);  
	        }  
	        //把字符串所有小写字母改为大写成为正规的mac地址并返回  
	        return sb.toString().toUpperCase();  
		} catch (UnknownHostException | SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }     
      return "";
          
    }  
}
