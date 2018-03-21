/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ucams.common.config.Global;
import com.ucams.common.persistence.Page;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.FTPPointUtil;
import com.ucams.common.utils.FTPPointUtil.UploadStatus;
import com.ucams.common.utils.IdGen;
import com.ucams.common.utils.PropertiesLoader;
import com.ucams.common.utils.RandomUtils;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.utils.UcamsTools;
import com.ucams.common.web.BaseController;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.entity.AmsProjectInfo;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.service.AmsAcceptanceService;
import com.ucams.modules.ams.service.AmsArchivesInfoService;
import com.ucams.modules.ams.service.AmsFileInfoService;
import com.ucams.modules.ams.service.AmsGuidanceService;
import com.ucams.modules.ams.service.AmsProjectInfoService;
import com.ucams.modules.ams.service.AmsUnitDetailinfoService;
import com.ucams.modules.sys.entity.Area;
import com.ucams.modules.sys.entity.Menu;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.entity.SysBaseInfo;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.service.AreaService;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.service.SysBaseInfoService;
import com.ucams.modules.sys.service.SystemService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 工程项目管理Controller
 * 
 * @author ws
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsProjectInfo")
public class AmsProjectInfoController extends BaseController {
	
	
	@Autowired
	private AmsAcceptanceService amsAcceptanceService;
	@Autowired
	private AmsArchivesInfoService amsArchivesInfoService;
	@Autowired
	private AmsGuidanceService amsGuidanceService;
	@Autowired
	private AmsProjectInfoService amsProjectInfoService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private AmsUnitDetailinfoService amsUnitDetailinfoService;
	@Autowired
	private SysBaseInfoService sysBaseInfoService;
	@Autowired
	private AmsFileInfoService amsFileInfoService;
	
	
	private static PropertiesLoader loader = new PropertiesLoader("ucams.properties");

