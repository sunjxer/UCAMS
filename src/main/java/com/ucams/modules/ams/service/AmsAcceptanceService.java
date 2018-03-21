/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.ucams.common.constant.CommonConstant;
import com.ucams.common.constant.UcamsConstant;
import com.ucams.common.mongo.DBOperationsU;
import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.JSONUtils;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.act.service.ActTaskService;
import com.ucams.modules.ams.dao.AmsAcceptanceDao;
import com.ucams.modules.ams.dao.AmsArchiveMenuDao;
import com.ucams.modules.ams.dao.AmsArchivesInfoDao;
import com.ucams.modules.ams.dao.AmsConstructDesDao;
import com.ucams.modules.ams.dao.AmsDesExtendDao;
import com.ucams.modules.ams.dao.AmsFileInfoDao;
import com.ucams.modules.ams.dao.AmsLandDesDao;
import com.ucams.modules.ams.dao.AmsPreRptDao;
import com.ucams.modules.ams.dao.AmsProjectInfoDao;
import com.ucams.modules.ams.dao.AmsUnitDetailinfoDao;
import com.ucams.modules.ams.dao.AmsUnitProInfoDao;
import com.ucams.modules.ams.entity.AmsAcceptance;
import com.ucams.modules.ams.entity.AmsAcceptanceArchives;
import com.ucams.modules.ams.entity.AmsArchiveMenu;
import com.ucams.modules.ams.entity.AmsArchivesInfo;
import com.ucams.modules.ams.entity.AmsConstructDes;
import com.ucams.modules.ams.entity.AmsDesExtend;
import com.ucams.modules.ams.entity.AmsDesProgram;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsLandDes;
import com.ucams.modules.ams.entity.AmsPreRpt;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.entity.AmsUnitProInfo;
import com.ucams.modules.ams.utils.CheckFileUtils;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 预验收管理Service
 * 
 * @author zkx
 * @version 2017-07-11
 */
@Service
@Transactional(readOnly = true)
public class AmsAcceptanceService extends CrudService<AmsAcceptanceDao, AmsAcceptance> {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private AmsProjectInfoDao amsProjectInfoDao;
	@Autowired
	private AmsUnitProInfoDao amsUnitProInfoDao;
	@Autowired
	private AmsArchivesInfoDao amsArchivesInfoDao;
	@Autowired
	private AmsFileInfoDao amsFileInfoDao;
	@Autowired
	private AmsDesExtendDao amsDesExtendDao;
	@Autowired
	private DBOperationsU dbOperationU;
	@Autowired
	private AmsUnitDetailinfoDao amsUnitDetailinfoDao;
	@Autowired
	private AmsPreRptService amsPreRptService;
	@Autowired
	private AmsPreRptDao amsPreRptDao;
	
	@Autowired
	private AmsAcceptanceDao amsAcceptanceDao;
	
	@Autowired
	private AmsConstructDesDao amsConstructDesDao;
	@Autowired
	private AmsLandDesDao amsLandDesDao;
	@Autowired
	private AmsArchiveMenuDao amsArchiveMenuDao;

