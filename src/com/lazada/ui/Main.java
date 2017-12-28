package com.lazada.ui;

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
import com.lazada.handler.ConnectImpl;
import com.lazada.handler.HttpHandler;
import com.lazada.model.Constant;
import com.lazada.model.json.ItemListElement;
import com.lazada.model.json.JsonRootBean;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Font;

import javax.swing.JCheckBox;

public class Main implements MouseListener{

	JFrame frmLazada;
	private JTextField store_text;
	private ConnectImpl catchInfoArea;
	private JDialog d;
	private JButton button;
	
	private final JSeparator separator_2 = new JSeparator();
	private JTextField keyword_text;
	private JTextField shop_text;
	private JTextField product_text;
	private JTextField startpage_text;
	private JTextField endpage_text;

	JCheckBox[] jway=new JCheckBox[3];
	JCheckBox[] jplatform=new JCheckBox[4];
	JTextField[] wayText=new JTextField[3];
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.
	                        getSystemLookAndFeelClassName());
					Main window = new Main();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLazada = new JFrame(Constant.SOFTWARENAME);
//		frmLazada.setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("\\image\\lazada.jpg")));
		frmLazada.setIconImage(new ImageIcon(Main.class.getResource("/image/lazada.jpg")).getImage());
		frmLazada.setBounds(100, 100, 643, 437);
		frmLazada.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmLazada.getContentPane().setLayout(null);
		frmLazada.setLocation(Login.centreContainer(frmLazada.getSize()));
		frmLazada.setResizable(false);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setBounds(154, 0, 327, 300);
		frmLazada.getContentPane().add(panel);
		panel.setLayout(null);
		
		button = new JButton("\u5F00\u59CB\u6536\u96C6");
		button.setBounds(98, 255, 106, 35);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				    if(store_text.getText().equals("")) {
				    	JOptionPane.showMessageDialog(d,"û�����뱣��·�������ļ�����","����������",JOptionPane.WARNING_MESSAGE);
				    }
				    if(startpage_text.getText().equals("")||endpage_text.getText().equals("")) {
				    	JOptionPane.showMessageDialog(d,"û��������ʼҳ����ֹҳ��","����������",JOptionPane.WARNING_MESSAGE);
				    }
