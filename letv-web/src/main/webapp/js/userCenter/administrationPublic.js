if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
var type = window.location.search.substring(window.location.search.indexOf("type=")+5);
$(function(){
	
	$("#rightProjectList").next().show();
	centerLoanTab(); //个人中心下部标签分页
	var wh = document.documentElement.clientHeight, ww = document.documentElement.clientWidth;
	var pw = (ww - $("#projectDebriefing").width())/2, pt =  (wh - $("#projectDebriefing").height())/2;
	document.getElementById("projectDebriefing").style.top = pt+"px";
	document.getElementById("projectDebriefing").style.left = pw+"px";
	$(".prompt_close").click(function(){
		$(".sbgpop").hide();
		$(".prompt_box").hide();
		AlertDialog.hide();
	});
	myEcreateWebUploader("image_file", "imgheadPhoto", "loan_logo_url", "imgheadLi");//上传头像
});

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
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			$("#blockChainAddress").val(data["user"]["blockChainAddress"]);
			$("#centerLoanTab").find("a.cur").first().click();
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
	
}

function getCenterList(name){
	
	if(name == "support"){
		//supportList(1);
		$("#supportType a").click(function(){
			$("#supportType a").removeClass("cur");
			$(this).addClass("cur"); 
			var code = $(this).attr("code");
			supportList(1, code);
		});
		$("#supportType").find("a.cur").first().click();
	}else if(name == "apply"){
		//applyList(1);
		$("#applyType a").click(function(){
			$("#applyType a").removeClass("cur");
			$(this).addClass("cur");
			var code = $(this).attr("code");
			applyList(1,code);
		});
		$("#applyType").find("a.cur").first().click();
	}else if(name == "collection"){
		getMyAttentionList(1);
	}
}
//我的收藏
function getMyAttentionList(page){
	$.ajax({
		url: path+"/crowdfundUserCenter/getMyAttentionList.html",
		type: "post",
		dataType: "json",
		data:{
			"loanType": "public"
		},
		success: function(data){
			if(!data["success"]){
				console.log("获取可转让列表失败");
				return false;
			}
			sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				eStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>您还没有收藏的项目，赶快去收藏吧~<br/><a href="'+path+'/common/projectList.html">浏览项目</a>'; 
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

//我的申请
function applyList(page,loanState){
	if(loanState){
		var ajaxData = {
			"loanType":"public",
			"loanState": loanState,
			"page": page,
			"rows": 6	
		}
	}else{
		var ajaxData = {
			"loanType":"public",
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
					
					oArr.push('<img src="'+cv.fileAddress+data[i]["loanLogo"]+'" width="132" height="100">');
					oArr.push('</a>');
					oArr.push('</div>');
					oArr.push('<div class="fl">');
//					oArr.push('<a href="'+path+'/common/loanDetail-public.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
					oArr.push('<h5><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a></h5>');
//					oArr.push('</a>');
					oArr.push('<p class="clearfix pro_det pro_det2"><span style="width: 100%;">区块链查询地址：<a target="_blank" href="https://charts.ripple.com/#/graph/">'+data[i]["blockChainAddress"]+'</a></span></p>');
					
					oArr.push('<p class="clearfix pro_det mt8 ">');
					oArr.push('<span>目标金额：<i>'+data[i]["overFundAmt"].toFixed(2)+companyCode+'</i></span>');
					if(data[i]["createTime"]){
						oArr.push('<span>申请时间：<i>'+data[i]["createTime"].substring(0,10)+'</i></span>');
					}else{
						oArr.push('<span>申请时间：<i>--</i></span>');
					}
					if(data[i]["supportRatio"]){
						oArr.push('<span class="wd1">进度：<i>'+(data[i]["supportRatio"]*100).toFixed(2)+'%</i></span>');
					}else{
						oArr.push('<span class="wd1">进度：<i>0.00%</i></span>');
					}
					oArr.push('<span class="wd2">操作：</span>');
					oArr.push('</p>');
					oArr.push('<p class="clearfix pro_det mt8">');
					oArr.push('<span>已募集金额：<i>'+data[i]["approveAmt"].toFixed(2)+companyCode+'</i></span>');
					if(data[i]["fundEndTime"]){
						oArr.push('<span>截至时间：<i>'+data[i]["fundEndTime"].substring(0,10)+'</i></span>');
					}else{
						oArr.push('<span>截至时间：<i>--</i></span>');
					}
					if(data[i]["loanState"] == "add"){
						oArr.push('<span class="wd1">状态：<i>草稿</i></span>');
					}else if(data[i]["loanState"] == "submit"){
						oArr.push('<span class="wd1">状态：<i>已提交</i></span>');
					}else if(data[i]["loanState"] == "preheat"){
						oArr.push('<span class="wd1">状态：<i>预热中</i></span>');
					}else if(data[i]["loanState"] == "funding"){
						oArr.push('<span class="wd1">状态：<i>募集中</i></span>');
					}else if(data[i]["loanState"] == "funded"){
						oArr.push('<span class="wd1">状态：<i>募集结束</i></span>');
					}else if(data[i]["loanState"] == "lended"){
						oArr.push('<span class="wd1">状态：<i>待发货</i></span>');
					}else if(data[i]["loanState"] == "end"){
						oArr.push('<span class="wd1">状态：<i>已发货</i></span>');
					}else{
						oArr.push('<span class="wd1">状态：<i>--</i></span>');
					}
					if(data[i]["loanState"] == "add"){
						oArr.push('<a class="col_blue tx_dec" href="'+path+'/common/enterPubBenefit.html?loanNo='+data[i]["loanNo"]+'">编辑</a>&nbsp;');
						oArr.push('<a class="col_blue tx_dec" onclick="delCaoLoan(event)"  loanName="'+data[i]["loanName"]+'" cloanNo="'+data[i]["loanNo"]+'">删除</a>');
					}else if(data[i]["loanState"] == "submit"){
						oArr.push('<i>--</i>');
					}else if(data[i]["loanState"] == "preheat"){
						oArr.push('<i>--</i>');
					}else if(data[i]["loanState"] == "funding"){
						oArr.push('<a class="col_blue tx_dec" onclick="projectDebriefing(event)" cloanNo="'+data[i]["loanNo"]+'">录入项目进展</a>');
					}else if(data[i]["loanState"] == "funded"){
						oArr.push('<i>--</i>');
					}else if(data[i]["loanState"] == "lended"){
						oArr.push('<a class="wd2 col_blue tx_dec" href="'+path+'/common/enterLogicoPublic.html?loanNo='+data[i]["loanNo"]+'&tcode=lended&ctype=3">发货</a>');
					}else if(data[i]["loanState"] == "end"){
						oArr.push('<a class="wd2 col_blue tx_dec" href="'+path+'/common/enterLogicoPublic.html?loanNo='+data[i]["loanNo"]+'&tcode=end&ctype=3">发货明细</a>');
					}else{
						oArr.push('<i>--</i>');
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

//我的支持
function supportList(page,loanState){
	var dataStr;
	if(loanState == "noPay" || loanState == "payed"){
		dataStr = {
				"loanType":"public",
				"payState": loanState,
				"requestRemark":"investor",
				"page": page,
				"rows": 6
		};
	}else{
		dataStr = {
				"loanType":"public",
				"mySupport": loanState,
				"requestRemark":"investor",
				"page": page,
				"rows": 6
		};
	}
	$.ajax({
//		url: path + "/crowdfundUserCenter/getMyInterviewList.html",
		url: path + "/crowdfundUserCenter/getMySupportList.html",
		type: "post",
		dataType: "json",
		data: dataStr,
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
					
					oArr.push('<img src="'+cv.fileAddress+data[i]["loanLogo"]+'" width="132" height="100">');
					oArr.push('</a>');
					oArr.push('</div>');
					oArr.push('<div class="fl">');
					oArr.push('<h5><span class="fr">发起人：<em>'+data[i]["loanUserName"]+'</em></span><a target="_blank" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a></h5>');
					oArr.push('<p class="clearfix pro_det pro_det2"><span style="width: 100%;">区块链查询地址：<a target="_blank" href="https://charts.ripple.com/#/graph/">'+data[i]["blockChainAddress"]+'</a></span></p>');
					
					oArr.push('<p class="clearfix pro_det mt8 pro_det2">');
					
					oArr.push('<span>订单编号：<i>'+data[i]["orderId"]+'</i></span>');
					oArr.push('<span >支持金额：<i>'+data[i]["supportAmt"].toFixed(2)+companyCode+'</i></span>');
					if(data[i]["payState"] == "noPay"){
						oArr.push('<span class="wd182">状态：<i>待支付</i></span>');
					}else if(data[i]["payState"] == "payed"){
						oArr.push('<span class="wd182">状态：<i>已支付</i></span>');
					}else if(data[i]["state"] == "lended"){
						oArr.push('<span class="wd182">状态：<i>待发货</i></span>');
					}else if(data[i]["state"] == "end"){
						oArr.push('<span class="wd182">状态：<i>已发货</i></span>');
					}else{
						oArr.push('<span class="wd182">状态：<i>--</i></span>');
					}
					
					oArr.push('</p>');
					oArr.push('<p class="clearfix pro_det mt8 pro_det2">');
					
					if(data[i]["supportRatio"]){
						oArr.push('<span>募集进度：<i>'+(data[i]["supportRatio"]*100).toFixed(2)+'%</i></span>');
					}else{
						oArr.push('<span>募集进度：<i>0.00%</i></span>');
					}
					oArr.push('<span>支持时间：<i>'+data[i]["supportTime"].substring(0,10)+'</i></span>');
					oArr.push('<span class="wd182">回报：<a class="col_blue tx_dec" onClick="returnDetails(this)">查看详情<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["backContent"]+'</em></i></a></span>');
					
					oArr.push('</p>');
					oArr.push('<p class="clearfix pro_det mt8 pro_det2">');
					oArr.push('<span>已筹集金额：<i>'+data[i]["approveAmt"].toFixed(2)+companyCode+'</i></span>');
					if(data[i]["fundEndTime"]){
						oArr.push('<span>截至时间：<i>'+data[i]["fundEndTime"].substring(0,10)+'</i></span>');
					}else{
						oArr.push('<span>截至时间：<i>--</i></span>');
					}
					oArr.push('<span class="wd182">操作：');
					if(data[i]["payState"] == "noPay"){
						if(data[i]["loanState"] == "funding"){
							oArr.push('<a class="col_blue tx_dec" href="'+path+'/common/support-pubBenefit.html?loanNo='+data[i]["loanNo"]+'&backNo='+data[i]["backNo"]+'">继续支付</a>');
						}else{
							oArr.push('<i>交易结束</i>');
						}
					}else if(data[i]["payState"] == "payed"){
						if(loanState == "sended"){
							oArr.push('<a orderId="'+data[i]["orderId"]+'" onClick="showDeliveryInfo(this)" class="col_blue tx_dec">查看发货明细</a>');
				        }else{
				        	if(data[i]["state"] == "end" || data[i]["state"] == "sending"){
				        		oArr.push('<a orderId="'+data[i]["orderId"]+'" onClick="showDeliveryInfo(this)" class="col_blue tx_dec">查看发货明细</a>');
						    }else{
						    	oArr.push('<a class="col_blue tx_dec" href="'+path+'/common/loanDetail-public.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">查看详情</a>');
							}
						}
					}else{
						oArr.push('<i>--</i>');
					}
					oArr.push('</span>');
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

//回报详情
function returnDetails(_this){
	$(_this).find("i").show()
	$(_this).mouseleave(function(){
		$(_this).find("i").hide();
	});
}


function supportPageData(obj){
	if(pagePause == 0){
		return false;
	}
	var code = $("#supportType a.cur").attr("code");
	if(code == "payed" || code == "noPay"){
		supportList(obj["current"],code);
	}else if(code == "lended" || code == "end"){
		supportList(obj["current"],"",code);
	}else if(code == "auditing" || code == "passed" || code == "refuse"){
		supportList(obj["current"],"","",code);
	}else if(code == "0"){
		supportList(obj["current"],"","","",code);
	}else{
		supportList(obj["current"]);
	}

//	if(code == "payed" || code == "noPay"){
//		supportList(obj["current"],code);
//	}else if(code == "lended" || code == "end"){
//		supportList(obj["current"],"",code);
//	}else if(code == "auditing" || code == "passed" || code == "refuse"){
//		supportList(obj["current"],"","",code);
//	}else if(code == "0"){
//		supportList(obj["current"],"","","",code);
//	}
	supportList(obj["current"],code);
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
				$("a[cloanno="+loanNo+"]").parent().parent().parent().remove();
			}
		},
		error: function(request){
			console.log("删除草稿项目异常");
		}
	});
}
//录入项目进展
function projectDebriefing(_this){
	$("#timeNode,#enterContent,#orgLoanReceiveProve").val("");
	$("#uploadInFo").html("");
	$("#progressUl").html($("#progressHtml").html());
	$("#projectDebriefing,.sbgpop").show();
	$("#projectDebriefing-none").click(function(){
		$("#projectDebriefing,.sbgpop,#tip_div").hide();
	});
	var obj = event.srcElement || event.target;
	var loanNo = $(obj).attr("cloanNo");
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
				if(Valify.isNull($("#orgLoanReceiveProve").val(), "changeUpload", 10, 30)){
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
				}
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
				}
//	        	else{
//					AlertDialog.RZmtip(data.msg);
//				}
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
