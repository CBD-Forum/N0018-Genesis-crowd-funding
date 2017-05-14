var url = window.location.href;
var code = url.substring(url.indexOf("=")+1);
$(function(){
	getSecondMenu();//动态获取二级菜单
	$("#secondMenu>li").each(function(){
		var _this = $(this).children("a");
		if(_this.attr("code") == code){
			$("#title").text(_this.text());
			$(this).removeClass("on").addClass("on");
		}
	});
	$.ajax({
		type : "POST",
		url : path + '/node/getNode.html',
		data : {
			nodeType:code,
		},
		success : function(data) {
			var html = [];
			for(var i in data["msg"]){
				html.push('<li>');
				html.push('<a href="' + path + '/common/nodeList_info.html?nodeId=' + data["msg"][i]["id"] + '" title="' + data["msg"][i]["title"] + '">' + data["msg"][i]["title"] + '</a>');
				html.push('<span class="fr">' + data["msg"][i]["createTime"] + '</span>');
				html.push('</li>');
			}
			if(html.length){
				$("#nodelist").html(html.join(''));				
			}else{				
				$("#nodelist").html('<li style="color:red;">暂无信息</li>');				
			}
		},
		error:function(){
			
		}
	});
});
