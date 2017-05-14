$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	//检测用户名
	$("#regiterUserId").blur(function(){
		MValify.username($(this).val(), false, "regiterTip");
	});
	//检测手机号
	$("#mobile").blur(function(){
		regiterPhone($(this).val(), "regiterTip");
	});
	//检测密码
	$("#regiterPassword").blur(function(){
		checkPassword($(this).val(), "regiterTip");
	});
	//检测重复密码
	$("#regiterPassword2").blur(function(){
		checkRePassword($(this).val(), $("#regiterPassword").val(), "regiterTip");
	});
	//检测图片验证码
	$("#regiterValiCode").blur(function(){
		checkValiDate($(this).val(), "regiterTip");
	});
	//手机验证码
	$("#verifyCode").blur(function(){
		checkNull($(this).val(), "手机验证码不能为空", "regiterTip");
	});
	//发送手机验证码
//	$("#viliBtn").click(sendRegeterViliCode);
	$("#sendViliimgBtn").unbind("click").click(sendRegeterViliCode);
	$("#registerBtn").click(go2Register);
});

function go2Register(){
	if(MValify.username($("#regiterUserId").val(), false, "regiterTip")){
		if(regiterPhone($("#mobile").val(), "regiterTip")){
			if(checkPassword($("#regiterPassword").val(), "regiterTip")){
				if(checkRePassword($("#regiterPassword2").val(), $("#regiterPassword").val(), "regiterTip")){
					//if(checkValiDate($("#regiterValiCode").val(), "regiterTip")){
					if(checkNull($("#verifyCode").val(), "手机验证码不能为空", "regiterTip")){
						if($("#read").prop("checked")){
							$.ajax({
								url: path + "/user/register.html",
								type: "post",
								dataType: "json",
								data: {
									"userId": $("#regiterUserId").val(),
									"password": $("#regiterPassword").val(),
									"password2": $("#regiterPassword2").val(),
									//"valiCode": $("#regiterValiCode").val(),
									"mobile": $("#mobile").val(),
									"verifyCode": $("#verifyCode").val(),
//									"inputInviteCode":$("#inviteCode").val()
								},
								success: function(data){
									if(data["success"]){
										$("#regiterTip").css("visibility","hidden");
										//后台注册验证成功，跳转
										document.cookie = "logined=y;path=/;";
										window.location.href = path + "/common/m-index.html";
									}else{
										$("#regiterTip").text(data["msg"]).css("visibility","visible");
										$("#imageStream2").attr("src",path+"/security/securityCodeImage.html?"+new Date().getTime());
									}
								},
								error: function(request){
									console.log("注册异常");
								}
							});
					}else{
						$("#regiterTip").css("visibility","visible").text("请阅读并同意用户注册协议");
						return false;
					}
					}
					//}
				}
			}
		}
	}
}
//验证注册手机号
function regiterPhone(phone, id){
	var isPhoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
	if(!phone){
		$("#" + id).text("手机号不能为空").css("visibility","visible");
		return false;
	}
	if(isPhoneReg.exec(phone)){
		$("#" + id).css("visibility","hidden");
		return true;
	}else{
		$("#" + id).text("手机号不正确").css("visibility","visible");
		return false;
	}
}
function sendRegeterViliCode(){
	//检测手机号
	if(MValify.username($("#regiterUserId").val(), false, "regiterTip")){
	if(regiterPhone($("#mobile").val(), "regiterTip")){
		if(checkValiDate($("#regiterValiCode").val(), "regiterTip")){
			$.ajax({
				url: path + "/user/validateMobile.html",
				type: "post",
				dataType: "json",
				data: {"mobile": $("#mobile").val()},
				success: function(data){
					if(data["success"]){
						$("#sendViliimgBtn").unbind("click").css("background", "#CCC");
						$("#sendViliimgBtn").text("稍后可重发");
						$.ajax({
							url: path + "/user/sendRegisterVerifyCode.html",
							type: "post",
							dataType: "json",
							data: {
								"userId": $("#regiterUserId").val(),
								"sendTarget": $("#mobile").val(),
								"valiCode": $("#regiterValiCode").val()
							},
							success: function(data){
								if(data["success"]){
									$("#imageStream2").attr("src",path + "/security/securityCodeImage.html?" + new Date().getTime());
									$("#viliBtn").unbind("click").css({"cursor":"default","color":"#888"}).text("60 秒后可重发");
									$("#sendViliimgBtn").css("background", "#CCC").unbind("click");
									countDown(60, "sendViliimgBtn", regiterOverFn);
									$("#regiterTip").css("visibility","hidden");
								}else{
									$("#sendViliimgBtn").css("background", "#e15e18").click(sendRegeterViliCode);
									$("#sendViliimgBtn").text("获取验证码");
									$("#imageStream2").attr("src",path + "/security/securityCodeImage.html?" + new Date().getTime());
									$("#regiterTip").text(data["msg"]).css("visibility","visible");
								}
							},
							error: function(request){
								conosle.log("发送手机验证码异常");
							}
						});
					}else{
						$("#imageStream2").attr("src",path + "/security/securityCodeImage.html?" + new Date().getTime());
						$("#regiterTip").css("visibility","visible").text(data["msg"]);
						$("#sendViliimgBtn").css("background", "#e15e18").click(sendRegeterViliCode);
						$("#sendViliimgBtn").text("获取验证码");
					}
				},
				error: function(request){
					$("#regiterTip").css("visibility","visible").text("发送异常，请稍后再试");
					conosle.log("判断手机号重复异常");
				}
			});
		}else{
			$("#imageStream2").attr("src",path + "/security/securityCodeImage.html?" + new Date().getTime());
		}
	}
	}
}
//验证用户名、密码是否为空
function checkNull(str, type, id){
	if(!str){
		$("#" + id).text(type).css("visibility","visible");
		return false;
	}
	$("#" + id).css("visibility","hidden");
	return true;
}

