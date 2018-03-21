package com.ucams.common.constant;
import com.ucams.common.utils.SystemPath;

/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年7月26日
 * 业务常量类
 */
public class UcamsConstant {
	
	//机构类型
	/**档案馆**/
	public static final String AMS_OFFICE_GRADE_DAG = "1";
	/**报建单位即: 建设单位**/
	public static final String AMS_OFFICE_GRADE_BJDY = "2";
	/**工程项目**/
	public static final String AMS_OFFICE_GRADE_GCXM = "3";
	/**单位工程**/
	public static final String AMS_OFFICE_GRADE_DWGC = "4";
	
	//角色类型(责任主体类型)
	/** 无类型**/
	public static final String AMS_RESBODY_TYPE_NONE= "none";
	/** 建设单位**/
	public static final String AMS_RESBODY_TYPE_JSDY = "security-role";
	/** 施工单位**/
	public static final String AMS_RESBODY_TYPE_SGDY= "user";
	/** 监理单位**/
	public static final String AMS_RESBODY_TYPE_JLDY= "assignment";
	
	//单位工程类型，数据字典unit_project_type
	/** 建筑工程**/
	public static final String AMS_UNITPRO_PROGRAM_TYPE_JZ = "0";
	/** 道路工程**/
	public static final String AMS_UNITPRO_PROGRAM_TYPE_DL = "1";
	/** 轨道工程**/
	public static final String AMS_UNITPRO_PROGRAM_TYPE_GD = "2";
	/**桥涵工程 **/
	public static final String AMS_UNITPRO_PROGRAM_TYPE_QH = "3";
	/** 管线工程**/
	public static final String AMS_UNITPRO_PROGRAM_TYPE_GX = "5";
	
	
	
	//拓展信息方案类型
	/**工程项目 **/
	public static final String AMS_DESPROGRAM_GCXM = "0";
	/** 专业记载**/
	public static final String AMS_DESPROGRAM_ZYJZ  = "1";
	/** 建设工程规划**/
	public static final String AMS_DESPROGRAM_JSGCGH  = "2";
	/** 建设土地规划**/
	public static final String AMS_DESPROGRAM_JSTDGH  = "3";
	/**案卷信息**/
	public static final String AMS_DESPROGRAM_AJXX = "4";
	/**文件拓展**/
	public static final String AMS_DESPROGRAM_WJZL = "5";
	
	//mongo存储ID规则
	/** 单位工程ID **/
	public static final String AMS_MONGO_IDS_UNIT = "ams_unit_pro_info_id";
	/** 项目ID **/
	public static final String AMS_MONGO_IDS_PROJECT = "ams_project_info_id";
	/** 文件ID **/
	public static final String AMS_MONGO_IDS_FILE = "ams_file_info_id";
	/** 案卷ID **/
	public static final String AMS_MONGO_IDS_ARCHIVES = "ams_archives_info_id";
	/** 建设工程规划ID **/
	public static final String AMS_MONGO_IDS_CONSTRUCT= "ams_construct_des_id";
	/** 建设用地规划ID **/
	public static final String AMS_MONGO_IDS_LAND = "ams_land_des_id";
	/** 拓展数据 **/
	public static final String AMS_MONGO_IDS_DATA = "data";
	
	//mongo数据结构Demo
	/**
	 * 建设工程规划拓展数据:
	 * ｛'ams_project_info_id' : '123456',
	 * 	  'ams_construct_des_id' : '465789',
	 * 	  data:{
	 * 			拓展数据
	 * 		}
	 * ｝
	 * 建设用地规划拓展数据:
	 * 	 ｛'ams_project_info_id' : '123456',
	 * 	  'ams_land_des_id' : '465789',
	 * 	  data:{
	 * 			拓展数据
	 * 		}
	 * ｝
	 * 
	 * 
	 */
	
	//mongo数据表名
	/**项目信息-拓展数据**/
	public static final String AMS_MONGO_TABLE_NAME_PROJECT = "ams_project_expand";
	/**单位工程-专业记载**/
	public static final String AMS_MONGO_TABLE_NAME_UNIT = "ams_unit_pro_expand";
	/**文件拓展数据**/
	public static final String AMS_MONGO_TABLE_NAME_FILE = "ams_file_expand";
	/**案卷拓展数据**/
	public static final String AMS_MONGO_TABLE_NAME_ARCHIVES = "ams_archives_expand";
	/**建设工程规划拓展数据**/
	public static final String AMS_MONGO_TABLE_NAME_CONSTRUCT= "ams_construct_expand";
	/**建设用地规划拓展数据**/
	public static final String AMS_MONGO_TABLE_NAME_LAND = "ams_land__expand";
	
	/**离线文件碎片记录**/
	public static final String OfflineFileSplice = "OfflineFileSplice";
	
	/**离线文件包name**/
	public static final String OfflineZipFile = "OfflineZipFile";
	
	/**离线文件包json数据**/
	public static final String OfflineZipJsonData = "OfflineZipJsonData";
	/**离线文件包检查结果**/
	public static final String OfflineZipCheckData = "OfflineZipCheckData";
	
	//资料软件上传文件地址Global.getConfig("file.path");
	public static final String FilePath=SystemPath.getRootPath()+"/fixed/filepath/";
	
	//离线文件上传zip包地址Global.getConfig("file.zipPath");
	public static final String ZipFilePath=SystemPath.getRootPath()+"/fixed/zipPath/";
	
	//离线文件上传zip包解压地址Global.getConfig("file.unzipPath");
	public static final String UnZipFilePath=SystemPath.getRootPath()+"/fixed/unzipPath/";
	
	public static final String UnZipFileStr="/fixed/unzipPath/";
	
	//自定义报表常量
	// -- 方案类型
	/** 平铺式报表 **/
	public static final String AMS_REPORT_PLAN_TYPE_TILE = "0";
	/** 统计式报表 **/
	public static final String AMS_REPORT_PLAN_TYPE_STATISTIC = "1";
	/** 图标式报表 **/
	public static final String AMS_REPORT_PLAN_TYPE_GRAP = "2";
	//-- 报表配置项类型
	/** 展示项 **/
	public static final String AMS_REPORT_CONF_ACCORD_SHOW = "0";
	/** 搜索项 **/
	public static final String AMS_REPORT_CONF_ACCORD_SEARCH = "1";
	//-- 报表检索方式
	/** 匹配 **/
	public static final String AMS_REPORT_CONF_COMPUT_MATCH = "match";
	/** 模糊 **/
	public static final String AMS_REPORT_CONF_COMPUT_FUZZY = "fuzzy";
	/** 时间范围 **/
	public static final String AMS_REPORT_CONF_COMPUT_DATESCOPE = "date_scope";
	/** 数字范围 **/
	public static final String AMS_REPORT_CONF_COMPUT_NUMSCOPE = "num_scope";
	//-- 作用等级
	/** 项目级 **/
	public static final String AMS_REPORT_CONF_EFFECT_PROJECT = "0";
	/** 工程级 **/
	public static final String AMS_REPORT_CONF_EFFECT_UNIT = "1";
	/** 案卷级 **/
	public static final String AMS_REPORT_CONF_EFFECT_ARCHIVES = "2";
	//mongodb as 子集名
	/** 项目集合名 **/
	public static final String AMS_REPORT_CONF_MONGO_AS_PROJECT = "project_docs";
	/** 工程集合名 **/
	public static final String AMS_REPORT_CONF_MONGO_AS_UNIT = "unit_docs";
	/** 案卷集合名 **/
	public static final String AMS_REPORT_CONF_MONGO_AS_ARCHIVES = "archivest_docs";
}
