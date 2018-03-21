/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.test.dao;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.test.entity.TestDataMain;

/**
 * 主子表生成DAO接口
 * @author zhuye
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestDataMainDao extends CrudDao<TestDataMain> {
	
}