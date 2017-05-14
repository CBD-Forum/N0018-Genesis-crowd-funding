if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
var pageSize = 6;
var type = window.location.search.substring(window.location.search.indexOf("type=")+5);
$(function(){
	
	$("#rightProjectList").next().show();
	centerLoanTab(); //个人中心下部标签分页
	$(".prompt_close").click(function(){
		$(".prompt_box").fadeOut();
		$(".sbgpop").hide();
		AlertDialog.hide();
	});
	
	getTransferConfig();
	//检测转让金额
	$("#Transfer_AMT").blur(function(){
		if(Valify.isNull($("#Transfer_AMT").val(), "Transfer_AMT", 10, 20)){
			if(isNaN($("#Transfer_AMT").val())){
				AlertDialog.show("转让金额请输入数字", "Transfer_AMT", 10, 20);
				return false;
			}
			if(Number($("#Transfer_AMT").val()) < 0){
				AlertDialog.show("转让金额不能小于0", "Transfer_AMT", 10, 20);
				return false;
			}
			AlertDialog.hide();
			$("#Transfer_fee1").text(($("#Transfer_AMT").val()*$("#Transfer_fee").attr("numb")).toFixed(2)+companyCode).attr("amt",($("#Transfer_AMT").val()*$("#Transfer_fee").attr("numb")).toFixed(2));
			$("#applicationAmt2").text((Number($("#Transfer_AMT").val())+Number($("#transFee1").attr("numb"))).toFixed(2)+companyCode);
			checkTransferAmt($("#applyApplicationDiv").attr("supportno"),$("#Transfer_AMT").val());
			return true;
		}
	});
	myEcreateWebUploader("image_file", "imgheadPhoto", "loan_logo_url", "imgheadLi");//上传头像
});
//检测转让金额
function checkTransferAmt(supportNo,transferAmt){
	$.ajax({
		url: path + "/crowdfundProductTransfer/checkTransferAmt.html",
		type: "post",
		dataType: "json",
		data: {
			"supportNo": supportNo,//支持编号
			"transferAmt": transferAmt//转让金额
		},
		success: function(data){
			if(data["success"]){
				$("#Transfer_AMT").attr("NumT",data["fee"]),//转让金额
				$("#Transfer_fee1").attr("NumT",data["transferAmt"])//转让手续费
				AlertDialog.hide();
				return true;
			}else{
				AlertDialog.show(data["msg"], "Transfer_AMT", 10, 20);
				return false;
			}
		},
		error: function(request){
			console.log("检测转让金额异常");
		}
	});
}
function getTransferConfig(){
	$.ajax({
		url: path + "/crowdfundProductTransfer/getTransferConfig.html",
		type: "post",
		dataType: "json",
		data: {},
		success: function(data){
			if(data["success"]){
				$("#Transfer_fee").text(data["TRANSFER_FEE"]*100+"%").attr("numb",data["TRANSFER_FEE"]);//转让手续费
				$("#Transfer_max_AMT").text(data["TRANSFER_MAX_AMT"]*100+"%").attr("amt",data["TRANSFER_MAX_AMT"]);//转让上限
				$("#Transfer_min_AMT").text(data["TRANSFER_MIN_AMT"]*100+"%").attr("amt",data["TRANSFER_MIN_AMT"]);//转让下限
			}
		},
		error: function(request){
			console.log("查看分红明细异常");
		}
	});
}

function centerLoanTab(){
	$("#centerLoanTab li").click(function(){
		$("#centerLoanTab").find("a").removeClass("cur");
		$(this).find("a").addClass("cur");
		$("#centerLoanType>div,.letvPage").hide();
		$(".letvPage").eq($(this).index()).show();
		$("#centerLoanType>div").eq($(this).index()).show();
		$("#centerLoanTabBody>div").hide();
		$("#" + $(this).attr("name") + "Body").show();
		getCenterList($(this).attr("name"));
	});
	if(type){
		$("#centerLoanTab").find("a").removeClass("cur");
		$("#centerLoanTab>li").eq(type).find("a").addClass("cur");
	}
	/*$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			$("#blockChainAddress").val(data["user"]["blockChainAddress"]);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});*/
	
}

function getCenterList(name){
	
	if(name == "support"){
		//supportList("",1,"");
		$("#supportType a").click(function(){
			$("#supportType a").removeClass("cur");
			$(this).addClass("cur"); 
			var code = $(this).attr("code");
			if(code == "possession"){
				getCanTransferList(1);
			}else{
				supportList(code,1,code);
			} 
		});
		$("#supportType").find("a.cur").first().click();
	}else if(name == "apply"){
		//applyList(1);
		$("#applyType a").click(function(){
			$("#applyType a").removeClass("cur");
			$(this).addClass("cur");
			if($(this).attr("code") == "canRefund" || $(this).attr("code") == "refunding" || $(this).attr("code") == "passed" || $(this).attr("code") == "refuse"){
				refundList(1,$(this).attr("code"));
			}else{
				applyList(1,$(this).attr("code"));
			}
			
		});
		$("#applyType").find("a.cur").first().click();
	}else if(name == "possession"){
		//changeAPIByCode(1);
		$("#possessionType a").click(function(){
			$("#possessionType a").removeClass("cur");
			$(this).addClass("cur");
			changeAPIByCode(1,$(this).attr("code"));
		});
		$("#possessionType").find("a.cur").first().click();
	}else if(name == "collection"){
		getMyAttentionList(1);
	}
}

//退款
function refundList(page,code){
	$.ajax({
		url: path+"/crowdfundUserCenter/getMySupportList.html",
		type: "post",
		dataType: "json",
		data:{
			"loanType": "pruduct",
			"transferUser": siteUserId,
			"mySupport":code,
			"requestRemark":"investor",
			"page":1,
			"rows":6
		},
		success: function(data){
			if(!data["success"]){
				console.log("获取可转让列表失败");
				return false;
			}
			sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			//listingArray.array = data;
			if(l == 0){
				eStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>您还没有发布项目，赶快去发布吧~<br/><a href="'+path+'/common/enterProjectNav.html">发布项目</a></div>'; 
				$("#applyBody>div").html(eStr);
				$("#applyPage").hide();
				return false;
			}
			var eArr = [], eStr = '';
			for(var i = 0; i<l; i++){
				
				eArr.push('<div class="proj_list">');
				eArr.push('<p class="clearfix mb17">');
				eArr.push('<span><i>项目编号：</i>'+data[i]["loanNo"]+'</span>');
				eArr.push('<span class="wid315"><i>收货人：</i>'+data[i]["supportUser"]+'</span>');
				eArr.push('<span class="wid245"><i>退款人：</i>'+data[i]["completeTime"]+'</span>');
				eArr.push('</p>');
				eArr.push('<span><i>项目名称：</i>'+data[i]["loanName"]+'</span>');
				eArr.push('<span class="wid315"><i>联系方式：</i>'+data[i]["supportUser"]+'</span>');
				eArr.push('<span class="wid245"><i>退款金额：</i>'+data[i]["completeTime"].toFixed(2)+companyCode+'</span>');
				eArr.push('</p>');
				eArr.push('<span><i>支持金额：</i>'+data[i]["supportAmt"].toFixed(2)+companyCode+'</span>');
				eArr.push('<span class="wid315"><i>收货地址：</i>'+data[i]["supportUser"]+'</span>');
				eArr.push('<span class="wid245"><i>退款说明：</i>'+data[i]["completeTime"]+'</span>');
				eArr.push('</p>');
				eArr.push('<span><i>支持时间：</i>'+data[i]["loanNo"]+companyCode+'</span>');
				eArr.push('<span class="wid315"><i>备注：</i>'+data[i]["supportUser"]+'</span>');
				if(code == "refunding"){
					eArr.push('<span class="wid245"><i>操作：</i>'+data[i]["completeTime"]+'</span>');
				}else if(code == "passed"){
					
				}else if(code == "refuse"){
					
				}else if(code == "possession"){
					
				}
				
				eArr.push('</p>');
				eArr.push('</div>');
				
			}
			eStr = eArr.join("");
			$("#applyBody>div").html(eStr);
			$("#applyPage").show();
			//分页设置
			pagePause = 0;
			if(page < 2){
				$("#applyPage").jPages({
					containerID : "applyBody",
					first:false,
					last:false,
					previous:" ",
					next:" ",
					clickStop   : true,
					perPage	: 6,
					allSumPage : sumPage,
					callback: function(obj){
						if(pagePause == 0){
							return false;
						}
						getMyAttentionList(obj["current"]);
					}
				});
			}
		},
		error:function(){
			
		}
	});
}

