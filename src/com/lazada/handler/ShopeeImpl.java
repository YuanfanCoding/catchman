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

public class ShopeeImpl extends CheckboxModel implements PlatformService{

	
	public ShopeeImpl(int type, String typetext) {
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

	@Override
	public void pageCatching(ConnectImpl connectImpl, WritableWorkbook workbook, int exlRow) {
		
		try {
			for (int i = 0; i < endpage - startpage + 1; i++) {
				connectImpl.append("��ʼץȡ��" + (i + startpage) + "ҳ�����ݡ�\n");
				
				if(type==1) //�ؼ���
					 exlRow=getFirstLevel(connectImpl, Constant.SHOPEEITEMSSITE, String.valueOf(startpage + i-1), typetext, workbook.getSheet(0), exlRow);//�ؼ���
				
				else {//����
					
					exlRow=getInfoByStoreLink(connectImpl,String.valueOf(startpage + i-1), typetext, workbook.getSheet(0), exlRow);//����
				}
				
				
				Thread.sleep(6000);// �ӳ�6�뷢������
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
		if(!keyword.equals("")) //�ؼ���
			urlstring = website + URLEncoder.encode(keyword, "utf-8") + "&newest=" + Integer.parseInt(pagenum)*50;

		else  //keywordΪ�ձ�ʾ���ǹؼ����������п����ǵ��̻�������
		
		urlstring = website+ "&page=" + pagenum;
		System.out.println(urlstring);	
		Document doc = null;
		while (doc == null) {
			doc = ExcelUtil.getDoc(urlstring);
			if(doc!=null) {
		  try {
			  
			  doc.toString();
			  
			  
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
			ci.append("��" + pagenum + "ҳ  " + "��" + (i + 1) + "������:  " + ietelement.getProductUrl() + "\n");
			System.out.println(ietelement.getProductUrl());
			exlRow = getDetailInfo(ci, "https:" + ietelement.getProductUrl(), sheet, exlRow, ietelement);
			ietelement.getLocation();
		}
		return exlRow;
	}
	@Override
	public int getDetailInfo(ConnectImpl ci, String urlstring, WritableSheet sheet, int exlRow, ListItems ietelement)
			throws RowsExceededException, WriteException {
		FinalInfo info = new FinalInfo();
		Document doc = null;
		while (doc == null) {
			doc = ExcelUtil.getDoc(urlstring);
			if (doc != null) {
//				System.out.println(doc.title().substring(0, doc.title().indexOf("| Lazada Malaysia")));
				info.setLink(urlstring);// ����	
				if(ietelement!=null)
				info.setLocation(ietelement.getLocation());// ���������ڵ�һҳ����������ץȡ
				info.setName(doc.title().substring(0, doc.title().indexOf("| Lazada Malaysia")));// ����
				info.setComment(doc.getElementsByClass("prd-reviews").get(0).text().toString().trim()
						.replace("(", "").replace(")", ""));// ����
				info.setBrand(doc.select("div.prod_header_brand_action").get(0).text().toString());// Ʒ��
				info.setStore(doc.getElementsByClass("basic-info__name").get(0).text().toString());// ������
				String category = "";
				Elements categorylist = doc.select("span.breadcrumb__item-text");
				if (categorylist != null && !categorylist.isEmpty()) {
					for (int i = 0; i < categorylist.size() - 1; i++)
						category += categorylist.get(i).text().toString() + "/";
				}
				info.setCategory(category);// ����
				String elimagestring = "";
				Elements elimage = doc.getElementsByClass("productImage");
				info.setMainimage(elimage.get(0).attr("data-big").toString());// ͼƬ
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
				info.setShort_description(elsdstring);// ����
				int length1=doc.getElementsByClass("prd-attributesList").html().length();
				info.setShort_descriptioncode(doc.getElementsByClass("prd-attributesList").html().substring(0, length1>32767?32767:length1));// �������
				String size = "";
				Elements sizelist = doc.select("span.grouped-size__popup__tab__content__item__size-item");
				if (sizelist != null && !sizelist.isEmpty()) {
					size += doc.getElementsByClass(
							"grouped-size__popup__tab__header__item grouped-size__popup__tab__header__item_state_active")
							.text().toString() + " \n";
					for (int i = 0; i < sizelist.size(); i++)
						size += sizelist.get(i).text().toString() + " \n";
				}
				info.setSize(size);// �ߴ�
				info.setSpecial_price(doc.select("span#product_price").text().toString());// �ؼ�
				info.setPrice(
						doc.select("span#price_box").text().toString().replace(",", "").replace("RM", "").trim());// ʵ��
				info.setDescription(doc.select("div.product-description__block").get(0).text().toString());// ����
				int length2=doc.select("div.product-description__block").get(0).html().length();
				info.setDescriptioncode(doc.select("div.product-description__block").get(0).html().substring(0,length2>32767?32767:length2));// ��������
				info.setPackage_content(doc.select("li.inbox__item").text().toString().replaceAll("<ul>", "")
						.replaceAll("</ul>", "").replaceAll("<li>", "").replaceAll("</li>", "")
						.replaceAll("<p>", "").replaceAll("</p>", ""));// ��װ
				info.setSellersku(doc.select("td#pdtsku").text().toString());// sku
				info.setOneItemStart(true);
					
			} else 
				ci.append("���ӣ�" + urlstring + "��ȡʧ�ܣ���ʼ���»�ȡ:--------------  \n");

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
			connectImpl.append("ץȡ�ɹ�:"+arry[i]+"\n");
			
		}
		return exlRow;
	}
	
	
}
