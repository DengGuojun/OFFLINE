package com.lpmas.tpp.console.channel.offline.handler;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.constant.admin.AdminUserConfig;
import com.lpmas.constant.currency.CurrencyConfig;
import com.lpmas.constant.info.SyncStatusConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.excel.ExcelReadResultBean;
import com.lpmas.framework.tools.common.bean.ImportResultBean;
import com.lpmas.framework.util.NumberKit;
import com.lpmas.framework.util.NumeralOperationKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.oms.client.OmsServiceClient;
import com.lpmas.oms.client.bean.request.SalesOrderItemRequestBean;
import com.lpmas.oms.client.bean.request.SalesOrderRequestBean;
import com.lpmas.oms.client.bean.response.DeliveryInfoSyncItemResponseBean;
import com.lpmas.oms.client.bean.response.DeliveryInfoSyncResponseBean;
import com.lpmas.oms.order.config.DeliveryMethodConfig;
import com.lpmas.oms.order.config.SalesOrderConfig;
import com.lpmas.oms.order.config.SalesOrderStatusConfig;
import com.lpmas.pdm.bean.ProductItemBean;
import com.lpmas.pdm.client.PdmServiceClient;
import com.lpmas.tpp.console.channel.offline.bean.OfflineOrderInfoBean;
import com.lpmas.tpp.console.channel.offline.bean.OfflineOrderItemBean;
import com.lpmas.tpp.console.channel.offline.business.OfflineOrderInfoBusiness;
import com.lpmas.tpp.console.channel.offline.business.OfflineOrderItemBusiness;
import com.lpmas.tpp.console.channel.offline.config.OfflineOrderInfoConfig;
import com.lpmas.tpp.console.channel.offline.dao.OfflineOrderInfoDao;
import com.lpmas.tpp.console.channel.offline.dao.OfflineOrderItemDao;
import com.lpmas.tpp.console.channel.offline.factory.OfflineDBFactory;

public class OfflineOrderInfoHandler {

	private static Logger log = LoggerFactory.getLogger(OfflineOrderInfoHandler.class);

	private int addOfflineOrderInfo(DBObject db, OfflineOrderInfoBean bean) {
		OfflineOrderInfoDao dao = new OfflineOrderInfoDao();
		return dao.insertOfflineOrderInfo(db, bean);
	}

	private int updateOfflineOrderInfo(DBObject db, OfflineOrderInfoBean bean) {
		OfflineOrderInfoDao dao = new OfflineOrderInfoDao();
		return dao.updateOfflineOrderInfo(db, bean);
	}

	private int addOfflineOrderItem(DBObject db, OfflineOrderItemBean bean) {
		OfflineOrderItemDao dao = new OfflineOrderItemDao();
		return dao.insertOfflineOrderItem(db, bean);
	}

	private int updateOfflineOrderItem(DBObject db, OfflineOrderItemBean bean) {
		OfflineOrderItemDao dao = new OfflineOrderItemDao();
		return dao.updateOfflineOrderItem(db, bean);
	}

