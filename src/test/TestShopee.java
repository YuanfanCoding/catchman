package test;

import static org.hamcrest.CoreMatchers.hasItems;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.lazada.model.Constant;
import com.lazada.model.json.shopee.first.FirstShopeeList;
import com.lazada.model.json.shopee.first.Items;

public class TestShopee {

	public static void main(String[] args) {

			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			try {
//	            HttpGet httpget = new HttpGet("https://shopee.com.my/api/v1/search_items/?by=pop&order=desc&keyword=sport%20shoes&newest=0&limit=50");
	            HttpGet httpget = new HttpGet("https://shopee.com.my/api/v1/search_items/?by=pop&order=desc&keyword=women%20shoes&newest=100&limit=50");
	            
//	           设置超时 
//	            3.X是这样的
	//
//	            HttpClient client=new DefaultHttpClient();
//	            client.setConnectionTimeout(30000); 
//	            client.setTimeout(30000);
//	            4.X是这样的
//	            HttpClient httpClient=new DefaultHttpClient();
//	            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,2000);//连接时间
//	            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,2000);//数据传输时间
//	            4.3是这样的
//	            CloseableHttpClient httpClient = HttpClients.createDefault();
//	            HttpGet httpGet=new HttpGet("http://www.baidu.com");//HTTP Get请求(POST雷同)
//	            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
//	            httpGet.setConfig(requestConfig);
//	            httpClient.execute(httpGet);//执行请求
//	            httpGet.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
//	            
//	            httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");  
//	      
//	            httpGet.setHeader("Accept-Encoding", "gzip, deflate");  
//	      
//	            httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");  
//	      
//	            httpGet.setHeader("Connection", "keep-alive");  
//	      
//	            httpGet.setHeader("Cookie", "__utma=226521935.73826752.1323672782.1325068020.1328770420.6;");  
//	      
//	            httpGet.setHeader("Host", "www.cnblogs.com");  
//	      
//	            httpGet.setHeader("refer", "http://www.baidu.com/s?tn=monline_5_dg&bs=httpclient4+MultiThreadedHttpConnectionManager");  
	      //http://blog.csdn.net/a9529lty/article/details/7008537
//	            httpget.setHeader("if-none-match-", "55b03-35472a6b759e4acdea22d1c79a88188a");  
//	            httpget.setHeader("User-Agent",
//	            		"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)");  
//	            "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
	            CloseableHttpResponse response = httpclient.execute(httpget);
	            
	            String web="";
	            try {
	                // 获取响应实体
	                HttpEntity entity = response.getEntity();
	                // 打印响应状态
	                if (entity != null) {
	                	web= EntityUtils.toString(entity,"UTF-8");
	                 	System.out.println(web);
	                 	
	                 	Gson gson = new Gson();
	                    FirstShopeeList firstShopeeList = gson.fromJson(web,FirstShopeeList.class);// 对于javabean直接给出class实例
	                 	
	                    System.out.println(firstShopeeList.getItems().size());
	                    for(int i=0;i<firstShopeeList.getItems().size();i++) {
	                    Items items	=firstShopeeList.getItems().get(i);
	                    	System.out.println(items.getItemid()+items.getShopid());
	                    	
	                    }
	                 	
//	                 	Gson gson = new Gson();
//	                    CategoryList categoryList = gson.fromJson("{\"calist\":"+web+"}",CategoryList.class);// 对于javabean直接给出class实例
//	                    
//	                    System.out.println(categoryList.getCalist().size());
//	                    if(Constant.calMap==null || Constant.calMap.size()==0) {
//	                    	Constant.calMap=new HashMap<Integer,String>();
//	                    	for(int i=0;i<categoryList.getCalist().size();i++) {//一层id
//	                    		
//	                    		List<Sub> sub=categoryList.getCalist().get(i).getSub();
//	                    		String firstname=categoryList.getCalist().get(i).getMain().getDisplay_name();
//	                    		
//	                    		for(int j=0;j<sub.size();j++) {
//	                    			
//	                    			List<Sub_sub> sub_sub=sub.get(j).getSub_sub();
//	                    			String secondname=sub.get(j).getDisplay_name();
//	                    			for(int k=0;k<sub_sub.size();k++) {
//	                    				System.out.println(sub_sub.get(k).getCatid());
//	                    				System.out.println(sub_sub.get(k).getDisplay_name());
//	                    				Constant.calMap.put(sub_sub.get(k).getCatid(),firstname+">"+secondname+ ">"+sub_sub.get(k).getDisplay_name());
//	                    			}
//	                    		}
//	                    	}	
//	                    }
	                 	
	                 	
	                 	
	                 	
//	                    Document doc= Jsoup.parse(web);
//	                    System.out.println(doc.select("div.ui-breadcrumb").text().toString());
//	                    
//	                    Elements elimage =doc.select("span.img-thumb-item");
//	                    for (int i = 0; i < (elimage.size() > 8 ? 8 : elimage.size() ); i++) {
//	                    	System.out.println(elimage.get(i).children().attr("src").toString().replaceFirst(".jpg_50x50", ""));
//	                    }
//	                    Elements maidians =doc.select("ul.product-property-list").select("li.property-item");
//	                
//	                    for(int i=0;i<maidians.size();i++) {
//	                        System.out.println(maidians.get(i).text().toString());
//	                        if(maidians.get(i).text().toString().contains("Brand Name"))
//	                    }
//	                    System.out.println(doc.select("ul.product-property-list").select("ul.util-clearfix").html());
//	                    System.out.println(doc.select("ul#j-sku-list-2").text().toString());
//	                    String line1=doc.select("script[type=text/javascript]").get(2).data().toString();
//	                    String line2=line1.substring(line1.indexOf("window.runParams.detailDesc="), line1.indexOf("window.runParams.transAbTest=")).trim();
//	                    System.out.println(line2.substring(line2.indexOf("https://"),line2.length()-2));
//	                    System.out.println(doc.select("div.ui-box").html().toString());
	                    
//	                	Gson gson = new Gson();
//	                    String line=doc.select("script[type=application/ld+json]").get(1).data().toString();
//	                    JsonRootBean info = gson.fromJson(line.replaceAll("@type", "type").replaceAll("@context", "context"),
//	        					JsonRootBean.class);// 对于javabean直接给出class实例
//	        			List<ItemListElement> ietlist = info.getItemListElement();
//	        			for (int i = 0; i < ietlist.size(); i++) {
//	        				ItemListElement ietelement = ietlist.get(i);
//	        				System.out.println("第" + (i + 1) + "个详情:  " + ietelement.getUrl()+"\n");
//	        		     	}
//	        			
//	        			 String line=doc.select("script").get(2).data().toString();
//	        			 System.out.println(line);
//	                     FirstLevelJsonRootBean info = gson.fromJson(line.replaceAll("window.pageData=", ""),
//	                    		 FirstLevelJsonRootBean.class);// 对于javabean直接给出class实例
//	         			List<ListItems> ietlist = info.getMods().getListItems();
//	         			for (int i = 0; i < ietlist.size(); i++) {
//	         				ListItems ietelement = ietlist.get(i);
//	         				System.out.println("第" + (i + 1) + "个详情:  " + ietelement.getLocation()+"\n");
//	         		     	}
	        			
	        			
//	                    System.out.println(doc.select("div.prod_header_brand_action").get(0).text().toString());
//	                    System.out.println(doc.select("a.basic-info__name").get(0).text().toString());
//	                    String category="";
//	    				Elements categorylist=doc.select("span.breadcrumb__item-text");
//	    				if(categorylist!=null && !categorylist.isEmpty()){
//	    					for(int i=0;i<categorylist.size()-1;i++)
//	    						category+=categorylist.get(i).text().toString()+"/";
//	    					
//	    				}
//	                    System.out.println(category);
//	                    String size="";
//	    				Elements sizelist=doc.select("span.grouped-size__popup__tab__content__item__size-item");
//	    				if(sizelist!=null && !sizelist.isEmpty()){
//	    					size+=doc.getElementsByClass("grouped-size__popup__tab__header__item grouped-size__popup__tab__header__item_state_active").text().toString()+" \n";
//	    					for(int i=0;i<sizelist.size();i++)
//	    						size+=sizelist.get(i).text().toString()+" \n";
//	    				}
//	    				 System.out.println(size);//尺寸
//	                    System.out.println(doc.title().substring(0, doc.title().indexOf("| Lazada Malaysia")));
//	                    System.out.println(doc.getElementsByClass("prd-reviews").get(0).text().toString().trim());
//	                    System.out.println(doc.getElementsByClass("productImage").get(0).attr("data-big").toString());
//	                    System.out.println(doc.getElementsByClass("prd-attributesList").select("span").first().text().toString());
//	                    System.out.println(doc.select("span#product_price").text().toString());
//	                    System.out.println(doc.select("span#price_box").text().toString());
	                  //    System.out.println(doc.select("span.breadcrumb__item-text").text().toString());                  
//	                    System.out.println(doc.select("li.inbox__item").text().toString());
//	                    System.out.println(doc.select("td#pdtsku").text().toString());
	                     // System.out.println(doc.select("script[type=application/ld+json]").get(0).data().toString());
	                      
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
	

}
