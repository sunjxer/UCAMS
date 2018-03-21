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
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.entity.User;

/**
 * 移交Entity
 * @author zkx
 * @version 2017-07-25
 */
public class AmsTransfer  extends ActEntity<AmsTransfer>{
	
	private static final long serialVersionUID = 1L;
	private User 	user;	//	归属用户 移交申请人
	private User 	user2;	//	归属用户 移交申请审批人
	private User 	user3;	//	归属用户 移交验收审批人
	private String procInsId;		// 流程id
	private AmsProjectInfo project;			// 项目id
	private Role role;		// 建设单位外键
	private Date transferApplicatonDate;		// 移交申请日期
	private Date estimateTransferDate;		// 预计移交日期
	private String transferApplication;		// 移交申请内容
	private String transferApplicantPhone;		// 移交申请人联系电话
	private String transferApproval;		// 移交申请审批意见
	private String status;		// 移交流程状态
	private String acceptanceTransferNo;		// 接收和移交证明书编号
	private String type;		//类型0资料；1声像
	private String exten1;		// exten1
	private String exten2;		// exten2
	private String exten3;		// exten3
	private String exten4;		// exten4
	private String exten5;		// exten5
	private String acceptsString;		// 预验收字符串
	private Date beginTransferApplicatonDate;		// 开始 移交申请日期
	private Date endTransferApplicatonDate;		// 结束 移交申请日期
	private Date beginEstimateTransferDate;		// 开始 预计移交日期
	private Date endEstimateTransferDate;		// 结束 预计移交日期
	private List<AmsUnitProInfo> amsUnitProInfoList=Lists.newArrayList();		// 单位工程集合
	private List<AmsArchivesInfo> amsArchivesInfoList=Lists.newArrayList();		// 案卷集合
	private List<AmsTransferArchives> amsTransferArchivesList=Lists.newArrayList();		// 移交预验收集合
	private List<AmsAcceptance> amsAcceptancesList=Lists.newArrayList();		// 预验收集合
	private List<AmsTransferRpt> amsPreRptList=Lists.newArrayList();		// 报告集合
	private List<AmsConstructDes> amsConstructDesList = Lists.newArrayList();			// 建设工程规划
	private List<AmsLandDes> amsLandDesList = Lists.newArrayList();						// 建设用地规划
	public AmsTransfer() {
		super();
	}

	public AmsTransfer(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程id长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTransferApplicatonDate() {
		return transferApplicatonDate;
	}

	public void setTransferApplicatonDate(Date transferApplicatonDate) {
		this.transferApplicatonDate = transferApplicatonDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEstimateTransferDate() {
		return estimateTransferDate;
	}

	public void setEstimateTransferDate(Date estimateTransferDate) {
		this.estimateTransferDate = estimateTransferDate;
	}
	
	@Length(min=0, max=255, message="移交申请内容长度必须介于 0 和 255 之间")
	public String getTransferApplication() {
		return transferApplication;
	}

	public void setTransferApplication(String transferApplication) {
		this.transferApplication = transferApplication;
	}
	
	
	@Length(min=0, max=20, message="移交申请人联系电话长度必须介于 0 和 20 之间")
	public String getTransferApplicantPhone() {
		return transferApplicantPhone;
	}

	public void setTransferApplicantPhone(String transferApplicantPhone) {
		this.transferApplicantPhone = transferApplicantPhone;
	}
	
	@Length(min=0, max=255, message="移交申请审批意见长度必须介于 0 和 255 之间")
	public String getTransferApproval() {
		return transferApproval;
	}

	public void setTransferApproval(String transferApproval) {
		this.transferApproval = transferApproval;
	}
	
	
	@Length(min=0, max=64, message="移交流程状态长度必须介于 0 和 64 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=30, message="接收和移交证明书编号长度必须介于 0 和 30 之间")
	public String getAcceptanceTransferNo() {
		return acceptanceTransferNo;
	}

	public void setAcceptanceTransferNo(String acceptanceTransferNo) {
		this.acceptanceTransferNo = acceptanceTransferNo;
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

	public Date getBeginTransferApplicatonDate() {
		return beginTransferApplicatonDate;
	}

	public void setBeginTransferApplicatonDate(Date beginTransferApplicatonDate) {
		this.beginTransferApplicatonDate = beginTransferApplicatonDate;
	}

	public Date getEndTransferApplicatonDate() {
		return endTransferApplicatonDate;
	}

	public void setEndTransferApplicatonDate(Date endTransferApplicatonDate) {
		this.endTransferApplicatonDate = endTransferApplicatonDate;
	}

	public Date getBeginEstimateTransferDate() {
		return beginEstimateTransferDate;
	}

	public void setBeginEstimateTransferDate(Date beginEstimateTransferDate) {
		this.beginEstimateTransferDate = beginEstimateTransferDate;
	}

	public Date getEndEstimateTransferDate() {
		return endEstimateTransferDate;
	}

	public void setEndEstimateTransferDate(Date endEstimateTransferDate) {
		this.endEstimateTransferDate = endEstimateTransferDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public User getUser3() {
		return user3;
	}

	public void setUser3(User user3) {
		this.user3 = user3;
	}

	public AmsProjectInfo getProject() {
		return project;
	}

	public void setProject(AmsProjectInfo project) {
		this.project = project;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<AmsUnitProInfo> getAmsUnitProInfoList() {
		return amsUnitProInfoList;
	}

	public void setAmsUnitProInfoList(List<AmsUnitProInfo> amsUnitProInfoList) {
		this.amsUnitProInfoList = amsUnitProInfoList;
	}

	public List<AmsArchivesInfo> getAmsArchivesInfoList() {
		return amsArchivesInfoList;
	}

	public void setAmsArchivesInfoList(List<AmsArchivesInfo> amsArchivesInfoList) {
		this.amsArchivesInfoList = amsArchivesInfoList;
	}

	public List<AmsTransferArchives> getAmsTransferArchivesList() {
		return amsTransferArchivesList;
	}

	public void setAmsTransferArchivesList(
			List<AmsTransferArchives> amsTransferArchivesList) {
		this.amsTransferArchivesList = amsTransferArchivesList;
	}

	public List<AmsAcceptance> getAmsAcceptancesList() {
		return amsAcceptancesList;
	}

	public void setAmsAcceptancesList(List<AmsAcceptance> amsAcceptancesList) {
		this.amsAcceptancesList = amsAcceptancesList;
	}

	public List<AmsTransferRpt> getAmsPreRptList() {
		return amsPreRptList;
	}

	public void setAmsPreRptList(List<AmsTransferRpt> amsPreRptList) {
		this.amsPreRptList = amsPreRptList;
	}

	public String getAcceptsString() {
		return acceptsString;
	}

	public void setAcceptsString(String acceptsString) {
		this.acceptsString = acceptsString;
	}

	public List<AmsConstructDes> getAmsConstructDesList() {
		return amsConstructDesList;
	}

	public void setAmsConstructDesList(List<AmsConstructDes> amsConstructDesList) {
		this.amsConstructDesList = amsConstructDesList;
	}

	public List<AmsLandDes> getAmsLandDesList() {
		return amsLandDesList;
	}

	public void setAmsLandDesList(List<AmsLandDes> amsLandDesList) {
		this.amsLandDesList = amsLandDesList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}