package com.princeli.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.princeli.solr.model.DocumentSolr;

public class Solr4CRUDTest {
	private  final Logger logger = LoggerFactory
			.getLogger(this.getClass());
	
	/** solr的url地址 */
	private static final String solrUrl = "http://127.0.0.1:8080/solr/document/";
	
	private  SolrServer solrServer;
	
	@Before
	public void init() {
			solrServer = new HttpSolrServer(solrUrl);
	}
	
	
	private void queryAll() {
		logger.info("查询documents的所有数据：");
		
		int start = 0;
		int rows = 500;
		Boolean flag = true;
		try {
			ModifiableSolrParams params = new ModifiableSolrParams();

			do {
				logger.info("开始查询第"+(start+1)+"到"+(start+rows)+"条数据");
				params.set("q", "*:*");
				params.set("start", start);
				params.set("rows", rows);
				QueryResponse response = solrServer.query(params);
				List<DocumentSolr> list = response.getBeans(DocumentSolr.class);

				if (list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						DocumentSolr solr = list.get(j);
						logger.info(solr.toString());
					}		
					
					if(list.size() < 500){
						break;
					}
					
					start += rows;
				}else{
					logger.info("暂无数据");
					flag = false;
				}
				
			} while (flag);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("结束查询documents的所有数据");
 
	}
	
 
	
	@Test
	public void query() {
		queryAll();
	}
	
	
	@Test
	public void add() {
		logger.info("新增documents的数据：");
		List<DocumentSolr> list = new ArrayList<DocumentSolr>();
		
		DocumentSolr doc1 = new DocumentSolr();
		doc1.setId("4");
		doc1.setTitle("新增1");
		doc1.setContent("How are you?");
		doc1.setDate_added(new Date());
		
		DocumentSolr doc2 = new DocumentSolr();
		doc2.setId("5");
		doc2.setTitle("新增2");
		doc2.setContent("I'm fine");
		doc2.setDate_added(new Date());
		
		list.add(doc1);
		list.add(doc2);
		
		try {
			solrServer.addBeans(list);
			solrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		queryAll();
		
	}
	
	
	@Test
	public void update() {
		logger.info("修改documents的数据：");
		try {
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("q", "id:2");
			params.set("start", 0);
			params.set("rows", 1);
			QueryResponse response = solrServer.query(params);
			List<DocumentSolr> list = response.getBeans(DocumentSolr.class);
			for (int i = 0; i < 1; i++) {
				DocumentSolr solr = list.get(i);
				solr.setContent("修改成功");
				solr.setTitle("haha");
			}
			if (list.size() > 0) {
				solrServer.addBeans(list);
				solrServer.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		queryAll();
	}
	
	
	@Test
	public void remove() {
		logger.info("删除documents的数据：");
		try {
			solrServer.deleteById("1");
			solrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		queryAll();	
	}
}
