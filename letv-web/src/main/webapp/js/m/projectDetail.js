var loanNo = getQueryString("loanNo");
var token = getQueryString("token");
var mobileTerminalFlag = getQueryString("mobileTerminalFlag");
var member = getQueryString("member");
var recordNum = 1;//记录 预约，认购记录的页数
var isProjectPay = 0; // 0=非高端 ，>0高端
var isTzr = 0;  //是认证投资人
var page = 1;//认购记录页数
var authStr = 0; //项目权限
var allPageNum = 1;
$(function(){
	slide("#container", 0, function (e) {
	    var that = this;
	
	    setTimeout(function () {
	        that.back.call();
	    }, 2000);
	
	});
	span_move_fun()
	getCrowdDetail();//项目详情信息
	getSupportList();
	$("#subComment").click(submitComment);//发表评论
	//分页
	$("#loanPage a").unbind("click").click(function(){
		allPageNum++;
		showInfoContent("proComment", allPageNum);
	});
	var $tab = $("#detailTab li");
	$.each($tab,function(k,v){
		$(v).click(function(){
			var name = $(v).attr("name");
			var turl = $(v).attr("url");
			
			if(name == "proRecord" || name == "proProgress" || name == "proComment" ){
				//if(token){//判断是否登录 未登录先登录		
					$.ajax({
						url: path + "/crowdfunding/getCrowdDetail.html",
						type: "post",
						dataType: "json",
						data: {
							"loanNo":loanNo
						},
						success: function(data){
							if(name == "rgList"&&!data["msg"]["investFlag"]){
								AlertDialog.tip("请先投资！");
								return false;
							}
							$tab.find("a").removeClass('cur');
							$(v).find("a").addClass('cur');
							$("#detailBody>div").hide();
							if(name == "proComment" || name == "proProgress" || name == "rgList"){
								showInfoContent(name ,1);
							}
							$("#"+name+"Body").show();
						},
						error: function(request){
							console.log("获取个人信息异常");
						}
					});
//				}else{
//					//AlertDialog.confirm(goMlogin,null,"您还没有登录，请先登录","登录","取消",null);
//					AlertDialog.tip("您还没有登录，请先登录");
//					return false;
//				}
			}else{
				$tab.find("a").removeClass('cur');
				$(v).find("a").addClass('cur');
				$("#detailBody>div").hide();
				$("#"+name+"Body").show(); //项目简介可直接点击查看
			}
		});
	});
	$tab.first().click();
	//getUserInfo(); //获取个人信息
//	$("#investFBtn").click(checkBindBankCard);
	
	$("#alertSure,#comClse").click(function(){
		
	});
});

