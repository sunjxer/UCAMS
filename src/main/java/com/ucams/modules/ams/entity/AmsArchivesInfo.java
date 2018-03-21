/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.ucams.common.annotation.FieldName;
import com.ucams.common.persistence.DataEntity;
import com.ucams.modules.ams.utils.FieldMeta;

/**
 * 组卷设置Entity
 * 
 * @author gyl
 * @version 2017-07-10
 */
public class AmsArchivesInfo extends DataEntity<AmsArchivesInfo> {

	private static final long serialVersionUID = 1L;
	private AmsFileInfo amsFileInfo;
	private AmsAcceptance amsAcceptance;
	private String projectId; // 项目主键
	private String unitProjectId; // 单位工程主键
	@FieldMeta(name = " 档号")
	private String archivesCode; // 档号
	@FieldMeta(name = " 案卷题名")
	private String archivesName; // 案卷题名
	@FieldMeta(name = " 编制单位")
	private String makeUnit; // 编制单位
	@FieldMeta(name = " 载体类型")
	private String carrierType; // 载体类型
	@FieldMeta(name = " 数量")
	private String filesCount; // 数量
	@FieldMeta(name = " 卷内文件起始时间")
	private Date startDate; // 卷内文件起始时间
	@FieldMeta(name = " 卷内文件终止时间")
	private Date endDate; // 卷内文件终止时间
	@FieldMeta(name = "  立卷人")
	private String author; // 立卷人
	@FieldMeta(name = " 立卷日期")
	private Date makeDate; // 立卷日期
	@FieldMeta(name = " 审核人")
	private String auditor; // 审核人
	@FieldMeta(name = " 审核日期")
	private Date auditDate; // 审核日期
	@FieldMeta(name = " 保管期限")
	private String storagePeriod; // 保管期限
	@FieldMeta(name = " 密级")
	private String degreeSecrets; // 密级
	@FieldMeta(name = " 主题词")
	private String mainTitle; // 主题词
	@FieldMeta(name = " 组卷情况说明")
	private String archivesExplain; // 组卷情况说明
	private String archivesJson; // 组卷著录扩展信息
	private String exten1; // 扩展1
	private String exten2; // 扩展2
	private String exten3; // 扩展3
	private String exten4; // 扩展4
	private String exten5; // 扩展5
	private String acceptanceId; // 预验收ID
	private List<String> ids = Lists.newArrayList(); // id集合
	private List<AmsDesExtend> amsDesExtendList = Lists.newArrayList();
	private String catalogType;  //案卷目录类型

	public AmsArchivesInfo() {
		super();
	}

	public AmsArchivesInfo(String id) {
		super(id);
	}
	
	@Length(min = 1, max = 64, message = "项目主键长度必须介于 1 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Length(min = 0, max = 64, message = "单位工程主键长度必须介于 0 和 64 之间")
	public String getUnitProjectId() {
		return unitProjectId;
	}

	public void setUnitProjectId(String unitProjectId) {
		this.unitProjectId = unitProjectId;
	}

	@Length(min = 0, max = 50, message = "档号长度必须介于 0 和 50 之间")
	public String getArchivesCode() {
		return archivesCode;
	}

	public void setArchivesCode(String archivesCode) {
		this.archivesCode = archivesCode;
	}

	@Length(min = 0, max = 100, message = "案卷题名长度必须介于 0 和 100 之间")
	public String getArchivesName() {
		return archivesName;
	}

	public void setArchivesName(String archivesName) {
		this.archivesName = archivesName;
	}

	@Length(min = 0, max = 50, message = "编制单位长度必须介于 0 和 50 之间")
	public String getMakeUnit() {
		return makeUnit;
	}

	public void setMakeUnit(String makeUnit) {
		this.makeUnit = makeUnit;
	}

	@Length(min = 0, max = 64, message = "载体类型长度必须介于 0 和 64 之间")
	public String getCarrierType() {
		return carrierType;
	}

	public void setCarrierType(String carrierType) {
		this.carrierType = carrierType;
	}

	@Length(min = 0, max = 11, message = "数量长度必须介于 0 和 11 之间")
	public String getFilesCount() {
		return filesCount;
	}

	public void setFilesCount(String filesCount) {
		this.filesCount = filesCount;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Length(min = 0, max = 20, message = "立卷人长度必须介于 0 和 20 之间")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	@Length(min = 0, max = 100, message = "审核人长度必须介于 0 和 100 之间")
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	@Length(min = 0, max = 64, message = "保管期限长度必须介于 0 和 64 之间")
	public String getStoragePeriod() {
		return storagePeriod;
	}

	public void setStoragePeriod(String storagePeriod) {
		this.storagePeriod = storagePeriod;
	}

	@Length(min = 0, max = 64, message = "密级长度必须介于 0 和 64 之间")
	public String getDegreeSecrets() {
		return degreeSecrets;
	}

	public void setDegreeSecrets(String degreeSecrets) {
		this.degreeSecrets = degreeSecrets;
	}

	@Length(min = 0, max = 200, message = "主题词长度必须介于 0 和 200 之间")
	public String getMainTitle() {
		return mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	@Length(min = 0, max = 500, message = "组卷情况说明长度必须介于 0 和 500 之间")
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

	@Length(min = 0, max = 100, message = "扩展1长度必须介于 0 和 100 之间")
	public String getExten1() {
		return exten1;
	}

	public void setExten1(String exten1) {
		this.exten1 = exten1;
	}

	@Length(min = 0, max = 100, message = "扩展2长度必须介于 0 和 100 之间")
	public String getExten2() {
		return exten2;
	}

	public void setExten2(String exten2) {
		this.exten2 = exten2;
	}

	@Length(min = 0, max = 100, message = "扩展3长度必须介于 0 和 100 之间")
	public String getExten3() {
		return exten3;
	}

	public void setExten3(String exten3) {
		this.exten3 = exten3;
	}

	@Length(min = 0, max = 100, message = "扩展4长度必须介于 0 和 100 之间")
	public String getExten4() {
		return exten4;
	}

	public void setExten4(String exten4) {
		this.exten4 = exten4;
	}

	@Length(min = 0, max = 100, message = "扩展5长度必须介于 0 和 100 之间")
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

	public AmsFileInfo getAmsFileInfo() {
		return amsFileInfo;
	}

	public void setAmsFileInfo(AmsFileInfo amsFileInfo) {
		this.amsFileInfo = amsFileInfo;
	}


	public List<AmsDesExtend> getAmsDesExtendList() {
		return amsDesExtendList;
	}

	public void setAmsDesExtendList(List<AmsDesExtend> amsDesExtendList) {
		this.amsDesExtendList = amsDesExtendList;
	}


	public String getAcceptanceId() {
		return acceptanceId;
	}

	public void setAcceptanceId(String acceptanceId) {
		this.acceptanceId = acceptanceId;
	}

	public AmsAcceptance getAmsAcceptance() {
		return amsAcceptance;
	}

	public void setAmsAcceptance(AmsAcceptance amsAcceptance) {
		this.amsAcceptance = amsAcceptance;
	}

	public String getCatalogType() {
		return catalogType;
	}

	public void setCatalogType(String catalogType) {
		this.catalogType = catalogType;
	}


}