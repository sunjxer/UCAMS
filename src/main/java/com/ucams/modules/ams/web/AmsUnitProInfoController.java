/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import java.util.Date;
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
import com.ucams.common.config.Global;
import com.ucams.common.persistence.Page;
import com.ucams.common.utils.DateUtils;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.web.BaseController;
import com.ucams.modules.ams.entity.AmsAcceptance;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.entity.AmsUnitProInfo;
import com.ucams.modules.ams.service.AmsFileInfoService;
import com.ucams.modules.ams.service.AmsUnitProInfoService;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 单位工程管理Controller
 * @author ws
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsUnitProInfo")
public class AmsUnitProInfoController extends BaseController {
	@Autowired
	private AmsFileInfoService amsFileInfoService;
	@Autowired
	private AmsUnitProInfoService amsUnitProInfoService;
	@Autowired
	private OfficeService officeService;
	@ModelAttribute
	public AmsUnitProInfo get(@RequestParam(required=false) String id) {
		AmsUnitProInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsUnitProInfoService.get(id);
		}
		if (entity == null){
			entity = new AmsUnitProInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsUnitProInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsUnitProInfo amsUnitProInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsUnitProInfo> page = amsUnitProInfoService.findPage(new Page<AmsUnitProInfo>(request, response), amsUnitProInfo); 
		model.addAttribute("projectid", amsUnitProInfo.getProjectId());
		model.addAttribute("page", page);
		return "modules/ams/amsUnitProInfoList";
	}

