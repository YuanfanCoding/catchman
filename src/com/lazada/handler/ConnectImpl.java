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
import com.lazada.util.BasicUtils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 
 * @author yuanfan
 * ������ƽ��ÿ����Ʒץȡ�ٶ�Ϊ1��
 */
public class ConnectImpl extends JTextArea{
    
	private String typetext;
	private int startpage;
	private int endpage;
	private String savepath;
	private int exlRow;
	private int type;//1Ϊ�ؼ��ʣ�2Ϊ���̣�3Ϊ��Ʒ
	
	private String platform;//ѡ���ƽ̨
	public static void main(String[] args) {
		
		try {
			long startTime=System.currentTimeMillis();   
			
//			ConnectImpl connectImpl=new ConnectImpl(3,"https://shopee.com.my/Nike-Flyknit-Racer-Sport-Shoes-i.41002014.640724773 https://shopee.com.my/Women's-Breathable-Walking-Sport-Shoes-SFGHOUSE-i.15271343.196224500",1,1,"C:\\Users\\Administrator\\Desktop\\123.xls");
			ConnectImpl connectImpl=new ConnectImpl(2,"https://www.lazada.sg/shop-vehicle-backup-cameras/?spm=a2o42.pdp.breadcrumb.4.63b5db20ozJOYz",1,2,"C:\\Users\\Administrator\\Desktop\\123.xls");
//			ConnectImpl connectImpl=new ConnectImpl(2,"https://autocare.aliexpress.com/store/all-wholesale-products/1489256.html?spm=2114.12010606.0.0.5bb911aap9Ed9",1,1,"C:\\Users\\Administrator\\Desktop\\123.xls");
//			ConnectImpl connectImpl=new ConnectImpl(1,"women bag",1,1,"C:\\Users\\Administrator\\Desktop\\123.xls");
//			ConnectImpl connectImpl=new ConnectImpl();
//			connectImpl.setType(1);
//			connectImpl.setPro("Petpet",1,1,"C:\\Users\\Administrator\\Desktop\\123.xls");	
//			ConnectImpl connectImpl=new ConnectImpl(3,"https://www.aliexpress.com/item/2017-new-fashion-women-shoes/32823588095.html?ws_ab_test=searchweb0_0,searchweb201602_4_10152_10151_10065_10068_10344_10342_10325_10546_10343_10340_10548_10341_10084_10617_10083_10616_10615_10307_10313_10059_10534_100031_10604_10103_10142,searchweb201603_25,ppcSwitch_5&algo_expid=9fe502ce-c27d-4ac6-b4a5-afc709abac99-6&algo_pvid=9fe502ce-c27d-4ac6-b4a5-afc709abac99&priceBeautifyAB=3",1,1,"C:\\Users\\Administrator\\Desktop\\123.xls");
			connectImpl.setPlatform(Constant.LAZADA);
			WritableWorkbook workbook=null;
			connectImpl.startCatching(workbook);
			long endTime=System.currentTimeMillis();
			System.out.println("��������ʱ�䣺 "+(endTime-startTime)/1000+"s");
		} catch (WriteException | IOException e ) {
			
			// TODO Auto-generated catch block
			System.out.println("����IO�쳣");
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
//				this.append("��ʼץȡ��" + (i + startpage) + "ҳ�����ݡ�\n");
//				
//				if(type==1) //�ؼ���
//					 exlRow=BasicUtil.getInfoFirstLevel(this, site, String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);//�ؼ���
//				
//				else {//����
//					
//					exlRow=BasicUtil.getInfoByStoreLink(this,String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);//����
//				}
//				
//				
//				Thread.sleep(6000);// �ӳ�6�뷢������
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
			{
				
//				workbook  = BasicUtil.initWorkbook(savepath);
				 workbook =Workbook.createWorkbook(new File(savepath));
		    	 int exlRow = 0;
		    	 int index=0;
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
			    
			}				
			exlRow++;
			PlatformService platformService = getPlatFormInstance();
			
			platformService.startCatching(this, exlRow, workbook);
		} catch (Exception e) {
			e.printStackTrace();
			this.append(e.getMessage());
		} finally {
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
        	return new ShopeeImpl(type,typetext,startpage,endpage);
        	
        case Constant.AMAZON:
//        	return new AmazonImpl(type,typetext);
			break;
		default:
			break;
			
		}
		

		return null;
	}
	

}
