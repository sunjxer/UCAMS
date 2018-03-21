/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsTransferRpt;

/**
 * 移交检查报告DAO接口
 * @author zkx
 * @version 2017-08-08
 */
@MyBatisDao
public interface AmsTransferRptDao extends CrudDao<AmsTransferRpt> {
	
}