function goMlogin(){
	window.location.href = path + "/common/m-login.html";
}
function charge(){
	window.location.href = path + "/common/m-centerRZ.html";
}
function charge1(){
	window.location.href = path + "/common/m-centerRZ.html?trigger";
}
function experience(_this){
	window.location.href = path + "/common/m-teamIntroduce.html?id="+$(_this).attr("aid");
}
//获取项目详情信息
function getCrowdDetail(){
	
	//获取支持者
	$.ajax({
		url: path + "/crowdfunding/getSupportList.html",
		type: "post",
		dataType: "json",
		data: {
			"isTransfer":"0",
			"loanNo": loanNo
		},
		success: function(data){
			if(!data["success"]){
				$("#remainTotal").text("0人");
				return false;
			}
			var total = data["msg"]["total"];
			$("#remainTotal").text(total+"人");
		},
		error: function(request){
			console.log("获取领投人列表异常！");
		}
	});
	var remainDays = 0;
	$.ajax({
		url: path + "/crowdfunding/getCrowdDetail.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"] || data["msg"]==""){
				return false;
			}
			data = data["msg"];
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
					$("#approveAmt").text(data["approveAmt"].toFixed(2)+companyCode);
				}else{
					$("#approveAmt").text("0.00");
				}
				$("#loanStateName").text("预热中");
				$("#xs_jd").remove();
				$("#buyNum1Span").remove();
				$("#fundEndTime").parent().hide();
			}else if(data["loanState"] == "funding"){ //筹款中
				if(data["approveAmt"]){
					var temp = String(data["approveAmt"]).replace(/,/g,"");
					$("#approveAmt").text(data["approveAmt"].toFixed(2)+companyCode);
				}else{
					$("#approveAmt").text("0.00"+companyCode);
				}
			}else if(data["loanState"] == "funded" || data["loanState"] == "lended" || data["loanState"] == "end"){ //筹款完成
				$("#approveAmt").text(data["approveAmt"]+companyCode);
				$("#loanStateName").text(data["loanStateName"]);
			}else{
				$("#loanStateName").text(data["loanStateName"]);
			}
			if(data["loanType"] == "product"){
				$("#loanName").html(data["loanName"]+'<a>'+data["superIndustryName"]+'</a>'); //项目名称
			}else{
				$("#loanName").html(data["loanName"]+'<a>公益</a>'); //项目名称
			}
			
			$("#loanPhoto").attr("src", cv["fileAddress"] + data["loanLogo"]).attr("title", data["loanName"]).attr("alt", data["loanName"]); //项目封面
			$("#yLoanuser").val(data["loanUser"]);
			$("#loanIntroduction").text(data["loanIntroduction"]);
			$("#loanUserName").text(data["loanUser2"]);
			$("#loanUserName1").text(data["loanUserName"]);
			if(data["fundEndTime"]){
				$("#fundEndTime").text(data["fundEndTime"].substring(0,10));
			}else{
				$("#fundEndTime").text(data["preheatEndTime"]);
			}
			$("#loanDetail").html(data["loanDetail"]); //项目详情
			$("#loanDes").text(data["loanDes"]); //项目简介
			if(data["mobileVideo"]){
				$("#mobileVideo").html("<iframe height=180 width=100% src='"+data["mobileVideo"]+"' frameborder=0 'allowfullscreen'></iframe>").show();
				//$("#mobileVideo").html('<video width="100%" height="200px" controls loop="loop" src="'+data["loanVideo"]+'"></video>').show();
			}
			if(data["loanState"] == "funded" || data["loanState"] == "lended" || data["loanState"] == "end"){//筹款结束
				$("#remainDay").text(0+'天');
			}else{
				if(data["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
					$("#remainDay,#remainTotal,#approveAmt").parent().hide();
					$("#attentionNum,#praiseNum,#fundAmtT").parent().show();
				}else{
					remainDays = data["remainDays"] ? (data["remainDays"] < 0 ? "0" : data["remainDays"]) : "0" ;
					$("#remainDay").text(remainDays+'天');
				}
			}
			$("#fundAmtT").text(data["fundAmt"].toFixed(2)+companyCode);
			$("#attentionNum").text(data["attentionNum"]+"人");
			$("#praiseNum").text(data["praiseNum"]+"人");
			$("#fundAmt").text(data["fundAmt"].toFixed(2)+companyCode).attr("num", data["fundAmt"]);
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
			$("#superIndustry").text(data["superIndustryName"]); //行业
			
			
		},
		error: function(request){
			console.log("获取股权详细信息异常");
		}
	});
}



