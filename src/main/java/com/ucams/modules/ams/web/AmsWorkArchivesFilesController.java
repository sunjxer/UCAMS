/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ucams.common.config.Global;
import com.ucams.common.persistence.Page;
import com.ucams.common.web.BaseController;
import com.ucams.common.utils.EntityUtils;
import com.ucams.common.utils.FTPPointUtil;
import com.ucams.common.utils.StringUtils;
import com.ucams.common.utils.UcamsTools;
import com.ucams.common.utils.FTPPointUtil.UploadStatus;
import com.ucams.modules.ams.entity.AmsWorkArchives;
import com.ucams.modules.ams.entity.AmsWorkArchivesFiles;
import com.ucams.modules.ams.service.AmsWorkArchivesFilesService;
import com.ucams.modules.ams.service.AmsWorkArchivesService;

/**
 * 业务管理档案案卷文件Controller
 * @author dyk
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsWorkArchivesFiles")
public class AmsWorkArchivesFilesController extends BaseController {

	@Autowired
	private AmsWorkArchivesFilesService amsWorkArchivesFilesService;
	@Autowired
	private AmsWorkArchivesService amsWorkArchivesService;
	
	@ModelAttribute
	public AmsWorkArchivesFiles get(@RequestParam(required=false) String id) {
		AmsWorkArchivesFiles entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = amsWorkArchivesFilesService.get(id);
		}
		if (entity == null){
			entity = new AmsWorkArchivesFiles();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:amsWorkArchivesFiles:view")
	@RequestMapping(value = {"list", ""})
	public String list(AmsWorkArchives amsWorkArchives,AmsWorkArchivesFiles amsWorkArchivesFiles, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AmsWorkArchivesFiles> page = amsWorkArchivesFilesService.findPage(new Page<AmsWorkArchivesFiles>(request, response), amsWorkArchivesFiles); 
		
		String groupId = amsWorkArchivesFiles.getGroupId();
		amsWorkArchives = amsWorkArchivesService.get(new AmsWorkArchives(groupId));
		model.addAttribute("amsArchivesInfo",amsWorkArchives);
		model.addAttribute("page", page);
		return "modules/ams/amsWorkArchivesFilesList";
	}

	@RequiresPermissions("ams:amsWorkArchivesFiles:view")
	@RequestMapping(value = "form")
	public String form(AmsWorkArchivesFiles amsWorkArchivesFiles, Model model) {
		//将当前的案卷ID也传过来
		model.addAttribute("amsWorkArchivesFiles", amsWorkArchivesFiles);
		return "modules/ams/amsWorkArchivesFilesForm";
	}
	
	@RequiresPermissions("ams:amsWorkArchivesFiles:view")
	@RequestMapping(value = "viewForm")
	public String viewForm(AmsWorkArchivesFiles amsWorkArchivesFiles, Model model) {
		//将当前的案卷ID也传过来
		model.addAttribute("amsWorkArchivesFiles", amsWorkArchivesFiles);
		return "modules/ams/amsWorkArchivesFilesViewForm";
	}

	@RequiresPermissions("ams:amsWorkArchivesFiles:edit")
	@RequestMapping(value = "save")
	public String save(AmsWorkArchivesFiles amsWorkArchivesFiles, MultipartHttpServletRequest request,Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, amsWorkArchivesFiles)){
			return form(amsWorkArchivesFiles, model);
		}
		MultipartFile file = request.getFile("file");
		
		String fileName=file.getOriginalFilename();
		String typeString=fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		boolean fail=true;
		if("pdf".equals(typeString)&&"1".equals(amsWorkArchivesFiles.getFileType())){
			fail=false;
		}else if(("jpg".equals(typeString))&&"2".equals(amsWorkArchivesFiles.getFileType())){
			fail=false;
		}
		if (file.getBytes().length > 0&&fail) {
			model.addAttribute("message","请选择正确的文件类型");
			return form(amsWorkArchivesFiles, model);
		}
		
		FTPPointUtil ftp = null;
		try {
			//保存数据
			amsWorkArchivesFilesService.save(amsWorkArchivesFiles);
			
			// 如果上传文件
			File f = null;
			StringBuffer filePath = new StringBuffer();
			
			//判断文件字节是否为0 (文件是否真实上传)
			if (EntityUtils.isNotEmpty(file)&&file.getBytes().length > 0) {
//				throw new FileNotFoundException("文件管理-文件上传-未找到上传文件");
				//组织文件存储路径：档案下面
				filePath.append("projectFile/" +  amsWorkArchivesFiles.getGroupId()); 

				filePath.append("/" + UcamsTools.createNewFileName(file));
				//用缓冲区来实现这个转换即使用java 创建的临时文件
				f=File.createTempFile("fileSavetmp_"+ amsWorkArchivesFiles.getId() , null);
				//链接FTP服务 -- 在线业务
				ftp = new FTPPointUtil("tadmin", "admin");
				//链接FTP服务
				ftp.connect();
				file.transferTo(f);
				//FTP服务上传
				UploadStatus status  = ftp.upload(f, filePath.toString());
				//删除缓冲区的临时文件
				f.deleteOnExit();
				//断开FTP服务
				ftp.disconnect();
				//判断上传结果,不成功
				if(!FTPPointUtil.UploadStatus.Upload_New_File_Success.equals(status)){
					throw new IOException("文件管理-文件上传-文件上传失败!");
				}
				//修改文件路径
				amsWorkArchivesFiles.setFilePath(FTPPointUtil.getOnlineUrl()+filePath.toString());
				amsWorkArchivesFilesService.update(amsWorkArchivesFiles);
				}
			
		} catch (Exception e) {
			ftp.disconnect();  //异常断开FTP服务
			e.printStackTrace();
			throw new IOException("文件管理-文件上传失败!");
		}
		amsWorkArchivesFilesService.save(amsWorkArchivesFiles);
		addMessage(redirectAttributes, "保存档案案卷文件成功");
		//将当前的案卷的ID也传过来
		String groupId = amsWorkArchivesFiles.getGroupId();
		return "redirect:"+Global.getAdminPath()+"/ams/amsWorkArchivesFiles/list?groupId="+groupId;
	}
	
	@RequiresPermissions("ams:amsWorkArchivesFiles:edit")
	@RequestMapping(value = "delete")
	public String delete(AmsWorkArchivesFiles amsWorkArchivesFiles, RedirectAttributes redirectAttributes) {
		amsWorkArchivesFilesService.delete(amsWorkArchivesFiles);
		addMessage(redirectAttributes, "删除业务管理档案案卷文件成功");
		String groupId = amsWorkArchivesFiles.getGroupId();
		return "redirect:"+Global.getAdminPath()+"/ams/amsWorkArchivesFiles/list?groupId="+groupId;
	}

}