//				    if(textField.getText().equals("")) {
//				    	JOptionPane.showMessageDialog(d,"û������ؼ��ʣ�","����������",JOptionPane.WARNING_MESSAGE);
//				    }
				    if(startpage_text.getText().matches("[0-9]+")&&endpage_text.getText().matches("[0-9]+")) {
				    		new Thread(new Runnable() {
					            @Override
					            public void run() {
							try {
//								textArea1.setPro(textField.getText(),Integer.parseInt(textField_1.getText()),Integer.parseInt(textField_2.getText()),textField_3.getText()+"\\" +new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())+".xls");								
								catchInfoArea.startCatching();
							} catch (WriteException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							JOptionPane.showMessageDialog(d,"���������ռ���ɣ�","nice!",JOptionPane.WARNING_MESSAGE);
							if(!Constant.totalnum.equals("������") && Constant.areadycatchnum>=Integer.parseInt(Constant.totalnum)) {
								JOptionPane.showMessageDialog(d,"�����ռ������Ѿ��ﵽ���ޣ�����ϵ�ͷ����ѣ�","�����ر�",JOptionPane.WARNING_MESSAGE);
								HttpHandler.updateUser(0);
								//frmLazada.dispose();
								System.exit(0);
							   }
					            }
							}).start();							
				    }
				    else JOptionPane.showMessageDialog(d,"��ʼҳ����ֹҳ��Ҫ���֣�","����������",JOptionPane.WARNING_MESSAGE);
				    
				    }
			
		});
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		panel.add(button);
		
		JLabel label_2 = new JLabel("\u4FDD\u5B58\u4F4D\u7F6E\uFF1A");
		label_2.setBounds(19, 230, 67, 15);
		panel.add(label_2);
		
		store_text = new JTextField();
		store_text.setBounds(73, 228, 177, 21);
		panel.add(store_text);
		store_text.setColumns(10);
		
		JButton btnNewButton = new JButton("----");
		btnNewButton.setBounds(260, 226, 46, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jchoose = new JFileChooser();
				jchoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jchoose.showOpenDialog(new JDialog());
		        if(jchoose.getCurrentDirectory()== null||jchoose.getCurrentDirectory().toString().length()==0||jchoose.getSelectedFile()==null){            
		        }else{  
		        	store_text.setText(jchoose.getSelectedFile().getPath());  
//		        	if(textField_4.getText()==null||textField_4.getText().length()==0) textField_4.setText(textField.getText()+"_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		        }
		        
			}
		});
		panel.add(btnNewButton);
		separator_2.setBounds(-46, 0, 69, 33);
		panel.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(-2, -2, 0, 412);
		panel.add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(327, 405, 0, -403);
		panel.add(separator_4);
		
		JLabel lblNewLabel_1 = new JLabel("\u8BF7\u60A8\u9009\u62E9\u91C7\u96C6\u65B9\u5F0F\uFF1A");
		lblNewLabel_1.setBounds(10, 5, 112, 15);
		lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 12));
		panel.add(lblNewLabel_1);
		
		JCheckBox keywordcheckBox = new JCheckBox("\u8BF7\u8F93\u5165\u5173\u952E\u8BCD\uFF1A");
		keywordcheckBox.setBackground(new Color(240, 248, 255));
		keywordcheckBox.setBounds(20, 26, 115, 23);
		keywordcheckBox.setName(Constant.KEYWORDCATCH);
		jway[0]=keywordcheckBox;
		keywordcheckBox.addItemListener(new MyItemListener());
		panel.add(keywordcheckBox);
		
		keyword_text = new JTextField();
		keyword_text.setBounds(150, 28, 116, 19);
		panel.add(keyword_text);
		keyword_text.setColumns(10);
		wayText[0]=keyword_text;
		
		JCheckBox storeCheckBox = new JCheckBox("\u8BF7\u8F93\u5165\u5E97\u94FA\u94FE\u63A5\uFF1A");
		storeCheckBox.setBackground(new Color(240, 248, 255));
		storeCheckBox.setBounds(19, 55, 121, 23);
		storeCheckBox.addItemListener(new MyItemListener());
		storeCheckBox.setName(Constant.STORECATCH);
		jway[1]=storeCheckBox;
		panel.add(storeCheckBox);
		
		JCheckBox productCheckBox = new JCheckBox("\u8BF7\u8F93\u5165\u4EA7\u54C1\u94FE\u63A5\uFF1A");
		productCheckBox.setBackground(new Color(240, 248, 255));
		productCheckBox.setBounds(19, 83, 121, 23);
		productCheckBox.addItemListener(new MyItemListener());
		productCheckBox.setName(Constant.PRODUCTCATCH);
		jway[2]=productCheckBox;
		panel.add(productCheckBox);
		
		shop_text = new JTextField();
		shop_text.setBounds(150, 57, 116, 19);
		shop_text.setColumns(10);
		panel.add(shop_text);
		wayText[1]=shop_text;
		
		product_text = new JTextField();
		product_text.setBounds(150, 84, 116, 19);
		product_text.setColumns(10);
		panel.add(product_text);
		wayText[2]=product_text; 
		
		JLabel label_3 = new JLabel("\u8BF7\u60A8\u9009\u62E9\u91C7\u96C6\u5E73\u53F0\uFF1A");
		label_3.setFont(new Font("����", Font.PLAIN, 12));
		label_3.setBounds(10, 112, 112, 15);
		panel.add(label_3);
		
		JCheckBox chckbxLazada = new JCheckBox("lazada");
		chckbxLazada.setSelected(true);
		chckbxLazada.setBackground(new Color(240, 248, 255));
		chckbxLazada.setBounds(19, 133, 67, 23);
		jplatform[0]=chckbxLazada;
		chckbxLazada.setName(Constant.LAZADA);
//		chckbxLazada.addItemListener(new MyItemListener());
		panel.add(chckbxLazada);
		
		JCheckBox chckbxShopee = new JCheckBox("shopee");
		chckbxShopee.setEnabled(false);
		chckbxShopee.setBackground(new Color(240, 248, 255));
		chckbxShopee.setBounds(90, 133, 67, 23);
		chckbxShopee.setName(Constant.SHOPEE);
