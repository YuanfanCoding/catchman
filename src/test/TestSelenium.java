package test;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

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
import org.apache.xalan.templates.ElemWhen;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.lazada.ui.Main;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestSelenium {
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void someNonStandardEntryPoint() {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 262);
		frame.getContentPane().add(panel);
		
		JButton button = new JButton("\u70B9\u51FB");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				test2();
			}
		});
		panel.add(button);
		frame.setVisible(true);
	}

	
	
	public static  void  test() {
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
	
	public static  void  test2() {
		String cookieString="";
		String t_uid="";
		 long starttime=System.currentTimeMillis(); 

         //设置必要参数
             DesiredCapabilities dcaps = new DesiredCapabilities();
             //ssl证书支持
             dcaps.setCapability("acceptSslCerts", true);
             //截屏支持
             dcaps.setCapability("takesScreenshot", true);
             //css搜索支持
             dcaps.setCapability("cssSelectorsEnabled", true);
             //js支持
             dcaps.setJavascriptEnabled(false);
             //驱动支持
             dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"C:\\Users\\Administrator\\Desktop\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
             //创建无界面浏览器对象
             PhantomJSDriver driver = new PhantomJSDriver(dcaps);
//		 System.setProperty("phantomjs.binary.path", "C:\\Users\\Administrator\\Desktop\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
//		 DesiredCapabilities caps = new DesiredCapabilities();
//		 ((DesiredCapabilities)caps).setJavascriptEnabled(false);
////		
//		 String [] phantomJsArgs = {"--ignore-ssl-errors=true","--ssl-protocol=tlsv1"};
//		 caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomJsArgs);
//		        WebDriver driver = new PhantomJSDriver(caps);
		       
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS); 
//                driver.findElement(by); 
		        String url="https://www.lazada.com.my/shop-samsonite/?from=wangpu&langFlag=en&page=2&pageTypeId=2&q=All-Products";
		        driver.get(url);
		        
		        long endtime=System.currentTimeMillis(); 
		        System.out.println("初始化时间："+(endtime-starttime));
//		        WebElement webElement = driver.findElement(By.id("video-player"));
//		        System.out.println(driver.getPageSource());
		        
		        
		        Set<Cookie> cookies = driver.manage().getCookies(); 
		        Iterator iterator=cookies.iterator();
		        while(iterator.hasNext()) {
		        	    Cookie cookie=(Cookie) iterator.next();
		        	    if(cookie.getName().equals("t_uid"))
		        	    	t_uid="t_uid="+cookie.getValue()+";";
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
					HttpGet httpget = new HttpGet("https://www.lazada.com.my/shop-samsonite/?from=wangpu&langFlag=en&page=2&pageTypeId=2&q=All-Products");
//					HttpGet httpget = new HttpGet(url);
					httpget.setHeader("cookie", t_uid);
					System.out.println(httpget.getHeaders("cookie"));
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
			                	System.out.println(web);
			                }
			            } finally {
			                response.close();
			            }  
			            }catch (ClientProtocolException e) {
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
		        long endtime2=System.currentTimeMillis(); 
		        System.out.println("使用时间："+(endtime2-endtime));
		        driver.close();
		        driver.quit();
	}
	
	
	
	public static void main(String[] args) {
		TestSelenium testSelenium=new TestSelenium();
		testSelenium.someNonStandardEntryPoint();
		
	}
}