	public ImportResultBean importOrderInfoList(int userId, int storeId, String tradeSource, ExcelReadResultBean excelReadResultBean)
			throws Exception {

		ImportResultBean resultBean = new ImportResultBean();
		List<String> successMsgList = new ArrayList<String>();
		List<String> errorMsgList = new ArrayList<String>();
		int successQuantity = 0;

		DBFactory dbFactory = new OfflineDBFactory();
		DBObject db = null;
		List<List<String>> contentList = excelReadResultBean.getContentList();
		for (int i = 1; i < contentList.size(); i++) {
			List<String> content = contentList.get(i);
			// 通过第八列的skuId是否有数据来确认次行是否读取，否则结束循环。
			if (content.size() < 2 || !StringKit.isValid(content.get(0)) || !StringKit.isValid(content.get(1))) {
				break;
			}
			try {
				db = dbFactory.getDBObjectW();
				db.beginTransition();// 事务开始
				// 如果商品项和数量不一致,则跳过
				String[] strSku = content.get(7).split(OfflineOrderInfoConfig.TYPE_SEPARATOR);
				String[] strQuantity = content.get(8).split(OfflineOrderInfoConfig.TYPE_SEPARATOR);
				String[] strPrice = content.get(9).split(OfflineOrderInfoConfig.TYPE_SEPARATOR);
				if (strSku.length != strQuantity.length || strSku.length != strPrice.length || strQuantity.length != strPrice.length) {
					errorMsgList.add("[" + content.get(0) + "]的商品项,数量,价格不一致，请校验正确的SKU,数量和价格");
					continue;
				}
				// 如果商品项不存在或数量,价格不正确，则跳过此订单导入
				boolean flag = false;
				double totalQuantity = 0;
				double totalPrice = 0;
				HashMap<String, ProductItemBean> productItemMap = new HashMap<String, ProductItemBean>();
				for (int j = 0; j < strSku.length; ++j) {
					String productItemNumber = strSku[j];
					PdmServiceClient pdmClient = new PdmServiceClient();
					ProductItemBean productItemBean = pdmClient.getProductItemByNumber(productItemNumber);
					if (productItemBean.getItemId() == 0) {
						errorMsgList.add("[" + content.get(0) + "]的商品" + strSku[j] + "不存在，请填写正确的SKU,多个值时用英文的,分隔");
						flag = true;
						continue;
					}
					if (strQuantity[j].isEmpty() || !isDouble(strQuantity[j])) {
						errorMsgList.add("[" + content.get(0) + "]的商品" + strSku[j] + "数量格式有误，请填写正确的数量,多个值时用英文的,分隔");
						flag = true;
						continue;
					}
					if (strPrice[j].isEmpty() || !isDouble(strPrice[j])) {
						errorMsgList.add("[" + content.get(0) + "]的商品" + strSku[j] + "价格格式有误，请填写正确的价格,多个值时用英文的,分隔");
						flag = true;
						continue;
					}
					totalQuantity = totalQuantity + Double.parseDouble(strQuantity[j]);
					totalPrice = totalPrice + NumeralOperationKit.multiply(Double.parseDouble(strQuantity[j]), Double.parseDouble(strPrice[j]));
					productItemMap.put(strSku[j], productItemBean);
				}
				if (!NumberKit.isAllDigit(content.get(1))) {
					errorMsgList.add("[" + content.get(0) + "]手机号码格式有误");
					continue;
				}
				if (!isDouble(content.get(10))) {
					errorMsgList.add("[" + content.get(0) + "]总价格式有误");
					continue;
				}
				System.out.println(totalPrice +",,"+Double.parseDouble(content.get(10)));
				if (totalPrice != Double.parseDouble(content.get(10))) {
					errorMsgList.add("[" + content.get(0) + "]的商品项,总价不一致，请校验正确的数量和价格并计算总价");
					continue;
				}
				if (flag) {
					continue;
				} else {
					// 导入Info信息
					OfflineOrderInfoBean orderInfoBean = getOfflineOrderInfoBean(content, storeId, tradeSource, userId, totalQuantity);
					int orderId = addOfflineOrderInfo(db, orderInfoBean);
					orderInfoBean.setOrderId(orderId);
					List<SalesOrderItemRequestBean> salesOrderItemList = new ArrayList<SalesOrderItemRequestBean>();
					for (int j = 0; j < strSku.length; ++j) {
						// 导入Item信息
						OfflineOrderItemBean orderItemBean = getOfflineOrderItemBean(productItemMap.get(strSku[j]), orderId, userId, strQuantity[j],
								strPrice[j]);
						int orderItemId = addOfflineOrderItem(db, orderItemBean);
						orderItemBean.setOrderItemId(orderItemId);

						SalesOrderItemRequestBean soItemRequestBean = getSalesOrderItemRequestBean(orderItemBean, productItemMap.get(strSku[j]));
						salesOrderItemList.add(soItemRequestBean);
					}
					db.commit();// 事务提交
					// 把销售订单的消息推给OMS
					OmsServiceClient client = new OmsServiceClient();
					SalesOrderRequestBean soRequestBean = getSalesOrderRequestBean(orderInfoBean, salesOrderItemList);
					try {
						client.sendSalesOrder(soRequestBean);
					} catch (Exception e) {
						log.error(e.getMessage());
						throw e;
					}
					successQuantity++;
				}
			} catch (Exception e) {
				db.rollback();// 事务回滚
				errorMsgList.add("[" + content.get(0) + "]导入失败");
				log.error("", e);
			} finally {
				try {
					db.close();
				} catch (SQLException sqle) {
					log.error("", sqle);
				}
			}
		}

		successMsgList.add("成功导入" + successQuantity + "条数据");
		resultBean.setSuccessMsgList(successMsgList);
		resultBean.setErrorMsgList(errorMsgList);
		return resultBean;
	}

