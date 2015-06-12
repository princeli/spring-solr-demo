package com.princeli.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/*.xml","classpath:/mongodb/*.xml","classpath:/solr/*.xml"})
public class BaseTest {
	public final Logger logger = LoggerFactory
			.getLogger(this.getClass());

}