	public AmsAcceptance get(String id) {
		AmsAcceptance acceptance = super.get(id);
		return acceptance;
	}
	/**
	 * 查询预验收选择则的单位工程及案卷
	 * @param amsAcceptance
	 */
	public void setSelectList(AmsAcceptance amsAcceptance) {
		List<AmsAcceptanceArchives> list=new ArrayList<AmsAcceptanceArchives>();
		// 查询单位工程
		List<AmsUnitProInfo> unitList = new ArrayList<AmsUnitProInfo>();
		// 查询文件
		List<AmsArchivesInfo> amsArchivesInfoList = new ArrayList<AmsArchivesInfo>();
		List<AmsConstructDes> amsConstructDesList = new ArrayList<AmsConstructDes>();
		List<AmsLandDes> amsLandDesList = new ArrayList<AmsLandDes>();
		if (StringUtils.isNotBlank(amsAcceptance.getId())) {
			list = dao.getList(amsAcceptance);
			for (AmsAcceptanceArchives amsAcceptanceArchives : list) {
				if("-1".equals(amsAcceptanceArchives.getArchiveId())&&EntityUtils.isNotEmpty(amsAcceptanceArchives.getAmsUnitProInfo())){
					amsAcceptanceArchives.getAmsUnitProInfo().setExten5("true");
					unitList.add(amsAcceptanceArchives.getAmsUnitProInfo());//idUnitInStrings.add(amsAcceptanceArchives.getUnitProjectId());// 已选单位工程id集合
				}else if("-1".equals(amsAcceptanceArchives.getUnitProjectId())&&EntityUtils.isNotEmpty(amsAcceptanceArchives.getAmsArchivesInfo())){
					amsAcceptanceArchives.getAmsArchivesInfo().setExten5("true");
					amsArchivesInfoList.add(amsAcceptanceArchives.getAmsArchivesInfo());//idArchiveInStrings.add(amsAcceptanceArchives.getArchiveId());// 已选文件id集合
				}else if("acc".equals(amsAcceptanceArchives.getUnitProjectId())){
					amsAcceptanceArchives.getAmsConstructDes().setExten5("true");
					amsConstructDesList.add(amsAcceptanceArchives.getAmsConstructDes());
				}else if("lan".equals(amsAcceptanceArchives.getUnitProjectId())){
					amsAcceptanceArchives.getAmsLandDes().setExten5("true");
					amsLandDesList.add(amsAcceptanceArchives.getAmsLandDes());
				}
			}
		}
		amsAcceptance.setAmsUnitProInfoList(unitList);
		amsAcceptance.setAmsArchivesInfoList(amsArchivesInfoList);
		amsAcceptance.setAmsConstructDesList(amsConstructDesList);
		amsAcceptance.setAmsLandDesList(amsLandDesList);
	}
	public void setList(AmsAcceptance amsAcceptance) {
		List<String> idUnitInStrings = new ArrayList<String>();
		List<String> idArchiveInStrings = new ArrayList<String>();
		List<AmsAcceptanceArchives> list=new ArrayList<AmsAcceptanceArchives>();
		// 查询单位工程
		AmsUnitProInfo amsUnitProInfo = new AmsUnitProInfo();
		amsUnitProInfo.setProjectId(amsAcceptance.getProject().getId());
		List<AmsUnitProInfo> unitList = new ArrayList<AmsUnitProInfo>();
		// 查询文件
		AmsArchivesInfo amsArchivesInfo = new AmsArchivesInfo();
		amsArchivesInfo.setProjectId(amsAcceptance.getProject().getId());
		amsArchivesInfo.setUnitProjectId("-1");
		List<AmsArchivesInfo> amsArchivesInfoList = new ArrayList<AmsArchivesInfo>();
		
		//建设工程、用地规划
				AmsConstructDes amsCons=new AmsConstructDes();
				amsCons.setProjectId(amsAcceptance.getProject().getId());
				AmsLandDes amsLand=new AmsLandDes();
				amsLand.setProjectId(amsAcceptance.getProject().getId());
				List<AmsConstructDes> amsConstructDesList = new ArrayList<AmsConstructDes>();
				List<AmsLandDes> amsLandDesList = new ArrayList<AmsLandDes>();
		
		if (StringUtils.isNotBlank(amsAcceptance.getId())) {
			list = dao.getList(amsAcceptance);
			for (AmsAcceptanceArchives amsAcceptanceArchives : list) {
				if("-1".equals(amsAcceptanceArchives.getArchiveId())&&EntityUtils.isNotEmpty(amsAcceptanceArchives.getAmsUnitProInfo())){
					amsAcceptanceArchives.getAmsUnitProInfo().setExten5("true");
					unitList.add(amsAcceptanceArchives.getAmsUnitProInfo());//idUnitInStrings.add(amsAcceptanceArchives.getUnitProjectId());// 已选单位工程id集合
				}else if("-1".equals(amsAcceptanceArchives.getUnitProjectId())&&EntityUtils.isNotEmpty(amsAcceptanceArchives.getAmsArchivesInfo())){
					amsAcceptanceArchives.getAmsArchivesInfo().setExten5("true");
					amsArchivesInfoList.add(amsAcceptanceArchives.getAmsArchivesInfo());//idArchiveInStrings.add(amsAcceptanceArchives.getArchiveId());// 已选文件id集合
				}else if("acc".equals(amsAcceptanceArchives.getUnitProjectId())){
					amsAcceptanceArchives.getAmsConstructDes().setExten5("true");
					amsConstructDesList.add(amsAcceptanceArchives.getAmsConstructDes());
				}else if("lan".equals(amsAcceptanceArchives.getUnitProjectId())){
					amsAcceptanceArchives.getAmsLandDes().setExten5("true");
					amsLandDesList.add(amsAcceptanceArchives.getAmsLandDes());
				}
			}
		}
	
		
		// 获取未选单位工程
		idUnitInStrings = new ArrayList<String>();
		idArchiveInStrings = new ArrayList<String>();
		List<String> idConStrings = new ArrayList<String>();
		List<String> idLanStrings = new ArrayList<String>();
		 list = dao.getIdsList(amsAcceptance);
		for (AmsAcceptanceArchives amsAcceptanceArchives : list) {
			if("-1".equals(amsAcceptanceArchives.getArchiveId())){
				if(!"2".equals(amsAcceptanceArchives.getDelFlag())){
					idUnitInStrings.add(amsAcceptanceArchives.getUnitProjectId());// 已选单位工程id集合
				}
				
			}else{
				if(!"2".equals(amsAcceptanceArchives.getDelFlag())&&"-1".equals(amsAcceptanceArchives.getUnitProjectId())){
					idArchiveInStrings.add(amsAcceptanceArchives.getArchiveId());// 已选文件id集合
				}
				if(!"2".equals(amsAcceptanceArchives.getDelFlag())&&"acc".equals(amsAcceptanceArchives.getUnitProjectId())){
					idConStrings.add(amsAcceptanceArchives.getArchiveId());// 已选id集合
				}
				if(!"2".equals(amsAcceptanceArchives.getDelFlag())&&"lan".equals(amsAcceptanceArchives.getUnitProjectId())){
					idLanStrings.add(amsAcceptanceArchives.getArchiveId());// 已选id集合
				}
			}
		}
		if (EntityUtils.isNotEmpty(idUnitInStrings) || idUnitInStrings.size() > 0) {
			idUnitInStrings.add("-1");
		}
		amsUnitProInfo.setIds(idUnitInStrings);
		amsUnitProInfo.setExten1("not in");
		int index = unitList.size();
		unitList.addAll(index, amsUnitProInfoDao.findList(amsUnitProInfo));
		for (int i = index; i < unitList.size(); i++) {
			AmsUnitProInfo amsUnitProInfo2 = unitList.get(i);
			amsUnitProInfo2.setExten5("");
		}
		// 获取未选文件
		if (EntityUtils.isNotEmpty(idArchiveInStrings) || idArchiveInStrings.size() > 0) {
			idArchiveInStrings.add("-1");
		}
		amsArchivesInfo.setIds(idArchiveInStrings);
		amsArchivesInfo.setExten1("not in");
		int archivesIndex = amsArchivesInfoList.size();
		amsArchivesInfoList.addAll(archivesIndex, amsArchivesInfoDao.findList(amsArchivesInfo));
		for (int i = archivesIndex; i < amsArchivesInfoList.size(); i++) {
			AmsArchivesInfo amsArchivesInfo2 = amsArchivesInfoList.get(i);
			amsArchivesInfo2.setExten5("");
		}
		//
		if (EntityUtils.isNotEmpty(idConStrings) || idConStrings.size() > 0) {
			idConStrings.add("-1");
		}
		amsCons.setIds(idConStrings);
		amsCons.setExten1("not in");
		int conIndex = amsConstructDesList.size();
		amsConstructDesList.addAll(conIndex,amsConstructDesDao.accFindList(amsCons));
		
		for (int i = conIndex; i < amsConstructDesList.size(); i++) {
			AmsConstructDes amsConstructDes2 = amsConstructDesList.get(i);
			amsConstructDes2.setExten5("");
		}
		//
		if (EntityUtils.isNotEmpty(idLanStrings) || idLanStrings.size() > 0) {
			idLanStrings.add("-1");
		}
		amsLand.setIds(idLanStrings);
		amsLand.setExten1("not in");
		int land = amsLandDesList.size();
		amsLandDesList.addAll(land, amsLandDesDao.accFindList(amsLand));
		for (int i = land; i < amsLandDesList.size(); i++) {
			AmsLandDes amsLandDes2 = amsLandDesList.get(i);
			amsLandDes2.setExten5("");
		}
		
		amsAcceptance.setAmsUnitProInfoList(unitList);
		amsAcceptance.setAmsArchivesInfoList(amsArchivesInfoList);
		amsAcceptance.setAmsConstructDesList(amsConstructDesList);
		amsAcceptance.setAmsLandDesList(amsLandDesList);
	}
	public void setVideoList(AmsAcceptance amsAcceptance) {
		List<String> idStrings = new ArrayList<String>();
		List<AmsAcceptanceArchives> list=new ArrayList<AmsAcceptanceArchives>();
		// 查询
		AmsArchiveMenu amsArchiveMenu = new AmsArchiveMenu();
		amsArchiveMenu.setProjectId(amsAcceptance.getProject().getId());
		List<AmsArchiveMenu> videoMenuList = new ArrayList<AmsArchiveMenu>();
		if (StringUtils.isNotBlank(amsAcceptance.getId())) {
			list = dao.getList(amsAcceptance);
			for (AmsAcceptanceArchives amsAcceptanceArchives : list) {
				if("-2".equals(amsAcceptanceArchives.getUnitProjectId())&&EntityUtils.isNotEmpty(amsAcceptanceArchives.getAmsArchiveMenu())){
					amsAcceptanceArchives.getAmsArchiveMenu().setExten5("true");
					videoMenuList.add(amsAcceptanceArchives.getAmsArchiveMenu());//idUnitInStrings.add(amsAcceptanceArchives.getUnitProjectId());// 已选单位工程id集合
				}
			}
		}
		
		// 获取未选
		idStrings = new ArrayList<String>();
		 list = dao.getIdsList(amsAcceptance);
		for (AmsAcceptanceArchives amsAcceptanceArchives : list) {
			if("-2".equals(amsAcceptanceArchives.getUnitProjectId())){
				if(!"2".equals(amsAcceptanceArchives.getDelFlag())){
					idStrings.add(amsAcceptanceArchives.getArchiveId());// 已选单位工程id集合
				}
				
			}
		}
		if (EntityUtils.isNotEmpty(idStrings) || idStrings.size() > 0) {
			idStrings.add("-1");
		}
		amsArchiveMenu.setIds(idStrings);
		amsArchiveMenu.setExten1("not in");
		int index = videoMenuList.size();
		videoMenuList.addAll(index, amsArchiveMenuDao.accFindList(amsArchiveMenu));
		for (int i = index; i < videoMenuList.size(); i++) {
			AmsArchiveMenu AmsArchiveMenu2 = videoMenuList.get(i);
			AmsArchiveMenu2.setExten5("");
		}
		amsAcceptance.setVideoMenuList(videoMenuList);
	}
	public void setVideoSelList(AmsAcceptance amsAcceptance) {
		List<AmsAcceptanceArchives> list=new ArrayList<AmsAcceptanceArchives>();
		List<AmsArchiveMenu> videoMenuList = new ArrayList<AmsArchiveMenu>();
		if (StringUtils.isNotBlank(amsAcceptance.getId())) {
			list = dao.getList(amsAcceptance);
			for (AmsAcceptanceArchives amsAcceptanceArchives : list) {
				if("-2".equals(amsAcceptanceArchives.getUnitProjectId())&&EntityUtils.isNotEmpty(amsAcceptanceArchives.getAmsArchiveMenu())){
					videoMenuList.add(amsAcceptanceArchives.getAmsArchiveMenu());//idUnitInStrings.add(amsAcceptanceArchives.getUnitProjectId());// 已选单位工程id集合
				}
			}
		}
		amsAcceptance.setVideoMenuList(videoMenuList);
	}