//我的收藏
function getMyAttentionList(page){
	$.ajax({
		url: path+"/crowdfundUserCenter/getMyAttentionList.html",
		type: "post",
		dataType: "json",
		data:{
			"loanType": "product"
		},
		success: function(data){
			if(!data["success"]){
				console.log("获取我的收藏列表失败");
				return false;
			}
			sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				eStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>您还没有收藏的项目，赶快去收藏吧~<br/><a href="'+path+'/common/projectList.html">浏览项目</a></div>'; 
				$("#collectionBody>div").html(eStr);
				$("#collectionPage").hide();
				return false;
			}
			var eArr = [], eStr = '';
			for(var i = 0; i<l; i++){
				eArr.push('<div class="pro_list clearfix">');
				eArr.push('<div class="fl mr30 position">');
				eArr.push('<div class="opacity"></div><div>'+data[i]["loanStateName"]+'</div>');
				eArr.push('<a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
				if(data[i]["loanLogo"]){
					eArr.push('<img src="'+cv.fileAddress+data[i]["loanLogo"]+'" width="132" height="100" >');
				}else{
					eArr.push('<img src="'+path+'/images/defaultImg.png" width="132" height="100" >');
				}
				eArr.push('</a>');
				eArr.push('</div>');
				eArr.push('<div class="fl">');
				eArr.push('<h5><a class="fr attentionLoan" follow="'+data[i]["loanNo"]+'" onclick="attentionLoan(this)">取消收藏</a><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a></h5>');
				eArr.push('<p class="clearfix pro_det pro_det2"><span style="width: 100%;">区块链查询地址：<a target="_blank" href="https://charts.ripple.com/#/graph/">'+data[i]["blockChainAddress"]+'</a></span></p>');
				
				eArr.push('<p class="clearfix pro_det mt8 pro_det2">');
				if(data[i]["loanUserNickName"]){
					eArr.push('<span>发起人：<i>'+data[i]["loanUserNickName"]+'</i></span>');
				}else{
					eArr.push('<span>发起人：<i>'+data[i]["loanUser"]+'</i></span>');
				}
				if(data[i]["attentionTime"]){
					eArr.push('<span>收藏时间：<i>'+data[i]["attentionTime"].substring(0,10)+'</i></span>');
				}else{
					eArr.push('<span>收藏时间：<i>--</i></span>');
				}
				if(data[i]["supportRatio"]){
					eArr.push('<span class="wd182">募集进度：<i>'+(data[i]["supportRatio"]*100).toFixed(2)+'%</i></span>');
				}else{
					eArr.push('<span class="wd182">募集进度：<i>0.00%</i></span>');
				}
				eArr.push('</p>');
				eArr.push('<p class="clearfix pro_det mt8 pro_det2">');
				if(data[i]["fundAmt"]){
					eArr.push('<span>已募集金额：<i>'+data[i]["fundAmt"].toFixed(2)+companyCode+'</i></span>');
				}else{
					eArr.push('<span>已募集金额：<i>0'+companyCode+'</i></span>');
				}
				if(data[i]["fundEndTime"]){
					eArr.push('<span>截止时间：<i>'+data[i]["fundEndTime"].substring(0,10)+'</i></span>');
				}else{
					eArr.push('<span>截止时间：<i>--</i></span>');
				}
				eArr.push('<span class="wd182">项目状态：<i>'+data[i]["loanStateName"]+'</i></span>');
				eArr.push('</p>');
				eArr.push('</div>');
				eArr.push('</div>');
			}
			eStr = eArr.join("");
			$("#collectionBody>div").html(eStr);
			$("#collectionPage").show();
			//分页设置
			pagePause = 0;
			if(page < 2){
				$("#collectionPage").jPages({
					containerID : "collectionBody",
					first:false,
					last:false,
					previous:" ",
					next:" ",
					clickStop   : true,
					perPage	: 6,
					allSumPage : sumPage,
					callback: function(obj){
						if(pagePause == 0){
							return false;
						}
						getMyAttentionList(obj["current"]);
					}
				});
			}
		},
		error:function(){
			
		}
	});
}



