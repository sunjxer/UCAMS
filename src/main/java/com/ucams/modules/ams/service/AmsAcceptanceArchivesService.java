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
import com.ucams.modules.ams.entity.AmsAcceptanceArchives;
import com.ucams.modules.ams.dao.AmsAcceptanceArchivesDao;

/**
 * 预验收案卷Service
 * @author zkx
 * @version 2017-07-18
 */
@Service
@Transactional(readOnly = true)
public class AmsAcceptanceArchivesService extends CrudService<AmsAcceptanceArchivesDao, AmsAcceptanceArchives> {

	@Autowired
	private AmsAcceptanceArchivesDao amsAcceptanceArchivesDao;
	
	public AmsAcceptanceArchives get(String id) {
		return super.get(id);
	}
	
	public List<AmsAcceptanceArchives> findList(AmsAcceptanceArchives amsAcceptanceArchives) {
		return super.findList(amsAcceptanceArchives);
	}
	
	public Page<AmsAcceptanceArchives> findPage(Page<AmsAcceptanceArchives> page, AmsAcceptanceArchives amsAcceptanceArchives) {
		return super.findPage(page, amsAcceptanceArchives);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsAcceptanceArchives amsAcceptanceArchives) {
		super.save(amsAcceptanceArchives);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsAcceptanceArchives amsAcceptanceArchives) {
		super.delete(amsAcceptanceArchives);
	}
	
	//根据ams_archives_info的案卷主键Id查询是否已验收  若存在，用于删除组卷
	@Transactional
	public String findAmsAcceptanceArchivesId(AmsAcceptanceArchives amsAcceptanceArchives){
		
		return amsAcceptanceArchivesDao.queryAmsAcceptanceArchivesColumnsById(amsAcceptanceArchives);
	}
	
}