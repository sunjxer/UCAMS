/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import org.hibernate.validator.constraints.Length;

import com.ucams.common.persistence.DataEntity;

/**
 * 移交案卷Entity
 * @author zkx
 * @version 2017-07-25
 */
public class AmsTransferArchives extends DataEntity<AmsTransferArchives> {
	
	private static final long serialVersionUID = 1L;
	private String projectid;		// 项目主键
	private String transferId;		// 移交主键
	private AmsAcceptance acceptance;	// 预验收
	private String unitProjectId;		// 单位工程主键
	private String archiveId;		// 案卷主键
	
	public AmsTransferArchives() {
		super();
	}

	public AmsTransferArchives(String id){
		super(id);
	}

	@Length(min=0, max=64, message="项目主键长度必须介于 0 和 64 之间")
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
	@Length(min=0, max=64, message="移交主键长度必须介于 0 和 64 之间")
	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	
	
	public AmsAcceptance getAcceptance() {
		return acceptance;
	}

	public void setAcceptance(AmsAcceptance acceptance) {
		this.acceptance = acceptance;
	}

	@Length(min=0, max=64, message="单位工程主键长度必须介于 0 和 64 之间")
	public String getUnitProjectId() {
		return unitProjectId;
	}

	public void setUnitProjectId(String unitProjectId) {
		this.unitProjectId = unitProjectId;
	}
	
	@Length(min=0, max=64, message="案卷主键长度必须介于 0 和 64 之间")
	public String getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}
	
}