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
import com.ucams.common.web.BaseController;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.entity.AmsUserDict;
import com.ucams.modules.ams.service.AmsUserDictService;
import com.ucams.modules.sys.entity.Office;

/**
 * 用户数据字典Controller
 * @author sunjx
 * @version 2017-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsUserDict")
public class AmsUserDictController extends BaseController {

	@Autowired
	private AmsUserDictService amsUserDictService;
	
	@ModelAttribute
	public AmsUserDict get(@RequestParam(required=false) String id) {
		AmsUserDict entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsUserDictService.get(id);
		}
		if (entity == null){
			entity = new AmsUserDict();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsUserDict:view")
	@RequestMapping(value = {""})
	public String index(Model model) {
		return "modules/ams/amsUserDictIndex";
	}
	
	@RequiresPermissions("ams:amsUserDict:view")
	@RequestMapping(value = {"list"})
	public String list(AmsUserDict amsUserDict, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AmsUserDict> list = amsUserDictService.findList(amsUserDict); 
		model.addAttribute("list", list);
		return "modules/ams/amsUserDictList";
	}

	@RequiresPermissions("ams:amsUserDict:view")
	@RequestMapping(value = "form")
	public String form(AmsUserDict amsUserDict, Model model) {
		if (amsUserDict.getParent()!=null && StringUtils.isNotBlank(amsUserDict.getParent().getId())){
			amsUserDict.setParent(amsUserDictService.get(amsUserDict.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(amsUserDict.getId())){
				AmsUserDict amsUserDictChild = new AmsUserDict();
				amsUserDictChild.setParent(new AmsUserDict(amsUserDict.getParent().getId()));
				List<AmsUserDict> list = amsUserDictService.findList(amsUserDict); 
				if (list.size() > 0){
					amsUserDict.setSort(list.get(list.size()-1).getSort());
					if (amsUserDict.getSort() != null){
						amsUserDict.setSort(amsUserDict.getSort() + 30);
					}
				}
			}
		}
		if (amsUserDict.getSort() == null){
			amsUserDict.setSort(30);
		}
		model.addAttribute("amsUserDict", amsUserDict);
		return "modules/ams/amsUserDictForm";
	}

	@RequiresPermissions("ams:amsUserDict:edit")
	@RequestMapping(value = "save")
	public String save(AmsUserDict amsUserDict, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsUserDict)){
			return form(amsUserDict, model);
		}
		amsUserDictService.save(amsUserDict);
		addMessage(redirectAttributes, "保存数据字典成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsUserDict/list";
	}
	
	@RequiresPermissions("ams:amsUserDict:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsUserDict amsUserDict, RedirectAttributes redirectAttributes) {
		amsUserDictService.delete(amsUserDict);
		addMessage(redirectAttributes, "删除数据字典成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsUserDict/list";
	}

/*	@RequiresPermissions("user")*/
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AmsUserDict> list = amsUserDictService.findList(new AmsUserDict());
		for (int i=0; i<list.size(); i++){
			AmsUserDict e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				map.put("pIds", e.getParentIds());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}