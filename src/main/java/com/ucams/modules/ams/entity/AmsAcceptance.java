/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.ucams.common.persistence.ActEntity;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.entity.User;

/**
 * 预验收管理Entity
 * @author zkx
 * @version 2017-07-11
 */
public class AmsAcceptance  extends ActEntity<AmsAcceptance> {
	
	private static final long serialVersionUID = 1L;
	private User 	user;	//	归属用户
	private User 	user2;	//	归属用户
	private User 	user3;	//	归属用户
	private String procInsId;		// 审批流id
	private AmsProjectInfo project;		// 项目主键
	private Role role;		// 建设单位外键
	private Date preAcceptanceApplyDate;		// 预验收申请日期
	private String preAcceptanceApplicant;		// 预验收申请人
	private String preAcceptanceApprovalOpinions;		// 预验收申请审批意见
	private String preAcceptanceApplicationApprover;		// 预验收申请审批人
	private String preAcceptanceApprover;		// 预验收人
	private String preAcceptanceNo;		// 预验收意见书编号
	private String status;		// 流程环节状态
	private String exten1;		// 扩展1
	private String exten2;		// 扩展2
	private String exten3;		// 扩展3
	private String exten4;		// 扩展4
	private String exten5;		// 扩展5
	private String type;		// 流程环节状态
	private String projectString;		// 单位工程集合
	private String archiverString;		// 公用卷集合
	private String accString;		// 建设工程规划集合
	private String lanString;		// 建设用地规划集合
	private String videoMenuString;		// 建设用地规划集合
	private Date beginPreAcceptanceApplyDate;		// 开始 预验收申请日期
	private Date endPreAcceptanceApplyDate;		// 结束 预验收申请日期
	private List<AmsUnitProInfo> amsUnitProInfoList=Lists.newArrayList();		// 单位工程集合
	private List<AmsArchivesInfo> amsArchivesInfoList=Lists.newArrayList();		// 案卷集合
	private List<AmsConstructDes> amsConstructDesList = Lists.newArrayList();			// 建设工程规划
	private List<AmsLandDes> amsLandDesList = Lists.newArrayList();						// 建设用地规划
	private List<AmsAcceptanceArchives> amsAcceptanceArchivesList=Lists.newArrayList();		// 预验收——案卷集合
	private List<AmsArchiveMenu> videoMenuList=Lists.newArrayList();		// 预验收——案卷集合
	private List<AmsPreRpt> amsPreRptList=Lists.newArrayList();		// 报告集合
	private List<String> ids=Lists.newArrayList();		// String集合，查询时使用
	
	public AmsAcceptance() {
		super();
	}

	public AmsAcceptance(String id){
		super(id);
	}

	public List<AmsPreRpt> getAmsPreRptList() {
		return amsPreRptList;
	}

	public void setAmsPreRptList(List<AmsPreRpt> amsPreRptList) {
		this.amsPreRptList = amsPreRptList;
	}

	public User getUser() {
		return user;
	}

	public void setUser2(User user) {
		this.user2 = user;
	}
	public User getUser2() {
		return user2;
	}
	
	public void setUser3(User user) {
		this.user3 = user;
	}
	public User getUser3() {
		return user3;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="预验收申请日期不能为空")
	public Date getPreAcceptanceApplyDate() {
		return preAcceptanceApplyDate;
	}

	public void setPreAcceptanceApplyDate(Date preAcceptanceApplyDate) {
		this.preAcceptanceApplyDate = preAcceptanceApplyDate;
	}
	
	public String getPreAcceptanceApplicant() {
		return preAcceptanceApplicant;
	}

	public void setPreAcceptanceApplicant(String preAcceptanceApplicant) {
		this.preAcceptanceApplicant = preAcceptanceApplicant;
	}
	
	@Length(min=1, max=255, message="预验收申请审批意见长度必须介于 1 和 255 之间")
	public String getPreAcceptanceApprovalOpinions() {
		return preAcceptanceApprovalOpinions;
	}

	public void setPreAcceptanceApprovalOpinions(String preAcceptanceApprovalOpinions) {
		this.preAcceptanceApprovalOpinions = preAcceptanceApprovalOpinions;
	}
	
	public String getPreAcceptanceApplicationApprover() {
		return preAcceptanceApplicationApprover;
	}

	public void setPreAcceptanceApplicationApprover(String preAcceptanceApplicationApprover) {
		this.preAcceptanceApplicationApprover = preAcceptanceApplicationApprover;
	}
	
	public String getPreAcceptanceApprover() {
		return preAcceptanceApprover;
	}

	public void setPreAcceptanceApprover(String preAcceptanceApprover) {
		this.preAcceptanceApprover = preAcceptanceApprover;
	}
	
	@Length(min=0, max=20, message="预验收意见书编号长度必须介于 0 和 20 之间")
	public String getPreAcceptanceNo() {
		return preAcceptanceNo;
	}

	public void setPreAcceptanceNo(String preAcceptanceNo) {
		this.preAcceptanceNo = preAcceptanceNo;
	}
	
	@Length(min=1, max=64, message="流程环节状态长度必须介于 1 和 64 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	public Date getBeginPreAcceptanceApplyDate() {
		return beginPreAcceptanceApplyDate;
	}

	public void setBeginPreAcceptanceApplyDate(Date beginPreAcceptanceApplyDate) {
		this.beginPreAcceptanceApplyDate = beginPreAcceptanceApplyDate;
	}
	
	public Date getEndPreAcceptanceApplyDate() {
		return endPreAcceptanceApplyDate;
	}

	public void setEndPreAcceptanceApplyDate(Date endPreAcceptanceApplyDate) {
		this.endPreAcceptanceApplyDate = endPreAcceptanceApplyDate;
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

	public List<AmsAcceptanceArchives> getAmsAcceptanceArchivesList() {
		return amsAcceptanceArchivesList;
	}

	public void setAmsAcceptanceArchivesList(
			List<AmsAcceptanceArchives> amsAcceptanceArchivesList) {
		this.amsAcceptanceArchivesList = amsAcceptanceArchivesList;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getProjectString() {
		return projectString;
	}

	public void setProjectString(String projectString) {
		this.projectString = projectString;
	}

	public String getArchiverString() {
		return archiverString;
	}

	public void setArchiverString(String archiverString) {
		this.archiverString = archiverString;
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

	public String getAccString() {
		return accString;
	}

	public void setAccString(String accString) {
		this.accString = accString;
	}

	public String getLanString() {
		return lanString;
	}

	public void setLanString(String lanString) {
		this.lanString = lanString;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVideoMenuString() {
		return videoMenuString;
	}

	public void setVideoMenuString(String videoMenuString) {
		this.videoMenuString = videoMenuString;
	}

	public List<AmsArchiveMenu> getVideoMenuList() {
		return videoMenuList;
	}

	public void setVideoMenuList(List<AmsArchiveMenu> videoMenuList) {
		this.videoMenuList = videoMenuList;
	}

		
}