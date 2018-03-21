/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.common.utils.EntityUtils;
import com.ucams.modules.ams.entity.AmsArchiveRules;
import com.ucams.modules.ams.dao.AmsArchiveRulesDao;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 归档一览表规则表Service
 * @author sunjx
 * @version 2017-06-12
 */
@Service
@Transactional(readOnly = true)
public class AmsArchiveRulesService extends CrudService<AmsArchiveRulesDao, AmsArchiveRules> {
	
	@Autowired
	AmsArchiveRulesDao amsArchiveRulesDao;
	
	public AmsArchiveRules get(String id) {
		return super.get(id);
	}
	
	public List<AmsArchiveRules> findList(AmsArchiveRules amsArchiveRules) {
		return super.findList(amsArchiveRules);
	}
	
	public Page<AmsArchiveRules> findPage(Page<AmsArchiveRules> page, AmsArchiveRules amsArchiveRules) {
		//设置排序
		page.setOrderBy("sort asc");
		return super.findPage(page, amsArchiveRules);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsArchiveRules amsArchiveRules) {
		super.save(amsArchiveRules);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsArchiveRules amsArchiveRules) {
		super.delete(amsArchiveRules);
	}
	
	/**
	 * 交换排序
	 * @param paramMap
	 */
	@Transactional(readOnly = false)
	public void changeSort(Map<String, Object> paramMap){
		amsArchiveRulesDao.updateSort(paramMap);
	}
}