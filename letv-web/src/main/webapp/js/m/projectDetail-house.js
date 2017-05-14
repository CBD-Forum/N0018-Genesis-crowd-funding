var loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7,window.location.search.length);
var recordNum = 1;//记录 预约，认购记录的页数
var isProjectPay = 0; // 0=非高端 ，>0高端
var isTzr = 0;  //是认证投资人
var page = 1;//认购记录页数
var authStr = 0; //项目权限

$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	if(userId == "null"){ //未登录
//		$("#indexHw").html('<a href="'+path+'/common/m-login.html">登录</a>&nbsp;&nbsp;/&nbsp;&nbsp;<a href="'+path+'/common/m-register.html">注册</a>');
		window.location.href = path + "/common/m-index.html";
	}else{
		if(userPhoto == "null" || userPhoto == null || userPhoto == ""){
			$("#indexHw").html('<a href="'+path+'/common/m-myCenter.html"><img src="'+path+'/images/defaultPhoto.png" style="width:35px; height:35px;border-radius: 50%;" /></a>');
		}else{
			$("#indexHw").html('<a href="'+path+'/common/m-myCenter.html"><img src="'+cv["fileAddress"]+userPhoto+'" style="width:35px; height:35px;border-radius: 50%;" /></a>');
		}
	}
	getCrowdDetail();//项目详情信息
	showProEvolve();//获取项目进度
	getSupportList();
	showProComment();//获取评论
	$("#subComment").click(submitComment);//发表评论
	
	var $tab = $("#detailTab li");
	$.each($tab,function(k,v){
		$(v).click(function(){
			$tab.find("a").removeClass('cur');
			$(v).find("a").addClass('cur');
			$("#detailBody>div").hide();
			var name = $(v).attr("name");
			$("#"+name+"Body").show();
		});
	});
	$tab.first().click();
	getUserInfo(); //获取个人信息
