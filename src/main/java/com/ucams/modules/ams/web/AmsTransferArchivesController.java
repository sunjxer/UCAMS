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
import com.ucams.modules.ams.entity.AmsTransferArchives;
import com.ucams.modules.ams.service.AmsTransferArchivesService;

/**
 * 移交案卷Controller
 * @author zkx
 * @version 2017-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsTransferArchives")
public class AmsTransferArchivesController extends BaseController {

	@Autowired
	private AmsTransferArchivesService amsTransferArchivesService;
	
	@ModelAttribute
	public AmsTransferArchives get(@RequestParam(required=false) String id) {
		AmsTransferArchives entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsTransferArchivesService.get(id);
		}
		if (entity == null){
			entity = new AmsTransferArchives();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsTransferArchives:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsTransferArchives amsTransferArchives, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsTransferArchives> page = amsTransferArchivesService.findPage(new Page<AmsTransferArchives>(request, response), amsTransferArchives); 
		model.addAttribute("page", page);
		return "modules/ams/amsTransferArchivesList";
	}

	@RequiresPermissions("ams:amsTransferArchives:view")
	@RequestMapping(value = "form")
	public String form(AmsTransferArchives amsTransferArchives, Model model) {
		model.addAttribute("amsTransferArchives", amsTransferArchives);
		return "modules/ams/amsTransferArchivesForm";
	}

	@RequiresPermissions("ams:amsTransferArchives:edit")
	@RequestMapping(value = "save")
	public String save(AmsTransferArchives amsTransferArchives, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsTransferArchives)){
			return form(amsTransferArchives, model);
		}
		amsTransferArchivesService.save(amsTransferArchives);
		addMessage(redirectAttributes, "保存移交案卷成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsTransferArchives/?repage";
	}
	
	@RequiresPermissions("ams:amsTransferArchives:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsTransferArchives amsTransferArchives, RedirectAttributes redirectAttributes) {
		amsTransferArchivesService.delete(amsTransferArchives);
		addMessage(redirectAttributes, "删除移交案卷成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsTransferArchives/?repage";
	}

}