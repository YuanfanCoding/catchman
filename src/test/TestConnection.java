package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.lazada.model.json.detail.ItemListElement;
import com.lazada.model.json.detail.JsonRootBean;


public class TestConnection {

	public static void main(String[] args) throws IOException {
		boolean iscomment=false;
		boolean isshortDes=false;
		String shortDes="";
		boolean ispackagecontent=false; //包装多行判断
		String packagecontent="";
		
		String urlstring="https://www.lazada.com.my/converse-553417c-chuck-taylor-all-star-dainty-peached-oxwomenblackbiscuit-21447654.html";
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
		
		OutputStream outputStream = new FileOutputStream(new File("E:\\buffer.txt"));
				
		StringBuffer lines = new StringBuffer("");
		String line=null;
		while ((line = reader.readLine()) != null) {
			
			//产品链接
		//	lines.append(urlstring);
			
			
			//标题
//			if(line.contains("<title>")){
//				lines.append(line.substring(line.indexOf("<title>")+7, line.indexOf("| Lazada Malaysia"))+"\n");
//				continue;
//			}
			
			//好评
			if(line.contains("<a class=\"prd-reviews\"")) {iscomment=true; continue;}
			if(iscomment && line.contains("</a>")){
		    	lines.append(line.replaceAll("</a>", "").trim()+"\n");
		    	iscomment=false;
		    	continue;
			}
		  
            //图片
			if(line.contains("data-big")){
				lines.append(line.substring(line.indexOf("https"), line.indexOf("\" class"))+"\n");
		    	continue;    
			}
			
			//卖点
			if(line.contains("prd-attributesList")) {
					if(line.contains("</span></li></ul>")){
						lines.append(line.substring(line.indexOf("<span>"), line.indexOf("</span></li></ul>")).replaceAll("</li>", "").replaceAll("<span>", "").replaceAll("</span>", "\n").replaceAll("<li class=\"\">", "")+"\n");
				    	System.out.println(line.substring(line.indexOf("<span>"), line.indexOf("</span></li></ul>")).replaceAll("</li>", "").replaceAll("<span>", "").replaceAll("</span>", "\n").replaceAll("<li class=\"\">", ""));
				    	
				    	continue;
					}
					else{
						shortDes+=line+" ";
						isshortDes=true; continue;
					}
			}
			if(isshortDes){
				boolean iscontain=line.contains("</span></li></ul>");
				if(!iscontain){
					shortDes+=line+" ";
				continue;
				}
				else if(iscontain){
					shortDes+=line+" ";
				//	System.out.println(shortDes);
					lines.append(shortDes.substring(shortDes.indexOf("<span>"), shortDes.indexOf("</span></li></ul>")).replaceAll("</li>", "").replaceAll("<span>", "").replaceAll("</span>", "\n").replaceAll("<li class=\"\">", "")+"\n");
					isshortDes=false;
					shortDes="";
					continue;
				}
				else{
					System.out.println("其他情况！");
				}
			}
			
			//特价
			if(line.contains("\"product_price\"")){
		    	lines.append(line.substring(line.indexOf("\">")+2, line.indexOf("</span>"))+"\n");
		    	continue;
			}
			
			//卖价
			if(line.contains("<span id=\"price_box\">")){
		    	lines.append(line.substring(line.indexOf("\">")+2, line.indexOf("</span>"))+"\n");
		    	continue;
			}
			
			//描述
			if(line.contains("<div id=\"offer-template-0\">")) {
		    	lines.append(line.substring(line.indexOf("<ul> <li>"), line.indexOf("<p> <noscript>")-1).replaceAll("<ul>", "").replaceAll("<li>", "").replaceAll("</li>", "\n")+"\n");
		    	continue;
			}

			//包装包括
			if(line.contains("inbox__item")){
				if(line.contains("</span></li>")){
					lines.append(line.substring(line.indexOf("<span>"), line.indexOf("</span></li></ul>")).replaceAll("</li>", "").replaceAll("<span>", "").replaceAll("</span>", "\n").replaceAll("<li class=\"\">", "")+"\n");
			    //	System.out.println(line.substring(line.indexOf("<span>"), line.indexOf("</span></li></ul>")).replaceAll("</li>", "").replaceAll("<span>", "").replaceAll("</span>", "\n").replaceAll("<li class=\"\">", ""));
			    	
			    	continue;
				}
				else{
					packagecontent+=line+" ";
					ispackagecontent=true; 
					continue;
				}
		}
		if(ispackagecontent){
			boolean iscontain=line.contains("</span></li>");
			if(!iscontain){
				packagecontent+=line+" ";
			continue;
			}
			else if(iscontain){
				shortDes+=line+" ";
				System.out.println(packagecontent);
				lines.append(packagecontent.substring(packagecontent.indexOf("<span>"), packagecontent.indexOf("</span></li></ul>")).replaceAll("</li>", "").replaceAll("<span>", "").replaceAll("</span>", "\n").replaceAll("<li class=\"\">", "")+"\n");
				ispackagecontent=false;
				packagecontent="";
				continue;
			}
			else{
				System.out.println("其他情况！");
			}
			}

			
			//SKU
			if(line.contains("<td id=\"pdtsku\"")){
		    	lines.append(line.substring(line.indexOf("\">")+2, line.indexOf("</td>"))+"\n");
		    	continue;
			}
			
			
			//	if (!"".equalsIgnoreCase(line)&&line.contains("<script type=\"application/ld+json\">")) {
			//	lines.append(line+ "\n");
				
				//line=line+"\n";
				//line=line.replaceAll("@type", "type").replaceAll("@context", "context").substring(line.indexOf("{")).replace("</script>", "");
			//	Gson gson = new Gson();
			//	JsonRootBean info = gson.fromJson(line, JsonRootBean.class);//对于javabean直接给出class实例
				//List<ItemListElement> ietlist=info.getItemListElement();
//			    for(int i=0;i<ietlist.size();i++) {
//			    	ItemListElement ietelement=ietlist.get(i);
//			    	System.out.println(ietelement.getUrl());
//			    }
				//JSONObject jsonObject=JSONObject.fromObject(line);
			   //JsonRootBean info=(JsonRootBean)JSONObject.toBean(jsonObject, JsonRootBean.class);
			//    System.out.println(info.getContext());
				outputStream.write(line.getBytes());
				//break;
			//}
			
		}
		System.out.println(lines.toString());
		is.close();
		isr.close();
		reader.close();
		outputStream.close();
	}

	
}
