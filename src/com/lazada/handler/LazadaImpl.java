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
		
		case 1://�ؼ���
			pageCatching(connectImpl,workbook,exlRow) ;
			break;
		case 2://��������
			pageCatching(connectImpl,workbook,exlRow) ;
			break;
		case 3://��Ʒ����
			getInfoByProductLink(connectImpl, typetext, workbook.getSheet(0), exlRow);
			connectImpl.append("ץȡ�ɹ������excel�鿴��\n");
			break;
		default:break;	
		
		}
	}

    public int getInfoByProductLink(ConnectImpl connectImpl,String links,WritableSheet sheet,int exlRow) throws IOException, RowsExceededException, WriteException{
		
		String[] arry=links.split(" ");
		for(int i=0;i<arry.length;i++) {
			exlRow=getDetailInfo(connectImpl,arry[i],sheet,exlRow,null);
			connectImpl.append("ץȡ�ɹ�:"+arry[i]+"\n");
			
		}
		return exlRow;
	}

	public void pageCatching(ConnectImpl connectImpl, WritableWorkbook workbook,int exlRow) {
        
				
		try {
			for (int i = 0; i < endpage - startpage + 1; i++) {
				connectImpl.append("��ʼץȡ��" + (i + startpage) + "ҳ�����ݡ�\n");
				
				if(type==1) //�ؼ���
					 exlRow=getFirstLevel(connectImpl, Constant.LAZADAKEYWORDSIDE, String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);//�ؼ���
				
				else {//����
					
					exlRow=getInfoByStoreLink(connectImpl,String.valueOf(startpage + i), typetext, workbook.getSheet(0), exlRow);//����
				}
				
				if(i!=endpage)
				Thread.sleep(10000);// �ӳ�6�뷢������
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
		if(!keyword.equals("")) //�ؼ���
			urlstring = website + URLEncoder.encode(keyword, "utf-8") + "&page=" + pagenum;

		else  //keywordΪ�ձ�ʾ���ǹؼ����������п����ǵ��̻�������
		
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
				ci.append("��" + pagenum + "ҳ����  " + "��ȡʧ�ܣ���ʼ���»�ȡ:--------------  \n");
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
				FirstLevelJsonRootBean.class);// ����javabeanֱ�Ӹ���classʵ��
		List<ListItems> secondietlist = firstLevelJsonRootBean.getMods().getListItems();
		Constant.areadycatchnum += secondietlist.size();
		System.out.println(secondietlist.size());
		for (int i = 0; i < secondietlist.size(); i++) {
			ListItems ietelement = secondietlist.get(i);
			ci.append("��" + pagenum + "ҳ  " + "��" + (i + 1) + "������:  " +"https:"+ ietelement.getProductUrl() + "\n");
			System.out.println(ietelement.getProductUrl());
			exlRow = getDetailInfo(ci, "https:" + ietelement.getProductUrl(), sheet, exlRow, ietelement);
			ietelement.getLocation();
		}
		return exlRow;
	}

	public  int getDetailInfo(ConnectImpl ci, String urlstring, WritableSheet sheet, int exlRow, ListItems ietelement) throws RowsExceededException, WriteException {
		FinalInfo info = new FinalInfo();
		Document doc = null;
		int failcount=0;//ʧ�����ξ��������ռ�
		while (doc == null) {
			doc = BasicUtil.getDoc(urlstring);
			if (doc != null) {
//				System.out.println(doc.title().substring(0, doc.title().indexOf("| Lazada Malaysia")));
				info.setLink(urlstring);// ����	
				if(ietelement!=null)
				info.setLocation(ietelement.getLocation());// ���������ڵ�һҳ����������ץȡ
				info.setName(doc.title().substring(0, doc.title().indexOf("| Lazada")));// ����
				
				info.setComment(doc.getElementsByClass("score").get(0).text().toString().trim().replace("out of 5", ""));// ����
				info.setSalenum(doc.getElementsByClass("pdp-review-summary__link").get(0).text().toString().trim().replace("Ratings", "")
						);// ����
//				if(doc.getElementsByClass("pdp-review-summary__link").size()>1)
//				info.setSalenum(doc.getElementsByClass("pdp-review-summary__link").get(1).text().toString().trim().replace("Answered Questions", ""));//����
				info.setBrand(doc.select("div.key-value").get(0).text().toString());// Ʒ��
				info.setStore(doc.getElementsByClass("seller-name__detail-name").get(0).text().toString());// ������
				String category = "";
				Elements categorylist = doc.select("span.breadcrumb_item_text");
				if (categorylist != null && !categorylist.isEmpty()) {
					for (int i = 0; i < categorylist.size() - 1; i++)
						category += categorylist.get(i).text().toString() + "/";
				}
				info.setCategory(category);// ����
				String elimagestring = "";
				Elements elimage = doc.getElementsByClass("item-gallery__thumbnail-image");
				info.setMainimage("https:"+elimage.get(0).attr("src").toString().replaceFirst(".jpg_110x110q75", ".jpg_800x800q83"));// ͼƬ
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
				info.setShort_description(elsdstring);// ����
				int length1=doc.getElementsByClass("pdp-product-highlights").html().length();
				info.setShort_descriptioncode(doc.getElementsByClass("pdp-product-highlights").html().substring(0, length1>32767?32767:length1));// �������
				String size = "";
				Elements sizelist = doc.select("span.sku-variable-size");
				if (sizelist != null && !sizelist.isEmpty()) {
					size += doc.getElementsByClass(
							"sku-tabpath-single")
							.text().toString() + " \n";
					for (int i = 0; i < sizelist.size(); i++)
						size += sizelist.get(i).text().toString() + " \n";
				}
				info.setSize(size);// �ߴ�
				info.setSpecial_price(doc.select("span.pdp-price_color_orange").text().toString().replace("RM", ""));// �ؼ�
				info.setPrice(
						doc.select("span.pdp-price_color_lightgray").text().toString().replace(",", "").replace("RM", "").trim());// ʵ��
				info.setDescription(doc.select("div.detail-content").get(0).text().toString());// ����
				int length2=doc.select("div.detail-content").get(0).html().length();
				info.setDescriptioncode(doc.select("div.detail-content").get(0).html().substring(0,length2>32767?32767:length2));// ��������
				info.setPackage_content(doc.select("div.box-content-html").text().toString());// ��װ
				info.setSellersku(doc.select("div.key-value").get(1).text().toString());// sku
				Elements trs =doc.select("span.key-title");
				
				for (int i=0;i<trs.size();i++) { //Compatibility by Model--�ֻ���
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
				ci.append("���ӣ�" + urlstring + "��"+failcount+"�λ�ȡʧ�ܣ���ʼ���»�ȡ:--------------  \n");
				else {
					ci.append("���ӣ�" + urlstring + "��"+failcount+"�λ�ȡʧ�ܣ���ʼ��һ���ռ�--------------  \n");
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
