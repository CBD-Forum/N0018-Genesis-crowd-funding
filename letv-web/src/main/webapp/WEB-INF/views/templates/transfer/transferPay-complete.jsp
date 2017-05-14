<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<style>
.header2{ border-bottom: 3px solid #1c2024;}
.bai_box{ background-color: #f4f3f3;overflow:hidden;padding-bottom:30px;}
</style>
<div class="bai_box">
<div class="box">
	<h4 class="orderH4">转让下单</h4>
	<div class="orderBox" style="overflow:hidden;">
		<div class="orderTit" id="loanName"></div>
		<ul class="orderTab">
			<li class="fl">
				<p class="p1">1</p>
				<p class="p2">转让订单信息填写</p>
			</li>
			<li class="fl" style="margin-left:13px;">
				<p class="p1">2</p>
				<p class="p2">支付</p>
			</li>
			<li class="cur fr">
				<p class="p1">3</p>
				<p class="p2">转让完成</p>
			</li>
		</ul>
		<div class="fin_div" style="padding-bottom:100px;">
	    <p class="fs_bold">恭喜您!已经支付成功</p>
		<!-- <p><em id="timeShow">5S</em>后自动返回个人中心</p> -->
		<p class="col_blue ft25" style="line-height:32px;">我们会在1-2个工作日完成审核，请您耐心等待！</p>
	    <div class="clearfix operat_btn wid255" style="margin-bottom:38px;">
		 <a class="cur" href="<%=path%>/common/index.html">返回首页</a>
		 <a href="<%=path%>/common/transferList.html">继续浏览</a>
		</div>
	   </div>
	</div>
</div>
</div>
<div class="sbgpop"></div>

<input id="indexFor" type="hidden" namefor="gua"/>
<script type="text/javascript">
	/* var time=5;
	var timer = window.setInterval(function(){
		time--;
		if (time==0) {
			window.clearInterval(timer);
			window.location.href=path+'/common/myCenter.html';
		}else if(time > 0){
			document.getElementById('timeShow').innerHTML=time+'S';
		}
	},1000); */
</script>

<script type="text/javascript" src="<%=path%>/js/common/jPages.js"></script>
<script type="text/javascript" src="<%=path%>/js/transfer/transferPay-complete.js"></script>
