package com.ucams.modules.ams.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ucams.common.utils.DateUtils;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.utils.excel.ExportExcel;
import com.ucams.common.web.BaseController;
import com.ucams.modules.ams.dto.AmsGuidanceExportDTO;
import com.ucams.modules.ams.entity.AmsGuidance;
import com.ucams.modules.ams.service.AmsGuidanceService;
/**
 * 业务指导统计Controller
 * @author gyl
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/guidanceStatistics")
public class GuidanceStatisticsController extends BaseController{
	
	@Autowired
	private AmsGuidanceService amsGuidanceService;
	
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
	/**
	 * 按人统计时间段内业务指导次数
	 * @param amsGuidance
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequiresPermissions("ams:guidanceStatistics:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsGuidance amsGuidance,HttpServletRequest request,HttpServletResponse response,Model model){
		try{
			//如果业务指导起始时间是空，则默认设置为当前年份的第一天
			if(amsGuidance.getAct().getBeginDate() == null){
				Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				c.clear();
				c.set(Calendar.YEAR, year);
				Date firstDate = c.getTime();
				amsGuidance.getAct().setBeginDate(firstDate);
			}
			//如果业务指导结束时间是空，则默认设置为当前日期
			if(amsGuidance.getAct().getEndDate() == null){
				Date nowDate = new Date();
				amsGuidance.getAct().setEndDate(nowDate);
			}
			//工作流程id
			amsGuidance.getAct().setProcDefKey("guidance");
			//业务指导次数查询
			List<AmsGuidanceExportDTO> list = amsGuidanceService.findGuidanceCountByUser(amsGuidance);
			model.addAttribute("list", list);
			model.addAttribute("amsGuidance", amsGuidance);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "modules/ams/guidanceStatisticsList";
	}
	/**
	 * 时间段内业务指导次数统计导出
	 * @param amsGuidance
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return String
	 */
	@RequestMapping(value = "expload",method=RequestMethod.POST)
	public String expload(AmsGuidance amsGuidance,HttpServletRequest request,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes){
		try{
			if(amsGuidance.getAct().getBeginDate() == null){
				Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				c.clear();
				c.set(Calendar.YEAR, year);
				Date firstDate = c.getTime();
				amsGuidance.getAct().setBeginDate(firstDate);
			}
			if(amsGuidance.getAct().getEndDate() == null){
				Date nowDate = new Date();
				amsGuidance.getAct().setEndDate(nowDate);
			}
			amsGuidance.getAct().setProcDefKey("guidance");
			List<AmsGuidanceExportDTO> list = amsGuidanceService.findGuidanceCountByUser(amsGuidance);
			//文件名
			String fileName = DateUtils.formatDate(amsGuidance.getAct().getBeginDate(), null)+"--"+DateUtils.formatDate(amsGuidance.getAct().getEndDate(), null)+"业务指导统计报表.xlsx";
			new ExportExcel("业务指导统计",AmsGuidanceExportDTO.class).setDataList(list).write(response, fileName).dispose();
			return null;
		}catch(Exception e){
			addMessage(redirectAttributes,"导出失败:"+e.getMessage());
		}
		return "";
	}
	
	/**
	 * 业务指导同期比统计
	 * @param amsGuidance
	 * @param request
	 * @param response
	 * @param model
	 * @return  String 
	 */
	@RequiresPermissions("ams:guidanceStatistics:view")
	@RequestMapping(value = "samePeriod")
	public String samePeriod(AmsGuidance amsGuidance,HttpServletRequest request,HttpServletResponse response,Model model){
		
		Calendar c = Calendar.getInstance();
		int nowYear = c.get(Calendar.YEAR);
		if(amsGuidance.getAct().getBeginDate() != null){
			c.setTime(amsGuidance.getAct().getBeginDate());
			nowYear = c.get(Calendar.YEAR);			
		}
		int beforeYear = nowYear-1;
		int beginMonth = 1;
		if(StringUtils.isNotBlank(amsGuidance.getExten1())){
			beginMonth = Integer.parseInt(amsGuidance.getExten1());
		}
		int endMonth = c.get(Calendar.MONTH);
		if(StringUtils.isNotBlank(amsGuidance.getExten2())){
			endMonth = Integer.parseInt(amsGuidance.getExten2());
		}
		amsGuidance.getAct().setProcDefKey("guidance");
		List<AmsGuidanceExportDTO> nowList = amsGuidanceService.findGuidanceCountByMonth(nowYear, beginMonth, endMonth, amsGuidance);
		List<AmsGuidanceExportDTO> beforeList = amsGuidanceService.findGuidanceCountByMonth(beforeYear, beginMonth, endMonth, amsGuidance);
		String monthArr = "";
		String nowCountArr = "";
		String beforeCountArr = "";
		for(AmsGuidanceExportDTO guidance : nowList){
			monthArr = monthArr + guidance.getMonth() + "月,";
			nowCountArr = nowCountArr + guidance.getCount()+",";
		}
		for(AmsGuidanceExportDTO guidance : beforeList){
			beforeCountArr = beforeCountArr + guidance.getCount() + ",";
		}
		model.addAttribute("monthArr", monthArr);
		model.addAttribute("nowCountArr", nowCountArr);
		model.addAttribute("beforeCountArr", beforeCountArr);
		model.addAttribute("nowYear", nowYear);
		model.addAttribute("beforeYear", beforeYear);
		return "modules/ams/guidanceSamePeriodView";
	}

}
