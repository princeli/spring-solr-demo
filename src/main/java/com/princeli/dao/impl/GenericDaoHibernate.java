package com.princeli.dao.impl;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import com.princeli.util.Utils;



 

/**
 * Hibernate的范型基类.
 * 
 * 可以在service类中直接创建使用.也可以继承出DAO子类,在多个Service类中共享DAO操作.
 * 参考Spring2.5自带的Petlinc例子,取消了HibernateTemplate,直接使用Hibernate原生API.
 * 通过Hibernate的sessionFactory.getCurrentSession()获得session,.
 * @param <T> DAO操作的对象类型
 * @param <PK> 主键类型
 * 
 * @author miller
 */
public class GenericDaoHibernate<T, PK extends Serializable> {

	private Log log = LogFactory.getLog(getClass()) ;
	
	private Class<T> classObject ;
	
	protected SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public GenericDaoHibernate(){
		//根据当前的子类来获取父类泛型类型Class<T>
		classObject = (Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	} 
	
	public void save(final T entity) {
		Assert.notNull(entity);
		getSession().saveOrUpdate(entity);
		log.debug("save entity: {}" + entity);
	}

	public void remove(final T entity) {
		Assert.notNull(entity);
		getSession().delete(entity);
		log.debug("delete entity: {}" + entity);
	}

	public void remove(final PK id) {
		Assert.notNull(id);
		remove(get(id));
		log.debug("delete entity: {}" + id);
	}
	
	
	public void update(final T entity) {
		Assert.notNull(entity);
		getSession().merge(entity);
		log.debug("merge entity: {}" + entity);
	}
	
 
	/**
	 * 批量插入
	 * @param ts
	 * @return
	 */
	public int batchSave(List<T> ts) {
		Assert.notNull(ts);
		for (int i = 0; i < ts.size(); i++) {

			Object obj = ts.get(i);

			getSession().save(obj);

			if (i % 500 == 0) {
				getSession().flush();
				getSession().clear();
			}

		}

		getSession().flush();
		getSession().clear();

		log.debug("save ts: {}" + ts.size() + "record");
		return ts.size();

	}

	/**
	 * 查询所有记录
	 * @return
	 */
	public List<T> getAll() {
		return findByCriteria();
	}
	
	/**
	 * 根据条件分页来查询记录
	 * @param firstResult
	 * @param maxResults
	 * @param criterions
	 * @return
	 */
	public List<T> findAll(final Long firstResult , final Long maxResults , final Criterion... criterions){
		return createCriteria(firstResult, maxResults, criterions).list() ;
	}

	/**
	 * 按id获取对象.
	 */
	@SuppressWarnings("unchecked")
	public T get(final PK id) {
		return (T) getSession().get(classObject, id);
	}

	/**
	 * 按属性查找对象列表.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(final String propertyName, final Object value) {
		Assert.hasText(propertyName);
		return createCriteria(Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 按属性查找唯一对象.
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByProperty(final String propertyName, final Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(Restrictions.eq(propertyName, value)).uniqueResult();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql hql语句
	 * @param values 数量可变的参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}
	/**
	 * 按HQL查询对象列表.
	 * @param hql hql语句
	 * @param firstResult 起始值
	 * @param maxResults  查询记录数
	 * @param values 数量可变的参数
	 */
	
	public List<T> findAll(final String hql,final Long firstResult , final Long maxResults , final Object... values){
		Query query = createQuery(hql, values) ;
		query.setFirstResult(firstResult.intValue()) ;
		query.setMaxResults(maxResults.intValue()) ;
		return query.list() ;
	}

	public Object findUnique(final String hql, final Object... values) {
		return createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueEntity(final String hql, final Object... values) {
		return (T) findUnique(hql, values);
	}

	/**
	 * 按HQL查询Integer类型结果. 
	 */
	public Integer findInt(final String hql, final Object... values) {
		Object object = findUnique(hql, values) ;
		if(null==object){
			return 0 ;
		}
		return Integer.parseInt(String.valueOf(object));
	}
	
	/**
	 * 按Criterion查询Integer类型结果. 
	 */
	public Integer findInt(final Criterion... criterion) {
		Criteria criteria = createCriteria(criterion) ;
		Integer totalRows =((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return totalRows ;
	} 

	/**
	 * 按HQL查询Long类型结果. 
	 */
	public Long findLong(final String hql, final Object... values) {
		Object object = findUnique(hql, values) ;
		if(null==object){
			return 0l ;
		}
		return Long.valueOf(String.valueOf(object));
	}

	/**
	 * 按Criterion查询对象列表.
	 * 
	 * @param criterion 数量可变的Criterion.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(final Criterion... criterion) {
		return createCriteria(criterion).list();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象,辅助函数.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString);
		Query queryObject = getSession().createQuery(queryString);
		if (values != null && values.length>0) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}

	/**
	 * 根据Criterion条件创建Criteria,辅助函数.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(classObject);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	/**
	 * 根据Criterion条件创建Criteria,辅助函数.
	 */
	public Criteria createCriteria(final Long firstResult , final Long maxResults , final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(classObject);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		criteria.setFirstResult(firstResult.intValue()) ;
		criteria.setMaxResults(maxResults.intValue()) ;
		return criteria;
	}
	
	/**
	 * 根据Criterion条件创建Criteria,增加排序功能,辅助函数
	 */
	public Criteria createCriteria(Map<String, String> sortMap, Criterion... criterions) {
		Criteria criteria = createCriteria(criterions);
		if (!Utils.mapIsEmpty(sortMap)) {
			for (String sortName : sortMap.keySet()) {
				if (Boolean.parseBoolean(sortMap.get(sortName))) {
					criteria.addOrder(Order.asc(sortName));
				} else {
					criteria.addOrder(Order.desc(sortName));
				}
			}
		}
		return criteria;
	}
	
	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 * 相对于SS2里那种以对象ID作为第3个参数查找数据库中原属性值的做法，以orgValue做第3个参数的做法侧重于从页面上发出Ajax判断请求的场景.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object orgValue) {
		if (newValue == null || newValue.equals(orgValue))
			return true;
		Object object = findUniqueByProperty(propertyName, newValue);
		return (object == null);
	}
}