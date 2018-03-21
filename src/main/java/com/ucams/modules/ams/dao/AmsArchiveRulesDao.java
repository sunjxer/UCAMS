/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;
import java.util.Map;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsArchiveRules;

/**
 * 归档一览表规则表DAO接口
 * @author sunjx
 * @version 2017-06-12
 */
@MyBatisDao
public interface AmsArchiveRulesDao extends CrudDao<AmsArchiveRules> {
	
	/**
	 * 交换更新排序sort
	 * @param paramMap
	 */
	public void updateSort(Map<String, Object> paramMap);
	
}