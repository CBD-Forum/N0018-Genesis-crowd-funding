var showObj = {
		"hot": "热门项目",
		"preheat": "正在挂牌",
		"funding": "正在众筹",
		"success": "成功案例",
		"back": "回报项目"
};
var state = getQueryString("state");
$(function(){
	$("#breadWord").text(showObj[state]);//标题根据Url类型展示
	$("#breadTitle").text(showObj[state]);//标题根据Url类型展示
	index2ScrollLoan();
	if(state == "hot"){ //热门项目
		getIndexLoanList("public_service", "", "hot", "indexPublic_serviceList"); //中间页公益项目
		getIndexLoanList("entity", "", "hot", "indexEntityList"); //中间页奖励项目
		getIndexLoanList("stock", "", "hot", "indexStockList"); //中间页股权项目
		getIndexLoanList("house", "", "hot", "indexHouseList"); //中间页房产项目
	}else{
		getIndexLoanList("public_service", state, "defaultSort", "indexPublic_serviceList"); //中间页公益项目
		getIndexLoanList("entity", state, "defaultSort", "indexEntityList"); //中间页奖励项目
		getIndexLoanList("stock", state, "defaultSort", "indexStockList"); //中间页股权项目
		getIndexLoanList("house", state, "defaultSort", "indexHouseList"); //中间页房产项目
	}
	scrollLeftNav();
});
function scrollLeftNav(){
	$(window).scroll(function(){
		if($(document).scrollTop() > $("#public_serviceLoanIndex").offset().top-10 && $(document).scrollTop() < $("#entityLoanIndex").offset().top){
			$("#indexScrollLoan").find("li").removeClass("cur");
			$("#indexScrollLoan").find("img").attr("src", path + "/images/dian_bai.png");
			$("#indexScrollLoan").find("li").eq(0).addClass("cur");
			$("#indexScrollLoan").find("img").eq(0).attr("src", path + "/images/dian_lan.png");
		}else if($(document).scrollTop() > $("#entityLoanIndex").offset().top-10 && $(document).scrollTop() < $("#stockLoanIndex").offset().top){
			$("#indexScrollLoan").find("li").removeClass("cur");
			$("#indexScrollLoan").find("img").attr("src", path + "/images/dian_bai.png");
			$("#indexScrollLoan").find("li").eq(1).addClass("cur");
			$("#indexScrollLoan").find("img").eq(1).attr("src", path + "/images/dian_lan.png");
		}else if($(document).scrollTop() > $("#stockLoanIndex").offset().top-10 && $(document).scrollTop() < $("#houseLoanIndex").offset().top){
			$("#indexScrollLoan").find("li").removeClass("cur");
			$("#indexScrollLoan").find("img").attr("src", path + "/images/dian_bai.png");
			$("#indexScrollLoan").find("li").eq(2).addClass("cur");
			$("#indexScrollLoan").find("img").eq(2).attr("src", path + "/images/dian_lan.png");
		}else if($(document).scrollTop() > $("#houseLoanIndex").offset().top-10){
			$("#indexScrollLoan").find("li").removeClass("cur");
			$("#indexScrollLoan").find("img").attr("src", path + "/images/dian_bai.png");
			$("#indexScrollLoan").find("li").eq(4).addClass("cur");
			$("#indexScrollLoan").find("img").eq(4).attr("src", path + "/images/dian_lan.png");
		}
	});
}
//首页控制项目来回跑
function index2ScrollLoan(){
	//首页左侧导航条定位
	var dl = (cv["winW"]-1200)/2-8;
	var ot;
	var navTop = $("#public_serviceLoanIndex").offset().top - 22;
	if(cv["winW"] <= 1366){ //窄屏幕
		$("#indexScrollLoan").css({"position": "absolute", "top":navTop+"px"});
	}else if(cv["winW"] > 1366 && cv["winW"] <= 1600){
		$("#indexScrollLoan").css({"position": "absolute", "top":navTop+"px", "left":"50px"});
	}else{
		$("#indexScrollLoan").css({"position": "absolute", "top":navTop+"px", "left":"100px"});
	}
	$("#indexScrollLoan").find("li").click(function(){
		$(this).siblings().removeClass("cur");
		$(this).addClass("cur");
		$(this).parent().find("img").attr("src", path + "/images/dian_bai.png");
		$(this).children("img").attr("src", path + "/images/dian_lan.png");
		ot = $($(this).children("a").attr("scroll")).offset().top;
		$('html,body').animate({
			scrollTop : ot + 'px'
		}, 800);
	});
	$(window).scroll(function(){
		if(cv["winW"] <= 1366){
			if($(document).scrollTop() > navTop){
				$("#indexScrollLoan").css({"position":"fixed", "left":"0px", "top":"0px"});
			}else{
				$("#indexScrollLoan").css({"position":"absolute", "left":"0px", "top":navTop+"px"});
			}
		}else if(cv["winW"] > 1366 && cv["winW"] <= 1600){
			if($(document).scrollTop() > navTop){
				$("#indexScrollLoan").css({"position":"fixed", "left":"50px", "top":"0px"});
			}else{
				$("#indexScrollLoan").css({"position":"absolute", "left":"50px", "top":navTop+"px"});
			}
		}else{
			if($(document).scrollTop() > navTop){
				$("#indexScrollLoan").css({"position":"fixed", "left":"100px", "top":"0px"});
			}else{
				$("#indexScrollLoan").css({"position":"absolute", "left":"100px", "top":navTop+"px"});
			}
		}
	});
}
/**
 * 获取首页的项目列表 ， 默认loanType为全部
 * loanProcess : 项目进程
 * sort: 排序
 * id: 预加载的html id
 */
