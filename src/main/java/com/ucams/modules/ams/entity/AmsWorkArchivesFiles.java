/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.ucams.common.persistence.DataEntity;

/**
 * 业务管理档案案卷文件Entity
 * @author dyk
 * @version 2017-12-13
 */
public class AmsWorkArchivesFiles extends DataEntity<AmsWorkArchivesFiles> {
	
	private static final long serialVersionUID = 1L;
	private String groupId;		// 案卷外键
	private String fileName;		// 文件名
	private String fileNo;		// 文图号
	private String author;		// 责任者
	private Date formDate;		// 形成日期
	private String filecount;		// 文件份数
	private String filePath;		// 文件存储路径
	private String fileType;		// 文件类型
	private String uploadType;		// 0: 资料 1.声像
	private String status;		// 0-初始状态，1-预验收通过，2-已移交
	private String fileJson;		// 文件著录扩展json
	private String exten1;		// 签章状态 -1:文档不存在数字签名 1:所有签名有效 2:所有签名有效，最后一次签名后追加了内容 3: 至少有一个签名是无效的
	private String exten2;		// exten2
	private String exten3;		// exten3
	private String exten4;		// exten4
	private String exten5;		// exten5
	
	public AmsWorkArchivesFiles() {
		super();
	}

	public AmsWorkArchivesFiles(String id){
		super(id);
	}

	@Length(min=0, max=64, message="案卷外键长度必须介于 0 和 64 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	
	@Length(min=0, max=2, message="声像长度必须介于 0 和 2 之间")
	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	
	@Length(min=0, max=1, message="0-初始状态，1-预验收通过，2-已移交长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getFileJson() {
		return fileJson;
	}

	public void setFileJson(String fileJson) {
		this.fileJson = fileJson;
	}
	
	@Length(min=0, max=100, message="签章状态 -1:文档不存在数字签名 1:所有签名有效 2:所有签名有效，最后一次签名后追加了内容 3: 至少有一个签名是无效的长度必须介于 0 和 100 之间")
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
	
}