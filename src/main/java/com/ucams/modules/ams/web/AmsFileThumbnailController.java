package com.ucams.modules.ams.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ucams.common.persistence.Page;
import com.ucams.common.utils.FileUtils;
import com.ucams.common.utils.KGUtils;
import com.ucams.common.utils.SystemPath;
import com.ucams.modules.ams.entity.AmsFileInfo;
import com.ucams.modules.ams.service.AmsFileInfoService;

/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年8月17日
 * 文件预览(缩略图)
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/fileThumbnai")
public class AmsFileThumbnailController {
	
	@Autowired
	private AmsFileInfoService amsFIleInfoService;
	
	@RequestMapping(value = {"toPreviewFile", ""})
	public String toPreviewFile(AmsFileInfo amsFileInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		//amsFileInfo.setGroupId("3b387fe1319745dcbd95000dfd7023f6");
		Page<AmsFileInfo> page = amsFIleInfoService.findArchivesFileList(new Page<AmsFileInfo>(request, response), amsFileInfo); 
        model.addAttribute("page", page);
        model.addAttribute("groupId",amsFileInfo.getGroupId());
		return "modules/ams/ToolPreviewFile";
	}
	
}