	public List<AmsAcceptance> findList(AmsAcceptance amsAcceptance) {
		return super.findList(amsAcceptance);
	}
	public List<AmsPreRpt> amsPreRptList(AmsPreRpt amsPreRpt) {
		return amsPreRptDao.findList(amsPreRpt);
	}
	public Page<AmsAcceptance> findPage(Page<AmsAcceptance> page,
			AmsAcceptance amsAcceptance) {
		return super.findPage(page, amsAcceptance);
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
	
	@Transactional(readOnly = false)
	public void saveRpt(AmsAcceptance amsAcceptance) {
		for (AmsPreRpt amsPreRpt : amsAcceptance.getAmsPreRptList()){
			if (amsPreRpt.DEL_FLAG_NORMAL.equals(amsPreRpt.getDelFlag())){
				if (StringUtils.isBlank(amsPreRpt.getId())){
					amsPreRpt.setTransferId(amsAcceptance.getId());
					amsPreRpt.setState("2");
					amsPreRpt.preInsert();
					amsPreRptDao.insert(amsPreRpt);
				}else{
					amsPreRpt.preUpdate();
					amsPreRptDao.update(amsPreRpt);
				}
			}else{
				amsPreRptDao.delete(amsPreRpt);
			}
		}
	}

	/**
	 * 流程进行业务
	 * 
	 * @param amsAcceptance
	 */
	@Transactional(readOnly = false)
	public void saveAcceptance(AmsAcceptance amsAcceptance) {
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		amsAcceptance.preUpdate();
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = amsAcceptance.getAct().getTaskDefKey();
		// 申请批复环节
		if ("acceptance".equals(taskDefKey)||"first".equals(taskDefKey)) {
			amsAcceptance.getAct().setComment(
					("yes".equals(amsAcceptance.getAct().getFlag()) ? "[同意] "
							: "[驳回] ")
							+ amsAcceptance.getPreAcceptanceApprovalOpinions());
			amsAcceptance.setStatus("yes".equals(amsAcceptance.getAct()
					.getFlag()) ? "2" : "-2");
			// 保存批复内容
			dao.updateAcceptance(amsAcceptance);
			
		}
		if ("accept".equals(taskDefKey)||"second".equals(taskDefKey)) {
			amsAcceptance.getAct().setComment(
					("yes".equals(amsAcceptance.getAct().getFlag()) ? "[同意] ": "[驳回] "));
			amsAcceptance.setStatus("yes".equals(amsAcceptance.getAct()
					.getFlag()) ? "3" : "-3");
			//驳回时关系表中delflag更新为2
			if("no".equals(amsAcceptance.getAct().getFlag())){
				dao.updateDelFlag(amsAcceptance);
			}
			// 保存批复内容
			dao.updateAcceptance(amsAcceptance);
			
		}
		vars.put("pass", "yes".equals(amsAcceptance.getAct().getFlag()) ? "1"
				: "0");
		// 流程提交
		actTaskService.complete(amsAcceptance.getAct().getTaskId(),
				amsAcceptance.getAct().getProcInsId(), amsAcceptance.getAct()
						.getComment(), vars);
	}

	@Transactional(readOnly = false)
	public void save(AmsAcceptance amsAcceptance) {
		String actString="1".equals(amsAcceptance.getType())?"amsVideoAcceptance":"amsAcceptance";
		if (StringUtils.isBlank(amsAcceptance.getId())) {
			Role role = new Role();
			role.setId(UserUtils.getRoleList().get(0).getId());
			amsAcceptance.setRole(role);
			amsAcceptance.setStatus("0");
			amsAcceptance.setExten3("0");
			amsAcceptance.preInsert();
			dao.insert(amsAcceptance);
			// 如果选择提交申请则启动流程，不选提交仅进行保存添加
			if ("yes".equals(amsAcceptance.getAct().getFlag())) {
				// 查询项目名称projectName
				String projectName = amsProjectInfoDao.get(
						amsAcceptance.getProject().getId()).getProjectName();
				
				
				actTaskService.startProcess(actString, "ams_acceptance",
						amsAcceptance.getId(), projectName);
				amsAcceptance.setStatus("1");
				dao.updateAcceptance(amsAcceptance);
			}

		} else {
			amsAcceptance.preUpdate();
			// 未提交时可以进行修改或修改并提交
			if ("0".equals(amsAcceptance.getStatus())) {
				// 如果选择提交申请则启动流程，不选提交仅进行修改
				if ("yes".equals(amsAcceptance.getAct().getFlag())) {
					actTaskService.startProcess(actString,
							"ams_acceptance", amsAcceptance.getId(),
							amsAcceptance.getProject().getProjectName());
					amsAcceptance.setStatus("1");
				}
				dao.update(amsAcceptance);
			} else {
				amsAcceptance
						.getAct()
						.setComment(
								("no".equals(amsAcceptance.getAct().getFlag()) ? "[销毁] "
										: "0".equals(amsAcceptance.getAct()
												.getFlag()) ? "" : "[重申] "));
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass",
						"yes".equals(amsAcceptance.getAct().getFlag()) ? "1"
								: "0");
				actTaskService.complete(amsAcceptance.getAct().getTaskId(),
						amsAcceptance.getAct().getProcInsId(), amsAcceptance
								.getAct().getComment(), vars);
				
				dao.update(amsAcceptance);
			}
		}
		// 插入关系表
		AmsAcceptanceArchives acceptanceArchives;
		List<AmsAcceptanceArchives> amsAcceptanceArchivesList = new ArrayList<AmsAcceptanceArchives>();
		if (StringUtils.isNotBlank(amsAcceptance.getProjectString())) {
			//插入单位工程
			String ids1[] = amsAcceptance.getProjectString().split(",");
			//在单位工程中插入预验收的id，用于判断单位工程其他操作的执行
			for (String id : ids1) {
				AmsUnitProInfo amsUnitProInfo = new AmsUnitProInfo();
				amsUnitProInfo.setId(id);
				amsUnitProInfo.setTransfreId(amsAcceptance.getId());
				amsUnitProInfoDao.modifyAmsUnitProInfo(amsUnitProInfo);
				acceptanceArchives = new AmsAcceptanceArchives();
				acceptanceArchives.setUnitProjectId(id);
				acceptanceArchives.setProjectId(amsAcceptance.getProject().getId());
				acceptanceArchives.setArchiveId("-1");
				acceptanceArchives.preInsert();
				amsAcceptanceArchivesList.add(acceptanceArchives);
			}
		}
		if (StringUtils.isNotBlank(amsAcceptance.getArchiverString())) {
			//插入案卷
			String ids2[] = amsAcceptance.getArchiverString().split(",");
			for (String id : ids2) {
				acceptanceArchives = new AmsAcceptanceArchives();
				acceptanceArchives.setProjectId(amsAcceptance.getProject().getId());
				acceptanceArchives.setUnitProjectId("-1");
				acceptanceArchives.setArchiveId(id);
				acceptanceArchives.preInsert();
				amsAcceptanceArchivesList.add(acceptanceArchives);
			}
		}
		
		if (StringUtils.isNotBlank(amsAcceptance.getAccString())) {
			//插入案卷
			String ids3[] = amsAcceptance.getAccString().split(",");
			for (String id : ids3) {
				acceptanceArchives = new AmsAcceptanceArchives();
				acceptanceArchives.setProjectId(amsAcceptance.getProject().getId());
				acceptanceArchives.setUnitProjectId("acc");
				acceptanceArchives.setArchiveId(id);
				acceptanceArchives.preInsert();
				amsAcceptanceArchivesList.add(acceptanceArchives);
				AmsConstructDes amsCons1=new AmsConstructDes(id);
				amsConstructDesDao.updateAcc(amsCons1);
				
			}
		}
		
		if (StringUtils.isNotBlank(amsAcceptance.getLanString())) {
			//插入案卷
			String ids4[] = amsAcceptance.getLanString().split(",");
			for (String id : ids4) {
				acceptanceArchives = new AmsAcceptanceArchives();
				acceptanceArchives.setProjectId(amsAcceptance.getProject().getId());
				acceptanceArchives.setUnitProjectId("lan");
				acceptanceArchives.setArchiveId(id);
				acceptanceArchives.preInsert();
				amsAcceptanceArchivesList.add(acceptanceArchives);
				AmsLandDes amsLand1=new AmsLandDes(id);
				amsLandDesDao.updateAcc(amsLand1);
			}
		}
		if (StringUtils.isNotBlank(amsAcceptance.getVideoMenuString())) {
			//插入案卷
			String ids4[] = amsAcceptance.getVideoMenuString().split(",");
			for (String id : ids4) {
				acceptanceArchives = new AmsAcceptanceArchives();
				acceptanceArchives.setProjectId(amsAcceptance.getProject().getId());
				acceptanceArchives.setUnitProjectId("-2");
				acceptanceArchives.setArchiveId(id);
				acceptanceArchives.preInsert();
				amsAcceptanceArchivesList.add(acceptanceArchives);
			}
		}
		amsAcceptance.setAmsAcceptanceArchivesList(amsAcceptanceArchivesList);
		dao.deleteAmsAcceptanceArchives(amsAcceptance);
		dao.insertAmsAcceptanceArchivesList(amsAcceptance);
		//驳回时关系表中delflag更新为2
		if("no".equals(amsAcceptance.getAct().getFlag())){
			dao.updateDelFlag(amsAcceptance);
		}
	}

	
	@Transactional(readOnly = false)
	public void delete(AmsAcceptance amsAcceptance) {
		dao.deleteAmsAcceptanceArchives(amsAcceptance);
		super.delete(amsAcceptance);
	}
	
