/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ucams.common.config.Global;
import com.ucams.common.persistence.Page;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.FTPPointUtil;
import com.ucams.common.utils.FTPPointUtil.UploadStatus;
import com.ucams.common.utils.FileUtils;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.utils.SystemPath;
import com.ucams.common.utils.UcamsTools;
import com.ucams.common.web.BaseController;
import com.ucams.modules.ams.entity.AmsArchiveMenu;
import com.ucams.modules.ams.entity.AmsArchiveRules;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsGenre;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.entity.AmsUnitProInfo;
import com.ucams.modules.ams.service.AmsFileInfoService;
import com.ucams.modules.ams.service.AmsGenreService;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 文件管理Controller
 * 
 * @author zkx
 * @version 2017-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsFileInfo")
public class AmsFileInfoController extends BaseController {

	@Autowired
	private AmsFileInfoService amsFileInfoService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AmsGenreService amsGenreService;

	@ModelAttribute
	public AmsFileInfo get(@RequestParam(required=false) String id,@RequestParam(required=false) String recordId) {
		AmsFileInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			//-1时获取影像文件
			if("-1".equals(recordId)){
				entity = amsFileInfoService.getVideo(id);
			}else{
				entity = amsFileInfoService.get(id);
			}
		}
		if (entity == null){
			//默认文件名
//			AmsArchiveRules amsArchiveRules=amsFileInfoService.getAmsArchiveRules(recordId);
			entity = new AmsFileInfo();
//			if(EntityUtils.isNotEmpty(amsArchiveRules)){
//				entity.setRecordFileName(amsArchiveRules.getFileName());	
//			}
//			entity.setRecordId(recordId);
		}
		return entity;
	}

	@RequiresPermissions("ams:amsFileInfo:view")
	@RequestMapping(value = { "list"})
	public String list(AmsFileInfo amsFileInfo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AmsFileInfo> page = amsFileInfoService.findPage(
				new Page<AmsFileInfo>(request, response), amsFileInfo);
		model.addAttribute("page", page);
		return "modules/ams/amsFileInfoList";
	}
	/**
	 * 项目下文件列表
	 * @param amsFileInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsFileInfo:view")
	@RequestMapping(value = { "proList"})
	public String proList(AmsFileInfo amsFileInfo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AmsFileInfo> page = amsFileInfoService.findPage(
				new Page<AmsFileInfo>(request, response), amsFileInfo);
		model.addAttribute("page", page);
		return "modules/ams/amsFileInfoList_pro";
	}
	@RequestMapping(value = { "proVideoList"})
	public String proVideoList(AmsFileInfo amsFileInfo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AmsFileInfo> page = amsFileInfoService.findPage(
				new Page<AmsFileInfo>(request, response), amsFileInfo);
		model.addAttribute("page", page);
		return "modules/ams/amsFileInfoList_pro_video";
	}

	@RequiresPermissions("ams:amsFileInfo:view_sy")
	@RequestMapping(value = { "view" })
	public String view(AmsFileInfo amsFileInfo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/ams/amsFileInfoList_view";
	}
	@RequestMapping(value = { "videoMain" })
	public String videoMain(AmsFileInfo amsFileInfo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/ams/amsFileInfoList_videoMain";
	}

	@RequiresPermissions("ams:amsFileInfo:view")
	@RequestMapping(value = "form")
	public String form(AmsFileInfo amsFileInfo, Model model) {
		String view="";
		//-1时是声像档案不需要扩展信息
		if ("-1".equals(amsFileInfo.getExten1())) {
			amsFileInfo.setFilePath(amsFileInfo.getFilePath());
			view="modules/ams/amsFileInfoVideoForm";
		}else{
			Role role = UserUtils.getUser().getRoleList().get(0);
			AmsArchiveRules amsArchiveRules=new AmsArchiveRules();
			amsArchiveRules.setDelFlag("0");
			amsArchiveRules.setCreateUnit(role.getRoleType());
			/*//查询归档一览表List更改后功能去掉
			amsFileInfo.setAmsArchiveRulesList(amsFileInfoService.findAmsArchiveRules(amsArchiveRules));
			*/
			//查询扩展信息List
			if(!StringUtils.isBlank(amsFileInfo.getUnitProjectId())){
				amsFileInfo.setAmsDesExtendList(amsFileInfoService.findExtendDataList( amsFileInfo));
			}
			view="modules/ams/amsFileInfoForm";
		}
	
		model.addAttribute("amsFileInfo", amsFileInfo);
		return view;
	}
	
	/**
	 * 
	 * @param amsFileInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsFileInfo:view")
	@RequestMapping(value = "formView")
	public String formView(AmsFileInfo amsFileInfo, Model model) {
		//组织文件预览全路径
		amsFileInfo.setFilePath(FTPPointUtil.getOnlineUrl() + amsFileInfo.getFilePath());
		model.addAttribute("amsFileInfo", amsFileInfo);
		if(!StringUtils.isBlank(amsFileInfo.getUnitProjectId())){
			amsFileInfo.setAmsDesExtendList(amsFileInfoService.findExtendDataList( amsFileInfo));
		}
		return "modules/ams/amsFileInfoFormView";
	}
	@RequestMapping(value = "formViewVideo")
	public String formViewVideo(AmsFileInfo amsFileInfo, Model model) {
		//组织文件预览全路径
		amsFileInfo.setFilePath(FTPPointUtil.getOnlineUrl() + amsFileInfo.getFilePath());
		model.addAttribute("amsFileInfo", amsFileInfo);
		if(!StringUtils.isBlank(amsFileInfo.getUnitProjectId())){
			amsFileInfo.setAmsDesExtendList(amsFileInfoService.findExtendDataList( amsFileInfo));
		}
		return "modules/ams/amsFileInfoFormViewVideo";
	}
	
	@RequiresPermissions("ams:amsFileInfo:edit")
	@RequestMapping(value = "save")
	public String save(AmsFileInfo amsFileInfo, Model model,RedirectAttributes redirectAttributes,
										MultipartHttpServletRequest request) throws IOException {
		if (!beanValidator(model, amsFileInfo)) {
			return form(amsFileInfo, model);
		}
		MultipartFile file = request.getFile("file");
		if(!"-1".equals(amsFileInfo.getExten1())){
			String fileName=file.getOriginalFilename();
			String typeString=fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			boolean fail=true;
			if("pdf".equals(typeString)&&"1".equals(amsFileInfo.getFileType())){
				fail=false;
			}else if(("jpg".equals(typeString))&&"2".equals(amsFileInfo.getFileType())){
				fail=false;
			}
			if (file.getBytes().length > 0&&fail) {
				model.addAttribute("message","请选择正确的文件类型");
				return form(amsFileInfo, model);
			}
		}
		System.out.println("***********************************************************");
		System.out.println(file.getBytes().length);
		System.out.println(file.getContentType());
		System.out.println(file.getSize()*0.01*100/1024/1024);
		System.out.println(file);
		System.out.println("***********************************************************");
		FTPPointUtil ftp = null;
		try {
			//保存数据
			amsFileInfoService.save(amsFileInfo);
//			if(!"-1".equals(amsFileInfo.getExten1())){//非声像文件时上传实体文件
			//上传文件
			
			// 如果上传文件
			File f = null;
			StringBuffer filePath = new StringBuffer();
			
			//判断文件字节是否为0 (文件是否真实上传)
			if (EntityUtils.isNotEmpty(file)&&file.getBytes().length > 0) {
//				throw new FileNotFoundException("文件管理-文件上传-未找到上传文件");
				//组织文件存储路径
				filePath.append("projectFile/" +  amsFileInfo.getProjectId()); 
				//若有单位工程ID
				if(EntityUtils.isNotEmpty(amsFileInfo.getUnitProjectId())){
					filePath.append("/" + amsFileInfo.getUnitProjectId());
				}
				filePath.append("/" + UcamsTools.createNewFileName(file));
				//用缓冲区来实现这个转换即使用java 创建的临时文件
				f=File.createTempFile("fileSavetmp_"+ amsFileInfo.getId() , null);
				//链接FTP服务 -- 在线业务
				ftp = new FTPPointUtil("tadmin", "admin");
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
				amsFileInfo.setFilePath(filePath.toString());
				amsFileInfoService.update(amsFileInfo);
				}
//			}
			
		} catch (Exception e) {
			ftp.disconnect();  //异常断开FTP服务
			e.printStackTrace();
			throw new IOException("文件管理-文件上传失败!");
		}
		
		addMessage(redirectAttributes, "保存文件成功");
		if(StringUtils.isBlank(amsFileInfo.getUnitProjectId())){//项目
			if("-1".equals(amsFileInfo.getExten1())){//声像
				return "redirect:" + Global.getAdminPath()
						+ "/ams/amsFileInfo/proVideoList?projectId="
					+ amsFileInfo.getProjectId()+"&recordId="+amsFileInfo.getRecordId();
			}
			//资料
			return "redirect:" + Global.getAdminPath()
					+ "/ams/amsFileInfo/proList?projectId="
					+ amsFileInfo.getProjectId()+"&recordId="+amsFileInfo.getRecordId();	
		}
		else{
		/*	//单位工程
			if("-1".equals(amsFileInfo.getExten1())){//声像
				return "redirect:" + Global.getAdminPath()
						+ "/ams/amsFileInfo/amsUnitProInfoList?id="
					+ amsFileInfo.getUnitProjectId()+"&recordId="+amsFileInfo.getRecordId();
			}*/
			//资料
			return "redirect:" + Global.getAdminPath()
					+ "/ams/amsFileInfo/list?unitProjectId="
					+ amsFileInfo.getUnitProjectId()+"&recordId="+amsFileInfo.getRecordId();
		}
		
	}
	public static String saveWebPdfFile(MultipartFile pdfFile,String pno){  
		String webFilePath = "";  
  //  if(pdfFile.getSize() > 0 && isImage(pdfFile.getContentType())){  
        FileOutputStream fos = null;  
        try {  
            byte[] b = pdfFile.getBytes();  
            /* 构造文件路径 */  
            String dirPath = SystemPath.getRootPath() ;
            String path="/fixed/"+pno; dirPath+=path;
            FileUtils.createDirectory(dirPath);
            File dir = new File(dirPath);  
            String fileName = pdfFile.getOriginalFilename();  
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String   newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
            String filePath = dirPath + "/"  + newFileName;  
            webFilePath = path+ "/"  + newFileName;  
            File file = new File(filePath);  
            dir.setWritable(true);  
            file.setWritable(true);  
            fos = new FileOutputStream(file);  
            fos.write(b);  
              
        } catch (IOException e) {  
            throw new RuntimeException("文件上传失败！" ,e);  
        }finally{  
            if(fos != null){  
                try {  
                    fos.close();  
                } catch (IOException e) {  
                    throw new RuntimeException("文件上传->输出流关闭失败！！！！" ,e);  
                }  
            }  
        }  
   // }  
      
    return webFilePath;  
}

	@RequiresPermissions("ams:amsFileInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsFileInfo amsFileInfo,
			RedirectAttributes redirectAttributes) {
		amsFileInfoService.delete(amsFileInfo);
		addMessage(redirectAttributes, "删除文件成功");
		if(StringUtils.isBlank(amsFileInfo.getUnitProjectId())){//项目
			if("-1".equals(amsFileInfo.getExten1())){//声像
				return "redirect:" + Global.getAdminPath()
						+ "/ams/amsFileInfo/proVideoList?projectId="
					+ amsFileInfo.getProjectId()+"&recordId="+amsFileInfo.getRecordId();
			}
			//资料
			return "redirect:" + Global.getAdminPath()
					+ "/ams/amsFileInfo/proList?projectId="
					+ amsFileInfo.getProjectId()+"&recordId="+amsFileInfo.getRecordId();	
		}
		else{//单位工程
			if("-1".equals(amsFileInfo.getRecordId())){//声像
				return "redirect:" + Global.getAdminPath()
						+ "/ams/amsFileInfo/amsUnitProInfoList?id="
					+ amsFileInfo.getUnitProjectId()+"&recordId="+amsFileInfo.getRecordId();
			}
			//资料
			return "redirect:" + Global.getAdminPath()
					+ "/ams/amsFileInfo/list?unitProjectId="
					+ amsFileInfo.getUnitProjectId()+"&recordId="+amsFileInfo.getRecordId();
		}
	}

	/**
	 * 获取JSON数据。
	 * 
	 * @return
	 */
	@RequiresPermissions("ams:amsFileInfo:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//获取当前用户拥有的机构权限
		Boolean isAll = false;
		List<Office> offices = null;
		//超级管理员
		if(	UserUtils.getUser().isAdmin())	{	
			offices = officeService.onlyUnitPro(UserUtils.getUser().getOffice().getParent());
		}else{
			offices = officeService.findList(false);
		}
		for (int i = -1; i < offices.size(); i++) {
			Map<String, Object> map = Maps.newHashMap();
				//设置树的根节点
				if(i == -1){
					map.put("id", "100");
					map.put("name", "工程项目与单位工程列表");
					map.put("isParent", true);
					mapList.add(map);
					continue;
				}
				Office office = offices.get(i);
				if ("3".equals(office.getGrade())&&"1".equals(office.getUseable())) {
					// id 增加$PRO$与$UNIT$用于区分项目工程与单位工程 ；$PRO$：项目工程；$UNIT$：单位工程
					map.put("id", office.getId() + "$PRO$");
					map.put("pId", "100");
					map.put("name", office.getName());
					map.put("isParent", true);
					mapList.add(map);
				} else if ("4".equals(office.getGrade())) {
					map = Maps.newHashMap();
					map.put("id", office.getId() + "$UNIT$");
					map.put("pId", office.getParentId() + "$PRO$");
					map.put("pIds", "" + office.getParentIds() + "$PRO$,");
					map.put("name", office.getName());
					mapList.add(map);
				}
		}
		return mapList;
	}
	@ResponseBody
	@RequestMapping(value = "treeDataVideo")
	public List<Map<String, Object>> treeDataVideo() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//获取当前用户拥有的机构权限
		Boolean isAll = false;
		List<Office> offices = null;
		//超级管理员
		if(	UserUtils.getUser().isAdmin())	{	
			offices = officeService.onlyUnitPro(UserUtils.getUser().getOffice().getParent());
		}else{
			offices = officeService.findList(false);
		}
		for (int i = -1; i < offices.size(); i++) {
			Map<String, Object> map = Maps.newHashMap();
				//设置树的根节点
				if(i == -1){
					map.put("id", "100");
					map.put("name", "工程项目列表");
					map.put("isParent", true);
					mapList.add(map);
					continue;
				}
				Office office = offices.get(i);
				if ("3".equals(office.getGrade())&&"1".equals(office.getUseable())) {
					// id 增加$PRO$与$UNIT$用于区分项目工程与单位工程 ；$PRO$：项目工程；$UNIT$：单位工程
					map.put("id", office.getId() + "$PRO$");
					map.put("pId", "100");
					map.put("name", office.getName());
					mapList.add(map);
				}
		}
		return mapList;
	}

	/**
	 * 查询责任主体信息及项目工程列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsFileInfo:view")
	@RequestMapping(value = { "businessList" })
	public String businessList(HttpServletRequest request, HttpServletResponse response,Model model) {
		AmsUnitDetailinfo amsUnitDetailinfo = amsFileInfoService.getAmsUnitDetailinfo();
		/*Page<AmsProjectInfo> page = amsFileInfoService.findAmsProInfo(
				new Page<AmsProjectInfo>(request, response));
		model.addAttribute("page", page);*/
		model.addAttribute("amsUnitDetailinfo", amsUnitDetailinfo);
		return "modules/ams/amsFileInfoOfficeList";
	}

	/**
	 * 查询项目工程信息及单位工程列表
	 * 
	 * @param amsProjectInfo
	 * @param id
	 *            项目工程id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsFileInfo:view")
	@RequestMapping(value = { "amsProjectInfoList" })
	public String amsProjectInfoList(AmsFileInfo amsFileInfo,AmsUnitProInfo amsUnitProInfo, String id,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		amsUnitProInfo.setProjectId(id);
		Page<AmsUnitProInfo> page = amsFileInfoService.findAmsUnitProInfo(new Page<AmsUnitProInfo>(request, response),amsUnitProInfo);
		AmsProjectInfo amsProjectInfos = new AmsProjectInfo();
		amsProjectInfos = amsFileInfoService.getPro(id);
		
		Page<AmsFileInfo> page1 =null;
		if(EntityUtils.isEmpty(amsFileInfo)){
			amsFileInfo = new AmsFileInfo();
		}
		amsFileInfo.setProjectId(id);
		
		//查询归档一揽表
		AmsGenre amsGenre=new AmsGenre();
		amsGenre.setType(amsProjectInfos.getProjectType());
		amsGenre.setCreateUnit(UserUtils.getUser().getRoleList().get(0).getRoleType());
//		amsGenre.setType("2");
//		amsGenre.setCreateUnit("security-role");
		amsGenre.setName(amsFileInfo.getFileName());
		amsGenre.setProjectId(amsFileInfo.getProjectId());
		amsGenre.setUnitProjectId("");
		amsGenre.setIsHaveFile(amsFileInfo.getExten5());
		List<AmsGenre> list = amsFileInfoService.fileFindList(amsGenre); 
		model.addAttribute("list", list);
		page1 = amsFileInfoService.findPage(
		new Page<AmsFileInfo>(request, response), amsFileInfo);	
		
		model.addAttribute("page", page);
		model.addAttribute("page1", page1);
		model.addAttribute("amsProjectInfo", amsProjectInfos);
		return "modules/ams/amsFileInfoProList";
		
	}
	/**
	 * 声像档案查询项目工程信息及单位工程列表
	 * 
	 * @param amsProjectInfo
	 * @param id
	 *            项目工程id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "amsProjectInfoVideoList" })
	public String amsProjectInfoVideoList(AmsFileInfo amsFileInfo,AmsUnitProInfo amsUnitProInfo, String id,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		amsUnitProInfo.setProjectId(id);
		Page<AmsUnitProInfo> page = amsFileInfoService.findAmsUnitProInfo(new Page<AmsUnitProInfo>(request, response),amsUnitProInfo);
		AmsProjectInfo amsProjectInfos = new AmsProjectInfo();
		amsProjectInfos = amsFileInfoService.getPro(id);
		
		Page<AmsFileInfo> page1 =null;
		if(EntityUtils.isEmpty(amsFileInfo)){
			amsFileInfo = new AmsFileInfo();
		}
		amsFileInfo.setProjectId(id);
		
			//查询归档一揽表
			AmsArchiveMenu amsArchiveMenu=new AmsArchiveMenu();
			amsArchiveMenu.setType(amsProjectInfos.getProjectType());
//			amsGenre.setCreateUnit(UserUtils.getUser().getRoleList().get(0).getRoleType());
//			amsGenre.setType("2");
//			amsGenre.setCreateUnit("security-role");
			amsArchiveMenu.setName(amsFileInfo.getFileName());
			amsArchiveMenu.setProjectId(amsFileInfo.getProjectId());
			amsArchiveMenu.setIsHaveFile(amsFileInfo.getExten5());
			List<AmsArchiveMenu> list = amsFileInfoService.fileFindList(amsArchiveMenu); 
			model.addAttribute("list", list);
			page1 = amsFileInfoService.findPage(
					new Page<AmsFileInfo>(request, response), amsFileInfo);	
		
		model.addAttribute("page", page);
		model.addAttribute("page1", page1);
		model.addAttribute("amsProjectInfo", amsProjectInfos);
		return "modules/ams/amsFileInfoProVideoList";
	}

	/**
	 * 查询单位工程信息及文件列表
	 * 
	 * @param amsUnitPro
	 * @param id
	 *            单位工程id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsFileInfo:view")
	@RequestMapping(value = { "amsUnitProInfoList" })
	public String amsUnitProInfoList(AmsFileInfo amsFileInfo,
			AmsUnitProInfo amsUnitPro, String id, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (StringUtils.isBlank(amsFileInfo.getFileType())) {
			amsFileInfo.setFileType("0");
		}
		Page<AmsFileInfo> page =null;
		amsFileInfo.setUnitProjectId(id);
		AmsUnitProInfo amsUnitProInfo = amsFileInfoService.getUnitPro(id);
		if("-1".equals(amsFileInfo.getRecordId())){
			page = amsFileInfoService.findVideoPage(
					new Page<AmsFileInfo>(request, response), amsFileInfo);
		}else {
			//查询归档一揽表
			AmsGenre amsGenre=new AmsGenre();
			amsGenre.setType(amsUnitProInfo.getUnitProjectType());
			amsGenre.setCreateUnit(UserUtils.getUser().getRoleList().get(0).getRoleType());
			amsGenre.setName(amsFileInfo.getFileName());
			amsGenre.setUnitProjectId(id);
			amsGenre.setIsHaveFile(amsFileInfo.getExten5());
			List<AmsGenre> list = amsFileInfoService.fileFindList(amsGenre); 
			model.addAttribute("list", list);
			page = amsFileInfoService.findPage(
					new Page<AmsFileInfo>(request, response), amsFileInfo);	
		}
		model.addAttribute("page", page);
		model.addAttribute("unitProjectId", id);
		model.addAttribute("amsUnitProInfo", amsUnitProInfo);
		//-1为查询声像档案；其他为资料文件
		if("-1".equals(amsFileInfo.getRecordId())){
			return "modules/ams/amsFileInfoUnitVideoList";
		}else {
			return "modules/ams/amsFileInfoUnitList";	
		}
		
	}
}