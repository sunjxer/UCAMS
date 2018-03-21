/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ucams.common.config.Global;
import com.ucams.common.persistence.Page;
import com.ucams.common.web.BaseController;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.entity.AmsPreRpt;
import com.ucams.modules.ams.service.AmsPreRptService;

/**
 * 检查报告Controller
 * @author sunjx
 * @version 2017-08-04
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsPreRpt")
public class AmsPreRptController extends BaseController {

	@Autowired
	private AmsPreRptService amsPreRptService;
	
	@ModelAttribute
	public AmsPreRpt get(@RequestParam(required=false) String id) {
		AmsPreRpt entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsPreRptService.get(id);
		}
		if (entity == null){
			entity = new AmsPreRpt();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsPreRpt:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsPreRpt amsPreRpt, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsPreRpt> page = amsPreRptService.findPage(new Page<AmsPreRpt>(request, response), amsPreRpt); 
		model.addAttribute("page", page);
		return "modules/ams/amsPreRptList";
	}

	@RequiresPermissions("ams:amsPreRpt:view")
	@RequestMapping(value = "form")
	public String form(AmsPreRpt amsPreRpt, Model model) {
		model.addAttribute("amsPreRpt", amsPreRpt);
		return "modules/ams/amsPreRptForm";
	}

	@RequiresPermissions("ams:amsPreRpt:edit")
	@RequestMapping(value = "save")
	public String save(AmsPreRpt amsPreRpt, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsPreRpt)){
			return form(amsPreRpt, model);
		}
		amsPreRptService.save(amsPreRpt);
		addMessage(redirectAttributes, "保存检查报告成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsPreRpt/?repage";
	}
	
	@RequiresPermissions("ams:amsPreRpt:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsPreRpt amsPreRpt, RedirectAttributes redirectAttributes) {
		amsPreRptService.delete(amsPreRpt);
		addMessage(redirectAttributes, "删除检查报告成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsPreRpt/?repage";
	}

}