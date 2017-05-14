$(function(){
	
	
	getSecondMenu();//动态获取二级菜单
	var url = window.location.href;
	var code = url.substring(url.indexOf("=")+1);
	$("#secondMenu>li>a").each(function(){
		var _this = $(this);
		if(_this.attr("code") == code){
			$("#title").text(_this.text());
			$(".tit").text(_this.text());
			$("#secondMenu").find("a").removeClass("cur");
			_this.addClass("cur");
			$("#topNavigation").text(_this.text());
			$("#aboutboxTit").html(_this.text());
		}
	});
	$.ajax({
		url : path + "/node/getNode.html",
		type: "post",
		dataType: "json",
		data: {
			nodeType:code
		},
		success:function(data){
			if(data["success"]){
				
				if(data["msg"].length > 0){
					var html = [];
					for(var i=0;i<data["msg"].length;i++){
						html.push("<li><span class='time'>" + data["msg"][i]["createTime"] + "</span><a href='" + path + "/common/notice_info.html?id=" + data["msg"][i]["id"] + "&nodeType=" + code + "'>" + data["msg"][i]["title"] + "</a></li>");
					}
					$("#dynUl").html(html.join(""));				
				}else{
					$("#dynUl").html("<li style='padding:20px;border:0;'>暂无数据！</li>");
				}
			}else{
				console.log("网站公告查询失败");
			}
		},
		error:function(){
			console.log("网站公告查询异常");
		}
	});
});