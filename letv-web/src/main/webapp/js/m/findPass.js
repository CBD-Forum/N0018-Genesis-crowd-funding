$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	$("#username").blur(function(){
		checkPhone($(this).val(), $(this).attr("id"));
	});
	$("#getVertify").click(getPhoneVali);//发送手机验证码
	$("#password").blur(function(){
		checkPass($(this).val(), $(this).attr("id"));
	});
	$("#password2").blur(function(){
		checkRePass($("#password").val(), $(this).val(), $(this).attr("id"));
	});
	$("#findPwdSubmit").click(findPass);//找回密码确认按钮事件
	
	$("#regiterValiCode").blur(function(){
		MValify.valiCode2($("#regiterValiCode").val(),true,"regiterValiCode")
	});
});
//验证手机号
function checkPhone(phone, id){
//	var isPhoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
	var isPhoneReg =/^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$/;
	if(!phone){
		$("#findPassTip").text('手机号不能为空').css("visibility","visible");
		return false;
	}
	if(isPhoneReg.exec(phone)){
		$("#findPassTip").css("visibility","hidden");
		return true;
	}else{
		$("#findPassTip").text('手机号不正确').css("visibility","visible");
		return false;
	}
}
//验证密码
function checkPass(str,id){
	if(!str){
		$("#findPassTip").text('密码长度应该为6~16位').css("visibility","visible");
		return false;
	}
	if(!/^(\w){6,16}$/.exec(str)){
		$("#findPassTip").text('密码长度应该为6~16位').css("visibility","visible");
		return false;
	}else{
		$("#findPassTip").css("visibility","hidden");
		return true;
	}
}
//验证重复密码
function checkRePass(str, str1, id){
	if(!str1){
		$("#findPassTip").text('两次输入密码不一致').css("visibility","visible");
		return false;
	}
	if(str != str1){
		$("#findPassTip").text('两次输入密码不一致').css("visibility","visible");
		return false;
	}else{
		$("#findPassTip").css("visibility","hidden");
		return true;
	}
}
//获取手机验证码
function getPhoneVali(){
	if(checkPhone($("#username").val(), "username")){
		$.ajax({
			url: path + "/verifycode/checkMobileFindPwd.html",
			type: "post",
			dataType: "json",
			data: {"sendTarget": $("#username").val()},
			success: function(data){
				if(data["success"]){
					$("#findPassTip").css("visibility","hidden");
//					$("#regeter2Svali").show("slow");
					
					if(MValify.isNull($("#regiterValiCode").val,"regiterValiCode",0,10)){
//						$("#sendViliimgBtn").unbind("click").click(function(){
						$("#getVertify").unbind("click").css({"cursor":"default","color":"#888"}).text("稍后可重发");
						$.ajax({
							url: path + "/verifycode/sendFindPwdVerifyCode.html",
							type: "post",
							dataType: "json",
							data: {
								"sendTarget": $("#username").val(),
								"valiCode": $("#regiterValiCode").val()
							},
							success: function(data){
								if(data["success"]){
//									$("#regeter2Svali").fadeOut();
//									$("#regiterValiCode").val("");
//									$("#imageStream2").attr("src",path + "/security/securityCodeImage.html?" + new Date().getTime());
									$("#getVertify").unbind("click").css({"cursor":"default","color":"#888"}).text("60 秒后可重发");
									countDown(60, "getVertify", overFn);
									$("#findPassTip").css("visibility","hidden");
									$("#findUserId").val(data["userId"]);
								}else{
									$("#getVertify").text("获取").css({"cursor":"pointer","color":"#55acef"});
//									$("#regiterValiCode").val("");
//									$("#imageStream2").attr("src",path + "/security/securityCodeImage.html?" + new Date().getTime());
									$("#findPassTip").text(data["msg"]).css("visibility","visible");
								}
							},
							error: function(request){
								conosle.log("发送手机验证码异常");
							}
						});
//					});
					}
				}else{
					$("#findPassTip").text("手机号不存在！").css("visibility","visible");
				}
			},
			error: function(request){
				$("#findPassTip").text("发送异常，请稍后再试").css("visibility","visible");
				conosle.log("判断手机号重复异常");
			}
		});
	}
}
function overFn(){
	$("#getVertify").text("获取验证码").css({"cursor":"pointer","color":"#54ABE0"});
	$("#getVertify").click(getPhoneVali);
}
//找回密码
function findPass(){
	if(checkPhone($("#username").val(), "username")){
		if(checkVertifyCode($("#phoneCode").val(), "phoneCode")){
			if(checkPass($("#password").val(), "password")){//密码验证
				if(checkRePass($("#password").val(), $("#password2").val(), "password2")){//重复密码验证
					$.ajax({
						url: path + "/user/findPassword.html",
						type: "post",
						dataType: "json",
						data: {
								"userId": $("#findUserId").val(),
								"verifyCode": $("#phoneCode").val(),
								"password1": $("#password").val(),
								"password2": $("#password2").val(),
								"sendTarget": $("#username").val()
							},
						success: function(data){
							if(data["msg"] == "success"){
								//后台注册验证成功，跳转
								AlertDialog.tip("修改成功！");
								setTimeout(function(){
									window.location.href = path + "/common/m-index.html";
								},2000);
							}else{
								AlertDialog.tip(data["msg"]);
							}
						},
						error: function(){
							console.log("变更密码异常");
						}
					});
				}
			}
		}
	}
}
function checkVertifyCode(code, id){
	if(!code){
		$("#findPassTip").text("验证码不能为空").css("visibility","visible");
		return false;
	}
	if(code.length != 6){
		$("#findPassTip").text("验证码应该为6位数字").css("visibility","visible");
		return false;
	}
	if(!isNaN(code)){
		$("#findPassTip").css("visibility","hidden");
		return true;
	}else{
		$("#findPassTip").text("验证码应该为纯数字").css("visibility","visible");
		return false;
	}
}