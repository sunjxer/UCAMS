/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.entity.AmsDesProgram;
import com.ucams.modules.ams.dao.AmsDesProgramDao;
import com.ucams.modules.ams.entity.AmsDesExtend;
import com.ucams.modules.ams.dao.AmsDesExtendDao;

/**
 * 工程专业配置记录Service
 * @author sunjx
 * @version 2017-06-21
 */
@Service
@Transactional(readOnly = true)
public class AmsDesProgramService extends CrudService<AmsDesProgramDao, AmsDesProgram> {

	@Autowired
	private AmsDesExtendDao amsDesExtendDao;
	@Autowired
	private AmsDesProgramDao amsDesProgramDao;
	
	public AmsDesProgram get(String id) {
		AmsDesProgram amsDesProgram = super.get(id);
		amsDesProgram.setAmsDesExtendList(amsDesExtendDao.findList(new AmsDesExtend(amsDesProgram)));
		return amsDesProgram;
	}
	
	public List<AmsDesProgram> findList(AmsDesProgram amsDesProgram) {
		return super.findList(amsDesProgram);
	}
	
	public Page<AmsDesProgram> findPage(Page<AmsDesProgram> page, AmsDesProgram amsDesProgram) {
		return super.findPage(page, amsDesProgram);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsDesProgram amsDesProgram) {
		super.save(amsDesProgram);
		for (AmsDesExtend amsDesExtend : amsDesProgram.getAmsDesExtendList()){
			if (amsDesExtend.getId() == null){
				continue;
			}
			if (AmsDesExtend.DEL_FLAG_NORMAL.equals(amsDesExtend.getDelFlag())){
				if (StringUtils.isBlank(amsDesExtend.getId())){
					amsDesExtend.setAmsEnginproTable(amsDesProgram);
					amsDesExtend.preInsert();
					amsDesExtendDao.insert(amsDesExtend);
				}else{
					amsDesExtend.preUpdate();
					amsDesExtendDao.update(amsDesExtend);
				}
			}else{
				amsDesExtendDao.delete(amsDesExtend);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsDesProgram amsDesProgram) {
		super.delete(amsDesProgram);
		amsDesExtendDao.delete(new AmsDesExtend(amsDesProgram));
	}
	
	@Transactional(readOnly = false)
	public void updateUseAble(Map<String, Object> paramMap){
		amsDesProgramDao.updateUseAble(paramMap);
	}
}