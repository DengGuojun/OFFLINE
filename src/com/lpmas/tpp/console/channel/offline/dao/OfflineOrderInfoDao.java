package com.lpmas.tpp.console.channel.offline.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.tpp.console.channel.offline.bean.OfflineOrderInfoBean;
import com.lpmas.tpp.console.channel.offline.factory.OfflineDBFactory;

public class OfflineOrderInfoDao {
	private static Logger log = LoggerFactory.getLogger(OfflineOrderInfoDao.class);

	public int insertOfflineOrderInfo(OfflineOrderInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new OfflineDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into offline_order_info ( store_id, country, province, city, region, address, receiver_name, mobile, express_company_id, express_number, order_fact_amount, total_quantity, user_comment, trade_source, order_status, sync_status, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getStoreId());
			ps.setString(2, bean.getCountry());
			ps.setString(3, bean.getProvince());
			ps.setString(4, bean.getCity());
			ps.setString(5, bean.getRegion());
			ps.setString(6, bean.getAddress());
			ps.setString(7, bean.getReceiverName());
			ps.setString(8, bean.getMobile());
			ps.setInt(9, bean.getExpressCompanyId());
			ps.setString(10, bean.getExpressNumber());
			ps.setDouble(11, bean.getOrderFactAmount());
			ps.setDouble(12, bean.getTotalQuantity());
			ps.setString(13, bean.getUserComment());
			ps.setString(14, bean.getTradeSource());
			ps.setString(15, bean.getOrderStatus());
			ps.setString(16, bean.getSyncStatus());
			ps.setInt(17, bean.getStatus());
			ps.setInt(18, bean.getCreateUser());
			ps.setString(19, bean.getMemo());

			result = db.executePstmtInsert();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public int insertOfflineOrderInfo(DBObject db, OfflineOrderInfoBean bean) {
		int result = -1;
		try {
			String sql = "insert into offline_order_info ( store_id, country, province, city, region, address, receiver_name, mobile, express_company_id, express_number, order_fact_amount, total_quantity, user_comment, trade_source, order_status, sync_status, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getStoreId());
			ps.setString(2, bean.getCountry());
			ps.setString(3, bean.getProvince());
			ps.setString(4, bean.getCity());
			ps.setString(5, bean.getRegion());
			ps.setString(6, bean.getAddress());
			ps.setString(7, bean.getReceiverName());
			ps.setString(8, bean.getMobile());
			ps.setInt(9, bean.getExpressCompanyId());
			ps.setString(10, bean.getExpressNumber());
			ps.setDouble(11, bean.getOrderFactAmount());
			ps.setDouble(12, bean.getTotalQuantity());
			ps.setString(13, bean.getUserComment());
			ps.setString(14, bean.getTradeSource());
			ps.setString(15, bean.getOrderStatus());
			ps.setString(16, bean.getSyncStatus());
			ps.setInt(17, bean.getStatus());
			ps.setInt(18, bean.getCreateUser());
			ps.setString(19, bean.getMemo());

			result = db.executePstmtInsert();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		}
		return result;
	}

	public int updateOfflineOrderInfo(OfflineOrderInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new OfflineDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update offline_order_info set store_id = ?, country = ?, province = ?, city = ?, region = ?, address = ?, receiver_name = ?, mobile = ?, express_company_id = ?, express_number = ?, order_fact_amount = ?, total_quantity = ?, user_comment = ?, trade_source = ?, order_status = ?, sync_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where order_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getStoreId());
			ps.setString(2, bean.getCountry());
			ps.setString(3, bean.getProvince());
			ps.setString(4, bean.getCity());
			ps.setString(5, bean.getRegion());
			ps.setString(6, bean.getAddress());
			ps.setString(7, bean.getReceiverName());
			ps.setString(8, bean.getMobile());
			ps.setInt(9, bean.getExpressCompanyId());
			ps.setString(10, bean.getExpressNumber());
			ps.setDouble(11, bean.getOrderFactAmount());
			ps.setDouble(12, bean.getTotalQuantity());
			ps.setString(13, bean.getUserComment());
			ps.setString(14, bean.getTradeSource());
			ps.setString(15, bean.getOrderStatus());
			ps.setString(16, bean.getSyncStatus());
			ps.setInt(17, bean.getStatus());
			ps.setInt(18, bean.getModifyUser());
			ps.setString(19, bean.getMemo());

			ps.setInt(20, bean.getOrderId());

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public int updateOfflineOrderInfo(DBObject db, OfflineOrderInfoBean bean) {
		int result = -1;
		try {
			String sql = "update offline_order_info set store_id = ?, country = ?, province = ?, city = ?, region = ?, address = ?, receiver_name = ?, mobile = ?, express_company_id = ?, express_number = ?, order_fact_amount = ?, total_quantity = ?, user_comment = ?, trade_source = ?, order_status = ?, sync_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where order_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getStoreId());
			ps.setString(2, bean.getCountry());
			ps.setString(3, bean.getProvince());
			ps.setString(4, bean.getCity());
			ps.setString(5, bean.getRegion());
			ps.setString(6, bean.getAddress());
			ps.setString(7, bean.getReceiverName());
			ps.setString(8, bean.getMobile());
			ps.setInt(9, bean.getExpressCompanyId());
			ps.setString(10, bean.getExpressNumber());
			ps.setDouble(11, bean.getOrderFactAmount());
			ps.setDouble(12, bean.getTotalQuantity());
			ps.setString(13, bean.getUserComment());
			ps.setString(14, bean.getTradeSource());
			ps.setString(15, bean.getOrderStatus());
			ps.setString(16, bean.getSyncStatus());
			ps.setInt(17, bean.getStatus());
			ps.setInt(18, bean.getModifyUser());
			ps.setString(19, bean.getMemo());

			ps.setInt(20, bean.getOrderId());

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		}
		return result;
	}

	public OfflineOrderInfoBean getOfflineOrderInfoByKey(int orderId) {
		OfflineOrderInfoBean bean = null;
		DBFactory dbFactory = new OfflineDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from offline_order_info where order_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, orderId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new OfflineOrderInfoBean();
				bean = BeanKit.resultSet2Bean(rs, OfflineOrderInfoBean.class);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e);
			bean = null;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return bean;
	}

