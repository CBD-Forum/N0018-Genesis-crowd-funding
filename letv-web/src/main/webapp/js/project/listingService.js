var param = window.location.search;
var LoanType = "", loanName = "";
if(param){
	var base64 = new Base64();
	LoanType = getQueryString('type');
	loanName = base64.decode(getQueryString('loanName'));
}
$(function(){
	searchType();//通过条件查询项目列表信息
	$("#listingSearchBtn").click(loanListingSearch);
});
//通过点击查询条件查询项目列表
function searchType(){
	$.each($("#searchType").find("a"),function(k, v){
		$(v).click(function(){
			loanName ='';
			$(this).parent().parent().find("a").removeClass("cur");
			$(this).addClass("cur");
			getProjectList(1);
		});
	});
	//获取url内容，筛选列表
	if(LoanType){
		$("#searchType").find("a[code='"+LoanType+"']").click();
	}else{
		getProjectList(1); //查询项目列表
	}
}
function loanListingSearch(){
	if(window.location.href.indexOf("listingService") != "-1"){ //当前在项目列表页
		listingSearchList(1);
	}else{
		var base64 = new Base64();
		window.location.href = path + "/common/listingService.html?loanName=" + base64.encode($("#topSearch").val());
	}
}
/**
 *   查询项目列表
 */
function getProjectList(page){
	var loanType = "", loanProcess = "", sort = "defaultSort";
	loanProcess = $("#loanProcess").find("a.cur").attr("code");
	//如果选择了热门项目
	if($("#loanProcess").find("a[code='hot']").hasClass("cur")){
		loanProcess = "";
		sort = "hot";
	}else{
		loanProcess = $("#loanProcess").find("a.cur").attr("code");
		sort = $("#sort").find("a.cur").attr("code");
	}
	var lArr = [], lStr = '', l, sumPage;
	var remainDays = 0;
	$.ajax({
		url: path + "/crowdfunding/getPageCrowdList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanProcess": loanProcess,
			"loanName":loanName,
			"sort": sort,
			"page": page,
			"rows": 6
		},
		success: function(data){
			if(!data["success"]){
				lStr = '<div style="padding:30px;color:red;">暂无数据</div>';
				$("#projectList>ul").html(lStr);
				$("div.page").hide();
				return false;
			}else{
				sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
				l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					lStr = '<div style="padding:30px;color:red;">暂无数据</div>';
					$("#projectList>ul").html(lStr);
					$("div.page").hide();
					return false;
				}else{
					for(var i=0;i<l;i++){
						
						lArr.push('<li class="list_ul_big">');
						lArr.push('<div class="img"><img src="'+cv["fileAddress"]+data[i]["loanLogo"]+'"/></div>');
	                	lArr.push('<div class="list_ul_pop"></div>');
	                    lArr.push('<div class="list_ul_font">');
	                    lArr.push('<div class="ul_font_top">');
	                    
	                    if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){ //如果是产品项目
	                    	lArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
	                    }else{
							if(data[i]["loanType"] == "stock"){
								if(siteUserId == "null"){
									lArr.push('<a target="_blank" href="javascript:void(0);" onclick="go2Login();">');
								}else{
									lArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
								}
							}else{
								lArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
							}
						}
	                    
	                    lArr.push('<p class="p1">'+data[i]["loanName"]+'</p>');
                        lArr.push('<p class="p2">'+data[i]["loanDes"]+'</p>');
                        lArr.push('</a>');
                        lArr.push('</div>');
                        lArr.push('<div class="ul_font_bottom">');
                        lArr.push('<p>已认购：￥'+data[i]["approveAmt"]+'</p>');
                    	lArr.push('<p style="width:250px;">');
                        lArr.push('<span class="fl">挂牌进度：</span>');
                        
                        if(data[i]["supportRatio"]*100 > 100){
                        	lArr.push(' <span class="jdBar"><span style="width:100%"></span></span>');
                        	lArr.push('<span class="fl fs12">100%</span>');
						}else{
							lArr.push(' <span class="jdBar"><span style="width:'+(data[i]["supportRatio"]*100).toFixed(0)+'%"></span></span>');
							lArr.push('<span class="fl fs12">'+(data[i]["supportRatio"]*100).toFixed(0)+'%</span>');
						}
                        
                        lArr.push('</p>');
                        lArr.push('<p>挂牌价格：￥'+data[i]["fundAmt"]+'</p>');
                        lArr.push('<p style="width:150px">挂牌股权比例：4%</p>');
                        if(data[i]["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
							remainDays = data[i]["remainPreheatDays"] ? (data[i]["remainPreheatDays"] < 0 ? "0" : data[i]["remainPreheatDays"]) : "0" ;
						}else{
							remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
						}
						if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
							lArr.push('<p style="width:150px">筹款成功</p>');
						}else{
							lArr.push('<p style="width:150px">剩余时间：'+remainDays+'天</p>');
						}

                        lArr.push('</div>');
                        lArr.push('</div>');
	                    lArr.push('</li>');
						
					}
					lStr = lArr.join("");
					$("#projectList>ul").html(lStr);
					$("div.page").show();
					$("#projectList>ul>li").mouseover(function(){
						$(this).find("div[name='s1']").hide();
						$(this).find("div[name='s2']").show();
					}).mouseout(function(){
						$(this).find("div[name='s1']").show();
						$(this).find("div[name='s2']").hide();
					});
					//分页设置
					pagePause = 0;
					if(page < 2){
						$("div.page").jPages({
							containerID : "projectList",
							clickStop   : true,
							perPage	: 6,
							allSumPage : sumPage,
							callback: ajaxPageData
						});
					}
				}
			}
		},
		error: function(request){
			console.log("获取项目列表异常");
		}
	});
}
//分页回调函数
function ajaxPageData(obj){
	if(pagePause == 0){
		return false;
	}
	getProjectList(obj["current"]);
}

