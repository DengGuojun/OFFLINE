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
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.system.bean.StoreInfoBean;
import com.lpmas.system.client.SystemServiceClient;
import com.lpmas.tms.bean.ExpressCompanyInfoBean;
import com.lpmas.tms.client.cache.ExpressCompanyInfoClientCache;
import com.lpmas.tpp.console.channel.offline.bean.OfflineOrderInfoBean;
import com.lpmas.tpp.console.channel.offline.business.OfflineOrderInfoBusiness;
import com.lpmas.tpp.console.channel.offline.config.OfflineConfig;
import com.lpmas.tpp.console.channel.offline.config.OfflineResource;

/**
 * Servlet implementation class OfflineOrderInfoList
 */
@WebServlet("/offline/OfflineOrderInfoList.do")
public class OfflineOrderInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OfflineOrderInfoList() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(OfflineResource.ORDER_INFO, OperationConfig.SEARCH)) {
			return;
		}

		int pageNum = ParamKit.getIntParameter(request, "pageNum", OfflineConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", OfflineConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);
		HashMap<String, String> condMap = ParamKit.getParameterMap(request, "orderId,storeId,tradeSource,expressNumber,orderStatus", "");
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		OfflineOrderInfoBusiness business = new OfflineOrderInfoBusiness();
		PageResultBean<OfflineOrderInfoBean> result = business.getOfflineOrderInfoPageListByMap(condMap, pageBean);
		
		
		//获取物流公司名称
		HashMap<Integer,String> expressCompanyNameMap = new HashMap<Integer,String>();
		for(OfflineOrderInfoBean bean : result.getRecordList()){
			ExpressCompanyInfoClientCache expressCompanyInfoClientCache = new ExpressCompanyInfoClientCache();
			ExpressCompanyInfoBean expressCompanyBean = expressCompanyInfoClientCache.getExpressCompanyInfoByKey(bean
					.getExpressCompanyId());
			expressCompanyNameMap.put(bean.getOrderId(), expressCompanyBean != null ? expressCompanyBean.getCompanyName(): "");
		}
		
		
		//获取商店列表
		SystemServiceClient systemClient = new SystemServiceClient();
		List<StoreInfoBean> storeList = systemClient.getStoreInfoListByChannelCode(OfflineConfig.CHANNEL_CODE);
		
		
		request.setAttribute("OfflineOrderList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("StoreList", storeList);
		request.setAttribute("ExpressCompanyNameMap", expressCompanyNameMap);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = request.getRequestDispatcher(OfflineConfig.PAGE_PATH + "OfflineOrderInfoList.jsp");
		rd.forward(request, response);
	}

}
