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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ucams.common.config.Global;
import com.ucams.common.persistence.Page;
import com.ucams.common.utils.DateUtils;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.FTPPointUtil;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.utils.excel.ExportExcel;
import com.ucams.common.web.BaseController;
import com.ucams.modules.ams.entity.AmsAcceptance;
import com.ucams.modules.ams.entity.AmsArchivesInfo;
import com.ucams.modules.ams.entity.AmsConstructDes;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsLandDes;
import com.ucams.modules.ams.entity.AmsPreRpt;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.entity.AmsUnitProInfo;
import com.ucams.modules.ams.service.AmsAcceptanceService;
import com.ucams.modules.ams.service.AmsArchivesInfoService;
import com.ucams.modules.ams.service.AmsConstructDesService;
import com.ucams.modules.ams.service.AmsFileInfoService;
import com.ucams.modules.ams.service.AmsLandDesService;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 预验收管理Controller
 * @author zkx
 * @version 2017-07-11
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsAcceptance")
public class AmsAcceptanceController extends BaseController {

	@Autowired
	private AmsAcceptanceService amsAcceptanceService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AmsArchivesInfoService amsArchivesInfoService;	
	@Autowired
	private AmsFileInfoService amsFileInfoService;
	
	@Autowired
	private AmsConstructDesService amsConstructDesService;
	
