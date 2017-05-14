var viliShow = false;//判断是否登录需要验证验证码

//表单登录验证
$(function(){
	
	//检测密码
	$("#password").blur(function(){
		checkNull($(this).val(), "password", "密码不能为空");
	});
	
	//更换验证码
	$("#changeBtn").click(function(){
		$("#imageStream1").attr("src",path + "/security/securityCodeImage.html?" + new Date().getTime());
		$("#authcode").focus();
	});
	$("#loginBtn").click(loginSub);
	//回车击中登录
	document.onkeydown = function(event){
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if(e && e.keyCode==13){
			loginSub();
		}
	};
});

//登录提交总体验证事件
function loginSub(){
	if(Valify.phone($("#phoneNum").val(), "phoneNum")){
		if(Valify.isNull($("#password").val(), "password", 10,20)){
			loginSubAjax();
		}
	}
	//登录提交事件
	function loginSubAjax(){
		$.ajax({
			url: path + "/user/login.html",
			type: "post",
			dataType: "json",
			data: {
				"userId": $("#phoneNum").val(),
				"password": $("#password").val()
			},
			success: function(data){
				if(data["msg"] == "success"){
					document.cookie = "logined=y;path=/;";
//					if(url.indexOf("?")>=0){
//						window.location.href = url+"&login=0";
//					}else{
//						window.location.href = url+"?login=0";
//					}
					window.location.href = path+"/common/index.html";
				}else{
					AlertDialog.tip(data["msg"]);
				}
			},
			error: function(){
				console.log("获取登录提交信息异常");
			}
		});
	}
}
//验证码实时验证
function checkValiDate(str, id, imgId){
	var valiCode = str, result = false;
	return Valify.valiCode(valiCode, result, id, imgId);
}
