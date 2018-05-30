package com.zjht.youoil.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCodeHelper {
	private static final Logger log = LoggerFactory.getLogger(QrCodeHelper.class);

	public static void main(String[] args) {
		writeQrcode("1234567", 250, 250, "F:/");
	}

	public static void writeQrcode(String qrcode, int width, int height, String path) {
		String text = "CODE:T:c;M:00000000000;BC:0000000000000000;TC:{qrcode};K:1;".replace("{qrcode}", qrcode);
		String format = "png";
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 0);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
			// String filePath="C:/new.png";
			String filePath = path + File.separator + qrcode + ".png";
			Path file = new File(filePath).toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, file);
			pressWithoutText(qrcode, filePath, filePath, Color.BLACK);
		} catch (WriterException e) {
			log.error("WriterException", e);
		} catch (IOException e) {
			log.error("IOException", e);
		}
	}

	public static void writeQrcode(String qrcode, int width, int height, String path,String explain) {
		String text = "CODE:T:c;M:00000000000;BC:0000000000000000;TC:{qrcode};K:1;".replace("{qrcode}", qrcode);
		String format = "png";
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 4);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
			// String filePath="C:/new.png";
			String filePath = path + File.separator + qrcode + ".png";
			Path file = new File(filePath).toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, file);
			int font = 14; //字体大小
//			//在二维码下方添加文字（文字居中）
			pressText(qrcode, explain, filePath, filePath, Font.BOLD, Color.black, font, width, height);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static int FONT_HEIGHT = 20;

	public static void writeBarQrcode(String qrcode, int width, int height, String path) {
		String text = "CODE:{qrcode}".replace("{qrcode}", qrcode);
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);
		int codeWidth = 3 + // start guard
				(7 * 6) + // left bars
				5 + // middle guard
				(7 * 6) + // right bars
				3; // end guard
		codeWidth = Math.max(codeWidth, width);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, codeWidth, height,
					hints);
			String filePath = path + File.separator + qrcode + ".png";
			int imageW = width;
			int imageH = height + FONT_HEIGHT;
			// 图片刻字
			BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			Image src = MatrixToImageWriter.toBufferedImage(bitMatrix);
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(Color.WHITE);
			for (int i = 0; i < FONT_HEIGHT; i++) {
				g.drawLine(0, height + i, width, height + i);
			}
			g.setColor(Color.black);
			int fontSize = 18;
			g.setFont(new Font(null, Font.BOLD, fontSize));
			// 计算文字开始的位置
			// x开始的位置：（图片宽度-字体大小*字的个数）/2
			int startX = (imageW - (fontSize * qrcode.length())) / 2 + imageW / 5;
			// y开始的位置：图片高度-（图片高度-图片宽度）/2+5
			int startY = imageH - fontSize / 2 + 5;
			g.drawString(qrcode, startX, startY);
			g.dispose();
			FileOutputStream out = new FileOutputStream(new File(filePath));
			ImageIO.write(image, "JPEG", out);
			out.close();
		} catch (Exception e) {
			log.error("Exception", e);
		}
	}
	// 单纯加字
	public static void pressText(String pressText,String explain, String newImg, String targetImg, int fontStyle, Color color,
			int fontSize, int width, int height) {

		// 计算文字开始的位置
		// x开始的位置：（图片宽度-字体大小*字的个数）/2
		int startX = (width - (fontSize * pressText.length() / 2)) / 2;
		int startX2 = (width - (fontSize * explain.length())) / 2;
		// y开始的位置：图片高度-（图片高度-图片宽度）/2
		int startY = height - fontSize / 2 -10;
		int startY2 = (height + 14) - fontSize / 2 - 10;

		try {
			File file = new File(targetImg);
			Image src = ImageIO.read(file);
			int imageW = src.getWidth(null);
			int imageH = src.getHeight(null);
			BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, imageW, imageH, null);
			g.setColor(color);
			g.setFont(new Font(null, fontStyle, fontSize));
			g.drawString(pressText, startX, startY);
			g.drawString(explain, startX2, startY2);
			g.dispose();
			FileOutputStream out = new FileOutputStream(newImg);
			ImageIO.write(image, "JPEG", out);
			// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			// encoder.encode(image);
			out.close();
			System.out.println("image press success");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void pressWithoutText(String code, String newImg, String targetImg, Color color) {
		try {
			File file = new File(targetImg);
			Image src = ImageIO.read(file);
			int imageW = src.getWidth(null);
			int imageH = src.getHeight(null);
			BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, imageW, imageH, null);
			g.setColor(color);
			g.drawString(code, 0, 0);
			g.dispose();
			FileOutputStream out = new FileOutputStream(newImg);
			ImageIO.write(image, "JPEG", out);
			out.close();
		} catch (Exception e) {
			log.error("Exception", e);
		}
	}
}
