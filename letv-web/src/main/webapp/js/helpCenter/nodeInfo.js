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
		}
	});
	$.ajax({
		type : "POST",
		url : path + '/node/getNode.html',
		data : {
			"nodeType":code
		},
		success : function(data) {
			if(data["success"]){
				if(data["msg"][0]["id"]){
					$("#title").text(data["msg"][0]["title"]);
					$("#cotent").html(data["msg"][0]["body"]);					
				}else{
					$("#cotent").html('<span style="padding:20px;color:#c30d23;">暂无数据</span>');
				}
			}
		},
		error:function(){
			
		}
	});
});
