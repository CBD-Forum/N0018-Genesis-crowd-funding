var allPageNum = 1;
var preheatPageNum = 1;
var fundingPageNum = 1;
var successPageNum = 1;
var isProjectPay = getQueryString("isProjectPay");
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	if(isProjectPay == "1"){
		$("#pName").text("项目融资");
		$("#xiangmuLi").html('<a class="col1" href="'+path+'/common/m-projectList-stock.html?isProjectPay=1"><p class="prol"></p>项目融资</a>');
	}else if(isProjectPay == "0"){
		$("#pName").text("项目融资");
		$("#xiangmuLi").html('<a class="col1" href="'+path+'/common/m-projectList-stock.html?isProjectPay=0"><p class="prol"></p>项目融资</a>');
	}
	if(userId == "null"){ //未登录
		$("#indexHw").html('<a href="'+path+'/common/m-login.html">登录</a>&nbsp;&nbsp;/&nbsp;&nbsp;<a href="'+path+'/common/m-register.html">注册</a>');
	}else{
		if(userPhoto == "null" || userPhoto == null || userPhoto == ""){
			$("#indexHw").html('<a href="'+path+'/common/m-myCenter.html"><img src="'+path+'/images/defaultPhoto.png" style="width:35px; height:35px;border-radius: 50%;" /></a>');
		}else{
			$("#indexHw").html('<a href="'+path+'/common/m-myCenter.html"><img src="'+cv["fileAddress"]+userPhoto+'" style="width:35px; height:35px;border-radius: 50%;" /></a>');
		}
	}
	$("#loadBody>div input").val("");
	$("#allMoreList").unbind("click").click(function(){
		getProjectList(allPageNum,"all");
	});
	$("#preheatMoreList").unbind("click").click(function(){
		getProjectList(preheatPageNum,"preheat");
	});
	$("#fundingMoreList").unbind("click").click(function(){
		getProjectList(fundingPageNum,"funding");
	});
	$("#successMoreList").unbind("click").click(function(){
		getProjectList(successPageNum,"success");
	});
	$("#backMoreList").unbind("click").click(function(){
		getProjectList(backPageNum,"back");
	});
	checkTab();
});
//项目分类选择
function checkTab(){
	var $tab = $("#loanTab li");
	$.each($tab,function(k,v){
		$(v).click(function(){
			var code  = $(this).attr("code");
			$tab.removeClass("cur");
			$(this).addClass("cur");
			$("#loadBody>div").hide();
			if($("#"+code+"Body input").val()){
				$("#"+code+"Body").show();
			}else{
				getProjectList(1,code);
			}
		});
	});
	$tab.first().click();
}

function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return ""; 
} 
//未认证的股权项目提示框
function showNoInvestor(){
	var at= (cv["winH"]-200)/2;
	$("#bgpop").show();
	$("#loanNoInvestor").css({"left":"2.5%","top":at+"px"}).show();
	
	$("#bgpop").click(function(){
		$("#bgpop").hide();
		$("#loanNoInvestor").hide();
	});
}
/**
 * 
 * @param page 页数
 * @param type 那种众筹
 * @param id 众筹分类
 */
