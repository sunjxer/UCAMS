/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import org.hibernate.validator.constraints.Length;

import com.ucams.common.persistence.DataEntity;
import com.ucams.modules.sys.entity.Role;

/**
 * 责任主体详细信息管理Entity
 * @author ws
 * @version 2017-07-04
 */
public class AmsUnitDetailinfo extends DataEntity<AmsUnitDetailinfo> {
	
	private static final long serialVersionUID = 1L;

	private Role role;		// 责任主体主键
	private String unitCreditCode;		// 社会信用代码
	private String address;		// 单位地址
	private String qualifications;		// 单位资质等级
	private String qualificationsOther;		// 单位资质等级_其它
	private String qualificationsNumber;		// 单位资质证书号
	private String responsiblePerson;		// 项目负责人
	private String responsiblePersonId;		// 项目负责人身份证号
	private String legalPerson;		// 法人
	private String exten1;		// 扩展1
	private String exten2;		// 扩展2
	private String exten3;		// 扩展3
	private String exten4;		// 扩展4
	private String exten5;		// 扩展5
	
	public AmsUnitDetailinfo() {
		super();
	}

	public AmsUnitDetailinfo(String id){
		super(id);
	}
	
	public AmsUnitDetailinfo(Role role){
		this.role = role;
	}
	
	//@Length(min=1, max=22, message="社会信用代码长度必须介于 1 和 22 之间")
	public String getUnitCreditCode() {
		return unitCreditCode;
	}

	public void setUnitCreditCode(String unitCreditCode) {
		this.unitCreditCode = unitCreditCode;
	}
	
	//@Length(min=1, max=50, message="单位地址长度必须介于 1 和 50 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	//@Length(min=1, max=64, message="单位资质等级长度必须介于 1 和 64 之间")
	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}
	
	//@Length(min=0, max=20, message="单位资质等级_其它长度必须介于 0 和 20 之间")
	public String getQualificationsOther() {
		return qualificationsOther;
	}

	public void setQualificationsOther(String qualificationsOther) {
		this.qualificationsOther = qualificationsOther;
	}
	
	//@Length(min=1, max=30, message="单位资质证书号长度必须介于 1 和 30 之间")
	public String getQualificationsNumber() {
		return qualificationsNumber;
	}

	public void setQualificationsNumber(String qualificationsNumber) {
		this.qualificationsNumber = qualificationsNumber;
	}
	
	//@Length(min=1, max=20, message="项目负责人长度必须介于 1 和 20 之间")
	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}
	
	//@Length(min=1, max=18, message="项目负责人身份证号长度必须介于 1 和 18 之间")
	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}

	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	
	//@Length(min=1, max=20, message="法人长度必须介于 1 和 20 之间")
	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	
	//@Length(min=0, max=100, message="扩展1长度必须介于 0 和 100 之间")
	public String getExten1() {
		return exten1;
	}

	public void setExten1(String exten1) {
		this.exten1 = exten1;
	}
	
	//@Length(min=0, max=100, message="扩展2长度必须介于 0 和 100 之间")
	public String getExten2() {
		return exten2;
	}

	public void setExten2(String exten2) {
		this.exten2 = exten2;
	}
	
	//@Length(min=0, max=100, message="扩展3长度必须介于 0 和 100 之间")
	public String getExten3() {
		return exten3;
	}

	public void setExten3(String exten3) {
		this.exten3 = exten3;
	}
	
	//@Length(min=0, max=100, message="扩展4长度必须介于 0 和 100 之间")
	public String getExten4() {
		return exten4;
	}

	public void setExten4(String exten4) {
		this.exten4 = exten4;
	}
	
	//@Length(min=0, max=100, message="扩展5长度必须介于 0 和 100 之间")
	public String getExten5() {
		return exten5;
	}

	public void setExten5(String exten5) {
		this.exten5 = exten5;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
}