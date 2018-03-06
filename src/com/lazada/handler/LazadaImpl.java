package com.lazada.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.lazada.model.Constant;
import com.lazada.model.json.firstlevel.FirstLevelJsonRootBean;
import com.lazada.model.json.firstlevel.ListItems;
import com.lazada.model.product.FinalInfo;
import com.lazada.util.BasicUtil;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class LazadaImpl extends CheckboxModel implements PlatformService{

	public LazadaImpl(int type, String typetext,int startpage,int endpage) {
		// TODO Auto-generated constructor stub
		this.type=type;
		this.typetext=typetext;
		this.startpage=startpage;
		this.endpage=endpage;
	}

	public void startCatching(ConnectImpl connectImpl,int exlRow,WritableWorkbook workbook) throws RowsExceededException, WriteException, IOException {
	
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

    public int getInfoByProductLink(ConnectImpl connectImpl,String links,WritableSheet sheet,int exlRow) throws IOException, RowsExceededException, WriteException{
		
		String[] arry=links.split(" ");
		for(int i=0;i<arry.length;i++) {
			exlRow=getDetailInfo(connectImpl,arry[i],sheet,exlRow,null);
			connectImpl.append("抓取成功:"+arry[i]+"\n");
			
		}
		return exlRow;
	}

	public void pageCatching(ConnectImpl connectImpl, WritableWorkbook workbook,int exlRow) {
        
				
		try {
			for (int i = 0; i < endpage - startpage + 1; i++) {
				connectImpl.append("开始抓取第" + (i + startpage) + "页的内容。\n");
				
				if(type==1) //关键词
					 exlRow=getFirstLevel(connectImpl, Constant.LAZADAKEYWORDSIDE, String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);//关键词
				
				else {//店铺
					
					exlRow=getInfoByStoreLink(connectImpl,String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);//店铺
				}
				
				if(i!=endpage)
				Thread.sleep(10000);// 延迟6秒发送请求
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			connectImpl.append(e.getMessage());
			//this.paintImmediately(this.getBounds());
			e.printStackTrace();
		}
		
	}

	public int getFirstLevel(ConnectImpl ci, String website, String pagenum, String keyword, WritableSheet sheet,
			int exlRow) throws IOException {
		String urlstring = "";
		if(!keyword.equals("")) //关键词
			urlstring = website + URLEncoder.encode(keyword, "utf-8") + "&page=" + pagenum;

		else  //keyword为空表示不是关键词搜索，有可能是店铺或者其他
		
		urlstring = website+ "&page=" + pagenum;
		System.out.println(urlstring);	
		Document doc = null;
		while (doc == null) {
			doc = BasicUtil.getDoc(urlstring);
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

	
	public int getFisrtLevelLink(ConnectImpl ci, Document doc, WritableSheet sheet, String pagenum, int exlRow) throws RowsExceededException, WriteException {
		Gson gson = new Gson();
		String lines = "";
		lines = doc.select("script").get(2).data().toString();
		FirstLevelJsonRootBean firstLevelJsonRootBean = gson.fromJson(lines.replaceAll("window.pageData=", ""),
				FirstLevelJsonRootBean.class);// 对于javabean直接给出class实例
		List<ListItems> secondietlist = firstLevelJsonRootBean.getMods().getListItems();
		Constant.areadycatchnum += secondietlist.size();
		System.out.println(secondietlist.size());
		for (int i = 0; i < secondietlist.size(); i++) {
			ListItems ietelement = secondietlist.get(i);
			ci.append("第" + pagenum + "页  " + "第" + (i + 1) + "个详情:  " +"https:"+ ietelement.getProductUrl() + "\n");
			System.out.println(ietelement.getProductUrl());
			exlRow = getDetailInfo(ci, "https:" + ietelement.getProductUrl(), sheet, exlRow, ietelement);
			ietelement.getLocation();
		}
		return exlRow;
	}

	public  int getDetailInfo(ConnectImpl ci, String urlstring, WritableSheet sheet, int exlRow, ListItems ietelement) throws RowsExceededException, WriteException {
		FinalInfo info = new FinalInfo();
		Document doc = null;
		int failcount=0;//失败两次就跳过不收集
		while (doc == null) {
			doc = BasicUtil.getDoc(urlstring);
			if (doc != null) {
//				System.out.println(doc.title().substring(0, doc.title().indexOf("| Lazada Malaysia")));
				info.setLink(urlstring);// 链接	
				if(ietelement!=null)
				info.setLocation(ietelement.getLocation());// 地区，存在第一页，所以在这抓取
				info.setName(doc.title().substring(0, doc.title().indexOf("| Lazada")));// 标题
				
				info.setComment(doc.getElementsByClass("score").get(0).text().toString().trim().replace("out of 5", ""));// 好评
				info.setSalenum(doc.getElementsByClass("pdp-review-summary__link").get(0).text().toString().trim().replace("Ratings", "")
						);// 评论
//				if(doc.getElementsByClass("pdp-review-summary__link").size()>1)
//				info.setSalenum(doc.getElementsByClass("pdp-review-summary__link").get(1).text().toString().trim().replace("Answered Questions", ""));//销量
				info.setBrand(doc.select("div.key-value").get(0).text().toString());// 品牌
				info.setStore(doc.getElementsByClass("seller-name__detail-name").get(0).text().toString());// 店铺名
				String category = "";
				Elements categorylist = doc.select("span.breadcrumb_item_text");
				if (categorylist != null && !categorylist.isEmpty()) {
					for (int i = 0; i < categorylist.size() - 1; i++)
						category += categorylist.get(i).text().toString() + "/";
				}
				info.setCategory(category);// 分类
				String elimagestring = "";
				Elements elimage = doc.getElementsByClass("item-gallery__thumbnail-image");
				info.setMainimage("https:"+elimage.get(0).attr("src").toString().replaceFirst(".jpg_110x110q75", ".jpg_800x800q83"));// 图片
				for (int i = 1; i < (elimage.size() > 8 ? 8 : elimage.size() - 1); i++) {
					Class clazz = info.getClass();
					Method m;
					try {
						m = clazz.getMethod("setImage" + (i + 1), String.class);
						m.invoke(info, elimage.get(i).attr("src").toString().replaceFirst(".jpg_110x110q75", ".jpg_800x800q83"));
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {

						e.printStackTrace();
					}

				}

				Elements elsd = doc.getElementsByClass("pdp-product-highlights").select("li");
				String elsdstring = "";
				for (Element els : elsd) {
					elsdstring += els.text().toString() + "\n";
				}
				info.setShort_description(elsdstring);// 卖点
				int length1=doc.getElementsByClass("pdp-product-highlights").html().length();
				info.setShort_descriptioncode(doc.getElementsByClass("pdp-product-highlights").html().substring(0, length1>32767?32767:length1));// 卖点代码
				String size = "";
				Elements sizelist = doc.select("span.sku-variable-size");
				if (sizelist != null && !sizelist.isEmpty()) {
					size += doc.getElementsByClass(
							"sku-tabpath-single")
							.text().toString() + " \n";
					for (int i = 0; i < sizelist.size(); i++)
						size += sizelist.get(i).text().toString() + " \n";
				}
				info.setSize(size);// 尺寸
				info.setSpecial_price(doc.select("span.pdp-price_color_orange").text().toString().replace("RM", ""));// 特价
				info.setPrice(
						doc.select("span.pdp-price_color_lightgray").text().toString().replace(",", "").replace("RM", "").trim());// 实价
				info.setDescription(doc.select("div.detail-content").get(0).text().toString());// 描述
				int length2=doc.select("div.detail-content").get(0).html().length();
				info.setDescriptioncode(doc.select("div.detail-content").get(0).html().substring(0,length2>32767?32767:length2));// 描述代码
				info.setPackage_content(doc.select("div.box-content-html").text().toString());// 包装
				info.setSellersku(doc.select("div.key-value").get(1).text().toString());// sku
				Elements trs =doc.select("span.key-title");
				
				for (int i=0;i<trs.size();i++) { //Compatibility by Model--手机类
					if(trs.get(i).text().toString().contains("Model")) {
						info.setCompatibility(doc.select("div.key-value").get(i).text().toString());
//						 System.out.print(tr.select("td").get(0).text().toString()+"  ");
//					     System.out.println(tr.select("td").get(1).text().toString());
						break;
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
				BasicUtil.handleOneItem(sheet, exlRow,info);
				exlRow++;
				info.backToInit();
			}
		}
		return exlRow;
	}

	
	public int getInfoByStoreLink(ConnectImpl connectImpl, String pagenum, String storelink, WritableSheet sheet,
			int exlRow) throws IOException {
		return getFirstLevel(connectImpl,storelink, pagenum, "", sheet, exlRow);
	}



}
