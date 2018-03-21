/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ucams.common.config.Global;
import com.ucams.common.constant.CommonConstant;
import com.ucams.common.constant.UcamsConstant;
import com.ucams.common.mongo.DBOperationsU;
import com.ucams.common.service.BaseService;
import com.ucams.common.utils.DateUtils;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.FTPPointUtil;
import com.ucams.common.utils.FileUtils;
import com.ucams.common.utils.IdGen;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.utils.UcamsTools;
import com.ucams.common.utils.FTPPointUtil.UploadStatus;
import com.ucams.modules.ams.dao.AmsAcceptanceArchivesDao;
import com.ucams.modules.ams.dao.AmsAcceptanceDao;
import com.ucams.modules.ams.dao.AmsArchivesInfoDao;
import com.ucams.modules.ams.dao.AmsDesExtendDao;
import com.ucams.modules.ams.dao.AmsFileInfoDao;
import com.ucams.modules.ams.dao.AmsGenreDao;
import com.ucams.modules.ams.dao.AmsPreRptDao;
import com.ucams.modules.ams.dao.AmsProjectInfoDao;
import com.ucams.modules.ams.dao.AmsTransferDao;
import com.ucams.modules.ams.dao.AmsUnitProInfoDao;
import com.ucams.modules.ams.entity.AmsAcceptance;
import com.ucams.modules.ams.entity.AmsAcceptanceArchives;
import com.ucams.modules.ams.entity.AmsArchivesFiles;
import com.ucams.modules.ams.entity.AmsArchivesInfo;
import com.ucams.modules.ams.entity.AmsDesExtend;
import com.ucams.modules.ams.entity.AmsDesProgram;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsGenre;
import com.ucams.modules.ams.entity.AmsPreRpt;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.ams.entity.AmsTransfer;
import com.ucams.modules.ams.entity.AmsTransferArchives;
import com.ucams.modules.ams.entity.AmsUnitProInfo;
import com.ucams.modules.ams.form.ArchivesInfoForm;
import com.ucams.modules.ams.form.FileInfoForm;
import com.ucams.modules.ams.form.GenreForm;
import com.ucams.modules.ams.form.ProjectInfoForm;
import com.ucams.modules.ams.form.SingleProjectForm;
import com.ucams.modules.ams.utils.CheckFileUtils;
import com.ucams.modules.sys.dao.OfficeDao;
import com.ucams.modules.sys.dao.RoleDao;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.service.SystemService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 在线上报数据接口Service
 * @author BQL
 * @version 2017-07-07
 */
@Service
public class AmsInterfaceService {

	
	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AmsGenreDao amsGenreDao;
	@Autowired
	private AmsProjectInfoDao amsProjectInfoDao;
	@Autowired
	private AmsUnitProInfoDao amsUnitProInfoDao;
	@Autowired
	private AmsFileInfoDao amsFileInfoDao;
	@Autowired
	private AmsDesExtendDao amsDesExtendDao;
	@Autowired
	private AmsArchivesInfoDao amsArchivesInfoDao;
	@Autowired
	private AmsTransferDao amsTransferDao;
	@Autowired
	private AmsAcceptanceDao amsAcceptanceDao;
	@Autowired
	private AmsAcceptanceArchivesDao amsAcceptanceArchivesDao;
	@Autowired
	private AmsPreRptDao amsPreRptDao;
	@Autowired
	private DBOperationsU dbOperationU;
	