	/**
	 * 检查单位工程信息
	 * @param unitProInfo
	 * @return 检查报告
	 */
//	public void checkUnitPro(List<AmsUnitProInfo> amsUnitProInfo,String amsAcceptanceId){
//		
//		try {
//			//从mongodb中查出拓展数据
//			Map<String, Map> mongodbDatas = this.getUnitProExpand(amsUnitProInfo,UcamsConstant.AMS_MONGO_TABLE_NAME_UNIT);
//			for(AmsUnitProInfo unitProInfo : amsUnitProInfo){
//				//-----------------------------------------检测单位工程基础/拓展信息--------------------------------------------//
//				 List<String> results = new ArrayList<String>();
//				 //检查单位工程基本信息
//				 results = CheckFileUtils.checkObjFieldIsNull(unitProInfo); 
//				 //检查单位工程拓展信息
//			     //从拓展信息管理中拿出对对应数据项
//					 List<AmsDesExtend> amsDesExtend = this.getDesExtendNotNull(UcamsConstant.AMS_DESPROGRAM_ZYJZ, unitProInfo.getUnitProjectType());
//					 //从mongo数据中取出当前单位工程的拓展数据
//					 Map gomap = mongodbDatas.get(unitProInfo.getId());
//					 //该单位工程没有专业记载
//					 if(EntityUtils.isNotEmpty(gomap)){
//						 for(AmsDesExtend extend : amsDesExtend){
//							 //查出拓展数据中value为空的项
//							 if(EntityUtils.isEmpty(gomap.get(extend.getName()))){  
//								 //将描述存入报告
//								 results.add(extend.getComments());  					      
//							 }
//						  }
//					 }else{
//						 results.add("专业记载数据");
//					 }
//				 //检测报告入库
//				 this.saveRpt(amsAcceptanceId, results, 
//						 CommonConstant.AMS_COMMON_PARAM_NUM00,  
//						 CommonConstant.AMS_COMMON_PARAM_NUM00, 
//						 CommonConstant.AMS_COMMON_PARAM_NUM00 ,
//						 unitProInfo.getUnitProjectName(),null,null);
//				 //组织检测报告
//				 //checkResults.put(unitProInfo.getUnitProjectName(), results);   
//				  
//				  //-----------------------------------------检测单位工程下案卷基础信息-------------------------------------------//
//				  AmsArchivesInfo amsArchivesInfo = new AmsArchivesInfo();
//				  amsArchivesInfo.setUnitProjectId(unitProInfo.getId());
//				  //查询每个单位工程下的组卷
//				  List<AmsArchivesInfo> archivesInfos =  amsArchivesInfoDao.findList(amsArchivesInfo);  
//				  //案卷检测报告
//				  this.checkArchives(archivesInfos,amsAcceptanceId,unitProInfo.getUnitProjectName());  
//				  //合并报告
//				  //checkResults.putAll(archivesReport);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//			
//	}
	
