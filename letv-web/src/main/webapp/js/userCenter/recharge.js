if(siteUserId == "null"){
	window.location.href = path + "/common/login.html";
}
$(function(){
	getUserAccount();
	$("#recharge").click(rechargeSub);
	$("#rechargeAmount").keyup(function(){
		checkRechargeAmount($("#rechargeAmount").val(), "rechargeAmount")
	});
});

function rechargeSub(){
	if(checkRechargeAmount($("#rechargeAmount").val(), "rechargeAmount")){
		$.ajax({
			url: path + "/sxyPay/recharge/checkCreateRecharge.html",
			type: "post",
			dataType: "json",
			async: false,
			data: {
				"rechargeAmt": $("#rechargeAmount").val()
			},
			success: function(data){
				if(data["success"]){
					$("#form_rechargeAmount").val($("#rechargeAmount").val());
					$("#submitBtn").click();
				}else{
					AlertDialog.tip(data["msg"]);
					return false;
				}
			},
			error: function(){
				console.log("充入异常");
			}
		});
	}
}

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
			$("#Rbalance").text(data["balance"].toFixed(2)).attr("num", data["balance"]) ; //可用余额
		},error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//验证充入金额是否正确
function checkRechargeAmount(str, id){
	var $inputt = $("#"+id).next();//错误提示框
	var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
	str = str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');//去掉收尾空格
	if(!str){
		AlertDialog.show("充入金额不能为空", id, 10, 20);
		return false;
	}else if(!reg.test(str)){
		AlertDialog.show("请正确填写充入金额,不能超过两位小数", id, 10, 20);
		return false;
	}else if(str == "0"){
		AlertDialog.show("请正确填写充入金额,不能超过两位小数", id, 10, 20);
		return false;
	}else{
		AlertDialog.hide();
		$("#actualAmount").text($("#rechargeAmount").val());
	}
	return true;
}
