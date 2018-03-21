package com.ucams.modules.test.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBObject;
import com.ucams.modules.test.entity.Person;
import com.ucams.modules.test.service.MongoTestService;

@Controller
@RequestMapping(value = "${adminPath}/test/mongodb")
public class MongoController {

	@Autowired
	private MongoTestService mongoTestService;

	@RequestMapping("/save")
	@ResponseBody
	public String goHome(HttpServletRequest request,
			HttpServletResponse response) {
		// mongoTemplate.insert(new Person("小张", 36, "工作"));
		JSONObject json1 = new JSONObject();
		json1.put("name", "张3131");
		json1.put("ename", "zhangsan1");
		JSONObject json2 = new JSONObject();
		json2.put("grade", "一年级");
		json2.put("teacher", "张老师");
		json1.put("data", json2);
		System.out.println(json1.toJSONString());
		mongoTestService.save(json1, "person");
		return "insert";
	}


	@RequestMapping("/find")
	@ResponseBody
	public String find() {
		List<DBObject> list  = mongoTestService.findAll();
		List<String> mapList = new ArrayList<String>();
		for(DBObject obj : list){
			mapList.add(obj.toString());
		}
		
		
		return JSONUtils.toJSONString(mapList);
	}

	/*@RequestMapping("/update")
	public String update() { // Query query = new
	   Query(Criteria.where("age").is(36)); // Update update = new
	   Update().set("name", "abc"); // mongoTemplate.updateFirst(query, update,
	   Person.class); mongoTemplate.updateFirst(new Query(new
	   Criteria("name").in("小吴")), new Update().set("name", "大笨瓜"),
	   Person.class); return "update"; 
	  }

	@RequestMapping("remove")
	@ResponseBody
	public void remove() {
		Query query = new Query(Criteria.where("age").is(36));
		mongoTemplate.remove(query, Person.class);
	}*/

}
