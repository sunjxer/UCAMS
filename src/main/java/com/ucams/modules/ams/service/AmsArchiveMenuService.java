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
import com.ucams.modules.ams.entity.AmsArchiveMenu;
import com.ucams.modules.ams.dao.AmsArchiveMenuDao;

/**
 * 声像档案类别管理Service
 * 2017年12月1日 下午1:37:13
 * @author dpj
 */
@Service
@Transactional(readOnly = true)
public class AmsArchiveMenuService extends TreeService<AmsArchiveMenuDao, AmsArchiveMenu> {
	
	@Autowired
	private AmsArchiveMenuDao amsArchiveMenuDao;
	
	public AmsArchiveMenu get(String id) {
		return super.get(id);
	}
	
	public List<AmsArchiveMenu> findList(AmsArchiveMenu amsArchiveMenu) {
		if (StringUtils.isNotBlank(amsArchiveMenu.getParentIds())){
			amsArchiveMenu.setParentIds(amsArchiveMenu.getParentIds().replace(" ", ""));
		}
		return super.findList(amsArchiveMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsArchiveMenu amsArchiveMenu) {
		super.save(amsArchiveMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsArchiveMenu amsArchiveMenu) {
		super.delete(amsArchiveMenu);
	}

}