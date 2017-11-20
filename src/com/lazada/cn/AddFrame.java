package com.lazada.cn;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;


import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddFrame {
    
	private JFrame jf;
	private JTextField txtQq;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
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
		
		txtQq = new JTextField();
		txtQq.setBounds(81, 17, 155, 21);
		txtQq.setText("      \u586B\u5199QQ\u53F7\u7801");
		panel.add(txtQq);
		txtQq.setColumns(10);
		
		JLabel label = new JLabel("\u5BC6  \u7801\uFF1A");
		label.setBounds(27, 45, 54, 15);
		panel.add(label);
		
		textField = new JTextField();
		textField.setBounds(81, 42, 155, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\u4ED8  \u6B3E\uFF1A");
		lblNewLabel_1.setBounds(27, 70, 54, 15);
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(81, 70, 155, 21);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label_1 = new JLabel("\u652F\u4ED8\u65E5\uFF1A");
		label_1.setBounds(27, 105, 54, 15);
		panel.add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(81, 105, 155, 21);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label_2 = new JLabel("\u5230\u671F\u65E5\uFF1A");
		label_2.setBounds(27, 144, 54, 15);
		panel.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setBounds(81, 141, 155, 21);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel label_3 = new JLabel("\u7535\u8111\u6570\uFF1A");
		label_3.setBounds(27, 185, 54, 15);
		panel.add(label_3);
		
		textField_4 = new JTextField();
		textField_4.setBounds(82, 182, 66, 21);
		panel.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel label_4 = new JLabel("\u6570\u91CF\uFF1A");
		label_4.setBounds(215, 185, 54, 15);
		panel.add(label_4);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u65E0\u9650\u5236", "2\u4E07\u4E2A"}));
		comboBox.setBounds(263, 182, 66, 21);
		panel.add(comboBox);
		
		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		button.setBackground(SystemColor.activeCaption);
		button.setBounds(169, 229, 66, 23);
		panel.add(button);
		jf.setBounds(100, 100, 445, 311);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jf.getContentPane().setLayout(null);
		//jf.setLocation(Login.centreContainer(jf.getSize()));
		jf.setVisible(true);
	}
}
