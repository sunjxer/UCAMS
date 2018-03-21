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
import com.ucams.modules.ams.entity.AmsArchivesInfo;
import com.ucams.modules.ams.entity.AmsGenre;
import com.ucams.modules.ams.service.AmsArchivesInfoService;
import com.ucams.modules.ams.service.AmsCheckElectronicFileService;
import com.ucams.modules.ams.service.AmsGenreService;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 电子文件检查Controller
 * @author sunjx
 * @version 2017-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsCheckElectronicFile")
public class AmsCheckElectronicFileController extends BaseController {

	@Autowired
	private AmsArchivesInfoService amsArchivesInfoService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AmsCheckElectronicFileService chcekElectronicFileService;
	
	@RequiresPermissions("ams:amsCheckElectronicFile:view")
	@RequestMapping(value = {""})
	public String index(Model model) {
		return "modules/ams/amsCheckElectronicFileIndex";
	}
	
	@RequiresPermissions("ams:amsCheckElectronicFile:view")
	@RequestMapping(value = {"list"})
	public String list(AmsArchivesInfo amsArchivesInfo,HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = request.getParameter("officeId");
		String grade = request.getParameter("officeGrade");
		if(EntityUtils.isNotEmpty(id)){
			if("3".equals(grade)){  //项目节点
				amsArchivesInfo.setProjectId(id);
			}
			if("4".equals(grade)){  //单位工程节点
				amsArchivesInfo.setUnitProjectId(id);
			}
			Page<AmsArchivesInfo> page = amsArchivesInfoService.findPage(new Page<AmsArchivesInfo>(request, response), amsArchivesInfo);
	        model.addAttribute("page", page);
	        model.addAttribute("officeId",id);
	        model.addAttribute("officeGrade",grade);
		}
		return "modules/ams/amsCheckElectronicFileList";
	}

	@RequiresPermissions("ams:amsCheckElectronicFile:view")
	@RequestMapping(value = "checking")
	@ResponseBody
	public Object form(AmsArchivesInfo amsArchivesInfo,String officeId, Model model,RedirectAttributes redirectAttributes) {
		List<AmsArchivesInfo> archivesList = new ArrayList<AmsArchivesInfo>();
		if(EntityUtils.isNotEmpty(amsArchivesInfo) && EntityUtils.isNotEmpty(amsArchivesInfo.getId())){
			AmsArchivesInfo archives = amsArchivesInfoService.get(amsArchivesInfo);
			archivesList.add(archives);
		}
		//检测报告
		List<String> orgenRptList =  chcekElectronicFileService.checkArchives(archivesList);
		return renderSuccess(orgenRptList);
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//获取当前用户拥有的机构权限
		List<Office> offices = null;
		if(	UserUtils.getUser().isAdmin())	{	
			offices = officeService.onlyUnitPro(UserUtils.getUser().getOffice().getParent());
		}else{
			offices = officeService.findList(false);
		}
		for (int i = 0; i < offices.size(); i++) {
			Map<String, Object> map = Maps.newHashMap();
			Office office = offices.get(i);
			if("3".equals(office.getGrade())){	
				map.put("id", office.getId());
				map.put("isParent", true);
				map.put("name", office.getName());
				map.put("grade", office.getGrade());
			}else{
				map.put("id", office.getId());
				map.put("pId", office.getParentId());
				map.put("pIds", office.getParentIds());
				map.put("name", office.getName());
				map.put("grade", office.getGrade());
			}
			mapList.add(map);
		}
		return mapList;
	}
	
}