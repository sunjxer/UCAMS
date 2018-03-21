/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import java.util.ArrayList;
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
import com.ucams.common.web.BaseController;
import com.ucams.common.persistence.Page;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.entity.AmsArchiveMenu;
import com.ucams.modules.ams.entity.AmsArchivesInfo;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsGenre;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.entity.AmsUnitProInfo;
import com.ucams.modules.ams.service.AmsArchivesInfoService;
import com.ucams.modules.ams.service.AmsCheckElectronicFileService;
import com.ucams.modules.ams.service.AmsCheckVideoFileService;
import com.ucams.modules.ams.service.AmsFileInfoService;
import com.ucams.modules.ams.service.AmsGenreService;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 声像文件检查Controller
 * @author sunjx
 * @version 2017-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsCheckVideoFile")
public class AmsCheckVideoFileController extends BaseController {
	
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AmsFileInfoService amsFileInfoService;
	@Autowired
	private AmsCheckVideoFileService amsCheckVideoFileService;
	
	@RequestMapping(value = { "" })
	public String videoMain(AmsFileInfo amsFileInfo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "modules/ams/amsCheckVideoFileIndex";
	}
	
	/**
	 * 声像档案查询项目工程信息及单位工程列表
	 * 
	 * @param amsProjectInfo
	 * @param id
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = { "amsProjectInfoVideoList" })
	public String amsProjectInfoVideoList(AmsFileInfo amsFileInfo,AmsUnitProInfo amsUnitProInfo, String id,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if(EntityUtils.isNotEmpty(id)){
			AmsProjectInfo amsProjectInfos = amsFileInfoService.getPro(id);
			amsFileInfo.setProjectId(id);
			AmsArchiveMenu amsArchiveMenu=new AmsArchiveMenu();
			amsArchiveMenu.setType(amsProjectInfos.getProjectType());
	//		amsGenre.setCreateUnit(UserUtils.getUser().getRoleList().get(0).getRoleType());
	//		amsGenre.setType("2");
	//		amsGenre.setCreateUnit("security-role");
			amsArchiveMenu.setName(amsFileInfo.getFileName());
		    amsArchiveMenu.setProjectId(amsFileInfo.getProjectId());
			amsArchiveMenu.setIsHaveFile(amsFileInfo.getExten5());
			List<AmsArchiveMenu> list = amsFileInfoService.fileFindList(amsArchiveMenu); 
			model.addAttribute("list", list);
			model.addAttribute("amsProjectInfo", amsProjectInfos);
		}
		return "modules/ams/amsCheckVideoFileList";
	}
	
	/**
	 * 查询责任主体信息及项目工程列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "businessList" })
	public String businessList(HttpServletRequest request, HttpServletResponse response,Model model) {
		AmsUnitDetailinfo amsUnitDetailinfo = amsFileInfoService.getAmsUnitDetailinfo();
		Page<AmsProjectInfo> page = amsFileInfoService.findAmsProInfo(
				new Page<AmsProjectInfo>(request, response));
		model.addAttribute("page", page);
		model.addAttribute("amsUnitDetailinfo", amsUnitDetailinfo);
		return "modules/ams/amsFileInfoOfficeList";
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
	
	@RequestMapping(value = { "fileInfoList"})
	public String fileInfoList(AmsFileInfo amsFileInfo, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<AmsFileInfo> page = amsFileInfoService.findPage(
				new Page<AmsFileInfo>(request, response), amsFileInfo);
		model.addAttribute("page", page);
		return "modules/ams/amsCheckVideoFileInfoList";
	}
	
	@RequestMapping(value = { "checking"})
	@ResponseBody
	public Object checkVideoFileInfo(AmsFileInfo amsFileInfo){
		
		List<AmsFileInfo> amsFileInfoList =  amsFileInfoService.findList(amsFileInfo);
		List<String> orgenRptList = null ;
		if(EntityUtils.isNotEmpty(amsFileInfoList) && amsFileInfoList.size() > 0){
			orgenRptList = amsCheckVideoFileService.checkFile(amsFileInfoList);
		}else{
			return renderSuccess("not found");
		}
		return renderSuccess(orgenRptList);
	}
	
}