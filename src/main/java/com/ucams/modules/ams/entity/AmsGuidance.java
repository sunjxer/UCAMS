/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.ucams.common.persistence.ActEntity;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.User;

/**
 * 业务指导内容管理Entity
 * @author gyl
 * @version 2017-06-26
 */
public class AmsGuidance extends ActEntity<AmsGuidance> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;	// 流程实例id
	private Office project;     //项目信息
	private String projectid;	// 项目外键
	private String address;		// 项目地址
	private Date expectDate;	// 期望指导日期
	private String liaisons;	// 联系人
	private String telphone;	// 联系电话
	private String content;		// 申请内容
	private String opinion;		// 批复意见
	private String guidance;	// 指导内容
	private Date guidanceDate;	// 指导日期
	private String exten1;		// 扩展1
	private String exten2;		// 扩展2
	private String exten3;		// 扩展3
	private String exten4;		// 扩展4
	private String exten5;		// 扩展5
	private List<String> ids = Lists.newArrayList();	//项目id集合
	private User user;
	private long count;
	
	public AmsGuidance() {
		super();
	}

	public AmsGuidance(String id){
		super(id);
	}

	@Length(min=1, max=64, message="流程实例id长度必须介于 1 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=64, message="项目外键长度必须介于 1 和 64 之间")
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
	@Length(min=1, max=200, message="项目地址长度必须介于 1 和 200 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="期望指导日期不能为空")
	public Date getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(Date expectDate) {
		this.expectDate = expectDate;
	}
	
	@Length(min=1, max=20, message="联系人长度必须介于 1 和 20 之间")
	public String getLiaisons() {
		return liaisons;
	}

	public void setLiaisons(String liaisons) {
		this.liaisons = liaisons;
	}
	
	@Length(min=1, max=20, message="联系电话长度必须介于 1 和 20 之间")
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
	@Length(min=0, max=1000, message="申请内容长度必须介于 0 和 1000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1000, message="批复意见长度必须介于 0 和 1000 之间")
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	@Length(min=0, max=1000, message="指导内容长度必须介于 0 和 1000 之间")
	public String getGuidance() {
		return guidance;
	}

	public void setGuidance(String guidance) {
		this.guidance = guidance;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGuidanceDate() {
		return guidanceDate;
	}

	public void setGuidanceDate(Date guidanceDate) {
		this.guidanceDate = guidanceDate;
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
	
	public Office getProject() {
		return project;
	}

	public void setProject(Office project) {
		this.project = project;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
	
}