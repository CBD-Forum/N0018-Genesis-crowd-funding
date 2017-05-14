var id = getQueryString("id");
$(function(){
	$.ajax({
		url: path + "/node/getNodeDetail.html",
		type: "post",
		dataType: "json",
		data: {"id": id},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			$("#breadTop").text(data["title"]);
			$("#title").text(data["title"]);
			$("#time").text(data["createTime"].substring(0,4)+"年"+data["createTime"].substring(5,7)+"月" + data["createTime"].substring(8,10)+"日      "+data["createTime"].substring(11,16));
			$("#content").html(data["body"]);
		},
		error: function(request){
			console.log("获取众筹知识项目详情异常");
		}
	});
});