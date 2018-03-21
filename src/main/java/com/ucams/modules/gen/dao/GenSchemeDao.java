/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.gen.dao;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.gen.entity.GenScheme;

/**
 * 生成方案DAO接口
 * @author zhuye
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenSchemeDao extends CrudDao<GenScheme> {
	
}
