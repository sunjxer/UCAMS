/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsAcceptance;
import com.ucams.modules.ams.entity.AmsAcceptanceArchives;

/**
 * 预验收管理DAO接口
 * @author zkx
 * @version 2017-07-11
 */
@MyBatisDao
public interface AmsAcceptanceDao extends CrudDao<AmsAcceptance> {
	
	public int updateAcceptance(AmsAcceptance amsAcceptance);
	public int updateDelFlag(AmsAcceptance amsAcceptance);
	public int insertAmsAcceptanceArchivesList(AmsAcceptance amsAcceptance);
	public int deleteAmsAcceptanceArchives(AmsAcceptance amsAcceptance);
	public List<AmsAcceptanceArchives> getList(AmsAcceptance amsAcceptance);
	public List<AmsAcceptanceArchives> getIdsList(AmsAcceptance amsAcceptance);
	//根据项目工程查询组卷是否添加
	//public String getAmsAcceptanceId(String id);
}