if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	//获取个人信息
	$.ajax({
		url: path + "/user/getUserInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			data = data["msg"];
			$("#realName").text(data["realName"].substring(0,1)+"**");
			$("#certificateNo").text(data["certificateNo"].substring(0,4)+"**********"+data["certificateNo"].substring(data["certificateNo"].length-4,data["certificateNo"].length));
			$("#createTime").text(data["createTime"]);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
});
