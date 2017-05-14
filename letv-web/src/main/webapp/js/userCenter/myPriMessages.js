if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	getUserInfoDetail();
	getPrivateLetter(1);
});
//获取个人信息
function getUserInfoDetail(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			if(data["photo"]){
				$("#centerUserPhoto").attr("src", cv["fileAddress"] + data["photo"]); //头像
			}else{
				$("#centerUserPhoto").attr("src", path + "/images/defaultPhoto.png"); //头像
			}
			$("#userRealName").text(data["realName"]); //真实姓名
			if(data["loanProvinceName"]){
				$("#address").html(data["loanProvinceName"]+"&nbsp;&nbsp;"+data["loanCityName"]); //地址
			}else{
				$("#address").text("--"); //地址
			}
			$("#createTime").text(data["createTime"].substring(0,10));
			if(data["selfDetail"]){
				$("#selfDetail2").html('<span class="C999">简介：</span>'+data["selfDetail"]).attr("title", data["selfDetail"]);
			}else{
				$("#selfDetail2").html('<span class="C999">简介：</span>他很忙，忙的什么都没来及留下。');
			}
		},error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//获取我的私信
function getPrivateLetter(page){
	var mArr = [], mStr = '', l, sumPage;
	$.ajax({
		url: path + "/letter/getPrivateLetterList.html",
		type: "post",
		dataType: "json",
		data: {
			"rows": 10,
			"page": page
		},
		success: function(data){
			if(!data["success"]){
				lStr = '<li style="padding:15px;color:red;">暂无数据</li>';
				$("#message_data").html(lStr);
				return false;
			}
			sumPage = (data["msg"]["total"]%10 == 0) ? data["msg"]["total"]/10 : Math.floor(data["msg"]["total"]/10) + 1;
			l = data["msg"]["rows"].length, list = data["msg"]["rows"];
			if(l == 0){
				lStr = '<li style="padding:15px;color:red;">暂无数据</li>';
				$("#message_data").html(lStr);
				return false;
			}
			for(var i =0;i<l;i++){
				mArr.push('<li class="clearfix"><div class="h_info"><h4>');
				mArr.push('<span class="ico"></span>');
				mArr.push('<a>'+list[i]["sendUser"]+'</a><span>'+list[i]["sendDate"]+'</span></h4>');
				if(list[i]["loanName"]){
					mArr.push('<span>相关项目：'+list[i]["loanName"]+'</span>');
				}
				mArr.push('</h4>');
				mArr.push('<p>'+list[i]["sendContent"]+'</p>');
				//mArr.push('<a class="delete">删除</a>');
				mArr.push('</div></li>');
			}
			mStr = mArr.join("");
			$("#message_data>ul").html(mStr);
			$("#commentPage").show();
			pagePause = 0;
			//分页设置
			if(page < 2){
				$("#commentPage").jPages({
					containerID : "message_data",
					clickStop   : true,
					perPage	: 10,
					allSumPage : sumPage,
					callback: ajaxPageData
				});
			}
		},
		error: function(request){
			console.log("获取我的私信异常");
		}
	});
}
function ajaxPageData(obj){
	if(pagePause == 0){
		return false;
	}
	getPrivateLetter(obj["current"]);
}