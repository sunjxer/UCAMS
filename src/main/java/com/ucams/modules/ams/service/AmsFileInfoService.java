/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.dao.AmsArchiveMenuDao;
import com.ucams.modules.ams.dao.AmsArchiveRulesDao;
import com.ucams.modules.ams.dao.AmsDesExtendDao;
import com.ucams.modules.ams.dao.AmsFileInfoDao;
import com.ucams.modules.ams.dao.AmsGenreDao;
import com.ucams.modules.ams.dao.AmsProjectInfoDao;
import com.ucams.modules.ams.dao.AmsUnitDetailinfoDao;
import com.ucams.modules.ams.dao.AmsUnitProInfoDao;
import com.ucams.modules.ams.entity.AmsArchiveMenu;
import com.ucams.modules.ams.entity.AmsArchiveRules;
import com.ucams.modules.ams.entity.AmsDesExtend;
import com.ucams.modules.ams.entity.AmsDesProgram;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsGenre;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.entity.AmsUnitProInfo;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 文件管理Service
 * @author zkx
 * @version 2017-06-23
 */
@Service
@Transactional(readOnly = true)
public class AmsFileInfoService extends CrudService<AmsFileInfoDao, AmsFileInfo> {
	@Autowired
	private AmsFileInfoDao amsFileInfoDao;
	@Autowired
	private AmsUnitDetailinfoDao amsUnitDetailinfoDao;
	@Autowired
	private AmsArchiveRulesDao amsArchiveRulesDao;
	@Autowired
	private AmsProjectInfoDao amsProjectInfoDao;
	@Autowired
	private AmsUnitProInfoDao amsUnitProInfoDao;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AmsGenreDao amsGenreDao;
	@Autowired
	private AmsDesExtendDao amsDesExtendDao;
	@Autowired
	private AmsArchiveMenuDao amsArchiveMenuDao;
	@Autowired
	private DBOperationsU dbOperationU;
	public AmsFileInfo get(String id) {
		return super.get(id);
	}
	public AmsFileInfo getVideo(String id) {
		return dao.getVideo(id);
	}
	public AmsArchiveRules getAmsArchiveRules(String id) {
		return amsArchiveRulesDao.get(id);
	}
	
	
	public List<AmsFileInfo> findList(AmsFileInfo amsFileInfo) {
		return super.findList(amsFileInfo);
	}
	public List<AmsDesExtend> findExtendDataList(AmsFileInfo amsFileInfo) { 
		AmsDesProgram amsDesProgram=new AmsDesProgram();
		AmsDesExtend amsDesExtend =new AmsDesExtend();
//		//获取的工程类型id  单位工程类型去掉
//		String type=amsUnitProInfoDao.get(amsFileInfo.getUnitProjectId()).getUnitProjectType();
//		amsDesProgram.setUnitProjectType(type);
		//文件类型编号
		amsDesProgram.setProgramType(UcamsConstant.AMS_DESPROGRAM_WJZL);  //方案类型
		amsDesExtend.setAmsEnginproTable(amsDesProgram);
		List<AmsDesExtend>  amsDesExtends=amsDesExtendDao.findExtendDataList(amsDesExtend);
		if(EntityUtils.isNotEmpty(amsDesExtends)&& amsDesExtends.size()>0&&amsFileInfo.getFileJson()!=null&&!"".equals(amsFileInfo.getFileJson())){
			JSONObject jsonObject=JSONObject.parseObject(amsFileInfo.getFileJson());
			for (int i = 0; i < amsDesExtends.size(); i++) {
				AmsDesExtend amsDesExtend1=amsDesExtends.get(i);
				JSONObject data=JSONObject.parseObject(jsonObject.getString("data"));
				amsDesExtend1.setSettings(data==null?"":data.getString(amsDesExtend1.getName()));
			}
		}
		return amsDesExtends;
	}
	
