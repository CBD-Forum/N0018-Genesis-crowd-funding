if(siteUserId == "null"){
	window.location.href = path + "/common/login.html";
}
var transferNo = getQueryString("transferNo");
var transId = getQueryString("transId");
var loanNo = getQueryString("loanNo");
$(function(){
	getUserInfo()
	getTransUserInfo();
	getCrowdfundTransferDetail();
	$("#transBtu").unbind("click").click(submitBuyTransfer);
});
//获取挂牌列表
function submitBuyTransfer(){
	$.ajax({
		url: path + "/letvPay/transfer/submitBuyTransfer.html",
		type: "post",
		dataType: "json",
		data:{
			"loanNo":loanNo,
			"transferNo":transferNo,
			"transferExtendId": transId
		},
		success: function(data){
			if(!data["success"]){
				AlertDialog.tip(data["errorMsg"]);
				console.log("获取转让详情失败");
				return false;
			}else{
				window.location.href = path + "/common/transferPay-complete.html?transferNo="+transferNo+"&transId="+data["msg"];	
			}
		},
		error:function(){
			console.log("获取挂牌接口失败")
		}
	});
}
//获取挂牌列表
function getCrowdfundTransferDetail(){
	$.ajax({
		url: path + "/crowdfundProductTransfer/getCrowdfundTransferDetail.html",
		type: "post",
		dataType: "json",
		data:{
			"transferNo":transferNo
		},
		success: function(data){
			if(!data["success"]){
				console.log("获取转让详情失败");
				return false;
			}
			var list = data["msg"];
			$("#loanName").text(list["loanName"]);
			$("#transferUser").text(list["nickName"]);
			$("#transferAmt").text(list["transferAmt"].toFixed(2));
			$("#backContent").text(list["backContent"]);
			
			$("#givTime").text(list["givTime"].substring(0,10));
			if(list["transFee"]){
				$("#transFee").text(list["transFee"].toFixed(2)+companyCode);
			}else{
				$("#transFee").text("免运费");
			}
			$("#supportAmt").text((Number(list["supportAmt"])+Number(list["transFee"])).toFixed(2));
		},
		error:function(){
			console.log("获取挂牌接口失败")
		}
	});
}

//获取个人信息
function getUserInfo(){
	
	//账户余额
	$.ajax({
		url: path + "/crowdfundUserCenter/getAccountInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			$("#balance").text(data["balance"].toFixed(2));
		},
		error: function(request){
			console.log("获取筹资项目详情信息异常");
		}
	});
	
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){

			if(!data["isAuth"]){
				$("#cz_btn").click(function(){
					window.location.href = path + "/common/realNameRZ.html";
				})
			}else if(!data["memberState"]){
				$("#cz_btn").click(function(){
					AlertDialog.tip("请先开通会员！");
					setTimeout(function(){
						window.location.href = path + "/common/accountSecurity.html";
					},2000)
				})
			}else{
				$("#cz_btn").click(function(){
					window.location.href = path + "/common/recharge.html";
				});
			}
			
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}

//获取挂牌列表
function getTransUserInfo(){
	
	$.ajax({
		url: path + "/letvPay/transfer/getTransUserInfo.html",
		type: "post",
		dataType: "json",
		data:{
			"id":transId
		},
		success: function(data){
			if(!data["success"]){
				AlertDialog.tip(data["msg"]);
				console.log("获取转让详情失败");
				return false;
			}
			var list = data["msg"];
			if(list["postAddressNo"] != ""){
				$("#addN").text("收货地址：");
				$("#address").text(list["supportContent"]);
			}else{
				$("#addN").text("邮箱：");
				$("#address").text(list["supportContent"]);
			}
			if(list["supportRemark"]){//备注
				$("#saleInput").text(list["supportRemark"]);
			}else{
				$("#saleInput").text("无备注");
			}
		},
		error:function(){
			console.log("获取挂牌接口失败")
		}
	});
}
