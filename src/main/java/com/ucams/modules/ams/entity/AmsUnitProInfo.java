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
 * 单位工程管理Entity
 * @author ws
 * @version 2017-06-26
 */
public class AmsUnitProInfo extends DataEntity<AmsUnitProInfo> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目外键
	@FieldMeta(name = " 单位工程编号")
	private String unitProjectNo;		// 单位工程编号
	@FieldMeta(name = " 单位工程地址")
	private String unitProjectAddress;
	@FieldMeta(name = "  单位工程名称")
	private String unitProjectName;		// 单位工程名称
	@FieldMeta(name = " 单位工程类型")
	private String unitProjectType;		// 单位工程类型
	@FieldMeta(name = " 规划许可证号")
	private String planningLicenseNumber;		// 规划许可证号
	@FieldMeta(name = " 施工许可证号")
	private String constructionPermitNumber;		// 施工许可证号
	@FieldMeta(name = " 开工日期")
	private Date startDate;		// 开工日期
	@FieldMeta(name = " 竣工日期")
	private Date finishDate;		// 竣工日期
	@FieldMeta(name = " 设计单位")
	private String designUnit;		// 设计单位
	@FieldMeta(name = " 勘察单位")
	private String explorationUnit;		// 勘察单位
	@FieldMeta(name = " 施工单位")
	private String contractor;		// 施工单位
	@FieldMeta(name = " 监理单位")
	private String supervisionUnit;		// 监理单位
	@FieldMeta(name = " X坐标")
	private String abscissa;		// X坐标
	@FieldMeta(name = " Y坐标")
	private String ordinate;		// Y坐标
	private String specialtyJson;		// 专业记载(著录)扩展信息
	private String transfreId;		// 预验收移交ID
	private String exten1;		// exten1
	private String exten2;		// exten2
	private String exten3;		// exten3
	private String exten4;		// exten4
	private String exten5;		// exten5
	private AmsAcceptance amsAcceptance;//预验收实体对象，取预验收status用于做页面操作判断
	private String landLeasingPeriod;		// 土地出让年限
	
	
	@Length(min=0, max=5, message="土地出让年限0-5")
	public String getLandLeasingPeriod() {
		return landLeasingPeriod;
	}

	public void setLandLeasingPeriod(String landLeasingPeriod) {
		this.landLeasingPeriod = landLeasingPeriod;
	}

	public AmsAcceptance getAmsAcceptance() {
		return amsAcceptance;
	}

	public void setAmsAcceptance(AmsAcceptance amsAcceptance) {
		this.amsAcceptance = amsAcceptance;
	}

	private List<String> ids = Lists.newArrayList();	// id集合
	
	private List<AmsDesExtend> amsDesExtendList = Lists.newArrayList();	
	
	public List<AmsDesExtend> getAmsDesExtendList() {
		return amsDesExtendList;
	}

	public void setAmsDesExtendList(List<AmsDesExtend> amsDesExtendList) {
		this.amsDesExtendList = amsDesExtendList;
	}

	public AmsUnitProInfo() {
		super();
	}

	public AmsUnitProInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="项目外键长度必须介于 0 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=0, max=20, message="单位工程编号长度必须介于 0 和 20 之间")
	public String getUnitProjectNo() {
		return unitProjectNo;
	}

	public void setUnitProjectNo(String unitProjectNo) {
		this.unitProjectNo = unitProjectNo;
	}
	
	@Length(min=0, max=100, message="单位工程名称长度必须介于 0 和 100 之间")
	public String getUnitProjectName() {
		return unitProjectName;
	}

	public void setUnitProjectName(String unitProjectName) {
		this.unitProjectName = unitProjectName;
	}
	

	@Length(min=0, max=500, message="单位工程类型长度必须介于 0 和 64之间")
	public String getUnitProjectType() {
		return unitProjectType;
	}

	public void setUnitProjectType(String unitProjectType) {
		this.unitProjectType = unitProjectType;
	}
	
	@Length(min=0, max=20, message="规划许可证号长度必须介于 0 和 20 之间")
	public String getPlanningLicenseNumber() {
		return planningLicenseNumber;
	}

	public void setPlanningLicenseNumber(String planningLicenseNumber) {
		this.planningLicenseNumber = planningLicenseNumber;
	}
	
	@Length(min=0, max=20, message="施工许可证号长度必须介于 0 和 20 之间")
	public String getConstructionPermitNumber() {
		return constructionPermitNumber;
	}

	public void setConstructionPermitNumber(String constructionPermitNumber) {
		this.constructionPermitNumber = constructionPermitNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
	@Length(min=0, max=50, message="设计单位长度必须介于 0 和 50 之间")
	public String getDesignUnit() {
		return designUnit;
	}

	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}
	
	@Length(min=0, max=50, message="勘察单位长度必须介于 0 和 50 之间")
	public String getExplorationUnit() {
		return explorationUnit;
	}

	public void setExplorationUnit(String explorationUnit) {
		this.explorationUnit = explorationUnit;
	}
	
	@Length(min=0, max=50, message="施工单位长度必须介于 0 和 50 之间")
	public String getContractor() {
		return contractor;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
	
	@Length(min=0, max=50, message="监理单位长度必须介于 0 和 50 之间")
	public String getSupervisionUnit() {
		return supervisionUnit;
	}

	public void setSupervisionUnit(String supervisionUnit) {
		this.supervisionUnit = supervisionUnit;
	}
	
	public String getAbscissa() {
		return abscissa;
	}

	public void setAbscissa(String abscissa) {
		this.abscissa = abscissa;
	}
	
	public String getOrdinate() {
		return ordinate;
	}

	public void setOrdinate(String ordinate) {
		this.ordinate = ordinate;
	}
	
	public String getSpecialtyJson() {
		return specialtyJson;
	}

	public void setSpecialtyJson(String specialtyJson) {
		this.specialtyJson = specialtyJson;
	}
	
	@Length(min=0, max=64, message="预验收移交ID长度必须介于 0 和 64 之间")
	public String getTransfreId() {
		return transfreId;
	}

	public void setTransfreId(String transfreId) {
		this.transfreId = transfreId;
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

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public String getUnitProjectAddress() {
		return unitProjectAddress;
	}
	public void setUnitProjectAddress(String unitProjectAddress) {
		this.unitProjectAddress = unitProjectAddress;
	}

	
}