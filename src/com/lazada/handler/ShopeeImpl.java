package com.lazada.handler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lazada.model.Constant;
import com.lazada.model.json.firstlevel.FirstLevelJsonRootBean;
import com.lazada.model.json.firstlevel.ListItems;
import com.lazada.model.json.shopee.category.CategoryList;
import com.lazada.model.json.shopee.category.Sub;
import com.lazada.model.json.shopee.category.Sub_sub;
import com.lazada.model.json.shopee.item.ShopeeItem;
import com.lazada.model.json.shopee.items.ShopeeModel;
import com.lazada.model.product.BaseInfo;
import com.lazada.model.product.FinalInfo;
import com.lazada.util.BasicUtils;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sourceforge.htmlunit.corejs.javascript.ConsString;

public class ShopeeImpl extends CheckboxModel implements PlatformService{

	
	public ShopeeImpl(int type, String typetext,int startpage,int endpage) {
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
					 exlRow=getFirstLevel(connectImpl, Constant.SHOPEEITEMSSITE, String.valueOf(startpage), typetext, workbook.getSheet(0), exlRow);//�ؼ���
				
				else {//����
					
					exlRow=getInfoByStoreLink(connectImpl,String.valueOf(startpage), typetext, workbook.getSheet(0), exlRow);//����
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
		
		if(Constant.calMap==null ||Constant.calMap.size()==0)
			 getCategoryList();
		String urlstring = "";
		String cookiesurl="";
		if(!keyword.equals("")) {//�ؼ���
			cookiesurl=Constant.SHOPEEKEYWORDSITE+ URLEncoder.encode(keyword, "utf-8")+ "&page="+(Integer.parseInt(pagenum)-1);
			urlstring = website + URLEncoder.encode(keyword, "utf-8") + "&newest=" + (Integer.parseInt(pagenum)-1)*50;
		}
			

		else  //keywordΪ�ձ�ʾ���ǹؼ����������п����ǵ��̻�������
		{
		cookiesurl= website+ "&page=" + (Integer.parseInt(pagenum)-1);
		
		urlstring = website+ "&page=" + (Integer.parseInt(pagenum)-1);
		}
		
		if(Constant.shopeecookies.equals("")&&Constant.shopeecsrftoken.equals("")) {
			
			ci.append("���ڳ�ʼ������:--------------  \n");
			//���cookies
			   getCookies(cookiesurl);
		}
		System.out.println(urlstring);	
		ci.append("���ڻ�ȡ��" + pagenum + "ҳ�����ݣ�  "  + urlstring+ "\n");
		String respString = null;
		while (respString == null) {
			respString = getDoc(urlstring);
			if(respString!=null) {
		  try {
		      StringEntity stringEntity = new StringEntity("{"+respString.substring(respString.indexOf("items")-1).replace("items", "item_shop_ids"),"utf-8");//���������������    
		      stringEntity.setContentEncoding("UTF-8");   
		      
		      String finaljson=BasicUtils.postData(Constant.SHOPEEPOSTSITE, Constant.shopeecookies, stringEntity, cookiesurl, Constant.shopeecsrftoken);
			  
		      exlRow=getFisrtLevelLink (finaljson,ci, sheet, pagenum, exlRow);
			  
		      ci.append("��" + pagenum + "ҳ����  " + "��ȡ�ɹ��� \n");
		      
				}catch(Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
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
	
	/**
	 * ��ȡshopee��Ʒ�������б�
	 */
	private void getCategoryList() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(Constant.SHOPEECATEGORYSITE);
			CloseableHttpResponse response = httpclient.execute(httpget);
			String web="";
            try {
                // ��ȡ��Ӧʵ��
                HttpEntity entity = response.getEntity();
                // ��ӡ��Ӧ״̬
                if (entity != null) {
                	web= EntityUtils.toString(entity,"UTF-8");
                }
             	
             	Gson gson = new Gson();
                CategoryList categoryList = gson.fromJson("{\"calist\":"+web+"}",CategoryList.class);// ����javabeanֱ�Ӹ���classʵ��
                
                System.out.println(categoryList.getCalist().size());
                if(Constant.calMap==null || Constant.calMap.size()==0) {
                	Constant.calMap=new HashMap<Integer,String>();
                	for(int i=0;i<categoryList.getCalist().size();i++) {//һ��id
                		
                		List<Sub> sub=categoryList.getCalist().get(i).getSub();
                		String firstname=categoryList.getCalist().get(i).getMain().getDisplay_name();
                		
                		for(int j=0;j<sub.size();j++) {
                			
                			List<Sub_sub> sub_sub=sub.get(j).getSub_sub();
                			String secondname=sub.get(j).getDisplay_name();
                			for(int k=0;k<sub_sub.size();k++) {
                				Constant.calMap.put(sub_sub.get(k).getCatid(),firstname+">"+secondname+ ">"+sub_sub.get(k).getDisplay_name());
                			}
                		}
                	}	
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // �ر�����,�ͷ���Դ
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       
	}

	public int getFisrtLevelLink(String json,ConnectImpl ci, WritableSheet sheet, String pagenum, int exlRow) throws RowsExceededException, WriteException {
		 //Json�Ľ��������
	    JsonParser parser = new JsonParser();
	    //��JSON��String ת��һ��JsonArray����
	    JsonArray jsonArray = parser.parse(json).getAsJsonArray();
		Gson gson = new Gson();
		ArrayList<ShopeeModel> shopeeModellist = new ArrayList<>();
		 FinalInfo info = new FinalInfo();
		    //��ǿforѭ������JsonArray
		    for (JsonElement model : jsonArray) {
		        //ʹ��GSON��ֱ��ת��Bean����
		    	ShopeeModel shopeeModel = gson.fromJson(model.toString(), ShopeeModel.class);
		    	shopeeModellist.add(shopeeModel);
//		    	System.out.println(shopeeModel.getItemid());
		    	info.setLink(Constant.SHOPEESIDE+shopeeModel.getName()+"-i."+shopeeModel.getShopid()+"."+shopeeModel.getItemid());// ����	
				info.setName(shopeeModel.getName());// ����
				info.setComment(String.valueOf(shopeeModel.getRating_star()));// ����
				info.setDiscuss(String.valueOf(shopeeModel.getCmt_count()));// ����
				info.setSalenum(String.valueOf(shopeeModel.getLiked_count()));// ϲ����
				String elimagestring = "";
				List<String> elimage = shopeeModel.getImage_list();
				info.setMainimage(Constant.SHOPEEPICSITE+shopeeModel.getImage());// ͼƬ
				for (int i = 1; i < (elimage.size() > 8 ? 8 : elimage.size() - 1); i++) {
					Class clazz = info.getClass();
					Method m;
					try {
						m = clazz.getMethod("setImage" + (i + 1), String.class);
						m.invoke(info, Constant.SHOPEEPICSITE+elimage.get(i));
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {

						e.printStackTrace();
					}

				}
				info.setSpecial_price(String.format("%.2f", (shopeeModel.getPrice()/100000)));// �ؼ�
				info.setPrice(String.format("%.2f", (shopeeModel.getPrice_before_discount()/100000)));// ʵ��
				String itemString="";
				itemString=BasicUtils.getItemJsonForShopee(Constant.SHOPEEITEMSITE+"item_id="+shopeeModel.getItemid()+"&shop_id="+shopeeModel.getShopid()).toString();
				while(itemString==null||itemString.equals("")) {
					ci.append("��Ʒ��"+info.getLink() + "��ȡʧ�ܣ���ʼ���»�ȡ:--------------  \n");
					itemString=BasicUtils.getItemJsonForShopee(Constant.SHOPEEITEMSITE+"item_id="+shopeeModel.getItemid()+"&shop_id="+shopeeModel.getShopid()).toString();
				}
				ShopeeItem shopeeItem= gson.fromJson(itemString, ShopeeItem.class);
				
				String descAll=shopeeItem.getDescription();
				if(descAll.contains("#")) {
					String maidian=descAll.substring(descAll.indexOf("#"));
					info.setShort_description(maidian);//���
					info.setShort_descriptioncode(maidian.replace("#", "li class="));//������
					descAll=descAll.substring(0, descAll.indexOf("#"));
				}
				info.setDescription(descAll);// ����
				info.setCategory(Constant.calMap.get(shopeeModel.getThird_catid()));//����
				info.setOneItemStart(true);
		    	if (info.isOneItemStart()) {
		    		System.out.println("�ɹ���ȡ���ӣ�"+info.getLink());
					BasicUtils.handleOneItem(sheet, exlRow,info);
					exlRow++;
					info.backToInit();
				}
		    }
		    
		Constant.areadycatchnum += shopeeModellist.size();
		
		return exlRow;
	}
	@Override
	public int getDetailInfo(ConnectImpl ci, String urlstring, WritableSheet sheet, int exlRow, ListItems ietelement)
			throws RowsExceededException, WriteException {
		FinalInfo info = new FinalInfo();
		Document doc = null;
		while (doc == null) {
			doc = BasicUtils.getDoc(urlstring);
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
				BasicUtils.handleOneItem(sheet, exlRow,info);
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
	
	
	public static void getCookies(String url) {
		
		long starttime=System.currentTimeMillis(); 
		        System.setProperty("phantomjs.binary.path", "C:\\Users\\Administrator\\Desktop\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		        WebDriver driver = new PhantomJSDriver();
		        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		        driver.get(url);
		        
		        long endtime=System.currentTimeMillis(); 
		        System.out.println("��ʼ��ʱ�䣺"+(endtime-starttime));
//		        WebElement webElement = driver.findElement(By.id("video-player"));
//		        System.out.println(driver.getPageSource());
		        Set<Cookie> cookies = driver.manage().getCookies(); 
		        Iterator iterator=cookies.iterator();
		        while(iterator.hasNext()) {
		        	    Cookie cookie=(Cookie) iterator.next();
		        	    if(cookie.getName().equals("csrftoken"))
		        	    Constant.shopeecsrftoken=cookie.getValue();
		        	    Constant.shopeecookies+=cookie.getName()+"="+cookie.getValue()+";";
		        	   System.out.println(cookie.getName());
		        	   System.out.println(cookie.getValue());
		        }
		        
		        driver.close();
		        driver.quit();

	}

	@Override
	public int getFisrtLevelLink(ConnectImpl ci, Document doc, WritableSheet sheet, String pagenum, int exlRow)
			throws RowsExceededException, WriteException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public static String getDoc(String url) {

		long startTime = System.currentTimeMillis();
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(4000)// ��ȡ��ʱ readtime-out
				.setConnectTimeout(4000)// ���ӳ�ʱ
				.setConnectionRequestTimeout(4000).build();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String web = "";
		try {
			HttpGet httpget = new HttpGet(url);
			// RequestConfig requestConfig =
			// RequestConfig.custom().setSocketTimeout(6*1000).build();
			httpget.setConfig(defaultRequestConfig);
			httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
			// if(url.contains("page=1")) {
			// Header[] header=httpget.getAllHeaders();
			// for(int i=0;i<header.length;i++) {
			// System.out.println(header[i].toString());
			// }
			// }
			// BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
			// "B960BA73258DB81B7204FEE3A5A78CA3");
			// cookie.setVersion(0);
			// cookie.setDomain("/pms/"); //���÷�Χ
			// cookie.setPath("/");
			// cookieStore.addCookie(cookie);
			CloseableHttpResponse response = httpclient.execute(httpget);
			long endTime = System.currentTimeMillis();
			System.out.println("�˴������ܺ�ʱ�� " + (endTime - startTime));
			try {
				// ��ȡ��Ӧʵ��
				HttpEntity entity = response.getEntity();
				// ��ӡ��Ӧ״̬
				if (entity != null) {
					web = EntityUtils.toString(entity, "UTF-8");
					
					return web;

				}
			} finally {
				response.close();
				httpclient.close();
			}
		} catch (org.apache.http.NoHttpResponseException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (java.net.UnknownHostException u) {
			u.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return web;
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
}
