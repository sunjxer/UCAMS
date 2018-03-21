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
import com.ucams.common.web.BaseController;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.entity.AmsGuidance;
import com.ucams.modules.ams.service.AmsGuidanceService;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 业务指导内容管理Controller
 * @author gyl
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsGuidance")
public class AmsGuidanceController extends BaseController {

	@Autowired
	private AmsGuidanceService amsGuidanceService;
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public AmsGuidance get(@RequestParam(required=false) String id) {
		AmsGuidance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsGuidanceService.get(id);
		}
		if (entity == null){
			entity = new AmsGuidance();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsGuidance:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsGuidance amsGuidance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsGuidance> page = amsGuidanceService.findPage(new Page<AmsGuidance>(request, response), amsGuidance); 
		model.addAttribute("page", page);
		return "modules/ams/amsGuidanceList";
	}

	@RequiresPermissions("ams:amsGuidance:view")
	@RequestMapping(value = "form")
	public String form(AmsGuidance amsGuidance, Model model) {
		
		String view = "amsGuidanceForm";		
		if(StringUtils.isNotBlank(amsGuidance.getId())){
			//环节编号
			String taskDefKey = amsGuidance.getAct().getTaskDefKey();
			//状态
			String status = amsGuidance.getAct().getStatus();
			if("finish".equals(status)){
				view= "amsGuidanceView";
			}else{
				//批复环节
				if("accept".equals(taskDefKey)){
					view= "amsGuidanceTodo";
				//指导内容添加环节
				}else if("content".equals(taskDefKey)){
					view= "amsGuidanceToContent";
				//修改
				}else if("update".equals(taskDefKey)){
					view= "amsGuidanceForm";
				//流程结束
				}else if(amsGuidance.getAct().isFinishTask()){
					view= "amsGuidanceView";
				}
			}
			
		//业务指导申请
		}else{			
			view= "amsGuidanceForm";
		}
		
		model.addAttribute("amsGuidance", amsGuidance);
		return "modules/ams/"+view;		
	}
	
	@RequiresPermissions("ams:amsGuidance:edit")
	@RequestMapping(value = "save")
	public String save(AmsGuidance amsGuidance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsGuidance)){
			return form(amsGuidance, model);
		}
		String result = amsGuidanceService.saveGuid(amsGuidance);
		
		addMessage(redirectAttributes, result);
		return "redirect:"+Global.getAdminPath()+"/ams/amsGuidance/?repage";
	}
	
	@RequestMapping(value = "saveGuidance")
	public String saveGuidance(AmsGuidance amsGuidance, Model model, RedirectAttributes redirectAttributes){
		if("accept".equals(amsGuidance.getAct().getTaskDefKey())){
			if(StringUtils.isBlank(amsGuidance.getOpinion())){
				addMessage(model, "请填写批复意见");
				return form(amsGuidance,model);
			}
		}else{
			if(StringUtils.isBlank(amsGuidance.getGuidance()) || amsGuidance.getGuidanceDate() == null){
				addMessage(model, "请填写指导内容和指导日期");
				return form(amsGuidance,model);
			}
		}
		amsGuidanceService.saveGuidance(amsGuidance);
		return "redirect:"+Global.getAdminPath()+"/act/task/todo/";
	}
	
	@RequiresPermissions("ams:amsGuidance:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsGuidance amsGuidance, RedirectAttributes redirectAttributes) {
		amsGuidanceService.delete(amsGuidance);
		addMessage(redirectAttributes, "删除业务指导成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsGuidance/?repage";
	}
		
	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示业务分类
	 * @param response
	 * @return
	 */
//	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData1")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		User u = UserUtils.getUser();
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.onlyUnitPro(u.getOffice().getParent());
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			//只显示项目列表
			if(!"3".equals(e.getGrade())){
				continue;
			}
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				map.put("address", e.getProAddress());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
}