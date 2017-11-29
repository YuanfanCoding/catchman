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
		// ����httpclient����
		CloseableHttpClient client = HttpClients.createDefault();
		// װ�����
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("name", name));
		nvps.add(new BasicNameValuePair("password", password));
		nvps.add(new BasicNameValuePair("mac", mac));
		// ���ò��������������
		HttpGet httpGet = new HttpGet(Constant.GETUSER + "?" + EntityUtils.toString(new UrlEncodedFormEntity(nvps, "utf-8")));

		// ����header��Ϣ
		// ָ������ͷ��Content-type������User-Agent��
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// ִ��������������õ������ͬ��������
		CloseableHttpResponse response = client.execute(httpGet);
		// ��ȡ���ʵ��
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// ��ָ������ת�����ʵ��ΪString����
			body = EntityUtils.toString(entity, "utf-8");
			Gson gson = new Gson();
			ui = gson.fromJson(body, Userinfo.class);
		}
		EntityUtils.consume(entity);
		// �ͷ�����
		response.close();
		return ui;
	}

	public static boolean addUser(Userinfo ui) throws ParseException, IOException {

		String body = "";
		// ����httpclient����
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(Constant.ADDUSER);
		StringEntity entitys = new StringEntity(new Gson().toJson(ui),"utf-8"); //��������������� 
		entitys.setContentEncoding("UTF-8"); 
		entitys.setContentType("application/json");
		httpPost.setEntity(entitys);

		// ����header��Ϣ
		// ָ������ͷ��Content-type������User-Agent��
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// ִ��������������õ������ͬ��������
		CloseableHttpResponse response = client.execute(httpPost);
		// ��ȡ���ʵ��
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// ��ָ������ת�����ʵ��ΪString����
			body = EntityUtils.toString(entity, "utf-8");
		}
		EntityUtils.consume(entity);
		// �ͷ�����
		response.close();
		if (body != null)
			return true;

		return false;
	}

	public static String getVersion() throws ParseException, IOException{
		String body = "";
		CloseableHttpClient client = HttpClients.createDefault();
		// ���ò��������������
		HttpGet httpGet = new HttpGet(Constant.GETVERSION);
		// ����header��Ϣ
		// ָ������ͷ��Content-type������User-Agent��
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// ִ��������������õ������ͬ��������
		CloseableHttpResponse response = client.execute(httpGet);
		// ��ȡ���ʵ��
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// ��ָ������ת�����ʵ��ΪString����
			body = EntityUtils.toString(entity, "utf-8");
		}
		EntityUtils.consume(entity);
		// �ͷ�����
		response.close();
		return body;
	}

	/**
	 * 
	 * @param type  0Ϊ�˳�ʱ��update
	 */
	public static boolean updateUser(int type) {  
		Userinfo ui = new Userinfo(Constant.name, Constant.password, String.valueOf(Constant.areadycatchnum),Constant.MACADDDRESS);
		// TODO Auto-generated method stub
		String body = "";
		// ����httpclient����
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(Constant.UPDATEUSER);
		StringEntity entitys = new StringEntity(new Gson().toJson(ui), "utf-8"); // ���������������
		entitys.setContentEncoding("UTF-8");
		entitys.setContentType("application/json");
		httpPost.setEntity(entitys);

		// ����header��Ϣ
		// ָ������ͷ��Content-type������User-Agent��
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// ִ��������������õ������ͬ��������
		CloseableHttpResponse response;
		try {
			response = client.execute(httpPost);

			// ��ȡ���ʵ��
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// ��ָ������ת�����ʵ��ΪString����
				body = EntityUtils.toString(entity, "utf-8");
				
			}

			EntityUtils.consume(entity);

			// �ͷ�����
			response.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
