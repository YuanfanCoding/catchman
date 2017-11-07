package com.lazada.cn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.lazada.json.model.ItemListElement;
import com.lazada.json.model.JsonRootBean;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ConnectImpl {

	public final static String website="http://www.lazada.com.my/catalog/?q="; 
	private String keyword;
	private int startpage;
	private int endpage;
	private String savepath;
	private int exlRow;
	public static void main(String[] args) {
		
		try {
			long startTime=System.currentTimeMillis();   
			new ConnectImpl("women shoes",1,3,"C:\\Users\\Administrator\\Desktop\\123.xls").startCatching();
			long endTime=System.currentTimeMillis();
			System.out.println("程序运行时间： "+(endTime-startTime)/1000+"s");
		} catch (WriteException | IOException e) {
			
			// TODO Auto-generated catch block
			System.out.println("出现IO异常");
			e.printStackTrace();
		}
	}

	public ConnectImpl( String keyword, int startpage, int endpage, String savepath) {
		// TODO Auto-generated constructor stub
	    this.keyword=keyword;
	    this.startpage=startpage;
		if(endpage>100) endpage=100;
		this.endpage=endpage;
		this.savepath=savepath;
		
	}
    
	/*
	 * @ workbook 传入工作簿
	 * 初始化sheet的第一行
	 */
	private void initWorkbook(WritableWorkbook workbook) throws IOException, RowsExceededException, WriteException {
		 this.exlRow = 0;
	     WritableSheet sheet = workbook.createSheet("web_data", 0);
	     sheet.addCell(new Label(0, this.exlRow, "标题"));
	     sheet.addCell(new Label(1, this.exlRow, "详情描述"));
	     sheet.addCell(new Label(2, this.exlRow, "卖点"));
	     sheet.addCell(new Label(3, this.exlRow, "卖价"));
	     sheet.addCell(new Label(4, this.exlRow, "特价"));
	     sheet.addCell(new Label(5, this.exlRow, "SKU"));
	     sheet.addCell(new Label(6, this.exlRow, "包装包括"));
	     sheet.addCell(new Label(7, this.exlRow, "产品本身链接"));
	     sheet.addCell(new Label(8, this.exlRow, "好评"));
	     sheet.addCell(new Label(9, this.exlRow, "图片1"));
	     sheet.addCell(new Label(10, this.exlRow, "图片2"));
	     sheet.addCell(new Label(11, this.exlRow, "图片3"));
	     sheet.addCell(new Label(12, this.exlRow, "图片4"));
	     sheet.addCell(new Label(13, this.exlRow, "图片5"));
	     sheet.addCell(new Label(14, this.exlRow, "图片6"));
	     sheet.addCell(new Label(15, this.exlRow, "图片7"));
	     sheet.addCell(new Label(16, this.exlRow++, "图片8"));
	     workbook.write();
	}
	public void startCatching() throws IOException , RowsExceededException, WriteException{
	
		WritableWorkbook workbook = null;
	    workbook = Workbook.createWorkbook(new File(
			     savepath));
		initWorkbook(workbook);
		for(int i=0;i<endpage-startpage+1;i++){
			System.out.println("开始抓取第"+(i+1)+"页的内容。");
			getFirstLevel(String.valueOf(startpage+i),workbook.getSheet(0));
			 try {
				Thread.sleep(6000);//延迟6秒发送请求
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	     workbook.write();
	     workbook.close();
	}
	
	private void getFirstLevel(String pagenum,WritableSheet sheet) throws IOException, RowsExceededException, WriteException{
		String urlstring = website + URLEncoder.encode(keyword, "utf-8") + "&page=" + pagenum;
		Document doc = null;
		while (doc == null) {
			doc = getDoc(urlstring);
			if(doc!=null) {
			Gson gson = new Gson();
			JsonRootBean info = gson.fromJson(doc.select("script[type=application/ld+json]").get(0).data().toString(),
					JsonRootBean.class);// 对于javabean直接给出class实例
			List<ItemListElement> ietlist = info.getItemListElement();
			for (int i = 0; i < ietlist.size(); i++) {
				ItemListElement ietelement = ietlist.get(i);
				System.out.println("第" + pagenum + "页  " + "第" + (i + 1) + "个详情:  " + ietelement.getUrl());
				getSencondLevel(ietelement.getUrl(), sheet);
			}
		  }
			else {
				System.out.println("第" + pagenum + "页数据  " + "获取失败，开始重新获取:--------------  ");
			}
		}
	}
	
	private void getSencondLevel(String urlstring,WritableSheet sheet) throws IOException,RowsExceededException, WriteException{

		    FinalInfo info=new FinalInfo();
		    info.setLink(urlstring);
		    Document doc = null;
			while (doc == null) {
				doc = getDoc(urlstring);
				if(doc!=null) {
				info.setName(doc.title().substring(0, doc.title().indexOf("| Lazada Malaysia")));//标题
				info.setComment(doc.getElementsByClass("prd-reviews").get(0).text().toString().trim());//好评
				info.setMainimage(doc.getElementsByClass("productImage").get(0).attr("data-big").toString());//图片
				info.setShort_description(doc.getElementsByClass("prd-attributesList").select("span").first().text().toString());//卖点
				info.setSpecial_price(doc.select("span#product_price").text().toString());//特价
				info.setPrice(doc.select("span#price_box").text().toString());//实价
				info.setDescription(doc.select("div#offer-template-0").text().toString());//描述        
				info.setPackage_content(doc.select("li.inbox__item").text().toString());//包装
				info.setSellersku(doc.select("td#pdtsku").text().toString());//sku
				info.setOneItemStart(true);
				}
				else {
					System.out.println("链接：" +urlstring + "获取失败，开始重新获取:--------------  ");
				}
			}
			if (info.isOneItemStart()) {
				sheet.addCell(new Label(0, this.exlRow, info.getName()));
				sheet.addCell(new Label(1, this.exlRow, info.getDescription()));
				sheet.addCell(new Label(2, this.exlRow, info.getShort_description()));
				sheet.addCell(new Label(3, this.exlRow, info.getPrice()));
				sheet.addCell(new Label(4, this.exlRow, info.getSpecial_price()));
				sheet.addCell(new Label(5, this.exlRow, info.getPrice()));
				sheet.addCell(new Label(6, this.exlRow, info.getSellersku()));
				sheet.addCell(new Label(7, this.exlRow, info.getPackage_content()));
				sheet.addCell(new Label(8, this.exlRow, info.getLink()));
				sheet.addCell(new Label(9, this.exlRow, info.getComment()));
				sheet.addCell(new Label(10, this.exlRow, info.getMainimage()));
				sheet.addCell(new Label(11, this.exlRow, ""));
				sheet.addCell(new Label(12, this.exlRow, ""));
				sheet.addCell(new Label(13, this.exlRow, ""));
				sheet.addCell(new Label(14, this.exlRow, ""));
				sheet.addCell(new Label(15, this.exlRow, ""));
				sheet.addCell(new Label(16, this.exlRow++, ""));
				info.backToInit();
			
			}
			
		}
	

	public Document getDoc(String url) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		Document doc = null;
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("User-Agent",
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)");

			CloseableHttpResponse response = httpclient.execute(httpget);

			String web = "";
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				if (entity != null) {
					web = EntityUtils.toString(entity, "UTF-8");

					doc = Jsoup.parse(web);

					return doc;

				}
			} finally {
				response.close();
				httpclient.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch(java.net.UnknownHostException u) {
			u.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
}
