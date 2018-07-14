package com.sdut.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 发送邮件的工具类
 * @author Administrator
 *
 */
public class MailUtils {
	/**
	 * 发送邮件
	 * @param toMail 收件人
	 * @param code   邮件内容
	 * @throws MessagingException 
	 * @throws Exception 
	 */
	public static void sendMail(String toMail, String code) throws Exception{
		
		//设置邮件服务器
		Properties props = new Properties();
		
		
		//与邮件服务器的链接
		Session session = Session.getInstance(props,new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("test@book.com", "123");
			}
			
		});
		
		//创建邮件
		Message message = new MimeMessage(session);
		//设置发件人
		message.setFrom(new InternetAddress("test@book.com"));
		//设置抄送
		message.setRecipient(RecipientType.TO, new InternetAddress(toMail));
		//设置主题
		message.setSubject("来自图书商城的邮件");
		//设置邮件的内容
		String msg = "点击<a href='http://127.0.0.1:8080/estore/activation?code="+code+"'>此处激活账户</a>";
		message.setContent(msg, "text/html;charset=utf-8");
		
		//发送邮件
		Transport.send(message);
		
	}
}
