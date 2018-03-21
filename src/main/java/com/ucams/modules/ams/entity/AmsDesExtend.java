/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.ucams.common.persistence.DataEntity;

/**
 * 工程专业配置记录Entity
 * 
 * @author sunjx
 * @version 2017-06-21
 */
public class AmsDesExtend extends DataEntity<AmsDesExtend> {

	private static final long serialVersionUID = 1L;
	private AmsDesProgram amsDesProgram; // 归属表ID 父类
	private String name; // 录入项名
	private String comments; // 描述
	private String columnType;    //字节类型
	private String columnLength; //字节类型
	private String showType; // 字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）
	private String isNull; // 是否可为空
	private String dictType; // 字典类型
	private String settings; // 其它设置（扩展字段JSON）
	private String sort; // 排序（升序）

	public AmsDesExtend() {
		super();
	}

	public AmsDesExtend(String id) {
		super(id);
	}
	
	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnLength() {
		return columnLength;
	}

	public void setColumnLength(String columnLength) {
		this.columnLength = columnLength;
	}

	public AmsDesProgram getAmsDesProgram() {
		return amsDesProgram;
	}

	public void setAmsDesProgram(AmsDesProgram amsDesProgram) {
		this.amsDesProgram = amsDesProgram;
	}

	public AmsDesExtend(AmsDesProgram amsDesProgram) {
		this.amsDesProgram = amsDesProgram;
	}

	public AmsDesProgram getAmsEnginproTable() {
		return amsDesProgram;
	}

	public void setAmsEnginproTable(AmsDesProgram amsDesProgram) {
		this.amsDesProgram = amsDesProgram;
	}

	@Length(min = 1, max = 500, message = "录入项名长度必须介于 1 和 500 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 1, max = 100, message = "描述长度必须介于 1 和 100 之间")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Length(min = 1, max = 200, message = "字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）长度必须介于 1 和 200 之间")
	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

	@Length(min = 0, max = 200, message = "字典类型长度必须介于 0 和 200 之间")
	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	@Length(min = 0, max = 2000, message = "其它设置（扩展字段JSON）长度必须介于 0 和 2000 之间")
	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}
	
	@NotNull
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}