	@Transactional(readOnly = true)
	public Map<String, Object> findUserProjectJson(String userName,String password){
		Map<String, Object> result = Maps.newHashMap();
		// 验证用户名密码是否为空
		if (EntityUtils.isEmpty(userName)||EntityUtils.isEmpty(password)) {
			result.put("result", "nulldata");
			return result;
		}
		
		// 校验用户名密码
		User user = systemService.getUserByLoginName(userName);
		if(user==null||!systemService.validatePassword(password, user.getPassword())){
			result.put("result", "incorrect");
			return result;
		}
		
		//获取拥有权限项目及单位工程
		String dsf=BaseService.dataScopeFilter(user, "office", "pp");
		List<AmsProjectInfo> projectInfos=amsProjectInfoDao.findOfficeProjectList(dsf);
		List<AmsUnitProInfo> unitProInfos=amsUnitProInfoDao.findOfficeUnitProjectList(dsf);
		
		
		List<ProjectInfoForm> projectList = Lists.newArrayList();
		Map<String,List<SingleProjectForm>> singleProjectListMap = Maps.newHashMap();
		ProjectInfoForm projectInfoForm=null;
		SingleProjectForm singleProjectForm=null;
		List<SingleProjectForm> singleProjectForms=null;
		for(AmsUnitProInfo amsUnitProInfo:unitProInfos){
			if(EntityUtils.isNotEmpty(amsUnitProInfo.getTransfreId())) continue;
			singleProjectForm=new SingleProjectForm();
			BeanUtils.copyProperties(amsUnitProInfo, singleProjectForm);
			singleProjectForms=singleProjectListMap.get(amsUnitProInfo.getProjectId());
			if (singleProjectForms==null) {
				singleProjectForms=new ArrayList<SingleProjectForm>();
				singleProjectListMap.put(amsUnitProInfo.getProjectId(), singleProjectForms);
			}
			singleProjectForms.add(singleProjectForm);
		}
		for(AmsProjectInfo amsProjectInfo:projectInfos){
			projectInfoForm=new ProjectInfoForm();
			BeanUtils.copyProperties(amsProjectInfo, projectInfoForm);
			projectInfoForm.setSingleProjectList(singleProjectListMap.get(amsProjectInfo.getId()));
			projectList.add(projectInfoForm);
		}
		//获取用户角色归档文件列表
		Role userRole=user.getRoleList().get(0);
		List<GenreForm> genreForms=new ArrayList<GenreForm>();
		if (userRole!=null&&EntityUtils.isNotEmpty(userRole.getRoleType())) {
			GenreForm genreForm=null;
			List<AmsGenre> amsGenres=amsGenreDao.findRoleGenreList(userRole.getRoleType());
			Map<String, GenreForm> genreMap=new HashMap<String, GenreForm>();
			StringBuffer genreIds=new StringBuffer();
			for(AmsGenre amsGenre:amsGenres){
				genreForm=new GenreForm();
				BeanUtils.copyProperties(amsGenre, genreForm);
				if(userRole.getRoleType().equals(genreForm.getCreateUnit())){
					genreIds.append(genreForm.getParentIds()+genreForm.getId()+",");
				}
				genreMap.put(genreForm.getId(), genreForm);
			}
			Iterator<String> iterator = new TreeSet(Arrays.asList(genreIds.toString().split(","))).iterator();
			while(iterator.hasNext()){
				genreForm=genreMap.get(iterator.next());
				if(genreForm!=null) genreForms.add(genreForm);
			}
			
		}
		
		result.put("result", "success");
		result.put("projectInfos", projectList);
		result.put("archiveRuless", genreForms);
		return result;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> findUserRoleByPassword(String userName,String password){
		Map<String, Object> result = Maps.newHashMap();
		// 验证用户名密码是否为空
		if (EntityUtils.isEmpty(userName)||EntityUtils.isEmpty(password)) {
			result.put("result", "nulldata");
			return result;
		}
		
		// 校验用户名密码
		User user = systemService.getUserByLoginName(userName);
		if(user==null||!systemService.validatePassword(password, user.getPassword())){
			result.put("result", "incorrect");
			return result;
		}
		Office office=officeDao.get(user.getOffice().getId());
		result.put("result", "success");
		result.put("office", office);
		result.put("role", user.getRoleList().get(0));
		
		return result;
	}
	
	/**
	 * 验证用户名密码,成功返回userId
	 * @param userName
	 * @param password
	 * @return
	 */
	@Transactional(readOnly = true)
	public String checkUserPassword(FileInfoForm fileInfoForm){
		// 验证用户名密码是否为空
		if (EntityUtils.isEmpty(fileInfoForm.getUserName())||EntityUtils.isEmpty(fileInfoForm.getPassword())) {
			return "nulldata";
		}
		// 校验用户名密码
		User user = systemService.getUserByLoginName(fileInfoForm.getUserName());
		if(user==null||!systemService.validatePassword(fileInfoForm.getPassword(), user.getPassword())){
			return "incorrect";
		}
		if(StringUtils.isEmpty(fileInfoForm.getProjectId())) return "errorprojectid";
		
//		List<AmsProjectInfo> projectInfos=amsProjectInfoDao.findOfficeProjectList(dsf);
		if(EntityUtils.isNotEmpty(fileInfoForm.getUnitProjectId())){
			//获取拥有权限项目及单位工程
			String dsf=BaseService.dataScopeFilter(user, "office", "pp");
			List<AmsUnitProInfo> unitProInfos=amsUnitProInfoDao.findOfficeUnitProjectList(dsf);
			for(AmsUnitProInfo amsUnitProInfo:unitProInfos){
				if (amsUnitProInfo.getId().equals(fileInfoForm.getUnitProjectId())) {
					if(EntityUtils.isNotEmpty(amsUnitProInfo.getTransfreId()))
					return "unitreport";
					break;
				}
			}
		}
		
		return user.getId();
	}
	/**
	 * 保存资料软件接口上传文件信息
	 * @param fileInfoForm
	 */
	@Transactional(readOnly = false)
	public void saveAmsFileInfo(FileInfoForm fileInfoForm) {
		
		AmsFileInfo amsFileInfo=amsFileInfoDao.get(fileInfoForm.getId());
		if(amsFileInfo==null){
			amsFileInfo=new AmsFileInfo();
			BeanUtils.copyProperties(fileInfoForm, amsFileInfo);
			if(EntityUtils.isNotEmpty(fileInfoForm.getExtendJson())){
				if (EntityUtils.isEmpty(amsFileInfo.getProjectId())) {
					amsFileInfo.setProjectId(amsUnitProInfoDao.get(amsFileInfo.getUnitProjectId()).getProjectId());
				}
				JSONObject jObject=new JSONObject();
				jObject.put("ams_project_info", amsFileInfo.getProjectId());
				jObject.put("ams_unit_pro_info", amsFileInfo.getUnitProjectId());
				jObject.put("ams_archives_info", amsFileInfo.getGroupId());
				jObject.put("ams_file_info", amsFileInfo.getId());
				jObject.put("data", JSON.parseObject(fileInfoForm.getExtendJson()));
				amsFileInfo.setFileJson(jObject.toJSONString());
			}
			amsFileInfo.setState("1");
			amsFileInfo.setCreateBy(new User(fileInfoForm.getCreateUser()));
			amsFileInfo.setCreateDate(new Date());
			amsFileInfo.setUpdateBy(amsFileInfo.getCreateBy());
			amsFileInfo.setUpdateDate(amsFileInfo.getCreateDate());
			amsFileInfoDao.insert(amsFileInfo);
		}else{
			BeanUtils.copyProperties(fileInfoForm, amsFileInfo);
			if(EntityUtils.isNotEmpty(fileInfoForm.getExtendJson())){
				if (EntityUtils.isEmpty(amsFileInfo.getProjectId())) {
					amsFileInfo.setProjectId(amsUnitProInfoDao.get(amsFileInfo.getUnitProjectId()).getProjectId());
				}
				JSONObject jObject=new JSONObject();
				jObject.put("ams_project_info", amsFileInfo.getProjectId());
				jObject.put("ams_unit_pro_info", amsFileInfo.getUnitProjectId());
				jObject.put("ams_archives_info", amsFileInfo.getGroupId());
				jObject.put("ams_file_info", amsFileInfo.getId());
				jObject.put("data", JSON.parseObject(fileInfoForm.getExtendJson()));
				amsFileInfo.setFileJson(jObject.toJSONString());
			}
			amsFileInfo.setUpdateBy(amsFileInfo.getCreateBy());
			amsFileInfo.setUpdateDate(amsFileInfo.getCreateDate());
			amsFileInfoDao.update(amsFileInfo);
		}
		
	}
	
	/**
	 * 检查离线包文件数据
	 * @param userName
	 * @param password
	 * @param unzipPath
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> checkUserOfflineFileData(String userName,String password,String unzipPath) throws Exception{
		Map<String, Object> result = Maps.newHashMap();
		result.put("result", "error");
		// 验证用户名密码是否为空
		if (EntityUtils.isEmpty(userName)||EntityUtils.isEmpty(password)) {
			result.put("data", "用户名密码不可为空");
			return result;
		}
		
		// 校验用户名密码
		User user = systemService.getUserByLoginName(userName);
		if(user==null||!systemService.validatePassword(password, user.getPassword())){
			result.put("data", "用户名密码错误");
			return result;
		}
		
		Role role=null;
		if(user.getRoleList()!=null){
			for (Role userRole : user.getRoleList()) {
				if("security-role".equals(userRole.getRoleType())){
					role=userRole;
					break;
				}
			}
			if(role==null){
				result.put("data", "非建设单位不可进行文件上传");
				return result;
			}
		}else{
			result.put("data", "角色异常");
			return result;
		}
		//json文件是否存在
		String jsonStr=FileUtils.readTxtFileContent(unzipPath+"/projectJsonData.txt");
		if(jsonStr==null){
			result.put("data", "项目信息文件不存在");
			return result;
		}
		JSONObject jsonObject=JSON.parseObject(jsonStr);
		//json文件数据是否存在
		ProjectInfoForm projectInfoForm=jsonObject.getObject("projectInfo",ProjectInfoForm.class);
		if(projectInfoForm==null){
			result.put("data", "项目信息数据不存在");
			return result;
		}
		if(EntityUtils.isEmpty(projectInfoForm.getPlanningLicenseNumber())){
			result.put("data", "规划许可证号为空");
			return result;
		}
		List<AmsProjectInfo> amsProjectInfos=amsProjectInfoDao.findListByPlanningLicenseNumber(projectInfoForm.getPlanningLicenseNumber());
		if(amsProjectInfos.size()==0){
			result.put("data", "项目未报建");
			return result;
		}
		if(amsProjectInfos.size()>1){
			result.put("data", "规划许可证号重复");
			return result;
		}
		AmsProjectInfo amsProjectInfo=amsProjectInfos.get(0);
		
		if(!role.getId().equals(amsProjectInfo.getConstructionId())){
			result.put("data", "规划许可证号已占用");
			return result;
		}
		if("0".equals(amsProjectInfo.getCheckStatus())){
			result.put("data", "项目报建未审核");
			return result;
		}
		if("2".equals(amsProjectInfo.getCheckStatus())){
			result.put("data", "项目未通过审核");
			return result;
		}
//		List<String> unitProjectIds=amsUnitProInfoDao.findUnitProjectByProjectId(amsProjectInfo.getId());
//		for(SingleProjectForm singleProjectForm: projectInfoForm.getSingleProjectList()){
//			if (unitProjectIds.contains(singleProjectForm.getId())) {
//				result.put("data", "离线包单位工程已存在");
//				return result;
//			}
//		}
		
		try {
			List<AmsPreRpt> checkResults = checkOfflineFile(jsonStr, unzipPath);
			UserUtils.getSession().setAttribute(UcamsConstant.OfflineZipCheckData, checkResults);
//			result.put("data", checkResults);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("data", "离线包文件检查失败");
			return result;
		}
		jsonObject.put("projectId", amsProjectInfo.getId());
		UserUtils.getSession().setAttribute(UcamsConstant.OfflineZipJsonData, jsonObject.toString());
		result.put("result", "success");
//		AmsUnitProInfo amsUnitProInfo=new AmsUnitProInfo();
//		amsUnitProInfo.setProjectId(amsProjectInfo.getId());
//		List<AmsUnitProInfo> amsUnitProInfos=amsUnitProInfoDao.findList(amsUnitProInfo);
//		ProjectInfoForm infoForm=new ProjectInfoForm();
//		BeanUtils.copyProperties(amsProjectInfo, infoForm);
//		infoForm.setSingleProjectList(new ArrayList<SingleProjectForm>());
//		SingleProjectForm singleProjectForm=null;
//		for(AmsUnitProInfo unitProInfo:amsUnitProInfos){
//			singleProjectForm=new SingleProjectForm();
//			BeanUtils.copyProperties(unitProInfo, singleProjectForm);
//			infoForm.getSingleProjectList().add(singleProjectForm);
//		}
//		result.put("target", infoForm);
		return result;
	}
	/**
	 * 检查离线包文件数据
	 * @param jsonData
	 * @param unzipPath
	 * @return
	 */
	public List<AmsPreRpt> checkOfflineFile(String jsonData,String unzipPath) throws Exception{
		
		List<AmsPreRpt> checkResults = new ArrayList<AmsPreRpt>();
		AmsPreRpt amsPreRpt=null;
		if (EntityUtils.isEmpty(jsonData)) {
			jsonData=(String) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipJsonData);
		}
		JSONObject jsonObject=JSON.parseObject(jsonData);
		//获取工程项目信息
		ProjectInfoForm projectInfoForm=jsonObject.getObject("projectInfo",ProjectInfoForm.class);
		List<ArchivesInfoForm> archivesInfoForms=jsonObject.getJSONArray("archivesinfos").toJavaList(ArchivesInfoForm.class);
		List<FileInfoForm> fileInfoForms=jsonObject.getJSONArray("fileinfos").toJavaList(FileInfoForm.class);
		//单位工程名称Map
		Map amsUnitProMap=new HashMap();
		amsUnitProMap.put("", "项目下案卷");
		//案卷位置Map
		Map amsArchivesMap=new HashMap();
		//检查工程项目信息
		List<String> results = null;
		AmsProjectInfo amsProjectInfo=new AmsProjectInfo();
		BeanUtils.copyProperties(projectInfoForm, amsProjectInfo);
		results = CheckFileUtils.checkObjFieldIsNull(amsProjectInfo);
		
		for (String error:results) {
			amsPreRpt=new AmsPreRpt(CommonConstant.AMS_COMMON_PARAM_NUM00, error+"(空)", CommonConstant.AMS_COMMON_PARAM_NUM00, CommonConstant.AMS_COMMON_PARAM_NUM00);
			checkResults.add(amsPreRpt);
		}
//		//工程项目附件文件检查
		String fileRes=null;
		File file=null;
//		results.clear();
//		file=new File(unzipPath,amsProjectInfo.getApprovalUrl());
//		if(!file.exists()) results.add("立项批准附件文件不存在");
//		else if(!"success".equals(fileRes =  CheckFileUtils.checkImage(file))){
//			results.add("立项批准附件"+fileRes);   //如果文件检测有问题则,加入检测报告
//		}
//		file=new File(unzipPath,amsProjectInfo.getPlanningLicenseUrl());
//		if(!file.exists()) results.add("项目规划附件文件不存在");
//		else if(!"success".equals(fileRes =  CheckFileUtils.checkImage(file))){
//			results.add("项目规划附件"+fileRes);   //如果文件检测有问题则,加入检测报告
//		}
//		file=new File(unzipPath,amsProjectInfo.getLandLicenseUrl());
//		if(!file.exists()) results.add("用地规划附件文件不存在");
//		else if(!"success".equals(fileRes =  CheckFileUtils.checkImage(file))){
//			results.add("用地规划附件"+fileRes);   //如果文件检测有问题则,加入检测报告
//		}
//		file=new File(unzipPath,amsProjectInfo.getLandPermitUrl());
//		if(!file.exists()) results.add("用地许可证附件文件不存在");
//		else if(!"success".equals(fileRes =  CheckFileUtils.checkImage(file))){
//			results.add("用地许可证附件"+fileRes);   //如果文件检测有问题则,加入检测报告
//		}
//		for (String error:results) {
//			amsPreRpt=new AmsPreRpt(CommonConstant.AMS_COMMON_PARAM_NUM00, error, CommonConstant.AMS_COMMON_PARAM_NUM00, CommonConstant.AMS_COMMON_PARAM_NUM00);
//			checkResults.add(amsPreRpt);
//		}
		
		//检查单位工程信息
		AmsUnitProInfo amsUnitProInfo=null;
		for(SingleProjectForm singleProjectForm:projectInfoForm.getSingleProjectList()){
			amsUnitProInfo=new AmsUnitProInfo();
			amsUnitProMap.put(singleProjectForm.getId(), singleProjectForm.getUnitProjectName());
			BeanUtils.copyProperties(singleProjectForm, amsUnitProInfo);
			results = CheckFileUtils.checkObjFieldIsNull(amsUnitProInfo);
			//检查单位工程拓展信息
			if(EntityUtils.isNotEmpty(singleProjectForm.getSpecialtyJson())){
			//从拓展信息管理中拿出对对应数据项
			List<AmsDesExtend> amsDesExtend = this.getDesExtendNotNull(UcamsConstant.AMS_DESPROGRAM_ZYJZ, amsUnitProInfo.getUnitProjectType());
			Map gomap = JSON.parseObject(singleProjectForm.getSpecialtyJson(),Map.class); //取出当前单位工程的拓展数据
				for(AmsDesExtend extend : amsDesExtend){
					if(EntityUtils.isEmpty(gomap.get(extend.getName()))){  //查出拓展数据中value为空的项
					 results.add(extend.getComments());  					      //将描述存入报告
					}
				}
			}else{
				//该单位工程没有专业记载
				results.add("专业记载数据");
			}
			
			for (String error:results) {
				amsPreRpt=new AmsPreRpt(CommonConstant.AMS_COMMON_PARAM_NUM00, amsUnitProInfo.getUnitProjectName()+"-"+error+"(空)", CommonConstant.AMS_COMMON_PARAM_NUM00, CommonConstant.AMS_COMMON_PARAM_NUM00);
				checkResults.add(amsPreRpt);
			}
		}
		//检查案卷信息
		AmsArchivesInfo amsArchivesInfo=null;
		for(ArchivesInfoForm archivesInfoForm:archivesInfoForms){
			
			amsArchivesInfo=new AmsArchivesInfo();
			amsArchivesMap.put(archivesInfoForm.getId(), amsUnitProMap.get(archivesInfoForm.getUnitProjectId())+"-"+archivesInfoForm.getArchivesName());
			BeanUtils.copyProperties(archivesInfoForm, amsArchivesInfo);
			results = CheckFileUtils.checkObjFieldIsNull(amsArchivesInfo);
			//检查案卷拓展信息
			if(EntityUtils.isNotEmpty(archivesInfoForm.getArchivesJson())){
			//从拓展信息管理中拿出对对应数据项
			List<AmsDesExtend> amsDesExtend = this.getDesExtendNotNull(UcamsConstant.AMS_DESPROGRAM_AJXX, "");
			Map gomap = JSON.parseObject(archivesInfoForm.getArchivesJson(),Map.class); //取出当前案卷的拓展数据
				for(AmsDesExtend extend : amsDesExtend){
					if(EntityUtils.isEmpty(gomap.get(extend.getName()))){  //查出拓展数据中value为空的项
						results.add(extend.getComments());  					      //将描述存入报告
					}
				}
			}else{
				//该单位工程没有专业记载
				results.add("拓展数据");
			}
			for (String error:results) {
				amsPreRpt=new AmsPreRpt(CommonConstant.AMS_COMMON_PARAM_NUM00, amsUnitProMap.get(archivesInfoForm.getUnitProjectId())+"-"+archivesInfoForm.getArchivesName()+"-"+error+"(空)", CommonConstant.AMS_COMMON_PARAM_NUM00, CommonConstant.AMS_COMMON_PARAM_NUM00);
				checkResults.add(amsPreRpt);
			}
		}
		
		List<AmsGenre> amsGenreList=amsGenreDao.findGenreFileList(amsProjectInfo.getProjectType());
		List<String> fileIds=new ArrayList<String>();
		for(FileInfoForm fileInfoForm:fileInfoForms){
			fileIds.add(fileInfoForm.getRecordId());
		}
		for(AmsGenre amsGenre:amsGenreList){
			if (!fileIds.contains(amsGenre.getId())) {
				results.add("归档一览表中 :" + amsGenre.getName() + " 无文件");
			}
		}
		
		AmsFileInfo amsFileInfo=null;
		for(FileInfoForm fileInfoForm:fileInfoForms){
			
			amsFileInfo=new AmsFileInfo();
			BeanUtils.copyProperties(fileInfoForm, amsFileInfo);
			results = CheckFileUtils.checkObjFieldIsNull(amsFileInfo);
			//检查案卷拓展信息
			if(EntityUtils.isNotEmpty(fileInfoForm.getExtendJson())){
			//从拓展信息管理中拿出对对应数据项
			List<AmsDesExtend> amsDesExtend = this.getDesExtendNotNull(UcamsConstant.AMS_DESPROGRAM_WJZL, "");
			Map gomap = JSON.parseObject(fileInfoForm.getExtendJson(),Map.class); //取出拓展数据
				for(AmsDesExtend extend : amsDesExtend){
					if(EntityUtils.isEmpty(gomap.get(extend.getName()))){  //查出拓展数据中value为空的项
					 results.add(extend.getComments());  					      //将描述存入报告
					}
				}
			}else{
				//该单位工程没有专业记载
				results.add("拓展数据");
			}
			for (String error:results) {
				amsPreRpt=new AmsPreRpt(CommonConstant.AMS_COMMON_PARAM_NUM00, amsArchivesMap.get(fileInfoForm.getGroupId())+"-"+fileInfoForm.getFileName()+"-"+error+"(空)", CommonConstant.AMS_COMMON_PARAM_NUM00, CommonConstant.AMS_COMMON_PARAM_NUM00);
				checkResults.add(amsPreRpt);
			}
			
			 //---------------------------------------检查物理文件------------------------------------//
			 
			amsPreRpt=new AmsPreRpt(CommonConstant.AMS_COMMON_PARAM_NUM01, null, CommonConstant.AMS_COMMON_PARAM_NUM00, CommonConstant.AMS_COMMON_PARAM_NUM00);
			//filename=filepath
			file = new File(unzipPath+amsFileInfo.getFilePath());
			if(file.exists()){
				if("1".equals(amsFileInfo.getFileType())){  //pdf文件
					fileRes =  CheckFileUtils.checkPdf(new FileInputStream(file), amsFileInfo.getFilecount());
					if(EntityUtils.isNotEmpty(fileRes)){
						//如果pdf文件检测有问题则,加入检测报告
						amsPreRpt.setError(amsArchivesMap.get(fileInfoForm.getGroupId())+"-"+fileInfoForm.getFileName()+"-"+fileRes);
					}
				}else if ("2".equals(amsFileInfo.getFileType())) {//图片文件
					if(!"success".equals(fileRes =  CheckFileUtils.checkImage(new FileInputStream(file)))){
						//如果pdf文件检测有问题则,加入检测报告
						amsPreRpt.setError(amsArchivesMap.get(fileInfoForm.getGroupId())+"-"+fileInfoForm.getFileName()+"-"+fileRes);
					}
				}
				
			}else{
				amsPreRpt.setError(amsArchivesMap.get(fileInfoForm.getGroupId())+"-"+fileInfoForm.getFileName()+"-没有文件");
			}
			checkResults.add(amsPreRpt);
		}
			
		return checkResults;
	}
	
