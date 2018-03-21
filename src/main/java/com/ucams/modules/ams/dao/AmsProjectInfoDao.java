/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.sys.entity.Office;

/**
 * 工程项目管理DAO接口
 * @author ws
 * @version 2017-06-26
 */
@MyBatisDao
public interface AmsProjectInfoDao extends CrudDao<AmsProjectInfo> {
	/**
	 * 根据工程名称获取工程
	 * @param projectName
	 * @return
	 */
	public AmsProjectInfo getByProjectName(AmsProjectInfo amsProjectInfo);
	
	public int updateProjectStatus(AmsProjectInfo amsProjectInfo);
	
	public int updateProjectBusinessMan(AmsProjectInfo amsProjectInfo);
	
/*	public AmsProjectInfo getProjectByNo();
*/	
	public AmsProjectInfo getProjectByNoNew();
	
	/**getProjectByNoNew
	 * 查询用户权限项目列表
	 * @param dsf
	 * @return
	 */
	public List<AmsProjectInfo> findOfficeProjectList(@Param(value = "dsf")String dsf);
	
	public List<AmsProjectInfo> findListByPlanningLicenseNumber(@Param(value = "planningLicenseNumber")String planningLicenseNumber);
	/**
	 * 文件管理查询项目工程列表
	 * @param role
	 * @return
	 *  @author zkx
	 */
	public List<AmsProjectInfo> findProjectList(AmsProjectInfo amsProjectInfo);

	//public String getAddress(String name);
	
	
}