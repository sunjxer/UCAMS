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
import com.ucams.modules.ams.entity.AmsConstructDes;
import com.ucams.modules.ams.service.AmsConstructDesService;

/**
 * 建设工程规划著录Controller
 * @author ws
 * @version 2017-07-28
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsConstructDes")
public class AmsConstructDesController extends BaseController {

	@Autowired
	private AmsConstructDesService amsConstructDesService;
	
	@ModelAttribute
	public AmsConstructDes get(@RequestParam(required=false) String id) {
		AmsConstructDes entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsConstructDesService.get(id);
		}
		if (entity == null){
			entity = new AmsConstructDes();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsConstructDes:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsConstructDes amsConstructDes, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsConstructDes> page = amsConstructDesService.findPage(new Page<AmsConstructDes>(request, response), amsConstructDes); 
		model.addAttribute("page", page);
		return "modules/ams/amsConstructDesList";
	}

	@RequiresPermissions("ams:amsConstructDes:view")
	@RequestMapping(value = "form")
	public String form(AmsConstructDes amsConstructDes, Model model) {
		model.addAttribute("amsConstructDes", amsConstructDes);
		return "modules/ams/amsConstructDesForm";
	}

	@RequiresPermissions("ams:amsConstructDes:edit")
	@RequestMapping(value = "save")
	public String save(AmsConstructDes amsConstructDes, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsConstructDes)){
			return form(amsConstructDes, model);
		}
		amsConstructDesService.save(amsConstructDes);
		addMessage(redirectAttributes, "保存建设工程规划成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsConstructDes/?repage";
	}
	
	@RequiresPermissions("ams:amsConstructDes:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsConstructDes amsConstructDes, RedirectAttributes redirectAttributes) {
		amsConstructDesService.delete(amsConstructDes);
		addMessage(redirectAttributes, "删除建设工程规划成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsConstructDes/?repage";
	}

}