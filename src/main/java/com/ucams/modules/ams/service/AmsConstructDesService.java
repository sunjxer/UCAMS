/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.modules.ams.entity.AmsConstructDes;
import com.ucams.modules.ams.dao.AmsConstructDesDao;

/**
 * 建设工程规划著录Service
 * @author ws
 * @version 2017-07-28
 */
@Service
@Transactional(readOnly = true)
public class AmsConstructDesService extends CrudService<AmsConstructDesDao, AmsConstructDes> {

	public AmsConstructDes get(String id) {
		return super.get(id);
	}
	
	public List<AmsConstructDes> findList(AmsConstructDes amsConstructDes) {
		return super.findList(amsConstructDes);
	}
	
	public Page<AmsConstructDes> findPage(Page<AmsConstructDes> page, AmsConstructDes amsConstructDes) {
		return super.findPage(page, amsConstructDes);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsConstructDes amsConstructDes) {
		super.save(amsConstructDes);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsConstructDes amsConstructDes) {
		super.delete(amsConstructDes);
	}
	
}