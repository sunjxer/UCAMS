package com.ucams.modules.test.web;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ucams.common.utils.OperationFileUtil;
import com.ucams.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/test/upload")
public class UploadFileController extends BaseController{

	@RequestMapping("")
	public String index() {
		return "modules/test/pdfUpload";
	}
	
	@RequestMapping("uploadFile")
	@ResponseBody
	public Object uploadFile(HttpServletRequest request) {
		Map<String, String>result = new HashMap<String, String>();
		try {
			Map<String, String> fileMap = OperationFileUtil.multiFileUpload(request, "G:/");
			Collection<String> values = fileMap.values(); 
			for (Object object : values) {
				result.put("fileUrl", object.toString());
			}
			result.put("success", "true");
			return result;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("success", "false");
		return result;
	}
}
