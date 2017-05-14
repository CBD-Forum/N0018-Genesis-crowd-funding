var luserId = getQueryString("luserId");
$(function(){
	getCrowUserDetail();
	getLeaderSupportList(1);
	getLeaderAttentionList(1);
	likeAndAttend();//关注和粉丝标签页切换
});
//关注和粉丝标签页切换
function likeAndAttend(){
	$("#ltxqTab a").click(function(){
		$("#ltxqTab a").addClass("a_home");
		$(this).removeClass("a_home");
		$("#ltxqBody>div").hide();
		getLTGTDetail($(this).attr("name"));
	});
	$("#ltxqTab a").eq(0).click();
}
function getLTGTDetail(id){
	var iArr = [], iStr = '', l;
	if($("#" + id + "Div").attr("hs") == "1"){ //已经展示过
		$("#" + id + "Div").show();
		return false;
	}
	var adata;
	if(id == "attent"){
		adata = {"userId": luserId};
	}else{
		adata = {"beAttentionUser": luserId};
	}
	$.ajax({
		url: path + "/userAttention/getAttentionUser.html",
		type: "post",
		dataType: "json",
		data: adata,
		success: function(data){
			if(!data["success"]){
				iStr = '<p style="padding:15px;color:red;">暂无数据</p>';
				$("#" + id + "Div").html(iStr);
				return false;
			}
			l = data["msg"].length, data = data["msg"];
			for(var i=0;i<l;i++){
				if(id == "attent"){
					var photoUrl = data[i]["userPhoto"] ? cv["fileAddress"]+data[i]["beUserPhoto"] : '../images/defaultPhoto.png'
					if(data[i]["userLevel"]=="lead"){
						iArr.push('<a href="'+path+'/common/leadDetail.html?luserId='+data[i]["beAttentionUser"]+'" title="'+data[i]["beRealName"]+'"><img src="'+photoUrl+'" width="52" height="52" style="margin:3px;"/></a>');
					}else{
						iArr.push('<a href="javascript:void(0);" title="'+data[i]["beRealName"]+'"><img src="'+photoUrl +'" width="52" height="52" style="margin:3px;"/></a>');
					}
				}else{
					
					if(data[i]["userLevel"]=="lead"){
						var photoUrl = data[i]["beUserPhoto"] ? cv["fileAddress"]+data[i]["beUserPhoto"] : '../images/defaultPhoto.png';
						iArr.push('<a href="'+path+'/common/leadDetail.html?luserId='+data[i]["userId"]+'" title="'+data[i]["realName"]+'"><img src="'+ photoUrl+'" width="52" height="52" style="margin:3px;"/></a>');
					}else{
						var photoUrl = data[i]["userPhoto"] ? cv["fileAddress"]+data[i]["userPhoto"] : '../images/defaultPhoto.png';
//						beUserPhoto
						iArr.push('<a href="javascript:void(0);" title="'+data[i]["realName"]+'"><img src="'+photoUrl +'" width="52" height="52" style="margin:3px;"/></a>');
					}
				}
			}
			iStr = iArr.join("");
			$("#" + id + "Div").html(iStr).attr("hs", "1").show();
		},
		error: function(request){
			console.log("获取领投人详细信息异常");
		}
	});
}
//获取投资过的项目
function getLeaderSupportList(page){
	var iArr = [], iStr = '', l, sumPage;
	$.ajax({
		url: path + "/userAttention/getLeaderSupportList.html",
		type: "post",
		dataType: "json",
		data: {
			"supportUser": luserId,
			"rows": 3,
			"page": page
			},
		success: function(data){
			if(!data["success"]){
				iStr = '<li style="color:red;padding:15px;">暂无项目数据</li>';
				$("#iLoanDiv>ul").html(iStr);
				return false;
			}
			sumPage = (data["msg"]["total"]%3 == 0) ? data["msg"]["total"]/3 : Math.floor(data["msg"]["total"]/3) + 1;
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				iStr = '<li style="color:red;padding:15px;border-bottom:0;">暂无项目数据</li>';
				$("#iLoanDiv>ul").html(iStr);
				return false;
			}
			for(var i=0;i<l;i++){
				iArr.push('<li>');
				iArr.push('<div class="ltImg fl"><img src="'+cv["fileAddress"]+data[i]["loanLogo"] + '" width="71" height="71"/></div>');
				//iArr.push('<div class="lt_tzr fl"><p class="p1"><span>中羽联</span> 投资人</p><p>已退出</p></div>');
				iArr.push('<div class="lt_tzr fl">');
				if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){
					iArr.push('<a href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a>');
				}else{
					if(data[i]["loanType"] == "stock"){
						if(siteUserId == "null"){
							iArr.push('<a href="javascript:void(0);">'+data[i]["loanName"]+'</a>');
						}else{
							iArr.push('<a href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">'+data[i]["loanName"]+'</a>');
						}
					}else{
						iArr.push('<a href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">'+data[i]["loanName"]+'</a>');
					}
				}
				iArr.push('</div>');
				var s_state;
				if(data[i]["loanState"]=='funding'){
					s_state='筹款中';
				}else if(data[i]["preheat"]=='preheat'){
					s_state='预热中';
				}else{
					s_state='融资完成';
				}
				iArr.push('<a href="javascript:void(0);" class="lt_ash fl" style="background:#104267;color:#FFF;cursor:default;">'+s_state+'</a>');
				iArr.push('</li>');
			}
			iStr = iArr.join("");
			$("#iLoanDiv>ul").html(iStr);
			$("#ipage").show();
			//分页设置
			pagePause = 0;
			if(page < 2){
				$("#ipage").jPages({
					first: false,
					last: false,
					containerID : "iLoanDiv",
					clickStop   : true,
					perPage	: 3,
					allSumPage : sumPage,
					callback: ajaxiPageData
				});
			}
		},
		error: function(request){
			console.log("获取领投人详细信息异常");
		}
	});
}
function ajaxiPageData(obj){
	if(pagePause == 0){
		return false;
	}
	getLeaderSupportList(obj["current"]);
}
//获取关注的项目
function getLeaderAttentionList(page){
	var aArr = [], aStr = '', l, sumPage;
	$.ajax({
		url: path + "/userAttention/getLeaderAttentionList.html",
		type: "post",
		dataType: "json",
		data: {
			"attentionUser": luserId,
			"rows": 3,
			"page": page
			},
		success: function(data){
			if(!data["success"]){
				aStr = '<li style="color:red;padding:15px;">暂无项目数据</li>';
				$("#aLoanDiv>div").html(aStr);
				return false;
			}
			sumPage = (data["msg"]["total"]%3 == 0) ? data["msg"]["total"]/3 : Math.floor(data["msg"]["total"]/3) + 1;
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				aStr = '<li style="color:red;padding:15px;border-bottom:0;">暂无项目数据</li>';
				$("#aLoanDiv>div").html(aStr);
				return false;
			}
			for(var i=0;i<l;i++){
				aArr.push('<li>');
				aArr.push('<div class="ltImg fl"><img src="'+cv["fileAddress"]+data[i]["loanLogo"] + '" width="71" height="71"/></div>');
				//iArr.push('<div class="lt_tzr fl"><p class="p1"><span>中羽联</span> 投资人</p><p>已退出</p></div>');
				
				 if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){ //如果是产品项目
					 aArr.push('<div class="lt_tzr fl"><a href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">'+data[i]["loanName"]+'</a></div>');
				}else{
					aArr.push('<div class="lt_tzr fl"><a href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">'+data[i]["loanName"]+'</a></div>');
				}
				aArr.push('</li>');
			}
			aStr = aArr.join("");
			$("#aLoanDiv>div").html(aStr);
			$("#apage").show();
			//分页设置
			pagePause = 0;
			if(page < 2){
				$("#apage").jPages({
					containerID : "aLoanDiv",
					clickStop   : true,
					perPage	: 3,
					allSumPage : sumPage,
					callback: ajaxaPageData
				});
			}
		},
		error: function(request){
			console.log("获取领投人详细信息异常");
		}
	});
}
function ajaxaPageData(obj){
	if(pagePause == 0){
		return false;
	}
	getLeaderAttentionList(obj["current"]);
}
//检测当前认证领投人是否被当前关注
function checkIsAttention(){
	$.ajax({
		url: path + "/userAttention/getIsAttention.html",
		type: "post",
		dataType: "json",
		data: {"beAttentionUser": luserId},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			
			if(data["msg"] == "0"){ //未关注过
				$("#ltAttentDiv").html('<a class="xs_bg2" id="ltAttent" href="javascript:void(0);">关注</a>');
			}else{
				$("#ltAttentDiv").html('<a class="xs_bg2" href="javascript:void(0);" style="background:#104267;cursor:default;">已关注</a>');
			}
			//关注
			$("#ltAttent").click(function(){
				$.ajax({
					url: path + "/userAttention/attentionUser.html",
					type: "post",
					dataType: "json",
					data: {"beAttentionUser": luserId},
					success: function(data){
						if(!data["success"]){
							return false;
						}
						$("#ltAttentDiv").html('<a class="xs_bg2" href="javascript:void(0);" style="background:#104267;cursor:default;">已关注</a>');
					},
					error: function(request){
						console.log("获取领投人详细信息异常");
					}
				});
			});
		},
		error: function(request){
			console.log("获取领投人详细信息异常");
		}
	});
}
//获取领投人详情
function getCrowUserDetail(){
	$.ajax({
		url: path + "/userAttention/getCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		data: {"userId": luserId},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			$("#realName").text(data["companyName"]); //真实姓名
			if(data["photo"]){
				$("#ltPhoto").attr("src", cv["fileAddress"]+data["photo"]); //头像
			}else{
				$("#ltPhoto").attr("src", path + "/images/defaultPhoto.png");
			}
			$("#concernIndustry").text(data["concernIndustry"]);
			$("#concernIndustry1").text(data["concernIndustry"]);
			$("#concernCity").text(data["concernCity"]);
			if(data["selfDetail"]){
				var des = data["selfDetail"].length;
				var str = "";
				if(des > 130){
					str = data["selfDetail"].substring(0,130)+"...";
				}else{
					str = data["selfDetail"]?data["selfDetail"]:"暂无简介信息";
				}
				$("#selfDetail").text(str); //个人介绍
			}
			
			$("#selfDetail").attr("title",data["selfDetail"]);
			$("#investPlanNum").text(data["investPlanNum"]); //计划投资项目个数
			if(data["hasInvestPlan"] == "1"){//预计总投资额
				$("#investPlanAmt").text(data["investPlanAmt"]+"万");
			}else{
				$("#investPlanAmt").text("无");
			}
			if(data["userExperience"]){//个人投资说明
				$("#userExperience").html(data["userExperience"]);
			}else{
				$("#userExperience").html('<span style="display:inline-block;color:red;padding:15px;">暂无数据</span>');
			}
			if(data["userId"] != siteUserId){
				checkIsAttention();//关注
			}
		},
		error: function(request){
			console.log("获取领投人详细信息异常");
		}
	});
}