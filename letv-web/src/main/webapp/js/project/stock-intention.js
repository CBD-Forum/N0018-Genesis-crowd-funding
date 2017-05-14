if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}
var loanNo = getQueryString("loanNo");
var loanState = getQueryString("loanState");
$(function(){
	getCrowdDetail();
//	getDeployMap();
	skdjf();
	$("#wen1").hover(function(){
		$("#wen1jsh").toggle();
	});
	if(loanState == "preheat"){//预热-领投
	}else if(loanState == "funding"){//筹款-跟投
		$("#ltrInfo").addClass("ltr_info");
		$("#tzr").show();
	}
	$("#chyBtu").attr("href",path+"/common/stock-pay.html?loanNo="+loanNo+"&loanState="+loanState);
	$("#confirmBtu").click(saveSupport);
});
//保存用户投资记录
function saveSupport(){
	if($("#fenshuNum1").val()<1){
		AlertDialog.tip("投资份数不能为0");
		return false;
	}
	if(!$("#ltrList>dd.a_home").attr("id")){
		AlertDialog.tip("请选择领投人");
		return false;
	}
	var url, dataStr;
	if(loanState == "preheat"){//预热-领投
		url = path + "/letvPay/invest/saveLendSupport.html" ,
		dataStr = {
			"loanNo" : loanNo,
			"deviceType" : "PC",
			"buyNum" : $("#fenshuNum1").val(),
			"supportAmt" : $("#fundAmt5").attr("num"),
			"intentionAmt" : $("#fundAmt5").attr("num")*($("#depositRatio").text()/100)
		};
	}else if(loanState == "funding"){//筹款-跟投
		url = path + "/letvPay/invest/saveFollowSupport.html" ,
		dataStr = {
			"loanNo" : loanNo,
			"deviceType" : "PC",
			"intentionFlag" : "1",
			"leadInvestor" : $("#ltrList>dd.a_home").attr("id"),
			"buyNum" : $("#fenshuNum1").val(),
			"supportAmt" : $("#fundAmt5").attr("num"),
			"intentionAmt" : $("#fundAmt5").attr("num")*($("#depositRatio").text()/100)
		};
	}
	$.ajax({
		url: url ,
		type: "post",
		dataType: "json",
		data: dataStr,
		success: function(data){
			if(data["success"]){
				 window.location.href=path+"/common/stock-intention-confirm.html?loanNo="+loanNo+"&loanState="+loanState+"&orderId="+data["msg"]+"&ltrId="+$("#ltrList>dd.a_home").attr("id");
			}else{
				return false;
			}
		},
		error: function(request){
			console.log("保存用户投资记录异常");
		}
	});
}
function skdjf(){
	$("#fenshuNum1").blur(function(){
		if(Number($(this).val()) >= Number($("#remainsFenshu1").attr("num"))){
			$(this).val($("#remainsFenshu1").attr("num"));
			$("#fundAmt5").text((Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(2)).attr("num", (Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(2)) ;
			//投资总额 / (融资总额 - 项目方出资) * 出让股份
			var couInvestZgbl1 = Number($("#fundAmt5").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))
			$("#couInvestZgbl1").text(Math.floor(couInvestZgbl1 * 100) / 100);
			$("#depositPay").text(Number($("#fundAmt5").attr("num")) * Number($("#depositRatio").text()));//支付定金
			$("#remainsFenshu1").text(Number($("#remainsFenshu1").attr("num")) - Number($("#fenshuNum1").val()));//剩余份数
		}else{
			$("#remainsFenshu1").text(Number($("#remainsFenshu1").attr("num")) - Number($("#fenshuNum1").val()));//剩余份数
			$("#fundAmt5").text((Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(2)).attr("num", Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())) ;
			//投资总额 / (融资总额 - 项目方出资) * 出让股份
			var projectRental = Number($("#fundAmt5").attr("num")).toFixed(2);
			var dollars  = Number($("#fundAmt1").attr("num"));
			var contribution = Number($("#projectFinanceAmt").attr("num"));
			var share = Number($("#qtyxhhr_zgbl").attr("all"))/100;
			var couInvestZgbl1 = ((projectRental/(dollars-contribution))*share)*100
			$("#couInvestZgbl1").text(Math.floor(couInvestZgbl1 * 100) / 100);	
			$("#depositPay").text((Number($("#fundAmt5").attr("num")) * Number($("#depositRatio").text()/100)).toFixed(2));//支付定金
			$("#retainage").text((Number($("#fundAmt5").attr("num")) - Number($("#depositPay").text())).toFixed(2));//需支付尾款
			$("#income1").text((Number($("#income1").attr("num"))*Number($("#depositPay").text())).toFixed(2));//订金支付阶段预计每日收益			
			$("#income2").text((Number($("#income1").attr("num"))*Number($("#fundAmt5").attr("num"))).toFixed(2));//尾款补齐后预计每日收益
		}
	});
	//加一份
	$("#iJIa1").unbind("click").click(function(){
		var fsNum = Number($("#remainsFenshu1").text()), jNum = 0;
		if(Number($("#remainsFenshu1").text())==0){ //超出剩余份数
			$("#iJIa1").css("color", "#ccc");
			AlertDialog.tip("已经被抢购一空，没有剩余了哦!");
			return false;
		}else{
			jNum = Number($("#fenshuNum1").val()) + 1;
			$("#fenshuNum1").val(jNum);
			$("#fundAmt5").text((Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(2)).attr("num", (Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(2)) ;
			$("#remainsFenshu1").text(Number($("#allFenshu1").text()) - Number($("#buyFenshu1").text()) - Number($("#fenshuNum1").val()));//剩余份数
			//投资总额 / (融资总额 - 项目方出资) * 出让股份
			var projectRental = Number($("#fundAmt5").attr("num")).toFixed(2);
			var dollars  = Number($("#fundAmt1").attr("num"));
			var contribution = Number($("#projectFinanceAmt").attr("num"));
			var share = Number($("#qtyxhhr_zgbl").attr("all"))/100;
			var couInvestZgbl1 = ((projectRental/(dollars-contribution))*share)*100
			$("#couInvestZgbl1").text(Math.floor(couInvestZgbl1 * 100) / 100);	
			$("#depositPay").text((Number($("#fundAmt5").attr("num")) * Number($("#depositRatio").text()/100)).toFixed(2));//支付定金
			$("#retainage").text((Number($("#fundAmt5").attr("num")) - Number($("#depositPay").text())).toFixed(2));//需支付尾款
			$("#income1").text((Number($("#income1").attr("num"))*Number($("#depositPay").text())).toFixed(2));//订金支付阶段预计每日收益			
			$("#income2").text((Number($("#income1").attr("num"))*Number($("#fundAmt5").attr("num"))).toFixed(2));//尾款补齐后预计每日收益
		}
	});
	//减一份
	$("#iJian1").unbind("click").click(function(){
		var fsNum = Number($("#remainsFenshu1").text()), jNum = 0;
		if(Number($("#fenshuNum1").val()) <= 0){ //输入框内只剩0份
			AlertDialog.tip("已经空空如也，不能再减少了哦!");
			$("#iJian1").css("color", "#CCC");
			return false;
		}else{
			jNum = Number($("#fenshuNum1").val()) - 1;
			$("#fenshuNum1").val(jNum);
			$("#fundAmt5").text((Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(2)).attr("num", (Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(2)) ;
			$("#remainsFenshu1").text(Number($("#allFenshu1").text()) - Number($("#buyFenshu1").text()) - Number($("#fenshuNum1").val()));//剩余份数
			//投资总额 / (融资总额 - 项目方出资) * 出让股份
			var projectRental = Number($("#fundAmt5").attr("num"));
			var dollars  = Number($("#fundAmt1").attr("num"));
			var contribution = Number($("#projectFinanceAmt").attr("num"));
			var share = Number($("#qtyxhhr_zgbl").attr("all"))/100;
			var couInvestZgbl1 = ((projectRental/(dollars-contribution))*share)*100
			$("#couInvestZgbl1").text(Math.floor(couInvestZgbl1 * 100) / 100);	
			$("#depositPay").text((Number($("#fundAmt5").attr("num")) * Number($("#depositRatio").text()/100)).toFixed(2));//支付定金
			$("#retainage").text((Number($("#fundAmt5").attr("num")) - Number($("#depositPay").text())).toFixed(2));//需支付尾款
			$("#income1").text((Number($("#income1").attr("num"))*Number($("#depositPay").text())).toFixed(2));//订金支付阶段预计每日收益			
			$("#income2").text((Number($("#income1").attr("num"))*Number($("#fundAmt5").attr("num"))).toFixed(2));//尾款补齐后预计每日收益
		}
	});

	var ltArr = [], ltStr = '', l; //加载领投人列表
	//获取加载领投人列表
	$.ajax({
		url: path + "/crowdfundingInvest/getLeader.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"].length, data = data["msg"];
			if(l == 0){
				$("#investLBtn").hide();
				$("#gtInvestBtn").show();
			}else{
				for(var i=0;i<l;i++){
					if(i == 0){
						ltArr.push('<dd class="a_home" id="'+data[i]["leadInvestor"]+'">');
					}else{
						ltArr.push('<dd id="'+data[i]["leadInvestor"]+'">');
					}
					ltArr.push('<a href="javascript:void(0);" class="clearfix">');
					if(data[i]["photo"]){
						ltArr.push('<p class="p1"><img src="'+cv["fileAddress"]+'/'+data[i]["photo"]+'" /></p>');
					}else{
						ltArr.push('<p class="p1"><img src="'+path+'/images/defaultPhoto-72.png" /></p>');
					}
					ltArr.push('<div style="float:right;width:200px;">');
					if(data[i]["nickName"]){
						ltArr.push('<p class="p2">'+data[i]["nickName"]+'</p>');
					}else{
						ltArr.push('<p class="p2">--</p>');
					}
					ltArr.push('<p class="p3">认投金额:<span>'+data[i]["totalSupportAmt"].toFixed(2)+'</span> '+companyCode+'</p>');
					ltArr.push('</div>');
					ltArr.push('</a></dd>');
				}
				ltStr = ltArr.join("");
				$("#ltrList").html(ltStr);
				$("#ltrList dd").click(function(){
					$("#ltrList dd").removeClass("a_home");
					$(this).addClass("a_home");
				});
			};
		},
		error: function(request){
			console.log("获取领头人列表异常！");
		}
	});
}

//获取项目详情信息
function getCrowdDetail(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getAccountInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			$("#balance").text(data["balance"].toFixed(2)) ; //可用余额
		},error: function(request){
			console.log("获取个人信息异常");
		}
	});
	$.ajax({
		url: path + "/crowdfunding/getCrowdDetail.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			if(data["loanState"] == "preheat"){
				$(".bottom_btn,#tzr").hide()
			}else{
				$(".bottom_btn,#tzr").show()
			}
			$("#loanName").text(data["loanName"]); //项目名称
			$("#completeTime").text(data["investEndTime"].substring(0,10)+" 23:59:59");
			$("#allFenshu1").text(data["stockNum"]);//共份数
			$("#buyFenshu1").text(data["buyNum"]);//已购买份数
			$("#remainsFenshu1").text(Number($("#allFenshu1").text()) - Number($("#buyFenshu1").text())).attr("num",Number($("#allFenshu1").text()) - Number($("#buyFenshu1").text()));//剩余份数
			if(data["stockPartAmt"]){
				$("#miniInvestAmt").text(data["stockPartAmt"]/10000+"万").attr("num", data["stockPartAmt"]);
				$("#miniInvestAmt1").text(data["stockPartAmt"].toFixed(2)).attr("num", data["stockPartAmt"].toFixed(2));
				$("#miniInvestAmt2").text(data["stockPartAmt"].toFixed(2)).attr("num", data["stockPartAmt"].toFixed(2));
//				$("#fundAmt5").text(data["stockPartAmt"].toFixed(2)).attr("num", data["stockPartAmt"]);
				$("#fundAmt5").text("0.00").attr("num", "0.00");
			}else{
				$("#miniInvestAmt").text("0.00").attr("num", "0.00");
				$("#miniInvestAmt1").text("0.00").attr("num", "0.00");
				$("#miniInvestAmt2").text("0.00").attr("num", "0.00");
				$("#fundAmt5").text("0.00").attr("num", "0.00");
			}

			$("#depositRatio").text(data["yxjPayScale"]*100);//意向金支付比例
			$("#yxjPlatformRatio").text(data["yxjPlatformRatio"]*100);//平台收取违约意向金比例
			
			$("#depositPay").text((Number($("#fundAmt5").attr("num")) * Number($("#depositRatio").text()/100)).toFixed(2));//支付定金
			$("#retainage").text((Number($("#fundAmt5").attr("num")) - Number($("#depositPay").text())).toFixed(2));//需支付尾款

			$("#income1").attr("num",data["dailyProfitRate"]);//每日收益率	
			$("#income1").text((Number(data["dailyProfitRate"])*Number($("#depositPay").text())).toFixed(2));//订金支付阶段预计每日收益			
			$("#income2").text((Number(data["dailyProfitRate"])*Number($("#fundAmt5").attr("num"))).toFixed(2));//尾款补齐后预计每日收益
			
			$("#yxjPayScaleA").text(data["yxjPayScale"]*100+"%"); 
			$("#yxjPlatformRatioA").text(data["yxjPlatformRatio"]*100+"%"); 
			
		},
		error: function(request){
			console.log("获取股权详细信息异常");
		}
	});
}

//获取项目详情信息
function getDeployMap(){
	$.ajax({
		url: path + "/crowdfunding/getDeployMap.html",
		type: "post",
		dataType: "json",
		data: {},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			
//			$("#loanName").text(data["loanName"]); //项目名称
			$("#depositRatio").text(data["yxjPayScale"]*100);//意向金支付比例
			$("#yxjPlatformRatio").text(data["yxjPlatformRatio"]*100);//平台收取违约意向金比例
//			$("#remainsFenshu1").text(Number($("#allFenshu1").text()) - Number($("#buyFenshu1").text()));//剩余份数
			
		},
		error: function(request){
			console.log("获取股权详细信息异常");
		}
	});
}