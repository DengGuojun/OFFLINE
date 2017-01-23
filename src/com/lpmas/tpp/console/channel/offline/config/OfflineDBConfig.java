package com.lpmas.tpp.console.channel.offline.config;

import com.lpmas.framework.util.PropertiesKit;

public class OfflineDBConfig {

	public static String DB_LINK_OFFLINE_W = PropertiesKit.getBundleProperties(OfflineConfig.OFFLINE_PROP_FILE_NAME,
			"DB_LINK_OFFLINE_W");

	public static String DB_LINK_OFFLINE_R = PropertiesKit.getBundleProperties(OfflineConfig.OFFLINE_PROP_FILE_NAME,
			"DB_LINK_OFFLINE_R");
}
