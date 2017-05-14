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
<div class="back_colf9">
  <div class="box">
   <h3 class="pay_title">非公开融资下单</h3>
   <div class="pay_box mb70">
	   <h4 id="loanName"></h4>
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
		 <p class="col_blue">完成</p>
		</li>
	   </ul>
	   <div class="fin_div">
    <p class="fs_bold" id="whetherSuccess"></p>
	<p><em style="color:#1ea5ff" id="timeShow">5S </em>后自动返回 个人中心</p>
    <div class="clearfix operat_btn wid255 ml20" style="margin-bottom:38px;width:440px;">
	 <a class="cur ml20" style="width:184px;" href="<%=path%>/common/index.html">返回首页</a>
	 <a style="width:184px;" href="<%=path%>/common/administrationStock.html">查看我的投资</a>
	</div>
	
   </div>
	   </div>
  </div>
</div>

<input id="indexFor" type="hidden" namefor="enter"/>
<div id="bgpop" class="bgpop" name="bigp"></div>
<script type="text/javascript">
	var loanNo = getQueryString("loanNo");
	var orderId = getQueryString("orderId");
	var intention = getQueryString("intention");
	var ttime = 1;
	$("#whetherSuccess").text("支付中请稍等....");
    var interval = setInterval(function(){
		if(intention == "0"){
			$.ajax({
				url: path + "/crowdfunding/selectPayIntentionIsSucess.html",
				type: "post",
				dataType: "json",
				async: false,
				data: {
					"orderId":orderId
				},
				success: function(data){
					if(ttime>100){
						clearInterval(interval);
	    				$("#whetherSuccess").text("支付失败！");
	    				var time=5;
	    				var timer = window.setInterval(function(){
	    					time--;
	    					if (time==0) {
	    						window.clearInterval(timer);
	    						window.location.href=path+'/common/myCenter.html';
	    					}else if(time > 0){
	    						document.getElementById('timeShow').innerHTML=time+'S';
	    					}
	    				},1000);
	    			}
	    			if(data["success"]){
	    				clearInterval(interval);
	    				$("#whetherSuccess").text("恭喜您！已经支付成功！");
	    				var time=5;
	    				var timer = window.setInterval(function(){
	    					time--;
	    					if (time==0) {
	    						window.clearInterval(timer);
	    						window.location.href=path+'/common/myCenter.html';
	    					}else if(time > 0){
	    						document.getElementById('timeShow').innerHTML=time+'S';
	    					}
	    				},1000);
	    			}else{
	    				ttime++;
	    			}
				},
				error: function(request){
					console.log("验证领投请求异常");
				}
			});
		}else if(intention == "1"){
			$.ajax({
				url: path + "/crowdfunding/selectPayIsSuccess.html",
				type: "post",
				dataType: "json",
				async: false,
				data: {
					"orderId":orderId
				},
				success: function(data){
					if(ttime>100){
						clearInterval(interval);
	    				$("#whetherSuccess").text("支付失败！");
	    				var time=5;
	    				var timer = window.setInterval(function(){
	    					time--;
	    					if (time==0) {
	    						window.clearInterval(timer);
	    						window.location.href=path+'/common/myCenter.html';
	    					}else if(time > 0){
	    						document.getElementById('timeShow').innerHTML=time+'S';
	    					}
	    				},1000);
	    			}
	    			if(data["success"]){
	    				clearInterval(interval);
	    				$("#whetherSuccess").text("恭喜您！已经支付成功！");
	    				var time=5;
	    				var timer = window.setInterval(function(){
	    					time--;
	    					if (time==0) {
	    						window.clearInterval(timer);
	    						window.location.href=path+'/common/myCenter.html';
	    					}else if(time > 0){
	    						document.getElementById('timeShow').innerHTML=time+'S';
	    					}
	    				},1000);
	    			}else{
	    				ttime++;
	    			}
				},
				error: function(request){
					console.log("验证领投请求异常");
				}
			});
		}else if(intention == "2"){
			$.ajax({
				url: path + "/crowdfunding/selectIntentionEndIsSucess.html",
				type: "post",
				dataType: "json",
				async: false,
				data: {
					"orderId":orderId
				},
				success: function(data){
					if(ttime>100){
						clearInterval(interval);
	    				$("#whetherSuccess").text("支付失败！");
	    				var time=5;
	    				var timer = window.setInterval(function(){
	    					time--;
	    					if (time==0) {
	    						window.clearInterval(timer);
	    						window.location.href=path+'/common/myCenter.html';
	    					}else if(time > 0){
	    						document.getElementById('timeShow').innerHTML=time+'S';
	    					}
	    				},1000);
	    			}
	    			if(data["success"]){
	    				clearInterval(interval);
	    				$("#whetherSuccess").text("恭喜您！已经支付成功！");
	    				var time=5;
	    				var timer = window.setInterval(function(){
	    					time--;
	    					if (time==0) {
	    						window.clearInterval(timer);
	    						window.location.href=path+'/common/myCenter.html';
	    					}else if(time > 0){
	    						document.getElementById('timeShow').innerHTML=time+'S';
	    					}
	    				},1000);
	    			}else{
	    				ttime++;
	    			}
				},
				error: function(request){
					console.log("验证领投请求异常");
				}
			});
		}
    },2000);
	
	function paymentTime(){
		var time=5;
		var timer = window.setInterval(function(){
			time--;
			if (time==0) {
				window.clearInterval(timer);
				window.location.href=path+'/common/myCenter.html';
			}else if(time > 0){
				document.getElementById('timeShow').innerHTML=time+'S';
			}
		},1000);
	}
	
	$.ajax({
		url: path + "/crowdfunding/getCrowdDetail.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			$("#loanName").text(data["loanName"]); //项目名称
		},
		error: function(request){
			console.log("获取股权详细信息异常");
		}
	});
</script>	
