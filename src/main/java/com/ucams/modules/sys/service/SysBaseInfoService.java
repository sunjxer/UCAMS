/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.modules.sys.dao.SysBaseInfoDao;
import com.ucams.modules.sys.entity.SysBaseInfo;

/**
 * 系统规则管理Service
 * @author gyl
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class SysBaseInfoService extends CrudService<SysBaseInfoDao, SysBaseInfo> {
	
	@Autowired
	private SysBaseInfoDao sysBaseInfoDao;

	public SysBaseInfo get(String id) {
		return super.get(id);
	}
	
	public List<SysBaseInfo> findList(SysBaseInfo sysBaseInfo) {
		return super.findList(sysBaseInfo);
	}
	
	public Page<SysBaseInfo> findPage(Page<SysBaseInfo> page, SysBaseInfo sysBaseInfo) {
		return super.findPage(page, sysBaseInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SysBaseInfo sysBaseInfo) {
		super.save(sysBaseInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysBaseInfo sysBaseInfo) {
		super.delete(sysBaseInfo);
	}
	
	public SysBaseInfo findSysBaseInfo(){
		SysBaseInfo sysBaseInfo;
		List<SysBaseInfo> list = sysBaseInfoDao.findSysBaseInfo();
		if(list.size() == 0){
			sysBaseInfo = new SysBaseInfo();
		}else{
			sysBaseInfo = list.get(0);
		}
		return sysBaseInfo;
	}
	
}