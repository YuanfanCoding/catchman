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

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.JSeparator;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import jxl.write.WriteException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

	JFrame frmLazada;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private ConnectImpl textArea1;
	private JDialog d;
	public  static final  String ADVERTISEMENT="http://ylfcoding.cn";
	private final JSeparator separator_2 = new JSeparator();

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
					try {
						textArea1.setPro("women shoes",1,100,textField_3.getText()+textField_4.getText()+".xls");
						textArea1.startCatching();
					//	new ConnectImpl("women shoes",1,100,textField_3.getText()+textField_4.getText()+".xls").startCatching();
					} catch (WriteException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
		
		textArea1= new ConnectImpl();
		textArea1.setBackground(SystemColor.text);
		textArea1.setBounds(0, 0, 327, 145);
		panel_3.add(textArea1);
	}
}