//我的转让
function changeAPIByCode(page,transferState){
	$.ajax({
		url: path+"/crowdfundProductTransfer/getCrowdfundTransferDetailList.html",
		type: "post",
		dataType: "json",
		data:{
			"transferUser": siteUserId,
/*			"transferState":transferState,*/
			"transferQueryType":transferState
		},
		success: function(data){
			if(!data["success"]){
				console.log("获取可转让列表失败");
				return false;
			}
			sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			//listingArray.array = data;
			if(l == 0){
				eStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>还没有项目记录哦，先去浏览其他项目吧~<br/><a href="'+path+'/common/projectList.html">浏览项目</a></div>'; 
				$("#possessionBody >div").html(eStr);
				$("#possessionPage").hide();
				return false;
			}
			var eArr = [], eStr = '';
			for(var i = 0; i<l; i++){
				eArr.push('<div class="pro_list clearfix pdtop_40">');
				eArr.push('<div class="fl mr30 position">');
				eArr.push('<div class="opacity"></div><div>'+data[i]["loanStateName"]+'</div>');
				eArr.push('<a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
				
			    eArr.push('<img src="'+cv.fileAddress+data[i]["loanLogo"]+'" width="132" height="100" >');
			    eArr.push('</a>');
				eArr.push('</div>');
				eArr.push('<div class="fl">');
				if(data[i]["transferState"] == "transfering"){
					eArr.push('<h5><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a><i>转让中</i></h5>');
				}else if(data[i]["transferState"] == "transfering_locking"){
					//eArr.push('<h5>'+data[i]["loanName"]+'<i>转让操作中</i></h5>');
					eArr.push('<h5><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a><i>转让中</i></h5>');
				}else if(data[i]["transferState"] == "transfered" && data[i]["transferAuditState"]=="auditing"){
					eArr.push('<h5><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a><i>审核中</i></h5>');
				}else if(data[i]["transferState"] == "transfered" && data[i]["transferAuditState"]=="auditing_passed"){
					//eArr.push('<h5>'+data[i]["loanName"]+'<i>审核通过操作中</i></h5>');
					eArr.push('<h5><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a><i>审核中</i></h5>');
				}else if(data[i]["transferState"] == "transfered" && data[i]["transferAuditState"]=="auditing_refuse"){
					//eArr.push('<h5>'+data[i]["loanName"]+'<i>审核拒绝操作中</i></h5>');
					eArr.push('<h5><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a><i>审核中</i></h5>');
				}else if(data[i]["transferState"] == "transfered" && data[i]["transferAuditState"]=="passed"){
					eArr.push('<h5><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a><i>转让完成</i></h5>');
				}else if(data[i]["transferState"] == "transfered" && data[i]["transferAuditState"]=="refuse"){
					eArr.push('<h5><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a><i>转让拒绝</i></h5>');
				}else if(data[i]["transferState"] == "transferend"){
					eArr.push('<h5><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a><i>转让结束</i></h5>');
				}/*else{
					eArr.push('<h5>'+data[i]["transferState"]+'<i>'+data[i]["transferAuditState"]+'</i></h5>');
				}*/
				oArr.push('<p class="clearfix pro_det pro_det2"><span style="width: 100%;">区块链查询地址：<a target="_blank" href="https://charts.ripple.com/#/graph/">'+data[i]["blockChainAddress"]+'</a></span></p>');
				
				eArr.push('<p class="clearfix pro_det mt8 pro_det2">');
				eArr.push('<span>发起人：<i>'+data[i]["loanUserName"]+'</i></span>');
				eArr.push('<span style="width:237px">转让编号：<i>'+data[i]["transferNo"]+'</i></span>');
				eArr.push('</p>');
				eArr.push('<p class="clearfix pro_det pro_det2">');
				eArr.push('<span>转让金额：<i>'+data[i]["transferAmt"].toFixed(2)+companyCode+'</i></span>');
				eArr.push('<span style="width:237px">转让发起时间：<i>'+data[i]["transferTime"]+'</i></span>');
				if(data[i]["transferState"] == "transfered" && data[i]["transferAuditState"]=="passed"){
					eArr.push('<span class="wd150 ml57" style="width: 116px;"><a class="col_blue tx_dec" onClick="possessionDetailed(this)" transferFee="'+data[i]["transferFee"]+'" transFee="'+data[i]["transFee"]+'" transferEndTime="'+data[i]["transferEndTime"]+'" loanNo="'+data[i]["loanNo"]+'" investAmt="'+data[i]["investAmt"]+'" transferAmt="'+data[i]["transferAmt"]+'" buyUserRealName="'+data[i]["buyUserRealName"]+'" transferTime="'+data[i]["transferTime"]+'" loanUserName="'+data[i]["loanUserName"]+'">查看转让明细</a></span>');
				}
				eArr.push('</p>');
				eArr.push('<p class="clearfix pro_det pro_det2">');
				eArr.push('<span>运费：<i>'+data[i]["transFee"].toFixed(2)+companyCode+'</i></span>');
				eArr.push('<span style="width:237px">转让截止时间：<i>'+data[i]["transferEndTime"]+'</i></span>');
				eArr.push('<span class="wd150 ml57" style="width: 116px;"><a class="col_blue tx_dec" onClick="returnDetails(this)">回报详情<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["backContent"]+'</em></i></a></span>');
				eArr.push('</p>');
				eArr.push('</div>');
				eArr.push('</div>');
				
			}
			eStr = eArr.join("");
			$("#possessionBody>div").html(eStr);
			$("#possessionPage").show();
			//分页设置
			pagePause = 0;
			if(page < 2){
				$("#possessionPage").jPages({
					containerID : "possessionBody",
					first:false,
					last:false,
					previous:" ",
					next:" ",
					clickStop   : true,
					perPage	: 6,
					allSumPage : sumPage,
					callback: function(obj){
						if(pagePause == 0){
							return false;
						}
						changeAPIByCode(obj["current"],$("#possessionType a.cur").attr("code"));
					}
				});
			}
		},
		error:function(){
			
		}
	});
}

//查看转让更改明细
function possessionDetailed(_this){
	$("#possessionDetailed").show();
	$(".sbgpop").show();
	var bl = (cv["winW"]-$("#possessionDetailed").width())/2, bt = (cv["winH"]-$("#possessionDetailed").height())/2;
	$("#possessionDetailed").css({"left":bl+"px", "top":bt+"px"}).fadeIn();
	$("#transferEndTime").text($(_this).attr("transferEndTime"));
	$("#possessionLoanNo").text($(_this).attr("loanNo"));
	$("#investAmt").text(Number($(_this).attr("investamt")).toFixed(2)+companyCode);
	$("#transferAmt").text(Number($(_this).attr("transferAmt")).toFixed(2)+companyCode);
	$("#transferAmt_sxf").text(Number($(_this).attr("transferFee")).toFixed(2)+companyCode);//手续费
	$("#possessionTransFee").text(Number($(_this).attr("transFee")).toFixed(2)+companyCode);
	$("#transferTotalAmt").text((Number($(_this).attr("transFee"))+Number($(_this).attr("transferAmt"))).toFixed(2)+companyCode);
	//$("#loanUserName").text($(_this).attr("loanUserName"));
	$("#actualArrivalAmount").text((Number($(_this).attr("transFee"))+Number($(_this).attr("transferAmt"))-Number($(_this).attr("transferFee"))).toFixed(2)+companyCode)
	$("#transferTime").text($(_this).attr("transferTime"));
	$("#buyUserRealName").text($(_this).attr("buyUserRealName"));
}

//我的申请
function applyList(page,loanState){
	if(loanState){
		var ajaxData = {
			"loanType":"product",
			"loanState": loanState,
			"page": page,
			"rows": 6	
		}
	}else{
		var ajaxData = {
			"loanType":"product",
			"loanStateAll": "all",
			"page": page,
			"rows": 6	
		}
	}
	$.ajax({
		url: path + "/crowdfundUserCenter/getMyCrowdfundList.html",
		type: "post",
		dataType: "json",
		data: ajaxData,
		success: function(data){
			if(!data["success"]){
				return false;
			}
			//if(id == "order"){
				var oArr = [], oStr = '', l, sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
				l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					oStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>您还没有发布项目，赶快去发布吧~<br/><a href="'+path+'/common/enterProjectNav.html">发布项目</a></div>';
					$("#applyBody>div").html(oStr).show();
					$("#applyBody").show();
					$("#applyPage").hide();
					return false;
				}
				for(var i=0;i<l;i++){
					oArr.push('<div class="pro_list clearfix">');
					oArr.push('<div class="fl mr30 position">');
					oArr.push('<div class="opacity"></div><div>'+data[i]["loanStateName"]+'</div>');
					oArr.push('<a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
					
				    oArr.push('<img src="'+cv.fileAddress+data[i]["loanLogo"]+'" width="132" height="100"/>');
				    oArr.push('</a>');
					oArr.push('</div>');
					oArr.push('<div class="fl">');
					oArr.push('<div class="clearfix">');
					oArr.push('<h5 class="fl"><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a></h5>');
					if(data[i]["loanState"]=="lended"){
						if(data[i]["refundCount"] > 0){
							oArr.push('<div class="fr" style="position:relative;">');
							oArr.push('<img src="'+path+'/images/letv/tk1.png" style="margin:-5px 5px 0 0;"/>');
							oArr.push('<a href="'+path+'/common/refundList.html?loanNo='+data[i]["loanNo"]+'" style="font-size:16px;color:#1ea5ff;text-decoration:underline;">退款列表</a>');
							if(data[i]["refundWaitConfirmCount"] > 0){
								oArr.push('<i style="position:absolute;top:-11px;right:-11px;"><img src="'+path+'/images/letv/tk2.png"/></i>');
							}
							oArr.push('</div>');
						}
					}
					oArr.push('</div>');
					oArr.push('<p class="clearfix pro_det pro_det2"><span style="width: 100%;">区块链查询地址：<a target="_blank" href="https://charts.ripple.com/#/graph/">'+ess"]+'</a></span></p>');
					
					oArr.push('<p class="clearfix pro_det mt8 pro_det2">');
					oArr.push('<span style="width:210px;">目标金额：<i title="'+data[i]["overFundAmtStr"]+companyCode+'" style="display:inline-block;width:116px;max-width:516px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;margin-bottom:-8px;">'+data[i]["overFundAmtStr"]+companyCode+'</i></span>');
					//oArr.push('<span class="wd182">申请时间：<i>'+data[i]["auditTime"].substring(0,10)+'</i></span>');
					if(data[i]["createTime"]){
						oArr.push('<span class="wd182">发起时间：<i>'+data[i]["createTime"].substring(0,10)+'</i></span>');
					}else{
						oArr.push('<span class="wd182">发起时间：<i>--</i></span>');
					}
					if(data[i]["supportRatio"]){
						oArr.push('<span style="width: 120px;">进度：<i>'+(data[i]["supportRatio"]*100).toFixed(2)+'%</i></span>');
					}else{
						oArr.push('<span style="width: 120px;">进度：<i>0.00%</i></span>');
					}
					oArr.push('<span style="width: 110px;">操作：</span>');
					oArr.push('</p>');
					oArr.push('<p class="clearfix pro_det mt8 pro_det2">');
					
					oArr.push('<span style="width: 210px;">已筹集金额：<i>'+data[i]["approveAmt"].toFixed(2)+companyCode+'</i></span>');
					if(data[i]["fundEndTime"]){
						oArr.push('<span class="wd182">截至时间：<i>'+data[i]["fundEndTime"].substring(0,10)+'</i></span>');
					}else{
						oArr.push('<span class="wd182">截至时间：<i>--</i></span>');
					}
					if(data[i]["loanState"]=="add"){
						oArr.push('<span style="width: 120px;">状态：<i>草稿</i></span>');
					}else if(data[i]["loanState"]=="submit"){
						oArr.push('<span style="width: 120px;">状态：<i>已提交</i></span>');
					}else if(data[i]["loanState"]=="preheat"){
						oArr.push('<span style="width: 120px;">状态：<i>预热中</i></span>');
					}else if(data[i]["loanState"]=="funding"){
						oArr.push('<span style="width: 120px;">状态：<i>募集中</i></span>');
					}else if(data[i]["loanState"]=="funded"){
						oArr.push('<span style="width: 120px;">状态：<i>募集结束</i></span>');
					}else if(data[i]["loanState"]=="lended"){
						oArr.push('<span style="width: 120px;">状态：<i>待发货</i></span>');
					}else if(data[i]["loanState"]=="end"){
						oArr.push('<span style="width: 120px;">状态：<i>已发货</i></span>');
					}else{
						oArr.push('<span style="width: 120px;">状态：--</span>');
					}
					if(data[i]["loanState"]=="add"){
						oArr.push('<span style="width: 110px;"><a class="col_blue tx_dec" style="padding-right:15px;" href="'+path+'/common/enterReward.html?loanNo='+data[i]["loanNo"]+'">编辑</a><a class="col_blue tx_dec" cloanno="'+data[i]["loanNo"]+'" loanName="'+data[i]["loanName"]+'" onclick="delCaoLoan(event)">删除</a></span>');
					}else if(data[i]["loanState"]=="submit"){
						oArr.push('<span style="width: 110px;"><i>--</i></span>');
					}else if(data[i]["loanState"]=="preheat"){
						oArr.push('<span style="width: 110px;"><i>--</i></span>');
					}else if(data[i]["loanState"]=="funding"){
						oArr.push('<span style="width: 110px;"><a class="col_blue tx_dec" onclick="projectDebriefing(this)" cloanNo="'+data[i]["loanNo"]+'" >录入项目进展</a></span>');
					}else if(data[i]["loanState"]=="funded"){
						oArr.push('<span style="width: 110px;"><i>--</i></span>');
					}else if(data[i]["loanState"]=="lended"){
						oArr.push('<span style="width: 110px;"><a class="col_blue tx_dec" href="'+path+'/common/enterLogico.html?loanNo='+data[i]["loanNo"]+'&tcode=lended&ctype=1">发货</a></span>');
					}else if(data[i]["loanState"]=="end"){
						oArr.push('<span style="width: 110px;"><a class="col_blue tx_dec" href="'+path+'/common/enterLogico.html?loanNo='+data[i]["loanNo"]+'&tcode=end&ctype=1">查看发货明细</a></span>');
					}else{
						oArr.push('<span style="width: 110px;"><i>--</i></span>');
					}
					oArr.push('</p>');
					
					oArr.push('</div>');
					oArr.push('</div>');
					
				}
				oStr = oArr.join("");
				$("#applyBody>div").html(oStr).show();
				$("#applyBody").show();
				$("#applyPage").show();
				//分页设置
				pagePause = 0;
				if(page < 2){
					$("#applyPage").jPages({
						containerID : "applyBody",
						first:false,
						last:false,
						previous:" ",
						next:" ",
						clickStop   : true,
						perPage	: 6,
						allSumPage : sumPage,
						callback: applyPageData
					});
				}
			//}
		},
		error: function(request){
			console.log("获取个人中心信息列表异常");
		}
	});
}

function applyPageData(obj){
	if(pagePause == 0){
		return false;
	}
	applyList(obj["current"],$("#applyType a.cur").attr("code"));
}

//录入项目进展
function projectDebriefing(_this){
	
	$("#timeNode,#enterContent,#orgLoanReceiveProve").val("");
	$("#uploadInFo").html("");
	$("#progressUl").html($("#progressHtml").html());
	var bl = (cv["winW"]-$("#projectDebriefing").width())/2, bt = (cv["winH"]-$("#projectDebriefing").height())/2;
	$("#projectDebriefing").css({"left":bl+"px", "top":bt+"px"}).fadeIn();
	$("#projectDebriefing,#bgpop").show();
	$("#projectDebriefing-none").click(function(){
		$("#projectDebriefing,#bgpop,#tip_div").hide();
	});
	//var obj = event.srcElement || event.target;
	var loanNo = $(_this).attr("cloanNo");
	$("#changeUpload").click(function(){
		$("#fileToUpload").click();
	});
	$("#enterContent").keyup(function(){
		if($(this).val().length>600){
			AlertDialog.show("说明不能超过600字", "enterContent", 10, 20);
			$(this).val($(this).val().substring(0,600));
		}
	});
	//录入项目进展
	$("#operatBtu").unbind("click").click(function(){
		if(Valify.isNull($("#timeNode").val(), "timeNode", 10, 30)){
			if(Valify.isNull($("#enterContent").val(), "enterContent", -10, 30)){
				//if(Valify.isNull($("#orgLoanReceiveProve").val(), "changeUpload", 10, 30)){
					$.ajax({
						url: path + "/crowdfundProgress/save.html",
						type: "post",
						dataType: "json",
						data: {
							"loanNo": loanNo,
							"timeNode" : $("#timeNode").val(),//时间节点
							"enterContent" : $("#enterContent").val(),//项目进展
							"imgFiles" : $("#orgLoanReceiveProve").val()//图片
						},
						success: function(data){
							if(data["success"]){
								$("#projectDebriefing").hide();
								AlertDialog.tip("录入成功！");
								setTimeout(function(){
									window.location.reload();
								},2000);
								$("#alertSure").click(function(){
									window.location.reload();
								});
							}else{
								$("#projectDebriefing").hide();
								AlertDialog.tip(data["msg"]);
								setTimeout(function(){
									window.location.reload();
								},2000);
								$("#alertSure").click(function(){
									window.location.reload();
								});
							}
						},
						error: function(request){
							console.log("查看分红明细异常");
						}
					});
				//}
			}
		}
	});
	$('#fileToUpload').on('change', function() {
	    $.ajaxFileUpload({
	        url:path+'/userAuth/uploadAuthFile.html',  
	        secureuri:false,  
	        fileElementId:'fileToUpload',//file标签的id  
	        dataType: 'json',//返回数据的类型  
	        success: function (data, status) {
	        	if(data["success"]){
	        		if($("#orgLoanReceiveProve").val()){
	        			var orgLoanReceiveProve = $("#orgLoanReceiveProve").val()+","+data.msg;
	        		}else{
	        			var orgLoanReceiveProve = data.msg;
	        		}
	        		$("#orgLoanReceiveProve").val(orgLoanReceiveProve);
	        		$("#uploadInFo").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+data.msg+'"><img src="'+cv.fileAddress+data.msg+'" width="100" height="100"/><a class="close_btn" style="height: 18px; width:18px; background: url('+path+'/images/letv/close2.png);" onClick=\'fileDelete(this,"#uploadInFo","#orgLoanReceiveProve")\'></a></div>');
	        		AlertDialog.hide();
				}else{
					AlertDialog.RZmtip(data.msg);
				}
	        },  
	        error: function (data, status, e) {  
	            alert(e);
	        },
	        complete:function(XMLHttpRequest,textStatus){
	        	
	        }
	    });  
	});
}

//文件删除
function fileDelete(_this,id1,id2){
	$(id2).val("");
	$(_this).parent().remove();
	var _num = $(id1).find("div").length;
	for(var f=0;f<_num;f++){
		if(_num>0){
			if($(id2).val()){
				$(id2).val($(id2).val()+","+$(id1).find("div").eq(f).attr("url"));
			}else{
				$(id2).val($(id1).find("div").eq(f).attr("url"));
			}
		}else if(_num<0){
			$(id2).val("");
		}
	}
}

//删除新增项目或草稿项目
function delCaoLoan(event){
	var obj = event.srcElement || event.target;
	var loanNo = $(obj).attr("cloanNo");
	var loanName = $(obj).attr("loanName");
	AlertDialog.confirmReward(list2DelLoan, null, "您确定要删除【"+loanName+"】项目？", "确定", "取消", loanNo);
}
function list2DelLoan(loanNo){
	$.ajax({
		url: path + "/crowdfunding/deleteCrowdFunding.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(data["success"]){
				AlertDialog.tip("删除成功");
				$("a[cloanno="+loanNo+"]").parent().parent().parent().parent().remove();
			}
		},
		error: function(request){
			console.log("删除草稿项目异常");
		}
	});
}
//我的支持
function supportList(code,page,mySupport){
	$.ajax({
		url: path + "/crowdfundUserCenter/getMySupportList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanType":"product",
			"mySupport": mySupport,
			"isTransfer":"0",
			"requestRemark":"investor",
			"page": page,
			"rows": 6
			},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			//if(id == "order"){
				var oArr = [], oStr = '', l, sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
				l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					oStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>您还没有相关订单，赶快去支持吧~<br/><a href="'+path+'/common/projectList.html">浏览项目</a></div>';
					$("#supportBody>div").html(oStr).show();
					$("#supportBody").show();
					$("#supportPage").hide();
					return false;
				}
				for(var i=0;i<l;i++){
					oArr.push('<div class="pro_list clearfix">');
					oArr.push('<div class="fl mr30 position">');
					oArr.push('<div class="opacity"></div><div>'+data[i]["loanStateName"]+'</div>');
					oArr.push('<a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
					
					if(data[i]["loanLogo"]){
					    oArr.push('<img src="'+cv.fileAddress+data[i]["loanLogo"]+'" width="132" height="100"/>');
					}else{
					    oArr.push('<img src="'+path+'/images/defaultImg.png" width="132" height="100"/>');
					}
					oArr.push('</a>');
					oArr.push('</div>');
					oArr.push('<div class="fl">');
					oArr.push('<h5><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a></h5>');
					oArr.push('<p class="clearfix pro_det pro_det2"><span style="width: 100%;">区块链查询地址：<a target="_blank" href="https://charts.ripple.com/#/graph/">'+data[i]["blockChainAddress"]+'</a></span></p>');
					
					oArr.push('<p class="clearfix pro_det mt8 pro_det2">');
					oArr.push('<span>订单编号：<i>'+data[i]["orderId"]+'</i></span>');
					oArr.push('<span>支持金额：<i>'+data[i]["supportAmt"].toFixed(2)+companyCode+'</i></span>');
					oArr.push('<span class="wd182">支持时间：<i>'+data[i]["supportTime"].substring(0,10)+'</i></span>');
					oArr.push('</p>');
					oArr.push('<p class="clearfix pro_det mt8 pro_det2">');
					if(data[i]["supportRatio"]){
						oArr.push('<span>募集进度：<i>'+(data[i]["supportRatio"]*100).toFixed(2)+'%</i></span>');
					}else{
						oArr.push('<span>募集进度：<i>0.00%</i></span>');
					}
					oArr.push('<span>已筹集金额：<i>'+data[i]["approveAmt"].toFixed(2)+companyCode+'</i></span>');
					if(data[i]["fundEndTime"]){
						oArr.push('<span class="wd182">截至时间：<i>'+data[i]["fundEndTime"].substring(0,10)+'</i></span>');
					}else{
						oArr.push('<span class="wd182">截至时间：<i>--</i></span>');
					}
					oArr.push('</p>');
					oArr.push('<p class="clearfix pro_det mt8 pro_det2">');
					oArr.push('<span>发起人：<i>'+data[i]["loanUserName"]+'</i></span>');
					oArr.push('<span>回报详情：<a class="col_blue tx_dec" onClick="returnDetails(this)">查看详情');
					oArr.push('<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["backContent"]+'</em></i>');
					oArr.push('</a></span>');
					if(data[i]["prizeNo"]>=0){
						oArr.push('<span class="wd182" style="z-index: 2;position: relative;">抽奖编号：');	
						oArr.push('<a class="col_blue tx_dec" onClick="returnDetails(this)">查看');
						oArr.push('<i class="tk_shm tk_shm2" style="display:none;background-size: 100% auto;width: 124px;background-position: top;">您的抽奖编号是：'+data[i]["prizeNo"]+'</i>');
						oArr.push('</a>');
						oArr.push('</span>');
					}
//					
//						$.ajax({
//							url: path+"/crowdfunding/getUserPrize.html",
//							type: "post",
//							async: false,
//							dataType: "json",
//							data: {
//								"loanNo":data[i]["loanNo"],
//								"supportUser": siteUserId
//							},
//							success: function(dataA){
//								if(dataA["msg"]){
//									oArr.push('<i class="tk_shm tk_shm2" style="display:none;background-size: 100% auto;width: 124px;background-position: top;">'+dataA["msg"]["prizeNo"]+'</i>');
//								}else{
//									oArr.push('<i class="tk_shm tk_shm2" style="display:none;background-size: 100% auto;width: 124px;background-position: top;">--</i>');
//								}
//							},
//							error: function(request){
//								console.log("保存项目基本信息异常");
//							}
//						});		
					
					oArr.push('</p>');
					oArr.push('<p class="clearfix pro_det mt8 pro_det2">');
					if(data[i]["transferDay"]){
						var transferDay = data[i]["transferDay"];
					}else{
						var transferDay = 0;
					}
					if(code!="banTransfer"){
						if(code == "noPay"){
							oArr.push('<span>状态：<i>待支付</i></span>');
							if(data[i]["loanState"] == "funding"){
								oArr.push('<span>操作：<a href="'+path+'/common/crowdfund-pay.html?loanNo='+data[i]["loanNo"]+'&backNo='+data[i]["backNo"]+'&orderId='+data[i]["orderId"]+'&ctype=1" class="col_blue tx_dec">继续支付</a></span>');
							}else{
								oArr.push('<span>操作：交易结束</span>');
							}
						}else if(code == "payed"){
							oArr.push('<span>状态：<i>已支付</i></span>');
							if(data[i]["prizeDrawFlag"] == "0"){
								if((data[i]["canTransferState"] == "1" && data[i]["transferCount"] == 0 && data[i]["supportClass"]!="transferSupport") || data[i]["state"] == "sending"){
									oArr.push('<span class="operation'+i+'">操作：');
									oArr.push('<i class="product_more">');
									oArr.push('<i class="product_more_Div">');
									oArr.push('<a class="a1"></a>');
									oArr.push('<a class="a2"></a>');
									oArr.push('</i>');
									oArr.push('<em class="product_more_Ul">');
//									if(data[i]["refundType"] == "canRefund"){//可退款-申请退款
//										oArr.push('<a value="tksq" loanNo="'+data[i]["loanNo"]+'" loanUserName="'+data[i]["loanUserName"]+'" loanName="'+data[i]["loanName"]+'" Amt="'+data[i]["approveAmt"]+'" Amt1="'+data[i]["supportAmt"]+'" orderId="'+data[i]["orderId"]+'" transFee="'+data[i]["transFee"]+'">申请退款</a>');
//						            }
									if(data[i]["canTransferState"] == "1" && data[i]["transferCount"] == 0  && data[i]["supportClass"]!="transferSupport"){//可转让-申请转让
										oArr.push('<a value="zrsq"  onClick="applyApplication(this)" backContent="'+data[i]["backContent"]+'" transFee="'+data[i]["transFee"]+'" transferDay="'+transferDay+'" amt="'+data[i]["supportAmt"]+'" user="'+data[i]["supportUser2"]+'" loanName="'+data[i]["loanName"]+'" loanNo="'+data[i]["loanNo"]+'" orderId="'+data[i]["orderId"]+'" supportAmt="'+data[i]["supportAmt"]+'" supportUserName="'+data[i]["supportUserName"]+'">申请转让</a>');
									}
									if(data[i]["state"] == "end" || data[i]["state"] == "sending"){//已发货-查看发货明细
										oArr.push('<a value="ckfhmx" orderId="'+data[i]["orderId"]+'"  onClick=" showDeliveryInfo(this)"  >查看发货明细</a>');
						            }
//									if(data[i]["refundType"] == "refundSuccess"){//退款成功-查看退款成功明细
//						            	oArr.push('<a value="cktkcgmx" orderId="'+data[i]["orderId"]+'">查看退款明细</a>');
//						            }
//									if(data[i]["refundType"] == "refundFail"){//退款失败-查看退款失败原因
//						            	oArr.push('<a value="cktksbyy" supportNo="'+data[i]["orderId"]+'" orderId="'+data[i]["orderId"]+'">查看退款失败原因</a>');
//						            }
									oArr.push('</em>');
									oArr.push('</i>');
									oArr.push('</span>');
								}else{
									oArr.push('<span>操作：<i>--</i></span>');
					            }
							}else{
								oArr.push('<span>操作：<i>--</i></span>');
				            }
							
						}else if(code == "waitSend"){
							oArr.push('<span>状态：<i>待发货</i></span>');
							if(data[i]["prizeDrawFlag"] == "0"){
								if((data[i]["canTransferState"] == "1" && data[i]["transferCount"] == 0 && data[i]["supportClass"]!="transferSupport") || data[i]["state"] == "sending"){
									oArr.push('<span class="operation'+i+'">操作：');
									oArr.push('<i class="product_more">');
									oArr.push('<i class="product_more_Div">');
									oArr.push('<a class="a1"></a>');
									oArr.push('<a class="a2"></a>');
									oArr.push('</i>');
									oArr.push('<em class="product_more_Ul">');
//									if(data[i]["refundType"] == "canRefund"){//可退款-申请退款
////										oArr.push('<option value="tksq" loanNo="'+data[i]["loanNo"]+'" loanUserName="'+data[i]["loanUserName"]+'" loanName="'+data[i]["loanName"]+'" Amt="'+data[i]["approveAmt"]+'" Amt1="'+data[i]["supportAmt"]+'" orderId="'+data[i]["orderId"]+'" transFee="'+data[i]["transFee"]+'">申请退款</option>');
//										oArr.push('<a value="tksq" loanNo="'+data[i]["loanNo"]+'" loanUserName="'+data[i]["loanUserName"]+'" loanName="'+data[i]["loanName"]+'" Amt="'+data[i]["approveAmt"]+'" Amt1="'+data[i]["supportAmt"]+'" orderId="'+data[i]["orderId"]+'" transFee="'+data[i]["transFee"]+'">申请退款</a>');
//						            }
									if(data[i]["canTransferState"] == "1" && data[i]["transferCount"] == 0 && data[i]["supportClass"]!="transferSupport"){//可转让-申请转让
//										oArr.push('<option value="zrsq" backContent="'+data[i]["backContent"]+'" transFee="'+data[i]["transFee"]+'" transferDay="'+transferDay+'" amt="'+data[i]["supportAmt"]+'" user="'+data[i]["supportUser2"]+'" loanName="'+data[i]["loanName"]+'" loanNo="'+data[i]["loanNo"]+'" orderId="'+data[i]["orderId"]+'" supportUserName="'+data[i]["supportUserName"]+'">申请转让</option>');
										oArr.push('<a value="zrsq" backContent="'+data[i]["backContent"]+'" transFee="'+data[i]["transFee"]+'" transferDay="'+transferDay+'" amt="'+data[i]["supportAmt"]+'" user="'+data[i]["supportUser2"]+'" loanName="'+data[i]["loanName"]+'" loanNo="'+data[i]["loanNo"]+'" orderId="'+data[i]["orderId"]+'" supportAmt="'+data[i]["supportAmt"]+'" supportUserName="'+data[i]["supportUserName"]+'">申请转让</a>');
									}
									if(data[i]["state"] == "end" || data[i]["state"] == "sending"){//已发货-查看发货明细
//										oArr.push('<option value="ckfhmx" orderId="'+data[i]["orderId"]+'">查看发货明细</option>');
										oArr.push('<a value="ckfhmx" orderId="'+data[i]["orderId"]+'" >查看发货明细</a>');
						            }
									oArr.push('</em>');
									oArr.push('</i>');
									oArr.push('</span>');
								}else{
									oArr.push('<span>操作：<i>--</i></span>');
					            }
							}else{
								oArr.push('<span>操作：<i>--</i></span>');
				            }
							
						}else if(code == "sended"){
							oArr.push('<span>状态：<i>已发货</i></span>');
							if(data[i]["state"] == "sending"){//已发货
								oArr.push('<span>操作：<a orderId="'+data[i]["orderId"]+'" onClick="showDeliveryInfo(this)" class="col_blue tx_dec">查看发货明细</a></span>');
				            }else{
								oArr.push('<span>操作：<i>--</i></span>');
				            }
						}else if(code == "canRefund"){
							oArr.push('<span>状态：<i>可退款</i></span>');
							if(data[i]["refundType"] == "canRefund"){//可退款
								oArr.push('<span>操作：<a class="col_blue tx_dec" loanNo="'+data[i]["loanNo"]+'" loanUserName="'+data[i]["loanUserName"]+'" loanName="'+data[i]["loanName"]+'" Amt="'+data[i]["approveAmt"]+'" Amt1="'+data[i]["supportAmt"]+'" orderId="'+data[i]["orderId"]+'" transFee="'+data[i]["transFee"]+'" onClick="applicationContent(this)" >申请退款</a></span>');
				            }else{
								oArr.push('<span>操作：<i>--</i></span>');
				            }
						}else if(code == "refunding"){
							oArr.push('<span>状态：<i>退款中</i></span>');
							oArr.push('<span>操作：<i>--</i></span>');
						}else if(code == "refundSuccess"){
							oArr.push('<span>状态：<i>退款成功</i></span>');
							if(data[i]["refundType"] == "refundSuccess"){//退款成功
								oArr.push('<span>状态：<a orderId="'+data[i]["orderId"]+'" onClick="showRefundSuccess(this)" class="col_blue tx_dec">查看退款明细</a></span>');
				            }else{
								oArr.push('<span>操作：<i>--</i></span>');
				            }
						}else if(code == "refundFail"){
							oArr.push('<span>状态：<i>退款失败</i></span>');
							if(data[i]["refundType"] == "refundFail"){//退款失败
								oArr.push('<span>状态：<a class="col_blue tx_dec" supportNo="'+data[i]["orderId"]+'" onClick="showRefundFailed(this)">查看退款失败原因</a></span>');
				            }else{
								oArr.push('<span>操作：<i>--</i></span>');
				            }
						}else if(code == "canTransfer"){
							oArr.push('<span>状态：<i>可转让</i></span>');
							if(data[i]["canTransferState"] == "1" && data[i]["transferCount"] == 0){//可转让-申请转让
								oArr.push('<span>操作：<a class="col_blue tx_dec" backContent="'+data[i]["backContent"]+'" transFee="'+data[i]["transFee"]+'" transferDay="'+transferDay+'" amt="'+data[i]["supportAmt"]+'" user="'+data[i]["supportUser2"]+'" loanName="'+data[i]["loanName"]+'" loanNo="'+data[i]["loanNo"]+'" orderId="'+data[i]["orderId"]+'" supportUserName="'+data[i]["supportUserName"]+'" supportAmt="'+data[i]["supportAmt"]+'" onClick="applyApplication(this)">申请转让</a></span>');
							}else{
								oArr.push('<span>操作：<i>--</i></span>');
							}
						}
					}else{//全部
						
						if(data[i]["payState"] == "noPay"){
							oArr.push('<span>状态：<i>待支付</i></span>');
							if(data[i]["loanState"] == "funding"){
								oArr.push('<span>操作：<a href="'+path+'/common/crowdfund-pay.html?loanNo='+data[i]["loanNo"]+'&backNo='+data[i]["backNo"]+'&orderId='+data[i]["orderId"]+'&ctype=1" class="col_blue tx_dec">继续支付</a></span>');
							}else{
								oArr.push('<span>操作：交易结束</span>');
							}
						}else if(data[i]["payState"] == "payed"){
							if(data[i]["state"] == "sending"){
								oArr.push('<span>状态：<i>已发货</i></span>');
								if(data[i]["state"] == "sending" || data[i]["refundType"] == "refundFail"){
									oArr.push('<span class="operation'+i+'">操作：');
//									oArr.push('<i class="product_more">');
//									oArr.push('<i class="product_more_Div">');
//									oArr.push('<a class="a1"></a>');
//									oArr.push('<a class="a2"></a>');
//									oArr.push('</i>');
//									oArr.push('<em class="product_more_Ul">');
									if(data[i]["state"] == "end" || data[i]["state"] == "sending"){//已发货-查看发货明细
										oArr.push('<a class="col_blue tx_dec" value="ckfhmx" orderId="'+data[i]["orderId"]+'">查看发货明细</a>');
						            }else{
						            	oArr.push('<i>--</i>');
						            }
									/*if(data[i]["refundType"] == "refundFail"){//退款失败-查看退款失败原因
										oArr.push('<a value="cktksbyy" supportNo="'+data[i]["orderId"]+'" orderId="'+data[i]["orderId"]+'">查看退款失败原因</a>');
						            }*/
//									oArr.push('</em>');
//									oArr.push('</i>');
									oArr.push('</span>');
								}else{
									oArr.push('<span>操作：<i>--</i></span>');
					            }
							}else if(data[i]["state"] == "lended"){
								
								oArr.push('<span>状态：<i>待发货</i></span>');
								if(data[i]["prizeDrawFlag"] == "0"){
									if(data[i]["canTransferState"] == "1" && data[i]["transferCount"] == "0" && data[i]["supportClass"]!="transferSupport"){
										
										oArr.push('<span class="operation'+i+'">操作：');
//										oArr.push('<i class="product_more">');
//										oArr.push('<i class="product_more_Div">');
//										oArr.push('<a class="a1"></a>');
//										oArr.push('<a class="a2"></a>');
//										oArr.push('</i>');
//										oArr.push('<em class="product_more_Ul">');
//										if(data[i]["refundType"] == "canRefund"){//可退款-申请退款
//											oArr.push('<a value="tksq" loanNo="'+data[i]["loanNo"]+'" loanUserName="'+data[i]["loanUserName"]+'" loanName="'+data[i]["loanName"]+'" Amt="'+data[i]["approveAmt"]+'" Amt1="'+data[i]["supportAmt"]+'" orderId="'+data[i]["orderId"]+'" transFee="'+data[i]["transFee"]+'">申请退款</a>');
//							            }
										//if(data[i]["canTransferState"] == "1"){//可转让-申请转让
											oArr.push('<a class="col_blue tx_dec" value="zrsq"  onClick="applyApplication(this)" backContent="'+data[i]["backContent"]+'" transFee="'+data[i]["transFee"]+'" transferDay="'+transferDay+'" amt="'+data[i]["supportAmt"]+'" user="'+data[i]["supportUser2"]+'" loanName="'+data[i]["loanName"]+'" loanNo="'+data[i]["loanNo"]+'" orderId="'+data[i]["orderId"]+'" supportAmt="'+data[i]["supportAmt"]+'" supportUserName="'+data[i]["supportUserName"]+'">申请转让</a>');
										//}
//										if(data[i]["refundType"] == "refundFail"){//退款失败-查看退款失败原因
//											oArr.push('<a value="cktksbyy" supportNo="'+data[i]["orderId"]+'" orderId="'+data[i]["orderId"]+'">查看退款失败原因</a>');
//							            }
//										oArr.push('</em>');
//										oArr.push('</i>');
										oArr.push('</span>');
									}else{
										oArr.push('<span>操作：<i>--</i></span>');
						            }
								}else{
									oArr.push('<span>操作：<i>--</i></span>');
					            }
								
							}/*else if (data[i]["refundType"] == "refundSuccess"){//已退款（退款成功）
								oArr.push('<span>状态：<i>退款成功</i></span>');
								if(data[i]["refundType"] == "refundSuccess"){
									oArr.push('<span>操作：<a class="col_blue tx_dec" orderId="'+data[i]["orderId"]+'" onClick="showRefundSuccess(this)">查看退款明细</a></span>');
								}else{
									oArr.push('<span>操作：<i>--</i></span>');
					            }
					        }*/else{//未知状态2
					        	oArr.push('<span>状态：<i>已支付</i></span>');
								oArr.push('<span>操作：<i>--</i></span>');
					        }
						}else{
							oArr.push('<span>操作：<i>--</i></span>');
					    }
					}
					if(data[i]["prizeDrawFlag"] == "1" && data[i]["prizeNo"]>=0){
						if(data[i]["prizeNo"]>=0 && data[i]["isPrize"]){
							if(data[i]["isPrize"] == "0"){
								oArr.push('<span class="wd182">抽奖状态：<i>未中奖</i></span>');
							}else{
								oArr.push('<span class="wd182">抽奖状态：<a class="col_blue">恭喜中奖</a></span>');
								oArr.push('<img src="'+path+'/images/letv/winning.png" style=" top: 30px;right: 40px;position: absolute;">');
							}
						}else{
							oArr.push('<span class="wd182">抽奖状态：<i>未开奖</i></span>');
						}
					}
					
					
					oArr.push('</p>');
					oArr.push('</div>');
					oArr.push('</div>');
				}
				oStr = oArr.join("");
				$("#supportBody>div").html(oStr);
				for(var i=0;i<l;i++){
					if($(".operation"+i+" .product_more_Ul a").length == 1){
						$(".operation"+i+" .product_more_Div .a2").hide();
						$(".operation"+i+" .product_more_Ul").hide();
						$(".operation"+i+" .product_more_Div .a1").text($(".operation"+i+" .product_more_Ul a").text());
						$(".operation"+i+" .product_more_Div .a1").attr("value",$(".operation"+i+" .product_more_Ul a").attr("value"));
					}else if($(".operation"+i+" .product_more_Ul a").length > 1){
//						$(".operation"+i+" .product_more_Div .a1").text($(".operation"+i+" .product_more_Ul a:first-child").text());
//						$(".operation"+i+" .product_more_Div .a1").attr("value",$(".operation"+i+" .product_more_Ul a").attr("value"));
						$(".operation"+i+" .product_more_Div .a1").text("查看更多");
					}
				}
				$(".product_more_Div .a2").click(function(){
					$(this).parent().parent().find(".product_more_Ul").slideToggle(500);
				});
				$(".product_more_Ul a").click(function(){
					$(this).parent().slideToggle(500);
					$(this).parent().parent().find($(".product_more_Div .a1")).text($(this).text());
					$(this).parent().parent().find($(".product_more_Div .a1")).attr("value",$(this).attr("value"));
					$(this).parent().parent().find($(".product_more_Div .a1")).click();
				});
				$(".product_more_Div .a1").click(function(){
					if($(this).text() == "查看更多"){
						$(this).parent().parent().find(".product_more_Ul").slideToggle(500);
					}
					var _this = $(this).parent().parent().find(".product_more_Ul a[value="+$(this).attr("value")+"]");
					if($(this).attr("value") == "ckfhmx"){//查看发货明细
						showDeliveryInfo(_this);
					}
					if($(this).attr("value") == "tksq"){//退款申请
						applicationContent(_this);
					}
					if($(this).attr("value") == "zrsq"){//转让申请
						applyApplication(_this);
					}
					if($(this).attr("value") == "cktkcgmx"){//查看退款成功明细
						showRefundSuccess(_this);
					}
					if($(this).attr("value") == "cktksbyy"){//查看退款失败原因
						showRefundFailed(_this);
					}
				});
				$("#supportBody").show();
				$("#supportPage").show();
				//分页设置
				pagePause = 0;
				if(page < 2){
					$("#supportPage").jPages({
						containerID : "supportBody",
						first:false,
						last:false,
						previous:" ",
						next:" ",
						clickStop   : true,
						perPage	: 6,
						allSumPage : sumPage,
						callback: supportPageData
					});
				}
			//}
		},
		error: function(request){
			console.log("获取个人中心信息列表异常");
		}
	});
}
//查看退款失败原因
function showRefundFailed(_this){
	$("#refundFailed").show();
	$(".sbgpop").show();
	var bl = (cv["winW"]-$("#refundFailed").width())/2, bt = (cv["winH"]-$("#refundFailed").height())/2;
	$("#refundFailed").css({"left":bl+"px", "top":bt+"px"}).fadeIn();
	$.ajax({
		url: path+"/crowdfunding/getLoanAuditRefundList.html",
		type: "post",
		async: false,
		dataType: "json",
		data: {
			"supportNo":$(_this).attr("supportNo")
		},
		success: function(data){
			if(data["msg"][0]){
				if(data["msg"][0]["auditTime"]){
					$("#auditTime").text(data["msg"][0]["auditTime"]);
				}else{
					$("#auditTime").text("--");
				}
				if(data["msg"][0]["auditOpinion"]){
					$("#auditOpinion").text(data["msg"][0]["auditOpinion"]);
				}else{
					$("#auditOpinion").text("--");
				}
			}else{
				$("#auditTime").text("--");
				$("#auditOpinion").text("--");
			}
		},
		error: function(request){
			console.log("保存项目基本信息异常");
		}
	});	
}
//查看退款成功明细
function showRefundSuccess(_this){
	$("#refundSuccess").show();
	$(".sbgpop").show();
	var bl = (cv["winW"]-$("#refundSuccess").width())/2, bt = (cv["winH"]-$("#refundSuccess").height())/2;
	$("#refundSuccess").css({"left":bl+"px", "top":bt+"px"}).fadeIn();
	$.ajax({
		url: path+"/crowdfundUserCenter/getMySupportList.html",
		type: "post",
		async: false,
		dataType: "json",
		data: {
			"orderId":$(_this).attr("orderId"),
			"requestRemark":"investor"
		},
		success: function(data){
			var data = data["msg"]["rows"][0];
			if(data["refuseComplateTime"]){
				$("#refuseSuccessTime").text(data["refuseComplateTime"]);
			}else{
				$("#refuseSuccessTime").text("--");
			}
			if(data["supportAmt"]){
				$("#refuseSupportAmt").text(data["supportAmt"].toFixed(2)+companyCode);
			}else{
				$("#refuseSupportAmt").text("--");
			}
			if(data["supportTime"]){
				$("#refuseSupportTime").text(data["supportTime"]);
			}else{
				$("#refuseSupportTime").text("--");
			}
			$("#refuseAmt").text((Number(data["supportAmt"])+Number(data["transFee"])).toFixed(2)+companyCode);
			if(data["refuseApplyTime"]){
				$("#refuseApplyTime").text(data["refuseApplyTime"]);
			}else{
				$("#refuseApplyTime").text("--");
			}
			if(data["refuseLoanAuditTime"]){
				$("#refuseLoanAuditTime").text(data["refuseLoanAuditTime"]);
			}else{
				$("#refuseLoanAuditTime").text("--");
			}
			if(data["applicationContent"]){
				$("#refuseationContent").text(data["applicationContent"]);
			}else{
				$("#refuseationContent").text("--");
			}
		},
		error: function(request){
			console.log("保存项目基本信息异常");
		}
	});	
}
//查看发货明细
function showDeliveryInfo(_this){
	$("#deliveryInfo").show();
	$(".sbgpop").show();
	var bl = (cv["winW"]-$("#deliveryInfo").width())/2, bt = (cv["winH"]-$("#deliveryInfo").height())/2;
	$("#deliveryInfo").css({"left":bl+"px", "top":bt+"px"}).fadeIn();
	$.ajax({
		url: path+"/crowdfundUserCenter/getMySupportList.html",
		type: "post",
		async: false,
		dataType: "json",
		data: {
			"orderId":$(_this).attr("orderId"),
			"requestRemark":"investor"
		},
		success: function(data){
			var data = data["msg"]["rows"][0];
			$("#FcompleteTime").text(data["completeTime"]);
			$("#FsupportAmt").text(data["supportAmt"].toFixed(2)+companyCode);
			$("#FsupportTime").text(data["supportTime"]);
			$("#FbackContent").text(data["backContent"]);
			$("#FpostUser").text(data["postUser"]);
			$("#FpostMobile").text(data["postMobile"]);
			if(data["backType"] == "S"){
				var FaddressPost;
				if(data["cityName"]){
					FaddressPost = data["provinceName"]+" "+data["cityName"]+" "+data["adressDetail"];
				}else{
					FaddressPost = data["provinceName"]+data["adressDetail"];
				}
				$("#FaddressPost").text(FaddressPost);//地址
				$("#FaddressPost").attr("title", FaddressPost);
				$("#FsendDelivery").parent().show();
				$("#FsendDelivery").text(data["sendDelivery"]);//物流公司
				$("#FsendDelivery").attr("title", data["sendDelivery"]);
				$("#FsendOrderId").text(data["sendOrderId"]);//物流单号
				$("#FsendOrderId").attr("title", data["sendOrderId"]);
			}else if(data["backType"] == "X"){
				$("#FaddressPost").text(data["sendDelivery"]);//地址
				$("#FaddressPost").attr("title", data["sendDelivery"]);
				$("#FsendDelivery").parent().hide();
			}
			$("#Fremark").text(data["remark"]);//备注
		},
		error: function(request){
			console.log("保存项目基本信息异常");
		}
	});	
}
//查看退款明细
function seeRefund(_this){
	$.ajax({
		url: path+"/crowdfunding/getLoanAuditRefundList.html",
		type: "post",
		async: false,
		data: {
			"supportNo":$(_this).attr("orderId")
		},
		success: function(data){
			if(data["success"]){
				$("#refundName").text(data["loanName"]);
				$("#refundAmt1,#refundAmt2").text(Number($(_this).attr("Amt")).toFixed(2));
				$("#refundTime1,#refundTime2,#refundTime3,#refundTime4").text(data["createTime"]);
				$("#refundExplain").html(data["auditOpinion"]);
				$("#seeRefundDiv,#bgpop").show();
				$(".prompt_close").click(function(){
					$("#seeRefundDiv,#bgpop").hide();
				});
			}else{
				AlertDialog.tip(data["msg"]);
			}
			
		},
		error: function(request){
			console.log("保存项目基本信息异常");
		}
	});
}

