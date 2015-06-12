package com.princeli.manager.impl;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;

import com.princeli.common.Pager;
import com.princeli.dao.EntityDao;
import com.princeli.exception.BusinessException;
import com.princeli.manager.EntityManager;

 

 

/**
 * 实体Manager业务和事物控制层
 * 子类实现具体实现Dao
 * 通过反射获取操作实体的Class类型
 * @author ly
 *
 */
public abstract class EntityManagerImpl<T> implements EntityManager<T> {
	
	/**
	 * 获取实体操作Dao层，有子类具体实现
	 * @return
	 * @throws BusinessException
	 */
	protected abstract EntityDao<T> getEntityDao() throws BusinessException  ;
	

	public T get(final Long id) throws BusinessException {
		try{
			return getEntityDao().get(id);
		}catch(HibernateException e){
			e.printStackTrace();
			throw new BusinessException();
		}
		
	}

	public List<T> getAll() throws BusinessException {
		try {
			return getEntityDao().getAll();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}
	

	public void remove(final T entity) throws BusinessException {
		try {
			getEntityDao().remove(entity);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	public void remove(final Long id) throws BusinessException {
		try {
			getEntityDao().remove(id);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
		
	}
	
	public int batchSave(List<T> ts) throws BusinessException{
		try {
			return getEntityDao().batchSave(ts);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	public void save(final T entity) throws BusinessException {
		try {
			getEntityDao().save(entity);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}
	
	public List<T> query(final String hsql, final Object... args) throws BusinessException {
		try {
			return getEntityDao().find(hsql, args);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}
	
	public T findUniqueEntity(final String hql, final Object... values) throws BusinessException{
		try{
			return getEntityDao().findUniqueEntity(hql, values) ;
		}catch(HibernateException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}
	
	
	/** 分页--根据criterions条件查询记录 */
	public List<T> findAll(final Pager page , final Criterion... criterions) throws BusinessException{
		try{
			int totalSize = getEntityDao().findInt(criterions) ;
			page.setTotalSize(totalSize) ;
			return getEntityDao().findAll(Long.valueOf(page.getStartSize()),Long.valueOf(page.getPageSize()), criterions) ;
		}catch(HibernateException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}
	
	/** 分页--根据HQL查询记录 */
	public List<T> findAll(final Pager pager , final String hql , final Object... values) throws BusinessException{
		try{
			String countHql = "select count(*) " + hql.substring(hql.indexOf("from"), hql.length()) ;
			int totalSize = getEntityDao().findInt(countHql, values) ;
			pager.setTotalSize(totalSize) ;
			return getEntityDao().findAll(hql, Long.valueOf(pager.getStartSize()),Long.valueOf(pager.getPageSize()), values) ;
		}catch(HibernateException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}
	
	public Pager<T> pagedQuery(String hql, int pageNo, int pageSize,
			Object... values) throws BusinessException {
		try {
			return getEntityDao().pagedQuery(hql, pageNo, pageSize, values);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}
	
}
