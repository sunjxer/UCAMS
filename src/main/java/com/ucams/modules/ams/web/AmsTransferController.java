/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import java.util.ArrayList;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ucams.common.config.Global;
import com.ucams.common.persistence.Page;
import com.ucams.common.utils.DateUtils;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.utils.excel.ExportExcel;
import com.ucams.common.web.BaseController;
import com.ucams.modules.ams.entity.AmsAcceptance;
import com.ucams.modules.ams.entity.AmsArchivesInfo;
import com.ucams.modules.ams.entity.AmsPreRpt;
import com.ucams.modules.ams.entity.AmsTransfer;
import com.ucams.modules.ams.entity.AmsTransferRpt;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.entity.AmsUnitProInfo;
import com.ucams.modules.ams.service.AmsArchivesInfoService;
import com.ucams.modules.ams.service.AmsTransferService;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 移交Controller
 * @author zkx
 * @version 2017-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsTransfer")
public class AmsTransferController extends BaseController {

	@Autowired
	private AmsTransferService amsTransferService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AmsArchivesInfoService amsArchivesInfoService;
	@ModelAttribute
	public AmsTransfer get(@RequestParam(required=false) String id) {
		AmsTransfer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsTransferService.get(id);
		}
		if (entity == null){
			entity = new AmsTransfer();
			entity.setUser(UserUtils.getUser());
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsTransfer:view")
	@RequestMapping(value = {"list"})
	public String list(AmsTransfer amsTransfer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsTransfer> page = amsTransferService.findPage(new Page<AmsTransfer>(request, response), amsTransfer); 
		model.addAttribute("page", page);
		model.addAttribute("type", amsTransfer.getType());
		return "modules/ams/amsTransferList";
	}

	@RequestMapping(value = "form")
	public String form(AmsTransfer amsTransfer, Model model) {
		String view = "amsTransferForm";		
		if(StringUtils.isNotBlank(amsTransfer.getId())&&!"0".equals(amsTransfer.getStatus())){
			//环节编号
			String taskDefKey = amsTransfer.getAct().getTaskDefKey();
			//批复环节
			if("transferPF".equals(taskDefKey)||"transferYJ".equals(taskDefKey)||"first".equals(taskDefKey)||"second".equals(taskDefKey)){
				if("1".equals(amsTransfer.getType())){
					amsTransferService.setSelList(amsTransfer);
				}
				view= "amsTransferDo";
			//修改
			}else if("transferXG".equals(taskDefKey)||"edit".equals(taskDefKey)){
				amsTransferService.setList(amsTransfer);
				view= "amsTransferForm";
			//流程结束
			}
			if(amsTransfer.getAct().isFinishTask()){
				if("1".equals(amsTransfer.getType())){
					amsTransferService.setSelList(amsTransfer);
				}
				view= "amsTransferView";
			}
		}else{	
			amsTransferService.setList(amsTransfer);
			view= "amsTransferForm";
		}
		
		model.addAttribute("amsTransfer", amsTransfer);
		return "modules/ams/"+view;	
	}
	@RequestMapping(value = "report")
	public String report(AmsTransfer amsTransfer, Model model) {
		//获取报告
		if(StringUtils.isNotBlank(amsTransfer.getId())){
			AmsTransferRpt amsPreRpt=new AmsTransferRpt();
			amsPreRpt.setTransferId(amsTransfer.getId());
			List<AmsTransferRpt>amsPreRptList = amsTransferService.amsPreRptList(amsPreRpt); 
			model.addAttribute("amsPreRptList", amsPreRptList);	
		}
		model.addAttribute("amsTransfer", amsTransfer);
		return "modules/ams/amsTransferReport";		
	}

	@RequiresPermissions("ams:amsTransfer:edit")
	@RequestMapping(value = "save")
	public String save(AmsTransfer amsTransfer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsTransfer)||StringUtils.isBlank(amsTransfer.getAcceptsString())){
			addMessage(model, "未选择预验收无法保存");
			return form(amsTransfer, model);
		}
	/*	if (StringUtils.isBlank(amsTransfer.getExten1())&&StringUtils.isBlank(amsTransfer.getExten2())){
			addMessage(model, "单位工程及案卷均不选择时无法保存或提交！");
			return form(amsTransfer, model);
		}*/
		amsTransferService.save(amsTransfer);
		addMessage(redirectAttributes, "yes".equals(amsTransfer.getAct().getFlag()) ? "移交保存并提交成功"
				:"no".equals(amsTransfer.getAct().getFlag()) ?  "销毁移交成功":"移交草稿保存成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsTransfer/list?project.id="+amsTransfer.getProject().getId()+"&type="+amsTransfer.getType();
	}
	@RequestMapping(value = "saveRpt")
	public String saveRpt(AmsTransfer amsTransfer, Model model, RedirectAttributes redirectAttributes) {
		
		amsTransferService.saveRpt(amsTransfer);
		model.addAttribute("amsTransfer", amsTransfer);
		addMessage(redirectAttributes, "报告保存成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsTransfer/report?id="+amsTransfer.getId();
	}
	@RequestMapping(value = "saveTransfer")
	public String saveGuidance(AmsTransfer amsTransfer, Model model, RedirectAttributes redirectAttributes){
		if("transferPF".equals(amsTransfer.getAct().getTaskDefKey())){
			if(StringUtils.isBlank(amsTransfer.getUser2().getId())){
				addMessage(model, "请填写批复人");
				return form(amsTransfer,model);
			}
			if(StringUtils.isBlank(amsTransfer.getTransferApproval())){
				addMessage(model, "请填写批复意见");
				return form(amsTransfer,model);
			}
		}
		if("transferYJ".equals(amsTransfer.getAct().getTaskDefKey())){
			if(StringUtils.isBlank(amsTransfer.getUser3().getId())){
				addMessage(model, "请填写批复人");
				return form(amsTransfer,model);
			}
		}
		amsTransferService.saveTransfer(amsTransfer);
		return "redirect:"+Global.getAdminPath()+"/act/task/todo/";
	}
	@RequiresPermissions("ams:amsTransfer:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsTransfer amsTransfer, RedirectAttributes redirectAttributes) {
		amsTransferService.delete(amsTransfer);
		addMessage(redirectAttributes, "删除移交成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsTransfer/list?project.id="+amsTransfer.getProject().getId()+"&type="+amsTransfer.getType();
	}
	@RequiresPermissions("ams:amsTransfer:view")
	@RequestMapping(value = {"main", ""})
	public String main(AmsTransfer amsTransfer) {
		return "modules/ams/amsTransferMain";
	}
	@RequestMapping(value = {"video"})
	public String video(AmsTransfer amsTransfer) {
		return "modules/ams/amsTransferMainVideo";
	}
	/**
	 * 获取JSON数据。
	 * 
	 * @return
	 */
	@RequiresPermissions("ams:amsTransfer:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Boolean isAll = false;
		List<Office> offices = new ArrayList<Office>();
		//超级管理员
		if(	UserUtils.getUser().isAdmin())	{	
			offices = officeService.onlyUnitPro(UserUtils.getUser().getOffice().getParent());
		}else{
			offices = officeService.findList(false);
		}
		for (int i = -1; i < offices.size(); i++) {
			Map<String, Object> map = Maps.newHashMap();
				//设置树的根节点
				if(i == -1){
					map.put("id", "100");
					map.put("name", "项目列表");
					map.put("isParent", true);
					mapList.add(map);
					continue;
				}
				Office office = offices.get(i);
				if ("3".equals(office.getGrade())&&"1".equals(office.getUseable())) {
					// id 增加$PRO$与$UNIT$用于区分项目工程与单位工程 ；$PRO$：项目工程；$UNIT$：单位工程
					map.put("id", office.getId());
					map.put("pId", "100");
					map.put("name", office.getName());
					mapList.add(map);
				} 
		}
		return mapList;
	}
	/**
	 * 获取单位工程JSON数据。
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeDataAcc")
	public List<Map<String, Object>> treeDataAcc(@RequestParam(required=false) String id) {
		AmsTransfer amsTransfer=new AmsTransfer();
		amsTransfer.setId(id);
		amsTransferService.setSelectList(amsTransfer);
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", "100");
		map.put("name", "单位工程及案卷");
		map.put("isParent", true);
		mapList.add(map);
		for (int i = 0; i < amsTransfer.getAmsUnitProInfoList().size(); i++) {
			map = Maps.newHashMap();
			AmsUnitProInfo   amsUnitProInfo  = amsTransfer.getAmsUnitProInfoList().get(i);
					map.put("id", amsUnitProInfo.getId());
					map.put("pId", "100");
					map.put("name", amsUnitProInfo.getUnitProjectName());
					mapList.add(map);
		}
		if (amsTransfer.getAmsArchivesInfoList().size()>0) {
			map = Maps.newHashMap();
			map.put("id", "-1");
			map.put("pId", "100");
			map.put("name", "公用卷");
			mapList.add(map);
		}
		if (amsTransfer.getAmsConstructDesList().size()>0) {
			map = Maps.newHashMap();
			map.put("id", "CON");
			map.put("pId", "100");
			map.put("name", "建设工程规划");
			mapList.add(map);
		}
		if (amsTransfer.getAmsLandDesList().size()>0) {
			map = Maps.newHashMap();
			map.put("id", "LAN");
			map.put("pId", "100");
			map.put("name", "建设工程用地");
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * 案卷列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "archivesList" })
	public String archivesList(@RequestParam(required=false) String unitProjectId,@RequestParam(required=false) String acceptanceId,@RequestParam(required=false) String projectId,HttpServletRequest request, HttpServletResponse response,Model model) {
		AmsArchivesInfo amsArchivesInfo=new AmsArchivesInfo();
		Page<AmsArchivesInfo> page;
		amsArchivesInfo.setUnitProjectId(unitProjectId);
		if("-1".equals(unitProjectId)){
			amsArchivesInfo.setProjectId(projectId);
			amsArchivesInfo.setAcceptanceId(acceptanceId);
			page = amsArchivesInfoService.transferFindProArchivesPage(new Page<AmsArchivesInfo>(request, response),amsArchivesInfo);
		}else {
			page = amsArchivesInfoService.findProArchivesPage(new Page<AmsArchivesInfo>(request, response),amsArchivesInfo);
		}
		model.addAttribute("unitProjectId", unitProjectId);
		model.addAttribute("page", page);
		return "modules/ams/amsAcceptanceArchivesList";
	}
	/**
	 * 查询责任主体信息及预验收列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsTransfer:view")
	@RequestMapping(value = { "allList" })
	public String allList(AmsTransfer amsTransfer,HttpServletRequest request, HttpServletResponse response,Model model) {
		AmsUnitDetailinfo amsUnitDetailinfo = amsTransferService.getAmsUnitDetailinfo();
		amsTransfer.setRole(UserUtils.getUser().getRoleList().get(0));
/*		Page<AmsTransfer> page = amsTransferService.findPage(new Page<AmsTransfer>(request, response),amsTransfer);
		model.addAttribute("page", page);
*/		model.addAttribute("amsUnitDetailinfo", amsUnitDetailinfo);
		return "modules/ams/amsAmsTransferAllList";
	}
	/**
	 * 导出报告
	 * @param amsTransfer
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(AmsTransfer amsTransfer, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "报告数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            AmsTransferRpt amsPreRpt=new AmsTransferRpt();
    			amsPreRpt.setTransferId(amsTransfer.getId());
    			List<AmsTransferRpt>amsPreRptList = amsTransferService.amsPreRptList(amsPreRpt); 
    		new ExportExcel("报告数据", AmsTransferRpt.class).setDataList(amsPreRptList).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}
		return "";
    }

}