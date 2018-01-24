package com.lazada.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

public class ExcelUtil {

	/*
	 * @ workbook 传入工作簿
	 * 初始化sheet的第一行
	 */
      public static WritableWorkbook initWorkbook(String savepath) throws IOException, RowsExceededException, WriteException {
    	 WritableWorkbook workbook = null;
  		 workbook = Workbook.createWorkbook(new File(savepath));
    	 int exlRow = 0;
	     WritableSheet sheet = workbook.createSheet("web_data", 0);
	     sheet.addCell(new Label(0, exlRow, "标题"));
	     sheet.addCell(new Label(1, exlRow, "类目"));
	     sheet.addCell(new Label(2, exlRow, "详情描述"));
	     sheet.addCell(new Label(3, exlRow, "描述代码"));
	     sheet.addCell(new Label(4, exlRow, "卖点"));
	     sheet.addCell(new Label(5, exlRow, "卖点代码"));
	     sheet.addCell(new Label(6, exlRow, "卖价"));
	     sheet.addCell(new Label(7, exlRow, "特价"));
	     sheet.addCell(new Label(8, exlRow, "SKU"));
	     sheet.addCell(new Label(9, exlRow, "包装包括"));
	     sheet.addCell(new Label(10, exlRow, "产品本身链接"));
	     sheet.addCell(new Label(11, exlRow, "好评"));
	     sheet.addCell(new Label(12, exlRow, "图片1"));
	     sheet.addCell(new Label(13, exlRow, "图片2"));
	     sheet.addCell(new Label(14, exlRow, "图片3"));
	     sheet.addCell(new Label(15, exlRow, "图片4"));
	     sheet.addCell(new Label(16, exlRow, "图片5"));
	     sheet.addCell(new Label(17, exlRow, "图片6"));
	     sheet.addCell(new Label(18, exlRow, "图片7"));
	     sheet.addCell(new Label(19, exlRow, "图片8"));
	     sheet.addCell(new Label(20, exlRow, "尺寸"));
	     sheet.addCell(new Label(21, exlRow, "店铺"));
	     sheet.addCell(new Label(22, exlRow, "品牌"));
	     sheet.addCell(new Label(23, exlRow, "地区"));
	     sheet.addCell(new Label(24, exlRow++, "Compatibility by Model"));
	     return workbook;
	}
	
	
	public static void handleOneItem(WritableSheet sheet,int exlRow,BaseInfo info) throws RowsExceededException, WriteException{
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
	    sheet.addCell(new Label(23, exlRow, info.getLocation()));
	    sheet.addCell(new Label(24, exlRow, info.getCompatibility()));
	}
	