/**
 *  在网站顶部查询众筹项目列表
 */
function listingSearchList(page){
	var lArr = [], lStr = '', l, sumPage;
	var remainDays = 0;
	$.ajax({
		url: path + "/crowdfunding/getPageCrowdList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanName": $("#listingSearch").val(),
			"sort": "defaultSort",
			"page": page,
			"rows": 6
		},
		success: function(data){
			if(!data["success"]){
				lStr = '<div style="padding:30px;color:red;">暂无数据</div>';
				$("#projectList").html(lStr);
				$("div.page").hide();
				return false;
			}else{
				sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
				l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					lStr = '<div style="padding:30px;color:red;">暂无数据</div>';
					$("#projectList").html(lStr);
					$("div.page").hide();
					return false;
				}else{
					for(var i=0;i<l;i++){
						lArr.push('<li class="list_ul_big">');
						lArr.push('<div class="img"><img src="'+cv["fileAddress"]+data[i]["loanLogo"]+'"/></div>');
	                	lArr.push('<div class="list_ul_pop"></div>');
	                    lArr.push('<div class="list_ul_font">');
	                    lArr.push('<div class="ul_font_top">');
	                    
	                    if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){ //如果是产品项目
	                    	lArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
	                    }else{
							if(data[i]["loanType"] == "stock"){
								if(siteUserId == "null"){
									lArr.push('<a target="_blank" href="javascript:void(0);" onclick="go2Login();">');
								}else{
									lArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
								}
							}else{
								lArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
							}
						}
	                    
	                    lArr.push('<p class="p1">'+data[i]["loanName"]+'</p>');
                        lArr.push('<p class="p2">'+data[i]["loanDes"]+'</p>');
                        lArr.push('</a>');
                        lArr.push('</div>');
                        lArr.push('<div class="ul_font_bottom">');
                        lArr.push('<p>已认购：￥'+data[i]["approveAmt"]+'</p>');
                    	lArr.push('<p style="width:250px;">');
                        lArr.push('<span class="fl">挂牌进度：</span>');
                        
                        if(data[i]["supportRatio"]*100 > 100){
                        	lArr.push(' <span class="jdBar"><span style="width:100%"></span></span>');
                        	lArr.push('<span class="fl fs12">100%</span>');
						}else{
							lArr.push(' <span class="jdBar"><span style="width:'+(data[i]["supportRatio"]*100).toFixed(0)+'%"></span></span>');
							lArr.push('<span class="fl fs12">'+(data[i]["supportRatio"]*100).toFixed(0)+'%</span>');
						}
                        
                        lArr.push('</p>');
                        lArr.push('<p>挂牌价格：￥'+data[i]["fundAmt"]+'</p>');
                        lArr.push('<p style="width:150px">挂牌股权比例：4%</p>');
                        if(data[i]["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
							remainDays = data[i]["remainPreheatDays"] ? (data[i]["remainPreheatDays"] < 0 ? "0" : data[i]["remainPreheatDays"]) : "0" ;
						}else{
							remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
						}
						if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
							lArr.push('<p style="width:150px">筹款成功</p>');
						}else{
							lArr.push('<p style="width:150px">剩余时间：'+remainDays+'天</p>');
						}

                        lArr.push('</div>');
                        lArr.push('</div>');
	                    lArr.push('</li>');
					}
					lStr = lArr.join("");
					$("#projectList>ul").html(lStr);
					$("div.page").show();
					$("#projectList>ul>li").mouseover(function(){
						$(this).find("div[name='s1']").hide();
						$(this).find("div[name='s2']").show();
					}).mouseout(function(){
						$(this).find("div[name='s1']").show();
						$(this).find("div[name='s2']").hide();
					});
					//分页设置
					pagePause = 0;
					if(page < 2){
						$("div.page").jPages({
							containerID : "projectList",
							clickStop   : true,
							perPage	: 6,
							allSumPage : sumPage,
							callback: ajaxPageDataA
						});
					}
				}
			}
		},
		error: function(request){
			console.log("获取项目列表异常");
		}
	});
}
//分页回调函数
function ajaxPageDataA(obj){
	if(pagePause == 0){
		return false;
	}
	listingSearchList(obj["current"]);
}
