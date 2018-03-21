/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.ucams.common.config.Global;
import com.ucams.common.constant.UcamsConstant;
import com.ucams.common.mongo.DBOperationsU;
import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.modules.act.service.ActTaskService;
import com.ucams.modules.ams.dao.AmsDesExtendDao;
import com.ucams.modules.ams.dao.AmsProjectInfoDao;
import com.ucams.modules.ams.entity.AmsConstructDes;
import com.ucams.modules.ams.entity.AmsDesExtend;
import com.ucams.modules.ams.entity.AmsDesProgram;
import com.ucams.modules.ams.entity.AmsLandDes;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.sys.dao.RoleDao;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.entity.SysBaseInfo;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.service.SysBaseInfoService;
import com.ucams.modules.sys.service.SystemService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 工程项目管理Service
 * @author ws
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class AmsProjectInfoService extends CrudService<AmsProjectInfoDao, AmsProjectInfo> {
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AmsProjectInfoDao amsProjectInfoDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SysBaseInfoService sysBaseInfoService;
	@Autowired
	private AmsDesExtendDao amsDesExtendDao;
	@Autowired
	private AmsConstructDesService amsConstructDesService;
	@Autowired
	private AmsLandDesService amsLandDesService;
	@Autowired
	private DBOperationsU dbOperationU;
	/**
	 * 根据工程名称获取工程
	 * @param projectName
	 * @return
	 */
	public AmsProjectInfo getByProjectName(AmsProjectInfo amsProjectInfo) {
		return amsProjectInfoDao.getByProjectName(amsProjectInfo);
	}
	
	/**
	 * 获取当前年最大编号
	 * @return
	 */
/*	public AmsProjectInfo getProjectByNo() {
		return amsProjectInfoDao.getProjectByNo()==null?new AmsProjectInfo():amsProjectInfoDao.getProjectByNo();
	}
*/	/**
	 * 获取当前年最大编号(5位顺序号）
	 * @return
	 */
	public AmsProjectInfo getProjectByNoNew() {
		return amsProjectInfoDao.getProjectByNoNew()==null?new AmsProjectInfo():amsProjectInfoDao.getProjectByNoNew();
	}
	
	public AmsProjectInfo get(String id) {
		return super.get(id);
	}
	
	public Page<AmsProjectInfo> findPage(Page<AmsProjectInfo> page, AmsProjectInfo amsProjectInfo) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		amsProjectInfo.getSqlMap().put("dsf", dataScopeFilter(amsProjectInfo.getCurrentUser(), "o", "a"));
	
		return super.findPage(page, amsProjectInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsProjectInfo amsProjectInfo) {
	
		//添加项目
		if (StringUtils.isBlank(amsProjectInfo.getId())) {// 启动流程
			amsProjectInfo.preInsert();
			dao.insert(amsProjectInfo);
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("applyUser", amsProjectInfo.getLoginName());
			if ("1".equals(amsProjectInfo.getIsfirst())) {
				actTaskService.startProcess("projectActivity",
						"ams_project_info", amsProjectInfo.getId(),
						amsProjectInfo.getProjectName(), vars);
			} else if ("2".equals(amsProjectInfo.getIsfirst())) {
				actTaskService.startProcess("projectActivity",
						"ams_project_info", amsProjectInfo.getId(),
						amsProjectInfo.getProjectName());
			}
		} else if ("edit".equals(amsProjectInfo.getAct().getTaskDefKey())) {// 流程操作：重申或者销毁
			Map<String, Object> vars = Maps.newHashMap();
			dao.update(amsProjectInfo);
			amsProjectInfo.preUpdate();
			amsProjectInfo.getAct().setComment(
					("yes".equals(amsProjectInfo.getAct().getFlag()) ? "[重申] "
							: "[销毁] "));
			vars.put("pass",
					"yes".equals(amsProjectInfo.getAct().getFlag()) ? "1" : "0");
			actTaskService.complete(amsProjectInfo.getAct().getTaskId(),
					amsProjectInfo.getAct().getProcInsId(), amsProjectInfo
							.getAct().getComment(), vars);
		} else {// 仅保存修改	
			super.save(amsProjectInfo);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsProjectInfo amsProjectInfo) {
		super.delete(amsProjectInfo);
	}
	
	/**
	 * 获取当前登录建设单位Id
	 * @return
	 */
	public String getJsUnitId(){
		String resjsunitid="";
		List<Role> roleList =UserUtils.getUser().getRoleList();
		for (Role role : roleList) {
			if("security-role".equals(role.getRoleType())){
				resjsunitid=role.getId();
			}
		}
		return resjsunitid;
	}
	/**
	 * 项目审批流程保存业务
	 * @param amsProjectInfo
	 * @author gyl
	 */
	@Transactional(readOnly = false)
	public void saveProjectActivity(AmsProjectInfo amsProjectInfo){
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		amsProjectInfo.preUpdate();
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = amsProjectInfo.getAct().getTaskDefKey();
		if("approve".equals(taskDefKey)){
			amsProjectInfo.getAct().setComment(("yes".equals(amsProjectInfo.getAct().getFlag())?"[同意] ":"[驳回] "));
			amsProjectInfo.setCheckStatus("yes".equals(amsProjectInfo.getAct().getFlag())?"1":"2");//1:审核通过  2:审核不通过
			dao.updateProjectStatus(amsProjectInfo);
			
			//首次报建项目审核通过分配系统管理权限
			if("1".equals(amsProjectInfo.getIsfirst())&&"1".equals(amsProjectInfo.getCheckStatus())){
				Role role=roleDao.get(amsProjectInfo.getConstructionId());
				Role rolep=roleDao.getById(amsProjectInfo.getConstructionId());
				role.setOffice(rolep.getOffice());
				//增加责任主体权限--动态菜单--默认roleId为100
				Role roleMenu=roleDao.get("100");
				role.setMenuList(roleMenu.getMenuList());
				//固定菜单
				/*String menupid="13,50e416dd9a744643a2bcf5b900905f34,35769c4cee684368bd07256214da0c53,ffe23a0e15f24aadaee9d86220a3cc13,8ccd46b968eb42bf9d1264c48b9a69dd,6125141f19204e7abf4079c34e9c3d74,b7e58357135d4bb78bef9def7607a7e5";
				String mpids[]=menupid.split(",");
				for (int i = 0; i < mpids.length; i++) {
					role.getMenuList().add(new Menu(mpids[i]));
				}*/
				role.setUpdateDate(new Date());
				systemService.saveRole(role);
			}
			Office office = officeService.get(amsProjectInfo.getId());
			office.setDelFlag("0");
			 officeService.update(office);
			//获取系统设置信息增加工程和用地规划
			//建设工程规划constructDes
			
			SysBaseInfo sysBaseInfo =sysBaseInfoService.findSysBaseInfo();
			if("1".equals(sysBaseInfo.getConstructDes())&&"yes".equals(amsProjectInfo.getAct().getFlag())){
				AmsConstructDes amsConstructDes	=new AmsConstructDes();
				amsConstructDes.setProjectId(amsProjectInfo.getId());
				amsConstructDesService.save(amsConstructDes);
			}
			//建设用地规划landDes
			if("1".equals(sysBaseInfo.getLandDes())&&"yes".equals(amsProjectInfo.getAct().getFlag())){
				AmsLandDes amsLandDes=new AmsLandDes();
				amsLandDes.setProjectId(amsProjectInfo.getId());
				amsLandDesService.save(amsLandDes);
			}
			
		}
		//添加审核意见
		amsProjectInfo.getAct().setComment(amsProjectInfo.getOpinion());
		dao.updateProjectBusinessMan(amsProjectInfo);
		vars.put("pass", "yes".equals(amsProjectInfo.getAct().getFlag())? "1" : "0");
		
		//提交流程
		actTaskService.complete(amsProjectInfo.getAct().getTaskId(), amsProjectInfo.getAct().getProcInsId(), amsProjectInfo.getAct().getComment(), vars);
	}
	
	/**
	 * 获取机构自动排序序号
	 * @return
	 */
	public String getOfficCode(int num){
			/*int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			return office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0");*/
			return String.valueOf(System.currentTimeMillis()+num);
		}
	
	/**
	 * 查看项目扩展信息表单
	 * @param amsUnitProInfo
	 * @return
	 */
	public List<AmsDesExtend> findExtendDataList(AmsProjectInfo amsProjectInfo) { 
		AmsDesProgram amsDesProgram=new AmsDesProgram();
		AmsDesExtend amsDesExtend=new AmsDesExtend();
		//工程项目类型
		amsDesProgram.setUnitProjectType(amsProjectInfo.getProjectType());
		//方案类型
		amsDesProgram.setProgramType(UcamsConstant.AMS_DESPROGRAM_GCXM);
		amsDesExtend.setAmsEnginproTable(amsDesProgram);
		List<AmsDesExtend>  amsDesExtends=amsDesExtendDao.findExtendDataList(amsDesExtend);
		if(amsDesExtends!=null && amsDesExtends.size()>0&&amsProjectInfo.getDescriptionJson()!=null&&!"".equals(amsProjectInfo.getDescriptionJson())){
			JSONObject jsonObject=JSONObject.parseObject(amsProjectInfo.getDescriptionJson());
			for (int i = 0; i < amsDesExtends.size(); i++) {
				AmsDesExtend amsDesExtend1=amsDesExtends.get(i);
				JSONObject data=JSONObject.parseObject(jsonObject.getString(UcamsConstant.AMS_MONGO_IDS_DATA));
				amsDesExtend1.setSettings(data==null?"":data.getString(amsDesExtend1.getName()));
			}
		}
		
		return amsDesExtends;
	}
	
	/**
	 * 获取扩展信息表单内容
	 * @param amsProjectInfo
	 */
	@Transactional(readOnly = false)
	public AmsProjectInfo getDescriptionJson(AmsProjectInfo amsProjectInfo) {
		boolean djsonIsNull=false;
		if(amsProjectInfo.getDescriptionJson()==null||"".equals(amsProjectInfo.getDescriptionJson())){
			djsonIsNull=true;
		}
		List<AmsDesExtend> amsDesExtends=amsProjectInfo.getAmsDesExtendList();
		if(amsDesExtends!=null && amsDesExtends.size()>0){
			JSONObject jObject=new JSONObject();
			jObject.put(UcamsConstant.AMS_MONGO_IDS_PROJECT, amsProjectInfo.getId());
			Map<String,Object> map =new HashMap<String,Object>();
			for (AmsDesExtend amsDesExtend:amsDesExtends) {
				map.put(amsDesExtend.getName(), amsDesExtend.getSettings());
			}
			JSONObject data=  JSON.parseObject(JSONObject.toJSONString(map));
			jObject.put(UcamsConstant.AMS_MONGO_IDS_DATA, data);
			amsProjectInfo.setDescriptionJson(jObject.toJSONString());
			dao.update(amsProjectInfo);
		//	super.save(amsProjectInfo);
			//mongodb数据保存
			//dbOperationU.save(data,"ams_unit_pro_info");
			
			//mongodb数据保存
			if (djsonIsNull&&amsProjectInfo.getDescriptionJson().length()>1)
			{
				dbOperationU.save(jObject,UcamsConstant.AMS_MONGO_TABLE_NAME_PROJECT);
			}else{
				DBObject queryCondition = new BasicDBObject();  
				queryCondition.put(UcamsConstant.AMS_MONGO_IDS_UNIT,"");  
				queryCondition.put(UcamsConstant.AMS_MONGO_IDS_PROJECT, amsProjectInfo.getId());  
				dbOperationU.updateDocument(UcamsConstant.AMS_MONGO_TABLE_NAME_PROJECT , queryCondition, new BasicDBObject(jObject));
			}
		}
		return amsProjectInfo;
	}

}