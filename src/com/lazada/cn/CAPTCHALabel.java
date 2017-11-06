package com.lazada.cn;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;

public class CAPTCHALabel extends JLabel {
private static final long serialVersionUID = -963570191302793615L;
private String text;// 用于保存生成验证图片的字符串
public CAPTCHALabel(String text) {
this.text = text;
setPreferredSize(new Dimension(60, 36));// 设置标签的大小
}
@Override
public void paint(Graphics g) {
super.paint(g);// 调用父类的构造方法
g.setFont(new Font("微软雅黑", Font.PLAIN, 16));// 设置字体
g.drawString(text, 5, 25);// 绘制字符串
}
}