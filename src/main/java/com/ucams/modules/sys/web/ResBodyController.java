/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.sys.web;

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
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.IdGen;
import com.ucams.common.utils.RandomUtils;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.web.BaseController;
import com.ucams.modules.ams.entity.AmsUnitDetailinfo;
import com.ucams.modules.ams.service.AmsUnitDetailinfoService;
import com.ucams.modules.sys.dao.RoleDao;
import com.ucams.modules.sys.entity.Office;
import com.ucams.modules.sys.entity.Role;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.service.ResBodyService;
import com.ucams.modules.sys.service.SystemService;
import com.ucams.modules.sys.utils.UserUtils;

/**
 * 责任主体Controller
 * @author sunjx
 * @version 2017-5-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/resBody")
public class ResBodyController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ResBodyService resBodyService;
	@Autowired
	private AmsUnitDetailinfoService amsUnitDetailinfoService;
	
	@RequiresPermissions("sys:resBody:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
		return "modules/sys/resBodyIndex";
	}
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}
	
	@RequiresPermissions("sys:resBody:view")
	@RequestMapping(value = {"list"})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		User u = UserUtils.getUser();	
		//如果没有选择责任主体，则用户列表不显示数据
		Page<User> page = null;
		Role role = null;
		//默认显示当前登录账号所属单位的责任主体信息    gyl  2017-10-19
		if(EntityUtils.isEmpty(user.getRole())){
			user.setRole(u.getRoleList().get(0));
		}

		page = systemService.findUser(new Page<User>(request, response), user);
		role = systemService.getRole(user.getRole().getId());

        model.addAttribute("page", page);
        model.addAttribute("role", role);
        model.addAttribute("officeList", officeService.onlyUnitPro(u.getOffice().getParent()));
		return "modules/sys/resBodyList";
	}
	
	@RequiresPermissions("sys:resBodyUser:view")
	@RequestMapping(value = "form_user")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		//此处设置 用户只分配一个角色
		/*model.addAttribute("allRoles", systemService.findAllRole().get(0));*/
		return "modules/sys/resBodyForm_User";
	}
	
	@RequiresPermissions("sys:resBodyRole:view")
	@RequestMapping(value = "form_role")
	public String form(Role role, Model model) {
		Role r = new Role();
		User u = UserUtils.getUser();	
		if(EntityUtils.isNotEmpty(role.getId())){
			r =  systemService.getRole(role.getId());
			if (r.getOffice()==null){
				r.setOffice(UserUtils.getUser().getOffice());
			}
		}
		model.addAttribute("role", r);
		 model.addAttribute("officeList", officeService.onlyUnitPro(u.getOffice().getParent()));
		return "modules/sys/resBodyForm_Role";
	}

	@RequiresPermissions("sys:resBodyRole:edit")
	@RequestMapping(value = "save_role")
	public String save(Role role, Model model, RedirectAttributes redirectAttributes) {
		if(!UserUtils.getUser().isAdmin()&&Global.YES.equals(role.getSysData())){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + adminPath + "/sys/resBody/list";
		}
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/resBody/list";
		}
		if (!beanValidator(model, role)){
			return form(role, model);
		}
		//获取模版角色
		Role roleMenu=systemService.getRole("200");
		role.setMenuList(roleMenu.getMenuList());
		resBodyService.saveRole(role);
		addMessage(redirectAttributes, "保存单位'" + role.getName() + "'成功，请刷新右侧列表");
		//重定向到编辑单位信息界面
		return "redirect:" + adminPath + "/sys/resBody/list?role.id=" + role.getId();
	}
	
	@RequiresPermissions("sys:resBodyRole:edit")
	@RequestMapping(value = "delete_role")
	public String delete(Role role, RedirectAttributes redirectAttributes) {
		if(!UserUtils.getUser().isAdmin() && role.getSysData().equals(Global.YES)){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + adminPath + "/sys/resBody/list";
		}
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/resBody/list";
		}
//		if (Role.isAdmin(id)){
//			addMessage(redirectAttributes, "删除角色失败, 不允许内置角色或编号空");
////		}else if (UserUtils.getUser().getRoleIdList().contains(id)){
////			addMessage(redirectAttributes, "删除角色失败, 不能删除当前用户所在角色");
//		}else{
			systemService.deleteRole(role);
			addMessage(redirectAttributes, "删除单位成功,请刷新右侧列表");
