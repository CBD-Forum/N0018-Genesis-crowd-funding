if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	getUserInfoDetail();
	getBankList();//查询银行卡信息
	showProvince();
	
	$("#bindCardBtn").click(subBindCard);//绑定银行卡功能
	$("#getVertifyb").click(getVertifyb);//发送手机验证码
	
});
//查询银行卡信息
function getBankList(){
	var bArr = [], bStr = '', l;
	$.ajax({
		url: path + "/recharge/getRechargeBankList.html",
		type: "post",
		dataType: "json",
		success: function(data){
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				return false;
			}
			bArr.push('<option value="">请选择开户银行</option>');
			for(var i=0;i<l;i++){
				bArr.push('<option value="'+data[i]["bankCode"]+'">'+data[i]["bankName"]+'</option>');
			}
			bStr = bArr.join("");
			$("#bankName").html(bStr);
		},
		error: function(request){
			console.log("获取银行卡信息异常");
		}
	});
}
//获取个人信息
function getUserInfoDetail(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			data = data["user"];
			$("#BrealName").text(data["realName"].substring(0,1)+"**");
			$("#BcertificateNo").text(data["certificateNo"].substring(0,4)+"**********"+data["certificateNo"].substring(data["certificateNo"].length-4,data["certificateNo"].length));
		},error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//绑定银行卡发送手机验证码
function getVertifyb(){
	$("#getVertifyb").unbind("click").text("请稍后...").css({"color":"#CCC", "cursor":"default", "border":"none"});
	$.ajax({
		url: path + "/verifycode/sendUserVerifyCode.html",
		type: "post",
		dataType: "json",
		data: {"codeType": "bind_bank_card"},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			$("#getVertifyb").unbind("click").text("60秒后发送").css({"color":"#CCC", "cursor":"default", "border":"none"});
			countDown(60, "getVertifyb", bOverFn);
		},
		error: function(request){
			$("#getVertifyb").text("获取验证码").css({"cursor":"pointer","color":"#0697da","border":"1px solid #aad5f7", "background":"none"});
			$("#getVertifyb").click(getVertifyb);
			console.log("发送绑定银行卡信息异常");
		}
	});
}
function bOverFn(){
	$("#getVertifyb").text("获取验证码").css({"cursor":"pointer","color":"#0697da","border":"1px solid #aad5f7", "background":"none"});
	$("#getVertifyb").click(getVertifyb);
}
//绑定银行卡事件
function subBindCard(){
	if(Valify.isNull($("#bankName").val(), "bankName", 10, 20)){
		if(Valify.isNull($("#province").val(), "province", 10, 20)){
			if(Valify.isNull($("#city").val(), "city", 10, 20)){
				if(Valify.isNull($("#bankNo").val(), "bankNo", 10, 20)){
					if($("#bankNo").val().length>20){
						AlertDialog.show("开户行银行卡号不能超过20位！","bankNo", 10, 20);
						return false;
					}
					if(Valify.isNull($("#phoneCode1").val(), "phoneCode1", 10, 20)){
						$(".bgpop,.Load").show();
						$.ajax({
							url: path + "/sxyPay/bindBnak/createUserAccount.html",
							type: "post",
							dataType: "json",
							data: {
		//						"ownerName": $("#realname").val(),
		//						"bankType": $("#cardCode").val(),
								"bankProvince": $("#province").val(),
								"bankCity": $("#city").val(),
								"bank": $("#bankName").val(),
								"openAcctBank": $("#bankAddress").val(),
								"bankAccount": $("#bankNo").val(),
								"verifyCode": $("#phoneCode1").val()
							},
							success: function(data){
								if(!data["success"]){
									$(".Load").hide();
									AlertDialog.tip(data["msg"]);
//									AlertDialog.show("验证码错误","phoneCode1", 10, 20);
									return false;
								}
								$(".Load").hide();
								AlertDialog.tip("绑定成功!");
								setTimeout(function(){
									location.href = path+"/common/bankList.html";
								},2000);
								$("#alertSure").click(function(){
									 location.href = path+"/common/bankList.html";
								});
							},
							error: function(request){
								console.log("绑定银行卡异常");
							}
						});
					}
				}
			}
		}
	}
}
//展示省份下拉数据
function showProvince(){
	//获取省份
	var pArr = [], pStr = '', l;
	$.ajax({
		url: path + "/area/getProvince.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			pArr.push('<option value="">请选择省</option>');
			for(var i=0;i<l;i++){
				pArr.push('<option value="'+data[i]["id"]+'">'+data[i]["shortName"]+'</option>');
			}
			pStr = pArr.join("");
			$("#province").html(pStr);
			//点击省份后，根据省份id查询城市
			$("#province").change(function(){
				showCity($(this).val());
			});
		},
		error: function(request){
			console.log("获取省份信息异常");
		}
	});
}
//展示城市下拉数据
function showCity(pid){
	var cArr = [], cStr = '', cl, list;
	$.ajax({
		url: path + "/area/getCity.html",
		type: "post",
		dataType: "json",
		data: {"pid": pid},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			cl = data["msg"]["rows"].length, list = data["msg"]["rows"];
			cArr.push('<option value="">请选择市</option>');
			for(var i=0;i<cl;i++){
				cArr.push('<option value="'+list[i]["id"]+'">'+list[i]["name"]+'</option>');
			}
			cStr = cArr.join("");
			$("#city").html(cStr);
		},
		error: function(request){
			console.log("获取城市信息异常");
		}
	});
}