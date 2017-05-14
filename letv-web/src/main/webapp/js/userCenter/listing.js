if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
var pageSize = 5;
var API = {
		//获取可挂牌列表接口
		"TransferList":path+"/crowdfundingInvestTransfer/getCrowdfundingInvestTransferList.html",
		//保存挂牌项目接口
		"saveTransfer":path+"/crowdfundingInvestTransfer/saveCrowdFundTransfer.html",
		//审核挂牌接口
		"AuditList":path+"/crowdfundingInvestTransfer/getCrowdfundingTransferAuditList.html",
		//挂牌状态接口
		"InfoList":path+"/crowdfundingInvestTransfer/getCrowdfundingTransferInfoList.html",
		//是否同意挂牌
		'IsAgree':path+"/crowdfundingInvestTransfer/updateIsAgree.html"
}
//标签对应的显示列表的id
var tab = {
		"canListing":"canListingArray",
		"Auditing":"AuditingArray",
		"Audited":"AuditedArray",
		"Listing":"ListingArray",
		"lisTingEnd":"lisTingEndArray"
}
$(function(){
	getUserInfo();
	tabChange();
});


var  listingArray = {
		array:[],
		findListingByLoanNo:function(loanNo){
			for(var i = 0; i<this.array.length;i++){
				if(this.array[i]['loanNo']==loanNo){
					return this.array[i];
				}
			}
			return false;
		},
		findListingByOrderId:function(orderid){
			for(var i = 0; i<this.array.length;i++){
				if(this.array[i]['orderId']==orderid){
					return this.array[i];
				}
			}
			return false;
		}
}

