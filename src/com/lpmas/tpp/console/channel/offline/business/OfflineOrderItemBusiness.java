package com.lpmas.tpp.console.channel.offline.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.tpp.console.channel.offline.bean.OfflineOrderItemBean;
import com.lpmas.tpp.console.channel.offline.dao.OfflineOrderItemDao;

public class OfflineOrderItemBusiness {
	public int addOfflineOrderItem(OfflineOrderItemBean bean) {
		OfflineOrderItemDao dao = new OfflineOrderItemDao();
		return dao.insertOfflineOrderItem(bean);
	}

	public int updateOfflineOrderItem(OfflineOrderItemBean bean) {
		OfflineOrderItemDao dao = new OfflineOrderItemDao();
		return dao.updateOfflineOrderItem(bean);
	}

	public OfflineOrderItemBean getOfflineOrderItemByKey(int orderItemId) {
		OfflineOrderItemDao dao = new OfflineOrderItemDao();
		return dao.getOfflineOrderItemByKey(orderItemId);
	}

	public PageResultBean<OfflineOrderItemBean> getOfflineOrderItemPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		OfflineOrderItemDao dao = new OfflineOrderItemDao();
		return dao.getOfflineOrderItemPageListByMap(condMap, pageBean);
	}

	public List<OfflineOrderItemBean> getOfflineOrderItemListByMap(HashMap<String, String> condMap) {
		OfflineOrderItemDao dao = new OfflineOrderItemDao();
		return dao.getOfflineOrderItemListByMap(condMap);
	}

}