package com.zjht.youoil.util;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class CreateTwodimensionCode {
	
	/*public static void main(String[] args) throws Exception {	
		//带logo的二维码测试
		String contents = "CODE:T:c;M:15914298125;BC:5815155343398737;TC:521324825;K:1;";
		//生成二维码
		File img_2d = new File("qr_" + System.currentTimeMillis() + ".png");
		encode(contents,1024,1024,img_2d.getPath());
	}*/
	
	private static final Hashtable<EncodeHintType, Object> encodeHints = new Hashtable<EncodeHintType, Object>();
   /* public static final void encode(String content, int width, int height, String imagePath) throws WriterException, IOException {
       File file = new File(imagePath);
       ImageIO.write(encode(content, width, height), "png", file);
    }*/

    public static final BufferedImage encode(String Imgpath ,String content, int width, int height, int onColor, int offColor) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, encodeHints);
        BufferedImage twodimensioncode = null;
        File logoImg = new File(Imgpath);	
       

		try{
			twodimensioncode = MatrixToImageWriter.toBufferedImage(matrix);
			//获取画笔
			Graphics2D g = twodimensioncode.createGraphics();
			//读取logo图片
			BufferedImage logo = ImageIO.read(logoImg);
			//设置二维码大小，太大，会覆盖二维码，此处20%
			int logoWidth = logo.getWidth(null) > twodimensioncode.getWidth()*2 /10 ? (twodimensioncode.getWidth()*2 /10) : logo.getWidth(null);
			int logoHeight = logo.getHeight(null) > twodimensioncode.getHeight()*2 /10 ? (twodimensioncode.getHeight()*2 /10) : logo.getHeight(null);
			//设置logo图片放置位置
			//中心
			int x = (twodimensioncode.getWidth() - logoWidth) / 2;
			int y = (twodimensioncode.getHeight() - logoHeight) / 2;
			//开始合并绘制图片
			g.drawImage(logo, x, y, logoWidth, logoHeight, null);
			logo.flush();
			twodimensioncode.flush();
		}catch(Exception e){
			System.out.println("二维码绘制logo失败");
		}
		return twodimensioncode;
    }

  /*  public static final BufferedImage encode(String content, int width, int height) throws WriterException {
        return encode(content, width, height, -16777216, -1);
    }
   */
	

	
}
