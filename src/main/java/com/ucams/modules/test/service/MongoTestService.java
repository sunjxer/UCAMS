package com.ucams.modules.test.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.ucams.common.mongo.DBOperations;
import com.ucams.common.mongo.DBOperationsU;

/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年7月1日
 * 类说明 
 */
@Service
@Transactional(readOnly = false)
public class MongoTestService {
	
	@Autowired
	private DBOperationsU dbOperationU;
	@Autowired
	private DBOperations dbOperation;
	
	public void save(Object obj,String table){
		dbOperationU.save(obj,table);
	}
	
	public List<DBObject> findAll(){
		List<DBObject> jsons =  dbOperationU.selectDocument("person", new BasicDBObject());
		System.out.println(jsons);
		return jsons;
	}
	
	/*public List<DBObject> easyFind(){
		
	}*/
}