	public static int getInfoByProductLink(ConnectImpl ci,String links,WritableSheet sheet,int exlRow) throws IOException, RowsExceededException, WriteException{
		
		String[] arry=links.split(" ");
		for(int i=0;i<arry.length;i++) {
			exlRow=getDetailInfo(ci,arry[i],sheet,exlRow,null);
			ci.append("抓取成功:"+arry[i]+"\n");
		}
		return exlRow;
	}
	private static int getFisrtLevelLink (ConnectImpl ci,Document doc,WritableSheet sheet,String pagenum,int exlRow) throws WriteException, IOException{
		
		Gson gson = new Gson();
		String lines="";
		
		switch (ci.getPlatform()) {
		
		case Constant.LAZADA:
//			 String line=doc.select("script[type=application/ld+json]").get(1).data().toString();
//           JsonRootBean info = gson.fromJson(line.replaceAll("@type", "type").replaceAll("@context", "context"),
//					JsonRootBean.class);// 对于javabean直接给出class实例
//			List<ItemListElement> ietlist = info.getItemListElement();
//		    Constant.areadycatchnum += ietlist.size();
//			for (int i = 0; i < ietlist.size(); i++) {
//				ItemListElement ietelement = ietlist.get(i);
//				ci.append("第" + pagenum + "页  " + "第" + (i + 1) + "个详情:  " + ietelement.getUrl()+"\n");
//				exlRow=getDetailInfo(ci,"https:"+ietelement.getUrl(), sheet,exlRow,ietelement);
////				 ietelement.getLocation();
//				System.out.println("第" + (i + 1) + "个详情:  " + ietelement.getUrl()+"\n");
//		     	}
			 lines=doc.select("script").get(2).data().toString();
		        FirstLevelJsonRootBean firstLevelJsonRootBean = gson.fromJson(lines.replaceAll("window.pageData=", ""),
		       		 FirstLevelJsonRootBean.class);// 对于javabean直接给出class实例
		        List<ListItems> secondietlist = firstLevelJsonRootBean.getMods().getListItems();
		        Constant.areadycatchnum += secondietlist.size();
		        System.out.println(secondietlist.get(0).getName());
				for (int i = 0; i < secondietlist.size(); i++) {
					ListItems ietelement = secondietlist.get(i);
					ci.append("第" + pagenum + "页  " + "第" + (i + 1) + "个详情:  " + ietelement.getProductUrl()+"\n");
					exlRow=getDetailInfo(ci,"https:"+ietelement.getProductUrl(), sheet,exlRow,ietelement);
					 ietelement.getLocation();
			     	}
				
			break;
			
        case Constant.ALIEXPRESS:
        	     Elements elements=null;
        	    if(ci.getType()==1)  elements=doc.select("a.history-item").select("a.product");
        	    else elements=doc.select("a.pic-rind");
		        Constant.areadycatchnum += elements.size();
				for (int i = 0; i < (elements.size()>44?44:elements.size()); i++) {
					ci.append("第" + pagenum + "页  " + "第" + (i + 1) + "个详情:  " + "https:"+elements.get(i).attr("href").toString()+"\n");
					exlRow=getDetailInfo(ci,"https:"+elements.get(i).attr("href").toString(), sheet,exlRow,null);
			     	}
			break;
        case Constant.SHOPEE:
			
			break;
        case Constant.AMAZON:
			
			break;
		default:
			break;
		}
				
	   
		return exlRow;
	}
	public static int getInfoFirstLevel(ConnectImpl ci,String website,String pagenum,String keyword,WritableSheet sheet,int exlRow) throws IOException, RowsExceededException, WriteException{
		
		String urlstring = "";
		if(!keyword.equals("")) //关键词
			urlstring = website + URLEncoder.encode(keyword, "utf-8") + "&page=" + pagenum;

		else { //keyword为空表示不是关键词搜索，有可能是店铺或者其他
		
		
		if(ci.getPlatform().equals(Constant.LAZADA)) urlstring = website+ "&page=" + pagenum;
		
		else if(ci.getPlatform().equals(Constant.ALIEXPRESS) ) urlstring = website+ pagenum+".html";
			
		
		else {
			
		}
		}
		Document doc = null;
		while (doc == null) {
			doc = getDoc(urlstring);
			if(doc!=null) {
		  try {
			  exlRow=getFisrtLevelLink (ci,doc, sheet, pagenum, exlRow);
				}catch(Exception e) {
					ci.append(e.getMessage());
					ci.paintImmediately(ci.getBounds());
				}
		      }
			else {
				ci.append("第" + pagenum + "页数据  " + "获取失败，开始重新获取:--------------  \n");
				//this.paintImmediately(this.getBounds());
			}
		}
		return exlRow;
	}
	
