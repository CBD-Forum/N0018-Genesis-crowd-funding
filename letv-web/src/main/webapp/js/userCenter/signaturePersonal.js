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
		$("#personalBtn").hide();
		$("#valiCode").parent().hide();
		$("#verifycode").parent().hide();
		$.ajax({
			url: path + "/espSignApi/queryUserSignInfo.html",
			type: "post",
			dataType: "json",
			success: function(data){
				var data = data["signature"]
				$("#passportNum").val(data["passportNum"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#mtpNum").val(data["mtpNum"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#mobile").val(data["mobile"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#yinzhang").show();
				$("#img").attr("src","data:image/jpeg;base64,"+data["signImg"]);
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
	}else{
		$("#sendViliimgBtn").click(verifycode);
		$("#personalBtn").click(personal);
	}
	getUserDetail();//获取个人信息
});
function verifycode(){
	//if(Valify.isNull($("#passportNum").val(),"passportNum",10,20)){
		if(Valify.phone($("#mobile").val(),"mobile")){
			if(Valify.isNull($("#valiCode").val(),"valiCode",10,20)){
				$.ajax({
					url: path + "/espSignApi/sendVerifyCode.html",
					type: "post",
					dataType: "json",
					data:{
						"usage":"user/create",
						"mobile":$("#mobile").val(),
						"valiCode":$("#valiCode").val()
					},
					success: function(data){
						if(data["success"]){
							$("#sendViliimgBtn").unbind("click").css({"background":"#CCC","cursor":"default"}).text("60秒后可重发");
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
	//}
	
}
function overFnCode(){
	$("#sendViliimgBtn").text("获取").css({"cursor":"pointer","background":"#ff9e4e"});
	$("#sendViliimgBtn").click(verifycode);
}

function personal(){
	//if(Valify.isNull($("#passportNum").val(),"passportNum",10,20)){
		if(Valify.phone($("#mobile").val(),"mobile")){
			if(Valify.isNull($("#valiCode").val(),"valiCode",10,20)){
				if(Valify.isNull($("#verifycode").val(),"verifycode",10,20)){
					$.ajax({
						url: path + "/espSignApi/createUser.html",
						type: "post",
						dataType: "json",
						data:{
							"userType":0,
							"personalName":$("#personalName").val(),
							"idCardNum":$("#idCardNum").val(),
							"passportNum":$("#passportNum").val(),
							"mtpNum":$("#mtpNum").val(),
							"mobile":$("#mobile").val(),
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
	//}
	
}

//获取个人信息
function getUserDetail(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			data = data["user"];
			$("#personalName").val(data["realName"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
			$("#idCardNum").val(data["certificateNo"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
			$("#mobile").val(data["mobile"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}