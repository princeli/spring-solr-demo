package com.princeli.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SysConfig {

	private static Logger logger = Logger.getLogger(SysConfig.class);

	private static Properties properties = new Properties();
	
	private static final String syscfg = "config/cfg.properties";

	static {
		try {
			InputStream is = SysConfig.class.getClassLoader().getResourceAsStream(syscfg);
			properties.load(is);
			logger.debug("加载配置'" + syscfg + "'成功！");
		} catch (FileNotFoundException e) {
			logger.error("配置文件不存在！",e);
		} catch (IOException e) {
			logger.error("读取配置文件IO错误！",e);
		}

	}

	/**
	 * 根据key获得sysConfig.properties中的值
	 * @param key
	 * @return
	 */
	public static String getStrValue(String key) {
		return properties.getProperty(key);
	}
	
	/**
	 * 根据key获得sysConfig.properties中的值
	 * @param key
	 * @param defaltValue	如果没有取到值返回默认值
	 * @return
	 */
	public static String getStrValue(String key,String defaltValue) {
		String value = properties.getProperty(key);
		if(value == null){
			return defaltValue;
		}else{
			return value;
		}
	}
	
	/**
	 * 根据key获得sysConfig.properties中的值,转为int
	 * @param key
	 * @return
	 */
	public static int getIntValue(String key){
		String value = getStrValue(key);
		try{
			int intValue = Integer.valueOf(value);
			return intValue;
		}catch(Exception e){
			logger.error("将value:" + value + ",转为int时发生错误",e);
		}
		return -1;
	}
	
	public static void main(String[] args) {
		System.out.println(getStrValue("fdsfs"));
	}

}
