package com.zjht.youoil.util.payquery;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件工具类
 * @author liujun
 * @since 2017年6月30日 上午9:28:53
 */
public abstract class PropertiesUtils {


	private static Properties getProp(){
		Properties p = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = PropertiesUtils.class.getResourceAsStream("/config.properties");
			p.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return p;
	}

	public static String getProperty(String key) {
		return getProp().getProperty(key);
	}

}
