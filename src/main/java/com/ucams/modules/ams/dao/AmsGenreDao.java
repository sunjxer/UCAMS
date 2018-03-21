/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;

import com.ucams.common.persistence.TreeDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsGenre;

/**
 * 归档一览表类别管理DAO接口
 * @author sunjx
 * @version 2017-06-12
 */
@MyBatisDao
public interface AmsGenreDao extends TreeDao<AmsGenre> {
	/**
	 * 文件查询归档一揽表 zkx
	 * @param amsGenre
	 * @return
	 */
	public List<AmsGenre> fileFindList(AmsGenre amsGenre);
	/**
	 * 查询责任主体归档文件列表
	 * @param createUnit
	 * @return
	 */
	public List<AmsGenre> findRoleGenreList(String createUnit);
	/**
	 * 组卷查询归档一览表  gyl
	 * @param amsGenre
	 * @return
	 */
	public List<AmsGenre> findAmsGenreList(AmsGenre amsGenre);
	
	/**
	 * 根据编号查询
	 * @param code
	 * @return
	 */
	public List<AmsGenre> findByCode(String code);
	/**
	 * 根据项目类型查询归档一览表设置上传文件
	 * @param proType
	 * @return
	 */
	public List<AmsGenre> findGenreFileList(String proType);
	
	
}