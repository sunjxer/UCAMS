package com.ucams.modules.ams.entity;

import com.ucams.common.persistence.DataEntity;

public class AmsArchivesFiles extends DataEntity<AmsArchivesFiles> {
	
	private static final long serialVersionUID = 1L;	
	private String groupId;   //案卷外键
	private String recordId;  //文件外键
	private String exten1;		// 扩展1
	private String exten2;		// 扩展2
	private String exten3;		// 扩展3
	private String exten4;		// 扩展4
	private String exten5;		// 扩展5
	private int sort;   //排序
	private int pageCount;  //页数
	private String startPage;  //起始页
	private String endPage;   //结束页
	private AmsArchivesInfo amsArchivesInfo;
	private String genreId;   //归档一览表id 文件题名id
	
	public AmsArchivesFiles(){
		super();
	}
	
	public AmsArchivesFiles(String id){
		super(id);
	}
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getExten1() {
		return exten1;
	}
	public void setExten1(String exten1) {
		this.exten1 = exten1;
	}
	public String getExten2() {
		return exten2;
	}
	public void setExten2(String exten2) {
		this.exten2 = exten2;
	}
	public String getExten3() {
		return exten3;
	}
	public void setExten3(String exten3) {
		this.exten3 = exten3;
	}
	public String getExten4() {
		return exten4;
	}
	public void setExten4(String exten4) {
		this.exten4 = exten4;
	}
	public String getExten5() {
		return exten5;
	}
	public void setExten5(String exten5) {
		this.exten5 = exten5;
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

	public AmsArchivesInfo getAmsArchivesInfo() {
		return amsArchivesInfo;
	}

	public void setAmsArchivesInfo(AmsArchivesInfo amsArchivesInfo) {
		this.amsArchivesInfo = amsArchivesInfo;
	}

	public String getGenreId() {
		return genreId;
	}

	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}

}