function showInfoContent(id, page){
	$("#detailBody>div").hide();
	//点击标签按钮后，页面滑动到合适查看的位置
	if($("#" + id + "Content").attr("h") == "1"){ //已经填充过内容
		$("#" + id + "Content").show();
		return false;
	}
	if(id == "proComment"){ //评论标签下获取数据
		$.ajax({
			url: path + "/crowdfunding/getCommentList.html",
			type: "post",
			dataType: "json",
			data: {
				"loanNo": loanNo,
				"rows": 10,
				"page": page
				},
			success: function(data){
				if(!data["success"]){
					return false;
				}
				var total = data["msg"]["total"];
				var sumPage = (total%10 == 0) ? total/10 : Math.floor(total/10) + 1;
				var pArr = [], pStr = '', l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				$(".reply").keyup(function(){
					var len = $(this).val().length;
					var num = 140 - len;
					if(len > 139){
					    $(this).val($(this).val().substring(0,140));
					    num = 0
					}
					$(this).parent().find("p>span>i").text(num);
				});
				$("#proCommentContent").fadeIn();
				if(l ==0){
					$("#commonPage").hide();
					$("#loanPage ").hide();
					return false;
				}
				for(var i = 0;i<l;i++){
					pArr.push('<div class="pl_list clearfix">');
					pArr.push('<div class="clearfix fqr_xq" style="margin-top:0">');
					pArr.push('<div class="fl mr25">');
					if(data[i]["photo"]){
						pArr.push('<img src="'+ cv["fileAddress"] + '/' + data[i]["photo"] +'" width="50px" height="50px" style="border-radius: 50%;">');
					}else{
						pArr.push('<img src="'+ path+'/images/defaultPhoto.png" width="50px" height="50px" style="border-radius: 50%;">');
					}
					pArr.push('</div>');
					pArr.push('<div class="fl rg_txt" style="width:70%;">');
					pArr.push('<p class="col6">');
					if(data[i]["userNickName"]){
						pArr.push('<span class="colaa">'+data[i]["userNickName"]+'：</span>');
					}else{
						pArr.push('<span class="colaa">'+data[i]["discussUser"]+'：</span>');
					}
					pArr.push(data[i]["content"]+'</p>');
//					pArr.push('<span class="colaa">'+data[i]["discussUser"]+'：</span>'+data[i]["content"]+'');
					pArr.push('</p>');
					pArr.push('<p class="mt15 col8">'+data[i]["discussTime"].substring(0,10)+'<a class="replay" aid="'+data[i]["id"]+'" onClick="replyShow(this)" >回复</a></p>');
					pArr.push('</div>');
					pArr.push('</div>');
					pArr.push('<div class="replay_content" id="'+data[i]["id"]+'">');
					pArr.push('<textarea placeholder="评论不能少于5个字哦！" class="reply"> </textarea>');
					pArr.push('<p class="clearfix mt15 colaa">');
					pArr.push('<span class="fl">还可以输入<i>140</i>字</span>');
					pArr.push('<a class="fr replay_btn" onClick="reply(this)" pid="'+data[i]["id"]+'">回复</a>');
					pArr.push('</p>');
					pArr.push('</div>');
					var r = data[i]["subList"].length, Rdata = data[i]["subList"];
					for(var j = 0;j<r;j++){
						pArr.push('<div class="mt25" style="margin-left:50px;">');
						pArr.push('<div class="clearfix fqr_xq" style="margin-top:0">');
						pArr.push('<div class="fl mr25">');
						if(Rdata[j]["photo"]){
							pArr.push('<img src="'+ cv["fileAddress"] + '/' + Rdata[j]["photo"] +'" width="50px" height="50px" style="border-radius: 50%;">');
						}else{
							pArr.push('<img src="'+ path+'/images/defaultPhoto.png" width="50px" height="50px" style="border-radius: 50%;">');
						}
						pArr.push('</div>');
						pArr.push('<div class="fl rg_txt" style="width:70%;">');
						pArr.push('<p class="col6">');
						if(Rdata[j]["userNickName"]){
							pArr.push('<span class="colaa">'+Rdata[j]["userNickName"]+'：</span>' + Rdata[j]["content"] +'');
						}else{
							pArr.push('<span class="colaa">'+Rdata[j]["discussUser"]+'：</span>' + Rdata[j]["content"] +'');
						}
						pArr.push('</p>');
						pArr.push('<p class="mt15 col8">' + Rdata[j]["discussTime"].substring(0,10) +'</p>');
						/*pArr.push('<p class="mt15 col8">2016-09-09<a class="replay">回复</a></p>');*/
						pArr.push('</div>');
						pArr.push('</div>');
						pArr.push('</div>');
					}
					pArr.push('</div>');
				}
				pStr = pArr.join("");
				if(page == 1){
					$("#commentUl>div").html(pStr);
				}else{
					$("#commentUl>div").append(pStr);
				}
				//$("#commonPage").show();
				var proLen = $("#commentUl>div>div").length;
				if(proLen == total){
					$("#loanPage").hide();
					if(proLen < 10 &&  proLen > 0){
						$("#loanPage ").hide();
					}
					return false;
				}else{
					$("#loanPage").show();
				}
				$(".replay_content textarea.reply").keyup(function(){
					if($(this).val().length>140){
						$(this).val($(this).val().substring(0,140));
					}
				});
				
				//分页设置
//				pagePause = 0;
//				if(page < 2){
//					$("#commonPage").jPages({
//						containerID : "commentUl",
//						first:false,
//						last:false,
//						previous:" ",
//						next:" ",
//						clickStop   : true,
//						perPage	: 10,
//						allSumPage : sumPage,
//						callback: ajaxPagecommentData
//					});
//				}
			},
			error: function(request){
				console.log("获取股权项目评论异常");
			}
		});
	}else if(id == "proProgress"){
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
					pStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>抱歉，暂无数据哦~</div>';
					$("#loanProgress").html(pStr);
					$("#proProgressContent").fadeIn().attr("h", "1");
					return false;
				}
				for(var i=0;i<l;i++){
					//显示项目进展
					pArr.push('<div class="jd_list clearfix">');
					if(data[i]["timeNode"]){
						pArr.push('<div class="fl col9 mt4 ft16" style="width:78px;">'+data[i]["timeNode"].substring(0,10)+'</div>');
					}else{
						pArr.push('<div class="fl col9 mt4 ft16" style="width:78px;">'+data[i]["enterTime"].substring(0,10)+'</div>');
					}
					
					pArr.push('<div class="fl jd-icon"></div>');
					pArr.push('<div class="fr" style="width: 60%;"><p>'+data[i]["enterContent"]+'</p>');
					var imgFileList = data[i]["imgFileList"]
					for(var j=0;j<imgFileList.length;j++){
						pArr.push('<img src="'+cv.fileAddress+imgFileList[j]["url"]+'" class="jd_img">');
					}
					pArr.push('</div>');
					pArr.push('</div>');
				}
				//展示项目进展
				pStr = pArr.join("");
				if(!$("#loanProgress").attr("h")){
					$("#loanProgress").append(pStr);
				}
				$("#loanProgress").fadeIn().attr("h", "1");
				var height = $("#loanProgress").height()-($("#loanProgress>div").eq($("#loanProgress>div").length-1).height()-10);
				$("#jd_content_bor").css("height",height+"px");
			},
			error: function(request){
				console.log("获取项目进展异常");
			}
		});
	}
}
function ajaxPagergData(obj){
	if(pagePause == 0){
		return false;
	}
	showInfoContent($("#detailTab a.cur").parent().attr("url"), $("#detailTab a.cur").parent().attr("name"), obj["current"]);
}
function ajaxPagecommentData(obj){
	if(pagePause == 0){
		return false;
	}
	showInfoContent($("#detailTab a.cur").parent().attr("url"), $("#detailTab a.cur").parent().attr("name"), obj["current"]);
}
function loginUrl(){}
//发表评论
function submitComment(){
	if(!token){
		//AlertDialog.tip("请登录后发表评论！");
		AlertDialog.confirm(loginUrl, null, "请登录后发表评论！", "去登录", "再看看", null);
		return false;
	}
	if($("#comVal").val().length < 5){
		AlertDialog.tip("评论不能少于5个字哦！");
		return false;
	}
	if($("#comVal").val().length > 200){
		AlertDialog.tip("评论不能超过200个字哦！");
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
				//todo 重新查询发表评论数据，数据填充
				$.ajax({
					url: path + "/crowdfunding/getCommentList.html",
					type: "post",
					dataType: "json",
					data: {
						"loanNo": loanNo
					},
					success:function(data){
						$("#comVal").val("");
						$("#commetNum").text(data["msg"]["total"]);
						showProCommentAjax(data,1);
					},
					error: function(request){
						console.log("获取评论异常");
					}
				});
			}
		},
		headers:{
			"token":token,
			"mobileTerminalFlag":mobileTerminalFlag
		},
		error: function(request){
			console.log("发表评论异常");
		}
	});
}

