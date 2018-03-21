/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsAcceptance;
import com.ucams.modules.ams.entity.AmsLandDes;

/**
 * 建设用地规划著录DAO接口
 * @author ws
 * @version 2017-07-28
 */
@MyBatisDao
public interface AmsLandDesDao extends CrudDao<AmsLandDes> {
	
	public List<AmsLandDes> findLandListByProjectId(@Param("list") List list);
	
	public List<AmsLandDes> accFindList(AmsLandDes entity);
	public int updateAcc(AmsLandDes entity);
	
}