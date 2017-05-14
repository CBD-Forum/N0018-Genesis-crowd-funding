$(function(){
	
	getUsVideo();
});
//获取关于我们的视频
function getUsVideo(){
	$.ajax({
		url: path + "/node/getNode.html",
		type: "post",
		dataType: "json",
		data: {"nodeType": "aboutus_video"},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			$("#aboutusVideo").html('<embed width="594" height="324" src="'+data["msg"][0]["description"]+'" allowFullScreen="true" quality="high" align="middle"  mode="transparent" type="application/x-shockwave-flash"></embed>');
		},
		error: function(request){
			console.log("获取关于我们的视频异常");
		}
	});
}