//申请退款
function applicationContent(_this){
	$("#approveAmtA").text(Number($(_this).attr("Amt1")).toFixed(2)+companyCode);
	$("#approveAmtB").text((Number($(_this).attr("Amt1"))+Number($(_this).attr("transFee"))).toFixed(2)+companyCode);
	$("#transFee").text(Number($(_this).attr("transFee")).toFixed(2)+companyCode).attr("amt",$(_this).attr("transFee"));
	$("#approveloanno").text($(_this).attr("loanno"));
	$("#approveloanname").text($(_this).attr("loanname"));
	$("#approveloanusername").text($(_this).attr("loanusername"));
	$("#applicationContent,#verifycode").val("");
	$("#talkDiv,#bgpop").show();
	$(".prompt_close").click(function(){
		$("#talkDiv,#bgpop,#tip_div").hide();
	});
	overFn();
	$("#applicationContentBtn").click(function(){
		if(Valify.isNull($("#applicationContent").val(), "applicationContent",-10,20)){
			if(Valify.isNull($("#verifycode").val(), "verifycode",0,20)){
				$.ajax({
					url: path+"/crowdfunding/updateRefundApplication.html",
					type: "post",
					async: false,
					dataType: "json",
					data: {
						"loanNo":$(_this).attr("loanno"),
						"orderId":$(_this).attr("orderId"),
						"transFee":$(_this).attr("transFee"),
						"applicationContent":$("#applicationContent").val(),
						"verifyCode":$("#verifycode").val()
					},
					success: function(data){
						$("#talkDiv,#bgpop,#tip_div").hide();
						if(data["success"]){
							AlertDialog.tip("申请成功");
							setTimeout(function(){
								window.location.reload();
							},2000);
							$("#alertSure").click(function(){
								window.location.reload();
							});
						}else{
							AlertDialog.tip(data["msg"]);
							return false;
						}
						
					},
					error: function(request){
						console.log("保存项目基本信息异常");
					}
				});
			}
		}
	});
}