	public PageResultBean<OfflineOrderInfoBean> getOfflineOrderInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<OfflineOrderInfoBean> result = new PageResultBean<OfflineOrderInfoBean>();
		DBFactory dbFactory = new OfflineDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from offline_order_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String orderId = condMap.get("orderId");
			if (StringKit.isValid(orderId)) {
				condList.add("order_id = ?");
				paramList.add(orderId);
			}
			String storeId = condMap.get("storeId");
			if (StringKit.isValid(storeId)) {
				condList.add("store_id = ?");
				paramList.add(storeId);
			}
			String tradeSource = condMap.get("tradeSource");
			if (StringKit.isValid(tradeSource)) {
				condList.add("trade_source = ?");
				paramList.add(tradeSource);
			}
			String orderStatus = condMap.get("orderStatus");
			if (StringKit.isValid(orderStatus)) {
				condList.add("order_status = ?");
				paramList.add(orderStatus);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by order_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, OfflineOrderInfoBean.class, pageBean, db);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public List<OfflineOrderInfoBean> getOfflineOrderInfoListByMap(HashMap<String, String> condMap) {
		List<OfflineOrderInfoBean> result = new ArrayList<OfflineOrderInfoBean>();
		DBFactory dbFactory = new OfflineDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from offline_order_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String storeId = condMap.get("storeId");
			if (StringKit.isValid(storeId)) {
				condList.add("store_id = ?");
				paramList.add(storeId);
			}
			String tradeSource = condMap.get("tradeSource");
			if (StringKit.isValid(tradeSource)) {
				condList.add("trade_source = ?");
				paramList.add(tradeSource);
			}
			String tradeOrderId = condMap.get("tradeOrderId");
			if (StringKit.isValid(tradeOrderId)) {
				condList.add("trade_order_id = ?");
				paramList.add(tradeOrderId);
			}
			String orderStatus = condMap.get("orderStatus");
			if (StringKit.isValid(orderStatus)) {
				condList.add("order_status = ?");
				paramList.add(orderStatus);
			}
			String deliveryOrderId = condMap.get("deliveryOrderId");
			if (StringKit.isValid(deliveryOrderId)) {
				condList.add("delivery_order_id = ?");
				paramList.add(deliveryOrderId);
			}
			String expressNumber = condMap.get("expressNumber");
			if (StringKit.isValid(expressNumber)) {
				condList.add("express_number = ?");
				paramList.add(expressNumber);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by order_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, OfflineOrderInfoBean.class, db);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

}
