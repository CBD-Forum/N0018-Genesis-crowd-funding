if(siteUserId == "null"){
	window.location.href = path + "/common/login.html";
}
var transferNo = getQueryString("transferNo");
$(function(){
	getCrowdfundTransferDetail();
});
//获取挂牌列表
function getCrowdfundTransferDetail(){
	$.ajax({
		url: path + "/crowdfundProductTransfer/getCrowdfundTransferDetail.html",
		type: "post",
		dataType: "json",
		data:{
			"transferNo":transferNo
		},
		success: function(data){
			if(!data["success"]){
				console.log("获取转让详情失败");
				return false;
			}
			var list = data["msg"];
			$("#loanName").text(list["loanName"]);
		},
		error:function(){
			console.log("获取挂牌接口失败")
		}
	});
}
