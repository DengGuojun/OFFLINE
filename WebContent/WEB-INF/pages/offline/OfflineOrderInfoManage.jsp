<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.oms.order.config.SalesOrderStatusConfig"  %>
<%@ page import="com.lpmas.tpp.console.channel.offline.config.*"  %>
<%@ page import="com.lpmas.tpp.console.channel.offline.bean.*"  %>
<% 
    OfflineOrderInfoBean bean = (OfflineOrderInfoBean)request.getAttribute("OfflineOrder");
    List<OfflineOrderItemBean> itemBeanList = (List<OfflineOrderItemBean>)request.getAttribute("ItemBeanList");
	String expressCompanyName = (String)request.getAttribute("ExpressCompanyName");
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	HashMap<Integer,String> expressCompanyNameMap = (HashMap<Integer,String>)request.getAttribute("ExpressCompanyNameMap");
%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单详情</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
	<div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="OfflineOrderInfoList.do">订单列表</a>&nbsp;>&nbsp;</li>
			<%=bean.getOrderId()%>
		</ul>
	</div>
	  <div class="modify_form">
	    <p>
	      <em class="int_label">订单ID:</em>
	      <em><%=bean.getOrderId() %></em>
	    </p>
	    <p>
	      <em class="int_label">省:</em>
	       <em><%=bean.getProvince()%></em>
	    </p>
	     <p>
	      <em class="int_label">市:</em>
	       <em><%=bean.getCity()%></em>
	    </p>
	     <p>
	      <em class="int_label">区:</em>
	       <em><%=bean.getRegion()%></em>
	    </p>
	     <p>
	      <em class="int_label">地址:</em>
	       <em><%=bean.getAddress()%></em>
	    </p>
	    <p>
	      <em class="int_label">收货人:</em>
	       <em><%=bean.getReceiverName()%></em>
	    </p>
	    <p>
	      <em class="int_label">联系电话:</em>
	       <em><%=bean.getMobile()%></em>
	    </p>
	    <p>
	      <em class="int_label">物流公司:</em>
	       <em><%=expressCompanyName%></em>
	    </p>
	    <p>
	       <em class="int_label">物流编号:</em>
	       <em><%=bean.getExpressNumber()%></em>
	    </p>
	    <p>
	    	  <em class="int_label">数量:</em>
	      <em><%=bean.getTotalQuantity()%></em>
	    </p>
	    <p>
	      <em class="int_label">订单实际总额:</em>
	       <em><%=bean.getOrderFactAmount()%></em>
	    </p>
	    <p>
	      <em class="int_label">订单状态:</em>
	       <em><%=SalesOrderStatusConfig.ORDER_STATUS_MAP.get(bean.getOrderStatus())%></em>
	    </p>	    	    
	  </div>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_style">
    <tr>
      <th><input type="checkbox" name="selectAllSoId" id="selectAllSoId" onclick="javascript:changeChiefCheckbox('selectAllSoId','selectSoId');"/></th>
      <th>明细ID</th>
      <th>商品项编码</th>      
      <th>数量</th>
      <th>实际单价</th>
      <th>单项总价</th>
      <th>物流公司</th>
      <th>物流编号</th>
    </tr>
    <%for(OfflineOrderItemBean itemBean : itemBeanList){ %>
    <tr>
      <td>&nbsp;</td>
      <td><%=itemBean.getOrderItemId() %></td>
      <td><%=itemBean.getProductItemNumber() %></td>
      <td><%=itemBean.getQuantity() %></td>
      <td><%=itemBean.getFactPrice() %></td>
      <td><%=itemBean.getItemFactAmount() %></td>
      <td><%=MapKit.getValueFromMap(itemBean.getOrderItemId(), expressCompanyNameMap)%></td>
      <td><%=itemBean.getExpressNumber() %></td>
    </tr>
    <%} %>   
  </table>

</body>
</html>