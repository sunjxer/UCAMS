/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.ucams.common.config.Global;
import com.ucams.common.persistence.Page;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.web.BaseController;
import com.ucams.modules.ams.entity.AmsAcceptance;
import com.ucams.modules.ams.entity.AmsAcceptanceArchives;
import com.ucams.modules.ams.entity.AmsArchivesFiles;
import com.ucams.modules.ams.entity.AmsArchivesInfo;
import com.ucams.modules.ams.entity.AmsConstructDes;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsGenre;
import com.ucams.modules.ams.entity.AmsLandDes;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.entity.AmsUnitProInfo;
import com.ucams.modules.ams.service.AmsAcceptanceArchivesService;
import com.ucams.modules.ams.service.AmsArchivesInfoService;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.SysBaseInfo;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.service.SysBaseInfoService;
import com.ucams.modules.sys.utils.DictUtils;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 组卷设置Controller
 * @author gyl
 * @version 2017-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsArchivesInfo")
public class AmsArchivesInfoController extends BaseController {
	
	@Autowired
	AmsAcceptanceArchivesService amsAcceptanceArchivesService;
	@Autowired
	private AmsArchivesInfoService amsArchivesInfoService;
	@Autowired
	private SysBaseInfoService sysBaseInfoService;
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AmsArchivesInfo get(@RequestParam(required=false) String id) {
		AmsArchivesInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsArchivesInfoService.get(id);
		}
		if (entity == null){
			entity = new AmsArchivesInfo();
		}
		return entity;
	}
	/**
	 * 案卷首页
	 * @param amsArchivesInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:view_sy")
	@RequestMapping(value = "view")
	public String view(AmsArchivesInfo amsArchivesInfo, HttpServletRequest request, HttpServletResponse response, Model model){
		//Page<AmsArchivesInfo> page = amsArchivesInfoService.findPage(new Page<AmsArchivesInfo>(request, response), amsArchivesInfo);
		//model.addAttribute("page", page);
		return "modules/ams/amsArchivesInfoList_view";
	}
	/**
	 * 文件列表
	 * @param amsArchivesInfo
	 * @param amsFileInfo
	 * @param id		案卷id
	 * @param projectId   项目id
	 * @param type      类型，已组卷in  未组卷all
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@RequestMapping(value = {"list"})
	public String list(AmsArchivesInfo amsArchivesInfo,AmsFileInfo amsFileInfo,String id,String projectId,String type, HttpServletRequest request, HttpServletResponse response, Model model) {
		AmsArchivesFiles amsArchivesFiles = new AmsArchivesFiles();
		amsArchivesFiles.setGroupId(id);
		amsFileInfo.setAmsArchivesFiles(amsArchivesFiles);
		amsFileInfo.setProjectId(projectId);
		amsFileInfo.setUnitProjectId(amsArchivesInfo.getUnitProjectId());
		//案卷预验收状态，0：预验收未提交或者预验收被驳回；>0：预验收中或者预验收通过
		int acceptanceCount = amsArchivesInfoService.getArchiveAcceptanceCount(amsArchivesInfo);
		AmsAcceptance amsAcceptance = new AmsAcceptance();
		amsAcceptance.setStatus(acceptanceCount+"");
		amsArchivesInfo.setAmsAcceptance(amsAcceptance);
		
		Page<AmsFileInfo> pageIn= new Page<AmsFileInfo>();
		Page<AmsFileInfo> pageAll = new Page<AmsFileInfo>();
		//基础查询
		if(StringUtils.isBlank(type)){
			pageIn = amsArchivesInfoService.findAmsFileInfoInList(new Page<AmsFileInfo>(request, response),amsFileInfo);
//			pageAll = amsArchivesInfoService.findAmsFileInfoAllList(new Page<AmsFileInfo>(request, response), amsFileInfo);
		//已组卷查询
		}else if("in".equals(type)){
//			pageAll = amsArchivesInfoService.findAmsFileInfoAllList(new Page<AmsFileInfo>(request, response), amsFileInfo);
			amsFileInfo.setFileName(amsArchivesInfo.getAmsFileInfo().getFileName());
			pageIn = amsArchivesInfoService.findAmsFileInfoInList(new Page<AmsFileInfo>(request, response),amsFileInfo);
		//未组卷查询
		}else if("all".equals(type)){
			pageIn = amsArchivesInfoService.findAmsFileInfoInList(new Page<AmsFileInfo>(request, response),amsFileInfo);
			amsFileInfo.setFileName(amsArchivesInfo.getAmsFileInfo().getFileName());
			pageAll = amsArchivesInfoService.findAmsFileInfoAllList(new Page<AmsFileInfo>(request, response), amsFileInfo);
		}
		model.addAttribute("pageIn", pageIn);
		model.addAttribute("pageAll", pageAll);
		model.addAttribute("amsArchivesInfo", amsArchivesInfo);
		return "modules/ams/amsArchivesInfoList";
	}
	/**
	 * 未组卷文件列表显示
	 * @param amsArchivesInfo
	 * @param amsFileInfo
	 * @param treeId	归档一览表节点id
	 * @param id		案卷id
	 * @param type		案卷类型：con建设工程规划  lan建设用地规划  其他一般组卷
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@RequestMapping(value = { "amsAllFileList" })
	public String amsALLFileList(AmsArchivesInfo amsArchivesInfo,AmsFileInfo amsFileInfo,String treeId,String id,String type, HttpServletRequest request, HttpServletResponse response, Model model){
		//amsArchivesInfo = amsArchivesInfoService.get(id);
		AmsArchivesFiles amsArchivesFiles = new AmsArchivesFiles();
		amsArchivesFiles.setGroupId(id);
		//建设用地规划或者建设工程规划，只展示项目下的文件，设置单位工程为-1
		if("con".equals(type)){
			AmsConstructDes amsConstructDes = amsArchivesInfoService.getCon(id);
			amsFileInfo.setProjectId(amsConstructDes.getProjectId());
			amsFileInfo.setUnitProjectId("-1");
		}else if("lan".equals(type)){
			AmsLandDes amsLandDes = amsArchivesInfoService.getLan(id);
			amsFileInfo.setProjectId(amsLandDes.getProjectId());
			amsFileInfo.setUnitProjectId("-1");
		}else{
			amsFileInfo.setProjectId(amsArchivesInfo.getProjectId());
			amsFileInfo.setUnitProjectId(amsArchivesInfo.getUnitProjectId());
		}
		amsFileInfo.setAmsArchivesFiles(amsArchivesFiles);
		amsFileInfo.setRecordId(treeId);
		if(amsArchivesInfo.getAmsFileInfo() != null){
			amsFileInfo.setFileName(amsArchivesInfo.getAmsFileInfo().getFileName());
		}
		
		Page<AmsFileInfo> pageAll = new Page<AmsFileInfo>();
		pageAll = amsArchivesInfoService.findAmsFileInfoAllList(new Page<AmsFileInfo>(request,response), amsFileInfo);
		model.addAttribute("pageAll",pageAll);
		model.addAttribute("amsArchivesInfo", amsArchivesInfo);
		model.addAttribute("treeId", treeId);
		model.addAttribute("type", type);
		return "modules/ams/amsAllFileList";
	}

	@RequiresPermissions("ams:amsArchivesInfo:view")
	@RequestMapping(value = "form")
	public String form(AmsArchivesInfo amsArchivesInfo, Model model) {
		model.addAttribute("amsArchivesInfo", amsArchivesInfo);
		return "modules/ams/amsArchivesInfoForm";
	}
	/**
	 * 添加案卷页面
	 * @param amsArchivesInfo
	 * @param model
	 * @param unitProjectId   单位工程id
	 * @param projectId       项目id
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:edit")
	@RequestMapping(value = "formAdd")
	public String formAdd(AmsArchivesInfo amsArchivesInfo, Model model,String unitProjectId,String projectId) {
		//判断项目下组卷或者单位工程下组卷
		if(StringUtils.isBlank(amsArchivesInfo.getProjectId())){
			amsArchivesInfo.setProjectId(projectId);
		}
		if(StringUtils.isBlank(amsArchivesInfo.getUnitProjectId()) || "-1".endsWith(amsArchivesInfo.getUnitProjectId())){
			amsArchivesInfo.setUnitProjectId(unitProjectId);
		}
		amsArchivesInfo = amsArchivesInfoService.getArch(amsArchivesInfo);
		model.addAttribute("amsArchivesInfo", amsArchivesInfo);
		return "modules/ams/amsArchivesInfoForm";
	}
	/**
	 * 添加案卷
	 * @param amsArchivesInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:edit")
	@RequestMapping(value = "save")
	public String save(AmsArchivesInfo amsArchivesInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsArchivesInfo)){
			return form(amsArchivesInfo, model);
		}
		String id = amsArchivesInfo.getId();
		//首次添加案卷，确定卷内目录类型
		if(StringUtils.isBlank(id)){
			SysBaseInfo sysBaseInfo = new SysBaseInfo();
			sysBaseInfo = sysBaseInfoService.findSysBaseInfo();
			amsArchivesInfo.setCatalogType(sysBaseInfo.getCatalog());
		}		
		AmsAcceptanceArchives amsAcceptanceArchives = new AmsAcceptanceArchives();
		amsAcceptanceArchives.setId(id);
		String amsAcceptanceArchivesId = amsAcceptanceArchivesService.findAmsAcceptanceArchivesId(amsAcceptanceArchives);
		if(amsAcceptanceArchivesId == null){
			amsArchivesInfoService.saveArch(amsArchivesInfo);
			addMessage(redirectAttributes, "保存组卷成功");
		}else{
			addMessage(redirectAttributes, "已组卷，无法修改");
		}
	
		if(StringUtils.isBlank(amsArchivesInfo.getUnitProjectId())){
			return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/amsProjectInfoList?id="+amsArchivesInfo.getProjectId();
		}else if("-1".equals(amsArchivesInfo.getUnitProjectId())){
			return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/amsProjectInfoList?id="+amsArchivesInfo.getProjectId();
		}else{
			return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/amsUnitProInfoList?id="+amsArchivesInfo.getUnitProjectId();
		}
	}
	/**
	 * 删除案卷
	 * @param amsArchivesInfo
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsArchivesInfo amsArchivesInfo, RedirectAttributes redirectAttributes) {
		String id = amsArchivesInfo.getId();
		AmsAcceptanceArchives amsAcceptanceArchives = new AmsAcceptanceArchives();
		amsAcceptanceArchives.setId(id);
		/*String amsAcceptanceArchivesId = amsAcceptanceArchivesService
				.findAmsAcceptanceArchivesId(amsAcceptanceArchives);
		if (amsAcceptanceArchivesId == null) {*/
		amsArchivesInfoService.deleteArchivesFiles(amsArchivesInfo);
		amsArchivesInfoService.delete(amsArchivesInfo);
		addMessage(redirectAttributes, "删除组卷成功");
		/*} else {
			addMessage(redirectAttributes, "已验收，无法删除");
		}*/
		if ("-1".equals(amsArchivesInfo.getUnitProjectId())) {
			return "redirect:" + Global.getAdminPath() + "/ams/amsArchivesInfo/amsProjectInfoList?id="
					+ amsArchivesInfo.getProjectId();
		} else {
			return "redirect:" + Global.getAdminPath() + "/ams/amsArchivesInfo/amsUnitProInfoList?id="
					+ amsArchivesInfo.getUnitProjectId();
		}
	}
	/**
	 * 删除组卷中的文件
	 * @param amsArchivesInfo
	 * @param id  案卷文件关系表id
	 * @param archId  案卷id
	 * @param type  案卷类型  con:建设工程规划著录  lan:建设土地规划著录  "":一般案卷
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:edit")
	@RequestMapping(value = "deleteFile")
	public String deleteFile(AmsArchivesInfo amsArchivesInfo,String id,String archId, String type,RedirectAttributes redirectAttributes) {
		AmsArchivesFiles amsArchivesFiles = new AmsArchivesFiles();
		amsArchivesFiles.setId(id);
		amsArchivesFiles.setGroupId(archId);
		//获取案卷基本信息
		amsArchivesInfo = this.get(archId);
		amsArchivesInfoService.deleteFile(amsArchivesFiles);
		addMessage(redirectAttributes, "删除文件成功");
		if("con".equals(type)){
			return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/conList?id="+archId+"&projectId="+amsArchivesInfoService.getCon(archId).getProjectId();
		}else if("lan".equals(type)){
			return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/lanList?id="+archId+"&projectId="+amsArchivesInfoService.getLan(archId).getProjectId();
		}else{
			return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/list?id="+amsArchivesInfo.getId()+"&projectId="+amsArchivesInfo.getProjectId();
		}
	}
	/**
	 * 组卷添加文件
	 * @param fileId  文件id
	 * @param archId  案卷id
	 * @param type  案卷类型  con:建设工程规划著录  lan:建设用地规划著录  "":一般案卷
	 * @param treeId  归档一览表id
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:edit")
	@RequestMapping(value = "addFile")
	public String addFile(AmsArchivesInfo amsArchivesInfo,String fileId,String archId,String type,String treeId,RedirectAttributes redirectAttributes){
		AmsArchivesFiles amsArchivesFiles = new AmsArchivesFiles();
		amsArchivesFiles.setGroupId(archId);
		amsArchivesFiles.setRecordId(fileId);
		//文件题名id
		amsArchivesFiles.setGenreId(treeId);
		amsArchivesInfo = this.get(archId);
		amsArchivesFiles.setAmsArchivesInfo(amsArchivesInfo);
		amsArchivesInfoService.addFile(amsArchivesFiles);
		addMessage(redirectAttributes, "文件保存成功");
//		if("con".equals(type)){
//			return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/conList?id="+archId+"&projectId="+amsArchivesInfoService.getCon(archId).getProjectId();
//		}else if("lan".equals(type)){
//			return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/lanList?id="+archId+"&projectId="+amsArchivesInfoService.getLan(archId).getProjectId();
//		}else{
			return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/amsAllFileList?id="+archId+"&treeId="+treeId+"&type="+type;
//		}
	}
	/**
	 * 归档一览表树形展示
	 * @param amsArchivesInfo
	 * @param id	案卷id	
	 * @param projectId	项目id	
	 * @param type		案卷类型
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@ResponseBody
	@RequestMapping(value = "recordTreeData")
	public List<Map<String,Object>> recordTreeData(AmsArchivesInfo amsArchivesInfo,String id,String projectId,String type){
		List<Map<String,Object>> mapList = Lists.newArrayList();
		//建设用地规划和建设工程规划，单位工程设为-1
		if("con".equals(type)){
			amsArchivesInfo.setUnitProjectId("-1");
			amsArchivesInfo.setProjectId(projectId);
		}else if("lan".equals(type)){
			amsArchivesInfo.setUnitProjectId("-1");
			amsArchivesInfo.setProjectId(projectId);
		}
		List<AmsGenre> list = amsArchivesInfoService.getRecordList(amsArchivesInfo);
		for(int i = 0; i<list.size(); i++){
			Map<String,Object> map = Maps.newHashMap();			
			AmsGenre amsGenre = list.get(i);
			if("1".equals(amsGenre.getParentId())){
				map.put("id", amsGenre.getId());
				map.put("name", amsGenre.getName());
				map.put("isParent", true);
				mapList.add(map);
			}else{
				//是否是最子节点
				if("1".equals(amsGenre.getIsEndChild())){
					map.put("id", amsGenre.getId()+"$END$");
				}else{
					map.put("id", amsGenre.getId());
				}
				map.put("pId", amsGenre.getParentId());
				map.put("pIds", amsGenre.getParentIds() );
				map.put("name", amsGenre.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	/**
	 * 获取JSON数据。
	 * 
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//获取当前用户拥有的机构权限
		Boolean isAll = false;
		List<Office> offices = null;
		if(	UserUtils.getUser().isAdmin())	{	
			offices = officeService.onlyUnitPro(UserUtils.getUser().getOffice().getParent());
		}else{
			offices = officeService.findList(false);
		}
		List proIdList = new ArrayList();
		for (int i = -1; i < offices.size(); i++) {
			Map<String, Object> map = Maps.newHashMap();		
				//设置树的根节点
				if(i == -1){
					map.put("id", "100");
					map.put("name", "工程项目与组卷列表");
					map.put("isParent", true);
					mapList.add(map);
					continue;
				}
				Office office = offices.get(i);
				if ("3".equals(office.getGrade())) {
					// id 增加$PRO$与$UNIT$用于区分项目工程与单位工程 ；$PRO$：项目工程；$UNIT$：单位工程
					map.put("id", office.getId() + "$PRO$");
					map.put("pId", "100");
					map.put("name", office.getName());
					map.put("isParent", true);
					mapList.add(map);
					proIdList.add(office.getId());
				} else if ("4".equals(office.getGrade())) {
					map = Maps.newHashMap();
					map.put("id", office.getId() + "$UNIT$");
					map.put("pId", office.getParentId() + "$PRO$");
					map.put("pIds", "" + office.getParentIds() + "$PRO$,");
					map.put("name", office.getName());
					mapList.add(map);
				}
		}
		//获取项目下的案卷列表
		List<AmsArchivesInfo> archList = amsArchivesInfoService.findArchListByProId(proIdList);
		for(AmsArchivesInfo amsArchivesInfo : archList){
			Map<String, Object> map = Maps.newHashMap();
			//项目下案卷
			if("-1".equals(amsArchivesInfo.getUnitProjectId())){
				//树结构中添加案卷
				map.put("id", amsArchivesInfo.getId() + "$ARC$");
				map.put("name", amsArchivesInfo.getArchivesName());
				map.put("pId", amsArchivesInfo.getProjectId()+ "$PRO$");
				mapList.add(map);
			//单位工程下案卷
			}else{
				map.put("id", amsArchivesInfo.getId() + "$ARC$");
				map.put("name", amsArchivesInfo.getArchivesName());
				map.put("pId", amsArchivesInfo.getUnitProjectId()+ "$UNIT$");
				mapList.add(map);
			}
		}
		
		SysBaseInfo sysBaseInfo = new SysBaseInfo();
		sysBaseInfo = sysBaseInfoService.findSysBaseInfo();
		//获取建设工程规划著录
		if("1".equals( sysBaseInfo.getConstructDes())){
			List<AmsConstructDes> conList = amsArchivesInfoService.findConstructDesListByProId(proIdList);
			for(AmsConstructDes amsConstructDes : conList){
				Map<String,Object> map = Maps.newHashMap();
				map.put("id",amsConstructDes.getId()+"$CON$");
				map.put("name", "建设工程规划");
				map.put("pId",amsConstructDes.getProjectId()+"$PRO$" );
				mapList.add(map);
			}
		}
		//建设用地规划著录
		if("1".equals(sysBaseInfo.getLandDes())){
			List<AmsLandDes> landList = amsArchivesInfoService.findLandDesListByProjectId(proIdList);
			for(AmsLandDes amsLandDes : landList){
				Map<String,Object> map = Maps.newHashMap();
				map.put("id", amsLandDes.getId()+"$LAN$");
				map.put("name", "建设用地规划");
				map.put("pId", amsLandDes.getProjectId()+"$PRO$");
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
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@RequestMapping(value =  "businessList" )
	public String businessList(HttpServletRequest request, HttpServletResponse response,Model model) {
		//AmsUnitDetailinfo amsUnitDetailinfo = amsArchivesInfoService.getAmsUnitDetailinfo();
		/*Page<AmsProjectInfo> page = amsArchivesInfoService.findAmsProInfo(
				new Page<AmsProjectInfo>(request, response));*/
		//model.addAttribute("page", page);
		//model.addAttribute("amsUnitDetailinfo", amsUnitDetailinfo);
		return "modules/ams/amsArchivesInfoOfficeList";
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
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@RequestMapping(value = { "amsProjectInfoList" })
	public String amsProjectInfoList(AmsArchivesInfo amsArchivesInfo,AmsUnitProInfo amsUnitProInfo, String id,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		amsUnitProInfo.setProjectId(id);
		amsArchivesInfo.setProjectId(id);
		Page<AmsUnitProInfo> page = amsArchivesInfoService.findAmsUnitProInfo(new Page<AmsUnitProInfo>(request, response),amsUnitProInfo);
		Page<AmsArchivesInfo> pageArch = amsArchivesInfoService.findPage(
				new Page<AmsArchivesInfo>(request, response), amsArchivesInfo);
		AmsProjectInfo amsProjectInfos = new AmsProjectInfo();
		amsProjectInfos = amsArchivesInfoService.getPro(id);
		//查询系统设置组卷方式
		SysBaseInfo sysBaseInfo = new SysBaseInfo();
		sysBaseInfo = sysBaseInfoService.findSysBaseInfo();
		model.addAttribute("page", page);
		model.addAttribute("pageArch", pageArch);
		model.addAttribute("amsProjectInfo", amsProjectInfos);
		model.addAttribute("sysBaseInfo", sysBaseInfo);
		return "modules/ams/amsArchivesInfoProList";
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
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@RequestMapping(value = { "amsUnitProInfoList" })
	public String amsUnitProInfoList(AmsArchivesInfo amsArchivesInfo,
			AmsUnitProInfo amsUnitPro, String id, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (amsArchivesInfo == null) {
			amsArchivesInfo = new AmsArchivesInfo();
		}
		amsArchivesInfo.setUnitProjectId(id);
		Page<AmsArchivesInfo> page = amsArchivesInfoService.findPage(
				new Page<AmsArchivesInfo>(request, response), amsArchivesInfo);
		AmsUnitProInfo amsUnitProInfo = amsArchivesInfoService.getUnitPro(id);
		//单位工程的预验收状态，如果单位工程在预验收中或者预验收通过，不能添加新的案卷
		int acceptanceStatus = amsArchivesInfoService.getUnitProAcceptanceStatus(id);
		model.addAttribute("page", page);
		model.addAttribute("unitProjectId", id);
		model.addAttribute("amsUnitProInfo", amsUnitProInfo);
		model.addAttribute("acceptanceStatus", acceptanceStatus);
		return "modules/ams/amsArchivesInfoUnitList";
	}
	/**
	 * 查询案卷信息
	 * 
	 * @param amsArchivesInfo
	 * @param id
	 *            案卷id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@RequestMapping(value = { "amsArchivesInfoList" })
	public String amsArchivesInfoList(AmsArchivesInfo amsArchivesInfo,String id,HttpServletRequest request,
			HttpServletResponse response,Model model){
		if(amsArchivesInfo == null){
			amsArchivesInfo = new AmsArchivesInfo();
		}
		amsArchivesInfo = amsArchivesInfoService.get(id);
		model.addAttribute("amsArchivesInfo", amsArchivesInfo);
		return "modules/ams/amsArchivesInfoView";
	}
	/**
	 * 查询案卷信息
	 * 
	 * @param amsArchivesInfo
	 * @param id
	 *            案卷id
	 * @param request
	 * @param response
	 * @param model
	 * @author zkx
	 * @return
	 */
	@RequestMapping(value = { "amsAcceptanceArcView" })
	public String amsAcceptanceArcView(AmsArchivesInfo amsArchivesInfo,String id,HttpServletRequest request,
			HttpServletResponse response,Model model){
		if(amsArchivesInfo == null){
			amsArchivesInfo = new AmsArchivesInfo();
		}
		AmsFileInfo  amsFileInfo=new AmsFileInfo();
		AmsArchivesFiles amsArchivesFiles=new AmsArchivesFiles();
		amsArchivesFiles.setGroupId(amsArchivesInfo.getId());
		amsFileInfo.setAmsArchivesFiles(amsArchivesFiles);
		Page<AmsFileInfo> pageIn= amsArchivesInfoService.findAmsFileInfoInList(new Page<AmsFileInfo>(request, response),amsFileInfo);

		model.addAttribute("pageIn", pageIn);
		model.addAttribute("amsArchivesInfo", amsArchivesInfo);
		return "modules/ams/amsAcceptanceArcView";
	}
	/**
	 * 建设工程规划著录
	 * @param amsConstructDes
	 * @param id   建设工程规划id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@RequestMapping(value = {"amsConstructDesList"})
	public String amsConstructDesList(AmsConstructDes amsConstructDes,String id,HttpServletRequest request,HttpServletResponse response,Model model){
		if(amsConstructDes == null){
			amsConstructDes = new AmsConstructDes();
		}
		amsConstructDes = amsArchivesInfoService.getCon(id);
		model.addAttribute("amsConstructDes", amsConstructDes);
		return "modules/ams/amsConstructDesView";
	}
	/**
	 * 建设用地规划著录
	 * @param amsLandDes
	 * @param id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@RequestMapping(value = {"amsLandDesList"})
	public String amsLandDesList(AmsLandDes amsLandDes,String id,HttpServletRequest request,HttpServletResponse response,Model model){
		if(amsLandDes == null){
			amsLandDes = new AmsLandDes();
		}
		amsLandDes = amsArchivesInfoService.getLan(id);
		model.addAttribute("amsLandDes", amsLandDes);
		return "modules/ams/amsLandDesView";
	}
	
	/**
	 * 保存建设工程规划信息和著录扩展信息
	 * @param amsConstructDes
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:edit")
	@RequestMapping(value = {"saveCon"})
	public String saveCon(AmsConstructDes amsConstructDes, Model model,
			RedirectAttributes redirectAttributes){
		if (!beanValidator(model, amsConstructDes)) {
			return "error";
		}
		amsArchivesInfoService.saveCon(amsConstructDes);
		return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/amsConstructDesList?id="+amsConstructDes.getId();	
	}
	/**
	 * 保存建设用地规划著录信息和扩展信息
	 * @param amsLandDes
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:edit")
	@RequestMapping(value = {"saveLan"})
	public String saveLan(AmsLandDes amsLandDes, Model model,
			RedirectAttributes redirectAttributes){
		if (!beanValidator(model, amsLandDes)) {
			return "error";
		}
		amsArchivesInfoService.saveLan(amsLandDes);
		return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/amsLandDesList?id="+amsLandDes.getId();	
	}
	
	/**
	 * 建设工程规划著录文件列表
	 * @param amsConstructDes
	 * @param amsFileInfo
	 * @param id     建设工程规划id
	 * @param projectId     项目id
	 * @param type
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@RequestMapping(value = {"conList"})
	public String conList(AmsConstructDes amsConstructDes,AmsFileInfo amsFileInfo,String id,String projectId,String type, HttpServletRequest request, HttpServletResponse response, Model model){
		AmsArchivesFiles amsArchivesFiles = new AmsArchivesFiles();
		amsArchivesFiles.setGroupId(id);
		amsFileInfo.setAmsArchivesFiles(amsArchivesFiles);
		amsFileInfo.setProjectId(projectId);
		//建设工程规划只需查询项目下的文件，单位工程id为-1
		amsFileInfo.setUnitProjectId("-1");
		int acceptanceState = amsArchivesInfoService.getConLanAcceptanceCount(id);
		AmsAcceptance amsAcceptance = new AmsAcceptance();
		amsAcceptance.setStatus(acceptanceState+"");
		amsConstructDes.setAmsAcceptance(amsAcceptance);		
		Page<AmsFileInfo> pageIn= new Page<AmsFileInfo>();
		Page<AmsFileInfo> pageAll = new Page<AmsFileInfo>();
		//基础查询
		if(StringUtils.isBlank(type)){
			pageIn = amsArchivesInfoService.findAmsFileInfoInList(new Page<AmsFileInfo>(request, response),amsFileInfo);
			pageAll = amsArchivesInfoService.findAmsFileInfoAllList(new Page<AmsFileInfo>(request, response), amsFileInfo);
		//已组卷查询
		}else if("in".equals(type)){
			pageAll = amsArchivesInfoService.findAmsFileInfoAllList(new Page<AmsFileInfo>(request, response), amsFileInfo);
			amsFileInfo.setFileName(amsConstructDes.getAmsFileInfo().getFileName());
			pageIn = amsArchivesInfoService.findAmsFileInfoInList(new Page<AmsFileInfo>(request, response),amsFileInfo);
		//未组卷查询
		}else if("all".equals(type)){
			pageIn = amsArchivesInfoService.findAmsFileInfoInList(new Page<AmsFileInfo>(request, response),amsFileInfo);
			amsFileInfo.setFileName(amsConstructDes.getAmsFileInfo().getFileName());
			pageAll = amsArchivesInfoService.findAmsFileInfoAllList(new Page<AmsFileInfo>(request, response), amsFileInfo);
		}
		model.addAttribute("pageIn", pageIn);
		model.addAttribute("pageAll", pageAll);
		model.addAttribute("amsConstructDes", amsConstructDes);
		return "modules/ams/amsConFileList";
	}
	/**
	 * 建设用地规划著录文件表
	 * @param amsLandDes
	 * @param amsFileInfo
	 * @param id
	 * @param projectId
	 * @param type
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@RequestMapping(value = {"lanList"})
	public String lanList(AmsLandDes amsLandDes,AmsFileInfo amsFileInfo,String id,String projectId,String type, HttpServletRequest request, HttpServletResponse response, Model model){
		AmsArchivesFiles amsArchivesFiles = new AmsArchivesFiles();
		amsArchivesFiles.setGroupId(id);
		amsFileInfo.setAmsArchivesFiles(amsArchivesFiles);
		amsFileInfo.setProjectId(projectId);
		//建设工程规划只需查询项目下的文件，单位工程id为-1
		amsFileInfo.setUnitProjectId("-1");
		int acceptanceState = amsArchivesInfoService.getConLanAcceptanceCount(id);
		AmsAcceptance amsAcceptance = new AmsAcceptance();
		amsAcceptance.setStatus(acceptanceState+"");
		amsLandDes.setAmsAcceptance(amsAcceptance);
		Page<AmsFileInfo> pageIn= new Page<AmsFileInfo>();
		Page<AmsFileInfo> pageAll = new Page<AmsFileInfo>();
		//基础查询
		if(StringUtils.isBlank(type)){
			pageIn = amsArchivesInfoService.findAmsFileInfoInList(new Page<AmsFileInfo>(request, response),amsFileInfo);
			pageAll = amsArchivesInfoService.findAmsFileInfoAllList(new Page<AmsFileInfo>(request, response), amsFileInfo);
		//已组卷查询
		}else if("in".equals(type)){
			pageAll = amsArchivesInfoService.findAmsFileInfoAllList(new Page<AmsFileInfo>(request, response), amsFileInfo);
			amsFileInfo.setFileName(amsLandDes.getAmsFileInfo().getFileName());
			pageIn = amsArchivesInfoService.findAmsFileInfoInList(new Page<AmsFileInfo>(request, response),amsFileInfo);
		//未组卷查询
		}else if("all".equals(type)){
			pageIn = amsArchivesInfoService.findAmsFileInfoInList(new Page<AmsFileInfo>(request, response),amsFileInfo);
			amsFileInfo.setFileName(amsLandDes.getAmsFileInfo().getFileName());
			pageAll = amsArchivesInfoService.findAmsFileInfoAllList(new Page<AmsFileInfo>(request, response), amsFileInfo);
		}
		model.addAttribute("pageIn", pageIn);
		model.addAttribute("pageAll", pageAll);
		model.addAttribute("amsLandDes", amsLandDes);
		return "modules/ams/amsLanFileList";
	}
	
	
	
	/**
	 * 文件排序呢
	 * @param model
	 * @param thisId   当前案卷文件关系id
	 * @param nextId   下移案卷文件关系id
	 * @param prevId   上移案卷文件关系id
	 * @return
	 */
	@RequestMapping(value = "sortCheck")
	@ResponseBody
	public String sortCheck(Model model,String thisId,String nextId,String prevId,String catalogType) {
		try {
			amsArchivesInfoService.changeSort(thisId, nextId, prevId,catalogType);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	/**
	 * 批量保存排序
	 * @param ids
	 * @param sorts
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "saveSort")
	public String saveSort(String[] ids, Integer[] sorts,String[] nums,String id,String projectId,String type, RedirectAttributes redirectAttributes) {
		List<AmsArchivesFiles> amsArchivesFileList=new ArrayList<AmsArchivesFiles>();
		List<AmsArchivesFiles> amsArchivesFileSaveList=new ArrayList<AmsArchivesFiles>();
		if(ids == null){
			if("con".equals(type)){
				return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/conList?id="+id+"&projectId="+projectId;
			}else if("lan".equals(type)){
				return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/lanList?id="+id+"&projectId="+projectId;
			}else{
				return "redirect:" + adminPath + "/ams/amsArchivesInfo/list?id="+id+"&projectId="+projectId;
			}
		}
    	for (int i = 0; i < ids.length; i++) {
    		AmsArchivesFiles amsArchivesFiles = new AmsArchivesFiles(ids[i]);
    		amsArchivesFiles.setSort(sorts[i]);
    		amsArchivesFiles.setExten1(nums[i]);
    		amsArchivesFileList.add(amsArchivesFiles);
    	}
    	for (int i=0;i< amsArchivesFileList.size();i++) {
    		for (int j=0;j<amsArchivesFileList.size()-1;j++) {
    			AmsArchivesFiles amsArchivesFilesj=amsArchivesFileList.get(j);
    			AmsArchivesFiles amsArchivesFilesj1=amsArchivesFileList.get(j+1);
    			if(amsArchivesFilesj.getSort()>amsArchivesFilesj1.getSort()){
    				AmsArchivesFiles amsArchivesFiles = new AmsArchivesFiles();
    				amsArchivesFiles=amsArchivesFilesj;	
    				amsArchivesFileList.set(j, amsArchivesFilesj1);
    				amsArchivesFileList.set(j+1, amsArchivesFiles);
    			}
    		}
		}
    	int start=1,end=0,beforePage=0;
    	for(AmsArchivesFiles amsArchivesFiles : amsArchivesFileList){
    		start=beforePage+start;
    		amsArchivesFiles.setStartPage(String.valueOf(start));
    		end=start+Integer.parseInt(amsArchivesFiles.getExten1())-1;
    		amsArchivesFiles.setEndPage(String.valueOf(end));
    		amsArchivesFileSaveList.add(amsArchivesFiles);
    		beforePage=Integer.parseInt(amsArchivesFiles.getExten1());
    		amsArchivesInfoService.updateSort(amsArchivesFiles);
    	}
    	addMessage(redirectAttributes, "保存排序成功!");
    	
    	if("con".equals(type)){
			return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/conList?id="+id+"&projectId="+projectId;
		}else if("lan".equals(type)){
			return "redirect:"+Global.getAdminPath()+"/ams/amsArchivesInfo/lanList?id="+id+"&projectId="+projectId;
		}else{
			return "redirect:" + adminPath + "/ams/amsArchivesInfo/list?id="+id+"&projectId="+projectId;
		}
    	
	}
	/**
	 * 
	 * @param amsArchivesInfo
	 * @param id		案卷id
	 * @param catalogType		案卷目录生成方式  0:按文件名；1：按文件题名
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsArchivesInfo:view")
	@RequestMapping(value = "catalog")
	public String catalog(AmsArchivesInfo amsArchivesInfo,String id,String catalogType,HttpServletRequest request,HttpServletResponse response,Model model){
		if(amsArchivesInfo == null){
			amsArchivesInfo = new AmsArchivesInfo();
		}
		AmsFileInfo amsFileInfo = new AmsFileInfo();
		AmsArchivesFiles amsArchivesFiles = new AmsArchivesFiles();
		amsArchivesFiles.setGroupId(id);
		amsFileInfo.setAmsArchivesFiles(amsArchivesFiles);
		List<AmsFileInfo> list = amsArchivesInfoService.findCatalog(amsFileInfo,catalogType);
		model.addAttribute("catalogType", catalogType);
		model.addAttribute("archivesId", id);
		model.addAttribute("list", list);
		return "modules/ams/amsArchivesCatalog";
	}
	/**
	 * 导出卷内目录
	 * @param id  案卷id
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "exploadCatalog")
	public void exploadCatalog(String id,String catalogType,HttpServletRequest request,HttpServletResponse response,Model model){
		//案卷信息
		AmsArchivesInfo amsArchivesInfo = new AmsArchivesInfo();
		amsArchivesInfo = amsArchivesInfoService.get(id);
		
		AmsFileInfo amsFileInfo = new AmsFileInfo();
		AmsArchivesFiles amsArchivesFiles = new AmsArchivesFiles();
		amsArchivesFiles.setGroupId(id);
		amsFileInfo.setAmsArchivesFiles(amsArchivesFiles);
		//查询案卷内文件列表
		List<AmsFileInfo> list = amsArchivesInfoService.findCatalog(amsFileInfo,catalogType);
		SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy年MM月dd日 " );
		//导出数据列表
		List dataList = new ArrayList();
		for(AmsFileInfo file : list){
			Object[] obj = new Object[7];
			obj[0] = file.getAmsArchivesFiles().getId();
			if("1".equals(catalogType)){
				obj[1] = file.getAmsGenre().getCode();
				obj[3] = file.getAmsGenre().getName();
			}else{
				obj[1] = file.getFileNo();
				obj[3] = file.getFileName();
			}
			obj[2] = file.getAuthor();
			obj[4] = sdf.format(file.getFormDate());
			obj[5] = file.getAmsArchivesFiles().getStartPage()+"-"+file.getAmsArchivesFiles().getEndPage();
			obj[6] = file.getRemarks();
			dataList.add(obj);
		}
		//列名
		String rowName[]={"序号","文件编号","责任者","文件题名","日期","页次","备注"};
		amsArchivesInfoService.getExcel(amsArchivesInfo.getArchivesName(), rowName, dataList, response);
	}
	@RequestMapping(value = { "exploadCover" })
	public void exploadCover(AmsArchivesInfo amsArchivesInfo,String id,HttpServletRequest request,HttpServletResponse response,Model model){
		ByteArrayOutputStream bos = null;
//		HttpHeaders headers = null;
		File file = null;
		//获取文件保存路径
		String path = request.getSession().getServletContext().getRealPath("/");
		try{
			 
			if(amsArchivesInfo == null){
				amsArchivesInfo = new AmsArchivesInfo();
			}
			//查询案卷信息
			amsArchivesInfo = amsArchivesInfoService.get(id);
			SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy年MM月dd日 " );
			String startDate = sdf.format(amsArchivesInfo.getStartDate());
			String endDate = sdf.format(amsArchivesInfo.getEndDate());
			//将需要保存的数据放入map中
			Map<String, String> data = new HashMap<String, String>();
			data.put("Text1",amsArchivesInfo.getArchivesCode());  
		    //data.put("Text2", amsArchivesInfo.getFilesCount());  
		    data.put("Text3", amsArchivesInfo.getArchivesName());  
		    data.put("Text6",amsArchivesInfo.getMakeUnit());
		    data.put("Text7", startDate+"——"+endDate);
		    data.put("Text9", DictUtils.getDictLabel(amsArchivesInfo.getStoragePeriod(), "storage_period", ""));
		    data.put("Text8", DictUtils.getDictLabel(amsArchivesInfo.getDegreeSecrets(), "degree_secrets", ""));
		    //模板文件
			String fileName = path+"\\download\\model.pdf";
			PdfReader reader = new PdfReader(fileName);
			bos = new ByteArrayOutputStream();
			PdfStamper ps = new PdfStamper(reader, bos);
		//	PdfContentByte under = ps.getUnderContent(1);
			//设置字体
			BaseFont bf=BaseFont.createFont("C:\\Windows\\Fonts\\simfang.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); 
			ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
			fontList.add(bf);
			/* 取出报表模板中的所有字段 */    
	        AcroFields fields = ps.getAcroFields();  
	        fields.setSubstitutionFonts(fontList);
	        for (String key : data.keySet()) {  
	            String value = data.get(key);  
	            fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的  
	        }
	        /* 必须要调用这个，否则文档不会生成的 */    
	        ps.setFormFlattening(true);  
	        ps.close();
	        //生成封面pdf文件
	        OutputStream fos = new FileOutputStream(path+"\\download\\"+amsArchivesInfo.getArchivesName()+".pdf");  
	        fos.write(bos.toByteArray());  
	        fos.flush();  
	        fos.close();  
	        bos.close();
	        reader.close();
	        file = new File(path+"\\download\\"+amsArchivesInfo.getArchivesName()+".pdf");
	  /*      headers = new HttpHeaders();    
	        String aname=amsArchivesInfo.getArchivesName();
	       byte[] b=aname.getBytes("ISO-8859-1");//用tomcat的格式（iso-8859-1）方式去读。 
	       String str=new String(b,"utf-8");//采用utf-8去接string 
	         //aname = new String(aname,aname.getBytes(),0,,"UTF-8"); 
	         
	         
	         System.out.println(str); 

	        String fName=new String(aname+".png");//为了解决中文名称乱码问题  	        
	        headers.setContentDispositionFormData("attachment", fName);  
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			*/
		}catch(Exception e){
			e.printStackTrace();
		}		
		//下载文件
		download(file.getPath(),response);
	}
	/**
	 * 文件下载  
	 * @param path   文件路径
	 * @param response
	 * @return
	 */
	public HttpServletResponse download(String path, HttpServletResponse response) {
	        try {
	            // path是指欲下载的文件的路径。
	            File file = new File(path);
	            // 取得文件名。
	            String filename = file.getName();
	            // 取得文件的后缀名。
	            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

	            // 以流的形式下载文件。
	            InputStream fis = new BufferedInputStream(new FileInputStream(path));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
	            // 清空response
	            response.reset();
	            // 设置response的Header
	            String fileName11 = URLEncoder.encode(filename,"UTF-8");
	            String headStr = "attachment; filename=\"" + fileName11 + "\"";
	            response.addHeader("Content-Disposition", headStr);
//	            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
	            response.addHeader("Content-Length", "" + file.length());
	            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	            response.setContentType("application/octet-stream");
	            toClient.write(buffer);
	            toClient.flush();
	            toClient.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        return response;
	    }
	
}