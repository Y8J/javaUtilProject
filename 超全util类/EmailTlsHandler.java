package com.zjht.youoil.util;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailTlsHandler {

	private static Log log = LogFactory.getLog(EmailTlsHandler.class);

	private Properties props = new Properties();

	private JavaMailSenderImpl javaMailSender;

	private SimpleMailMessage simpleMailMessage;

	public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public static void main(String[] args) {
		ApplicationContext a = new ClassPathXmlApplicationContext(new String[] { "message-emailTls.xml" });
		EmailTlsHandler handler = (EmailTlsHandler) a.getBean("emailHandler");
		// for(int i=0;i<100;i++)
		handler.sendTLSMail("haha", "<html><body><img src='cid:ATT00001'></body></html>", "1500844084@qq.com", "", "", "");
	}

	/**
	 * 
	 * @param subject
	 * @param content
	 * @param to
	 * @param attachFile
	 * @param cc
	 */
	public void sendSimpleMail(String subject, String content, String to, String attachFile, String cc) {
		sendMail(subject, content, to, attachFile, cc);
	}

	/**
	 * 通过SSL的方式发送邮件，该方式支持想外部邮箱发送邮件
	 * 
	 * @param subject
	 * @param content
	 * @param to
	 * @param attachFile
	 * @param cc
	 */
	public void sendSSLMail(String subject, String content, String to, String attachFile, String cc) {

	}

	/**
	 * 通过TLS的方式发送邮件，该方式支持想外部邮箱发送邮件
	 * 
	 * @param subject
	 * @param content
	 * @param to
	 * @param attachFile
	 * @param cc
	 */
	public void sendTLSMail(String subject, String content, String to, String attachFile, String cc, String inline) {
		Properties javaMailProperties = javaMailSender.getJavaMailProperties();
		final String username = javaMailProperties.getProperty("mail.smtp.user");
		final String password = javaMailProperties.getProperty("mail.smtp.password");
		Session session = Session.getDefaultInstance(props, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		javaMailSender.setSession(session);
		sendMail(subject, content, to, attachFile, cc, inline);
	}

	private void sendMail(String subject, String content, String to, String attachFile, String cc) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "gbk");
			messageHelper.setFrom(simpleMailMessage.getFrom()); // 设置发件人Email
			messageHelper.setSubject(subject); // 设置邮件主题
			messageHelper.setText(content, true); // 设置邮件主题内容
			if (to != null && to.indexOf(";") > -1) {
				messageHelper.setTo(to.split(";")); // 多个收件人Email
			} else if (to != null && !"".equals(to) && to.indexOf(";") < 0) {
				messageHelper.setTo(to); // 单个收件人Email
			}
			if (cc != null && cc.indexOf(";") > -1) {
				messageHelper.setCc(cc.split(";")); // 多个抄送人
			} else if (cc != null && !"".equals(cc) && cc.indexOf(";") < 0) {
				messageHelper.setCc(cc); // 一个抄送人
			}
			if (attachFile != null && !"".equals(attachFile)) {
				File file = new File(attachFile);
				messageHelper.addAttachment("file.getName(", file); // 添加附件
			}
			javaMailSender.send(mimeMessage); // 发送附件邮件
			log.info("send email is finished");
		} catch (Exception e) {
			if (log.isDebugEnabled())
				e.printStackTrace();
			log.error("异常信息：" + e);
		}

	}

	private void sendMail(String subject, String content, String to, String attachFile, String cc, String inline) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "gbk");
			messageHelper.setFrom(simpleMailMessage.getFrom()); // 设置发件人Email
			messageHelper.setSubject(subject); // 设置邮件主题
			messageHelper.setText(content, true); // 设置邮件主题内容
			if (to != null && to.indexOf(";") > -1) {
				messageHelper.setTo(to.split(";")); // 多个收件人Email
			} else if (to != null && !"".equals(to) && to.indexOf(";") < 0) {
				messageHelper.setTo(to); // 单个收件人Email
			}
			if (cc != null && cc.indexOf(";") > -1) {
				messageHelper.setCc(cc.split(";")); // 多个抄送人
			} else if (cc != null && !"".equals(cc) && cc.indexOf(";") < 0) {
				messageHelper.setCc(cc); // 一个抄送人
			}
			if (inline != null && !"".equals(inline)) {
				File file = new File(inline);
				messageHelper.addInline("ATT00001", new FileSystemResource(file)); // 添加附件
			}
			if (attachFile != null && !"".equals(attachFile)) {
				File file = new File(attachFile);
				messageHelper.addAttachment(file.getName(), file); // 添加附件
			}

			javaMailSender.send(mimeMessage); // 发送附件邮件
			log.info("send email is finished");
		} catch (Exception e) {
			if (log.isDebugEnabled())
				e.printStackTrace();
			log.error("异常信息：" + e);
		}

	}

	/**
	 * 发邮件带附件
	 * 将内容以附件形式发送不用生成附件文件
	 * @param subject 主题
	 * @param content 内容
	 * @param to 收件人，用;分隔可多人
	 * @param attName 附件名
	 * @author yangxiaoyong
	 * @version 创建时间：2014年7月10日 上午9:15:29 参考 www.sql8.net
	 */
	public void sendMail(String subject, String content, String to, String attName) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "gbk");
			messageHelper.setFrom(simpleMailMessage.getFrom()); // 设置发件人Email
			messageHelper.setSubject(subject); // 设置邮件主题
			messageHelper.setText(content, true); // 设置邮件主题内容
			if (to != null && to.indexOf(";") > -1) {
				messageHelper.setTo(to.split(";")); // 多个收件人Email
			} else if (to != null && !"".equals(to) && to.indexOf(";") < 0) {
				messageHelper.setTo(to); // 单个收件人Email
			}
			if (!StringUtils.isBlank(attName)) {
				//将内容已附件形式发送
				Multipart mm = new MimeMultipart();
				BodyPart mdp = new MimeBodyPart();
				DataHandler dh = new DataHandler(content.toString(), "text/plain;charset=gbk");
				mdp.setFileName(MimeUtility.encodeWord(attName));
				mdp.setDataHandler(dh);
				mm.addBodyPart(mdp);
				mimeMessage.setContent(mm);
			}
			javaMailSender.send(mimeMessage); // 发送附件邮件
			log.info("send email is finished");
		} catch (Exception e) {
			if (log.isDebugEnabled())
				e.printStackTrace();
			log.error("异常信息：" + e);
		}

	}
	/**
	 * 发邮件带附件
	 * 将内容以附件形式发送不用生成附件文件
	 * @param subject 主题
	 * @param content 内容
	 * @param to 收件人，用;分隔可多人
	 * @param attName 附件名
	 * @author yangxiaoyong
	 * @version 创建时间：2014年7月10日 上午9:15:29 参考 www.sql8.net
	 */
	public void sendMail(String subject, String content, String to,File file) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
			messageHelper.setFrom(simpleMailMessage.getFrom()); // 设置发件人Email
			messageHelper.setSubject(subject); // 设置邮件主题
			messageHelper.setText(content, true); // 设置邮件主题内容
			if (to != null && to.indexOf(";") > -1) {
				messageHelper.setTo(to.split(";")); // 多个收件人Email
			} else if (to != null && !"".equals(to) && to.indexOf(";") < 0) {
				messageHelper.setTo(to); // 单个收件人Email
			}
			if (file!=null&&file.isFile()) {
				messageHelper.addAttachment(MimeUtility.encodeText(file.getName()), file); // 添加附件
			}
			javaMailSender.send(mimeMessage); // 发送附件邮件
			log.info("send email is finished");
		} catch (Exception e) {
			if (log.isDebugEnabled())
				e.printStackTrace();
			log.error("异常信息：" + e);
		}

	}

	public void sendEmailThread(final String subject, final String content, final String to, final String attName) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				sendMail(subject, content, to, attName);
			}
		});
		t.start();
	}

}
