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
			//创建httpclient对象
			CloseableHttpClient client = HttpClients.createDefault();
			//创建post方式请求对象
			HttpPost httpPost = new HttpPost("http://ylfcoding.cn/");
			
			//装填参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("name",name));
			nvps.add(new BasicNameValuePair("password",password));
			//设置参数到请求对象中
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			System.out.println("请求参数："+nvps.toString());
			
			//设置header信息
			//指定报文头【Content-type】、【User-Agent】
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			
			//执行请求操作，并拿到结果（同步阻塞）
			CloseableHttpResponse response = client.execute(httpPost);
			//获取结果实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				//按指定编码转换结果实体为String类型
				body = EntityUtils.toString(entity, "utf-8");
				System.out.println(body);
			}
			EntityUtils.consume(entity);
			//释放链接
			response.close();
	        return ui;
		}
}
