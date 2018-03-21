/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ucams.common.mapper.JsonMapper;
import com.ucams.common.utils.CacheUtils;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.SpringContextHolder;
import com.ucams.modules.ams.dao.AmsDictDao;
import com.ucams.modules.ams.entity.AmsDict;
import com.ucams.modules.sys.dao.DictDao;
import com.ucams.modules.sys.entity.Dict;

/**
 * 业务字典工具类
 * @author zhuye
 * @version 2013-5-29
 */
public class AmsDictUtils {
	
	private static AmsDictDao dictDao = SpringContextHolder.getBean(AmsDictDao.class);

	public static final String CACHE_AMS_DICT_MAP = "amsDictMap";
	public static final String CACHE_AMS_PARENT_DICT_MAP = "parentAmsDictMap";
	
	public static String getAmsDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (AmsDict dict : getAmsDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}
	
	public static String getAmsDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getAmsDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getAmsDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (AmsDict dict : getAmsDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	/**
	 * 查询父节点类型
	 * @return
	 */
	public static List<AmsDict> getParentAmsDictList(String typeFlag){
		
		AmsDict amsDict = new AmsDict();
		if(EntityUtils.isNotEmpty(typeFlag)){
			amsDict.setTypeFlag(typeFlag);
		}
		List<AmsDict> dictList = dictDao.findParentDictList(amsDict);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	public static List<AmsDict> getAmsDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<AmsDict>> dictMap = (Map<String, List<AmsDict>>)CacheUtils.get(CACHE_AMS_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (AmsDict dict : dictDao.findAllList(new AmsDict())){
				List<AmsDict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_AMS_DICT_MAP, dictMap);
		}
		List<AmsDict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JsonMapper.toJsonString(getAmsDictList(type));
	}
	
}
