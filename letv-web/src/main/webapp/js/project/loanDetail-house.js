var loanNo = getQueryString("loanNo");
var recordNum = 1;//记录 预约，认购记录的页数
$(function(){
	getCrowdDetail(); 
	changeTab();
	ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传评论图片
	
	$("#attentionBtn").click(attentionLoan);//关注事件
	$("#talkAboutBtn").click(talkAboutLoan); //约谈
	$("#countBtn").click(countStoke); //计算器
	$("#investLastBtn").unbind("click").click(getUserInfo); //认购，跟投
	$("#subComment").click(submitComment);//发表评论
	
	getCommetNum();//获取评论数量
});
//获取评论数量
function getCommetNum(){
	$.ajax({
		url: path + "/crowdfunding/getCommentList.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(data["success"]){
				$("#commetNum").text(data["msg"]["total"]);
			}
		},
		error: function(request){
			console.log("获取评论数异常");
		}
	});
}
function ecreateWebUploader(pickId, showId, setValueId, parentId){
	urlArr = [];
	var uploader=WebUploader.create({
		auto: true,
	    pick: {
	        id: '#'+pickId,
	        multiple: false
	    },
	    accept: {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png',
	        mimeTypes: 'image/*'
	    },
	    // swf文件路径
	    swf:path + '/js/common/webuploader/Uploader.swf',
	    chunked: true,
	    compress :false,
	    server: path + '/crowdfunding/uploadLoanFile.html',
	    fileNumLimit: 300,
	    fileSizeLimit: 5 * 1024 * 1024,    // 200 M
	    fileSingleSizeLimit: 1 * 1024 * 1024    // 50 M
	});
	
	uploader.on('uploadSuccess', function (file, ret) {
//        var $file = $('#' + file.id);
        try {
            var responseText = (ret._raw || ret),
                json = $.parseJSON(responseText);
            if (json["success"]) {
            	AlertDialog.hide();
            	//预览图片
            	$("#" + parentId).show();
            	if(showId == "pro5"){ //项目展示图片，需要5张，情况特殊
            		urlArr.push(json["msg"]);
            		$("#" + setValueId).val(urlArr.join(","));
            		for(var i=0;i<5;i++){
            			if(!$("#proImageLi").find("img").eq(i).attr("src")){ //从前面数，某一个还未添加路径
            				$("#proImageLi").find("img").eq(i).attr("src", cv["fileAddress"] + "/" + json["msg"]) ;
            				$("#" + parentId).children("div").eq(i).show().children("div").attr("i",i);
            				break;
            			}
            		}
            		$.each($("#proImageLi").children("div"),function(k, v){
            			$(v).mouseover(function(){
                			$(this).children("div").show().unbind("click").bind("click",function(){
                				AlertDialog.confirm(delImage, null, "你确定要删除这张图片吗？", "删除", "取消", $(this).attr("id"));
                			});
                		}).mouseout(function(){
                			$(this).children("div").hide();
                		});
            		});
            	}else{
            		$('#'+showId).attr("src", cv["fileAddress"] + "/" + json["msg"]);
            		$("#" + setValueId).val(json["msg"]);
            	}
            } else {
            	AlertDialog.show(json["msg"], pickId, 10, 30);
//                $file.find('.error').text(json.state).show();
            }
        } catch (e) {
//            $file.find('.error').text(lang.errorServerUpload).show();
        }
    });
	
	return uploader;
}
//获取项目详情信息
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
			//控制页面内，根据项目状态显示
			
			if(data["loanState"] == "preheat"){ //预热中
				$("#hasSprotTtitle").text("目前已预购金额");
				$("#approveAmt").text((data["totalPreSupportAmt"]/10000)+"万");
				$("#loanStateName").text("预热中");
				$("#xs_jd").hide();
				$("#buyNum1Span").hide();
				$("#buttonDiv").html('<a class="xs_bg2" href="javascript:void(0);" id="investFirstBtn">我要领投</a><a class="xs_bg2" href="javascript:void(0);" id="wantPreSupportBtn">我要预约</a>');
				$("#investFirstBtn").click(investFirst); //领投
				$("#wantPreSupportBtn").click(wantPreSupport);//预约
				$("#state2RecordTit").text("预约记录");
				getPreSupportList(recordNum);//预约记录
			}else if(data["loanState"] == "funding"){ //筹款中
				
				$("#approveAmt").text(formatCurrency(data["approveAmt"]));
				$("#loanStateName").text("融资中");
				$("#buttonDiv").html('<a class="xs_bg2" href="javascript:void(0);" id="investLastBtn">我要投资</a>');
				$("#investLastBtn").click(function(){
					getUserInfo();
				}); //认购，跟投
				
				$("#state2RecordTit").text("领投记录");
				getFundSupportList(recordNum);//认购记录
			}else if(data["loanState"] == "funded" || data["loanState"] == "lended" || data["loanState"] == "end"){ //筹款完成
				$("#approveAmt").text(data["approveAmt"]);
				$("#loanStateName").text(data["loanStateName"]);
				$("#talkAboutBtn").css("background", "#CCC").unbind("click");
				$("#buttonDiv").html('<a class="xs_bg2" href="javascript:void(0);" id="investLastBtn">我要投资</a>');
				$("#investLastBtn").css("background", "#CCC").unbind("click");
				
				
				$("#state2RecordTit").text("领投记录");
				getFundSupportList(recordNum);//认购记录
			}else{
				$("#loanStateName").text(data["loanStateName"]);
				$("#talkAboutBtn").css("background", "#CCC").unbind("click");
				$("#buttonDiv").html('<a class="xs_bg2" href="javascript:void(0);" id="investLastBtn">我要投资</a>');
				$("#investLastBtn").css("background", "#CCC").unbind("click");
				$("#attentionBtn").unbind("click");
			}
			
			$("#loanName").text(data["loanName"]); //项目名称
			$("#yLoanuser").val(data["loanUser"]);
			$("#loanUserName").text(data["loanUser2"]);
			$("#loanUserName1").text(data["loanUserName"]);
			
			$("#loanDetail").text(data["loanDetail"]); //项目详情
			$("#loanDes").text(data["loanDes"]); //项目简介
			if(data["photo"]){
				$("#userPhoto").attr("src", cv["fileAddress"] + '/' + data["photo"]); //头像
			}else{
				$("#userPhoto").attr("src", path + '/images/defaultPhoto.png'); //头像
			}
			$("#loanPhoto").attr("src", cv["fileAddress"] + '/' + data["loanLogo"]).attr("title", data["loanName"]).attr("alt", data["loanName"]); //项目封面
			
			if(data["loanVideo"]){ //如果上传了视频
				$("#xs_video").html('<embed width="658px;" height="355px;" src="'+data["loanVideo"]+'" allowFullScreen="true" value="transparent" quality="high" align="middle" wmode="Opaque"  mode="transparent" type="application/x-shockwave-flash"></embed>'); //视频
				//详情tab标签置顶操作
				$(window).scroll(function(){
					if($(document).scrollTop() > 730){
						$("#detailTab").css({"position":"fixed", "top":"0", "padding-top":"15px", "background":"#f7f7f7", "width":"1150px", "margin-left":"-50px", "padding-left":"50px", "z-index":"2"});
						$(".zcdel_leftbox").css("border-top", "1px solid #dddad6");
						$("#buttonDiv").css({"position":"fixed", "top":"0", "padding-left":"80px", "z-index":"2"});
						if($("#buttonDiv a").length == 1){
							$("#buttonDiv a").css("margin-top", "0");
						}else{
							$("#buttonDiv a").eq(0).css({"margin-top":"2px", "margin-left":"-40px", "width": "100px"});
							$("#buttonDiv a").eq(1).css({"margin-top":"-39px", "margin-left":"100px", "width": "100px"});
						}
					}else{
						$("#detailTab").css({"position":"static", "top":"0", "padding-top":"0", "background":"none", "width":"auto", "margin-left":"0", "padding-left":"0"});
						$(".zcdel_leftbox").css("border-top", "none");
						$("#buttonDiv").css({"position":"static", "top":"0", "padding-left":"0"});
						if($("#buttonDiv a").length == 1){
							$("#buttonDiv a").css("margin-top", "10px");
						}else{
							$("#buttonDiv a").css({"margin-top":"10px", "margin-left":"auto", "width": "206px"});
						}
					}
				});
			}else{
				//详情tab标签置顶操作
				$(window).scroll(function(){
					if($(document).scrollTop() > 370){
						$("#detailTab").css({"position":"fixed", "top":"0", "padding-top":"15px", "background":"#f7f7f7", "width":"1150px", "margin-left":"-50px", "padding-left":"50px", "z-index":"2"});
						$(".zcdel_leftbox").css("border-top", "1px solid #dddad6");
						$("#buttonDiv").css({"position":"fixed", "top":"0", "padding-left":"80px", "z-index":"2"});
						if($("#buttonDiv a").length == 1){
							$("#buttonDiv a").css("margin-top", "0");
						}else{
							$("#buttonDiv a").eq(0).css({"margin-top":"2px", "margin-left":"-40px", "width": "100px"});
							$("#buttonDiv a").eq(1).css({"margin-top":"-39px", "margin-left":"100px", "width": "100px"});
						}
					}else{
						$("#detailTab").css({"position":"static", "top":"0", "padding-top":"0", "background":"none", "width":"auto", "margin-left":"0", "padding-left":"0"});
						$(".zcdel_leftbox").css("border-top", "none");
						$("#buttonDiv").css({"position":"static", "top":"0", "padding-left":"0"});
						if($("#buttonDiv a").length == 1){
							$("#buttonDiv a").css("margin-top", "10px");
						}else{
							$("#buttonDiv a").css({"margin-top":"10px", "margin-left":"auto", "width": "206px"});
						}
					}
				});
				$("#xs_video").hide();
			}
			if(data["loanState"] == "funded" || data["loanState"] == "lended" || data["loanState"] == "end"){//筹款结束
				$("#remainDay").text(0);
			}else{
				remainDays = data["remainDays"] ? (data["remainDays"] < 0 ? 0 : data["remainDays"]) : 0;
				$("#remainDay").text(remainDays);
			}
			$("#fundAmt").text(formatCurrency(data["fundAmt"])).attr("num", data["fundAmt"]);
			var fundAmt = (data["fundAmt"])/10000;
			$("#fundAmt1").text(fundAmt+"万").attr("num", data["fundAmt"]);
			$("#fundAmt2").text(formatCurrency(data["fundAmt"].toFixed(2)));
			
			$("#supportRatio1").text((data["supportRatio"]*100).toFixed(2));
			$("#pBar").css("width", (data["supportRatio"]*100).toFixed(0) + "%");
			if(!data["projectFinanceAmt"] || data["projectFinanceAmt"] == 0){ //项目方出资为0，这一行不显示
				$("#projectFinanceAmt").parent().next().removeClass("fr").addClass("fl");
				$("#projectFinanceAmt").attr("num",0);
				$("#projectFinanceAmt").parent().hide();
			}else{
				$("#projectFinanceAmt").text(data["projectFinanceAmt"]/10000+"万").attr("num", data["projectFinanceAmt"]);
			}
			if(data["projectFinanceAmt"]){
				$("#projectFinanceAmt1").text(formatCurrency(data["projectFinanceAmt"].toFixed(2)));
			}
			$("#projectFinanceAmt2").text("￥" + (data["projectFinanceAmt"]).toFixed(2)).attr("num", (data["projectFinanceAmt"]).toFixed(2));
			$("#financeNum").text(data["financeNum"]);
			//if(data["release_time"]){
				$("#release_time").text(data["createTime"].substring(0, 10)); //发起时间
			//}
			if(data["miniInvestAmt"]){
				$("#miniInvestAmt").text(data["miniInvestAmt"]/10000+"万").attr("num", data["miniInvestAmt"]);
				$("#miniInvestAmt1").text(data["miniInvestAmt"].toFixed(2)).attr("num", data["miniInvestAmt"]);
				$("#miniInvestAmt2").text(data["miniInvestAmt"].toFixed(2)).attr("num", data["miniInvestAmt"]);
				$("#fundAmt5").text(data["miniInvestAmt"].toFixed(2)).attr("num", data["miniInvestAmt"]);
			}else{
				$("#miniInvestAmt").text(0).attr("num", 0);
				$("#miniInvestAmt1").text(0).attr("num", 0);
				$("#miniInvestAmt2").text(0).attr("num", 0);
				$("#fundAmt5").text(0).attr("num", 0);
			}
			$("#superIndustry").text(data["superIndustryName"]); //行业
			$("#loanNum").text(data["loanNum"]); //开店数
			$("#buyNum").text(data["buyNum"]); //认购数
			$("#buyNum1").text(data["buyNum"]); //认购数
			$("#remainNum").text(Number($("#financeNum").text()) - Number($("#buyNum1").text())); //剩余份数
			$("#interviewNum").text(data["interviewNum"]); // 约谈人数
			$("#attentionNum").text(data["attentionNum"]);//关注数
			$("#address").text(data["loanProvinceName"] + data["loanCityName"]);
			$("#proInfoContent .loanDetail").html(data["loanDetail"]);//项目介绍
			$("#proBudgetContent").html(data["financeBudget"]); //项目预算
			
			//电子协议下载
			if(data["loanContract"]){
				$('#eContractDownload').attr('href',cv.fileAddress+data["loanContract"]);
			}
			
			//计算器赋值
			if(data["miniInvestAmt"]){
				$("#countInput").attr("placeholder", "不低于"+(data["miniInvestAmt"]).toFixed(2));
			}else{
				$("#countInput").attr("placeholder", "不低于0");
			}
			$("#fundAmt3").text("￥" + (data["fundAmt"]).toFixed(2)).attr("num", (data["fundAmt"]).toFixed(2));
			$("#qtyxhhr").text("￥" + ($("#fundAmt3").attr("num") - $("#projectFinanceAmt2").attr("num")).toFixed(2)).attr("all", ($("#fundAmt3").attr("num") - $("#projectFinanceAmt2").attr("num")).toFixed(2));//计算器其他有限合伙人
			$("#xmfptczblVal").text((Number($("#projectFinanceAmt2").attr("num"))/Number($("#fundAmt3").attr("num"))*100).toFixed(2) + "%").attr("num", (Number($("#projectFinanceAmt2").attr("num"))/Number($("#fundAmt3").attr("num"))*100).toFixed(2)); //项目方(普通合伙人)出资比例
			$("#projectBonusRatio").text((data["projectBonusRatio"]*100).toFixed(2) + "%").attr("num", (data["projectBonusRatio"]*100).toFixed(2)); //项目方(普通合伙人)份额占股比例
			$("#qtyxhhr_czbl").text((100 - Number($("#xmfptczblVal").attr("num"))).toFixed(2) + "%").attr("num", 100 - Number($("#xmfptczblVal").attr("num"))); //投资人（其他有限合伙人）出资比例
			$("#qtyxhhr_zgbl").text((data["investBonusRatio"]*100).toFixed(2) + "%").attr("num", (data["investBonusRatio"]*100).toFixed(2)).attr("all", (data["investBonusRatio"]*100).toFixed(2)); //投资人（其他有限合伙人）份额占股比例
		},
		error: function(request){
			console.log("获取股权详细信息异常");
		}
	});
}


