package com.lazada.cn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpHandler {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			isValid("123","123");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Userinfo isValid(String name,String password) throws ParseException, IOException{
		Userinfo ui=null;
		String body = "";
			//����httpclient����
			CloseableHttpClient client = HttpClients.createDefault();
			//����post��ʽ�������
			HttpPost httpPost = new HttpPost("http://ylfcoding.cn/");
			
			//װ�����
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("name",name));
			nvps.add(new BasicNameValuePair("password",password));
			//���ò��������������
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			System.out.println("���������"+nvps.toString());
			
			//����header��Ϣ
			//ָ������ͷ��Content-type������User-Agent��
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			
			//ִ��������������õ������ͬ��������
			CloseableHttpResponse response = client.execute(httpPost);
			//��ȡ���ʵ��
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				//��ָ������ת�����ʵ��ΪString����
				body = EntityUtils.toString(entity, "utf-8");
				System.out.println(body);
			}
			EntityUtils.consume(entity);
			//�ͷ�����
			response.close();
	        return ui;
		}
}
