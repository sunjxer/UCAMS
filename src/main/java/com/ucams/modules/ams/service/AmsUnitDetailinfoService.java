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
import com.ucams.modules.ams.dao.AmsUnitDetailinfoDao;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;

/**
 * 责任主体详细信息管理Service
 * @author ws
 * @version 2017-07-04
 */
@Service
@Transactional(readOnly = true)
public class AmsUnitDetailinfoService extends CrudService<AmsUnitDetailinfoDao, AmsUnitDetailinfo> {
	@Autowired
	private AmsUnitDetailinfoDao amsUnitDetailinfoDao;
	
	public AmsUnitDetailinfo get(String id) {
		return super.get(id);
	}
	
	public List<AmsUnitDetailinfo> findList(AmsUnitDetailinfo amsUnitDetailinfo) {
		return super.findList(amsUnitDetailinfo);
	}
	
	/**
	 * 根据责任主体Id读取责任主体明细信息
	 * @param unitId
	 * @return
	 */
	public AmsUnitDetailinfo getByUnitId(AmsUnitDetailinfo amsUnitDetailinfo) {
		return amsUnitDetailinfoDao.getByUnitId(amsUnitDetailinfo);
	}
	
	public Page<AmsUnitDetailinfo> findPage(Page<AmsUnitDetailinfo> page, AmsUnitDetailinfo amsUnitDetailinfo) {
		return super.findPage(page, amsUnitDetailinfo);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsUnitDetailinfo amsUnitDetailinfo) {
		super.save(amsUnitDetailinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsUnitDetailinfo amsUnitDetailinfo) {
		super.delete(amsUnitDetailinfo);
	}

	
}