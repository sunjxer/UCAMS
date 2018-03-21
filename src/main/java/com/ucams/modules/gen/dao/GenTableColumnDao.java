/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.gen.dao;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 * @author zhuye
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {
	
	public void deleteByGenTableId(String genTableId);
}
