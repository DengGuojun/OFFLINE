package com.lpmas.tpp.console.channel.offline.bean;

import java.sql.Timestamp;

import com.lpmas.framework.annotation.FieldTag;

public class OfflineOrderInfoBean {
	@FieldTag(name = "订单ID")
	private int orderId = 0;
	@FieldTag(name = "商店ID")
	private int storeId = 0;
	@FieldTag(name = "国家")
	private String country = "";
	@FieldTag(name = "省份")
	private String province = "";
	@FieldTag(name = "城市")
	private String city = "";
	@FieldTag(name = "地区")
	private String region = "";
	@FieldTag(name = "地址")
	private String address = "";
	@FieldTag(name = "收货人姓名")
	private String receiverName = "";
	@FieldTag(name = "手机")
	private String mobile = "";
	@FieldTag(name = "快递公司")
	private int expressCompanyId = 0;
	@FieldTag(name = "快递单号")
	private String expressNumber = "";
	@FieldTag(name = "订单实价")
	private double orderFactAmount = 0;
	@FieldTag(name = "总件数")
	private double totalQuantity = 0;
	@FieldTag(name = "顾客留言")
	private String userComment = "";
	@FieldTag(name = "交易来源")
	private String tradeSource = "";
	@FieldTag(name = "订单状态")
	private String orderStatus = "";
	@FieldTag(name = "同步状态")
	private String syncStatus = "";
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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public double getOrderFactAmount() {
		return orderFactAmount;
	}

	public void setOrderFactAmount(double orderFactAmount) {
		this.orderFactAmount = orderFactAmount;
	}

	public double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public String getTradeSource() {
		return tradeSource;
	}

	public void setTradeSource(String tradeSource) {
		this.tradeSource = tradeSource;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
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