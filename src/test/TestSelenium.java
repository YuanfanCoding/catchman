package test;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class TestSelenium {

	public static void main(String[] args) {
		
		String xcsrftoken="";
		String cookieString="";
		 long starttime=System.currentTimeMillis(); 
		 System.setProperty("phantomjs.binary.path", "C:\\Users\\Administrator\\Desktop\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		        WebDriver driver = new PhantomJSDriver();
		       
		        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		        String url="https://shopee.com.my/search/?keyword=women+shoes&page=3&subcategory";
		        driver.get(url);
		        
		        long endtime=System.currentTimeMillis(); 
		        System.out.println("初始化时间："+(endtime-starttime));
//		        WebElement webElement = driver.findElement(By.id("video-player"));
//		        System.out.println(driver.getPageSource());
		        
		        
		        Set<Cookie> cookies = driver.manage().getCookies(); 
		        Iterator iterator=cookies.iterator();
		        while(iterator.hasNext()) {
		        	    Cookie cookie=(Cookie) iterator.next();
		        	    if(cookie.getName().equals("csrftoken"))
		        	    xcsrftoken=cookie.getValue();
		        	    cookieString+=cookie.getName()+"="+cookie.getValue()+";";
		        	   System.out.println(cookie.getName());
		        	   System.out.println(cookie.getValue());
		        }
		        
		       
		            
		        CloseableHttpClient httpclient = HttpClients.createDefault();
//		        CookieStore cookieStore = new BasicCookieStore();
//				 httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		        String web="";
		        try {
		        	CloseableHttpResponse response=null;
					HttpGet httpget = new HttpGet("https://shopee.com.my/api/v1/search_items/?by=pop&order=desc&keyword=women%20shoes&newest=150&limit=50");
//					HttpGet httpget = new HttpGet(url);
					
					response = httpclient.execute(httpget);
					 
//					 List<Cookie> cookies = cookieStore.getCookies();
//					 for (int i = 0; i < cookies.size(); i++) {
//						 System.out.println(cookies.get(i).getName());
//			        	 System.out.println(cookies.get(i).getValue());
//					 }
					 
			            try {
			                // 获取响应实体
			                HttpEntity entity = response.getEntity();
			                // 打印响应状态
			                if (entity != null) {
			                	web= EntityUtils.toString(entity,"UTF-8");
//			                	System.out.println(web);
			                 	System.out.println("{"+web.substring(web.indexOf("items")-1).replace("items", "item_shop_ids"));
			                 	
			                }
			            } finally {
			                response.close();
			            }
			            
			            HttpPost httppost = new HttpPost("https://shopee.com.my/api/v1/items/");
			            httppost.addHeader("referer", url);
			            httppost.addHeader("x-csrftoken", xcsrftoken);
			            httppost.setHeader("cookie", cookieString);
			            System.out.println(url);
			            System.out.println(xcsrftoken);
			            System.out.println(cookieString);
			             StringEntity stringEntity = new StringEntity("{"+web.substring(web.indexOf("items")-1).replace("items", "item_shop_ids"),"utf-8");//解决中文乱码问题    
			             stringEntity.setContentEncoding("UTF-8");    
//			              entity.setContentType("text");    
			              httppost.setEntity(stringEntity);
			            
			            response = httpclient.execute(httppost);
			            
			            try {
			                // 获取响应实体
			                HttpEntity entity = response.getEntity();
			                // 打印响应状态
			                if (entity != null) {
			                	web= EntityUtils.toString(entity,"UTF-8");
			                	System.out.println(web);
//			                 	System.out.println("{"+web.substring(web.indexOf("items")-1).replace("items", "item_shop_ids"));
			                 	
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
		        
//					httpget.setHeader("refer", "https://shopee.com.my/Women-Casual-Fashion-New-Sandal-Slippers-Beach-Outdoor-Open-Toe-Shoes-i.27639727.564092163");  
					
		  
//		        System.out.println(webElement.getText());
//		        System.out.println(webElement.getAttribute("src").toString());
		        long endtime2=System.currentTimeMillis(); 
		        System.out.println("使用时间："+(endtime2-endtime));
		        driver.close();
		        driver.quit();
	}

}
