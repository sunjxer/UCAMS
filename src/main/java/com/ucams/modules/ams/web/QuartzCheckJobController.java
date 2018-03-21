package com.ucams.modules.ams.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年12月25日
 * 接入定时检查任务
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/quartzCheckJob")
public class QuartzCheckJobController {
	
	// 临时接入，不做安全认证(token，验签，加密)
	
	
	@RequestMapping(value="")
	public String index(){
		return "modules/ams/quartzCheckJobList";
	}
	
	//添加任务
	
	public void addjob(){
		
	}
	
	//查询任务
	
	public void queryjob(){
		
	}
	
	//开启任务
	
	public void runjob(){
		
	}
	
	//恢复任务
	
	public void reschedulejob(){
		
	}
	
	//删除任务
	
	public void deletejob(){
		
	}
}
