/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.sys.dao;

import com.ucams.common.persistence.TreeDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author zhuye
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