//	$("#investFBtn").click(checkBindBankCard);
	
	$("#alertSure,#comClse").click(function(){
		
	});
});
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
			if(data["userLevel"] == "common"){
				isTzr = 0;
			}else{
				isTzr = 1;
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//获取项目详情信息
function getCrowdDetail(){
	var remainDays = 0;
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
			
			//控制页面内，根据项目状态显示
//			authStr = data["authSet"]?parseInt(data["authSet"]):0;
//			$("authStrInput").val(authStr);
//			if(parseInt(investorLevel) < authStr){
//				window.location.href = path + "/common/m-index.html" ;
//				return false;
//			}
			
			isProjectPay = data["projectFinanceAmt"];
			if(isProjectPay > 0){
				$("#crgfTitle").hide();
				$("#bonusesDiv").hide();
				$("#bonusesDiv2").hide();
			}else{
				$("#bonusesDiv").show();
				$("#bonusesDiv2").show();
			}
			if(data["loanState"] == "preheat"){ //预热中
				$("#hasSprotTtitle").text("目前已预购金额");
				if(data["approveAmt"]){
//					var temp = data["approveAmt"].replace(/,/g,"");
					$("#approveAmt").text((Number(data["approveAmt"]) / 10000).toFixed(2));
				}else{
					$("#approveAmt").text("0.00");
				}
				$("#loanStateName").text("预热中");
				$("#xs_jd").remove();
				$("#buyNum1Span").remove();
				$("#buttonDiv").html('<a href="javascript:void(0);" id="investFirstBtn">我要领投</a><a href="javascript:void(0);" id="wantPreSupportBtn">我要预约</a>');
				$("#investFirstBtn").click(investFirst); //领投
				$("#wantPreSupportBtn").click(wantPreSupport);//预约
			}else if(data["loanState"] == "funding"){ //筹款中
				if(data["approveAmt"]){
					var temp = data["approveAmt"].replace(/,/g,"");
					$("#approveAmt").text((Number(temp) / 10000).toFixed(2));
				}else{
					$("#approveAmt").text("0.00");
				}
				$("#loanStateName").text("融资中");
				$("#buttonDiv").html('<a class="xs_bg2" href="'+path+'/common/m-withVote.html?loanNo='+data["loanNo"]+'">我要投资</a>');
				if(parseFloat(isProjectPay) == 0){
					$("#buttonDiv").append('<a class="fr back2" href="javascript:void(0);" id="wantPreSupportBtn">我要预约</a>');
					$("#wantPreSupportBtn").click(wantPreSupport);//预约
				}
				$("#investLastBtn").click(investLast); //认购，跟投
			}else if(data["loanState"] == "funded" || data["loanState"] == "lended" || data["loanState"] == "end"){ //筹款完成
//				if(data["approveAmt"]){
//					var temp = data["approveAmt"].replace(/,/g,"");
//					$("#approveAmt").text((Number(temp)/ 10000).toFixed(2));
//				}else{
//					$("#approveAmt").text("0.00");
//				}
				$("#approveAmt").text(data["approveAmt"]);
				$("#loanStateName").text(data["loanStateName"]);
				$("#talkAboutBtn").css("background", "#CCC").unbind("click");
				$("#buttonDiv").html('<a style="background:#ccc;" href="javascript:void(0);">我要投资</a>');
				$("#investLastBtn").css("background", "#CCC").unbind("click");
				$("#state2RecordTit").text("领投记录");
			}else{
				$("#loanStateName").text(data["loanStateName"]);
				$("#talkAboutBtn").css("background", "#CCC").unbind("click");
				$("#buttonDiv").html('<a class="xs_bg2" href="'+path+'/common/m-withVote.html?loanNo='+data["loanNo"]+'">我要投资</a>');
				$("#investLastBtn").css("background", "#CCC").unbind("click");
				$("#attentionBtn").unbind("click");
			}
			$("#loanName").text(data["loanName"]); //项目名称
			$("#loanPhoto").attr("src", cv["fileAddress"] + data["loanLogo"]).attr("title", data["loanName"]).attr("alt", data["loanName"]); //项目封面
			$("#yLoanuser").val(data["loanUser"]);
			$("#loanUserName").text(data["loanUser2"]);
			$("#loanUserName1").text(data["loanUserName"]);
			
			$("#loanDetail").text(data["loanDetail"]); //项目详情
			$("#loanDes").text(data["loanDes"]); //项目简介
			if(data["loanVideo"]){ //如果上传了视频
				$("#xs_video").html('<iframe height="450px" width="100%" src="'+data["loanVideo"]+'" frameborder=0 allowfullscreen></iframe>'); //视频
				$("#xs_video").show();
			}else{
				$("#xs_video").hide();
			}
			
			if(data["loanState"] == "funded" || data["loanState"] == "lended" || data["loanState"] == "end"){//筹款结束
				$("#remainDay").text(0);
			}else{
				if(data["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
					remainDays = data["remainPreheatDays"] ? (data["remainPreheatDays"] < 0 ? "0" : data["remainPreheatDays"]) : "0" ;
				}else{
					remainDays = data["remainDays"] ? (data["remainDays"] < 0 ? "0" : data["remainDays"]) : "0" ;
				}
				$("#remainDay").text(remainDays);
			}
			$("#fundAmt").text(formatCurrency(data["fundAmt"])).attr("num", data["fundAmt"]);
			var fundAmt = (data["fundAmt"] - data["projectFinanceAmt"])/10000;
			$("#fundAmt1").text("￥"+(data["fundAmt"]/10000).toFixed(2)).attr("num", data["fundAmt"]);
			$("#fundAmt2").text(formatCurrency(data["fundAmt"].toFixed(2)));
			if(data["loanNo"] == '2015101230822759' || data["loanNo"] == '2015101239688157'){
				$("#supportRatio1").text("100%");
				$("#pBar").css("width", "100%");
			}else{
				$("#supportRatio1").text((data["supportRatio"]*100).toFixed(2)+"%");
				if((data["supportRatio"]*100) >100){
					$("#pBar").css("width", "100%");
				}else{
					$("#pBar").css("width", (data["supportRatio"]*100).toFixed(2)+"%");
				}
				
			}
			
			if(data["projectFinanceAmt"] > 0){ //项目方出资为0，这一行不显示
//				$("#projectFinanceAmt").parent().next().removeClass("fr").addClass("fl");
				$("#crgfTitle").remove();
			}
			$("#financeNum").val(data["financeNum"]);
			if(data["release_time"]){
				$("#release_time").text(data["releaseTime"].substring(0, 10)); //发起时间
			}
			if(data["miniInvestAmt"]){
				$("#miniInvestAmt").text(data["miniInvestAmt"]/10000+"万").attr("num", data["miniInvestAmt"]);
				$("#miniInvestAmt1").text(data["miniInvestAmt"].toFixed(0)).attr("num", data["miniInvestAmt"]);
				$("#miniInvestAmt2").text(data["miniInvestAmt"].toFixed(0)).attr("num", data["miniInvestAmt"]);
				$("#fundAmt5").text(data["miniInvestAmt"].toFixed(0)).attr("num", data["miniInvestAmt"]);
			}else{
				$("#miniInvestAmt").text(0).attr("num", 0);
				$("#miniInvestAmt1").text(0).attr("num", 0);
				$("#miniInvestAmt2").text(0).attr("num", 0);
				$("#fundAmt5").text(0).attr("num", 0);
			}
			$("#superIndustry").text(data["superIndustryName"]); //行业
			$("#loanNum").text(data["loanNum"]); //开店数
			$("#buyNum").text(data["buyNum"]); //认购数
			$("#buyNum1").val(data["buyNum"]); //认购数
			$("#remainNum").text(Number($("#financeNum").val()) - Number($("#buyNum1").val())); //剩余份数
			$("#interviewNum").text(data["interviewNum"]); // 约谈人数
			$("#attentionNum").text(data["attentionNum"]);//关注数
			$("#address").text(data["loanProvinceName"] + data["loanCityName"]);
			$("#proInfoContent").html(data["loanDetail"]);//项目介绍
			$("#proBudgetContent").html(data["financeBudget"]?data["financeBudget"]:"<div class='nodata'>暂无数据</div>"); //项目需知
			$("#qtyxhhr_zgbl").text((data["investBonusRatio"]*100).toFixed(2) + "%").attr("num", (data["investBonusRatio"]*100).toFixed(2)).attr("all", (data["investBonusRatio"]*100).toFixed(2)); //投资人（其他有限合伙人）份额占股比例
		},
		error: function(request){
			console.log("获取股权详细信息异常");
		}
	});
}

