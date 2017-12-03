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
import com.lazada.handler.TestConnectImpl;
import com.lazada.model.Constant;
import com.lazada.model.json.ItemListElement;
import com.lazada.model.json.JsonRootBean;

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
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class TestMain implements MouseListener{

	JFrame frmLazada;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private TestConnectImpl textArea1;
	private JDialog d;
	public  static final  String ADVERTISEMENT="http://ylfcoding.cn";
	private final JSeparator separator_2 = new JSeparator();

//	public final static String website="http://www.lazada.com.my/catalog/?q="; 
//	private String keyword;
//	private int startpage;
//	private int endpage;
//	private String savepath;
//	private int exlRow;
	 
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
	public TestMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frmLazada = new JFrame();
//		frmLazada.setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("\\image\\lazada.jpg")));
		frmLazada.setIconImage(new ImageIcon(Main.class.getResource("/image/lazada.jpg")).getImage());
		frmLazada.setTitle("LAZADA\u6536\u96C6\u5668");
		frmLazada.setBounds(100, 100, 643, 437);
		frmLazada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLazada.getContentPane().setLayout(null);
		frmLazada.setLocation(TestLogin.centreContainer(frmLazada.getSize()));
		frmLazada.setResizable(false);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setBounds(154, 0, 327, 255);
		frmLazada.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u5173\u952E\u8BCD\uFF1A");
		lblNewLabel.setBounds(10, 20, 54, 15);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setText("womens shoes");
		textField.setBounds(57, 17, 148, 21);
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
				    if(textField_1.getText().equals("")||textField_2.getText().equals("")) {
				    	JOptionPane.showMessageDialog(d,"没有输入起始页和终止页！","请重新输入",JOptionPane.WARNING_MESSAGE);
				    }
				    if(textField.getText().equals("")) {
				    	JOptionPane.showMessageDialog(d,"没有输入关键词！","请重新输入",JOptionPane.WARNING_MESSAGE);
				    }
				    if(textField_1.getText().matches("[0-9]+")&&textField_2.getText().matches("[0-9]+")) {	
							new Thread(new Runnable() {
					            @Override
					            public void run() {							
								textArea1.setPro(textField.getText(),Integer.parseInt(textField_1.getText()),Integer.parseInt(textField_2.getText()),textField_3.getText()+"\\" +textField_4.getText()+".xls");
								try {
									textArea1.startCatching();
								
							if(JOptionPane.showConfirmDialog(d,"第一页数据收集完成，需要更多数据请访问本软件官网！","nice!",JOptionPane.DEFAULT_OPTION)==0){							 
						            URI uri = new URI(ADVERTISEMENT);  
						            Desktop.getDesktop().browse(uri);  						    
							    }
								} catch (WriteException | IOException | URISyntaxException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
								System.exit(0);	
					         }
					       }).start();		
								
				     }
				    else JOptionPane.showMessageDialog(d,"起始页和终止页需要数字！","请重新输入",JOptionPane.WARNING_MESSAGE);
				    
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
		        if(jchoose.getCurrentDirectory()== null||jchoose.getCurrentDirectory().toString().length()==0||jchoose.getSelectedFile()==null){            
		        }else{  
		        	textField_3.setText(jchoose.getSelectedFile().getPath());  
		        	if(textField_4.getText()==null||textField_4.getText().length()==0) textField_4.setText(textField.getText()+"_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		        }
		        
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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"lazada", "\u5F85\u5F00\u53D1.."}));
		comboBox.setBounds(227, 17, 89, 21);
		panel.add(comboBox);
		
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
		txtrqqdsQq.setText("\u5BA2\u670DQQ\uFF1A318074670\r\n\u5FAE  \u4FE1\uFF1Ads318074670\r\nQQ \u7FA4\uFF1A609970186");
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
		panel_3.setBounds(154, 255, 327, 155);
		frmLazada.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		
		textArea1= new TestConnectImpl();
		textArea1.setBounds(0, 0, 327, 151);
		//panel_3.add(textArea1);
		textArea1.setBackground(SystemColor.text);
		
		JScrollPane scrollPane = new JScrollPane(textArea1);
		scrollPane.setBounds(0, 0, 327, 151);
		panel_3.add(scrollPane);
		frmLazada.setVisible(true);	
	}

	 
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		try {  
            URI uri = new URI(ADVERTISEMENT);  
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
}
