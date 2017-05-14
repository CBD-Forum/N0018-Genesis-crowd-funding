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
	var pdf = window.location.search.substring(window.location.search.indexOf("pdf=")+4,window.location.search.length);
	var pdfUrl = path+'/htmlforpdf/getContractView.html?nodeType='+pdf;
	if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
		window.location.href=pdfUrl;
	}else{
		PDFObject.embed(pdfUrl,"#pdfDiv");
	}

};
</script>
