/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.modules.ams.entity.AmsDesExtend;
import com.ucams.modules.ams.dao.AmsDesExtendDao;

/**
 * 工程专业配置内容Service
 * @author sunjx
 * @version 2017-06-16
 */
@Service
@Transactional(readOnly = true)
public class AmsDesExtendService extends CrudService<AmsDesExtendDao, AmsDesExtend> {

	public AmsDesExtend get(String id) {
		return super.get(id);
	}
	
	public List<AmsDesExtend> findList(AmsDesExtend amsDesExtend) {
		return super.findList(amsDesExtend);
	}
	
	public Page<AmsDesExtend> findPage(Page<AmsDesExtend> page, AmsDesExtend amsDesExtend) {
		return super.findPage(page, amsDesExtend);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsDesExtend amsDesExtend) {
		super.save(amsDesExtend);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsDesExtend amsDesExtend) {
		super.delete(amsDesExtend);
	}
	
}