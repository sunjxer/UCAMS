/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.ucams.common.persistence.ActEntity;
import com.ucams.modules.sys.entity.Area;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.entity.User;

/**
 * 工程项目管理Entity
 * @author ws
 * @version 2017-06-26
 */
public class AmsProjectInfo extends ActEntity<AmsProjectInfo> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	private String constructionId;		// 建设单位主键
	private String projectType;		// 项目类别
	private String projectNo;		// 项目编号
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
	private String planningLicenseUrl;		// 项目规划附件地址
	private String landLicenseNumber;		// 用地规划许可证号
	private String landLicenseUrl;		// 用地规划附件地址
	private String landPermitNumber;		// 用地许可证号
	private String landPermitUrl;		// 用地许可证附件地址
	private Date startDate;		// 开工日期
	private Date finishDate;		// 竣工日期
	private String landLeasingPeriod;		// 土地出让年限
	private String descriptionJson;		// 著录扩展数据
	private String businessMan;		// 业务指导员
	private String businessManName;		// 业务指导员名称	
	private String checkStatus;		// 审核状态 0:未通过；1:通过；2：不通过
	private String opinion;		// 审核意见
	private String exten1;		
	private String exten2;		// exten2
	private String exten3;		// exten3
	private String exten4;		// exten4
	private String exten5;		// exten5
	private Role role;		// role
	private User user;		// user
	private AmsUnitDetailinfo amsUnitDetailInfo;		// role明细(责任主体明细)
	private String oldProjectName;// 原项目名
	private Area area;
	private String check;//条款
	private List<String> ids;	// id集合
	private String isfirst;		// 是否首次报建,1:首次,2:非首次
	private String isonline; //是否是在线报建项目0-是，1-否
	
	//辅助传参
	private String name;
	private String enname;
	private String loginName;
	
	private List<AmsDesExtend> amsDesExtendList = Lists.newArrayList();	
	
	public String getIsfirst() {
		return isfirst;
	}

	public void setIsfirst(String isfirst) {
		this.isfirst = isfirst;
	}

	public List<AmsDesExtend> getAmsDesExtendList() {
		return amsDesExtendList;
	}

	public void setAmsDesExtendList(List<AmsDesExtend> amsDesExtendList) {
		this.amsDesExtendList = amsDesExtendList;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnname() {
		return enname;
	}
	public void setEnname(String enname) {
		this.enname = enname;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public AmsProjectInfo(Role role) {
		this();
		this.role = role;
	}
	public AmsProjectInfo(User user) {
		this();
		this.user = user;
	}
	
	

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getOldProjectName() {
		return oldProjectName;
	}

	public void setOldProjectName(String oldProjectName) {
		this.oldProjectName = oldProjectName;
	}

	public AmsProjectInfo() {
		super();
	}

	public AmsProjectInfo(String id){
		super(id);
	}


	
	public AmsProjectInfo(Area area) {
		this();
		this.area = area;
	}
	
	

	public AmsProjectInfo(AmsUnitDetailinfo amsUnitDetailInfo) {
		this();
		this.amsUnitDetailInfo = amsUnitDetailInfo;
	}

	public AmsUnitDetailinfo getAmsUnitDetailInfo() {
		return amsUnitDetailInfo;
	}

	public void setAmsUnitDetailInfo(AmsUnitDetailinfo amsUnitDetailInfo) {
		this.amsUnitDetailInfo = amsUnitDetailInfo;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

	@Length(min=0, max=64, message="流程实例ID长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=64, message="建设单位主键长度必须介于 0 和 64 之间")
	public String getConstructionId() {
		return constructionId;
	}

	public void setConstructionId(String constructionId) {
		this.constructionId = constructionId;
	}
	
	@Length(min=0, max=64, message="项目类别长度必须介于 0 和 64 之间")
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	@Length(min=0, max=20, message="项目编号长度必须介于 0 和 20 之间")
	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	
	@Length(min=0, max=100, message="项目名称长度必须介于 0 和 100 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=0, max=64, message="项目地点长度必须介于 0 和 64 之间")
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
	@Length(min=0, max=100, message="项目地址长度必须介于 0 和 100 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
	@Length(min=0, max=100, message="勘察单位长度必须介于 0 和 100 之间")
	public String getProspectingUnit() {
		return prospectingUnit;
	}

	public void setProspectingUnit(String prospectingUnit) {
		this.prospectingUnit = prospectingUnit;
	}
	
	@Length(min=0, max=100, message="监理单位长度必须介于 0 和 100 之间")
	public String getSupervisionUnit() {
		return supervisionUnit;
	}

	public void setSupervisionUnit(String supervisionUnit) {
		this.supervisionUnit = supervisionUnit;
	}
	
	@Length(min=0, max=20, message="立项批准文号长度必须介于 0 和 20 之间")
	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	
	@Length(min=0, max=200, message="立项批准附件地址长度必须介于 0 和 200 之间")
	public String getApprovalUrl() {
		return approvalUrl;
	}

	public void setApprovalUrl(String approvalUrl) {
		this.approvalUrl = approvalUrl;
	}
	
	@Length(min=0, max=20, message="规划许可证号长度必须介于 0 和 20 之间")
	public String getPlanningLicenseNumber() {
		return planningLicenseNumber;
	}

	public void setPlanningLicenseNumber(String planningLicenseNumber) {
		this.planningLicenseNumber = planningLicenseNumber;
	}
	
	@Length(min=0, max=200, message="项目规划附件地址长度必须介于 0 和 200 之间")
	public String getPlanningLicenseUrl() {
		return planningLicenseUrl;
	}

	public void setPlanningLicenseUrl(String planningLicenseUrl) {
		this.planningLicenseUrl = planningLicenseUrl;
	}
	
	@Length(min=0, max=20, message="用地规划许可证号长度必须介于 0 和 20 之间")
	public String getLandLicenseNumber() {
		return landLicenseNumber;
	}

	public void setLandLicenseNumber(String landLicenseNumber) {
		this.landLicenseNumber = landLicenseNumber;
	}
	
	@Length(min=0, max=200, message="用地规划附件地址长度必须介于 0 和 200 之间")
	public String getLandLicenseUrl() {
		return landLicenseUrl;
	}

	public void setLandLicenseUrl(String landLicenseUrl) {
		this.landLicenseUrl = landLicenseUrl;
	}
	
	@Length(min=0, max=20, message="用地许可证号长度必须介于 0 和 20 之间")
	public String getLandPermitNumber() {
		return landPermitNumber;
	}

	public void setLandPermitNumber(String landPermitNumber) {
		this.landPermitNumber = landPermitNumber;
	}
	
	@Length(min=0, max=200, message="用地许可证附件地址长度必须介于 0 和 200 之间")
	public String getLandPermitUrl() {
		return landPermitUrl;
	}

	public void setLandPermitUrl(String landPermitUrl) {
		this.landPermitUrl = landPermitUrl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
	@Length(min=0, max=11, message="土地出让年限长度必须介于 0 和 11 之间")
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
	
	public String getBusinessMan() {
		return businessMan;
	}

	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}
	
	public String getBusinessManName() {
		return businessManName;
	}

	public void setBusinessManName(String businessManName) {
		this.businessManName = businessManName;
	}

	@Length(min=0, max=1, message="审核状态长度必须介于 0 和 1 之间")
	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	@Length(min=0, max=255, message="审核意见长度必须介于 0 和 255 之间")
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	@Length(min=0, max=100, message="exten1长度必须介于 0 和 100 之间")
	public String getExten1() {
		return exten1;
	}

	public void setExten1(String exten1) {
		this.exten1 = exten1;
	}
	
	@Length(min=0, max=100, message="exten2长度必须介于 0 和 100 之间")
	public String getExten2() {
		return exten2;
	}

	public void setExten2(String exten2) {
		this.exten2 = exten2;
	}
	
	@Length(min=0, max=100, message="exten3长度必须介于 0 和 100 之间")
	public String getExten3() {
		return exten3;
	}

	public void setExten3(String exten3) {
		this.exten3 = exten3;
	}
	
	@Length(min=0, max=100, message="exten4长度必须介于 0 和 100 之间")
	public String getExten4() {
		return exten4;
	}

	public void setExten4(String exten4) {
		this.exten4 = exten4;
	}
	
	@Length(min=0, max=100, message="exten5长度必须介于 0 和 100 之间")
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

	public String getIsonline() {
		return isonline;
	}

	public void setIsonline(String isonline) {
		this.isonline = isonline;
	}
	
}