<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/pdfobject.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/common/pdfobject.min.js"></script>
<style>
.footer,.header1,.header2{display:none;}
</style>
<div style="position: fixed;width:100%;height:100%;" id="pdfDiv">
</div>
<script>

window.onload = function (){
	var tradeId = getQueryString("tradeId");
	var type = getQueryString("type");
	if(type == "recharge_unFreeze"){
		$.ajax({
			url: path + "/rechargeAgreement/getRechargeAgreement.html",
			type: "post",
			dataType: "json",
			data: {
				"orderId":tradeId
			},
			success: function(data){
				if(!data["success"]){
					return false;
				}
				var pdfUrl=cv.fileAddress+data["path"];
				if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
					window.location.href=pdfUrl;
				}else{
					PDFObject.embed(pdfUrl,"#pdfDiv");
				}
			},
			error: function(){
				console.log("获取交易明细列表数据异常");
			}
		});
	}else if(type == "withdraw"){
		$.ajax({
			url: path + "/withDrawAgreement/getwithDrawAgreement.html",
			type: "post",
			dataType: "json",
			data: {
				"orderId":tradeId
			},
			success: function(data){
				if(!data["success"]){
					return false;
				}
				var pdfUrl=cv.fileAddress+data["path"];
				if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
					window.location.href=pdfUrl;
				}else{
					PDFObject.embed(pdfUrl,"#pdfDiv");
				}
			},
			error: function(){
				console.log("获取交易明细列表数据异常");
			}
		});
	}
	

};
</script>