	public void syncDeliveryInfoResponse(DeliveryInfoSyncResponseBean responseBean) throws Exception {
		DBFactory dbFactory = new OfflineDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			db.beginTransition();// 事务开始

			OfflineOrderInfoBusiness infoBusiness = new OfflineOrderInfoBusiness();
			OfflineOrderItemBusiness itemBusiness = new OfflineOrderItemBusiness();
			OfflineOrderInfoBean orderBean = infoBusiness.getOfflineOrderInfoByKey(Integer.valueOf(responseBean.getOuterOrderId()));
			if (orderBean != null) {
				// 同步OrderItem的信息
				List<DeliveryInfoSyncItemResponseBean> itemResponseList = responseBean.getDeliveryInfoSyncItemList();
				for (DeliveryInfoSyncItemResponseBean itemResponseBean : itemResponseList) {
					OfflineOrderItemBean itemBean = itemBusiness.getOfflineOrderItemByKey(Integer.valueOf(itemResponseBean.getOuterOrderItemId()));
					if (itemBean != null) {
						itemBean.setOrderItemStatus(itemResponseBean.getSoItemStatus());
						itemBean.setExpressCompanyId(itemResponseBean.getTransporterId());
						itemBean.setExpressNumber(itemResponseBean.getTransportNumber());
						itemBean.setModifyUser(AdminUserConfig.SYSTEM_USER);
						updateOfflineOrderItem(db, itemBean);
						// 同步OrderInfo的信息,物流信息在ItemResponseBean里
						orderBean.setOrderStatus(responseBean.getSoStatus());
						orderBean.setModifyUser(AdminUserConfig.SYSTEM_USER);
					}
				}
				updateOfflineOrderInfo(db, orderBean);
			}

			db.commit();// 事务提交
		} catch (Exception e) {
			db.rollback();// 事务回滚
			log.error("", e);
			throw e;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
	}

	private OfflineOrderInfoBean getOfflineOrderInfoBean(List<String> content, int storeId, String tradeSource, int userId, double totalQuantity) {
		OfflineOrderInfoBean orderInfoBean = new OfflineOrderInfoBean();
		orderInfoBean.setStoreId(storeId);
		orderInfoBean.setTradeSource(tradeSource);
		orderInfoBean.setCountry(OfflineOrderInfoConfig.DEFAULT_COUNTRY);
		orderInfoBean.setProvince(content.get(2));
		orderInfoBean.setCity(content.get(3));
		orderInfoBean.setRegion(content.get(4));
		orderInfoBean.setAddress(content.get(5));
		orderInfoBean.setReceiverName(content.get(0));
		orderInfoBean.setMobile(new BigDecimal(content.get(1)).toPlainString());
		orderInfoBean.setTotalQuantity(totalQuantity);
		orderInfoBean.setOrderFactAmount(Double.valueOf(content.get(10)));
		orderInfoBean.setUserComment(content.get(6));
		orderInfoBean.setOrderStatus(SalesOrderStatusConfig.ORDS_PAYED);
		orderInfoBean.setSyncStatus(SyncStatusConfig.SYNCS_SENT);
		orderInfoBean.setStatus(Constants.STATUS_VALID);
		orderInfoBean.setCreateUser(userId);
		return orderInfoBean;
	}

