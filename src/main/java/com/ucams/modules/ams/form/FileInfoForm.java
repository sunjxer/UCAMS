/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.form;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.ucams.modules.ams.entity.AmsDesExtend;

/**
 * 文件Form
 * @author BQL
 * @version 2017-06-28
 */
public class FileInfoForm{
	
	private String userName;		//用户名
	private String password;		//密码
	private String id;				//文件ID
	private String projectId;		// 项目外键
	private String unitProjectId;		// 单位工程外键
	private String groupId;		// 案卷外键
	private String recordId;		// 归档一览外键
	private String fileName;		// 文件名
	private String fileNo;		// 文图号
	private String author;		// 责任者
	@JSONField(format="yyyy-MM-dd")
	private Date formDate;		// 形成日期  (年-月-日  时：分：秒)
	private String filecount;		// 文件份数
	private String fileType;		// 文件类型
	private String dongle;		// 加密锁序列号
	private String extendJson;		// 文件著录扩展json
	
	private String createUser;		//创建者
	private String filePath;		// 文件存储路径
	
	private int sort;   //排序
	private int pageCount;  //页数
	private String startPage;  //起始页
	private String endPage;   //结束页
	public FileInfoForm() {
		super();
	}
 
	
	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

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
	
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	

	public Date getFormDate() {
		return formDate;
	}


	public void setFormDate(Date formDate) {
		this.formDate = formDate;
	}


	public String getCreateUser() {
		return createUser;
	}


	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public String getFilecount() {
		return filecount;
	}

	public void setFilecount(String filecount) {
		this.filecount = filecount;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getDongle() {
		return dongle;
	}

	public void setDongle(String dongle) {
		this.dongle = dongle;
	}

	public String getExtendJson() {
		return extendJson;
	}

	public void setExtendJson(String extendJson) {
		this.extendJson = extendJson;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getStartPage() {
		return startPage;
	}


	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}


	public String getEndPage() {
		return endPage;
	}


	public void setEndPage(String endPage) {
		this.endPage = endPage;
	}


	public int getSort() {
		return sort;
	}


	public void setSort(int sort) {
		this.sort = sort;
	}


	public int getPageCount() {
		return pageCount;
	}


	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
}