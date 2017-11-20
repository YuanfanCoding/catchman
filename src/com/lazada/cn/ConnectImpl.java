package com.lazada.cn;

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
import com.lazada.json.model.ItemListElement;
import com.lazada.json.model.JsonRootBean;

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
	private String keyword;
	private int startpage;
	private int endpage;
	private String savepath;
	private int exlRow;
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

	public ConnectImpl( String keyword, int startpage, int endpage, String savepath) {
		// TODO Auto-generated constructor stub
	    this.keyword=keyword;
	    this.startpage=startpage;
		if(endpage>100) endpage=100;
		this.endpage=endpage;
		this.savepath=savepath;
		
	}
    
	public void setPro(String keyword, int startpage, int endpage, String savepath) {
		 this.keyword=keyword;
		    this.startpage=startpage;
			if(endpage>100) endpage=100;
			this.endpage=endpage;
			this.savepath=savepath; 
	}
	
	public ConnectImpl() {
		// TODO Auto-generated constructor stub
	}
 
	/*
	 * @ workbook ���빤����
	 * ��ʼ��sheet�ĵ�һ��
	 */
	private void initWorkbook(WritableWorkbook workbook) throws IOException, RowsExceededException, WriteException {
		 this.exlRow = 0;
	     WritableSheet sheet = workbook.createSheet("web_data", 0);
	     sheet.addCell(new Label(0, this.exlRow, "����"));
	     sheet.addCell(new Label(1, this.exlRow, "��������"));
	     sheet.addCell(new Label(2, this.exlRow, "����"));
	     sheet.addCell(new Label(3, this.exlRow, "����"));
	     sheet.addCell(new Label(4, this.exlRow, "�ؼ�"));
	     sheet.addCell(new Label(5, this.exlRow, "SKU"));
	     sheet.addCell(new Label(6, this.exlRow, "��װ����"));
	     sheet.addCell(new Label(7, this.exlRow, "��Ʒ��������"));
	     sheet.addCell(new Label(8, this.exlRow, "����"));
	     sheet.addCell(new Label(9, this.exlRow, "ͼƬ1"));
	     sheet.addCell(new Label(10, this.exlRow, "ͼƬ2"));
	     sheet.addCell(new Label(11, this.exlRow, "ͼƬ3"));
	     sheet.addCell(new Label(12, this.exlRow, "ͼƬ4"));
	     sheet.addCell(new Label(13, this.exlRow, "ͼƬ5"));
	     sheet.addCell(new Label(14, this.exlRow, "ͼƬ6"));
	     sheet.addCell(new Label(15, this.exlRow, "ͼƬ7"));
	     sheet.addCell(new Label(16, this.exlRow++, "ͼƬ8"));
	   
	}
	public void startCatching() throws IOException , RowsExceededException, WriteException{
		WritableWorkbook workbook = null;
		workbook = Workbook.createWorkbook(new File(savepath));
		initWorkbook(workbook);
		try {
			for (int i = 0; i < endpage - startpage + 1; i++) {
				this.append("��ʼץȡ��" + (i + startpage) + "ҳ�����ݡ�\n");
				this.paintImmediately(this.getBounds());
				getFirstLevel(String.valueOf(startpage + i), workbook.getSheet(0));
				Thread.sleep(6000);// �ӳ�6�뷢������
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			this.append(e.getMessage());
			this.paintImmediately(this.getBounds());
			e.printStackTrace();
		} finally {
			workbook.write();
			workbook.close();
		}
	}
	private void getFirstLevel(String pagenum,WritableSheet sheet) throws IOException, RowsExceededException, WriteException{
		String urlstring = website + URLEncoder.encode(keyword, "utf-8") + "&page=" + pagenum;
		Document doc = null;
		while (doc == null) {
			doc = getDoc(urlstring);
			if(doc!=null) {
		  try {
			Gson gson = new Gson();
			String line=doc.select("script[type=application/ld+json]").get(0).data().toString();
			JsonRootBean info = gson.fromJson(line.replaceAll("@type", "type").replaceAll("@context", "context"),
					JsonRootBean.class);// ����javabeanֱ�Ӹ���classʵ��
			List<ItemListElement> ietlist = info.getItemListElement();
			for (int i = 0; i < ietlist.size(); i++) {
				ItemListElement ietelement = ietlist.get(i);
				this.append("��" + pagenum + "ҳ  " + "��" + (i + 1) + "������:  " + ietelement.getUrl()+"\n");
				this.paintImmediately(this.getBounds());
				getSencondLevel(ietelement.getUrl(), sheet);
		     	}
				}catch(Exception e) {
					this.append(e.getMessage());
					this.paintImmediately(this.getBounds());
				}
		      }
			else {
				this.append("��" + pagenum + "ҳ����  " + "��ȡʧ�ܣ���ʼ���»�ȡ:--------------  \n");
				this.paintImmediately(this.getBounds());
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
				info.setName(doc.title().substring(0, doc.title().indexOf("| Lazada Malaysia")));//����
				info.setComment(doc.getElementsByClass("prd-reviews").get(0).text().toString().trim());//����
				
				String elimagestring="";
				Elements elimage=doc.getElementsByClass("productImage");
				info.setMainimage(elimage.get(0).attr("data-big").toString());//ͼƬ
				for(int i=1;i<elimage.size()-1;i++) {
					Class clazz = info.getClass();
					Method m;
						try {
							m = clazz.getMethod("setImage"+(i+1),String.class);
							m.invoke(info, elimage.get(i).attr("data-big").toString());
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							this.append("gsl\n");
							this.paintImmediately(this.getBounds());
							e.printStackTrace();
						}
					
				}
				
				Elements elsd=doc.getElementsByClass("prd-attributesList").select("span");
				String elsdstring="";
				for(Element els:elsd) {
					elsdstring+=els.text().toString()+"\n";
				}
				info.setShort_description(elsdstring);//����
				
				info.setSpecial_price(doc.select("span#product_price").text().toString());//�ؼ�
				info.setPrice(doc.select("span#price_box").text().toString());//ʵ��
				info.setDescription(doc.select("div.product-description__block").get(0).text().toString());//����        
				info.setPackage_content(doc.select("li.inbox__item").text().toString().replaceAll("<ul>", "").replaceAll("</ul>", "").replaceAll("<li>", "").replaceAll("</li>", "").replaceAll("<p>", "").replaceAll("</p>", ""));//��װ
				info.setSellersku(doc.select("td#pdtsku").text().toString());//sku
				info.setOneItemStart(true);
				}
				else {
					this.append("���ӣ�" +urlstring + "��ȡʧ�ܣ���ʼ���»�ȡ:--------------  \n");
					this.paintImmediately(this.getBounds());
				}
			}
			if (info.isOneItemStart()) {
				sheet.addCell(new Label(0, this.exlRow, info.getName()));
				sheet.addCell(new Label(1, this.exlRow, info.getDescription()));
				sheet.addCell(new Label(2, this.exlRow, info.getShort_description()));
				sheet.addCell(new Label(3, this.exlRow, info.getPrice()));
				sheet.addCell(new Label(4, this.exlRow, info.getSpecial_price()));
				sheet.addCell(new Label(5, this.exlRow, info.getSellersku()));
				sheet.addCell(new Label(6, this.exlRow, info.getPackage_content()));
				sheet.addCell(new Label(7, this.exlRow, info.getLink()));
				sheet.addCell(new Label(8, this.exlRow, info.getComment()));
				sheet.addCell(new Label(9, this.exlRow, info.getMainimage()));
				sheet.addCell(new Label(10, this.exlRow, info.getImage2()));
				sheet.addCell(new Label(11, this.exlRow, info.getImage3()));
				sheet.addCell(new Label(12, this.exlRow, info.getImage4()));
				sheet.addCell(new Label(13, this.exlRow, info.getImage5()));
				sheet.addCell(new Label(14, this.exlRow, info.getImage6()));
				sheet.addCell(new Label(15, this.exlRow, info.getImage7()));
				sheet.addCell(new Label(16, this.exlRow++, info.getImage8()));
				info.backToInit();
			
			}
			
		}
	

	public Document getDoc(String url) {

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
