package com.ucams.common.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class DBOperationsUImpl implements DBOperationsU {

	@Autowired
	private DBOperations dbOperations;

	@Override
	public void save(Object entity) {
		dbOperations.getMongoTemplate().save(entity);
	}

	@Override
	public void save(Object entity, String table) {
		dbOperations.getMongoTemplate().save(entity, table);
	}

	@Override
	public <T> void saveList(List<T> list) {
		dbOperations.getMongoTemplate().save(list);
	}

	@Override
	public boolean deleteDocument(String collectionName, DBObject query) {
		boolean result = false;
		WriteResult writeResult = null;
		DBCollection collection = dbOperations.getDbCollection(collectionName);
		if (null != collection) {
			writeResult = collection.remove(query);
			if (null != writeResult) {
				if (writeResult.getN() > 0) {
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public boolean updateDocument(String collectionName, DBObject query,
			DBObject updatedDocument) {
		boolean result = false;
		WriteResult writeResult = null;
		DBCollection collection = dbOperations.getDbCollection(collectionName);
		if (null != collection) {
			writeResult = collection.update(query, updatedDocument);
			if (null != writeResult) {
				if (writeResult.getN() > 0) {
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public void insertDocument(String collectionName, DBObject newDocument) {
		DBCollection collection = dbOperations.getDbCollection(collectionName);
		if (null != collection) {
			if (!this.isDocumentExsit(collectionName, newDocument)) {
				collection.insert(newDocument);
			}
		}
	}

	@Override
	public List<DBObject> selectDocument(String collectionName, DBObject query) {
		List<DBObject> result = new ArrayList<DBObject>();
		DBCursor dbCursor = null;
		DBCollection collection = dbOperations.getDbCollection(collectionName);
		if (null != collection) {
			dbCursor = collection.find(query);
			if (null != dbCursor && dbCursor.hasNext()) {
				for(DBObject odb : dbCursor){
					result.add(odb);
				}
			}
		}
		return result;
	}

	@Override
	public boolean isDocumentExsit(String collectionName, DBObject query) {
		boolean result = false;
		DBCursor dbCursor = null;
		DBCollection collection = dbOperations.getDbCollection(collectionName);
		if (null != collection) {
			dbCursor = collection.find(query);
			if (null != dbCursor && dbCursor.hasNext()) {
				result = true;
			}
		}
		return result;
	}
}
