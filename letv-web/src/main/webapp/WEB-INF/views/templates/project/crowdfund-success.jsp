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
<div class="back_colf9">
  <div class="box">
   <h3 class="pay_title">众筹下单</h3>
   <div class="pay_box mb70">
	   <h4><a id="loanName"></a></h4>
	   <ul class="clearfix step_pay">
		<li>
		 <div class="step_1">
		  <i>1</i>
		 </div>
		 <p>订单信息填写</p>
		</li>
		<li>
		 <div class="step_1  ">
		  <i>2</i>
		 </div>
		 <p>支付</p>
		</li>
		<li style="margin-right:0">
		 <div class="step_1 step_2">
		  <i>3</i>
		 </div>
		 <p  class="col_blue">完成</p>
		</li>
	   </ul>
	   <div class="fin_div">
    <p class="fs_bold" id="whetherSuccess"></p>
	<p><em style="color:#1ea5ff" id="timeShow">5S </em>后自动返回 个人中心</p>
    <div class="clearfix operat_btn wid255 ml20" style="margin-bottom:38px;width:440px;">
	 <a class="cur ml20" style="width:184px;" href="<%=path%>/common/index.html">返回首页</a>
	 <a style="width:184px;" id="wdzcBtu">查看我的支持</a>
	</div>
	
   </div>
	   </div>
  </div>

<input id="indexFor" type="hidden" namefor="loan"/>
<script type="text/javascript" src="<%=path%>/js/project/crowdfund-success.js"></script>