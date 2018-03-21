package com.ucams.common.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年9月19日
 * 业务工具类
 */
public class UcamsTools {
	/**
	 * 生成新文件名 
	 * 规则: 年月日时分秒毫秒_6位随机数
	 * @param file 文件流
	 * @return 新文件名
	 */
	public static String createNewFileName(MultipartFile file){
		StringBuffer str = new StringBuffer();
		try {
			String fileName = file.getOriginalFilename();  //文件名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();//文件后缀
			str.append(DateUtils.getDate("yyyyMMddHHmmss"));
			str.append("_");
			str.append(RandomUtils.getStringRandom(6));
			str.append(".");
			str.append(fileExt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str.toString();
	}
	public static String createNewFileName(File file){
		StringBuffer str = new StringBuffer();
		try {
			String fileName = file.getName();  //文件名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();//文件后缀
			str.append(DateUtils.getDate("yyyyMMddHHmmss"));
			str.append("_");
			str.append(RandomUtils.getStringRandom(6));
			str.append(".");
			str.append(fileExt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str.toString();
	}
	
}
