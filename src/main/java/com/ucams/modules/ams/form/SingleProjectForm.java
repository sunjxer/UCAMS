package com.ucams.modules.ams.form;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.ucams.common.utils.DateUtils;

/**
 * 单位工程信息form
 * 
 * @author BQL
 * @version 创建时间：2017年7月7日 上午11:05:48
 */
public class SingleProjectForm {

	private String id;
	private String unitProjectName;		// 单位工程名称
//	private String unitProjectAddress;		// 单位工程地址
	private String unitProjectType;		// 单位工程类型
//	private String planningLicenseNumber;		// 规划许可证号
	private String constructionPermitNumber;		// 施工许可证号
	@JSONField(format="yyyy-MM-dd")
	private Date startDate;		// 开工日期
	@JSONField(format="yyyy-MM-dd")
	private Date finishDate;		// 竣工日期
	private String designUnit;		// 设计单位
	private String explorationUnit;		// 勘察单位
	private String contractor;		// 施工单位
	private String supervisionUnit;		// 监理单位
	private String abscissa;		// X坐标
//	private String ordinate;		// Y坐标
	private String specialtyJson;		// 专业记载(著录)扩展信息
	private String transfreId;		// 预验收移交ID


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnitProjectName() {
		return unitProjectName;
	}

	public void setUnitProjectName(String unitProjectName) {
		this.unitProjectName = unitProjectName;
	}

	public String getUnitProjectType() {
		return unitProjectType;
	}

	public void setUnitProjectType(String unitProjectType) {
		this.unitProjectType = unitProjectType;
	}

	public String getConstructionPermitNumber() {
		return constructionPermitNumber;
	}

	public void setConstructionPermitNumber(String constructionPermitNumber) {
		this.constructionPermitNumber = constructionPermitNumber;
	}

	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getDesignUnit() {
		return designUnit;
	}

	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}

	public String getExplorationUnit() {
		return explorationUnit;
	}

	public void setExplorationUnit(String explorationUnit) {
		this.explorationUnit = explorationUnit;
	}

	public String getContractor() {
		return contractor;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}

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

	public String getSpecialtyJson() {
		return specialtyJson;
	}

	public void setSpecialtyJson(String specialtyJson) {
		this.specialtyJson = specialtyJson;
	}

	public String getTransfreId() {
		return transfreId;
	}

	public void setTransfreId(String transfreId) {
		this.transfreId = transfreId;
	}

	
}
