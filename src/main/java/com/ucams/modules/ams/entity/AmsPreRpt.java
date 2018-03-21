/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.entity;

import org.hibernate.validator.constraints.Length;

import com.ucams.common.persistence.DataEntity;
import com.ucams.common.utils.excel.annotation.ExcelField;

/**
 * 检查报告Entity
 * @author sunjx
 * @version 2017-08-04
 */
public class AmsPreRpt extends DataEntity<AmsPreRpt> {
	
	private static final long serialVersionUID = 1L;
	private String transferId;		// 预验收与移交ID
	@ExcelField(title="错误类型", type=1, align=2, sort=10,dictType="rptType")
	private String errorType;		// 0-内容 不全（必填项未填），1-文件数量不符
	@ExcelField(title="               错误内容                         ", type=1, align=1, sort=20)
	private String error;		// 错误内容
	private String opersion;		// 位置
	@ExcelField(title="错误来源", type=1, align=2, sort=40,dictType="rptState")
	private String state;		// 0-正常错误，1-忽略，2-录入；打印报告时不打印状态为1的数据。
	private String exten1;		// exten1
	private String exten2;		// exten2
	private String exten3;		// exten3
	private String exten4;		// exten4
	private String exten5;		// exten5
	
	public AmsPreRpt() {
		super();
	}

	public AmsPreRpt(String id){
		super(id);
	}

	public AmsPreRpt(String errorType, String error, String opersion,
			String state) {
		super();
		this.errorType = errorType;
		this.error = error;
		this.opersion = opersion;
		this.state = state;
	}

	@Length(min=1, max=64, message="预验收与移交ID长度必须介于 1 和 64 之间")
	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	
	@Length(min=1, max=1, message="0-内容 不全（必填项未填），1-文件数量不符长度必须介于 1 和 1 之间")
	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	@Length(min=0, max=255, message="错误内容长度必须介于 0 和 255 之间")
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	@Length(min=0, max=255, message="位置长度必须介于 0 和 255 之间")
	public String getOpersion() {
		return opersion;
	}

	public void setOpersion(String opersion) {
		this.opersion = opersion;
	}
	
	@Length(min=0, max=1, message="0-正常错误，1-忽略，2-录入；打印报告时不打印状态为1的数据。长度必须介于 0 和 1 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=1, max=100, message="exten1长度必须介于 1 和 100 之间")
	public String getExten1() {
		return exten1;
	}

	public void setExten1(String exten1) {
		this.exten1 = exten1;
	}
	
	@Length(min=1, max=100, message="exten2长度必须介于 1 和 100 之间")
	public String getExten2() {
		return exten2;
	}

	public void setExten2(String exten2) {
		this.exten2 = exten2;
	}
	
	@Length(min=1, max=100, message="exten3长度必须介于 1 和 100 之间")
	public String getExten3() {
		return exten3;
	}

	public void setExten3(String exten3) {
		this.exten3 = exten3;
	}
	
	@Length(min=1, max=100, message="exten4长度必须介于 1 和 100 之间")
	public String getExten4() {
		return exten4;
	}

	public void setExten4(String exten4) {
		this.exten4 = exten4;
	}
	
	@Length(min=1, max=100, message="exten5长度必须介于 1 和 100 之间")
	public String getExten5() {
		return exten5;
	}

	public void setExten5(String exten5) {
		this.exten5 = exten5;
	}
	
}