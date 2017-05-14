if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
var eventObj = null;
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	centerLoanTab(); //个人中心下部标签分页
	centerLoanType(); //个人中心项目投资类别选择
	enterLoanType(); //个人中心发起项目类别选择
});
//个人中心下部标签页
function centerLoanTab(){
	$("#centerLoanTab").find("a").click(function(){
		$("#centerLoanTab").find("li").removeClass("cur");
		$("#centerLoanTab").find("a").removeClass("cur");
		$(this).addClass("cur");
		$(this).parent().addClass("cur");
		$("#centerLoanTabBody>div").hide();
		$("#" + $(this).attr("name") + "Body").show();
		getCenterList($(this).attr("name"));
	});
	$("#centerLoanTab").find("a").first().click();
}
//获取个人中心查询列表
function getCenterList(id){
	if(id == "invest"){
		$("#investBody").show();
		$("#centerLoanSonTab").find("a").click(function(){
			$("#centerLoanSonTab").find("a").removeClass("cur");
			$(this).addClass("cur");
			$("#centerLoanSonBody>div").hide();
			$("#" + $(this).attr("name") + "Body").show();
			getCIList($(this).attr("name"), $(this).attr("furl"), $("#centerLoanType").find("a.cur").attr("code"), 1);
		});
		
		$("#centerLoanSonTab2").find("a").click(function(){
			$("#centerLoanSonTab2").find("a").removeClass("cur");
			$(this).addClass("cur");
			$("#centerLoanSonBody>div").hide();
			$("#" + $(this).attr("name") + "Body").show();
			getCIList($(this).attr("name"), $(this).attr("furl"), $("#centerLoanType").find("a.cur").attr("code"), 1);
		});
		$("#centerLoanSonTab2").find("a").first().click();
		//centerLoanTypeFun();
	}else if(id == "enter"){
		$("#enterBody").show();
		
		$("#enterLoanSonTab1").find("a").click(function(){
			$("#enterLoanSonTab1").find("a").removeClass("cur");
			$(this).addClass("cur");
			getELList($(this).attr("name"), $("#enterLoanType").find("a.cur").attr("code"), 1);
		});
		
		$("#enterLoanSonTab2").find("a").click(function(){
			$("#enterLoanSonTab2").find("a").removeClass("cur");
			$(this).addClass("cur");
			getELList($(this).attr("name"), $("#enterLoanType").find("a.cur").attr("code"), 1);
		});
		
		$("#enterLoanSonTab3").find("a").click(function(){
			$("#enterLoanSonTab3").find("a").removeClass("cur");
			$(this).addClass("cur");
			getELList($(this).attr("name"), $("#enterLoanType").find("a.cur").attr("code"), 1);
		});
		
		$("#enterLoanType").find("a").first().click();
		//enterLoanType();
	}
}
function getCIList(id, url, type, page){
	$("#centerLoanTitleDiv>dl").hide();
	$("#centerLoanSonBody>div").hide();
	$("#centerLoanSonBody>div.page").hide();
	$.ajax({
		url: path + url,
		type: "post",
		dataType: "json",
		data: {
			"loanType": type,
			"page": page,
			"rows": 6
			},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			if(id == "order"){ //我的约谈
				$("#" + id + "Title").show();
				var oArr = [], oStr = '', l, sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
				l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					oStr = '<dl class="nodata">暂无数据</dl>';
					$("#orderBody>div").html(oStr).show();
					$("#orderBody").show();
					$("#orderPage").hide();
					return false;
				}
				for(var i=0;i<l;i++){
					oArr.push('<div class="cen_list">');
					oArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
					oArr.push('<p style=" height:30px; line-height:30px; font-size:16px;">'+data[i]["loanName"]+'</p>');
					oArr.push('<p class="p1">筹资金额：<span>￥'+data[i]["fundAmt"].toFixed(0)+'</span></p>');
					oArr.push('<p class="p1">发起人：<span>'+data[i]["loanUser2"]+'</span></p>');
					if(data[i]["interviewTime"]){
						oArr.push('<p class="p1">时间：<span>'+data[i]["interviewTime"].substring(0,10)+'</span></p>');
					}else{
						oArr.push('<p class="p1">时间：<span>--</span></p>');
					}
					oArr.push('</a></div>');
					oArr.push('</div>');	
				}
				oStr = oArr.join("");
				$("#orderBody>div").html(oStr);
				$("#orderBody").show();
				$("#orderPage").show();
				//分页设置
				pagePause = 0;
				if(page < 2){
					$("#orderPage").jPages({
						containerID : "orderBody",
						clickStop   : true,
						perPage	: 6,
						allSumPage : sumPage,
						callback: orderPageData
					});
				}
			}else if(id == "rg"){ //我的认购
				$("#" + id + "Title").show();
				var rArr = [], rStr = '', l, sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
				l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					rStr = '<dl class="nodata">暂无数据</dl>';
					$("#orderBody>div").html(rStr).show();
					$("#orderBody").show();
					$("#orderPage").hide();
					return false;
				}
				for(var i=0;i<l;i++){
					rArr.push('<div class="cen_list">');
					if(data[i]["loanType"] == "public_service" || data[i]["loanType"] == "entity"){
						rArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
					}else{
						rArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
					}
					rArr.push('<p style=" height:30px; line-height:30px; font-size:16px;">'+data[i]["loanName"]+'</p>');
					rArr.push('<p class="p1">认领金额：<span>￥'+data[i]["supportAmt"].toFixed(0)+'</span></p>');
					rArr.push('<p class="p1">筹资目标：<span>￥'+data[i]["fundAmt"].toFixed(0)+'</span></p>');
					rArr.push('<p class="p1">发起人：<span>'+data[i]["loanUser2"]+'</span></p>');
					if(data[i]["leadInvestor"]){
						rArr.push('<p class="p1">领投人：<span>'+data[i]["leadInvestor"]+'</span></p>');
					}else{
						rArr.push('<p class="p1">领投人：<span>--</span></p>');
					}
					rArr.push('</a></div>');
					rArr.push('<ul class="rz_tab" style="margin-top:0;border-top:1px solid #D7D7D7;">');
					if(data[i]["payStateName"]){
						rArr.push('<li class="wz">'+data[i]["payStateName"]+'</li>');
					}else{
						rArr.push('<li class="wz">--</li>');
					}
					rArr.push('<li class="li_sx"></li>');
					if(data[i]["payState"]=="noPay"){
						rArr.push('<li class="ca" deleteId='+data[i]["id"]+' onclick=deleteSupportEvent(event)>取消订单</li>');	
					}
					rArr.push('</ul>');			
					rArr.push('</div>');
				}
				rStr = rArr.join("");
				$("#orderBody>div").html(rStr);
				$("#orderBody").show();
				$("#orderPage").show();
				//分页设置
				pagePause = 0;
				if(page < 2){
					$("#orderPage").jPages({
						containerID : "orderBody",
						clickStop   : true,
						perPage	: 6,
						allSumPage : sumPage,
						callback: orderPageData
					});
				}
			}else if( id == "yg" ){ //我的预购
				$("#" + id + "Title").show();
				var rArr = [], rStr = '', l, sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
				l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					rStr = '<dl class="nodata">暂无数据</dl>';
					$("#orderBody>div").html(rStr).show();
					$("#orderBody").show();
					$("#orderPage").hide();
					return false;
				}
				for(var i=0;i<l;i++){
					rArr.push('<div class="cen_list">');
					if(data[i]["loanType"] == "public_service" || data[i]["loanType"] == "entity"){
						rArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
					}else{
						rArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
					}
					rArr.push('<p style=" height:30px; line-height:30px; font-size:16px;">'+data[i]["loanName"]+'</p>');
					rArr.push('<p class="p1">项目编号：<span>'+data[i]["loanNo"]+'</span></p>');
					if(data[i]["supportAmt"]){
						rArr.push('<p class="p1">预购金额：<span>￥'+data[i]["supportAmt"].toFixed(0)+'</span></p>');
					}else{
						rArr.push('<p class="p1">预购金额：<span>--</span></p>');
					}
					if(data[i]["fundAmt"]){
						rArr.push('<p class="p1">项目融资金额：<span>￥'+data[i]["fundAmt"].toFixed(0)+'</span></p>');
					}else{
						rArr.push('<p class="p1">项目融资金额：<span>--</span></p>');
					}
					
					rArr.push('<p class="p1">预购时间：<span>'+data[i]["supportTime"]+'</span></p>');
					rArr.push('</a></div>');
					rArr.push('</div>');
				}
				rStr = rArr.join("");
				$("#orderBody>div").html(rStr);
				$("#orderBody").show();
				$("#orderPage").show();
				//分页设置
				pagePause = 0;
				if(page < 2){
					$("#orderPage").jPages({
						containerID : "orderBody",
						clickStop   : true,
						perPage	: 6,
						allSumPage : sumPage,
						callback: orderPageData
					});
				}
			}else if(id == "attent"){ //我的关注
				$("#" + id + "Title").show();
				var aArr = [], aStr = '', l, sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
				l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					aStr = '<dl class="nodata">暂无数据</dl>';
					$("#orderBody>div").html(aStr).show();
					$("#orderBody").show();
					$("#orderPage").hide();
					return false;
				}
				for(var i=0;i<l;i++){
					aArr.push('<div class="cen_list">');
					if(data[i]["loanType"] == "public_service" || data[i]["loanType"] == "entity"){
						aArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
					}else{
						aArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
					}
					aArr.push('<p style=" height:30px; line-height:30px; font-size:16px;">'+data[i]["loanName"]+'</p>');
					aArr.push('<p class="p1">筹资目标：<span>￥'+data[i]["fundAmt"].toFixed(0)+'</span></p>');
					aArr.push('<p class="p1">发起人：<span>'+data[i]["loanUser2"]+'</span></p>');
					aArr.push('<p class="p1">关注事件：<span>'+data[i]["attentionTime"]+'</span></p>');
					aArr.push('</a></div>');
					aArr.push('<ul class="rz_tab" style="margin-top:0;border-top:1px solid #D7D7D7;">');
					aArr.push('<li class="wz">'+data[i]["loanStateName"]+'</li>');
					aArr.push('<li class="li_sx"></li>');
					aArr.push('<li class="ca" deleteAttentId="'+data[i]["id"]+'" onclick="deleteAttentEvent(event)">取消关注</li>');	
					aArr.push('</ul>');			
					aArr.push('</div>');
				}
				aStr = aArr.join("");
				$("#orderBody>div").html(aStr);
				$("#orderBody").show();
				$("#orderPage").show();
				//分页设置
				pagePause = 0;
				if(page < 2){
					$("#orderPage").jPages({
						containerID : "orderBody",
						clickStop   : true,
						perPage	: 6,
						allSumPage : sumPage,
						callback: orderPageData
					});
				}
			}else if(id == "rgg"){ //投资项目 > 产品众筹 > 我的认购 
				$("#" + id + "Title").show();
				var rArr = [], rStr = '', l, sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
				l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					rStr = '<dl class="nodata">暂无数据</dl>';
					$("#orderBody>div").html(rStr).show();
					$("#orderBody").show();
					$("#orderPage").hide();
					return false;
				}
				for(var i=0;i<l;i++){
					rArr.push('<div class="cen_list">');
					if(data[i]["loanType"] == "public_service" || data[i]["loanType"] == "entity"){
						rArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
					}else{
						rArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
					}
					rArr.push('<p style=" height:30px; line-height:30px; font-size:16px;">'+data[i]["loanName"]+'</p>');
					rArr.push('<p class="p1">认购金额：<span>￥'+ data[i]["supportAmt"].toFixed(2)+'</span></p>');
					rArr.push('<p class="p1">筹资目标：<span>￥'+data[i]["fundAmt"].toFixed(2)+'</span></p>');
					rArr.push('<p class="p1">认购时间：<span>'+data[i]["supportTime"]+'</span></p>');
					rArr.push('</a></div>');
					rArr.push('<ul class="rz_tab" style="margin-top:0;border-top:1px solid #D7D7D7;">');
					if(data[i]["payStateName"]){
						rArr.push('<li class="wz" payId="' + data[i]["id"] + '" onclick="">'+data[i]["payStateName"]+'</li>');
					}else{
						rArr.push('<li class="wz">--</li>');
					}
					rArr.push('<li class="li_sx"></li>');
					if(data[i]["payState"]=="noPay"){
						rArr.push('<li class="ca" deleteId='+data[i]["id"]+' onclick=deleteSupportEvent(event)>取消订单</li>');	
						//rArr.push('<dd style="width:130px;"><a href="javascript:void(0);" deleteId='+data[i]["id"]+' onclick=deleteSupportEvent(event)>取消订单</a></dd>');
					}
					rArr.push('</ul>');			
					rArr.push('</div>');			
				}
				rStr = rArr.join("");
				$("#orderBody>div").html(rStr);
				$("#orderBody").show();
				$("#orderPage").show();
				//分页设置
				pagePause = 0;
				if(page < 2){
					$("#orderPage").jPages({
						containerID : "orderBody",
						 first: "",
					     last: "",
						clickStop   : true,
						perPage	: 6,
						allSumPage : sumPage,
						callback: orderPageData
					});
				}
			}
		},
		error: function(request){
			console.log("获取个人中心信息列表异常");
		}
	});
}
function orderPageData(obj){
	if(pagePause == 0){
		return false;
	}
	getCIList($("#centerLoanSonTab2").find("a.cur").attr("name"), $("#centerLoanSonTab2").find("a.cur").attr("furl"), $("#centerLoanType").find("a.cur").attr("code"), obj["current"]);
}
//个人中心项目投资类别选项
function centerLoanType(){
	$("#centerLoanType").find("a").click(function(){
		$("#centerLoanType").find("a").removeClass("cur");
		$(this).addClass("cur");
		if($(this).attr("code")=="entity" || $(this).attr("code")=="public_service"){
			$("#centerLoanSonTab2").show();
			$("#centerLoanSonTab").hide();
			getCIList($("#centerLoanSonTab2").find("a.cur").attr("name"), $("#centerLoanSonTab2").find("a.cur").attr("furl"), $(this).attr("code"), 1);
		}else{
			if($("#centerLoanSonTab").css("display") == "none"){
				$("#centerLoanSonTab2").hide();
				$("#centerLoanSonTab").show();
			}
			getCIList($("#centerLoanSonTab").find("a.cur").attr("name"), $("#centerLoanSonTab").find("a.cur").attr("furl"), $(this).attr("code"), 1);
		}
		
	});
}
function enterLoanType(){
	$("#enterLoanType").find("a").click(function(){
		$("#enterLoanType").find("a").removeClass("cur");
		$(this).addClass("cur");
		if($(this).attr("code") == "stock"){ //展示enterLoanSonTab2
			$("#enterLoanSonTab1").hide();
			$("#enterLoanSonTab2").show();
			$("#enterLoanSonTab3").hide();
			getELList($("#enterLoanSonTab2").find("a.cur").attr("name"), $(this).attr("code"), 1);
		}else if( $(this).attr("code") == "house"){
			$("#enterLoanSonTab1").hide();
			$("#enterLoanSonTab2").hide();
			$("#enterLoanSonTab3").show();
			getELList($("#enterLoanSonTab3").find("a.cur").attr("name"), $(this).attr("code"), 1);
		}else{
			$("#enterLoanSonTab1").show();
			$("#enterLoanSonTab2").hide();
			$("#enterLoanSonTab3").hide();
			getELList($("#enterLoanSonTab1").find("a.cur").attr("name"), $(this).attr("code"), 1);
		}
	});
}
function centerLoanTypeFun(){
	
	$("#centerLoanType").find("a").click(function(){
		$("#centerLoanType").find("a").removeClass("cur");
		$(this).addClass("cur");
		if($(this).attr("code") == "entity"){ //展示enterLoanSonTab2
			$("#centerLoanSonTab").hide();
			$("#centerLoanSonTab2").show();
			getCIList($("#centerLoanSonTab2").find("a.cur").attr("name"), $("#centerLoanSonTab2").find("a.cur").attr("furl"), $(this).attr("code"), 1);
		}else{
			$("#centerLoanSonTab").show();
			$("#centerLoanSonTab2").hide();
			getCIList($("#centerLoanSonTab").find("a.cur").attr("name"), $("#centerLoanSonTab").find("a.cur").attr("furl"), $(this).attr("code"), 1);
		}
	});
}


