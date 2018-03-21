/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.modules.ams.entity.AmsTransferRpt;
import com.ucams.modules.ams.dao.AmsTransferRptDao;

/**
 * 移交检查报告Service
 * @author zkx
 * @version 2017-08-08
 */
@Service
@Transactional(readOnly = true)
public class AmsTransferRptService extends CrudService<AmsTransferRptDao, AmsTransferRpt> {

	public AmsTransferRpt get(String id) {
		return super.get(id);
	}
	
	public List<AmsTransferRpt> findList(AmsTransferRpt amsTransferRpt) {
		return super.findList(amsTransferRpt);
	}
	
	public Page<AmsTransferRpt> findPage(Page<AmsTransferRpt> page, AmsTransferRpt amsTransferRpt) {
		return super.findPage(page, amsTransferRpt);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsTransferRpt amsTransferRpt) {
		super.save(amsTransferRpt);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsTransferRpt amsTransferRpt) {
		super.delete(amsTransferRpt);
	}
	
}