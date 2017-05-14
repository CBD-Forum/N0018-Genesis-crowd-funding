$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	$.ajax({
		url: path + "/node/getNode.html",
		type: "post",
		dataType: "json",
		data: {"nodeType": "investment_service_agreement"},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			$("#agreeBox").html(data["msg"][0]["body"]);
		}
	});
});