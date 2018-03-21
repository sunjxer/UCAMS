/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.DataEntity;
import com.ucams.common.persistence.Page;

/**
 * Service基类
 * @author zhuye
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity);
	}
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findList(entity));
		return page;
	}

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	/**
	 * 保存数据（插入）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void insert(T entity) {
		//	entity.preInsertByNotId();
			dao.insert(entity);
	}
		/**
	 * 保存数据前设置默认用户和时间
	 * 用户默认为1
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void setDefaultDateUser(T entity) {
			entity.preDateUser();
	}
	
	/**
	 * 保存数据（更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void update(T entity) {
			entity.preUpdate();
			dao.update(entity);
	}
	
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}

}
