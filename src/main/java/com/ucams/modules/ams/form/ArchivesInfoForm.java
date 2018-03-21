/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.form;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;




/**
 * 组卷设置Form
 * @author BQL
 * @version 2017-07-10
 */
public class ArchivesInfoForm {
	
	private String id;
	private String unitProjectId;		// 单位工程主键
	private String archivesCode;		// 档号
	private String archivesName;		// 案卷题名
	private String makeUnit;		// 编制单位
	private String carrierType;		// 载体类型
	private String filesCount;		// 数量
	@JSONField(format="yyyy-MM-dd")
	private Date startDate;		// 卷内文件起始时间
	@JSONField(format="yyyy-MM-dd")
	private Date endDate;		// 卷内文件终止时间
	private String author;		// 立卷人
	@JSONField(format="yyyy-MM-dd")
	private Date makeDate;		// 立卷日期
	private String auditor;		// 审核人
	@JSONField(format="yyyy-MM-dd")
	private Date auditDate;		// 审核日期
	private String storagePeriod;		// 保管期限
	private String degreeSecrets;		// 密级
	private String mainTitle;		// 主题词
	private String archivesExplain;		// 组卷情况说明
	private String archivesJson;		// 组卷著录扩展信息
	private String exten1;		// 扩展1
	private String exten2;		// 扩展2
	private String exten3;		// 扩展3
	private String exten4;		// 扩展4
	private String exten5;		// 扩展5
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnitProjectId() {
		return unitProjectId;
	}
	public void setUnitProjectId(String unitProjectId) {
		this.unitProjectId = unitProjectId;
	}
	public String getArchivesCode() {
		return archivesCode;
	}
	public void setArchivesCode(String archivesCode) {
		this.archivesCode = archivesCode;
	}
	public String getArchivesName() {
		return archivesName;
	}
	public void setArchivesName(String archivesName) {
		this.archivesName = archivesName;
	}
	public String getMakeUnit() {
		return makeUnit;
	}
	public void setMakeUnit(String makeUnit) {
		this.makeUnit = makeUnit;
	}
	public String getCarrierType() {
		return carrierType;
	}
	public void setCarrierType(String carrierType) {
		this.carrierType = carrierType;
	}
	public String getFilesCount() {
		return filesCount;
	}
	public void setFilesCount(String filesCount) {
		this.filesCount = filesCount;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getStoragePeriod() {
		return storagePeriod;
	}
	public void setStoragePeriod(String storagePeriod) {
		this.storagePeriod = storagePeriod;
	}
	public String getDegreeSecrets() {
		return degreeSecrets;
	}
	public void setDegreeSecrets(String degreeSecrets) {
		this.degreeSecrets = degreeSecrets;
	}
	public String getMainTitle() {
		return mainTitle;
	}
	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}
	public String getArchivesExplain() {
		return archivesExplain;
	}
	public void setArchivesExplain(String archivesExplain) {
		this.archivesExplain = archivesExplain;
	}
	public String getArchivesJson() {
		return archivesJson;
	}
	public void setArchivesJson(String archivesJson) {
		this.archivesJson = archivesJson;
	}
	public String getExten1() {
		return exten1;
	}
	public void setExten1(String exten1) {
		this.exten1 = exten1;
	}
	public String getExten2() {
		return exten2;
	}
	public void setExten2(String exten2) {
		this.exten2 = exten2;
	}
	public String getExten3() {
		return exten3;
	}
	public void setExten3(String exten3) {
		this.exten3 = exten3;
	}
	public String getExten4() {
		return exten4;
	}
	public void setExten4(String exten4) {
		this.exten4 = exten4;
	}
	public String getExten5() {
		return exten5;
	}
	public void setExten5(String exten5) {
		this.exten5 = exten5;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
}