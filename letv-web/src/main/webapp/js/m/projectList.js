var allPageNum = 1,loanName="",type="";
type = getQueryString("type");
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	if(userId == "null"){ //未登录
		$("#indexHw").html('<a style="display:inline-block; height:90px; line-height:90px;" href="'+path+'/common/m-login.html">登录</a>&nbsp;&nbsp;/&nbsp;&nbsp;<a style="display:inline-block; height:90px; line-height:90px; margin-right:20px;" href="'+path+'/common/m-register.html">注册</a>');
		$("#myCenter").attr("href",path+"/common/m-login.html");
	}else{
		if(userPhoto == "null" || userPhoto == null || userPhoto == ""){
			$("#indexHw").html('<a class="user_p" href="'+path+'/common/m-myCenter.html"><img src="'+path+'/images/defaultPhoto.png" style="width:61px; height:61px;border-radius: 50%;" /></a>');
		}else{
			$("#indexHw").html('<a class="user_p" href="'+path+'/common/m-myCenter.html"><img src="'+cv["fileAddress"]+userPhoto+'" style="width:61px; height:61px;border-radius: 50%;" /></a>');
		}
		$("#myCenter").attr("href",path+"/common/m-myCenter.html");
	}
	checkTab();
	
});
function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return ""; 
}
function checkTab(){
	var $typeTab = $("#loanType a");
	$.each($typeTab,function(k,v){
		$(v).unbind("click").click(function(){
			$typeTab.removeClass("a_cur");
			$(v).addClass("a_cur");
			$("#loanType").hide();
			if($(v).attr("code") == "public_service" || $(v).attr("code") == "entity"){
				$("#loanProcess a[code='preheat']").hide();
			}else{
				$("#loanProcess a[code='preheat']").show();
			}
		//	$("#loanPage a").text("点击查看更多>>");
			$("#loanTypeA").removeClass("cur").attr("type","0");
			allPageNum = 1;
			getProjectList(allPageNum);
		});
	});
	var $processTab = $("#loanProcess a");
	$.each($processTab,function(k,v){
		$(v).click(function(){
			$processTab.removeClass("a_cur");
			$(v).addClass("a_cur");
			$("#loanProcess").hide();
		//	$("#loanPage a").text("点击查看更多>>");
			$("#loanProcessA").removeClass("cur").attr("type","0");
			allPageNum = 1;
			getProjectList(allPageNum);
		});
	});
	if(type){
		$("#loanType a[code="+type+"]").click();
	}else{
		$typeTab.first().click();
	}
	
	$("#loanTypeA").click(function(){
		if($(this).attr("type") == "0"){
			$(this).addClass("cur");
			$("#loanType").show();
			$(this).attr("type","1");
		}else{
			$(this).removeClass("cur");
			$("#loanType").hide();
			$(this).attr("type","0");
		}
		$("#loanProcess").hide();
		$("#loanProcessA").removeClass("cur").attr("type","0");
	});
	
	$("#loanProcessA").click(function(){
		if($(this).attr("type") == "0"){
			$(this).addClass("cur");
			$("#loanProcess").show();
			$(this).attr("type","1");
		}else{
			$(this).removeClass("cur");
			$("#loanProcess").hide();
			$(this).attr("type","0");
		}
		$("#loanTypeA").removeClass("cur").attr("type","0");
		$("#loanType").hide();
	});
	$("#projectList *").click(function(){
		$("#loanProcess").hide();
		$("#loanProcessA").removeClass("cur").attr("type","0");
		$("#loanTypeA").removeClass("cur").attr("type","0");
		$("#loanType").hide();
	});
	//搜索
	$("#topSearch").click(function(){
		allPageNum = 1;
		getProjectList(allPageNum);
	});
	
	//分页
//	$("#loanPage a").unbind("click").click(function(){
//		getProjectList(allPageNum);
//	});
	var totalheight = 0; 
	$(window).scroll(function(){
        totalHeight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
		if(($(document).height()-100)<=totalHeight){
			allPageNum++;
			getProjectList(allPageNum);
		}
   });
}