	/**
	 * 检查案卷信息
	 * @param unitProInfo
	 * @return 检查报告
	 */
//	public void checkArchives(List<AmsArchivesInfo> archivesInfos,String amsAcceptanceId,String unitProName){
//		
//		Map<String, List<String>> checkResults = new HashMap<String, List<String>>();
//		try {
//			for(AmsArchivesInfo archives : archivesInfos){
//				 //---------------------------------------检查案卷信息------------------------------------//
//				 List<String> results = new ArrayList<String>();
//				 //检查案卷基本信息
//				 results = CheckFileUtils.checkObjFieldIsNull(archives); 
//				 //检查报告入库
//				 this.saveRpt(amsAcceptanceId, results, 
//						 CommonConstant.AMS_COMMON_PARAM_NUM00,  
//						 CommonConstant.AMS_COMMON_PARAM_NUM00, 
//						 CommonConstant.AMS_COMMON_PARAM_NUM00,
//						 unitProName,archives.getArchivesName(),null
//						 );
//				 //组织检测报告
//				 checkResults.put(archives.getArchivesName(), results);
//				 //---------------------------------------检查案卷下文件基础/拓展信息--------------------//
//				 List<AmsFileInfo> fileInfos =  amsFileInfoDao.findCheckList(archives.getId());   //查询案卷下的所有文件
//				 //文件检测报告
//				 this.checkFile(fileInfos,amsAcceptanceId,archives.getArchivesName(),unitProName);  
//				 //合并报告
//				 //checkResults.putAll(fileReport);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//			
//	}
	
