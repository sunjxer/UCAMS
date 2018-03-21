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
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.ucams.common.beanvalidator.BeanValidators;
import com.ucams.common.config.Global;
import com.ucams.common.constant.CommonConstant;
import com.ucams.common.constant.UcamsConstant;
import com.ucams.common.web.BaseController;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.utils.excel.ImportExcel;
import com.ucams.modules.ams.dto.AmsGenreImportDTO;
import com.ucams.modules.ams.entity.AmsGenre;
import com.ucams.modules.ams.service.AmsGenreService;
import com.ucams.modules.ams.utils.AmsDictUtils;

/**
 * 归档一览表类别管理Controller
 * @author sunjx
 * @version 2017-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsGenre")
public class AmsGenreController extends BaseController {

	@Autowired
	private AmsGenreService amsGenreService;
	
	@ModelAttribute
	public AmsGenre get(@RequestParam(required=false) String id) {
		AmsGenre entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsGenreService.get(id);
		}
		if (entity == null){
			entity = new AmsGenre();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsGenre:view")
	@RequestMapping(value = {""})
	public String index(Model model) {
		return "modules/ams/amsGenreIndex";
	}
	
	@RequiresPermissions("ams:amsGenre:view")
	@RequestMapping(value = {"list"})
	public String list(AmsGenre amsGenre, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<AmsGenre> list = amsGenreService.findList(amsGenre); 
		model.addAttribute("list", list);
		return "modules/ams/amsGenreList";
	}

	@RequiresPermissions("ams:amsGenre:view")
	@RequestMapping(value = "form")
	public String form(AmsGenre amsGenre, Model model) {
		if (amsGenre.getParent()!=null && StringUtils.isNotBlank(amsGenre.getParent().getId())){
			amsGenre.setParent(amsGenreService.get(amsGenre.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(amsGenre.getId())){
				AmsGenre amsGenreChild = new AmsGenre();
				amsGenreChild.setParent(new AmsGenre(amsGenre.getParent().getId()));
				List<AmsGenre> list = amsGenreService.findList(amsGenre); 
				if (list.size() > 0){
					amsGenre.setSort(list.get(list.size()-1).getSort());
					if (amsGenre.getSort() != null){
						amsGenre.setSort(amsGenre.getSort() + 30);
					}
				}
			}
		}
		if (amsGenre.getSort() == null){
			amsGenre.setSort(30);
		}
		model.addAttribute("amsGenre", amsGenre);
		return "modules/ams/amsGenreForm";
	}

	@RequiresPermissions("ams:amsGenre:edit")
	@RequestMapping(value = "save")
	public String save(AmsGenre amsGenre, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, amsGenre)){
			return form(amsGenre, model);
		}
		AmsGenre parentGenre = null;
		//归档表目录添加
		if( "0".equals(amsGenre.getFlag())){
			//查出父节点,赋值给当前对象
			parentGenre = amsGenreService.get(amsGenre.getParent().getId());
			amsGenre.setType(parentGenre.getType());
			amsGenre.setIsEndChild("1"); //新数据默认为最后子节点
		}
		//基础类型添加
		if("1".equals(amsGenre.getFlag())){
			//设置默认值 0：归档目录添加 1： 基础类型添加
			amsGenre.setCreateUnit("-1");   // -1 代表无生成单位
			amsGenre.setCode("-1");           // -1 代表无编码
			amsGenre.setIsEndChild("0");    //0 不是最后子节点
			amsGenre.setParent(new AmsGenre("1"));  //父节点为根节点
		}
		amsGenreService.save(amsGenre);
		//添加数据后，重置其父节点的子节点状态
		parentGenre.setIsEndChild("0");  //将父节点置为:不是最后子节点
		amsGenreService.save(parentGenre); //更新父节点
		
		addMessage(redirectAttributes, "保存类别成功");
		return "redirect:" + adminPath + "/ams/amsGenre/list";
	}
	
	@RequiresPermissions("ams:amsGenre:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsGenre amsGenre, RedirectAttributes redirectAttributes) {
		amsGenreService.delete(amsGenre);
		addMessage(redirectAttributes, "删除类别成功");
		return "redirect:"+Global.getAdminPath()+"/ams/amsGenre/list";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AmsGenre> list = amsGenreService.findList(new AmsGenre());
		for (int i=0; i<list.size(); i++){
			AmsGenre e = list.get(i);
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
	/**
	 * 导入数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("ams:amsGenre:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file,String unitProjectType, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/ams/amsGenre/list?repage";
		}
		//如果已选择基础类型则插入该节点
		AmsGenre genGenre = new AmsGenre();
		String dictLabel = "";
		if(EntityUtils.isNotEmpty(unitProjectType)){
			dictLabel =AmsDictUtils.getAmsDictLabel(unitProjectType, "unit_project_type", "unfound");
			genGenre.setParent(new AmsGenre("1"));
			genGenre.setName(dictLabel);
			genGenre.setSort(30);
			genGenre.setCode("-1");
			genGenre.setType(unitProjectType);  //基础类型
			genGenre.setIsEndChild("0"); //是否为最后子节点
			genGenre.setCreateUnit("-1"); //无生成单位
			amsGenreService.save(genGenre);
		}else{
			addMessage(redirectAttributes, "数据异常: 未找到基础类型");
			return "redirect:" + adminPath + "/ams/amsGenre/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AmsGenreImportDTO> list = ei.getDataList(AmsGenreImportDTO.class);
			for (int f = 0 ; f < list.size() ; f ++){
				try{
					AmsGenreImportDTO genreDto = list.get(f);
					//编号非空证明该条数据为正文
					if(EntityUtils.isNotEmpty(genreDto.getCode()) && ! isContainChinese(genreDto.getCode())){
						List<AmsGenre> genre = amsGenreService.findByCode(genreDto.getCode());
						if(EntityUtils.isNotEmpty(genre) && genre.size() > 0){
							addMessage(redirectAttributes, "数据异常:  Excel数据中存在与历史数据冲突的Code");
							amsGenreService.delete(genGenre);
							return "redirect:" + adminPath + "/ams/amsGenre/list?repage";
						}
						AmsGenre amsGenre = new AmsGenre();
						//判断是否为子节点
						if(EntityUtils.isNotEmpty(genreDto.getParentCode())){
							//若为子节点 查出父节点
							List<AmsGenre> pGenre = amsGenreService.findByCode(genreDto.getParentCode());
							String unitType = null;
							String isChildNode = null;
							if(EntityUtils.isEmpty(pGenre) ){
								failureMsg.append("<br/>名称 "+genreDto.getName()+" 导入失败：找不到父节点");
								failureNum++; //计算失败条数
							}else if(pGenre.size() >1){
								failureMsg.append("<br/>名称 "+genreDto.getName()+" 导入失败：存在多个父节点，存在脏数据");
								failureNum++; //计算失败条数
							}else{
								//判断生成单位类型
								if(EntityUtils.isNotEmpty(genreDto.getJsUnit())){
									unitType = UcamsConstant.AMS_RESBODY_TYPE_JSDY;
									isChildNode = CommonConstant.AMS_COMMON_PARAM_NUM01;
								}else if(EntityUtils.isNotEmpty(genreDto.getJlUnit())){
									unitType = UcamsConstant.AMS_RESBODY_TYPE_JLDY;
									isChildNode = CommonConstant.AMS_COMMON_PARAM_NUM01;
								}else if(EntityUtils.isNotEmpty(genreDto.getSgUnit())){
									unitType = UcamsConstant.AMS_RESBODY_TYPE_SGDY;
									isChildNode = CommonConstant.AMS_COMMON_PARAM_NUM01;
								}else{
									unitType = "-1";
									isChildNode = CommonConstant.AMS_COMMON_PARAM_NUM00;
								}
								//组织参数,入库
								amsGenre.setParent(pGenre.get(0));
								amsGenre.setName(genreDto.getName());
								amsGenre.setSort(f *30+10);
								amsGenre.setCode(genreDto.getCode());
								amsGenre.setType(unitProjectType);  //基础类型
								amsGenre.setIsEndChild(isChildNode); //是否为最后子节点
								amsGenre.setCreateUnit(unitType);
							}
						}else{
							//不是子节点 组织参数 入库
							amsGenre.setParent(genGenre); //父节点为根节点
							amsGenre.setName(genreDto.getName());
							amsGenre.setSort(f *30+10);
							amsGenre.setCode(genreDto.getCode());
							amsGenre.setType(unitProjectType);  //基础类型
							amsGenre.setIsEndChild("0"); //是否为最后子节点
							amsGenre.setCreateUnit("-1");
						}
						successNum ++; //计算成功条数
						amsGenreService.save(amsGenre);
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>名称 "+list.get(f).getName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					ex.printStackTrace();
					failureMsg.append("<br/>名称 "+list.get(f).getName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条数据，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条数据"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入归档一览表失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/ams/amsGenre/list?repage";
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