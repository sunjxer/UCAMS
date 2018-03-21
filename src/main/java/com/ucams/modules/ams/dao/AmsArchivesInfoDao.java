/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsArchivesFiles;
import com.ucams.modules.ams.entity.AmsArchivesInfo;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.ams.entity.AmsUnitProInfo;

/**
 * 组卷设置DAO接口
 * @author gyl
 * @version 2017-07-10
 */
@MyBatisDao
public interface AmsArchivesInfoDao extends CrudDao<AmsArchivesInfo> {
	
	/**
	/**
	 * 查询项目工程列表
	 * @param role
	 * @return
	 */
	public List<AmsProjectInfo> findProjectList(AmsProjectInfo amsProjectInfo);
	
	/**
	 * 查询单位工程信息列表
	 * @param amsUnitProInfo
	 * @return
	 */
	public List<AmsUnitProInfo> findUnitProjectList(AmsUnitProInfo amsUnitProInfo);
	/**
	 * 查询案卷列表
	 * @param List
	 * @return
	 */
	public List<AmsArchivesInfo> findArchListByProId(@Param("list") List list); 
	/**
	 * 预验收查询项目下案卷列表
	 * @param amsArchivesInfo
	 * @return
	 */
	public List<AmsArchivesInfo> findProArchivesList(AmsArchivesInfo amsArchivesInfo); 
	/**
	 * 移交查询项目下案卷列表
	 * @param amsArchivesInfo
	 * @return
	 */
	public List<AmsArchivesInfo> transferFindProArchivesPage(AmsArchivesInfo amsArchivesInfo); 
	/**
	 * 查询已组卷文件列表
	 * @param amsFileInfo
	 * @return
	 */
	public List<AmsFileInfo> findAmsFileInfoInList(AmsFileInfo amsFileInfo);
	
	public List<String> findGenreIdById(AmsArchivesFiles amsArchivesFiles);
	/**
	 * 查询未组卷文件列表
	 * @param amsFileInfo
	 * @return
	 */
	public List<AmsFileInfo> findAmsFileInfoAllList(AmsFileInfo amsFileInfo);
	/**
	 * 删除案卷文件
	 * @param amsArchivesFiles
	 * @return
	 */
	public int deleteFile(AmsArchivesFiles amsArchivesFiles);
	/**
	 * 修改文件排序和起始页
	 * @param list
	 * @return
	 */
	public int updateAmsArchivesFiles(AmsArchivesFiles amsArchivesFiles);
	/**
	 * 批量修改文件排序和起始页
	 * @param amsArchivesFiles
	 * @return
	 */
	public int updateSorts(AmsArchivesFiles amsArchivesFiles);
	/**
	 * 添加案卷文件
	 * @param amsArchivesFiles
	 * @return
	 */
	public int addFile(AmsArchivesFiles amsArchivesFiles);
	/**
	 * 根据案卷id获取文件数量
	 * @param amsArchivesFiles
	 * @return
	 */
	public int findCountByGroupId(AmsArchivesFiles amsArchivesFiles);
	/**
	 * 获取已组卷文件列表
	 * @param amsArchivesFiles
	 * @return
	 */
	public List<AmsArchivesFiles> findArchivesFilesList(AmsArchivesFiles amsArchivesFiles);
	
	public List<AmsArchivesFiles> findArchivesFilesListByGerne(AmsArchivesFiles amsArchivesFiles);
	
	public List<AmsArchivesFiles> findArchivesFilesListBySort(AmsArchivesFiles amsArchivesFiles);
	
	/**
	 * 获取单个案卷文件关系
	 * @param amsArchivesFiles
	 * @return
	 */
	public AmsArchivesFiles getAmsArchivesFiles(AmsArchivesFiles amsArchivesFiles);
	/**
	 * 修改组卷文件顺序
	 * @param paramMap
	 */
	public void updateSort(Map<String, Object> paramMap);
	
	//根据项目工程查询组卷是否添加
	
	public String getAmsArchivesInfoId(String id);
	/**
	 * 获取单位工程预验收提交、通过的次数
	 * @param id 单位工程id
	 * @return
	 */
	public int findUnitProjectAcceptanceCount(String id);
	/**
	 * 获取案卷预验收提交通过的次数
	 * @param archId 案卷id
	 * @return
	 */
	public int findArchivesAcceptanceCount(String archId);
	/**
	 * 删除案卷文件关系
	 * @param groupId  案卷id
	 */
	public void deleteArchivesFiles(String groupId);
	
	/**
	 * 获取项目案卷ID
	 * @param id
	 * @return
	 */
	public List<String> findArchivesInfoIdByProject(String projectId);
	
	public List<AmsArchivesInfo> findAmsArchivesListByFileIdArchId(String acceptId,String fileId);
	public List<AmsArchivesInfo> findAmsArchivesListByFileIdUnitProId(String acceptId,String fileId);
}