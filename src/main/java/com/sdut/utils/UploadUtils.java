package com.sdut.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtils {
	
	public static String upload(MultipartFile file) throws Exception {
		//访问路径
		String fwPath = "http://localhost:8080/estore/pic/";
		String filename = "";
		//定义上传位置,不可能是项目根路径
		String path = "e:/wk/upload/";
		File filePath = new File(path);
		//判断上传完位置是否存在
		if(!filePath.exists()) {
			//只能创建当前目录，不能创建父目录
//			filePath.mkdir();
			//创建目录以及父目录
			filePath.mkdirs();
		}
		
		//获取上传文件的名字
		filename = file.getOriginalFilename();
		
		//获取上传文件的后缀名
		filename = filename.substring(filename.lastIndexOf('.'));
		
		//上传文件设置新的名称
		filename = UUIDUtils.getUUID() + filename;
		
		path += filename;
		//将文件上传（复制）到指定位置
		file.transferTo(new File(path));
		
		
		return fwPath + filename;
	}
}
