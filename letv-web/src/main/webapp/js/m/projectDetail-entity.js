var loanNo, loanState,page=1; //预定义项目编号和评论过后的标识
loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7, window.location.search.indexOf("&state"));
loanState = window.location.search.substring(window.location.search.indexOf("state=")+6, window.location.search.length);
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	if(userId == "null"){ //未登录
		$("#indexHw").html('<a href="'+path+'/common/m-login.html">登录</a>&nbsp;&nbsp;/&nbsp;&nbsp;<a href="'+path+'/common/m-register.html">注册</a>');
	}else{
		if(userPhoto == "null" || userPhoto == null || userPhoto == ""){
			$("#indexHw").html('<a href="'+path+'/common/m-myCenter.html"><img src="'+path+'/images/defaultPhoto.png" style="width:35px; height:35px;border-radius: 50%;" /></a>');
		}else{
			$("#indexHw").html('<a href="'+path+'/common/m-myCenter.html"><img src="'+cv["fileAddress"]+userPhoto+'" style="width:35px; height:35px;border-radius: 50%;" /></a>');
		}
	}
	getCrowdDetail(); //获取详情
	getBackSetList();//回报设置
	showProEvolve(); //获取项目动态
	getSupportList();//认购记录
	$("#gzimg").click(attentionLoan); //关注 | 赞