function getProjectList(page,id){
	if(id == "all"){
		id="";
	}
	var lArr = [], lStr = '', l;
	var remainDays = 0;
	$.ajax({
		url: path + "/crowdfunding/getPageCrowdList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanType": "stock",
			"loanProcess": id,
			"sort": "releaseTime",
			"isProjectPay":isProjectPay,
			"page": page,
			"rows": 6
		},
		success: function(data){
			if(id == ""){
				id="all";
			}
			if(!data["success"]){
				lStr = '<div class="nodata">暂无数据</div>';
				$("#"+id+"Body .body").html(lStr);
				$("#moreList").hide();
				$("#"+id+"Body input").val(1);
				$("#"+id+"Body").show();
				return false;
			}else{
				l = data["msg"]["rows"].length, total = data["msg"]["total"], data = data["msg"]["rows"];
				if(l == 0){
					lStr = '<div class="nodata">暂无数据</div>';
					$("#"+id+"Body .body").html(lStr);
					$("#"+id+"Body input").val(1);
					$("#"+id+"Body .list_more").hide();
					$("#"+id+"Body").show();
					return false;
				}else{
					var authStr = 0;
					for(var i =0;i<l;i++){
						authStr = data[i]["authSet"];
						lArr.push('<div class="equity-info" style="margin:10px;">');
						lArr.push('<div class="equity-img">');
//						if(userId == "null"){
//							lArr.push('<a href="'+path+'/common/m-login.html">');
//						}else{
//							if(userLevel == "common"){
//								lArr.push('<a href="javascript:void(0);" onclick="showNoInvestor();">');
//							}else if(userLevel == "authed"){
//								if(parseInt(investorLevel) >= authStr){
//									lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
//								}else{
//									lArr.push('<a href="javascript:void(0);" onclick="showTip();">');
//								}
//							}else if(userLevel == "lead"){
//								lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
//							}
//						}
						if(userId == null|| userId == "null"){
							lArr.push('<a href="'+path+'/common/m-login.html">');
						}else{
							if(authStr == "allAuth"){
								lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
							}else if(authStr == "authed"){ //
								if(userLevel == "authed" || userLevel == "lead"){
									lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
								}else{
									lArr.push('<a href="javascript:void(0);" onclick="showNoInvestor(this)" message="合格投资人或专业投资人">');
								}
							}else if(authStr == "lead"){
								if(userLevel == "lead"){
									lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
								}else{
									lArr.push('<a href="javascript:void(0);" onclick="showNoInvestor(this)" message="专业投资人">');
								}
							}
						}
						lArr.push('<img src="'+cv["fileAddress"]+data[i]["loanLogo"]+'" style="width:100%; height:100%;" />');
						lArr.push('</a><div class="pop"></div>');
						/*if(userId == "null"){
							lArr.push('<a href="'+path+'/common/m-login.html" class="pop-f">'+data[i]["loanName"]+'</a>');
						}else{
							if(userLevel == "common"){
								lArr.push('<a href="javascript:void(0);" onclick="showNoInvestor();" class="pop-f">'+data[i]["loanName"]+'</a>');
							}else if(userLevel == "authed"){
								if(parseInt(investorLevel) >= authStr){
									lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'" class="pop-f">'+data[i]["loanName"]+'</a>');
								}else{
									lArr.push('<a href="javascript:void(0);" onclick="showTip();" class="pop-f">'+data[i]["loanName"]+'</a>');
								}
							}else if(userLevel == "lead"){
								lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'" class="pop-f">'+data[i]["loanName"]+'</a>');
							}
						}*/
						if(userId == null|| userId == "null"){
							lArr.push('<a href="'+path+'/common/m-login.html" class="pop-f">'+data[i]["loanName"]+'</a>');
						}else{
							if(authStr == "allAuth"){
								lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'" class="pop-f">'+data[i]["loanName"]+'</a>');
							}else if(authStr == "authed"){ //
								if(userLevel == "authed" || userLevel == "lead"){
									lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'" class="pop-f">'+data[i]["loanName"]+'</a>');
								}else{
									lArr.push('<a href="javascript:void(0);" onclick="showNoInvestor(this)" message="合格投资人或专业投资人" class="pop-f">'+data[i]["loanName"]+'</a>');
								}
							}else if(authStr == "lead"){
								if(userLevel == "lead"){
									lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'" class="pop-f">'+data[i]["loanName"]+'</a>');
								}else{
									lArr.push('<a href="javascript:void(0);" onclick="showNoInvestor(this)" message="专业投资人" class="pop-f">'+data[i]["loanName"]+'</a>');
								}
							}
						}
						lArr.push('</a>');
						lArr.push('</div>');
						lArr.push('<ul class="equity-jd">');
						if(data[i]["loanNo"] == '2015101230822759' || data[i]["loanNo"] == '2015101239688157'){
							lArr.push('<li>已筹资<span class="col1">￥'+formatCurrency(data[i]["fundAmt"])+'</span>');
						}else{
							lArr.push('<li>已筹资<span class="col1">￥'+formatCurrency(data[i]["approveAmt"])+'</span>');
							
						}
							if(data[i]["projectFinanceAmt"] == 0){
								var investBonusRatio = data[i]["investBonusRatio"]?(data[i]["investBonusRatio"]*100).toFixed(2):"0";
								lArr.push('<div style=" width: 50%; display: inline-block; float: right; text-align: right;">分红比例<span>'+investBonusRatio+'%</span></div>');
							}
								
						lArr.push('</li>');
						
						lArr.push('<li class="jd">');
						if(data[i]["loanNo"] == '2015101230822759' || data[i]["loanNo"] == '2015101239688157'){
							lArr.push('<div class="jdBar"><span style="width:100%" class="back2"></span></div>');
						}else{
							
							if(data[i]["supportRatio"]*100 > 100){
								lArr.push('<div class="jdBar"><span style="width:100%" class="back2"></span></div>');
							}else{
								lArr.push('<div class="jdBar"><span style="width:'+(data[i]["supportRatio"]*100).toFixed(0)+'%" class="back2"></span></div>');
							}
						}
						lArr.push('<p class="clearfix">');
						if(data[i]["loanNo"] == '2015101230822759' || data[i]["loanNo"] == '2015101239688157'){
							lArr.push('<span class="fl">100%</span>');
						}else{
							lArr.push('<span class="fl">'+(data[i]["supportRatio"]*100).toFixed(0)+'%</span>');
						}
						if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){//筹款结束
							remainDays = 0;
						}else{
							if(data[i]["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
								remainDays = data[i]["remainPreheatDays"] ? (data[i]["remainPreheatDays"] < 0 ? "0" : data[i]["remainPreheatDays"]) : "0" ;
							}else{
								remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
							}
						}
						lArr.push('<span class="fr">剩余'+remainDays+'天</span>');
						lArr.push('</p>');
						lArr.push('</li>');
						lArr.push('</ul>');
						lArr.push('<ul class="pro_bottom_ul">');
						if(userId == "null" || userId == null){
							lArr.push('<li class="like"><a href="'+path+'/common/m-login.html" class="zan"><em>'+data[i]["attentionNum"]+'</em></a></li>');
							lArr.push('<li class="apro"><a href="'+path+'/common/m-login.html" class="comment border0"><em>'+data[i]["commentNum"]+'</em></a></li>');
						}else{
							lArr.push('<li class="like"><a shijian="zanGzimg" loanid="'+data[i]["loanNo"]+'" href="javascript:void(0);" class="zan"><em name="gz'+id+(i+1)+'">'+data[i]["attentionNum"]+'</em></a></li>');
//							if(parseInt(investorLevel) >= authStr){
//								lArr.push('<li class="apro"><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'" class="pl border0"><em>'+data[i]["commentNum"]+'</em></a></li>');
//							}else{
//								lArr.push('<li class="apro"><a href="javascript:void(0);" onclick="showTip();" class="pl border0"><em>'+data[i]["commentNum"]+'</em></a></li>');
//							}
							if(authStr == "allAuth"){
								lArr.push('<li class="apro"><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'" class="pl border0"><em>'+data[i]["commentNum"]+'</em></a></li>');
							}else if(authStr == "authed"){ //
								if(userLevel == "authed" || userLevel == "lead"){
									lArr.push('<li class="apro"><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'" class="pl border0"><em>'+data[i]["commentNum"]+'</em></a></li>');
								}else{
									lArr.push('<li class="apro"><a href="javascript:void(0);" onclick="showNoInvestor(this);" message="合格投资人或专业投资人" class="pl border0"><em>'+data[i]["commentNum"]+'</em></a></li>');
								}
							}else if(authStr == "lead"){
								if(userLevel == "lead"){
									lArr.push('<li class="apro"><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'" class="pl border0"><em>'+data[i]["commentNum"]+'</em></a></li>');
								}else{
									lArr.push('<li class="apro"><a href="javascript:void(0);" onclick="showNoInvestor(this);" message="专业投资人" class="pl border0"><em>'+data[i]["commentNum"]+'</em></a></li>');
								}
							}
						}
						lArr.push('</ul>');
						lArr.push('</div>');
					}
					lStr = lArr.join("");
					$("#"+id+"Body .body").append(lStr);
					$("#"+id+"Body .list_more").hide();
					$("#"+id+"Body input").val(1);
					$("#"+id+"Body").show();
					if(id == "all"){
						allPageNum++;
					}else if(id == "preheat"){
						preheatPageNum++;
					}else if(id == "funding"){
						fundingPageNum++;
					}else if(id == "success"){
						successPageNum++;
					}else if(id == "back"){
						backPageNum++;
					}
					$("a[shijian=zanGzimg]").click(function(){
						attentionLoan($(this).attr("loanid"),$(this).children("em").attr("name"));
					});
					
					//判断如果已经没有可加载的项目，隐藏查看更多按钮
					if($("#"+id+"Body .body>div").length == total){
						$("#"+id+"MoreList").remove();
					}else{
						$("#"+id+"MoreList").show();
					}
				}
			}
		},
		error: function(request){
			console.log("获取项目列表异常");
		}
	});
}
//关注（赞）事件
function attentionLoan(loanNo,name){
	if(userId == "null"){
		AlertDialog.tip("您还没有登录，请登录！");
		return false;
	}
	$.ajax({
		url: path + "/crowdfunding/attentionLoan.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				AlertDialog.tip(data["msg"]);
				return false;
			}else{
				var nvalue = Number($("em[name="+name+"]").text());
				$("em[name="+name+"]").html(nvalue+1);
				AlertDialog.tip("关注成功");
			}
		},
		error: function(request){
			console.log("关注项目异常");
		}
	});
}
function showTip(){
	$("#bgpop").show();
	AlertDialog.tip("您的权限等级无法查看项目详情");
}