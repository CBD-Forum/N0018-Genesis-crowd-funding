$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	//检测用户名
	$("#loginUserId").blur(function(){
		checkNull($(this).val(), "用户名不能为空");
	});
	//检测密码
	$("#loginPassword").blur(function(){
		checkNull($(this).val(), "密码不能为空");
	});
	//检测验证码
	$("#loginValiCode").blur(function(){
		checkValiDate($(this).val(), "loginTip");
	});
	
	$("#loginBtn").click(go2Login);
});

//登录
function go2Login(){
	if(checkNull($("#loginUserId").val(), "用户名不能为空")){//用户名验证
		if(checkNull($("#loginPassword").val() ,"密码不能为空")){//密码验证
			if(checkValiDate($("#loginValiCode").val(), "loginTip")){
				$.ajax({
					url: path + "/user/login.html",
					type: "post",
					dataType: "json",
					data: {
							"userId": $("#loginUserId").val(),
							"password": $("#loginPassword").val(),
							"valiCode": $("#loginValiCode").val()
							},
					success: function(data){
						if(data["msg"] == "success"){
							document.cookie = "logined=y;path=/;";
							window.location.href = path + "/common/m-index.html";
						}else{
							$("#loginTip").text(data["msg"]).css("visibility","visible");
							$("#imageStream1").attr("src",path+"/security/securityCodeImage.html?"+new Date().getTime());
						}
					},
					error: function(){
						console.log("获取登录提交信息异常");
					}
				});
			}
		}
	}
}

//验证用户名、密码是否为空
function checkNull(str, type){
	if(!str){
		$("#loginTip").text(type).css("visibility","visible");
		return false;
	}
	$("#loginTip").css("visibility","hidden");
	return true;
}
//验证码实时验证
function checkValiDate(str, id){
	var valiCode = str, result = false;
	return MValify.valiCode(valiCode, result, id,true);
}