//发送验证码
function sendViliCode(){
	if(Valify.isNull($("#applicationContent").val(),"applicationContent",-10,20)){
		$("#sendViliimgBtn").unbind("click");
		$.ajax({
			url: path + "/crowdfunding/sendMobileRefundVerifyCode.html",
			type: "post",
			dataType: "json",
			success: function(data){
				if(data["success"]){
					$("#sendViliimgBtn").unbind("click").css({"cursor":"default","color":"#fff"}).text("60 秒后可重发");
					$("#sendViliimgBtn").css("cursor","default").unbind("click");
					productDown(60, "sendViliimgBtn", overFn);
				}else{
					AlertDialog.tip(data["msg"]);
					return false;
				}
			},
			error: function(request){
				console.log("发送手机验证码异常");
			}
		});
	}
}
function overFn(){
	$("#sendViliimgBtn").text("获取验证码").css({"cursor":"pointer","color":"#222","background-color":"#f7f7f7"});
	$("#sendViliimgBtn").unbind("click").click(sendViliCode);
}
//申请发货
function applyPetition(supportNo){
	$.ajax({
		url: path+"/crowdfunding/updateApplicationDelivery.html",
		type: "post",
		async: false,
		dataType: "json",
		data: {
			"supportNo":supportNo
		},
		success: function(dataA){
			if(dataA["success"]){
				AlertDialog.tip("申请成功");
			}
		},
		error: function(request){
			console.log("保存项目基本信息异常");
		}
	});
}
//回报详情
function returnDetails(_this){
	$(_this).find("i").show()
	$(_this).mouseleave(function(){
		$(_this).find("i").hide();
	});
}


