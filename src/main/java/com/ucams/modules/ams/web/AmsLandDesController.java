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
import com.ucams.modules.ams.entity.AmsLandDes;
import com.ucams.modules.ams.service.AmsLandDesService;

/**
 * 建设用地规划著录Controller
 * @author ws
 * @version 2017-07-28
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsLandDes")
public class AmsLandDesController extends BaseController {

	@Autowired
	private AmsLandDesService amsLandDesService;
	
	@ModelAttribute
	public AmsLandDes get(@RequestParam(required=false) String id) {
		AmsLandDes entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsLandDesService.get(id);
		}
		if (entity == null){
			entity = new AmsLandDes();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsLandDes:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsLandDes amsLandDes, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsLandDes> page = amsLandDesService.findPage(new Page<AmsLandDes>(request, response), amsLandDes); 
		model.addAttribute("page", page);
		return "modules/ams/amsLandDesList";
	}

	@RequiresPermissions("ams:amsLandDes:view")
	@RequestMapping(value = "form")
	public String form(AmsLandDes amsLandDes, Model model) {
		model.addAttribute("amsLandDes", amsLandDes);
		return "modules/ams/amsLandDesForm";
	}

	@RequiresPermissions("ams:amsLandDes:edit")
	@RequestMapping(value = "save")
	public String save(AmsLandDes amsLandDes, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsLandDes)){
			return form(amsLandDes, model);
		}
		amsLandDesService.save(amsLandDes);
		addMessage(redirectAttributes, "保存建设用地规划成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsLandDes/?repage";
	}
	
	@RequiresPermissions("ams:amsLandDes:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsLandDes amsLandDes, RedirectAttributes redirectAttributes) {
		amsLandDesService.delete(amsLandDes);
		addMessage(redirectAttributes, "删除建设用地规划成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsLandDes/?repage";
	}

}