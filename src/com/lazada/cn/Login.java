package com.lazada.cn;

import javax.swing.*;

import java.awt.*; //�����Ҫ�İ�
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener{
	JButton jb1, jb2;
	JTextField jtf;
	JPasswordField jpwd;
	JLabel jl1, jl2,labeln,labels;
	JPanel jp1, jp2, jp3,panemid;
	JDialog d;
	public Login() {
		
		 labeln=new JLabel("123");//����  
		 panemid=new JPanel();//�в�
		 panemid.setLayout(new GridLayout(3,2));  
		 labels=new JLabel("456");//�ϲ�  
		// �������
		jb1 = new JButton("��¼");
		jb2 = new JButton("ȡ��");

		jtf = new JTextField(10);
		jpwd = new JPasswordField(10);

		jl1 = new JLabel("�û�����");
		jl2 = new JLabel("��    �룺");

		jb1.addActionListener(this);
		jb2.addActionListener(this);
		
		// ���ò��ֹ�����
		//this.setLayout(new GridLayout(3, 1,5,5));
		// ������
		panemid.add(jl1);
		panemid.add(jtf);

		panemid.add(jl2);
		panemid.add(jpwd);

		panemid.add(jb1);
		panemid.add(jb2);
		
		this.add(labeln,BorderLayout.NORTH);  
        this.add(labels,BorderLayout.SOUTH);  
        this.add(panemid,BorderLayout.CENTER); //Ĭ�����м� 
		// ���ô�������
		this.setTitle("��¼����");
		this.setSize(500, 300);
		this.setLocation(400, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	public static void main(String[] args) throws Exception {
		new Login();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd=e.getActionCommand();
		if(cmd.equals("��¼"))
		{
		String name=jtf.getText();
		char[] c=jpwd.getPassword();
		String password=new String(c);
		if(IOhandler.isValid(name, password))
		{
	    this.dispose();
		//new SimpleUi().initUI();
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
		return;
		}
		else
		{
		JOptionPane.showMessageDialog(d,"�û������������","����������",JOptionPane.WARNING_MESSAGE);
		jtf.setText("");
		jpwd.setText("");
		}
		}
		if(cmd.equals("ȡ��"))
		System.exit(0);
		}
	
	}