function getProjectList(page){
	loanName = $("#loanName").val();
	var lArr = [], lStr = '', l, total,loanType,loanProcess;
	loanType = $("#loanType a.a_cur").attr("code");
	loanProcess = $("#loanProcess a.a_cur").attr("code");
	var remainDays = 0;
	$.ajax({
		url: path + "/crowdfunding/getPageCrowdList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanType": loanType,
			"loanProcess": loanProcess,
			"loanName":loanName,
			"order":"desc",
			"page": page,
			"rows": 6
		},
		success: function(data){
			if(!data["success"]){
				lStr = '<div style="padding:30px;color:red; font-size:24px; text-align:center;">暂无数据</div>';
				$("#projectList").html(lStr);
				$("#loanPage").hide();
				return false;
			}
			total = data["msg"]["total"],l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
//				lStr = '<div style="padding:30px;color:red; font-size:24px; text-align:center">暂无数据</div>';
//				$("#projectList").append(lStr);
				return false;
			}
			for(var i =0;i<l;i++){
				lArr.npush('<li class="prolist_li">');
				lArr.npush('<div class="img">');
				if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){ //如果是产品项目
                	lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
                }else{
					if(data[i]["loanType"] == "stock"){
						if(userId == "null"){
							lArr.push('<a href="'+path+'/common/m-login.html">');
						}else{
							lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
						}
					}else{
						lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
					}
				}
				lArr.npush('<img src="'+cv["fileAddress"]+data[i]["loanLogo"]+'"/></div>');
				lArr.npush('<div class="list_ul_pop"></div>');
				lArr.npush('<div class="list_ul_font">');
				lArr.npush('<div class="ul_font_top">');
				lArr.npush('<p class="p1">');
				if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){ //如果是产品项目
                	lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
                }else{
					if(data[i]["loanType"] == "stock"){
						if(userId == "null"){
							lArr.push('<a href="'+path+'/common/m-login.html">');
						}else{
							lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
						}
					}else{
						lArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
					}
				}
				lArr.npush('#0</a></p>',[data[i]["loanName"]]);
				lArr.npush('<p class="p2">#0</p>',[data[i]["loanDes"]]);
				lArr.npush('</div>');
				lArr.npush('<div class="ul_font_bottom">');
				lArr.npush('<p>已融资：￥'+data[i]["approveAmt"].toFixed(2)+'</p>');
				lArr.npush('<p>');
				lArr.npush('<span class="fl">项目进度：</span>');
				if(data[i]["supportRatio"]*100 > 100){
					lArr.npush('<span class="jdBar"><span style="width:100%"></span></span>');
				}else{
					lArr.npush('<span class="jdBar"><span style="width:'+(data[i]["supportRatio"]*100).toFixed(0)+'%"></span></span>');
				}
				lArr.npush('<span class="fl fs20">'+(data[i]["supportRatio"]*100).toFixed(0)+'%</span>');
				lArr.npush('</p>');
				lArr.npush('<p>融资目标：￥'+data[i]["fundAmt"]+'</p>');
				if(data[i]["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
					remainDays = data[i]["remainPreheatDays"] ? (data[i]["remainPreheatDays"] < 0 ? "0" : data[i]["remainPreheatDays"]) : "0" ;
				}else{
					remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
				}
				if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
					lArr.npush('<p>筹款成功</p>');
				}else{
					lArr.npush('<p>剩余时间：'+remainDays+'天</p>');
				}
				lArr.npush('</div>');
				lArr.npush('<div class="list_ul_pop"></div>');
				lArr.npush('</div>');
				lArr.npush('</li>');
			}
			lStr = lArr.join("");
			
			if(page == 1){
				$("#projectList").html(lStr);
			}else{
				$("#projectList").append(lStr);
			}
		//	allPageNum++;
			var proLen = var proLen = $("#commentUl>div>div").length;
			if(proLen == total){
				$("#loanPage").show();
				$("#loanPage a").text("更多精彩项目，敬请期待");
				if(proLen < 10 &&  proLen > 0){
					$("#loanPage").hide();
				}
				return false;
			}else{
				$("#loanPage").show();
			}
		},
		error: function(request){
			console.log("获取项目列表异常");
		}
	});
}
