package com.lazada.cn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;
import com.lazada.json.model.ItemListElement;
import com.lazada.json.model.JsonRootBean;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class CacthHandler {

	
	public final static String website="http://www.lazada.com.my/catalog/?q="; 
	private String keyword;
	private int startpage;
	private int endpage;
	private String savepath;
	private int exlRow;
	
	public static void main(String[] args) {
		
		try {
			new CacthHandler("women shoes",1,3,"C:\\Users\\Administrator\\Desktop\\123.xls").startCatching();
		} catch (WriteException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("出现IO异常");
			e.printStackTrace();
		}
	}
	
	public CacthHandler( String keyword, int startpage, int endpage, String savepath) {
		// TODO Auto-generated constructor stub
	    this.keyword=keyword;
	    this.startpage=startpage;
		if(endpage>100) endpage=100;
		this.endpage=endpage;
		this.savepath=savepath;
		
	}
    
	public void startCatching() throws IOException , RowsExceededException, WriteException{
		// TODO Auto-generated method stub
	//	OutputStream outputStream = new FileOutputStream(new File("E:\\buffer.txt"));
		this.exlRow = 0;
         OutputStream outputStream = new FileOutputStream(new File(
	     savepath));
	     WritableWorkbook workbook = null;
	     workbook = Workbook.createWorkbook(outputStream);
	     WritableSheet sheet = workbook.createSheet("web_data", 0);
	     sheet.addCell(new Label(0, this.exlRow, "标题"));
	     sheet.addCell(new Label(1, this.exlRow, "详情描述"));
	     sheet.addCell(new Label(2, this.exlRow, "卖点"));
	     sheet.addCell(new Label(3, this.exlRow, "卖价"));
	     sheet.addCell(new Label(4, this.exlRow, "特价"));
	     sheet.addCell(new Label(5, this.exlRow, "SKU"));
	     sheet.addCell(new Label(6, this.exlRow, "包装包括"));
	     sheet.addCell(new Label(7, this.exlRow, "产品本身链接"));
	     sheet.addCell(new Label(8, this.exlRow, "好评"));
	     sheet.addCell(new Label(9, this.exlRow, "图片1"));
	     sheet.addCell(new Label(10, this.exlRow, "图片2"));
	     sheet.addCell(new Label(11, this.exlRow, "图片3"));
	     sheet.addCell(new Label(12, this.exlRow, "图片4"));
	     sheet.addCell(new Label(13, this.exlRow, "图片5"));
	     sheet.addCell(new Label(14, this.exlRow, "图片6"));
	     sheet.addCell(new Label(15, this.exlRow, "图片7"));
	     sheet.addCell(new Label(16, this.exlRow++, "图片8"));
	    
		for(int i=0;i<endpage-startpage+1;i++){
			System.out.println("开始抓取第"+(i+1)+"页的内容。");
			getFirstLevel(String.valueOf(startpage+i),sheet);
		}
		outputStream.close();
	}
	private void getFirstLevel(String pagenum,WritableSheet sheet) throws IOException, RowsExceededException, WriteException{
		String urlstring=website+URLEncoder.encode(keyword, "utf-8") +"&page="+pagenum;
		URL url = new URL(urlstring);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection
				.addRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)");
		connection.setConnectTimeout(60000);
		connection.connect();

		InputStream is = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader reader = new BufferedReader(isr);
		
		StringBuffer lines = new StringBuffer("");
		String line=null;
		while ((line = reader.readLine()) != null) {
			
			if (!"".equalsIgnoreCase(line)&&line.contains("<script type=\"application/ld+json\">")) {
			//	lines.append(line+ "\n");
				line=line+"\n";
				line=line.replaceAll("@type", "type").replaceAll("@context", "context").substring(line.indexOf("{")).replace("</script>", "");
				Gson gson = new Gson();
				JsonRootBean info = gson.fromJson(line, JsonRootBean.class);//对于javabean直接给出class实例
				List<ItemListElement> ietlist=info.getItemListElement();
			    for(int i=0;i<ietlist.size();i++) {
			    	ItemListElement ietelement=ietlist.get(i);
			    	System.out.println("第"+pagenum+"页  "+"第"+(i+1)+"个详情:  "+ietelement.getUrl());
			    	getSencondLevel(ietelement.getUrl(),sheet);
			    }
			//	outputStream.write(line.getBytes());
				break;
			}
			
		}
		is.close();
		isr.close();
		reader.close();
		
	}
	
	private void getSencondLevel(String urlstring,WritableSheet sheet) throws IOException,RowsExceededException, WriteException{

        boolean iscomment=false;  //好评多行判断
        boolean isshortDes=false; //卖点多行判断
		String shortDes="";
		boolean istitle=false; //标题多行判断
		String title="";
		boolean ispackagecontent=false; //包装多行判断
		String packagecontent="";
		
        URL url = new URL(urlstring);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection
				.addRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)");
		connection.setConnectTimeout(60000);
		connection.connect();

		InputStream is = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader reader = new BufferedReader(isr);
		
		FinalInfo info=new FinalInfo();
		info.setLink(urlstring);
		
		StringBuffer lines = new StringBuffer("");
		String line=null;
		while ((line = reader.readLine()) != null) {
			
			//产品链接
			
			//标题
			if(!info.isGetName()&&line.contains("<title>")){
				info.setOneItemStart(true);
				if(line.contains("</title>")){
					info.setName(line.substring(line.indexOf("<title>")+7, line.indexOf("| Lazada Malaysia")));
					info.setGetName(true);
					continue;
				}
				else{
					title+=line+" ";
					istitle=true; 
					continue;
				}
			}
			if(info.isOneItemStart()&&!info.isGetName()&&istitle){
				
				boolean iscontain=line.contains("</title>");
				if(!iscontain){
					title+=line+" ";
				continue;
				}
				else if(iscontain){
					title+=line+" ";
					info.setName(line.substring(line.indexOf("<title>")+7, line.indexOf("| Lazada Malaysia")));
					istitle=false;
					title="";
					info.setGetName(true);
					continue;
				}
				else{
					System.out.println("其他情况！");
				}
				
			}
			//好评
			if(line.contains("<a class=\"prd-reviews\"")) {iscomment=true; continue;}
			if(info.isOneItemStart()&&!info.isGetComment()&&iscomment && line.contains("</a>")){
				info.setComment(line.replaceAll("</a>", "").trim());
		    	//lines.append(line.replaceAll("</a>", "").trim()+"\n");
				info.setGetComment(true);
		    	iscomment=false;
		    	continue;
			}
		  
            //图片
			if(info.isOneItemStart()&&!info.isGetMainImage()&&line.contains("data-big")){
				info.setMainimage(line.substring(line.indexOf("https"), line.indexOf("\" class")));
			//	lines.append(line.substring(line.indexOf("https"), line.indexOf("\" class"))+"\n");
				info.setGetMainImage(true);
		    	continue;    
			}
			
			//卖点
			if(info.isOneItemStart()&&!info.isGetShort_description()&&line.contains("prd-attributesList")){
				if(line.contains("</span></li></ul>")){
					info.setShort_description(line.substring(line.indexOf("<span>"), line.indexOf("</span></li></ul>")).replaceAll("</li>", "").replaceAll("<span>", "").replaceAll("</span>", "\n").replaceAll("<li class=\"\">", ""));
					info.setGetShort_description(true);
					continue;
				}
				else{
					shortDes+=line+" ";
					isshortDes=true; continue;
				}

			}
			if(info.isOneItemStart()&&!info.isGetShort_description()&&isshortDes){
				boolean iscontain=line.contains("</span></li></ul>");
				if(!iscontain){
					shortDes+=line+" ";
				continue;
				}
				else if(iscontain){
					shortDes+=line+" ";
					info.setShort_description(shortDes.substring(shortDes.indexOf("<span>"), shortDes.indexOf("</span></li></ul>")).replaceAll("</li>", "").replaceAll("<span>", "").replaceAll("</span>", "\n").replaceAll("<li class=\"\">", "")+"\n");
					isshortDes=false;
					shortDes="";
					info.setGetShort_description(true);
					continue;
				}
				else{
					System.out.println("其他情况！");
				}
			}
			//特价
			if(info.isOneItemStart()&&!info.isGetSpecial_price()&&line.contains("\"product_price\"")){
				info.setSpecial_price(line.substring(line.indexOf("\">")+2, line.indexOf("</span>")));
		    //	lines.append(line.substring(line.indexOf("\">")+2, line.indexOf("</span>"))+"\n");
		    	info.setGetSpecial_price(true);
				continue;
			}
			
			//卖价
			if(info.isOneItemStart()&&!info.isGetPrice()&&line.contains("<span id=\"price_box\">")){
				info.setPrice(line.substring(line.indexOf("\">")+2, line.indexOf("</span>")));
		   // 	lines.append(line.substring(line.indexOf("\">")+2, line.indexOf("</span>"))+"\n");
		    	info.setGetPrice(true);
				continue;
			}
			
			//描述
			if(info.isOneItemStart()&&!info.isGetDescription()&&line.contains("<div id=\"offer-template-0\">")) {
				info.setDescription(line.substring(line.indexOf("<ul> <li>"), line.indexOf("<p> <noscript>")-1).replaceAll("<ul>", "").replaceAll("<li>", "").replaceAll("</li>", "\n"));
		    //	lines.append(line.substring(line.indexOf("<ul> <li>"), line.indexOf("<p> <noscript>")-1).replaceAll("<ul>", "").replaceAll("<li>", "").replaceAll("</li>", "\n")+"\n");
		    	info.setGetDescription(true);
				continue;
			}

			//包装包括
			if(info.isOneItemStart()&&!info.isGetPackage_content()&&line.contains("inbox__item")&&!line.contains("<span></span>")){
				if(line.contains("</span></li>")){
					info.setPackage_content(line.substring(line.indexOf("<span>")+6, line.indexOf("</span>")));
					info.setGetPackage_content(true);
			    	if(!info.isGetDescription()){
			    		info.setDescription("");
			    		info.setGetDescription(true);
			    	}
					continue;
				}
				else{
					packagecontent+=line+" ";
					ispackagecontent=true; 
					continue;
				}
			}
			if(info.isOneItemStart()&&!info.isGetPackage_content()&&ispackagecontent){
				
				boolean iscontain=line.contains("</span></li>");
				if(!iscontain){
					packagecontent+=line+" ";
				continue;
				}
				else if(iscontain){
					packagecontent+=line+" ";
					info.setPackage_content(line.substring(packagecontent.indexOf("<span>")+6, packagecontent.indexOf("</span>")));
					ispackagecontent=false;
					packagecontent="";
					info.setGetPackage_content(true);
					if(!info.isGetDescription()){
			    		info.setDescription("");
			    		info.setGetDescription(true);
			    	}
					continue;
				}
				else{
					System.out.println("其他情况！");
				}
				
			}
			
			//SKU
			if(info.isOneItemStart()&&!info.isGetSellerSku()&&line.contains("<td id=\"pdtsku\"")){
				info.setSellersku(line.substring(line.indexOf("\">")+2, line.indexOf("</td>")));
		   // 	lines.append(line.substring(line.indexOf("\">")+2, line.indexOf("</td>"))+"\n");
		    	info.setGetSellerSku(true);
				continue;
			}
			
			if ((info.isOneItemStart()) && (info.isGetName())
					&& (info.isGetComment()) && (info.isGetMainImage())
					&& (info.isGetShort_description()) && (info.isGetSpecial_price())
					&& (info.isGetPrice()) && (info.isGetDescription())
					&& (info.isGetPackage_content())&& (info.isGetSellerSku())) {
				sheet.addCell(new Label(0, this.exlRow, info.getName()));
				sheet.addCell(new Label(1, this.exlRow, info.getDescription()));
				sheet.addCell(new Label(2, this.exlRow, info.getShort_description()));
				sheet.addCell(new Label(3, this.exlRow, info.getPrice()));
				sheet.addCell(new Label(4, this.exlRow, info.getSpecial_price()));
				sheet.addCell(new Label(5, this.exlRow, info.getPrice()));
				sheet.addCell(new Label(6, this.exlRow, info.getSellersku()));
				sheet.addCell(new Label(7, this.exlRow, info.getPackage_content()));
				sheet.addCell(new Label(8, this.exlRow, info.getLink()));
				sheet.addCell(new Label(9, this.exlRow, info.getComment()));
				sheet.addCell(new Label(10, this.exlRow, info.getMainimage()));
				sheet.addCell(new Label(11, this.exlRow, ""));
				sheet.addCell(new Label(12, this.exlRow, ""));
				sheet.addCell(new Label(13, this.exlRow, ""));
				sheet.addCell(new Label(14, this.exlRow, ""));
				sheet.addCell(new Label(15, this.exlRow, ""));
				sheet.addCell(new Label(16, this.exlRow++, ""));
				info.backToInit();
				break;
			}
			
		}
		is.close();
		isr.close();
		reader.close();
		
		
	}

}
