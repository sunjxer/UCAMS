/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import com.ucams.common.persistence.TreeDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsUserDict;

/**
 * 用户数据字典DAO接口
 * @author sunjx
 * @version 2017-06-16
 */
@MyBatisDao
public interface AmsUserDictDao extends TreeDao<AmsUserDict> {
	
}