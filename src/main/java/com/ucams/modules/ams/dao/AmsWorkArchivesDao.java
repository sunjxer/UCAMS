/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsGuidance;
import com.ucams.modules.ams.entity.AmsWorkArchives;

/**
 * 业务管理档案案卷DAO接口
 * @author dyk
 * @version 2017-12-13
 */
@MyBatisDao
public interface AmsWorkArchivesDao extends CrudDao<AmsWorkArchives> {
	
	//查询案卷和文件();
	public List<AmsWorkArchives> findAllArchivesFilesList(AmsWorkArchives amsWorkArchives);
	
	//安唯一条件查数据
	public AmsWorkArchives getByParam(AmsWorkArchives amsWorkArchives);
}