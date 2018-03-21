/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.ucams.common.config.Global;
import com.ucams.common.constant.CommonConstant;
import com.ucams.common.constant.UcamsConstant;
import com.ucams.common.mongo.DBOperations;
import com.ucams.common.mongo.DBOperationsU;
import com.ucams.common.persistence.Page;
import com.ucams.common.service.CrudService;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.JSONUtils;
import com.ucams.modules.ams.entity.AmsArchivesInfo;
import com.ucams.modules.ams.entity.AmsDesExtend;
import com.ucams.modules.ams.entity.AmsDesProgram;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsPreRpt;
import com.ucams.modules.ams.utils.CheckFileUtils;
import com.ucams.modules.ams.dao.AmsArchivesInfoDao;
import com.ucams.modules.ams.dao.AmsDesExtendDao;
import com.ucams.modules.ams.dao.AmsFileInfoDao;
import com.ucams.modules.ams.dao.AmsPreRptDao;

/**
 * 电子文件检查报告Service
 * @author sunjx
 * @version 2017-08-04
 */
@Service
@Transactional(readOnly = true)
public class AmsCheckElectronicFileService extends CrudService<AmsPreRptDao, AmsPreRpt> {
	
	@Autowired
	private AmsFileInfoDao amsFileInfoDao;
	@Autowired
	private DBOperationsU dbOperationU;
	@Autowired
	private AmsDesExtendDao amsDesExtendDao;
	@Autowired
	private AmsArchivesInfoDao amsArchviesInfoDao;
	