//获取项目进展
function showProEvolve(){
	var pArr = [], pStr = ''; //项目进展初始化变量
	$.ajax({
		url: path + "/crowdfunding/getProgessList.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			var l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				pStr = '<span class="nodata">暂无数据</span>';
				$("#loanProgress").html(pStr);
				return false;
			}
			for(var i=0;i<l;i++){
				pArr.push('<li class="cur"><i></i>');
				pArr.push('<div class="jz-list">');
				pArr.push('<p class="tit"><span class="fs12">'+data[i]["enterTime"].substring(0,10)+'</span></p>');
				pArr.push('<p class="fs12">'+data[i]["enterContent"]+'</p>');
				pArr.push('</div>');
				pArr.push('</li>');
			}
			//展示项目进展
			pStr = pArr.join("");
			$("#loanProgress").html(pStr);
		},
		error: function(request){
			console.log("获取项目进展异常");
		}
	});
}

//发表评论
function submitComment(){
	if(userId == "null" ||userId == null){
		AlertDialog.tip("请登录后发表评论！");
		return false;
	}
	if($("#comVal").val().length < 5){
		AlertDialog.tip("评论不能少于5个字哦！");
		return false;
	}
	$.ajax({
		url: path + "/crowdfunding/submitComment.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo": loanNo,
			"content": $("#comVal").val()
		},
		success: function(data){
			if(!data["success"]){
				AlertDialog.tip(data["msg"]);
				return false;
			}else{
				AlertDialog.tip("发表成功！");
				$.ajax({
					url: path + "/crowdfunding/getCommentList.html",
					type: "post",
					dataType: "json",
					data: {
						"loanNo": loanNo
					},
					success:function(data){
						$("#comVal").val("");
						showProComment();
					},
					error: function(request){
						console.log("获取评论异常");
					}
				});
			}
		},
		error: function(request){
			console.log("发表评论异常");
		}
	});
}
//显示评论
function showProComment(){
	var pArr = [], pStr = '', l = 0, list;
	$.ajax({
		url: path + "/crowdfunding/getCommentList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo": loanNo
		},
		success:function(data){
			if(!data["success"]){
				$("#proCommentBody ul").html('<div class="nodata">暂无数据</div>');
				return false;
			}
			l = data["msg"]["rows"].length,list = data["msg"]["rows"];
			if( l == 0){
				$("#proCommentBody ul").html('<div class="nodata">暂无数据</div>');
				return false;
			}
			for(var i=0;i<l;i++){
				pArr.push('<li class="clearfix">');
				if(list[i]["photo"]){
					pArr.push('<div class="img-div"><img src="'+cv["fileAddress"]+list[i]["photo"]+'"/></div>');
				}else{
					pArr.push('<div class="img-div"><img src="'+path+'/images/defaultPhoto.png"/></div>');
				}
				pArr.push('<div class="font-div">');
				var username = list[i]["discussUser"].substring(0,2)+"***"+list[i]["discussUser"].substring(list[i]["discussUser"].length-1,list[i]["discussUser"].length);
				pArr.push('<p class="user">用户'+username+'</p>');
				pArr.push('<p>'+list[i]["content"]+'</p>');
				pArr.push('<div class="z-pl clearfix fs12">');
				pArr.push('<p class="fl">'+list[i]["differDays"]+'天前</p>');
				pArr.push('</div>');
				pArr.push('</div>');
				pArr.push('</li>');
			}
			pStr = pArr.join('');
			$("#proCommentBody ul").html(pStr);
		},
		error: function(request){
			console.log("获取评论异常");
		}
	});
}

