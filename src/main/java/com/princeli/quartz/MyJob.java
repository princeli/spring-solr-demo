package com.princeli.quartz;

import java.util.Date;
 

 

/*
 * 使用spring+Quartz执行任务调度的具体类
 * */
public class MyJob {
 
	public void work() {
		System.out.println("当前时间:" + new Date().toString());
 
	}
}
