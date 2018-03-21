/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.service.TreeService;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.entity.AmsUcArchiveMenu;
import com.ucams.modules.ams.dao.AmsUcArchiveMenuDao;

/**
 * 城建档案类别管理Service
 * 2017年12月1日 下午1:33:35
 * @author Administrator
 */
@Service
@Transactional(readOnly = true)
public class AmsUcArchiveMenuService extends TreeService<AmsUcArchiveMenuDao, AmsUcArchiveMenu> {
	
	@Autowired
	private AmsUcArchiveMenuDao amsUcArchiveMenuDao;
	
	public AmsUcArchiveMenu get(String id) {
		return super.get(id);
	}
	
	public List<AmsUcArchiveMenu> findList(AmsUcArchiveMenu amsUcArchiveMenu) {
		if (StringUtils.isNotBlank(amsUcArchiveMenu.getParentIds())){
			amsUcArchiveMenu.setParentIds(amsUcArchiveMenu.getParentIds().replace(" ", ""));
		}
		return super.findList(amsUcArchiveMenu);
	}
	
	@Transactional(readOnly = false)
	public AmsUcArchiveMenu findByCode(AmsUcArchiveMenu amsUcArchiveMenu) {
		return amsUcArchiveMenuDao.findByCode(amsUcArchiveMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsUcArchiveMenu amsUcArchiveMenu) {
		super.save(amsUcArchiveMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsUcArchiveMenu amsUcArchiveMenu) {
		super.delete(amsUcArchiveMenu);
	}
}