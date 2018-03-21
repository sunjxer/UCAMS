package com.ucams.modules.ams.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.ucams.common.utils.EntityUtils;

/**
 * @author 作者: sunjx
 * @version 创建时间：2017年7月12日 文件校验工具
 */
public class CheckFileUtils {

	// 图片像素规则 宽 * 高
	public static final String IMAGE_PIXEL = "500*600";
	// dpi > 600    197*210

	/**
	 * 图片校验
	 * @param file 文件流
	 * @return 校验报告
	 */
	public static String checkImage(InputStream is) {
		String checkReport = null;
		String ret = getImageInfo(is);
		if (! ret.equals(IMAGE_PIXEL)) {
			checkReport = "像素不合规范: 图片像素应为" + IMAGE_PIXEL;
		} 
		return checkReport;
	}

	/**
	 * pdf页数校验
	 * @param file 文件流 
	 * @return pdf实际页数
	 */
	public static String checkPdf(InputStream file, String page) {
		String checkReport = null;
		Map<String, Object> pdfMes = PDFReader.getPDFInformation(file);
		if (! page.equals(pdfMes.get("pages"))) {
			checkReport =  "页数与填写数据不符: 文件实际页数为" + pdfMes.get("pages");
		}
		return checkReport;
	}

	/**
	 * 检查实体内属性是否为空
	 * @param 需要检查的实体类 Object 任意实体
	 * @return  属性为空的字段别名
	 * @throws IllegalAccessException
	 */
	public static List<String> checkObjFieldIsNull(Object obj)
			throws IllegalAccessException {
		List<String> list = new ArrayList<String>();
		//getDeclaredFields()只能获取自己声明的各种字段，包括public，protected，private。
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			FieldMeta meta = f.getAnnotation(FieldMeta.class);
			if(meta == null){ continue; }
			//如果属性为String并且为空值
			if (f.getGenericType().toString().equals("class java.lang.String")){
				if((String)f.get(obj) == null || "".equals((String)f.get(obj))){
					SortableField sf = new SortableField(meta, f);
					list.add(sf.getMeta().name() + "(空数据)");
				}
			//实体属性不为空，注解不为空
			}else if(EntityUtils.isEmpty(f.get(obj))){
				SortableField sf = new SortableField(meta, f);
				list.add(sf.getMeta().name() + "(空数据)");
			}
		}
		return list;
	}
	
	/** 
     * 获取map中value为空的key
     * @param map 
     * @return 
     */   
    public static List<String> getNullValueKeys(Map<?, ?> map){   
        Set<?> set = map.keySet();   
        List<String> keys = new ArrayList<String>();
        try {
        	 for (Iterator<?> iterator = set.iterator(); iterator.hasNext();) {   
                 Object key = (Object) iterator.next();   
                 String value =String.valueOf(map.get(key));
                 if(StringUtils.isBlank(value)){
                	 keys.add(value);
                 }
             }   
		} catch (Exception e) {
			e.printStackTrace();
		}
        return keys;
    }
    
    /**
	* 检测网络资源是否存在　
	* 
	* @param strUrl
	* @return 流未关闭
	*/
	public static boolean isNetFileAvailable(String strUrl) {
		InputStream netFileInputStream = null;
		try {
			URL url = new URL(strUrl);
			URLConnection urlConn = url.openConnection();
			netFileInputStream = urlConn.getInputStream();
			if (null != netFileInputStream) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (netFileInputStream != null)
					netFileInputStream.close();
			} catch (IOException e) {
			}
		}
	}
	
	/**
	* 检测网络资源流转换
	* @param strUrl
	* @return 流未关闭
	*/
	public static InputStream interUrlInputStream(String strUrl) {
		InputStream netFileInputStream = null;
		try {
			URL url = new URL(strUrl);
			URLConnection urlConn = url.openConnection();
			netFileInputStream = urlConn.getInputStream();
			if (null != netFileInputStream) {
				return netFileInputStream;
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		} 
	}
	
	public static String getImageInfo(InputStream is){
		 BufferedImage src = null;
	        String ret = null;
	        try {
	            src = javax.imageio.ImageIO.read(is);
	            Integer width = src.getWidth(); // 得到源图宽
	            Integer height = src.getHeight(); //得到源图高
	            ret = String.valueOf(width) + "*"+  String.valueOf(height);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return ret;
	}
	
	public static void main(String[] args) {
		
	boolean a =	isNetFileAvailable("http://192.168.0.215:9090/online/projectFile/5f36aadf2602491999938a784ad966e7/1059f05752c840bfb9925a0c4b9b7da7/20170925152511_Ge81P0.pdf");
	System.out.println(a);
	}
}