	@Autowired
	private AmsLandDesService amsLandDesService;
	
	
	@ModelAttribute
	public AmsAcceptance get(@RequestParam(required=false) String id) {
		AmsAcceptance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsAcceptanceService.get(id);
		}
		if (entity == null){
			entity = new AmsAcceptance();
			entity.setUser(UserUtils.getUser());
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsAcceptance:view")
	@RequestMapping(value = {"main", ""})
	public String main(AmsAcceptance amsAcceptance, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/ams/amsAcceptanceMain";
	}
	@RequestMapping(value = {"video"})
	public String video(AmsAcceptance amsAcceptance, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/ams/amsVideoAcceptanceMain";
	}
	@RequiresPermissions("ams:amsAcceptance:view")
	@RequestMapping(value = {"list"})
	public String list(AmsAcceptance amsAcceptance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsAcceptance> page = amsAcceptanceService.findPage(new Page<AmsAcceptance>(request, response), amsAcceptance); 
		model.addAttribute("page", page);
		model.addAttribute("type", amsAcceptance.getType());
		return "modules/ams/amsAcceptanceList";
	}

	@RequestMapping(value = "form")
	public String form(AmsAcceptance amsAcceptance, Model model) {
		String view = "amsAcceptanceForm";		
		if(StringUtils.isNotBlank(amsAcceptance.getId())&&!"0".equals(amsAcceptance.getStatus())){
			//环节编号
			String taskDefKey = amsAcceptance.getAct().getTaskDefKey();
			//批复环节
			if("acceptance".equals(taskDefKey)||"accept".equals(taskDefKey)||"first".equals(taskDefKey)||"second".equals(taskDefKey)){
				if("1".equals(amsAcceptance.getType())){
					amsAcceptanceService.setVideoSelList(amsAcceptance);
				}
				view= "amsAcceptanceDo";
			//修改
			}else if("edit".equals(taskDefKey)){
				view= "amsAcceptanceForm";
				if("1".equals(amsAcceptance.getType())){
					amsAcceptanceService.setVideoList(amsAcceptance);
				}else{
					amsAcceptanceService.setList(amsAcceptance);	
				}
			//流程结束
			}if(amsAcceptance.getAct().isFinishTask()){
				if("1".equals(amsAcceptance.getType())){
					amsAcceptanceService.setVideoSelList(amsAcceptance);
				}
				view= "amsAcceptanceView";
			}
		
		}else{			
			view= "amsAcceptanceForm";
			if("1".equals(amsAcceptance.getType())){
				amsAcceptanceService.setVideoList(amsAcceptance);
			}else{
				amsAcceptanceService.setList(amsAcceptance);	
			}
		}
		
		//获取报告
		if(StringUtils.isNotBlank(amsAcceptance.getId())){
			AmsPreRpt amsPreRpt=new AmsPreRpt();
			amsPreRpt.setTransferId(amsAcceptance.getId());
			amsAcceptance.setAmsPreRptList(amsAcceptanceService.amsPreRptList(amsPreRpt)); 
		}
		model.addAttribute("amsAcceptance", amsAcceptance);
		return "modules/ams/"+view;		
	}
	@RequestMapping(value = "report")
	public String report(AmsAcceptance amsAcceptance, Model model) {
		//获取报告
		if(StringUtils.isNotBlank(amsAcceptance.getId())){
			AmsPreRpt amsPreRpt=new AmsPreRpt();
			amsPreRpt.setTransferId(amsAcceptance.getId());
			List<AmsPreRpt>amsPreRptList = amsAcceptanceService.amsPreRptList(amsPreRpt); 
			model.addAttribute("amsPreRptList", amsPreRptList);	
		}
		model.addAttribute("amsAcceptance", amsAcceptance);
		return "modules/ams/amsAcceptanceReport";		
	}

	@RequiresPermissions("ams:amsAcceptance:edit")
	@RequestMapping(value = "save")
	public String save(AmsAcceptance amsAcceptance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsAcceptance)){
			return form(amsAcceptance, model);
		}
		/*if (StringUtils.isBlank(amsAcceptance.getArchiverString())&&StringUtils.isBlank(amsAcceptance.getProjectString())){
			addMessage(model, "单位工程及案卷均不选择时无法保存或提交！");
			return form(amsAcceptance, model);
		}*/
		amsAcceptanceService.save(amsAcceptance);
		addMessage(redirectAttributes, "yes".equals(amsAcceptance.getAct().getFlag()) ? "预验收保存并提交成功"
				: "no".equals(amsAcceptance.getAct().getFlag()) ?  "销毁成功":"草稿保存成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsAcceptance/list?project.id="+amsAcceptance.getProject().getId()+"&type="+amsAcceptance.getType();
	}
	@RequestMapping(value = "saveRpt")
	public String saveRpt(AmsAcceptance amsAcceptance, Model model, RedirectAttributes redirectAttributes) {
		
		amsAcceptanceService.saveRpt(amsAcceptance);
		model.addAttribute("amsAcceptance", amsAcceptance);
		addMessage(redirectAttributes, "报告保存成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsAcceptance/report?id="+amsAcceptance.getId();
	}
	
	@RequestMapping(value = "saveAcceptance")
	public String saveGuidance(AmsAcceptance amsAcceptance, Model model, RedirectAttributes redirectAttributes){
		if("acceptance".equals(amsAcceptance.getAct().getTaskDefKey())){
			if(StringUtils.isBlank(amsAcceptance.getUser2().getId())){
				addMessage(model, "请填写批复人");
				return form(amsAcceptance,model);
			}
			if(StringUtils.isBlank(amsAcceptance.getPreAcceptanceApprovalOpinions())){
				addMessage(model, "请填写批复意见");
				return form(amsAcceptance,model);
			}
		}
		if("accept".equals(amsAcceptance.getAct().getTaskDefKey())){
			if(StringUtils.isBlank(amsAcceptance.getUser3().getId())){
				addMessage(model, "请填写批复人");
				return form(amsAcceptance,model);
			}
		}
		amsAcceptanceService.saveAcceptance(amsAcceptance);
		return "redirect:"+Global.getAdminPath()+"/act/task/todo/";
	}
	
	@RequiresPermissions("ams:amsAcceptance:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsAcceptance amsAcceptance, RedirectAttributes redirectAttributes) {
		amsAcceptanceService.delete(amsAcceptance);
		addMessage(redirectAttributes, "删除预验收成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsAcceptance/list?project.id="+amsAcceptance.getProject().getId()+"&type="+amsAcceptance.getType();
	}
	/**
	 * 获取JSON数据。
	 * 
	 * @return
	 */
	@RequiresPermissions("ams:amsAcceptance:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//获取当前用户拥有的机构权限
		Boolean isAll = false;
		List<Office> offices = null;
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
				map.put("id", office.getId());
				map.put("pId", "100");
				map.put("name", office.getName());
				mapList.add(map);
			} 
		}
		return mapList;
	}
	/**
	 * 获取预验收单位工程JSON数据。
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeDataAcc")
	public List<Map<String, Object>> treeDataAcc(@RequestParam(required=false) String id) {
		AmsAcceptance amsAcceptance=new AmsAcceptance();
		amsAcceptance.setId(id);
		amsAcceptanceService.setSelectList(amsAcceptance);
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", "100");
		map.put("name", "单位工程及案卷");
		map.put("isParent", true);
		mapList.add(map);
		for (int i = 0; i < amsAcceptance.getAmsUnitProInfoList().size(); i++) {
			map = Maps.newHashMap();
			AmsUnitProInfo   amsUnitProInfo  = amsAcceptance.getAmsUnitProInfoList().get(i);
					map.put("id", amsUnitProInfo.getId());
					map.put("pId", "100");
					map.put("name", amsUnitProInfo.getUnitProjectName());
					mapList.add(map);
		}
		if (amsAcceptance.getAmsArchivesInfoList().size()>0) {
			map = Maps.newHashMap();
			map.put("id", "-1");
			map.put("pId", "100");
			map.put("name", "公用卷");
			mapList.add(map);
		}
		if (amsAcceptance.getAmsConstructDesList().size()>0) {
			map = Maps.newHashMap();
			map.put("id", "CON");
			map.put("pId", "100");
			map.put("name", "建设工程规划");
			mapList.add(map);
		}
		if (amsAcceptance.getAmsLandDesList().size()>0) {
			map = Maps.newHashMap();
			map.put("id", "LAN");
			map.put("pId", "100");
			map.put("name", "建设工程用地");
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * 查询责任主体信息及预验收列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("ams:amsAcceptance:view")
	@RequestMapping(value = { "allList" })
	public String allList(AmsAcceptance amsAcceptance,HttpServletRequest request, HttpServletResponse response,Model model) {
		AmsUnitDetailinfo amsUnitDetailinfo = amsAcceptanceService.getAmsUnitDetailinfo();
		amsAcceptance.setRole(UserUtils.getUser().getRoleList().get(0));
		/*Page<AmsAcceptance> page = amsAcceptanceService.findPage(new Page<AmsAcceptance>(request, response),amsAcceptance);
		model.addAttribute("page", page);*/
		model.addAttribute("amsUnitDetailinfo", amsUnitDetailinfo);
		return "modules/ams/amsAcceptanceAllList";
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
		amsArchivesInfo.setProjectId(projectId);
		amsArchivesInfo.setAcceptanceId(acceptanceId);
		page = amsArchivesInfoService.findProArchivesPage(new Page<AmsArchivesInfo>(request, response),amsArchivesInfo);
		model.addAttribute("page", page);
		model.addAttribute("unitProjectId", unitProjectId);
		return "modules/ams/amsAcceptanceArchivesList";
	}
	/**
	 * 案卷列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "lanDes" })
	public String lanDes(@RequestParam(required=false) String projectId,HttpServletRequest request, HttpServletResponse response,Model model) {
		AmsLandDes amsLandDes=new AmsLandDes();
		Page<AmsLandDes> page;
		amsLandDes.setProjectId(projectId);
		page = amsLandDesService.findPage(new Page<AmsLandDes>(request, response),amsLandDes);
		model.addAttribute("page", page);
		return "modules/ams/amsAcceptanceLanList";
	}
	/**
	 * 案卷列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "construtDes" })
	public String construtDes(@RequestParam(required=false) String projectId,HttpServletRequest request, HttpServletResponse response,Model model) {
		AmsConstructDes amsConstructDes=new AmsConstructDes();
		Page<AmsConstructDes> page;
		amsConstructDes.setProjectId(projectId);
		page = amsConstructDesService.findPage(new Page<AmsConstructDes>(request, response),amsConstructDes);
		model.addAttribute("page", page);
		return "modules/ams/amsAcceptanceConList";
	}
	/**
	 * 导出报告
	 * @param amsAcceptance
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(AmsAcceptance amsAcceptance, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "报告数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
    			AmsPreRpt amsPreRpt=new AmsPreRpt();
    			amsPreRpt.setTransferId(amsAcceptance.getId());
    			List<AmsPreRpt>amsPreRptList = amsAcceptanceService.amsPreRptList(amsPreRpt); 
    		new ExportExcel("报告数据", AmsPreRpt.class).setDataList(amsPreRptList).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}
		return "";
    }
    /**
     * 二维码扫描查看文件信息
     * @param amsAcceptance
     * @param amsAcceptanceId  预验收id
     * @param fileId  二维码扫描文件id
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = { "getScanFile" })
    public String getScanFile(AmsAcceptance amsAcceptance,String amsAcceptanceId,String fileId,HttpServletRequest request, HttpServletResponse response,Model model,RedirectAttributes redirectAttributes){
    	String fId = fileId.substring(0, 32);
    	if(EntityUtils.isBlank(fId)){
    		addMessage(model, "请扫描纸质表格上方二维码！");
    		return form(amsAcceptance,model);
    	}
    	AmsFileInfo amsFileInfo = amsFileInfoService.get(fId);
    	if(EntityUtils.isEmpty(amsFileInfo)){
    		addMessage(model, "表格不存在！");
    		return form(amsAcceptance,model);
    	}
    	List<AmsArchivesInfo> list = amsArchivesInfoService.findArchivesByFileIdAcceptId(amsAcceptanceId, fId);
    	if(list == null){
    		addMessage(model, "表格不存在！");
    		return form(amsAcceptance,model);
    	}
    	amsFileInfo.setFilePath(FTPPointUtil.getOnlineUrl() + amsFileInfo.getFilePath());
		model.addAttribute("amsFileInfo", amsFileInfo);
		model.addAttribute("list", list);
    	return "modules/ams/scanFileView";
    }
	
}