//获取评论
function showProCommentAjax(data,page){
	var total = data["msg"]["total"];
	var sumPage = (total%10 == 0) ? total/10 : Math.floor(total/10) + 1;
	var pArr = [], pStr = '', l = data["msg"]["rows"].length, data = data["msg"]["rows"];
	$(".reply").keyup(function(){
		var len = $(this).val().length;
		var num = 140 - len;
		if(len > 139){
		    $(this).val($(this).val().substring(0,140));
		    num = 0
		}
		$(this).parent().find("p>span>i").text(num);
	});
	if(l ==0){
		$("#loanPage ").hide();
		return false;
	}
	for(var i = 0;i<l;i++){
		pArr.push('<div class="pl_list clearfix">');
		pArr.push('<div class="clearfix fqr_xq" style="margin-top:0">');
		pArr.push('<div class="fl mr25">');
		if(data[i]["photo"]){
			pArr.push('<img src="'+ cv["fileAddress"] + '/' + data[i]["photo"] +'" width="50px" height="50px" style="border-radius: 50%;">');
		}else{
			pArr.push('<img src="'+ path+'/images/defaultPhoto.png" width="50px" height="50px" style="border-radius: 50%;">');
		}
		pArr.push('</div>');
		pArr.push('<div class="fl rg_txt" style="max-width:70%;">');
		pArr.push('<p class="col6">');
		if(data[i]["userNickName"]){
			pArr.push('<span class="colaa">'+data[i]["userNickName"]+'：</span>');
		}else{
			pArr.push('<span class="colaa">'+data[i]["discussUser"]+'：</span>');
		}
//		pArr.push('<span class="colaa">'+data[i]["discussUser"]+'：</span>');
		pArr.push(data[i]["content"]+'</p>');
		pArr.push('<p class="mt15 col8">'+data[i]["discussTime"].substring(0,10)+'<a class="replay" aid="'+data[i]["id"]+'" onClick="replyShow(this)" >回复</a></p>');
		pArr.push('</div>');
		pArr.push('</div>');
		pArr.push('<div class="replay_content" id="'+data[i]["id"]+'">');
		pArr.push('<textarea placeholder="评论不能少于5个字哦！" class="reply"> </textarea>');
		pArr.push('<p class="clearfix mt15 colaa">');
		pArr.push('<span class="fl">还可以输入<i>140</i>字</span>');
		pArr.push('<a class="fr replay_btn" onClick="reply(this)" pid="'+data[i]["id"]+'">回复</a>');
		pArr.push('</p>');
		pArr.push('</div>');
		var r = data[i]["subList"].length, Rdata = data[i]["subList"];
		for(var j = 0;j<r;j++){
			pArr.push('<div class="mt25" style="margin-left:50px;">');
			pArr.push('<div class="clearfix fqr_xq" style="margin-top:0">');
			pArr.push('<div class="fl mr25">');
			if(Rdata[j]["photo"]){
				pArr.push('<img src="'+ cv["fileAddress"] + '/' + Rdata[j]["photo"] +'" width="50px" height="50px" style="border-radius: 50%;">');
			}else{
				pArr.push('<img src="'+ path+'/images/defaultPhoto.png" width="50px" height="50px" style="border-radius: 50%;">');
			}
			pArr.push('</div>');
			pArr.push('<div class="fl rg_txt" style="width:70%;">');
			pArr.push('<p class="col6">');
			if(Rdata[j]["userNickName"]){
				pArr.push('<span class="colaa">'+Rdata[j]["userNickName"]+'：</span>' + Rdata[j]["content"] +'');
			}else{
				pArr.push('<span class="colaa">'+Rdata[j]["discussUser"]+'：</span>' + Rdata[j]["content"] +'');
			}
			pArr.push('</p>');
			pArr.push('<p class="mt15 col8">' + Rdata[j]["discussTime"].substring(0,10) +'</p>');
			/*pArr.push('<p class="mt15 col8">2016-09-09<a class="replay">回复</a></p>');*/
			pArr.push('</div>');
			pArr.push('</div>');
			pArr.push('</div>');
		}
		pArr.push('</div>');
	}
	pStr = pArr.join("");
	$("#commentUl>div").html(pStr);
	$("#commonPage").hide();
	var proLen = $("#commentUl>div>div").length;
	if(proLen == total){
		$("#loanPage").hide();
		if(proLen < 10 &&  proLen > 0){
			$("#loanPage ").hide();
		}
		return false;
	}else{
		$("#loanPage").show();
	}
	$(".replay_content textarea.reply").keyup(function(){
		if($(this).val().length>140){
			$(this).val($(this).val().substring(0,140));
		}
	})
	//分页设置
//	pagePause = 0;
//	if(page < 2){
//		$("#commonPage").jPages({
//			containerID : "commentUl",
//			first:false,
//			last:false,
//			previous:" ",
//			next:" ",
//			clickStop   : true,
//			perPage	: 10,
//			allSumPage : sumPage,
//			callback: ajaxPagecommentData
//		});
//	}
	$("#proCommentContent").fadeIn();
}

