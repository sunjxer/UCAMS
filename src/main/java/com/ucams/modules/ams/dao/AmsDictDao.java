/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsDict;

/**
 * 字典DAO接口
 * @author zhuye
 * @version 2014-05-16
 */
@MyBatisDao
public interface AmsDictDao extends CrudDao<AmsDict> {

	public List<String> findTypeList(AmsDict dict);
	
	public List<AmsDict> findParentDictList(AmsDict dict);
}
