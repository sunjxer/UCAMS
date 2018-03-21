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
import com.ucams.modules.ams.entity.AmsArchiveMenu;
import com.ucams.modules.ams.service.AmsArchiveMenuService;
import com.ucams.modules.sys.service.OfficeService;
import com.ucams.modules.sys.service.SystemService;



/**
 * 声像档案类别管理Contorller
 * 2017年12月1日 下午1:36:27
 * @author dpj
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsArchiveMenu")
public class AmsArchiveMenuController extends BaseController {

	@Autowired
	private AmsArchiveMenuService amsArchiveMenuService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public AmsArchiveMenu get(@RequestParam(required=false) String id) {
		AmsArchiveMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsArchiveMenuService.get(id);
		}
		if (entity == null){
			entity = new AmsArchiveMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsArchiveMenu:view")
	@RequestMapping(value = {"main"})
	public String index(Model model) {
		return "modules/ams/amsArchiveMenuMain";
	}
	
	@RequiresPermissions("ams:amsArchiveMenu:view")
	@RequestMapping(value = {"list"})
	public String list(AmsArchiveMenu amsArchiveMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AmsArchiveMenu> list = amsArchiveMenuService.findList(amsArchiveMenu); 
		model.addAttribute("list", list);
		return "modules/ams/amsArchiveMenuList";
	}

	@RequiresPermissions("ams:amsArchiveMenu:view")
	@RequestMapping(value = "form")
	public String form(AmsArchiveMenu amsArchiveMenu, Model model) {
		
		if (amsArchiveMenu.getParent()!=null && StringUtils.isNotBlank(amsArchiveMenu.getParent().getId())){
			amsArchiveMenu.setParent(amsArchiveMenuService.get(amsArchiveMenu.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			
			if (StringUtils.isBlank(amsArchiveMenu.getId())){
				AmsArchiveMenu amsArchiveMenuChild = new AmsArchiveMenu();
				amsArchiveMenuChild.setParent(new AmsArchiveMenu(amsArchiveMenu.getParent().getId()));
				List<AmsArchiveMenu> list = amsArchiveMenuService.findList(amsArchiveMenu); 
				if (list.size() > 0){
					amsArchiveMenu.setSort(list.get(list.size()-1).getSort());
					if (amsArchiveMenu.getSort() != null){
						amsArchiveMenu.setSort(amsArchiveMenu.getSort() + 30);
					}
				}
			}
		}
		if (amsArchiveMenu.getSort() == null){
			amsArchiveMenu.setSort(30);
		}
	
		model.addAttribute("amsArchiveMenu", amsArchiveMenu);
		return "modules/ams/amsArchiveMenuForm";
	}

	@RequiresPermissions("ams:amsArchiveMenu:edit")
	@RequestMapping(value = "save")
	public String save(AmsArchiveMenu amsArchiveMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsArchiveMenu)){
			return form(amsArchiveMenu, model);
		}
		AmsArchiveMenu parentArchiveMenu = null;
		//目录添加
		if( "0".equals(amsArchiveMenu.getFlag())){
			//查出父节点,赋值给当前对象
			parentArchiveMenu = amsArchiveMenuService.get(amsArchiveMenu.getParent().getId());
			amsArchiveMenu.setType(parentArchiveMenu.getType());
			//amsArchiveMenu.setIsEndChild("1"); //新数据默认为最后子节点
		}
		//基础类型添加
		if("1".equals(amsArchiveMenu.getFlag())){
			//设置默认值 0：归档目录添加 1： 基础类型添加
			amsArchiveMenu.setCreateUnit("-1");   // -1 代表无生成单位
			amsArchiveMenu.setCode("-1");           // -1 代表无编码
			//amsArchiveMenu.setIsEndChild("0");    //0 不是最后子节点
			amsArchiveMenu.setParent(new AmsArchiveMenu("1"));  //父节点为根节点
		}
		
		amsArchiveMenuService.save(amsArchiveMenu);
		//添加数据后，重置其父节点的子节点状态
		//parentArchiveMenu.setIsEndChild("0");  //将父节点置为:不是最后子节点
		amsArchiveMenuService.save(parentArchiveMenu); //更新父节点
		
		addMessage(redirectAttributes, "保存类别成功");
		return "redirect:" + adminPath + "/ams/amsArchiveMenu/list";
	}
	
	@RequiresPermissions("ams:amsArchiveMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsArchiveMenu amsArchiveMenu, RedirectAttributes redirectAttributes) {
		amsArchiveMenuService.delete(amsArchiveMenu);
		addMessage(redirectAttributes, "删除类别成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsArchiveMenu/list";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AmsArchiveMenu> list = amsArchiveMenuService.findList(new AmsArchiveMenu());
		for (int i=0; i<list.size(); i++){
			AmsArchiveMenu e = list.get(i);
			//type=7为子节点 过滤掉子节点
			if (640>e.getSort()&&StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)
					){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				map.put("pIds", e.getParentIds());
				//map.put("isParent", true);
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