package test;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class TestSelenium {

	public static void main(String[] args) {
		 long starttime=System.currentTimeMillis(); 
		 System.setProperty("phantomjs.binary.path", "C:\\Users\\Administrator\\Desktop\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		        WebDriver driver = new PhantomJSDriver();
		        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		        driver.get("https://shopee.com.my/search/?keyword=women+shoes&subcategory");
		        
		        
//		        WebElement webElement = driver.findElement(By.id("video-player"));
//		        System.out.println(driver.getPageSource());
		        Set<Cookie> cookies = driver.manage().getCookies(); 
		        Iterator iterator=cookies.iterator();
		        while(iterator.hasNext()) {
		        	    Cookie cookie=(Cookie) iterator.next();
		        	   System.out.println(cookie.getName());
		        	   System.out.println(cookie.getValue());
		        }
//		        System.out.println(webElement.getText());
//		        System.out.println(webElement.getAttribute("src").toString());
		        long endtime=System.currentTimeMillis(); 
		        System.out.println("使用时间："+(endtime-starttime));
//		        driver.close();
		        driver.quit();
	}

}