function getCanTransferList(page){
	$.ajax({
		url: path + "/crowdfundProductTransfer/getCanTransferList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanType":"product",
			
			"page": page,
			"rows": 6
			},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			var transferDay=data["transferDay"];
			$("#transferDay").text(transferDay+"天")
			var oArr = [], oStr = '', l, sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				oStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>您还没有相关订单，赶快去支持吧~<br/><a href="'+path+'/common/projectList.html">浏览项目</a></div>';
				$("#supportBody>div").html(oStr).show();
				$("#supportBody").show();
				$("#supportPage").hide();
				return false;
			}
			for(var i=0;i<l;i++){
				oArr.push('<div class="pro_list clearfix">');
				oArr.push('<div class="fl mr30 position">');
				oArr.push('<div class="opacity"></div><div>'+data[i]["loanStateName"]+'</div>');
			    oArr.push('<img src="'+cv.fileAddress+data[i]["loanLogo"]+'" width="132" height="100"/>');
				oArr.push('</div>');
				oArr.push('<div class="fl">');
				oArr.push('<h5>'+data[i]["loanName"]+'</h5>');
				oArr.push('<p class="clearfix pro_det pro_det2">');
				oArr.push('<span>订单编号：<i>'+data[i]["orderId"]+'</i></span>');
				oArr.push('<span>支持金额：<i>'+data[i]["supportAmt"].toFixed(2)+companyCode+'</i></span>');
				oArr.push('<span class="wd182">支持时间：<i>'+data[i]["supportTime"].substring(0,10)+'</i></span>');
				oArr.push('</p>');
				oArr.push('<p class="clearfix pro_det mt8 pro_det2">');
				if(data[i]["supportRatio"]){
					oArr.push('<span>募集进度：<i>'+(data[i]["supportRatio"]*100).toFixed(2)+'%</i></span>');
				}else{
					oArr.push('<span>募集进度：<i>0.00%</i></span>');
				}
				oArr.push('<span>已筹集金额：<i>'+data[i]["approveAmt"].toFixed(2)+companyCode+'</i></span>');
				oArr.push('<span class="wd182">截至时间：<i>'+data[i]["fundEndTime"].substring(0,10)+'</i></span>');
				oArr.push('</p>');
				oArr.push('<p class="clearfix pro_det mt8 pro_det2">');
				oArr.push('<span>发起人：<i>'+data[i]["loanUserName"]+'</i></span>');
				oArr.push('<span>回报详情：<a class="col_blue tx_dec" onClick="returnDetails(this)">查看详情');
				oArr.push('<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["backContent"]+'</em></i>');
//				$.ajax({
//					url: path+"/crowdfunding/getBackSetByBackNo.html",
//					type: "post",
//					async: false,
//					dataType: "json",
//					data: {
//						"backNo":data[i]["backNo"],
//						"loanNo":data[i]["loanNo"]
//					},
//					success: function(dataA){
//						if(dataA["msg"]){
//							oArr.push('<i class="tk_shm tk_shm2" style="display:none;">'+dataA["msg"]["backContent"]+'</i>');
//						}
//						
//					},
//					error: function(request){
//						console.log("保存项目基本信息异常");
//					}
//				});
				
				oArr.push('</a></span>');
				oArr.push('<span class="wd182">抽奖编号：<a class="col_blue tx_dec" onClick="returnDetails(this)">查看');
				$.ajax({
					url: path+"/crowdfunding/getUserPrize.html",
					type: "post",
					async: false,
					dataType: "json",
					data: {
						"loanNo":data[i]["loanNo"],
						"supportUser": siteUserId
					},
					success: function(dataA){
						if(dataA["msg"]){
							oArr.push('<i class="tk_shm tk_shm2" style="display:none;background-size: 100% auto;width: 124px;background-position: top;">'+dataA["msg"]["prizeNo"]+'</i>');
						}else{
							oArr.push('<i class="tk_shm tk_shm2" style="display:none;background-size: 100% auto;width: 124px;background-position: top;">--</i>');
						}
					},
					error: function(request){
						console.log("保存项目基本信息异常");
					}
				});		
				oArr.push('</a></span>');
				oArr.push('</p>');
				oArr.push('<p class="clearfix pro_det mt8 pro_det2">');
				oArr.push('<span>状态：<i>可转让</i></span>');
				if(data[i]["transferDay"]){
					var transferDay = data[i]["transferDay"];
				}else{
					var transferDay = 0;
				}
				oArr.push('<span>操作：<a class="col_blue tx_dec" backContent="'+data[i]["backContent"]+'" transFee="'+data[i]["transFee"]+'" transferDay="'+data[i]["transferDay"]+'" amt="'+data[i]["supportAmt"]+'" user="'+data[i]["supportUser2"]+'" loanName="'+data[i]["loanName"]+'" loanNo="'+data[i]["loanNo"]+'" orderId="'+data[i]["orderId"]+'" supportUserName="'+data[i]["supportUserName"]+'"supportAmt="'+data[i]["supportAmt"]+'" onClick="applyApplication(this)">申请转让</a></span>');
				oArr.push('</p>');
				oArr.push('</div>');
				oArr.push('</div>');
				
			}
			oStr = oArr.join("");
			$("#supportBody>div").html(oStr);
			$("#supportBody").show();
			$("#supportPage").show();
			
			//分页设置
			pagePause = 0;
			if(page < 2){
				$("#supportPage").jPages({
					containerID : "supportBody",
					first:false,
					last:false,
					previous:" ",
					next:" ",
					clickStop   : true,
					perPage	: 6,
					allSumPage : sumPage,
					callback: zrPageData
				});
			}
		},
		error: function(request){
			console.log("获取个人中心信息列表异常");
		}
	});
}
function zrPageData(obj){
	if(pagePause == 0){
		return false;
	}
	getCanTransferList(obj["current"]);
}
function supportPageData(obj){
	if(pagePause == 0){
		return false;
	}
	var code = $("#supportType a.cur").attr("code");
	supportList(code,obj["current"],code);
}
//申请转让
function applyApplication(_this){
	var bl = (cv["winW"]-$("#applyApplicationDiv").width())/2, bt = (cv["winH"]-$("#applyApplicationDiv").height())/2;
	$("#applyApplicationDiv").css({"left":bl+"px", "top":bt+"px"}).show();
	$("#Transfer_AMT").val("");
	$("#Transfer_fee1").text("0.00"+companyCode);
	$("#applicationAmt2").text("0.00"+companyCode);
	$("#applicationVerifycode").val("");
	$("#bgpop").show();
	$("#applyApplicationDiv-none,#bgpop").click(function(){
		$("#applyApplicationDiv,#bgpop,#tip_div").hide();
	});
	$("#applyApplicationDiv").attr("supportNo",$(_this).attr("orderId"));
	$("#applicationDetails").text($(_this).attr("backContent"));
	$("#transFee1").text(Number($(_this).attr("transFee")).toFixed(2)+companyCode).attr("numb",$(_this).attr("transFee"));
	$("#applicationLoan").text($(_this).attr("loanNo"));
	$("#applicationEntryName").text($(_this).attr("loanName"));
	$("#applicationName").text($(_this).attr("supportUserName"));
	$("#applicationSupportAmt").text(Number($(_this).attr("supportAmt")).toFixed(2)+companyCode);
	$("#Transfer_max").text(Number($("#Transfer_max_AMT").attr("amt"))*Number($(_this).attr("supportAmt"))+companyCode);//转让金额上限
	$("#Transfer_min").text(Number($("#Transfer_min_AMT").attr("amt"))*Number($(_this).attr("supportAmt"))+companyCode);//转让金额下限
//	$("#applicationAmt1").text(Number($(_this).attr("amt")).toFixed(2)+companyCode);
//	$("#applicationAmt2").text((Number($(_this).attr("amt"))+Number($(_this).attr("transFee"))).toFixed(2)+companyCode);
	$("#transferDay").text($(_this).attr("transferDay")+"天");
	clearInterval(productTime);
	$("#VerifycodeDiv").html($("#VerifycodeHtml").html());
	overFnA();
	//$("#applicationSendViliimg").unbind("click").click(applicationViliCode);
	$("#applicationBtn").unbind("click").click(function(){
		if(Valify.isNull($("#Transfer_AMT").val(), "Transfer_AMT",0,20)){
			if(Valify.isNull($("#applicationVerifycode").val(), "applicationVerifycode",0,20)){
				$.ajax({
					url: path+"/crowdfundProductTransfer/saveCrowdFundTransfer.html",
					type: "post",
					async: false,
					dataType: "json",
					data: {
						"supportNo":$(_this).attr("orderId"),
						"verifyCode":$("#applicationVerifycode").val(),//手机验证码
						"transferAmt":$("#Transfer_AMT").val(),//转让金额
						"transferFee":$("#Transfer_fee1").attr("amt")//转让手续费
//						"transferAmt":$("#Transfer_AMT").attr("NumT"),//转让金额
//						"transferFee":$("#Transfer_fee1").attr("NumT")//转让手续费
					},
					success: function(data){
						$("#talkDiv,#bgpop,#tip_div,#applyApplicationDiv").hide();
						if(data["success"]){
							AlertDialog.tip("申请成功");
							supportList($("#supportType a.cur").attr("code"),1,$("#supportType a.cur").attr("code"))
						}else{
							AlertDialog.tip(data["msg"]);
						}
						
					},
					error: function(request){
						console.log("保存项目基本信息异常");
					}
				});
			}
		}
	});
}
//发送验证码
function applicationViliCode(){
	//if(Valify.isNull($("#applicationDay").val(),"applicationDay",-10,20)){
		$("#applicationSendViliimg").unbind("click");
		$.ajax({
			url: path + "/crowdfundProductTransfer/sendMobileTransferVerifyCode.html",
			type: "post",
			dataType: "json",
			success: function(data){
				if(data["success"]){
					$("#applicationSendViliimg").unbind("click").css({"cursor":"default","color":"#fff"}).text("60 秒后可重发");
					$("#applicationSendViliimg").css("cursor","default").unbind("click");
					productDown(60, "applicationSendViliimg", overFnA);
				}else{
					AlertDialog.tip(data["msg"]);
				}
			},
			error: function(request){
				console.log("发送手机验证码异常");
			}
		});
	//}
}
function overFnA(){
	$("#applicationSendViliimg").text("获取验证码").css({"cursor":"pointer","color":"#222","background":"#f7f7f7"});
	$("#applicationSendViliimg").unbind("click").click(applicationViliCode);
}

//收藏事件
function attentionLoan(_this){
	var loanNo = $(_this).attr("follow");
	AlertDialog.confirm(DelAttentionLoan, null, "您确定要取消收藏吗？", "确定", "取消", loanNo);
	function DelAttentionLoan(){
		$.ajax({
			url: path + "/crowdfunding/cancelAttention.html",
			type: "post",
			dataType: "json",
			data: {"loanNo": loanNo},
			success: function(data){
				if(data["success"]){
					$(_this).find("div.div2>span").text(Number($(_this).find("div.div2>span").text())-1);
					AlertDialog.tip("取消成功");
					getMyAttentionList(1);
				}
			},
			error: function(request){
				console.log("收藏项目异常");
			}
		});
	}
}

