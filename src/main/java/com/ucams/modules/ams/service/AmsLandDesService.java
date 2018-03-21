/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.modules.ams.entity.AmsLandDes;
import com.ucams.modules.ams.dao.AmsLandDesDao;

/**
 * 建设用地规划著录Service
 * @author ws
 * @version 2017-07-28
 */
@Service
@Transactional(readOnly = true)
public class AmsLandDesService extends CrudService<AmsLandDesDao, AmsLandDes> {

	public AmsLandDes get(String id) {
		return super.get(id);
	}
	
	public List<AmsLandDes> findList(AmsLandDes amsLandDes) {
		return super.findList(amsLandDes);
	}
	
	public Page<AmsLandDes> findPage(Page<AmsLandDes> page, AmsLandDes amsLandDes) {
		return super.findPage(page, amsLandDes);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsLandDes amsLandDes) {
		super.save(amsLandDes);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsLandDes amsLandDes) {
		super.delete(amsLandDes);
	}
	
}