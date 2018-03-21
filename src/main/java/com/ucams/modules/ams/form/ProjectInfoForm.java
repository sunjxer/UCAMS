package com.ucams.modules.ams.form;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.ucams.common.utils.DateUtils;

/** 项目信息form
 * @author BQL 
 * @version 创建时间：2017年7月7日 上午11:03:08 
 */
public class ProjectInfoForm {

	private String id;
	private String projectType;		// 项目类别
	private String projectName;		// 项目名称
	private String local;		// 项目地点
	private String address;		// 项目地址
	private String projectApprovalUnit;		// 立项批准单位
	private String designUnit;		// 设计单位
	private String prospectingUnit;		// 勘察单位
	private String supervisionUnit;		// 监理单位
	private String approvalNumber;		// 立项批准文号
	private String approvalUrl;		// 立项批准附件地址
	private String planningLicenseNumber;		// 规划许可证号
	private String landLicenseNumber;		// 用地规划许可证号
	private String landPermitNumber;		// 用地许可证号
	private String landLeasingPeriod;		// 土地出让年限
	private String descriptionJson;		// 著录扩展数据
	private List<SingleProjectForm> singleProjectList;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProjectApprovalUnit() {
		return projectApprovalUnit;
	}

	public void setProjectApprovalUnit(String projectApprovalUnit) {
		this.projectApprovalUnit = projectApprovalUnit;
	}

	public String getDesignUnit() {
		return designUnit;
	}

	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}

	public String getProspectingUnit() {
		return prospectingUnit;
	}

	public void setProspectingUnit(String prospectingUnit) {
		this.prospectingUnit = prospectingUnit;
	}

	public String getSupervisionUnit() {
		return supervisionUnit;
	}

	public void setSupervisionUnit(String supervisionUnit) {
		this.supervisionUnit = supervisionUnit;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public String getApprovalUrl() {
		return approvalUrl;
	}

	public void setApprovalUrl(String approvalUrl) {
		this.approvalUrl = approvalUrl;
	}

	public String getPlanningLicenseNumber() {
		return planningLicenseNumber;
	}

	public void setPlanningLicenseNumber(String planningLicenseNumber) {
		this.planningLicenseNumber = planningLicenseNumber;
	}

	public String getLandLicenseNumber() {
		return landLicenseNumber;
	}

	public void setLandLicenseNumber(String landLicenseNumber) {
		this.landLicenseNumber = landLicenseNumber;
	}

	public String getLandPermitNumber() {
		return landPermitNumber;
	}

	public void setLandPermitNumber(String landPermitNumber) {
		this.landPermitNumber = landPermitNumber;
	}

	public String getLandLeasingPeriod() {
		return landLeasingPeriod;
	}

	public void setLandLeasingPeriod(String landLeasingPeriod) {
		this.landLeasingPeriod = landLeasingPeriod;
	}

	public String getDescriptionJson() {
		return descriptionJson;
	}

	public void setDescriptionJson(String descriptionJson) {
		this.descriptionJson = descriptionJson;
	}

	public List<SingleProjectForm> getSingleProjectList() {
		return singleProjectList;
	}
	public void setSingleProjectList(List<SingleProjectForm> singleProjectList) {
		this.singleProjectList = singleProjectList;
	}
	
	
	
}
