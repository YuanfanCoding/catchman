package com.lazada.ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import org.apache.http.ParseException;

import com.google.gson.Gson;
import com.lazada.handler.HttpHandler;
import com.lazada.model.Constant;
import com.lazada.model.user.Userinfo;
import com.lazada.util.CaptchaLabel;

public class Login extends JFrame implements ActionListener {
	private static final long serialVersionUID = -4655235896173916415L;
	private JPanel contentPane;
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	private JTextField validateTextField;
	private String randomText;
	JDialog d;
    
	public static void main(String args[]) {
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setTitle(Constant.SOFTWARENAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		JPanel usernamePanel = new JPanel();
		contentPane.add(usernamePanel);
		JLabel usernameLable = new JLabel("\u7528\u6237\u540D\uFF1A");
		usernameLable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		usernamePanel.add(usernameLable);
		usernameTextField = new JTextField();
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
		CaptchaLabel label = new CaptchaLabel(randomText);// 随机验证码
		label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		validatePanel.add(label);
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel);
		JButton submitButton = new JButton("登录");
		submitButton.addActionListener(this);
		submitButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		buttonPanel.add(submitButton);
		JButton cancelButton = new JButton("退出");
		cancelButton.addActionListener(this);
		cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		buttonPanel.add(cancelButton);
		JButton subscButton = new JButton("没有账号？");
		subscButton.addActionListener(this);
		subscButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		//subscButton.setForeground(Color.RED);
		buttonPanel.add(subscButton);
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
		String cmd = e.getActionCommand();
		if (cmd.equals("登录")) {
			if (!randomText.equals(validateTextField.getText()))
				JOptionPane.showMessageDialog(d, "验证码错误！", "请重新输入",
						JOptionPane.WARNING_MESSAGE);
			else {
				if(!doCheckVersion()) {
					if(JOptionPane.showConfirmDialog(d, "请到官网"+Constant.WEBSITE+"更新版本！", "版本更新",
						JOptionPane.WARNING_MESSAGE)==0) {
						  URI uri;
						try {
							uri = new URI(Constant.WEBSITE);
						    Desktop.getDesktop().browse(uri);  
						} catch (URISyntaxException |IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
				          
					}
				}
				else {
				String name = usernameTextField.getText();
				char[] c = passwordField.getPassword();
				String password = new String(c);
				Userinfo ui=null;
				try {
					ui = HttpHandler.getUser(name, password,Constant.MACADDDRESS);
				} catch (ParseException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (ui!=null) { 
					//先验证过期时间，再验证mac数量，最后验证抓取数量
					String message=doCheckUser(ui);
					if(message.equals("")) {
					   Constant.areadycatchnum=Integer.parseInt(ui.getAreadynum());
					   Constant.totalnum=ui.getCatchnum();
					   Constant.name=ui.getName();
					   Constant.password=ui.getPassword();
					   this.dispose();
				       Main window = new Main();
					}  
					else {
						JOptionPane.showMessageDialog(d, message, "抱歉",
							JOptionPane.WARNING_MESSAGE);
					      usernameTextField.setText("");
					      passwordField.setText("");
					}
				} else {
					JOptionPane.showMessageDialog(d, "用户名或密码错误", "请重新输入",
							JOptionPane.WARNING_MESSAGE);
					usernameTextField.setText("");
					passwordField.setText("");
				}
			}
			}
		}
		if (cmd.equals("退出"))
			System.exit(0);
		
		if (cmd.equals("没有账号？")){
			JOptionPane.showMessageDialog(d, "请添加QQ:318074670 或者 微信:ds318074670免费获取账号！", "账号获取",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private String doCheckUser(Userinfo ui) {
		try {	
		if(ui.getPcnum().equals("0")) return "您的使用电脑台数超过了！请退出其他已经登陆的电脑！";
		if(!ui.getCatchnum().equals("无限制") && Integer.parseInt(ui.getCatchnum())<=Integer.parseInt(ui.getAreadynum())) return "抓取数量已超出之前购买的额度！请联系客服续费！";
		if(new SimpleDateFormat("yyyy/MM/dd").parse(ui.getLimittime()).before(new Date())) return "你的使用期限已到，请联系客服续费！谢谢！";		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private boolean doCheckVersion()  {
        
		try {
			return HttpHandler.getVersion().equals(Constant.VERSION);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static String getRandomString(int length) { // length表示生成字符串的长度
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