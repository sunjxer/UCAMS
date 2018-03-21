/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.ucams.common.constant.UcamsConstant;
import com.ucams.common.mongo.DBOperationsU;
import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.common.utils.EntityUtils;
import com.ucams.modules.ams.dao.AmsArchivesInfoDao;
import com.ucams.modules.ams.dao.AmsConstructDesDao;
import com.ucams.modules.ams.dao.AmsDesExtendDao;
import com.ucams.modules.ams.dao.AmsFileInfoDao;
import com.ucams.modules.ams.dao.AmsGenreDao;
import com.ucams.modules.ams.dao.AmsLandDesDao;
import com.ucams.modules.ams.dao.AmsProjectInfoDao;
import com.ucams.modules.ams.dao.AmsUnitDetailinfoDao;
import com.ucams.modules.ams.dao.AmsUnitProInfoDao;
import com.ucams.modules.ams.entity.AmsArchivesFiles;
import com.ucams.modules.ams.entity.AmsArchivesInfo;
import com.ucams.modules.ams.entity.AmsConstructDes;
import com.ucams.modules.ams.entity.AmsDesExtend;
import com.ucams.modules.ams.entity.AmsDesProgram;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsGenre;
import com.ucams.modules.ams.entity.AmsLandDes;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.entity.AmsUnitProInfo;
import com.ucams.modules.sys.dao.RoleDao;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 组卷设置Service
 * @author gyl
 * @version 2017-07-10
 */
@Service
@Transactional(readOnly = true)
public class AmsArchivesInfoService extends CrudService<AmsArchivesInfoDao, AmsArchivesInfo> {
	
	@Autowired
	private AmsArchivesInfoDao amsArchivesInfoDao;
	
	@Autowired
	private AmsUnitDetailinfoDao amsUnitDetailinfoDao;
	
	@Autowired
	private AmsProjectInfoDao amsProjectInfoDao;
	
	@Autowired
	private AmsUnitProInfoDao amsUnitProInfoDao;
	
	@Autowired
	private AmsConstructDesDao amsConstructDesDao;
	
	@Autowired
	private AmsLandDesDao amsLandDesDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private AmsDesExtendDao amsDesExtendDao;
	
	@Autowired
	private AmsFileInfoDao amsFileInfoDao;
	
	@Autowired
	private AmsGenreDao amsGenreDao;
	@Autowired
	private DBOperationsU dbOperationU;
	

	public AmsArchivesInfo get(String id) {
		return super.get(id);
	}
	
	public List<AmsArchivesInfo> findList(AmsArchivesInfo amsArchivesInfo) {
		return super.findList(amsArchivesInfo);
	}
	
	public Page<AmsArchivesInfo> findPage(Page<AmsArchivesInfo> page, AmsArchivesInfo amsArchivesInfo) {
		return super.findPage(page, amsArchivesInfo);
	}
	
	/**
	 * 预验收查询项目下案卷
	 * @param page
	 * @param amsArchivesInfo
	 * @author zkx
	 * @return
	 */
	public Page<AmsArchivesInfo> findProArchivesPage(Page<AmsArchivesInfo> page, AmsArchivesInfo amsArchivesInfo) {
		amsArchivesInfo.setPage(page);
		return page.setList(dao.findProArchivesList(amsArchivesInfo));
	}
	/**
	 * 移交查询项目下案卷
	 * @param page
	 * @param amsArchivesInfo
	 * @author zkx
	 * @return
	 */
	public Page<AmsArchivesInfo> transferFindProArchivesPage(Page<AmsArchivesInfo> page, AmsArchivesInfo amsArchivesInfo) {
		amsArchivesInfo.setPage(page);
		return page.setList(dao.transferFindProArchivesPage(amsArchivesInfo));
	}
	
	@Transactional(readOnly = false)
	public void save(AmsArchivesInfo amsArchivesInfo) {
		super.save(amsArchivesInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsArchivesInfo amsArchivesInfo) {
		super.delete(amsArchivesInfo);
	}
	
	/**
	 * 删除案卷文件关系
	 * @param amsArchivesInfo
	 */
	@Transactional(readOnly = false)
	public void deleteArchivesFiles(AmsArchivesInfo amsArchivesInfo){
		amsArchivesInfoDao.deleteArchivesFiles(amsArchivesInfo.getId());
	}
	
	/**
	 * 获得责任主体信息
	 * @param id 
	 * @return
	 */
	public AmsUnitDetailinfo getAmsUnitDetailinfo() {
		Role role=UserUtils.getUser().getRoleList().get(0);
		return amsUnitDetailinfoDao.getByUnitId(new AmsUnitDetailinfo(role));
	}
	
	/**
	 * 分页查询项目工程信息列表
	 * @param page
	 * @param amsUnitProInfo
	 * @return
	 */
	public Page<AmsProjectInfo> findAmsProInfo(Page<AmsProjectInfo> page) {
		
		AmsProjectInfo amsProjectInfo=new AmsProjectInfo();
		List<Office> offices = UserUtils.getOfficeList(); 
		List<String> ids = new ArrayList<String>(); 
		for(int i=0; i<offices.size(); i++){
			//过滤机构，选择类型为3的就是项目;4是单位工程
				if("3".equals(offices.get(i).getGrade())){ 
					ids.add(offices.get(i).getId());
				}
			}
		// 设置分页参数
		amsProjectInfo.setPage(page);
		if(ids.size()>0){
			amsProjectInfo.setIds(ids);
			// 执行分页查询
			List<AmsProjectInfo> list=amsArchivesInfoDao.findProjectList(amsProjectInfo);
			page.setList(list);
		}
		return page;
	}
	
	/**
	 * 分页查询单位工程信息列表
	 * @param page
	 * @param amsUnitProInfo
	 * @return
	 */
	public Page<AmsUnitProInfo> findAmsUnitProInfo(Page<AmsUnitProInfo> page,AmsUnitProInfo amsUnitProInfo ) {
		List<Office> offices = UserUtils.getOfficeList();
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < offices.size() - 1; i++) {
			//过滤机构，选择类型为3的就是项目;4是单位工程
			if ("4".equals(offices.get(i).getGrade())) {
				ids.add(offices.get(i).getId());
			}
		}
		amsUnitProInfo.setIds(ids);
		// 设置分页参数
		amsUnitProInfo.setPage(page);
		// 执行分页查询
		page.setList(amsArchivesInfoDao.findUnitProjectList(amsUnitProInfo));
		return page;
	}
	
