/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.ucams.common.persistence.TreeEntity;

/**
 * 声像档案类别entity
 * @author dpj
 * 2017年11月30日14:55:51
 */
public class AmsArchiveMenu extends TreeEntity<AmsArchiveMenu> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 类别编码
	private String createUnit;		// 生成单位
	private String type;
	private String isEndChild; //是否是最后子节点
	private String isHaveFile;//判断是否有文件
	private String projectId;//文件查询归档一揽表使用
	private String unitProjectId;//文件查询归档一揽表使用
	private String exten1;		// 扩展1 
	private String exten5;		// 扩展5
	private List<String> ids=Lists.newArrayList();		// String集合，查询时使用
	public AmsArchiveMenu() {
		super();
	}

	public AmsArchiveMenu(String id){
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
	public AmsArchiveMenu getParent() {
		return parent;
	}

	public void setParent(AmsArchiveMenu parent) {
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

	public String getExten1() {
		return exten1;
	}

	public void setExten1(String exten1) {
		this.exten1 = exten1;
	}

	public String getExten5() {
		return exten5;
	}

	public void setExten5(String exten5) {
		this.exten5 = exten5;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
	
	
}