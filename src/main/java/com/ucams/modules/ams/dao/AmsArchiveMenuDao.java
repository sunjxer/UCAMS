/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;
import java.util.List;

import com.ucams.common.persistence.TreeDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsArchiveMenu;

/**
 * 声像档案类别Dao
 * @author dpj
 * 2017年11月30日14:56:08
 */
@MyBatisDao
public interface AmsArchiveMenuDao extends TreeDao<AmsArchiveMenu> {

	public List<AmsArchiveMenu> fileMenuList(AmsArchiveMenu amsArchiveMenu);
	public List<AmsArchiveMenu> accFindList(AmsArchiveMenu amsArchiveMenu);
	
}