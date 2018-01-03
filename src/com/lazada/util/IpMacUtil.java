package com.lazada.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class IpMacUtil {
	
	public static void  main(String args[]) {
		System.out.println(getMACAddress());
	}
	
	 //��ȡMAC��ַ�ķ���  
    public static String getMACAddress(){  
    	
    	
		try {
			InetAddress ia = InetAddress.getLocalHost();//��ȡ����IP����  
			byte[] mac= NetworkInterface.getByInetAddress(ia).getHardwareAddress(); //�������ӿڶ��󣨼������������õ�mac��ַ��mac��ַ������һ��byte�����С�  
			//��������ǰ�mac��ַƴװ��String  
	        StringBuffer sb = new StringBuffer();  
	          
	        for(int i=0;i<mac.length;i++){  
	            if(i!=0){  
	                sb.append("-");  
	            }  
	            //mac[i] & 0xFF ��Ϊ�˰�byteת��Ϊ������  
	            String s = Integer.toHexString(mac[i] & 0xFF);  
	            sb.append(s.length()==1?0+s:s);  
	        }  
	        //���ַ�������Сд��ĸ��Ϊ��д��Ϊ�����mac��ַ������  
	        return sb.toString().toUpperCase();  
		} catch (UnknownHostException | SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }     
      return "";
          
    }  
}
