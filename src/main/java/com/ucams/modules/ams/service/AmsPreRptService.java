/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.modules.ams.entity.AmsPreRpt;
import com.ucams.modules.ams.dao.AmsPreRptDao;

/**
 * 检查报告Service
 * @author sunjx
 * @version 2017-08-04
 */
@Service
@Transactional(readOnly = true)
public class AmsPreRptService extends CrudService<AmsPreRptDao, AmsPreRpt> {

	public AmsPreRpt get(String id) {
		return super.get(id);
	}
	
	public List<AmsPreRpt> findList(AmsPreRpt amsPreRpt) {
		return super.findList(amsPreRpt);
	}
	
	public Page<AmsPreRpt> findPage(Page<AmsPreRpt> page, AmsPreRpt amsPreRpt) {
		return super.findPage(page, amsPreRpt);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsPreRpt amsPreRpt) {
		super.save(amsPreRpt);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsPreRpt amsPreRpt) {
		super.delete(amsPreRpt);
	}
	
}