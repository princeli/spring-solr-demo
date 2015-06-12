package com.princeli.mongodb.dao.impl;

import org.springframework.stereotype.Repository;

import com.princeli.mongodb.dao.CustomerDao;
import com.princeli.mongodb.model.Customer;

@Repository("customerDao")
public class CustomerDaoImpl extends BaseMongoDaoImpl<Customer> implements CustomerDao {

 
}