	/**
	 * 获得工程项目信息
	 * @param id 工程项目id
	 * @return
	 */
	public AmsProjectInfo getPro(String id) {
		return amsProjectInfoDao.get(id);
	}
	
	/**
	 * 获得单位工程信息
	 * @param id 单位工程id
	 * @return
	 */
	public AmsUnitProInfo getUnitPro(String id) {
		return amsUnitProInfoDao.get(id);
	}
	/**
	 * 单位工程预验收状态，单位工程提交预验收或者验收通过，如果大于等于1则为预验收已提交或者预验收通过，如果等于0则预验收未提交或者被驳回
	 * @param id
	 * @return
	 */
	public int getUnitProAcceptanceStatus(String id) {
		return amsArchivesInfoDao.findUnitProjectAcceptanceCount(id);
	}
	
	/**
	 * 获取本单位所有案卷列表
	 * @param proIdList
	 * @return
	 */
	public List<AmsArchivesInfo> findArchListByProId(List proIdList){
		List<AmsArchivesInfo> list = new ArrayList();
		if(proIdList.size() >0){
			list = amsArchivesInfoDao.findArchListByProId(proIdList);
		}
//		list = amsArchivesInfoDao.findArchListByProId(proIdList);
		return list;
	};
	/**
	 * 获取建设工程规划著录列表
	 * @param proIdList
	 * @return
	 */
	public List<AmsConstructDes> findConstructDesListByProId(List proIdList){
		List<AmsConstructDes> list = new ArrayList();
		if(proIdList.size() >0){
			list = amsConstructDesDao.findConstructListByProjectId(proIdList);
		}
		return list;
	}
	/**
	 * 获取建设用地规划著录列表
	 * @param proIdList
	 * @return
	 */
	public List<AmsLandDes> findLandDesListByProjectId(List proIdList){
		List<AmsLandDes> list = new ArrayList();
		if(proIdList.size() >0){
			list = amsLandDesDao.findLandListByProjectId(proIdList);
		}
		return list;
	}
	
