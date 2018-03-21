/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.test.dao;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.test.entity.Test;

/**
 * 测试DAO接口
 * @author zhuye
 * @version 2013-10-17
 */
@MyBatisDao
public interface TestDao extends CrudDao<Test> {
	
}
