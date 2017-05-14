if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	if(userId == "null"){ //未登录
		$("#myCenter").attr("href",path+"/common/m-login.html");
	}else{
		$("#myCenter").attr("href",path+"/common/m-myCenter.html");
	}
	getUserAccount(); //获取个人账户信息
	getUserBank(); //查询绑定的银行卡
	$("#withdrawInput").blur(checkWithAmt); //提现金额输入框鼠标离开
	$("#getVertify").click(getWithVertify); //获取验证码
	
	//手续费计算
	$("#db").click(function(){
		if(!isNaN($("#withdrawInput").val())){
			$("#poundage").text("￥" + $("#poundage").attr("db"));
		}
	});
	$("#bl").click(function(){
		if(!isNaN($("#withdrawInput").val()) && $("#withdrawInput").val()){
			AlertDialog.hide();
			$("#poundage").text("￥" + Number($("#withdrawInput").val()) * Number($("#poundage").attr("bl")));
		}else{
			AlertDialog.show("请输入提现金额", "withdrawInput", 0, 150);
		}
	});
	$("#withdrawBtn").unbind("click").click(userWithdraw);
});
//获取个人账户
function getUserAccount(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getAccountInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			$("#userBalance").text("￥" + data["balance"].toFixed(0)).attr("num", data["balance"].toFixed(0)) ; //可用余额
			$("#poundage").attr("db", data["feeParam"]).attr("bl", data["ratioFeeParam"]).text("￥" + data["feeParam"]);
		},error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//查询绑定的银行卡
function getUserBank(){
	var bArr = [], bStr = '', l;
	$.ajax({
		url: path + "/bank/getUserBank.html",
		type: "post",
		dataType: "json",
		success: function(data){
			l = data["msg"].length, data = data["msg"];
			if(l == 0){
				return false;
			}
			for(var i=0;i<l;i++){
				bArr.push('<option value="'+data[i]["bankNo"]+'">'+data[i]["bankAccount"]+'</option>');
			}
			bStr = bArr.join("");
			$("#bankAccount").html(bStr);
		},
		error: function(request){
			console.log("获取绑定的银行卡异常");
		}
	});
}
//检测提现金额
function checkWithAmt(){
	if(MValify.isNull($("#withdrawInput").val(), "withdrawInput", -10, 150)){
		if(isNaN($("#withdrawInput").val())){
			AlertDialog.show("请输入数字", "withdrawInput", -10, 150);
			return false;
		}
		if(Number($("#withdrawInput").val()) <= 0){
			AlertDialog.show("提现金额必须大于0", "withdrawInput", -10, 150);
			return false;
		}
		if(Number($("#withdrawInput").val()) > Number($("#userBalance").attr("num"))){
			AlertDialog.show("账户余额不足", "withdrawInput", -10, 150);
			return false;
		}
		AlertDialog.hide();
		if($("#db").prop("checked")){ //当前选中按单笔收取手续费
			$("#poundage").text("￥" + $("#poundage").attr("db"));
		}
		if($("#bl").prop("checked")){
			$("#poundage").text("￥" + Number($("#withdrawInput").val()) * Number($("#poundage").attr("bl")));
		}
		return true;
	}
}
//发送提现验证码
function getWithVertify(){
	if($("#bankAccount").val() == "请选择提现银行卡"){
		AlertDialog.show("请添加提现银行卡", "bankAccount", 0, 100);
	}else{
		$("#getVertify").unbind("click").text("请稍后...").css({"color":"#FB4D01", "cursor":"default", "border":"none","background":"#ccc"});
		$.ajax({
			url: path + "/verifycode/sendUserVerifyCode.html",
			type: "post",
			dataType: "json",
			data: {"codeType": "withdraw_remind"},
			success: function(data){
				if(!data["success"]){
					return false;
				}
				$("#getVertify").unbind("click").text("60秒后发送").css({"color":"#FB4D01", "cursor":"default", "border":"none"});
				countDown(60, "getVertify", wOverFn);
			},
			error: function(request){
				$("#getVertify").text("获取验证码").css({"cursor":"pointer","color":"#3b9ee3","border":"1px solid #aad5f7", "background":"none"});
				$("#getVertify").click(getWithVertify);
				console.log("发送绑定银行卡信息异常");
			}
		});
	}
}
function wOverFn(){
	$("#getVertify").text("获取验证码").css({"cursor":"pointer","color":"#3b9ee3","border":"1px solid #aad5f7", "background":"none"});
	$("#getVertify").click(getWithVertify);
}
//提现
function userWithdraw(){
	if(MValify.isNull($("#bankAccount").val(), "bankAccount", -10, 150)){
		if(checkWithAmt()){
			if(MValify.isNull($("#phoneCode").val(), "phoneCode", -10, 150)){
				//提现前验证
				$.ajax({
					url: path + "/withdraw/checkWithdraw.html",
					type: "post",
					dataType: "json",
					data: {
						"bankCardId": $("#bankAccount").val(),
						"amt": $("#withdrawInput").val(),
						"feeType": $("#db").prop("checked") ? "perFee": "ratioFee",
						"verifyCode": $("#phoneCode").val()
					},
					success: function(data){
						if(!data["success"]){
							AlertDialog.tip(data["msg"]);
							return false;
						}
						
						//提现
						$.ajax({
							url: path + "/withdraw/saveWithdrawOrder.html",
							type: "post",
							dataType: "json",
							data: {
								"bankCardId": $("#bankAccount").val(),
								"amt": $("#withdrawInput").val(),
								"feeType": $("#db").prop("checked") ? "perFee": "ratioFee"
							},
							success: function(data){
								if(!data["success"]){
									AlertDialog.tip(data["msg"]);
									return false;
								}
								AlertDialog.tip("提现申请成功");
								setTimeout(function(){
									window.location.href = path + "/common/m-myCenter.html";
								}, 2000);
							},
							error: function(request){
								console.log("提现验证异常");
							}
						});
						
					},
					error: function(request){
						console.log("提现验证异常");
					}
				});
			}
		}
	}
}