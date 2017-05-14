if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	changeTab();
	//关闭弹框
	$(".prompt_close").click(function(){
		$(".sbgpop").hide();
		$(".prompt_box").hide();
	});
	myEcreateWebUploader("image_file", "imgheadPhoto", "loan_logo_url", "imgheadLi");//上传头像
});
//标题样式切换
function changeTab(){
	var $tab = $("#commTitle>li>a");
	$.each($tab,function(k,v){
		$(v).click(function(){
			$tab.removeClass("a_home");
			$(this).addClass("a_home");
			$("#messagesBody>div").hide();
			$("#"+$(this).attr("vname")+"Body").show();
			if($(this).attr("vname") == "all"){
				getMessageList(1, $(this).attr("vname"));
			}else if($(this).attr("vname") == "unread"){
				getMessageList(1, $(this).attr("vname"), 0);
			}else if($(this).attr("vname") == "read"){
				getMessageList(1, $(this).attr("vname"), 1);
			}
		});
	});
	$tab.first().click();
}
//系统消息
function getMessageList(page, id, status){
	var mArr = [], mStr = '', l, list, sumPage;
	$.ajax({
		url: path + "/message/getMessageList.html",
		type: "post",
		dataType: "json",
		data:{
			"status":status,
			"page":page,
			"rows":6
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
			l = data["msg"]["rows"].length, list = data["msg"]["rows"];
			if(l == 0){
				$("#"+id+"ListBody>ul").hide();
				$("#"+id+"ListBody>div").html('<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>当前没有消息记录哦，快去做点什么吧~</div>').show();
				$("#"+id+"ListPage").hide();
			}else{
				for(var i =0;i<l;i++){
					if(list[i]["status"] == "0"){
						mArr.npush('<li class="colorF">');
					}else if(list[i]["status"] == "1"){
						mArr.npush('<li>');
					}
					if(list[i]["messageChildTypeName"]){
						mArr.npush('<a page="'+page+'" status="'+status+'" cid="'+id+'" aid="'+list[i]["id"]+'" onclick="showCont(event)" style="width:35%" href="javascript:void(0)">'+list[i]["messageChildTypeName"]+'</a>');
					}else{
						mArr.npush('<a page="'+page+'" status="'+status+'" cid="'+id+'" aid="'+list[i]["id"]+'" onclick="showCont(event)" style="width:35%" href="javascript:void(0)">--</a>');
					}
					mArr.npush('<p style="width:35%">'+list[i]["sendTime"]+'</p>');
					if(list[i]["status"] == "0"){
						mArr.npush('<p style="width:15%" class="red">未读</p>');
					}else if(list[i]["status"] == "1"){
						mArr.npush('<p style="width:15%">已读</p>');
					}
					mArr.npush('<a page="'+page+'" status="'+status+'" cid="'+id+'" aid="'+list[i]["id"]+'" onclick="deleteMsg(event)" style="width:15%;" href="javascript:void(0)">删除</a>');
					mArr.npush('</li>');
				}
				mStr = mArr.join("");
				$("#"+id+"ListBody>div").hide();
				$("#"+id+"ListBody>ul").html(mStr).show();
				$("#"+id+"ListPage").show();
				pagePause = 0;
				//分页设置
				if(page < 2){
					$("#"+id+"ListPage").jPages({
						containerID : ""+id+"ListBody",
						clickStop   : true,
						first: false,
				        previous: " ",
				        next: " ",
				        last: false,
						perPage	: 6,
						allSumPage : sumPage,
						callback: ajaxPageData
					});
				}
			}
		},
		error: function(request){
			console.log("获取系统消息异常");
		}
	});
}
//分页回调函数-系统消息
function ajaxPageData(obj){
	if(pagePause == 0){
		return false;
	}
	var vname = $("#commTitle>li>a.a_home").attr("vname");
	var status;
	if(vname == "all"){
		status;
	}else if(vname == "unread"){
		status = 0;
	}else if(vname == "read"){
		status = 1;
	}
	getMessageList(obj["current"], vname, status);
}
//查看消息详情
function showCont(event){
	var obj = event.srcElement || event.target;
	var id = $(obj).attr("aid");
	var page = $(obj).attr("page");
	var cid = $(obj).attr("cid");
	if($(obj).attr("status") == "undefined"){
		var status = "";
	}else{
		var status = $(obj).attr("status");
	}
	$.ajax({
		url: path + "/message/getMessageList.html",
		type: "post",
		dataType: "json",
		data:{
			"id":id
		},
		success: function(data){
			var list = data["msg"]["rows"];
			if(!data["success"]){
				return false;
			}
			$("#MessTit").text(list[0]["messageChildTypeName"]);
			if(list[0]["messageChildType"] == "interview_loan_msg"){
				var strMes = "<p>约谈内容:"+list[0]["content"]+"</p>"
				$("#MessCont").html(strMes);
			}else{
				$("#MessCont").text(list[0]["content"]);
			}
			var wh = document.documentElement.clientHeight, ww = document.documentElement.clientWidth;
			var lw = (ww - 505)/2, lt =  (wh - $("#MessDiv").height())/2;
			document.getElementById("MessDiv").style.top = lt+"px";
			document.getElementById("MessDiv").style.left = lw+"px";
			$(".sbgpop").show();
			$("#MessDiv").show();
			$.ajax({
				url: path + "/message/updateMsg.html",
				type: "post",
				dataType: "json",
				data: {
					"id" : id
				},
				success : function(data){
					if(status != "1"){
						getMessageList(page, cid, status);
					}
				},
				error: function(request){
					console.log("获取系统消息异常");
				}
			});
		},
		error: function(request){
			console.log("获取系统消息异常");
		}
	});
	
}
//删除
function deleteMsg(event){
	AlertDialog.confirm(DelMsgLoan, null, "您确定要删除消息吗？", "确定", "取消", null);
	var obj = event.srcElement || event.target;
	var id = $(obj).attr("aid");
	var page = $(obj).attr("page");
	var cid = $(obj).attr("cid");
	if($(obj).attr("status") == "undefined"){
		var status = "";
	}else{
		var status = $(obj).attr("status");
	}
	function DelMsgLoan(){
		$.ajax({
			url: path + "/message/batchDelete.html",
			type: "post",
			dataType: "json",
			data: {
				ids : id
			},
			success : function(data){
				getMessageList(page, cid, status);
				AlertDialog.mtip("删除成功");
			},
			error: function(request){
				console.log("获取系统消息异常");
			}
		});
	}
}
//我的评论
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
			sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
			l = data["msg"]["rows"].length, list = data["msg"]["rows"];
			if(l == 0){
				cStr = '<span style="color:#ea544a;">暂无数据</span>';
				$("#commentPage").hide();
			}else{
				for(var i =0;i<l;i++){
					cArr.push('<li class="clearfix"><div class="h_info"><h4>');
					cArr.push('<span class="ico"></span>');
					cArr.push('<span>'+list[i]["discussTime"]+'</span></h4>');
					cArr.push('<p>'+list[i]["content"]+'</p>');
					//cArr.push('<a class="delete">删除</a></div></li>');
				}
				cStr = cArr.join("");
				$("#commentPage").show();
				pagePause = 0;
				//分页设置
				if(page < 2){
					$("#commentPage").jPages({
						containerID : "comment_data",
						clickStop   : true,
						perPage	: 6,
						allSumPage : sumPage,
						callback: ajaxPageData2
					});
				}
			}
			$("#comment").attr("hasc", "1");
			$("#comment_data>ul").html(cStr).removeClass("jp-hidden");
			$("#comment_data").show();
			//$("#comment_data>ul").removeClass("jp-hidden");
		},
		error: function(request){
			console.log("获取我的评论异常");
		}
	});
}
//分页回调函数-我的评论
function ajaxPageData2(obj){
	if(pagePause == 0){
		return false;
	}
	getCommentList(obj["current"]);
}
