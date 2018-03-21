/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.service.TreeService;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.entity.AmsUserDict;
import com.ucams.modules.ams.dao.AmsUserDictDao;

/**
 * 用户数据字典Service
 * @author sunjx
 * @version 2017-06-16
 */
@Service
@Transactional(readOnly = true)
public class AmsUserDictService extends TreeService<AmsUserDictDao, AmsUserDict> {

	public AmsUserDict get(String id) {
		return super.get(id);
	}
	
	public List<AmsUserDict> findList(AmsUserDict amsUserDict) {
		if (StringUtils.isNotBlank(amsUserDict.getParentIds())){
			amsUserDict.setParentIds(amsUserDict.getParentIds().replace(" ", ""));
		}
		return super.findList(amsUserDict);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsUserDict amsUserDict) {
		super.save(amsUserDict);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsUserDict amsUserDict) {
		super.delete(amsUserDict);
	}
	
}