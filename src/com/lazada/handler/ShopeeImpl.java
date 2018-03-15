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
import org.apache.http.client.methods.HttpPost;
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
import com.lazada.model.json.shopee.first.FirstShopeeList;
import com.lazada.model.json.shopee.first.Items;
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

public class ShopeeImpl extends CheckboxModel implements PlatformService {

	public ShopeeImpl(int type, String typetext, int startpage, int endpage) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.typetext = typetext;
		this.startpage = startpage;
		this.endpage = endpage;
	}

	@Override
	public void startCatching(ConnectImpl connectImpl, int exlRow, WritableWorkbook workbook)
			throws RowsExceededException, WriteException, IOException {
		// 获取分类列表
		if (Constant.calMap == null || Constant.calMap.size() == 0) {
			getCategoryList();
//			connectImpl.append("成功获取虾皮产品分类！\n");
		}

		if (Constant.WORKPATH.equals("")) {// 得到当前路径，为了后面加载phantom.exe
			Constant.WORKPATH = System.getProperty("user.dir");
			System.out.println(System.getProperty("user.dir"));
		}

		switch (type) {

		case 1:// 关键词
			pageCatching(connectImpl, workbook, exlRow);
			break;
		case 2:// 店铺链接
			pageCatching(connectImpl, workbook, exlRow);
			break;
		case 3:// 产品链接
			getInfoByProductLink(connectImpl, typetext, workbook.getSheet(0), exlRow);
			connectImpl.append("抓取成功！请打开excel查看。\n");
			break;
		default:
			break;

		}

	}

	@Override
	public void pageCatching(ConnectImpl connectImpl, WritableWorkbook workbook, int exlRow) {

		try {
			for (int i = 0; i < endpage - startpage + 1; i++) {

				if (type == 1) // 关键词
					exlRow = getFirstLevel(connectImpl, Constant.SHOPEEITEMSSITE, String.valueOf(startpage + i),
							typetext, workbook.getSheet(0), exlRow);// 关键词

				else {// 店铺

					exlRow = getInfoByStoreLink(connectImpl, String.valueOf(startpage + i), typetext,
							workbook.getSheet(0), exlRow);// 店铺
				}

				Thread.sleep(6000);// 延迟6秒发送请求
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			connectImpl.append(e.getMessage());
			// this.paintImmediately(this.getBounds());
			e.printStackTrace();
		}

	}

	@Override
	public int getFirstLevel(ConnectImpl ci, String website, String pagenum, String keyword, WritableSheet sheet,
			int exlRow) throws IOException {
		boolean isKeyWord = !keyword.equals("");
		String urlstring = "";
		String cookiesurl = "";
		if (isKeyWord) {// 关键词
			cookiesurl = Constant.SHOPEEKEYWORDSITE + URLEncoder.encode(keyword, "utf-8") + "&page="
					+ (Integer.parseInt(pagenum) - 1);
			urlstring = website + URLEncoder.encode(keyword, "utf-8") + "&newest="
					+ (Integer.parseInt(pagenum) - 1) * 50;
		}

		else // keyword为空表示不是关键词搜索，有可能是店铺或者其他
			cookiesurl = website + "?page=" + (Integer.parseInt(pagenum) - 1);

		if (Constant.shopeecookies.equals("") && Constant.shopeecsrftoken.equals("")) {
			ci.append("虾皮采集需要时间，请耐心等候:--------------  \n");
			ci.append("正在初始化工作:--------------  \n");
			ci.append("加载容器中:--------------  \n");
			// 获得cookies
			getCookies(cookiesurl);
			ci.append("初始化成功，下面开始获取资源--------------  \n");
		}


		// 店铺采集需要先通过名字获取id，再拼接items请求地址
		if (!isKeyWord) {

			StringEntity stringEntity = new StringEntity(
					"{\"usernames\":[\"" + website.substring(website.indexOf(".my/") + 4) + "\"]}", "utf-8");// {"usernames":["healthystyle.my"]}
			stringEntity.setContentEncoding("UTF-8");
			String shopidjson = postData(Constant.SHOPEESHIDBYNAMESITE, Constant.shopeecookies, stringEntity, website,
					Constant.shopeecsrftoken);// [{"healthystyle.my": 6765824}]
			String shopid = shopidjson.substring(shopidjson.indexOf(":") + 1, shopidjson.lastIndexOf("}")).trim();
			System.out.println(shopid);
			urlstring = Constant.SHOPEEITEMSSITEBYSHOPID + "&newest=" + (Integer.parseInt(pagenum) - 1) * 30
					+ "&match_id=" + shopid;
		}

//		System.out.println(urlstring);
		ci.append("正在获取第" + pagenum + "页的数据：  " + urlstring + "\n");

		String respString = null;
		while (respString == null) {
			respString = getDoc(urlstring);
			if (respString != null) {
				try {
					// Gson gson=new Gson();

					// 店铺和关键词通用采集-》先获取items列表，再post列表数据得到更多内容
					StringEntity stringEntity = new StringEntity("{"
							+ respString.substring(respString.indexOf("items") - 1).replace("items", "item_shop_ids"),
							"utf-8");// 解决中文乱码问题
					stringEntity.setContentEncoding("UTF-8");

					String finaljson = postData(Constant.SHOPEEPOSTSITE, Constant.shopeecookies, stringEntity,
							cookiesurl, Constant.shopeecsrftoken);
					System.out.println("finaljson串："+finaljson);
					exlRow = getFisrtLevelLink(finaljson, ci, sheet, pagenum, exlRow);

					/**
					 * 通过调用每个item api
					 */
					// System.out.println(respString);
					// FirstShopeeList firstShopeeList=gson.fromJson(respString,
					// FirstShopeeList.class);
					// List<Items> items=firstShopeeList.getItems();
					// for(int i=0;i<items.size();i++) {
					//
					// String itemurlstring=Constant.SHOPEESIDE + "-i." + items.get(i).getShopid() +
					// "."
					// +items.get(i).getItemid();
					// exlRow = getDetailInfo(ci, itemurlstring, sheet, exlRow,null);
					// ci.append("第" + pagenum + "页数据 第" +i+ "个产品获取成功！ \n");
					// }

					ci.append("第" + pagenum + "页数据  " + "获取成功！ \n");

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
					ci.append(e.getMessage());
					ci.paintImmediately(ci.getBounds());
				}
			} else {
				ci.append("第" + pagenum + "页数据  " + "获取失败，开始重新获取:--------------  \n");
				// this.paintImmediately(this.getBounds());
			}
		}
		return exlRow;
	}

	/**
	 * 获取shopee产品分类总列表
	 */
	private void getCategoryList() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(Constant.SHOPEECATEGORYSITE);
			CloseableHttpResponse response = httpclient.execute(httpget);
			String web = "";
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				if (entity != null) {
					web = EntityUtils.toString(entity, "UTF-8");
				}

				Gson gson = new Gson();
				CategoryList categoryList = gson.fromJson("{\"calist\":" + web + "}", CategoryList.class);// 对于javabean直接给出class实例
				if (Constant.calMap == null || Constant.calMap.size() == 0) {
					Constant.calMap = new HashMap<Integer, String>();
					for (int i = 0; i < categoryList.getCalist().size(); i++) {// 一层id

						List<Sub> sub = categoryList.getCalist().get(i).getSub();
						String firstname = categoryList.getCalist().get(i).getMain().getDisplay_name();

						for (int j = 0; j < sub.size(); j++) {

							List<Sub_sub> sub_sub = sub.get(j).getSub_sub();
							String secondname = sub.get(j).getDisplay_name();
							for (int k = 0; k < sub_sub.size(); k++) {
								Constant.calMap.put(sub_sub.get(k).getCatid(),
										firstname + ">" + secondname + ">" + sub_sub.get(k).getDisplay_name());
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
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public int getFisrtLevelLink(String json, ConnectImpl ci, WritableSheet sheet, String pagenum, int exlRow)
			throws RowsExceededException, WriteException {
		// Json的解析类对象
		JsonParser parser = new JsonParser();
		// 将JSON的String 转成一个JsonArray对象
		JsonArray jsonArray = parser.parse(json).getAsJsonArray();
		Gson gson = new Gson();
		ArrayList<ShopeeModel> shopeeModellist = new ArrayList<>();
		FinalInfo info = new FinalInfo();
		System.out.println("总共有："+jsonArray.size());
		// 加强for循环遍历JsonArray
		for ( int j=0;j<jsonArray.size();j++) {
			JsonElement model=jsonArray.get(j);
			// 使用GSON，直接转成Bean对象
			ShopeeModel shopeeModel = gson.fromJson(model.toString(), ShopeeModel.class);
			shopeeModellist.add(shopeeModel);
			// System.out.println(shopeeModel.getItemid());
			info.setLink(Constant.SHOPEESIDE + shopeeModel.getName() + "-i." + shopeeModel.getShopid() + "."
					+ shopeeModel.getItemid());// 链接
			info.setName(shopeeModel.getName());// 标题
			info.setComment(String.valueOf(shopeeModel.getRating_star()));// 好评
			info.setDiscuss(String.valueOf(shopeeModel.getCmt_count()));// 评论
			info.setSalenum(String.valueOf(shopeeModel.getLiked_count()));// 喜欢数
			String elimagestring = "";
			List<String> elimage = shopeeModel.getImage_list();
			info.setMainimage(Constant.SHOPEEPICSITE + shopeeModel.getImage());// 图片
			for (int i = 1; i < (elimage.size() > 8 ? 8 : elimage.size() - 1); i++) {
				Class clazz = info.getClass();
				Method m;
				try {
					m = clazz.getMethod("setImage" + (i + 1), String.class);
					m.invoke(info, Constant.SHOPEEPICSITE + elimage.get(i));
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {

					e.printStackTrace();
				}

			}
			info.setSpecial_price(String.format("%.2f", (shopeeModel.getPrice() / 100000)));// 特价
			info.setPrice(String.format("%.2f", (shopeeModel.getPrice_before_discount() / 100000)));// 实价
			String itemString = "";
			itemString = getItemJsonForShopee(Constant.SHOPEEITEMSITE + "item_id=" + shopeeModel.getItemid()
					+ "&shop_id=" + shopeeModel.getShopid()).toString();
			while (itemString == null || itemString.equals("")) {
				ci.append("产品：" + info.getLink() + "获取失败，开始重新获取:--------------  \n");
				itemString = getItemJsonForShopee(Constant.SHOPEEITEMSITE + "item_id=" + shopeeModel.getItemid()
						+ "&shop_id=" + shopeeModel.getShopid()).toString();
			}
			ShopeeItem shopeeItem = gson.fromJson(itemString, ShopeeItem.class);

			String descAll = shopeeItem.getDescription();
			if (descAll.contains("#")) {
				String maidian = descAll.substring(descAll.indexOf("#"));
				info.setShort_description(maidian);// 买点
				info.setShort_descriptioncode(maidian.replace("#", "li class="));// 买点代码
				descAll = descAll.substring(0, descAll.indexOf("#"));
			}
			info.setDescription(descAll);// 描述
			info.setCategory(Constant.calMap.get(shopeeModel.getThird_catid()));// 分类
			info.setOneItemStart(true);
			if (info.isOneItemStart()) {
//				System.out.println("成功获取链接：" + info.getLink());
				handleOneItem(sheet, exlRow, info);
				exlRow++;
				ci.append("成功获取第" + (j+1) + "个产品："+info.getLink()+" \n");
				info.backToInit();
			}
			
		}

		Constant.areadycatchnum += shopeeModellist.size();

		return exlRow;
	}

	/**
	 * 产品详情
	 */
	@Override
	public int getDetailInfo(ConnectImpl ci, String urlstring, WritableSheet sheet, int exlRow, ListItems ietelement)
			throws RowsExceededException, WriteException {
		FinalInfo info = new FinalInfo();
		Gson gson = new Gson();
		if (Constant.shopeecookies.equals("") && Constant.shopeecsrftoken.equals("")) {
			ci.append("虾皮采集需要时间，请耐心等候:--------------  \n");
			ci.append("正在初始化工作:--------------  \n");
			ci.append("加载容器中:--------------  \n");
			// 获得cookies
			getCookies(urlstring);
			ci.append("初始化成功，下面开始获取资源--------------  \n");
		}

		String itemString = "";
		String itemurel = Constant.SHOPEEITEMSITE + "item_id="
				+ urlstring.substring(urlstring.lastIndexOf(".") + 1).trim() + "&shop_id="
				+ urlstring.substring(urlstring.indexOf("i.") + 2, urlstring.lastIndexOf(".")).trim();
		itemString = getItemJsonForShopee(itemurel);
		while (itemString == null || itemString.equals("")) {
			ci.append("产品：" + urlstring + "获取失败，开始重新获取:--------------  \n");
			itemString = getItemJsonForShopee(itemurel);
		}
		System.out.println(itemString);
		ShopeeItem shopeeItem = gson.fromJson(itemString, ShopeeItem.class);

		info.setLink(urlstring);// 链接

		info.setName(shopeeItem.getName());// 标题
		info.setComment(String.valueOf(shopeeItem.getRating_star()));// 好评
		info.setDiscuss(String.valueOf(shopeeItem.getCmt_count()));// 评论
		info.setSalenum(String.valueOf(shopeeItem.getLiked_count()));// 喜欢数
		String elimagestring = "";
		List<String> elimage = shopeeItem.getImage_list();
		info.setMainimage(Constant.SHOPEEPICSITE + shopeeItem.getImage());// 图片
		for (int i = 1; i < (elimage.size() > 8 ? 8 : elimage.size() - 1); i++) {
			Class clazz = info.getClass();
			Method m;
			try {
				m = clazz.getMethod("setImage" + (i + 1), String.class);
				m.invoke(info, Constant.SHOPEEPICSITE + elimage.get(i));
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {

				e.printStackTrace();
			}

		}
		if (shopeeItem.getPrice() != null)
			info.setSpecial_price(String.format("%.2f", (Double.valueOf(shopeeItem.getPrice()) / 100000)));// 特价
		if (shopeeItem.getPrice_before_discount() != null)
			info.setPrice(String.format("%.2f", (Double.valueOf(shopeeItem.getPrice_before_discount()) / 100000)));// 实价
		String descAll = shopeeItem.getDescription();
		if (descAll.contains("#")) {
			String maidian = descAll.substring(descAll.indexOf("#"));
			info.setShort_description(maidian);// 买点
			info.setShort_descriptioncode(maidian.replace("#", "li class="));// 买点代码
			descAll = descAll.substring(0, descAll.indexOf("#"));
		}
		info.setDescription(descAll);// 描述
		info.setCategory(Constant.calMap.get(shopeeItem.getThird_catid()));// 分类
		if (Constant.calMap.get(shopeeItem.getThird_catid()) == null)
			info.setCategory("other");// 分类
		info.setOneItemStart(true);
		if (info.isOneItemStart()) {
			handleOneItem(sheet, exlRow, info);
			exlRow++;
			info.backToInit();
		}
		return exlRow;
	}

	@Override
	public int getInfoByStoreLink(ConnectImpl connectImpl, String pagenum, String storelink, WritableSheet sheet,
			int exlRow) throws IOException {
		return getFirstLevel(connectImpl, storelink, pagenum, "", sheet, exlRow);
	}

	@Override
	public int getInfoByProductLink(ConnectImpl connectImpl, String links, WritableSheet sheet, int exlRow)
			throws IOException, RowsExceededException, WriteException {
		String[] arry = links.split(" ");
		for (int i = 0; i < arry.length; i++) {
			exlRow = getDetailInfo(connectImpl, arry[i], sheet, exlRow, null);
			connectImpl.append("抓取成功:" + arry[i] + "\n");
		}
		return exlRow;
	}

	public static void getCookies(String url) {

		long starttime = System.currentTimeMillis();
		System.setProperty("phantomjs.binary.path",
				"C:\\Users\\Administrator\\Desktop\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		WebDriver driver = new PhantomJSDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(url);

		long endtime = System.currentTimeMillis();
		System.out.println("初始化时间：" + (endtime - starttime));
		// WebElement webElement = driver.findElement(By.id("video-player"));
		// System.out.println(driver.getPageSource());
		Set<Cookie> cookies = driver.manage().getCookies();
		Iterator iterator = cookies.iterator();
		while (iterator.hasNext()) {
			Cookie cookie = (Cookie) iterator.next();
			if (cookie.getName().equals("csrftoken"))
				Constant.shopeecsrftoken = cookie.getValue();
			Constant.shopeecookies += cookie.getName() + "=" + cookie.getValue() + ";";
//			System.out.println(cookie.getName());
//			System.out.println(cookie.getValue());
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
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(4000)// 读取超时 readtime-out
				.setConnectTimeout(4000)// 连接超时
				.setConnectionRequestTimeout(4000).build();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String web = "";
		try {
			HttpGet httpget = new HttpGet(url);
			// RequestConfig requestConfig =
			// RequestConfig.custom().setSocketTimeout(6*1000).build();
			httpget.setConfig(defaultRequestConfig);
//			httpget.setHeader("x-api-source","pc");
//			httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
			// if(url.contains("page=1")) {
			// Header[] header=httpget.getAllHeaders();
			// for(int i=0;i<header.length;i++) {
			// System.out.println(header[i].toString());
			// }
			// }
			// BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
			// "B960BA73258DB81B7204FEE3A5A78CA3");
			// cookie.setVersion(0);
			// cookie.setDomain("/pms/"); //设置范围
			// cookie.setPath("/");
			// cookieStore.addCookie(cookie);
			CloseableHttpResponse response = httpclient.execute(httpget);
			long endTime = System.currentTimeMillis();
//			System.out.println("此次请求总耗时： " + (endTime - startTime));
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
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

	public static void handleOneItem(WritableSheet sheet, int exlRow, BaseInfo info)
			throws RowsExceededException, WriteException {
		int index = 0;
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

	public static String postData(String url, String cookieString, StringEntity json, String refer, String xcsrftoken) {
		HttpPost httppost = new HttpPost(url);
		httppost.addHeader("x-api-source","pc");
		httppost.addHeader("referer", refer);
		httppost.addHeader("x-csrftoken", xcsrftoken);
		httppost.setHeader("cookie", cookieString);
//		System.out.println(url);
//		System.out.println(xcsrftoken);
//		System.out.println(cookieString);
		// entity.setContentType("text");
		httppost.setEntity(json);

		CloseableHttpClient httpclient = HttpClients.createDefault();

		CloseableHttpResponse response;

		String web = "";
		try {
			response = httpclient.execute(httppost);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			if (entity != null) {
				web = EntityUtils.toString(entity, "UTF-8");
//				System.out.println(web);
				// System.out.println("{"+web.substring(web.indexOf("items")-1).replace("items",
				// "item_shop_ids"));

			}
			response.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {

				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return web;

	}

	public static String getItemJsonForShopee(String url) {

		long startTime = System.currentTimeMillis();
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(4000)// 读取超时 readtime-out
				.setConnectTimeout(4000)// 连接超时
				.setConnectionRequestTimeout(4000).build();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String web = "";
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(defaultRequestConfig);
			httpget.addHeader("if-none-match-", "123456abcde");
			httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
			CloseableHttpResponse response = httpclient.execute(httpget);

			long endTime = System.currentTimeMillis();
//			System.out.println("此次请求总耗时： " + (endTime - startTime));

			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
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
}
