if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
var pageNum = 1; //分页
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	changeTab();
	$("#loanPage").click(function(){
		getMyAttentionList(pageNum);
	});
});
function changeTab(){
	var $tab = $("#loanType li");
	$.each($tab,function(k,v){
		$(v).click(function(){
			$tab.removeClass("cur");
			$(v).addClass("cur");
			pageNum = 1;
			getMyAttentionList(pageNum); //获取我关注的项目
		});
	});
	$tab.first().click();
	$("#loanPage").click(function(){
		getMyAttentionList(pageNum);
	});
}
//获取我关注的项目
function getMyAttentionList(page){
	var loanType = "",lArr = [],lStr = "",total = 0,l = 0,data;
	loanType = $("#loanType li.cur").attr("code");
	$("#loanBody>div").hide();
	$("#"+loanType+"Body").show();
	$.ajax({
		url: path + "/crowdfundUserCenter/getMyAttentionList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanType": loanType,
			"page": page,
			"rows": 6
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l =  data["msg"]["rows"].length,total = data["msg"]["total"],data = data["msg"]["rows"];
			if(l == 0){
				lStr = '<div style="line-height:80px; text-align:center; background:#fff; font-size:16px; color:red;">暂无数据</div>';
				$("#"+loanType+"Body").html(lStr);
				$("#loanPage").hide();
				return false;
			}
			for(var i =0;i<l;i++){
				lArr.npush('<ul>');
				lArr.npush('<li><span>项目名称：</span>'+data[i]["loanName"]+'</li>');
				lArr.npush('<li><span>筹集金额：</span>￥'+data[i]["fundAmt"].toFixed(2)+'</li>');
				lArr.npush('<li><span>项目状态：</span>'+data[i]["loanStateName"]+'</li>');
				lArr.npush('<li><span style="margin-left:15px;">发起人：</span>'+data[i]["loanUser2"]+'</li>');
				lArr.npush('<li><span>关注时间：</span>'+data[i]["attentionTime"]+'</li>');
				lArr.npush('<li><span style="margin-left:29px;">操作：</span><a href="javascript:void(0);" deleteAttentId="'+data[i]["id"]+'" onclick="deleteAttentEvent(event)">取消关注</a></li>');
				lArr.npush('</ul>');
			}
			lStr = lArr.join('');
			if(page == 1){
				$("#"+loanType+"Body").html(lStr);
			}else{
				$("#"+loanType+"Body").append(lStr);
			}
			if($("#"+loanType+"Body ul").length == total){
				$("#loanPage").hide();
			}else{
				pageNum++;
				$("#loanPage").show();
			}
		},
		error: function(request){
			console.log("获取我关注的项目信息异常");
		}
	});
}
//取消关注
function deleteAttentEvent(event){
	var obj = event.srcElement || event.target;
	var deleteAttentId = $(obj).attr("deleteAttentId");
	AlertDialog.confirm(deleteAttent, null, "您确定要取消关注吗？", "确定", "取消", deleteAttentId);
}
function deleteAttent(id){
	$.ajax({
		url: path + "/crowdfunding/cancelAttention.html",
		type: "post",
		dataType: "json",
		data: {
			"id": id
		},
		success: function(data){
			if(data["success"]){
				AlertDialog.tip("取消关注成功");
				$("a[deleteAttentId="+id+"]").parent().parent().remove();
			}
		},
		error: function(request){
			console.log("取消关注异常");
		}
	});
}