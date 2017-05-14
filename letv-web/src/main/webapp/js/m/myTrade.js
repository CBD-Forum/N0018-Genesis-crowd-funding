if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
var pageNum = 1;
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	getUserAccount();
	$("#moreList").click(function(){
		getTradeDetail(pageNum);
	});
	changeTab();
});
function changeTab(){
	var $tab = $("#loanType li"),code = "";
	$.each($tab,function(k,v){
		$(v).click(function(){
			$tab.removeClass("cur");
			$(v).addClass("cur");
			pageNum = 1;
			code = $(v).attr("code");
			$("#detailBody>div").hide();
			if(code == "trade_type_person"){ //收支明细
				getTradeDetail(pageNum);
			}else if(code == "withdraw_state"){ //提现记录
				getWithDrawList(pageNum);
			}
		});
	});
	$tab.first().click();
}

//获取交易详情信息
function getTradeDetail(page){
	var dArr = [],dStr = "",l=0,total = 0,data;
	$.ajax({
		url: path + "/bill/getUserBill.html",
		type: "post",
		data:{
			"page":page,
			"rows":5,
			"tradeTypeFlag":"tradeTypeFlag"
		},
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"]["rows"].length,total = data["msg"]["total"],data = data["msg"]["rows"];
			if(l == 0){
				dStr = '<div style="line-height:80px; text-align:center; background:#fff; font-size:16px; color:red;">暂无数据</div>';
				$("#trade_type_personBody").html(dStr).show();
				$("#moreList").hide();
				return false;
			}
			for(var i=0;i<l;i++){
				dArr.npush('<ul>');
				dArr.npush('<li><span>交易时间：</span>'+data[i]["tradeTime"]+'</li>');
				dArr.npush('<li><span>交易类型：</span>'+data[i]["tradeTypeName"]+'</li>');
				dArr.npush('<li><span style="margin-left:29px;">单号：</span>'+data[i]["tradeId"]+'</li>');
				dArr.npush('<li><span style="margin-left:29px;">资金：</span>'+data[i]["displayAmt"]+'</li>');
				dArr.npush('<li><span>交易对方：</span>#0</li>',[data[i]["oppositeSide"]]);
				dArr.npush('<li><span style="margin-left:29px;">摘要：</span>#0</li>',[data[i]["detail"]]);
				dArr.npush('</ul>');
			}
			dStr = dArr.join('');
			if(page == 1){
				$("#trade_type_personBody").html(dStr).show();
			}else{
				$("#trade_type_personBody").append(dStr).show();
			}
			
			if($("#trade_type_personBody ul").length == total){
				$("#moreList").hide();
			}else{
				pageNum++;
				$("#moreList").show();
			}
		},
		error: function(request){
			console.log("获取个人收支明细类型异常");
		}
	});
}
//获取提现记录
function getWithDrawList(page){
	var dArr = [],dStr = "",l=0,total = 0,data;
	$.ajax({
		url: path + "/withdraw/getWithDrawList.html",
		type: "post",
		data:{
			"page":page,
			"rows":5
		},
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"]["rows"].length,total = data["msg"]["total"],data = data["msg"]["rows"];
			if(l == 0){
				dStr = '<div style="line-height:80px; text-align:center; background:#fff; font-size:16px; color:red;">暂无数据</div>';
				$("#withdraw_stateBody").html(dStr).show();
				$("#moreList").hide();
				return false;
			}
			for(var i=0;i<l;i++){
				dArr.npush('<ul>');
				dArr.npush('<li><span style="margin-left:29px;">时间：</span>'+data[i]["applyTime"]+'</li>');
				dArr.npush('<li><span style="margin-left:29px;">编号：</span>'+data[i]["orderId"]+'</li>');
				dArr.npush('<li><span>银行卡号：</span>#0</li>',[data[i]["bankCardId"]]);
				dArr.npush('<li><span>提现金额：</span>'+data[i]["amt"].toFixed(2)+'</li>');
				var cost = data[i]["amt"] - data[i]["actualMoney"];
				dArr.npush('<li><span style="margin-left:29px;">费用：</span>'+cost+'</li>');
				dArr.npush('<li><span style="margin-left:29px;">状态：</span>#0</li>',[data[i]["stateName"]]);
				dArr.npush('</ul>');
			}
			dStr = dArr.join('');
			if(page == 1){
				$("#withdraw_stateBody").html(dStr).show();
			}else{
				$("#withdraw_stateBody").append(dStr).show();
			}
			
			if($("#withdraw_stateBody ul").length == total){
				$("#moreList").hide();
			}else{
				pageNum++;
				$("#moreList").show();
			}
		},
		error: function(request){
			console.log("获取提现记录异常");
		}
	});
}
//获取个人账户
function getUserAccount(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getAccountInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			
			$("#userBalance").text(data["balance"].toFixed(2)).attr("num", data["balance"]) ; //可用余额
		},error: function(request){
			console.log("获取个人信息异常");
		}
	});
}