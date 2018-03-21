/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.act.service.ActTaskService;
import com.ucams.modules.ams.dao.AmsAcceptanceDao;
import com.ucams.modules.ams.dao.AmsProjectInfoDao;
import com.ucams.modules.ams.dao.AmsTransferArchivesDao;
import com.ucams.modules.ams.dao.AmsTransferDao;
import com.ucams.modules.ams.dao.AmsTransferRptDao;
import com.ucams.modules.ams.dao.AmsUnitDetailinfoDao;
import com.ucams.modules.ams.entity.AmsAcceptance;
import com.ucams.modules.ams.entity.AmsTransfer;
import com.ucams.modules.ams.entity.AmsTransferArchives;
import com.ucams.modules.ams.entity.AmsTransferRpt;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 移交Service
 * @author zkx
 * @version 2017-07-25
 */
@Service
@Transactional(readOnly = true)
public class AmsTransferService extends CrudService<AmsTransferDao, AmsTransfer> {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private AmsAcceptanceDao amsAcceptanceDao;
	@Autowired
	private AmsProjectInfoDao amsProjectInfoDao;
	@Autowired
	private AmsTransferArchivesDao amsTransferArchivesDao;
	@Autowired
	private AmsUnitDetailinfoDao amsUnitDetailinfoDao;
	@Autowired
	private AmsTransferRptDao amsTransferRptDao;
	@Autowired
	private AmsAcceptanceService amsAcceptanceService;
	public AmsTransfer get(String id) {
		return super.get(id);
	}
	
	public List<AmsTransfer> findList(AmsTransfer amsTransfer) {
		return super.findList(amsTransfer);
	}
	
