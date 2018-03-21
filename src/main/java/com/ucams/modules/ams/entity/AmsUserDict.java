/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.ucams.common.persistence.TreeEntity;

/**
 * 用户数据字典Entity
 * @author sunjx
 * @version 2017-06-16
 */
public class AmsUserDict extends TreeEntity<AmsUserDict> {
	
	private static final long serialVersionUID = 1L;
//	private AmsUserDict parent;		// 父级编号
//	private String parentIds;		// 所有父级编号
//	private String name;		// 名称
//	private String sort;		// 排序
	private String code;		// 编码
	private String type;		// 类型
	
	public AmsUserDict() {
		super();
	}

	public AmsUserDict(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public AmsUserDict getParent() {
		return parent;
	}

	public void setParent(AmsUserDict parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=100, message="编码长度必须介于 0 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=1, max=64, message="类型长度必须介于 1 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}