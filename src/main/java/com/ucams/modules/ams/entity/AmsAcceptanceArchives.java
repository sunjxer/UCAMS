/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import org.hibernate.validator.constraints.Length;

import com.ucams.common.persistence.DataEntity;

/**
 * 预验收案卷Entity
 * @author zkx
 * @version 2017-07-18
 */
public class AmsAcceptanceArchives extends DataEntity<AmsAcceptanceArchives> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目主键
	private String acceptanceId;		// 预验收主键
	private String unitProjectId;		// 单位工程主键
	private String archiveId;		// 案卷主键
	private AmsUnitProInfo amsUnitProInfo;		// 单位工程主键
	private AmsArchivesInfo amsArchivesInfo;		// 案卷主键
	private AmsConstructDes amsConstructDes;		// 建设工程规划
	private AmsLandDes amsLandDes;		//建设用地规划
	private AmsArchiveMenu amsArchiveMenu;		//声像归档目录
	
	public AmsAcceptanceArchives() {
		super();
	}

	public AmsAcceptanceArchives(String id){
		super(id);
	}
	@Length(min=1, max=64, message="项目主键长度必须介于 1 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Length(min=1, max=64, message="预验收主键长度必须介于 1 和 64 之间")
	public String getAcceptanceId() {
		return acceptanceId;
	}

	public void setAcceptanceId(String acceptanceId) {
		this.acceptanceId = acceptanceId;
	}
	
	@Length(min=1, max=64, message="单位工程主键长度必须介于 1 和 64 之间")
	public String getUnitProjectId() {
		return unitProjectId;
	}

	public void setUnitProjectId(String unitProjectId) {
		this.unitProjectId = unitProjectId;
	}
	
	@Length(min=1, max=64, message="案卷主键长度必须介于 1 和 64 之间")
	public String getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}

	public AmsUnitProInfo getAmsUnitProInfo() {
		return amsUnitProInfo;
	}

	public void setAmsUnitProInfo(AmsUnitProInfo amsUnitProInfo) {
		this.amsUnitProInfo = amsUnitProInfo;
	}

	public AmsArchivesInfo getAmsArchivesInfo() {
		return amsArchivesInfo;
	}

	public void setAmsArchivesInfo(AmsArchivesInfo amsArchivesInfo) {
		this.amsArchivesInfo = amsArchivesInfo;
	}

	public AmsConstructDes getAmsConstructDes() {
		return amsConstructDes;
	}

	public void setAmsConstructDes(AmsConstructDes amsConstructDes) {
		this.amsConstructDes = amsConstructDes;
	}

	public AmsLandDes getAmsLandDes() {
		return amsLandDes;
	}

	public void setAmsLandDes(AmsLandDes amsLandDes) {
		this.amsLandDes = amsLandDes;
	}

	public AmsArchiveMenu getAmsArchiveMenu() {
		return amsArchiveMenu;
	}

	public void setAmsArchiveMenu(AmsArchiveMenu amsArchiveMenu) {
		this.amsArchiveMenu = amsArchiveMenu;
	}

	
	
}