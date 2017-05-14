$(function(){
	getSecondMenu();//动态获取二级菜单
	$.ajax({
		type: "post",
		url : path + "/node/getNode.html",
		data: {
			nodeType:"ggjj"
		},
		dataType: "json",
		success:function(data){
			if(data["success"] && data["msg"].length > 0){
				$(".aboutbox").html(data["msg"][0]["body"]);
			}else{
				$(".aboutbox").html("暂无数据！");
			}
		},
		error:function(){
			console.log("公司简介查询异常");
		}
	});
});