package com.princeli.util;

import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 


public class Utils {
	
	private static final Logger logger = LoggerFactory
			.getLogger(Utils.class);
	
	/**
	 * 判断Map是否为空
	 * @param map
	 * @return
	 */
	public static  boolean mapIsEmpty(Map<?,?> map){
		if(null==map || map.size() <= 0){
			return true ;
		}
		return false;
	}
	
	
	/**
	 * 验证手机号码是否正确
	 * @param mobileNum
	 * @return
	 */
	public static boolean checkMobileNum(String mobileNum){
		boolean flag = false ;
		try{
			String regExp = "^((13[5-9]{1})|(15[0-2]{1})|(15[7-9]{1})|(18[7-8]{1})|(14[7]{1})|13[4]{1})+\\d{8}$" ;
			if(mobileNum.matches(regExp)){
				flag = true ;
			}
		}catch(Exception e){
			e.printStackTrace() ;
		}
		return flag ;
	}
	
	
	
	/**
	 * 产生随机数
	 * @param length
	 * @return
	 */
	public static String getStringOfRandomLetter(int length) {
		if (length <= 0)
			return null;
		char[] NUM_LETTER = { '0', '1', '2', '3', '4', '5', '6', '7', '8','9'};
		StringBuffer sbRandom = new StringBuffer();
		char[] character = NUM_LETTER;
		for (int i = 0; i < length; i++) {
			sbRandom.append(character[new Random().nextInt(character.length)]);
		}
		return sbRandom.toString();
	}
 
	
}
