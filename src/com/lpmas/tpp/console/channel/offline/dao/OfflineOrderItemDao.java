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
import com.lpmas.tpp.console.channel.offline.bean.OfflineOrderItemBean;
import com.lpmas.tpp.console.channel.offline.factory.OfflineDBFactory;

public class OfflineOrderItemDao {
	private static Logger log = LoggerFactory.getLogger(OfflineOrderItemDao.class);

	public int insertOfflineOrderItem(OfflineOrderItemBean bean) {
		int result = -1;
		DBFactory dbFactory = new OfflineDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into offline_order_item ( order_id, product_item_number, product_name, express_company_id, express_number, quantity, fact_price, item_fact_amount, order_item_status, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getOrderId());
			ps.setString(2, bean.getProductItemNumber());
			ps.setString(3, bean.getProductName());
			ps.setInt(4, bean.getExpressCompanyId());
			ps.setString(5, bean.getExpressNumber());
			ps.setDouble(6, bean.getQuantity());
			ps.setDouble(7, bean.getFactPrice());
			ps.setDouble(8, bean.getItemFactAmount());
			ps.setString(9, bean.getOrderItemStatus());
			ps.setInt(10, bean.getStatus());
			ps.setInt(11, bean.getCreateUser());
			ps.setString(12, bean.getMemo());

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

	public int insertOfflineOrderItem(DBObject db, OfflineOrderItemBean bean) {
		int result = -1;
		try {
			String sql = "insert into offline_order_item ( order_id, product_item_number, product_name, express_company_id, express_number, quantity, fact_price, item_fact_amount, order_item_status, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getOrderId());
			ps.setString(2, bean.getProductItemNumber());
			ps.setString(3, bean.getProductName());
			ps.setInt(4, bean.getExpressCompanyId());
			ps.setString(5, bean.getExpressNumber());
			ps.setDouble(6, bean.getQuantity());
			ps.setDouble(7, bean.getFactPrice());
			ps.setDouble(8, bean.getItemFactAmount());
			ps.setString(9, bean.getOrderItemStatus());
			ps.setInt(10, bean.getStatus());
			ps.setInt(11, bean.getCreateUser());
			ps.setString(12, bean.getMemo());

			result = db.executePstmtInsert();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		}
		return result;
	}

	public int updateOfflineOrderItem(OfflineOrderItemBean bean) {
		int result = -1;
		DBFactory dbFactory = new OfflineDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update offline_order_item set order_id = ?, product_item_number = ?, product_name = ?, express_company_id = ?, express_number = ?, quantity = ?, fact_price = ?, item_fact_amount = ?, order_item_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where order_item_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getOrderId());
			ps.setString(2, bean.getProductItemNumber());
			ps.setString(3, bean.getProductName());
			ps.setInt(4, bean.getExpressCompanyId());
			ps.setString(5, bean.getExpressNumber());
			ps.setDouble(6, bean.getQuantity());
			ps.setDouble(7, bean.getFactPrice());
			ps.setDouble(8, bean.getItemFactAmount());
			ps.setString(9, bean.getOrderItemStatus());
			ps.setInt(10, bean.getStatus());
			ps.setInt(11, bean.getModifyUser());
			ps.setString(12, bean.getMemo());

			ps.setInt(13, bean.getOrderItemId());

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

	public int updateOfflineOrderItem(DBObject db, OfflineOrderItemBean bean) {
		int result = -1;
		try {
			String sql = "update offline_order_item set order_id = ?, product_item_number = ?, product_name = ?, express_company_id = ?, express_number = ?, quantity = ?, fact_price = ?, item_fact_amount = ?, order_item_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where order_item_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getOrderId());
			ps.setString(2, bean.getProductItemNumber());
			ps.setString(3, bean.getProductName());
			ps.setInt(4, bean.getExpressCompanyId());
			ps.setString(5, bean.getExpressNumber());
			ps.setDouble(6, bean.getQuantity());
			ps.setDouble(7, bean.getFactPrice());
			ps.setDouble(8, bean.getItemFactAmount());
			ps.setString(9, bean.getOrderItemStatus());
			ps.setInt(10, bean.getStatus());
			ps.setInt(11, bean.getModifyUser());
			ps.setString(12, bean.getMemo());

			ps.setInt(13, bean.getOrderItemId());

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		}
		return result;
	}

	public OfflineOrderItemBean getOfflineOrderItemByKey(int orderItemId) {
		OfflineOrderItemBean bean = null;
		DBFactory dbFactory = new OfflineDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from offline_order_item where order_item_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, orderItemId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new OfflineOrderItemBean();
				bean = BeanKit.resultSet2Bean(rs, OfflineOrderItemBean.class);
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

	public PageResultBean<OfflineOrderItemBean> getOfflineOrderItemPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<OfflineOrderItemBean> result = new PageResultBean<OfflineOrderItemBean>();
		DBFactory dbFactory = new OfflineDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from offline_order_item";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by order_item_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, OfflineOrderItemBean.class,
					pageBean, db);
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

	public List<OfflineOrderItemBean> getOfflineOrderItemListByMap(HashMap<String, String> condMap) {
		List<OfflineOrderItemBean> result = new ArrayList<OfflineOrderItemBean>();
		DBFactory dbFactory = new OfflineDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from offline_order_item";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String storeId = condMap.get("storeId");
			if (StringKit.isValid(storeId)) {
				condList.add("store_id = ?");
				paramList.add(storeId);
			}
			String orderId = condMap.get("orderId");
			if (StringKit.isValid(orderId)) {
				condList.add("order_id = ?");
				paramList.add(orderId);
			}
			String productItemNumber = condMap.get("productItemNumber");
			if (StringKit.isValid(productItemNumber)) {
				condList.add("product_item_number = ?");
				paramList.add(productItemNumber);
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
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, OfflineOrderItemBean.class,
					db);
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
