if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-login.html";
}
var pageNum = 1; //分页
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	changeTab();
	$("#loanPage").click(function(){
		getMySupportList(pageNum); //获取我发起的项目
	});
});
function changeTab(){
	var $typeTab = $("#loanType li"),code="";
	$.each($typeTab,function(k,v){
		$(v).click(function(){
			$typeTab.removeClass("cur");
			$(v).addClass("cur");
			$("#loanType").hide();
			$("#loanTypeA").attr("type","0");
			code = $(this).attr("code");
			getShowLoanProcess(code); //获取相关的项目状态
		});
	});
	
	$("#loanTypeA").click(function(){
		if($(this).attr("type") == "0"){
			$(this).addClass("cur");
			$("#loanType").show();
			$(this).attr("type","1");
		}else{
			$(this).removeClass("cur");
			$("#loanType").hide();
			$(this).attr("type","0");
		}
		$("#loanProcess").hide();
		$("#loanProcessA").removeClass("cur").attr("type","0");
	});
	
	$("#loanProcessA").click(function(){
		if($(this).attr("type") == "0"){
			$(this).addClass("cur");
			$("#loanProcess").show();
			$(this).attr("type","1");
		}else{
			$(this).removeClass("cur");
			$("#loanProcess").hide();
			$(this).attr("type","0");
		}
		$("#loanTypeA").removeClass("cur").attr("type","0");
		$("#loanType").hide();
	});
	
	$typeTab.first().click();
}
//获取相关的项目状态
function getShowLoanProcess(code){
	var pStr = "";
	if(code == "public_service" || code == "entity"){
		pStr = "";
		pStr += '<li name="rgg" furl="/crowdfundUserCenter/getMySupportList.html">我的认购</li>';
	}else if(code == "stock" || code == "house" || code == ""){
		pStr = "";
		pStr += '<li name="order" furl="/crowdfundUserCenter/getMyInterviewList.html">我的约谈</li>';
		pStr += '<li name="yg" furl="/crowdfundUserCenter/getMyPreSupportList.html">我的预购</li>';
		pStr += '<li name="rg" furl="/crowdfundUserCenter/getMySupportList.html">我的认购</li>';
	}
	$("#loanProcess").html(pStr);
	var $processType = $("#loanProcess li");
	$.each($processType,function(k,v){
		$(v).click(function(){
			$processType.removeClass("cur");
			$(v).addClass("cur");
			$("#loanProcess").hide();
			$("#loanProcessA").attr("type","0");
			pageNum = 1;
			getMySupportList(pageNum); //获取我发起的项目
		});
	});
	$processType.first().click();
}
//获取我投资的项目
function getMySupportList(page){
	var type = "",furl="",fid = "",lArr = [],lStr = "",l=0,total=0,data;
	type = $("#loanType li.cur").attr("code");
	furl = $("#loanProcess li.cur").attr("furl");
	fid = $("#loanProcess li.cur").attr("name");
	$("#loanBody>div").hide();
	$("#"+type+"Body").show();
	$.ajax({
		url: path + furl,
		type: "post",
		dataType: "json",
		data: {
			"loanType": type,
			"page": page,
			"rows": 6
			},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"]["rows"].length,total = data["msg"]["total"],data = data["msg"]["rows"];
			if(l == 0){
				lStr = '<div style="line-height:80px; text-align:center; background:#fff; font-size:16px; color:red;">暂无数据</div>';
				$("#"+type+"Body").html(lStr);
				$("#loanPage").hide();
				return false;
			}
			if(fid == "rgg"){ //筹爱心，抽好货 我的认购
				for(var i =0;i<l;i++){
					lArr.npush('<ul>');
					lArr.npush('<li><span>项目名称：</span>'+data[i]["loanName"]+'</li>');
					lArr.npush('<li><span>认购金额：</span>￥'+data[i]["supportAmt"].toFixed(2)+'</li>');
					lArr.npush('<li><span>筹集目标：</span>￥'+data[i]["fundAmt"].toFixed(2)+'</li>');
					if(data[i]["payStateName"]){
						lArr.npush('<li><span>项目状态：</span>'+data[i]["payStateName"]+'</li>');
					}else{
						lArr.npush('<li><span>项目状态：</span>--</li>');
					}
					lArr.npush('<li><span>认购时间：</span>'+data[i]["supportTime"]+'</li>');
					if(data[i]["payState"]=="noPay"){
						lArr.npush('<li><span style="margin-left:29px;">操作：</span><a href="javascript:void(0);" deleteId='+data[i]["id"]+' onclick=deleteSupportEvent(event)>取消订单</a></li>');
					}else{
						lArr.npush('<li><span style="margin-left:29px;">操作：</span>--</li>');
					}
					lArr.npush('</ul>');
				}
			}else if(fid == "order"){ //我的约谈
				for(var i =0;i<l;i++){
					lArr.npush('<ul>');
					lArr.npush('<li><span>项目名称：</span>#0</li>',[data[i]["loanName"]]);
					if(data[i]["fundAmt"]){
						
						lArr.npush('<li><span>筹集目标：</span>￥#0</li>',[data[i]["fundAmt"].toFixed(2)]);
					}else{
						lArr.npush('<li><span>筹集目标：</span>--</li>');
					}
//					lArr.npush('<li><span>筹集目标：</span>￥#0</li>',[data[i]["fundAmt"].toFixed(2)]);
					lArr.npush('<li><span style="margin-left:15px;">发起人：</span>#0</li>',[data[i]["loanUser2"]]);
					if(data[i]["interviewTime"]){
						lArr.npush('<li><span style="margin-left:29px;">时间：</span>#0</li>',[data[i]["interviewTime"].substring(0,10)]);
					}else{
						lArr.npush('<li><span style="margin-left:29px;">时间：</span>--</li>');
					}
					lArr.npush('</ul>');
				}
			}else if(fid == "yg"){
				for(var i =0;i<l;i++){
					lArr.npush('<ul>');
					lArr.npush('<li><span>项目编号：</span>'+data[i]["loanNo"]+'</li>');
					lArr.npush('<li><span>项目名称：</span>'+data[i]["loanName"]+'</li>');
					if(data[i]["supportAmt"]){
						lArr.npush('<li><span>预购金额：</span>￥'+data[i]["supportAmt"].toFixed(2)+'</li>');
					}else{
						lArr.npush('<li><span>预购金额：</span>--</li>');
					}
					if(data[i]["fundAmt"]){
						lArr.npush('<li><span>融资金额：</span>￥'+data[i]["fundAmt"].toFixed(2)+'</li>');
					}else{
						lArr.npush('<li><span>融资金额：</span>--</li>');
					}
					lArr.npush('<li><span>预购时间：</span>'+data[i]["supportTime"]+'</li>');
					lArr.npush('</ul>');
				}
			}else if(fid == "rg"){
				for(var i =0;i<l;i++){
					lArr.npush('<ul>');
					lArr.npush('<li><span>项目名称：</span>'+data[i]["loanName"]+'</li>');
					lArr.npush('<li><span>认购金额：</span>￥'+data[i]["supportAmt"].toFixed(2)+'</li>');
					lArr.npush('<li><span>筹资目标：</span>￥'+data[i]["fundAmt"].toFixed(2)+'</li>');
					if(data[i]["payStateName"]){
						lArr.npush('<li><span>项目状态：</span>'+data[i]["payStateName"]+'</li>');
					}else{
						lArr.npush('<li><span>项目状态：</span>--</li>');
					}
					lArr.npush('<li><span style="margin-left:15px;">发起人：</span>'+data[i]["loanUser2"]+'</li>');
					if(data[i]["leadInvestor"]){
						lArr.npush('<li><span style="margin-left:15px;">领投人：</span>'+data[i]["leadInvestor"]+'</li>');
					}else{
						lArr.npush('<li><span style="margin-left:15px;">领投人：</span>--</li>');
					}
					if(data[i]["payState"]=="noPay"){
						lArr.npush('<li><span style="margin-left:29px;">操作：</span><a href="javascript:void(0);" deleteId="'+data[i]["id"]+'" onclick="deleteSupportEvent(event)">取消订单</a></li>');
					}else{
						lArr.npush('<li><span style="margin-left:29px;">操作：</span>--</li>');
					}
					lArr.npush('</ul>');
				}
			}
			lStr = lArr.join('');
			if(page == 1){
				$("#"+type+"Body").html(lStr);
			}else{
				$("#"+type+"Body").append(lStr);
			}
			if($("#"+type+"Body ul").length == total){
				$("#loanPage").hide();
			}else{
				pageNum++;
				$("#loanPage").show();
			}
		},
		error: function(request){
			console.log("获取我的投资信息列表异常");
		}
	});
}
//取消订单
function deleteSupportEvent(event){
	var obj = event.srcElement || event.target;
	var deleteId = $(obj).attr("deleteId");
	AlertDialog.confirm(deleteSupport, null, "您确定要取消订单吗？", "确定", "取消", deleteId);
}
function deleteSupport(id){
	$.ajax({
		url: path + "/crowdfunding/deleteSupport.html",
		type: "post",
		dataType: "json",
		data: {
			"id": id
		},
		success: function(data){
			if(data["success"]){
				AlertDialog.tip("取消订单成功");
				$("a[deleteId="+id+"]").parent().parent().remove();
			}
		},
		error: function(request){
			console.log("取消订单异常");
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