package com.princeli.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.princeli.mongodb.dao.CustomerDao;
import com.princeli.mongodb.model.Customer;
import com.princeli.solr.model.DocumentSolr;
import com.princeli.solr.model.UserSolr;
import com.princeli.solr.repositories.DocumentRepository;
import com.princeli.solr.repositories.UserRepository;

/**
 * 单元测试
 * @author Administrator
 *
 */
public class SolrRepositoryTestBySpringTestFramework extends BaseTest{
	
 
    
    @Resource
    private DocumentRepository documentRepository;
    
    @Resource
    private UserRepository userRepository;
	
 
    @Test
    public void testFind(){
    	List<DocumentSolr> list = documentRepository.findByQueryAnnotation("1","world", new Sort(Sort.Direction.DESC, "id"));
    	
    	for (DocumentSolr documentSolr : list) {
			System.out.println(documentSolr);
		}
    }
	
    @Test
    public void testSave(){
    	DocumentSolr doc = new DocumentSolr();
		doc.setId("6");
		doc.setTitle("spring新增1");
		//doc.setContent("haha");
		doc.setDate_added(new Date());
    	documentRepository.save(doc);
    }
    
	/**
	 * 插入100万条数据，id自定义
	 */
	@Test
	public void testSaveList() {
		
	}

    
	@Test
	public void testGetAll() {
 
	     Iterable<UserSolr> iter2=  userRepository.findAll();
	     
	     for (Object i : iter2)
	     {
	         System.out.println(i);
	     }
	}
	
	@Test
	public void testFindById() {
 
		DocumentSolr doc = documentRepository.findOne("1");
		
		System.out.println(doc);
	}
	
	@Test
	public void testUpdate() {
		DocumentSolr doc = documentRepository.findOne("1");
		System.out.println("修改前："+doc);
		doc.setTitle("baby1");
		
		documentRepository.save(doc);	
		
		DocumentSolr newdoc = documentRepository.findOne("1");
		System.out.println("修改后："+doc);
		
 
	}
	
	@Test
	public void testRemove() {
		DocumentSolr doc = documentRepository.findByTitle("spring新增1");
		
		System.out.println(doc);
		
		documentRepository.delete(doc);
		
	}
	
	@Test
	public void testCount() {
 
	}

	
}
