$(function(){
	getSecondMenu();//动态获取二级菜单
	var url = window.location.href;
	var code = url.substring(url.indexOf("=")+1);
	$("#secondMenu>li>a").each(function(){
		var _this = $(this);
		if(_this.attr("code") == code){
			$("#title").text(_this.text());
			_this.parent().removeClass("on").addClass("on").siblings().removeClass("on");
		}
	});
	$.ajax({
		type : "POST",
		url : path + '/node/getNodeDetail.html',
		data : {
			"id":code
		},
		success : function(data) {
			if(data["success"]){
				if(data["msg"]["id"]){
					$("#title").text(data["msg"]["title"]);
					$("#content").html(data["msg"]["body"]);					
				}else{
					$("#cotent").html('<span style="padding:20px;color:#c30d23;">暂无数据</span>');
				}
			}
		},
		error:function(){
			
		}
	});
});
