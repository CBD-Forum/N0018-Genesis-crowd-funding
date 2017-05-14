$(function(){
	getSecondMenu();//动态获取二级菜单
	var url = window.location.href;
	var id = url.substring(url.indexOf("?id=")+4,url.lastIndexOf("&"));
	var code = url.substring(url.lastIndexOf("=")+1);
	$("#secondMenu>li>a").each(function(){
		var _this = $(this);
		if(_this.attr("code") == code){
			$("#listTitle").text(_this.text());
			$("#listTitle").attr("href",$("#listTitle").attr("href")+code);
			_this.addClass("cur");

		}
	});
	$.ajax({
		type: "post",
		url : path + "/node/getNodeDetail.html",
		data: {
			"id":id
		},
		dataType: "json",
		success:function(data){
			if(data["success"]){
				if(data["msg"]){
					$("#aboutboxTit").text(data["msg"]["title"]);
					$(".aboutbox").html(data["msg"]["body"]);					
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