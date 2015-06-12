package com.princeli.dao;


import java.util.List;

import org.hibernate.criterion.Criterion;

import com.princeli.common.Pager;

 
 
/**
 * 实体操作接口
 * @author ly
 * @param <T>
 */
public interface EntityDao<T> {

	public T get(final Long id);

	public List<T> getAll();
	
	public List<T> findAll(final Long firstResult , final Long maxResults , final Criterion... criterions);
	
	public void save(final T entity);

	public void remove(final T entity);

	public void remove(final Long id);
	
	public void update(final T entity) ;
	
	public int batchSave(List<T> ts);
	
	public List<T> find(final String hql,final Object... values);
	
	public List<T> findAll(final String hql , final Long firstResult , final Long maxResults , final Object... values);
	
	public List<T> findByProperty(final String propertyName, final Object value); 
	
	public T findUniqueEntity(final String hql, final Object... values);
	
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object orgValue);
	
	public Integer findInt(final String hql, final Object... values) ;

	public Integer findInt(final Criterion... criterions) ;
	
	public Pager<T> pagedQuery(String hql, int pageNo, int pageSize, Object... values);
}
