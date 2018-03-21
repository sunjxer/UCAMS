/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.ucams.common.persistence.DataEntity;

/**
 * 业务管理档案案卷Entity
 * @author dyk
 * @version 2017-12-13
 */
public class AmsWorkArchives extends DataEntity<AmsWorkArchives> {
	
	private static final long serialVersionUID = 1L;
	private String archivesCode;		// archives_code
	private String warehouseId;		// warehouse_id
	private String cell;		// cell
	private String cabinet;		// cabinet
	private String layer;		// layer
	private String archivesName;		// archives_name
	private String makeUnit;		// make_unit
	private String carrierType;		// carrier_type
	private String filesCount;		// （案卷内文件的数量计算）
	private Date startDate;		// start_date
	private Date endDate;		// end_date
	private Date makeDate;		// make_date
	private String year;		// year
	private String storagePeriod;		// storage_period
	private String degreeSecrets;		// degree_secrets
	private String mainTitle;		// main_title
	private String archivesExplain;		// archives_explain
	private String archivesJson;		// archives_json
	private String exten1;		// 案卷类型 0：普通案卷；1：声像案卷
	private String exten2;		// exten2
	private String exten3;		// exten3
	private String exten4;		// exten4
	private String exten5;		// exten5
	//files
	private AmsWorkArchivesFiles amsWorkArchivesFiles;
	
	public AmsWorkArchives() {
		super();
	}

	public AmsWorkArchives(String id){
		super(id);
	}

	@Length(min=0, max=50, message="archives_code长度必须介于 0 和 50 之间")
	public String getArchivesCode() {
		return archivesCode;
	}

	public void setArchivesCode(String archivesCode) {
		this.archivesCode = archivesCode;
	}
	
	@Length(min=0, max=64, message="warehouse_id长度必须介于 0 和 64 之间")
	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	@Length(min=0, max=20, message="cell长度必须介于 0 和 20 之间")
	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}
	
	@Length(min=0, max=20, message="cabinet长度必须介于 0 和 20 之间")
	public String getCabinet() {
		return cabinet;
	}

	public void setCabinet(String cabinet) {
		this.cabinet = cabinet;
	}
	
	@Length(min=0, max=20, message="layer长度必须介于 0 和 20 之间")
	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}
	
	@Length(min=0, max=100, message="archives_name长度必须介于 0 和 100 之间")
	public String getArchivesName() {
		return archivesName;
	}

	public void setArchivesName(String archivesName) {
		this.archivesName = archivesName;
	}
	
	@Length(min=0, max=50, message="make_unit长度必须介于 0 和 50 之间")
	public String getMakeUnit() {
		return makeUnit;
	}

	public void setMakeUnit(String makeUnit) {
		this.makeUnit = makeUnit;
	}
	
	@Length(min=0, max=64, message="carrier_type长度必须介于 0 和 64 之间")
	public String getCarrierType() {
		return carrierType;
	}

	public void setCarrierType(String carrierType) {
		this.carrierType = carrierType;
	}
	
	@Length(min=0, max=11, message="（案卷内文件的数量计算）长度必须介于 0 和 11 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	
	@Length(min=0, max=11, message="year长度必须介于 0 和 11 之间")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Length(min=0, max=64, message="storage_period长度必须介于 0 和 64 之间")
	public String getStoragePeriod() {
		return storagePeriod;
	}

	public void setStoragePeriod(String storagePeriod) {
		this.storagePeriod = storagePeriod;
	}
	
	@Length(min=0, max=64, message="degree_secrets长度必须介于 0 和 64 之间")
	public String getDegreeSecrets() {
		return degreeSecrets;
	}

	public void setDegreeSecrets(String degreeSecrets) {
		this.degreeSecrets = degreeSecrets;
	}
	
	@Length(min=0, max=200, message="main_title长度必须介于 0 和 200 之间")
	public String getMainTitle() {
		return mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}
	
	@Length(min=0, max=500, message="archives_explain长度必须介于 0 和 500 之间")
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
	
	@Length(min=0, max=100, message="案卷类型 0：普通案卷；1：声像案卷长度必须介于 0 和 100 之间")
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

	public AmsWorkArchivesFiles getAmsWorkArchivesFiles() {
		return amsWorkArchivesFiles;
	}

	public void setAmsWorkArchivesFiles(AmsWorkArchivesFiles amsWorkArchivesFiles) {
		this.amsWorkArchivesFiles = amsWorkArchivesFiles;
	}
	
}