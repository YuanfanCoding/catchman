package com.lazada.handler;

import java.io.IOException;
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
import com.lazada.util.ExcelUtil;

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
			doc = ExcelUtil.getDoc(urlstring);
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
			doc = ExcelUtil.getDoc(urlstring);
			if (doc != null) {
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
				info.setLink(urlstring);
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
				info.setSpecial_price(doc.select("div.p-price-content").text().toString());// 特价
				info.setPrice(doc.select("del.p-del-price-content").select("del.notranslate").text().toString());// 实价
				

		        Document tempdoc = null;
		 		while (tempdoc == null) {
		 			 String line1=doc.select("script[type=text/javascript]").get(2).data().toString();
		 	         String line2=line1.substring(line1.indexOf("window.runParams.detailDesc="), line1.indexOf("window.runParams.transAbTest=")).trim();
		 	        
		 			tempdoc = ExcelUtil.getDoc(line2.substring(line2.indexOf("https://"),line2.length()-2));
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
				ExcelUtil.handleOneItem(sheet, exlRow,info);
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

	

}