function getELList(loanState, loanType, page){
	var eArr = [], eStr = '', l, sumPage;
	$.ajax({
		url: path + "/crowdfundUserCenter/getMyCrowdfundList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanType": loanType,
			"loanState": loanState,
			"page": page,
			"rows": 6
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			if(loanState=="re_passed"){
				$("#enterTitle").hide();
				$("#enter1Title").show();
				$("#enter2Title").hide();
			}else if(loanState=="preheat"){
				$("#enterTitle").hide();
				$("#enter1Title").hide();
				$("#enter2Title").show();
			}else{
				$("#enterTitle").show();
				$("#enter1Title").hide();
				$("#enter2Title").hide();
			}
			sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				eStr = '<dl class="nodata">暂无数据</dl>';
				$("#enterListBody>div").html(eStr).show();
				$("#enterListPage").hide();
				return false;
			}
			if(loanState=="re_passed"){ //预热申请
				for(var i=0;i<l;i++){
					eArr.push('<div class="cen_list">');
					if(data[i]["loanType"] == "public_service" || data[i]["loanType"] == "entity"){
						eArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
					}else{
						eArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
					}
					eArr.push('<p style=" height:30px; line-height:30px; font-size:16px;">'+data[i]["loanName"]+'</p>');
					eArr.push('<p class="p1">代付金额：<span>￥'+data[i]["prepayAmt"].toFixed(0)+'</span></p>');
					eArr.push('<p class="p1">目标目标：<span>￥'+data[i]["fundAmt"].toFixed(0)+'</span></p>');
					eArr.push('<p class="p1">发布日期：<span>'+data[i]["createTime"]+'</span></p>');
					eArr.push('<p class="p1">项目状态：<span>'+data[i]["loanStateName"]+'</span></p>');
					eArr.push('</a></div>');
					eArr.push('<ul class="rz_tab" style="margin-top:0;border-top:1px solid #D7D7D7;">');
					
					if(loanType == "entity" || loanType == "public_service"){
						eArr.push('<li class="wz">发货</li>');
					}
					if(loanType == "stock"){
						if(data[i]["isPayFirstAmt"] == "0"){ //显示支付订金按钮
							eArr.push('<li class="wz"><a style="margin-left:10px;" href="javascript:void(0);" type="zfdj" onclick="checkBindBankCard(event);" zprepayAmt="'+data[i]["prepayAmt"]+'" zprojectFinanceAmt="'+data[i]["projectFinanceAmt"]+'" zloanNo="'+data[i]["loanNo"]+'">支付订金</a></li>');
						}else{
							eArr.push('<li class="wz">已支付</li>');
						}
					}
					eArr.push('</ul>');			
					eArr.push('</div>');
					
				}
			}else if(loanState=="preheat"){
				for(var i=0;i<l;i++){
					eArr.push('<div class="cen_list">');
					if(data[i]["loanType"] == "public_service" || data[i]["loanType"] == "entity"){
						eArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
					}else{
						eArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
					}
					eArr.push('<p style=" height:30px; line-height:30px; font-size:16px;">'+data[i]["loanName"]+'</p>');
					eArr.push('<p class="p1">目标金额：<span>￥'+data[i]["fundAmt"].toFixed(0)+'</span></p>');
					
					if(!data[i]["releaseTime"]){
						eArr.push('<p class="p1">发布日期：<span>--</span></p>');
					}else{
						eArr.push('<p class="p1">发布日期：<span>'+data[i]["releaseTime"]+'</span></p>');
					}
					if(!data[i]["fundEndTime"]){
						eArr.push('<p class="p1">截止日期：<span>--</span></p>');
					}else{
						eArr.push('<p class="p1">截止日期：<span>'+data[i]["fundEndTime"]+'</span></p>');
					}
					eArr.push('<p class="p1">尾款金额：<span>'+data[i]["remainPrepayAmt"]+'</span></p>');
					eArr.push('<p class="p1">项目状态：<span>'+data[i]["loanStateName"]+'</span></p>');
					eArr.push('<p class="p1">领头人数：<span>'+data[i]["leaderNum"]+'</span></p>');
					eArr.push('</a></div>');
					eArr.push('<ul class="rz_tab" style="margin-top:0;border-top:1px solid #D7D7D7;">');
					
					
					if(loanType == "entity" || loanType == "public_service"){
						eArr.push('<li class="wz">发货</li>');
					}
					if(loanType == "stock"){
						if(data[i]["isPayLastAmt"] == "0"){
							eArr.push('<li class="wz"><a style="margin-left:10px;" href="javascript:void(0);" type="zfwk" onclick="checkBindBankCard(event);" zremainPrepayAmt="'+data[i]["remainPrepayAmt"]+'" zprojectFinanceAmt="'+data[i]["projectFinanceAmt"]+'" zloanNo="'+data[i]["loanNo"]+'" zleaderNum="'+data[i]["leaderNum"]+'">支付尾款</a></li>');
						}else{
							eArr.push('<li class="wz">已支付</li>');
						}
					}
					eArr.push('</ul>');			
					eArr.push('</div>');
				}
			}else{
				for(var i=0;i<l;i++){
					eArr.push('<div class="cen_list">');
					if(data[i]["loanType"] == "public_service" || data[i]["loanType"] == "entity"){
						eArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
					}else{
						eArr.push('<div><a href="'+path+'/common/m-projectDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
					}
					eArr.push('<p style=" height:30px; line-height:30px; font-size:16px;">'+data[i]["loanName"]+'</p>');
					eArr.push('<p class="p1">目标金额：<span>￥'+data[i]["fundAmt"].toFixed(0)+'</span></p>');
					if(!data[i]["releaseTime"]){
						eArr.push('<p class="p1">发布日期：<span>--</span></p>');
					}else{
						eArr.push('<p class="p1">发布日期：<span>'+data[i]["releaseTime"].substring(0, 10)+'</span></p>');
					}
					if(!data[i]["fundEndTime"]){
						eArr.push('<p class="p1">截止日期：<span>--</span></p>');
					}else{
						eArr.push('<p class="p1">截止日期：<span>'+data[i]["fundEndTime"]+'</span></p>');
					}
					eArr.push('<p class="p1">已投金额：<span>￥'+data[i]["approveAmt"].toFixed(0)+'</span></p>');
					eArr.push('<p class="p1">项目状态：<span>'+data[i]["loanStateName"]+'</span></p>');
					eArr.push('</a></div>');
					eArr.push('<ul class="rz_tab" style="margin-top:0;border-top:1px solid #D7D7D7;">');
					
					if(loanState == "lended"){ //待发货 | 待分红
						if(loanType == "entity" || loanType == "public_service"){
							eArr.push('<li class="wz"><a href="'+path+'/common/m-enterLogico.html?loanNo='+data[i]["loanNo"]+'">发货</a></li>');
						}
					}else if(data[i]["loanState"] == "new" || data[i]["loanState"] == "add"){
						if(data[i]["loanType"] == 'entity'){
							eArr.push('<li class="ca" style="width:100%;"><a style="margin-left:10px;" cloanNo="'+data[i]["loanNo"]+'" href="javascript:void(0);" onclick="delCaoLoan(event)">删除</a></li>');
						}else if(data[i]["loanType"] == 'public_service'){
							eArr.push('<li class="ca" style="width:100%;"><a style="margin-left:10px;" cloanNo="'+data[i]["loanNo"]+'" href="javascript:void(0);" onclick="delCaoLoan(event)">删除</a></li>');	
						}else if(data[i]["loanType"] == 'stock'){
							eArr.push('<li class="ca" style="width:100%;"><a style="margin-left:10px;" cloanNo="'+data[i]["loanNo"]+'" href="javascript:void(0);" onclick="delCaoLoan(event)">删除</a></li>');	
						}else if(data[i]["loanType"] == 'house'){
							eArr.push('<li class="ca" style="width:100%;"><a style="margin-left:10px;" cloanNo="'+data[i]["loanNo"]+'" href="javascript:void(0);" onclick="delCaoLoan(event)">删除</a></li>');
						}
					}else{
						eArr.push('<li class="wz">--</li>');
					}
					eArr.push('</ul>');			
					eArr.push('</div>');
				}
			}
			eStr = eArr.join("");
			$("#enterListBody>div").html(eStr).show();
			$("#enterListPage").show();
			//分页设置
			pagePause = 0;
			if(page < 2){
				$("#enterListPage").jPages({
					containerID : "enterListBody",
					clickStop   : true,
					perPage	: 6,
					allSumPage : sumPage,
					callback: enterPageData
				});
			}
		},
		error: function(request){
			console.log("获取发起项目列表异常");
		}
	});
}
//预购订金弹框
function stockZfdj(event){
	var obj = event.srcElement || event.target;
	var prepayAmt = $(obj).attr("zprepayAmt"), projectFinanceAmt = $(obj).attr("zprojectFinanceAmt"); //发起人首付款  项目方出资金额
	var loanNo = $(obj).attr("zloanNo");
	var tl = (cv["winW"]-450)/2, tt = (cv["winH"]-128)/2;
	$("#bgpop").show();
	$("#zfdjDiv").css({"left":"2.5%", "top":tt+"px"}).show();
	$("#zfdjAmt").text(Number(prepayAmt).toFixed(0)); 
	$("#zfdjPercent").text((Number(prepayAmt)/Number(projectFinanceAmt)*100).toFixed(2)); //出资比例
	//关闭
	$("#bgpop").click(function(){
		$("#zfdjDiv").hide();
		$(this).hide();
	});
	$("#canelPayZfdjBtn").click(function(){
		$("#zfdjDiv").hide();
		$("#bgpop").hide();
	});
	//跳转form赋值
	$("#zfdjForm input[name='supportAmt']").val(prepayAmt);
	$("#zfdjForm input[name='loanNo']").val(loanNo);
//	$("#payZfdjBtn").click(function(){
//		$("#zfdjFormBtn").click();
//		$("#zfdjDiv").hide();
//		$("#bgpop").hide();
//	});
	//进行支付 
	$.ajax({
		url: path + "/fundpool/yeepay/pay/directCenterBindPay.html",
		type: "post",
		dataType: "json",
		async: false,
		data: {
			"cardTop":$("#zfdjForm input[name='cardTop']").val(),
			"cardLast":$("#zfdjForm input[name='cardLast']").val(),
			"supportAmt":$("#zfdjForm input[name='supportAmt']").val(),
			"loanNo":$("#zfdjForm input[name='loanNo']").val(),
			"payNode":$("#zfdjForm input[name='payNode']").val(),
			"payType":$("#zfdjForm input[name='payType']").val()
		},
		success:function(data){
			if(data["orderId"]){
				$("#okPay").text("支付中...").unbind();
				$("#sendBtn").unbind();
				var i = 0;
				var obj = setInterval(function(){
					if(checkPay(data["orderId"]) == true || i == 20){
						clearInterval(obj);// 取消
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
//支付尾款弹框
function stockZfwk(event){
//	var obj = event.srcElement || event.target;
	var remainPrepayAmt = $(obj).attr("zremainPrepayAmt"), projectFinanceAmt = $(obj).attr("zprojectFinanceAmt"); //发起人首付款  项目方出资金额
	var loanNo = $(obj).attr("zloanNo"), leaderNum = $(obj).attr("zleaderNum");
	var tl = (cv["winW"]-450)/2, tt = (cv["winH"]-300)/2;
	$("#bgpop").show();
	$("#zfwkDiv").css({"left":"2.5%", "top":tt+"px"}).show();
	
	//弹框内赋值
	$("#t_leaderNum").text(leaderNum);
	$("#zfwkAmt").text(Number(remainPrepayAmt).toFixed(0));  //支付尾款
	$("#zfwkPercent").text((Number(remainPrepayAmt)/Number(projectFinanceAmt)*100).toFixed(2)); //出资比例
	$("#zfwkPercent1").text((Number(remainPrepayAmt)/Number(projectFinanceAmt)*100).toFixed(2)); //出资比例
	//关闭
	$("#bgpop").click(function(){
		$("#zfwkDiv").hide();
		$(this).hide();
	});
	$("#canelPayZfwkBtn").click(function(){
		$("#zfwkDiv").hide();
		$("#bgpop").hide();
	});
	//跳转form赋值
	$("#zfwkForm input[name='supportAmt']").val(remainPrepayAmt);
	$("#zfwkForm input[name='loanNo']").val(loanNo);
//	$("#payZfwkBtn").click(function(){
//		$("#zfwkFormBtn").click();
//		$("#zfwkDiv").hide();
//		$("#bgpop").hide();
//	});
	//进行支付 
	$.ajax({
		url: path + "/fundpool/yeepay/pay/directCenterBindPay.html",
		type: "post",
		dataType: "json",
		async: false,
		data: {
			"cardTop":$("#zfwkForm input[name='cardTop']").val(),
			"cardLast":$("#zfwkForm input[name='cardLast']").val(),
			"supportAmt":$("#zfwkForm input[name='supportAmt']").val(),
			"loanNo":$("#zfwkForm input[name='loanNo']").val(),
			"payNode":$("#zfwkForm input[name='payNode']").val(),
			"payType":$("#zfwkForm input[name='payType']").val()
		},
		success:function(data){
			if(data["orderId"]){
				$("#okPay").text("支付中...").unbind();
				$("#sendBtn").unbind();
				var i = 0;
				var obj = setInterval(function(){
					if(checkPay(data["orderId"]) == true || i == 20){
						clearInterval(obj);// 取消
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
function enterPageData(obj){
	if(pagePause == 0){
		return false;
	}
	if($("#enterLoanSonTab1").css("display") == "none"){ //房产或股权
		getELList($("#enterLoanSonTab2").find("a.cur").attr("name"), $("#enterLoanType").find("a.cur").attr("code"), obj["current"]);
	}else if($("#enterLoanSonTab2").css("display") == "none"){ //公益或产品
		getELList($("#enterLoanSonTab1").find("a.cur").attr("name"), $("#enterLoanType").find("a.cur").attr("code"), obj["current"]);
	}
}
//判断领投跟投认证，改变显示结果
function judgeLtgtRZ(data){
	if(data["userLevel"] == "common"){
		if(data["investAuthState"] == "auditing"){ //跟投人审核中
			$("#rzgtrIcon").click(function(){
				AlertDialog.tip("跟投人认证正在审核中");
			});
			$("#rzltrIcon").click(function(){
				AlertDialog.tip("跟投人认证正在审核中");
			});
			$("#centerRZBtn").css("visibility", "visible").click(function(){
				AlertDialog.tip("跟投人认证正在审核中");
			});
		}else if(data["leadAuthState"] == "auditing"){ //领投人审核中
			$("#rzgtrIcon").click(function(){
				AlertDialog.tip("领投人认证正在审核中");
			});
			$("#rzltrIcon").click(function(){
				AlertDialog.tip("领投人认证正在审核中");
			});
			$("#centerRZBtn").css("visibility", "visible").click(function(){
				AlertDialog.tip("领投人认证正在审核中");
			});
		}else{
			$("#rzgtrIcon").click(function(){
				window.location.href = path + "/common/centerRZ.html?type=gtr";
			});
			$("#rzltrIcon").click(function(){
				window.location.href = path + "/common/centerRZ.html?type=ltr";
			});
			$("#centerRZBtn").css("visibility", "visible").click(function(){
				window.location.href = path + "/common/centerRZ.html?type=gtr";
			});
		}
	}else if(data["userLevel"] == "authed"){ //跟投人认证已通过
		$("#rzgtrIcon").unbind("click").attr("src", path + "/images/v2.png").css("cursor", "default");
		$("#rzltrIcon").css("cursor", "pointer");
		if(data["leadAuthState"] == "auditing"){
			$("#rzltrIcon").click(function(){
				AlertDialog.tip("领投人认证正在审核中");
			});
			$("#centerRZBtn").css("visibility", "visible").click(function(){
				AlertDialog.tip("领投人认证正在审核中");
			});
		}else{
			$("#rzltrIcon").click(function(){
				window.location.href = path + "/common/centerRZ.html?type=ltr";
			});
			$("#centerRZBtn").css("visibility", "visible").click(function(){
				window.location.href = path + "/common/centerRZ.html?type=ltr";
			});
		}
	}else if(data["userLevel"] == "lead"){ //跟投人和领投人都已经通过
		$("#rzgtrIcon").attr("src", path + "/images/v2.png").css("cursor", "default");
		$("#rzltrIcon").attr("src", path + "/images/v1.png").css("cursor", "default");
	}
}
//取消订单
function deleteSupportEvent(event){
	var obj = event.srcElement || event.target;
	var deleteId = $(obj).attr("deleteId");
	AlertDialog.confirm(deleteSupport, null, "您确定要取消订单吗？", "确定", "取消", deleteId);
}

function deleteSupport(id){
	$.ajax({
		url: path + "/crowdfunding/deleteSupport.html",
		type: "post",
		dataType: "json",
		data: {
			"id": id
		},
		success: function(data){
			if(data["success"]){
				AlertDialog.tip("取消订单成功");
				$("li[deleteId="+id+"]").parent().parent().remove();
			}
		},
		error: function(request){
			console.log("取消订单异常");
		}
	});
}

//取消关注
function deleteAttentEvent(event){
	var obj = event.srcElement || event.target;
	var deleteAttentId = $(obj).attr("deleteAttentId");
	AlertDialog.confirm(deleteAttent, null, "您确定要取消关注吗？", "确定", "取消", deleteAttentId);
}

function deleteAttent(id){
	$.ajax({
		url: path + "/crowdfunding/cancelAttention.html",
		type: "post",
		dataType: "json",
		data: {
			"id": id
		},
		success: function(data){
			if(data["success"]){
				AlertDialog.tip("取消关注成功");
				$("a[deleteAttentId="+id+"]").parent().parent().remove();
			}
		},
		error: function(request){
			console.log("取消关注异常");
		}
	});
}

//删除新增项目或草稿项目
function delCaoLoan(event){
	var obj = event.srcElement || event.target;
	var loanNo = $(obj).attr("cloanNo");
	AlertDialog.confirm(list2DelLoan, null, "您确定要删除吗？", "确定", "取消", loanNo);
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
				$("a[cloanno="+loanNo+"]").parent().parent().remove();
			}
		},
		error: function(request){
			console.log("删除草稿项目异常");
		}
	});
}


/*__________________________________ 支付 __________________________________________*/
//检测是否绑定银行卡
function checkBindBankCard(event){
	obj = event;
	$.ajax({
		url: path + "/fundpool/yeepay/pay/queryAuthbindList.html",
		type: "post",
		dataType: "json",
		success:function(data){
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
					$(".bgpop").fadeOut();
					obj.fadeOut();
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
				$("input[name='cardTop']").val($("#selBank option:checked").attr("top"));
				$("input[name='cardLast']").val($("#selBank option:checked").attr("last"));
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
				checkFn();//支付
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

//支付分配管理
function checkFn(){
	var obj = eventObj.srcElement || eventObj.target;
	if($(obj).attr("type") == "zfdj"){
		stockZfdj(eventObj);
	}else if($(obj).attr("type") == "zfwk"){
		stockZfwk(eventObj);
	}
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