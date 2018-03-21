/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsPreRpt;

/**
 * 检查报告DAO接口
 * @author sunjx
 * @version 2017-08-04
 */
@MyBatisDao
public interface AmsPreRptDao extends CrudDao<AmsPreRpt> {
	
}