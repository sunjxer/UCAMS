/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsConstructDes;
import com.ucams.modules.ams.entity.AmsLandDes;

/**
 * 建设工程规划著录DAO接口
 * @author ws
 * @version 2017-07-28
 */
@MyBatisDao
public interface AmsConstructDesDao extends CrudDao<AmsConstructDes> {
	
	public List<AmsConstructDes> findConstructListByProjectId(@Param("list") List list);
	
	public List<AmsConstructDes> accFindList(AmsConstructDes entity);
	
	public int updateAcc(AmsConstructDes entity);
	
}