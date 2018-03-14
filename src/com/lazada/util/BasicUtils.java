package com.lazada.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.google.gson.Gson;
import com.lazada.handler.ConnectImpl;
import com.lazada.model.Constant;
import com.lazada.model.json.detail.ItemListElement;
import com.lazada.model.json.detail.JsonRootBean;
import com.lazada.model.json.firstlevel.FirstLevelJsonRootBean;
import com.lazada.model.json.firstlevel.ListItems;
import com.lazada.model.product.BaseInfo;
import com.lazada.model.product.FinalInfo;

import jxl.JXLException;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class BasicUtils {

	/*
	 * @ workbook ���빤���� ��ʼ��sheet�ĵ�һ��
	 */
	public static WritableWorkbook initWorkbook(String savepath)
			throws IOException, RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(new File(savepath));
		int exlRow = 0;
		int index = 0;
		WritableSheet sheet = workbook.createSheet("web_data", 0);
		sheet.addCell(new Label(index++, exlRow, "����"));
		sheet.addCell(new Label(index++, exlRow, "��Ŀ"));
		sheet.addCell(new Label(index++, exlRow, "��������"));
		sheet.addCell(new Label(index++, exlRow, "��������"));
		sheet.addCell(new Label(index++, exlRow, "����"));
		sheet.addCell(new Label(index++, exlRow, "�������"));
		sheet.addCell(new Label(index++, exlRow, "��Ƶ����"));
		sheet.addCell(new Label(index++, exlRow, "����"));
		sheet.addCell(new Label(index++, exlRow, "�ؼ�"));
		sheet.addCell(new Label(index++, exlRow, "SKU(Lazada)"));
		sheet.addCell(new Label(index++, exlRow, "��װ����"));
		sheet.addCell(new Label(index++, exlRow, "��Ʒ��������"));
		sheet.addCell(new Label(index++, exlRow, "����"));
		sheet.addCell(new Label(index++, exlRow, "����"));
		sheet.addCell(new Label(index++, exlRow, "����"));
		sheet.addCell(new Label(index++, exlRow, "ͼƬ1"));
		sheet.addCell(new Label(index++, exlRow, "ͼƬ2"));
		sheet.addCell(new Label(index++, exlRow, "ͼƬ3"));
		sheet.addCell(new Label(index++, exlRow, "ͼƬ4"));
		sheet.addCell(new Label(index++, exlRow, "ͼƬ5"));
		sheet.addCell(new Label(index++, exlRow, "ͼƬ6"));
		sheet.addCell(new Label(index++, exlRow, "ͼƬ7"));
		sheet.addCell(new Label(index++, exlRow, "ͼƬ8"));
		sheet.addCell(new Label(index++, exlRow, "�ߴ�"));
		sheet.addCell(new Label(index++, exlRow, "����"));
		sheet.addCell(new Label(index++, exlRow, "Ʒ��"));
		sheet.addCell(new Label(index++, exlRow, "����"));
		sheet.addCell(new Label(index++, exlRow, "Compatibility by Model(Lazada)"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ1"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ2"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ3"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ4"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ5"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ6"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ7"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ8"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ9"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ10"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ11"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ12"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ13"));
		sheet.addCell(new Label(index++, exlRow, "Сͼ14"));
		sheet.addCell(new Label(index, exlRow++, "Сͼ15"));

		return workbook;
	}

	public static void handleOneItem(WritableSheet sheet, int exlRow, BaseInfo info)
			throws RowsExceededException, WriteException {
		int index = 0;
		sheet.addCell(new Label(index++, exlRow, info.getName()));
		sheet.addCell(new Label(index++, exlRow, info.getCategory()));
		sheet.addCell(new Label(index++, exlRow, info.getDescription()));
		sheet.addCell(new Label(index++, exlRow, info.getDescriptioncode()));
		sheet.addCell(new Label(index++, exlRow, info.getShort_description()));
		sheet.addCell(new Label(index++, exlRow, info.getShort_descriptioncode()));
		sheet.addCell(new Label(index++, exlRow, info.getVideoLink()));
		sheet.addCell(new Label(index++, exlRow, info.getPrice()));
		sheet.addCell(new Label(index++, exlRow, info.getSpecial_price()));
		sheet.addCell(new Label(index++, exlRow, info.getSellersku()));
		sheet.addCell(new Label(index++, exlRow, info.getPackage_content()));
		sheet.addCell(new Label(index++, exlRow, info.getLink()));
		sheet.addCell(new Label(index++, exlRow, info.getComment()));
		sheet.addCell(new Label(index++, exlRow, info.getDiscuss()));
		sheet.addCell(new Label(index++, exlRow, info.getSalenum()));
		sheet.addCell(new Label(index++, exlRow, info.getMainimage()));
		sheet.addCell(new Label(index++, exlRow, info.getImage2()));
		sheet.addCell(new Label(index++, exlRow, info.getImage3()));
		sheet.addCell(new Label(index++, exlRow, info.getImage4()));
		sheet.addCell(new Label(index++, exlRow, info.getImage5()));
		sheet.addCell(new Label(index++, exlRow, info.getImage6()));
		sheet.addCell(new Label(index++, exlRow, info.getImage7()));
		sheet.addCell(new Label(index++, exlRow, info.getImage8()));
		sheet.addCell(new Label(index++, exlRow, info.getSize()));
		sheet.addCell(new Label(index++, exlRow, info.getStore()));
		sheet.addCell(new Label(index++, exlRow, info.getBrand()));
		sheet.addCell(new Label(index++, exlRow, info.getLocation()));
		sheet.addCell(new Label(index++, exlRow, info.getCompatibility()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage1()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage2()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage3()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage4()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage5()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage6()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage7()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage8()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage9()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage10()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage11()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage12()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage13()));
		sheet.addCell(new Label(index++, exlRow, info.getSmallimage14()));
		sheet.addCell(new Label(index, exlRow, info.getSmallimage15()));
	}

	public static Document getDoc(String url) {

		// CloseableHttpClient httpclient = HttpClients.createDefault();
		// CookieStore cookieStore = new BasicCookieStore();
		long startTime = System.currentTimeMillis();
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(4000)// ��ȡ��ʱ readtime-out
				.setConnectTimeout(4000)// ���ӳ�ʱ
				.setConnectionRequestTimeout(4000).build();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		Document doc = null;
		try {
			HttpGet httpget = new HttpGet(url);
			// RequestConfig requestConfig =
			// RequestConfig.custom().setSocketTimeout(6*1000).build();
			httpget.setConfig(defaultRequestConfig);
			httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
			// if(url.contains("page=1")) {
			// Header[] header=httpget.getAllHeaders();
			// for(int i=0;i<header.length;i++) {
			// System.out.println(header[i].toString());
			// }
			// }
			// BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
			// "B960BA73258DB81B7204FEE3A5A78CA3");
			// cookie.setVersion(0);
			// cookie.setDomain("/pms/"); //���÷�Χ
			// cookie.setPath("/");
			// cookieStore.addCookie(cookie);
			CloseableHttpResponse response = httpclient.execute(httpget);

			long endTime = System.currentTimeMillis();
			System.out.println("�˴������ܺ�ʱ�� " + (endTime - startTime));
			String web = "";
			try {
				// ��ȡ��Ӧʵ��
				HttpEntity entity = response.getEntity();
				// ��ӡ��Ӧ״̬
				if (entity != null) {
					web = EntityUtils.toString(entity, "UTF-8");
					doc = Jsoup.parse(web);
					return doc;

				}
			} finally {
				response.close();
				httpclient.close();
			}
		} catch (org.apache.http.NoHttpResponseException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (java.net.UnknownHostException u) {
			u.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static void setCookieStore(HttpResponse httpResponse) {
		System.out.println("----setCookieStore");
		BasicCookieStore cookieStore = new BasicCookieStore();
		// JSESSIONID
		String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
		String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
		System.out.println("JSESSIONID:" + JSESSIONID);
		// �½�һ��Cookie
		BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
		cookie.setVersion(0);
		cookie.setDomain("127.0.0.1");
		cookie.setPath("/CwlProClient");
		// cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
		// cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
		// cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
		// cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
		cookieStore.addCookie(cookie);
	}

	public static void getCookies(String url) {

		long starttime = System.currentTimeMillis();
		System.setProperty("phantomjs.binary.path",
				"C:\\Users\\Administrator\\Desktop\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		WebDriver driver = new PhantomJSDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		// String
		// url="https://shopee.com.my/search/?keyword=women+shoes&page=3&subcategory";
		driver.get(url);

		long endtime = System.currentTimeMillis();
		System.out.println("��ʼ��ʱ�䣺" + (endtime - starttime));
		// WebElement webElement = driver.findElement(By.id("video-player"));
		// System.out.println(driver.getPageSource());
		Set<Cookie> cookies = driver.manage().getCookies();
		Iterator iterator = cookies.iterator();
		while (iterator.hasNext()) {
			Cookie cookie = (Cookie) iterator.next();
			if (cookie.getName().equals("csrftoken"))
				Constant.shopeecsrftoken = cookie.getValue();
			Constant.shopeecookies += cookie.getName() + "=" + cookie.getValue() + ";";
			System.out.println(cookie.getName());
			System.out.println(cookie.getValue());
		}

	}

	public static String postData(String url, String cookieString, StringEntity json, String refer, String xcsrftoken) {
		HttpPost httppost = new HttpPost(url);
		httppost.addHeader("referer", refer);
		httppost.addHeader("x-csrftoken", xcsrftoken);
		httppost.setHeader("cookie", cookieString);
		System.out.println(url);
		System.out.println(xcsrftoken);
		System.out.println(cookieString);
		// entity.setContentType("text");
		httppost.setEntity(json);

		CloseableHttpClient httpclient = HttpClients.createDefault();

		CloseableHttpResponse response;

		String web = "";
		try {
			response = httpclient.execute(httppost);
			// ��ȡ��Ӧʵ��
			HttpEntity entity = response.getEntity();
			// ��ӡ��Ӧ״̬
			if (entity != null) {
				web = EntityUtils.toString(entity, "UTF-8");
				System.out.println(web);
				// System.out.println("{"+web.substring(web.indexOf("items")-1).replace("items",
				// "item_shop_ids"));

			}
			response.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// �ر�����,�ͷ���Դ
			try {

				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return web;

	}

	
	public static String getItemJsonForShopee(String url) {
		
		long startTime = System.currentTimeMillis();
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(4000)// ��ȡ��ʱ readtime-out
				.setConnectTimeout(4000)// ���ӳ�ʱ
				.setConnectionRequestTimeout(4000).build();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String web = "";
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(defaultRequestConfig);
			httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
			CloseableHttpResponse response = httpclient.execute(httpget);

			long endTime = System.currentTimeMillis();
			System.out.println("�˴������ܺ�ʱ�� " + (endTime - startTime));
			
			try {
				// ��ȡ��Ӧʵ��
				HttpEntity entity = response.getEntity();
				// ��ӡ��Ӧ״̬
				if (entity != null) {
					web = EntityUtils.toString(entity, "UTF-8");
					return web;
				}
			} finally {
				response.close();
				httpclient.close();
			}
		} catch (org.apache.http.NoHttpResponseException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (java.net.UnknownHostException u) {
			u.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return web;

	}
	
}
