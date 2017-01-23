package com.lpmas.tpp.console.channel.offline.bean;

import java.sql.Timestamp;

import com.lpmas.framework.annotation.FieldTag;

public class OfflineOrderItemBean {
	@FieldTag(name = "明细ID")
	private int orderItemId = 0;
	@FieldTag(name = "订单ID")
	private int orderId = 0;
	@FieldTag(name = "商品项编码")
	private String productItemNumber = "";
	@FieldTag(name = "商品名称，渠道上的名称")
	private String productName = "";
	@FieldTag(name = "快递公司")
	private int expressCompanyId = 0;
	@FieldTag(name = "快递单号")
	private String expressNumber = "";
	@FieldTag(name = "数量")
	private double quantity = 0;
	@FieldTag(name = "实际单价")
	private double factPrice = 0;
	@FieldTag(name = "单项实价")
	private double itemFactAmount = 0;
	@FieldTag(name = "细项状态")
	private String orderItemStatus = "";
	@FieldTag(name = "状态")
	private int status = 0;
	@FieldTag(name = "创建时间")
	private Timestamp createTime = null;
	@FieldTag(name = "创建用户")
	private int createUser = 0;
	@FieldTag(name = "修改时间")
	private Timestamp modifyTime = null;
	@FieldTag(name = "修改用户")
	private int modifyUser = 0;
	@FieldTag(name = "备注")
	private String memo = "";

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getProductItemNumber() {
		return productItemNumber;
	}

	public void setProductItemNumber(String productItemNumber) {
		this.productItemNumber = productItemNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getExpressCompanyId() {
		return expressCompanyId;
	}

	public void setExpressCompanyId(int expressCompanyId) {
		this.expressCompanyId = expressCompanyId;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getFactPrice() {
		return factPrice;
	}

	public void setFactPrice(double factPrice) {
		this.factPrice = factPrice;
	}

	public double getItemFactAmount() {
		return itemFactAmount;
	}

	public void setItemFactAmount(double itemFactAmount) {
		this.itemFactAmount = itemFactAmount;
	}

	public String getOrderItemStatus() {
		return orderItemStatus;
	}

	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(int modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}