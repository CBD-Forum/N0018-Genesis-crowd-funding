var valiPause = false;
$(function(){
	
	$("#userId").blur(function(){
		isNull($(this).val(), "userId", "请填写账号/手机号");
	});
	$("#employeeNo").blur(function(){
		isNull($(this).val(), "employeeNo", "请填写员工号");
	});
	$("#password").blur(function(){
		isNull($(this).val(), "password", "请填写登录密码");
	});
	$("#valiCode").blur(function(){
//		isNull($(this).val(), "valiCode", "验证码不能为空");
		$.ajax({
			url: path + "/checkSecurityCode.html",
			type: "POST",
			dataType: "json",
			data: {
				"valiCode": $("#valiCode").val()
			},
			success: function(data){
				if(data["success"]){//登录成功
					console.log("正确");
				}else{//登录异常
					console.log("错误");
				}
			},
			error: function(){
				console.log("获取登录提交信息失败");
			}
		});
	});
	$("#loginBtn").click(checkLogin);
	//回车击中登录
	document.onkeydown = function(event){
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if(e && e.keyCode==13){
			checkLogin();
		}
	};
	//返回错误信息
	var errorMsg = $('#errorMsg').val();
	if (errorMsg) {
		errorMsg =eval('('+errorMsg+')');
		if (errorMsg.isShowCode) {
			$('#vilidiv').show();
		}
		if(errorMsg.msg){
			$('#msg').html('');
			$('#msg').html(errorMsg.msg).show();
		}
	}
});
//登录提交事件
function checkLogin(){
	//判断账号是否为空
	if (!$("#userId").val()) {
		$("#userId").next().text('请填写账号/手机号').show();
	}
	//判断密码是否为空
	if (!$("#employeeNo").val()) {
		$("#employeeNo").next().text('请填写员工号').show();
	}
	//判断密码是否为空
	if (!$("#password").val()) {
		$("#password").next().text('请填写登录密码').show();
	}
	if (!$("#userId").val() || !$("#employeeNo").val() || !$("#password").val()) {
		return;
	}
	
	$("#regeditForm").attr("action", path + "/j_spring_security_check");
	$("#regeditForm").submit();
}

//验证焦点离开输入框是否为空
function isNull(str, id, tip){
	var $inputt = (id=="valiCode") ? $inputt.parent().children(".inputt") : $("#" + id).next();
	if(!str){
		$inputt.text(tip).show();
		return false;
	}
	$inputt.hide();
	return true;
}
