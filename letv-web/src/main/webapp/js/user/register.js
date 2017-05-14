var inviteCode = window.location.search.substring(window.location.search.indexOf("inviteCode=")+11,window.location.search.length);
var userId;
//表单验证
$(function(){
	$("input").keyup(function(){
		if($(this).val().indexOf(" ")>=0){
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	})
	
	//如果是好友推荐过来的，给推荐人输入框赋值
	if(inviteCode){
		$("#inviteCode").val(inviteCode).attr("readonly","readonly");
	}
	
	$("#sendViliimgBtn").click(sendViliCode);
	$("#regeditBtn").click(regeditSub);
	//回车击中注册
	document.onkeydown = function(event){
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if(e && e.keyCode==13){
			regeditSub();
		}
	};
});

//注册提交按钮事件
function regeditSub(){
	
	if(Valify.regiterUser($("#regiterUserId").val(),"regiterUserId")){
		if(Valify.phone($("#phoneNum").val(), "phoneNum")){
			if(Valify.isNull($("#valiCode").val(), "valiCode",0,20)){
				$.ajax({
					url: path + "/user/checkValidateCode.html",
					type: "post",
					dataType: "json",
					data: {
						"nickName": $("#regiterUserId").val(),
						"sendTarget": $("#phoneNum").val(),
						"valiCode": $("#valiCode").val()
					},
					success: function(data){
						if(data["success"]){
							if(Valify.isNull($("#verifycode").val(), "verifycode",0,20)){
								if(Valify.password($("#password").val(), "password",0,20)){
									if(Valify.rePass($("#password").val(), $("#password2").val(),"password2")){
										$.ajax({
											url: path + "/businessconfig/getByDisplayName.html",
											type: "post",
											dataType: "json",
											data: {
												"displayName":"inviteCode_switch"
											},
											success: function(data){
												if(data["code"]=="1"){
													if(checkPhone($("#inviteCode").val(), "inviteCode")){
														if(!$("#read").is(':checked')){
															AlertDialog.show($("#read").attr("nullMessage"), "read", 30, 22	);
															return false;
														}
														$.ajax({
															url: path + "/user/register.html",
															type: "post",
															dataType: "json",
															data: {
																"userId":userId,
																"nickName": $("#regiterUserId").val(),
																"mobile": $("#phoneNum").val(),
																"password": $("#password").val(),
																"password2": $("#password2").val(),
																"verifyCode": $("#verifycode").val(),
																"inputInviteCode" : $("#inviteCode").val()
															},
															success: function(data){
																if(data["success"]){
																	document.cookie = "logined=y;path=/;";
																	window.location.href = path + "/common/realNameRZ.html?type=0";
																}else{
																	if(data["msg"].indexOf("邀请")>=0){
																		AlertDialog.show(data["msg"], "inviteCode", 10, 20);
																	}else{
																		AlertDialog.tip(data["msg"]);
																	}
																}
															},
															error: function(){
																console.log("获取注册跳转信息异常");
															}
														});
													}
												}else{
													if(!$("#read").is(':checked')){
														AlertDialog.show($("#read").attr("nullMessage"), "read", 30, 22	);
														return false;
													}
													$.ajax({
														url: path + "/user/register.html",
														type: "post",
														dataType: "json",
														data: {
															"userId":userId,
															"nickName": $("#regiterUserId").val(),
															"mobile": $("#phoneNum").val(),
															"password": $("#password").val(),
															"password2": $("#password2").val(),
															"verifyCode": $("#verifycode").val(),
															"inputInviteCode" : $("#inviteCode").val()
														},
														success: function(data){
															if(data["success"]){
																document.cookie = "logined=y;path=/;";
																window.location.href = path + "/common/realNameRZ.html?type=0";
															}else{
																if(data["msg"].indexOf("邀请")>=0){
																	AlertDialog.show(data["msg"], "inviteCode", 10, 20);
																}else{
																	AlertDialog.tip(data["msg"]);
																}
															}
														},
														error: function(){
															console.log("获取注册跳转信息异常");
														}
													});
												}
											},
											error: function(){
												console.log("获取注册跳转信息异常");
											}
										});
										
									}
								}
							}
						}else{
							AlertDialog.tip(data["msg"]);
							$("#imageStream").attr("src",path + "/security/securityCodeImage.html?" + new Date().getTime());
						}
					},
					error: function(request){
						console.log("发送手机验证码异常");
					}
				});
				
			}
		}
	}

}

//发送手机验证码
function sendViliCode(){
	if(Valify.isNull($("#regiterUserId").val(),"regiterUserId",0,20)){
		if($("#regiterUserId").val().length>1 && $("#regiterUserId").val().length<9){
			if(Valify.phone($("#phoneNum").val(), "phoneNum")){
				if(Valify.isNull($("#valiCode").val(), "valiCode",0,20)){
					$.ajax({
						url: path + "/user/sendRegisterVerifyCode.html",
						type: "post",
						dataType: "json",
						data: {
							"nickName": $("#regiterUserId").val(),
							"sendTarget": $("#phoneNum").val(),
							"valiCode": $("#valiCode").val()
						},
						success: function(data){
							if(data["success"]){
								$("#sendViliimgBtn").unbind("click").css({"cursor":"default","color":"#fff"}).text("60 秒后可重发");
								$("#sendViliimgBtn").css("cursor","default").unbind("click");
								countDown(60, "sendViliimgBtn", overFn);
								userId= data["userId"];
							}else{
								AlertDialog.tip(data["msg"]);
								$("#imageStream").attr("src",path + "/security/securityCodeImage.html?" + new Date().getTime());
							}
						},
						error: function(request){
							console.log("发送手机验证码异常");
						}
					});
				}
			}
		}else{
			AlertDialog.show("昵称长度2~8个字符", "regiterUserId", 10, 20);
		}
	}
}
function overFn(){
	$("#sendViliimgBtn").text("获取短信验证码").css({"cursor":"pointer","color":"#fff"});
	$("#sendViliimgBtn").click(sendViliCode);
}
function checkPhone(phone, id){
	var isPhoneReg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$/;
	if(phone){
		if(isPhoneReg.exec(phone)){
			AlertDialog.hide();
			return true;
		}else{
			AlertDialog.show("邀请人手机号不正确", id, 10, 20);
			return false;
		}
	}else{
		AlertDialog.show("请输入邀请人手机号", id, 10, 20);
		return false;
	}
	
	AlertDialog.hide();
	return true;
}