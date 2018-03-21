/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.test.dao;

import com.ucams.common.persistence.TreeDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.test.entity.TestTree;

/**
 * 树结构生成DAO接口
 * @author zhuye
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}