<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
String thirdNo = (String)session.getAttribute("thirdNo");
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/ueditor/third-party/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/demo.css"/>
<script type="text/javascript">
	var thirdNo = "<%=thirdNo%>"; //是否需要绑定用户
</script>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
</style>

<div class="back_colh">
  <div class="box gy_info">
   <h3>非公开融资</h3>
   <ul class="lch_nav clearfix">
    <li class="lch5">1. 填写项目基本信息</li>
	<li class="lch2">2. 融资信息</li>
	<li class="lch2">3. 创始人信息</li>
	<li class="lch6">4. 完成</li>
   </ul>
   <div class="fin_div">
    <p class="col_blue fs_bold">恭喜您!众筹申请已经提交成功</p>
	<p class="col_blue ft25" style="line-height:32px;">我们会在2-3个工作日完成审核，请您耐心等待！</p>
    <div class="clearfix operat_btn wid255" style="margin-bottom:38px;">
	 <a href="<%=path%>/common/index.html" class="cur">返回首页</a>
	 <a href="<%=path%>/common/administrationStock.html?type=1">查看我的申请</a>
	</div>
	<p><em id="timeShow">5S</em>后自动返回个人中心</p>
   </div>
  </div>
 </div>

<input id="indexFor" type="hidden" namefor="entry"/>
<script type="text/javascript" src="<%=path%>/js/project/enterStock-submit.js"></script>