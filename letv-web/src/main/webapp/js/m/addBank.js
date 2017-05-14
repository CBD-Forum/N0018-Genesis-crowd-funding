if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	if(userId == "null"){ //未登录
		$("#myCenter").attr("href",path+"/common/m-login.html");
	}else{
		$("#myCenter").attr("href",path+"/common/m-myCenter.html");
	}
	showProvince();
	$("#bindCardBtn").click(subBindCard);//绑定银行卡功能
	$("#getVertifyb").click(getVertifyb);
});
//绑定银行卡发送手机验证码
function getVertifyb(){
	$("#getVertifyb").unbind("click").text("请稍后...").css({"color":"#FB4D01", "cursor":"default", "border":"none"});
	$.ajax({
		url: path + "/verifycode/sendUserVerifyCode.html",
		type: "post",
		dataType: "json",
		data: {"codeType": "mobile_verify"},
		success: function(data){
			if(!data["success"]){
				AlertDialog.mtip(data["msg"]);
				bOverFn();
				return false;
			}
			$("#getVertifyb").unbind("click").text("60秒后发送").css({"color":"#FB4D01", "cursor":"default", "border":"none","background":"#ccc"});
			countDown(60, "getVertifyb", bOverFn);
		},
		error: function(request){
			$("#getVertifyb").text("获取验证码").css({"cursor":"pointer","color":"#3b9ee3","border":"1px solid #aad5f7", "background":"none"});
			$("#getVertifyb").click(getVertifyb);
			console.log("发送绑定银行卡信息异常");
		}
	});
}
function bOverFn(){
	$("#getVertifyb").text("获取验证码").css({"cursor":"pointer","color":"#3b9ee3","border":"1px solid #aad5f7", "background":"none"});
	$("#getVertifyb").click(getVertifyb);
}
//绑定银行卡事件
function subBindCard(){
	if(MValify.realName($("#realname").val(), "realname")){
		if(MValify.cardCode($("#cardCode").val(), "cardCode")){
			if(MValify.isNull($("#province").val(), "province", -10, 100)){
//				if(MValify.isNull($("#city").val(), "city", -10, 140)){
					if(MValify.isNull($("#bankName").val(), "bankName", -10, 150)){
						if(MValify.isNull($("#bankAddress").val(), "bankAddress", -10, 150)){
							if(MValify.isNull($("#bankNo").val(), "bankNo", -10, 150)){
								if(MValify.isNull($("#phoneCode1").val(), "phoneCode1", -10, 150)){
									$.ajax({
										url: path + "/bank/bindUserBank.html",
										type: "post",
										dataType: "json",
										data: {
											"ownerName": $("#realname").val(),
											"bankType": $("#cardCode").val(),
											"bankProvince": $("#province").val(),
											"bankCity": $("#city").val(),
											"bank": $("#bankName").val(),
											"openAcctBank": $("#bankAddress").val(),
											"bankAccount": $("#bankNo").val(),
											"verifyCode": $("#phoneCode1").val()
										},
										success: function(data){
											if(!data["success"]){
												AlertDialog.mtip(data["msg"]);
												return false;
											}
											AlertDialog.mtip("绑定成功");
											setTimeout(function(){
												window.location.href = path+"/common/m-withdraw.html";
											},400);
										},
										error: function(request){
											console.log("绑定银行卡异常");
										}
									});
								}
							}
						}
					}
//				}
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