//		jplatform[1]=chckbxShopee;
		chckbxShopee.addItemListener(new MyItemListener());
		panel.add(chckbxShopee);
		
		JCheckBox chckbxAmazon = new JCheckBox("Amazon");
		chckbxAmazon.setEnabled(false);
		chckbxAmazon.setBackground(new Color(240, 248, 255));
		chckbxAmazon.setBounds(163, 133, 67, 23);
		chckbxAmazon.setName(Constant.AMAZON);
//		jplatform[2]=chckbxAmazon;
		chckbxAmazon.addItemListener(new MyItemListener());
		panel.add(chckbxAmazon);
		
		JCheckBox chckbxAliexpress = new JCheckBox("AliExpress");
		chckbxAliexpress.setEnabled(false);
		chckbxAliexpress.setBackground(new Color(240, 248, 255));
		chckbxAliexpress.setBounds(240, 133, 85, 23);
		chckbxAliexpress.setName(Constant.ALIEXPRESS);
//		jplatform[3]=chckbxAliexpress;
		chckbxAliexpress.addItemListener(new MyItemListener());
		panel.add(chckbxAliexpress);
		
		JLabel label = new JLabel("\u8BF7\u60A8\u586B\u5199\u91C7\u96C6\u9875\u6570\uFF1A");
		label.setFont(new Font("����", Font.PLAIN, 12));
		label.setBounds(10, 162, 112, 15);
		panel.add(label);
		
		JLabel label_1 = new JLabel("\u8D77\u59CB\u9875\uFF1A");
		label_1.setBounds(20, 187, 54, 15);
		panel.add(label_1);
		
		startpage_text = new JTextField();
		startpage_text.setText("1");
		startpage_text.setColumns(10);
		startpage_text.setBounds(76, 187, 59, 19);
		panel.add(startpage_text);
		
		JLabel label_4 = new JLabel("\u7EC8\u6B62\u9875\uFF1A");
		label_4.setBounds(176, 187, 54, 15);
		panel.add(label_4);
		
		endpage_text = new JTextField();
		endpage_text.setText("1");
		endpage_text.setColumns(10);
		endpage_text.setBounds(232, 185, 59, 19);
		panel.add(endpage_text);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(224, 255, 255));
		panel_1.setBounds(0, 0, 154, 410);
		frmLazada.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_l1 = new JLabel("New label");
		lblNewLabel_l1.addMouseListener(this);
		lblNewLabel_l1.setBounds(0, 67, 144, 122);
		lblNewLabel_l1.setIcon(new ImageIcon(Main.class.getResource("/image/left1.jpg")));
		panel_1.add(lblNewLabel_l1);

		JLabel lblNewLabel_l2 = new JLabel("New label");
		lblNewLabel_l2.addMouseListener(this);
		lblNewLabel_l2.setBounds(0, 198, 154, 194);
		lblNewLabel_l2.setIcon(new ImageIcon(Main.class.getResource("/image/left2.jpg")));
		panel_1.add(lblNewLabel_l2);
		
		JTextArea txtrqqdsQq = new JTextArea();
		txtrqqdsQq.setBackground(new Color(224, 255, 255));
		txtrqqdsQq.setEditable(false);
		txtrqqdsQq.setToolTipText("");
		txtrqqdsQq.setText(" \u5BA2\u670DQQ\uFF1A318074670\r\n \u5FAE  \u4FE1\uFF1Ads318074670\r\n QQ  \u7FA4\uFF1A609970186");
		txtrqqdsQq.setBounds(0, 0, 154, 68);
		panel_1.add(txtrqqdsQq);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(224, 255, 255));
		panel_2.setBounds(482, 0, 145, 410);
		frmLazada.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_r1 = new JLabel("\u53F3\u4E00\u5E7F\u544A");
		lblNewLabel_r1.addMouseListener(this);
		lblNewLabel_r1.setBounds(0, 0, 145, 212);
		lblNewLabel_r1.setIcon(new ImageIcon(Main.class.getResource("/image/right1.jpg")));
		panel_2.add(lblNewLabel_r1);
		
		JLabel lblNewLabel_r2 = new JLabel("New label");
		lblNewLabel_r2.addMouseListener(this);
		lblNewLabel_r2.setBounds(0, 216, 145, 62);
		lblNewLabel_r2.setIcon(new ImageIcon(Main.class.getResource("/image/right2.jpg")));
		panel_2.add(lblNewLabel_r2);
		
		JLabel lblNewLabel_r3 = new JLabel("New label");
		lblNewLabel_r3.addMouseListener(this);
		lblNewLabel_r3.setBounds(0, 286, 145, 72);
		lblNewLabel_r3.setIcon(new ImageIcon(Main.class.getResource("/image/right3.jpg")));
		panel_2.add(lblNewLabel_r3);
		
		JLabel lblNewLabel_r4 = new JLabel("New label");
		lblNewLabel_r4.addMouseListener(this);
		lblNewLabel_r4.setBounds(0, 366, 145, 34);
		lblNewLabel_r4.setIcon(new ImageIcon(Main.class.getResource("/image/right4.jpg")));
		panel_2.add(lblNewLabel_r4);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.textHighlightText);
		panel_3.setBounds(154, 297, 327, 113);
		frmLazada.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		 
		
		catchInfoArea= new ConnectImpl();
		catchInfoArea.setBounds(0, 0, 327, 151);
		//panel_3.add(textArea1);
		catchInfoArea.setBackground(SystemColor.text);
		
		JScrollPane scrollPane = new JScrollPane(catchInfoArea);
		scrollPane.setBounds(0, 0, 327, 113);
		panel_3.add(scrollPane);
		frmLazada.setVisible(true);
		frmLazada.addWindowListener(new WindowAdapter() {  //�ر�֮ǰҪ�ύ�Ѿ��ռ��������Լ�mac-1
			public void windowClosing(WindowEvent e) { 
				frmLazada.dispose();
		     }   
			public void windowClosed(WindowEvent e) {  
               if(HttpHandler.updateUser(0))
                System.exit(0);
            }  
			  
			});
	}

	 
	@Override
	public void mouseClicked(MouseEvent e) {
		
		try {   
            URI uri = new URI(Constant.ADVERTISEMENT);  
            Desktop.getDesktop().browse(uri);  
        } catch (URISyntaxException aa) {  
            aa.printStackTrace();  
        } catch (IOException ee) {  
            ee.printStackTrace();  
        }  
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
    public HashMap getActiveJcb(int type) {
    	HashMap result=new HashMap();
    	if(type==1) {//1Ϊjway
    		for(int i=0;i<jway.length;i++) {
    			if(jway[i].isSelected()) {
    				result.put(jway[i].getName(), wayText[i]);
    				break;
    			}
    		}
    	}
    	else {
    		for(int i=0;i<jway.length;i++) {
    			if(jplatform[i].isSelected()) {
    				result.put(jplatform[i].getName(), true);
    				break;
    			}
    	}
    }
		return result;
    
}
	class MyItemListener implements ItemListener {  
	    public void itemStateChanged(ItemEvent e) {  
	        JCheckBox jcb = (JCheckBox) e.getItem();// ���õ����¼�ǿ��ת��ΪJCheckBox��  
	        if (jcb.isSelected()) {// �ж��Ƿ�ѡ��  
	        	if(searchArry(jcb)==0)
	        	for(int i=0;i<jway.length;i++){
	        		if(!jway[i].equals(jcb)) jway[i].setSelected(false);
	        	}
	        	else 
	        	for(int i=0;i<jplatform.length;i++){
		        	if(!jplatform[i].equals(jcb)) jplatform[i].setSelected(false);
		        }
	        } 
	    } 
	    
	    public int searchArry(JCheckBox jcb) {  
	    	int i=0;
	    	while(i<4){
	    		if(i>2) return 1;
	    		if(jcb.equals(jway[i])) return 0;
	    		if(jcb.equals(jplatform[i])) return 1;
	    	}
			return 0;
	       
	    }
	    

}
}
