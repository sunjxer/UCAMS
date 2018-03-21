/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.gen.dao;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.gen.entity.GenTable;

/**
 * 业务表DAO接口
 * @author zhuye
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableDao extends CrudDao<GenTable> {
	
}
