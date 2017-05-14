if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}
var loanNo = getQueryString("loanNo");
var loanState = getQueryString("loanState");
var orderId = getQueryString("orderId");
var ltrId = getQueryString("ltrId");
$(function(){
	getSupportInfo();
	getCrowdDetail();
	$("#investFBtn").click(userInvestment);
});
function getSupportInfo(){
	$.ajax({
		url: path + "/crowdfunding/getSupportInfo.html",
		type: "post",
		dataType: "json",
		async: false,
		data: {
			"orderId" : orderId
		},
		success: function(data){
			if(data["success"]){
				var data = data["msg"];
				$("#miniInvestAmt").text(data["miniInvestAmt"].toFixed(2));
				$("#buyNum").text(data["buyNum"]);
				$("#supportAmt").text(data["supportAmt"].toFixed(2));
				$("#balance").text(data["balance"]);
				$("#dailyProfit").text(data["dailyProfit"]);
				$("#completeTime").text(data["investEndTime"]);
			}
		},
		error: function(request){
			console.log("验证领投请求异常");
		}
	});
}
//投资前验证
function userInvestment(){
	var url, dataStr;
	if(loanState == "preheat"){//预热-领投
		url = path + "/letvPay/invest/checkBeforeLendSupport.html";
		dataStr = {
				"loanNo" : loanNo,
				"deviceType" : "PC",
				"buyNum" : $("#buyNum").text(),
				"orderId" : orderId,
				"supportAmt" : $("#supportAmt").text()
		};
	}else if(loanState == "funding"){//筹款-跟投
		url = path + "/letvPay/invest/checkBeforeSupport.html";
		dataStr = {
				"loanNo" : loanNo,
				"deviceType" : "PC",
				"leadInvestor" : ltrId,
				"buyNum" : $("#buyNum").text(),
				"supportAmt" : $("#supportAmt").text(),
				"orderId" : orderId,
				"intentionAmt" : $("#depositPay").text()//意向金金应支付金额
		};
	}
	$.ajax({
		url: url,
		type: "post",
		dataType: "json",
		async: false,
		data: dataStr ,
		success: function(data){
			if(data["success"]){
				submitInvest();
			}else{
				AlertDialog.tip(data["msg"]);
				return false;
			}
		},
		error: function(request){
			console.log("验证领投请求异常");
		}
	});
}
//投资前验证
function submitInvest(){
	var url, dataStr;
	if(loanState == "preheat"){//预热-领投
		url = path + "/letvPay/invest/submitLendInvest.html";
		dataStr = {
				"loanNo" : loanNo,
				"deviceType" : "PC",
				"buyNum" : $("#buyNum").text(),
				"orderId" : orderId,
				"supportAmt" : $("#supportAmt").text()
		};
	}else if(loanState == "funding"){//筹款-跟投
		url = path + "/letvPay/invest/submitFullInvest.html";
		dataStr = {
				"loanNo" : loanNo,
				"deviceType" : "PC",
				"leadInvestor" : ltrId,
				"buyNum" : $("#buyNum").text(),
				"supportAmt" : $("#supportAmt").text(),
				"orderId" : orderId,
				"intentionAmt" : $("#depositPay").text()//意向金金应支付金额
		};
	}
	$.ajax({
		url: url,
		type: "post",
		dataType: "json",
		async: false,
		data: dataStr ,
		success: function(data){
			if(data["success"]){
				if($("#depositPay").text()){
					setTimeout(function(){
						window.location.href = path+"/common/stock-complete.html?loanNo="+loanNo+"&orderId="+orderId+"&intention=0";
					},1500);
				}else{
					setTimeout(function(){
						window.location.href = path+"/common/stock-complete.html?loanNo="+loanNo+"&orderId="+orderId+"&intention=1";
					},1500);
				}
				
			}else{
				AlertDialog.tip(data["msg"]);
				return false;
			}
		},
		error: function(request){
			console.log("验证领投请求异常");
		}
	});
}
//获取项目详情信息
function getCrowdDetail(){
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
			$("#completeTime").text(data["investEndTime"].substring(0,10)+" 23:59:59");
			$("#income1").attr("num",data["dailyProfitRate"]);//每日收益率	

			getDeployMap();
		},
		error: function(request){
			console.log("获取股权详细信息异常");
		}
	});
}
function getDeployMap(){
	$.ajax({
		url: path + "/crowdfunding/getDeployMap.html",
		type: "post",
		dataType: "json",
		data: {},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			
			$("#depositRatio").text(data["yxjPayScale"]*100);//意向金支付比例
			$("#yxjPlatformRatio").text(data["yxjPlatformRatio"]*100);//平台收取违约意向金比例
			$("#depositPay").text((Number($("#supportAmt").text()) * (Number($("#depositRatio").text())/100)).toFixed(2));//支付定金
			$("#retainage").text((Number($("#supportAmt").text()) - Number($("#depositPay").text())).toFixed(2));//需支付尾款

			$("#income1").text((Number($("#income1").attr("num"))*Number($("#depositPay").text())).toFixed(2));//订金支付阶段预计每日收益			
			$("#income2").text((Number($("#income1").attr("num"))*Number($("#supportAmt").text())).toFixed(2));//尾款补齐后预计每日收益
		},
		error: function(request){
			console.log("获取股权详细信息异常");
		}
	});
}