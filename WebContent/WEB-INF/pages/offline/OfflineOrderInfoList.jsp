<%@page import="com.lpmas.oms.order.config.SalesOrderStatusConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.system.bean.*"  %>
<%@ page import="com.lpmas.system.client.cache.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%@ page import="com.lpmas.tpp.console.channel.offline.config.*"  %>
<%@ page import="com.lpmas.tpp.console.channel.offline.bean.*"  %>
<%
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<OfflineOrderInfoBean> list = (List<OfflineOrderInfoBean>)request.getAttribute("OfflineOrderList");
	List<StoreInfoBean> storeList = (List<StoreInfoBean>)request.getAttribute("StoreList");
	HashMap<Integer,String> expressCompanyNameMap = (HashMap<Integer,String>)request.getAttribute("ExpressCompanyNameMap");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	int storeId = ParamKit.getIntParameter(request, "storeId", 0);
	String tradeSource = ParamKit.getParameter(request, "tradeSource", "");
%>

<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>线下订单列表</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
<script type="text/javascript">
        document.domain='<%=DOMAIN%>'; 
</script>
</head>
<body class="article_bg">
<p class="article_tit">线下订单列表</p>
<form id="formSearch" name="formSearch" method="post" action="OfflineOrderInfoList.do">
  <div class="search_form">
  	<em class="em1">订单ID：</em>
	<input type="text" name="orderId" id="orderId" value="<%=ParamKit.getParameter(request, "orderId", "")%>" size="20" />
  	<em class="em1">商店：</em>
  	<select name="storeId" id="storeId" onchange="$('#tradeSource').empty();getTradeSourceList('storeId','tradeSource','')">
  	<option value=""></option>
    	<%
    	for(StoreInfoBean storeInfoBean:storeList){ %>
        <option value="<%=storeInfoBean.getStoreId() %>" <%=(storeId == storeInfoBean.getStoreId())?"selected":"" %>><%=storeInfoBean.getStoreName() %></option>
    <%} %>
    </select>
    <em class="em1">来源：</em>
  	<select name="tradeSource" id="tradeSource">
    </select>
    <em class="em1">订单状态：</em>
    <select name="orderStatus" id="orderStatus">
    	<option value=""></option>
    	<%
    	String orderStatus = ParamKit.getParameter(request, "orderStatus", "");
    	for(StatusBean<String, String> statusBean:SalesOrderStatusConfig.ORDER_STATUS_LIST){ %>
        <option value="<%=statusBean.getStatus() %>" <%=(statusBean.getStatus().equals(orderStatus))?"selected":"" %>><%=statusBean.getValue() %></option>
    <%} %>
    </select>
    <%if(adminUserHelper.hasPermission(OfflineResource.ORDER_INFO, OperationConfig.SEARCH)){ %>
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
    <input name="" type="button" class="search_btn_sub" onclick="exportData()" value="导出"/>
    <%} %>
  </div>
</form>
  <table width="100%" border="0"  cellpadding="0" class="table_style">
    <tr>
      <th>订单ID</th>
      <th>收货人</th>
      <th>订单状态</th>
      <th>操作</th>
    </tr>
    <%
    for(OfflineOrderInfoBean bean:list){%> 
    <tr>
      <td><%=bean.getOrderId() %></td>
      <td><%=bean.getReceiverName() %></td>
      <td id="orderId_<%=bean.getOrderId()%>"><%=MapKit.getValueFromMap(bean.getOrderStatus(), SalesOrderStatusConfig.ORDER_STATUS_MAP)%></td>
      <td align="center">
      	<%if(adminUserHelper.hasPermission(OfflineResource.ORDER_INFO, OperationConfig.SEARCH)){ %>
      	<a href="/offline/OfflineOrderInfoManage.do?orderId=<%=bean.getOrderId()%>">查看</a>
      	<%} %>
      </td>
    </tr>	
    <%} %>
  </table>
<ul class="page_info">
<%@ include file="../include/page.jsp" %>
</ul>
</body>
<script>
function exportData(){
	var storeId = $("#storeId").val();   
	var orderStatus = $("#orderStatus").val();  
	var tradeSource = $("#tradeSource").val(); 
	$("#formSearch").attr("action","OfflineOrderInfoExport.do?storeId="+storeId + "&orderStatus="+ orderStatus + "&tradeSource="+ tradeSource);
	$("#formSearch").submit();
	$("#formSearch").attr("action","OfflineOrderInfoList.do");
	return true;
}
$(document).ready(function() {
	//获取来源
		var url='<%=SYSTEM_URL%>/system/SystemAjaxList.do';
		$.getScript(url,function(data){
			getTradeSourceList('storeId','tradeSource','<%=tradeSource%>');
		});
});
</script>
</html>