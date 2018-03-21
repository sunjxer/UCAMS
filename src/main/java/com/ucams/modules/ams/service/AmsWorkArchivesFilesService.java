/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.modules.ams.entity.AmsWorkArchivesFiles;
import com.ucams.modules.ams.dao.AmsWorkArchivesFilesDao;

/**
 * 业务管理档案案卷文件Service
 * @author dyk
 * @version 2017-12-13
 */
@Service
@Transactional(readOnly = true)
public class AmsWorkArchivesFilesService extends CrudService<AmsWorkArchivesFilesDao, AmsWorkArchivesFiles> {

	@Autowired
	private AmsWorkArchivesFilesDao amsWorkArchivesFilesDao;
	
	public AmsWorkArchivesFiles get(String id) {
		return super.get(id);
	}
	
	public List<AmsWorkArchivesFiles> findList(AmsWorkArchivesFiles amsWorkArchivesFiles) {
		return super.findList(amsWorkArchivesFiles);
	}
	
	public Page<AmsWorkArchivesFiles> findPage(Page<AmsWorkArchivesFiles> page, AmsWorkArchivesFiles amsWorkArchivesFiles) {
		amsWorkArchivesFiles.setPage(page);
		page.setList(dao.findList(amsWorkArchivesFiles));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(AmsWorkArchivesFiles amsWorkArchivesFiles) {
		super.save(amsWorkArchivesFiles);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsWorkArchivesFiles amsWorkArchivesFiles) {
		super.delete(amsWorkArchivesFiles);
	}
	
	@Transactional(readOnly = false)
	public AmsWorkArchivesFiles getByParam(AmsWorkArchivesFiles amsWorkArchivesFiles) {
		return amsWorkArchivesFilesDao.getByParam(amsWorkArchivesFiles);
	}
	
}