	private OfflineOrderItemBean getOfflineOrderItemBean(ProductItemBean productItemBean, int orderId, int userId, String quantity, String price) {
		OfflineOrderItemBean orderItemBean = new OfflineOrderItemBean();
		orderItemBean.setOrderId(orderId);
		orderItemBean.setProductItemNumber(productItemBean.getItemNumber());
		orderItemBean.setQuantity(Double.valueOf(quantity));
		orderItemBean.setFactPrice(Double.valueOf(price));
		orderItemBean.setItemFactAmount(NumeralOperationKit.multiply(Double.valueOf(price), Double.valueOf(quantity)));
		orderItemBean.setOrderItemStatus(SalesOrderStatusConfig.ORDS_PAYED);
		orderItemBean.setStatus(Constants.STATUS_VALID);
		orderItemBean.setCreateUser(userId);
		return orderItemBean;
	}

	private SalesOrderItemRequestBean getSalesOrderItemRequestBean(OfflineOrderItemBean orderItemBean, ProductItemBean productItemBean) {
		SalesOrderItemRequestBean soItemRequestBean = new SalesOrderItemRequestBean();
		soItemRequestBean.setOuterOrderItemId(String.valueOf(orderItemBean.getOrderItemId()));
		soItemRequestBean.setProductId(productItemBean.getProductId());
		soItemRequestBean.setProductItemId(productItemBean.getItemId());
		soItemRequestBean.setProductItemNumber(productItemBean.getItemNumber());
		soItemRequestBean.setProductName(productItemBean.getItemName());
		soItemRequestBean.setPurchaseType(SalesOrderConfig.PURT_NORMAL);
		soItemRequestBean.setDeliveryMethod(DeliveryMethodConfig.DM_WAREHOUSE);
		soItemRequestBean.setCurrency(CurrencyConfig.CUR_CNY);
		soItemRequestBean.setQuantity(orderItemBean.getQuantity());
		soItemRequestBean.setListPrice(orderItemBean.getFactPrice());
		soItemRequestBean.setOfferPrice(orderItemBean.getFactPrice());
		soItemRequestBean.setFactPrice(orderItemBean.getFactPrice());
		soItemRequestBean.setItemAmount(orderItemBean.getItemFactAmount());
		soItemRequestBean.setItemFactAmount(orderItemBean.getItemFactAmount());
		return soItemRequestBean;

	}

	private SalesOrderRequestBean getSalesOrderRequestBean(OfflineOrderInfoBean orderInfoBean, List<SalesOrderItemRequestBean> salesOrderItemList) {
		SalesOrderRequestBean soRequestBean = new SalesOrderRequestBean();
		soRequestBean.setOuterOrderId(String.valueOf(orderInfoBean.getOrderId()));
		soRequestBean.setStoreId(orderInfoBean.getStoreId());
		soRequestBean.setTradeSource(orderInfoBean.getTradeSource());
		soRequestBean.setCountry(orderInfoBean.getCountry());
		soRequestBean.setProvince(orderInfoBean.getProvince());
		soRequestBean.setCity(orderInfoBean.getCity());
		soRequestBean.setRegion(orderInfoBean.getRegion());
		soRequestBean.setAddress(orderInfoBean.getAddress());
		soRequestBean.setReceiverName(orderInfoBean.getReceiverName());
		soRequestBean.setMobile(orderInfoBean.getMobile());
		soRequestBean.setCurrency(CurrencyConfig.CUR_CNY);
		soRequestBean.setSoAmount(orderInfoBean.getOrderFactAmount());
		soRequestBean.setSoFactAmount(orderInfoBean.getOrderFactAmount());
		soRequestBean.setTotalQuantity(orderInfoBean.getTotalQuantity());
		soRequestBean.setSoType(SalesOrderConfig.ORDT_NORMAL);
		soRequestBean.setSalesOrderItemList(salesOrderItemList);
		return soRequestBean;
	}

	public boolean isDouble(String str) {
		return str.matches("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$") || str.matches("[0-9]+");
	}

}
