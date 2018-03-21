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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ucams.common.config.Global;
import com.ucams.common.persistence.Page;
import com.ucams.common.web.BaseController;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.entity.AmsAcceptanceArchives;
import com.ucams.modules.ams.service.AmsAcceptanceArchivesService;

/**
 * 预验收案卷Controller
 * @author zkx
 * @version 2017-07-18
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsAcceptanceArchives")
public class AmsAcceptanceArchivesController extends BaseController {

	@Autowired
	private AmsAcceptanceArchivesService amsAcceptanceArchivesService;
	
	@ModelAttribute
	public AmsAcceptanceArchives get(@RequestParam(required=false) String id) {
		AmsAcceptanceArchives entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsAcceptanceArchivesService.get(id);
		}
		if (entity == null){
			entity = new AmsAcceptanceArchives();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsAcceptanceArchives:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsAcceptanceArchives amsAcceptanceArchives, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsAcceptanceArchives> page = amsAcceptanceArchivesService.findPage(new Page<AmsAcceptanceArchives>(request, response), amsAcceptanceArchives); 
		model.addAttribute("page", page);
		return "modules/ams/amsAcceptanceArchivesList";
	}

	@RequiresPermissions("ams:amsAcceptanceArchives:view")
	@RequestMapping(value = "form")
	public String form(AmsAcceptanceArchives amsAcceptanceArchives, Model model) {
		model.addAttribute("amsAcceptanceArchives", amsAcceptanceArchives);
		return "modules/ams/amsAcceptanceArchivesForm";
	}

	@RequiresPermissions("ams:amsAcceptanceArchives:edit")
	@RequestMapping(value = "save")
	public String save(AmsAcceptanceArchives amsAcceptanceArchives, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsAcceptanceArchives)){
			return form(amsAcceptanceArchives, model);
		}
		amsAcceptanceArchivesService.save(amsAcceptanceArchives);
		addMessage(redirectAttributes, "保存预验收案卷成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsAcceptanceArchives/?repage";
	}
	
	@RequiresPermissions("ams:amsAcceptanceArchives:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsAcceptanceArchives amsAcceptanceArchives, RedirectAttributes redirectAttributes) {
		amsAcceptanceArchivesService.delete(amsAcceptanceArchives);
		addMessage(redirectAttributes, "删除预验收案卷成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsAcceptanceArchives/?repage";
	}

}