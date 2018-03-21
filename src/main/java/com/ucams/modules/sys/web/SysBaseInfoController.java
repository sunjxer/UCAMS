/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.sys.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ucams.common.config.Global;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.web.BaseController;
import com.ucams.modules.sys.entity.SysBaseInfo;
import com.ucams.modules.sys.service.SysBaseInfoService;

/**
 * 系统规则管理Controller
 * @author gyl
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysBaseInfo")
public class SysBaseInfoController extends BaseController {

	@Autowired
	private SysBaseInfoService sysBaseInfoService;
	
	@ModelAttribute
	public SysBaseInfo get(@RequestParam(required=false) String id) {
		SysBaseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysBaseInfoService.get(id);
		}
		if (entity == null){
			entity = new SysBaseInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysBaseInfo:view")
	@RequestMapping(value = {"form",""})
	public String form(SysBaseInfo sysBaseInfo, Model model) {
		sysBaseInfo = sysBaseInfoService.findSysBaseInfo();
		model.addAttribute("sysBaseInfo", sysBaseInfo);
		return "modules/sys/sysBaseInfoForm";
	}

	@RequiresPermissions("sys:sysBaseInfo:edit")
	@RequestMapping(value = "save")
	public String save(SysBaseInfo sysBaseInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysBaseInfo)){
			return form(sysBaseInfo, model);
		}
		sysBaseInfoService.save(sysBaseInfo);
		addMessage(redirectAttributes, "保存系统规则成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysBaseInfo/?repage";
	}
	
	@RequiresPermissions("sys:sysBaseInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SysBaseInfo sysBaseInfo, RedirectAttributes redirectAttributes) {
		sysBaseInfoService.delete(sysBaseInfo);
		addMessage(redirectAttributes, "删除系统规则成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysBaseInfo/";
	}

}