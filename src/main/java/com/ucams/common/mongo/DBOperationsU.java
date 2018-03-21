package com.ucams.common.mongo;

import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBObject;

/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年7月1日
 * mongodb操作接口--无实体映射
 */
public interface DBOperationsU{
	
	/**
	 * 新增
	 */
	public void save(Object entity);
	
	public void save(Object entity , String table);
	
	public <T> void saveList(List<T> list);
	
	/**
	 * 删除
	 * @param collectionName 连接对象名 (=表名)
	 * @param query	 操作条件 (=where)
	 * @return
	 */
	public boolean deleteDocument(String collectionName, DBObject query);
	
	/**
	 * 更新
	 * @param collectionName 连接对象名 (=表名)
	 * @param query 操作条件 (=where)
	 * @param updatedDocument更新后对象
	 */
	public boolean updateDocument(String collectionName, DBObject query,DBObject updatedDocument);
	
	/**
	 * 插入
	 * @param collectionName 连接对象名 (=表名)
	 * @param newDocument 操作条件 (=where)
	 */
	public void insertDocument(String collectionName, DBObject newDocument);
	
	/**
	 * 查询
	 * @param collectionName 连接对象名 (=表名)
	 * @param query 操作条件 (=where)
	 * @return DBObject  转JSON
	 */
	public List<DBObject> selectDocument(String collectionName, DBObject query);
	
	/**
	 * 
	 * @param collectionName 连接对象名 (=表名)
	 * @param query 操作条件 (=where)
	 * @return 
	 */
	public boolean isDocumentExsit(String collectionName, DBObject query);
	
	
}
