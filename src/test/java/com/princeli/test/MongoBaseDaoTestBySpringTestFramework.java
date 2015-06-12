package com.princeli.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.princeli.mongodb.dao.CustomerDao;
import com.princeli.mongodb.model.Customer;

/**
 * 单元测试
 * @author Administrator
 *
 */
public class MongoBaseDaoTestBySpringTestFramework extends BaseTest{
	
    @Autowired 
    private MongoTemplate mongoTemplate;
	
	@Resource(name = "customerDao")
	private CustomerDao customerDao;
	
	
    @Test
    public void testMongoTemplate(){
        Customer customer = new Customer();
        customer.setFirstName("li");
        customer.setLastName("hao");
        mongoTemplate.insert(customer);
    }
	
    @Test
    public void testSave(){
        Customer customer = new Customer();
        customer.setFirstName("zhang");
        customer.setLastName("san");
        customerDao.save(customer);
        logger.info("添加成功");
    }
    
	/**
	 * 插入100万条数据，id自定义
	 */
	@Test
	public void testSaveList() {
		List<Customer> customerList = new ArrayList<Customer>();
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 100000; i++) {
				Customer customer = new Customer();
		        customer.setFirstName("zhang"+j);
		        customer.setLastName("san"+i);
		        customerList.add(customer);
			}
		}
		customerDao.saveList(customerList);
		
		 logger.info("添加"+customerList.size()+"条数据成功");
	}

    
	@Test
	public void testGetAll() {
		logger.info("获取全部的数据----------------------");
		List<Customer> customerList = customerDao.getAll();
		for (Customer customer : customerList) {
			logger.info(customer.toString());
		}
		
	}
	
	@Test
	public void testFindById() {
		logger.info("获取单个对象----------------------");
		Customer customer = customerDao.findById("5571672fe4194b846faf8f2e");
		logger.info(customer.toString());
		
	}
	
	@Test
	public void testUpdate() {
		Customer customer = customerDao.findById("5571672fe4194b846faf8f2e");
		customer.setFirstName("li");
		customer.setLastName("si");
		customerDao.update(customer);
		Customer ncustomer = customerDao.findById("5571672fe4194b846faf8f2e");
		logger.info(ncustomer.toString());
		logger.info("修改数据成功");
 
	}
	
	@Test
	public void testRemove() {
		Customer customer = customerDao.findById("5571672fe4194b846faf8f2e");
		customerDao.remove(customer);
		Customer oldCustomer = customerDao.findById("5571672fe4194b846faf8f2e");
		if (oldCustomer == null) {
			logger.info((oldCustomer == null)+"");
			logger.info("删除对象成功");
		}
		customerDao.save(customer);
	}
	
	@Test
	public void testCount() {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("firstName").is("li");
		query.addCriteria(criteria);
		long total = customerDao.count(query);
		logger.info("用户总数:" + total);
	}

	
}
