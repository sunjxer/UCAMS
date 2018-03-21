package com.ucams.modules.ams.dto;

import com.ucams.common.utils.excel.annotation.ExcelField;

/**
 * 业务指导统计导出类
 * @author gyl
 *
 */
public class AmsGuidanceExportDTO {
	/**序号**/
	private String id;
	/**姓名**/
	private String userName;
	/**部门**/
	private String officeName;
	/**业务指导次数**/
	private long count;
	
	private String month;
	
	@ExcelField(title="序号" ,sort=10)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ExcelField(title="姓名" ,sort=20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@ExcelField(title="部门" ,sort=30)
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	@ExcelField(title="业务指导次数" ,sort=40)
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}

}
