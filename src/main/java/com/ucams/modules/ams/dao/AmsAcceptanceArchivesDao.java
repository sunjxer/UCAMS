/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsAcceptanceArchives;

/**
 * 预验收案卷DAO接口
 * @author zkx
 * @version 2017-07-18
 */
@MyBatisDao
public interface AmsAcceptanceArchivesDao extends CrudDao<AmsAcceptanceArchives> {
	
	// 根据ams_archives_info的Id查询是否已验收  若存在，用于删除组卷
	public String queryAmsAcceptanceArchivesColumnsById(AmsAcceptanceArchives amsAcceptanceArchives);

}