	/**
	 * 保存项目信息
	 * @param projectId
	 * @param matchedData
	 * @param jsonData
	 * @return
	 */
	@Transactional(readOnly = false)
	public Boolean saveOfflineProjectInfo(StringBuffer message) throws Exception{
		
		
		String filename=(String) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipFile);
    	filename=filename.substring(0, filename.lastIndexOf("."));
		String jsonData=(String) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipJsonData);
		JSONObject jsonObject=JSON.parseObject(jsonData);
		//获取工程项目信息
		String projectId =jsonObject.getObject("projectId",String.class);
		ProjectInfoForm projectInfoForm=jsonObject.getObject("projectInfo",ProjectInfoForm.class);
		List<ArchivesInfoForm> archivesInfoForms=jsonObject.getJSONArray("archivesinfos").toJavaList(ArchivesInfoForm.class);
		List<FileInfoForm> fileInfoForms=jsonObject.getJSONArray("fileinfos").toJavaList(FileInfoForm.class);
		
		message.delete(0, message.length());
		message.append("工程信息导入失败！");
		//工程信息保存
		AmsProjectInfo amsProjectInfo=amsProjectInfoDao.get(projectId);
		if("1".equals(amsProjectInfo.getExten1())&&"0".equals(amsProjectInfo.getCheckStatus())){//首次报建未审核工程项目
			Role role=roleDao.get(amsProjectInfo.getConstructionId());
			//增加责任主体权限--动态菜单--默认roleId为100
			Role roleMenu=roleDao.get("100");
			role.setMenuList(roleMenu.getMenuList());
			role.setUpdateDate(new Date());
			systemService.saveRole(role);
			amsProjectInfo.setCheckStatus("1");
		}
		
		BeanUtils.copyProperties(projectInfoForm, amsProjectInfo);
		amsProjectInfo.setId(projectId);
		amsProjectInfoDao.update(amsProjectInfo);
		
		message.delete(0, message.length());
		message.append("单位工程信息导入失败！");
		//单位工程信息保存
		List<String> unitProjectIds=amsUnitProInfoDao.findUnitProjectByProjectId(amsProjectInfo.getId());
		AmsUnitProInfo amsUnitProInfo=null;
		Office office=null;
		Office ofparent=officeDao.get(amsProjectInfo.getId());
		if (!amsProjectInfo.getProjectName().equals(projectInfoForm.getProjectName())) {
			ofparent.setName(projectInfoForm.getProjectName());
			officeDao.update(ofparent);
		}
		for(SingleProjectForm singleProjectForm:projectInfoForm.getSingleProjectList()){
			if(unitProjectIds.contains(singleProjectForm.getId())) continue;   //检索单位工程ID是否已存在
			amsUnitProInfo=new AmsUnitProInfo();
			BeanUtils.copyProperties(singleProjectForm, amsUnitProInfo);
			amsUnitProInfo.setProjectId(amsProjectInfo.getId());
			amsUnitProInfo.setCreateBy(UserUtils.getUser());
			amsUnitProInfo.setCreateDate(new Date());
			amsUnitProInfo.setUpdateBy(UserUtils.getUser());
			amsUnitProInfo.setUpdateDate(new Date());
			//单位工程编号设置
			String  pno= "";
			AmsUnitProInfo unitProInfo = amsUnitProInfoDao.getUnitProjectByNo();
			if(EntityUtils.isNotEmpty(unitProInfo) && EntityUtils.isNotEmpty(unitProInfo.getUnitProjectNo())){
				pno=String.valueOf(Integer.parseInt(unitProInfo.getUnitProjectNo())+1);
			}else{
				pno="10001";
			}
			amsUnitProInfo.setUnitProjectNo("UM"+DateUtils.formatDate(amsUnitProInfo.getStartDate()!=null?amsUnitProInfo.getStartDate():new Date())+pno);
			amsUnitProInfoDao.insert(amsUnitProInfo);
			office=new Office();
			office.setId(amsUnitProInfo.getId());
			office.setParent(ofparent);
			office.setParentIds(ofparent.getParentIds()+amsUnitProInfo.getProjectId()+",");
			office.setName(amsUnitProInfo.getUnitProjectName());
			office.setArea(ofparent.getArea());
			office.setType("2");
			office.setGrade("4");
			office.setUseable(Global.YES);
			office.setUpdateBy(ofparent.getCreateBy());
			office.setUpdateDate(new Date());
			office.setCreateBy(ofparent.getCreateBy());
			office.setCreateDate(new Date());
			officeDao.insert(office);
			//角色权限
			Role role=new Role(amsProjectInfo.getConstructionId());
			role.getOfficeList().add(office);
			roleDao.insertRoleOffice(role);
			//专业记载信息保存
			JSONObject jObject=new JSONObject();
			jObject.put(UcamsConstant.AMS_MONGO_IDS_PROJECT, amsUnitProInfo.getProjectId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_UNIT, amsUnitProInfo.getId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_DATA, JSON.parseObject(amsUnitProInfo.getSpecialtyJson()));
			//mongodb数据保存
//			dbOperationU.save(jObject,UcamsConstant.AMS_MONGO_TABLE_NAME_UNIT);
		}
		
		List<String> archivesIds=amsArchivesInfoDao.findArchivesInfoIdByProject(amsProjectInfo.getId());
		AmsArchivesInfo amsArchivesInfo=null;
		for(ArchivesInfoForm archivesInfoForm:archivesInfoForms){
			//验证已导入单位工程和项目案卷
			if(unitProjectIds.contains(archivesInfoForm.getUnitProjectId())) continue;
			if("-1".equals(archivesInfoForm.getUnitProjectId())&&archivesIds.contains(archivesInfoForm.getId())) continue;
			message.delete(0, message.length());
			message.append("案卷信息导入失败！案卷名称："+archivesInfoForm.getArchivesName());
			amsArchivesInfo=new AmsArchivesInfo();
			BeanUtils.copyProperties(archivesInfoForm, amsArchivesInfo);
			amsArchivesInfo.setProjectId(amsProjectInfo.getId());
			amsArchivesInfo.setUpdateBy(ofparent.getCreateBy());
			amsArchivesInfo.setUpdateDate(new Date());
			amsArchivesInfo.setCreateBy(ofparent.getCreateBy());
			amsArchivesInfo.setCreateDate(new Date());
			amsArchivesInfoDao.insert(amsArchivesInfo);
			JSONObject jObject=new JSONObject();
			jObject.put(UcamsConstant.AMS_MONGO_IDS_PROJECT, amsArchivesInfo.getProjectId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_UNIT, amsArchivesInfo.getUnitProjectId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_ARCHIVES, amsArchivesInfo.getId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_DATA, JSON.parseObject(amsArchivesInfo.getArchivesJson()));
			//mongodb数据保存
//			dbOperationU.save(jObject,UcamsConstant.AMS_MONGO_TABLE_NAME_UNIT);
		}
		
		
		AmsFileInfo amsFileInfo=null;
		File file =null;
		String unzipPath = UcamsConstant.UnZipFilePath+"/"+filename;
		
		for(FileInfoForm fileInfoForm:fileInfoForms){
			if(unitProjectIds.contains(fileInfoForm.getUnitProjectId())) continue;
			message.delete(0, message.length());
			message.append("文件信息导入失败！文件名称"+fileInfoForm.getFileName());
			amsFileInfo=new AmsFileInfo();
			BeanUtils.copyProperties(fileInfoForm, amsFileInfo);
			amsFileInfo.setState("1");
			amsFileInfo.setProjectId(amsProjectInfo.getId());
			amsFileInfo.setFormDate(amsFileInfo.getFormDate());
			amsFileInfo.setUpdateBy(ofparent.getCreateBy());
			amsFileInfo.setUpdateDate(new Date());
			amsFileInfo.setCreateBy(ofparent.getCreateBy());
			amsFileInfo.setCreateDate(new Date());
			
			file = new File(unzipPath+amsFileInfo.getFilePath());
			//组织文件存储路径
			StringBuffer filePath = new StringBuffer();
			filePath.append("projectFile/" +  amsFileInfo.getProjectId()); 
			//若有单位工程ID
			if(EntityUtils.isNotEmpty(amsFileInfo.getUnitProjectId())){
				filePath.append("/" + amsFileInfo.getUnitProjectId());
			}
			filePath.append("/" + UcamsTools.createNewFileName(file));
			//链接FTP服务 -- 在线业务
			FTPPointUtil ftp = new FTPPointUtil("tadmin", "admin");
			//链接FTP服务
			ftp.connect();
			//FTP服务上传
			UploadStatus status  = ftp.upload(file, filePath.toString());
			//断开FTP服务
			ftp.disconnect();
			//判断上传结果,不成功
			if(!FTPPointUtil.UploadStatus.Upload_New_File_Success.equals(status)){
				message.delete(0, message.length());
				message.append("文件上传失败！文件名称"+fileInfoForm.getFileName());
				throw new IOException("文件管理-文件上传-文件上传失败!");
			}
			//修改文件路径
			amsFileInfo.setFilePath(filePath.toString());
			
			amsFileInfoDao.insert(amsFileInfo);
			AmsArchivesFiles amsArchivesFiles=new AmsArchivesFiles();
			BeanUtils.copyProperties(fileInfoForm, amsArchivesFiles);
			amsArchivesFiles.setGroupId(fileInfoForm.getGroupId());
			amsArchivesFiles.setRecordId(fileInfoForm.getId());
			amsArchivesFiles.setUpdateBy(ofparent.getCreateBy());
			amsArchivesFiles.setUpdateDate(new Date());
			amsArchivesFiles.setCreateBy(ofparent.getCreateBy());
			amsArchivesFiles.setCreateDate(new Date());
			amsArchivesInfoDao.addFile(amsArchivesFiles);
			
			JSONObject jObject=new JSONObject();
			jObject.put(UcamsConstant.AMS_MONGO_IDS_PROJECT, amsFileInfo.getProjectId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_UNIT, amsFileInfo.getUnitProjectId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_ARCHIVES, amsFileInfo.getGroupId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_FILE, amsFileInfo.getId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_DATA, JSON.parseObject(amsFileInfo.getFileJson()));
			//mongodb数据保存
//			dbOperationU.save(jObject,UcamsConstant.AMS_MONGO_TABLE_NAME_File);
			
		}
		//删除zip包解压解压文件
		new File(unzipPath).delete();
		
		message.delete(0, message.length());
		message.append("案卷文件信息导入失败！");
		AmsAcceptance acceptance=new AmsAcceptance();
		acceptance.setId(IdGen.uuid());
		acceptance.setProject(amsProjectInfo);
		acceptance.setRole(new Role(amsProjectInfo.getConstructionId()));
		acceptance.setUser(ofparent.getCreateBy());
		acceptance.setUser2(UserUtils.getUser());
		acceptance.setUser3(UserUtils.getUser());
		acceptance.setStatus("3");
		acceptance.setPreAcceptanceApplyDate(new Date());
		acceptance.setUpdateBy(ofparent.getCreateBy());
		acceptance.setUpdateDate(new Date());
		acceptance.setCreateBy(ofparent.getCreateBy());
		acceptance.setCreateDate(new Date());
		amsAcceptanceDao.insert(acceptance);
		// 插入关系表
		AmsAcceptanceArchives acceptanceArchives;
		for(SingleProjectForm singleProjectForm:projectInfoForm.getSingleProjectList()){
			if(unitProjectIds.contains(singleProjectForm.getId())) continue;   //检索单位工程ID是否已存在
			acceptanceArchives=new AmsAcceptanceArchives();
			acceptanceArchives.setId(IdGen.uuid());
			acceptanceArchives.setProjectId(amsProjectInfo.getId());
			acceptanceArchives.setAcceptanceId(acceptance.getId());
			acceptanceArchives.setUnitProjectId(singleProjectForm.getId());
			acceptanceArchives.setArchiveId("-1");
			acceptanceArchives.setUpdateBy(ofparent.getCreateBy());
			acceptanceArchives.setUpdateDate(new Date());
			acceptanceArchives.setCreateBy(ofparent.getCreateBy());
			acceptanceArchives.setCreateDate(new Date());
			amsAcceptanceArchivesDao.insert(acceptanceArchives);
		}
		for(ArchivesInfoForm archivesInfoForm:archivesInfoForms){
			if(unitProjectIds.contains(archivesInfoForm.getUnitProjectId())||!"".equals(archivesInfoForm.getUnitProjectId().trim())) continue;
			acceptanceArchives=new AmsAcceptanceArchives();
			acceptanceArchives.setId(IdGen.uuid());
			acceptanceArchives.setProjectId(amsProjectInfo.getId());
			acceptanceArchives.setAcceptanceId(acceptance.getId());
			acceptanceArchives.setUnitProjectId("-1");
			acceptanceArchives.setArchiveId(archivesInfoForm.getId());
			acceptanceArchives.setUpdateBy(ofparent.getCreateBy());
			acceptanceArchives.setUpdateDate(new Date());
			acceptanceArchives.setCreateBy(ofparent.getCreateBy());
			acceptanceArchives.setCreateDate(new Date());
			amsAcceptanceArchivesDao.insert(acceptanceArchives);
		}
		AmsTransfer amsTransfer=new AmsTransfer();
		amsTransfer.setId(IdGen.uuid());
		amsTransfer.setProject(amsProjectInfo);
		amsTransfer.setRole(new Role(amsProjectInfo.getConstructionId()));
		amsTransfer.setTransferApplicatonDate(new Date());
		amsTransfer.setEstimateTransferDate(new Date());
		amsTransfer.setUser(ofparent.getCreateBy());
		amsTransfer.setUser2(UserUtils.getUser());
		amsTransfer.setUser3(UserUtils.getUser());
		amsTransfer.setStatus("3");
		amsTransfer.setUpdateBy(ofparent.getCreateBy());
		amsTransfer.setUpdateDate(new Date());
		amsTransfer.setCreateBy(ofparent.getCreateBy());
		amsTransfer.setCreateDate(new Date());
		amsTransferDao.insert(amsTransfer);
		AmsTransferArchives transferArchives = new AmsTransferArchives();
		transferArchives = new AmsTransferArchives();
		transferArchives.setId(IdGen.uuid());
		transferArchives.setProjectid(amsProjectInfo.getId());
		transferArchives.setTransferId(amsTransfer.getId());
		transferArchives.setAcceptance(acceptance);
		
		amsTransferDao.insertAmsTransferArchives(transferArchives);
		
		message.delete(0, message.length());
		message.append("检查报告导入失败！");
		List<AmsPreRpt> amsPreRptList=(List<AmsPreRpt>) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipCheckData);
		for(AmsPreRpt amsPreRpt:amsPreRptList){
			amsPreRpt.setId(IdGen.uuid());
			amsPreRpt.setTransferId(acceptance.getId());
			amsPreRpt.setCreateBy(ofparent.getCreateBy());
			amsPreRpt.setCreateDate(new Date());
			amsPreRpt.setUpdateBy(ofparent.getCreateBy());
			amsPreRpt.setUpdateDate(new Date());
			amsPreRptDao.insert(amsPreRpt);
		}
		
		return true;
	}
	
	/**
	 * 获取相应的拓展信息
	 * @param programType 方案类型
	 * @param unitProType 单位工程类型
	 * @return
	 */
	public List<AmsDesExtend> getDesExtendNotNull(String programType,String unitProType){
		//获取非空数据项
		List<AmsDesExtend> extendList = null;
		try {
			AmsDesProgram amsDesProgram = new AmsDesProgram();
			amsDesProgram.setProgramType(programType);
			amsDesProgram.setUnitProjectType(unitProType);
			amsDesProgram.setUseable("1");
			AmsDesExtend extend = new AmsDesExtend();
			extend.setAmsDesProgram(amsDesProgram);
			extendList = amsDesExtendDao.findByUpType(extend);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return extendList;
	}
}