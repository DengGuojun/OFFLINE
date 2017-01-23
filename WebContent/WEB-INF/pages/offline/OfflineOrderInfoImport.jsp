<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<%@ page import="com.lpmas.framework.tools.common.bean.ImportResultBean"  %>
<%
	List<StoreInfoBean> storeList = (List<StoreInfoBean>)request.getAttribute("StoreList");
	int storeId = request.getAttribute("StoreId") !=null ? (Integer)request.getAttribute("StoreId") : 0;
	String tradeSource = request.getAttribute("TradeSource") !=null ? (String)request.getAttribute("TradeSource") : "";
	ImportResultBean importResultBean = (ImportResultBean)request.getAttribute("ImportResult");
%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>线下订单导入</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type="text/javascript">
        document.domain='<%=DOMAIN%>'; 
</script>
</head>
<body class="article_bg">
	<div class="article_tit">
		<ul class="art_nav">
			<li><a href="OfflineOrderInfoList.do">线下订单列表</a>&nbsp;>&nbsp;导入订单</li>
		</ul>
	</div>
	<form method="post" id="formData"  enctype="multipart/form-data" action=""  onsubmit="javascript:return formOnSubmit();">		   
		<div class="modify_form">
	   <p><em class="int_label">商店：</em>
  	    <select name="storeId" id="storeId" onchange="$('#tradeSource').empty();getTradeSourceList('storeId','tradeSource','')">
  	   <option value=""></option>
    	<%
    	for(StoreInfoBean storeInfoBean:storeList){ %>
        <option value="<%=storeInfoBean.getStoreId() %>" <%=storeId == storeInfoBean.getStoreId() ? "selected" : "" %>><%=storeInfoBean.getStoreName() %></option>
        <%} %>
        </select>
        </p>
        <p>
        <em class="int_label">来源：</em>
	  	<select name="tradeSource" id="tradeSource">
	    </select>
	    </p>
			<p>
				<em class="int_label">导入线下订单：</em>
				<input type="file" name="file" id="file" size="80" checkStr="文件;txt;true;;2000" /><a href="/examples/OfflineOrderInfoSample.xls" target="_blank">模板</a>
			</p>
			<%if(importResultBean !=null){ %>
				<div class="article_subtit">处理结果</div>
				<div class="modify_form">
					<p>
						<%=importResultBean.getSuccessMsgList().get(0) %>
					</p>
				</div>
				<%if(!importResultBean.getErrorMsgList().isEmpty()) {%>
				<div class="article_subtit">错误信息</div>
				<div class="modify_form">
				  <%for(String msg : importResultBean.getErrorMsgList()) {%>
						<p><%=msg%></p>
					<%}%> 
				</div>
				<%}%>
			 <%} %>
		</div>
		<div class="div_center">
			<p>
				
				<input type="submit" name="button" id="button" class="modifysubbtn" value="提交" />
				
			</p>
		</div>
	</form>
</body>
<script>
function formOnSubmit(){
	var storeId = $("#storeId").val();
	var tradeSource = $("#tradeSource").val(); 
	$("#formData").attr("action", "OfflineOrderInfoImport.do?storeId="+storeId+"&tradeSource="+tradeSource);
	$("#formData").submit();
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