	/**
	 * 检查文件信息
	 * @param unitProInfo
	 * @return 检查报告
	 */
//	public void checkFile(List<AmsFileInfo> fileInfos,String amsAcceptanceId,String archivesNmae,String unitProName){
//		
//		try {
//			//检查文件拓展信息
//			 Map<String, Map> mongodbDatas = this.getFileExpand(fileInfos,UcamsConstant.AMS_MONGO_TABLE_NAME_FILE);
//			for(AmsFileInfo file : fileInfos){
//				 //---------------------------------------检查文件信息------------------------------------//
//				List<String> resultsFileMsg = new ArrayList<String>();
//				List<String> resultsFileUrl = new ArrayList<String>();
//				 //检查文件基本信息
//				resultsFileMsg = CheckFileUtils.checkObjFieldIsNull(file); 				 
//				 //文件没有拓展数据
//				 if(EntityUtils.isNotEmpty(mongodbDatas) && mongodbDatas.size() >0){
//					 List<AmsDesExtend> amsDesExtend = this.getDesExtendNotNull(UcamsConstant.AMS_DESPROGRAM_WJZL, null);
//					 //从mongo数据中取出当前单位工程的拓展数据
//					 Map gomap = mongodbDatas.get(file.getId()); 
//					  for(AmsDesExtend extend : amsDesExtend){
//						 //查出拓展数据中value为空的项
//						 if(EntityUtils.isEmpty(gomap.get(extend.getName()))){  
//							 //将描述存入报告
//							 resultsFileMsg.add(extend.getComments());  					      
//						 }
//					  }
//				 }else{
//					 resultsFileMsg.add("拓展数据");
//				 }
//				 //文件信息检查报告入库
//				 this.saveRpt(amsAcceptanceId, resultsFileMsg, 
//						 CommonConstant.AMS_COMMON_PARAM_NUM00,  
//						 CommonConstant.AMS_COMMON_PARAM_NUM00, 
//						 CommonConstant.AMS_COMMON_PARAM_NUM00 ,
//						 unitProName,archivesNmae,file.getFileName()
//						 );
//				 
//				  //---------------------------------------检查物理文件------------------------------------//
//				 if(! "-1".equals(file.getRecordId())){  //pdf文件
//					 File thisFile = new File(file.getFilePath());
//					 if(thisFile.exists()){
//						 String fileRes =  CheckFileUtils.checkPdf(new FileInputStream(thisFile), file.getFilecount());
//							if(EntityUtils.isNotEmpty(fileRes)){
//								 //如果pdf文件检测有问题则,加入检测报告
//								resultsFileUrl.add(fileRes);  
//							}
//					 }else{
//						 resultsFileUrl.add("没有文件");
//					 }
//				 }
//				//物理文件检查报告入库
//				 this.saveRpt(amsAcceptanceId, resultsFileMsg, 
//						 CommonConstant.AMS_COMMON_PARAM_NUM01,  
//						 CommonConstant.AMS_COMMON_PARAM_NUM00, 
//						 CommonConstant.AMS_COMMON_PARAM_NUM00 ,
//						 unitProName,archivesNmae,file.getFileName()
//						 );
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}	
//	}
	
	/**
	 * 获取相应的拓展信息
	 * @param programType 方案类型
	 * @param unitProType 单位工程类型
	 * @return
	 */
