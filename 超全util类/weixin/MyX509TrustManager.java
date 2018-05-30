package com.zjht.youoil.util.weixin;

import java.security.cert.CertificateException;

import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

public class MyX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return null;
	}

	public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
			String authType) throws CertificateException {

	}

	public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
			String authType) throws CertificateException {

	}
}