//我要领投
function investFirst(){
	if(userId == "null"){
		go2Login();
		return false;
	}
	if($("#yLoanuser").val() == userId){
		AlertDialog.tip("您不能领投自己的项目");
		return false;
	}
	$.ajax({
		url: path + "/crowdfundUserCenter/getCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			if(data["userLevel"] == "lead"){
				$("#bgpop").show();
				var it = (cv["winH"]-410)/2, il = (cv["winW"]-455)/2;
				$("#investFirstDiv").css({"top": it+"px", "left": "3%"}).show();
				//关闭
				$("#bgpop").click(function(){
					if($("#tip_div").attr("id")){
						$("#tip_div").hide();
					}
					$("#bgpop").hide();
					$("#investFirstDiv").hide();
				});
				$("#remainsFenshu1").text(Number($("#financeNum").val()) - Number($("#buyNum1").val()));//剩余份数
				$("#supFomr").find("input[name='followInvest']").remove();
				//加一份
				$("#iJIa1").unbind("click").click(function(){
					var fsNum = Number($("#remainsFenshu1").text()), jNum = 0;
					if(Number($("#fenshuNum1").val()) > fsNum || Number($("#fenshuNum1").val()) == fsNum){ //超出剩余份数
						$("#iJIa1").css("color", "#ccc");
						return false;
					}else{
						jNum = Number($("#fenshuNum1").val()) + 1;
						$("#fenshuNum1").val(jNum);
						$("#fundAmt5").text((Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(0)).attr("num", Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())) ;
						//投资总额 / (融资总额 - 项目方出资) * 出让股份
						$("#couInvestZgbl1").text(formatCurrency(Number($("#fundAmt5").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))));
					}
				});
				//减一份
				$("#iJian1").unbind("click").click(function(){
					var fsNum = Number($("#remainsFenshu1").text()), jNum = 0;
					if(Number($("#fenshuNum1").val()) < 2){ //输入框内只剩0份
						$("#iJian1").css("color", "#CCC");
						return false;
					}else{
						jNum = Number($("#fenshuNum1").val()) - 1;
						$("#fenshuNum1").val(jNum);
						$("#fundAmt5").text((Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(0)).attr("num", Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())) ;
						//投资总额 / (融资总额 - 项目方出资) * 出让股份
						$("#couInvestZgbl1").text(formatCurrency(Number($("#fundAmt5").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))));
					}
				});
				$("#investFBtn").unbind("click").click(function(){
					checkBindBankCard();
				});
			}else{
				AlertDialog.mtip("领投人认证通过后可进行领投");
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
	
}
//申请领投前验证
function pay(){
	if(MValify.isNull($("#applyExplain").val(), "applyExplain",-60, 100)){
		$.ajax({
			url: path + "/fundpool/leaderPay/checkBeforeOrderInvest.html",
			type: "post",
			dataType: "json",
			async: false,
			data: {
				"buyNum": $("#fenshuNum1").val(),
				"loanNo": loanNo
			},
			success: function(data){
				if(!data["success"]){
					$("#investFirstDiv").hide();
					AlertDialog.tip(data["msg"]);
				}else{
					$("#investTip").hide();
					$("#lForm input[name='buyNum']").val($("#fenshuNum1").val());
					$("#lForm input[name='loanNo']").val(loanNo);
					$("#lForm input[name='applyLeadDes']").val($("#applyExplain").val());
//					$("#lFormBtn").click();
					//支付
					$.ajax({
						url: path + "/fundpool/yeepay/pay/directBindPay.html",
						type: "post",
						dataType: "json",
						async: false,
						data: {
							"cardTop":$("#cardTop").val(),
							"cardLast":$("#cardLast").val(),
							"payType":$("#payType").val(),
							"loanNo":$("#loanNo").val(),
							"buyNum":$("#buyNum").val(),
							"applyLeadDes":$("#applyLeadDes").val()
						},
						success:function(data){
							if(data["orderId"]){
								$("#okPay").text("支付中...").unbind();
								$("#sendBtn").unbind();
								var i = 0;
								var obj = setInterval(function(){
									if(checkPay(data["orderId"]) == true || i == 20){
										clearInterval(obj);//取消
										if(i == 20){
											$("#selCard a.close").click();
											AlertDialog.mtip("支付失败！",function(){
												window.location.href = window.location.href;
											});
										}
									}
									i++;
								},3000);
							}else{
								AlertDialog.mtip(data["msg"]);
							}
						},
						error:function(){
							
						}
					});
				}
			},
			error: function(request){
				console.log("验证领投请求异常");
			}
		});
	};
}
//我要预约
function wantPreSupport(){
	if(userId == "null"){
		go2Login();
		return false;
	}
	$("#bgpop").show();
	var it = (cv["winH"]-230)/2, il = (cv["winW"]-415)/2;
	$("#preSupportDiv").css({"top": it+"px", "left":"2.5%"}).show();
	//赋值
	$("#preMinAmt").text($("#miniInvestAmt").text()).attr("num", $("#miniInvestAmt").attr("num"));
	$("#preMaxAmt").text(formatCurrency(Number($("#fundAmt1").attr("num"))*2)).attr("num", Number($("#fundAmt1").attr("num"))*2);
	//关闭
	$("#bgpop").click(function(){
		if($("#tip_div").attr("id")){
			AlertDialog.hide();
		}
		$("#bgpop").hide();
		$("#preSupportDiv").hide();
	});
	$("#canelPreSupportBtn").click(function(){
		if($("#tip_div").attr("id")){
			AlertDialog.hide();
		}
		$("#bgpop").hide();
		$("#preSupportDiv").hide();
	});
	$("#preSupportBtn").unbind("click").click(function(){
		if(checkNum($("#preSupportInput").val(), "preSupportInput")){
			$.ajax({
				url: path + "/crowdfundingInvest/savePreSupport.html",
				type: "post",
				dataType: "json",
				async: false,
				data: {
					"supportAmt": $("#preSupportInput").val(),
					"loanNo": loanNo
				},
				success: function(data){
					if(!data["success"]){
						AlertDialog.show(data["msg"], "preSupportInput", 10,20);
					}else{
						$("#preSupportTipl").hide();
						$("#preSupportDiv").hide();
						AlertDialog.tip("预购成功!");
					}
				},
				error: function(request){
					console.log("我要预约请求异常");
				}
			});
		}
	});
}
//我要认购，跟投
function investLast(){
	if(userId == "null"){
		go2Login();
		return false;
	}
//	if(!isTzr){
//		var al = (cv["winW"]-360)/2,at= (cv["winH"]-200)/2;
//		$("#attentionBg").show();
//		$("#noInvestorTip").css({"left":"2.5%","top":at+"px"}).show();
//		
//		$("#attentionBg").click(function(){
//			$("#attentionBg").hide();
//			$("#noInvestorTip").hide();
//		});
//		return false;
//	}
	
//	if(parseInt(investorLevel) < authStr){
//		window.location.href = path + "/common/m-index.html" ;
//		return false;
//	}
	
	var ltArr = [], ltStr = '', l; //加载领投人列表
	$("#bgpop").show();
	var it = (cv["winH"]-412)/2, il = (cv["winW"]-600)/2;
	$("#investLastDiv").css({"top":it+"px", "left": "2.5%"}).show();
	//关闭
	$("#bgpop").click(function(){
		$("#bgpop").hide();
		$("#investLastDiv").hide();
	});
	$("#supFomr").append($('<input type="hidden" name="leadInvestor"/>'));
	$("#remainsFenshu").text(Number($("#financeNum").val()) - Number($("#buyNum1").val()));//剩余份数
	$("#fundAmt4").text($("#miniInvestAmt1").text());
	//更换验证码
	$("#changeValiBtn").click(function(){
		$("#imageInvest").attr("src", path + "/security/securityCodeImage.html?" + new Date().getTime());
	});
	$("#imageInvest").attr("src", path + "/security/securityCodeImage.html?" + new Date().getTime());
	
	//加一份
	$("#iJIa").unbind("click").click(function(){
		var fsNum = Number($("#remainsFenshu").text()), jNum = 0;
		if(Number($("#fenshuNum").val()) == fsNum){ //超出剩余份数
			$("#iJIa").css("color", "#ccc");
			return false;
		}else{
			jNum = Number($("#fenshuNum").val()) + 1;
			$("#fenshuNum").val(jNum);
			$("#fundAmt4").text(formatCurrency(Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum").val()))).attr("num", Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum").val())) ;
			//投资总额 / (融资总额 - 项目方出资) * 出让股份
			$("#couInvestZgbl").text((Number($("#fundAmt4").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))/100).toFixed(2));
		}
	});
	//减一份
	$("#iJian").unbind("click").click(function(){
		var fsNum = Number($("#remainsFenshu").text()), jNum = 0;
		if(Number($("#fenshuNum").val()) < 2){ //输入框内只剩0份
			$("#iJian").css("color", "#CCC");
			return false;
		}else{
			jNum = Number($("#fenshuNum").val()) - 1;
			$("#fenshuNum").val(jNum);
			$("#fundAmt4").text(formatCurrency(Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum").val()))).attr("num", Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum").val())) ;
			//投资总额 / (融资总额 - 项目方出资) * 出让股份
			$("#couInvestZgbl").text(formatCurrency(Number($("#fundAmt4").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))));
		}
	});
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
						ltArr.push('<dd>');
					}
					ltArr.push('<a href="javascript:void(0);" class="clearfix">');
					if(data[i]["photo"]){
						ltArr.push('<p class="p1"><img src="'+cv["fileAddress"]+'/'+data[i]["photo"]+'" width="48"/></p>');
					}else{
						ltArr.push('<p class="p1"><img src="'+path+'/images/defaultPhoto.png" width="48"/></p>');
					}
					ltArr.push('<p class="p2" style="height:40px;line-height:50px;">'+data[i]["companyName"]+'</p>');
					ltArr.push('<p class="p3" style="height:30px;line-height:20px;">认投金额:<span>'+data[i]["totalSupportAmt"]+'</span></p>');
					ltArr.push('</a></dd>');
				}
				ltStr = ltArr.join("");
				$("#ltrList").html(ltStr);
			};
		},
		error: function(request){
			console.log("获取领头人列表异常！");
		}
	});
	
	//跟投前验证
	$("#investLBtn").unbind("click").click(function(){
		if($("#fenshuNum").val() == "0"){ //添加认购份数
			AlertDialog.show("请添加认购份数", "fenshuNum", 10, 20);
			return false;
		}
		if(!$("#investValiInput").val()){
			AlertDialog.show("请输入验证码", "investValiInput", 10, 20);
			return false;
		}
		$.ajax({
			url: path + "/fundpool/invest/checkBeforeEntitySupport.html",
			type: "post",
			dataType: "json",
			async: false,
			data: {
				"buyNum": $("#fenshuNum").val(),
				"loanNo": loanNo,
				"investType": "followInvest",
				"leadInvestor": $("#ltrList>dd.a_home").attr("id"),
				"valiCode": $("#investValiInput").val()
			},
			success: function(data){
				if(!data["success"]){
					$("#investLastDiv").hide();
					AlertDialog.tip(data["msg"]);
				}else{
					$("#investTip").hide();
					$("#formBuyNum").val($("#fenshuNum").val());
					$("#formLoanNo").val(loanNo);
					$("#supFomr").find("input[name='investType']").val("followInvest"); //跟投改变值
					$("#supFomr").find("input[name='leadInvestor']").val($("#ltrList>dd.a_home").attr("id"));
					$("#supFormBtn").click();
				}
			},
			error: function(request){
				console.log("验证领投请求异常");
			}
		});
	});
}
function checkNum(str, id){
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 130);
		$("#" + id).val("");
		return false;
	}else{
		if(isNaN(str)){
			AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 130);
			$("#" + id).val("");
			return false;
		}
		if(Number(str) < Number($("#preMinAmt").attr("num"))){
			AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 130);
			$("#" + id).val("");
			return false;
		}
		if(Number(str) > Number($("#preMaxAmt").attr("num"))){
			AlertDialog.show($("#" + id).attr("logicMessage1"), id, 10, 130);
			return false;
		}
	}
	AlertDialog.hide();
	return true;
}