function getIndexLoanList(type, process, sort, id){
	var iArr = [], iStr = '', l;
	var remainDays = 0;
	$.ajax({
		url: path + "/crowdfunding/getPageCrowdList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanType": type,
			"loanProcess": process,
			"sort": sort,
			"page": 1,
			"rows": 3
		},
		success: function(data){
			if(!data["success"]){
				iStr = '<div style="padding:30px;color:red;">暂无数据</div>';
				$("#" + id).html(iStr);
				return false;
			}else{
				l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					iStr = '<div style="padding:30px;color:red;">暂无数据</div>';
					$("#" + id).html(iStr);
					return false;
				}else{
					for(var i=0;i<l;i++){
//						if(i < 2){
//							iArr.push('<li style="margin-right:150px;">');
//						}else{
//							iArr.push('<li>');
//						}
//						if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){ //如果是公益项目或奖励项目
//							iArr.push('<div class="img"><a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'"><img alt="'+data[i]["loanName"]+'" src="'+cv["fileAddress"]+data[i]["loanLogo"]+'" /></a></div>');
//							iArr.push('<a target="_blank" class="pTitle" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a>');
//						}else{
//							iArr.push('<div class="img"><a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'"><img alt="'+data[i]["loanName"]+'" src="'+cv["fileAddress"]+data[i]["loanLogo"]+'" /></a></div>');
//							iArr.push('<a target="_blank" class="pTitle" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">'+data[i]["loanName"]+'</a>');
//						}
//						iArr.push('<div class="ycz">已筹资<span>￥'+formatCurrency(data[i]["approveAmt"])+'</span>');
//						if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
//							iArr.push('<em>已成功</em>');
//						}
//						iArr.push('</div>');
//						if(data[i]["supportRatio"]*100 > 100){
//							iArr.push('<div class="jdBar"><span style="width:100%"></span></div>');
//						}else{
//							iArr.push('<div class="jdBar"><span style="width:'+(data[i]["supportRatio"]*100).toFixed(0)+'%"></span></div>');
//						}
//						iArr.push('<div class="jdWrite clearfix">');
//						iArr.push('<span class="span1">'+(data[i]["supportRatio"]*100).toFixed(2)+'%</span>');
//						if(data[i]["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
//							remainDays = data[i]["remainPreheatDays"] ? (data[i]["remainPreheatDays"] < 0 ? "0" : data[i]["remainPreheatDays"]) : "0" ;
//						}else{
//							remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
//						}
//						//iArr.push('<span class="span2 fr">剩余'+remainDays+'天</span>');
//						if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
//							iArr.push('<span class="span2 fr">筹款成功</span>');
//						}else{
//						
//							iArr.push('<span class="span2 fr">剩余'+remainDays+'天</span>');
//						}
//						iArr.push('</div>');
//						iArr.push('<div class="control">');
//						if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){ //如果是公益项目或奖励项目
//							iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'"><span class="like">'+data[i]["attentionNum"]+'</span></a>');
//							iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'"><span class="comment">'+data[i]["commentNum"]+'</span></a>');
//						}else{
//							if(data[i]["loanType"] == "stock"){
//								if(siteUserId == "null"){
//									iArr.push('<a href="javascript:void(0);" onclick="go2Login();"><span class="like">'+data[i]["attentionNum"]+'</span></a>');
//									iArr.push('<a href="javascript:void(0);" onclick="go2Login();"><span class="comment">'+data[i]["commentNum"]+'</span></a>');
//								}else{
//									iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'"><span class="like">'+data[i]["attentionNum"]+'</span></a>');
//									iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'"><span class="comment">'+data[i]["commentNum"]+'</span></a>');
//								}
//							}else{
//								iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'"><span class="like">'+data[i]["attentionNum"]+'</span></a>');
//								iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'"><span class="comment">'+data[i]["commentNum"]+'</span></a>');
//							}
//						}
//						iArr.push('</div></li>');
//					}
						if(i<3){
							iArr.push('<li class="list_ul_success">');
							iArr.push('<div class="img">');
							if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){ //如果是产品项目
								iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
							}else{
								if(data[i]["loanType"] == "stock"){
//									if(siteUserId == "null"){
//										iArr.push('<a target="_blank" href="javascript:void(0);" onclick="go2Login();">');
//									}else{
										iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
//									}
								}else{
									iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
								}
							}
							iArr.push('<img class="imgwh" src="'+cv["fileAddress"]+data[i]["loanLogo"]+'"/></a></div>');
							iArr.push('<div class="list_ul_pop_1"></div>');
							iArr.push('<div class="list_ul_font_1">');
							iArr.push('<div class="ul_font_top" style="height:40px;">');
							if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){ //如果是产品项目
								iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
							}else{
								if(data[i]["loanType"] == "stock"){
//									if(siteUserId == "null"){
//										iArr.push('<a target="_blank" href="javascript:void(0);" onclick="go2Login();">');
//									}else{
										iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
//									}
								}else{
									iArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
								}
							}
							iArr.push('<p class="p1">'+data[i]["loanName"]+'</p>');
							if(data[i]["loanDes"]){
								iArr.push('<p class="p2">'+data[i]["loanDes"]+'</p>');
							}
							iArr.push('</a></div>');
							iArr.push('<div class="ul_font_bottom">');
							if(process == "hot" || process == "success" || process == "funding" || process == ""){
								iArr.push('<p>已融资：￥'+data[i]["approveAmt"]+'</p>');
								iArr.push(' <p style="width:220px;">');
								iArr.push('<span class="fl">项目进度：</span>');
								if(data[i]["supportRatio"]*100 > 100){
									iArr.push(' <span class="jdBar_1"><span style="width:100%"></span></span>');
								}else{
									iArr.push(' <span class="jdBar_1"><span class="spancolor" style="width:'+(data[i]["supportRatio"]*100).toFixed(0)+'%"></span></span>');
								}
								iArr.push('<span class="fl fs12">'+(data[i]["supportRatio"]*100).toFixed(2)+'%</span>');
								iArr.push('</p>');
								iArr.push('</br>');	
								iArr.push('<p style="margin-top:10px;">融资目标：￥'+data[i]["fundAmt"]+'</p>');
								
								if(data[i]["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
									remainDays = data[i]["remainPreheatDays"] ? (data[i]["remainPreheatDays"] < 0 ? "0" : data[i]["remainPreheatDays"]) : "0" ;
								}else{
									remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
								}
								if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
									iArr.push('<p  style="margin-top:10px;">'+data[i]["loanStateName"]+'</p>');
								}else{
									iArr.push('<p style="margin-top:10px;">剩余时间：'+remainDays+'天</p>');
								}
							}else{
								iArr.push('<p style="margin-top:22px;">已融资：￥'+data[i]["approveAmt"]+'</p>');
								iArr.push('<p  style="margin-top:22px;">融资目标：￥'+data[i]["fundAmt"]+'</p>');
							}
							iArr.push('</div>');
							iArr.push('</div>');
							iArr.push('</li>');
						}	
					
					}
					iStr = iArr.join("");
					$("#" + id).html(iStr);
				}
			}
		},
		error: function(request){
			console.log("获取首页项目列表异常");
		}
	});
}