//	$("#subComment").click(submitComment);//发表评论
	
	var $eTab = $("#entityTab li");
	$.each($eTab,function(k,v){
		$(v).click(function(){
			$("#entityTab li").children("a").removeClass("cur");
			$(this).children("a").addClass("cur");
			$("#entityBody>div").hide();
			var code = $(v).attr("code");
			if(code == "comment"){
				if(!$("#"+code+"Body input").val()){
					showProComment();
				}
			}
			$("#"+code+"Body").show();
		});
	});
	$eTab.first().click();
});
//获取详情
function getCrowdDetail(){
	var remainDays = 0;
	$.ajax({
		url: path + "/crowdfunding/getCrowdDetail.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			$("#loanCover").attr("src",cv["fileAddress"] + data["loanLogo"]).attr("alt",data["loanName"]);
			$("#loanName").text(data["loanName"]);
			$("#fqrInfo").html(data["loanUserName"]?data["loanUserName"]:"--"+"&nbsp;&nbsp;"+data["loanProvinceName"]+data["loanCityName"]);
			
			$("#loanUser").text(data["loanUser2"]);
			$("#saleArea").attr("loanUser", data["loanUser"]); //用于私信的LoanUser
			//loanUser存cookie，用于支付页面发送私信
			document.cookie = "supportLoanUser="+data["loanUser"]+";path=/;";
			$("#loanAddress").html('&nbsp;&nbsp;' + data["loanProvinceName"] + data["loanCityName"]);
			$("#approveAmt").html("￥"+(Number(data["approveAmt"]) / 10000).toFixed(2));
			$("#approveAmt1").text(data["approveAmt"]);
			$("#supportRatio1").text((data["supportRatio"]*100).toFixed(2) + "%");
			$("#pBar").css("width", (data["supportRatio"]*100).toFixed(2) + "%");
			if(data["loanState"] == "funded" || data["loanState"] == "lended" || data["loanState"] == "end"){//筹款结束
				$("#remainDay").text(0);
				$("#remainDay1").text(0);
			}else{
//				remainDays = data["remainDays"] ? (data["remainDays"] < 0 ? 0 : data["remainDays"]) : 0;
				if(data["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
					remainDays = data["remainPreheatDays"] ? (data["remainPreheatDays"] < 0 ? "0" : data["remainPreheatDays"]) : "0" ;
				}else{
					remainDays = data["remainDays"] ? (data["remainDays"] < 0 ? "0" : data["remainDays"]) : "0" ;
				}
				$("#remainDay").text(remainDays);
				$("#remainDay1").text(remainDays);
			}
			$("#loanDes").html(data['loanDes'] ? (data['loanDes'].length > 58 ? (data['loanDes'].substring(0,58) + "...") : data['loanDes']) : "暂无数据");
			$("#fundDays").text(data["fundDays"]);
			var fundAmt = (data["fundAmt"] / 10000).toFixed(2);
			$("#fundAmt").html("￥"+fundAmt+"万");
			$("#supportNum").text(data["supportNum"]);
			$("#supportNum1").text(data["supportNum"]);
			$("#commentNum").text(data["commentNum"]);
			/*if(data["loanVideo"]){ //如果有视频，先加载视频
				$("#proInfoContent").html('<iframe height="450px" width="100%" src="'+data["loanVideo"]+'" frameborder=0 allowfullscreen></iframe>');
			}*/
			if(data["houseDeveloper"]){
				$("#mobileVideo").html('<a  target="_blank" href='+data["houseDeveloper"]+'>点击此处可查看项目视频</a>').show();
			}
			if(data["loanDetail"]){
				$("#loanDetail").html(data["loanDetail"]);//项目介绍
			}else{
				$("#loanDetail").html('<div class="nodata">暂无数据</div>');//项目介绍
			}
			
		},
		error: function(request){
			console.log("获取项目详情异常！");
		}
	});
}
//获取项目进展
function showProEvolve(){
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
				pStr = '<span class="nodata">暂无数据</span>';
				$("#loanProgress").html(pStr);
				return false;
			}
			for(var i=0;i<l;i++){
				pArr.push('<li class="cur"><i></i>');
				pArr.push('<div class="jz-list">');
				pArr.push('<p class="tit"><span class="fs12">'+data[i]["enterTime"].substring(0,10)+'</span></p>');
				pArr.push('<p class="fs12">'+data[i]["enterContent"]+'</p>');
				pArr.push('</div>');
				pArr.push('</li>');
			}
			//展示项目进展
			pStr = pArr.join("");
			$("#loanProgress").html(pStr);
		},
		error: function(request){
			console.log("获取项目进展异常");
		}
	});
}
//获取回报设置列表
function getBackSetList(){
	var aArr = [], aStr = '',aArr2 = [] , aStr2 = ''; //弹出框列表展示
	var smallN = 0;
	$.ajax({
		url: path + "/crowdfunding/getBackSetList.html",
		type: "post",
		data: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				aStr = '<div class="nodata">暂无数据</div>';
				$("#repayBody").html(aStr);
				return false;
			}
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if( l == 0){
				aStr = '<div class="nodata">暂无数据</div>';
				$("#repayBody").html(aStr);
				return false;
			}
			for(var i=0;i<l;i++){
				aArr.push('<div class="entiy_back_list">');
				aArr.push('<p>');
				aArr.push('<label class="amt">支持￥<i>'+data[i]["amt"]+'</i></label>');
				if(loanState == "funding"){ //筹款中
					if(data[i]["state"] == "notfull"){
						if(userId == "null" || userId == null){
							aArr.push('<a class="back_spot y" href="'+path+'/common/m-login.html">去支持</a>');
						}else{
							aArr.push('<a class="back_spot y" href="'+path+'/common/m-support-entity.html?loanNo='+loanNo+'&backNo='+data[i]["backNo"]+'">去支持</a>');
						}
					}else{
						aArr.push('<a class="back_spot c" href="javascript:void(0);">去支持</a>');
					}
				}else{
					aArr.push('<a class="back_spot c" href="javascript:void(0);">支持已满</a>');
				}
				aArr.push('</p>');
				if(data[i]["photoUrl1"]){
					aArr.push('<p class="back_con">'+data[i]["backContent"]+'&nbsp;&nbsp;<img class="loanlogoC_m"  sid="small'+smallN+''+i+'" src="'+ cv["fileAddress"] + '/' + data[i]["photoUrl1"] +'"></p>');
					aArr2.push('<div  sid="small'+smallN+''+i+'img"><img class="imgWd2"  src="'+ cv["fileAddress"] + '/' + data[i]["photoUrl1"] +'"></div>');
				}else{
					aArr.push('<p class="back_con">'+data[i]["backContent"]+'</p>');
				}
			    if(data[i]["numberLimits"] == 0){ //不限额
					aArr.push('<p class="back_sz">已有 '+data[i]["supportNum"]+' 位支持者 / 不限额</p>');
				}else{
					aArr.push('<p class="back_sz">已有 '+data[i]["supportNum"]+' 位支持者 / 限额&nbsp;'+data[i]["numberLimits"]+'&nbsp;位</p>');
				}
				aArr.push('</div>');
			}
			aStr = aArr.join("");
			$("#repayBody").html(aStr);
			aStr2 = aArr2.join("");
			$("#loanBig").html(aStr2);
			
			//点击页面回报设置的小图展示回报设置的大图
			$("#repayBody img[sid^='small'").unbind("click").click(function(){
				$("#bgpop2").show();
				$("#big_close").show();
				$("#loanBig").css({"left":(cv["winW"]-320)/2+"px","top":(cv["winH"]-220)/2+"px"}).show();
				$("#big_close").css({"left":(cv["winW"]+255)/2+"px","top":(cv["winH"]-255)/2+"px"}).show();
				$("#loanBig>div").hide();
				$("#loanBig>div[sid='"+$(this).attr("sid")+"img']").show();
			});
			//大图片关闭按钮事件
			$("#bgpop2").unbind("click").click(function(){
				$("#loanBig").hide();
				$("#bgpop2").hide();
				$("#big_close").hide();
			});
			$("#big_close").unbind("click").click(function(){
				$("#loanBig").hide();
				$("#bgpop2").hide();
				$("#big_close").hide();
			})
			
		},
		error: function(request){
			console.log("获取回报设置列表异常");
		}
	});
}