/*__________________________________ 支付 __________________________________________*/
//检测是否绑定银行卡
function checkBindBankCard(){
	$.ajax({
		url: path + "/fundpool/yeepay/pay/queryAuthbindList.html",
		type: "post",
		dataType: "json",
		success:function(data){
			$("#investFirstDiv").hide();
			AlertDialog.hide();
			if(data["cardlist"] != "[]"){
				var str = data["cardlist"].replace("[","").replace("]","");
				var jsonList = [];
				var selHtml = [];
				if(str.indexOf("},{") != -1){
					var list = str.split("},{");
					for(var i = 0;i < list.length;i++){
						if((i+1)%2 == 0){
							jsonList[i] = JSON.parse("{" + list[i])
						}else{							
							jsonList[i] = JSON.parse(list[i]+"}")
						}
					}
				}else{
					jsonList[0] = JSON.parse(str);
				}
				selHtml.push('<option>请选择支付银行卡</option>');
				for(var i = 0 ; i < jsonList.length;i++){
					selHtml.push('<option value="" top="' + jsonList[i]["card_top"] + '" last="' + jsonList[i]["card_last"] + '" phone="' + jsonList[i]["phone"] + '">' + jsonList[i]["card_top"] + "****" + jsonList[i]["card_last"] + '(' + jsonList[i]["card_name"] + ')</option>');
				}
				$("#selBank").html(selHtml.join(""));
				//显示支付银行选择框
				var obj = $("#selCard");
				var t = (cv.winH - obj.height())/2;
				var l = (cv.winW - obj.width())/2;
				$(".bgpop").fadeIn();
				obj.css({"top":t+"px","left":l+"px"}).fadeIn();
				//关闭支付银行选择框
				$("#selCard a.close").unbind().click(function(){
					AlertDialog.hide();
					$(".bgpop").fadeOut();
					obj.fadeOut();
					$("#investFirstDiv").fadeOut();
				});
				$("#selBank").attr("disabled",false);
				$("#sendBtn").unbind().click(sentSMS).text("获取验证码");//发送验证码
				$("#okPay").unbind().click(checkPhoneCode).text("确认支付");//检测码证码
				$("#phoneCode").val("");
			}else{
				AlertDialog.mtip("您还没有绑定银行卡不可支付！",function(){
					window.location.href = path + "/common/m-createBankCard.html";
				});
			}
		},
		error:function(){
			
		}
	});
}

