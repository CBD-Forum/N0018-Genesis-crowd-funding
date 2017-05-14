var pageNum = 1; //分页
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
	var url = window.location.href;
	var code = url.substring(url.indexOf("=")+1);
	$.ajax({
		type: "post",
		dataType: "json",
		url : path + "/node/getNode.html",
		data: {
			nodeType:code
		},
		success:function(data){
			if(data["success"]){
				if(data["msg"].length > 0){
					$(".aboutbox").html("<p class='tit'>" + data["msg"][0]["title"] + "</p>" + data["msg"][0]["body"]);					
				}else{
					$(".aboutbox").html("<li>暂无数据！</li>");
				}
			}else{
				console.log("最新动态详情查询失败");
			}
		},
		error:function(){
			console.log("最新动态详情查询异常");
		}
	});
});