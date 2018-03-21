/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import com.ucams.modules.ams.entity.AmsUcArchiveMenu;
import com.ucams.modules.ams.service.AmsUcArchiveMenuService;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.service.SystemService;



/**
 * 城建档案菜单管理Controller
 * 2017年12月1日 下午1:32:59
 * @author Administrator
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsUcArchiveMenu")
public class AmsUcArchiveMenuController extends BaseController {

	@Autowired
	private AmsUcArchiveMenuService amsUcArchiveMenuService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public AmsUcArchiveMenu get(@RequestParam(required=false) String id) {
		AmsUcArchiveMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsUcArchiveMenuService.get(id);
		}
		if (entity == null){
			entity = new AmsUcArchiveMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsUcArchiveMenu:view")
	@RequestMapping(value = {"main"})
	public String index(Model model) {
		return "modules/ams/amsUcArchiveMenuMain";
	}
	
	@RequiresPermissions("ams:amsUcArchiveMenu:view")
	@RequestMapping(value = {"list"})
	public String list(AmsUcArchiveMenu amsUcArchiveMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AmsUcArchiveMenu> list = amsUcArchiveMenuService.findList(amsUcArchiveMenu); 
		model.addAttribute("list", list);
		return "modules/ams/amsUcArchiveMenuList";
	}

	@RequiresPermissions("ams:amsUcArchiveMenu:view")
	@RequestMapping(value = "form")
	public String form(AmsUcArchiveMenu amsUcArchiveMenu, Model model) {
		if (amsUcArchiveMenu.getParent()!=null && StringUtils.isNotBlank(amsUcArchiveMenu.getParent().getId())){
			amsUcArchiveMenu.setParent(amsUcArchiveMenuService.get(amsUcArchiveMenu.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(amsUcArchiveMenu.getId())){
				AmsUcArchiveMenu amsUcArchiveMenuChild = new AmsUcArchiveMenu();
				amsUcArchiveMenuChild.setParent(new AmsUcArchiveMenu(amsUcArchiveMenu.getParent().getId()));
				List<AmsUcArchiveMenu> list = amsUcArchiveMenuService.findList(amsUcArchiveMenu); 
				if (list.size() > 0){
					amsUcArchiveMenu.setSort(list.get(list.size()-1).getSort());
					if (amsUcArchiveMenu.getSort() != null){
						amsUcArchiveMenu.setSort(amsUcArchiveMenu.getSort() + 30);
					}
				}
			}
		}
		if (amsUcArchiveMenu.getSort() == null){
			amsUcArchiveMenu.setSort(30);
		}
		model.addAttribute("amsUcArchiveMenu", amsUcArchiveMenu);
		return "modules/ams/amsUcArchiveMenuForm";
	}

	@RequiresPermissions("ams:amsUcArchiveMenu:edit")
	@RequestMapping(value = "save")
	public String save(AmsUcArchiveMenu amsUcArchiveMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsUcArchiveMenu)){
			return form(amsUcArchiveMenu, model);
		}
		AmsUcArchiveMenu parentUcArchiveMenu = null;
		//城建档案类别目录添加
		if( "0".equals(amsUcArchiveMenu.getFlag())){
			//查出父节点,赋值给当前对象
			parentUcArchiveMenu = amsUcArchiveMenuService.get(amsUcArchiveMenu.getParent().getId());
			amsUcArchiveMenu.setType(parentUcArchiveMenu.getType());
			amsUcArchiveMenu.setIsEndChild("1"); //新数据默认为最后子节点
		}
		//基础类型添加
		if("1".equals(amsUcArchiveMenu.getFlag())){
			//设置默认值 0：归档目录添加 1： 基础类型添加
			amsUcArchiveMenu.setCreateUnit("-1");   // -1 代表无生成单位
			amsUcArchiveMenu.setCode("-1");           // -1 代表无编码
			amsUcArchiveMenu.setIsEndChild("0");    //0 不是最后子节点
			amsUcArchiveMenu.setParent(new AmsUcArchiveMenu("1"));  //父节点为根节点
		}
		amsUcArchiveMenuService.save(amsUcArchiveMenu);
		//添加数据后，重置其父节点的子节点状态
		//parentUcArchiveMenu.setIsEndChild("0");  //将父节点置为:不是最后子节点
		amsUcArchiveMenuService.save(parentUcArchiveMenu); //更新父节点
		
		addMessage(redirectAttributes, "保存类别成功");
		return "redirect:" + adminPath + "/ams/amsUcArchiveMenu/list";
	}
	
	@RequiresPermissions("ams:amsUcArchiveMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsUcArchiveMenu amsUcArchiveMenu, RedirectAttributes redirectAttributes) {
		amsUcArchiveMenuService.delete(amsUcArchiveMenu);
		addMessage(redirectAttributes, "删除类别成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsUcArchiveMenu/list";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//获取数据 经过过滤放入mapList中前台解析到tree
		List<AmsUcArchiveMenu> list = amsUcArchiveMenuService.findList(new AmsUcArchiveMenu());
		for (int i=0; i<list.size(); i++){
			AmsUcArchiveMenu e = list.get(i);
			if (e.getType().equals("0")&&StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)
					){
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
		
	/**
	 * 校验字符串中是否涵中文
	 * @param str
	 */
	public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}