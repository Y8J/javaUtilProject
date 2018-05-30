package com.zjht.youoil.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerificationCode {

	// 给定范围获得随机颜色
	static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);

	}

	/**
	 * 生成验证图片
	 * 
	 * @param num1
	 * @param num2
	 * @param funNo 操作符 0加1减3乘
	 * @author yangxiaoyong
	 * @version 创建时间：2014年5月28日 下午3:46:08 参考 www.sql8.net
	 */
	public static BufferedImage createVerificationImage(int num1, int num2, int funNo) {
		// 在内存中创建图象
		int width = 100, height = 30;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(240, 250));
		g.fillRect(0, 0, width, height);
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(180, 230));
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String funMethod = "";
		switch (funNo) {
		case 0:
			funMethod = "+";
			break;
		case 1:
			funMethod = "- ";
			break;
		case 2:
			funMethod = "×";
			break;
		}
		String calc = num1 + " " + funMethod + " " + num2 + " = ?";
		g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
		g.drawString(calc, 5, 25);
		// 图象生效
		g.dispose();
		return image;
	}
}
