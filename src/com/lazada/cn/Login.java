package com.lazada.cn;

import javax.swing.*;

import java.awt.*; //导入必要的包
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
		
		 labeln=new JLabel("123");//北部  
		 panemid=new JPanel();//中部
		 panemid.setLayout(new GridLayout(3,2));  
		 labels=new JLabel("456");//南部  
		// 创建组件
		jb1 = new JButton("登录");
		jb2 = new JButton("取消");

		jtf = new JTextField(10);
		jpwd = new JPasswordField(10);

		jl1 = new JLabel("用户名：");
		jl2 = new JLabel("密    码：");

		jb1.addActionListener(this);
		jb2.addActionListener(this);
		
		// 设置布局管理器
		//this.setLayout(new GridLayout(3, 1,5,5));
		// 添加组件
		panemid.add(jl1);
		panemid.add(jtf);

		panemid.add(jl2);
		panemid.add(jpwd);

		panemid.add(jb1);
		panemid.add(jb2);
		
		this.add(labeln,BorderLayout.NORTH);  
        this.add(labels,BorderLayout.SOUTH);  
        this.add(panemid,BorderLayout.CENTER); //默认是中间 
		// 设置窗体属性
		this.setTitle("登录界面");
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
		if(cmd.equals("登录"))
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
		JOptionPane.showMessageDialog(d,"用户名或密码错误","请重新输入",JOptionPane.WARNING_MESSAGE);
		jtf.setText("");
		jpwd.setText("");
		}
		}
		if(cmd.equals("取消"))
		System.exit(0);
		}
	
	}


