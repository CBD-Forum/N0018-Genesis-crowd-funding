$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	//查看短信内容
	$("#showInfo").click(function(){
		$("#agreeContent").html('我是 '+realName+' ，邀请你注册"若水众筹"，百度“若水众筹”，注册填写邀请码&nbsp;&nbsp;<span style="color:#ea5505;">'+userId+'</span>【若水众筹】。');
		$("#bgpop").show();
		var al = (cv["winW"]-305)/2, at = (cv["winH"]-$("#agreeBoxInfo").height())/2;
		$("#agreeBoxInfo").css({"left":al+"px", "top":at+"px"}).show();
		$(".agree_close").click(function(){
			$("#agreeBoxInfo").fadeOut();
			$(".bgpop").hide();
		});
	});
	//发送邀请码
	$("#inviteBtn").unbind("click").click(sendInviteCode);
});
function sendInviteCode(){
	if(checkPhone($("#phone").val(), "phone")){
		AlertDialog.hide();
		if(checkValiCode($("#valicode").val(), false, "valicode")){
			AlertDialog.hide();
			$.ajax({
				url: path + "/verifycode/sendInviteCodeFundPool.html",
				type: "post",
				dataType: "json",
				data: {"mobile": $("#phone").val()},
				success: function(data){
					if(data["success"]){
						$("#inviteBtn").unbind("click");
						countDownFriend(60, "inviteBtn", function(){
							$("#inviteBtn").text("免费发送");
							$("#inviteBtn").unbind("click").click(sendInviteCode);
						});
						AlertDialog.tip("发送邀请码成功");
					}else{
						AlertDialog.tip(data["msg"]);
					}
				},
				error: function(request){
					console.log("获取邀请码异常");
				}
			});
		}else{
			AlertDialog.show("请输入正确的验证码", "valicode", -10, 160);
		}
	}else{
		AlertDialog.show("请输入正确的手机号码", "phone", -10, 160);
	}
}
function checkPhone(phone, id){
	var isPhoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
	if(!phone){
		AlertDialog.show("手机号不能为空", id, -5, 100);
		return false;
	}
	if(isPhoneReg.exec(phone)){
		AlertDialog.hide();
		return true;
	}else{
		AlertDialog.show("手机号不正确", id, -5, 100);
		return false;
	}
}
function checkValiCode(str, result, id, isAjax){
	if(!str){
		AlertDialog.show("图片验证码不能为空", id, -5, 100);
		return false;
	}
	if(isAjax == false){
		return result;
	}
	$.ajax({
		url: path + "/user/validateVerifyCode.html",
		type: "post",
		async: false,
		dataType: "json",
		data: {"valiCode": str},
		success: function(data){
			if(data["msg"] == "success"){
				AlertDialog.hide();
				result = true;
			}else{
				AlertDialog.show(data["msg"], id, -5, 100);
				result = false;
			}
		},
		error: function(){
			console.log("获取邀请码异常");
		}
	});
	return result;
}

//倒计时
function countDownFriend(time, id, overFn){
	time = Number(time);
	var countTime = setInterval(function(){
		time -= 1;
		$("#" + id).text(time + " 秒后可重发").css({"background":"#ccc","cursor":"default","color":"#fb4d01","border":"none"});
		if(time == 0){
			clearInterval(countTime);
			$("#" + id).attr({"style":"background:#248af9","border":"none"});
			overFn();
		}
	},1000);
}
