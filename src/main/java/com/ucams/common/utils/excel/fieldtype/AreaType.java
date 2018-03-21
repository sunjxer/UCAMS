/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.common.utils.excel.fieldtype;

import com.ucams.common.utils.StringUtils;
import com.ucams.modules.sys.entity.Area;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 字段类型转换
 * @author zhuye
 * @version 2013-03-10
 */
public class AreaType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		for (Area e : UserUtils.getAreaList()){
			if (StringUtils.trimToEmpty(val).equals(e.getName())){
				return e;
			}
		}
		return null;
	}

	/**
	 * 获取对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Area)val).getName() != null){
			return ((Area)val).getName();
		}
		return "";
	}
}
