var loanNo = getQueryString("loanNo");
var backNo = getQueryString("backNo");
var orderId = getQueryString("orderId");
var ctype = getQueryString("ctype");
$(function(){
	getDetail();
	getUserInfo();
	$("#supportBtn").click(supportSub);
});

//获取个人信息
function getUserInfo(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["user"]["email"]){
				$("#mailbox").html('<a href="'+path+'/common/accountSecurity.html">绑定邮箱</a>');
			}else{
				$("#mailbox").html(data["user"]["email"]);
			}
			
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
//获取支持详情
function getDetail(){
	var postAddressNo;
	$.ajax({
		url: path + "/crowdfunding/getBackSetList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo": loanNo,
			"backNo": backNo
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"]["rows"][0];
			$("#supLoanName").text(data["loanName"]);
			$("#supContent").text(data["backContent"]);
			$("#supAmt").text(data["amt"].toFixed(2)).attr("amt",data["amt"]);
			var amt=data["amt"];
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
					var num=data["dailyProfitRate"]*amt;
					$("#dailyProfit").text(Math.floor(num * 100) / 100);
					
				},
				error: function(request){
					console.log("获取项目详情异常！");
				}
			});
			if(data["transFee"] == 0){//免运费
				$("#suphAmt").text("免运费").attr("amt","0");
			}else{
				$("#suphAmt").text(data["transFee"].toFixed(2)+companyCode).attr("amt",data["transFee"]);
			}
			$("#paymentAmt").text((data["amt"]+data["transFee"]).toFixed(2)).attr("amt",data["amt"]+data["transFee"]);
			if(data["backType"] == "X"){
				$("#mailbox").parent().show();
			}else{
				$("#address").parent().show();
			}
		},
		error: function(request){
			console.log("获取筹资项目详情信息异常");
		}
	});
	
	$.ajax({
		url: path + "/crowdfunding/getSupportInfo.html",
		type: "post",
		dataType: "json",
		data: {
			"orderId": orderId
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			$("#remark").text(data['remark']);
			postAddressNo = data["postAddressNo"];
			$.ajax({
				url: path + "/address/getPostAddressById.html",
				type: "post",
				dataType: "json",
				async: false,
				data:{
					"id":data["postAddressNo"]
				},
				success: function(data){
					var data = data["msg"],address,city;
					provinece = data["provinceName"] ? data["provinceName"] : "";
					city = data["cityName"] ? data["cityName"] : "";
					$("#address").html(provinece+city+data["adressDetail"]+data["postUser"]+data["mobile"]);
				},
				error: function(request){
					console.log("获取收货地址异常");
				}
			});
			
		},
		error: function(request){
			console.log("获取筹资项目详情信息异常");
		}
	});
	
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
}

function recharge(){
	window.location.href = path + "/common/recharge.html";
}

//支持众筹
function supportSub(){
	if(Number($("#balance").text())<Number($("#paymentAmt").attr("amt"))){
		//AlertDialog.tip("余额不足，请充入！");
		AlertDialog.confirm(recharge, null, "余额不足，请充入！", "去充入", "取消", null);
		return false;
	}
	//提交支持前验证
	$.ajax({
		url: path + "/letvPay/invest/checkBeforeSupport.html",
		type: "post",
		dataType: "json",
		async: false,
		data: {
			"supportAmt": $("#supAmt").attr("amt"),
			"loanNo": loanNo,
			"backNo": backNo,
			"deviceType":"PC",
			"orderId":orderId,
			"transFee":$("#suphAmt").attr("amt"),
			"invstType": "commonInvest"
		},
		success: function(data){
			if(!data["success"]){
				AlertDialog.tip(data["msg"]);
			}else{
				$.ajax({
					url: path + "/letvPay/invest/submitFullInvest.html",
					type: "post",
					dataType: "json",
					async: false,
					data: {
						"supportAmt": $("#supAmt").attr("amt"),
						"loanNo": loanNo,
						"backNo": backNo,
						"deviceType":"PC",
						"orderId":orderId,
						"transFee":$("#suphAmt").attr("amt"),
						"invstType": "commonInvest"
					},
					success: function(data){
						if(!data["success"]){
							AlertDialog.tip(data["msg"]);
						}else{
							if(ctype == "1"){
								window.location.href = path + "/common/crowdfund-success.html?loanNo="+loanNo+"&backNo="+backNo+"&ctype=1"+"&orderId="+orderId;
							}else{
								window.location.href = path + "/common/crowdfund-success.html?loanNo="+loanNo+"&backNo="+backNo+"&ctype=0"+"&orderId="+orderId;
							}
						}
					},
					error: function(request){
						console.log("获取用户余额异常");
					}
				});
			}
		},
		error: function(request){
			console.log("获取用户余额异常");
		}
	});
}