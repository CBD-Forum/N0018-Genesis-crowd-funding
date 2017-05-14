var type = getQueryString("type");
$(function(){
	var typeObj = {
			"know": "众筹知识",
			"info": "行业资讯"
	};
	$("#typec").text(typeObj[type]); //顶部面包屑赋值
	if(type == "know"){
		getZczsList("crowdfund_know"); //众筹知识列表
	}else{
		getZczsList("industry_news"); //众筹知识列表
	}
});
//查询众筹知识
function getZczsList(code){
	var zArr = [],zStr="",l;
	$.ajax({
		type : "post",
		url : path + '/node/getNode.html',
		data : {
			"nodeType":code
		},
		success : function(data) {
			if(!data["success"]){
				zStr = '<span style="padding:20px;color:#c30d23;">暂无数据</span>';
				$("#zczsList").html(zStr);
				return false;
			}
			l = data["msg"].length,list = data["msg"];
			for(var i=0;i<l;i++){
				if(type == "know"){
					zArr.push('<li><a class="zc_list_a" title="'+list[i]["title"]+'" href="'+path+'/common/zcKnowDetail.html?id='+list[i]["id"]+'">【'+list[i]["title"]+'】</a></li>');
				}else{
					zArr.push('<li><a class="zc_list_a" title="'+list[i]["title"]+'" href="'+path+'/common/zcKnowZXDetail.html?id='+list[i]["id"]+'">【'+list[i]["title"]+'】</a></li>');
				}
			}
			zStr = zArr.join("");
			$("#zczsList").html(zStr);
		},
		error:function(request){
			console.log("获取众筹知识列表异常");
		}
	});
}