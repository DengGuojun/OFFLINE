package com.lpmas.tpp.console.channel.offline.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.excel.ExcelConfig;
import com.lpmas.framework.excel.ExcelWriteBean;
import com.lpmas.framework.excel.WebExcelWriteKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.oms.order.config.SalesOrderStatusConfig;
import com.lpmas.pdm.bean.ProductItemBean;
import com.lpmas.pdm.client.PdmServiceClient;
import com.lpmas.tms.bean.ExpressCompanyInfoBean;
import com.lpmas.tms.client.cache.ExpressCompanyInfoClientCache;
import com.lpmas.tpp.console.channel.offline.bean.OfflineOrderInfoBean;
import com.lpmas.tpp.console.channel.offline.bean.OfflineOrderItemBean;
import com.lpmas.tpp.console.channel.offline.business.OfflineOrderInfoBusiness;
import com.lpmas.tpp.console.channel.offline.business.OfflineOrderItemBusiness;
import com.lpmas.tpp.console.channel.offline.config.OfflineOrderInfoConfig;
import com.lpmas.tpp.console.channel.offline.config.OfflineResource;

@WebServlet("/offline/OfflineOrderInfoExport.do")
public class OfflineOrderInfoExport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OfflineOrderInfoExport() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(OfflineResource.ORDER_INFO, OperationConfig.EXPORT)) {
			return;
		}

		HashMap<String, String> condMap = ParamKit.getParameterMap(request, "storeId,status,orderStatus,tradeSource", "");
		OfflineOrderInfoBusiness infoBusiness = new OfflineOrderInfoBusiness();
		OfflineOrderItemBusiness itemBusiness = new OfflineOrderItemBusiness();
		List<OfflineOrderInfoBean> list = infoBusiness.getOfflineOrderInfoListByMap(condMap);
		if (list.isEmpty()) {
			HttpResponseKit.alertMessage(response, "系统没有相关的数据", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		try {
			ExcelWriteBean excelWriteBean = new ExcelWriteBean();
			String fileType = ExcelConfig.FT_XLSX;
			List<String> headerList = new ArrayList<String>();
			headerList.add("订单id");
			headerList.add("联系人");
			headerList.add("省");
			headerList.add("市");
			headerList.add("区");
			headerList.add("地址");
			headerList.add("电话");
			headerList.add("SKU");
			headerList.add("商品");
			headerList.add("数量");
			headerList.add("价格");
			headerList.add("总价");
			headerList.add("状态");
			headerList.add("物流公司");
			headerList.add("物流编号");

			List<List<Object>> contentLineList = new ArrayList<List<Object>>();
			List<Object> contentRowList = null;
			for (OfflineOrderInfoBean orderBean : list) {
				contentRowList = new ArrayList<Object>();
				contentRowList.add(orderBean.getOrderId());
				contentRowList.add(orderBean.getReceiverName());
				contentRowList.add(orderBean.getProvince());
				contentRowList.add(orderBean.getCity());
				contentRowList.add(orderBean.getRegion());
				contentRowList.add(orderBean.getAddress());
				contentRowList.add(orderBean.getMobile());
				// 获取对应的OrderItem
				String tempSku = "";
				String tempProductName = "";
				String tempQuantity = "";
				String tempItemFactAmount = "";
				String tempCompanyName = "";
				String tempExpressNumber = "";
				HashMap<String, String> itemCondMap = new HashMap<String, String>();
				itemCondMap.put("orderId", String.valueOf(orderBean.getOrderId()));
				itemCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
				List<OfflineOrderItemBean> orderItemList = itemBusiness.getOfflineOrderItemListByMap(itemCondMap);
				PdmServiceClient pdmClient = new PdmServiceClient();
				HashMap<String, ProductItemBean> productItemMap = new HashMap<String, ProductItemBean>();
				for (OfflineOrderItemBean itemBean : orderItemList) {
					if (!tempSku.isEmpty()) {
						tempSku = tempSku + OfflineOrderInfoConfig.TYPE_SEPARATOR + itemBean.getProductItemNumber();
					} else {
						tempSku = tempSku + itemBean.getProductItemNumber();
					}

					if (productItemMap.containsKey(itemBean.getProductItemNumber())) {
						if (!tempProductName.isEmpty()) {
							tempProductName = tempProductName + OfflineOrderInfoConfig.TYPE_SEPARATOR
									+ productItemMap.get(itemBean.getProductItemNumber()).getItemName();
						} else {
							tempProductName = tempProductName
									+ productItemMap.get(itemBean.getProductItemNumber()).getItemName();
						}
					} else {
						ProductItemBean productItemBean = pdmClient
								.getProductItemByNumber(itemBean.getProductItemNumber());
						if (productItemBean != null) {
							productItemMap.put(itemBean.getProductItemNumber(), productItemBean);
							if (!tempProductName.isEmpty()) {
								tempProductName = tempProductName + OfflineOrderInfoConfig.TYPE_SEPARATOR
										+ productItemBean.getItemName();
							} else {
								tempProductName = tempProductName + productItemBean.getItemName();
							}
						} else {
							if (!tempProductName.isEmpty()) {
								tempProductName = tempProductName + OfflineOrderInfoConfig.TYPE_SEPARATOR
										+ itemBean.getProductName();
							} else {
								tempProductName = tempProductName + itemBean.getProductName();
							}
						}
					}
					if (!tempQuantity.isEmpty()) {
						tempQuantity = tempQuantity + OfflineOrderInfoConfig.TYPE_SEPARATOR + itemBean.getQuantity();
					} else {
						tempQuantity = tempQuantity + itemBean.getQuantity();
					}
					if (!tempItemFactAmount.isEmpty()) {
						tempItemFactAmount = tempItemFactAmount + OfflineOrderInfoConfig.TYPE_SEPARATOR
								+ itemBean.getItemFactAmount();
					} else {
						tempItemFactAmount = tempItemFactAmount + itemBean.getItemFactAmount();
					}
					ExpressCompanyInfoClientCache expressCompanyInfoClientCache = new ExpressCompanyInfoClientCache();
					ExpressCompanyInfoBean expressCompanyBean = expressCompanyInfoClientCache
							.getExpressCompanyInfoByKey(itemBean.getExpressCompanyId());
					if (!tempCompanyName.isEmpty()) {
						tempCompanyName = tempCompanyName + OfflineOrderInfoConfig.TYPE_SEPARATOR
								+ (expressCompanyBean != null ? expressCompanyBean.getCompanyName() : "");
					} else {
						tempCompanyName = tempCompanyName
								+ (expressCompanyBean != null ? expressCompanyBean.getCompanyName() : "");
					}
					if (!tempExpressNumber.isEmpty()) {
						tempExpressNumber = tempExpressNumber + OfflineOrderInfoConfig.TYPE_SEPARATOR
								+ itemBean.getExpressNumber();
					} else {
						tempExpressNumber = tempExpressNumber + itemBean.getExpressNumber();
					}

				}
				contentRowList.add(tempSku);
				contentRowList.add(tempProductName);
				contentRowList.add(tempQuantity);
				contentRowList.add(tempItemFactAmount);
				contentRowList.add(orderBean.getOrderFactAmount());
				contentRowList.add(SalesOrderStatusConfig.ORDER_STATUS_MAP.get(orderBean.getOrderStatus()));
				contentRowList.add(tempCompanyName);
				contentRowList.add(tempExpressNumber);
				contentLineList.add(contentRowList);
			}
			excelWriteBean.setFileName("线下订单");
			excelWriteBean.setFileType(fileType);
			excelWriteBean.setSheetName("发货记录");
			excelWriteBean.setHeaderList(headerList);
			excelWriteBean.setContentList(contentLineList);

			WebExcelWriteKit webExcelWriteKit = new WebExcelWriteKit();
			webExcelWriteKit.outputExcel(excelWriteBean, request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
