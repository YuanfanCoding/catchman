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
import com.lazada.model.json.ItemListElement;
import com.lazada.model.json.JsonRootBean;
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
 * ������ƽ��ÿ����Ʒץȡ�ٶ�Ϊ3��
 */
public class ConnectImpl extends JTextArea{
    
	public final static String website="http://www.lazada.com.my/catalog/?q="; 
	private String typetext;
	private int startpage;
	private int endpage;
	private String savepath;
	private int exlRow;
	private int type;//1Ϊ�ؼ��ʣ�2Ϊ���̣�3Ϊ��Ʒ
	public static void main(String[] args) {
		
		try {
			long startTime=System.currentTimeMillis();   
			new ConnectImpl("women shoes",1,1,"C:\\Users\\Administrator\\Desktop\\123.xls").startCatching();
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


	public ConnectImpl( String typetext, int startpage, int endpage, String savepath) {
		// TODO Auto-generated constructor stub
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
 

	public void startCatching() throws IOException , RowsExceededException, WriteException{
		WritableWorkbook workbook = ExcelUtil.initWorkbook(savepath);
		exlRow++;
		
		switch (type){
			
		case 1://�ؼ���
			try {
				for (int i = 0; i < endpage - startpage + 1; i++) {
					this.append("��ʼץȡ��" + (i + startpage) + "ҳ�����ݡ�\n");
					exlRow=ExcelUtil.getInfoByKeyWord(this, website, String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);
					Thread.sleep(6000);// �ӳ�6�뷢������
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				this.append(e.getMessage());
				//this.paintImmediately(this.getBounds());
				e.printStackTrace();
			} finally {
				workbook.write();
				workbook.close();
			}
			break;
		case 2://��������
			
			break;
		case 3://��Ʒ����
			exlRow=ExcelUtil.getInfoByProductLink(this, typetext, workbook.getSheet(0), exlRow);
			this.append("ץȡ�ɹ������excel�鿴��\n");
			break;
			
		default:break;	
		}
		
		
		
		

	}
	public void getFirstLevelByKeyWord(String pagenum,String keyword,WritableSheet sheet) throws IOException, RowsExceededException, WriteException{
		String urlstring = website + URLEncoder.encode(keyword, "utf-8") + "&page=" + pagenum;
		Document doc = null;
		while (doc == null) {
			doc = getDoc(urlstring);
			if(doc!=null) {
		  try {
			Gson gson = new Gson();
			String line=doc.select("script[type=application/ld+json]").get(1).data().toString();
			JsonRootBean info = gson.fromJson(line.replaceAll("@type", "type").replaceAll("@context", "context"),
					JsonRootBean.class);// ����javabeanֱ�Ӹ���classʵ��
			List<ItemListElement> ietlist = info.getItemListElement();
			Constant.areadycatchnum += ietlist.size();
			for (int i = 0; i < ietlist.size(); i++) {
				ItemListElement ietelement = ietlist.get(i);
				this.append("��" + pagenum + "ҳ  " + "��" + (i + 1) + "������:  " + ietelement.getUrl()+"\n");
				System.out.println("��" + pagenum + "ҳ  " + "��" + (i + 1) + "������:  " + ietelement.getUrl()+"\n");
				//this.paintImmediately(this.getBounds());
				getSencondLevel(ietelement.getUrl(), sheet);
		     	}
				}catch(Exception e) {
					this.append(e.getMessage());
					System.out.println("error----");
					System.out.println(e.getMessage());
					this.paintImmediately(this.getBounds());
				}
		      }
			else {
				this.append("��" + pagenum + "ҳ����  " + "��ȡʧ�ܣ���ʼ���»�ȡ:--------------  \n");
				//this.paintImmediately(this.getBounds());
				System.out.println("��" + pagenum + "ҳ����  " + "��ȡʧ�ܣ���ʼ���»�ȡ:--------------  \n");
			}
		}
		
	}
	
	public void getSencondLevel(String urlstring,WritableSheet sheet) throws IOException,RowsExceededException, WriteException{

		    FinalInfo info=new FinalInfo();
		    info.setLink(urlstring);
		    Document doc = null;
			while (doc == null) {
				doc = getDoc(urlstring);
				if(doc!=null) {
				info.setName(doc.title().substring(0, doc.title().indexOf("| Lazada Malaysia")));//����
				info.setComment(doc.getElementsByClass("prd-reviews").get(0).text().toString().trim().replace("(", "").replace(")", ""));//����
				info.setBrand(doc.select("div.prod_header_brand_action").get(0).text().toString());//Ʒ��
				info.setStore(doc.getElementsByClass("basic-info__name").get(0).text().toString());//������
				String category="";
				Elements categorylist=doc.select("span.breadcrumb__item-text");
				if(categorylist!=null && !categorylist.isEmpty()){
					for(int i=0;i<categorylist.size()-1;i++)
						category+=categorylist.get(i).text().toString()+"/";
				}
				info.setCategory(category);//����
				String elimagestring="";
				Elements elimage=doc.getElementsByClass("productImage");
				info.setMainimage(elimage.get(0).attr("data-big").toString());//ͼƬ
				for(int i=1;i<(elimage.size()>8?8:elimage.size()-1);i++) {
					Class clazz = info.getClass();
					Method m;
						try {
							m = clazz.getMethod("setImage"+(i+1),String.class);
							m.invoke(info, elimage.get(i).attr("data-big").toString());
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							
							e.printStackTrace();
						}
					
				}
				
				Elements elsd=doc.getElementsByClass("prd-attributesList").select("span");
				String elsdstring="";
				for(Element els:elsd) {
					elsdstring+=els.text().toString()+"\n";
				}
				info.setShort_description(elsdstring);//����
				info.setShort_descriptioncode(doc.getElementsByClass("prd-attributesList").html());//�������
				String size="";
				Elements sizelist=doc.select("span.grouped-size__popup__tab__content__item__size-item");
				if(sizelist!=null && !sizelist.isEmpty()){
					size+=doc.getElementsByClass("grouped-size__popup__tab__header__item grouped-size__popup__tab__header__item_state_active").text().toString()+" \n";
					for(int i=0;i<sizelist.size();i++)
						size+=sizelist.get(i).text().toString()+" \n";
				}
				info.setSize(size);//�ߴ�
				info.setSpecial_price(doc.select("span#product_price").text().toString());//�ؼ�
				info.setPrice(doc.select("span#price_box").text().toString().replace(",","" ));//ʵ��
				info.setDescription(doc.select("div.product-description__block").get(0).text().toString());//����     
				info.setDescriptioncode(doc.select("div.product-description__block").get(0).html());//��������
				info.setPackage_content(doc.select("li.inbox__item").text().toString().replaceAll("<ul>", "").replaceAll("</ul>", "").replaceAll("<li>", "").replaceAll("</li>", "").replaceAll("<p>", "").replaceAll("</p>", ""));//��װ
				info.setSellersku(doc.select("td#pdtsku").text().toString());//sku
				info.setOneItemStart(true);
				}
				else {
					this.append("���ӣ�" +urlstring + "��ȡʧ�ܣ���ʼ���»�ȡ:--------------  \n");
					//this.paintImmediately(this.getBounds());
				}
			}
			if (info.isOneItemStart()) {
				ExcelUtil.handleOneItem(sheet, exlRow, 0, info);
				exlRow++;
				info.backToInit();
			
			}
		}
	

	public  Document getDoc(String url) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		Document doc = null;
		try {
			HttpGet httpget = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6*1000).build();
			httpget.setConfig(requestConfig);
			httpget.setHeader("User-Agent",
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)");

			CloseableHttpResponse response = httpclient.execute(httpget);

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
		} 
		  catch (ClientProtocolException e) {
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
