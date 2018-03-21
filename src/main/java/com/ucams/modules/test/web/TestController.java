/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.test.web;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.ucams.common.persistence.Page;
import com.ucams.common.utils.FileUtils;
import com.ucams.common.utils.KGUtils;
import com.ucams.common.utils.OperationFileUtil;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.utils.SystemPath;
import com.ucams.common.web.BaseController;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.service.AmsFileInfoService;
import com.ucams.modules.ams.web.AmsProjectInfoController;
import com.ucams.modules.sys.entity.Log;
import com.ucams.modules.sys.entity.User;
import com.ucams.modules.sys.utils.UserUtils;
import com.ucams.modules.test.entity.Test;
import com.ucams.modules.test.service.TestService;

/**
 * 测试Controller
 * @author zhuye
 * @version 2013-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/test/restful")
public class TestController extends BaseController {

	@Autowired
	private TestService testService;
	@Autowired
	private AmsFileInfoService amsFIleInfoService;
	
	/*@ModelAttribute
	@RequestMapping("/find")
	public Test get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return testService.get(id);
		}else{
			return new Test();
		}
	}
	
	*//**
	 * 显示列表页
	 * @param test
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *//*
	@RequiresPermissions("test:test:view")
	@RequestMapping(value = {"list", ""})
	public String list(Test test, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/test/testList";
	}
	
	*//**
	 * 获取硕正列表数据（JSON）
	 * @param test
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *//*
	@RequiresPermissions("test:test:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Test> listData(Test test, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			test.setCreateBy(user);
		}
        Page<Test> page = testService.findPage(new Page<Test>(request, response), test); 
        return page;
	}
	
	*//**
	 * 新建或修改表单
	 * @param test
	 * @param model
	 * @return
	 *//*
	@RequiresPermissions("test:test:view")
	@RequestMapping(value = "form")
	public String form(Test test, Model model) {
		model.addAttribute("test", test);
		return "modules/test/testForm";
	}

	*//**
	 * 表单保存方法
	 * @param test
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *//*
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "save")
	public String save(Test test, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, test)){
			return form(test, model);
		}
//		testService.save(test);
		addMessage(redirectAttributes, "保存测试'" + test.getName() + "'成功");
		return "redirect:" + adminPath + "/test/test/?repage";
	}
	
	*//**
	 * 删除数据方法
	 * @param id
	 * @param redirectAttributes
	 * @return
	 *//*
	@RequiresPermissions("test:test:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Test test, RedirectAttributes redirectAttributes) {
//		testService.delete(test);
//		addMessage(redirectAttributes, "删除测试成功");
//		return "redirect:" + adminPath + "/test/test/?repage";
		return "true";
	}
	
	@RequestMapping(value = "testRest")
	@ResponseBody
	public String testRest(){
		String s = "{\"categoryId\": 1,\"categoryName\": \"饮品\",\"categoryImage\": \"/upload/yinpin.jpg\"}";
		return s;
	}*/
	
	@RequestMapping(value = {"toPreviewFile", ""})
	public String toPreviewFile(AmsFileInfo amsFileInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		//组卷ID
		amsFileInfo.setGroupId("c1da330053194edda6c7d31d04e77e70");
		Page<AmsFileInfo> page = amsFIleInfoService.findArchivesFileList(new Page<AmsFileInfo>(request, response), amsFileInfo); 
        model.addAttribute("page", page);
		return "modules/test/ToolPreviewFile";
	}
	
	@RequestMapping(value = {"checkFileOfSing"})
	@ResponseBody
	public Object checkFileOfSing(AmsFileInfo amsFileInfo, @RequestParam("file") MultipartFile file,HttpServletRequest request, HttpServletResponse response, Model model) {
		String result = "";
		try {
			 result = KGUtils.getSigninfo(file.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String saveWebImgFile(MultipartFile imgFile,String pno){  
        String webFilePath = "";  
      //  if(imgFile.getSize() > 0 && isImage(imgFile.getContentType())){  
            FileOutputStream fos = null;  
            try {  
                byte[] b = imgFile.getBytes();  
                /* 构造文件路径 */  
                String dirPath =   
                		SystemPath.getSysPath() ;
                		String path="fixed/"+pno;
                		dirPath+=path;
                FileUtils.createDirectory(dirPath);
                File dir = new File(dirPath);  
                if(!dir.exists()){  
                    dir.mkdirs();  
                }  
                String fileName = imgFile.getOriginalFilename();  
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                 String   newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
                String filePath = dirPath + "/"  + newFileName;  
                webFilePath = path+ "/"  + newFileName;  
                File file = new File(filePath);  
                  
                dir.setWritable(true);  
                file.setWritable(true);  
                  
                fos = new FileOutputStream(file);  
                fos.write(b);  
                  
            } catch (IOException e) {  
                throw new RuntimeException("文件上传失败！" ,e);  
            }finally{  
                if(fos != null){  
                    try {  
                        fos.close();  
                    } catch (IOException e) {  
                        throw new RuntimeException("文件上传->输出流关闭失败！！！！" ,e);  
                    }  
                }  
            }  
       // }  
          
        return webFilePath;  
    }
	public static void main(String[] args) {
		
		try {
			Socket socket =  IO.socket("http://192.168.0.212:3000");
			socket.connect();
			JSONObject obj = new JSONObject();
			obj.put("userId", "1234");
			socket.emit("hasNewMessage", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
