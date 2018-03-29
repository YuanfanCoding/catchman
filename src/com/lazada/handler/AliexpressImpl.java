package com.lazada.handler;

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
import org.w3c.dom.css.ElementCSSInlineStyle;

import com.google.gson.Gson;
import com.lazada.model.Constant;
import com.lazada.model.json.firstlevel.FirstLevelJsonRootBean;
import com.lazada.model.json.firstlevel.ListItems;
import com.lazada.model.product.BaseInfo;
import com.lazada.model.product.FinalInfo;
import com.lazada.util.BasicUtils;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class AliexpressImpl extends CheckboxModel implements PlatformService{

	public AliexpressImpl(int type, String typetext,int startpage,int endpage) {
		// TODO Auto-generated constructor stub
		this.type=type;
		this.typetext=typetext;
		this.startpage=startpage;
		this.endpage=endpage;
	}

	@Override
	public void startCatching(ConnectImpl connectImpl, int exlRow, WritableWorkbook workbook)
			throws RowsExceededException, WriteException, IOException {
		  switch (type){
			
			case 1://关键词
				pageCatching(connectImpl,workbook,exlRow) ;
				break;
			case 2://店铺链接
				pageCatching(connectImpl,workbook,exlRow) ;
				break;
			case 3://产品链接
				getInfoByProductLink(connectImpl, typetext, workbook.getSheet(0), exlRow);
				connectImpl.append("抓取成功！请打开excel查看。\n");
				break;
			default:break;	
			
			}
		
	}

	@Override
	public void pageCatching(ConnectImpl connectImpl, WritableWorkbook workbook, int exlRow) {
		
		try {
			for (int i = 0; i < endpage - startpage + 1; i++) {
				connectImpl.append("开始抓取第" + (i + startpage) + "页的内容。\n");
				
				if(type==1) //关键词
					 exlRow=getFirstLevel(connectImpl, Constant.ALIEXPRESSKEYWORDSIDE, String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);//关键词
				
				else {//店铺
					
					exlRow=getInfoByStoreLink(connectImpl,String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);//店铺
				}
				
				if(i!=endpage)
				Thread.sleep(6000);// 延迟6秒发送请求
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			connectImpl.append(e.getMessage());
			//this.paintImmediately(this.getBounds());
			e.printStackTrace();
		}
		
	}

	@Override
	public int getFirstLevel(ConnectImpl ci, String website, String pagenum, String keyword, WritableSheet sheet,
			int exlRow) throws IOException {
		String urlstring = "";
		if(!keyword.equals("")) //关键词
			urlstring = website + URLEncoder.encode(keyword, "utf-8") + "&page=" + pagenum;

		else  //keyword为空表示不是关键词搜索，有可能是店铺或者其他
		urlstring = website+ pagenum+".html";
		
		System.out.println(urlstring);	
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

	@Override
	public int getDetailInfo(ConnectImpl ci, String urlstring, WritableSheet sheet, int exlRow, ListItems ietelement)
			throws RowsExceededException, WriteException {
		FinalInfo info = new FinalInfo();
		Document doc = null;
		int failcount=0;//失败两次就跳过不收集
		while (doc == null) {
			doc = getDoc(urlstring);
			if (doc != null) {
			    info.setName(doc.select("h1.product-name").text().toString());// 标题
				info.setComment(doc.select("span.percent-num").text().toString());// 好评
				info.setDiscuss(doc.select("span.rantings-num").text().toString().replace("votes", "").replaceAll("[( )]", ""));//评论量
				info.setSalenum(doc.select("span.order-num").text().toString().replace("orders",""));//销量
				info.setStore(doc.select("a.store-lnk").text().toString());// 店铺名
				if(doc.select("div.ui-breadcrumb").text().toString()!=null && !doc.select("div.ui-breadcrumb").text().toString().equals(""))
					info.setCategory(doc.select("div.ui-breadcrumb").text().toString());// 分类
				else 
				info.setCategory(doc.select("div.m-sop-crumb").text().toString());// 分类
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
				info.setLink(urlstring);
		        Elements maidians =doc.select("ul.product-property-list").select("li.property-item");
		        String elsdstring = "";
		        for(int i=0;i<maidians.size();i++) {
		        	elsdstring +=maidians.get(i).text().toString()+ "\n";
		            if(maidians.get(i).text().toString().contains("Brand Name"))
		            	info.setBrand(maidians.get(i).text().toString());
		        }
				info.setShort_description(elsdstring);// 卖点
				Elements es=doc.select("ul.product-property-list").select("ul.util-clearfix").select("li");
				String maidiancode="";
				for(int i=0;i<(es.size()>8?8:es.size());i++) {
					maidiancode+=es.get(i).html()+" \n";
				}
				info.setShort_descriptioncode(maidiancode);// 卖点代码
				
				if(!doc.select("ul#j-sku-list-2").text().toString().equals(""))
				info.setSize(doc.select("ul#j-sku-list-2").text().toString());// 尺寸
				else 
				info.setSize(doc.select("ul#j-sku-list-3").text().toString());// 尺寸
				info.setSpecial_price(doc.select("div.p-price-content").text().toString().replaceAll("US $", ""));// 特价
				info.setPrice(doc.select("del.p-del-price-content").select("del.notranslate").text().toString().replaceAll("US $", ""));// 实价
				

		        Document tempdoc = null;
		 		while (tempdoc == null) {
		 			 String line1="";
		 			 if(doc.select("script[type=text/javascript]").get(4).data().toString().contains("window.runParams.detailDesc="))
		 			 line1=doc.select("script[type=text/javascript]").get(4).data().toString();
		 			 else
		 			 line1=doc.select("script[type=text/javascript]").get(6).data().toString();
		 	         String line2=line1.substring(line1.indexOf("window.runParams.detailDesc="), line1.indexOf("window.runParams.transAbTest=")).trim();
		 	        
		 			tempdoc = getDoc(line2.substring(line2.indexOf("https://"),line2.length()-2));
		 			if (tempdoc != null) {
		 				int length1=tempdoc.text().length();
		 				int length2=tempdoc.toString().length();
		 				info.setDescription(tempdoc.text().substring(0,length1>32767?32767:length1));// 描述
		 				info.setDescriptioncode(tempdoc.toString().substring(0,length2>32767?32767:length2));// 描述代码
		 			}
		 		}
		 		 Elements packagelists =doc.select("ul.product-packaging-list").select("ul.util-clearfix").select("li.packaging-item");
		 		 
		 		 String pElement="";
		 		 for(Element pElements:packagelists) {
		 			pElement+=pElements.text().toString()+"\n";
		 		 }
		 		 
				info.setPackage_content(pElement);// 包装
				info.setSellersku("");// sku
				info.setLocation(doc.select("dd.store-address").text().toString());
				
				Elements itemskuimagelist=doc.select("li.item-sku-image").select("img"); //小图
				 for (int i = 0; i < (itemskuimagelist.size() > 15 ? 15 : itemskuimagelist.size() ); i++) {
			        	Class clazz = info.getClass();
						Method m;
						try {
							m = clazz.getMethod("setSmallimage" + (i + 1), String.class);
							m.invoke(info, itemskuimagelist.get(i).attr("src").toString().replaceFirst(".jpg_50x50", ""));
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException
								| IllegalArgumentException | InvocationTargetException e) {

							e.printStackTrace();
						}
			        }
				info.setOneItemStart(true);
					
			} else {
				failcount++;
				if(failcount<3)
				ci.append("链接：" + urlstring + "第"+failcount+"次获取失败，开始重新获取:--------------  \n");
				else {
					ci.append("链接：" + urlstring + "第"+failcount+"次获取失败，开始下一个收集--------------  \n");
					break;
				}
			}
			if (info.isOneItemStart()) {
				handleOneItem(sheet, exlRow,info);
				exlRow++;
				info.backToInit();
			}
		}
		return exlRow;
	}

	@Override
	public int getInfoByStoreLink(ConnectImpl connectImpl, String pagenum, String storelink, WritableSheet sheet,
			int exlRow) throws IOException {
		return getFirstLevel(connectImpl,storelink, pagenum, "", sheet, exlRow);
		
	}

	@Override
	public int getInfoByProductLink(ConnectImpl connectImpl, String links, WritableSheet sheet, int exlRow)
			throws IOException, RowsExceededException, WriteException {
		String[] arry=links.split(" ");
		for(int i=0;i<arry.length;i++) {
			exlRow=getDetailInfo(connectImpl,arry[i],sheet,exlRow,null);
			connectImpl.append("抓取成功:"+arry[i]+"\n");
			
		}
		return exlRow;
	}

	@Override
	public int getFisrtLevelLink(ConnectImpl ci, Document doc, WritableSheet sheet, String pagenum, int exlRow)
			throws RowsExceededException, WriteException {
		Gson gson = new Gson();
		String lines = "";
		Elements elements=null;
	    if(ci.getType()==1)  elements=doc.select("a.history-item").select("a.product");
	    else elements=doc.select("a.pic-rind");
        Constant.areadycatchnum += elements.size();
		for (int i = 0; i < (elements.size()>44?44:elements.size()); i++) {
			ci.append("第" + pagenum + "页  " + "第" + (i + 1) + "个详情:  " + "https:"+elements.get(i).attr("href").toString()+"\n");
			exlRow=getDetailInfo(ci,"https:"+elements.get(i).attr("href").toString(), sheet,exlRow,null);
	     	}
		return exlRow;
	}

	public static void handleOneItem(WritableSheet sheet,int exlRow,BaseInfo info) throws RowsExceededException, WriteException{
		int index=0;
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

}
