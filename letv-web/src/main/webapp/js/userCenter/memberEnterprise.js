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
	$("#enterpriseName").keyup(function(){
		if($(this).val().length>50){
			AlertDialog.show("企业名称不能超过50个字！", "enterpriseName", 0, 30, "jump");
			$(this).val($(this).val().substring(0,50));
		}
	});
	$("#organizationNo").keyup(function(){
		if($(this).val().length>18){
			AlertDialog.show("组织机构代码不能超过18个字！", "organizationNo", 0, 30, "jump");
			$(this).val($(this).val().substring(0,18));
		}
	});
	$("#legalPerson").keyup(function(){
		if($(this).val().length>10){
			AlertDialog.show("企业法人不能超过10个字！", "legalPerson", 0, 30, "jump");
			$(this).val($(this).val().substring(0,10));
		}
	});
	if(type){
		$("#enterpriseBtn").hide();
		$("#return").show();
		$.ajax({
			url: path + "/enterpriseMember/getById.html",
			type: "post",
			dataType: "json",
			data:{
				"userId":siteUserId
			},
			success: function(data){
				var data = data["msg"];
				$("#enterpriseName").val(data["enterpriseName"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#legalPerson").val(data["legalPerson"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#legalPersonPhone").val(data["legalPersonPhone"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#organizationNo").val(data["organizationNo"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#licenseNo").val(data["licenseNo"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#licenseAddress").val(data["licenseAddress"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#licenseExpireDate").val(data["licenseExpireDate"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#businessScope").val(data["businessScope"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#website").val(data["website"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#address").val(data["address"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
				$("#summary").val(data["summary"]).attr("readonly","readonly").css({"background":"#eee","color":"#bbb"}).attr("placeholder","");
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
	}else{
		$("#enterpriseBtn").click(enterprise);
	}
	
	getUserDetail();//获取个人信息
});

function enterprise(){
	if(Valify.isNull($("#enterpriseName").val(),"enterpriseName",10,20)){
		if(Valify.isNull($("#organizationNo").val(),"organizationNo",10,20)){
			$.ajax({
				url: path + "/enterpriseMember/saveEnterPriseMember.html",
				type: "post",
				dataType: "json",
				data:{
					"loginName":$("#loginName").val(),
					"memberName":$("#memberName").val(),
					"telephone":$("#telephone").val(),
					"enterpriseName":$("#enterpriseName").val(),
					"legalPerson":$("#legalPerson").val(),
					"legalPersonPhone":$("#legalPersonPhone").val(),
					"organizationNo":$("#organizationNo").val(),
					"licenseNo":$("#licenseNo").val(),
					"licenseAddress":$("#licenseAddress").val(),
					"licenseExpireDate":$("#licenseExpireDate").val(),
					"businessScope":$("#businessScope").val(),
					"website":$("#website").val(),
					"address":$("#address").val(),
					"summary":$("#summary").val(),
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
		}
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
			$("#loginName").val(data["userId"]).attr("readonly","readonly");;
			$("#memberName").val(data["nickName"]).attr("readonly","readonly");;
			$("#telephone").val(data["mobile"]).attr("readonly","readonly");;
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}