/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.ucams.common.persistence.DataEntity;

/**
 * 系统规则管理Entity
 * @author gyl
 * @version 2017-06-26
 */
public class SysBaseInfo extends DataEntity<SysBaseInfo> {
	
	private static final long serialVersionUID = 1L;
	private String preUnitprojectno;  //单位工程编号前缀
	private String preProjectno;	  //项目编号前缀
	private String archivesType;		// 组卷方式
	private String archivesPreno;		// 档号前缀
	private String archiveBeginno;		// 档号起始号
	private String preAcceptancePreno;		// 预验收意见书编号前缀
	private String preAcceptanceBeginno;		// 预验收意见书起始编号
	private String transferPreno;		// 移交证明书编号前缀
	private String transferBeginno;		// 移交证明书起始编号
	private String fileDes;		// 文件级是否著录
	private String constructDes;		// 建设工程规划是否著录
	private String landDes;		// 建设用地规划是否著录
	private String catalog;		//卷内目录设置规则
	private String collectionType;		//馆藏档案电子化设置
	private String exten1;		// 扩展1
	private String exten2;		// 扩展2
	private String exten3;		// 扩展3
	private String exten4;		// 扩展4
	private String exten5;		// 扩展5
	
	public SysBaseInfo() {
		super();
	}

	public SysBaseInfo(String id){
		super(id);
	}

	
	
	@Length(min=1, max=10, message="单位工程编号前缀长度必须介于 1 和 10 之间")
	public String getPreUnitprojectno() {
		return preUnitprojectno;
	}

	public void setPreUnitprojectno(String preUnitprojectno) {
		this.preUnitprojectno = preUnitprojectno;
	}
	@Length(min=1, max=10, message="项目编号前缀长度必须介于 1 和 10 之间")
	public String getPreProjectno() {
		return preProjectno;
	}

	public void setPreProjectno(String preProjectno) {
		this.preProjectno = preProjectno;
	}

	@Length(min=1, max=1, message="组卷方式长度必须介于 1 和 1 之间")
	public String getArchivesType() {
		return archivesType;
	}

	public void setArchivesType(String archivesType) {
		this.archivesType = archivesType;
	}
	
	@Length(min=1, max=10, message="档号前缀长度必须介于 1 和 10 之间")
	public String getArchivesPreno() {
		return archivesPreno;
	}

	public void setArchivesPreno(String archivesPreno) {
		this.archivesPreno = archivesPreno;
	}
	
	@Length(min=1, max=5, message="档号起始号长度必须介于 1 和 5 之间")
	public String getArchiveBeginno() {
		return archiveBeginno;
	}

	public void setArchiveBeginno(String archiveBeginno) {
		this.archiveBeginno = archiveBeginno;
	}
	
	@Length(min=1, max=10, message="预验收意见书编号前缀长度必须介于 1 和 10 之间")
	public String getPreAcceptancePreno() {
		return preAcceptancePreno;
	}

	public void setPreAcceptancePreno(String preAcceptancePreno) {
		this.preAcceptancePreno = preAcceptancePreno;
	}
	
	@Length(min=1, max=5, message="预验收意见书起始编号长度必须介于 1 和 5 之间")
	public String getPreAcceptanceBeginno() {
		return preAcceptanceBeginno;
	}

	public void setPreAcceptanceBeginno(String preAcceptanceBeginno) {
		this.preAcceptanceBeginno = preAcceptanceBeginno;
	}
	
	@Length(min=1, max=10, message="移交证明书编号前缀长度必须介于 1 和 10 之间")
	public String getTransferPreno() {
		return transferPreno;
	}

	public void setTransferPreno(String transferPreno) {
		this.transferPreno = transferPreno;
	}
	
	@Length(min=1, max=5, message="移交证明书起始编号长度必须介于 1 和 5 之间")
	public String getTransferBeginno() {
		return transferBeginno;
	}

	public void setTransferBeginno(String transferBeginno) {
		this.transferBeginno = transferBeginno;
	}
	
	@Length(min=1, max=1, message="文件级是否著录长度必须介于 1 和 1 之间")
	public String getFileDes() {
		return fileDes;
	}

	public void setFileDes(String fileDes) {
		this.fileDes = fileDes;
	}
	
	@Length(min=1, max=1, message="建设工程规划是否著录长度必须介于 1 和 1 之间")
	public String getConstructDes() {
		return constructDes;
	}

	public void setConstructDes(String constructDes) {
		this.constructDes = constructDes;
	}
	
	@Length(min=1, max=1, message="建设用地规划是否著录长度必须介于 1 和 1 之间")
	public String getLandDes() {
		return landDes;
	}

	public void setLandDes(String landDes) {
		this.landDes = landDes;
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

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	
}