/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.Map;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsDesProgram;

/**
 * 工程专业配置记录DAO接口
 * @author sunjx
 * @version 2017-06-21
 */
@MyBatisDao
public interface AmsDesProgramDao extends CrudDao<AmsDesProgram> {
	
	/**
	 * 启用/禁用 
	 * @param amsDesProjram
	 */
	public void updateUseAble(Map<String, Object> paramMap);
}