/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;
import java.util.Map;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsFileInfo;

/**
 * 文件管理DAO接口
 * @author zkx
 * @version 2017-06-23
 */
@MyBatisDao
public interface AmsFileInfoDao extends CrudDao<AmsFileInfo> {
	/**
	 * 查询声像档案数据列表
	 * @param entity
	 * @return
	 */
	public List<AmsFileInfo> findVideoList(AmsFileInfo amsFileInfo);
	/**
	 * 组卷下文件（包含声像及资料，检查时使用）
	 * @param archivesId 组卷ID 
	 * @return
	 */
	public List<AmsFileInfo> findCheckList(String archivesId);
	/**
	 * 声像档案
	 * @return
	 */
	public AmsFileInfo getVideo(String id);
	/**
	 * 查询文件
	 * @return
	 */
	public AmsFileInfo get(AmsFileInfo amsFileInfo);
	
	public AmsFileInfo getFile(AmsFileInfo amsFileInfo);
	/**
	 * 分页查询案卷下所有文件
	 * @param params
	 * @return
	 */
	public List<AmsFileInfo> findArchivesFileList(AmsFileInfo amsFileInfo);
	
	/**
	 * 查询单位工程下是否添加文件，作为删除单位工程的依据
	 */
	public AmsFileInfo getAmsFileInfoId(String id);
}