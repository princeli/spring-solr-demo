package com.princeli.dao.impl;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.springframework.util.Assert;

import com.princeli.common.Pager;
import com.princeli.dao.EntityDao;

 

public class EntityDaoImpl<T> extends GenericDaoHibernate<T,Long> implements EntityDao<T> {

	public Pager<T> pagedQuery(String hql, int pageNo, int pageSize,
			Object... values) {
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		// Count查询
		String countQueryString = " select nvl(count (*),0) " + removeSelect(removeOrders(hql));
		Integer totalCount = findInt(countQueryString, values); 
		
		// 实际查询返回分页对象
		int startIndex = Pager.getStartOfPage(pageNo, pageSize);
		Query query = createQuery(hql, values);
		//开启缓存
		query.setCacheable(true);
		List<T> list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

		return new Pager<T>(pageNo , startIndex, totalCount, pageSize, list);
	}
	
	
	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}
	
	
	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	
}
