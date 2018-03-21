/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.sys.dao;

import java.util.List;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 * @author zhuye
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	/**
	 * 按角色类型查询菜单(角色类型 = 菜单备注)
	 * @return list
	 */
	public List<String> findByRoleType(String roleType);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
}