	public static int getDetailInfo(ConnectImpl ci, String urlstring, WritableSheet sheet, int exlRow,
			ListItems ietelement) throws IOException, RowsExceededException, WriteException {

		FinalInfo info = new FinalInfo();
		Document doc = null;
		while (doc == null) {
			doc = getDoc(urlstring);
			if (doc != null) {
				
				info.setLink(urlstring);// 链接
				
				switch (ci.getPlatform()) {
				
				case Constant.LAZADA:
					
					if (ietelement != null)
						info.setLocation(ietelement.getLocation());// 地区，存在第一页，所以在这抓取
					getLazadaDetailInfo(info,doc);
					break;
					
					
				case Constant.ALIEXPRESS:

					
					getAliexpressDetailInfo(info,doc);
					
					break;
					
					
				case Constant.SHOPEE:

					break;
					
					
				case Constant.AMAZON:

					break;
					
					
				default:
					break;
				}

			} else 
				ci.append("链接：" + urlstring + "获取失败，开始重新获取:--------------  \n");

			if (info.isOneItemStart()) {
				ExcelUtil.handleOneItem(sheet, exlRow, info);
				exlRow++;
				info.backToInit();
			}
		}
		return exlRow;
	}
	
	
	public static void getAliexpressDetailInfo(FinalInfo info,Document doc) throws IOException, RowsExceededException, WriteException {
		
		info.setName(doc.select("h1.product-name").text().toString());// 标题
		info.setComment(doc.select("span.percent-num").text().toString()+"  "+doc.select("span.rantings-num").text().toString());// 好评
		info.setStore(doc.select("a.store-lnk").text().toString());// 店铺名
		info.setCategory(doc.select("div.ui-breadcrumb").text().toString());// 分类
		Elements elimage =doc.select("span.img-thumb-item");
		info.setMainimage(elimage.get(0).children().attr("src").toString().replaceFirst(".jpg_50x50", ""));// 图片
		
        for (int i = 1; i < (elimage.size() > 8 ? 8 : elimage.size() ); i++) {
        	Class clazz = info.getClass();
			Method m;
			try {
				m = clazz.getMethod("setImage" + (i + 1), String.class);
				m.invoke(info, elimage.get(i).children().attr("src").toString().replaceFirst(".jpg_50x50", ""));
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {

				e.printStackTrace();
			}
        }
		
        Elements maidians =doc.select("ul.product-property-list").select("li.property-item");
        String elsdstring = "";
        for(int i=0;i<maidians.size();i++) {
        	elsdstring +=maidians.get(i).text().toString()+ "\n";
            if(maidians.get(i).text().toString().contains("Brand Name"))
            	info.setBrand(maidians.get(i).text().toString());
        }
		info.setShort_description(elsdstring);// 卖点
		info.setShort_descriptioncode(doc.select("ul.product-property-list").select("ul.util-clearfix").html());// 卖点代码
		if(!doc.select("ul#j-sku-list-2").text().toString().equals(""))
		info.setSize(doc.select("ul#j-sku-list-2").text().toString());// 尺寸
		else 
		info.setSize(doc.select("ul#j-sku-list-3").text().toString());// 尺寸
		info.setSpecial_price(doc.select("del.p-del-price-content").select("del.notranslate").text().toString());// 特价
		info.setPrice(doc.select("div.p-price-content").text().toString());// 实价
		

         Document tempdoc = null;
 		while (tempdoc == null) {
 			 String line1=doc.select("script[type=text/javascript]").get(2).data().toString();
 	         String line2=line1.substring(line1.indexOf("window.runParams.detailDesc="), line1.indexOf("window.runParams.transAbTest=")).trim();
 			tempdoc = getDoc(line2.substring(line2.indexOf("https://"),line2.length()-2));
 			if (tempdoc != null) {
 				info.setDescription(tempdoc.text());// 描述
 				info.setDescriptioncode(tempdoc.toString());// 描述代码
 			}
 		}
 		 Elements packagelists =doc.select("ul.product-packaging-list").select("ul.util-clearfix").select("li.packaging-item");
 		 
 		 String pElement="";
 		 for(Element pElements:packagelists) {
 			pElement+=pElements.text().toString()+"\n";
 		 }
 		 
		info.setPackage_content(pElement);// 包装
		
		info.setSellersku("");// sku
		info.setOneItemStart(true);
	}
	
	
	public static void getAmazonDetailInfo(FinalInfo info,Document doc) throws IOException, RowsExceededException, WriteException {
		
	}
	
	
	public static void getShopeeDetailInfo(FinalInfo info,Document doc) throws IOException, RowsExceededException, WriteException {
		
	}
	
	public static void getLazadaDetailInfo(FinalInfo info,Document doc) throws IOException, RowsExceededException, WriteException {

		info.setName(doc.title().substring(0, doc.title().indexOf("| Lazada Malaysia")));// 标题
		info.setComment(doc.getElementsByClass("prd-reviews").get(0).text().toString().trim()
				.replace("(", "").replace(")", ""));// 好评
		info.setBrand(doc.select("div.prod_header_brand_action").get(0).text().toString());// 品牌
		info.setStore(doc.getElementsByClass("basic-info__name").get(0).text().toString());// 店铺名
		String category = "";
		Elements categorylist = doc.select("span.breadcrumb__item-text");
		if (categorylist != null && !categorylist.isEmpty()) {
			for (int i = 0; i < categorylist.size() - 1; i++)
				category += categorylist.get(i).text().toString() + "/";
		}
		info.setCategory(category);// 分类
		String elimagestring = "";
		Elements elimage = doc.getElementsByClass("productImage");
		info.setMainimage(elimage.get(0).attr("data-big").toString());// 图片
		for (int i = 1; i < (elimage.size() > 8 ? 8 : elimage.size() - 1); i++) {
			Class clazz = info.getClass();
			Method m;
			try {
				m = clazz.getMethod("setImage" + (i + 1), String.class);
				m.invoke(info, elimage.get(i).attr("data-big").toString());
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {

				e.printStackTrace();
			}

		}

		Elements elsd = doc.getElementsByClass("prd-attributesList").select("span");
		String elsdstring = "";
		for (Element els : elsd) {
			elsdstring += els.text().toString() + "\n";
		}
		info.setShort_description(elsdstring);// 卖点
		info.setShort_descriptioncode(doc.getElementsByClass("prd-attributesList").html());// 卖点代码
		String size = "";
		Elements sizelist = doc.select("span.grouped-size__popup__tab__content__item__size-item");
		if (sizelist != null && !sizelist.isEmpty()) {
			size += doc.getElementsByClass(
					"grouped-size__popup__tab__header__item grouped-size__popup__tab__header__item_state_active")
					.text().toString() + " \n";
			for (int i = 0; i < sizelist.size(); i++)
				size += sizelist.get(i).text().toString() + " \n";
		}
		info.setSize(size);// 尺寸
		info.setSpecial_price(doc.select("span#product_price").text().toString());// 特价
		info.setPrice(
				doc.select("span#price_box").text().toString().replace(",", "").replace("RM", "").trim());// 实价
		info.setDescription(doc.select("div.product-description__block").get(0).text().toString());// 描述
		info.setDescriptioncode(doc.select("div.product-description__block").get(0).html());// 描述代码
		info.setPackage_content(doc.select("li.inbox__item").text().toString().replaceAll("<ul>", "")
				.replaceAll("</ul>", "").replaceAll("<li>", "").replaceAll("</li>", "")
				.replaceAll("<p>", "").replaceAll("</p>", ""));// 包装
		info.setSellersku(doc.select("td#pdtsku").text().toString());// sku
		info.setOneItemStart(true);
		
	}
	
	

	public static Document getDoc(String url) {

//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		CookieStore cookieStore = new BasicCookieStore();  
		long startTime=System.currentTimeMillis();   
		RequestConfig defaultRequestConfig = RequestConfig.custom()
			    .setSocketTimeout(4000)//读取超时 readtime-out
			    .setConnectTimeout(4000)//连接超时
			    .setConnectionRequestTimeout(4000)
			    .build();
		CloseableHttpClient httpclient =HttpClients.createDefault();   
		Document doc = null;
		try {
			HttpGet httpget = new HttpGet(url);
//			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6*1000).build();
			httpget.setConfig(defaultRequestConfig);
			httpget.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
//			if(url.contains("page=1")) {
//			Header[] header=httpget.getAllHeaders();
//			for(int i=0;i<header.length;i++) {
//				System.out.println(header[i].toString());
//			}
//			}
//	        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "B960BA73258DB81B7204FEE3A5A78CA3");   
//	        cookie.setVersion(0);    
//	        cookie.setDomain("/pms/");   //设置范围  
//	        cookie.setPath("/");   
//	        cookieStore.addCookie(cookie); 
			CloseableHttpResponse response = httpclient.execute(httpget);
			
			long endTime=System.currentTimeMillis(); 
			System.out.println("此次请求总耗时： "+(endTime-startTime));
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


	
	public static void setCookieStore(HttpResponse httpResponse) {
	    System.out.println("----setCookieStore");
	    BasicCookieStore cookieStore = new BasicCookieStore();
	    // JSESSIONID
	    String setCookie = httpResponse.getFirstHeader("Set-Cookie")
	        .getValue();
	    String JSESSIONID = setCookie.substring("JSESSIONID=".length(),
	        setCookie.indexOf(";"));
	    System.out.println("JSESSIONID:" + JSESSIONID);
	    // 新建一个Cookie
	    BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
	        JSESSIONID);
	    cookie.setVersion(0);
	    cookie.setDomain("127.0.0.1");
	    cookie.setPath("/CwlProClient");
	    // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
	    // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
	    // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
	    // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
	    cookieStore.addCookie(cookie);
	  }
	
	public static int getInfoByStoreLink(ConnectImpl connectImpl,
		    String pagenum, String storelink,
			WritableSheet sheet, int exlRow) throws RowsExceededException, WriteException, IOException {
		return getInfoFirstLevel(connectImpl,storelink, pagenum, "", sheet, exlRow);
		 
	}

	
	
}
