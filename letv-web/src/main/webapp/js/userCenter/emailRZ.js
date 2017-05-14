if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	getUserInfoDetail();
	$("#sendBtn").click(sendCode);
	$("#saveEmail").click(checkEmail);
});
//获取个人信息
function getUserInfoDetail(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			if(data["photo"]){
				$("#centerUserPhoto").attr("src", cv["fileAddress"] + data["photo"]); //头像
			}else{
				$("#centerUserPhoto").attr("src", path + "/images/defaultPhoto.png"); //头像
			}
			$("#userRealName").text(data["realName"]); //真实姓名
			if(data["loanProvinceName"]){
				$("#address").html(data["loanProvinceName"]+"&nbsp;&nbsp;"+data["loanCityName"]); //地址
			}else{
				$("#address").text("--"); //地址
			}
			$("#createTime").text(data["createTime"].substring(0,10));
			if(data["selfDetail"]){
				$("#selfDetail2").html('<span class="C999">简介：</span>'+data["selfDetail"]).attr("title", data["selfDetail"]);
			}else{
				$("#selfDetail2").html('<span class="C999">简介：</span>他很忙，忙的什么都没来及留下。');
			}
		},error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//发送邮箱认证码
function sendCode(){
	if(Valify.email($("#email").val(), "email")){
		$("#sendBtn").unbind("click").css({"cursor":"default","color":"#888"}).text("稍后可重发");
		$.ajax({
			url: path + "/verifycode/sendEmailAuthCode.html",
			type: "post",
			dataType: "json",
			data: {
				"sendTarget": $("#email").val()
			},
			success: function(data){
				if(data["success"]){
					$("#sendBtn").unbind("click").css({"cursor":"default","color":"#888"}).text("60 秒后可重发");
					$("#sendTipLi").text("邮件已发送至邮箱，请查收！").css({"visibility":"visible"});
					countDown(60, "sendBtn", regiterOverFn);
				}
			},
			error: function(request){
				console.log("获取发送邮箱认证码信息异常");
			}
		});
	}
}
function regiterOverFn(){
	$("#sendTipLi").css({"visibility":"hidden"});
	$("#sendBtn").text("发送邮箱认证码").css({"margin-left":"5px", "font-size":"14px", "padding":"0 10px"});
	$("#sendBtn").click(sendCode);
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
	if(Valify.email($("#email").val(), "email")){
		if(isNull($("#emailCode").val(), "emailCode")){
			$.ajax({
				url: path + "/safeLevel/authEmail.html",
				type: "post",
				dataType: "json",
				data: {
					"sendTarget": $("#email").val(),
					"verifyCode": $("#emailCode").val()
				},
				success: function(data){
					if(data["success"]){
						AlertDialog.tip("认证成功");
						setTimeout(function(){
							window.location.href = path + "/common/myCenter.html";
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