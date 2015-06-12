package com.princeli.mongodb.dao.impl;
 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.princeli.common.Pager;
import com.princeli.mongodb.dao.BaseMongoDao;
import com.princeli.util.ReflectionUtils;

 

public abstract class BaseMongoDaoImpl<T> implements BaseMongoDao<T>{

	private static final int DEFAULT_SKIP = 0;
	private static final int DEFAULT_LIMIT = 200;
	
	/**
	 * spring mongodb　集成操作类　
	 */
	@Autowired
	@Qualifier("mongoTemplate")
	protected MongoTemplate mongoTemplate;
 
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<T> find(Query query) {
		return mongoTemplate.find(query, this.getEntityClass());
	}

	@Override
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, this.getEntityClass());
	}

	@Override
	public void update(Query query, Update update) {
		mongoTemplate.findAndModify(query, update, this.getEntityClass());
	}

	@Override
	public T save(T entity) {
		mongoTemplate.insert(entity);
		return entity;
	}

	@Override
	public T findById(String id) {
		return mongoTemplate.findById(id, this.getEntityClass());
	}

	@Override
	public T findById(String id, String collectionName) {
		return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
	}
	
	@Override
	public Pager<T> findPage(Pager<T> page,Query query){
		long count = this.count(query);
		page.setTotalSize((int) count);
		int pageNumber = page.getCurrentPage();
		int pageSize = page.getPageSize();
		query.skip((pageNumber - 1) * pageSize).limit(pageSize);
		List<T> rows = this.find(query);
		page.setData(rows);
		return page;
	}
	
	@Override
	public long count(Query query){
		return mongoTemplate.count(query, this.getEntityClass());
	}
	
	
	@Override
	public List<T> getAll(){
		return find(null);
	}
	
	
	@Override
	public void remove(T entity){
		mongoTemplate.remove(entity);
	}
	
	@Override
	public void update(T entity){
		mongoTemplate.save(entity);
	}
	
	@Override
	public void saveList(List<T> list){
		mongoTemplate.insert(list, this.getEntityClass());
	}
	

	/**
	 * 获取需要操作的实体类class
	 * 
	 * @return
	 */
	private Class<T> getEntityClass(){
		return ReflectionUtils.getSuperClassGenricType(getClass());
	}

	

}

