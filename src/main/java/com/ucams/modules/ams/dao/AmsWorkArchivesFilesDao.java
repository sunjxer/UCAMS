/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsWorkArchives;
import com.ucams.modules.ams.entity.AmsWorkArchivesFiles;

/**
 * 业务管理档案案卷文件DAO接口
 * @author dyk
 * @version 2017-12-13
 */
@MyBatisDao
public interface AmsWorkArchivesFilesDao extends CrudDao<AmsWorkArchivesFiles> {
	
	//安唯一条件查数据
	public AmsWorkArchivesFiles getByParam(AmsWorkArchivesFiles amsWorkArchivesFiles);
	
}