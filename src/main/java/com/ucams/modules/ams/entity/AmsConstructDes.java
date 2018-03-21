/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.ucams.common.persistence.DataEntity;

/**
 * 建设工程规划著录Entity
 * @author ws
 * @version 2017-07-28
 */
public class AmsConstructDes extends DataEntity<AmsConstructDes> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目主键
	private String fileId;		// 文件主键
	private String projectName;		// 工程名称
	private String address;		// 工程地点
	private String constructionUnit;		// 建设单位
	private String projectApprovalUnit;		// 立项批准单位
	private String designUnit;		// 设计单位
	private String prospectingUnit;		// 施工单位
	private String approvalNumber;		// 立项批准文号
	private String planningLicenseNumber;		// 规划许可证号
	private String landLicenseNumber;		// 用地规划许可证号
	private String landPermitNumber;		// 用地许可证号
	private String topographicMap;		// 地形图号
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

	public AmsConstructDes() {
		super();
	}

	public AmsConstructDes(String id){
		super(id);
	}

	public AmsConstructDes(String id,String projectId) {
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
	
	@Length(min=0, max=100, message="工程名称长度必须介于 0 和 100 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=0, max=50, message="工程地点长度必须介于 0 和 50 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=50, message="建设单位长度必须介于 0 和 50 之间")
	public String getConstructionUnit() {
		return constructionUnit;
	}

	public void setConstructionUnit(String constructionUnit) {
		this.constructionUnit = constructionUnit;
	}
	
	@Length(min=0, max=50, message="立项批准单位长度必须介于 0 和 50 之间")
	public String getProjectApprovalUnit() {
		return projectApprovalUnit;
	}

	public void setProjectApprovalUnit(String projectApprovalUnit) {
		this.projectApprovalUnit = projectApprovalUnit;
	}
	
	@Length(min=0, max=20, message="设计单位长度必须介于 0 和 20 之间")
	public String getDesignUnit() {
		return designUnit;
	}

	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}
	
	@Length(min=0, max=100, message="施工单位长度必须介于 0 和 100 之间")
	public String getProspectingUnit() {
		return prospectingUnit;
	}

	public void setProspectingUnit(String prospectingUnit) {
		this.prospectingUnit = prospectingUnit;
	}
	
	@Length(min=0, max=20, message="立项批准文号长度必须介于 0 和 20 之间")
	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	
	@Length(min=0, max=20, message="规划许可证号长度必须介于 0 和 20 之间")
	public String getPlanningLicenseNumber() {
		return planningLicenseNumber;
	}

	public void setPlanningLicenseNumber(String planningLicenseNumber) {
		this.planningLicenseNumber = planningLicenseNumber;
	}
	
	@Length(min=0, max=20, message="用地规划许可证号长度必须介于 0 和 20 之间")
	public String getLandLicenseNumber() {
		return landLicenseNumber;
	}

	public void setLandLicenseNumber(String landLicenseNumber) {
		this.landLicenseNumber = landLicenseNumber;
	}
	
	@Length(min=0, max=20, message="用地许可证号长度必须介于 0 和 20 之间")
	public String getLandPermitNumber() {
		return landPermitNumber;
	}

	public void setLandPermitNumber(String landPermitNumber) {
		this.landPermitNumber = landPermitNumber;
	}
	
	@Length(min=0, max=50, message="地形图号长度必须介于 0 和 50 之间")
	public String getTopographicMap() {
		return topographicMap;
	}

	public void setTopographicMap(String topographicMap) {
		this.topographicMap = topographicMap;
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