if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}
var loanNo = getQueryString("loanNo");
var loanState = getQueryString("loanState");
var orderId = getQueryString("orderId");
var ltrId = getQueryString("ltrId");

$(function(){
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
		} ,
		success: function(data){
			if(data["success"]){
				var data = data["msg"];
				$("#miniInvestAmt").text(data["miniInvestAmt"].toFixed(2));
				$("#buyNum").text(data["buyNum"]);
				$("#supportAmt").text(data["supportAmt"].toFixed(2));
				$("#balance").text(data["balance"]);
				var num=Number($("#dailyProfit").attr("num"))*Number(data["supportAmt"]);
				$("#dailyProfit").text(Math.floor(num * 100) / 100);
			}
		},
		error: function(request){
			console.log("验证领投请求异常");
		}
	});
}

function toDecimal(x) {    
    var f = parseFloat(x);    
    if (isNaN(f)) {    
        return;    
    }    
    f = Math.round(x*100)/100;    
    return f;    
}   

//投资前验证
function userInvestment(){
	var url, dataStr;
	if(loanState == "preheat"){//预热-领投
		url = path + "/letvPay/invest/checkBeforeLendSupport.html";
		dataStr = {
				"loanNo" : loanNo,
				"deviceType" : "PC",
				"orderId":orderId,
				"buyNum" : $("#buyNum").text(),
				"supportAmt" : $("#supportAmt").text()
		};
	}else if(loanState == "funding"){//筹款-跟投
		url = path + "/letvPay/invest/checkBeforeSupport.html";
		dataStr = {
				"loanNo" : loanNo,
				"deviceType" : "PC",
				"orderId":orderId,
				"leadInvestor" : ltrId,
				"buyNum" : $("#buyNum").text(),
				"supportAmt" : $("#supportAmt").text(),
				"intentionAmt" : $("#intentionAmt").text()//意向金金应支付金额
		};
	}
	$.ajax({
		url: url,
		type: "post",
		dataType: "json",
		async: false,
		data: dataStr ,
		success: function(data){
			console.log(data);
			if(data["success"]){
				//submitInvest();
				var ajaxUrl;
				if(loanState == "preheat"){//预热-领投
					ajaxUrl = path + "/letvPay/invest/submitLendInvest.html";
				}else if(loanState == "funding"){//筹款-跟投
					ajaxUrl = path + "/letvPay/invest/submitFullInvest.html";
				}
				$.ajax({
					url: ajaxUrl,
					type: "post",
					dataType: "json",
					async: false,
					data: dataStr ,
					success: function(data){
						if(data["success"]){
							if($("#intentionAmt").text()){
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
			}else{
				AlertDialog.tip(data["msg"]);
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
				"orderId":orderId,
				"buyNum" : $("#buyNum").text(),
				"supportAmt" : $("#supportAmt").text()
		};
	}else if(loanState == "funding"){//筹款-跟投
		url = path + "/letvPay/invest/submitFullInvest.html";
		dataStr = {
				"loanNo" : loanNo,
				"deviceType" : "PC",
				"orderId":orderId,
				"leadInvestor" : ltrId,
				"buyNum" : $("#buyNum").text(),
				"supportAmt" : $("#supportAmt").text(),
				"intentionAmt" : $("#intentionAmt").text()//意向金金应支付金额
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
				if($("#intentionAmt").text()){
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
			$("#dailyProfit").attr("num", data["dailyProfitRate"]);
			
			getSupportInfo();
		},
		error: function(request){
			console.log("获取股权详细信息异常");
		}
	});
}