	public Page<AmsFileInfo> findPage(Page<AmsFileInfo> page, AmsFileInfo amsFileInfo) {
		return super.findPage(page, amsFileInfo);
	} 
	/**
	 * 查询声像档案
	 * @param page
	 * @param amsFileInfo
	 * @return
	 */
	public Page<AmsFileInfo> findVideoPage(Page<AmsFileInfo> page, AmsFileInfo amsFileInfo) {
		amsFileInfo.setPage(page);
		amsFileInfo.setCreateBy(UserUtils.getUser());
		page.setList(dao.findVideoList(amsFileInfo));
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
	 * 获得g归档一揽表
	 * @return
	 */
	public List<AmsGenre> fileFindList(AmsGenre amsGenre) {
		return amsGenreDao.fileFindList(amsGenre);
	}
	/**
	 * 获得声像归档目录
	 * @return
	 */
	public List<AmsArchiveMenu> fileFindList(AmsArchiveMenu amsArchiveMenu) {
		return amsArchiveMenuDao.fileMenuList(amsArchiveMenu);
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
		User user = UserUtils.getUser();
		List<Office> offices = officeService.onlyUnitPro(user.getOffice().getParent());
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
			List<AmsProjectInfo> list=amsProjectInfoDao.findProjectList(amsProjectInfo);
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
		User user = UserUtils.getUser();
		List<Office> offices = officeService.onlyUnitPro(user.getOffice().getParent());
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < offices.size(); i++) {
			//过滤机构，选择类型为3的就是项目;4是单位工程
			if ("4".equals(offices.get(i).getGrade())) {
				ids.add(offices.get(i).getId());
			}
		}
		if(ids.size() >0 ){
			amsUnitProInfo.setIds(ids);
			// 设置分页参数
			amsUnitProInfo.setPage(page);
			// 执行分页查询
			page.setList(amsUnitProInfoDao.findUnitProjectList(amsUnitProInfo));
		}
		return page;
	}
	/**
	 * 更改后功能去掉8.1
	 * 查询归档信息列表
	 * @param page
	 * @param amsArchiveRules
	 * @return
	 *//*
	public List<AmsArchiveRules> findAmsArchiveRules(AmsArchiveRules amsArchiveRules) {
		return amsFileInfoDao.findAmsArchiveRules(amsArchiveRules);
	}*/
	@Transactional(readOnly = false)
	public void save(AmsFileInfo amsFileInfo) {
		List<AmsDesExtend> amsDesExtends=amsFileInfo.getAmsDesExtendList();
		Boolean isInsert=true;
		if (amsFileInfo.getIsNewRecord()){
			amsFileInfo.preInsert();	
			amsFileInfo.setState("0");//在线添加
		}else {
			amsFileInfo.preUpdate();
			isInsert=false;
		}
		JSONObject jObject=new JSONObject();
		if(amsDesExtends!=null && amsDesExtends.size()>0){
			String unitProId=amsFileInfo.getUnitProjectId();
			String projectId=amsUnitProInfoDao.get(amsFileInfo.getUnitProjectId()).getProjectId();
			jObject.put(UcamsConstant.AMS_MONGO_IDS_PROJECT, projectId);
			jObject.put(UcamsConstant.AMS_MONGO_IDS_UNIT, unitProId);
			jObject.put(UcamsConstant.AMS_MONGO_IDS_FILE, amsFileInfo.getId());
			Map<String,Object> map =new HashMap<String,Object>();
			for (AmsDesExtend amsDesExtend:amsDesExtends) {
				map.put(amsDesExtend.getName(), amsDesExtend.getSettings());
			}
			JSONObject data=  JSON.parseObject(JSONObject.toJSONString(map));
			jObject.put("data", data);
			amsFileInfo.setFileJson(jObject.toJSONString());
		}
		if(StringUtils.isNotBlank(amsFileInfo.getUnitProjectId())){
			amsFileInfo.setProjectId(amsUnitProInfoDao.get(amsFileInfo.getUnitProjectId()).getProjectId());
		}
		if (isInsert){
			super.insert(amsFileInfo);	
			//mongodb数据保存
			dbOperationU.save(jObject,UcamsConstant.AMS_MONGO_TABLE_NAME_FILE);
		}else {
			super.update(amsFileInfo);
			dbOperationU.updateDocument(UcamsConstant.AMS_MONGO_TABLE_NAME_FILE,new BasicDBObject(UcamsConstant.AMS_MONGO_IDS_FILE,amsFileInfo.getId()),new BasicDBObject(jObject));
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsFileInfo amsFileInfo) {
		super.delete(amsFileInfo);
	}
	
	/**
	 * 分页查询案卷下的所有文件
	 */
	public Page<AmsFileInfo> findArchivesFileList(Page<AmsFileInfo> page, AmsFileInfo amsFileInfo) {
		page.setPageSize(2);  //设置每页显示数据条数
		amsFileInfo.setPage(page);
		page.setList(dao.findArchivesFileList(amsFileInfo));
		return page;
	} 
	
	/**
	 * 查询单位工程下是否添加文件，作为删除单位工程的依据
	 * 2017年9月22日 上午9:25:56
	 */
	public AmsFileInfo getAmsFileInfoId(String id) {
		return amsFileInfoDao.getAmsFileInfoId(id);
	}
}