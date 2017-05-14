if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
$(function(){
	//返回上一页
	$("#back").click(function(){
		history.go(-1);
	});
	getUserInfo();//获取个人信息
	getUserInfoPhone();//获取个人手机号码
	centerBanner();//个人中心的背景图
});

//获取个人信息
function getUserInfo(){
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
			$("#userId").text(data["userId"]);
			if(data["loanProvinceName"]){
				$("#address").html(data["loanProvinceName"]); //地址
			}else{
				$("#address").text("--"); //地址
			}
			$("#totalSupportAmt").text(data["totalSupportAmt"].toFixed(0)); //累计投资项目总额
			$("#investLoanNum").text(data["investLoanNum"]); //投资项目总数
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//获取手机号码
function getUserInfoPhone(){
	$.ajax({
		url: path + "/user/getUserInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			var mobile = data["mobile"].substring(0,3)+"****"+data["mobile"].substring(7,11);
			$("#mobile").text(mobile);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//退出登录
function logout(){
	$.ajax({
		url: path + "/user/loginOut.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			window.location.href = path + "/common/m-index.html";
		},
		error: function(request){
			console.log("获取退出登录信息异常异常");
		}
	});
}
//个人中心的背景图
function centerBanner(){
	$.ajax({
		url: path + "/banner/getBannerByCode.html",
		type: "post",
		dataType: "json",
		data: {"code": "centerIndex"},
		success: function(data){
			$(".percen_top").css({"background":"url("+cv["fileAddress"]+data[0]["picture"]+") no-repeat center","background-size":"cover"});
		},
		error: function(request){
			console.log("获取个人中心背景图失败");
		}
	});
}