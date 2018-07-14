package com.sdut.test;

import com.sdut.utils.MailUtils;

public class Test {

	public static void main(String[] args) throws Exception {
		String code = "点击<a herf='#'>此处</a>激活账户";
		MailUtils.sendMail("ok@book1.com", code);
		
		System.out.println("已发送");
	}

}
