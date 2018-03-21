/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.Page;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsGuidance;

/**
 * 业务指导内容管理DAO接口
 * @author gyl
 * @version 2017-06-26
 */
@MyBatisDao
public interface AmsGuidanceDao extends CrudDao<AmsGuidance> {
	
	public int updateOpinion(AmsGuidance amsGuidance);
	
	public int updateGuidance(AmsGuidance amsGuidance);
	
	public List<AmsGuidance> findAmsGuidanceList(AmsGuidance amsGuidance);
	
	/**
	 * 根据项目工程查询业务指导是否添加
	 */
	public String getAmsGuidanceId(String id); 
	
	
}