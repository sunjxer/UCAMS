/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;



import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;

/**
 * 责任主体详细信息管理DAO接口
 * @author ws
 * @version 2017-07-04
 */
@MyBatisDao
public interface AmsUnitDetailinfoDao extends CrudDao<AmsUnitDetailinfo> {

	/**
	 * 根据责任主体Id获取责任主体明细信息
	 * @param id
	 * @return
	 */
	public AmsUnitDetailinfo  getByUnitId(AmsUnitDetailinfo amsUnitDetailinfo);

    public Integer getAmsUnitDetailinfByoUnitCreditCode(AmsUnitDetailinfo r);

	

	
	
}