//	public List<AmsDesExtend> getDesExtendNotNull(String programType,String unitProType){
//		//获取非空数据项
//		List<AmsDesExtend> extendList = null;
//		try {
//			AmsDesProgram amsDesProgram = new AmsDesProgram();
//			amsDesProgram.setProgramType(programType);
//			amsDesProgram.setUnitProjectType(unitProType);
//			amsDesProgram.setUseable("1");
//			AmsDesExtend extend = new AmsDesExtend();
//			extend.setAmsDesProgram(amsDesProgram);
//			extendList = amsDesExtendDao.findByUpType(extend);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return extendList;
//	}
	
	/**
	 * 查询文件拓展数据
	 * @param Map<String, Map>
	 * @return
	 */
//	public Map<String, Map> getFileExpand(List<AmsFileInfo> list,String tableName){
//		DBObject queryCondition = new BasicDBObject();          
////		List<String> reports = null ;
//		Map<String, Map> containers = new HashMap<String, Map>();
//		try {
//			 BasicDBList values = new BasicDBList();  
//			 for(AmsFileInfo file : list){
//				 values.add(file.getId());                    //添加in的查询条件
////				 containers.put(unit.getId(), unit.getUnitProjectName());  //将单位工程信息存入map容器
//			 }
//			 queryCondition.put(UcamsConstant.AMS_MONGO_IDS_FILE, new BasicDBObject("$in", values));  
//			 List<DBObject> results =  dbOperationU.selectDocument(tableName, queryCondition);
//			 for(DBObject dbo : results){
////				 Map<String, Object> map = JSONUtils.StringToMap(dbo.get("data").toString());  //取出data中的拓展数据
////				 String unitId = (String) dbo.get(UcamsConstant.AMS_MONGO_IDS_UNIT);  //获取该条数据的单位工程ID
////				 List<String> res = CheckFileUtils.getNullValueKeys(map); //获取拓展数据中 value为空的key
////				 
////				 String unitName = containers.get(unitId);
////				 reports.add(containers.get(unitId) + )
//				 containers.put((String)dbo.get(UcamsConstant.AMS_MONGO_IDS_FILE),
//						 	JSONUtils.StringToMap(dbo.get(UcamsConstant.AMS_MONGO_IDS_DATA).toString()));
//			 }
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		
//	   return containers;
//	}
	
	/**
	 * 查询单位工程拓展数据
	 * @param Map<String, Map>
	 * @return
	 */
