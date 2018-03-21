/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.service.CrudService;
import com.ucams.common.utils.CacheUtils;
import com.ucams.common.utils.EntityUtils;
import com.ucams.modules.ams.dao.AmsDictDao;
import com.ucams.modules.ams.entity.AmsDict;
import com.ucams.modules.ams.utils.AmsDictUtils;

/**
 * 业务字典Service
 * @author zhuye
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AmsDictService extends CrudService<AmsDictDao, AmsDict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new AmsDict());
	}

	@Transactional(readOnly = false)
	public void save(AmsDict dict) {
		//添加数据为父类型
		if("1".equals(dict.getParentFlag() )){
			dict.setParentId("0");
		}else{
			//如果不是父类型，并且添加了分类，则给父ID赋值
			if(EntityUtils.isNotEmpty(dict.getAmsDict())){
				dict.setParentId(dict.getAmsDict().getId());
			}
		}
		super.save(dict);
		CacheUtils.remove(AmsDictUtils.CACHE_AMS_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(AmsDict dict) {
		super.delete(dict);
		CacheUtils.remove(AmsDictUtils.CACHE_AMS_DICT_MAP);
	}

}
