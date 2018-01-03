package com.lazada.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.swing.JTextArea;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.lazada.model.Constant;
import com.lazada.model.json.detail.ItemListElement;
import com.lazada.model.json.detail.JsonRootBean;
import com.lazada.model.product.FinalInfo;
import com.lazada.util.ExcelUtil;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 
 * @author yuanfan
 * 提升到平均每个商品抓取速度为1秒
 */
public class ConnectImpl extends JTextArea{
    
	private String typetext;
	private int startpage;
	private int endpage;
	private String savepath;
	private int exlRow;
	private int type;//1为关键词，2为店铺，3为产品
	public static void main(String[] args) {
		
		try {
			long startTime=System.currentTimeMillis();   
//			new ConnectImpl(2,"https://www.lazada.com.my/maccosmetics-flagship-store/?sort=popularity",1,1,"C:\\Users\\Administrator\\Desktop\\123.xls").startCatching();
//			new ConnectImpl(1,"womens shoes",1,1,"C:\\Users\\Administrator\\Desktop\\123.xls").startCatching();
			new ConnectImpl(3,"https://www.lazada.com.my/amart-fashion-women-flat-shoes-spring-rose-embroidery-platform-casual-shoeswhite-63716565.html https://www.lazada.com.my/amart-fashion-women-flat-shoes-spring-rose-embroidery-platform-casual-shoeswhite-63716565.html",1,1,"C:\\Users\\Administrator\\Desktop\\123.xls").startCatching();
			long endTime=System.currentTimeMillis();
			System.out.println("程序运行时间： "+(endTime-startTime)/1000+"s");
		} catch (WriteException | IOException e ) {
			
			// TODO Auto-generated catch block
			System.out.println("出现IO异常");
			e.printStackTrace();
		}
	}


	public void setPro(String typetext, int startpage, int endpage, String savepath) {
		// TODO Auto-generated method stub
		    this.typetext=typetext;
		    this.startpage=startpage;
			if(endpage>100) endpage=100;
			this.endpage=endpage;
			this.savepath=savepath; 
	}
	
	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public ConnectImpl( String typetext, int startpage, int endpage, String savepath) {
		// TODO Auto-generated constructor stub
	    this.typetext=typetext;
	    this.startpage=startpage;
		if(endpage>100) endpage=100;
		this.endpage=endpage;
		this.savepath=savepath;
		
	}
	
	public ConnectImpl(int type, String typetext, int startpage, int endpage, String savepath) {
		// TODO Auto-generated constructor stub
		this.type=type;
	    this.typetext=typetext;
	    this.startpage=startpage;
		if(endpage>100) endpage=100;
		this.endpage=endpage;
		this.savepath=savepath;
		
	}
    
	public void setPro(int type,String typetext, int startpage, int endpage, String savepath) {
		    this.type=type;
		    this.typetext=typetext;
		    this.startpage=startpage;
			if(endpage>100) endpage=100;
			this.endpage=endpage;
			this.savepath=savepath; 
	}
	
	public ConnectImpl() {
		// TODO Auto-generated constructor stub
	}
 
	public void pageCatching(int type,WritableWorkbook workbook) throws IOException , RowsExceededException, WriteException{
		
		try {
			for (int i = 0; i < endpage - startpage + 1; i++) {
				this.append("开始抓取第" + (i + startpage) + "页的内容。\n");
				if(type==1)
				exlRow=ExcelUtil.getInfoFirstLevel(this, Constant.KEYWORDSIDE, String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);
				else exlRow=ExcelUtil.getInfoByStoreLink(this,String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);
				Thread.sleep(6000);// 延迟6秒发送请求
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.append(e.getMessage());
			//this.paintImmediately(this.getBounds());
			e.printStackTrace();
		}
	}
	public void startCatching() throws IOException , RowsExceededException, WriteException{
		WritableWorkbook workbook = ExcelUtil.initWorkbook(savepath);
		exlRow++;
		
		switch (type){
		
		case 1://关键词
			pageCatching(type,workbook) ;
			break;
		case 2://店铺链接
			pageCatching(type,workbook) ;
			break;
		case 3://产品链接
			exlRow=ExcelUtil.getInfoByProductLink(this, typetext, workbook.getSheet(0), exlRow);
			this.append("抓取成功！请打开excel查看。\n");
			break;
			
		default:break;	
		}
		
			workbook.write();
			workbook.close();
		
	}
	

}
