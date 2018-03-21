/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;
import com.ucams.common.persistence.TreeDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsUcArchiveMenu;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;

/**
 * 城建档案类别管理Dao
 * 2017年12月1日 下午1:31:43
 * @author dpj
 */
@MyBatisDao
public interface AmsUcArchiveMenuDao extends TreeDao<AmsUcArchiveMenu> {

	public AmsUcArchiveMenu  findByCode(AmsUcArchiveMenu amsUcArchiveMenu);
}