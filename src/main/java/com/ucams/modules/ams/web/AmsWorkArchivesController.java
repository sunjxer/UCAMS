/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.ucams.common.utils.StringUtils;
import com.ucams.common.web.BaseController;
import com.ucams.modules.ams.entity.AmsUcArchiveMenu;
import com.ucams.modules.ams.entity.AmsWorkArchives;
import com.ucams.modules.ams.entity.AmsWorkArchivesFiles;
import com.ucams.modules.ams.service.AmsUcArchiveMenuService;
import com.ucams.modules.ams.service.AmsWorkArchivesFilesService;
import com.ucams.modules.ams.service.AmsWorkArchivesService;

/**
 * 业务管理档案案卷Controller
 * 
 * @author dyk
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsWorkArchives")
public class AmsWorkArchivesController extends BaseController {

	@Autowired
	private AmsWorkArchivesService amsWorkArchivesService;
	@Autowired
	private AmsUcArchiveMenuService amsUcArchiveMenuService;
	@Autowired
	private AmsWorkArchivesFilesService amsWorkArchivesFilesService;

	@ModelAttribute
	public AmsWorkArchives get(@RequestParam(required = false) String id) {
		AmsWorkArchives entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = amsWorkArchivesService.get(id);
		}
		if (entity == null) {
			entity = new AmsWorkArchives();
		}
		return entity;
	}

	@RequiresPermissions("ams:amsWorkArchives:view")
	@RequestMapping(value = { "list", "" })
	public String list(AmsWorkArchives amsWorkArchives, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		AmsWorkArchivesFiles amsWorkArchivesFiles =new AmsWorkArchivesFiles();
		//第三级目录（档案）
		if(!StringUtils.isEmpty(amsWorkArchives.getArchivesName())&&StringUtils.isEmpty(amsWorkArchives.getAmsWorkArchivesFiles().getFileName())){
			// 判断是否是查看
			String formView = request.getParameter("formView");
			model.addAttribute("formView", formView);
			//通过传参找到对应档案信息（唯一的）
			amsWorkArchives = amsWorkArchivesService.getByParam(amsWorkArchives);
			//通过案卷Id找到所有对应的文件信息
			amsWorkArchivesFiles.setGroupId(amsWorkArchives.getId());
			Page<AmsWorkArchivesFiles> page = amsWorkArchivesFilesService.findPage(new Page<AmsWorkArchivesFiles>(request, response), amsWorkArchivesFiles); 
			model.addAttribute("page", page);
			model.addAttribute("amsWorkArchivesFiles",amsWorkArchivesFiles);
			model.addAttribute("amsArchivesInfo", amsWorkArchives);
			return "modules/ams/amsWorkArchivesFilesList";
		}else if(amsWorkArchives.getAmsWorkArchivesFiles()!=null&&!StringUtils.isEmpty(amsWorkArchives.getAmsWorkArchivesFiles().getFileName())){
			//if(!StringUtils.isEmpty(amsWorkArchives.getAmsWorkArchivesFiles().getFileName())){
				//通过传参找到对应档案信息（唯一的）
				AmsWorkArchives amsWorkArchivesNoFileInfo = amsWorkArchivesService.getByParam(amsWorkArchives);
				//通过案卷Id找到所有对应的文件信息
				//通过groupId与fileName查找数据
				amsWorkArchivesFiles.setGroupId(amsWorkArchivesNoFileInfo.getId());
				amsWorkArchivesFiles.setFileName(amsWorkArchives.getAmsWorkArchivesFiles().getFileName());
				amsWorkArchivesFiles = amsWorkArchivesFilesService.getByParam(amsWorkArchivesFiles);
				model.addAttribute("amsWorkArchivesFiles", amsWorkArchivesFiles);
				//${ctx}/ams/amsWorkArchivesFiles/form?id=${amsWorkArchivesFiles.id}&viewForm=1
				return "modules/ams/amsWorkArchivesFilesViewForm";
		}else{
			Page<AmsWorkArchives> page = amsWorkArchivesService.findPage(new Page<AmsWorkArchives>(request, response),
					amsWorkArchives);
			model.addAttribute("page", page);
			return "modules/ams/amsWorkArchivesList";
		}
	}

	@RequiresPermissions("ams:amsWorkArchives:view")
	@RequestMapping(value = "form")
	public String form(AmsWorkArchives amsWorkArchives, HttpServletRequest request, Model model) {
		// 判断是否是查看
		String formView = request.getParameter("formView");
		model.addAttribute("formView", formView);
		// 查找所有编制单位
		List<AmsWorkArchives> AmsWorkArchivesList = amsWorkArchivesService.findAllList(amsWorkArchives);

		model.addAttribute("makeUnitList", AmsWorkArchivesList);
		model.addAttribute("amsWorkArchives", amsWorkArchives);
		return "modules/ams/amsWorkArchivesForm";
	}
	
	@RequestMapping(value = "ListMain")
	public String ListMain(AmsWorkArchives amsWorkArchives, HttpServletRequest request, Model model) {
		
		//第三级目录（档案）
		if(!StringUtils.isEmpty(amsWorkArchives.getArchivesName())&&StringUtils.isEmpty(amsWorkArchives.getAmsWorkArchivesFiles().getFileName())){
			// 判断是否是查看
			String formView = request.getParameter("formView");
			model.addAttribute("formView", formView);
			// 查找所有编制单位
			List<AmsWorkArchives> AmsWorkArchivesList = amsWorkArchivesService.findAllList(amsWorkArchives);

			model.addAttribute("makeUnitList", AmsWorkArchivesList);
			model.addAttribute("amsWorkArchives", amsWorkArchives);
			return "modules/ams/amsWorkArchivesForm";
		}else{
			return "modules/ams/amsWorkArchivesForm";
		}
		
	}

	@RequiresPermissions("ams:amsWorkArchives:edit")
	@RequestMapping(value = "save")
	public String save(AmsWorkArchives amsWorkArchives, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsWorkArchives)) {
			return form(amsWorkArchives, request, model);
		}
		amsWorkArchivesService.save(amsWorkArchives);
		addMessage(redirectAttributes, "保存业务管理档案案卷成功");
		return "redirect:" + Global.getAdminPath() + "/ams/amsWorkArchives/formMain";
	}

	@RequiresPermissions("ams:amsWorkArchives:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsWorkArchives amsWorkArchives, RedirectAttributes redirectAttributes) {
		amsWorkArchivesService.delete(amsWorkArchives);
		addMessage(redirectAttributes, "删除业务管理档案案卷成功");
		return "redirect:" + Global.getAdminPath() + "/ams/amsWorkArchives/formMain";
	}

	/**
	 * 案卷小类
	 * 
	 * @param extId
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		// 获取数据 经过过滤放入mapList中前台解析到tree
		// 获取指定的父类级别
		AmsUcArchiveMenu amsUcArchiveMenu = new AmsUcArchiveMenu();
		amsUcArchiveMenu.setCode("D");
		amsUcArchiveMenu = amsUcArchiveMenuService.findByCode(amsUcArchiveMenu);
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", amsUcArchiveMenu.getId());
		map.put("name", amsUcArchiveMenu.getName());
		map.put("isParent", true);
		mapList.add(map);

		// 获取对应的子类
		List<AmsUcArchiveMenu> list = amsUcArchiveMenuService.findList(new AmsUcArchiveMenu());
		for (int i = 0; i < list.size(); i++) {
			AmsUcArchiveMenu e = list.get(i);
			if (amsUcArchiveMenu.getId().equals(e.getParentId())) {
				map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);

			}
		}
		return mapList;
	}

	/**
	 * 著录界面
	 * 
	 * @param amsTransfer
	 * @return
	 */
	@RequiresPermissions("ams:amsWorkArchives:edit")
	@RequestMapping(value = { "formMain", "" })
	public String main(AmsWorkArchives amsWorkArchives) {
		return "modules/ams/amsWorkArchivesFormMain";
	}

	/**
	 * 案卷信息树结构 获取JSON数据。
	 * 
	 * @return
	 */
	@RequiresPermissions("ams:amsWorkArchives:view")
	@ResponseBody
	@RequestMapping(value = "archiveTreeData")
	public List<Map<String, Object>> archiveTreeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		List<Object> list = new ArrayList<>();
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		// 设置树的根节点
		List<AmsWorkArchives> amsWorkArchivesList = amsWorkArchivesService
				.findAllArchivesFilesList(new AmsWorkArchives());
		// 存根节点
		HashMap<String, Object> makeUnitMap = Maps.newHashMap();
		// 存小类节点
		HashMap<String, Object> minTypeMap = Maps.newHashMap();
		// 存档案（年）节点
		HashMap<String, Object> yearMap = Maps.newHashMap();
		// 存文件节点
		HashMap<String, Object> fileMap = Maps.newHashMap();
		// 去重复的value值
		for (AmsWorkArchives amsWorkArchives : amsWorkArchivesList) {
			if (!makeUnitMap.containsValue(amsWorkArchives.getMakeUnit())) {
				//当前i从1开始，及put(1)-->put(2)
				makeUnitMap.put(getPutId(++i), amsWorkArchives.getMakeUnit());
			}
			//当前i,j从1开始，及put(1,1)-->put(2,5)
			//判断是不是有相同的节点数据
			if (!minTypeMap.containsValue(amsWorkArchives.getExten1())) {
				minTypeMap.put(getPutId(i,++j), amsWorkArchives.getExten1());
			}else{
				//判断是不是当前父节点下面的值重复了,重复则不添加，反之添加
				//[通过值找到对应的所有key，取key 的上一级末尾第二个的逗号前面；判断是否相同，只要有相同跳过不添加；全都不相同，添加当前值。]
				List<String> keyList = getKeyList(minTypeMap,amsWorkArchives.getExten1());
				Boolean flag = false;
				for (String key : keyList) {
					if(key.substring(0, key.lastIndexOf(",")).equals(getPutId(i))){
						flag=true;
						break;
					}
				}
				if(!flag){
					minTypeMap.put(getPutId(i,++j), amsWorkArchives.getExten1());
				}
			}
			//当前i,j,k从1开始，及put(1,1,1)-->put(2,5,12)
			if (!yearMap.containsValue(amsWorkArchives.getArchivesName()+"["+amsWorkArchives.getYear()+"]")) {
				yearMap.put(getPutId(i,j,++k), amsWorkArchives.getArchivesName()+"["+amsWorkArchives.getYear()+"]");
			}else{
				List<String> keyList = getKeyList(yearMap,amsWorkArchives.getArchivesName()+"["+amsWorkArchives.getYear()+"]");
				Boolean flag = false;
				for (String key : keyList) {
					if(key.substring(0, key.lastIndexOf(",")).equals(getPutId(i,j))){
						flag=true;
						break;
					}
				}
				if(!flag){
					yearMap.put(getPutId(i,j,++k), amsWorkArchives.getArchivesName()+"["+amsWorkArchives.getYear()+"]");
				}
			}
			////当前i,j,k,l从1开始，及put(1,1,1,1)-->put(2,5,12,22)
			if (amsWorkArchives.getAmsWorkArchivesFiles() != null) {
				if (!fileMap.containsValue(amsWorkArchives.getAmsWorkArchivesFiles().getFileName())) {
					fileMap.put(getPutId(i,j,k,++l), amsWorkArchives.getAmsWorkArchivesFiles().getFileName());
				}else{
					List<String> keyList = getKeyList(fileMap,amsWorkArchives.getAmsWorkArchivesFiles().getFileName());
					Boolean flag = false;
					for (String key : keyList) {
						if(key.substring(0, key.lastIndexOf(",")).equals(getPutId(i,j,k))){
							flag=true;
							break;
						}
					}
					if(!flag){
						fileMap.put(getPutId(i,j,k,++l), amsWorkArchives.getAmsWorkArchivesFiles().getFileName());
					}
				}
			}
		}
		
		Iterator<Map.Entry<String, Object>> makeUnitIter = makeUnitMap.entrySet().iterator();
		while (makeUnitIter.hasNext()) {
			Map.Entry<String, Object> entry = makeUnitIter.next();
			String key = entry.getKey();
			Object val = entry.getValue();
			map = Maps.newHashMap();
			map.put("id", key);
			map.put("name", val);
			map.put("isParent", true);
			mapList.add(map);
		}
		
		Iterator<Map.Entry<String, Object>> minTypeIter = minTypeMap.entrySet().iterator();
		while (minTypeIter.hasNext()) {
			Map.Entry<String, Object> entry = minTypeIter.next();
			String key = entry.getKey();
			Object val = entry.getValue();
			map = Maps.newHashMap();
			map.put("id", key);
			map.put("pId", getFather(key));
			map.put("name", val);
			mapList.add(map);
		}
		
		Iterator<Map.Entry<String, Object>> yearIter = yearMap.entrySet().iterator();
		while (yearIter.hasNext()) {
			Map.Entry<String, Object> entry = yearIter.next();
			String key = entry.getKey();
			Object val = entry.getValue();
			map = Maps.newHashMap();
			map.put("id", key);
			map.put("pId", getFather(key));
			map.put("name", val);
			mapList.add(map);
		}
		
		Iterator<Map.Entry<String, Object>> fileIter = fileMap.entrySet().iterator();
		while (fileIter.hasNext()) {
			Map.Entry<String, Object> entry = fileIter.next();
			String key = entry.getKey();
			Object val = entry.getValue();
			map = Maps.newHashMap();
			map.put("id", key);
			map.put("pId", getFather(key));
			map.put("name", val);
			mapList.add(map);
		}
		
		return mapList;
	}

	/**
	 * value取key
	 * @param map
	 * @param value
	 * @return
	 */
	public static List<String> getKeyList(HashMap<String, Object> map, Object value) {
		List<String> keyList = new ArrayList<String>();
		for (String getKey : map.keySet()) {
			if (map.get(getKey).equals(value)) {
				keyList.add(getKey);
			}
		}
		return keyList;
	}
	/**
	 * 生成id
	 * @param intArray
	 * @return
	 */
	 public String getPutId(int... intArray){ 
		 String s="";
	        for (int i : intArray)  
	            s= s+i+",";
			return s.substring(0, s.length()-1);
	 }  
	 
	 public String getFather(String s){
		 return s.substring(0, s.lastIndexOf(","));
	 }
}