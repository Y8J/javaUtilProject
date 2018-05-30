package com.zjht.youoil.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SimpleMailSender {
	private JavaMailSenderImpl mailSender;

	public SimpleMailSender() {

	}

	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String serviceName, Map<String, String> map) {
		try {
			MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			String sender = PropertyUtil.getPropertyValueDir("mail", "mail." + serviceName + ".sender");
			String from = PropertyUtil.getPropertyValueDir("mail", "mail." + serviceName + ".from");
			if (StringUtils.isNotEmpty(sender)) {
				InternetAddress iadd = new InternetAddress(from, sender);
				helper.setFrom(iadd);
			}
			String to = PropertyUtil.getPropertyValueDir("mail", "mail." + serviceName + ".to");
			String subject = PropertyUtil.getPropertyValueDir("mail", "mail." + serviceName + ".subject");
			String template = PropertyUtil.getPropertyValueDir("mail", "mail." + serviceName + ".template");
			helper.setSubject(subject);
			helper.setText(setTemplate(template, map));
			helper.setTo(to.split(","));
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			System.out.println("O . 发送Email失败了....");
			e.printStackTrace();
		}
	}

	private String setTemplate(String template, Map<String, String> map) {
		String text = template;
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			text = StringUtils.replace(text, "${" + key + "}", value);
		}
		return text;
	}
}