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
import com.ucams.modules.ams.entity.AmsWorkArchives;
import com.ucams.modules.ams.dao.AmsWorkArchivesDao;

/**
 * 业务管理档案案卷Service
 * @author dyk
 * @version 2017-12-13
 */
@Service
@Transactional(readOnly = true)
public class AmsWorkArchivesService extends CrudService<AmsWorkArchivesDao, AmsWorkArchives> {

	@Autowired
	private AmsWorkArchivesDao amsWorkArchivesDao;
	public AmsWorkArchives get(String id) {
		return super.get(id);
	}
	
	public List<AmsWorkArchives> findList(AmsWorkArchives amsWorkArchives) {
		return super.findList(amsWorkArchives);
	}
	
	public List<AmsWorkArchives> findAllList(AmsWorkArchives amsWorkArchives) {
		return super.findList(amsWorkArchives);
	}
	
	public Page<AmsWorkArchives> findPage(Page<AmsWorkArchives> page, AmsWorkArchives amsWorkArchives) {
		return super.findPage(page, amsWorkArchives);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsWorkArchives amsWorkArchives) {
		super.save(amsWorkArchives);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsWorkArchives amsWorkArchives) {
		super.delete(amsWorkArchives);
	}
	
	@Transactional(readOnly = false)
	public List<AmsWorkArchives> findAllArchivesFilesList(AmsWorkArchives amsWorkArchives) {
		return amsWorkArchivesDao.findAllArchivesFilesList(amsWorkArchives);
	}
	
	@Transactional(readOnly = false)
	public AmsWorkArchives getByParam(AmsWorkArchives amsWorkArchives) {
		return amsWorkArchivesDao.getByParam(amsWorkArchives);
	}
	
	
}