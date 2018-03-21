/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.ams.entity.AmsUnitProInfo;

/**
 * 单位工程管理DAO接口
 * @author ws
 * @version 2017-06-26
 */
@MyBatisDao
public interface AmsUnitProInfoDao extends CrudDao<AmsUnitProInfo> {
	public AmsUnitProInfo getUnitProjectByNo();
	
	/**
	 * 查询用户权限单位工程列表
	 * @param dsf
	 * @return
	 */
	public List<AmsUnitProInfo> findOfficeUnitProjectList(@Param(value = "dsf")String dsf);
	/**
	 * 查询单位工程信息列表
	 * @param amsUnitProInfo
	 * @return 
	 * @author zkx
	 */
	public List<AmsUnitProInfo> findUnitProjectList(AmsUnitProInfo amsUnitProInfo);
	
	public List<String> findUnitProjectByProjectId(String projectId);
	
	/**
	 * 在通过预验收后，在单位工程中插入预验收的id
	 * 2017年10月10日 下午2:37:45
	 * @author dpj
	 */
	public void modifyAmsUnitProInfo(AmsUnitProInfo amsUnitProInfo);
	
	/**
	 * 档案馆对外查询二级目录单位工程 项目id，项目名称，建设单位，项目地址
	 * 2017年11月9日 上午11:10:42
	 * @author dpj
	 */
	public List<AmsUnitProInfo> getForeignFindUnitProInfo(String id);
	
	/**
	 * 单位工程管理模块的项目工程列表
	 * 2017年11月24日 下午2:54:01
	 * @author Administrator
	 * @param
	 */
	public List<AmsProjectInfo> findProjectList(AmsProjectInfo amsProjectInfo);
}