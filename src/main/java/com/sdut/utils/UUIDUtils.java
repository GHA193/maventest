package com.sdut.utils;

import java.util.UUID;

/**
 * 生成激活码的工具类
 * @author Administrator
 *
 */
public class UUIDUtils {
	
	public static String getUUID() {
		//将32位的id中的-去掉
		return UUID.randomUUID().toString().replace("-", "");
	}

}
