/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.form;

/**
 * 归档一览表类别管理Form
 * @author BQL
 * @version 2017-06-12
 */
public class GenreForm {
	
	protected String id;	// 编号
	protected String parentId;	// 父级编号
	protected String parentIds; // 所有父级编号
	protected String name; 	// 机构名称
	protected Integer sort;		// 排序
	private String nodeType; //节点类型  0 归档表目录添加 1 基础类型添加
	private String code;		// 类别编码
	private String createUnit;		// 生成单位
	private String type;
	private String isEndChild; //是否是最后子节点
	private String isHaveFile;//判断是否有文件
	protected String remarks;	// 备注
	public GenreForm() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCreateUnit() {
		return createUnit;
	}
	public void setCreateUnit(String createUnit) {
		this.createUnit = createUnit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getIsEndChild() {
		return isEndChild;
	}
	public void setIsEndChild(String isEndChild) {
		this.isEndChild = isEndChild;
	}
	public String getIsHaveFile() {
		return isHaveFile;
	}
	public void setIsHaveFile(String isHaveFile) {
		this.isHaveFile = isHaveFile;
	}
	
}