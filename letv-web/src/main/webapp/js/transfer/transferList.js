var param = window.location.search;
var LoanType = "", loanName = "", nodeType="";
if(param){
	var base64 = new Base64();
	LoanType = getQueryString('type');
	nodeType = getQueryString('nodeType');
	loanName = base64.decode(getQueryString('loanName'));
}
$(function(){
	transferRule();
	getCrowdfundingInvestTransferList(1);
	$("#transferRuleBlock").click(function(){
		$("#transferDiv,.bgpop").show();
		$(".prompt_close").click(function(){
			$("#transferDiv,.bgpop").hide();
		})
	});
});

//转让规则
function transferRule(){
	$.ajax({
		type: "post",
		url : path + "/node/getNode.html",
		data: {
			nodeType:"zrgz"
		},
		dataType: "json",
		success:function(data){
			if(data["success"]){
				if(data["msg"].length > 0){
					$(".prompt_conter").html(data["msg"][0]["body"]);					
				}else{
					$(".prompt_conter").html("<li style='padding: 20px;'>暂无内容！</li>");
				}
			}else{
				console.log("转让规则内容查询失败");
			}
		},
		error:function(){
			console.log("转让规则内容查询失败");
		}
	});
}

//获取挂牌列表
function getCrowdfundingInvestTransferList(page){
	$.ajax({
		url: path + "/crowdfundProductTransfer/getCrowdfundTransferDetailList.html",
		type: "post",
		dataType: "json",
		data:{
			"order":"desc",
			"page": page,
			"rows": 8,
			"transferState":"transfering"
		},
		success: function(data){
			if(!data["success"]){
				console.log("获取转让列表失败");
				return false;
			}
			sumPage = (data["msg"]["total"]%8 == 0) ? data["msg"]["total"]/8 : Math.floor(data["msg"]["total"]/8) + 1;
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				eStr = '<div class="defaultData" style="padding:50px 0;"><img src="'+path+'/images/letv/moren.png"><br/>还没有项目记录哦，先去浏览其他项目吧~<br/><a href="'+path+'/common/projectList.html">浏览众筹项目</a><a href="'+path+'/common/index.html">返回首页</a>'; 
				$("#projectList>ul").html(eStr);
				$(".page").hide();
				return false;
			}
			var eArr = [], eStr = '';
			for(var i = 0; i<data.length; i++){
				eArr.push('<li>');
				
				if(typeof(data[i]["loanName"])!="undefined")
					eArr.push('<a class="listTit" href="'+path+'/common/loanDetail-product.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a>');
				eArr.push('<div class="cont_data">');
				eArr.push('<div class="list_cont fl">');
				var logoUrl = '';
				if(data[i]["loanLogo"]){
					logoUrl = cv["fileAddress"]+data[i]["loanLogo"];
				}
				eArr.push('<img src="'+logoUrl+'"/>');
				eArr.push('<p>'+data[i]["backContent"]+'</p>');
				eArr.push('</div>');
				eArr.push('<div class="fr" style="width:131px;height:30px;">');
				if(data[i]["transferState"] == "transfering"){
					eArr.push('<a class="transBtu" onclick="getTransferNo(this)" loanNo="'+data[i]["loanNo"]+'" transferNo="'+data[i]["transferNo"]+'" loanUser="'+data[i]["loanUser"]+'" userId="'+data[i]["transferUser"]+'">立即购买</a>');
				}else if(data[i]["transferState"] == "transfered"){
					eArr.push('<img src="'+path+'/images/letv/zhaq05.png"/>');
				}else if(data[i]["transferState"] == "transferend"){
					eArr.push('<a class="transBtu" style="background:#999;">转让结束</a>');
				}
				eArr.push('</div>');
				
				if(data[i]['transferDay'] > 0){
					eArr.push('<p class="fr">'+data[i]['transferDay']+'天</p>');
				}else if(data[i]['transferDay'] <= 0){
					eArr.push('<p class="fr cont" countTime="'+data[i]['transferEndTime']+'"><i></i><i></i><i></i></p>');
				}else{
					eArr.push('<p class="fr">转让已结束</p>');
				}
				
				if(data[i]["sendTime"]){
					eArr.push('<p class="fr">'+data[i]["sendTime"].substring(0,10)+'</p>');
				}else{
					eArr.push('<p class="fr">--</p>');
				}
				eArr.push('<p class="fr">'+data[i]["nickName"]+'</p>');
				if(data[i]["transFee"]){
					var transferAmt = Number(data[i]["transferAmt"])+Number(data[i]["transFee"]);
				}else{
					var transferAmt = Number(data[i]["transferAmt"])
				}
				eArr.push('<p class="fr money">'+transferAmt.toFixed(2)+companyCode+'</p>');
				eArr.push('</div>');
				eArr.push('</li>');
			}
			eStr = eArr.join("");
			$("#projectList>ul").html(eStr);
			$(".letvPage").show();
			
			$.each($(".cont[countTime]"), function(k, v){
				var end_time = new Date($(v).attr("countTime")).getTime();
				var ct = (end_time-new Date().getTime())/1000;
				(function(ct, v){
					var cInterval = setInterval(function(){
						if(ct > 0){
							var hours = Math.floor(ct/3600) == 0 ? "00" : (Math.floor(ct/3600)<10 ? "0"+Math.floor(ct/3600) : Math.floor(ct/3600));
							var mimutes = Math.floor(ct%3600/60) == 0 ? "00" : (Math.floor(ct%3600/60)<10 ? "0"+Math.floor(ct%3600/60) : Math.floor(ct%3600/60));
							var second = Math.floor(ct%3600%60) == 0 ? "00" : (Math.floor(ct%3600%60)<10 ? "0"+Math.floor(ct%3600%60) : Math.floor(ct%3600%60));
							$(v).children("i").eq(0).text(hours+"时");
							$(v).children("i").eq(1).text(mimutes+"分");
							$(v).children("i").eq(2).text(second+"秒");
						}else{
							$(v).children("i").eq(0).text("00"+"时");
							$(v).children("i").eq(1).text("00"+"分");
							$(v).children("i").eq(2).text("00"+"秒");
							window.clearInterval(cInterval);
						}
						ct--;
					},1000);
				})(ct, v);
			});
	
			//分页设置
			pagePause = 0;
			if(page < 2){
				$(".letvPage").jPages({
					containerID : "projectList",
					first:false,
					last:false,
					previous:" ",
					next:" ",
					clickStop   : true,
					perPage	: 8,
					allSumPage : sumPage,
					callback: ajaxPageData
				});
			}
		},
		error:function(){
			console.log("获取挂牌接口失败")
		}
	});
}
//分页回调函数
function ajaxPageData(obj){
	if(pagePause == 0){
		return false;
	}
	getCrowdfundingInvestTransferList(obj["current"]);
}
//获取个人信息
function getTransferNo(_this){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(siteUserId == "null"){
				window.location.href = path + "/common/login.html";
				return false;
			}
			if(data["isAuth"]){
				if($(_this).attr("userid") == siteUserId){
					AlertDialog.tip("您不能支持自己的转让!");
					return false;
				}if($(_this).attr("loanUser") == siteUserId){
					AlertDialog.tip("您不能支持自己的项目!");
					return false;
				}else{
					window.location.href = path + "/common/transferPay.html?loanNo="+$(_this).attr("loanNo")+"&transferNo="+$(_this).attr("transferNo");
				}
			}else{
				window.location.href = path + "/common/realNameRZ.html" ;
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}