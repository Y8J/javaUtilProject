package com.zjht.youoil.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.zjht.youoil.common.web.Constants;

/**
 * Cookie 辅助类
 * 
 * @author liufang
 * 
 */
public class CookieUtils {

    /**
     * 每页条数cookie名称
     */
    public static final String COOKIE_PAGE_SIZE = "_cookie_page_size";

    /**
     * 默认每页条数
     */
    public static final int    DEFAULT_SIZE     = 20;

    /**
     * 最大每页条数
     */
    public static final int    MAX_SIZE         = 200;


    /**
     * 获得cookie的每页条数
     * 
     * 使用_cookie_page_size作为cookie name
     * 
     * @param request
     *            HttpServletRequest
     * @return default:20 max:200
     */
    public static int getPageSize(HttpServletRequest request) {
        Assert.notNull(request);
        Cookie cookie = getCookie(request, COOKIE_PAGE_SIZE);
        int count = 0;
        if (cookie != null) {
            try {
                count = Integer.parseInt(cookie.getValue());
            } catch (Exception e) {}
        }
        if (count <= 0) {
            count = DEFAULT_SIZE;
        } else if (count > MAX_SIZE) {
            count = MAX_SIZE;
        }
        return count;
    }

    /**
     * 获得cookie
     * 
     * @param request
     *            HttpServletRequest
     * @param name
     *            cookie name
     * @return if exist return cookie, else return null.
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Assert.notNull(request);
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) { return c; }
            }
        }
        return null;
    }

    /**
     * 取消cookie
     * 
     * @param response
     * @param name
     * @param domain
     */
    public static void cancleCookie(HttpServletResponse response, String name, String domain) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        if (!StringUtils.isBlank(domain)) {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);
    }


    //==========================================================================================================================================

    private static final Logger log = LoggerFactory.getLogger(CookieUtils.class);


    private CookieUtils() {}

    public static boolean saveBaseCookie(HttpServletRequest request, HttpServletResponse response, String key, String value) {
        return saveCookie(request, response, CookieManager.BASE_COOKIE_NAME, key, value);
    }

    public static boolean saveBaseCookie(HttpServletRequest request, HttpServletResponse response, Map<String, String> cookieMap) {
        return saveCookie(request, response, CookieManager.BASE_COOKIE_NAME, cookieMap);
    }

    public static boolean saveCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, Map<String, String> cookieMap) {
        boolean result = true;
        try {
            // 往Cookies
            CookieManager cookieManager = new CookieManager(request, response, PropertiesUtil.getAppDomain(), Constants.SPT);
            Set<Entry<String, String>> set = cookieMap.entrySet();
            for (Entry<String, String> entry : set) {
                cookieManager.setValue(cookieName, entry.getKey(), entry.getValue());
            }
            cookieManager.save(cookieName, CookieManager.MAX_COOKIE_AGE);
        } catch (Exception e) {
            log.error("saveCookie ERROR cookieName: " + cookieName + "  cookieMap :" + cookieMap.toString());
            result = false;
        }
        return result;
    }

    public static boolean saveCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String key, String value) {
        boolean result = true;
        try {
            // 往Cookies
            CookieManager cookieManager = new CookieManager(request, response, PropertiesUtil.getAppDomain(), Constants.SPT);
            cookieManager.setValue(cookieName, key, value);
            cookieManager.save(cookieName, CookieManager.MAX_COOKIE_AGE);
        } catch (Exception e) {
            log.error("saveCookie ERROR cookieName: " + cookieName + " key:" + key + "  value :" + value);
            result = false;
        }
        return result;
    }

    public static String getTemplates(HttpServletRequest request, HttpServletResponse response) {
        try {
            String templatesPath = getBaseValue(request, response, CookieManager.TEMPLATES);
            if (StringUtils.isNotBlank(templatesPath)) {
                return templatesPath;
            } else {
                templatesPath = Constants.TEMPLATES_PATH;
                saveBaseCookie(request, response, CookieManager.TEMPLATES, templatesPath);
                return templatesPath;
            }
        } catch (Exception e) {
            log.error("getTemplatesPathValue ERROR ");
            return Constants.TEMPLATES_PATH;
        }
    }

    public static String getUserCity(HttpServletRequest request, HttpServletResponse response) {
        try {
            String templatesPath = getBaseValue(request, response, CookieManager.CITY);
            if (StringUtils.isNotBlank(templatesPath)) {
                return templatesPath;
            } else {
                templatesPath = getCityCodeByRequest(request);
                if (StringUtils.isBlank(templatesPath)) {
                    templatesPath = Constants.DEFAULT_CITY;
                }
                saveBaseCookie(request, response, CookieManager.CITY, templatesPath);
                return templatesPath;
            }
        } catch (Exception e) {
            log.error("getTemplatesPathValue ERROR ");
            return Constants.DEFAULT_CITY;
        }
    }

    /**
     * 根据用户IP，去ip138查询用户城市
     * 
     * @param request
     * @return
     */
    public static String getCityCodeByRequest(HttpServletRequest request) {
        try {
            String ip = request.getRemoteAddr();
            String strURL = "http://www.ip138.com/ips1388.asp?ip=" + ip + "&action=2";
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), "gb2312");
            BufferedReader bufReader = new BufferedReader(input);
            String line = "";
            StringBuilder contentBuf = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                contentBuf.append(line);
            }
            String buf = contentBuf.toString();
            int beginIx = buf.indexOf("本站主数据");
            int endIx = buf.indexOf("</li><li>");
            String result = buf.substring(beginIx, endIx);
            String[] city = new String(PropertiesUtil.getString("cityList").getBytes("ISO8859-1"), "UTF-8").split("\\|");
            for (String c : city) {
                String[] cy = c.split("-");
                if (result.indexOf(cy[0]) != -1) { return PropertiesUtil.getString(cy[1]); }
            }
        } catch (Exception e) {
            log.error("getCityCodeByRequest", e);
        }
        return Constants.DEFAULT_CITY;
    }

    public static String getBaseValue(HttpServletRequest request, HttpServletResponse response, String key) {
        return getValue(request, response, CookieManager.BASE_COOKIE_NAME, key);
    }

    public static String getValue(HttpServletRequest request, HttpServletResponse response, String key) {
        return getValue(request, response, CookieManager.TEMP_COOKIE_NAME, key);
    }

    public static String getValue(HttpServletRequest request, HttpServletResponse response, String cookieName, String key) {
        CookieManager cookieManager = new CookieManager(request, response, PropertiesUtil.getAppDomain(), Constants.SPT);
        return cookieManager.getValue(cookieName, key);
    }

    //清除Cookie
    public static boolean deleteTempCookie(HttpServletRequest request, HttpServletResponse response) {
        boolean result = true;
        try {
            CookieManager cookieManager = new CookieManager(request, response, PropertiesUtil.getAppDomain(), Constants.SPT);
            cookieManager.removeCookie();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

}
