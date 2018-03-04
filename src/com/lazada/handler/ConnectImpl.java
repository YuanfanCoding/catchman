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
import com.lazada.util.BasicUtil;

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
	
	private String platform;//选择的平台
	public static void main(String[] args) {
		
		try {
			long startTime=System.currentTimeMillis();   
			
//			ConnectImpl connectImpl=new ConnectImpl(2,"https://www.lazada.com.my/kristall-official-store/?hybrid=1&q=All-Products&from=wangpu&pageTypeId=2",1,3,"C:\\Users\\Administrator\\Desktop\\123.xls");
			ConnectImpl connectImpl=new ConnectImpl(1,"Mobile Phone",1,1,"C:\\Users\\Administrator\\Desktop\\123.xls");
//			ConnectImpl connectImpl=new ConnectImpl();
//			connectImpl.setType(1);
//			connectImpl.setPro("womens shoes",1,1,"C:\\Users\\Administrator\\Desktop\\123.xls");	
//			ConnectImpl connectImpl=new ConnectImpl(3,"https://www.aliexpress.com/item/2017-new-fashion-women-shoes/32823588095.html?ws_ab_test=searchweb0_0,searchweb201602_4_10152_10151_10065_10068_10344_10342_10325_10546_10343_10340_10548_10341_10084_10617_10083_10616_10615_10307_10313_10059_10534_100031_10604_10103_10142,searchweb201603_25,ppcSwitch_5&algo_expid=9fe502ce-c27d-4ac6-b4a5-afc709abac99-6&algo_pvid=9fe502ce-c27d-4ac6-b4a5-afc709abac99&priceBeautifyAB=3",1,1,"C:\\Users\\Administrator\\Desktop\\123.xls");
			connectImpl.setPlatform(Constant.ALIEXPRESS);
			WritableWorkbook workbook=null;
			connectImpl.startCatching(workbook);
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


	public String getPlatform() {
		return platform;
	}


	public void setPlatform(String platform) {
		this.platform = platform;
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
 
//	public void pageCatching(int type,WritableWorkbook workbook) throws IOException , RowsExceededException, WriteException{
//		String site="";
//		
//		
//		
//		try {
//			for (int i = 0; i < endpage - startpage + 1; i++) {
//				this.append("开始抓取第" + (i + startpage) + "页的内容。\n");
//				
//				if(type==1) //关键词
//					 exlRow=BasicUtil.getInfoFirstLevel(this, site, String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);//关键词
//				
//				else {//店铺
//					
//					exlRow=BasicUtil.getInfoByStoreLink(this,String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);//店铺
//				}
//				
//				
//				Thread.sleep(6000);// 延迟6秒发送请求
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			this.append(e.getMessage());
//			//this.paintImmediately(this.getBounds());
//			e.printStackTrace();
//		}
//	}
	
	
	public void startCatching(WritableWorkbook workbook) throws IOException, WriteException {

		// WritableWorkbook workbook = null;

		try {
			if (workbook == null)
				workbook = BasicUtil.initWorkbook(savepath);
			exlRow++;
			PlatformService platformService = getPlatFormInstance();

			platformService.startCatching(this, exlRow, workbook);
		} catch (IOException | WriteException e) {
			e.printStackTrace();
			this.append(e.getMessage());
		} finally {
//			System.out.println(44444);
			workbook.write();
			workbook.close();
			workbook=null;
		}

	}


	private PlatformService getPlatFormInstance() {
		
       switch (platform) {
		
		case Constant.LAZADA:
			return  new LazadaImpl(type,typetext,startpage,endpage);
			
	    case Constant.ALIEXPRESS:
        	return new AliexpressImpl(type,typetext,startpage,endpage);
			
        case Constant.SHOPEE:
        	return new ShopeeImpl(type,typetext);
			
        case Constant.AMAZON:
//        	return new AmazonImpl(type,typetext);
			break;
		default:
			break;
			
		}
		

		return null;
	}
	

}
