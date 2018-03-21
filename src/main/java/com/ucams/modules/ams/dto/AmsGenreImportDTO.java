package com.ucams.modules.ams.dto;

import com.ucams.common.utils.excel.annotation.ExcelField;

/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年9月14日
 * 归档一览表信息导入模版类 
 */
public class AmsGenreImportDTO {
	
	/**编号**/
	private String code;
	/**父级编号**/
	private String parentCode;
	/**名称**/
	private String name;
	/**生成单位-建设单位**/
	private String jsUnit;
	/**生成单位-施工单位**/
	private String sgUnit;
	/**生成单位-监理单位**/
	private String jlUnit;
	
	@ExcelField(title="编号" ,sort=10)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@ExcelField(title="父级编号",sort=20)
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	@ExcelField(title="名称",sort=30)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ExcelField(title="建设单位",sort=40)
	public String getJsUnit() {
		return jsUnit;
	}
	public void setJsUnit(String jsUnit) {
		this.jsUnit = jsUnit;
	}
	@ExcelField(title="施工单位",sort=50)
	public String getSgUnit() {
		return sgUnit;
	}
	public void setSgUnit(String sgUnit) {
		this.sgUnit = sgUnit;
	}
	@ExcelField(title="监理单位",sort=60)
	public String getJlUnit() {
		return jlUnit;
	}
	public void setJlUnit(String jlUnit) {
		this.jlUnit = jlUnit;
	}
	
	
}
