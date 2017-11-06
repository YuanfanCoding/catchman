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

public class TestHttpCilent {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
            HttpGet httpget = new HttpGet("https://www.lazada.com.my/converse-553419c-chuck-taylor-all-star-dainty-peached-oxwomenpolarbluebiscuit-21447652.html");
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
      
            httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)");  
            
            CloseableHttpResponse response = httpclient.execute(httpget);
            
            String web="";
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                if (entity != null) {
                	web= EntityUtils.toString(entity,"UTF-8");
                //	System.out.println(web);
                    Document doc= Jsoup.parse(web);
                    System.out.println(doc.title().substring(0, doc.title().indexOf("| Lazada Malaysia")));
                    System.out.println(doc.getElementsByClass("prd-reviews").get(0).text().toString().trim());
                    System.out.println(doc.getElementsByClass("productImage").get(0).attr("data-big").toString());
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
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
