/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.ucams.common.constant.UcamsConstant;
import com.ucams.common.mongo.DBOperationsU;
import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.dao.AmsDesExtendDao;
import com.ucams.modules.ams.dao.AmsUnitDetailinfoDao;
import com.ucams.modules.ams.dao.AmsUnitProInfoDao;
import com.ucams.modules.ams.entity.AmsDesExtend;
import com.ucams.modules.ams.entity.AmsDesProgram;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.entity.AmsUnitProInfo;
import com.ucams.modules.sys.dao.RoleDao;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.service.SystemService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 单位工程管理Service
 * @author ws
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class AmsUnitProInfoService extends CrudService<AmsUnitProInfoDao, AmsUnitProInfo> {
	@Autowired
	private DBOperationsU dbOperationU;
	@Autowired
	private SystemService systemService;
	@Autowired
	private AmsProjectInfoService amsProjectInfoService;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AmsUnitProInfoDao amsUnitProInfoDao;
	@Autowired
	private AmsDesExtendDao amsDesExtendDao;
	@Autowired
	private AmsUnitDetailinfoDao  amsUnitDetailinfoDao;
	
	public AmsUnitProInfo get(String id) {
		return super.get(id);
	}
	
	public List<AmsUnitProInfo> findList(AmsUnitProInfo amsUnitProInfo) {
		return super.findList(amsUnitProInfo);
	}
	
	public Page<AmsUnitProInfo> findPage(Page<AmsUnitProInfo> page, AmsUnitProInfo amsUnitProInfo) {
		amsUnitProInfo.setPage(page);
		page.setList(amsUnitProInfoDao.findList(amsUnitProInfo));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(AmsUnitProInfo amsUnitProInfo) {
		super.save(amsUnitProInfo);
	}
	
	/**
	 * 单位工程添加后增加对应的机构与role关系
	 * @param projectid
	 * @param office
	 */
	@Transactional(readOnly = false)
	public void saveRoleUnitProject(String projectid,Office office) {
		AmsProjectInfo amsProjectInfo =amsProjectInfoService.get(projectid);
		Role role=roleDao.get(amsProjectInfo.getConstructionId());
		Role rolep=roleDao.getById(amsProjectInfo.getConstructionId());
		role.setOffice(rolep.getOffice());
		role.getOfficeList().add(office);
		role.setUpdateDate(new Date());
		systemService.saveRole(role);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsUnitProInfo amsUnitProInfo) {
		super.delete(amsUnitProInfo);
	}
	
	/**
	 * 根据单位工程获取动态form
	 * @param amsUnitProInfo
	 * @return
	 */
	public List<AmsDesExtend> findExtendDataList(AmsUnitProInfo amsUnitProInfo) { 
		AmsDesProgram amsDesProgram=new AmsDesProgram();
		AmsDesExtend amsDesExtend=new AmsDesExtend();
		DBObject queryCondition = new BasicDBObject();  
		//单位工程类型
		amsDesProgram.setUnitProjectType(amsUnitProInfo.getUnitProjectType());
		//方案类型
		amsDesProgram.setProgramType(UcamsConstant.AMS_DESPROGRAM_ZYJZ);
		amsDesExtend.setAmsEnginproTable(amsDesProgram);
		List<AmsDesExtend>  amsDesExtends=amsDesExtendDao.findExtendDataList(amsDesExtend);
		queryCondition.put(UcamsConstant.AMS_MONGO_IDS_UNIT, amsUnitProInfo.getId());  
		queryCondition.put(UcamsConstant.AMS_MONGO_IDS_PROJECT,amsUnitProInfo.getProjectId());  
		List<DBObject> results =  dbOperationU.selectDocument(UcamsConstant.AMS_MONGO_TABLE_NAME_UNIT, queryCondition);
		if(amsDesExtends!=null && amsDesExtends.size()>0&&results!=null&& results.size()>0){
			JSONObject jsonObject=JSONObject.parseObject(results.get(0).toString());
			for (int i = 0; i < amsDesExtends.size(); i++) {
				AmsDesExtend amsDesExtend1=amsDesExtends.get(i);
				JSONObject data=JSONObject.parseObject(jsonObject.getString(UcamsConstant.AMS_MONGO_IDS_DATA));
				amsDesExtend1.setSettings(data==null?"":data.getString(amsDesExtend1.getName()));
			}
		}
		return amsDesExtends;
	}
	
	/**
	 * 保存专业记载信息
	 * @param amsUnitProInfo
	 */
	@Transactional(readOnly = false)
	public void majorSave(AmsUnitProInfo amsUnitProInfo) {
		List<AmsDesExtend> amsDesExtends=amsUnitProInfo.getAmsDesExtendList();
		if(amsDesExtends!=null && amsDesExtends.size()>0){
			JSONObject jObject=new JSONObject();
			jObject.put(UcamsConstant.AMS_MONGO_IDS_PROJECT, amsUnitProInfo.getProjectId());
			jObject.put(UcamsConstant.AMS_MONGO_IDS_UNIT, amsUnitProInfo.getId());
			Map<String,Object> map =new HashMap<String,Object>();
			for (AmsDesExtend amsDesExtend:amsDesExtends) {
				map.put(amsDesExtend.getName(), amsDesExtend.getSettings());
			}
			JSONObject data=  JSON.parseObject(JSONObject.toJSONString(map));
			jObject.put(UcamsConstant.AMS_MONGO_IDS_DATA, data);
			amsUnitProInfo.setSpecialtyJson(jObject.toJSONString());
			
			//mongodb存储
			dbOperationU.save(jObject,UcamsConstant.AMS_MONGO_TABLE_NAME_UNIT);
			/*if (amsUnitProInfo.getSpecialtyJson()==null&&"".endsWith(amsUnitProInfo.getSpecialtyJson())){
				
			}else{
				DBObject queryCondition = new BasicDBObject();  
				queryCondition.put(UcamsConstant.AMS_MONGO_IDS_UNIT, amsUnitProInfo.getId());  
				queryCondition.put(UcamsConstant.AMS_MONGO_IDS_PROJECT,amsUnitProInfo.getProjectId());  
				dbOperationU.updateDocument(UcamsConstant.AMS_MONGO_TABLE_NAME_UNIT , queryCondition, new BasicDBObject(jObject));
			}*/
		}
		super.save(amsUnitProInfo);
	}
	
	/**
	 * 获取当前年最大编号
	 * @return
	 */
	public AmsUnitProInfo getUnitProjectByNo() {
		return amsUnitProInfoDao.getUnitProjectByNo();
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
			List<AmsProjectInfo> list=amsUnitProInfoDao.findProjectList(amsProjectInfo);
			page.setList(list);
		}
		return page;
	}
	/**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public boolean isNumeric(String str){
           Pattern pattern = Pattern.compile("[0-9]*");
           Matcher isNum = pattern.matcher(str);
           if( !isNum.matches() ){
               return false;
           }
           return true;
    }
}