//发送支付验证短信
function sentSMS(){
	if($("#selBank option:checked").text() == "请选择支付银行卡"){
		AlertDialog.show("请选择支付银行卡", "selBank", 5, 150);
		return;
	}else{
		AlertDialog.hide();
	}
	$("#sendBtn").unbind("click").text("发送中...");
	$.ajax({
		url: path + "/fundpool/yeepay/pay/sendPayVerifyCode.html",
		type: "post",
		dataType: "json",
		data: {
			"phone": $("#selBank option:checked").attr("phone")
		},
		success: function(data){
			if(data["success"]){
				$("#selBank").attr("disabled",true);
				$("#cardTop").val($("#selBank option:checked").attr("top"));
				$("#cardLast").val($("#selBank option:checked").attr("last"));
				$("#sendBtn").unbind("click").text("60秒后发送").css({"color":"#CCC"});
				countDown(60, "sendBtn", bOverFn);
			}
		},
		error: function(request){
			$("#sendBtn").text("获取验证码").css({"color":"#248af9"});
			$("#sendBtn").click(sentSMS);
			console.log("发送绑定银行卡信息异常");
		}
	});
}

function bOverFn(){
	$("#sendBtn").text("获取验证码").css({"color":"#FFF", "background":"#248af9"});
	$("#sendBtn").click(sentSMS);
}

