package com.lpmas.tpp.console.channel.offline.config;

import com.lpmas.framework.config.Constants;

public class OfflineConfig {

	// appId
	public static final String APP_ID = "TPP";
	
	//渠道代码
	public static final String CHANNEL_CODE = "OFFLINE";

	// 根节点父级ID
	public static final int ROOT_PARENT_ID = 0;

	public static final String OFFLINE_PROP_FILE_NAME = Constants.PROP_FILE_PATH + "/offline_config";

	public static final Integer DEFAULT_PAGE_NUM = 1;
	public static final Integer DEFAULT_PAGE_SIZE = 20;

	public static final String ERROR_PAGE = Constants.PAGE_PATH + "common/error_page.jsp";
	public static final String PAGE_PATH = Constants.PAGE_PATH + "offline/";


}
