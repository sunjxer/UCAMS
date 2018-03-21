/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.service.BaseService;
import com.ucams.common.service.TreeService;
import com.ucams.modules.sys.dao.OfficeDao;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * 
 * @author zhuye
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	@Autowired
	private OfficeDao officeDao;

	public List<Office> findAll() {
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll) {
		if (isAll != null && isAll) {
			return UserUtils.getOfficeAllList();
		} else {
			return UserUtils.getOfficeList();
		}
	}

	public List<Office> onlyUnitPro(Office office) {
		List<Office> officeList = new ArrayList<Office>();
		User user = UserUtils.getUser();
		if (user.isAdmin()) {
			officeList = officeDao.onlyUnitPro(new Office());
		} else {
			/*office.getSqlMap().put("dsf",
					BaseService.dataScopeFilter(user, "a", ""));*/
			officeList = officeDao.onlyUnitPro(office);
		}
		return officeList;
	}

	@Transactional(readOnly = true)
	public List<Office> findList(Office office) {
		if (office != null) {
			User user = UserUtils.getUser();
			office.setParentIds(office.getParentIds() + "%");
			office.getSqlMap().put(
					"dsf",
					BaseService.dataScopeFilter(user.getCurrentUser(), "a",
							"pp"));
			return dao.findByParentIdsLike(office);
		}
		return new ArrayList<Office>();
	}

	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	@Transactional(readOnly = false)
	public void insertSysRoleOffice(String roleId, String officeId) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("roleId", roleId);
		map.put("officeId", officeId);
		dao.insertSysRoleOffice(map);	
	}
	
}
