/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ucams.common.persistence.TreeEntity;

/**
 * 城建档案类别管理entity
 * 2017年12月1日 下午1:35:46
 * @author dpj
 */
public class AmsUcArchiveMenu extends TreeEntity<AmsUcArchiveMenu> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 类别编码
	private String createUnit;		// 生成单位
	private String type;
	private String isEndChild; //是否是最后子节点
	private String isHaveFile;//判断是否有文件
	private String projectId;//文件查询归档一揽表使用
	private String unitProjectId;//文件查询归档一揽表使用
	public AmsUcArchiveMenu() {
		super();
	}

	public AmsUcArchiveMenu(String id){
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
	public AmsUcArchiveMenu getParent() {
		return parent;
	}

	public void setParent(AmsUcArchiveMenu parent) {
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