if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
var pageNum = 1;
var commentNum = 1;
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	$("#messageMore").click(function(){
		getMessageList(pageNum);
	});
	$("#commentMore").click(function(){
		getCommentList(commentNum);
	});
	changeTab();
});
//标题样式切换
function changeTab(){
	var $taba = $("#commTitle a");
	$.each($taba, function(k, v){
		$(v).click(function(){
			$taba.removeClass("cur");
			$(v).addClass("cur");
			$("#commInfo>div").hide();//隐藏所有列表div
			if($(v).attr("hasc") == "1"){
				$("#" + $(v).attr("id") + "_data").show();
				if($("#" + $(v).attr("id") + "_data li").length >0){
					$("#" + $(v).attr("id") + "Page").show();
				}
			}else{
				if($(v).attr("id") == "message"){
					getMessageList(pageNum);
				}else{
					getCommentList(commentNum);
				}
			}
		});
	});
	getMessageList(1);
}
//系统消息
function getMessageList(page){
	var mArr = [], mStr = '', l, list, total;
	$.ajax({
		url: path + "/message/getMessageList.html",
		type: "post",
		dataType: "json",
		data:{
			"page":page,
			"rows":6
		},
		success: function(data){
			if(!data["success"]){
				mStr = '<span class="nodata">暂无数据</span>';
				$("#messageMore").remove();
				$("#messageList").html(mStr).show();
				$("#message_data").show();
				return false;
			}
			l = data["msg"]["rows"].length,total = data["msg"]["total"], list = data["msg"]["rows"];
			if(l == 0 || total == 0){
				mStr = '<span class="nodata" style="color:#ea544a;">暂无数据</span>';
				$("#messageList").remove();
				$("#messageMore").hide();
				$("#message_data").html(mStr).show();
				return false;
			}else{
				for(var i =0;i<l;i++){
					mArr.push('<div class="message clearfix">');
					/*mArr.push('<div class="c_img"><img src="" width="100%" /></div>');*/
					mArr.push('<div class="c_con" style="width:95%">');
					mArr.push('<p>');
					if(list[i]["messageChildTypeName"]){
						mArr.push('<label>'+list[i]["messageChildTypeName"]+'</label>');
					}else{
						mArr.push('<label>--</label>');
					}
					var sendTime = list[i]["sendTime"]?list[i]["sendTime"].substring(5,16):"";
					mArr.push('<label style="float:right;font-size:12px;color:#d7d7d7;margin-right:10px;">'+sendTime+'</label>');
					mArr.push('</p>');
					mArr.push('<p>'+list[i]["content"]+'</p>');
					mArr.push('</div>');
					mArr.push('</div>');
				}
				mStr = mArr.join("");
				$("#messageList").append(mStr);
				$("#message").attr("hasc", "1");
				$("#message_data").show();
				pageNum++;
				if($("#messageList>div").length == total){
					$("#messageMore").remove();
				}else{
					$("#messageMore").show();
				}
			}
		},
		error: function(request){
			console.log("获取系统消息异常");
		}
	});
}
/*我的评论*/
function getCommentList(page){
	var cArr = [], cStr = '', l, list, sumPage;
	$.ajax({
		url: path + "/crowdfundUserCenter/getMyCommentList.html",
		type: "post",
		dataType: "json",
		data:{
			"page":page,
			"rows":6
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"]["rows"].length, list = data["msg"]["rows"],total = data["msg"]["total"];
			if(l == 0){
				cStr = '<span class="nodata" style="color:#ea544a;">暂无数据</span>';
				$("#commentMore").hide();
				$("#commentList").remove();
				$("#comment_data").html(cStr).show();
				//$("#commInfo").css({"background":"#fff","text-align":"center","padding":"0,auto"})
				return false;
			}
			for(var i =0;i<l;i++){
				cArr.push('<div class="message clearfix">');
				cArr.push('<div class="c_con" style="width:95%">');
				cArr.push('<p>');
				cArr.push('<label style="color:#B2B2B2;">'+list[i]["discussTime"]+'</label>');
				cArr.push('</p>');
				cArr.push('<p>'+list[i]["content"]+'</p>');
				cArr.push('</div>');
				cArr.push('</div>');
			}
			cStr = cArr.join("");
			$("#comment").attr("hasc", "1");
			$("#commentList").html(cStr);
			$("#comment_data").show();
			
			commentNum++;
			if($("#commentList>div").length == total){
				$("#commentMore").hide();
			}else{
				$("#commentMore").show();
			}
		},
		error: function(request){
			console.log("获取我的评论异常");
		}
	});
}