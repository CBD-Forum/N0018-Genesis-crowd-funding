if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
var type = getQueryString("type");
$(function(){
	$("input").keyup(function(){
		if($(this).val().indexOf(" ")>=0){
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	})
	if(type){
		$("#return").show();
		$("#enterpriseBtn").hide();
		$("#valiCode").parent().hide();
		$("#verifycode").parent().hide();
		$.ajax({
			url: path + "/espSignApi/queryUserSignInfo.html",
			type: "post",
			dataType: "json",
			success: function(data){
				var data = data["signature"];
				$("#orgName").val(data["orgName"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#usci").val(data["usci"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				//$("#orgCode").val(data["orgCode"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#businessNum").val(data["businessNum"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#legalPersonName").val(data["legalPersonName"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#transactorMobile").val(data["transactorMobile"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#yinzhang").show();
				$("#img").attr("src","data:image/jpeg;base64,"+data["signImg"]);
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
		
	}else{
		$("#sendViliimgBtn").click(verifycode);
		$("#enterpriseBtn").click(enterprise);
	}
	$.ajax({
		url: path + "/enterpriseMember/getById.html",
		type: "post",
		dataType: "json",
		data:{
			"userId":siteUserId
		},
		success: function(data){
			var data = data["msg"];
			$("#orgCode").val(data["organizationNo"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");;
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
	getUserDetail();//获取个人信息
});
function verifycode(){
	if(Valify.isNull($("#orgName").val(),"orgName",10,20)){
		//if(Valify.isNull($("#usci").val(),"usci",10,20)){
			if(Valify.isNull($("#orgCode").val(),"orgCode",10,20)){
				//if(Valify.isNull($("#businessNum").val(),"businessNum",10,20)){
					if(Valify.realName($("#legalPersonName").val(),"legalPersonName",10,20)){
						if(Valify.phone($("#transactorMobile").val(),"transactorMobile")){
							if(Valify.isNull($("#valiCode").val(),"valiCode",10,20)){
								$.ajax({
									url: path + "/espSignApi/sendVerifyCode.html",
									type: "post",
									dataType: "json",
									data:{
										"usage":"user/create",
										"mobile":$("#transactorMobile").val(),
										"valiCode":$("#valiCode").val()
									},
									success: function(data){
										if(data["success"]){
											$("#sendViliimgBtn").unbind("click").css({"background":"#CCC","color":"#fff","cursor":"default"}).text("60秒后可重发");
											countDown(60, "sendViliimgBtn", overFnCode);
										}else{
											AlertDialog.tip(data["msg"]);
										}
									},
									error: function(request){
										console.log("获取个人信息异常");
									}
								});
							}
						}
					}
				//}
			}
		//}
	}
	
}
function overFnCode(){
	$("#sendViliimgBtn").text("获取").css({"cursor":"pointer","background":"#ff9e4e"});
	$("#sendViliimgBtn").click(verifycode);
}

function enterprise(){
	if(Valify.isNull($("#orgName").val(),"orgName",10,20)){
		//if(Valify.isNull($("#usci").val(),"usci",10,20)){
			if(Valify.isNull($("#orgCode").val(),"orgCode",10,20)){
				//if(Valify.isNull($("#businessNum").val(),"businessNum",10,20)){
					if(Valify.realName($("#legalPersonName").val(),"legalPersonName",10,20)){
						if(Valify.phone($("#transactorMobile").val(),"transactorMobile")){
							if(Valify.isNull($("#valiCode").val(),"valiCode",10,20)){
								if(Valify.isNull($("#verifycode").val(),"verifycode",10,20)){
									$.ajax({
										url: path + "/espSignApi/createUser.html",
										type: "post",
										dataType: "json",
										data:{
											"userType":1,
											"orgName":$("#orgName").val(),
											"usci":$("#usci").val(),
											"orgCode":$("#orgCode").val(),
											"businessNum":$("#businessNum").val(),
											"legalPersonName":$("#legalPersonName").val(),
											"transactorName":$("#transactorName").val(),
											"transactoridCardNum":$("#transactoridCardNum").val(),
											"transactorMobile":$("#transactorMobile").val(),
											"authCode":$("#verifycode").val()
										},
										success: function(data){
											if(data["success"]){
												window.location.href = path + "/common/accountSecurity.html" ;
											}else{
												AlertDialog.tip(data["msg"]);
											}
										},
										error: function(request){
											console.log("获取个人信息异常");
										}
									});
								}
							}
						}
					}
				//}
			}
		//}
	}
	
}

//获取个人信息
function getUserDetail(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			data = data["user"];
			$("#transactorName").val(data["realName"]).css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
			$("#transactoridCardNum").val(data["certificateNo"]).css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
			$("#transactorMobile").val(data["mobile"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
			
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}