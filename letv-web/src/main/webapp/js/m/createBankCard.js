if(userId == "null"|| userId == null){
	window.location.href = path + "/common/m-login.html";
}
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	$("#getVertifyb").unbind().click(getVertifyb);
	$("#withdrawBtn").unbind().click(bindCard);
});

//验证银行卡号
function checkBankCardCode(id){
	var bankCardNo = $("#" + id).val();
	bankCardNo = bankCardNo.replace(/[ ]/g,"");
	if(/^\d{16,30}$/.test(bankCardNo)){
		return true;
	}else if(!bankCardNo){
		AlertDialog.show("请输入银行卡号", id, -10, 150);
		return false;
	}else{
		AlertDialog.show("银行卡号格式不正确", id, -10, 150);
		return false;
	}
}

//手机号验证
function checkPhone(phone, id){
	var isPhoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
	if(!phone){
		AlertDialog.show("手机号不能为空", id, -10, 150);
		return false;
	}
	if(isPhoneReg.exec(phone)){
		AlertDialog.hide();
		return true;
	}else{
		AlertDialog.show("手机号不正确", id, -10, 150);
		return false;
	}
}
//操作绑定银行卡前验证及保存
function bindCard(){
	//开卡姓名
	if(!MValify.realName($("#userRealName").val(), "userRealName")){
		return;
	}else{
		AlertDialog.hide();
	}
	//开卡身份证号
	if(!MValify.cardCode($("#certificateNo").val(), "certificateNo")){
		return;
	}else{
		AlertDialog.hide();
	}
	//银行卡号
	if(!checkBankCardCode("bankCardCode")){
		return;
	}else{
		AlertDialog.hide();
	}
	//银行预留手机号
	if(!checkPhone($("#bankPhone").val(), "bankPhone")){
		return;
	}else{
		AlertDialog.hide();
	}
	//验证码
	if(isNaN($("#phoneCode").val()) && $("#phoneCode").val().length != 6){
		AlertDialog.show("phoneCode", phoneCode, -10, 150);
		return;
	}else{
		AlertDialog.hide();
	}
	$.ajax({
		url  : path + "/fundpool/yeepay/pay/confirmBindBankcard.html",
		type : "post",
		dataType : "json",
		async: false,
		data : {
			"requestId": $("#requestid").val(),
			"validateCode": $("#phoneCode").val()
		},
		success:function(data){
			if(data["error_code"]){
				if("参数[validatecode]不能为空" == data["error_msg"]){
					AlertDialog.mtip("手机验证码不能为空");
				}else{
					AlertDialog.mtip(data["error_msg"]);
				}
				return;
			}else{
				AlertDialog.mtip("银行卡绑定成功！",function(){
					window.location.href = path + "/common/m-myCenter.html";
				});
			}
		},
		error:function(){
			
		}
	});
}

//绑定银行卡发送手机验证码
function getVertifyb(){
	//开卡姓名
	if(!MValify.realName($("#userRealName").val(), "userRealName")){
		return;
	}else{
		AlertDialog.hide();
	}
	//开卡身份证号
	if(!MValify.cardCode($("#certificateNo").val(), "certificateNo")){
		return;
	}else{
		AlertDialog.hide();
	}
	//银行卡号
	if(!checkBankCardCode("bankCardCode")){
		return;
	}else{
		AlertDialog.hide();
	}
	//银行预留手机号
	if(!checkPhone($("#bankPhone").val(), "bankPhone")){
		return;
	}else{
		AlertDialog.hide();
	}
	$("#getVertifyb").unbind("click").text("请稍后...").css({"color":"#CCC"});
	$.ajax({
		url: path + "/fundpool/yeepay/pay/bindBankcard.html",
		type: "post",
		dataType: "json",
		data: {
			"cardNo": $("#bankCardCode").val(),
			"idCardNo": $("#certificateNo").val(),
			"realname":$("#userRealName").val(),
			"phone": $("#bankPhone").val()
		},
		success: function(data){
			if(data["error_code"]){
				AlertDialog.mtip(data["error_msg"]);
				$("#getVertifyb").text("获取验证码").unbind().click(getVertifyb).css({"cursor":"pointer","color":"#FFF"});
				return false;
			}else{
				$("#requestid").val(data["requestid"]);
				$("#getVertifyb").unbind().click(getVertifyb).text("获取验证码").hide();
				$("#btnLI").show();
//				$("#getVertifyb").unbind("click").text("60秒后发送").css({"color":"#CCC"});
//				countDown(60, "getVertifyb", bOverFn);
			}
		},
		error: function(request){
			$("#getVertifyb").text("获取验证码").css({"cursor":"pointer","color":"#FFF"});
			$("#getVertifyb").click(getVertifyb);
			console.log("发送绑定银行卡信息异常");
		}
	});
}
function bOverFn(){
	$("#getVertifyb").text("获取验证码").css({"cursor":"pointer","color":"#0697da","border":"1px solid #aad5f7", "background":"none"});
	$("#getVertifyb").click(getVertifyb);
}