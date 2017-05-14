<%
	String path = request.getContextPath();
	String urlPath = null;
	String fullUrl = request.getRequestURL().toString();
	// if the uri is only "/" then we are basically done
	if ("/".equals(request.getRequestURI())) {
		urlPath = fullUrl.substring(0, fullUrl.length() - 1);
	}
	// find first "/" starting after hostname is specified
	int index = fullUrl.indexOf("/", fullUrl.indexOf(request.getServerName()));
	// extract just the part leading up to uri
	urlPath = fullUrl.substring(0, index);
	// then just add on the context path
	urlPath += path;
	// make certain that we don't end with a /
	if (urlPath.endsWith("/")) {
		urlPath = urlPath.substring(0, urlPath.length() - 1);
	}
	String basePath = urlPath;
%>
<%
	out.println("<link rel='stylesheet' type='text/css' href='" + basePath + "/style/easyui.css' />");
	out.println("<link rel='stylesheet' type='text/css' href='" + basePath + "/style/color.css' />");
	out.println("<link rel='stylesheet' type='text/css' href='" + basePath + "/style/icon.css' />");
	out.println("<link rel='stylesheet' type='text/css' href='" + basePath + "/style/style.css' />");
	out.println("<script type='text/javascript' src='" + basePath + "/js/common/jquery.js'></script>");
	out.println("<script type='text/javascript' src='" + basePath + "/js/common/jquery.easyui.min.js'></script>");
	out.println("<script type='text/javascript' src='" + basePath + "/js/common/easyui-lang-zh_CN.js'></script>");
	out.println("<script type='text/javascript' src='" + basePath + "/js/common/jquery-validation/jquery.validate.js'></script>");
	out.println("<script type='text/javascript' src='" + basePath + "/js/common/jquery-validation/messages_zh.js'></script>");
	out.println("<script type='text/javascript' src='" + basePath + "/js/common/My97DatePicker/WdatePicker.js'></script>");
	out.println("<script type='text/javascript' src='" + basePath + "/js/common/common.js'></script>");
	out.println("<script type='text/javascript'>var path=\"" + basePath + "\";</script>");
%>

