/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.ucams.common.persistence.DataEntity;
import com.ucams.modules.ams.utils.FieldMeta;

/**
 * 文件组卷Entity
 * @author zkx
 * @version 2017-06-28
 */
public class AmsFileInfo extends DataEntity<AmsFileInfo> {
	
	private static final long serialVersionUID = 1L;
	private AmsArchivesFiles amsArchivesFiles;
	private AmsGenre amsGenre;      //文件题名
	private String projectId;		// 工程外键
	private String unitProjectId;		// 单位工程外键
	private String groupId;		// 案卷外键
	private String recordId;		// 归档一览外键
	@FieldMeta(name = " 文件名")
	private String fileName;		// 文件名
	@FieldMeta(name = " 文图号")
	private String fileNo;		// 文图号
	@FieldMeta(name = " 责任者")
	private String author;		// 责任者
	@FieldMeta(name = " 形成日期")
	private Date formDate;		// 形成日期
	@FieldMeta(name = "文件份数")
	private String filecount;		// 文件份数
	@FieldMeta(name = "文件存储路径")
	private String filePath;		// 文件存储路径
	@FieldMeta(name = "文件类型")
	private String fileType;		// 文件类型
	@FieldMeta(name = "数据来源")
	private String filesource;		// 数据来源
	@FieldMeta(name = "加密锁序列号")
	private String dongle;		// 加密锁序列号
	@FieldMeta(name = " 状态")
	private String state;		// 状态
	private String fileJson;		// 文件著录扩展json
	private String structureJson;		// 结构化数据
	private String exten1;		// exten1
	private String exten2;		// exten2
	private String exten3;		// exten3
	private String exten4;		// exten4
	private String exten5;		// exten5
	private String recordFileName;		// 案卷题名
	private List<AmsDesExtend> amsDesExtendList = Lists.newArrayList();	
	private List<AmsArchiveRules> amsArchiveRulesList = Lists.newArrayList();	
	
	public AmsFileInfo() {
		super();
	}

	public AmsFileInfo(String id){
		super(id);
	}
	@Length(min=0, max=64, message="工程外键长度必须介于 0 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Length(min=0, max=64, message="单位工程外键长度必须介于 0 和 64 之间")
	public String getUnitProjectId() {
		return unitProjectId;
	}

	public AmsGenre getAmsGenre() {
		return amsGenre;
	}

	public void setAmsGenre(AmsGenre amsGenre) {
		this.amsGenre = amsGenre;
	}

	public void setUnitProjectId(String unitProjectId) {
		this.unitProjectId = unitProjectId;
	}
	
	@Length(min=0, max=64, message="案卷外键长度必须介于 0 和 64 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@Length(min=0, max=64, message="归档一览外键长度必须介于 0 和 64 之间")
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	@Length(min=0, max=100, message="文件名长度必须介于 0 和 100 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=30, message="文图号长度必须介于 0 和 30 之间")
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	@Length(min=0, max=64, message="责任者长度必须介于 0 和 64 之间")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFormDate() {
		return formDate;
	}

	public void setFormDate(Date formDate) {
		this.formDate = formDate;
	}
	
	@Length(min=0, max=11, message="文件份数长度必须介于 0 和 11 之间")
	public String getFilecount() {
		return filecount;
	}

	public void setFilecount(String filecount) {
		this.filecount = filecount;
	}
	
	@Length(min=0, max=255, message="文件存储路径长度必须介于 0 和 255 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Length(min=0, max=64, message="文件类型长度必须介于 0 和 64 之间")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Length(min=0, max=50, message="数据来源长度必须介于 0 和 50 之间")
	public String getFilesource() {
		return filesource;
	}

	public void setFilesource(String filesource) {
		this.filesource = filesource;
	}
	
	@Length(min=0, max=20, message="加密锁序列号长度必须介于 0 和 20 之间")
	public String getDongle() {
		return dongle;
	}

	public void setDongle(String dongle) {
		this.dongle = dongle;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getFileJson() {
		return fileJson;
	}

	public void setFileJson(String fileJson) {
		this.fileJson = fileJson;
	}
	
	public String getStructureJson() {
		return structureJson;
	}

	public void setStructureJson(String structureJson) {
		this.structureJson = structureJson;
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

	public String getRecordFileName() {
		return recordFileName;
	}

	public void setRecordFileName(String recordFileName) {
		this.recordFileName = recordFileName;
	}
	
	public List<AmsDesExtend> getAmsDesExtendList() {
		return amsDesExtendList;
	}

	public void setAmsDesExtendList(List<AmsDesExtend> amsDesExtendList) {
		this.amsDesExtendList = amsDesExtendList;
	}

	public AmsArchivesFiles getAmsArchivesFiles() {
		return amsArchivesFiles;
	}

	public void setAmsArchivesFiles(AmsArchivesFiles amsArchivesFiles) {
		this.amsArchivesFiles = amsArchivesFiles;
	}

	public List<AmsArchiveRules> getAmsArchiveRulesList() {
		return amsArchiveRulesList;
	}

	public void setAmsArchiveRulesList(List<AmsArchiveRules> amsArchiveRulesList) {
		this.amsArchiveRulesList = amsArchiveRulesList;
	}
	
}