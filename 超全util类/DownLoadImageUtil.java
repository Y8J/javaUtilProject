package com.zjht.youoil.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjht.youoil.common.file.FileNameUtils;
import com.zjht.youoil.common.web.Constants;

public class DownLoadImageUtil {
	private static Logger logger = LoggerFactory.getLogger(DownLoadImageUtil.class);
	public static void main(String[] args) {
		try {
			download("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=N7oxsPpcETgBsLHhB_Tgvh1dMJ2LMI4JR4J2KoA0gyLDIaiGFC9HeRdMdQ9WbzDqgvsaYiyoCGcxeyfWsNlm13jQnOhKzQYXnFjRRzz_49z8XtzgEUeQ4SGWIZ57TrhWPNDfAEADBR&media_id=TUWA40AJE2zCHUasjgjMBAP1MzMCh0_yXUWPxdySzFvfGRW-_omKN8NdlcFpGNNd", "D:/2.jpg");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean download(String imgUrl,String path) throws MalformedURLException,FileNotFoundException,IOException {
		URL url = new URL(imgUrl);
		HttpURLConnection connection=(HttpURLConnection) url.openConnection();
		InputStream is = connection.getInputStream();
		OutputStream os = new FileOutputStream(path);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();/* 后面三行为关闭输入输出流以及网络资源的固定格式 */
		is.close();
		if (connection!=null) {
			connection.disconnect();
		}
		return true;
	}
	/**
	 * 下载图片至微信目录
	 * 下载成功则返回本地服务器全路径
	 * 下载失败则返回url全路径
	 * @param url
	 * @param request
	 * @return
	 * @throws MalformedURLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String downloadToWechat(String url,HttpServletRequest request) throws MalformedURLException,FileNotFoundException,IOException {
		ServletContext servletContext=request.getSession().getServletContext();
		String uploadpath=PropertyUtil.getProperty("youoil.upload.path")+"/wechat";
		String real = servletContext.getRealPath(uploadpath);
		String dateDir = FileNameUtils.genPathName();
		// 创建目录
		File root = new File(real, dateDir);
		if (!root.exists()) {
			root.mkdirs();
		}
		// 取文件名
		String ext = "jpg";
		String fileName = FileNameUtils.genFileNameWithOutPath(ext);
		// 相对路径
		String relPath = Constants.SPT + dateDir + Constants.SPT ;
		//图片存放路径
		String outputPath = servletContext.getRealPath(uploadpath+relPath);
		try {
			boolean saveFlag=download(url, outputPath+Constants.SPT+fileName);
			if (saveFlag) {
				//全路径
				String allrelPath = PropertyUtil.getPropertyValueDir("project", "project.site.basepath")+uploadpath+relPath+fileName;
				return allrelPath;
			}else{
				return url;
			}
		} catch (MalformedURLException e) {
//			e.printStackTrace();
			logger.error("下载图片至微信文件夹MalformedURLException异常：",e);
			return url;
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			logger.error("下载图片至微信文件夹FileNotFoundException异常：",e);
			return url;
		} catch (IOException e) {
//			e.printStackTrace();
			logger.error("下载图片至微信文件夹FileNotFoundException异常：",e);
			return url;
		}
	}
}