//检测支付验证码
function checkPhoneCode(){
	if($("#phoneCode").val().length != 6 || isNaN($("#phoneCode").val())){
		AlertDialog.show("请输入短信验证码", "phoneCode", 5, 50);
		return;
	}else{
		AlertDialog.hide();
	}
	$.ajax({
		url: path + "/fundpool/yeepay/pay/checkMobileCode.html",
		type: "post",
		dataType: "json",
		data: {
			"phone":$("#selBank option:checked").attr("phone"),
			"verifyCode": $("#phoneCode").val()
		},
		success: function(data){
			if(data["success"]){
				pay();
			}else{
				AlertDialog.mtip(data["msg"]);
				return;
			}
		},
		error: function(request){
			console.log("验证支付验证码异常");
		}
	});
}

//检测是否支付
function checkPay(orderId){
	var bool = false;
	$.ajax({
		url: path + "/fundpool/yeepay/pay/checkOrderState.html",
		type: "post",
		dataType: "json",
		async: false,
		data: {
			"orderId":orderId
		},
		success:function(data){
			if(data["state"] == "payed"){//成功
				$("#selCard a.close").click();
				AlertDialog.mtip(data["msg"],function(){
					window.location.href = window.location.href;
				});
				bool = true;
			}else if(data["state"] == "cancel"){//失败
				$("#selCard a.close").click();
				AlertDialog.mtip(data["msg"],function(){
					window.location.href = window.location.href;
				});
				bool = true;
			}else{
				bool = false;
			}
		},
		error:function(){
			bool = false;
		}
	});
	return bool;
}

