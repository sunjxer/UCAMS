package com.ucams.test.test;


import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.ucams.modules.ams.dao.AmsProjectInfoDao;
import com.ucams.modules.ams.entity.AmsProjectInfo;

@Resource
@ContextConfiguration(locations={"classpath:../spring-context.xml"})
public class Test123 {
//	@Autowired
//	private AmsProjectInfoDao amsProjectInfoDao;
//	@Test
//	//getExternalProInfoList
//	public void test1(){
//		 List<AmsProjectInfo> findAllList = amsProjectInfoDao.getExternalProInfoList("22");
//		 System.out.println(findAllList.size());
//	}
}

