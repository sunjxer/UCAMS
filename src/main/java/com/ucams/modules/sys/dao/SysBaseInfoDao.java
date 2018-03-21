/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.sys.dao;

import java.util.List;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.sys.entity.SysBaseInfo;

/**
 * 系统规则管理DAO接口
 * @author gyl
 * @version 2017-06-26
 */
@MyBatisDao
public interface SysBaseInfoDao extends CrudDao<SysBaseInfo> {
	
	public List<SysBaseInfo> findSysBaseInfo();
	
	
	
	
}