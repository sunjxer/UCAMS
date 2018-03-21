/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.ucams.common.persistence.TreeDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author zhuye
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	
	/**
	 * 只查询项目/单位工程
	 */
	public List<Office> onlyUnitPro(Office office);
	
	
	public int updateName(Office entity);


	public void insertSysRoleOffice(Map map);
}