//获取个人信息
function getUserInfo(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			judgeLtgtRZ(data);//检测投资人认证与领投人认证
			if(data["photo"]){
				$("#centerUserPhoto").attr("src", cv["fileAddress"] + data["photo"]); //头像
			}else{
				$("#centerUserPhoto").attr("src", path + "/images/defaultPhoto.png"); //头像
			}
			$("#userRealName").text(data["realName"]); //真实姓名
			if(data["emailState"] == "已绑定" || data["emailState"] == "已认证"){ //邮箱认证通过
				$("#emailIcon").attr("src", path + "/images/v3_3.png").css("cursor", "default");
			}else{
				$("#emailIcon").attr("src", path + "/images/v3.png").click(function(){
					window.location.href = path + "/common/emailRZ.html";
				});
			}
			
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
			$("#totalSupportAmt").text(data["totalSupportAmt"].toFixed(2)); //累计投资项目总额
			$("#investLoanNum").text(data["investLoanNum"]); //投资项目总数
			if(data["followInvestorNum"] && data["followInvestorNum"]>0){ //显示当前用户的跟投人数
				$("#followInvestorNum").text(data["followInvestorNum"]+"人");
				$("#centerGTNum").removeClass("none");
			}
			if(data["concernCity"]){
				$("#centerTarget").text(data["concernCity"] + "，" + data["concernIndustry"]); //我的标签
			}else{
				$("#centerTarget").text("--"); //我的标签
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}

//判断领投跟投认证，改变显示结果
function judgeLtgtRZ(data){
	if(data["userLevel"] == "common"){
		if(data["investAuthState"] == "auditing"){ //跟投人审核中
			$("#rzgtrIcon").click(function(){
				AlertDialog.tip("跟投人认证正在审核中");
			});
			$("#rzltrIcon").click(function(){
				AlertDialog.tip("跟投人认证正在审核中");
			});
			$("#centerRZBtn").css("visibility", "visible").click(function(){
				AlertDialog.tip("跟投人认证正在审核中");
			});
		}else if(data["leadAuthState"] == "auditing"){ //领投人审核中
			$("#rzgtrIcon").click(function(){
				AlertDialog.tip("领投人认证正在审核中");
			});
			$("#rzltrIcon").click(function(){
				AlertDialog.tip("领投人认证正在审核中");
			});
			$("#centerRZBtn").css("visibility", "visible").click(function(){
				AlertDialog.tip("领投人认证正在审核中");
			});
		}else{
			$("#rzgtrIcon").click(function(){
				window.location.href = path + "/common/centerRZ.html?type=gtr";
			});
			$("#rzltrIcon").click(function(){
				window.location.href = path + "/common/centerRZ.html?type=ltr";
			});
			$("#centerRZBtn").css("visibility", "visible").click(function(){
				window.location.href = path + "/common/centerRZ.html?type=gtr";
			});
		}
	}else if(data["userLevel"] == "authed"){ //跟投人认证已通过
		$("#rzgtrIcon").unbind("click").attr("src", path + "/images/v2.png").css("cursor", "default");
		$("#rzltrIcon").css("cursor", "pointer");
		if(data["leadAuthState"] == "auditing"){
			$("#rzltrIcon").click(function(){
				AlertDialog.tip("领投人认证正在审核中");
			});
			$("#centerRZBtn").css("visibility", "visible").click(function(){
				AlertDialog.tip("领投人认证正在审核中");
			});
		}else{
			$("#rzltrIcon").click(function(){
				window.location.href = path + "/common/centerRZ.html?type=ltr";
			});
			$("#centerRZBtn").css("visibility", "visible").click(function(){
				window.location.href = path + "/common/centerRZ.html?type=ltr";
			});
		}
	}else if(data["userLevel"] == "lead"){ //跟投人和领投人都已经通过
		$("#rzgtrIcon").attr("src", path + "/images/v2.png").css("cursor", "default");
		$("#rzltrIcon").attr("src", path + "/images/v1.png").css("cursor", "default");
	}
}

//tab标签切换
function tabChange1(){
	$("#centerLoanType").find("a").click(function(){
		$("#centerLoanType").find("a").removeClass("cur");
		$(this).addClass("cur");
		$("#enterListBody >div").html(" ");
		$("#enterListPage").hide();
		var code = $(this).attr("code");
		//传入code 以及显示的第几页
		tabBindList(code,1)
	});
	$("#centerLoanType").find("a:first").click();
}

//tab标签切换
function tabChange(){
	$(".transferTab").find("a").click(function(){
		$(".transferTab").find("a").removeClass("current");
		$(this).addClass("current");
		$("#enterListBody >div").html(" ");
		$("#enterListPage").hide();
		var code = $(this).attr("code");
		//传入code 以及显示的第几页
		tabBindList(code,1)
	});
	$(".transferTab").find("a:first").click();
}

//标签页与列表关联
function tabBindList(code,page){
	changeAPIByCode(code,page);
	showStateListing(code); 
}

//tab切换显示时显示的列表
function showStateListing(tabName){
	var list = $("#centerLoanTitleDiv >dl");
	for(var i = 0;i<list.length;i++){
		list.eq(i).hide()
	}
	$("#"+tab[tabName]).show()
}

//获取当前标签名字
function getCurTba(){
	return $("#centerLoanType dd.cur").attr("code");
}

//获取挂牌列表
function getCrowdfundingInvestTransferList(reqUrl,params,callback){
	$.ajax({
		url: reqUrl,
		type: "post",
		dataType: "json",
		data:params,
		success: function(data){
			if(!data["success"]){
				console.log("获取可挂牌列表失败");
				return false;
			}
			sumPage = (data["msg"]["total"]%pageSize == 0) ? data["msg"]["total"]/pageSize : Math.floor(data["msg"]["total"]/pageSize) + 1;
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			listingArray.array = data;
			if(l == 0){
				eStr = '<dl style="color:red;margin-top:20px;padding:20px 40px;">暂无数据</dl>'; 
				$("#enterListBody >div").html(eStr);
				$("#enterListPage").hide();
				return false;
			}
			
			//根据数据生成dom
			callback(data);
			$("#enterListPage").show();
			//分页设置
			pagePause = 0;
			if(params["page"] < 2){
				$("#enterListPage").jPages({
					containerID : "enterListBody",
					clickStop   : true,
					perPage	: pageSize,
					allSumPage : sumPage,
					callback: function(obj){
						if(pagePause == 0){
							return false;
						}
						params["page"] = obj["current"];
						getCrowdfundingInvestTransferList(reqUrl,params,callback);
					}
				});
			}
		},
		error:function(){
			
		}
	});
}

//创建挂牌完成列表
function cratelisTingEndList(data){
	var eArr = [], eStr = '';
	for(var i = 0; i<data.length; i++){
		eArr.push('<dl class="sx_list clearfix bb_Lis">');
		eArr.push('<dd style="width:130px;"><a target="_blank" title="'+data[i]["loanName"]+'" href="'+path+'/common/transferListDetail.html?loanNo='+data[i]['loanNo']+'&transferNo='+data[i]['transferNo']+'">'+data[i]["loanName"]+'</a></dd>');
		eArr.push('<dd style="width:100px;">￥'+data[i]["partsMoney"].toFixed(2)+'</dd>');
		eArr.push('<dd style="width:120px;">'+data[i]["transferParts"]+'</dd>');
		eArr.push('<dd style="width:120px;">'+data[i]["transferCompleteParts"]+'</dd>');
		eArr.push('<dd style="width:100px;">'+data[i]["transferCompleteMoney"]+'</dd>');
		eArr.push('<dd style="width:120px;">'+data[i]["transferCompleteFee"]+'</dd>');
		eArr.push('</dl>');
	}
	eStr = eArr.join("");
	$("#enterListBody>div").html(eStr);
}

//创建挂牌中列表
function crateListingList(data){
	var eArr = [], eStr = '';
	for(var i = 0; i<data.length; i++){
		eArr.push('<dl class="sx_list clearfix bb_Lis">');
		eArr.push('<dd style="width:130px;"><a target="_blank" title="'+data[i]["loanName"]+'" href="'+path+'/common/transferListDetail.html?loanNo='+data[i]['loanNo']+'&transferNo='+data[i]['transferNo']+'">'+data[i]["loanName"]+'</a></dd>');
		eArr.push('<dd style="width:100px;">￥'+data[i]["partsMoney"].toFixed(2)+'</dd>');
		eArr.push('<dd style="width:100px;">'+data[i]["transferParts"]+'</dd>');
		eArr.push('<dd style="width:100px;">'+data[i]["transferCompleteParts"]+'</dd>');
		eArr.push('<dd style="width:100px;">'+data[i]["transferDay"]+'</dd>');
		eArr.push('<dd style="width:120px;">'+data[i]["startTime"]+'</dd>');
		eArr.push('</dl>');
	}
	eStr = eArr.join("");
	$("#enterListBody>div").html(eStr);
}

//创建审核完成列表
function crateAuditedList(data){
	var eArr = [], eStr = '';
	for(var i = 0; i<data.length; i++){
		eArr.push('<dl class="sx_list clearfix bb_Lis">');
		eArr.push('<dd style="width:100px;"><a target="_blank" title="'+data[i]["loanName"]+'" href="'+path+'/common/loanDetail-stock.html?loanNo='+data[i]['loanNo']+'">'+data[i]["loanName"]+'</a></dd>');
		eArr.push('<dd style="width:80px;">￥'+data[i]["partsMoney"].toFixed(2)+'</dd>');
		eArr.push('<dd style="width:60px;">'+data[i]["transferParts"]+'</dd>');
		eArr.push('<dd style="width:60px;">'+data[i]["transferDay"]+'</dd>');
		eArr.push('<dd style="width:80px;">'+data[i]["auditTime"]+'</dd>');
		eArr.push('<dd style="width:100px;">'+data[i]["auditStateName"]+'</dd>');
		eArr.push('<dd style="width:100px; overflow:hidden;white-space:nowrap;" title='+data[i]["auditOpinion"]+'>'+data[i]["auditOpinion"]+'</dd>');
		eArr.push('<dd style="width:100px;" transferId="'+data[i]["id"]+'">');
		if(data[i]['auditState'] == 'audit_refuse'){
			eArr.push('--')
		}else{
			eArr.push('<a style="margin-right:15px;cursor:pointer" class="okTransfer">确认</a>')
			eArr.push('<a style="cursor:pointer" class="cancelTransfer">取消</a>')
		}

		eArr.push('</dd></dl>');
	}
	eStr = eArr.join("");
	$("#enterListBody>div").html(eStr);
	$(".okTransfer").on("click",function(){
		AlertDialog.confirm(startTransfer,false,'确定挂牌么？','确定','取消',$(this).parent().attr('transferId'))
	})
	$(".cancelTransfer").on("click",function(){
		AlertDialog.confirm(cancelTransfer,false,'确定取消挂牌么？','确定','取消',$(this).parent().attr('transferId'))
	})
}

//创建审核中列表 
function createAuditingList(data){
	var eArr = [], eStr = '';
	for(var i = 0; i<data.length; i++){
		eArr.push('<dl class="sx_list clearfix bb_Lis">');
		eArr.push('<dd style="width:130px;"><a target="_blank" title="'+data[i]["loanName"]+'" href="'+path+'/common/loanDetail-stock.html?loanNo='+data[i]['loanNo']+'">'+data[i]["loanName"]+'</a></dd>');
		eArr.push('<dd style="width:130px;">￥'+data[i]["partsMoney"].toFixed(2)+'</dd>');
		eArr.push('<dd style="width:130px;">'+data[i]["transferParts"]+'</dd>');
		eArr.push('<dd style="width:130px;">'+data[i]["transferDay"]+'</dd>');
		eArr.push('<dd style="width:180px;">'+data[i]["apply_time"]+'</dd>');
		eArr.push('</dl>');
	}
	eStr = eArr.join("");
	$("#enterListBody>div").html(eStr);
//	$("#enterBody").show();
}


//创建可挂牌列表
function crateCanListing(data){
	var eArr = [], eStr = '';
	for(var i = 0; i<data.length; i++){
		eArr.push('<dl class="sx_list clearfix bb_Lis">');
		eArr.push('<dd style="width:130px;"><a target="_blank" title="'+data[i]["loanName"]+'" href="'+path+'/common/loanDetail-stock.html?loanNo='+data[i]['loanNo']+'">'+data[i]["loanName"]+'</a></dd>');
		if(data[i]["miniInvestAmt"]){
			eArr.push('<dd style="width:130px;">￥'+data[i]["miniInvestAmt"].toFixed(2)+'</dd>');
		}else{
			eArr.push('<dd style="width:130px;">--</dd>');
		}
		eArr.push('<dd style="width:130px;">'+data[i]["buyNum"]+'</dd>');
		eArr.push('<dd style="width:130px;">'+data[i]["supportTime"]+'</dd>');
		eArr.push('<dd style="width:180px;">');
		eArr.push('<a orderId='+data[i]["orderId"]+'>申请挂牌</a>')
		eArr.push('</dd></dl>');
	}
	eStr = eArr.join("");
	$("#enterListBody>div").html(eStr);
	$("#enterListBody>div a[orderId]").on("click",function(){
		alerApply($(this).attr("orderId"));
	})
//	$("#enterBody").show();
}

//根据code 拼装参数 获取列表
function changeAPIByCode(code,page){
	var params;
	var callback;
	switch(code){
		//可挂牌列表
		case "canListing":
			params = {};
			params["page"] = page;
			params["rows"] = pageSize;
			getCrowdfundingInvestTransferList(API["TransferList"],params,crateCanListing)
			break;
		//审核中
		case "Auditing":
			params = {};
			params["page"] = page;
			params["rows"] = pageSize;
			params["auditState"] = "auditing";
			getCrowdfundingInvestTransferList(API["AuditList"],params,createAuditingList)
			break;
		//审核完成
		case "Audited":
			params = {};
			params["page"] = page;
			params["rows"] = pageSize;
			params["auditStateAll"] = 1;
			params["isAgreeNull"] = 1;
			getCrowdfundingInvestTransferList(API["AuditList"],params,crateAuditedList)
			break;
		//挂牌中
		case "Listing":
			params = {};
			params["page"] = page;
			params["rows"] = pageSize;
			params["auditState"] = "audit_pass";
			params["status"] = "transfering";
			params["isAgree"] = 0;
			getCrowdfundingInvestTransferList(API["InfoList"],params,crateListingList)
			break;
		//挂牌完成
		case "lisTingEnd":
			params = {};
			params["page"] = page;
			params["rows"] = pageSize;
			params["auditState"] = "audit_pass";
			params["transferStateAll"] = 1;
			getCrowdfundingInvestTransferList(API["InfoList"],params,cratelisTingEndList)
			break;
		default:
			break;
	}
}

//是否开始挂牌转让接口
function whetherTransfer(flag){
	alert(flag)
}

//弹出申请挂牌提示框
function alerApply(orderId){
	var total = $("#createListing input[name='total']");
	var unitPrice = $("#createListing input[name='unitPrice']");
	var days = $("#createListing input[name='days']");
	var ok = $("#sendListing");
	var cancel = $("#cancel");
	var aLeft = ($(window).width()-$(".alerApply").outerWidth())/2;
	var aTop = (($(window).height()-$(".alerApply").outerHeight())/3)+$(document).scrollTop();
	$(".alerApply").css({
		left:aLeft,
		top:aTop
	})
	$(".alerApply").show();
	$(".alertbg").show();
	
	ok.unbind("click").bind("click",function(){
		if(validate(orderId)){
			createListing({
				"orderId":orderId,
				"total":total.val(),
				"unitPrice":unitPrice.val(),
				"days":days.val(),
				"callback":close
			})
		}
	})
	cancel.unbind("click").bind("click",function(){
		close();
	})
	$(".alertbg").unbind("click").bind("click",function(){
		close();
	})
	//关闭窗口
	var close = function(flag,message){
		if(flag === undefined){
			flag=true;
		}
		if(message === undefined){
			message = "";
		}
		if(flag){
			$(".alerApply").hide();
			$(".alertbg").hide();
			total.val(" ");
			unitPrice.val(" ");
			days.val(" ");
			AlertDialog.hide();
			$("#responseMessage").text(message);
		}else{
			$("#responseMessage").text(message);
		}
	}
	//教研窗口数据
	var validate = function(orderId){
		var expSpace = /(^\s*)|(\s*$)/g;
		var totalNum = total.val().replace(expSpace,"");
		var unitPriceNum = unitPrice.val().replace(expSpace,"");
		var daysNum = days.val().replace(expSpace,"");
		var curLoan = listingArray.findListingByOrderId(orderId)
		if(!curLoan){
			close();
			return false;
		}
		if(!totalNum){
			AlertDialog.show("申请份数不能为空","totalParts" , 10, 20);
			return false; 
		}
		//申请份数教研
		if(Number(totalNum) > curLoan["buyNum"]){
			AlertDialog.show("申请份数不能大于可申请最大份数","totalParts" , 10, 20);
			return false; 
		}
		if(Number(totalNum) < 0){
			AlertDialog.show("申请份数不能小于0份","totalParts" , 10, 20);
			return false; 
		}
		//申请单价校验
		if(!unitPriceNum){
			AlertDialog.show("申请单价不能为空","perAmt" , 10, 20);
			return false; 
		}
		if(Number(unitPriceNum) < 0){
			AlertDialog.show("申请单价不能小于0","perAmt" , 10, 20);
			return false; 
		}
		//申请天数校验
		if(!daysNum){
			AlertDialog.show("申请天数不能为空","transferDay" , 10, 20);
			return false; 
		}
		if(Number(daysNum) < 0){
			AlertDialog.show("申请天数不能小于0","transferDay" , 10, 20);
			return false; 
		}
		return true;
	}
	
}

//确定开始挂牌
function startTransfer(id){
	var params = {
		'isAgree':0,
		'id':id
	}
	sendisTransfer(params,function(){
		tabBindList('Audited',1)
	})
}

//取消挂牌
function cancelTransfer(id){
	var params = {
		'isAgree':1,
		'id':id
	}
	sendisTransfer(params,function(){
		tabBindList('Audited',1)
	})
}

//发送申请挂牌信息
function createListing(param){
	$.ajax({
		url: API["saveTransfer"],
		type: "post",
		dataType: "json",
		data: {
			"orderNo":param["orderId"],
			"transferParts":param["total"],
			"partMoney":param["unitPrice"],
			"transferDay":param["days"]
		},
		success: function(data){
			if(!data["success"]){
				param["callback"](false,data["msg"]);
				return false;
			}
			$("#responseMessage").text("申请成功");
			setTimeout(function(){
				param["callback"]();
				tabBindList("canListing",1);
			},1000)
		},
		error:function(){
			console.log("调用申请接口失败")
		}
	});
}

//发送是否挂牌请求
function sendisTransfer(params,callback){
	$.ajax({
		url: API["IsAgree"],
		type: "post",
		dataType: "json",
		data: params,
		success: function(data){
			if(!data["success"]){
				return false;
			}
			callback()
		},
		error:function(){
			console.log("调用开始挂牌接口失败")
		}
	});
}