//发表评论
function submitComment(){
	if(userId == "null" ||userId == null){
		AlertDialog.tip("请登录后发表评论！");
		return false;
	}
	if($("#comVal").val().length < 5){
		AlertDialog.tip("评论不能少于5个字哦！");
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
				$.ajax({
					url: path + "/crowdfunding/getCommentList.html",
					type: "post",
					dataType: "json",
					data: {
						"loanNo": loanNo
					},
					success:function(data){
						$("#comVal").val("");
						showProComment();
					},
					error: function(request){
						console.log("获取评论异常");
					}
				});
			}
		},
		error: function(request){
			console.log("发表评论异常");
		}
	});
}
//显示评论
function showProComment(){
	var pArr = [], pStr = '', l = 0, list;
	$.ajax({
		url: path + "/crowdfunding/getCommentList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo": loanNo
		},
		success:function(data){
			if(!data["success"]){
				$("#commentBody ul").html('<div class="nodata">暂无数据</div>');
				return false;
			}
			l = data["msg"]["rows"].length,list = data["msg"]["rows"];
			if( l == 0){
				$("#commentBody ul").html('<div class="nodata">暂无数据</div>');
				return false;
			}
			for(var i=0;i<l;i++){
				pArr.push('<li class="clearfix">');
				pArr.push('<div class="img-div"><img src="'+cv["fileAddress"]+list[i]["photo"]+'"/></div>');
				pArr.push('<div class="font-div">');
				var username = list[i]["discussUser"].substring(0,2)+"***"+list[i]["discussUser"].substring(list[i]["discussUser"].length-1,list[i]["discussUser"].length);
				pArr.push('<p class="user">用户'+username+'</p>');
				pArr.push('<p>'+list[i]["content"]+'</p>');
				pArr.push('<div class="z-pl clearfix fs12">');
				pArr.push('<p class="fl">'+list[i]["differDays"]+'天前</p>');
				pArr.push('</div>');
				pArr.push('</div>');
				pArr.push('</li>');
			}
			pStr = pArr.join('');
			$("#commentBody ul").html(pStr);
		},
		error: function(request){
			console.log("获取评论异常");
		}
	});
}

//认购记录
function getSupportList(){
	$.ajax({
		url: path + "/crowdfunding/getSupportList.html",
		type: "post",
		dataType: "json",
		data:{
			"loanNo":loanNo,
			"page":page,
			"rows":10
		}, 
		success: function(data){
			if(!data["success"]){
				sStr = '<div style="padding:30px;color:red;">暂无数据</div>';
				$("#suportTable").html(lStr).show();
				return false;
			}
			var l = data["msg"]["rows"].length,total = data["msg"]["total"];
			var sumPage = (data["msg"]["total"]%10 == 0) ? data["msg"]["total"]/10 : Math.floor(data["msg"]["total"]/10) + 1;
			data = data["msg"]["rows"];
			var sArr = [];
			if(l == 0){
				sStr = '<div style="padding:30px;color:red;">暂无数据</div>';
				$("#suportTable").html(sStr).show();
				return false;
			}
			for(var i=0;i<l;i++){
//				if(data[i]["isLoanLeader"] > 0){//领投人
//					
//				}
				sArr.push('<tr class="even">');
				sArr.push('<td style="width:33%;text-align:center;height: 35px;">'+data[i]["supportUser2"]+'</td>');
				sArr.push('<td style="width:33%;text-align:center;height: 35px;">'+data[i]["supportAmt"]+'</td>');
				sArr.push('<td style="width:33%;text-align:center;height: 35px;">'+data[i]["supportTime"].substring(0, 10)+'</td>');
				sArr.push('</tr>');
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

//关注（赞）事件
function attentionLoan(){
	if(siteUserId == "null"){
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
				$("#gzimg").children("i").text("已关注");
				AlertDialog.tip("关注成功");
			}
		},
		error: function(request){
			console.log("关注项目异常");
		}
	});
}