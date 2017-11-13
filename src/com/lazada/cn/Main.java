package com.lazada.cn;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.SystemColor;

import javax.swing.UIManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.lazada.json.model.ItemListElement;
import com.lazada.json.model.JsonRootBean;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.JSeparator;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

	JFrame frmLazada;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextArea textArea1;
	private JDialog d;
	public  static final  String ADVERTISEMENT="http://ylfcoding.cn";
	private final JSeparator separator_2 = new JSeparator();

	public final static String website="http://www.lazada.com.my/catalog/?q="; 
	private String keyword;
	private int startpage;
	private int endpage;
	private String savepath;
	private int exlRow;
	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmLazada.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLazada = new JFrame();
//		frmLazada.setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("\\image\\lazada.jpg")));
		frmLazada.setIconImage(new ImageIcon("image\\lazada.jpg").getImage());
		frmLazada.setTitle("LAZADA\u6536\u96C6\u5668");
		frmLazada.setBounds(100, 100, 643, 458);
		frmLazada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLazada.getContentPane().setLayout(null);
		frmLazada.setLocation(LoginTest.centreContainer(frmLazada.getSize()));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setBounds(154, 0, 327, 255);
		frmLazada.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u5173\u952E\u8BCD\uFF1A");
		lblNewLabel.setBounds(33, 20, 54, 15);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(83, 17, 177, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("\u8D77\u59CB\u9875");
		label.setBounds(33, 64, 54, 15);
		panel.add(label);
		
		textField_1 = new JTextField();
		textField_1.setBounds(77, 61, 66, 21);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label_1 = new JLabel("\u7EC8\u6B62\u9875");
		label_1.setBounds(170, 64, 54, 15);
		panel.add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(215, 61, 66, 21);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JButton button = new JButton("\u5F00\u59CB\u6536\u96C6");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				    if(textField_3.getText().equals("")||textField_4.getText().equals("")) {
				    	JOptionPane.showMessageDialog(d,"没有输入保存路径或者文件名！","请重新输入",JOptionPane.WARNING_MESSAGE);
				    }
				    else {
					
						setPro("women shoes",1,2,textField_3.getText()+textField_4.getText()+".xls");
						try {
							startCatching();
						} catch (RowsExceededException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (WriteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						textArea1.append("3333333");
					//	new ConnectImpl("women shoes",1,100,textField_3.getText()+textField_4.getText()+".xls").startCatching();
			//		} catch (WriteException | IOException e1) {
						// TODO Auto-generated catch block
				//		e1.printStackTrace();
			//		}
				   }

			}
		});
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setBounds(112, 103, 112, 23);
		panel.add(button);
		
		JLabel label_2 = new JLabel("\u4FDD\u5B58\u4F4D\u7F6E\uFF1A");
		label_2.setBounds(10, 154, 67, 15);
		panel.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setBounds(83, 151, 177, 21);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton = new JButton("----");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jchoose = new JFileChooser();
				jchoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jchoose.showOpenDialog(new JDialog());
		        if(jchoose.getCurrentDirectory().toString()== null){            
		        }else{  
		        	textField_3.setText(jchoose.getSelectedFile().getPath());  
		        }
		        textField_4.setText(textField.getText()+"_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			}
		});
		btnNewButton.setBounds(270, 150, 46, 23);
		panel.add(btnNewButton);
		
		JLabel lblExcel = new JLabel("excel\u6587\u4EF6\u540D\uFF1A");
		lblExcel.setBounds(10, 202, 89, 18);
		panel.add(lblExcel);
		
		textField_4 = new JTextField();
		textField_4.setBounds(87, 201, 162, 21);
		panel.add(textField_4);
		textField_4.setColumns(10);
		separator_2.setBounds(-46, 0, 69, 33);
		panel.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(-2, -2, 0, 412);
		panel.add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(327, 405, 0, -403);
		panel.add(separator_4);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(0, 0, 154, 410);
		frmLazada.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("\u56FE\u7247\u4E00");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {  
		            URI uri = new URI(ADVERTISEMENT);  
		            Desktop.getDesktop().browse(uri);  
		        } catch (URISyntaxException aa) {  
		            aa.printStackTrace();  
		        } catch (IOException ee) {  
		            ee.printStackTrace();  
		        }  
			}
		});
		lblNewLabel_1.setBounds(0, 41, 154, 124);
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\guanggao\\\u5DE61\u6539.jpg"));
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u56FE\u7247\u4E8C");
		lblNewLabel_2.setBounds(0, 164, 154, 208);
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\guanggao\\\u5DE6\u4E8C\u6539.jpg"));
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("\u5DE6\u4E00\u5E7F\u544A");
		lblNewLabel_4.setBounds(0, 379, 154, 31);
		lblNewLabel_4.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\guanggao\\\u53F3\u56DB\u6539.jpg"));
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_7 = new JLabel(" Q Q\uFF1A123456789\r\n");
		lblNewLabel_7.setForeground(new Color(30, 144, 255));
		lblNewLabel_7.setBackground(new Color(0, 250, 154));
		lblNewLabel_7.setBounds(10, 0, 134, 23);
		panel_1.add(lblNewLabel_7);
		
		JLabel label_3 = new JLabel("\u5FAE\u4FE1\uFF1A123456789");
		label_3.setForeground(new Color(154, 205, 50));
		label_3.setBackground(new Color(50, 205, 50));
		label_3.setBounds(10, 22, 109, 15);
		panel_1.add(label_3);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(482, 0, 145, 410);
		frmLazada.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("\u53F3\u4E00\u5E7F\u544A");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\guanggao\\\u53F3\u8FB9\u4E0A.jpg"));
		lblNewLabel_3.setBounds(0, 0, 145, 91);
		panel_2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_5 = new JLabel("\u53F3\u4E8C\u5E7F\u544A");
		lblNewLabel_5.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\guanggao\\\u53F3\u4E2D.jpg"));
		lblNewLabel_5.setBounds(0, 96, 145, 213);
		panel_2.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("\u53F3\u4E09\u5E7F\u544A");
		lblNewLabel_6.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\guanggao\\\u53F3\u8FB9\u4E0B.jpg"));
		lblNewLabel_6.setBounds(0, 319, 145, 91);
		panel_2.add(lblNewLabel_6);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.textHighlightText);
		panel_3.setBounds(154, 255, 327, 155);
		frmLazada.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		textArea1= new JTextArea();
		textArea1.setBackground(SystemColor.text);
		textArea1.setBounds(0, 0, 327, 145);
		panel_3.add(textArea1);
		textArea1.append("4444444");
	}
	
	
	public void setPro(String keyword, int startpage, int endpage, String savepath) {
		 this.keyword=keyword;
		    this.startpage=startpage;
			if(endpage>100) endpage=100;
			this.endpage=endpage;
			this.savepath=savepath;
	}
	
	private void test() {
		textArea1.append("test");
	}
	
	/*
	 * @ workbook 传入工作簿
	 * 初始化sheet的第一行
	 */
	private void initWorkbook(WritableWorkbook workbook) throws IOException, RowsExceededException, WriteException {
		 this.exlRow = 0;
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
	   
	}
	private void startCatching() throws IOException , RowsExceededException, WriteException{
		WritableWorkbook workbook = null;
		workbook = Workbook.createWorkbook(new File(savepath));
		initWorkbook(workbook);
		try {
			for (int i = 0; i < endpage - startpage + 1; i++) {
				textArea1.append("开始抓取第" + (i + 1) + "页的内容。\n");
				textArea1.paintImmediately(textArea1.getBounds()); 
				getFirstLevel(String.valueOf(startpage + i), workbook.getSheet(0));
				Thread.sleep(6000);// 延迟6秒发送请求
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			workbook.write();
			workbook.close();
		}
	}
	private void getFirstLevel(String pagenum,WritableSheet sheet) throws IOException, RowsExceededException, WriteException{
		String urlstring = website + URLEncoder.encode(keyword, "utf-8") + "&page=" + pagenum;
		Document doc = null;
		while (doc == null) {
			doc = getDoc(urlstring);
			if(doc!=null) {
			Gson gson = new Gson();
			JsonRootBean info = gson.fromJson(doc.select("script[type=application/ld+json]").get(0).data().toString(),
					JsonRootBean.class);// 对于javabean直接给出class实例
			List<ItemListElement> ietlist = info.getItemListElement();
			for (int i = 0; i < ietlist.size(); i++) {
				ItemListElement ietelement = ietlist.get(i);
				textArea1.append("第" + pagenum + "页  " + "第" + (i + 1) + "个详情:  " + ietelement.getUrl());
				textArea1.paintImmediately(textArea1.getBounds()); 
				getSencondLevel(ietelement.getUrl(), sheet);
			}
		  }
			else {
				textArea1.append("第" + pagenum + "页数据  " + "获取失败，开始重新获取:--------------  ");
				textArea1.paintImmediately(textArea1.getBounds()); 
			}
		}
	}
	
	private void getSencondLevel(String urlstring,WritableSheet sheet) throws IOException,RowsExceededException, WriteException{

		    FinalInfo info=new FinalInfo();
		    info.setLink(urlstring);
		    Document doc = null;
			while (doc == null) {
				doc = getDoc(urlstring);
				if(doc!=null) {
				info.setName(doc.title().substring(0, doc.title().indexOf("| Lazada Malaysia")));//标题
				info.setComment(doc.getElementsByClass("prd-reviews").get(0).text().toString().trim());//好评
				
				String elimagestring="";
				Elements elimage=doc.getElementsByClass("productImage");
				info.setMainimage(elimage.get(0).attr("data-big").toString());//图片
				for(int i=1;i<elimage.size()-1;i++) {
					Class clazz = info.getClass();
					Method m;
					try {
						m = clazz.getMethod("setImage"+(i+1),String.class);
						m.invoke(info, elimage.get(i).attr("data-big").toString());
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				Elements elsd=doc.getElementsByClass("prd-attributesList").select("span");
				String elsdstring="";
				for(Element els:elsd) {
					elsdstring+=els.text().toString()+"\n";
				}
				info.setShort_description(elsdstring);//卖点
				
				info.setSpecial_price(doc.select("span#product_price").text().toString());//特价
				info.setPrice(doc.select("span#price_box").text().toString());//实价
				info.setDescription(doc.select("div.product-description__block").get(0).text().toString());//描述        
				info.setPackage_content(doc.select("li.inbox__item").text().toString().replaceAll("<ul>", "").replaceAll("</ul>", "").replaceAll("<li>", "").replaceAll("</li>", "").replaceAll("<p>", "").replaceAll("</p>", ""));//包装
				info.setSellersku(doc.select("td#pdtsku").text().toString());//sku
				info.setOneItemStart(true);
				}
				else {
					textArea1.append("链接：" +urlstring + "获取失败，开始重新获取:--------------  ");
					textArea1.paintImmediately(textArea1.getBounds()); 
				}
			}
			if (info.isOneItemStart()) {
				sheet.addCell(new Label(0, this.exlRow, info.getName()));
				sheet.addCell(new Label(1, this.exlRow, info.getDescription()));
				sheet.addCell(new Label(2, this.exlRow, info.getShort_description()));
				sheet.addCell(new Label(3, this.exlRow, info.getPrice()));
				sheet.addCell(new Label(4, this.exlRow, info.getSpecial_price()));
				sheet.addCell(new Label(5, this.exlRow, info.getSellersku()));
				sheet.addCell(new Label(6, this.exlRow, info.getPackage_content()));
				sheet.addCell(new Label(7, this.exlRow, info.getLink()));
				sheet.addCell(new Label(8, this.exlRow, info.getComment()));
				sheet.addCell(new Label(9, this.exlRow, info.getMainimage()));
				sheet.addCell(new Label(10, this.exlRow, info.getImage2()));
				sheet.addCell(new Label(11, this.exlRow, info.getImage3()));
				sheet.addCell(new Label(12, this.exlRow, info.getImage4()));
				sheet.addCell(new Label(13, this.exlRow, info.getImage5()));
				sheet.addCell(new Label(14, this.exlRow, info.getImage6()));
				sheet.addCell(new Label(15, this.exlRow, info.getImage7()));
				sheet.addCell(new Label(16, this.exlRow++, info.getImage8()));
				info.backToInit();
			
			}
			
		}
	

	private Document getDoc(String url) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		Document doc = null;
		try {
			 
			HttpGet httpget = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6*1000).build();
			httpget.setConfig(requestConfig);
			httpget.setHeader("User-Agent",
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)");

			CloseableHttpResponse response = httpclient.execute(httpget);

			String web = "";
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				if (entity != null) {
					web = EntityUtils.toString(entity, "UTF-8");

					doc = Jsoup.parse(web);

					return doc;

				}
			} finally {
				response.close();
				httpclient.close();
			}
		} catch (org.apache.http.NoHttpResponseException e) {
			e.printStackTrace();
		} 
		  catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch(java.net.UnknownHostException u) {
			u.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
}
