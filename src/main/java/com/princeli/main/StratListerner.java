package com.princeli.main;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
public class StratListerner {

private static final String log4jPath = "/log4j.properties" ;
	
	public void initLog4j() throws Exception {
		Properties pro = new Properties();
		InputStream is = getClass().getResourceAsStream(log4jPath);
		pro.load(is);
		PropertyConfigurator.configure(pro); 
	}
	
	public static void start(String...args) {
		try{
			new StratListerner().initLog4j();
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					new String[] { "classpath*:spring/applicationContext*.xml",
							"classpath*:hibernate/applicationContext*.xml",
							"classpath*:mongodb/applicationContext*.xml",
							"classpath*:quartz/applicationContext*.xml",
							"classpath*:solr/applicationContext*.xml" });

		}catch(Exception e){
			e.printStackTrace() ; 
			System.out.println("初始化solr推送服务失败"); 
		}
	}
}
