/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ucams.common.config.Global;
import com.ucams.common.persistence.Page;
import com.ucams.common.web.BaseController;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.entity.AmsArchiveRules;
import com.ucams.modules.ams.entity.AmsGenre;
import com.ucams.modules.ams.service.AmsArchiveRulesService;
import com.ucams.modules.ams.service.AmsGenreService;
import com.ucams.modules.sys.entity.Office;

/**
 * 归档一览表规则表Controller
 * @author sunjx
 * @version 2017-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsArchiveRules")
public class AmsArchiveRulesController extends BaseController {

	@Autowired
	private AmsArchiveRulesService amsArchiveRulesService;
	@Autowired
	private AmsGenreService amsGenreService;
	
	@ModelAttribute
	public AmsArchiveRules get(@RequestParam(required=false) String id) {
		AmsArchiveRules entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsArchiveRulesService.get(id);
		}
		if (entity == null){
			entity = new AmsArchiveRules();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsArchiveRules:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
		return "modules/ams/amsArchiveRulesIndex";
	}
	
	@RequiresPermissions("ams:amsArchiveRules:view")
	@RequestMapping(value = {"list"})
	public String list(AmsArchiveRules amsArchiveRules, HttpServletRequest request, HttpServletResponse response, Model model) {
		//关联左侧树节点的查询条件
		if(EntityUtils.isNotEmpty(amsArchiveRules.getAmsGenre())){
			model.addAttribute("amsGenreId", amsArchiveRules.getAmsGenre().getId());
		}
		Page<AmsArchiveRules> page = amsArchiveRulesService.findPage(new Page<AmsArchiveRules>(request, response), amsArchiveRules); 
		model.addAttribute("page", page);
		return "modules/ams/amsArchiveRulesList";
	}

	@RequiresPermissions("ams:amsArchiveRules:view")
	@RequestMapping(value = "form")
	public String form(AmsArchiveRules amsArchiveRules, Model model) {
		//回显---归属类别
		if( EntityUtils.isNotEmpty(amsArchiveRules.getAmsGenre())){
			AmsGenre amsGenre =  amsGenreService.get(amsArchiveRules.getAmsGenre().getId());
			amsArchiveRules.setAmsGenre(amsGenre);
		}
		model.addAttribute("amsArchiveRules", amsArchiveRules);
		return "modules/ams/amsArchiveRulesForm";
	}

	@RequiresPermissions("ams:amsArchiveRules:edit")
	@RequestMapping(value = "save")
	public String save(AmsArchiveRules amsArchiveRules, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsArchiveRules)){
			return form(amsArchiveRules, model);
		}
		amsArchiveRulesService.save(amsArchiveRules);
		addMessage(redirectAttributes, "保存文件成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsArchiveRules/list?amsGenre.id=" + amsArchiveRules.getAmsGenre().getId();
	}
	
	@RequiresPermissions("ams:amsArchiveRules:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsArchiveRules amsArchiveRules, RedirectAttributes redirectAttributes) {
		amsArchiveRulesService.delete(amsArchiveRules);
		addMessage(redirectAttributes, "删除文件成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsArchiveRules/list?amsGenre.id=" + amsArchiveRules.getAmsGenre().getId();
	}
	
	@RequestMapping(value = "sortCheck")
	@ResponseBody
	public String sortCheck(Model model,String thisId,String nextId,String prevId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("thisId", thisId);
		try {
			if(EntityUtils.isNotEmpty(nextId)){   //下移
				paramMap.put("changeId", nextId);
				amsArchiveRulesService.changeSort(paramMap);
				return "success";
			}
			if(EntityUtils.isNotEmpty(prevId)){  //上移
				paramMap.put("changeId", prevId);
				amsArchiveRulesService.changeSort(paramMap);
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	@RequestMapping(value = "copyData")
	@ResponseBody
	public Object copyData(Model model,String[] selectedData) {
		// 数组非空验证
		if(EntityUtils.isNotEmpty(selectedData) && selectedData.length > 0){
			try {
				for(String rid : selectedData){  //遍历Ids并查询数据
					AmsArchiveRules rules =  amsArchiveRulesService.get(rid);
					if(EntityUtils.isNotEmpty(rules)){  //如果不为空则复制数据/保存
						rules.setId(null);  //置空id 
						amsArchiveRulesService.save(rules);
					}
				}
				return renderSuccess("数据复制成功！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return renderError("数据复制失败！");
	}
}