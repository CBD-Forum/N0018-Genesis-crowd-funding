<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
	<head>
		<title></title>
		<script type="text/javascript" src="<%=path%>/js/crowdfund/auditing.js"></script>
		<script type="text/javascript" src="<%=path%>/js/crowdfund/loanCommon.js"></script>
		<script type="text/javascript">
			//发请求，取数据
			$("body").html(data["content"]);
		</script>
	
	</head>
	<body>
	
	</body>
</html>
	
	