	@ModelAttribute
	public AmsProjectInfo get(@RequestParam(required = false) String id) {
		AmsProjectInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = amsProjectInfoService.get(id);
		}
		if (entity == null) {
			entity = new AmsProjectInfo();
		}
		return entity;
	}

	@RequestMapping(value = { "amsAcceptanceProDetail"})
	public String amsAcceptanceProDetail(AmsProjectInfo amsProjectInfo, Model model) {
		amsProjectInfo.setApprovalUrl(FTPPointUtil.getOnlineUrl() + amsProjectInfo.getApprovalUrl());
		amsProjectInfo.setPlanningLicenseUrl(FTPPointUtil.getOnlineUrl() +amsProjectInfo.getPlanningLicenseUrl());
		amsProjectInfo.setLandPermitUrl(FTPPointUtil.getOnlineUrl() + amsProjectInfo.getLandPermitUrl());
		amsProjectInfo.setLandLicenseUrl(FTPPointUtil.getOnlineUrl() + amsProjectInfo.getLandLicenseUrl());
		model.addAttribute("amsProjectInfo", amsProjectInfo);
		return "modules/ams/amsAcceptanceProDetail";
	}
	@RequiresPermissions("ams:amsProjectInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(AmsProjectInfo amsProjectInfo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String jsUnitId = amsProjectInfoService.getJsUnitId();
		amsProjectInfo.setConstructionId(jsUnitId);
		Page<AmsProjectInfo> page = amsProjectInfoService.findPage(
				new Page<AmsProjectInfo>(request, response), amsProjectInfo);
		model.addAttribute("jsUnitId", jsUnitId);
		model.addAttribute("page", page);
		return "modules/ams/amsProjectInfoList";
	}

	/**
	 * 项目审批流程控制
	 * 
	 * @param amsProjectInfo
	 * @param model
	 * @return
	 * @author gyl
	 */
	@RequiresPermissions("ams:amsProjectInfo:view")
	@RequestMapping(value = "form")
	public String form(AmsProjectInfo amsProjectInfo, Model model) {
		String view = "amsProjectInfoForm";
		if (StringUtils.isNotBlank(amsProjectInfo.getId())) {
			// 环节编号获取
			String taskDefKey = amsProjectInfo.getAct().getTaskDefKey();
			// 环节是否审批
			if ("approve".equals(taskDefKey)) {
				// 如果审批去审批页面
				view = "amsProjectInfoApprove";
			} else if ("edit".equals(taskDefKey)) {// 判断是否到务指导环节
				view = "amsProjectInfoForm";
			} 
			if (amsProjectInfo.getAct().isFinishTask()) {// 流程结束
				view = "amsProjectInfoView";
			}
		} else {
			// 设置建设英文名称
			// amsProjectInfo.setEnname(RandomUtils.getStringRandom(8));
			view = "amsProjectInfoForm";
		}
		//构建文件预览地址
		amsProjectInfo.setApprovalUrl(FTPPointUtil.getOnlineUrl() + amsProjectInfo.getApprovalUrl());
		amsProjectInfo.setPlanningLicenseUrl(FTPPointUtil.getOnlineUrl() +amsProjectInfo.getPlanningLicenseUrl());
		amsProjectInfo.setLandPermitUrl(FTPPointUtil.getOnlineUrl() + amsProjectInfo.getLandPermitUrl());
		amsProjectInfo.setLandLicenseUrl(FTPPointUtil.getOnlineUrl() + amsProjectInfo.getLandLicenseUrl());
		model.addAttribute("amsProjectInfo", amsProjectInfo);
		return "modules/ams/" + view;
		/*
		 * model.addAttribute("amsProjectInfo", amsProjectInfo); return
		 * "modules/ams/amsProjectInfoForm";
		 */
	}
	
	/**
	 * 项目信息修改
	 * @param amsProjectInfo
	 * @param model
	 * @return
	 * @author gyl
	 */
	@RequiresPermissions("ams:amsProjectInfo:view")
	@RequestMapping(value = "update")
	public String update(AmsProjectInfo amsProjectInfo, Model model) {
		amsProjectInfo.setAmsDesExtendList(amsProjectInfoService.findExtendDataList(amsProjectInfo));
		//构建文件预览地址
		amsProjectInfo.setApprovalUrl(FTPPointUtil.getOnlineUrl() + amsProjectInfo.getApprovalUrl());
		amsProjectInfo.setPlanningLicenseUrl(FTPPointUtil.getOnlineUrl() +amsProjectInfo.getPlanningLicenseUrl());
		amsProjectInfo.setLandPermitUrl(FTPPointUtil.getOnlineUrl() + amsProjectInfo.getLandPermitUrl());
		amsProjectInfo.setLandLicenseUrl(FTPPointUtil.getOnlineUrl() + amsProjectInfo.getLandLicenseUrl());
		model.addAttribute("amsProjectInfo", amsProjectInfo);
		return "modules/ams/amsProjectInfoForm";
	}

	/**
	 * 验证项目名称是否重复
	 * 
	 * @param oldProjectName
	 * @param projectName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkProjectName")
	public String checkProjectName(String oldProjectName, String projectName) {
		AmsProjectInfo amsproj = new AmsProjectInfo();
		amsproj.setProjectName(projectName);
		AmsProjectInfo amsProjectInfo = amsProjectInfoService.getByProjectName(amsproj);
		if (projectName != null && projectName.equals(oldProjectName)) {
			return "true";
		} else if (projectName != null && amsProjectInfo == null) {
			return "true";
		}
		return "false";
	}
	
	/**
	 * 验证角色名是否有效
	 * 
	 * @param oldName
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName1(String oldName, String name) {
		if (name != null && name.equals(oldName)) {
			return "true";
		} else if (name != null && systemService.getRoleByName(name) == null) {
			return "true";
		}
		return "false";
	}
	/**
	 * 验证统一社会信用代码名是否重复
	 * 
	 * @param oldName
	 * @param codename
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkUnitCreditCode")
	public String checkUnitCreditCode(String oldName, String codeName) {
		if (codeName != null && codeName.equals(oldName)) {
			return "true";
		} else if (codeName != null && systemService.getAmsUnitDetailinfByoUnitCreditCode(codeName) == 0) {
			return "true";
		}
		return "false";
	}

	/**
	 * 验证角色英文名是否有效
	 * 
	 * @param oldName
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkEnname")
	public String checkEnname(String oldEnname, String enname) {
		if (enname != null && enname.equals(oldEnname)) {
			return "true";
		} else if (enname != null
				&& systemService.getRoleByEnname(enname) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 登录名重复验证
	 * 
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName != null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName != null
				&& systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	
	/*//默认项目地址
	@ResponseBody
	@RequestMapping(value = "getAddress")
	public String getAddress() {
		User u=UserUtils.getUser();
		String address="";
		if(u!=null&&u.getId()!=null&&!u.getId().equals(""))
			address=amsProjectInfoService.getAddress(u.getLoginName());				
		return  address;
	}*/
	
	/**
	 * 项目审批流程保存控制
	 * @param amsProjectInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @author gyl
	 */
	@RequestMapping(value = "saveProjectActivity")
	public String saveProjectActivity(AmsProjectInfo amsProjectInfo,
			Model model, RedirectAttributes redirectAttributes) {
		if ("approve".equals(amsProjectInfo.getAct().getTaskDefKey())) {
			if( "yes".equals(amsProjectInfo.getAct().getFlag())
			|| "no".equals(amsProjectInfo.getAct().getFlag())){
				}if (StringUtils.isBlank(amsProjectInfo.getBusinessMan())) {
					addMessage(model, "请填写业务指导员");
					return form(amsProjectInfo, model);
			} 
		}
		AmsProjectInfo projectInfo = amsProjectInfoService.get(amsProjectInfo.getId());
		amsProjectInfo.setIsfirst(projectInfo.getIsfirst());//是否首次报建
		amsProjectInfoService.saveProjectActivity(amsProjectInfo);
		return "redirect:" + Global.getAdminPath() + "/act/task/todo/";
	}
	@RequestMapping(value = "save")
	public String save(AmsProjectInfo amsProjectInfo, Model model,
			RedirectAttributes redirectAttributes, 
			MultipartHttpServletRequest request) throws IllegalStateException,
			IOException {
		if (!beanValidator(model, amsProjectInfo)) {
			return form(amsProjectInfo, model);
		}
		String jsUnitId = amsProjectInfoService.getJsUnitId();
		amsProjectInfo.setCheckStatus("0");
		amsProjectInfo.setIsonline("0");
		if (!"".equals(jsUnitId)) {
			//设置项目编号
			SysBaseInfo sysBaseInfo = new SysBaseInfo();
			sysBaseInfo = sysBaseInfoService.findSysBaseInfo();
			String PreProjectNO = sysBaseInfo.getPreProjectno();
			String pid = amsProjectInfo.getId();
			if (StringUtils.isBlank(pid)) {
				String pno = amsProjectInfoService.getProjectByNoNew()
						.getProjectNo();
				if (StringUtils.isBlank(pno)) {
					pno = "00001";
				} else {
					pno = String.valueOf(Long.parseLong(pno) + 1);
					pno = String.format("%04d",Integer.parseInt(pno) + 1);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				amsProjectInfo.setProjectNo(PreProjectNO
						+ sdf.format(amsProjectInfo.getStartDate()) + pno);
				
			}
			List<MultipartFile> files = request.getFiles("file");
			AmsProjectInfo oldamsProjectInfo = new AmsProjectInfo();
			oldamsProjectInfo = amsProjectInfoService.get(amsProjectInfo.getId());
			if(EntityUtils.isNotEmpty(oldamsProjectInfo)){
				amsProjectInfo.setApprovalUrl(oldamsProjectInfo.getApprovalUrl());
				amsProjectInfo.setPlanningLicenseUrl(oldamsProjectInfo.getPlanningLicenseUrl());
				amsProjectInfo.setLandLicenseUrl(oldamsProjectInfo.getLandLicenseUrl());
				amsProjectInfo.setLandPermitUrl(oldamsProjectInfo.getLandPermitUrl());
			}
			
			if (files.size() > 0) {
				Map<String, String> imgs = new HashMap<String, String>();
				// 如果上传文件 链接FTP服务 -- 在线业务
				FTPPointUtil ftp = null;
				File f = null;
				String filePath = null;
				for (int i = 0; i < files.size(); i++) {
					MultipartFile imgFile = files.get(i);
					try {
						//判断文件字节是否为0 (文件是否真实上传)
						if (imgFile.getBytes().length <= 0) {
							break;
							//throw new FileNotFoundException("工程报建-文件上传-未找到上传文件");
						}
						//组织文件存储路径
						filePath = "projectFirst/" + amsProjectInfo.getProjectNo() + "/" + UcamsTools.createNewFileName(imgFile);
						//用缓冲区来实现这个转换即使用java 创建的临时文件
						f=File.createTempFile("savetmp"+ i , null);
						ftp = new FTPPointUtil("tadmin", "admin");
						//链接FTP服务
						ftp.connect();
						files.get(i).transferTo(f);
						//FTP服务上传
						UploadStatus status =ftp.upload(f, filePath);
						//存储文件路径准备入库
						imgs.put("img" + i, filePath);
						//删除缓冲区的临时文件
						f.deleteOnExit();
						//断开FTP服务
						ftp.disconnect();
						//判断上传结果,不成功
						if(!FTPPointUtil.UploadStatus.Upload_New_File_Success.equals(status)){
							throw new IOException("工程报建-文件上传-文件上传失败!");
						}
					} catch (Exception e) {
						ftp.disconnect();  //异常断开FTP服务
						e.printStackTrace();
						throw new IOException("工程报建-文件上传-文件上传失败!");
					}
				}
				amsProjectInfo.setApprovalUrl(EntityUtils.isEmpty(imgs.get("img0"))?amsProjectInfo.getApprovalUrl():imgs.get("img0"));
				amsProjectInfo.setPlanningLicenseUrl(EntityUtils.isEmpty(imgs.get("img1"))?amsProjectInfo.getPlanningLicenseUrl():imgs.get("img1"));
				amsProjectInfo.setLandLicenseUrl(EntityUtils.isEmpty(imgs.get("img2"))?amsProjectInfo.getLandLicenseUrl():imgs.get("img2"));
				amsProjectInfo.setLandPermitUrl(EntityUtils.isEmpty(imgs.get("img3"))?amsProjectInfo.getLandPermitUrl():imgs.get("img3"));
				amsProjectInfo.setConstructionId(jsUnitId);
			}
			// 设置扩展信息
			amsProjectInfo.setDescriptionJson(amsProjectInfoService.getDescriptionJson(amsProjectInfo).getDescriptionJson());
			amsProjectInfo.setIsfirst("2");// 非首次报建
			amsProjectInfoService.save(amsProjectInfo);
			// 保存或修改项目同时维护机构
			Office office = new Office();
			// 自动获取排序号
			office.setId(amsProjectInfo.getId());
			office.setParent(UserUtils.getUser().getOffice().getParent());
			office.setParentIds(UserUtils.getUser().getOffice().getParentIds()
					+ UserUtils.getUser().getOffice().getParent().getId() + ",");
			office.setName(amsProjectInfo.getProjectName());
			office.setArea(UserUtils.getUser().getOffice().getArea());
			office.setType("1");// 项目类型默认值
			office.setGrade("3");// 项目默认级别
			office.setUseable(Global.YES);// 默认可用
			office.setUpdateBy(UserUtils.getUser());
			office.setUpdateDate(new Date());
			office.setCreateBy(UserUtils.getUser());
			office.setCreateDate(new Date());
			office.setDelFlag("9"); // 未审核标记 用于office的tree结构筛选
			office.setCode(amsProjectInfoService.getOfficCode(0));
			if (StringUtils.isBlank(pid)) {
				//新报建项目在机构表中添加对应节点
				officeService.insert(office);
				//添加新报建项目与角色之间的关系
				officeService.insertSysRoleOffice(amsProjectInfo.getCurrentUser().getRoleList().get(0).getId(),office.getId());
			} else {
				officeService.update(office);
			}
		   addMessage(redirectAttributes, "操作成功");
		} else {
			addMessage(redirectAttributes, "非建设单位不可进行添加项目!");
		}
		return "redirect:" + Global.getAdminPath() + "/ams/amsProjectInfo/?repage";
	}

	@RequiresPermissions("ams:amsProjectInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsProjectInfo amsProjectInfo,RedirectAttributes redirectAttributes) {
		//根据接收项目工程的id到业务指导表中做查询
		String amsGuidanceId = amsGuidanceService.getAmsGuidanceId(amsProjectInfo.getId());
		//根据接受项目工程的id到组卷表中查询
		String amsArchivesInfoId = amsArchivesInfoService.getAmsArchivesInfoId(amsProjectInfo.getId());
		//根据接受项目工程的id到验收表预中查询
		//String amsAcceptanceId = amsAcceptanceService.getAmsAcceptanceId(amsProjectInfo.getId());
		//根据接受项目工程的id到文件管理表中查询
		AmsFileInfo amsFileInfoId = amsFileInfoService.getAmsFileInfoId(amsProjectInfo.getId());	
				//sys_role( office_id, 
		//Role  r=systemService.getByOfficeId(amsProjectInfo.getId());
		if(amsGuidanceId != null){
			addMessage(redirectAttributes, "工程项目管理下业务指导已存在无法删除");
			return "redirect:" + Global.getAdminPath()+ "/ams/amsProjectInfo/?repage";

		}else if(amsArchivesInfoId != null){
			addMessage(redirectAttributes, "工程项目管理下组卷已存在无法删除");
			return "redirect:" + Global.getAdminPath()+ "/ams/amsProjectInfo/?repage";

		}else if(amsFileInfoId != null){
			addMessage(redirectAttributes, "工程项目管理下文件已存在无法删除");
			return "redirect:" + Global.getAdminPath()+ "/ams/amsProjectInfo/?repage";

		}else{
			amsProjectInfoService.delete(amsProjectInfo);
			// 删除项目同时删除机构
			officeService.delete(officeService.get(amsProjectInfo.getId()));
			addMessage(redirectAttributes, "删除工程项目成功");
			return "redirect:" + Global.getAdminPath()+ "/ams/amsProjectInfo/?repage";

		}
	}


	/**
	 * 工程报建页面初始化
	 * 
	 * @param amsProjectInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "first")
	public String first(AmsProjectInfo amsProjectInfo, Model model) {
		model.addAttribute("amsProjectInfo", amsProjectInfo);
		return "modules/ams/amsProjectInfoFirst";
	}

	/**
	 * 工程报建信息保存
	 * 
	 * @param amsProjectInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "firstSave")
	public ModelAndView firstSave(String unitCreditCode,AmsProjectInfo amsProjectInfo, Model model,
			RedirectAttributes redirectAttributes,
			MultipartHttpServletRequest request) throws IOException {
		if (!beanValidator(model, amsProjectInfo)) {
			// return form(amsProjectInfo, model);
		}
		/******************** 涉及对象声明 ************************/
		AmsUnitDetailinfo amsUnitDetailInfo = new AmsUnitDetailinfo();// 责任主体明细
		User user = new User();// 用户
		Role role = new Role();// 角色
		Office officeJs = new Office();// 建设单位机构
		Office officeBm = new Office();// 建设单位默认部门机构
		Office office = new Office();// 建设单位默认项目机构
		// 角色与用户sys_user_role
		// 角色与菜单sys_role_menu
		// 角色与机构sys_role_office
		/********************* 对象赋值 **************************/
		// 用户名,角色赋值转移
		amsProjectInfo.getRole().setName(amsProjectInfo.getName());
		amsProjectInfo.getRole().setEnname(amsProjectInfo.getEnname());
		amsProjectInfo.getUser().setLoginName(amsProjectInfo.getLoginName());
		user = amsProjectInfo.getUser();// 报建页面用户信息
		amsUnitDetailInfo = amsProjectInfo.getAmsUnitDetailInfo();// 报建页面责任主体明细
		role = amsProjectInfo.getRole();// 报建页面角色信息
		try {
			// 建设单位机构参数设置
			officeJs.setId(IdGen.uuid());
			Office poffice = officeService.get("99");// 获取上级节点
			officeJs.setParent(poffice);
			officeJs.setParentIds("0,1,99,");
			officeJs.setName(amsProjectInfo.getRole().getName());
			officeJs.setArea(poffice.getArea());
			officeJs.setType("1");// 建设单位默认类型
			officeJs.setGrade("2");// 建设单位默认级别
			officeJs.setUseable(Global.YES);// 默认可用
			officeJs.preDateUser();
			officeJs.setDelFlag("0");// 未审核标记 用于office的tree结构筛选
			officeJs.setRemarks("建设单位");
			officeJs.setCode(amsProjectInfoService.getOfficCode(0));
			officeService.insert(officeJs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// 机构默认部门参数设置
			officeBm.setId(IdGen.uuid());
			officeBm.setParent(officeJs);
			officeBm.setParentIds(officeJs.getParentIds() + officeJs.getId()
					+ ",");
			officeBm.setType("2");// 默认机构下部门类型
			officeBm.setRemarks("建设单位部门");
			officeBm.setName(officeJs.getName());
			officeBm.setArea(officeJs.getArea());
			officeBm.setGrade("2");// 默认机构下部门级别
			officeBm.setUseable(Global.YES);// 默认可用
			officeBm.preDateUser();
			officeBm.setDelFlag("0");// 默认未删除
			officeBm.setCode(amsProjectInfoService.getOfficCode(1));
			officeService.insert(officeBm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String pid = amsProjectInfo.getId();
		if (StringUtils.isBlank(pid)) {
			// 项目编号设置
			AmsProjectInfo amsProjectInfoNo = amsProjectInfoService.getProjectByNoNew();
			SysBaseInfo sysBaseInfo = new SysBaseInfo();
			sysBaseInfo = sysBaseInfoService.findSysBaseInfo();
			String PreProjectNO = sysBaseInfo.getPreProjectno();
			String pno = "";
			if (EntityUtils.isNotEmpty(amsProjectInfoNo)
					&& StringUtils.isNotBlank(amsProjectInfoNo.getProjectNo())) {
				pno = String.format("%04d",Integer.parseInt(amsProjectInfoNo
						.getProjectNo()) + 1);
				
			} else {
				pno = "00001";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			amsProjectInfo.setProjectNo(PreProjectNO
					+ sdf.format(amsProjectInfo.getStartDate()) + pno);				
		}
		
		try {
			List<MultipartFile> files = request.getFiles("file");
			// 如果上传文件 
			FTPPointUtil ftp = null;
			if (files.size() > 0) {
				Map<String, String> imgs = new HashMap<String, String>();
				File f = null;
				String filePath = null;
				for (int i = 0; i < files.size(); i++) {
					MultipartFile imgFile = files.get(i);
					try {
						//判断文件字节是否为0 (文件是否真实上传)
						if (imgFile.getBytes().length <= 0) { 
							break;
							//throw new FileNotFoundException("首次报建-文件上传-未找到上传文件！");
						}
						//组织文件存储路径
						filePath = "projectFirst/" +  amsProjectInfo.getProjectNo() + "/" + UcamsTools.createNewFileName(imgFile);
						//用缓冲区来实现这个转换即使用java 创建的临时文件
						f=File.createTempFile("firstSavetmp"+ i , null);
						//链接FTP服务 -- 在线业务
						ftp = new FTPPointUtil("tadmin", "admin");
						//链接FTP服务
						ftp.connect();
						files.get(i).transferTo(f);
						//FTP服务上传
						UploadStatus status = ftp.upload(f, filePath);
						//存储文件路径准备入库
						imgs.put("img" + i, filePath);
						//删除缓冲区的临时文件
						f.deleteOnExit();
						//断开FTP服务
						ftp.disconnect();
						//判断上传结果,不成功
						if(!FTPPointUtil.UploadStatus.Upload_New_File_Success.equals(status)){
							throw new IOException("首次报建-文件上传-文件上传失败!");
						}
					} catch (Exception e) {
						ftp.disconnect();  //异常断开FTP服务
						e.printStackTrace();
						throw new IOException("首次报建-文件上传失败!");
					}
				}
				amsProjectInfo.setApprovalUrl(imgs.get("img0"));
				amsProjectInfo.setPlanningLicenseUrl(imgs.get("img1"));
				amsProjectInfo.setLandLicenseUrl(imgs.get("img2"));
				amsProjectInfo.setLandPermitUrl(imgs.get("img3"));
			}
			amsProjectInfo.setCheckStatus("0");// 默认未审核
			amsProjectInfo.preDateUser();
			amsProjectInfo.setIsfirst("1");// 首次报建
			amsProjectInfo.setIsonline("0");
			amsProjectInfoService.save(amsProjectInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("首次报建，数据保存失败!");
		}

		try {
			// 保存项目同时维护机构
			office.setId(amsProjectInfo.getId());
			office.setParent(officeJs);
			office.setParentIds(officeJs.getParentIds() + officeJs.getId()
					+ ",");
			office.setName(amsProjectInfo.getProjectName());
			office.setArea(officeJs.getArea());
			office.setType("1");// 项目机构类型
			office.setGrade("3");// 项目机构级别
			office.setUseable(Global.YES);// 可用
			office.preDateUser();
			office.setDelFlag("9");// 未删除	·
			office.setRemarks("项目");
			office.setCode(amsProjectInfoService.getOfficCode(0));
			officeService.insert(office);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// 角色参数设置
			role.setRoleType("security-role");// 默认建设单位
			role.setUseable("1");// 默认可用
			role.setDataScope("9");// 数据来源
			role.setSysData("0");//设置管理员可修改角色的操作
			// 8位随机英文名称
			role.setEnname(RandomUtils.getStringRandom(8));
			role.setOffice(officeBm);
			List<Office> officeList = new ArrayList<Office>();
			officeList.add(office);
			role.setOfficeList(officeList);
			List<Menu> menuList = new ArrayList<Menu>();
			// 默认项目查看权限
			String menupid = "0,1,62,63,73,3bebe44473f143b6b99d42b0c3ef5e93,1f1b1baa45f64f6787105f2ae29d8e71,8342dbb1c3bd41609cba91005fd05313";

			String mpids[] = menupid.split(",");
			for (int i = 0; i < mpids.length; i++) {
				menuList.add(new Menu(mpids[i]));
			}
			role.setMenuList(menuList);
			role.preDateUser();
			// 保存角色同时维护:角色与菜单关系表,角色与机构关系表
			systemService.saveRole(role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// 责任主体明细信息参数设置
			amsUnitDetailInfo.setId(IdGen.uuid());
			amsUnitDetailInfo.setRole(role);
			amsUnitDetailInfo.preDateUser();
			amsUnitDetailInfo.setUnitCreditCode(unitCreditCode);
			amsUnitDetailinfoService.insert(amsUnitDetailInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// 用户参数设置
			user.setCompany(officeJs);// 设置建设单位
			user.setOffice(officeBm);// 设置部门
			user.setName(officeBm.getName());
			user.setPassword(SystemService.entryptPassword(user
					.getNewPassword()));
			user.setNo("10001");
			user.setNo(officeBm.getName());
			user.setLoginFlag("1");
			user.setRemarks("公司管理员");
			List<Role> roleList = new ArrayList<Role>();
			roleList.add(role);
			user.setRoleList(roleList);
			user.preDateUser();
			// 保存用户同时维护:用户与角色关系表
			systemService.saveUser(user);
			// 设置建设单位角色id
			amsProjectInfo.setConstructionId(role.getId());
			amsProjectInfoService.save(amsProjectInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		addMessage(redirectAttributes, "工程报建成功,请等待系统审批!");
		Map<String, String> loginMap = new HashMap<String, String>();
		loginMap.put("username", user.getLoginName());
		loginMap.put("password", user.getPassword());
		return new ModelAndView(new RedirectView(Global.getAdminPath()
				+ "/login"), loginMap);
	}

	/**
	 * 工程报建页面区域数据初始化
	 * 
	 * @param extId
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "areaTreeData")
	public List<Map<String, Object>> areaTreeData(
			@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Area> list = areaService.findAll();
		for (int i = 0; i < list.size(); i++) {
			Area e = list.get(i);
			if (StringUtils.isBlank(extId)
					|| (extId != null && !extId.equals(e.getId()) && e
							.getParentIds().indexOf("," + extId + ",") == -1)) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

	/**
	 * 条款内容页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "clause")
	public String clause(Model model) {
		return "modules/ams/amsProjectInfoClause";
	}

	/**
	 * 附件查看内容页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "enc")
	public String enc(Model model, String imgpath) {
		model.addAttribute("imgpath", imgpath);
		return "modules/ams/amsProjectInfoEnc";
	}
	
	/**
	 * 项目信息拓展页面初始化
	 * @param amsUnitProInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "majorForm")
	public String majorForm(AmsProjectInfo amsProjectInfo,String status, Model model) {
		//根据单位工程类型获取初始化配置信息
		amsProjectInfo.setAmsDesExtendList(amsProjectInfoService.findExtendDataList(amsProjectInfo)); 
		model.addAttribute("status", status);
		model.addAttribute("amsProjectInfo", amsProjectInfo);
		return "modules/ams/amsProjectExtendForm";
	}
	/**
	 * 项目信息拓展保存
	 * @param amsUnitProInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */

	@RequestMapping(value = "amsProjectExtendSave")
	public String amsProjectExtendSave(AmsProjectInfo amsProjectInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsProjectInfo)){
			return form(amsProjectInfo,model);
		}
		amsProjectInfoService.getDescriptionJson(amsProjectInfo);
		addMessage(redirectAttributes, "项目信息拓展保存成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsProjectInfo/?repage&projectId="+amsProjectInfo.getProcInsId();
	}
	
}