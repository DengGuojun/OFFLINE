package com.lpmas.tpp.console.channel.offline.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.tpp.console.channel.offline.bean.OfflineOrderInfoBean;
import com.lpmas.tpp.console.channel.offline.dao.OfflineOrderInfoDao;

public class OfflineOrderInfoBusiness {
	public int addOfflineOrderInfo(OfflineOrderInfoBean bean) {
		OfflineOrderInfoDao dao = new OfflineOrderInfoDao();
		return dao.insertOfflineOrderInfo(bean);
	}

	public int updateOfflineOrderInfo(OfflineOrderInfoBean bean) {
		OfflineOrderInfoDao dao = new OfflineOrderInfoDao();
		return dao.updateOfflineOrderInfo(bean);
	}

	public OfflineOrderInfoBean getOfflineOrderInfoByKey(int orderId) {
		OfflineOrderInfoDao dao = new OfflineOrderInfoDao();
		return dao.getOfflineOrderInfoByKey(orderId);
	}

	public PageResultBean<OfflineOrderInfoBean> getOfflineOrderInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		OfflineOrderInfoDao dao = new OfflineOrderInfoDao();
		return dao.getOfflineOrderInfoPageListByMap(condMap, pageBean);
	}

	public List<OfflineOrderInfoBean> getOfflineOrderInfoListByMap(HashMap<String, String> condMap) {
		OfflineOrderInfoDao dao = new OfflineOrderInfoDao();
		return dao.getOfflineOrderInfoListByMap(condMap);
	}

}