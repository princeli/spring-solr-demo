package com.princeli.manager;


import java.util.List;

import org.hibernate.criterion.Criterion;

import com.princeli.common.Pager;
import com.princeli.exception.BusinessException;

 

 


/**
 * 业务处理时一些简单的对象处理方法
 * @author ly
 *
 */
public interface EntityManager<T> {

	/** 根据ID查询 */
	T get(final Long id) throws BusinessException;

	/** 查询所有记录 */
	List<T> getAll() throws BusinessException;
	
	/** 保存记录 */
	void save(final T entity) throws BusinessException;

	/** 删除实体 */
	void remove(final T entity) throws BusinessException;

	/** 根据ID删除实体 */
	void remove(final Long id) throws BusinessException;
	
	/**批量保存记录*/
	int batchSave(List<T> ts) throws BusinessException;
	
	/** 根据HQL查询唯一记录 */
	T findUniqueEntity(final String hql, final Object... values) throws BusinessException;
	
	/** 根据HQL查询所有记录数 */
	List<T> query(final String hsql, final Object... args) throws BusinessException;
	
	/** 分页--根据criterions条件查询记录 */
	List<T> findAll(final Pager page , final Criterion... criterions) throws BusinessException;
	
	/** 分页--根据HQL查询记录 */ 
	List<T> findAll(final Pager pager , final String hql , final Object... values) throws BusinessException;
	
	
	public Pager<T> pagedQuery(String hql, int pageNo, int pageSize, Object... values) throws BusinessException;
	
}