	public Page<AmsTransfer> findPage(Page<AmsTransfer> page, AmsTransfer amsTransfer) {
		return super.findPage(page, amsTransfer);
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
	public List<AmsTransferRpt> amsPreRptList(AmsTransferRpt amsPreRpt) {
		return amsTransferRptDao.findList(amsPreRpt);
	}
	/**
	 * 查询的单位工程及案卷
	 * @param amsAcceptance
	 */
	public void setSelectList(AmsTransfer amsTransfer) {
		AmsAcceptance amsAcceptance=new AmsAcceptance();
		List<AmsAcceptance> acclist=new ArrayList<AmsAcceptance>();
		List<AmsTransferArchives> list=new ArrayList<AmsTransferArchives>();
		list = amsTransferArchivesDao.findTranList(amsTransfer);
        	amsAcceptance.setProject(amsTransfer.getProject());
			//查询已选预验收
			for (AmsTransferArchives amsTransferArchives : list) {
				amsAcceptanceService.setSelectList(amsTransferArchives.getAcceptance());
				amsTransfer.getAmsUnitProInfoList().addAll(amsTransferArchives.getAcceptance().getAmsUnitProInfoList());
				amsTransfer.getAmsArchivesInfoList().addAll(amsTransferArchives.getAcceptance().getAmsArchivesInfoList());
				amsTransfer.getAmsConstructDesList().addAll(amsTransferArchives.getAcceptance().getAmsConstructDesList());
				amsTransfer.getAmsLandDesList().addAll(amsTransferArchives.getAcceptance().getAmsLandDesList());
				
				amsTransferArchives.getAcceptance().setExten5("true");
				acclist.add(amsTransferArchives.getAcceptance());
			} 
		amsTransfer.setAmsAcceptancesList(acclist);
	}
	public void setList(AmsTransfer amsTransfer) {
		AmsAcceptance amsAcceptance=new AmsAcceptance();
		List<AmsAcceptance> acclist=new ArrayList<AmsAcceptance>();
		List<AmsTransferArchives> list=new ArrayList<AmsTransferArchives>();
		list = amsTransferArchivesDao.findTranList(amsTransfer);
        	amsAcceptance.setProject(amsTransfer.getProject());
			//查询已选预验收
			for (AmsTransferArchives amsTransferArchives : list) {
				amsTransferArchives.getAcceptance().setExten5("true");;
				acclist.add(amsTransferArchives.getAcceptance());
			}
			//查询未选预验收
			int index =acclist.size();
			amsAcceptance.setExten1("not in");
			amsAcceptance.setType(amsTransfer.getType()); 
			acclist.addAll(index, amsAcceptanceDao.findList(amsAcceptance));
			for (int i=index; i<acclist.size();i++) {
				acclist.get(i).setExten5("");
			}
		amsTransfer.setAmsAcceptancesList(acclist);
	}
	public void setSelList(AmsTransfer amsTransfer) {
		List<AmsAcceptance> acclist=new ArrayList<AmsAcceptance>();
		List<AmsTransferArchives> list=new ArrayList<AmsTransferArchives>();
		list = amsTransferArchivesDao.findTranList(amsTransfer);
			//查询已选预验收
			for (AmsTransferArchives amsTransferArchives : list) {
				acclist.add(amsTransferArchives.getAcceptance());
			}
		amsTransfer.setAmsAcceptancesList(acclist);
	}

	
	@Transactional(readOnly = false)
	public void save(AmsTransfer amsTransfer) {
		String actString="1".equals(amsTransfer.getType())?"amsTransferVideo":"amsTransfer";
		if (StringUtils.isBlank(amsTransfer.getId())) {
			amsTransfer.setRole(UserUtils.getRoleList().get(0));
			amsTransfer.setStatus("0");
			amsTransfer.preInsert();
			dao.insert(amsTransfer);
			// 如果选择提交申请则启动流程，不选提交仅进行保存添加
			if ("yes".equals(amsTransfer.getAct().getFlag())) {
				// 查询项目名称projectName
				String projectName = amsProjectInfoDao.get(
						amsTransfer.getProject().getId()).getProjectName();
				actTaskService.startProcess(actString, "ams_transfer",
						amsTransfer.getId(), projectName);
				amsTransfer.setStatus("1");
				dao.updateTransfer(amsTransfer);
			}

		} else {
			amsTransfer.preUpdate();
			// 未提交时可以进行修改或修改并提交
			if ("0".equals(amsTransfer.getStatus())) {
				// 如果选择提交申请则启动流程，不选提交仅进行修改
				if ("yes".equals(amsTransfer.getAct().getFlag())) {
					actTaskService.startProcess(actString,
							"ams_transfer", amsTransfer.getId(),
							amsTransfer.getProject().getProjectName());
					amsTransfer.setStatus("1");
				}
				dao.update(amsTransfer);
			} else {
				amsTransfer
						.getAct()
						.setComment(
								("no".equals(amsTransfer.getAct().getFlag()) ? "[销毁] "
										: "0".equals(amsTransfer.getAct()
												.getFlag()) ? "" : "[重申] "));
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass",
						"yes".equals(amsTransfer.getAct().getFlag()) ? "1"
								: "0");
				actTaskService.complete(amsTransfer.getAct().getTaskId(),
						amsTransfer.getAct().getProcInsId(), amsTransfer
								.getAct().getComment(), vars);
				
				dao.update(amsTransfer);
			}
		}
		// 插入关系表
		AmsTransferArchives transferArchives;
		List<AmsTransferArchives> amsTransferArchivesList = new ArrayList<AmsTransferArchives>();
		if (StringUtils.isNotBlank(amsTransfer.getAcceptsString())) {
			String ids1[] = amsTransfer.getAcceptsString().split(",");
			for (String id : ids1) {
				transferArchives = new AmsTransferArchives();
				transferArchives.setProjectid(amsTransfer.getProject().getId());
				transferArchives.setAcceptance(new AmsAcceptance(id));
				transferArchives.preInsert();
				amsTransferArchivesList.add(transferArchives);
			}
		}
		amsTransfer.setAmsTransferArchivesList(amsTransferArchivesList);
		dao.deleteAmsTransferArchives(amsTransfer);
		dao.insertAmsTransferArchivesList(amsTransfer);
		if("no".equals(amsTransfer.getAct().getFlag())){
			dao.updateDelFlag(amsTransfer);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsTransfer amsTransfer) {
		super.delete(amsTransfer);
	}
	/**
	 * 流程进行业务
	 * 
	 * @param amsTransfer
	 */
	@Transactional(readOnly = false)
	public void saveTransfer(AmsTransfer amsTransfer) {
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		amsTransfer.preUpdate();
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = amsTransfer.getAct().getTaskDefKey();
		// 申请批复环节
		if ("transferPF".equals(taskDefKey)||"first".equals(taskDefKey)) {
			amsTransfer.getAct().setComment(
					("yes".equals(amsTransfer.getAct().getFlag()) ? "[同意] "
							: "[驳回] ")
							+ amsTransfer.getTransferApproval());
			amsTransfer.setStatus("yes".equals(amsTransfer.getAct()
					.getFlag()) ? "2" : "-2");
			// 保存批复内容
			dao.updateTransfer(amsTransfer);
		}
		if ("transferYJ".equals(taskDefKey)||"second".equals(taskDefKey)) {
			amsTransfer.getAct().setComment(
					("yes".equals(amsTransfer.getAct().getFlag()) ? "[同意] ": "[驳回] "));
			amsTransfer.setStatus("yes".equals(amsTransfer.getAct()
					.getFlag()) ? "3" : "-3");
			if("no".equals(amsTransfer.getAct().getFlag())){
				dao.updateDelFlag(amsTransfer);
			}
			// 保存批复内容
			dao.updateTransfer(amsTransfer);
		}
		vars.put("pass", "yes".equals(amsTransfer.getAct().getFlag()) ? "1"
				: "0");
		// 流程提交
		actTaskService.complete(amsTransfer.getAct().getTaskId(),
				amsTransfer.getAct().getProcInsId(), amsTransfer.getAct()
						.getComment(), vars);
	}
	@Transactional(readOnly = false)
	public void saveRpt(AmsTransfer amsTransfer) {
		for (AmsTransferRpt amsPreRpt : amsTransfer.getAmsPreRptList()){
			if (amsPreRpt.DEL_FLAG_NORMAL.equals(amsPreRpt.getDelFlag())){
				if (StringUtils.isBlank(amsPreRpt.getId())){
					amsPreRpt.setTransferId(amsTransfer.getId());
					amsPreRpt.setState("2");
					amsPreRpt.preInsert();
					amsTransferRptDao.insert(amsPreRpt);
				}else{
					amsPreRpt.preUpdate();
					amsTransferRptDao.update(amsPreRpt);
				}
			}else{
				amsTransferRptDao.delete(amsPreRpt);
			}
		}
	}
}