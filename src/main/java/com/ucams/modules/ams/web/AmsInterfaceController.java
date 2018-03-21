package com.ucams.modules.ams.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ucams.common.config.Global;
import com.ucams.common.constant.UcamsConstant;
import com.ucams.common.persistence.Page;
import com.ucams.common.utils.DateUtils;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.FTPPointUtil;
import com.ucams.common.utils.FileUtils;
import com.ucams.common.utils.IdGen;
import com.ucams.common.utils.UcamsTools;
import com.ucams.common.utils.FTPPointUtil.UploadStatus;
import com.ucams.common.utils.excel.ExportExcel;
import com.ucams.common.web.BaseController;
import com.ucams.modules.ams.entity.AmsAcceptance;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsPreRpt;
import com.ucams.modules.ams.form.ArchivesInfoForm;
import com.ucams.modules.ams.form.FileInfoForm;
import com.ucams.modules.ams.form.ProjectInfoForm;
import com.ucams.modules.ams.form.SingleProjectForm;
import com.ucams.modules.ams.service.AmsInterfaceService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 上报数据接口Controller
 * @author bql
 * @version 2017-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsInterface")
public class AmsInterfaceController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(AmsInterfaceController.class);

	@Autowired
	private AmsInterfaceService amsInterfaceService;
	
	/**
	 * 根据用户名密码获取用户工程及归档文件设置信息
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = {"findDataAmsProjectInfo"})
	@ResponseBody
	public Object findDataAmsProjectInfo(String userName,String password){
		Map<String, Object> result = null;
		try {
			result = amsInterfaceService.findUserProjectJson(userName, password);
			logger.info("获取用户工程及归档文件设置信息成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = Maps.newHashMap();
			result.put("result", "failure");
			logger.info("获取用户工程及归档文件设置信息失败");
		}
		return result;
	}
	/**
	 * 资料软件文件上传接口
	 * @param file
	 * @param fileJson
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"writeAmsFileInfo"})
	@ResponseBody
	public String writeAmsFileInfo(@RequestParam("file") CommonsMultipartFile file,String fileJson,HttpServletRequest request, HttpServletResponse response){
		String path = UcamsConstant.FilePath;
		String view = null;
		File paperfile =new File(path);
		//如果文件夹不存在则创建
		if  (!paperfile .exists()  && !paperfile .isDirectory())
			paperfile .mkdir();
		try {
			fileJson=StringEscapeUtils.unescapeHtml4(fileJson);
			FileInfoForm fileInfoForm=JSON.parseObject(fileJson, FileInfoForm.class);
			//验证用户名密码
			view=amsInterfaceService.checkUserPassword(fileInfoForm);
			if (!"nulldata".equals(view)&&!"incorrect".equals(view)&&!"errorprojectid".equals(view)&&!"unitreport".equals(view)) {
				fileInfoForm.setCreateUser(view);
				//文件处理
				//判断文件字节是否为0 (文件是否真实上传)
				if (EntityUtils.isNotEmpty(file)&&file.getBytes().length > 0) {
					StringBuffer filePath = new StringBuffer();
					//组织文件存储路径
					filePath.append("projectFile/" +  fileInfoForm.getProjectId()); 
					//若有单位工程ID
					if(EntityUtils.isNotEmpty(fileInfoForm.getUnitProjectId())){
						filePath.append("/" + fileInfoForm.getUnitProjectId());
					}
					filePath.append("/" + UcamsTools.createNewFileName(file));
					//用缓冲区来实现这个转换即使用java 创建的临时文件
					File f=File.createTempFile("fileSavetmp_"+ fileInfoForm.getId() , null);
					//链接FTP服务 -- 在线业务
					FTPPointUtil ftp = new FTPPointUtil("tadmin", "admin");
					//链接FTP服务
					ftp.connect();
					file.transferTo(f);
					//FTP服务上传
					UploadStatus status  = ftp.upload(f, filePath.toString());
					//删除缓冲区的临时文件
					f.deleteOnExit();
					//断开FTP服务
					ftp.disconnect();
					//判断上传结果,不成功
					if(!FTPPointUtil.UploadStatus.Upload_New_File_Success.equals(status)){
						throw new IOException("文件管理-文件上传-文件上传失败!");
					}
					//修改文件路径
					fileInfoForm.setFilePath(filePath.toString());
				}
				
				amsInterfaceService.saveAmsFileInfo(fileInfoForm);
				view= "success";
				logger.info("资料软件单文件上传成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			view= "failure";
		} 
		return view;
	}
	/**
	 * 跳转文件上传页
	 * @return
	 */
	@RequiresPermissions("ams:amsOfflineFile:upload")
	@RequestMapping(value = {"form",""})
	public String form(Model model) {
		return "modules/ams/amsOfflineFileForm";
	}
	
	@RequiresPermissions("ams:amsOfflineFile:upload")
	@RequestMapping(value = {"checkUserNameAndPassword"})
	@ResponseBody
	public Object checkUserNameAndPassword(String userName,String password){
		Map<String, Object> result = null;
		try {
			result = amsInterfaceService.findUserRoleByPassword(userName, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = Maps.newHashMap();
			result.put("result", "failure");
		}
		return result;
	}
	/**
	 * 接收上传文件碎片
	 * @return
	 */
	@RequiresPermissions("ams:amsOfflineFile:upload")
	@RequestMapping(value = {"uploadOfflineFileSplice"})
	@ResponseBody
	public String uploadOfflineFileSplice(@RequestParam("file") CommonsMultipartFile file,String fileId,Integer index,String filename,Integer total,HttpServletRequest request,HttpServletResponse response){
		String view = "success";
		String path = UcamsConstant.ZipFilePath;
		
		File paperfile =new File(path);
		//如果文件夹不存在则创建
		if  (!paperfile .exists() && !paperfile .isDirectory())
			paperfile .mkdir();
		Session session=UserUtils.getSession();
		String[] fileList=(String[]) session.getAttribute(UcamsConstant.OfflineFileSplice);
		if (fileList==null||fileList.length!=total){
			fileList=new String[total];
			session.setAttribute(UcamsConstant.OfflineFileSplice, fileList);
		}
	    try {
			String guId = IdGen.uuid()+filename;
	    	File newFile=new File(path,guId);
	        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
	        file.transferTo(newFile);
			
	        fileList[index]=guId;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			view= "failure";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			view= "failure";
		}
	    return view;
	}
	
	/**
	 * 拼接上传文件碎片
	 */
	@RequiresPermissions("ams:amsOfflineFile:upload")
	@RequestMapping(value = {"splitjointOfflineFileSplice"})
	@ResponseBody
	public Object splitjointOfflineFileSplice(String filename,Integer total){
		Map<String, Object> result = Maps.newHashMap();
		String path = UcamsConstant.ZipFilePath;
		try{
			File paperfile =new File(path);
			//如果文件夹不存在则创建    
			if  (!paperfile .exists()  && !paperfile .isDirectory())
				paperfile .mkdir();
			Session session=UserUtils.getSession();
			String[] fileList=(String[]) session.getAttribute(UcamsConstant.OfflineFileSplice);
			boolean condition=true;
			if (fileList==null||fileList.length!=total){
				condition=false;
			}
	        for(String fileId:fileList){
	        	if (EntityUtils.isEmpty(fileId)) {
	        		condition=false;
				}
	        }
	        if (condition) {
	        	filename=DateUtils.getDate("yyyyMMddHHmmssms")+".zip";
	        	OutputStream os = new FileOutputStream(new File(path,filename),true);
		    	for (int i = 0; i < fileList.length; i++) {
		    		File localFile=new File(path,fileList[i]);
					InputStream in =new FileInputStream(localFile);
		            byte[] bytes = new byte[1024*100];
		            int c;
		            while((c = in.read(bytes))!= -1){
		            	os.write(bytes,0,c);   
		            }
		            in.close();
		            localFile.delete();
				}
		    	os.flush();
				os.close();
				session.removeAttribute(UcamsConstant.OfflineFileSplice);
				session.setAttribute(UcamsConstant.OfflineZipFile, filename);
				result.put("result", "success");
				logger.info("拼接上传文件碎片成功");
			}else{
				for(String fileId:fileList){
		        	if (EntityUtils.isNotEmpty(fileId)) {
		        		new File(path,fileId).delete();
					}
		        }
				session.removeAttribute(UcamsConstant.OfflineFileSplice);
				result.put("result", "losefile");
				logger.info("上传文件碎片缺失");
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("result", "failure");
			logger.info("拼接上传文件碎片失败");
		}
		return result;
	}
	/**
	 * 撤销文件上传
	 * @param filename
	 * @return
	 */
	@RequiresPermissions("ams:amsOfflineFile:upload")
	@RequestMapping(value = {"cancelOfflineFileSplice"})
	@ResponseBody
	public String cancelOfflineFileSplice(){
		String path = UcamsConstant.ZipFilePath;
		try{
			File paperfile =new File(path);
			//如果文件夹不存在则创建    
			if  (!paperfile .exists()  && !paperfile .isDirectory())
				paperfile .mkdir();
			Session session=UserUtils.getSession();
			String[] fileList=(String[]) session.getAttribute(UcamsConstant.OfflineFileSplice);
			if (fileList==null){
				return "finish";
			}
	        for(String fileId:fileList){
	        	if (EntityUtils.isNotEmpty(fileId)) {
	        		new File(path,fileId).delete();
				}
	        }
	        session.removeAttribute(UcamsConstant.OfflineFileSplice);
	        logger.info("文件上传撤销成功");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("文件上传撤销失败");
			return "failure";
		}
		return "success";
	}
	
	/**
	 * 验证上传文件包
	 * @param userName
	 * @param password
	 * @param filename
	 * @return
	 */
	@RequiresPermissions("ams:amsOfflineFile:upload")
	@RequestMapping(value = {"provingOfflineFileData"})
	@ResponseBody
	public Object provingOfflineFileData(String userName,String password){
		Map<String, Object> result =null;
		String zipPath = UcamsConstant.ZipFilePath;
		String unzipPath = UcamsConstant.UnZipFilePath;
		File paperfile =new File(unzipPath);
		//如果文件夹不存在则创建    
		if  (!paperfile .exists()  && !paperfile .isDirectory())
			paperfile .mkdir();
		String filename=(String) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipFile);
		File zipFile=new File(zipPath+"/"+filename);
		try{
			unzipPath=unzipPath+"/"+filename.substring(0, filename.lastIndexOf("."));
			//文件解压
			if(!FileUtils.unBigZipFiles(zipFile, unzipPath)){
				result=new HashMap<String, Object>();
				result.put("result", "error");//文件解压失败
				result.put("data", "文件解压失败");
				logger.info("离线文件解压失败");
				return result;
			}
			result=amsInterfaceService.checkUserOfflineFileData(userName, password, unzipPath);
			if("success".equals(result.get("result"))){
				logger.info("离线文件验证完成");
			}else{
				logger.info(result.get("data").toString());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=result==null?new HashMap<String, Object>():result;
			result.put("result", "failure");
			logger.info("离线文件验证失败");
		}finally{
			zipFile.delete();
		}
		return result;
	}
	@RequiresPermissions("ams:amsOfflineFile:upload")
	@RequestMapping(value = {"showOfflineFileDetailed"})
	public String showOfflineFileDetailed(Model model) {
		
		String jsonStr=(String) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipJsonData);
		JSONObject jsonObject=JSON.parseObject(jsonStr);
		ProjectInfoForm projectInfoForm=jsonObject.getObject("projectInfo",ProjectInfoForm.class);
		projectInfoForm.setId(jsonObject.getObject("projectId",String.class));
		model.addAttribute("projectInfoForm", projectInfoForm);
		return "modules/ams/amsOffineFileDetailed";
	}
	/**
	 * 解压zip文件
	 * @return
	 */
	@RequiresPermissions("ams:amsOfflineFile:upload")
	@RequestMapping(value = {"unzipOfflineZipFile"})
	@ResponseBody
	public String unzipOfflineZipFile(String filename){
		String path="G:/test";
		File zipFile=new File(path+"/"+filename);
		try{
			//文件解压
			if(!FileUtils.unBigZipFiles(zipFile, path))
				return "decompression";
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failure";
		}
		return "success";
	}
	//文件包入库
	@RequiresPermissions("ams:amsOfflineFile:upload")
	@RequestMapping(value = {"saveOfflineProjectInfo"})
	public String saveOfflineProjectInfo(Model model, RedirectAttributes redirectAttributes){
		StringBuffer message=new StringBuffer();
		try {
			amsInterfaceService.saveOfflineProjectInfo(message);
			logger.info("离线文件包入库成功");
			addMessage(redirectAttributes, "离线文件包入库成功!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("离线文件包入库失败",e);
			addMessage(redirectAttributes, "离线文件包入库失败,"+message);
		}
		
		return "redirect:" + Global.getAdminPath() + "/ams/amsInterface/form";
	}
	
	/**
	 * 获取单位工程JSON数据。
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeDataAcc")
	public List<Map<String, Object>> treeDataAcc(@RequestParam(required=false) String id) {
		String jsonStr=(String) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipJsonData);
		JSONObject jsonObject=JSON.parseObject(jsonStr);
		ProjectInfoForm projectInfoForm=jsonObject.getObject("projectInfo",ProjectInfoForm.class);
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", "100");
		map.put("name", "单位工程及案卷");
		map.put("isParent", true);
		mapList.add(map);
		for (SingleProjectForm singleProjectForm: projectInfoForm.getSingleProjectList()) {
			map = Maps.newHashMap();
			map.put("id", singleProjectForm.getId());
			map.put("pId", "100");
			map.put("name", singleProjectForm.getUnitProjectName());
			mapList.add(map);
		}
		List<ArchivesInfoForm> archivesInfoForms=jsonObject.getJSONArray("archivesinfos").toJavaList(ArchivesInfoForm.class);
		for(ArchivesInfoForm archivesInfoForm:archivesInfoForms){
			if(EntityUtils.isEmpty(archivesInfoForm.getUnitProjectId())){
				map = Maps.newHashMap();
				map.put("id", "-1");
				map.put("pId", "100");
				map.put("name", "项目下案卷");
				mapList.add(map);
				break;
			}
		}
		return mapList;
	}
	
	@RequiresPermissions("ams:amsOfflineFile:upload")
	@RequestMapping(value = "report")
	public String report(AmsAcceptance amsAcceptance, Model model) {
		//获取报告
		List<AmsPreRpt> checkResults=(List<AmsPreRpt>) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipCheckData);
		model.addAttribute("offlineFileReport", checkResults);
		return "modules/ams/amsOffineFileReport";		
	}
	
	/**
	 * 保存检查错误信息
	 * @param amsAcceptance
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsOfflineFile:upload")
	@RequestMapping(value = "saveReport")
	public String saveReport(AmsAcceptance amsAcceptance,Model model) {
		//获取报告
		//List<Map> checkResults=(List<Map>) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipCheckData);
		Iterator<AmsPreRpt> checkResults = amsAcceptance.getAmsPreRptList().iterator();
		AmsPreRpt amsPreRpt=null;
		while(checkResults.hasNext()){
			amsPreRpt = checkResults.next();
		    if(amsPreRpt==null||EntityUtils.isEmpty(amsPreRpt.getError())){
		    	checkResults.remove();
		    }
		}
		UserUtils.getSession().setAttribute(UcamsConstant.OfflineZipCheckData, amsAcceptance.getAmsPreRptList());
		return "redirect:"+Global.getAdminPath()+"/ams/amsInterface/report";
	}
	
	/**
	 * 导出报告
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "export")
    public String exportFile(HttpServletRequest request, HttpServletResponse response) {
		try {
            String fileName = "报告数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    		List<AmsPreRpt> amsPreRptList=(List<AmsPreRpt>) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipCheckData);
    		new ExportExcel("报告数据", AmsPreRpt.class).setDataList(amsPreRptList).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			logger.info("导出失败！失败信息："+e.getMessage());
		}
		return "";
    }
    
    
    @RequestMapping(value = { "archivesList" })
	public String archivesList(@RequestParam(required=false) String unitProjectId,HttpServletRequest request, HttpServletResponse response,Model model) {
    	String jsonStr=(String) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipJsonData);
		JSONObject jsonObject=JSON.parseObject(jsonStr);
		List<ArchivesInfoForm> archivesInfoForms=jsonObject.getJSONArray("archivesinfos").toJavaList(ArchivesInfoForm.class);
		if(!"100".equals(unitProjectId)){
			Iterator<ArchivesInfoForm> archivesInfoIterator = archivesInfoForms.iterator();
			ArchivesInfoForm archivesInfoForm=null;
			while(archivesInfoIterator.hasNext()){
				archivesInfoForm = archivesInfoIterator.next();
			    if("-1".equals(unitProjectId)&&EntityUtils.isNotEmpty(archivesInfoForm.getUnitProjectId())||!"-1".equals(unitProjectId)&&!unitProjectId.equals(archivesInfoForm.getUnitProjectId())){
			    	archivesInfoIterator.remove();
			    }
			}
		}
		
		Page<ArchivesInfoForm> page=new Page<ArchivesInfoForm>(request, response);
//		page.setPageSize(7);
		page.setCount(archivesInfoForms.size());
		page.initialize();
		int start=(page.getPageNo()-1)*page.getPageSize();
		start=start<0?0:start;
		int end=start+page.getPageSize();
		end=end>archivesInfoForms.size()?archivesInfoForms.size():end;
		for (int i = start; i < end; i++) {
			page.getList().add(archivesInfoForms.get(i));
		}
		
		model.addAttribute("unitProjectId", unitProjectId);
		model.addAttribute("page", page);
		return "modules/ams/amsOffineFileArchivesList";
	}
    
    @RequestMapping(value = {"toPreviewFile"})
	public String toPreviewFile(AmsFileInfo amsFileInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
//    	String unzipPath=Global.getConfig("file.unzipPath");
    	String filename=(String) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipFile);
    	filename=filename.substring(0, filename.lastIndexOf("."));
    	String jsonStr=(String) UserUtils.getSession().getAttribute(UcamsConstant.OfflineZipJsonData);
		JSONObject jsonObject=JSON.parseObject(jsonStr);
		List<FileInfoForm> fileInfoForms=jsonObject.getJSONArray("fileinfos").toJavaList(FileInfoForm.class);
		Iterator<FileInfoForm> fileInfoFormsIterator = fileInfoForms.iterator();
		FileInfoForm fileInfoForm=null;
		while(fileInfoFormsIterator.hasNext()){
			fileInfoForm = fileInfoFormsIterator.next();
		    if(!amsFileInfo.getGroupId().equals(fileInfoForm.getGroupId())){
		    	fileInfoFormsIterator.remove();
		    }
		}
    	
    	Page<FileInfoForm> page = new Page<FileInfoForm>(request, response);
    	page.setPageSize(10);
		page.setCount(fileInfoForms.size());
		page.initialize();
		int start=(page.getPageNo()-1)*page.getPageSize();
		start=start<0?0:start;
		int end=start+page.getPageSize();
		end=end>fileInfoForms.size()?fileInfoForms.size():end;
		for (int i = start; i < end; i++) {
			fileInfoForm=fileInfoForms.get(i);
			fileInfoForm.setFilePath(request.getContextPath()+UcamsConstant.UnZipFileStr+filename+fileInfoForm.getFilePath());
			page.getList().add(fileInfoForm);
		}
        model.addAttribute("page", page);
        model.addAttribute("groupId",amsFileInfo.getGroupId());
		return "modules/ams/amsOffineFilePreview";
	}
}