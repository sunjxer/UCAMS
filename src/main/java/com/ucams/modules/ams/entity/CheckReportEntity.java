package com.ucams.modules.ams.entity;
/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年7月27日
 * 检测报告数据实体
 */
public class CheckReportEntity {
	
	private String id ;
	private String name;
	private String type;
	private String url;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
