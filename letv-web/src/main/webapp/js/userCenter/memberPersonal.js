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
		$("#personalBtn").hide();
		$("#return").show();
		$.ajax({
			url: path + "/personal/getById.html",
			type: "post",
			dataType: "json",
			data:{
				"userId":siteUserId
			},
			success: function(data){
				var data = data["msg"]
				$("#email").val(data["email"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#career").val(data["career"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
	}else{
		$("#personalBtn").click(personal);
	}
	getUserDetail();//获取个人信息
});

function personal(){
	//if(Valify.email($("#email").val(), "email")){
	$.ajax({
		url: path + "/personal/savePsersonalMember.html",
		type: "post",
		dataType: "json",
		data:{
			"memberName":$("#memberName").val(),
			"realName":$("#realName").val(),
			"idCardNo":$("#idCardNo").val(),
			"mobile":$("#mobile").val(),
			"email":$("#email").val(),
			"career":$("#career").val(),
			"userId":siteUserId
		},
		success: function(data){
			if(data["success"]){
				window.location.href = path + "/common/accountSecurity.html" ;
			}else{
				AlertDialog.RZmtip(data["msg"]);
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
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
			$("#idCardNo").val(data["certificateNo"]);
			$("#realName").val(data["realName"]);
			$("#memberName").val(data["nickName"]);
			$("#mobile").val(data["mobile"]);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}