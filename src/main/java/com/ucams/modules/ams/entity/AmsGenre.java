/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ucams.common.persistence.TreeEntity;

/**
 * 归档一览表类别管理Entity
 * @author sunjx
 * @version 2017-06-12
 */
public class AmsGenre extends TreeEntity<AmsGenre> {
	
	private static final long serialVersionUID = 1L;
//	private AmsGenre parent;		// 父级编号
//	private String parentIds;		// 所有父级编号
//	private String name;		// 名称
//	private String sort;		// 排序
	private String code;		// 类别编码
	private String createUnit;		// 生成单位
	private String type;
	private String isEndChild; //是否是最后子节点
	private String isHaveFile;//判断是否有文件
	private String projectId;//文件查询归档一揽表使用
	private String unitProjectId;//文件查询归档一揽表使用
	public AmsGenre() {
		super();
	}

	public AmsGenre(String id){
		super(id);
	}
	
	public String getIsEndChild() {
		return isEndChild;
	}

	public void setIsEndChild(String isEndChild) {
		this.isEndChild = isEndChild;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	@NotNull(message="父级编号不能为空")
	public AmsGenre getParent() {
		return parent;
	}

	public void setParent(AmsGenre parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=100, message="类别编码长度必须介于 0 和 100 之间")
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

	public String getIsHaveFile() {
		return isHaveFile;
	}

	public void setIsHaveFile(String isHaveFile) {
		this.isHaveFile = isHaveFile;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUnitProjectId() {
		return unitProjectId;
	}

	public void setUnitProjectId(String unitProjectId) {
		this.unitProjectId = unitProjectId;
	}
	
	
	
}