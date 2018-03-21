/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.ucams.common.persistence.DataEntity;

/**
 * 工程专业配置记录Entity
 * @author sunjx
 * @version 2017-06-21
 */
public class AmsDesProgram extends DataEntity<AmsDesProgram> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String comments;		// 描述
	private String useable;		// 是否启用(0：不启用，1 启用)
	private String programType;
	private List<AmsDesExtend> amsDesExtendList = Lists.newArrayList();		// 子表列表
	private String unitProjectType;	// 工程类型 
	
	public AmsDesProgram() {
		super();
	}

	public AmsDesProgram(String id){
		super(id);
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	@Length(min=1, max=200, message="名称长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public String getUnitProjectType() {
		return unitProjectType;
	}

	public void setUnitProjectType(String unitProjectType) {
		this.unitProjectType = unitProjectType;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=500, message="描述长度必须介于 1 和 500 之间")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Length(min=1, max=1, message="是否启用(0：不启用，1 启用)长度必须介于 1 和 1 之间")
	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public List<AmsDesExtend> getAmsDesExtendList() {
		return amsDesExtendList;
	}

	public void setAmsDesExtendList(List<AmsDesExtend> amsDesExtendList) {
		this.amsDesExtendList = amsDesExtendList;
	}

}