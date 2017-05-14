var loanNo= getQueryString('loanNo');
var transferNo = getQueryString('transferNo');
var recordNum = 1;//记录 预约，认购记录的页数
$(function(){
	getCrowdDetail(); 
	changeTab();
	getTransFerDetail(createTransfer);
	ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传评论图片
	$("#subComment").click(submitComment);//发表评论
	purchaseHistory();
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

function charge(){
	window.location.href = path + "/common/centerRZ.html";
}
//获取项目详情信息
function getCrowdDetail(){
	var remainDays = 0;
	$.ajax({
		url: path + "/crowdfunding/getCrowdDetail.html",
		type: "post",
		dataType: "json",
//		async: false,
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			
			//控制页面内，根据项目状态显示
			if(data["loanState"] == "preheat"){ //预热中
				$("#buttonDiv").html('<a class="xs_bg2" href="javascript:void(0);" id="investFirstBtn"  style="margin-top: 10px;>我要投资</a>');
				$("#investLastBtn").css("background", "#CCC").unbind("click");
			}else if(data["loanState"] == "funding"){ //筹款中
				$("#buttonDiv").html('<a class="xs_bg2" href="javascript:void(0);" id="investLastBtn"  style="margin-top: 10px;>我要投资</a>');
				$("#investLastBtn").css("background", "#CCC").unbind("click");
			}else if(data["loanState"] == "funded" || data["loanState"] == "lended" || data["loanState"] == "end"){ //筹款完成
				$("#buttonDiv").html('<a class="xs_bg2" href="javascript:void(0);" id="investLastBtn" style="margin-top: 10px;">我要投资</a>');
			}else{
				$("#buttonDiv").html('<a class="xs_bg2" href="javascript:void(0);" id="investLastBtn"  style="margin-top: 10px;>我要投资</a>');
				$("#investLastBtn").css("background", "#CCC").unbind("click");
			}
			// 点击我要投资前先认证
			$.ajax({
				url:API['getCrowdfundUserDetail'],
				type:'post',
				dataType: "json",
				data:{
				},
				success:function(data){
					if(!data['success']){
						return false
					}
					var all = $("#financeNum").attr('num');
					var buy = $("#buyNum1").attr('num'); 
					$("#investLastBtn").unbind("click").click(function(){
						 if(data["msg"]["userLevel"] == "authed" || data["msg"]["userLevel"] == "lead"){
							 	investLast();
								return false;
						}else{
							AlertDialog.confirm(charge, null, "请先进行投资认证", "去认证", "再看看", null);
						}
					});
					
				},
				error:function(){
					
				}
			})
			
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
			$("#superIndustry").text(data["superIndustryName"]); //行业
			$("#address").text(data["loanProvinceName"] + data["loanCityName"]);
			$("#proInfoContent .loanDetail").html(data["loanDetail"]);//项目介绍
			$("#proBudgetContent").html(data["financeBudget"]); //项目预算
			$('#release_time').html(data["createTime"])
			//电子协议下载
			if(data["loanContract"]){
				$('#eContractDownload').attr('href',cv.fileAddress+data["loanContract"]);
			}
			
			
			
//			$("#fundAmt3").text("￥" + (data["fundAmt"]).toFixed(2)).attr("num", (data["fundAmt"]).toFixed(2));
//			$("#qtyxhhr").text("￥" + ($("#fundAmt3").attr("num") - $("#projectFinanceAmt2").attr("num")).toFixed(2)).attr("all", ($("#fundAmt3").attr("num") - $("#projectFinanceAmt2").attr("num")).toFixed(2));//计算器其他有限合伙人
//			$("#xmfptczblVal").text((Number($("#projectFinanceAmt2").attr("num"))/Number($("#fundAmt3").attr("num"))*100).toFixed(2) + "%").attr("num", (Number($("#projectFinanceAmt2").attr("num"))/Number($("#fundAmt3").attr("num"))*100).toFixed(2)); //项目方(普通合伙人)出资比例
//			$("#projectBonusRatio").text((data["projectBonusRatio"]*100).toFixed(2) + "%").attr("num", (data["projectBonusRatio"]*100).toFixed(2)); //项目方(普通合伙人)份额占股比例
//			$("#qtyxhhr_czbl").text((100 - Number($("#xmfptczblVal").attr("num"))).toFixed(2) + "%").attr("num", 100 - Number($("#xmfptczblVal").attr("num"))); //投资人（其他有限合伙人）出资比例
//			$("#qtyxhhr_zgbl").text((data["investBonusRatio"]*100).toFixed(2) + "%").attr("num", (data["investBonusRatio"]*100).toFixed(2)).attr("all", (data["investBonusRatio"]*100).toFixed(2)); //投资人（其他有限合伙人）份额占股比例
		},
		error: function(request){
			console.log("获取股权详细信息异常");
		}
	});
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
		if($(this).attr("name") == "proBudget" ||  $(this).attr("name") == "rgList" || $(this).attr("name") == "proComment" ||  $(this).attr("name") == "proProgress" ||  $(this).attr("name") == "proAgree"){
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
					    	return false;
						}else{
							AlertDialog.confirm(gtRz, null, "请先进行跟投人认证", "去认证", "再看看", null);
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
function gtRz(){
	window.location.href = path + "/common/centerRZ.html";
}
//查看详情页标签下内容
function showInfoContent(url, id, page){
	$("#detailBody>div").hide();
	//点击标签按钮后，页面滑动到合适查看的位置
	if($("#xs_video").css("display") == "none"){
		$('html,body').animate({
			scrollTop : '300px'
		}, 800);
	}else{
		$('html,body').animate({
			scrollTop : '710px'
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

//购买
function investLast(){
	if(siteUserId == "null"){
		go2Login();
		return false;
	}
	$("#investValiInput").val("");
	$("#bgpop").show();
	var it = (cv["winH"]-505)/2, il = (cv["winW"]-600)/2;
	$("#investLastDiv").css({"top": it+"px", "left": il+"px"}).show();
	//关闭
	$("#bgpop").click(function(){
		$("#bgpop").hide();
		$("#investLastDiv").hide();
		$("#tip_div").hide();
	});
	$("#supFomr").append($('<input type="hidden" name="leadInvestor"/>'));
	$("#miniInvestAmt1").text(Number($("#miniInvestAmt").attr("num")));
	$("#fundAmt4").text(Number($("#miniInvestAmt").attr("num"))*Number($("#fenshuNum").val()));
	$("#remainsFenshu").text(Number($("#financeNum").attr("num"))-Number($("#buyNum1").attr('num'))-Number($("#fenshuNum").val()));//剩余份数
//	$("#fundAmt4").text($("#miniInvestAmt1").text());
	//更换验证码
	$("#changeValiBtn").click(function(){
		$("#imageInvest").attr("src", path + "/security/securityCodeImage.html?" + new Date().getTime());
	});
	$("#imageInvest").attr("src", path + "/security/securityCodeImage.html?" + new Date().getTime());
	$("#couInvestZgbl").text(((Number($("#fenshuNum").val())/Number($("#financeNum").attr('num'))*Number($("#qtyxhhr_zgbl").attr("num")))*100).toFixed(2));
	//加一份
	$("#iJIa").unbind("click").click(function(){
		var fsNum = Number($("#remainsFenshu").text()), jNum = 0;
		var shengyu = Number($("#financeNum").attr("num"))-Number($("#buyNum1").attr('num'))-$("#fenshuNum").val();
		if(shengyu <= 0){ //超出剩余份数
			$("#iJIa").css("color", "#ccc");
			return false;
		}else{
			jNum = Number($("#fenshuNum").val()) + 1;
			$("#fenshuNum").val(jNum);
			$("#remainsFenshu").text(Number($("#financeNum").attr("num"))-Number($("#buyNum1").attr('num'))-jNum);//剩余份数
			$("#fundAmt4").text(Number($("#miniInvestAmt").attr("num"))*Number($("#fenshuNum").val()));
			//认购份数 / 挂牌份数 *挂牌比例  = 分红比率
			$("#couInvestZgbl").text(((Number($("#fenshuNum").val())/Number($("#financeNum").attr('num'))*Number($("#qtyxhhr_zgbl").attr("num")))*100).toFixed(2));
		}
	});
	//减一份
	$("#iJian").unbind("click").click(function(){
		var fsNum = Number($("#remainsFenshu").text()), jNum = 0;
		if($("#fenshuNum").val() < 1){ //输入框内只剩0份
			$("#iJian").css("color", "#CCC");
			return false;
		}else{
			jNum = Number($("#fenshuNum").val()) - 1;
			$("#fenshuNum").val(jNum);
			$("#remainsFenshu").text(Number($("#financeNum").attr("num"))-Number($("#buyNum1").attr('num'))-jNum);//剩余份数
			$("#fundAmt4").text(Number($("#miniInvestAmt").attr("num"))*Number($("#fenshuNum").val()));
			//认购份数 / 挂牌份数 *挂牌比例  = 分红比率
			$("#couInvestZgbl").text(((Number($("#fenshuNum").val())/Number($("#financeNum").attr('num'))*Number($("#qtyxhhr_zgbl").attr("num")))*100).toFixed(2));
		}
	});
	
	$("#fenshuNum").unbind("keyup").bind('keyup',function(){
		var last = Number($("#financeNum").attr("num"))-Number($("#buyNum1").attr('num'))-Number($("#fenshuNum").val())
		if(!Number($("#fenshuNum").val())|| $("#fenshuNum").val()<0){
			$("#fenshuNum").val('');
		}else if(last<0){
			$("#fenshuNum").val(Number($("#financeNum").attr("num"))-Number($("#buyNum1").attr('num')));
		}
		var fenshuNum = $("#fenshuNum").val() == '' ? 0: Number($("#fenshuNum").val())
		$("#remainsFenshu").text(Number($("#financeNum").attr("num"))-Number($("#buyNum1").attr('num'))-$("#fenshuNum").val());//剩余份数
		$("#fundAmt4").text(Number($("#miniInvestAmt").attr("num"))*Number($("#fenshuNum").val()));
		//认购份数 / (挂牌份数 *挂牌比例)  = 分红比率
		$("#couInvestZgbl").text(Number($("#fenshuNum").val())/((Number($("#financeNum").attr('num'))*Number($("#qtyxhhr_zgbl").attr("num")))).toFixed(2))
	})
	
	//购买前验证
	$("#investLBtn").unbind("click").click(function(){
		if($("#fenshuNum").val() == "0"){ //添加认购份数
			AlertDialog.show("请添加认购份数", "fenshuNum", 10, 20);
			return false;
		}
		if(!$("#investValiInput").val()){
			AlertDialog.show("请输入验证码", "investValiInput", 10, 20);
			return false;
		}
		$.ajax({
			url: path + "/fundpool/transfer/checkBeforeBuyTransfer.html",
			type: "post",
			dataType: "json",
			async: false,
			data: {
				transferNo:transferNo,
				buyParts:$("#fenshuNum").val(),
				valiCode:$("#investValiInput").val()
			},
			success: function(data){
				if(!data["success"]){
					AlertDialog.tip(data["msg"]);
					$("#bgpop").hide();
					$("#investLastDiv").hide();
					$("#tip_div").hide();
					return false;
				}else{
					submitBuyTransfer();
				}
			},
			error: function(request){
				console.log("验证领投请求异常");
			}
		});
	});
}



var API = {
	'getTransFerDetail':path+'/crowdfundingInvestTransfer/getCrowdfundTransferDetail.html',
	'submitBuyTransfer':path+'/fundpool/transfer/submitBuyTransfer.html',
	'purchaseHistory':path+'/crowdfundingInvestTransfer/getCrowdfundTransferUserPayed.html',
	'getCrowdfundUserDetail':path+'/crowdfundUserCenter/getCrowdfundUserDetail.html'
}


function getTransFerDetail(callback){
	$.ajax({
		url:API['getTransFerDetail'],
		type:'post',
		dataType: "json",
		data: {
			transferNo:transferNo,
			buyParts:$("#fenshuNum").val()
		},
		success:function(data){
			if(!data["success"]){
				AlertDialog.tip(data["msg"]);
				return false;
			}
			callback(data['msg']);
//			BuyCompetence(); 
		},
		error:function(){
			
		}
	})
}

function createTransfer(data){
	var progress =(data["sumBuyMoney"]/data["transferMoney"]).toFixed(3);
	var Surplus_day = '--'
		if(data['deadline']){
			var date_part = data['deadline'].split(" ")[0];
			var date_time = data['deadline'].split(" ")[1]
			var deadDate = new Date(date_part.split("-")[0],date_part.split("-")[1]-1,date_part.split("-")[2],date_time.split(":")[0],date_time.split(":")[1],date_time.split(":")[2]);
			var Surplus = deadDate.getTime() - new Date();
			Surplus_day = Math.ceil(Surplus/86400000 <=0?0:Surplus/86400000);
		}
	//已购买金额
	$("#approveAmt").text(data['sumBuyMoney']);
	//完成进度显示的进度条
	$("#pBar").css("width",progress*100+"%");
	//完成进度显示百分比
	$("#supportRatio1").text(progress*100);
	//剩余天数
	$("#remainDay").text(Surplus_day);
	//总金额
	$("#fundAmt1").attr("num",data['transferMoney']).text(data['transferMoney']);
	//每份的金额
	$("#miniInvestAmt").attr('num',data['partMoney']).text(data['partMoney']);
	//总分数
	$("#financeNum").attr("num",data['transferParts']).text(data['transferParts']);
	//已购买的份数
	$("#buyNum1").attr('num',data['sumBuyParts']).text(data['sumBuyParts']);
	//挂牌比率
	$("#qtyxhhr_zgbl").attr("num",((Number(data["transferRatio"]))).toFixed(4)).text(((Number(data["transferRatio"]))*100).toFixed(2)+"%");
	//显示的状态名
	$("#loanStateName").text(data['statusName']);
	
}

function submitBuyTransfer(){
	$("#subumitTransfer").attr("action",API['submitBuyTransfer']);
	$("#subumitTransfer").attr("method","post");
	$("#subumitTransfer").attr("target",'_blank');
	$("#hideTransferNo").val(transferNo);
	$("#hideBuyParts").val($("#fenshuNum").val());
	$("#subumitTransfer").submit();
//	window.location.reload();
}

//获取购买列表
function purchaseHistory(){
	$.ajax({
		url:API['purchaseHistory'],
		type:'post',
		dataType: "json",
		data:{
			transferNo:transferNo
		},
		success:function(data){
			if(!data["success"]){
				return false;
			}
			createPurchaseHistory(data['msg'])
		},
		error:function(){
			console.log("加载购买列表失败")
		}
	})
}

function createPurchaseHistory(data){
	var arr = [],strArr;
	for(var i = 0; i<data.length; i++){
		arr.push('<li>');
		arr.push('<h3>'+data[i]['userId']+'</h3>')
		arr.push('<h4>'+data[i]['transferTime']+'</h4>')
		arr.push('<p>')
		arr.push('购买金额:<i>'+data[i]['buyMoney']+'</i>')
		arr.push('购买份数:<i>'+data[i]['buyParts']+'</i>')
		arr.push("</p>")
		arr.push('</li>');
	}
	strArr = arr.join("");
	$("#preSupportList").html(strArr);
}


//用户权限 
function getCrowdfundUserDetail(){

} 

function BuyCompetence(){
	var disabledButn = function(msg){
		$("#investLastBtn").css({
			"backgroundColor":"#D8D3D3"
		})
		if(msg){
			$("#investLastBtn").text(msg)
		}
		$("#investLastBtn").unbind("click");
		$("#investLastBtn").show();
	}
	var open = function(){
		$("#investLastBtn").click(investLast);
		$("#investLastBtn").show();
	}
	if(part == 'transfer'){
		var all = $("#financeNum").attr('num');
		var buy = $("#buyNum1").attr('num'); 
		if(isNaN(Number(all)) || isNaN(Number(all)) || Number(all) == Number(buy)){
			disabledButn();
			return false;
		}
	}
	
	$.ajax({
		url:API['getCrowdfundUserDetail'],
		type:'post',
		dataType: "json",
		data:{
		},
		success:function(data){
			if(!data['success']){
				return false
			}
			var all = $("#financeNum").attr('num');
			var buy = $("#buyNum1").attr('num'); 
			if(isNaN(Number(all)) || isNaN(Number(all)) || Number(all) == Number(buy)){
				disabledButn();
				return false;
			}
			if(data["msg"]["investAuthState"] == "passed" || data["msg"]["leadAuthState"] == "passed"){
				open();	
			}else{
				disabledButn("请先进行认证");
			}
		},
		error:function(){
			
		}
	})
	
}

