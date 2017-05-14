$(function(){
	getSecondMenu();//动态获取二级菜单
	var url = window.location.href;
	var code = url.substring(url.indexOf("=")+1);
	$("#secondMenu>li>a").each(function(){
		var _this = $(this);
		if(_this.attr("code") == code){
			$("#title").text(_this.text());
			$("#secondMenu").find("a").removeClass("cur");
			_this.addClass("cur");
			$("#aboutboxTit").html(_this.text());
			$("#topNavigation").text(_this.text());
		}
	});
	$.ajax({
		type: "post",
		url : path + "/node/getNode.html",
		data: {
			nodeType:code
		},
		dataType: "json",
		success:function(data){
			if(data["success"]){
				if(data["msg"].length > 0){
					
					$(".aboutbox").html(data["msg"][0]["body"]);					
				}else{
					$(".aboutbox").html("<li style='padding: 20px;'>暂无数据！</li>");
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