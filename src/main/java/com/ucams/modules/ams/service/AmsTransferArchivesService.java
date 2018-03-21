/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.modules.ams.entity.AmsTransferArchives;
import com.ucams.modules.ams.dao.AmsTransferArchivesDao;

/**
 * 移交案卷Service
 * @author zkx
 * @version 2017-07-25
 */
@Service
@Transactional(readOnly = true)
public class AmsTransferArchivesService extends CrudService<AmsTransferArchivesDao, AmsTransferArchives> {

	public AmsTransferArchives get(String id) {
		return super.get(id);
	}
	
	public List<AmsTransferArchives> findList(AmsTransferArchives amsTransferArchives) {
		return super.findList(amsTransferArchives);
	}
	
	public Page<AmsTransferArchives> findPage(Page<AmsTransferArchives> page, AmsTransferArchives amsTransferArchives) {
		return super.findPage(page, amsTransferArchives);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsTransferArchives amsTransferArchives) {
		super.save(amsTransferArchives);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsTransferArchives amsTransferArchives) {
		super.delete(amsTransferArchives);
	}
	
}