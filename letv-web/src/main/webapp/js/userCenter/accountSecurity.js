if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	getUserInfoDetail();//获取个人资料
	var wh = document.documentElement.clientHeight, ww = document.documentElement.clientWidth;
	var lw = (ww - 505)/2, lt =  (wh - $("#phoneDiv").height())/2, llt = (wh - $("#passwordDiv").height())/2;
	document.getElementById("phoneDiv").style.top = lt+"px";
	document.getElementById("phoneDiv").style.left = lw+"px";
	document.getElementById("passwordDiv").style.top = llt+"px";
	document.getElementById("passwordDiv").style.left = lw+"px";
	document.getElementById("emailDiv").style.top = llt+"px";
	document.getElementById("emailDiv").style.left = lw+"px";
	getUserInfo();
	
	$(".prompt_close").click(function(){
		$(".sbgpop").hide();
		$(".prompt_box").hide();
		AlertDialog.hide();
	});
	//原始密码
	$("#pass").blur(function(){
		if($("#pass").val() == ""){
			AlertDialog.show("原始密码不能为空", "pass", -10,300);
			return false;
		}
	});
	
	myEcreateWebUploader("image_file", "imgheadPhoto", "loan_logo_url", "imgheadLi");//上传头像
	/*新密码 键盘弹起事件*/
	$("#newPass").keyup(function(){
		Valify.passStrength($(this).val());
	}).blur(function(){
		Valify.password($(this).val(),"newPass");
		return false;
	});
	//确认密码
	$("#okPass").blur(function(){
		Valify.rePass($("#newPass").val(),$(this).val(),"okPass");
		return false;
	});
	//修改密码
	$("#savePass").click(updatePassword);

	//修改手机号码
	$("#updateMobile").click(updateMobile);
	
});
function modifyPhone(){
	$("#verifycode,#mobile1,#verifycode1").val("");
	$(".sbgpop").show();
	$("#phoneDiv").show();
	clearInterval(productTime);
	$("#VerifycodeDiv").html($("#VerifycodeHtml").html());
	clearInterval(productTime1);
	$("#VerifycodeDiv1").html($("#VerifycodeHtml1").html());
	//发送手机验证码
	$("#sendBtn").click(sendCode);
	$("#sendBtn1").click(sendCode1);
}
function modifyPwd(){
	$("#pass,#newPass,#okPass").val("");
	$(".sbgpop").show();
	$("#passwordDiv").show();
}
function getUserInfo(){
	$.ajax({
		url: path + "/user/getUserInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			var tel = data["mobile"].substr(0, 3) + '****' + data["mobile"].substr(7);
			$("#mobile").text(tel);
			$("#phone").text(tel);
			$("#tel").val(data["mobile"])
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
/*修改密码*/
function updatePassword(){
	if(isNull($("#pass").val(),"pass","原始密码不能为空",-10,300)){
		if(Valify.password($("#newPass").val(),"newPass")){
			if(Valify.rePass($("#newPass").val(),$("#okPass").val(),"okPass")){
				$.ajax({
					url: path + "/user/updateUserPwd.html",
					type: "post",
					dataType: "json",
					data:{
						"password1":$("#newPass").val(),
						"password2":$("#okPass").val(),
						"oldPwd":$("#pass").val()
					},
					success: function(data){
						if(data["success"]){
							AlertDialog.tip("保存成功！");
							setTimeout(function(){
								window.location.href = path + "/common/myCenter.html";
							},1000);
						}else{
							AlertDialog.tip("原始密码不正确，请重新输入");
							return false;
						}
					},
					error: function(request){
						console.log("修改密码异常");
					}
				});
			}
		}
	}
}



/**
 * 判断值是否为空
 * @param str 元素的值
 * @param id 元素id
 * @param tipStr 提示信息
 * @param topSize 距离上边的距离
 * @param rightSize 距离右边的距离
 */
function isNull(str,id,tipStr,topSize,rightSize){
	if(!str){
		AlertDialog.show(tipStr, id, topSize, rightSize);
		return false;
	}
	AlertDialog.hide();
	return true;
}
function updateMobile(){
	if(isNull($("#verifycode").val(),"verifycode","原手机号验证码不能为空",-10,200)){
		if(Valify.phone($("#mobile1").val(),"mobile1")){
			if(isNull($("#verifycode1").val(),"verifycode1","新手机号验证码不能为空",-10,200)){
				$.ajax({
					url: path + "/user/updateMobile.html",
					type: "post",
					dataType: "json",
					data: {
						"oldMobile": $("#tel").val(),//旧手机号
						"newMobile": $("#mobile1").val(),//新手机号
						"verifyCode1": $("#verifycode").val(),//原手机号验证码
						"verifyCode2": $("#verifycode1").val()//新手机号验证码
					},
					success: function(data){
						if(data["success"]){
							AlertDialog.tip("修改成功！");
							setTimeout(function(){
								window.location.href = path + "/common/accountSecurity.html";
							},2000);
						}else{
							AlertDialog.tip(data["msg"]);
						}
					},
					error: function(request){
						console.log("获取发送认证码信息异常");
					}
				})	
			}
		}
	}
}
//发送手机认证码
function sendCode(){
	$.ajax({
		url: path + "/verifycode/sendMobileUpdateCodeOld.html",
		type: "post",
		dataType: "json",
		data: {
			"sendTarget": $("#tel").val()
		},
		success: function(data){
			if(data["success"]){
				$("#sendBtn").unbind("click").css({"background":"#CCC","color":"#fff","cursor":"default"}).text("60秒后可重发");
				productDown(60, "sendBtn", overFnCode);
			}
		},
		error: function(request){
			console.log("获取发送认证码信息异常");
		}
	})
}
function overFnCode(){
	$("#sendBtn").text("获取验证码").css({"cursor":"pointer","background":"#f7f7f7"});
	$("#sendBtn").click(sendCode);
}
//发送手机认证码
function sendCode1(){
	if(Valify.phone($("#mobile1").val(),"mobile1")){
		$.ajax({
			url: path + "/verifycode/sendMobileUpdateCodeNew.html",
			type: "post",
			dataType: "json",
			data: {
				"sendTarget": $("#mobile1").val()
			},
			success: function(data){
				if(data["success"]){
					$("#sendBtn1").unbind("click").css({"background":"#CCC","color":"#fff","cursor":"default"}).text("60 秒后可重发");
					productDown1(60, "sendBtn1", overFnCode1);
				}else{
					AlertDialog.show(data["msg"], "mobile1", -10, 300);
				}
			},
			error: function(request){
				console.log("获取发送认证码信息异常");
			}
		})	
	}

}
function overFnCode1(){
	$("#sendBtn1").text("获取验证码").css({"cursor":"pointer","background":"#f7f7f7"});
	$("#sendBtn1").click(sendCode1);
}

//发送邮箱认证码
function sendCodeEamil(){
	if(Valify.email($("#newEmail").val(), "newEmail")){
		$.ajax({
			url: path + "/safeLevel/checkEmail.html",
			type: "post",
			dataType: "json",
			data: {
				"email": $("#newEmail").val()
			},
			success: function(data){
				if(data["isExist"]){
					AlertDialog.show("邮箱已经存在，请重新输入！", "newEmail", 0, 20);
					return false;
				}else{
					$("#emailSendBtn").unbind("click").css({"cursor":"default","color":"#888"}).text("稍后可重发");
					$.ajax({
						url: path + "/verifycode/sendEmailAuthCode.html",
						type: "post",
						dataType: "json",
						data: {
							"sendTarget": $("#newEmail").val()
						},
						success: function(data){
							if(data["success"]){
								$("#emailSendBtn").unbind("click").css({"cursor":"default","color":"#888"}).text("60 秒后可重发");
								productDown(60, "emailSendBtn", emailOverFn);
							}
						},
						error: function(request){
							console.log("获取发送邮箱认证码信息异常");
						}
					});
				}
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
		
	}
}
function emailOverFn(){
	clearInterval(productTime);
	$("#emailSendBtn").text("获取验证码").css({"cursor":"pointer", "background":"#f7f7f7", "color":"#222"});
	$("#emailSendBtn").click(sendCodeEamil);
}
//验证是否为空
function isNull(str, id){
	if(!str){
		AlertDialog.show($("#"+id).attr("nullMessage"), id, 0, 20);
		return false;
	}else{
		AlertDialog.hide();
		return true;
	}
}
//认证邮箱
function checkEmail(){
	if(Valify.email($("#newEmail").val(), "newEmail")){
		$.ajax({
			url: path + "/safeLevel/checkEmail.html",
			type: "post",
			dataType: "json",
			data: {
				"email": $("#newEmail").val()
			},
			success: function(data){
				if(data["isExist"]){
					AlertDialog.show("邮箱已经存在，请重新输入！", "newEmail", 0, 20);
					return false;
				}
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
		if(isNull($("#emailCode").val(), "emailCode")){
			$.ajax({
				url: path + "/safeLevel/authEmail.html",
				type: "post",
				dataType: "json",
				data: {
					"sendTarget": $("#newEmail").val(),
					"verifyCode": $("#emailCode").val()
				},
				success: function(data){
					if(data["success"]){
						AlertDialog.tip("认证成功");
						setTimeout(function(){
							window.location.href = path + "/common/accountSecurity.html";
						},2000)
					}else{
						AlertDialog.tip(data["msg"]);
					}
				},
				error: function(request){
					console.log("获取认证电子邮箱信息异常");
				}
			})
		}
	}
}
//绑定邮箱
function modifyEmail(){
	$("#newEmail,#emailCode").val("");
	$(".sbgpop").show();
	clearInterval(productTime);
	$("#emailDiv").show().find("ul").html($("#emailDivHtml").html());
	$("#emailSendBtn").click(sendCodeEamil);
	$("#checkEmail").click(checkEmail);
	//邮箱是否存在
	$("#newEmail").blur(function(){
		if(!$(this).val()){
			AlertDialog.show("请输入邮箱", "newEmail", 0, 20);
			return false;
		}
		$.ajax({
			url: path + "/safeLevel/checkEmail.html",
			type: "post",
			dataType: "json",
			data: {
				"email": $("#newEmail").val()
			},
			success: function(data){
				if(data["isExist"]){
					AlertDialog.show("邮箱已经存在，请重新输入！", "newEmail", 0, 20);
				}
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
	});
	
}
//获取个人信息
function getUserInfoDetail(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
//			data = data["user"];
			if(data["isAuth"]){
				$("#iconRZ").html('<i class="i2"></i>已认证');
				$("#realName").text(data["user"]["realName"].substring(0,1)+"**");
				$("#nameRZ").text("查看").attr("href",path+"/common/realNameRZTG.html");
				//绑卡
				if(data["isBindCard"]){
					$("#bindBankCard").text("添加").attr("href",path+"/common/bankList.html");
				}else{
					$("#bindBankCard").text("绑定").attr("href",path+"/common/bankList.html");
				}
				
				//会员认证
				if(data["memberState"]){
					$("#memberRZ").html('<i class="i2"></i>已开通');
					if(data["userType"] == "0"){
						$("#member").text("个人会员");
						$("#memberBtn").text("查看").attr("href",path+"/common/memberPersonal.html?type=p");
					}else if(data["userType"] == "1"){
						$("#member").text("企业会员");
						$("#memberBtn").text("查看").attr("href",path+"/common/memberEnterprise.html?type=e");
					}
					
					//印章认证
					if(data["isSetSignature"]){
						$("#sealRz").html('<i class="i2"></i>已开户');
						if(data["userType"] == "0"){
							$("#modifySeal").text("查看").attr("href",path+"/common/signaturePersonal.html?type=p");
						}else if(data["userType"] == "1"){
							$("#modifySeal").text("查看").attr("href",path+"/common/signatureEnterprise.html?type=e");
						}
					}else{
						$("#sealRz").html('<i class="i1"></i>未开户');
						if(data["userType"] == "0"){
							$("#modifySeal").text("去开户").attr("href",path+"/common/signaturePersonal.html");
						}else if(data["userType"] == "1"){
							$("#modifySeal").text("去开户").attr("href",path+"/common/signatureEnterprise.html");
						}
					}
					
				}else{
					$("#memberRZ").html('<i class="i1"></i>未开通');
					$("#memberBtn").text("开通会员").click(function(){
						$(".kh_alert,.sbgpop").show();
						$("#alert-none").click(function(){
							$(".kh_alert,.sbgpop").hide();
						})
					});
					
					
					$("#sealRz").html('<i class="i1"></i>未开户');
					$("#modifySeal").text("去开户").click(function(){
						AlertDialog.tip("请先开通会员");
					});
					
				}
				
			}else{
				$("#iconRZ").html('<i class="i1"></i>未认证');
				$("#realName").text("您还没有认证");
				$("#nameRZ").text("认证").attr("href",path+"/common/realNameRZ.html");
				//绑卡
				$("#bindBankCard").text("绑定").attr("href",path+"/common/realNameRZ.html");
				
				//印章认证
				$("#sealRz").html('<i class="i1"></i>未开户');
				$("#modifySeal").text("去开户").click(function(){
					//AlertDialog.tip("请先实名认证");
					window.location.href = path + "/common/realNameRZ.html";
				});
				//会员认证
				$("#memberRZ").html('<i class="i1"></i>未开通');
				$("#memberBtn").text("去开通").click(function(){
					window.location.href = path + "/common/realNameRZ.html";
				});
			}
			//绑卡
			if(data["isBindCard"]){
				$("#CiconRZ").html('<i class="i2"></i>已绑定');
				$.ajax({
					url: path + "/bank/getUserBank.html",
					type: "post",
					dataType: "json",
					success: function(data){
						l = data["msg"].length;
						$("#bankCard").text("已绑定银行卡 "+l+" 张");
					},
					error: function(request){
						console.log("获取绑定的银行卡异常");
					}
				});
				
				
			}else{
				$("#CiconRZ").html('<i class="i1"></i>未绑定');
				$("#bankCard").text("还未绑定银行卡");
			}
			if(!data["user"]["email"]){
				$("#emailRZ").html('<i class="i1"></i>未认证');
				$("#email").text("虚拟回报需要邮箱地址");
				$("#modifyEmail").text("去认证").click(modifyEmail);
			}else{
				$("#emailRZ").html('<i class="i2"></i>已认证');
				$("#email").text(data["user"]["email"]);
				$("#modifyEmail").text("已认证");
			}
			
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}