//回复
function replyShow(id){
	var _id = $(id).attr("aid");
	$("#"+_id).show();
}
function reply(id){
	if(!token){
		//AlertDialog.tip("请登录后发表评论！");
		AlertDialog.confirm(loginUrl, null, "请登录后发表评论！", "去登录", "再看看", null);
		return false;
	}
	if($(id).parent().parent().find("textarea").val().length < 5){
		AlertDialog.tip("评论不能少于5个字哦！");
		return false;
	}
	var pid = $(id).attr("pid");
	$.ajax({
		url: path + "/crowdfunding/submitComment.html",
		type: "post",
		dataType: "json",
		data: {
			"pid": pid,
			"content": $(id).parent().parent().find("textarea").val()
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
						showProCommentAjax(data,1);
					},
					error: function(request){
						console.log("获取评论异常");
					}
				});
			}
		},
		headers:{
			"token":token,
			"mobileTerminalFlag":mobileTerminalFlag
		},
		error: function(request){
			console.log("发表评论异常");
		}
	});
}

//投资记录
function getSupportList(){
	$.ajax({
		url: path + "/crowdfunding/getSupportList.html",
		type: "post",
		dataType: "json",
		data:{
			"loanNo":loanNo,
			"isTransfer":"0",
			"page":page,
			"rows":10
		}, 
		success: function(data){
			if(!data["success"]){
				sStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>抱歉，暂无数据哦~</div>';
				$("#suportTable").html(sStr).show();
				return false;
			}
			var l = data["msg"]["rows"].length,total = data["msg"]["total"];
			var sumPage = (data["msg"]["total"]%10 == 0) ? data["msg"]["total"]/10 : Math.floor(data["msg"]["total"]/10) + 1;
			data = data["msg"]["rows"];
			var sArr = [];
			if(l == 0){
				sStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>抱歉，暂无数据哦~</div>';
				$("#suportTable").html(sStr).show();
				return false;
			}
			for(var i=0;i<l;i++){
				sArr.push('<div class="pl_list clearfix">');
				sArr.push('<div class="clearfix fqr_xq" style="margin-top:0">');
				sArr.push('<div class="fl mr25">');
				if(data[i]["photo"]){
					sArr.push('<img src="'+ cv["fileAddress"] + '/' + data[i]["photo"] +'" width="50px" height="50px" style="border-radius: 50%;">');
				}else{
					sArr.push('<img src="'+ path+'/images/defaultPhoto.png" width="50px" height="50px" style="border-radius: 50%;">');
				}
				sArr.push('</div>');
				sArr.push('<div class="fl rg_txt" style="width:70%;">');
				sArr.push('<p><span class="color333">'+data[i]["supportNickName"]+'</span></p>');
				sArr.push('<p style="line-height: 30px;"><span class="color333 fr">'+data[i]["supportAmt"].toFixed(2)+companyCode+'</span><span class="color333">'+data[i]["supportTime"]+'</span></p>');
				sArr.push('</div>');
				sArr.push('</div>');
				sArr.push('</div>');
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
function span_move_fun(){
	$("#detailTab").on("swipeleft",function(){
		$(this).animate({left:'-300px'});
	});
	$("#detailTab").on("swiperight",function(){
		$(this).animate({left:'0px'});
	});
	$(".ui-loader-default").hide();
}

var slide = function (obj, offset, callback) {
    var start,
        end,
        isLock = false,//是否锁定整个操作
        isCanDo = false,//是否移动滑块
        isTouchPad = (/hp-tablet/gi).test(navigator.appVersion),
        hasTouch = 'ontouchstart' in window && !isTouchPad;

    //将对象转换为jquery的对象
    obj = $(obj);

    var objparent = obj.parent();

    /*操作方法*/
    var fn =
        {
            //移动容器
            translate: function (diff,left) {
                obj.css({
                	"width":diff+"%",
                	"left":left+"%",
                    "-webkit-transition": "width 2s,left 2s",
                    "transition": "width 2s,left 2s"
                });
            },
            //设置效果时间
            setTranslition: function (time,left) {
                obj.css({
                	"width":time+"%",
                	"left":left+"%",
                    "-webkit-transition": "width 2s,left 2s",
                    "transition": "width 2s,left 2s"
                });
            },
            //返回到初始位置
            back: function () {
                fn.translate(100,0);
                //标识操作完成
                isLock = false;
            }
        };

    //滑动开始
    obj.bind("touchstart", function (e) {

        if (objparent.scrollTop() <= 0 && !isLock) {
            var even = typeof event == "undefined" ? e : event;
            //标识操作进行中
            isLock = true;
            isCanDo = true;
            //保存当前鼠标Y坐标
            start = hasTouch ? even.touches[0].pageY : even.pageY;
            //消除滑块动画时间
            fn.setTranslition(100,0);
        }
    });

    //滑动中
    obj.bind("touchmove", function (e) {

        if (objparent.scrollTop() <= 0 && isCanDo) {

            var even = typeof event == "undefined" ? e : event;

            //保存当前鼠标Y坐标
            end = hasTouch ? even.touches[0].pageY : even.pageY;
            if (start < end) {
                even.preventDefault();
                //消除滑块动画时间
                fn.setTranslition(100,0);
                //移动滑块
                fn.translate(120,-20);
            }

        }
    });


    //滑动结束
    obj.bind("touchend", function (e) {
        if (isCanDo) {
            isCanDo = false;
            //判断滑动距离是否大于等于指定值
            if (end - start >= offset) {
                //设置滑块回弹时间
                fn.setTranslition(100,0);
                //保留提示部分
                fn.translate(100,0);

                //执行回调函数
                if (typeof callback == "function") {
                    callback.call(fn, e);
                }
            } else {
                //返回初始状态
                fn.back();
            }
        }
    });
}