package com.princeli.mongodb.dao;
 
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.princeli.common.Pager;


public interface BaseMongoDao<T> {

	/**
	 * 通过条件查询实体(集合)
	 * 
	 * @param query
	 */
	public List<T> find(Query query) ;

	/**
	 * 通过一定的条件查询一个实体
	 * 
	 * @param query
	 * @return
	 */
	public T findOne(Query query) ;

	/**
	 * 通过条件查询更新数据
	 * 
	 * @param query
	 * @param update
	 * @return
	 */
	public void update(Query query, Update update) ;

	/**
	 * 保存一个对象到mongodb
	 * 
	 * @param entity
	 * @return
	 */
	public T save(T entity) ;

	/**
	 * 通过ID获取记录
	 * 
	 * @param id
	 * @return
	 */
	public T findById(String id) ;

	/**
	 * 通过ID获取记录,并且指定了集合名(表的意思)
	 * 
	 * @param id
	 * @param collectionName
	 *            集合名
	 * @return
	 */
	public T findById(String id, String collectionName) ;
	
	/**
	 * 分页查询
	 * @param page
	 * @param query
	 * @return
	 */
	public Pager<T> findPage(Pager<T> page,Query query);
	
	/**
	 * 求数据总和
	 * @param query
	 * @return
	 */
	public long count(Query query);
	
	
	/**
	 * 查找全部
	 * @return
	 */
	public List<T> getAll();
	
	
	/**
	 * 删除对象
	 * @param entity
	 */
	public void remove(T entity);
	
	/**
	 * 更新对象
	 * @param update
	 */
	public void update(T entity) ;
	
	
	/**
	 * 批量插入list
	 * @param list 要插入的对象集合
	 */
	public void saveList(List<T> list);
	
}