	@RequiresPermissions("ams:amsUnitProInfo:view")
	@RequestMapping(value = "form")
	public String form(AmsUnitProInfo amsUnitProInfo, Model model) {
		model.addAttribute("amsUnitProInfo", amsUnitProInfo);
		return "modules/ams/amsUnitProInfoForm";
	}
	 /**
		 * 查询单位工程信息及专业记载
		 * 
		 * @param amsUnitPro
		 * @param id
		 *            单位工程id
		 * @param request
		 * @param response
		 * @param model
		 * @author zkx
		 * @return
		 */
		@RequestMapping(value = { "amsUnitProInfoView" })
		public String amsUnitProInfoView(AmsUnitProInfo amsUnitProInfo, Model model) {
			amsUnitProInfo.setAmsDesExtendList(amsUnitProInfoService.findExtendDataList(amsUnitProInfo)); 
			model.addAttribute("amsUnitProInfo", amsUnitProInfo);
			return "modules/ams/amsAcceptanceUnitList";
		}
	@RequiresPermissions("ams:amsUnitProInfo:edit")
	@RequestMapping(value = "save")
	public String save(AmsUnitProInfo amsUnitProInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsUnitProInfo)){
			return form(amsUnitProInfo, model);
		}
		String pid=amsUnitProInfo.getId();
		if(!StringUtils.isNotBlank(amsUnitProInfo.getId())){
			//单位工程编号设置
			String  pno= "";
			AmsUnitProInfo unitProInfo = amsUnitProInfoService.getUnitProjectByNo();
			if(EntityUtils.isNotEmpty(unitProInfo) && EntityUtils.isNotEmpty(unitProInfo.getUnitProjectNo())){
				pno=String.valueOf(Integer.parseInt(unitProInfo.getUnitProjectNo())+1);
			}else{
				pno="10001";
			}
			amsUnitProInfo.setUnitProjectNo("UM"+DateUtils.formatDate(amsUnitProInfo.getStartDate())+pno);
		}
		amsUnitProInfoService.save(amsUnitProInfo);
		//保存或修改单位工程同时维护机构
				Office office=new Office();
					office.setId(amsUnitProInfo.getId());
					Office ofparent=officeService.get(amsUnitProInfo.getProjectId());
				office.setParent(ofparent);
				office.setParentIds(ofparent.getParentIds()+amsUnitProInfo.getProjectId()+",");
				office.setName(amsUnitProInfo.getUnitProjectName());
				office.setArea(UserUtils.getUser().getOffice().getArea());
				office.setType("2");
				office.setGrade("4");
				office.setUseable(Global.YES);
				office.setUpdateBy(UserUtils.getUser());
				office.setUpdateDate(new Date());
				office.setCreateBy(UserUtils.getUser());
				office.setCreateDate(new Date());
				office.setDelFlag("0");
				if("".equals(pid)){
					officeService.insert(office);
					amsUnitProInfoService.saveRoleUnitProject(amsUnitProInfo.getProjectId(),office);
				    /* 构造文件路径 */  
	             //   String dirPath =   SystemPath.getSysPath() +"fixed\\"++amsUnitProInfo.getUnitProjectNo();
	              //  FileUtils.createDirectory(dirPath);
				}else{
					officeService.update(office);
				}
		addMessage(redirectAttributes, "保存单位工程成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsUnitProInfo/?repage&projectId="+amsUnitProInfo.getProjectId();
	}
	
	@RequiresPermissions("ams:amsUnitProInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsUnitProInfo amsUnitProInfo, RedirectAttributes redirectAttributes) {
		//接收单位工程的id到文件表中做查询
		AmsFileInfo amsFileInfoId = amsFileInfoService.getAmsFileInfoId(amsUnitProInfo.getId());
		if(amsFileInfoId == null){
			//删除单位节点
			Office offices =new Office();
			offices.setId(amsUnitProInfo.getId());
			officeService.delete(offices);
			//删除单位工程同时删除机构
			amsUnitProInfoService.delete(amsUnitProInfo);
			addMessage(redirectAttributes, "删除单位工程成功");
			return "redirect:"+Global.getAdminPath()+"/ams/amsUnitProInfo/?repage&projectId="+amsUnitProInfo.getProjectId();
		}else{
			addMessage(redirectAttributes, "删除失败,已经存在资料文档!");
			return "redirect:"+Global.getAdminPath()+"/ams/amsUnitProInfo/?repage&projectId="+amsUnitProInfo.getProjectId();
		}
		
	}
	
	/**
	 * 专业记载页面初始化
	 * @param amsUnitProInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "majorForm")
	public String majorForm(AmsUnitProInfo amsUnitProInfo,String status, Model model) {
		//根据单位工程类型获取初始化配置信息
		amsUnitProInfo.setAmsDesExtendList(amsUnitProInfoService.findExtendDataList(amsUnitProInfo)); 
		model.addAttribute("status", status);
		model.addAttribute("amsUnitProInfo", amsUnitProInfo);
		return "modules/ams/amsMajorInfoForm";
	}
	
	/**
	 * 保存专业记载信息
	 * @param amsUnitProInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
/*	@RequiresPermissions("ams:amsUnitProInfo:editRecord")*/
	@RequestMapping(value = "majorSave")
	public String majorSave(AmsUnitProInfo amsUnitProInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsUnitProInfo)){
			return form(amsUnitProInfo,model);
		}
		amsUnitProInfoService.majorSave(amsUnitProInfo);
		addMessage(redirectAttributes, "专业记载保存成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsUnitProInfo/?repage&projectId="+amsUnitProInfo.getProjectId();
	}
	
	/**
	 * 
	 * 2017年11月23日 下午1:35:39
	 * @author dpj
	 * @param  amsUnitProInfo
	 */
	@RequiresPermissions("ams:amsUnitProInfo:view")
	@RequestMapping(value = {"main"})
	public String main(AmsUnitProInfo amsUnitProInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/ams/amsUnitProInfoMain";
	}
	/**
	 * 获得机构下的项目tree
	 * 2017年11月23日 上午11:39:22
	 * @author dpj
	 * @param ''
	 */
	@RequiresPermissions("ams:amsUnitProInfo:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//获取当前用户拥有的机构权限
		//Boolean isAll = false;
		List<Office> offices = null;
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
				map.put("id", office.getId());
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
	@RequiresPermissions("ams:amsUnitProInfo:view")
	@RequestMapping(value =  "businessList" )
	public String businessList(HttpServletRequest request, HttpServletResponse response,Model model) {
		AmsUnitDetailinfo amsUnitDetailinfo = amsUnitProInfoService.getAmsUnitDetailinfo();
		Page<AmsProjectInfo> page = amsUnitProInfoService.findAmsProInfo(
				new Page<AmsProjectInfo>(request, response));
		model.addAttribute("page", page);
		model.addAttribute("amsUnitDetailinfo", amsUnitDetailinfo);
		return "modules/ams/amsUnitProInfoOfficeList";
	}
}