	/**
	 * 已组卷文件
	 * @param page
	 * @param amsFileInfo
	 * @return
	 */
	public Page<AmsFileInfo> findAmsFileInfoInList(Page<AmsFileInfo> page, AmsFileInfo amsFileInfo){
		//设置分页参数
		amsFileInfo.setPage(page);
		//获取案卷已组文件列表
		List<AmsFileInfo> list = amsArchivesInfoDao.findAmsFileInfoInList(amsFileInfo);
		/*List<AmsFileInfo> listFile = new ArrayList<AmsFileInfo>();
		//页数
		int count = 1;
		for(int i=0;i<list.size();i++){
			AmsFileInfo amsFile = list.get(i);
			AmsArchivesFiles amsArchivesFiles = amsFile.getAmsArchivesFiles();
			//设置文件在案卷中的页数
			amsArchivesFiles.setPageCount(count);
			count += Integer.parseInt(amsFile.getFilecount());
			amsFile.setAmsArchivesFiles(amsArchivesFiles);
			listFile.add(amsFile);
		}
		page.setList(listFile);*/
		page.setList(list);
		return page;
	}
	/**
	 * 查询未组卷文件
	 * @param page
	 * @param amsFileInfo
	 * @return
	 */
	public Page<AmsFileInfo> findAmsFileInfoAllList(Page<AmsFileInfo> page, AmsFileInfo amsFileInfo){
		amsFileInfo.setPage(page);
		page.setList(amsArchivesInfoDao.findAmsFileInfoAllList(amsFileInfo));
		return page;
	}
	/**
	 * 删除案卷中的文件
	 * @param amsArchivesFiles
	 */
	@Transactional(readOnly = false)
	public void deleteFile(AmsArchivesFiles amsArchivesFiles){ 
		//获取要删除的案卷文件关系
		amsArchivesFiles = amsArchivesInfoDao.getAmsArchivesFiles(amsArchivesFiles);
		int pageCount = Integer.parseInt(amsArchivesFiles.getEndPage())-Integer.parseInt(amsArchivesFiles.getStartPage())+1;
		int sort = amsArchivesFiles.getSort();
		//获取已组卷文件列表
		List<AmsArchivesFiles> list = amsArchivesInfoDao.findArchivesFilesList(amsArchivesFiles);
		for(AmsArchivesFiles archivesFiles : list){
			AmsArchivesFiles ams = new AmsArchivesFiles();
			//列表中的文件排序大于需要删除的文件排序，修改文件排序和起始页
			if(archivesFiles.getSort() > sort){
				//起始页减去要删除的文件页数即为修改后的文件起始页
				int startPage = Integer.parseInt(archivesFiles.getStartPage())-pageCount;
				int endPage = Integer.parseInt(archivesFiles.getEndPage())-pageCount;
				ams.setId(archivesFiles.getId());
				ams.setStartPage(startPage+"");
				ams.setEndPage(endPage+"");
				//删除一个文件，排序减一即可
				ams.setSort(archivesFiles.getSort()-1);
				amsArchivesInfoDao.updateAmsArchivesFiles(ams);
			}
		}

		//删除需要删除的文件
		amsArchivesInfoDao.deleteFile(amsArchivesFiles);
	}
	/**
	 * 添加案卷中的文件
	 * @param amsArchivesFiles
	 */
	@Transactional(readOnly = false)
	public void addFile(AmsArchivesFiles amsArchivesFiles){
		//获取文件信息
		AmsFileInfo amsFileInfo = new AmsFileInfo();
		amsFileInfo.setId(amsArchivesFiles.getRecordId());
		amsFileInfo = amsFileInfoDao.getFile(amsFileInfo);
		//获取已组卷文件列表
		List<AmsArchivesFiles> list = amsArchivesInfoDao.findArchivesFilesList(amsArchivesFiles);		
		int count = list.size();
		//0：卷内目录为按照文件名，1：卷内目录按照文件题名生成
		if("0".equals(amsArchivesFiles.getAmsArchivesInfo().getCatalogType())||amsArchivesFiles.getAmsArchivesInfo().getCatalogType()==""||amsArchivesFiles.getAmsArchivesInfo().getCatalogType()==null){
			//如果没有已组卷文件，设置起始页为1，结束页为文件页数
			if(count <= 0){
				amsArchivesFiles.setStartPage("1");
				amsArchivesFiles.setEndPage(amsFileInfo.getFilecount());
			//如果有已组卷文件，起始页为最后一个文件的结束页+1，结束页为最后一个文件的结束页+文件页数
			}else{
				AmsArchivesFiles archivesFiles = list.get(0);
				int startPage = Integer.parseInt(archivesFiles.getEndPage())+1;
				int endPage = Integer.parseInt(archivesFiles.getEndPage())+Integer.parseInt(amsFileInfo.getFilecount());
				amsArchivesFiles.setStartPage(startPage+"");
				amsArchivesFiles.setEndPage(endPage+"");			
			}
			//设置文件卷内顺序
			amsArchivesFiles.setSort(count+1);
		}else{
			if(count <= 0){
				amsArchivesFiles.setStartPage("1");
				amsArchivesFiles.setEndPage(amsFileInfo.getFilecount());
				amsArchivesFiles.setSort(count+1);
			}else{
				//文件题名相同的已组卷文件
				List<AmsArchivesFiles> genreList = amsArchivesInfoDao.findArchivesFilesListByGerne(amsArchivesFiles);
				//如果没有文件题名相同的文件，文件顺序添加
				if(genreList.size() <= 0){
					AmsArchivesFiles archivesFiles = list.get(0);
					int startPage = Integer.parseInt(archivesFiles.getEndPage())+1;
					int endPage = Integer.parseInt(archivesFiles.getEndPage())+Integer.parseInt(amsFileInfo.getFilecount());
					amsArchivesFiles.setStartPage(startPage+"");
					amsArchivesFiles.setEndPage(endPage+"");
					amsArchivesFiles.setSort(count+1);
				//如果有文件题名相同的文件，文件添加在相同题名文件后，该文件题名排序后的文件排序加1，起始页也相应增加
				}else{
					AmsArchivesFiles archivesFiles = genreList.get(0);
					amsArchivesFiles.setSort(archivesFiles.getSort()+1);
					int startPage = Integer.parseInt(archivesFiles.getEndPage())+1;
					int endPage = Integer.parseInt(archivesFiles.getEndPage())+Integer.parseInt(amsFileInfo.getFilecount());
					amsArchivesFiles.setStartPage(startPage+"");
					amsArchivesFiles.setEndPage(endPage+"");
					//排序在大于该文件排序的文件列表
					List<AmsArchivesFiles> sortList = amsArchivesInfoDao.findArchivesFilesListBySort(amsArchivesFiles);
					if(sortList.size()>0){
						for(AmsArchivesFiles aFiles : sortList){
							aFiles.setSort(aFiles.getSort()+1);
							int aStartPage = Integer.parseInt(aFiles.getStartPage())+Integer.parseInt(amsFileInfo.getFilecount());
							int aEndPage = Integer.parseInt(aFiles.getEndPage())+Integer.parseInt(amsFileInfo.getFilecount());
							aFiles.setStartPage(aStartPage+"");
							aFiles.setEndPage(aEndPage+"");
							amsArchivesInfoDao.updateSorts(aFiles);
						}
					}
				}
			}
		}
		
		if(StringUtils.isBlank(amsArchivesFiles.getId())){
			amsArchivesFiles.preInsert();
			amsArchivesInfoDao.addFile(amsArchivesFiles);
		}
	}
	/**
	 * 获取建设工程规划基本信息和扩展信息
	 * @param id
	 * @return
	 */
	public AmsConstructDes getCon(String id){
		AmsConstructDes amsConstructDes = new AmsConstructDes();
		amsConstructDes = amsConstructDesDao.get(id);
		//判断建设工程规划信息是否存在，如果不存在，从项目表中获取
		if(StringUtils.isBlank(amsConstructDes.getProjectName())){
			//获取工程相关信息
			AmsProjectInfo amsProjectInfo = amsProjectInfoDao.get(amsConstructDes.getProjectId());
			//获取建设单位名称
			Role role = roleDao.get(amsProjectInfo.getConstructionId());
			
			amsConstructDes.setProjectName(amsProjectInfo.getProjectName());
			amsConstructDes.setAddress(amsProjectInfo.getAddress());
			amsConstructDes.setConstructionUnit(role.getName());
			amsConstructDes.setProjectApprovalUnit(amsProjectInfo.getProjectApprovalUnit());
			amsConstructDes.setDesignUnit(amsProjectInfo.getDesignUnit());
			amsConstructDes.setProspectingUnit(amsProjectInfo.getProspectingUnit());
			amsConstructDes.setApprovalNumber(amsProjectInfo.getApprovalNumber());
			amsConstructDes.setPlanningLicenseNumber(amsProjectInfo.getPlanningLicenseNumber());
			amsConstructDes.setLandLicenseNumber(amsProjectInfo.getLandLicenseNumber());
			amsConstructDes.setLandPermitNumber(amsProjectInfo.getLandPermitNumber());
		}
		//扩展信息
//		List<AmsDesExtend>  amsDesExtends = this.findExtendDataList(amsConstructDes);
		List<AmsDesExtend>  amsDesExtends = this.findExtendDataList(UcamsConstant.AMS_DESPROGRAM_JSGCGH);
		if(EntityUtils.isNotEmpty(amsDesExtends)&& amsDesExtends.size()>0&&amsConstructDes.getDescriptionJson()!=null&&!"".equals(amsConstructDes.getDescriptionJson())){
			JSONObject jsonObject=JSONObject.parseObject(amsConstructDes.getDescriptionJson());
			for (int i = 0; i < amsDesExtends.size(); i++) {
				AmsDesExtend amsDesExtend1=amsDesExtends.get(i);
				JSONObject data=JSONObject.parseObject(jsonObject.getString("data"));
				amsDesExtend1.setSettings(data==null?"":data.getString(amsDesExtend1.getName()));
			}
		}		
		amsConstructDes.setAmsDesExtendList(amsDesExtends);
		return amsConstructDes;
	}
	/**
	 * 获取案卷扩展信息
	 * @param amsArchivesInfo
	 * @return
	 */
	public AmsArchivesInfo getArch(AmsArchivesInfo amsArchivesInfo){
//		AmsArchivesInfo arch = new AmsArchivesInfo();
		List<AmsDesExtend>  amsDesExtends = this.findExtendDataList(UcamsConstant.AMS_DESPROGRAM_AJXX);
		if(EntityUtils.isNotEmpty(amsDesExtends)&& amsDesExtends.size()>0&&amsArchivesInfo.getArchivesJson()!=null&&!"".equals(amsArchivesInfo.getArchivesJson())){
			JSONObject jsonObject=JSONObject.parseObject(amsArchivesInfo.getArchivesJson());
			for (int i = 0; i < amsDesExtends.size(); i++) {
				AmsDesExtend amsDesExtend1=amsDesExtends.get(i);
				JSONObject data=JSONObject.parseObject(jsonObject.getString("data"));
				amsDesExtend1.setSettings(data==null?"":data.getString(amsDesExtend1.getName()));
			}
		}
		amsArchivesInfo.setAmsDesExtendList(amsDesExtends);
		return amsArchivesInfo;
	}
	/**
	 * 获取建设用地规划基本信息和著录信息
	 * @param id
	 * @return
	 */
	public AmsLandDes getLan(String id){
		AmsLandDes amsLandDes = new AmsLandDes();
		amsLandDes = amsLandDesDao.get(id);
		if(StringUtils.isBlank(amsLandDes.getProjectName())){
			AmsProjectInfo amsProjectInfo = amsProjectInfoDao.get(amsLandDes.getProjectId());
			//获取建设单位名称
			Role role = roleDao.get(amsProjectInfo.getConstructionId());
			amsLandDes.setProjectName(amsProjectInfo.getProjectName());
			amsLandDes.setAddress(amsProjectInfo.getAddress());
			amsLandDes.setLandUseUnit(role.getName());//征地单位默认为建设单位
		}
		List<AmsDesExtend>  amsDesExtends = this.findExtendDataList(UcamsConstant.AMS_DESPROGRAM_JSTDGH);
		if(EntityUtils.isNotEmpty(amsDesExtends)&& amsDesExtends.size()>0&&amsLandDes.getDescriptionJson()!=null&&!"".equals(amsLandDes.getDescriptionJson())){
			JSONObject jsonObject=JSONObject.parseObject(amsLandDes.getDescriptionJson());
			for (int i = 0; i < amsDesExtends.size(); i++) {
				AmsDesExtend amsDesExtend1=amsDesExtends.get(i);
				JSONObject data=JSONObject.parseObject(jsonObject.getString("data"));
				amsDesExtend1.setSettings(data==null?"":data.getString(amsDesExtend1.getName()));
			}
		}
		amsLandDes.setAmsDesExtendList(amsDesExtends);
		return amsLandDes;
	}
	/**
	 * 获取扩展信息
	 * @param type  类型：建设工程规划、建设用地规划、案卷
	 * @return List
	 */
	public List<AmsDesExtend> findExtendDataList(String type) {
		AmsDesProgram amsDesProgram=new AmsDesProgram();
		AmsDesExtend amsDesExtend =new AmsDesExtend();
		amsDesProgram.setProgramType(type);  //方案类型 建设工程规划
		amsDesExtend.setAmsEnginproTable(amsDesProgram);
		List<AmsDesExtend>  amsDesExtends=amsDesExtendDao.findExtendDataList(amsDesExtend);
		return amsDesExtends;
	}
	/**
	 * 获取扩展信息
	 * @param amsConstructDes
	 * @return
	 */
/*	public List<AmsDesExtend> findExtendDataList(AmsConstructDes amsConstructDes) { 
		AmsDesProgram amsDesProgram=new AmsDesProgram();
		AmsDesExtend amsDesExtend =new AmsDesExtend();
//		//获取的工程类型id  单位工程类型去掉
//		String type=amsUnitProInfoDao.get(amsFileInfo.getUnitProjectId()).getUnitProjectType();
//		amsDesProgram.setUnitProjectType(type);
		//文件类型编号
		amsDesProgram.setProgramType(UcamsConstant.AMS_DESPROGRAM_JSGCGH);  //方案类型 建设工程规划
		amsDesExtend.setAmsEnginproTable(amsDesProgram);
		List<AmsDesExtend>  amsDesExtends=amsDesExtendDao.findExtendDataList(amsDesExtend);
		if(EntityUtils.isNotEmpty(amsDesExtends)&& amsDesExtends.size()>0&&amsConstructDes.getDescriptionJson()!=null&&!"".equals(amsConstructDes.getDescriptionJson())){
			JSONObject jsonObject=JSONObject.parseObject(amsConstructDes.getDescriptionJson());
			for (int i = 0; i < amsDesExtends.size(); i++) {
				AmsDesExtend amsDesExtend1=amsDesExtends.get(i);
				JSONObject data=JSONObject.parseObject(jsonObject.getString("data"));
				amsDesExtend1.setSettings(data==null?"":data.getString(amsDesExtend1.getName()));
			}
		}
		return amsDesExtends;
	}*/
	
