package com.zjht.youoil.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;

/**
 * 配置文件处理类 
 * 
 * @author lijunjie
 * 
 * 
 */
public class PropertyUtil {
	/**
	 * 获取属性文件/config/website.properties  的配置
	 * @param key 
	 * @return
	 */
	public static String getProperty(String key) {
		InputStream in = PropertyUtil.class.getResourceAsStream("/config/website.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty(key);
	}
	/**
	 * 根据属性文件路径查找键值
	 * @param key属性文件键
	 * @param path属性文件路径
	 * @return
	 */
	public static String getPropertyByPath(String key,String path) {
		InputStream in = PropertyUtil.class.getResourceAsStream(path);
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty(key);
	}

	// 获取webapp目录下配置文件
	public static String getProperty(String key,String propertityName, HttpServletRequest request) {
		InputStream in = request.getSession().getServletContext().getResourceAsStream("/WEB-INF/config/"+propertityName+".properties");

		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty(key);
	}

	/**
	 * 获取配置文件中部署环境信息，true为生产环境，false为测试环境
	 * 
	 * @return
	 */
	public static boolean isProductDeploy() {
		return (new Boolean(getProperty("isProductDeploy"))).booleanValue();
	}

	/**
	 * 获取对应文件夹下的配置文件
	 * 
	 * @param dirName
	 * @return
	 */
	public static Properties getPropertes(String dirName) {

		InputStream in = PropertyUtil.class.getResourceAsStream("/config/" + dirName + getConfigDir() + "/parameter.properties");

		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p;
	}

	public static String getConfigDir() {
		String dir = "/test";
		if (isProductDeploy()) {
			dir = "/product";
		}
		return dir;
	}

	/**
	 * 获取配置文件里对应的值
	 * @param dirName config下文件夹的名字 与properties文件名对应
	 * @param key 属性名
	 * @return
	 */
	public static String getPropertyValue(String dirName, String key) {
		InputStream in = PropertyUtil.class.getResourceAsStream("/config/" + dirName +".properties");
		
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p.getProperty(key);
	}

	public static String getAppProperty(String key) {
		InputStream in = PropertyUtil.class.getResourceAsStream("/config/app" + getConfigDir() + "/app.properties");

		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p.getProperty(key);
	}
	
	/**
	 * 获取区分测试环境和生产环境的数据
	 * @param name 文件夹名（需与properties文件名对应）
	 * @param key 参数的key
	 * @return
	 */
	public static String getPropertyValueDir(String name, String key) {
		InputStream in = PropertyUtil.class.getResourceAsStream("/config/" + name + getConfigDir() + "/" + name + ".properties");

		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p.getProperty(key);
	}

	/**
	 * 获取配置文件中设定的参数（定时任务中查询时间参数）
	 * 
	 * @param name
	 * @return
	 */
	public static int getDeadline(String name) {
		int deadline = 0;
		String deadlineStr = getAppProperty(name);
		if (StringHelper.isNotBlank(deadlineStr)) {
			deadline = Integer.parseInt(deadlineStr);
		}
		return deadline;
	}
	/**
	 * 查找/config/messages.properties 的属性文件键值对
	 * @param key
	 * @return
	 */
	public static String getMessagesProperty(String key) {
		InputStream in = PropertyUtil.class.getResourceAsStream("/config/messages.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = p.getProperty(key);
		return result;
	}

	public static String getSignTempDirectory() {
		URL url = PropertyUtil.class.getResource("/config/temp");
		if (url != null)
			return url.getPath();
		return "";
	}

	/**
	 * 获取config下dirName文件夹下fileName的配置文件
	 * 
	 * @param dirName 文件夹名
	 * @param fileName 文件名
	 * @return
	 */
	public static Properties getPropertes(String dirName,String fileName) {

		InputStream in = PropertyUtil.class.getResourceAsStream("/config/" + dirName + getConfigDir() + "/"+fileName+".properties");

		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p;
	}
	
	/**
	 * 查找/config/messages.properties 的属性文件键值对
	 * @param key
	 * @return
	 */
	public static String getSMSProperty(String key) {
		InputStream in = PropertyUtil.class.getResourceAsStream("/config/sms.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = p.getProperty(key);
		return result;
	}
}
