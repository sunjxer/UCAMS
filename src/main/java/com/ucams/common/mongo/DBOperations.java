package com.ucams.common.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.ReadPreference;

/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年7月1日
 * mongodb操作接口--有实体映射
 */
public interface DBOperations {
	
	/**
	 * 获取db
	 * @return
	 */
	public DB getDB();
	
	/**
	 * 获取DB Collection
	 * @param collectionName
	 * @return
	 */
	public DBCollection getDbCollection(String collectionName);

	/**
	 * 获取Spring Template
	 * @return
	 */
	public MongoTemplate getMongoTemplate();
	
	/**
	 * 新增
	 */
	public void save(Object entity);
	
	public void save(Object entity , String table);
	
	public <T> void saveList(List<T> list);
	/**
	 * 删除
	 */
	public <T> void removeById(String objId, Class<T> entityClass);
	
	public <T> void removeByNoObjectId(String id,Class<T> entityClass); 
	
	public <T> void remove(Query query, Class<T> entityClass);
	
	/**
	 * 更新
	 */
	public <T> void update(Query query, Update update, Class<T> entityClass);
	
	public <T> void update(String objId, Update update, Class<T> entityClass);
	
	public <T> void updateObjectId(String objId, Update update, Class<T> entityClass);
	
	public <T> void updateMulti(Query query, Update update, Class<T> entityClass);
	
	public <T> void updateMulti(String objId, Update update, Class<T> entityClass);
	
	
	/**
	 * 查询
	 */
	
	public <T> List<T> find(Query query, Class<T> entityClass);
	
	public <T> List<T> find(Query query, Class<T> entityClass, ReadPreference readPreference);
	
	public <T> T findById(String objId, Class<T> entityClass);
	
	public <T> T findOne(Query query, Class<T> entityClass);
	
	public <T> T findOne(Query query, Class<T> entityClass, ReadPreference readPreference);
	
	public <T> T findAndRemove(Query query, Class<T> entityClass);
	
	public <T> T findAndRemove(String objId, Class<T> entityClass);
	
	public <T> T findAndModify(Query query, Update update, Class<T> entityClass);
	
	/**
	 * 根据条件查询符合数据数量
	 */
	public <T> long count(Query query, Class<T> entityClass);

	/**
	 * 分页查询
	 * @param page
	 * @param entityClass
	 * @param collectionName
	 */
	/*public <T> Pager<T> getObjectPagination(Pager<T> page);*/
	
	public <T> T findByIdNoObjectId(String objId, Class<T> entityClass);
	
}
