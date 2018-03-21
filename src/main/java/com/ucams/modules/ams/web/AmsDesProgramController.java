/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ucams.common.config.Global;
import com.ucams.common.mapper.JsonMapper;
import com.ucams.common.persistence.Page;
import com.ucams.common.web.BaseController;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.entity.AmsDesProgram;
import com.ucams.modules.ams.entity.AmsUserDict;
import com.ucams.modules.ams.service.AmsDesProgramService;
import com.ucams.modules.ams.service.AmsUserDictService;
import com.ucams.modules.sys.utils.DictUtils;

/**
 * 工程专业配置记录Controller
 * @author sunjx
 * @version 2017-06-21
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsDesProgram")
public class AmsDesProgramController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(AmsDesProgramController.class);
	@Autowired
	private AmsDesProgramService amsDesProgramService;
	@Autowired
	private AmsUserDictService amsUserDictService;
	
	@ModelAttribute
	public AmsDesProgram get(@RequestParam(required=false) String id) {
		AmsDesProgram entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsDesProgramService.get(id);
		}
		if (entity == null){
			entity = new AmsDesProgram();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsDesProgram:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsDesProgram amsDesProgram, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsDesProgram> page = amsDesProgramService.findPage(new Page<AmsDesProgram>(request, response), amsDesProgram); 
		model.addAttribute("page", page);
		return "modules/ams/amsDesProgramList";
	}

	@RequiresPermissions("ams:amsDesProgram:view")
	@RequestMapping(value = "form")
	public String form(AmsDesProgram amsDesProgram, Model model) {
		model.addAttribute("amsDesProgram", amsDesProgram);
		model.addAttribute("selectsList", DictUtils.getDictList("ams_selects"));
		model.addAttribute("columnTypeList", DictUtils.getDictList("des_column_type"));
		model.addAttribute("isNullList", DictUtils.getDictList("yes_no"));
		return "modules/ams/amsDesProgramForm";
	}
	
	@RequiresPermissions("ams:amsDesProgram:edit")
	@RequestMapping(value = "save")
	public String save(AmsDesProgram amsDesProgram,Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsDesProgram)){
			return form(amsDesProgram, model);
		}
		amsDesProgramService.save(amsDesProgram);
		addMessage(redirectAttributes, "保存工程专业配置记录成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsDesProgram/?repage";
	}
	
	@RequiresPermissions("ams:amsDesProgram:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsDesProgram amsDesProgram, RedirectAttributes redirectAttributes) {
		amsDesProgramService.delete(amsDesProgram);
		addMessage(redirectAttributes, "删除工程专业配置记录成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsDesProgram/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		//构造专业列表树
		List<Map<String, Object>> mapList = Lists.newArrayList();
		AmsUserDict amsUserDict = new AmsUserDict();
		amsUserDict.setType("0");
		List<AmsUserDict> list = amsUserDictService.findList(amsUserDict);
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
	
	@RequiresPermissions("ams:amsDesProgram:edit")
	@RequestMapping(value = "changeAble")
	public String changeAble(AmsDesProgram amsDesProgram,@RequestParam("flag") String flag, Model model) {
		//获取将要变更的实体
		AmsDesProgram amsDescProgeram = amsDesProgramService.get(amsDesProgram);	
		try {	
			//启动
			if("0".equals(flag)){
				//将该方案所属专业的所有启用状态置为 禁用
				Map<String,Object > paramMap = new HashMap<String, Object>();
				paramMap.put("unitProjectType", amsDescProgeram.getUnitProjectType());
				paramMap.put("useable", "0");
				paramMap.put("programType", amsDescProgeram.getProgramType());		
				amsDesProgramService.updateUseAble(paramMap);
				//将选中方案状态置为 启用
				Map<String,Object > paramMapU = new HashMap<String, Object>();
				paramMapU.put("id", amsDescProgeram.getId());
				paramMapU.put("useable", "1");
				amsDesProgramService.updateUseAble(paramMapU);
			}
			//禁用
			if("1".equals(flag)){
				Map<String,Object > paramMapN = new HashMap<String, Object>();
				paramMapN.put("id", amsDescProgeram.getId());
				paramMapN.put("useable", "0");
				amsDesProgramService.updateUseAble(paramMapN);
			}
		} catch (Exception e) {
			logger.error("工程专业配置，启用/禁用 错误, 方案ID:" + amsDescProgeram.getId());
			e.printStackTrace();
		}
		
		return "redirect:"+Global.getAdminPath()+"/ams/amsDesProgram/?repage";
	}
}