//检测密码
function checkPassword(str,id){
	if(!str){
		$("#" + id).text("密码长度应该为6~16位,由字母和数字组成").css("visibility","visible");
		return false;
	}
	if(!/^(\w){6,16}$/.exec(str)){
		$("#" + id).text("密码长度应该为6~16位,由字母和数字组成").css("visibility","visible");
		return false;
	}else{
		$("#" + id).css("visibility","hidden");
		return true;
	}
}

//检测重复密码
function checkRePassword(str, str1, id){
	if(!str1){
		$("#" + id).text("密码不能为空").css("visibility","visible");
		return false;
	}
	if(str != str1){
		$("#" + id).text("两次输入密码不一致").css("visibility","visible");
		return false;
	}else{
		$("#"+id).css("visibility","hidden");
		return true;
	}
}

//验证码实时验证
function checkValiDate(str, id){
	var valiCode = str, result = false;
	return MValify.valiCode(valiCode, result, id);
}

function regiterOverFn(){
	$("#sendViliimgBtn").text("获取验证码");
	$("#sendViliimgBtn").css("background", "none").click(sendRegeterViliCode);
}

//查看注册协议
function showAgree(){
	$.ajax({
		url: path + "/node/getNode.html",
		type: "post",
		dataType: "json",
		data:{
			nodeType:"zcxy"
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
//			$("#agreeContent").html(data["msg"]["templateContent"]);
//			$("#agreeTime").text(data["msg"]["createTime"].substring(0,10));
			$("#agreeContent").html(data["msg"][0]["body"]);
//			$("#agreeTime").text(data["msg"][0]["createTime"].substring(0,10));
			
			$("#xyBgpop").show();
			var al = (cv["winW"]-580)/2, at = (cv["winH"]-$(".agree_box").height())/2;
			$(".agree_box").css({"left":"2.5%", "top":at+"px"}).show();
			$(".agree_close").click(function(){
				$("#xyBgpop").hide();
				$(".agree_box").fadeOut();
			});
		},
		error: function(request){
			console.log("获取协议范文异常");
		}
	});
}