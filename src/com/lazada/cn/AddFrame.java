package com.lazada.cn;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.google.gson.Gson;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;


import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class AddFrame {
    
	private JFrame jf;
	private JTextField name;
	private JTextField password;
	private JTextField money;
	private JTextField paytime;
	private JTextField limittime;
	private JTextField pcnum;
	private JTextArea other;
	private JComboBox catchnum;
	private JDialog d;
	
	private final static String PATH="/userinfo";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      new AddFrame().add();
	}


	public void add(){
		jf=new JFrame();
		jf.setTitle("\u589E\u52A0\u7528\u6237\u4FE1\u606F");
		
		JPanel panel = new JPanel();
		jf.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		lblNewLabel.setBounds(27, 20, 54, 15);
		panel.add(lblNewLabel);
		
		name = new JTextField();
		name.setBounds(81, 17, 155, 21);
		name.setText("      \u586B\u5199QQ\u53F7\u7801");
		panel.add(name);
		name.setColumns(10);
		
		JLabel label = new JLabel("\u5BC6  \u7801\uFF1A");
		label.setBounds(27, 45, 54, 15);
		panel.add(label);
		
		password = new JTextField();
		password.setBounds(81, 42, 155, 21);
		panel.add(password);
		password.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\u4ED8  \u6B3E\uFF1A");
		lblNewLabel_1.setBounds(27, 70, 54, 15);
		panel.add(lblNewLabel_1);
		
		money = new JTextField();
		money.setBounds(81, 70, 155, 21);
		panel.add(money);
		money.setColumns(10);
		
		JLabel label_1 = new JLabel("\u652F\u4ED8\u65E5\uFF1A");
		label_1.setBounds(27, 105, 54, 15);
		panel.add(label_1);
		
		paytime = new JTextField();
		paytime.setBounds(81, 105, 155, 21);
		panel.add(paytime);
		paytime.setColumns(10);
		
		JLabel label_2 = new JLabel("\u5230\u671F\u65E5\uFF1A");
		label_2.setBounds(27, 144, 54, 15);
		panel.add(label_2);
		
		limittime = new JTextField();
		limittime.setBounds(81, 141, 155, 21);
		panel.add(limittime);
		limittime.setColumns(10);
		
		JLabel label_3 = new JLabel("\u7535\u8111\u6570\uFF1A");
		label_3.setBounds(27, 185, 54, 15);
		panel.add(label_3);
		
		pcnum = new JTextField();
		pcnum.setBounds(82, 182, 66, 21);
		panel.add(pcnum);
		pcnum.setColumns(10);
		
		JLabel label_4 = new JLabel("\u6570\u91CF\uFF1A");
		label_4.setBounds(215, 185, 54, 15);
		panel.add(label_4);
		
		catchnum = new JComboBox();
		catchnum.setBounds(263, 182, 66, 21);
		catchnum.setModel(new DefaultComboBoxModel(new String[] {"\u65E0\u9650\u5236", "2\u4E07\u4E2A"}));
		panel.add(catchnum);
		
		JButton button = new JButton("\u786E\u5B9A");
		button.setBounds(169, 229, 66, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 if(name.getText().equals("")||password.getText().equals("")||money.getText().equals("")||paytime.getText().equals("")||limittime.getText().equals("")||pcnum.getText().equals("")||catchnum.getSelectedItem().toString().equals("")||other.getText().equals("")) 
					 JOptionPane.showMessageDialog(d,"请输入以上所有信息！","请重新输入",JOptionPane.WARNING_MESSAGE);
					 else {
						 Userinfo ui=new Userinfo(UUID.randomUUID().toString(),name.getText(),password.getText(),money.getText(),paytime.getText(),limittime.getText(),pcnum.getText(),catchnum.getSelectedItem().toString(),other.getText());
						 Gson gson=new Gson();
						 WriteHandler(gson.toJson(ui));
						 JOptionPane.showMessageDialog(d,"添加成功！","nice！",JOptionPane.WARNING_MESSAGE);
					 }
				
			}
		});
		button.setBackground(SystemColor.activeCaption);
		panel.add(button);
		
		JLabel label_5 = new JLabel("\u5907\u6CE8\uFF1A");
		label_5.setBounds(263, 20, 54, 15);
		panel.add(label_5);
		
		other = new JTextArea();
		other.setBounds(295, 41, 124, 98);
		panel.add(other);
		jf.setBounds(100, 100, 445, 311);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jf.getContentPane().setLayout(null);
		//jf.setLocation(Login.centreContainer(jf.getSize()));
		jf.setVisible(true);
	}
	
	private void WriteHandler(String info) {
		try{
			FileWriter writer = new FileWriter(PATH, true);
	        writer.write(info);
	        writer.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
