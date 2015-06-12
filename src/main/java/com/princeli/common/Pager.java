package com.princeli.common;



import java.util.List;
import java.util.Scanner;

/**
 * 分页实体类
 * @author miller
 */
public class Pager<T> {
	/** 当前页 */
	private int currentPage = 1 ; 
	
	/** 每页显示记录,默认为10记录 */
	private int pageSize = 10 ;
	
	/** 总的页数 */
	private int totalPage ;
	
	/** 总的记录数 */
	private int totalSize ;
	
	/** 分页起始值 */
	private int startSize = 0 ; 
	
	/** 上一页 */
	private int prePage ;
	
	/** 下一页 */
	private int nextPage ; 
	
	private List<T> data; // 当前页中存放的记录,类型一般为List
	
	
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Pager(int currentPage , int pageSize){
		this.currentPage = currentPage ;
		this.pageSize = pageSize ;
	}
	
	public Pager(int pageSize){
		this.pageSize = pageSize ;
	}
	
	/**
	 * 默认构造方法.
	 *
	 * @param start	 本页数据在数据库中的起始位置
	 * @param totalSize 数据库中总记录条数
	 * @param pageSize  本页容量
	 * @param data	  本页包含的数据
	 */
	public Pager(int pageNo , int start, int totalSize, int pageSize, List<T> data) {
		this.pageSize = pageSize;
		this.startSize = start;
		this.totalSize = totalSize;
		this.data = data;
		this.currentPage = pageNo ;
	}
	
	
	public int getCurrentPage() {   
		if(this.currentPage==0){
			this.currentPage = 1 ;
		}
		if(this.currentPage > this.getTotalPage()){
			this.currentPage = this.getTotalPage() ;
		}
		return this.currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalPage() {
		if(this.totalSize % this.pageSize == 0){
			this.totalPage = (this.totalSize / this.pageSize) ;
		}else{
			this.totalPage = ((this.totalSize / this.pageSize) + 1);
		}
		return this.totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getTotalSize() {
		return totalSize;
	}
	
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	
	
	public int getStartSize() {
		if(this.currentPage > getTotalPage()){
			this.currentPage = getTotalPage() ;
		}
		if(this.currentPage <= 0 ){
			this.currentPage = 1 ;
		}
		this.startSize = (this.currentPage==1) ? 0 : ((this.currentPage-1)*this.pageSize) ;
		return this.startSize;
	}
	
	public void setStartSize(int startSize) {
		this.startSize = startSize;
	}
	
	
	/**
	 * 获取任一页第一条数据在数据集的位置.
	 *
	 * @param pageNo   从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}
	
	public static void main(String[] args) {
		Pager pager = new Pager(10,201) ;
		System.out.println("总共有 ："+pager.getTotalPage()+"页");
		Scanner scanner = new Scanner(System.in) ;
		while(scanner.hasNext()){
			pager.setCurrentPage(scanner.nextInt()) ; 
			System.out.println("每页显示：" + pager.getPageSize()) ;
			System.out.println("当前页：" + pager.getCurrentPage()) ;
			System.out.println("数据起始行为：" + pager.getStartSize()) ;
		}
	}

	public int getPrePage() {
		this.prePage = (this.currentPage==1) ? 1 : (this.currentPage-1) ; 
		return this.prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		this.nextPage = (this.currentPage==this.totalPage) ? this.totalPage : (this.currentPage+1) ; 
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
}
