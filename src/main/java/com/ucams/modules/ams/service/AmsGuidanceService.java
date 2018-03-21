/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.common.utils.DateUtils;
import com.ucams.modules.act.service.ActTaskService;
import com.ucams.modules.ams.dao.AmsGuidanceDao;
import com.ucams.modules.ams.dto.AmsGuidanceExportDTO;
import com.ucams.modules.ams.entity.AmsGuidance;
import com.ucams.modules.sys.dao.UserDao;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 业务指导内容管理Service
 * @author gyl
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class AmsGuidanceService extends CrudService<AmsGuidanceDao, AmsGuidance> {
	
	@Autowired
	private AmsGuidanceDao amsGuidanceDao;
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private HistoryService historyService;
	
	public AmsGuidance get(String id) {
		return super.get(id);
	}
	
	public List<AmsGuidance> findList(AmsGuidance amsGuidance) {
		return super.findList(amsGuidance);
	}
	
	public Page<AmsGuidance> findPage(Page<AmsGuidance> page, AmsGuidance amsGuidance) {
		User u = UserUtils.getUser();
		List<Office> list = officeService.onlyUnitPro(u.getOffice().getParent());
		
//		List<Office> list = UserUtils.getOfficeList();
		List<String> oList = new ArrayList<String>();
		
		for(int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if(e.getGrade().equals("3")){
				oList.add(e.getId());
			}
		}
		amsGuidance.setPage(page);
		amsGuidance.setIds(oList);
		List<AmsGuidance> amsList = dao.findAmsGuidanceList(amsGuidance);
		page.setList(amsList);
		return page;
	}
	
	@Transactional(readOnly = false)
	public String saveGuid(AmsGuidance amsGuidance) {
		String result = null;
		//添加申请
		if(StringUtils.isBlank(amsGuidance.getId())){
			amsGuidance.preInsert();
			dao.insert(amsGuidance);
			//启动流程
			actTaskService.startProcess("guidance", "ams_guidance", amsGuidance.getId(), amsGuidance.getProject().getName());			
			result = "保存业务指导成功";
		//修改或者销毁申请
		}else{
			amsGuidance.preUpdate();
			amsGuidance.setDelFlag("yes".equals(amsGuidance.getAct().getFlag())?"0":"1");
			dao.update(amsGuidance);
			amsGuidance.getAct().setComment(("yes".equals(amsGuidance.getAct().getFlag())?"[重申] ":"[销毁] "));
			result = "yes".equals(amsGuidance.getAct().getFlag())?"修改业务指导成功":"销毁业务指导成功";
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(amsGuidance.getAct().getFlag())? "1" : "0");
			actTaskService.complete(amsGuidance.getAct().getTaskId(), amsGuidance.getAct().getProcInsId(), amsGuidance.getAct().getComment(), vars);
		}
		return result;
	}
	/**
	 * 流程进行业务
	 * @param amsGuidance
	 */
	@Transactional(readOnly = false)
	public void saveGuidance(AmsGuidance amsGuidance){
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		amsGuidance.preUpdate();
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = amsGuidance.getAct().getTaskDefKey();
/*		String assignee = amsGuidance.getAct().getAssignee();
		String assigneeName = amsGuidance.getAct().getAssigneeName();*/
		//申请批复环节
		if("accept".equals(taskDefKey)){
			amsGuidance.getAct().setComment(("yes".equals(amsGuidance.getAct().getFlag())?"[同意] ":"[驳回] ")+amsGuidance.getOpinion());
			//保存批复内容
			dao.updateOpinion(amsGuidance);
		//指导内容添加环节
		}else if("content".equals(taskDefKey)){
			amsGuidance.getAct().setComment(amsGuidance.getGuidance());			
			dao.updateGuidance(amsGuidance);
		}
		vars.put("pass", "yes".equals(amsGuidance.getAct().getFlag())? "1" : "0");
		//流程提交
		actTaskService.complete(amsGuidance.getAct().getTaskId(), amsGuidance.getAct().getProcInsId(), amsGuidance.getAct().getComment(), vars);		
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsGuidance amsGuidance) {
		super.delete(amsGuidance);
	}
	//查询项目工程下是否添加业务指导，作为删除项目工程的依据
	@Transactional
	public String getAmsGuidanceId(String id){
		
		return amsGuidanceDao.getAmsGuidanceId(id);
	}
	/**
	 * 根据起始日期、人员查询业务指导次数
	 * @param amsGuidance
	 * @return List<AmsGuidanceExportDTO>
	 */
	public List<AmsGuidanceExportDTO> findGuidanceCountByUser(AmsGuidance amsGuidance){
		List<AmsGuidanceExportDTO> list = new ArrayList<AmsGuidanceExportDTO>();
		User user = new User();
		//档案馆用户列表
		List<User> userList = userDao.findDagUserList(user);
		//所有已完成的任务
		HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(null).finished()
				.includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();
		//已完成任务的起止日期
		histTaskQuery.taskCompletedAfter(amsGuidance.getAct().getBeginDate());
		histTaskQuery.taskCompletedBefore(amsGuidance.getAct().getEndDate());
		
		for(int i=0; i<userList.size(); i++){
			User u = userList.get(i);
			AmsGuidanceExportDTO guidance = new AmsGuidanceExportDTO();
			guidance.setId((i+1)+"");
			guidance.setUserName(u.getName());
			guidance.setOfficeName(u.getOffice().getName());
			String userId = u.getLoginName();
			//已完成的业务指导任务
			histTaskQuery.processDefinitionKey(amsGuidance.getAct().getProcDefKey());
			//某用户完成的业务指导任务
			histTaskQuery.taskAssignee(userId);
			guidance.setCount(histTaskQuery.count());
			list.add(guidance);
		}
		return list;
	}
	
	public List<AmsGuidanceExportDTO> findGuidanceCountByMonth(int year,int beginMonth,int endMonth,AmsGuidance amsGuidance){
		List<AmsGuidanceExportDTO> list = new ArrayList<AmsGuidanceExportDTO>();
		//所有已完成的任务
		HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(null).finished()
						.includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();
		//已完成的业务指导任务
		histTaskQuery.processDefinitionKey(amsGuidance.getAct().getProcDefKey());
		
		Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        for(int i=beginMonth; i<=endMonth;i++){
        	AmsGuidanceExportDTO guidance = new AmsGuidanceExportDTO();
        	//设置月份
            cal.set(Calendar.MONTH, i-1);
            //获取某月最小天数
            int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_MONTH,firstDay);
            Date beginDate = cal.getTime();
            //获取某月最大天数
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            //设置日历中月份的最大天数
            cal.set(Calendar.DAY_OF_MONTH, lastDay);
            Date endDate = cal.getTime();
            //已完成任务的起止日期
    		histTaskQuery.taskCompletedAfter(beginDate);
    		histTaskQuery.taskCompletedBefore(endDate);
    		guidance.setMonth(i+"");
    		guidance.setCount(histTaskQuery.count());
    		list.add(guidance);
        }
		
		return list;
		
	}
}