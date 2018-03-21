/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import java.util.List;

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
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.service.AmsUnitDetailinfoService;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 责任主体详细信息管理Controller
 * @author ws
 * @version 2017-07-04
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsUnitDetailinfo")
public class AmsUnitDetailinfoController extends BaseController {

	@Autowired
	private AmsUnitDetailinfoService amsUnitDetailinfoService;
	
	@ModelAttribute
	public AmsUnitDetailinfo get(@RequestParam(required=false) String id) {
		AmsUnitDetailinfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsUnitDetailinfoService.get(id);
		}
		if (entity == null){
			entity = new AmsUnitDetailinfo();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsUnitDetailinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsUnitDetailinfo amsUnitDetailinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsUnitDetailinfo> page = amsUnitDetailinfoService.findPage(new Page<AmsUnitDetailinfo>(request, response), amsUnitDetailinfo); 
		model.addAttribute("page", page);
		return "modules/ams/amsUnitDetailinfoList";
	}

	@RequiresPermissions("ams:amsUnitDetailinfo:view")
	@RequestMapping(value = "form")
	public String form(AmsUnitDetailinfo amsUnitDetailinfo, Model model) {
		//根据责任主体id获取责任主体明细id 
		AmsUnitDetailinfo amsUnitinfo=amsUnitDetailinfoService.getByUnitId( amsUnitDetailinfo);
		model.addAttribute("amsUnitDetailinfo", amsUnitinfo);
		return "modules/ams/amsUnitDetailinfoForm";
	}

	@RequestMapping(value = "save")
	public String save(AmsUnitDetailinfo amsUnitDetailinfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsUnitDetailinfo)){
			return form(amsUnitDetailinfo, model);
		}
		amsUnitDetailinfoService.save(amsUnitDetailinfo);
		addMessage(redirectAttributes, "保存责任主体信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/resBody/list";
	}
	
	@RequestMapping(value = "supplement")
	public String supplement(AmsUnitDetailinfo amsUnitDetailinfo, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (!beanValidator(model, amsUnitDetailinfo)){
			return form(amsUnitDetailinfo, model);
		}
		amsUnitDetailinfo.setRole(user.getRoleList().get(0));
		amsUnitDetailinfoService.save(amsUnitDetailinfo);
		addMessage(redirectAttributes, "保存责任主体信息成功");
		return "modules/sys/sysIndex";
	}
	@RequiresPermissions("ams:amsUnitDetailinfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsUnitDetailinfo amsUnitDetailinfo, RedirectAttributes redirectAttributes) {
		amsUnitDetailinfoService.delete(amsUnitDetailinfo);
		addMessage(redirectAttributes, "删除责任主体信息成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsUnitDetailinfo/?repage";
	}

}