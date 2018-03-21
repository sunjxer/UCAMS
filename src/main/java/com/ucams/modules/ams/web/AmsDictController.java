/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

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
import com.ucams.common.utils.StringUtils;
import com.ucams.common.web.BaseController;
import com.ucams.modules.ams.entity.AmsDict;
import com.ucams.modules.ams.service.AmsDictService;
import com.ucams.modules.sys.entity.Dict;

/**
 * 业务字典Controller
 * @author zhuye
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsDict")
public class AmsDictController extends BaseController {

	@Autowired
	private AmsDictService amsDictService;
	
	@ModelAttribute
	public AmsDict get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return amsDictService.get(id);
		}else{
			return new AmsDict();
		}
	}
	
	@RequiresPermissions("ams:amsDict:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsDict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> typeList = amsDictService.findTypeList();
		model.addAttribute("typeList", typeList);
        Page<AmsDict> page = amsDictService.findPage(new Page<AmsDict>(request, response), dict); 
        model.addAttribute("page", page);
		return "modules/ams/amsDictList";
	}

	@RequiresPermissions("ams:amsDict:view")
	@RequestMapping(value = "form")
	public String form(AmsDict dict, Model model) {
		model.addAttribute("dict", dict);
		return "modules/ams/amsDictForm";
	}

	@RequiresPermissions("ams:amsDict:edit")
	@RequestMapping(value = "save")//@Valid 
	public String save(AmsDict dict, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/ams/amsDict/?repage&type="+dict.getType();
		}
		if (!beanValidator(model, dict)){
			return form(dict, model);
		}
		amsDictService.save(dict);
		addMessage(redirectAttributes, "保存业务字典'" + dict.getLabel() + "'成功");
		return "redirect:" + adminPath + "/ams/amsDict/?repage&type="+dict.getType();
	}
	
	@RequiresPermissions("ams:amsDict:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsDict dict, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/ams/amsDict/?repage";
		}
		amsDictService.delete(dict);
		addMessage(redirectAttributes, "删除业务字典成功");
		return "redirect:" + adminPath + "/ams/amsDict/?repage&type="+dict.getType();
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String type, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		AmsDict dict = new AmsDict();
		dict.setType(type);
		List<AmsDict> list = amsDictService.findList(dict);
		for (int i=0; i<list.size(); i++){
			AmsDict e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", StringUtils.replace(e.getLabel(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "listData")
	public List<AmsDict> listData(@RequestParam(required=false) String type) {
		AmsDict dict = new AmsDict();
		dict.setType(type);
		return amsDictService.findList(dict);
	}

}
