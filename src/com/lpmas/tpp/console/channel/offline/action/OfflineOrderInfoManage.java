package com.lpmas.tpp.console.channel.offline.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.tms.bean.ExpressCompanyInfoBean;
import com.lpmas.tms.client.cache.ExpressCompanyInfoClientCache;
import com.lpmas.tpp.console.channel.offline.bean.OfflineOrderInfoBean;
import com.lpmas.tpp.console.channel.offline.bean.OfflineOrderItemBean;
import com.lpmas.tpp.console.channel.offline.business.OfflineOrderInfoBusiness;
import com.lpmas.tpp.console.channel.offline.business.OfflineOrderItemBusiness;
import com.lpmas.tpp.console.channel.offline.config.OfflineConfig;
import com.lpmas.tpp.console.channel.offline.config.OfflineResource;

@WebServlet("/offline/OfflineOrderInfoManage.do")
public class OfflineOrderInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OfflineOrderInfoManage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		int orderId = ParamKit.getIntParameter(request, "orderId", 0);
		if (!adminUserHelper.checkPermission(OfflineResource.ORDER_INFO, OperationConfig.SEARCH)) {
			return;
		}

		OfflineOrderInfoBusiness business = new OfflineOrderInfoBusiness();
		OfflineOrderInfoBean bean = business.getOfflineOrderInfoByKey(orderId);
		if (bean == null) {
			HttpResponseKit.alertMessage(response, "订单信息错误", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		OfflineOrderItemBusiness itemBusiness = new OfflineOrderItemBusiness();
		// 获取对应的OrderItem
		HashMap<String, String> itemCondMap = new HashMap<String, String>();
		itemCondMap.put("orderId", String.valueOf(bean.getOrderId()));
		itemCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
		List<OfflineOrderItemBean> itemBeanList = itemBusiness.getOfflineOrderItemListByMap(itemCondMap);
		ExpressCompanyInfoClientCache expressCompanyInfoClientCache = new ExpressCompanyInfoClientCache();
		ExpressCompanyInfoBean expressCompanyBean = expressCompanyInfoClientCache
				.getExpressCompanyInfoByKey(bean.getExpressCompanyId());
		// 获取物流公司名称
		HashMap<Integer, String> expressCompanyNameMap = new HashMap<Integer, String>();
		for (OfflineOrderItemBean itemBean : itemBeanList) {
			ExpressCompanyInfoBean expressItemCompanyBean = expressCompanyInfoClientCache
					.getExpressCompanyInfoByKey(itemBean.getExpressCompanyId());
			expressCompanyNameMap.put(itemBean.getOrderItemId(),
					expressItemCompanyBean != null ? expressItemCompanyBean.getCompanyName() : "");
		}

		request.setAttribute("ExpressCompanyNameMap", expressCompanyNameMap);
		request.setAttribute("ExpressCompanyName",
				expressCompanyBean != null ? expressCompanyBean.getCompanyName() : "");
		request.setAttribute("OfflineOrder", bean);
		request.setAttribute("ItemBeanList", itemBeanList);
		request.setAttribute("AdminUserHelper", adminUserHelper);

		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(OfflineConfig.PAGE_PATH + "OfflineOrderInfoManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
