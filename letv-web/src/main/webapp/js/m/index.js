var pageNum = 1;
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
	showBanner();//首页banner
//	$("#moreList").unbind("click").click(function(){
//		getIndexPorjectList(pageNum);
//	});
	getIndexPorjectList(pageNum);//首页项目列表
	
	var totalheight = 0; 
	$(window).scroll(function(){
        totalHeight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
		if(($(document).height()-100)<=totalHeight){
			pageNum++;
			getIndexPorjectList(pageNum);
		}
   });
	
});
//banner
function showBanner(){
	$.ajax({
		url: path + "/banner/getBannerByCode.html",
		type: "post",
		dataType: "json",
		data: {"code": "mobile_banner"},
		success: function(data){
			var bArr = [], bStr = '', l = data.length;
			for(var i=0;i<l;i++){
				bArr.push('<li>');
				bArr.push('<img src="'+cv.fileAddress + data[i]["picture"]+'" width="100%" />');
				bArr.push('</li>');
			}
			bStr = bArr.join("");
			$("#banner_pig").html(bStr);
			TouchSlide({ 
				slideCell:"#slideBox",
				titCell:".hd ul",
				mainCell:".bd ul", 
				effect:"leftLoop", 
				autoPage:true //自动分页
			});
		},
		error: function(request){
			console.log("获取banner图片信息异常");
		}
	});
}
//首页项目列表
function getIndexPorjectList(page){
	var iArr = [], iStr = '';
	var remainDays = 0;
	$.ajax({
		url: path + "/crowdfunding/getPageCrowdList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanProcess":"hot",
			"sort": "hot",
			"page": page,
			"rows": 2
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}else{
				var l = data["msg"]["rows"].length,  total = data["msg"]["total"],data = data["msg"]["rows"];
				if(l == 0){
					return false;
				}
				for(var i=0; i<l; i++){
					iArr.npush('<li class="prolist_li">');
					iArr.npush('<div class="img">');
					if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){ //如果是产品项目
                    	iArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
                    }else{
						if(data[i]["loanType"] == "stock"){
//							if(userId == "null"){
//								iArr.push('<a href="'+path+'/common/m-login.html">');
//							}else{
								iArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
//							}
						}/*else{
							iArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
						}*/
					}
					iArr.npush('<img src="'+cv["fileAddress"]+data[i]["loanLogo"]+'"/>');
					iArr.npush('</a></div>');
					iArr.npush('<div class="list_ul_pop"></div>');
					iArr.npush('<div class="list_ul_font">');
					iArr.npush('<div class="ul_font_top">');
					iArr.npush('<p class="p1">');
					if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){ //如果是产品项目
                    	iArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
                    }else{
						if(data[i]["loanType"] == "stock"){
//							if(userId == "null"){
//								iArr.push('<a href="'+path+'/common/m-login.html">');
//							}else{
								iArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
//							}
						}/*else{
							iArr.push('<a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
						}*/
					}
					iArr.npush('#0</a></p>',[data[i]["loanName"]]);
//					iArr.npush('<p class="p2">#0</p>',[data[i]["loanDes"]]);
					iArr.npush('</div>');
					iArr.npush('<div class="ul_font_bottom">');
					iArr.npush('<p>已融资：￥'+data[i]["approveAmt"].toFixed(2)+'</p>');
					iArr.npush('<p>');
					iArr.npush('<span class="fl">项目进度：</span>');
					if(data[i]["supportRatio"]*100 > 100){
						iArr.npush('<span class="jdBar"><span style="width:100%"></span></span>');
					}else{
						iArr.npush('<span class="jdBar"><span style="width:'+(data[i]["supportRatio"]*100).toFixed(0)+'%"></span></span>');
					}
					iArr.npush('<span class="fl fs20">'+(data[i]["supportRatio"]*100).toFixed(0)+'%</span>');
					iArr.npush('</p>');
					iArr.npush('<p>融资目标：￥'+data[i]["fundAmt"]+'</p>');
					if(data[i]["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
						remainDays = data[i]["remainPreheatDays"] ? (data[i]["remainPreheatDays"] < 0 ? "0" : data[i]["remainPreheatDays"]) : "0" ;
					}else{
						remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
					}
					if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
						iArr.push('<p>筹款成功</p>');
					}else{
						iArr.push('<p>剩余时间：'+remainDays+'天</p>');
					}
					iArr.npush('</div>');
					iArr.npush('<div class="list_ul_pop"></div></div>');
					iArr.npush('</li>');
				}
				iStr = iArr.join("");
				$("#projectList").append(iStr);
//				pageNum ++ ;
				if($("#projectList>li").length == total){
					$("#moreList").show();
					$("#moreList a").text("更多精彩项目，敬请期待");
				}
			}
		},
		error: function(request){
			console.log("获取首页项目列表异常");
		}
	});
}
