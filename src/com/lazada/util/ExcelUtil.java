package com.lazada.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;

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
import com.lazada.handler.ConnectImpl;
import com.lazada.model.Constant;
import com.lazada.model.json.ItemListElement;
import com.lazada.model.json.JsonRootBean;
import com.lazada.model.product.BaseInfo;
import com.lazada.model.product.FinalInfo;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtil {

	/*
	 * @ workbook ���빤����
	 * ��ʼ��sheet�ĵ�һ��
	 */
      public static WritableWorkbook initWorkbook(String savepath) throws IOException, RowsExceededException, WriteException {
    	 WritableWorkbook workbook = null;
  		 workbook = Workbook.createWorkbook(new File(savepath));
    	 int exlRow = 0;
	     WritableSheet sheet = workbook.createSheet("web_data", 0);
	     sheet.addCell(new Label(0, exlRow, "����"));
	     sheet.addCell(new Label(1, exlRow, "��Ŀ"));
	     sheet.addCell(new Label(2, exlRow, "��������"));
	     sheet.addCell(new Label(3, exlRow, "��������"));
	     sheet.addCell(new Label(4, exlRow, "����"));
	     sheet.addCell(new Label(5, exlRow, "�������"));
	     sheet.addCell(new Label(6, exlRow, "����"));
	     sheet.addCell(new Label(7, exlRow, "�ؼ�"));
	     sheet.addCell(new Label(8, exlRow, "SKU"));
	     sheet.addCell(new Label(9, exlRow, "��װ����"));
	     sheet.addCell(new Label(10, exlRow, "��Ʒ��������"));
	     sheet.addCell(new Label(11, exlRow, "����"));
	     sheet.addCell(new Label(12, exlRow, "ͼƬ1"));
	     sheet.addCell(new Label(13, exlRow, "ͼƬ2"));
	     sheet.addCell(new Label(14, exlRow, "ͼƬ3"));
	     sheet.addCell(new Label(15, exlRow, "ͼƬ4"));
	     sheet.addCell(new Label(16, exlRow, "ͼƬ5"));
	     sheet.addCell(new Label(17, exlRow, "ͼƬ6"));
	     sheet.addCell(new Label(18, exlRow, "ͼƬ7"));
	     sheet.addCell(new Label(19, exlRow, "ͼƬ8"));
	     sheet.addCell(new Label(20, exlRow, "�ߴ�"));
	     sheet.addCell(new Label(21, exlRow, "����"));
	     sheet.addCell(new Label(22, exlRow++, "Ʒ��"));
	   
	     return workbook;
	}
	
	
	public static void handleOneItem(WritableSheet sheet,int exlRow,int type,BaseInfo info) throws RowsExceededException, WriteException{
		sheet.addCell(new Label(0, exlRow, info.getName()));
		sheet.addCell(new Label(1, exlRow, info.getCategory()));
		sheet.addCell(new Label(2, exlRow, info.getDescription()));
		sheet.addCell(new Label(3, exlRow, info.getDescriptioncode()));
		sheet.addCell(new Label(4, exlRow, info.getShort_description()));
		sheet.addCell(new Label(5, exlRow, info.getShort_descriptioncode()));
		sheet.addCell(new Label(6, exlRow, info.getPrice()));
		sheet.addCell(new Label(7, exlRow, info.getSpecial_price()));
		sheet.addCell(new Label(8, exlRow, info.getSellersku()));
		sheet.addCell(new Label(9, exlRow, info.getPackage_content()));
		sheet.addCell(new Label(10, exlRow, info.getLink()));
		sheet.addCell(new Label(11, exlRow, info.getComment()));
		sheet.addCell(new Label(12, exlRow, info.getMainimage()));
		sheet.addCell(new Label(13, exlRow, info.getImage2()));
		sheet.addCell(new Label(14, exlRow, info.getImage3()));
		sheet.addCell(new Label(15, exlRow, info.getImage4()));
		sheet.addCell(new Label(16, exlRow, info.getImage5()));
		sheet.addCell(new Label(17, exlRow, info.getImage6()));
		sheet.addCell(new Label(18, exlRow, info.getImage7()));
		sheet.addCell(new Label(19, exlRow, info.getImage8()));
		sheet.addCell(new Label(20, exlRow, info.getSize()));
		sheet.addCell(new Label(21, exlRow, info.getStore()));
	    sheet.addCell(new Label(22, exlRow, info.getBrand()));
	}
	
	public static int getInfoByProductLink(ConnectImpl ci,String link,WritableSheet sheet,int exlRow) throws IOException, RowsExceededException, WriteException{
		
		return getDetailInfo(ci,link,sheet,exlRow);
	}
	public static int getInfoByKeyWord(ConnectImpl ci,String website,String pagenum,String keyword,WritableSheet sheet,int exlRow) throws IOException, RowsExceededException, WriteException{
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
				ci.append("��" + pagenum + "ҳ  " + "��" + (i + 1) + "������:  " + ietelement.getUrl()+"\n");
				//this.paintImmediately(this.getBounds());
				exlRow=getDetailInfo(ci,ietelement.getUrl(), sheet,exlRow);
		     	}
				}catch(Exception e) {
					ci.append(e.getMessage());
					ci.paintImmediately(ci.getBounds());
				}
		      }
			else {
				ci.append("��" + pagenum + "ҳ����  " + "��ȡʧ�ܣ���ʼ���»�ȡ:--------------  \n");
				//this.paintImmediately(this.getBounds());
			}
		}
		return exlRow;
	}
	
	public static int getDetailInfo(ConnectImpl ci,String urlstring,WritableSheet sheet,int exlRow) throws IOException,RowsExceededException, WriteException{

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
					ci.append("���ӣ�" +urlstring + "��ȡʧ�ܣ���ʼ���»�ȡ:--------------  \n");
					//this.paintImmediately(this.getBounds());
				}
			}
			if (info.isOneItemStart()) {
				ExcelUtil.handleOneItem(sheet, exlRow, 0, info);
				exlRow++;
				info.backToInit();
			
			}
			return exlRow;
		}
	

	public static Document getDoc(String url) {

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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
}