//		}
		return "redirect:" + adminPath + "/sys/resBody/list";
	}
	
	@RequiresPermissions("sys:resBodyUser:edit")
	@RequestMapping(value = "save_user")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		User u = UserUtils.getUser();
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		//添加用户角色
		user.setRoleList(user.getRoleList());
		//新增用户与登录用户 所属单位/部门一致
		if(EntityUtils.isEmpty(user.getCompany())){
			user.setCompany(u.getCompany());
		}
		if(EntityUtils.isEmpty(user.getOffice())){
			user.setOffice(u.getOffice());
		}
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/resBody/list";
	}
	
	@RequiresPermissions("sys:resBodyUser:edit")
	@RequestMapping(value = "reuseInfo")
	@ResponseBody
	public Object reuseInfo(String unitDetaInfoId,String resBodyType, RedirectAttributes redirectAttributes) {
		//获取角色
		AmsUnitDetailinfo unitDetaInfo = amsUnitDetailinfoService.get(unitDetaInfoId);
		//获取责任主体信息
		Role role = systemService.getRole(unitDetaInfo.getRole().getId());
		//复制角色
		role.setId(null);
		role.setRoleType(resBodyType); //重置角色业务类型
		role.setEnname(RandomUtils.getStringRandom(8)); 
		role.setOffice(UserUtils.getUser().getOffice()); //将新角色的归属机构改为当前用户的归属机构
		role.setMenuList(systemService.getRole("200").getMenuList()); //获取模版菜单权限
		//复制责任主体信息
		unitDetaInfo.setId(null);
		unitDetaInfo.setRole(role);
		systemService.saveRole(role);
		if(EntityUtils.isNotEmpty(unitDetaInfo)){
			amsUnitDetailinfoService.save(unitDetaInfo);
			return renderSuccess("复用成功");
		}else{
			return renderSuccess("复用成功,责任主体缺少详细信息");
		}
	}
	
	@RequiresPermissions("sys:resBodyUser:view")
	@RequestMapping(value = "getReuseInfo")
	@ResponseBody
	public Object getReuseInfo(String roleId) {	
		//查询责任主体详细信息
		Role role = new Role();
		role.setId(roleId);
		return renderSuccess(amsUnitDetailinfoService.getByUnitId(new AmsUnitDetailinfo(role)));
	}
	
	@RequiresPermissions("sys:resBodyUser:edit")
	@RequestMapping(value = "delete_user")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/resBody/list";
		}
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		//重定向到选择的单位信息页面
		return "redirect:" + adminPath + "/sys/resBody/list?role.id=" + user.getRoleList().get(0).getId();
	}
	
	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param grade 显示业务分类
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//角色类型
		//监理单位：assignment、建设单位：security-role、施工单位：user
		Role role = new Role();
		role.setRoleType("assignment");
		List<Role> assignmentRoles = systemService.findRoleByType(role,false);
		role.setRoleType("security-role");
		List<Role> securityRoles = systemService.findRoleByType(role,false);
		role.setRoleType("user");
		List<Role> userRoles = systemService.findRoleByType(role,false);
		for(int i= -1; i<securityRoles.size(); i++){
			Map<String, Object> map = Maps.newHashMap();
			//设置父节点
			if(i == -1){
				map.put("id","100");
				map.put("name",  "建设单位");
				map.put("isParent", true);
				mapList.add(map);
			}else{
				map.put("id", securityRoles.get(i).getId());
				map.put("pId", "100");
				map.put("name", securityRoles.get(i).getName());
				mapList.add(map);
			}
			
		}
		for(int i=-1; i<assignmentRoles.size(); i++){
			Map<String, Object> map = Maps.newHashMap();
			//设置父节点
			if(i == -1){
				map.put("id","101");
				map.put("name",  "监理单位");
				map.put("isParent", true);
				mapList.add(map);
			}else{
				map.put("id", assignmentRoles.get(i).getId());
				map.put("pId", "101");
				map.put("name",  assignmentRoles.get(i).getName());
				mapList.add(map);
			}
			
		}
		for(int i=-1; i<userRoles.size(); i++){
			Map<String, Object> map = Maps.newHashMap();
			//设置父节点
			if(i == -1){
				map.put("id","102");
				map.put("name",  "施工单位");
				map.put("isParent", true);
				mapList.add(map);
			}else{
				map.put("id", userRoles.get(i).getId());
				map.put("pId", "102");
				map.put("name", userRoles.get(i).getName());
				mapList.add(map);
			}
			
		}
		return mapList;
	}
	
	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param grade 显示业务分类
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "resData")
	public List<Map<String, Object>> resData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//角色类型
		//监理单位：assignment、建设单位：security-role、施工单位：user
		Role role = new Role();
		role.setRoleType("assignment");
		List<Role> assignmentRoles = systemService.findRoleByType(role,true);
		role.setRoleType("user");
		List<Role> userRoles = systemService.findRoleByType(role,true);
		
		for(int i=-1; i<assignmentRoles.size(); i++){
			Map<String, Object> map = Maps.newHashMap();
			//设置父节点
			if(i == -1){
				map.put("id","101");
				map.put("name",  "监理单位");
				map.put("isParent", true);
				mapList.add(map);
			}else{
				map.put("id", assignmentRoles.get(i).getId());
				map.put("pId", "101");
				map.put("name",  assignmentRoles.get(i).getName());
				mapList.add(map);
			}
			
		}
		for(int i=-1; i<userRoles.size(); i++){
			Map<String, Object> map = Maps.newHashMap();
			//设置父节点
			if(i == -1){
				map.put("id","102");
				map.put("name",  "施工单位");
				map.put("isParent", true);
				mapList.add(map);
			}else{
				map.put("id", userRoles.get(i).getId());
				map.put("pId", "102");
				map.put("name", userRoles.get(i).getName());
				mapList.add(map);
			}
			
		}
		return mapList;
	}
	
	/**
	 * 验证角色名是否有效
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkName")
	public Object checkName(String oldName, String name) {
		//获取已存在的责任主体信息
		Role role = systemService.getRoleByName(name);
		AmsUnitDetailinfo amsUnitDetailinfo = amsUnitDetailinfoService.getByUnitId(new AmsUnitDetailinfo(role));
		if (name!=null && name.equals(oldName)) {
			return renderSuccess();
		} else if (name!=null &&  role == null) {
			return renderSuccess();
		}
		return renderError(amsUnitDetailinfo);
	}
	
	/**
	 * 验证角色英文名是否有效
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkEnname")
	public String checkEnname(String oldEnname, String enname) {
		if (enname!=null && enname.equals(oldEnname)) {
			return "true";
		} else if (enname!=null && systemService.getRoleByEnname(enname) == null) {
			return "true";
		}
		return "false";
	}
	
	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:resBodyUser:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	
	
}
