/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.ucams.common.persistence.DataEntity;

/**
 * 建设用地规划著录Entity
 * @author ws
 * @version 2017-07-28
 */
public class AmsLandDes extends DataEntity<AmsLandDes> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目主键
	private String fileId;		// 文件主键
	private String projectName;		// 用地项目名称
	private String address;		// 征地位置
	private String landUseUnit;		// 用地单位(默认建设单位）
	private String expropriatedUnit;		// 被征单位
	private String topographicMap;		// 地形图号
	private String landType;		// 用地分类
	private String expropriation;		// 征拨分类
	private String originalLandType;		// 原土地分类
	private Date approvalDate;		// 批准时间
	private BigDecimal landArea;		// 用地面积
	private String descriptionJson;		// 著录扩展信息
	private String exten1;		// 扩展1
	private String exten2;		// 扩展2
	private String exten3;		// 扩展3
	private String exten4;		// 扩展4
	private String exten5;		// 扩展5
	private List<AmsDesExtend> amsDesExtendList = Lists.newArrayList();
	private AmsFileInfo amsFileInfo;
	private AmsAcceptance amsAcceptance;
	
	private List<String> ids = Lists.newArrayList(); // id集合
	public AmsLandDes() {
		super();
	}

	public AmsLandDes(String id){
		super(id);
	}
	
	public AmsLandDes(String id,String projectId){
		super(id);
		this.projectId = projectId;
	}

	@Length(min=1, max=64, message="项目主键长度必须介于 1 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=0, max=64, message="文件主键长度必须介于 0 和 64 之间")
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	@Length(min=0, max=50, message="用地项目名称长度必须介于 0 和 50 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=0, max=100, message="征地位置长度必须介于 0 和 100 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=50, message="用地单位(默认建设单位）长度必须介于 0 和 50 之间")
	public String getLandUseUnit() {
		return landUseUnit;
	}

	public void setLandUseUnit(String landUseUnit) {
		this.landUseUnit = landUseUnit;
	}
	
	@Length(min=0, max=20, message="被征单位长度必须介于 0 和 20 之间")
	public String getExpropriatedUnit() {
		return expropriatedUnit;
	}

	public void setExpropriatedUnit(String expropriatedUnit) {
		this.expropriatedUnit = expropriatedUnit;
	}
	
	@Length(min=0, max=50, message="地形图号长度必须介于 0 和 50 之间")
	public String getTopographicMap() {
		return topographicMap;
	}

	public void setTopographicMap(String topographicMap) {
		this.topographicMap = topographicMap;
	}
	
	@Length(min=0, max=64, message="用地分类长度必须介于 0 和 64 之间")
	public String getLandType() {
		return landType;
	}

	public void setLandType(String landType) {
		this.landType = landType;
	}
	
	@Length(min=0, max=64, message="征拨分类长度必须介于 0 和 64 之间")
	public String getExpropriation() {
		return expropriation;
	}

	public void setExpropriation(String expropriation) {
		this.expropriation = expropriation;
	}
	
	@Length(min=0, max=64, message="原土地分类长度必须介于 0 和 64 之间")
	public String getOriginalLandType() {
		return originalLandType;
	}

	public void setOriginalLandType(String originalLandType) {
		this.originalLandType = originalLandType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	public BigDecimal getLandArea() {
		return landArea;
	}

	public void setLandArea(BigDecimal landArea) {
		this.landArea = landArea;
	}
	
	public String getDescriptionJson() {
		return descriptionJson;
	}

	public void setDescriptionJson(String descriptionJson) {
		this.descriptionJson = descriptionJson;
	}
	
	@Length(min=0, max=100, message="扩展1长度必须介于 0 和 100 之间")
	public String getExten1() {
		return exten1;
	}

	public void setExten1(String exten1) {
		this.exten1 = exten1;
	}
	
	@Length(min=0, max=100, message="扩展2长度必须介于 0 和 100 之间")
	public String getExten2() {
		return exten2;
	}

	public void setExten2(String exten2) {
		this.exten2 = exten2;
	}
	
	@Length(min=0, max=100, message="扩展3长度必须介于 0 和 100 之间")
	public String getExten3() {
		return exten3;
	}

	public void setExten3(String exten3) {
		this.exten3 = exten3;
	}
	
	@Length(min=0, max=100, message="扩展4长度必须介于 0 和 100 之间")
	public String getExten4() {
		return exten4;
	}

	public void setExten4(String exten4) {
		this.exten4 = exten4;
	}
	
	@Length(min=0, max=100, message="扩展5长度必须介于 0 和 100 之间")
	public String getExten5() {
		return exten5;
	}

	public void setExten5(String exten5) {
		this.exten5 = exten5;
	}

	public List<AmsDesExtend> getAmsDesExtendList() {
		return amsDesExtendList;
	}

	public void setAmsDesExtendList(List<AmsDesExtend> amsDesExtendList) {
		this.amsDesExtendList = amsDesExtendList;
	}

	public AmsFileInfo getAmsFileInfo() {
		return amsFileInfo;
	}

	public void setAmsFileInfo(AmsFileInfo amsFileInfo) {
		this.amsFileInfo = amsFileInfo;
	}

	public AmsAcceptance getAmsAcceptance() {
		return amsAcceptance;
	}

	public void setAmsAcceptance(AmsAcceptance amsAcceptance) {
		this.amsAcceptance = amsAcceptance;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
}