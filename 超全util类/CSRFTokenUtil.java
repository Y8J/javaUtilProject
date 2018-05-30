package com.zjht.youoil.util;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public final class CSRFTokenUtil {

    public static final String CSRF_PARAM_NAME  = "token";

    public static final String CSRF_TOKEN_ERROR = "token_error";


    public static String getTokenForSession(HttpServletRequest request) {
        String token = null;
        HttpSession session = request.getSession();
        synchronized (session) {
            token = (String) session.getAttribute(CSRF_PARAM_NAME);
            if (null == token) {
                token = generateToken(request);
            }
        }
        return token;
    }

    public static String generateToken(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String token = UUID.randomUUID().toString();
        session.setAttribute(CSRF_PARAM_NAME, token);
        request.setAttribute(CSRF_PARAM_NAME, token);
        return token;
    }

    public static boolean isValidCsrfToken(HttpServletRequest request) {
        String requestToken = getTokenFromRequest(request);
        if (StringUtils.isBlank(requestToken)) {
            request.setAttribute(CSRF_TOKEN_ERROR, "非法请求！");
            return false;
        }

        String token = getTokenForSession(request);
        if (StringUtils.equals(requestToken, token)) {
            return true;
        } else {
            request.setAttribute(CSRF_TOKEN_ERROR, "请求超时！");
            return false;
        }
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        return request.getParameter(CSRF_PARAM_NAME);
    }

    private CSRFTokenUtil() {

    };

}
