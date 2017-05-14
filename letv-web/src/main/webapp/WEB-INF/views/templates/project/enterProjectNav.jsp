<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>

<div class="ban2">
  <ul class="sq_box clearfix" id="enterNav">
   <li>
    <div class="img_ic" id="enterReward1">
	 <img src="<%=path %>/images/letv/cic2.png"/>
	 <img src="<%=path %>/images/letv/cic1.png" class="img_ic1"/>
	</div>
	<p class="tc mt30 mb35"><img src="<%=path %>/images/letv/sjx.png"/></p>
	<h3>产品众筹</h3>
	<p style="text-align:center;">发现新奇好玩，成就创新项目</p>
   </li>
   <li>
    <div class="img_ic" id="enterStock2">
	 <img src="<%=path %>/images/letv/cic4.png"/>
	 <img src="<%=path %>/images/letv/cic1.png" class="img_ic1"/>
	</div>
	<p class="tc mt30 mb35"><img src="<%=path %>/images/letv/sjx.png"/></p>
	<h3>非公开融资</h3>
	<p style="text-align:center;">助力创业公司，共享成长收益</p>
   </li>
   <li style="margin-right:0">
    <div class="img_ic" id="enterPubBenefit3">
	 <img src="<%=path %>/images/letv/cic6.png"/>
	 <img src="<%=path %>/images/letv/cic1.png" class="img_ic1"/>
	</div>
	<p class="tc mt30 mb35"><img src="<%=path %>/images/letv/sjx.png"/></p>
	<h3>公益众筹</h3>
	<p style="text-align:center;">参与爱心救助，成就真情延续</p>
   </li>
  </ul>
 </div>
<div class="bgpop" id="bgpop"></div>
<script type="text/javascript">
	if(siteUserId != "null"){ //已经登录
// 		$("#enterPubBenefit3").click(function(){
// 			window.location.href = path + "/common/enterPubBenefit.html";
// 		});
// 		$("#enterReward1").click(function(){
// 			window.location.href = path + "/common/enterReward.html";
// 		});
		$("#enterStock2").unbind("click").click(enterStock);
		$("#enterPubBenefit3").unbind("click").click(enterStock1);
		$("#enterReward1").unbind("click").click(enterStock2);
	}else{
		$("#enterPubBenefit3,#enterReward1,#enterStock2").click(function(){
			window.location.href = path + "/common/login.html";
		});
	}
	function href(){
		window.location.href = path + "/common/accountSecurity.html";
	}
	function enterStock(){
		$.ajax({
			url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
			type: "post",
			dataType: "json",
			success: function(data){
				//印章认证
				if(data["isAuth"]){
					if(data["memberState"]){
						if(data["isSetSignature"]){
							window.location.href = path + "/common/enterStock.html";
						}else{
							AlertDialog.mtip("请先印章开户",href);
						}
					}else{
						AlertDialog.mtip("请先开通会员",href);
					}
				}else{
					AlertDialog.mtip("请先实名认证",href);
				}
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
	}
	function enterStock1(){
		$.ajax({
			url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
			type: "post",
			dataType: "json",
			success: function(data){
				if(data["isAuth"]){
					window.location.href = path + "/common/enterPubBenefit.html";
				}else{
					AlertDialog.mtip("请先实名认证",href);
				}
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
	}
	function enterStock2(){
		$.ajax({
			url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
			type: "post",
			dataType: "json",
			success: function(data){
				if(data["isAuth"]){
					window.location.href = path + "/common/enterReward.html";
				}else{
					AlertDialog.mtip("请先实名认证",href);
				}
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
	}
</script>

<input id="indexFor" type="hidden" namefor="entry"/>