	/**
	 * 保存建设工程规划基本信息和扩展信息
	 * @param amsConstructDes
	 */
	@Transactional(readOnly = false)
	public void saveCon(AmsConstructDes amsConstructDes){
		List<AmsDesExtend> amsDesExtends = amsConstructDes.getAmsDesExtendList();
		//获取扩展信息并转化为json串
		JSONObject jObject=new JSONObject();
		
		if(amsDesExtends!=null && amsDesExtends.size()>0){
			jObject.put(UcamsConstant.AMS_MONGO_IDS_PROJECT, amsConstructDes.getProjectId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_CONSTRUCT, amsConstructDes.getId());
			Map<String,Object> map =new HashMap<String,Object>();
			for (AmsDesExtend amsDesExtend:amsDesExtends) {
				map.put(amsDesExtend.getName(), amsDesExtend.getSettings());
			}
			JSONObject data=  JSON.parseObject(JSONObject.toJSONString(map));
			jObject.put("data", data);
			if(amsConstructDes.getDescriptionJson() == null){
				dbOperationU.save(jObject,UcamsConstant.AMS_MONGO_TABLE_NAME_CONSTRUCT);
			}else{
				dbOperationU.updateDocument(UcamsConstant.AMS_MONGO_TABLE_NAME_CONSTRUCT,new BasicDBObject(UcamsConstant.AMS_MONGO_IDS_CONSTRUCT,amsConstructDes.getId()),new BasicDBObject(jObject));
			}
			amsConstructDes.setDescriptionJson(jObject.toJSONString());
		}
		amsConstructDesDao.update(amsConstructDes);
	}
	/**
	 * 保存建设用地规划著录基本信息和扩展信息
	 * @param amsLandDes
	 */
	@Transactional(readOnly = false)
	public void saveLan(AmsLandDes amsLandDes){
		List<AmsDesExtend> amsDesExtends = amsLandDes.getAmsDesExtendList();
		//获取扩展信息并转化为json串
		JSONObject jObject=new JSONObject();
		if(amsDesExtends!=null && amsDesExtends.size()>0){
			jObject.put(UcamsConstant.AMS_MONGO_IDS_PROJECT, amsLandDes.getProjectId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_LAND, amsLandDes.getId());
			Map<String,Object> map =new HashMap<String,Object>();
			for (AmsDesExtend amsDesExtend:amsDesExtends) {
				map.put(amsDesExtend.getName(), amsDesExtend.getSettings());
			}
			JSONObject data=  JSON.parseObject(JSONObject.toJSONString(map));
			jObject.put("data", data);
			if(amsLandDes.getDescriptionJson() == null){
				dbOperationU.save(jObject,UcamsConstant.AMS_MONGO_TABLE_NAME_LAND);
			}else{
				dbOperationU.updateDocument(UcamsConstant.AMS_MONGO_TABLE_NAME_LAND,new BasicDBObject(UcamsConstant.AMS_MONGO_IDS_LAND,amsLandDes.getId()),new BasicDBObject(jObject));
			}
			amsLandDes.setDescriptionJson(jObject.toJSONString());
		}
		amsLandDesDao.update(amsLandDes);
	}
	/**
	 * 保存案卷基本信息和扩展信息
	 * @param amsArchivesInfo
	 */
	@Transactional(readOnly = false)
	public void saveArch(AmsArchivesInfo amsArchivesInfo){
		Boolean isInsert = true;
		if(StringUtils.isBlank(amsArchivesInfo.getId())){
			amsArchivesInfo.preInsert();
		}else{
			amsArchivesInfo.preUpdate();
			isInsert = false;
		}
		//保存案卷扩展信息
		JSONObject jObject=new JSONObject();
		List<AmsDesExtend> amsDesExtends = amsArchivesInfo.getAmsDesExtendList();
		if(amsDesExtends != null && amsDesExtends.size()>0){
			
			jObject.put(UcamsConstant.AMS_MONGO_IDS_PROJECT, amsArchivesInfo.getProjectId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_UNIT, amsArchivesInfo.getUnitProjectId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_ARCHIVES, amsArchivesInfo.getId());
			Map<String,Object> map =new HashMap<String,Object>();
			for (AmsDesExtend amsDesExtend:amsDesExtends) {
				map.put(amsDesExtend.getName(), amsDesExtend.getSettings());
			}
			JSONObject data=  JSON.parseObject(JSONObject.toJSONString(map));
			jObject.put("data", data);
			amsArchivesInfo.setArchivesJson(jObject.toJSONString());
		}
		if(isInsert){
			super.insert(amsArchivesInfo);
			dbOperationU.save(jObject,UcamsConstant.AMS_MONGO_TABLE_NAME_ARCHIVES);
		}else{
			super.update(amsArchivesInfo);
			dbOperationU.updateDocument(UcamsConstant.AMS_MONGO_TABLE_NAME_ARCHIVES,new BasicDBObject(UcamsConstant.AMS_MONGO_IDS_ARCHIVES,amsArchivesInfo.getId()),new BasicDBObject(jObject));
		}
	}
	
