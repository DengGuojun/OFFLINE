package com.lpmas.tpp.console.channel.offline.config;

import com.lpmas.framework.util.PropertiesKit;

public class OfflineOrderInfoConfig {

	public static final String DEFAULT_COUNTRY = "中国";

	public static final String ALLOWED_FILE_TYPE = "xls,xlsx";// 设置允许上传的文件类型
	public static final int MAX_SIZE = 5 * 1024 * 1024; // 设置上传文件最大为5M
	// 设置文件保存路径
	public static final String FILE_PATH = PropertiesKit.getBundleProperties(OfflineConfig.OFFLINE_PROP_FILE_NAME,
			"FILE_PATH");

	// 设置文件导入文件模板路径
	public static final String SAMPLE_FILE_PATH = PropertiesKit
			.getBundleProperties(OfflineConfig.OFFLINE_PROP_FILE_NAME, "SAMPLE_PATH");
	// 导入导出文件的分隔符
	public static final String TYPE_SEPARATOR = ",";

}
