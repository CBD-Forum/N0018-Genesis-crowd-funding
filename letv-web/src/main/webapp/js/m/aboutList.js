if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
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
	getAboutIChouList(); //获取关于爱筹网的二级菜单
});
//获取关于爱筹网的二级菜单
function getAboutIChouList(){
	$.ajax({
		url: path + "/auth/getAboutIChouList.html",
		type: "post",
		dataType: "json",
		async:false,
		success: function(data){
			var html = [],l = data.length;
			for(var i = 0; i<l; i++){
				var url = data[i]["url"];
				html.push('<li><a href="' + path + url +'">' + data[i]["label"] + '</a></li>');
			}
			$("#secondMenu").html(html.join(""));
		},
		error: function(request){
			console.log("获取二级菜单列表异常");
		}
	});
}