//	public Map<String, Map> getUnitProExpand(List<AmsUnitProInfo> list,String tableName){
//		DBObject queryCondition = new BasicDBObject();          
////		List<String> reports = null ;
//		Map<String, Map> containers = new HashMap<String, Map>();
//		try {
//			 BasicDBList values = new BasicDBList();  
//			 for(AmsUnitProInfo unit : list){
//				 values.add(unit.getId());                    //添加in的查询条件
////				 containers.put(unit.getId(), unit.getUnitProjectName());  //将单位工程信息存入map容器
//			 }
//			 queryCondition.put(UcamsConstant.AMS_MONGO_IDS_UNIT, new BasicDBObject("$in", values));  
//			 List<DBObject> results =  dbOperationU.selectDocument(tableName, queryCondition);
//			 for(DBObject dbo : results){
////				 Map<String, Object> map = JSONUtils.StringToMap(dbo.get("data").toString());  //取出data中的拓展数据
////				 String unitId = (String) dbo.get(UcamsConstant.AMS_MONGO_IDS_UNIT);  //获取该条数据的单位工程ID
////				 List<String> res = CheckFileUtils.getNullValueKeys(map); //获取拓展数据中 value为空的key
////				 
////				 String unitName = containers.get(unitId);
////				 reports.add(containers.get(unitId) + )
//				 containers.put((String)dbo.get(UcamsConstant.AMS_MONGO_IDS_UNIT),
//						 	JSONUtils.StringToMap(dbo.get(UcamsConstant.AMS_MONGO_IDS_DATA).toString()));
//			 }
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		
//	   return containers;
//	}
	
	/**
	 * 检查报告入库
	 * @param amsAcceptanceId  预验收与移交ID
	 * @param results 错误内容集合
	 * @param errorType 0-内容 不全（必填项未填），1-文件数量不符 
	 * @param opersion 位置 
	 * @param state 0-正常错误，1-忽略，2-录入；打印报告时不打印状态为1的数据。  
	 */
	public void saveRpt(String amsAcceptanceId,List<String> results,String errorType,
							String opersion,String state,String unitProName,String archivesNmae,String fileName ){
		try {
				StringBuffer str = null;
				//数据入库
				AmsPreRpt entity = new AmsPreRpt();
				entity.setTransferId(amsAcceptanceId); //预验收与移交ID
				entity.setErrorType(errorType);
				
				entity.setOpersion(opersion);
				entity.setState(state);
				entity.setCreateBy(UserUtils.getUser());
				entity.setCreateDate(new Date());
				entity.setUpdateBy(UserUtils.getUser());
				entity.setUpdateDate(new Date());
				//组织报告内容
				for(String content : results){
					str = new StringBuffer();
					if(EntityUtils.isNotEmpty(unitProName)){
						str.append(unitProName + "-");
					}
					if(EntityUtils.isNotEmpty(archivesNmae)){
						str.append(archivesNmae + "-");
					}
					if(EntityUtils.isNotEmpty(fileName)){
						str.append(fileName + "-");
					}
					str.append(content + "(空)");
					entity.setError(str.toString());
					amsPreRptService.save(entity);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//查询项目工程下是否添加预验收，作为删除项目工程的依据
	/*@Transactional
	public String getAmsAcceptanceId(String id) {

		return amsAcceptanceDao.getAmsAcceptanceId(id);
	}*/
}