	/**
	 * 检查案卷信息
	 * @param unitProInfo
	 * @return 检查报告
	 */
	@Transactional(readOnly = false)
	public List<String> checkArchives(List<AmsArchivesInfo> archivesList){
		
		List<String> orgenRptList = new ArrayList<String>();
		try {
			for(AmsArchivesInfo archives : archivesList){
				 //---------------------------------------检查案卷信息------------------------------------//
				 List<String> results = new ArrayList<String>();
				 //检查案卷基本信息
				 results = CheckFileUtils.checkObjFieldIsNull(archives); 
				 //组织检测报告
				 List<String> archivesRpt =  this.orgenRpt(results, archives.getArchivesName(), null);
				 
				 //---------------------------------------检查案卷下文件基础/拓展信息--------------------//
				 List<AmsFileInfo> fileInfos =  amsFileInfoDao.findCheckList(archives.getId());   //查询案卷下的所有文件
				 //文件检测报告
				 orgenRptList.addAll(this.orgenRpt(results, archives.getArchivesName(), null));
				 orgenRptList.addAll(this.checkFile(fileInfos,archives.getArchivesName()));
				 
				 //设置案卷检测状态
				 this.changeCheckType(orgenRptList, archives);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orgenRptList;
	}
	
	/**
	 * 检查文件信息
	 * @param unitProInfo
	 * @return 检查报告
	 */
	public List<String> checkFile(List<AmsFileInfo> fileInfos,String archivesName){
		List<String> orgenRptList = new ArrayList<String>();
		try {
			//检查文件拓展信息
			 Map<String, Map> mongodbDatas = this.getFileExpand(fileInfos,UcamsConstant.AMS_MONGO_TABLE_NAME_FILE);
			 for(AmsFileInfo file : fileInfos){
				 //---------------------------------------检查文件信息------------------------------------//
				List<String> resultsFileMsg = new ArrayList<String>();
				//List<String> resultsFileUrl = new ArrayList<String>();
				 //检查文件基本信息
				resultsFileMsg = CheckFileUtils.checkObjFieldIsNull(file); 				 
				 //文件没有拓展数据
				 if(EntityUtils.isNotEmpty(mongodbDatas) && mongodbDatas.size() >0){
					 List<AmsDesExtend> amsDesExtend = this.getDesExtendNotNull(UcamsConstant.AMS_DESPROGRAM_WJZL, null);
					 //从mongo数据中取出当前单位工程的拓展数据
					 Map<? , ?> gomap = mongodbDatas.get(file.getId()); 
					  for(AmsDesExtend extend : amsDesExtend){
						 //查出拓展数据中value为空的项
						 if(EntityUtils.isEmpty(gomap.get(extend.getName()))){  
							 //将描述存入报告
							 resultsFileMsg.add(extend.getComments());  					      
						 }
					  }
				 }else{
					 resultsFileMsg.add("没有拓展数据");
				 }
				 //文件信息检查报告
				 orgenRptList.addAll(this.orgenRpt(resultsFileMsg, archivesName, file.getFileName()));
				 //---------------------------------------检查物理文件------------------------------------//
				 orgenRptList.addAll(this.checkElectronicFile(file,archivesName));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return orgenRptList;
	}
	
	/**
	 * 检查电子文件
	 * @param file 文件模型
	 * @throws IOException 
	 */
	private List<String> checkElectronicFile(AmsFileInfo file,String archivesName) throws IOException{
		 List<String> resultsFileMsg = new ArrayList<String>();
		 //组织文件地址
		 String fileUrl = Global.getConfig("httpd.online.url") + "/" + file.getFilePath();
		 //File thisFile = new File(file.getFilePath());
		 InputStream fileStream = CheckFileUtils.interUrlInputStream(fileUrl);
		 //校验文件是否存在
		 if(CheckFileUtils.isNetFileAvailable(fileUrl)){
			 if("1".equals(file.getFileType())){  	//pdf文件
				try {
					String pdfNum =  CheckFileUtils.checkPdf(fileStream, file.getFilecount());  //校验pdf页数
					//Map<String, String> fileResMap =KGUtils.getSigninfo(fileStream); //检测pdf内签章
					if(EntityUtils.isNotEmpty(pdfNum) ){
						resultsFileMsg.add(pdfNum);
					}
//					if(EntityUtils.isNotEmpty(fileResMap)){
//						resultsFileMsg.add(fileResMap.get("trope"));  //签章状态入库
//						queryMap.put("singnetType", fileResMap.get("type")); 
//						queryMap.put("fileId", file.getFileId());
//						amsFileInfoDao.updateFileSignetType(queryMap);  //修改文件的签章状态
//					}
				} catch (Exception e) {
					e.printStackTrace();
					fileStream.close();
				}
			  }else if("2".equals(file.getFileType())){  //图片文件
				  String fileRes =  CheckFileUtils.checkImage(fileStream);
				  if(EntityUtils.isNotEmpty(fileRes)){
						resultsFileMsg.add(fileRes);  
				  }
			  }else{
				  resultsFileMsg.add("非法文件类型");
			  }
			//关闭流
		    fileStream.close();
		 }else{
			 resultsFileMsg.add("文件不存在");
		 }
		 
		 return this.orgenRpt(resultsFileMsg, archivesName, file.getFileName());
		
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
	
	/**
	 * 查询文件拓展数据
	 * @param Map<String, Map>
	 * @return
	 */
	public Map<String, Map> getFileExpand(List<AmsFileInfo> list,String tableName){
		DBObject queryCondition = new BasicDBObject();          
//		List<String> reports = null ;
		Map<String, Map> containers = new HashMap<String, Map>();
		try {
			 BasicDBList values = new BasicDBList();  
			 for(AmsFileInfo file : list){
				 values.add(file.getId());                    //添加in的查询条件
//				 containers.put(unit.getId(), unit.getUnitProjectName());  //将单位工程信息存入map容器
			 }
			 queryCondition.put(UcamsConstant.AMS_MONGO_IDS_FILE, new BasicDBObject("$in", values));  
			 List<DBObject> results =  dbOperationU.selectDocument(tableName, queryCondition);
			 for(DBObject dbo : results){
				 containers.put((String)dbo.get(UcamsConstant.AMS_MONGO_IDS_FILE),
						 	this.resStringToMap(dbo.get(UcamsConstant.AMS_MONGO_IDS_DATA).toString()));
			 }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	   return containers;
	}
	
	//String 转map
	private Map<String, String> resStringToMap(String json){
		return  JSON.parseObject(json, new TypeReference<HashMap<String,String>>() {});
	}
	
	/**
	 * 组织检测报告
	 * @param results 检测情况列表
	 * @param archivesName 案卷名称
	 * @param fileName 文件名称
	 * @return
	 */
	private List<String> orgenRpt(List<String> results,String archivesName, String fileName) {
		List<String> organRpt = new ArrayList<String>();
		try {
			StringBuffer str = null;
			//组织报告内容
			for(String content : results){
				str = new StringBuffer();
				if(EntityUtils.isNotEmpty(archivesName)){
					str.append(archivesName + "-");
				}
				if(EntityUtils.isNotEmpty(fileName)){
					str.append(fileName + "-");
				}
				str.append(content);
				organRpt.add(str.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return organRpt;
	}
	
	/**
	 * 设置案卷的检查状态
	 * @param rptList 检测报告
	 * @param archivesId 案卷ID
	 */
	@Transactional(readOnly = false)
	private void changeCheckType(List<String> rptList,AmsArchivesInfo amsArchives){
		JSONObject checkType = new JSONObject();
		//如果报告存在 则已检查 未通过 -- 报告不存在则已检查-已通过
		if(EntityUtils.isNotEmpty(rptList) && rptList.size() > 0){
			amsArchives.setExten2(CommonConstant.AMS_COMMON_PARAM_NUM00);
		}else{
			amsArchives.setExten2(CommonConstant.AMS_COMMON_PARAM_NUM01);
		}
		amsArchviesInfoDao.update(amsArchives);
		
	}
}