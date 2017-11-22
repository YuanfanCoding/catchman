package com.lazada.cn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOhandler {

	private static final String fileName = "D:/user.txt";
	
	public static void writeInfo(String info){
		
	try{
		FileWriter writer = new FileWriter(fileName, true);
        writer.write(info);
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
		
    }
	
	public static boolean isValid(String name,String password){
    boolean ispass=false;
	try{
     InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(fileName))); // ����һ������������reader  
     BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
     String line = "";  
     line = br.readLine();  
     
     while (line != null) {  
         String infoarr[]=line.split(" ");
        
         if(infoarr[1].equals(name)&&infoarr[2].equals(password)) {
        	 ispass=true;
        	 break;
         }
         line = br.readLine(); // һ�ζ���һ������  
     }  
		}catch(Exception e){
			System.out.println(e.toString());
		}
	
	return ispass;
	}
	
}