//投资记录
function getSupportList(){
	$.ajax({
		url: path + "/crowdfunding/getSupportList.html",
		type: "post",
		dataType: "json",
		data:{
			"loanNo":loanNo,
			"page":page,
			"rows":10
		}, 
		success: function(data){
			if(!data["success"]){
				sStr = '<div style="padding:30px;color:red;">暂无数据</div>';
				$("#suportTable").html(lStr).show();
				return false;
			}
			var l = data["msg"]["rows"].length,total = data["msg"]["total"];
			var sumPage = (data["msg"]["total"]%10 == 0) ? data["msg"]["total"]/10 : Math.floor(data["msg"]["total"]/10) + 1;
			data = data["msg"]["rows"];
			var sArr = [];
			if(l == 0){
				sStr = '<div style="padding:30px;color:red;">暂无数据</div>';
				$("#suportTable").html(sStr).show();
				return false;
			}
			for(var i=0;i<l;i++){
//				if(data[i]["isLoanLeader"] > 0){//领投人
//					
//				}
				sArr.push('<tr class="even">');
				sArr.push('<td style="width:33%;text-align:center;height: 35px;">'+data[i]["supportUser2"]+'</td>');
				sArr.push('<td style="width:33%;text-align:center;height: 35px;">'+data[i]["supportAmt"]+'</td>');
				sArr.push('<td style="width:33%;text-align:center;height: 35px;">'+data[i]["supportTime"].substring(0, 10)+'</td>');
				sArr.push('</tr>');
			}
			sStr = sArr.join("");
			$("#suportTable").html(sStr).show();
			if(page == sumPage){
				$("#backMoreList").unbind().hide();
			}else if(sumPage > 1){
				$("#backMoreList").unbind().click(function(){
					page++
					getSupportList();
				}).show();
			}
		},
		error:function(){
			
		}
	})
}