//获取个人信息
function getUserInfo(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
		    if(data["msg"]["userLevel"] == "authed" || data["msg"]["userLevel"] == "lead"){
		    	investLast();
			}else{
				AlertDialog.confirm(gtRz, null, "请先进行跟投人认证", "去认证", "再看看", null);
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
function gtRz(){
	window.location.href = path + "/common/centerRZ.html";
}


//发表评论
function submitComment(){
	if(siteUserId == "null"){
		AlertDialog.tip("请登录后发表评论！");
		return false;
	}
	if($("#comVal").val().length < 5){
		AlertDialog.tip("评论不能少于5个字哦！");
		return false;
	}
	if($("#comVal").val().length > 200){
		AlertDialog.tip("评论不能超过200个字哦！");
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
				//todo 重新查询发表评论数据，数据填充
				$.ajax({
					url: path + "/crowdfunding/getCommentList.html",
					type: "post",
					dataType: "json",
					data: {
						"loanNo": loanNo
					},
					success:function(data){
						$("#comVal").val("");
						$("#commetNum").text(data["msg"]["total"]);
						showProCommentAjax(data);
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

//获取评论
function showProCommentAjax(data){
	var sumPage = (data["msg"]["total"]%10 == 0) ? data["msg"]["total"]/10 : Math.floor(data["msg"]["total"]/10) + 1;
	var pArr = [], pStr = '', l = data["msg"]["rows"].length, data = data["msg"]["rows"];
	for(var i = 0;i<l;i++){
		pArr.push('<li>');
		if(data[i]["photo"]){
			pArr.push('<img style="width:76px;height:76px;border-radius:50%;" src="'+ cv["fileAddress"] + '/' + data[i]["photo"] +'" />');  //评论者头像
		}else{
			pArr.push('<img style="width:76px;height:76px;border-radius:50%;" src="'+path+'/images/defaultPhoto.png" />');  //评论者头像
		}
		pArr.push('<span class="pro_pl_nr">');
		pArr.push('<span><i>'+data[i]["discussUser2"]+'</i>  '+data[i]["differDays"]+'天前</span>');
		pArr.push('<span>'+data[i]["content"]+'</span>');
		pArr.push('</li>');
	}
	pStr = pArr.join("");
	$("#commentDiv>ul").html(pStr);
	//分页设置
	pagePause = 0;
	if(page < 2){
		$("#commonPage").jPages({
			containerID : "commentDiv",
			clickStop   : true,
			perPage	: 10,
			allSumPage : sumPage,
			callback: ajaxPagecommentData
		});
	}
	$("#proCommentContent").fadeIn();
}

//切换tab标签
function changeTab(){
	$("#detailTab>a").click(function(){
		$("#detailTab>a").removeClass("cur");
		$(this).addClass("cur");
		var tName = $(this).attr("name"); 
		var turl = $(this).attr("url");
		if($(this).attr("name") == "proBudget" ||  $(this).attr("name") == "rgList" || $(this).attr("name") == "proComment" || $(this).attr("name") == "proProgress" ||  $(this).attr("name") == "proAgree"){//其他登录后可看
			if(getCookie("logined") && siteUserId != "null"){//判断是否登录 未登录先登录
				$.ajax({
					url: path + "/crowdfundUserCenter/getCrowdfundUserDetail.html",
					type: "post",
					dataType: "json",
					success: function(data){
						if(!data["success"]){
							return false;
						}
					    if(data["msg"]["userLevel"] == "authed" || data["msg"]["userLevel"] == "lead"){  // 判断是否认证 ，未认证先去认证
					    	showInfoContent(turl, tName,1);
						}else{
							AlertDialog.confirm(charge, null, "请先进行跟投人认证", "去认证", "再看看", null);
						}
					},
					error: function(request){
						console.log("获取个人信息异常");
					}
				});
			}else{
				AlertDialog.confirm(go2Login,null,"您还没有登录，请先登录","登录","取消",null);
				return false;
			}
		}else{ //项目简介、电子协议和项目进度可直接点击查看
			showInfoContent($(this).attr("url"), $(this).attr("name"),1);
		}
		
		
//		showInfoContent($(this).attr("url"), $(this).attr("name"),1);
	});
}
function charge(){
	window.location.href = path + "/common/centerRZ.html";
}
//查看详情页标签下内容
function showInfoContent(url, id, page){
	$("#detailBody>div").hide();
	//点击标签按钮后，页面滑动到合适查看的位置
	if($("#xs_video").css("display") == "none"){
		$('html,body').animate({
			scrollTop : '600px'
		}, 800);
	}else{
		$('html,body').animate({
			scrollTop : '600px'
		}, 800);
	}
	if($("#" + id + "Content").attr("h") == "1"){ //已经填充过内容
		$("#" + id + "Content").fadeIn();
		return false;
	}
	if(id == "proComment"){ //评论标签下获取数据
		$.ajax({
			url: path + "/crowdfunding/getCommentList.html",
			type: "post",
			dataType: "json",
			data: {
				"loanNo": loanNo,
				"rows": 10,
				"page": page
				},
			success: function(data){
				if(!data["success"]){
					return false;
				}
				var sumPage = (data["msg"]["total"]%10 == 0) ? data["msg"]["total"]/10 : Math.floor(data["msg"]["total"]/10) + 1;
				var pArr = [], pStr = '', l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				for(var i = 0;i<l;i++){
					pArr.push('<li>');
					if(data[i]["photo"]){
						pArr.push('<img style="width:76px;height:76px;border-radius:50%;" src="'+ cv["fileAddress"] + '/' + data[i]["photo"] +'" />');  //评论者头像
					}else{
						pArr.push('<img style="width:76px;height:76px;border-radius:50%;" src="'+path+'/images/defaultPhoto.png" />');  //评论者头像
					}
					pArr.push('<span class="pro_pl_nr">');
					pArr.push('<span><i>'+data[i]["discussUser2"]+'</i>  '+data[i]["differDays"]+'天前</span>');
					pArr.push('<span>'+data[i]["content"]+'</span>');
					//pArr.push('<img src="<%=path %>/images/pl.png" />');
					//pArr.push('</span><div class="pro_pl_nrpl"><a href="#">评论（0）</a></div>');
					pArr.push('</li>');
				}
				pStr = pArr.join("");
				$("#commentUl").html(pStr);
				$("#proCommentContent").fadeIn();
				//分页设置
				pagePause = 0;
				if(page < 2){
					$("#commonPage").jPages({
						containerID : "commentDiv",
						clickStop   : true,
						perPage	: 10,
						allSumPage : sumPage,
						callback: ajaxPagecommentData
					});
				}
			},
			error: function(request){
				console.log("获取股权项目评论异常");
			}
		});
	}else if(id == "proProgress"){
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
					pStr = '<span style="padding:15px;color:red;">暂无数据</span>';
					$("#loanProgress").html(pStr);
					$("#proProgressContent").fadeIn().attr("h", "1");
					return false;
				}
				for(var i=0;i<l;i++){
					//显示项目进展
					pArr.push('<li>');
					pArr.push('<span class="pro_jz_rq">'+data[i]["enterTime"].substring(0,10)+'</span>');
					pArr.push('<span class="pro_jz_yq"><img src="'+path+'/images/point_2.png" /></span>');
					pArr.push('<span class="pro_jz_nr">');
/*					pArr.push('<span class="pro_jz_fqr"><span>发起人</span><span style="display:inline-block;width:40px;"></span>'+data[i]["enterUser2"]+'</span>');
*/					pArr.push('<span class="pro_jz_del">'+data[i]["enterContent"]+'</span>');
					pArr.push('</span></li>');
				}
				//展示项目进展
				pStr = pArr.join("");
				$("#loanProgress").html(pStr);
				$("#proProgressContent").fadeIn().attr("h", "1");
			},
			error: function(request){
				console.log("获取项目进展异常");
			}
		});
	}else if(id == "rgList"){
		var sArr = [], sStr = '', l, sumPage;
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
					$("#rgListContent").html(lStr).show();
					return false;
				}
				l = data["msg"]["rows"].length,total = data["msg"]["total"];
				sumPage = (data["msg"]["total"]%10 == 0) ? data["msg"]["total"]/10 : Math.floor(data["msg"]["total"]/10) + 1;
				data = data["msg"]["rows"];
				if(l == 0){
					sStr = '<div style="padding:30px;color:red;">暂无数据</div>';
					$("#rgListContent").html(sStr).show();
					return false;
				}
				for(var i=0;i<l;i++){
					if(i%2 == 0){ //even
						sArr.push('<tr class="even">');
					}else{
						sArr.push('<tr class="odd">');
					}
					sArr.push('<td style="width:87px;text-align:center;">'+(i+1)+'</td>');
					sArr.push('<td style="width:123px;text-align:center;">'+data[i]["supportUser2"]+'</td>');
					sArr.push('<td style="width:155px;text-align:center;">'+data[i]["supportAmt"]+'</td>');
					sArr.push('<td style="width:200px;text-align:center;">'+data[i]["supportTime"].substring(0, 10)+'</td>');
					sArr.push('<td style="width:100px;text-align:center;">'+data[i]["payStateName"]+'</td>');
					sArr.push('</tr>');
				}
				sStr = sArr.join("");
				$("#rgListTable").html(sStr);
				$("#rgListContent").show();
				//分页设置
				pagePause = 0;
				if(page < 2){
					$("#rgPage").jPages({
						containerID : "rgListTable",
						clickStop   : true,
						perPage	: 10,
						allSumPage : sumPage,
						callback: ajaxPagergData
					});
				}
			},
			error: function(request){
				console.log("预约记录请求异常");
			}
		});
	}
}
function ajaxPagergData(obj){
	if(pagePause == 0){
		return false;
	}
	showInfoContent($("#detailTab>a.cur").attr("url"), $("#detailTab>a.cur").attr("name"), obj["current"]);
}
function ajaxPagecommentData(obj){
	if(pagePause == 0){
		return false;
	}
	showInfoContent($("#detailTab>a.cur").attr("url"), $("#detailTab>a.cur").attr("name"), obj["current"]);
}
//关注（赞）事件
function attentionLoan(){
	if(siteUserId == "null"){
		go2Login();
		return false;
	}
	var attNum = 0;
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
				$(this).children("c").text("已关注");
				attNum = Number($("#attentionNum").text()) + 1;
				$("#attentionNum").text(attNum);
			}
		},
		error: function(request){
			console.log("关注项目异常");
		}
	});
}
//约谈事件
function talkAboutLoan(){
	if(siteUserId == "null"){
		go2Login();
		return false;
	}
	//展示约谈弹框
	$("#bgpop").show();
	var sl = (cv["winW"]-419)/2, st = (cv["winH"]-304)/2;
	$("#talkDiv").css({"top":st+"px","left":sl+"px"}).show();
	//关闭事件
	$("#bgpop").click(function(){
		$("#talkDiv").hide();
		$(this).hide();
		if($("#tip_div").attr("id")){ //如果存在提示，关闭弹框同事关闭提示
			AlertDialog.hide();
		}
	});
	$("#talkBtn").unbind("click").click(function(){
		if(Valify.isNull($("#talkArea").val(), "talkArea", -50, 40)){
			$.ajax({
				url: path + "/crowdfundingInvest/saveInterviewRecord.html",
				type: "post",
				dataType: "json",
				data: {
					"loanNo": loanNo,
					"isStationMsg": $("#sendEmailCheck").prop("checked") ? 1 : 0,
					"isEmail": $("#sendLoanEmail").prop("checked") ? 1 : 0,
					"interviewContent": $("#talkArea").val()
				},
				success: function(data){
					if(!data["success"]){
						$("#talkDiv").hide();
						AlertDialog.tip(data["msg"]);
						return false;
					}else{
						$("#talkDiv").hide();
						AlertDialog.tip("约谈申请成功！");
					}
				},
				error: function(request){
					console.log("提交约谈申请异常！");
				}
			});
		}
	});
}
//筹好业计算器
function countStoke(){
	$("#stockBgpop").show();
	var sl = (cv["winW"]-604)/2, st = (cv["winH"]-344)/2;
	$("#countDiv").css({"top":st+"px","left":sl+"px"}).show();
	$("#countInput").keyup(function(){
		if(isNaN($(this).val())){
			$(this).val("");
		}
	});
	//计算器内计算比例
	$("#counterBtn").click(function(){
		if(Number($("#countInput").val()) < Number($("#miniInvestAmt").attr("num")) || !$("#countInput").val()){
			AlertDialog.show("输入金额不能低于"+$("#miniInvestAmt").attr("num"), "countInput", -10, 30);
			return false;
		}
		if(Number($("#countInput").val()) > (Number($("#fundAmt3").attr("num")) - Number($("#projectFinanceAmt2").attr("num")))){
			AlertDialog.show("输入金额不能高于"+(Number($("#fundAmt3").attr("num")) - Number($("#projectFinanceAmt2").attr("num"))), "countInput", -10, 30);
			return false;
		}
		AlertDialog.hide();
		//计算得值
		$("#qtyxhhr").text("￥" + formatCurrency(Number($("#fundAmt3").attr("num")) - Number($("#projectFinanceAmt2").attr("num")) - Number($("#countInput").val()))).attr("num", Number($("#fundAmt3").attr("num")) - Number($("#projectFinanceAmt2").attr("num")) - Number($("#countInput").val()));
		$("#qtyxhhr_czbl").text((Number($("#qtyxhhr").attr("num"))/Number($("#fundAmt3").attr("num"))*100).toFixed(2) + "%");
		$("#investPercent").text((Number($("#countInput").val())/Number($("#fundAmt3").attr("num"))*100).toFixed(2) + "%");
		$("#stokePercent").text(((Number($("#countInput").val())/Number($("#qtyxhhr").attr("all"))) * (100 - Number($("#projectBonusRatio").attr("num")))).toFixed(2) + "%").attr("num", ((Number($("#countInput").val())/Number($("#qtyxhhr").attr("all"))) * (100 - Number($("#projectBonusRatio").attr("num")))).toFixed(2)) ;
		$("#qtyxhhr_zgbl1").text(((100 - Number($("#projectBonusRatio").attr("num")) - Number($("#stokePercent").attr("num")))).toFixed(2) + "%");
		
	});
	//关闭
	$("#stockBgpop").click(function(){
		$("#countDiv").hide();
		$("#stockBgpop").hide();
		if($("#tip_div").attr("id")){
			AlertDialog.hide();
		}
	});
}
function checkNum(str, id){
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 30);
		$("#" + id).val("");
		return false;
	}else{
		if(isNaN(str)){
			AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 30);
			$("#" + id).val("");
			return false;
		}
		if(Number(str) < Number($("#preMinAmt").attr("num"))){
			AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 30);
			$("#" + id).val("");
			return false;
		}
		if(Number(str) > Number($("#preMaxAmt").attr("num"))){
			AlertDialog.show($("#" + id).attr("logicMessage1"), id, 10, 30);
			return false;
		}
	}
	AlertDialog.hide();
	return true;
}
//我要领投
function investFirst(){
	if(siteUserId == "null"){
		go2Login();
		return false;
	}
	if($("#yLoanuser").val() == siteUserId){
		AlertDialog.tip("您不能领投自己的项目");
		return false;
	}
	$.ajax({
		url: path + "/crowdfundUserCenter/getCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			if(data["userLevel"] == "lead"){
				$("#bgpop").show();
				var it = (cv["winH"]-410)/2, il = (cv["winW"]-455)/2;
				$("#investFirstDiv").css({"top": it+"px", "left": il+"px"}).show();
				//关闭
				$("#bgpop").click(function(){
					if($("#tip_div").attr("id")){
						$("#tip_div").hide();
					}
					$("#bgpop").hide();
					$("#investFirstDiv").hide();
					//弹框操作数据初始化
					$("#fenshuNum1").val("1");
					$("#applyExplain").val("");
					$("#fundAmt5").text($("#miniInvestAmt1").attr("num")).attr("num", $("#miniInvestAmt1").attr("num"));
					$("#couInvestZgbl1").text((Number($("#fundAmt5").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))).toFixed(2));
				});
				$("#remainsFenshu1").text(Number($("#financeNum").text()) - Number($("#buyNum1").text()));//剩余份数
				$("#supFomr").find("input[name='followInvest']").remove();
				$("#fenshuNum1").blur(function(){
					if(Number($(this).val()) > Number($("#remainsFenshu1").text())){
						$(this).val("0");
						$("#fundAmt5").text((Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(2)).attr("num", Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())) ;
						//投资总额 / (融资总额 - 项目方出资) * 出让股份
						$("#couInvestZgbl1").text((Number($("#fundAmt5").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))).toFixed(2));
					
					}else{
						$("#remainsFenshu1").text(Number($("#financeNum").text()) - Number($("#buyNum1").text())-Number($("#fenshuNum1").val()));//剩余份数
						$("#fundAmt5").text((Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(2)).attr("num", Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())) ;
						//投资总额 / (融资总额 - 项目方出资) * 出让股份
//						$("#couInvestZgbl1").text((Number($("#fundAmt5").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))).toFixed(2));
						//投资总额 / (融资总额 - 项目方出资) * 出让股份
						var projectRental = Number($("#fundAmt5").attr("num")).toFixed(2);
						var dollars  = Number($("#fundAmt1").attr("num"));
						var contribution = Number($("#projectFinanceAmt").attr("num"));
						var share = Number($("#qtyxhhr_zgbl").attr("all"))/100;
						$("#couInvestZgbl1").text((((projectRental/(dollars-contribution))*share)*100).toFixed(2));	
					}
				});
				//加一份
				$("#iJIa1").unbind("click").click(function(){
					var fsNum = Number($("#remainsFenshu1").text()), jNum = 0;
					if(Number($("#remainsFenshu1").text()) <= 0){ //超出剩余份数
						$("#iJIa1").css("color", "#ccc");
						return false;
					}else{
						jNum = Number($("#fenshuNum1").val()) + 1;
						$("#fenshuNum1").val(jNum);
						$("#fundAmt5").text((Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(2)).attr("num", Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())) ;
						$("#remainsFenshu1").text(Number($("#financeNum").text()) - Number($("#buyNum1").text())-Number($("#fenshuNum1").val()));//剩余份数
						//投资总额 / (融资总额 - 项目方出资) * 出让股份
//						$("#couInvestZgbl1").text((Number($("#fundAmt5").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))).toFixed(2));
						//投资总额 / (融资总额 - 项目方出资) * 出让股份
						var projectRental = Number($("#fundAmt5").attr("num")).toFixed(2);
						var dollars  = Number($("#fundAmt1").attr("num"));
						var contribution = Number($("#projectFinanceAmt").attr("num"));
						var share = Number($("#qtyxhhr_zgbl").attr("all"))/100;
						$("#couInvestZgbl1").text((((projectRental/(dollars-contribution))*share)*100).toFixed(2));	
					}
				});
				//减一份
				$("#iJian1").unbind("click").click(function(){
					var fsNum = Number($("#remainsFenshu1").text()), jNum = 0;
					if(Number($("#fenshuNum1").val()) < 1){ //输入框内只剩0份
						$("#iJian1").css("color", "#CCC");
						return false;
					}else{
						jNum = Number($("#fenshuNum1").val()) - 1;
						$("#fenshuNum1").val(jNum);
						$("#fundAmt5").text((Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())).toFixed(2)).attr("num", Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum1").val())) ;
						$("#remainsFenshu1").text(Number($("#financeNum").text()) - Number($("#buyNum1").text())-Number($("#fenshuNum1").val()));//剩余份数
						//投资总额 / (融资总额 - 项目方出资) * 出让股份
//						$("#couInvestZgbl1").text((Number($("#fundAmt5").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))).toFixed(2));
						//投资总额 / (融资总额 - 项目方出资) * 出让股份
						var projectRental = Number($("#fundAmt5").attr("num"));
						var dollars  = Number($("#fundAmt1").attr("num"));
						var contribution = Number($("#projectFinanceAmt").attr("num"));
						var share = Number($("#qtyxhhr_zgbl").attr("all"))/100;
						$("#couInvestZgbl1").text((((projectRental/(dollars-contribution))*share)*100).toFixed(2));	
					}
				});
				//申请领投前验证
				$("#investFBtn").click(function(){
					if(Valify.isNull($("#applyExplain").val(), "applyExplain", 10, 30)){
						$.ajax({
							url: path + "/fundpool/leaderPay/checkBeforeOrderInvest.html",
							type: "post",
							dataType: "json",
							async: false,
							data: {
								"buyNum": $("#fenshuNum1").val(),
								"loanNo": loanNo
							},
							success: function(data){
								if(!data["success"]){
									$("#investFirstDiv").hide();
									AlertDialog.tip(data["msg"]);
									setTimeout(function(){
										window.open(path+"/common/centerRZ.html?type=gtr","_blank");
									},1500);
								}else{
									$("#investTip").hide();
									$("#lForm input[name='buyNum']").val($("#fenshuNum1").val());
									$("#lForm input[name='loanNo']").val(loanNo);
									$("#lForm input[name='applyLeadDes']").val($("#applyExplain").val());
									$("#lFormBtn").click();
								}
							},
							error: function(request){
								console.log("验证领投请求异常");
							}
						});
					};
				});
			}else{
				AlertDialog.confirm(goRZLtr, null, "领投人认证通过后可进行领投", "去认证", "取消");
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
function goRZLtr(){
	window.location.href = path + "/common/centerRZ.html?type=ltr";
}
//我要预约
function wantPreSupport(){
	if(siteUserId == "null"){
		go2Login();
		return false;
	}
	$("#preSupportInput").val("");
	$("#bgpop").show();
	
	var it = (cv["winH"]-230)/2, il = (cv["winW"]-415)/2;
	$("#preSupportDiv").css({"top": it+"px", "left": il+"px"}).show();
	//赋值
	$("#preMinAmt").text($("#miniInvestAmt").text()).attr("num", $("#miniInvestAmt").attr("num"));
	$("#preMaxAmt").text(formatCurrency(Number($("#fundAmt1").attr("num"))*2)).attr("num", Number($("#fundAmt1").attr("num"))*2);
	//关闭
	$("#bgpop").click(function(){
		if($("#tip_div").attr("id")){
			AlertDialog.hide();
		}
		$("#bgpop").hide();
		$("#preSupportDiv").hide();
	});
	$("#canelPreSupportBtn").click(function(){
		if($("#tip_div").attr("id")){
			AlertDialog.hide();
		}
		$("#bgpop").hide();
		$("#preSupportDiv").hide();
	});
	$("#preSupportBtn").unbind("click").click(function(){
		if(checkNum($("#preSupportInput").val(), "preSupportInput")){
			$.ajax({
				url: path + "/crowdfundingInvest/savePreSupport.html",
				type: "post",
				dataType: "json",
				async: false,
				data: {
					"supportAmt": $("#preSupportInput").val(),
					"loanNo": loanNo
				},
				success: function(data){
					if(!data["success"]){
						AlertDialog.show(data["msg"], "preSupportInput", 10, 20);
					}else{
						$("#preSupportTipl").hide();
						$("#preSupportDiv").hide();
						AlertDialog.tip("预购成功!");
					}
				},
				error: function(request){
					console.log("我要预约请求异常");
				}
			});
		}
	});
}
//我要认购，跟投
function investLast(){
	if(siteUserId == "null"){
		go2Login();
		return false;
	}
	var ltArr = [], ltStr = '', l; //加载领投人列表
	$("#fenshuNum").val("1");
	$("#investValiInput").val("");
	$("#bgpop").show();
	var it = (cv["winH"]-505)/2, il = (cv["winW"]-600)/2;
	$("#investLastDiv").css({"top": it+"px", "left": il+"px"}).show();
	//关闭
	$("#bgpop").click(function(){
		$("#bgpop").hide();
		AlertDialog.hide();
		$("#investLastDiv").hide();
		$("#tip_div").hide();
	});
	if($("#supFomr input[name='leadInvestor']").length == 0){
		$("#supFomr").append($('<input type="hidden" name="leadInvestor"/>'));
	}
	$("#remainsFenshu").text(Number($("#financeNum").text()) - Number($("#buyNum1").text()));//剩余份数
	$("#fundAmt4").text($("#miniInvestAmt1").text());
	//更换验证码
	$("#changeValiBtn").click(function(){
		$("#imageInvest").attr("src", path + "/security/securityCodeImage.html?" + new Date().getTime());
	});
	$("#imageInvest").attr("src", path + "/security/securityCodeImage.html?" + new Date().getTime());
	$("#fenshuNum").blur(function(){
		if(Number($(this).val()) > (Number($("#financeNum").text()) - Number($("#buyNum1").text()))){
			$(this).val("0");
			var currentBuy = (Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum").val())).toFixed(2);
			$("#fundAmt4").text(currentBuy).attr("num", currentBuy) ;
			//投资总额 / (融资总额 - 项目方出资) * 出让股份
			var projectRental = Number($("#fundAmt4").attr("num"));
			var dollars  = Number($("#fundAmt1").attr("num"));
			var contribution = Number($("#projectFinanceAmt").attr("num"));
			var share = Number($("#qtyxhhr_zgbl").attr("all"))/100;
			$("#couInvestZgbl").text((((projectRental/(dollars-contribution))*share)*100).toFixed(2));	
		}else{
			$("#remainsFenshu").text(Number($("#financeNum").text()) - Number($("#buyNum1").text())-Number($("#fenshuNum").val()));//剩余份数
			var currentBuy = (Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum").val())).toFixed(2);
			$("#fundAmt4").text(currentBuy).attr("num", currentBuy) ;
			//投资总额 / (融资总额 - 项目方出资) * 出让股份
			var projectRental = Number($("#fundAmt4").attr("num"));
			var dollars  = Number($("#fundAmt1").attr("num"));
			var contribution = Number($("#projectFinanceAmt").attr("num"));
			var share = Number($("#qtyxhhr_zgbl").attr("all"))/100;
			$("#couInvestZgbl").text((((projectRental/(dollars-contribution))*share)*100).toFixed(2));
		}
	});
	//加一份
	$("#iJIa").unbind("click").click(function(){
		var fsNum = Number($("#remainsFenshu").text()), jNum = 0;
		if(Number($("#remainsFenshu").text()) <= 0){ //超出剩余份数
			$("#iJIa").css("color", "#ccc");
			return false;
		}else{
			jNum = Number($("#fenshuNum").val()) + 1;
			$("#fenshuNum").val(jNum);
			var currentBuy = (Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum").val())).toFixed(2);
			$("#fundAmt4").text(currentBuy).attr("num", currentBuy) ;
			$("#remainsFenshu").text(Number($("#financeNum").text()) - Number($("#buyNum1").text())-Number($("#fenshuNum").val()));//剩余份数

			//投资总额 / (融资总额 - 项目方出资) * 出让股份
			var projectRental = Number($("#fundAmt4").attr("num"));
			var dollars  = Number($("#fundAmt1").attr("num"));
			var contribution = Number($("#projectFinanceAmt").attr("num"));
			var share = Number($("#qtyxhhr_zgbl").attr("all"))/100;
			$("#couInvestZgbl").text((((projectRental/(dollars-contribution))*share)*100).toFixed(2));
		}
	});
	//减一份
	$("#iJian").unbind("click").click(function(){
		var fsNum = Number($("#remainsFenshu").text()), jNum = 0;
		if(Number($("#fenshuNum").val()) < 2){ //输入框内只剩0份
			$("#iJian").css("color", "#CCC");
			return false;
		}else{
			jNum = Number($("#fenshuNum").val()) - 1;
			$("#fenshuNum").val(jNum);
			var currentBuy = (Number($("#miniInvestAmt1").attr("num")) * Number($("#fenshuNum").val())).toFixed(2);
			$("#fundAmt4").text(currentBuy).attr("num", currentBuy) ;
			$("#remainsFenshu").text(Number($("#financeNum").text()) - Number($("#buyNum1").text())-Number($("#fenshuNum").val()));//剩余份数

			//投资总额 / (融资总额 - 项目方出资) * 出让股份
			var projectRental = Number($("#fundAmt4").attr("num"));
			var dollars  = Number($("#fundAmt1").attr("num"));
			var contribution = Number($("#projectFinanceAmt").attr("num"));
			var share = Number($("#qtyxhhr_zgbl").attr("all"))/100;
			$("#couInvestZgbl").text((((projectRental/(dollars-contribution))*share)*100).toFixed(2));
		}
	});
	//获取加载领投人列表
	$.ajax({
		url: path + "/crowdfundingInvest/getLeader.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"].length, data = data["msg"];
			if(l == 0){
				$("#investLBtn").hide();
				$("#gtInvestBtn").show();
			}else{
				for(var i=0;i<l;i++){
					if(i == 0){
						ltArr.push('<dd class="a_home" id="'+data[i]["leadInvestor"]+'">');
					}else{
						ltArr.push('<dd id="'+data[i]["leadInvestor"]+'">');
					}
					ltArr.push('<a href="javascript:void(0);" class="clearfix">');
					if(data[i]["photo"]){
						ltArr.push('<p class="p1"><img src="'+cv["fileAddress"]+'/'+data[i]["photo"]+'" width="48"/></p>');
					}else{
						ltArr.push('<p class="p1"><img src="'+path+'/images/defaultPhoto.png" width="48"/></p>');
					}
					ltArr.push('<p class="p2" style="height:30px;line-height:45px;">'+data[i]["companyName"]+'</p>');
					ltArr.push('<p class="p3" style="height:30px;line-height:20px;">认投金额:<span>'+data[i]["totalSupportAmt"]+'</span></p>');
					ltArr.push('</a></dd>');
				}
				ltStr = ltArr.join("");
				$("#ltrList").html(ltStr);
				$("#ltrList dd").click(function(){
					$("#ltrList dd").removeClass("a_home");
					$(this).addClass("a_home");
				});
			};
		},
		error: function(request){
			console.log("获取领头人列表异常！");
		}
	});
	
	//跟投前验证
	$("#investLBtn").unbind("click").click(function(){
		if($("#ltrList dd.a_home").length == 0){
			AlertDialog.show("请选择领投人", "ltrList", 20, 100);
			return false;
		}
		if($("#fenshuNum").val() == "0"){ //添加认购份数
			AlertDialog.show("请添加认购份数", "fenshuNum", 10, 20);
			return false;
		}
		if(!$("#investValiInput").val()){
			AlertDialog.show("请输入验证码", "investValiInput", 10, 20);
			return false;
		}
		$.ajax({
			url: path + "/fundpool/invest/checkBeforeEntitySupport.html",
			type: "post",
			dataType: "json",
			async: false,
			data: {
				"buyNum": $("#fenshuNum").val(),
				"loanNo": loanNo,
				"investType": "followInvest",
				"leadInvestor": $("#ltrList>dd.a_home").attr("id"),
				"valiCode": $("#investValiInput").val()
			},
			success: function(data){
				AlertDialog.hide();
				if(!data["success"]){
					$("#imageInvest").attr("src", path + "/security/securityCodeImage.html?" + new Date().getTime());
					AlertDialog.show(data["msg"], "investValiInput", 10, 20);
				}else{
					$("#investTip").hide();
					$("#formBuyNum").val($("#fenshuNum").val());
					$("#formLoanNo").val(loanNo);
					$("#supFomr").find("input[name='investType']").val("followInvest"); //跟投改变值
					$("#supFomr").find("input[name='leadInvestor']").val($("#ltrList>dd.a_home").attr("id"));
					$("#supFormBtn").click();
				}
			},
			error: function(request){
				console.log("验证领投请求异常");
			}
		});
	});
}

//预约记录
function getPreSupportList(page){
	var sArr = [], sStr = '', l;
	$.ajax({
		url: path + "/crowdfundingInvest/getPreSupportList.html",
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
				$("#preSupportList").html(lStr);
				return false;
			}
			l = data["msg"]["rows"].length,total = data["msg"]["total"], data = data["msg"]["rows"];
			if(l == 0){
				sStr = '<div style="padding:30px;color:red;">暂无数据</div>';
				$("#preSupportList").html(sStr);
				return false;
			}
			for(var i =0;i<l;i++){
				sArr.push('<li class="clearfix">');
				if(data[i]["photo"]){
					sArr.push('<div class="xs_img fl"><img src="'+cv["fileAddress"] + '/' + data[i]["photo"]+'" alt="" /></div>');
				}else{
					sArr.push('<div class="xs_img fl"><img src="'+path + '/images/defaultPhoto.png" /></div>');
				}
				sArr.push('<div class="xs_wz fr">');
				sArr.push('<div class="xs_yyje">预约金额：<i>'+data[i]["supportAmt"]+'</i>元 </div>');
				sArr.push('<div class="xs_name">'+data[i]["supportUser2"]+'</div>');
				if(data[i]["selfDetail"]){
					sArr.push('<p>个人简介：<span>'+data[i]["selfDetail"]+'</span></p>');
				}else{
					sArr.push('<p>个人简介：<span>--</span></p>');
				}
				sArr.push('</div>');
				sArr.push('</li>');
			}
			sStr = sArr.join("");
			$("#preSupportList").append(sStr);
			if(total > 10){
				$("#more").show();
				$("#more").unbind("click").click(function(){
					recordNum++;
					getPreSupportList(recordNum);
				});
				if(total <= (10*recordNum)){
					$("#more").hide();
				}
			}
			
		},
		error: function(request){
			console.log("预约记录请求异常");
		}
	});
}
//投资记录
function getFundSupportList(page){
	var sArr = [], sStr = '', l;
	$.ajax({
		url: path + "/crowdfundingInvest/getLeadSupportList.html",
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
				$("#preSupportList").html(lStr);
				return false;
			}
			l = data["msg"]["rows"].length,total = data["msg"]["total"], data = data["msg"]["rows"];
			if(l == 0){
				sStr = '<div style="padding:30px;color:red;">暂无数据</div>';
				$("#preSupportList").html(sStr);
				return false;
			}
			for(var i =0;i<l;i++){
				sArr.push('<li class="clearfix">');
				if(data[i]["photo"]){
					sArr.push('<div class="xs_img fl"><img src="'+cv["fileAddress"] + '/' + data[i]["photo"]+'" alt="" /></div>');
				}else{
					sArr.push('<div class="xs_img fl"><img src="'+path + '/images/defaultPhoto.png" /></div>');
				}
				sArr.push('<div class="xs_wz fr">');
				sArr.push('<div class="xs_yyje">认购金额：<i>'+data[i]["supportAmt"]+'</i>元 </div>');
				sArr.push('<div class="xs_name">'+data[i]["supportUser2"]+'</div>');
				if(data[i]["selfDetail"]){
					sArr.push('<p>个人简介：<span>'+data[i]["selfDetail"]+'</span></p>');
				}else{
					sArr.push('<p>个人简介：<span>--</span></p>');
				}
				sArr.push('</div>');
				sArr.push('</li>');
				if(data[i]["isLoanLeader"] == 1){ //是领投人
					sArr.push('<div class="bz_ltr"></div>');
				}
			}
			sStr = sArr.join("");
			$("#preSupportList").append(sStr);
			
			if(total > 10){
				$("#more").show();
				$("#more").unbind("click").click(function(){
					recordNum++;
					getFundSupportList(recordNum);
				});
				if(total <= (10*recordNum)){
					$("#more").hide();
				}
			}
		},
		error: function(request){
			console.log("预约记录请求异常");
		}
	});
}