	/**
	 * 修改案卷中文件的顺序
	 * @param paramMap
	@Transactional(readOnly = false)
	public void changeSort(Map<String, Object> paramMap){
		amsArchivesInfoDao.updateSort(paramMap);
	}*/
	
	/**
	 * 案卷中文件排序
	 * @param thisId   当前文件id
	 * @param nextId  下移文件id
	 * @param prevId  上移文件id
	 * @param catalogType  案卷目录生成规则 0：卷内目录为按照文件名，1：卷内目录按照文件题名生成
	 */
	@Transactional(readOnly = false)
	public void changeSort(String thisId,String nextId,String prevId,String catalogType){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("thisId", thisId);
		//获取当前案卷文件信息
		AmsArchivesFiles thisArchives = new AmsArchivesFiles();
		thisArchives.setId(thisId);
		thisArchives = amsArchivesInfoDao.getAmsArchivesFiles(thisArchives);
		//当前文件起始页、结束页、文件页数
		int thisStartPage = Integer.parseInt(thisArchives.getStartPage());
		int thisEndPage = Integer.parseInt(thisArchives.getEndPage());
		int thisPageCount = thisEndPage-thisStartPage+1;
		try {
			if(EntityUtils.isNotEmpty(nextId)){   //下移
				paramMap.put("changeId", nextId);
				//获取下移文件信息
				AmsArchivesFiles nextArchives = new AmsArchivesFiles();
				nextArchives.setId(nextId);
				nextArchives = amsArchivesInfoDao.getAmsArchivesFiles(nextArchives);
				//卷内目录按照文件名生成
				if("0".equals(catalogType)||catalogType==""||catalogType==null){
					int nextStartPage = Integer.parseInt(nextArchives.getStartPage());
					int nextEndPage = Integer.parseInt(nextArchives.getEndPage());
					int nextPageCount = nextEndPage - nextStartPage +1;
					//交换位置后，当前文件起始页不变，当前文件结束页为当前文件起始页+下移文件页数-1,下移文件起始页为前文件起始页+下移文件页数，下移文件结束页不变
					paramMap.put("changeStartPage", thisStartPage+"");
					paramMap.put("changeEndPage", (thisStartPage+nextPageCount-1)+"");
					paramMap.put("thisStartPage", (thisStartPage+nextPageCount)+"");
					paramMap.put("thisEndPage", nextEndPage+"");
					amsArchivesInfoDao.updateSort(paramMap);
				//卷内目录按照文件题名生成
				}else{
					//如果需要移动的两个文件文件题名相同,移动方式为两个文件交换位置
					if(thisArchives.getGenreId().equals(nextArchives.getGenreId())){
						int nextStartPage = Integer.parseInt(nextArchives.getStartPage());
						int nextEndPage = Integer.parseInt(nextArchives.getEndPage());
						int nextPageCount = nextEndPage - nextStartPage +1;
						//交换位置后，当前文件起始页不变，当前文件结束页为当前文件起始页+下移文件页数-1,下移文件起始页为前文件起始页+下移文件页数，下移文件结束页不变
						paramMap.put("changeStartPage", thisStartPage+"");
						paramMap.put("changeEndPage", (thisStartPage+nextPageCount-1)+"");
						paramMap.put("thisStartPage", (thisStartPage+nextPageCount)+"");
						paramMap.put("thisEndPage", nextEndPage+"");
						amsArchivesInfoDao.updateSort(paramMap);
					//如果需要移动的两个文件文件题名不同，则两个文件题名下的文件交换位置
					}else{
						List<AmsArchivesFiles> thisList = amsArchivesInfoDao.findArchivesFilesListByGerne(thisArchives);
						List<AmsArchivesFiles> nextList = amsArchivesInfoDao.findArchivesFilesListByGerne(nextArchives);
						int thisCount = thisList.size();
						int nextCount = nextList.size();
						int tStartPage = Integer.parseInt(thisList.get(thisCount-1).getStartPage());
						int tEndPage = Integer.parseInt(thisList.get(0).getEndPage());
						int tPageCount = tEndPage-tStartPage+1;
						int nStartPage = Integer.parseInt(nextList.get(nextCount-1).getStartPage());
						int nEndPage = Integer.parseInt(nextList.get(0).getEndPage());
						int nPageCount = nEndPage-nStartPage+1;
						for(AmsArchivesFiles tArchives : thisList){
							tArchives.setSort(tArchives.getSort()+nextCount);
							tArchives.setStartPage((Integer.parseInt(tArchives.getStartPage())+nPageCount)+"");
							tArchives.setEndPage((Integer.parseInt(tArchives.getEndPage())+nPageCount)+"");
							amsArchivesInfoDao.updateSorts(tArchives);
						}
						
						for(AmsArchivesFiles nArchives : nextList){
							nArchives.setSort(nArchives.getSort()-thisCount);
							nArchives.setStartPage((Integer.parseInt(nArchives.getStartPage())-tPageCount)+"");
							nArchives.setEndPage((Integer.parseInt(nArchives.getEndPage())-tPageCount)+"");
							amsArchivesInfoDao.updateSorts(nArchives);
						}
						
					}
				}
			}
			//上移文件和上移文件基本相同
			if(EntityUtils.isNotEmpty(prevId)){  //上移
				paramMap.put("changeId", prevId);	
				AmsArchivesFiles prevArchives = new AmsArchivesFiles();
				prevArchives.setId(prevId);
				prevArchives = amsArchivesInfoDao.getAmsArchivesFiles(prevArchives);
				if("0".equals(catalogType)||catalogType==""||catalogType==null){
					int prevStartPage = Integer.parseInt(prevArchives.getStartPage());
					int prevEndPage = Integer.parseInt(prevArchives.getEndPage());
					int prevPageCount = prevEndPage - prevStartPage +1;
					paramMap.put("thisStartPage", prevStartPage+"");
					paramMap.put("thisEndPage", (prevStartPage+thisPageCount-1)+"");
					paramMap.put("changeStartPage", (prevStartPage+thisPageCount)+"");
					paramMap.put("changeEndPage", thisEndPage+"");
					amsArchivesInfoDao.updateSort(paramMap);
				}else{
					if(thisArchives.getGenreId().equals(prevArchives.getGenreId())){
						int prevStartPage = Integer.parseInt(prevArchives.getStartPage());
						int prevEndPage = Integer.parseInt(prevArchives.getEndPage());
						int prevPageCount = prevEndPage - prevStartPage +1;
						paramMap.put("thisStartPage", prevStartPage+"");
						paramMap.put("thisEndPage", (prevStartPage+thisPageCount-1)+"");
						paramMap.put("changeStartPage", (prevStartPage+thisPageCount)+"");
						paramMap.put("changeEndPage", thisEndPage+"");
						amsArchivesInfoDao.updateSort(paramMap);
					}else{
						List<AmsArchivesFiles> thisList = amsArchivesInfoDao.findArchivesFilesListByGerne(thisArchives);
						List<AmsArchivesFiles> prevList = amsArchivesInfoDao.findArchivesFilesListByGerne(prevArchives);
						int thisCount = thisList.size();
						int prevCount = prevList.size();
						int tStartPage = Integer.parseInt(thisList.get(thisCount-1).getStartPage());
						int tEndPage = Integer.parseInt(thisList.get(0).getEndPage());
						int tPageCount = tEndPage-tStartPage+1;
						int pStartPage = Integer.parseInt(prevList.get(prevCount-1).getStartPage());
						int pEndPage = Integer.parseInt(prevList.get(0).getEndPage());
						int pPageCount = pEndPage-pStartPage+1;
						for(AmsArchivesFiles tArchives : thisList){
							tArchives.setSort(tArchives.getSort()-prevCount);
							tArchives.setStartPage((Integer.parseInt(tArchives.getStartPage())-pPageCount)+"");
							tArchives.setEndPage((Integer.parseInt(tArchives.getEndPage())-pPageCount)+"");
							amsArchivesInfoDao.updateSorts(tArchives);
						}
						
						for(AmsArchivesFiles pArchives : prevList){
							pArchives.setSort(pArchives.getSort()+thisCount);
							pArchives.setStartPage((Integer.parseInt(pArchives.getStartPage())+tPageCount)+"");
							pArchives.setEndPage((Integer.parseInt(pArchives.getEndPage())+tPageCount)+"");
							amsArchivesInfoDao.updateSorts(pArchives);
						}
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询归档一览表列表
	 * @param amsArchivesInfo
	 * @return
	 */
	public List getRecordList(AmsArchivesInfo amsArchivesInfo){
		List<AmsGenre> list = new ArrayList();
		AmsGenre amsGenre = new AmsGenre();
		if("-1".equals(amsArchivesInfo.getUnitProjectId())){
			AmsProjectInfo amsProjectInfo = amsProjectInfoDao.get(amsArchivesInfo.getProjectId());
			amsGenre.setType(amsProjectInfo.getProjectType());
		}else{
			AmsUnitProInfo amsUnitProInfo = amsUnitProInfoDao.get(amsArchivesInfo.getUnitProjectId());
			amsGenre.setType(amsUnitProInfo.getUnitProjectType());
		}
		list = amsGenreDao.findAmsGenreList(amsGenre);
		return list;
	}
	@Transactional(readOnly = false)
	public void updateSort(AmsArchivesFiles amsArchivesFiles) {
		dao.updateSorts(amsArchivesFiles);
	}


  //查询项目工程下是否添加组卷，作为删除项目工程的依据
	@Transactional
	public String getAmsArchivesInfoId(String id){
		
		return amsArchivesInfoDao.getAmsArchivesInfoId(id);
	}
	/**
	 * 获取案卷预验收状态
	 * @param amsArchivesInfo
	 * @return
	 */
	public int getArchiveAcceptanceCount(AmsArchivesInfo amsArchivesInfo) {
		int acceptanceCount = 0;
		//案卷是单位工程下的案卷还是项目工程下的案卷
		if("-1".equals(amsArchivesInfo.getUnitProjectId())){
			//项目工程下案卷
			acceptanceCount = amsArchivesInfoDao.findArchivesAcceptanceCount(amsArchivesInfo.getId());
		}else{
			//单位工程下案卷
			acceptanceCount = amsArchivesInfoDao.findUnitProjectAcceptanceCount(amsArchivesInfo.getUnitProjectId());
		}
		return acceptanceCount;
	}
	
	public int getConLanAcceptanceCount(String id){
		int acceptanceCount = 0;
		acceptanceCount = amsArchivesInfoDao.findArchivesAcceptanceCount(id);
		return acceptanceCount;
	}
	
	public List<AmsArchivesInfo> findArchivesByFileIdAcceptId(String acceptId,String fileId){
		List<AmsArchivesInfo> list = new ArrayList<AmsArchivesInfo>();
		list = amsArchivesInfoDao.findAmsArchivesListByFileIdArchId(acceptId, fileId);
		List<AmsArchivesInfo> list1 = amsArchivesInfoDao.findAmsArchivesListByFileIdUnitProId(acceptId, fileId);
		if(list.size() > 0 &&  list1.size() > 0){
			list.addAll(list1);
			return list;
		}else if(list.size() == 0 && list1.size()>0){
			return list1;
		}else if(list.size() > 0 && list1.size() ==0){
			return list;
		}else{
			return null;
		}	
		
	}
	/**
	 * 查找案卷目录
	 * @param amsFileInfo
	 * @param catalogType  目录生成类型
	 * @return
	 */
	public List<AmsFileInfo> findCatalog(AmsFileInfo amsFileInfo,String catalogType){
		List<AmsFileInfo> returnList = new ArrayList<AmsFileInfo>();
		List<AmsFileInfo> list = amsArchivesInfoDao.findAmsFileInfoInList(amsFileInfo);
		//按照文件名称生成
		if("0".equals(catalogType) || catalogType == null){
			returnList = list;
		//按照文件题名生成
		}else{
			AmsArchivesFiles amsArchivesFiles = amsFileInfo.getAmsArchivesFiles();
			//已组卷文件文件题名列表
			List<String> genreList = amsArchivesInfoDao.findGenreIdById(amsArchivesFiles);
			for(int i=0; i<genreList.size(); i++){				
				String genreId = genreList.get(i);
				amsFileInfo.getAmsArchivesFiles().setGenreId(genreId);
				//根据文件题名查询已组卷文件列表，按照文件排序顺序查询；第一个文件的起始页为该文件题名文件的起始页，最后一个文件的结束页为该文件题名文件的结束页
				List<AmsFileInfo> gList = amsArchivesInfoDao.findAmsFileInfoInList(amsFileInfo);
				AmsFileInfo file = gList.get(0);
				AmsFileInfo lFile = gList.get(gList.size()-1);				
				file.getAmsArchivesFiles().setEndPage(lFile.getAmsArchivesFiles().getEndPage());
				returnList.add(file);
			}
			
		}
		return returnList;
	}
	/**
	 * 生成excel并导出excel
	 * @param title 导出表格的表名
	 * @param rowName 导出表格的列名
	 * @param dataList  导出对象list集合
	 */
	public void getExcel(String title,String[] rowName,List<Object[]>  dataList,HttpServletResponse response){
		HSSFWorkbook workbook =new HSSFWorkbook(); // 创建一个excel对象  
        HSSFSheet sheet =workbook.createSheet(title); // 创建表格
        // 产生表格标题行  
        /*HSSFRow rowm  =sheet.createRow(0);  // 行  
        HSSFCell cellTiltle =rowm.createCell(0);  // 单元格
        // sheet样式定义  
        HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook); // 头样式  
        HSSFCellStyle style = this.getStyle(workbook);  // 单元格样式 
        // 合并第一行的所有列  
        sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) (rowName.length-1)));  
        cellTiltle.setCellStyle(columnTopStyle);  
        cellTiltle.setCellValue("");*/
        HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook); // 头样式  
        HSSFCellStyle style = this.getStyle(workbook);  // 单元格样式 
        int columnNum = rowName.length;  // 表格列的长度  
        HSSFRow rowRowName = sheet.createRow(0);  // 在第一行创建行 
        //设置行高
        rowRowName.setHeight((short) 1200);
        HSSFCellStyle cells =workbook.createCellStyle();  
        cells.setBottomBorderColor(HSSFColor.BLACK.index);    
        rowRowName.setRowStyle(cells);
        // 循环 将列名放进去  
        for (int i = 0; i < columnNum; i++) {  
            HSSFCell  cellRowName = rowRowName.createCell((int)i);  
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 单元格类型  
              
            HSSFRichTextString text = new HSSFRichTextString(rowName[i]);  // 得到列的值  
            cellRowName.setCellValue(text); // 设置列的值  
            cellRowName.setCellStyle(columnTopStyle); // 样式  
        }
        // 将查询到的数据设置到对应的单元格中  
        for (int i = 0; i < dataList.size(); i++) {  
            Object[] obj = dataList.get(i);//遍历每个对象  
            HSSFRow row = sheet.createRow(i+1);//创建所需的行数  
            row.setHeight((short) 900);
            for (int j = 0; j < obj.length; j++) {  
                 HSSFCell  cell = null;   //设置单元格的数据类型   
                 if(j==0){  
                     // 第一列设置为序号  
                     cell = row.createCell(j,HSSFCell.CELL_TYPE_NUMERIC);  
                     cell.setCellValue(i+1);  
                 }else{  
                     cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);  
                     if(!"".equals(obj[j]) && obj[j] != null){    
                            cell.setCellValue(obj[j].toString());                       //设置单元格的值    
                        }else{  
                            cell.setCellValue("  ");  
                        }    
                 }  
                 cell.setCellStyle(style); // 样式  
            }  
        }  
        //  设置列宽
        sheet.setColumnWidth(0,  10*130);   // 在EXCEL文档中实际列宽为100
        sheet.setColumnWidth(1,  20*130);   
        sheet.setColumnWidth(2,  20*130);  
        sheet.setColumnWidth(3,  70*130);
        sheet.setColumnWidth(4,  20*130);
        sheet.setColumnWidth(5,  15*130);
        sheet.setColumnWidth(6,  15*130);
        //设置打印页面页边距
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); // 纸张
        sheet.setDisplayGridlines(false);
        sheet.setPrintGridlines(false);
        sheet.setMargin(HSSFSheet.TopMargin,( double ) 1.0 ); // 上边距
        sheet.setMargin(HSSFSheet.BottomMargin,( double ) 0.7 ); // 下边距
        sheet.setMargin(HSSFSheet.LeftMargin,( double ) 0.9 ); // 左边距
        sheet.setMargin(HSSFSheet.RightMargin,( double ) 0.5 ); // 右边距
        
        /*sheet.autoSizeColumn((short)0); //调整第一列宽度 
        sheet.setDefaultColumnWidth(200);
        sheet.setColumnWidth(2, 15000);
        sheet.setColumnWidth(4, 20000);
        sheet.autoSizeColumn((short)1); //调整第二列宽度  
        sheet.autoSizeColumn((short)2); //调整第三列宽度  
        sheet.autoSizeColumn((short)3); //调整第四列宽度  
        sheet.autoSizeColumn((short)4); //调整第五列宽度  
        sheet.autoSizeColumn((short)5); //调整第六列宽度 
        */
        if(workbook !=null){    
            try    
            {    
                // excel 表文件名  
                String fileName = title + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";    
                String fileName11 = URLEncoder.encode(fileName,"UTF-8");  
                String headStr = "attachment; filename=\"" + fileName11 + "\"";    
                response.setContentType("APPLICATION/OCTET-STREAM");    
                response.setHeader("Content-Disposition", headStr);    
                OutputStream out = response.getOutputStream();    
                workbook.write(out);  
                out.flush();  
                out.close();  
            }    
            catch (IOException e)    
            {    
                e.printStackTrace();    
            }   
              
        }  
	}
	
	 public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {    
         
	        // 设置字体    
	        HSSFFont font = workbook.createFont();    
	        //设置字体大小    
	        font.setFontHeightInPoints((short)13);    
	        //字体加粗    
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);    
	        //设置字体名字     
	        font.setFontName("Courier New");    
	        //设置样式;     
	        HSSFCellStyle style = workbook.createCellStyle();    
	        //设置底边框;     
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);    
	        //设置底边框颜色;      
	        style.setBottomBorderColor(HSSFColor.BLACK.index);    
	        //设置左边框;       
	        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);    
	        //设置左边框颜色;     
	        style.setLeftBorderColor(HSSFColor.BLACK.index);    
	        //设置右边框;     
	        style.setBorderRight(HSSFCellStyle.BORDER_THIN);    
	        //设置右边框颜色;     
	        style.setRightBorderColor(HSSFColor.BLACK.index);    
	        //设置顶边框;     
	        style.setBorderTop(HSSFCellStyle.BORDER_THIN);    
	        //设置顶边框颜色;      
	        style.setTopBorderColor(HSSFColor.BLACK.index);    
	        //在样式用应用设置的字体;      
	        style.setFont(font);    
	        //设置自动换行;     
	        style.setWrapText(false);    
	        //设置水平对齐的样式为居中对齐;      
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
	        //设置垂直对齐的样式为居中对齐;     
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);    
	            
	        return style;    
	            
	  }    
	      
	    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {    
	        // 设置字体    
	        HSSFFont font = workbook.createFont();    
	        //设置字体大小    
	        //font.setFontHeightInPoints((short)10);    
	        //字体加粗    
	        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);    
	        //设置字体名字     
	        font.setFontName("Courier New");    
	        //设置样式;     
	        HSSFCellStyle style = workbook.createCellStyle();    
	        //设置底边框;     
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);    
	        //设置底边框颜色;      
	        style.setBottomBorderColor(HSSFColor.BLACK.index);    
	        //设置左边框;       
	        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);    
	        //设置左边框颜色;     
	        style.setLeftBorderColor(HSSFColor.BLACK.index);    
	        //设置右边框;     
	        style.setBorderRight(HSSFCellStyle.BORDER_THIN);    
	        //设置右边框颜色;     
	        style.setRightBorderColor(HSSFColor.BLACK.index);    
	        //设置顶边框;     
	        style.setBorderTop(HSSFCellStyle.BORDER_THIN);    
	        //设置顶边框颜色;      
	        style.setTopBorderColor(HSSFColor.BLACK.index);    
	        //在样式用应用设置的字体;      
	        style.setFont(font);    
	        //设置自动换行;     
	        style.setWrapText(true);    
	        //设置水平对齐的样式为居中对齐;      
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
	        //设置垂直对齐的样式为居中对齐;     
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);    
	           
	        return style;    
	  }    

}