if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-login.html";
}
var pageNum = 1; //分页
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
	changeTab();
	$("#loanPage").unbind("click").click(function(){ //查看更多
		var code = $("#loanType li.cur").attr("code");
		if(code == "canListing"){ //可挂牌
			getCrowdfundingInvestTransferList(pageNum);
		}else if(code == "Auditing" || code == "Audited"){ //审核中，已审核
			getCrowdfundingTransferAuditList(code,pageNum);
		}else if(code == "Listing" || code == "lisTingEnd"){ //挂牌中，挂牌完成
			getCrowdfundingTransferInfoList(code,pageNum);
		}
	});
});
function changeTab(){
	var $tab = $("#loanType li");
	$.each($tab,function(k,v){
		$(v).click(function(){
			$tab.removeClass("cur");
			$(v).addClass("cur");
			$("#loanBody>div").hide();
			$("#"+$(v).attr("code")+"Body").show();
			changeLoanType($(v).attr("code")); //选择不同的分类
		});
	});
	$tab.first().click();
}
//选择不同的分类
function changeLoanType(code){
	pageNum = 1;
	if(code == "canListing"){ //可挂牌
		getCrowdfundingInvestTransferList(pageNum);
	}else if(code == "Auditing" || code == "Audited"){ //审核中，已审核
		getCrowdfundingTransferAuditList(code,pageNum);
	}else if(code == "Listing" || code == "lisTingEnd"){ //挂牌中，挂牌完成
		getCrowdfundingTransferInfoList(code,pageNum);
	}
}
//获取可挂牌的项目
function getCrowdfundingInvestTransferList(page){
	var eStr = "", eArr = [], total = 0,l=0,data;
	$.ajax({
		url: path+"/crowdfundingInvestTransfer/getCrowdfundingInvestTransferList.html",
		type: "post",
		dataType: "json",
		data:{
			"page":page,
			"rows":6
		},
		success: function(data){
			if(!data["success"]){
				console.log("获取可挂牌列表失败");
				return false;
			}
			total =data["msg"]["total"],l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				eStr = '<div style="line-height:80px; text-align:center; background:#fff; font-size:16px; color:red;">暂无数据</div>';
				$("#canListingBody").html(eStr);
				$("#loanPage").hide();
				return false;
			}
			for(var i =0;i<l;i++){
				eArr.npush('<ul>');
				eArr.npush('<li><span>项目名称：</span>'+data[i]["loanName"]+'</li>');
				if(data[i]["miniInvestAmt"]){
					eArr.npush('<li><span>认购单价：</span>￥'+data[i]["miniInvestAmt"].toFixed(2)+'</li>');
				}else{
					eArr.npush('<li><span>认购单价：</span>--</li>');
				}
				eArr.npush('<li><span>可挂牌份数：</span>'+data[i]["buyNum"]+'</li>');
				eArr.npush('<li><span>认购时间：</span>'+data[i]["supportTime"]+'</li>');
				eArr.npush('<li><span style="margin-left:29px;">操作：</span><a orderid='+data[i]["orderId"]+' totalnum="'+data[i]["buyNum"]+'" href="javascript:void(0);" onclick="alerApply(event)">申请挂牌</a></li>');
				eArr.npush('</ul>');
			}
			eStr = eArr.join('');
			if(page == 1){
				$("#canListingBody").html(eStr);
			}else{
				$("#canListingBody").append(eStr);
			}
			if($("#canListingBody ul").length == total){
				$("#loanPage").hide();
			}else{
				pageNum++;
				$("#loanPage").show();
			}
		},
		error:function(request){
			console.log('获取可挂牌项目异常');
		}
	});
}
//申请挂牌
function alerApply(event){
	$("#bgpop").show();
	var il = (cv["winW"] - $("#alerApplyDiv").width())/2,it = (cv["winH"]-$("#alerApplyDiv").height())/2;
	$("#alerApplyDiv").css({
		"left":il+"px",
		"top":"60px"
	}).show();
	$("#cancel").unbind("click").click(function(){
		AlertDialog.hide();
		$("#alerApplyDiv input[type=text]").val('');
		$("#bgpop").hide();
		$("#alerApplyDiv").hide();
	});
	var obj = event.srcElement || event.target;
	var orderId = $(obj).attr("orderid");
	var totalNum = Number($(obj).attr("totalnum"));
	//点击确定按钮
	$("#sendListing").unbind("click").click(function(){
		if(Number($("#totalParts").val()) > 0 && Number($("#totalParts").val())){
			if(Number($("#totalParts").val()) <= totalNum){
				if(Number($("#perAmt").val()) > 0){
					if(Number($("#transferDay").val()) > 0 && Number($("#transferDay").val())){
						wapDialog.hide();
						$.ajax({
							url: path+"/crowdfundingInvestTransfer/saveCrowdFundTransfer.html",
							type: "post",
							dataType: "json",
							data: {
								"orderNo":orderId,
								"transferParts":$("#totalParts").val(),
								"partMoney":$("#perAmt").val(),
								"transferDay":$("#transferDay").val()
							},
							success: function(data){
								if(!data["success"]){
									$("#alerApplyDiv input[type=text]").val('');
									AlertDialog.mtip(data["msg"]);
									$("#alerApplyDiv").hide();
									return false;
								}
								$("#alerApplyDiv input[type=text]").val('');
								AlertDialog.mtip('申请成功');
								window.location.href = window.location.href;
							},
							error:function(request){
								console.log("申请挂牌失败");
							}
						});
					}else{
						wapDialog.show("请输入大于0的数字", "transferDay", 0, 60);
					}
				}else{
					wapDialog.show("请输入大于0的数字", "perAmt", 0, 60);
				}
			}else{
				wapDialog.show("申请份数不能大于可申请最大份数", "totalParts", 0, 150);
			}
		}else{
			wapDialog.show("请输入大于0的数字", "totalParts", 0, 60);
		}
	});
}
//审核中，已审核列表
function getCrowdfundingTransferAuditList(code,page){
	var db = {},total = 0,l = 0,eStr = "",eArr = [],data;
	if(code == "Auditing"){ //审核中
		db={
			"page":page,
			"rows":6,
			"auditState":"auditing"
		}
	}else if(code == "Audited"){ //已审核
		db={
			"page":page,
			"rows":6,
			"auditStateAll":"1",
			"isAgreeNull":"1"
		}
	}
	$.ajax({
		url: path + "/crowdfundingInvestTransfer/getCrowdfundingTransferAuditList.html",
		type: "post",
		dataType: "json",
		data:db,
		success: function(data){
			if(!data["success"]){
				return false;
			}
			total =data["msg"]["total"],l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				eStr = '<div style="line-height:80px; text-align:center; background:#fff; font-size:16px; color:red;">暂无数据</div>';
				$("#"+code+"Body").html(eStr);
				$("#loanPage").hide();
				return false;
			}
			if(code == "Audited"){ //已审核
				for(var i =0;i<l;i++){
					eArr.npush('<ul>');
					eArr.npush('<li><span>项目名称：</span>'+data[i]["loanName"]+'</li>');
					eArr.npush('<li><span>挂牌单价：</span>￥'+data[i]["partsMoney"].toFixed(2)+'</li>');
					eArr.npush('<li><span>挂牌份数：</span>'+data[i]["transferParts"]+'</li>');
					eArr.npush('<li><span>挂牌天数：</span>'+data[i]["transferDay"]+'</li>');
					eArr.npush('<li><span>审核时间：</span>'+data[i]["auditTime"]+'</li>');
					eArr.npush('<li><span>审核状态：</span>'+data[i]["auditStateName"]+'</li>');
					eArr.npush('<li><span>审核原因：</span>'+data[i]["auditOpinion"]+'</li>');
					if(data[i]['auditState'] == 'audit_refuse'){
						eArr.npush('<li><span>审核操作：</span>--</li>');
					}else{
						eArr.npush('<li><span>审核操作：</span><a href="javascript:void(0);" tid="'+data[i]["id"]+'" onclick="startTransferBtn(event)" style="margin-right:20px;">确认</a><a href="javascript:void(0);" tid="'+data[i]["id"]+'" onclick="cancelTransferBtn(event)">取消</a></li>');
					}
					eArr.npush('</ul>');
				}
			}else if(code == "Auditing"){ //审核中
				for(var i =0;i<l;i++){
					eArr.npush('<ul>');
					eArr.npush('<li><span>项目名称：</span>'+data[i]["loanName"]+'</li>');
					eArr.npush('<li><span>挂牌单价：</span>￥'+data[i]["partsMoney"].toFixed(2)+'</li>');
					eArr.npush('<li><span>挂牌份数：</span>'+data[i]["transferParts"]+'</li>');
					eArr.npush('<li><span>挂牌天数：</span>'+data[i]["transferDay"]+'</li>');
					eArr.npush('<li><span>申请时间：</span>'+data[i]["apply_time"]+'</li>');
					eArr.npush('</ul>');
				}
			}
			eStr = eArr.join('');
			if(page == 1){
				$("#"+code+"Body").html(eStr);
			}else{
				$("#"+code+"Body").append(eStr);
			}
			if($("#"+code+"Body ul").length == total){
				$("#loanPage").hide();
			}else{
				pageNum++;
				$("#loanPage").show();
			}
		},
		error: function(request){
			console.log("获取挂牌信息异常");
		}
	});
}
//已审核的确认操作
function startTransferBtn(event){
	var obj = event.srcElement || event.target;
	var tId = $(obj).attr("tid");
	AlertDialog.confirm(startTransfer,false,'确定挂牌么？','确定','取消',tId);
}
function startTransfer(tId){
	$.ajax({
		url: path+"/crowdfundingInvestTransfer/updateIsAgree.html",
		type: "post",
		dataType: "json",
		data: {
			'isAgree':0,
			'id':tId
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			pageNum = 1;
			AlertDialog.mtip('挂牌成功!', getCrowdfundingTransferAuditList($("#loanType li.cur").attr("code"),pageNum));
		},
		error:function(){
			console.log("调用开始挂牌接口失败")
		}
	});
}
//已审核的取消操作
function cancelTransferBtn(event){
	var obj = event.srcElement || event.target;
	var tId = $(obj).attr("tid");
	AlertDialog.confirm(cancelTransfer,false,'确定取消挂牌么？','确定','取消',tId);
}
function cancelTransfer(tId){
	$.ajax({
		url: path+"/crowdfundingInvestTransfer/updateIsAgree.html",
		type: "post",
		dataType: "json",
		data: {
			'isAgree':1,
			'id':tId
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			pageNum = 1;
			AlertDialog.mtip('取消挂牌成功!', getCrowdfundingTransferAuditList($("#loanType li.cur").attr("code"),pageNum));
		},
		error:function(){
			console.log("调用取消挂牌接口失败")
		}
	});
}
//挂牌中、挂牌完成
function getCrowdfundingTransferInfoList(code,page){
	var db = {},eStr = "",eArr = [], total = 0, l = 0,data;
	if(code == "Listing"){ //挂牌中
		db = {
			"page":page,
			"rows":6,
			"auditState":"audit_pass",
			"status":"transfering",
			"isAgree":"0"
		}
	}else if(code == "lisTingEnd"){ //挂牌完成
		db = {
			"page":page,
			"rows":6,
			"auditState":"audit_pass",
			"transferStateAll":"1"
		}
	}
	$.ajax({
		url: path+"/crowdfundingInvestTransfer/getCrowdfundingTransferInfoList.html",
		type: "post",
		dataType: "json",
		data: db,
		success: function(data){
			if(!data["success"]){
				return false;
			}
			total =data["msg"]["total"],l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				eStr = '<div style="line-height:80px; text-align:center; background:#fff; font-size:16px; color:red;">暂无数据</div>';
				$("#"+code+"Body").html(eStr);
				$("#loanPage").hide();
				return false;
			}
			if(code == "Listing"){ //挂牌中
				for(var i =0;i<l;i++){
					eArr.npush('<ul>');
					eArr.npush('<li><span>项目名称：</span>'+data[i]["loanName"]+'</li>');
					eArr.npush('<li><span>挂牌单价：</span>￥'+data[i]["partsMoney"].toFixed(2)+'</li>');
					eArr.npush('<li><span>挂牌份数：</span>'+data[i]["transferParts"]+'</li>');
					eArr.npush('<li><span>挂牌完成份数：</span>'+data[i]["transferCompleteParts"]+'</li>');
					eArr.npush('<li><span>挂牌天数：</span>'+data[i]["transferDay"]+'</li>');
					eArr.npush('<li><span>挂牌开始时间：</span>'+data[i]["startTime"]+'</li>');
					eArr.npush('</ul>');
				}
			}else if(code == "lisTingEnd"){ //挂牌完成
				for(var i =0;i<l;i++){
					eArr.npush('<ul>');
					eArr.npush('<li><span>项目名称：</span>'+data[i]["loanName"]+'</li>');
					eArr.npush('<li><span>挂牌单价：</span>￥'+data[i]["partsMoney"].toFixed(2)+'</li>');
					eArr.npush('<li><span>挂牌份数：</span>'+data[i]["transferParts"]+'</li>');
					eArr.npush('<li><span>挂牌完成份数：</span>'+data[i]["transferCompleteParts"]+'</li>');
					eArr.npush('<li><span>挂牌到账金额：</span>￥'+data[i]["transferCompleteMoney"]+'</li>');
					eArr.npush('<li><span>挂牌服务费：</span>'+data[i]["transferCompleteFee"]+'</li>');
					eArr.npush('</ul>');
				}
			}
			eStr = eArr.join('');
			if(page == 1){
				$("#"+code+"Body").html(eStr);
			}else{
				$("#"+code+"Body").append(eStr);
			}
			if($("#"+code+"Body ul").length == total){
				$("#loanPage").hide();
			}else{
				pageNum++;
				$("#loanPage").show();
			}
		},
		error:function(){
			console.log("调用挂牌接口失败");
		}
	});
}