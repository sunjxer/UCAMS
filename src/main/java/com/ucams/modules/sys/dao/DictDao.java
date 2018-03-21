/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.sys.dao;

import java.util.List;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author zhuye
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}
