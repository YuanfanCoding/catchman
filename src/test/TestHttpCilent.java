package test;

import java.io.IOException;
import java.text.ParseException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TestHttpCilent {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
            HttpGet httpget = new HttpGet("https://www.lazada.com.my/korean-style-womens-clothing-korean-jeans-elastic-highwaist-jeans-high-waist-jeans-long-pants-black-13863285.html");
          //  HttpGet httpget = new HttpGet("https://www.lazada.com.my/petpet/?spm=a2o4k.prod.0.0.27c36a81A1xgwt&ref=popular-search3=petpet");
            
//           设置超时 
//            3.X是这样的
//
//            HttpClient client=new DefaultHttpClient();
//            client.setConnectionTimeout(30000); 
//            client.setTimeout(30000);
//            4.X是这样的
//            HttpClient httpClient=new DefaultHttpClient();
//            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,2000);//连接时间
//            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,2000);//数据传输时间
//            4.3是这样的
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpGet httpGet=new HttpGet("http://www.baidu.com");//HTTP Get请求(POST雷同)
//            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
//            httpGet.setConfig(requestConfig);
//            httpClient.execute(httpGet);//执行请求
//            httpGet.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
//            
//            httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");  
//      
//            httpGet.setHeader("Accept-Encoding", "gzip, deflate");  
//      
//            httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");  
//      
//            httpGet.setHeader("Connection", "keep-alive");  
//      
//            httpGet.setHeader("Cookie", "__utma=226521935.73826752.1323672782.1325068020.1328770420.6;");  
//      
//            httpGet.setHeader("Host", "www.cnblogs.com");  
//      
//            httpGet.setHeader("refer", "http://www.baidu.com/s?tn=monline_5_dg&bs=httpclient4+MultiThreadedHttpConnectionManager");  
      //http://blog.csdn.net/a9529lty/article/details/7008537
            httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)");  
            
            CloseableHttpResponse response = httpclient.execute(httpget);
            
            String web="";
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                if (entity != null) {
                	web= EntityUtils.toString(entity,"UTF-8");
                	System.out.println(web);
//                    Document doc= Jsoup.parse(web);
//                    String category="";
//    				Elements categorylist=doc.select("span.breadcrumb__item-text");
//    				if(categorylist!=null && !categorylist.isEmpty()){
//    					for(int i=0;i<categorylist.size()-1;i++)
//    						category+=categorylist.get(i).text().toString()+"/";
//    					
//    				}
//                    System.out.println(category);
//                    String size="";
//    				Elements sizelist=doc.select("span.grouped-size__popup__tab__content__item__size-item");
//    				if(sizelist!=null && !sizelist.isEmpty()){
//    					size+=doc.getElementsByClass("grouped-size__popup__tab__header__item grouped-size__popup__tab__header__item_state_active").text().toString()+" \n";
//    					for(int i=0;i<sizelist.size();i++)
//    						size+=sizelist.get(i).text().toString()+" \n";
//    				}
//    				 System.out.println(size);//尺寸
//                    System.out.println(doc.title().substring(0, doc.title().indexOf("| Lazada Malaysia")));
//                    System.out.println(doc.getElementsByClass("prd-reviews").get(0).text().toString().trim());
//                    System.out.println(doc.getElementsByClass("productImage").get(0).attr("data-big").toString());
//                    System.out.println(doc.getElementsByClass("prd-attributesList").select("span").first().text().toString());
//                    System.out.println(doc.select("span#product_price").text().toString());
//                    System.out.println(doc.select("span#price_box").text().toString());
                  //    System.out.println(doc.select("span.breadcrumb__item-text").text().toString());                  
//                    System.out.println(doc.select("li.inbox__item").text().toString());
//                    System.out.println(doc.select("td#pdtsku").text().toString());
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
