/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.oa.dao;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.oa.entity.OaNotify;

/**
 * 通知通告DAO接口
 * @author zhuye
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNotifyDao extends CrudDao<OaNotify> {
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(OaNotify oaNotify);
	
}