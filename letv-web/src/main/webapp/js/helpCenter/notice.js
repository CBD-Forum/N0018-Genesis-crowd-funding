$(function(){
	getSecondMenu();//动态获取二级菜单
	$.ajax({
		type: "post",
		url : path + "/node/getNode.html",
		data: {
			nodeType:"wzgg"
		},
		dataType: "json",
		success:function(data){
			if(data["success"]){
				if(data["msg"].length > 0){
					var html = [];
					for(var i=0;i<data["msg"].length;i++){
						html.push("<li><a href='" + path + "/common/notice_info.html?id=" + data["msg"][i]["id"] + "'>" + data["msg"][i]["title"] + "</a><span class='time'>" + data["msg"][i]["createTime"] + "</span></li>");
					}
					$("#dynUl").html(html.join(""));					
				}else{
					$("#dynUl").html("<li>暂无数据！</li>");
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