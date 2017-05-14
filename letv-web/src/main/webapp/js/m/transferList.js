var pageNum = 1,loanName="";
$(function(){
	if(userId == "null"){ //未登录
		$("#myCenter").attr("href",path+"/common/m-login.html");
	}else{
		$("#myCenter").attr("href",path+"/common/m-myCenter.html");
	}
	var $tab = $("#tranferType a");
	$.each($tab,function(k,v){
		$(v).click(function(){
			$("#tranferType a").removeClass("cur");
			$(v).addClass("cur");
			pageNum = 1;
			getCrowdfundTransferDetailList($(v).attr("code"),pageNum);
		});
	});
	$tab.first().click();
	//点击“查看更多” 和 搜索
//	$("#allPage").click(function(){
//		getCrowdfundTransferDetailList($("#tranferType a.cur").attr("code"),pageNum);
//	});
	var totalheight = 0; 
	$(window).scroll(function(){
        totalHeight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
		if(($(document).height()-100)<=totalHeight){
			pageNum++;
			getCrowdfundTransferDetailList($("#tranferType a.cur").attr("code"),pageNum);
		}
   });
	$("#loanNameBtn").click(function(){
		pageNum = 1;
		getCrowdfundTransferDetailList($("#tranferType a.cur").attr("code"),pageNum);
	});
});
function getCrowdfundTransferDetailList(code,page){
	var params = {},total,l,eArr = [],eStr = "";
	loanName = $("#loanName").val();
	if(code == "Listing"){
		params = {
			"isAgree":"0",
			"status":"transfering",
			"loanName":loanName,
			"page":page,
			"rows":5
		}
	}else if(code == "lisTingEnd"){
		params = {
			"transferStateAll":"1",
			"isAgree":"0",
			"loanName":loanName,
			"page":page,
			"rows":5
		}
	}
	$.ajax({
		url: path+"/crowdfundingInvestTransfer/getCrowdfundTransferDetailList.html",
		type: "post",
		dataType: "json",
		data:params,
		success: function(data){
			if(!data["success"]){
				console.log("获取可挂牌列表失败");
				return false;
			}
			total = data["msg"]["total"],l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
//				eStr = '<div style="color:red; font-size:24px; text-align:center; padding:60px 0px;">暂无数据</div>'; 
//				$("#projectList").html(eStr);
//				$("#allPage").hide();
				return false;
			}
			for(var i =0;i<l;i++){
				var progress =(data[i]["sumBuyMoney"]/data[i]["transferMoney"]).toFixed(3);
				eArr.npush('<li class="prolist_li">');
				eArr.npush('<div class="img"><a href="'+path+'/common/m-transferListDetail.html?loanNo='+data[i]['loanNo']+'&transferNo='+data[i]['transferNo']+'"><img src="'+cv["fileAddress"]+data[i]["loanLogo"]+'"/></a></div>');
				eArr.npush('<div class="list_ul_pop"></div>');
				eArr.npush('<div class="list_ul_font">');
				eArr.npush('<div class="ul_font_top">');
				eArr.npush('<p class="p1"><a href="'+path+'/common/m-transferListDetail.html?loanNo='+data[i]['loanNo']+'&transferNo='+data[i]['transferNo']+'">#0</a></p>',[data[i]["loanName"]]);
				eArr.npush('</div>');
				eArr.npush('<div class="ul_font_bottom">');
				eArr.npush('<p>已认购：￥'+data[i]['sumBuyMoney'].toFixed(2)+'</p>');
				eArr.npush('<p>');
				eArr.npush('<span class="fl">挂牌进度：</span>');
				eArr.npush('<span class="jdBar"><span style="width:'+(progress*100)+'%"></span></span>');
				eArr.npush('<span class="fl fs20">'+(progress*100).toFixed(0)+'%</span>');
				eArr.npush('</p>');
//				eArr.npush('<p>挂牌价格：￥400,000,00</p>');
				eArr.npush('<p>挂牌股权比例：'+((Number(data[i]["transferRatio"]))*100).toFixed(2)+'%</p>');
				var Surplus_day = 0;
				if(data[i]['deadline']){
					var date_part = data[i]['deadline'].split(" ")[0];
					var date_time = data[i]['deadline'].split(" ")[1]
					var deadDate = new Date(date_part.split("-")[0],date_part.split("-")[1]-1,date_part.split("-")[2],date_time.split(":")[0],date_time.split(":")[1],date_time.split(":")[2]);
					var Surplus = deadDate.getTime() - new Date();
					Surplus_day = Math.ceil(Surplus/86400000 <=0?0:Surplus/86400000);
				}
				eArr.npush('<p>剩余时间：'+Surplus_day+'天</p>');
				eArr.npush('</div>');
				eArr.npush('<div class="list_ul_pop"></div></div>');
				eArr.npush('</li>');
			}
			eStr = eArr.join('');
			if(page == 1){
				$("#projectList").html(eStr);
			}else{
				$("#projectList").append(eStr);
			}
			var proLen = $("#projectList>li").length;
			if($("#projectList li").length == total){
				$("#allPage a").text("更多精彩项目，敬请期待");
				if(proLen < 6 &&  proLen > 0){
					$("#allPage").hide();
				}
			}else{
			//	pageNum++;
				$("#allPage").show();
			}
		},
		error:function(){
			console.log("获取挂牌接口异常")
		}
	});
}