if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
var type = getQueryString("type");
$(function(){
	if(type){
		$("#indexUrl").css("display","inline-block");
	}
	$("#subInvestBtn").unbind("click").click(subInvestRZ);
	getUserDetail();//获取个人信息
});
//提交投资认证申请
function subInvestRZ(){
	if(Valify.realName($("#realname").val(), "realname")){
		if(Valify.cardCode($("#cardCode").val(), "cardCode")){
			if(Valify.isNull($("#bankcardNo").val(), "bankcardNo", 10, 20)){
				$.ajax({
					url: path + "/sxyPay/account/checkUserInfo.html",
					type: "post",
					dataType: "json",
					data: {
	//					"realName":$("#realname").val(),
						"certificateNo":$("#cardCode").val()
					},
					success: function(data){
						if(!data["success"]){
							AlertDialog.tip(data["msg"]);
							return false;
						}else{
							$("#bgpop").show();
							$(".LoadImg").show();
							/*$("#form_realName").val($("#realname").val());
							$("#form_certificateNo").val($("#cardCode").val());
							$("#form_bankCardNo").val($("#bankNo").val());
							$("#submitBtn").click();*/
							submitRZ();
						}
					},
					error: function(request){
						console.log("保存投资认证异常");
					}
				});
			}
		}
	}
}
function submitRZ(){
	$.ajax({
		url: path + "/sxyPay/account/createUserAccount.html",
		type: "post",
		dataType: "json",
		data: {
			realName:$("#realname").val(),
			certificateNo:$("#cardCode").val(),
			bankNo:$("#bankcardNo").val()
		},
		success: function(data){
			if(!data["success"]){
				$(".LoadImg").hide();
				AlertDialog.tip(data["msg"]);
				return false;
			}else{
				$(".LoadImg").hide();
				AlertDialog.tip("实名认证成功！");
				setTimeout(function(){
					location.href = path+"/common/realNameRZTG.html";
//					if(url.indexOf("?")>=0){
//						window.location.href = url+"&nameRz=0";
//					}else{
//						window.location.href = url+"?nameRz=0";
//					}
				},2000);
				$("#alertSure").click(function(){
					 location.href = path+"/common/realNameRZTG.html";
//					if(url.indexOf("?")>=0){
//						window.location.href = url+"&nameRz=0";
//					}else{
//						window.location.href = url+"?nameRz=0";
//					}
				});
			}
		},
		error: function(request){
			console.log("实名认证失败");
		}
	});
}
//获取个人信息
function getUserDetail(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			data = data["user"];
			$("#realname").val(data["realName"]);
			$("#cardCode").val(data["certificateNo"]);
			$("#bankCardNo").val(data["bankCardNo"]);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}