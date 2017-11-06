package com.lazada.cn;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class LoginTest extends JFrame implements ActionListener{
private static final long serialVersionUID = -4655235896173916415L;
private JPanel contentPane;
private JTextField usernameTextField;
private JPasswordField passwordField;
private JTextField validateTextField;
private String randomText;
  JDialog d;
public static void main(String args[]){
   try {
     UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
     } catch (Throwable e) {
      e.printStackTrace();
    }
    EventQueue.invokeLater(new Runnable(){
    public void run(){
     try{
    LoginTest frame=new LoginTest();
    frame.setVisible(true);
     }catch(Exception e){
       e.printStackTrace();
     }
    }
    });
     }
    public LoginTest(){
      setTitle("系统登录");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      contentPane=new JPanel();
      setContentPane(contentPane);
       contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.PAGE_AXIS));
        JPanel usernamePanel=new JPanel();
        contentPane.add(usernamePanel);
       JLabel usernameLable=new JLabel("\u7528\u6237\u540D\uFF1A");
       usernameLable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        usernamePanel.add(usernameLable);
        usernameTextField=new JTextField();
        usernameTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        usernamePanel.add(usernameTextField);
        usernameTextField.setColumns(10);
        JPanel passwordPanel = new JPanel();
        contentPane.add(passwordPanel);
        JLabel passwordLabel = new JLabel("\u5BC6 \u7801\uFF1A");
        passwordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        passwordPanel.add(passwordLabel);
         passwordField = new JPasswordField();
        passwordField.setColumns(10);
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
         passwordPanel.add(passwordField);
         JPanel validatePanel = new JPanel();
        contentPane.add(validatePanel);
         JLabel validateLabel = new JLabel("\u9A8C\u8BC1\u7801\uFF1A");
          validateLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
         validatePanel.add(validateLabel);
           validateTextField = new JTextField();
         validateTextField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
          validatePanel.add(validateTextField);
            validateTextField.setColumns(5);
           randomText = getRandomString(4);
           CAPTCHALabel label = new CAPTCHALabel(randomText);//随机验证码
             label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
               validatePanel.add(label);
            JPanel buttonPanel=new JPanel();
            contentPane.add(buttonPanel);
            JButton submitButton=new JButton("登录");
           submitButton.addActionListener(this);
             submitButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
               buttonPanel.add(submitButton);
            JButton cancelButton=new JButton("退出");
           cancelButton.addActionListener(this);
           cancelButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
               buttonPanel.add(cancelButton);
           pack();// 自动调整窗体大小
           setLocation(centreContainer(getSize()));// 让窗体居中显示
           this.setSize(500, 300);
              }
    public static Point centreContainer(Dimension size) {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// 获得屏幕大小
    	int x = (screenSize.width - size.width) / 2;// 计算左上角的x坐标
    	int y = (screenSize.height - size.height) / 2;// 计算左上角的y坐标
    	return new Point(x, y);// 返回左上角坐标
    	}
    
    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd=e.getActionCommand();
		if(cmd.equals("登录"))
		{
		if(!randomText.equals(validateTextField.getText()))
			JOptionPane.showMessageDialog(d,"验证码错误！","请重新输入",JOptionPane.WARNING_MESSAGE);
		else {
		String name=usernameTextField.getText();
		char[] c=passwordField.getPassword();
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
		usernameTextField.setText("");
		passwordField.setText("");
		}
		}
		}
		if(cmd.equals("退出"))
		System.exit(0);
		}
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
        Random random = new Random();   
        StringBuffer sb = new StringBuffer();   
        for (int i = 0; i < length; i++) {   
            int number = random.nextInt(base.length());   
            sb.append(base.charAt(number));   
        }   
        return sb.toString();   
     }   

}