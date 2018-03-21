package com.ucams.common.mongo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DuplicateKeyException;
import com.mongodb.ReadPreference;

/*** 
 * @author sunjx
 * @Repository
 */
public class DBOperationsImpl implements DBOperations{
	
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoTemplate template;
	
	
	@Override
	public DB getDB() {
		return template.getDb();
	}

	@Override
	public DBCollection getDbCollection(String collectionName) {
		return template.getCollection(collectionName);
	}

	@Override
	public MongoTemplate getMongoTemplate() {
		return template;
	}

	@Override
	public void save(Object entity) {
		try{
			template.save(entity);
		}catch(DuplicateKeyException e){}
	}
	
	@Override
	public void save(Object entity , String table){
		try{
			template.save(entity,table);
		}catch(DuplicateKeyException e){}
	}
	@Override
	public <T> void saveList(List<T> list) {
		try{
			template.insertAll(list);
		}catch(DuplicateKeyException e){}
	}

	@Override
	public <T> void removeById(String objId, Class<T> entityClass) {
		Query q = new Query(Criteria.where("_id").is(new ObjectId(objId)));
		template.remove(q, entityClass);
	}
	
	@Override
	public <T> void remove(Query query, Class<T> entityClass) {
		template.remove(query, entityClass);
	}

	@Override
	public <T> void update(Query query, Update update, Class<T> entityClass) {
		template.updateFirst(query, update, entityClass);
	}

	@Override
	public <T> void update(String objId, Update update, Class<T> entityClass) {
		Query q = new Query(Criteria.where("_id").is(new ObjectId(objId)));
		update(q, update, entityClass);
	}

	@Override
	public <T> void updateObjectId(String objId, Update update, Class<T> entityClass) {
		Query q = new Query(Criteria.where("_id").is(objId));
		update(q, update, entityClass);
	}

	@Override
	public <T> void updateMulti(Query query, Update update, Class<T> entityClass) {
		template.updateMulti(query, update, entityClass);		
	}

	@Override
	public <T> void updateMulti(String objId, Update update, Class<T> entityClass) {
		Query q = new Query(Criteria.where("_id").is(new ObjectId(objId)));
		updateMulti(q, update, entityClass);
	}

	@Override
	public <T> List<T> find(Query query, Class<T> entityClass) {		
		//默认由从库读取数据
		template.getDb().setReadPreference(ReadPreference.secondaryPreferred());
		return template.find(query, entityClass);
	}

	@Override
	public <T> List<T> find(Query query, Class<T> entityClass, ReadPreference readPreference) {
		if(null == readPreference) return find(query, entityClass);
		
		//从设置的主/从库中读取数据
		template.getDb().setReadPreference(readPreference);
		
		return template.find(query, entityClass);
	}

	@Override
	public <T> T findById(String objId, Class<T> entityClass) {
		Query query = new Query(Criteria.where("_id").is(new ObjectId(objId)));
		return findOne(query, entityClass);
	}

	@Override
	public <T> T findOne(Query query, Class<T> entityClass) {
		return (T)template.findOne(query, entityClass);
	}

	@Override
	public <T> T findOne(Query query, Class<T> entityClass, ReadPreference readPreference) {
		if(null == readPreference) return findOne(query, entityClass);

		//从设置的主/从库中读取数据
		String collectionName = template.getCollectionName(entityClass);
		return (T)template.getCollection(collectionName).findOne(query.getQueryObject(),
				query.getFieldsObject(), query.getSortObject(), readPreference);
	}

	@Override
	public <T> T findAndRemove(Query query, Class<T> entityClass) {
		return (T)template.findAndRemove(query, entityClass);
	}
	
	@Override
	public <T> T findAndRemove(String objId, Class<T> entityClass){
		Query query = new Query(Criteria.where("_id").is(new ObjectId(objId)));
		return findAndRemove(query, entityClass);
	}
	
	@Override
	public <T> T findAndModify(Query query, Update update, Class<T> entityClass){
		return template.findAndModify(query, update, entityClass);
	}

	@Override
	public <T> long count(Query query, Class<T> entityClass) {
		return template.count(query, entityClass);
	}

	/*@Override
	public <T> Pager<T> getObjectPagination(Pager<T> page) {
		long totalCount = count(page.getQuery(), page.getEntityClass());
		long pageCount = 1;
		if(totalCount % page.getPageSize() == 0){
			pageCount = totalCount / page.getPageSize();
		}else{
			pageCount = totalCount / page.getPageSize() + 1;
		}
		if(totalCount != 0){
			List<T> elements = template.find(page.getQuery(), page.getEntityClass());
			page.setElements(elements);
			page.setPageCount(pageCount);
			page.setElementsCount(totalCount);
		}
		return page;
	}*/

	@Override
	public <T> T findByIdNoObjectId(String objId, Class<T> entityClass) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("_id").is(objId));
		return findOne(query, entityClass);
	}

	@Override
	public <T> void removeByNoObjectId(String id, Class<T> entityClass) {
		Query query = new Query(Criteria.where("_id").is(id));
		template.remove(query, entityClass);
	}

	
}
