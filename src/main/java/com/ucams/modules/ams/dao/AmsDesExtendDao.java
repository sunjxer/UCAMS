/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsDesExtend;
import com.ucams.modules.ams.entity.AmsDesProgram;

/**
 * 工程专业配置记录DAO接口
 * @author sunjx
 * @version 2017-06-21
 */
@MyBatisDao
public interface AmsDesExtendDao extends CrudDao<AmsDesExtend> {
	
	/**
	 * 通过单位工程类型，方案类型获取配置
	 * @param amsDesProgram
	 * @return
	 */
	public List<AmsDesExtend> findByUpType(AmsDesExtend amsDesExtend);
	/**
	 * 文件查询扩展信息列表
	 * @param amsUnitProInfo
	 * @return
	 * @author zkx
	 */
	public List<AmsDesExtend> findExtendDataList(AmsDesExtend amsDesExtend);
	
}