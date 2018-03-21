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
import com.ucams.modules.ams.entity.AmsTransferRpt;
import com.ucams.modules.ams.service.AmsTransferRptService;

/**
 * 移交检查报告Controller
 * @author zkx
 * @version 2017-08-08
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsTransferRpt")
public class AmsTransferRptController extends BaseController {

	@Autowired
	private AmsTransferRptService amsTransferRptService;
	
	@ModelAttribute
	public AmsTransferRpt get(@RequestParam(required=false) String id) {
		AmsTransferRpt entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsTransferRptService.get(id);
		}
		if (entity == null){
			entity = new AmsTransferRpt();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsTransferRpt:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsTransferRpt amsTransferRpt, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsTransferRpt> page = amsTransferRptService.findPage(new Page<AmsTransferRpt>(request, response), amsTransferRpt); 
		model.addAttribute("page", page);
		return "modules/ams/amsTransferRptList";
	}

	@RequiresPermissions("ams:amsTransferRpt:view")
	@RequestMapping(value = "form")
	public String form(AmsTransferRpt amsTransferRpt, Model model) {
		model.addAttribute("amsTransferRpt", amsTransferRpt);
		return "modules/ams/amsTransferRptForm";
	}

	@RequiresPermissions("ams:amsTransferRpt:edit")
	@RequestMapping(value = "save")
	public String save(AmsTransferRpt amsTransferRpt, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsTransferRpt)){
			return form(amsTransferRpt, model);
		}
		amsTransferRptService.save(amsTransferRpt);
		addMessage(redirectAttributes, "保存移交检查报告成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsTransferRpt/?repage";
	}
	
	@RequiresPermissions("ams:amsTransferRpt:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsTransferRpt amsTransferRpt, RedirectAttributes redirectAttributes) {
		amsTransferRptService.delete(amsTransferRpt);
		addMessage(redirectAttributes, "删除移交检查报告成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsTransferRpt/?repage";
	}

}