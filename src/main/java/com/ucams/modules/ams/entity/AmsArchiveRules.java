/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

import com.ucams.common.persistence.DataEntity;

/**
 * 归档一览表规则表Entity
 * @author sunjx
 * @version 2017-06-12
 */
public class AmsArchiveRules extends DataEntity<AmsArchiveRules> {
	
	private static final long serialVersionUID = 1L;
	private String fileName;		// 归档文件名
	private String createUnit;		// 生成单位
	private Integer sort; //排序
	private String org01;		// 预留字段01
	private String org02;		// 预留字段02
	private String org03;		// 预留字段03
	private AmsGenre amsGenre; //归属类别
	private AmsUserDict amsUserDict; //单位工程类型
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public AmsUserDict getAmsUserDict() {
		return amsUserDict;
	}

	public void setAmsUserDict(AmsUserDict amsUserDict) {
		this.amsUserDict = amsUserDict;
	}

	public AmsArchiveRules() {
		super();
	}

	public AmsArchiveRules(String id){
		super(id);
	}
	@JsonIgnore
	@NotNull(message="归属类别不能为空")
	public AmsGenre getAmsGenre() {
		return amsGenre;
	}

	public void setAmsGenre(AmsGenre amsGenre) {
		this.amsGenre = amsGenre;
	}
	
	@Length(min=1, max=100, message="归档文件名长度必须介于 1 和 100 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@JsonIgnore
	@NotNull(message="生成单位不能为空")
	public String getCreateUnit() {
		return createUnit;
	}

	public void setCreateUnit(String createUnit) {
		this.createUnit = createUnit;
	}
	
	public String getOrg01() {
		return org01;
	}

	public void setOrg01(String org01) {
		this.org01 = org01;
	}
	
	public String getOrg02() {
		return org02;
	}

	public void setOrg02(String org02) {
		this.org02 = org02;
	}
	
	public String getOrg03() {
		return org03;
	}

	public void setOrg03(String org03) {
		this.org03 = org03;
	}
	
}