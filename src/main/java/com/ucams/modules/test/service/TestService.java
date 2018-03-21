/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.service.CrudService;
import com.ucams.modules.test.dao.TestDao;
import com.ucams.modules.test.entity.Test;

/**
 * 测试Service
 * @author zhuye
 * @version 2013-10-17
 */
@Service
@Transactional(readOnly = true)
public class TestService extends CrudService<TestDao, Test> {
	
}
