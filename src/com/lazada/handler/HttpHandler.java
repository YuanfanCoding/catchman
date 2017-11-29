package com.lazada.handler;

import java.awt.EventQueue;
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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.lazada.model.Constant;
import com.lazada.model.user.Userinfo;
import com.lazada.ui.Login;
import com.lazada.util.IpMacUtil;

public class HttpHandler {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Constant.name="1016127734";
		Constant.password="123456";
		Constant.areadycatchnum=36;
		updateUser(0);
	}

	public static Userinfo getUser(String name, String password,String mac) throws ParseException, IOException {
		Userinfo ui = null;
		String body = "";
		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 装填参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("name", name));
		nvps.add(new BasicNameValuePair("password", password));
		nvps.add(new BasicNameValuePair("mac", mac));
		// 设置参数到请求对象中
		HttpGet httpGet = new HttpGet(Constant.GETUSER + "?" + EntityUtils.toString(new UrlEncodedFormEntity(nvps, "utf-8")));

		// 设置header信息
		// 指定报文头【Content-type】、【User-Agent】
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpGet);
		// 获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, "utf-8");
			Gson gson = new Gson();
			ui = gson.fromJson(body, Userinfo.class);
		}
		EntityUtils.consume(entity);
		// 释放链接
		response.close();
		return ui;
	}

	public static boolean addUser(Userinfo ui) throws ParseException, IOException {

		String body = "";
		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(Constant.ADDUSER);
		StringEntity entitys = new StringEntity(new Gson().toJson(ui),"utf-8"); //解决中文乱码问题 
		entitys.setContentEncoding("UTF-8"); 
		entitys.setContentType("application/json");
		httpPost.setEntity(entitys);

		// 设置header信息
		// 指定报文头【Content-type】、【User-Agent】
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		// 获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, "utf-8");
		}
		EntityUtils.consume(entity);
		// 释放链接
		response.close();
		if (body != null)
			return true;

		return false;
	}

	public static String getVersion() throws ParseException, IOException{
		String body = "";
		CloseableHttpClient client = HttpClients.createDefault();
		// 设置参数到请求对象中
		HttpGet httpGet = new HttpGet(Constant.GETVERSION);
		// 设置header信息
		// 指定报文头【Content-type】、【User-Agent】
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpGet);
		// 获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, "utf-8");
		}
		EntityUtils.consume(entity);
		// 释放链接
		response.close();
		return body;
	}

	/**
	 * 
	 * @param type  0为退出时的update
	 */
	public static boolean updateUser(int type) {  
		Userinfo ui = new Userinfo(Constant.name, Constant.password, String.valueOf(Constant.areadycatchnum),Constant.MACADDDRESS);
		// TODO Auto-generated method stub
		String body = "";
		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(Constant.UPDATEUSER);
		StringEntity entitys = new StringEntity(new Gson().toJson(ui), "utf-8"); // 解决中文乱码问题
		entitys.setContentEncoding("UTF-8");
		entitys.setContentType("application/json");
		httpPost.setEntity(entitys);

		// 设置header信息
		// 指定报文头【Content-type】、【User-Agent】
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response;
		try {
			response = client.execute(httpPost);

			// 获取结果实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// 按指定编码转换结果实体为String类型
				body = EntityUtils.toString(entity, "utf-8");
				
			}

			EntityUtils.consume(entity);

			// 释放链接
			response.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
