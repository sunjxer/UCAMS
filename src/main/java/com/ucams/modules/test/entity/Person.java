package com.ucams.modules.test.entity;
/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年7月1日
 * 类说明 
 */
public class Person {
	private String name;
	private Integer age;
	private String work;
	
	public Person(String name,Integer age,String work){
		this.name = name;
		this.age = age;
		this.work = work;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	
	
}
