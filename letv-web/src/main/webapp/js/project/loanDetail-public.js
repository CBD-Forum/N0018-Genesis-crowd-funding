var loanNo, loanState , loanCover; //预定义项目编号和评论过后的标识
//loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7, window.location.search.indexOf("&state"));
//loanState = window.location.search.substring(window.location.search.indexOf("state=")+6, window.location.search.length);
loanNo = getQueryString("loanNo");
loanState = getQueryString("state");
loanType = getQueryString("type");
$(function(){
	getCrowdDetail(); 
	changeTab();
	
	
	$(".blockChain>img").click(function(){
		var _this = $(this).parent();
		if(_this.attr("ioc") == "0"){
			_this.attr("ioc","1");
			_this.find(".chartsIoc").addClass("chartsIoc2");
			_this.find("strong").show();
			$(".chartsNone").click(function(){
				_this.attr("ioc","0");
				_this.find(".chartsIoc").removeClass("chartsIoc2");
				_this.find("strong").hide();
			});
		}else{
			_this.attr("ioc","0");
			_this.find(".chartsIoc").removeClass("chartsIoc2");
			_this.find("strong").hide();
		}
	});
	getBackSetList();
	if(!loanType){
		ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传项目封面图片
		$("#subComment").click(submitComment);//发表评论
		$("#attentionNum").click(attentionLoan); //关注
		$("#praiseNum").click(crowdfund); //赞
		showProEvolve();
		$("#privateSale").click(toPrivateSale); //私信
		//详情tab标签置顶操作
//		$(window).scroll(function(){
//			if($(document).scrollTop() > 877){
//				$("#loanDetailTab").css({"position":"fixed", "top":"0", "height":"42px", "z-index":"2", "margin-top":"0"});
//			}else{
//				$("#loanDetailTab").css({"position":"static", "top":"0", "height":"64px", "z-index":"2", "margin-top":"0"});
//			}
//		});
	}else{
		$("#loanDetail-share").html('<img src="'+path+'/images/letv/share.png">');
		if(siteUserId == "null"){
			$(".header1>div.box").html('<div class="fl"><p>欢迎您来到乐视金融众筹网！</p></div><div class="fr"><i><a><img class="rightImgs" src="'+path+'/images/defaultPhoto.png"></a><a style="padding:0 7px;" class="red" href="'+path+'/common/login.html" >登录</a><a href="'+path+'/common/register.html">注册</a></i><span class="line">|</span><a target="_blank">乐视金融</a><span class="line">|</span><a>帮助中心</a><span class="line">|</span><em>热线电话：400-999-5157</em></div>');
		}else{
			$(".header1>div.box").html('<div class="fl"><p>欢迎您来到乐视金融众筹网！</p></div><div class="fr"><i><a><img class="rightImgs" src="'+path+'/images/defaultPhoto.png"></a><a style="padding:0 7px;" class="red">'+nickName+'</a><a>退出</a></i><span class="line">|</span><a target="_blank">乐视金融</a><span class="line">|</span><a>帮助中心</a><span class="line">|</span><em>热线电话：400-999-5157</em></div>');
		}
		$(".header2>div.box").html('<img src="'+path+'/images/letv/browseTopB.png">');
		$(".footer>div.box").html('<img src="'+path+'/images/letv/browseFooter.png">');
	}
	
});
function copySuccess(){
    //flash回调
    AlertDialog.tip("复制成功！");
}
//发私信
function toPrivateSale(){
	if(siteUserId == "null"){
		go2Login();
		return false;
	}
	$("#bgpop").show();
	var sl = (cv["winW"]-419)/2, st = (cv["winH"]-204)/2;
	$("#priSaleDiv").css({"top":st+"px","left":sl+"px"}).show();
	$("#bgpop").click(function(){
		$(this).hide();
		$("#priSaleDiv").hide();
		if($("#tip_div").attr("id")){ //如果存在提示，关闭弹框同事关闭提示
			AlertDialog.hide();
		}
	});
	$("#saleBtn").click(function(){
		if(Valify.isNull($("#saleArea").val(), "saleArea", -67, 40)){
			$.ajax({
				url: path + "/letter/savePrivateLetter.html",
				type: "post",
				dataType: "json",
				data: {
					"receiveUser": $("#saleArea").attr("loanUser"),
					"sendContent": $("#saleArea").val(),
					"loanNo": loanNo
					},
				success: function(data){
					if(!data["success"]){
						$(this).hide();
						$("#priSaleDiv").hide();
						if($("#tip_div").attr("id")){ //如果存在提示，关闭弹框同事关闭提示
							AlertDialog.hide();
						}
						AlertDialog.tip(data["msg"]);
						return false;
					}
					$(this).hide();
					$("#priSaleDiv").hide();
					if($("#tip_div").attr("id")){ //如果存在提示，关闭弹框同事关闭提示
						AlertDialog.hide();
					}
					AlertDialog.tip("发送成功");
				},
				error: function(request){
					console.log("发送私信异常");
				}
			});
		}
	});
}

//赞
function crowdfund(){
	if(siteUserId == "null"){
		//AlertDialog.tip("您还没有登录，请登录！");
		AlertDialog.confirm(go2Login,null,"您还没有登录，请先登录","登录","取消",null);
		return false;
	}
	$.ajax({
		url: path + "/crowdfunding/praiseCrowdfund.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				AlertDialog.tip(data["msg"]);
				return false;
			}else{
				$("#praiseNum").text(Number($("#praiseNum").text())+1);
			}
		},
		error: function(request){
			console.log("项目异常");
		}
	});
}

//收藏事件
function attentionLoan(){
	if(siteUserId == "null"){
		//AlertDialog.tip("您还没有登录，请登录！");
		AlertDialog.confirm(go2Login,null,"您还没有登录，请先登录","登录","取消",null);
		return false;
	}
	$.ajax({
		url: path + "/crowdfunding/attentionLoan.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				AlertDialog.confirm(DelAttentionLoan, null, "您确定要取消收藏吗？", "确定", "取消", loanNo);
				return false;
			}else{
				$("#attentionNum").text(Number($("#attentionNum").text())+1);
				AlertDialog.tip("收藏成功");
			}
		},
		error: function(request){
			console.log("收藏项目异常");
		}
	});
}
function DelAttentionLoan(){
	$.ajax({
		url: path + "/crowdfunding/cancelAttention.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(data["success"]){
				$("#attentionNum").text(Number($("#attentionNum").text())-1);
				AlertDialog.tip("取消成功");
			}
		},
		error: function(request){
			console.log("收藏项目异常");
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
//获取产品|公益项目详情
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
			loanCover = cv["fileAddress"] + data["loanLogo"];
			window._bd_share_config={
			"common":{
				"bdText":data["loanName"],
				"bdMini":"2",
				"bdMiniList":false,
				"bdPic":loanCover,
				"bdStyle":"0",
				"bdSize":"24"
				},
			"share":{"bdText": data["loanName"]}};
			with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
			
			if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){ 
				$(".blockChain-copy a").css("margin-left","15px");
				$("#copyInput").val(data["blockChainAddress"]);
				$("#copyBtn").click(function(){
					AlertDialog.tip("请选中文本，使用Ctrl+C复制");
				});
			}else{
				$("#copyInput").val(data["blockChainAddress"]);
				var copyCon = data["blockChainAddress"];
				var flashvars = {
					content: encodeURIComponent(copyCon),
					uri: path+'/images/letv/flashCopy_btn.png'
				};
				var params = {
					wmode: "transparent",
					allowScriptAccess: "always"
				};
				swfobject.embedSWF(path+"/js/common/copy/clipboard.swf", "copyBtn", "70", "30", "9.0.0", null, flashvars, params);
			}
			
			
			$("#loanCover").attr("src",cv["fileAddress"] + "/" + data["loanLogo"]).attr("alt",data["loanName"]);
			$("#loanName").text(data["loanName"]);
			$("#loanName1").text(data["loanName"]);
			$("#loanDesc").text(data["loanDes"]);
			$("#loanStateName").text(data["loanStateName"]);
			if(data["superIndustryName"]){
				$("#superIndustryName").text(data["superIndustryName"]);
			}else{
				$("#superIndustryName").text(data["loanTypeName"]);
			}
			$("#loanIntroduction").text(data["loanIntroduction"]);
			$("#attentionNum").text(data["attentionNum"]);
			$("#praiseNum").text(data["praiseNum"]);
			if(data["dailyProfitRate"]){
				$("#dailyProfit").text('预计每日收益：'+data["dailyProfitRate"]*100+'%');
			}else{
				$("#dailyProfit").text('预计每日收益：0%');
			}
			
			if(data["fundEndTime"]){
				$("#fundEndTime").text(data["fundEndTime"].substring(0,10));
			}else{
				$("#fundEndTime").text("--");
			}
			//下部右侧赋值
			if(data["photo"]){
				$("#userPhone").attr("src",cv["fileAddress"] + "/" + data["photo"]);//发起人头像
			}else{
				$("#userPhone").attr("src",path + "/images/defaultPhoto.png");//发起人头像
			}
			if(data["selfDetail"]){
				$("#selfDetail").html(data["selfDetail"]);//个人简介
			}
			$("#loanUser").text(data["loanUser2"]);
			$("#saleArea").attr("loanUser", data["loanUser"]); //用于私信的LoanUser
			$("#PDloanUser").attr("loanUser", data["loanUser"]); //判断是否投资自己的项目
			//loanUser存cookie，用于支付页面发送私信
			document.cookie = "supportLoanUser="+data["loanUser"]+";path=/;";
			if(data["loanProvinceName"]){
				$("#loanAddress").html('&nbsp;&nbsp;' + data["loanProvinceName"] );
			}
			if(data["loanCityName"]){
				$("#loanAddressCity").html('&nbsp;&nbsp;' + data["loanCityName"]);
			}
			$("#approveAmt").text(data["approveAmt"].toFixed(2));
			if(data["supportRatio"]*100 > 100){
				$("#pBar").css("width", "100%");
				$("#supportRatio1").css("left", "90%");
			}else{
				$("#pBar").css("width", (data["supportRatio"]*100).toFixed(2) + "%");
				if(data["supportRatio"]*100-5<0){
					$("#supportRatio1").css("left",0 + "%");
				}else{
					$("#supportRatio1").css("left", (data["supportRatio"]*100-10).toFixed(2) + "%");
				}
				
			}
			$("#supportRatio1").text((data["supportRatio"]*100).toFixed(2) + "%");
//			if(data["lockDay"]){
//				$("#lockDay").text('转让锁定期：'+data["lockDay"]+'天');
//			}else{
//				$("#lockDay").text("转让锁定期：0天");
//			}
			
			if(data["loanState"] == "funded" || data["loanState"] == "lended" || data["loanState"] == "end"){//筹款结束
				$("#remainDay").text(0);
			}else{
				remainDays = data["remainDays"] ? (data["remainDays"] < 0 ? 0 : data["remainDays"]) : 0;
				$("#remainDay").text(remainDays);
			}
			$("#fundDays").text(data["fundDays"]);
			$("#fundAmt").text(data["fundAmt"].toFixed(2));
			$("#supportNum").text(data["supportNum"]);
			$("#supportNum1").text("支持人数："+data["supportNum"]+"人");
			$("#commentNum").text(data["commentNum"]);
			if(data["loanVideo"]){ //如果有视频，先加载视频
				$("#proInfoContent").html('<embed width="740" height="450" src="'+data["loanVideo"]+'" allowFullScreen="true" value="transparent" quality="high" align="middle" wmode="Opaque"  mode="transparent" type="application/x-shockwave-flash"></embed>');
				$("#proInfoContent").append('<div style="height:20px;"></div>');
			}
			$("#proInfoContent").append(data["loanDetail"]);//项目介绍
		},
		error: function(request){
			console.log("获取项目详情异常！");
		}
	});
}
//切换tab标签
function changeTab(){
	$("#detailTab").find("a").click(function(){
		$(this).parent().parent().find("a").removeClass("cur");
		$(this).addClass("cur");
		showInfoContent($(this).attr("url"), $(this).attr("name"), 1);
	});
}
//获取回报设置列表
function getBackSetList(){
	var tArr = [], tStr = '', tArr2 = [] , tStr2 = '', l; //顶部两条列表展示
	var aArr = [], aStr = ''; //弹出框列表展示
	var bArr = [], bStr = ''; //底部列表展示
	var wordNum = ["一", "二", "三"];
	var smallN = 0;
	$.ajax({
		url: path + "/crowdfunding/getBackSetList.html",
		type: "post",
		data: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			//展示顶部两条数据
			var n = l;
			
			
			
			//展示右侧底部回报列表
			for(var i=0;i<l;i++){
				if(data[i]["backLable"] != "M"){
					var state = data[i]["state"];
					bArr.push('<div class="rg_cent">');
					if(data[i]["numberLimits"] || data[i]["numberLimits"]>0){
						bArr.push('<h3 class="clearfix">'+data[i]["amt"].toFixed(2)+companyCode+'<span class="fr">已有<em>'+data[i]["supportNum"]+'</em>人支持 / 限额<em>'+data[i]["numberLimits"]+'</em>人</span></h3>');
					}else{
						bArr.push('<h3 class="clearfix">'+data[i]["amt"].toFixed(2)+companyCode+'<span class="fr">已有<em>'+data[i]["supportNum"]+'</em>人支持 / 无限</span></h3>');
					}
					bArr.push('<div class="pdl20">');
					bArr.push('<p class="mtb10 ft_bd ft14">你将获得： </p>');
					bArr.push('<div class="lg26">'+data[i]["backContent"]+'</div>');
					if(data[i]["photoUrl1"]){
						bArr.push('<img class="mtb20"   sid="small" src="'+cv.fileAddress+data[i]["photoUrl1"]+'" width="90px" height="90px" ></p>');
					}
					bArr.push('<p class="col6 lg26">配送费用： ');
					if(data[i]["transFee"] == 0){
						bArr.push('<span class="col3">免邮</span>');
					}else if(data[i]["transFee"]){
						bArr.push('<span class="col3">'+data[i]["transFee"].toFixed(2)+companyCode+'</span>');
					}else{
						bArr.push('<span class="col3">免邮</span>');
					}
					bArr.push('</p>');
					
					if(data[i]["backTime"] == 0){ //回报发送时间
						bArr.push('<p class="col6 lg26">回报时间： 项目成功结束后立即回报</p>');
					}else{
						bArr.push('<p class="col6 lg26">回报时间： 项目成功结束后<span class="col3">'+data[i]["backTime"]+'</span>天内</p>');
					}
					if(siteUserId == "null"){
						if(state == "notfull"){
							bArr.push('<a href="javascript:void(0);" onclick="loginUrlA();" class="agree_btn" >支持'+data[i]["amt"].toFixed(2)+companyCode+'</a>');
						}else if(state == "full"){
							bArr.push('<a href="javascript:void(0);" class="agree_btn" style="background:#ccc;">支持'+data[i]["amt"].toFixed(2)+companyCode+'</a>');
						}
					}else{
						if(loanState == "funding"){ //筹款中
							if(state == "notfull"){
								var supportBackNo = data[i]["backNo"];
								var supportAmt = data[i]["amt"].toFixed(2);
								var loanUser = data[i]["loanUser"];
								$.ajax({
									url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
									type: "post",
									dataType: "json",
									async: false,
									success: function(data){
										
										if(loanUser == siteUserId){
											bArr.push('<a onclick="checkLoan()" class="agree_btn" >支持'+supportAmt+companyCode+'</a>');
										}else{
											if(data["isAuth"]){
												bArr.push('<a href="'+path+'/common/support-pubBenefit.html?loanNo='+loanNo+'&backNo='+supportBackNo+'"  target="_blank" class="agree_btn" >支持'+supportAmt+companyCode+'</a>');
											}else{
												bArr.push('<a href="'+path+'/common/realNameRZ.html" class="agree_btn" >支持'+supportAmt+companyCode+'</a>');
											}
										}
									},
									error: function(request){
										console.log("获取个人信息异常");
									}
								});
							}else if(state == "full"){
								bArr.push('<a href="javascript:void(0);" style="background:#ccc;" class="agree_btn" >支持'+data[i]["amt"].toFixed(2)+companyCode+'</a>');
							}
						}else{
							bArr.push('<a href="javascript:void(0);" style="background:#ccc;" class="agree_btn" >支持'+data[i]["amt"].toFixed(2)+companyCode+'</a>');
						}
					}
					
					bArr.push('</div>');
					bArr.push('</div>');
				}
				
				
			}
			bStr = bArr.join("");
			$("#bottomBackSetList").html(bStr);
			
			
			//点击bottom里面的小图展示回报设置的大图
			$("#bottomBackSetList img[sid^='small'").unbind("click").click(function(){
				$("#bgpop2").show();
				$("#loanBig").css({"left":(cv["winW"]-820)/2+"px","top":(cv["winH"]-520)/2+"px"}).show();
				$("#loanBig>div>img").attr("src",$(this).attr("src")).show();
				$("#big_close").css({"left":($("#loanBig>div>img").offset().left+$("#loanBig>div>img").width()-20)+"px","top":($("#loanBig>div>img").offset().top-$("body").scrollTop()-20)+"px"}).show();
			});
			//大图片关闭按钮事件
			$("#big_close").unbind("click").click(function(){
				$("#loanBig").hide();
				$("#bgpop2").hide();
				$("#big_close").hide();
			});
			
			
		},
		error: function(request){
			console.log("获取回报设置列表异常");
		}
	});
}
function checkLoan(){
	AlertDialog.tip("您不能投资自己的项目");
	return false;
}
//查看详情页标签下内容
function showInfoContent(url, id, page){
	var idata;
	idata = {
			"loanNo": loanNo,
			"page": page,
			"rows": 10
			};
	$("#detailBody>div").hide();
	if(id == "proEvolve"){
		$("#proEvolveContent").show();
		var height = $("#loanProgress").height()-($("#loanProgress>div").eq($("#loanProgress>div").length-1).height()-15);
		$("#jd_content_bor").css("height",height+"px");
	}else if(id == "proInfo"){
		$("#proInfoContent").show();
	}
	//点击标签按钮后，页面滑动到合适查看的位置
	$('html,body').animate({
		scrollTop : '645px'
	}, 800);
	
	$.ajax({
		url: path + url,
		type: "post",
		dataType: "json",
		data: idata,
		success: function(data){
			if(!data["success"]){
				return false;
			}
			if(id == "proRecord"){ //众筹记录
				showProRecord(data);
			}else if(id == "proComment"){ //评论
				showProComment(data);
			}
		},
		error: function(request){
			console.log("获取详情内容异常");
		}
	});
	//获取支持者
	function showProRecord(data){
		var num = data["msg"]["total"];
		var sumPage = (data["msg"]["total"]%10 == 0) ? data["msg"]["total"]/10 : Math.floor(data["msg"]["total"]/10) + 1;
		var pArr = [], pStr = '', l = data["msg"]["rows"].length, data = data["msg"]["rows"];
		
		pArr.push('<li class="clearfix tit_agr">');
		pArr.push('<span class="wd1">序号</span>');
		pArr.push('<span class="wd2">支持人</span>');
		pArr.push('<span class="wd3">众筹'+companyCode+'</span>');
		pArr.push('<span class="wd4">众筹时间</span>');
		pArr.push('<span class="wd5">状态</span>');
		pArr.push('</li>');
		
		for(var i = 0;i<l;i++){
			
			if(i%2 == 0){
				pArr.push('<li class="clearfix ">');
			}else{
				pArr.push('<li class="clearfix bg-f5">');
			}
			
			pArr.push('<span class="wd1">'+(i+1)+'</span>');
			pArr.push('<span class="wd2">'+data[i]["supportNickName"]+'</span>');
			pArr.push('<span class="wd3">'+data[i]["supportAmt"].toFixed(2)+'</span>');
			pArr.push('<span class="wd4">'+data[i]["supportTime"]+'</span>');
			pArr.push('<span class="wd5">已支付</span>');
			pArr.push('</li>');
			
		}
		if(l == 0){
			pArr.push('<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>抱歉，还没有人投资过该项目，暂无数据哦~</div>');
			$("#suportPage").hide();
		}
		pStr = pArr.join("");
		$("#suportTable").html(pStr);
		//刷新后改变众筹记录数量
		$("#supportNum1").text("支持人数："+num+"人");
		//分页设置
		pagePause = 0;
		if(page < 2){
			$("#suportPage").jPages({
				containerID : "suportTable",
				first:false,
				last:false,
				previous:" ",
				next:" ",
				clickStop   : true,
				perPage	: 10,
				allSumPage : sumPage,
				callback: ajaxPageSupportData
				
				
			});
		}
		$("#proRecordContent").fadeIn();
	}
	//获取评论
	function showProComment(data){
		var num = data["msg"]["total"];
		var sumPage = (data["msg"]["total"]%10 == 0) ? data["msg"]["total"]/10 : Math.floor(data["msg"]["total"]/10) + 1;
		var pArr = [], pStr = '', l = data["msg"]["rows"].length, data = data["msg"]["rows"];
		$(".reply").keyup(function(){
			var len = $(this).val().length;
			var num = 140 - len;
			if(len > 139){
			    $(this).val($(this).val().substring(0,140));
			    num = 0
			}
			$(this).parent().find("p>span>i").text(num);
		});
		$("#proCommentContent").fadeIn();
		if(l ==0){
			$("#commonPage").hide();
			return false;
		}
		for(var i = 0;i<l;i++){
			pArr.push('<div class="pl_list clearfix ptb20">');
			pArr.push('<div class="clearfix fqr_xq" style="margin-top:0">');
			pArr.push('<div class="fl mr25">');
			if(data[i]["photo"]){
				pArr.push('<img src="'+ cv["fileAddress"] + '/' + data[i]["photo"] +'" width="81px" height="81px" style="border-radius: 50%;">');
			}else{
				pArr.push('<img src="'+ path+'/images/defaultPhoto.png" width="81px" height="81px" style="border-radius: 50%;">');
			}
			pArr.push('</div>');
			pArr.push('<div class="fl rg_txt" style="width:605px;">');
			pArr.push('<p class="col6">');
			if(data[i]["userNickName"]){
				pArr.push('<span class="colaa">'+data[i]["userNickName"]+'：</span>' + data[i]["content"] +'');
			}else{
				pArr.push('<span class="colaa">'+data[i]["discussUser"]+'：</span>' + data[i]["content"] +'');
			}
//			pArr.push('<span class="colaa">'+data[i]["discussUser"]+'：</span>'+data[i]["content"]+'');
			pArr.push('</p>');
			pArr.push('<p class="mt15 col8">'+data[i]["discussTime"].substring(0,10)+'<a class="replay" aid="'+data[i]["id"]+'" onClick="replyShow(this)">回复</a></p>');
			pArr.push('</div>');
			pArr.push('</div>');
			pArr.push('<div class="replay_content fr" id="'+data[i]["id"]+'">');
			pArr.push('<textarea placeholder="评论不能少于5个字哦！" class="reply"> </textarea>');
			pArr.push('<p class="clearfix mt15 colaa">');
			pArr.push('<span class="fl">还可以输入<i>140</i>字</span>');
			pArr.push('<a class="fr replay_btn" onClick="reply(this)" pid="'+data[i]["id"]+'">回复</a>');
			pArr.push('</p>');
			pArr.push('</div>');
			var r = data[i]["subList"].length, Rdata = data[i]["subList"];
			for(var j = 0;j<r;j++){
				pArr.push('<div class="fr mt25" style="width: 630px;">');
				pArr.push('<div class="clearfix fqr_xq" style="margin-top:0">');
				pArr.push('<div class="fl mr25">');
				if(Rdata[j]["photo"]){
					pArr.push('<img src="'+ cv["fileAddress"] + '/' + Rdata[j]["photo"] +'" width="81px" height="81px" style="border-radius: 50%;">');
				}else{
					pArr.push('<img src="'+ path+'/images/defaultPhoto.png" width="81px" height="81px" style="border-radius: 50%;">');
				}
				pArr.push('</div>');
				pArr.push('<div class="fl rg_txt" style="width:515px;">');
				pArr.push('<p class="col6">');
				if(Rdata[j]["userNickName"]){
					pArr.push('<span class="colaa">'+Rdata[j]["userNickName"]+'：</span>' + Rdata[j]["content"] +'');
				}else{
					pArr.push('<span class="colaa">'+Rdata[j]["discussUser"]+'：</span>' + Rdata[j]["content"] +'');
				}
//				pArr.push('<span class="colaa">' + Rdata[j]["discussUser"] +'：</span>' + Rdata[j]["content"] +'');
				pArr.push('</p>');
				pArr.push('<p class="mt15 col8">' + Rdata[j]["discussTime"].substring(0,10) +'</p>');
				/*pArr.push('<p class="mt15 col8">2016-09-09<a class="replay">回复</a></p>');*/
				pArr.push('</div>');
				pArr.push('</div>');
				pArr.push('</div>');
			}
			pArr.push('</div>');
		}
		pStr = pArr.join("");
		$("#commentUl>div").html(pStr);
		$("#commonPage").show();
		$(".replay_content textarea.reply").keyup(function(){
			if($(this).val().length>140){
				$(this).val($(this).val().substring(0,140));
			}
		})
		//刷新后改变评论数量
		$("#commentNum").text(num);
		//分页设置
		pagePause = 0;
		if(page < 2){
			$("#commonPage").jPages({
				containerID : "commentUl",
				first:false,
				last:false,
				previous:" ",
				next:" ",
				clickStop   : true,
				perPage	: 10,
				allSumPage : sumPage,
				callback: ajaxPagecommentData
			});
		}
		$("#proCommentContent").fadeIn().attr("h", "1");
	}
}
function ajaxPageSupportData(obj){
	if(pagePause == 0){
		return false;
	}
	showInfoContent($("#detailTab>li>a.cur").attr("url"), $("#detailTab>li>a.cur").attr("name"), obj["current"]);
}
function ajaxPagecommentData(obj){
	if(pagePause == 0){
		return false;
	}
	showInfoContent($("#detailTab>li>a.cur").attr("url"), $("#detailTab>li>a.cur").attr("name"), obj["current"]);
}

function loginUrl(){
	window.location.href = path + "/common/login.html";
}

//发表评论
function submitComment(){
	if(siteUserId == "null"){
		//AlertDialog.tip("请登录后发表评论！");
		AlertDialog.confirm(loginUrl, null, "请登录后发表评论！", "去登录", "再看看", null);
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
						showProCommentAjax(data,1);
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
function showProCommentAjax(data,page){
	
	var num = data["msg"]["total"];
	var sumPage = (data["msg"]["total"]%10 == 0) ? data["msg"]["total"]/10 : Math.floor(data["msg"]["total"]/10) + 1;
	var pArr = [], pStr = '', l = data["msg"]["rows"].length, data = data["msg"]["rows"];
	$(".reply").keyup(function(){
		var len = $(this).val().length;
		var num = 140 - len;
		if(len > 139){
		    $(this).val($(this).val().substring(0,140));
		    num = 0
		}
		$(this).parent().find("p>span>i").text(num);
	});
	
	if(l ==0){
		$("#commonPage").hide();
		return false;
	}
	for(var i = 0;i<l;i++){
		pArr.push('<div class="pl_list clearfix ptb20">');
		pArr.push('<div class="clearfix fqr_xq" style="margin-top:0">');
		pArr.push('<div class="fl mr25">');
		if(data[i]["photo"]){
			pArr.push('<img src="'+ cv["fileAddress"] + '/' + data[i]["photo"] +'" width="81px" height="81px" style="border-radius: 50%;">');
		}else{
			pArr.push('<img src="'+ path+'/images/defaultPhoto.png" width="81px" height="81px" style="border-radius: 50%;">');
		}
		pArr.push('</div>');
		pArr.push('<div class="fl rg_txt" style="width:605px;">');
		pArr.push('<p class="col6">');
		if(data[i]["userNickName"]){
			pArr.push('<span class="colaa">'+data[i]["userNickName"]+'：</span>' + data[i]["content"] +'');
		}else{
			pArr.push('<span class="colaa">'+data[i]["discussUser"]+'：</span>' + data[i]["content"] +'');
		}
//		pArr.push('<span class="colaa">'+data[i]["discussUser"]+'：</span>'+data[i]["content"]+'');
		pArr.push('</p>');
		pArr.push('<p class="mt15 col8">'+data[i]["discussTime"].substring(0,10)+'<a class="replay" aid="'+data[i]["id"]+'" onClick="replyShow(this)">回复</a></p>');
		pArr.push('</div>');
		pArr.push('</div>');
		pArr.push('<div class="replay_content fr" id="'+data[i]["id"]+'">');
		pArr.push('<textarea placeholder="评论不能少于5个字哦！" class="reply"> </textarea>');
		pArr.push('<p class="clearfix mt15 colaa">');
		pArr.push('<span class="fl">还可以输入<i>140</i>字</span>');
		pArr.push('<a class="fr replay_btn" onClick="reply(this)" pid="'+data[i]["id"]+'">回复</a>');
		pArr.push('</p>');
		pArr.push('</div>');
		var r = data[i]["subList"].length, Rdata = data[i]["subList"];
		for(var j = 0;j<r;j++){
			pArr.push('<div class="fr mt25" style="width: 630px;">');
			pArr.push('<div class="clearfix fqr_xq" style="margin-top:0">');
			pArr.push('<div class="fl mr25">');
			if(Rdata[j]["photo"]){
				pArr.push('<img src="'+ cv["fileAddress"] + '/' + Rdata[j]["photo"] +'" width="81px" height="81px" style="border-radius: 50%;">');
			}else{
				pArr.push('<img src="'+ path+'/images/defaultPhoto.png" width="81px" height="81px" style="border-radius: 50%;">');
			}
			pArr.push('</div>');
			pArr.push('<div class="fl rg_txt" style="width:515px;">');
			pArr.push('<p class="col6">');
			if(Rdata[j]["userNickName"]){
				pArr.push('<span class="colaa">'+Rdata[j]["userNickName"]+'：</span>' + Rdata[j]["content"] +'');
			}else{
				pArr.push('<span class="colaa">'+Rdata[j]["discussUser"]+'：</span>' + Rdata[j]["content"] +'');
			}
//			pArr.push('<span class="colaa">' + Rdata[j]["discussUser"] +'：</span>' + Rdata[j]["content"] +'');
			pArr.push('</p>');
			pArr.push('<p class="mt15 col8">' + Rdata[j]["discussTime"].substring(0,10) +'</p>');
			/*pArr.push('<p class="mt15 col8">2016-09-09<a class="replay">回复</a></p>');*/
			pArr.push('</div>');
			pArr.push('</div>');
			pArr.push('</div>');
		}
		pArr.push('</div>');
		
	}
	pStr = pArr.join("");
	$("#commentUl>div").html(pStr);
	$("#commentNum").text(num);
	$("#commonPage").show();
	$(".replay_content textarea.reply").keyup(function(){
		if($(this).val().length>140){
			$(this).val($(this).val().substring(0,140));
		}
	})
	//分页设置
	pagePause = 0;
	if(page < 2){
		$("#commonPage").jPages({
			containerID : "commentUl",
			first:false,
			last:false,
			previous:" ",
			next:" ",
			clickStop   : true,
			perPage	: 10,
			allSumPage : sumPage,
			callback: ajaxPagecommentData
		});
	}
	$("#proCommentContent").fadeIn().attr("h", "1");
}

//回复
function replyShow(id){
	var _id = $(id).attr("aid");
	$("#"+_id).show();
}
function reply(id){
	if(siteUserId == "null"){
		//AlertDialog.tip("请登录后发表评论！");
		AlertDialog.confirm(loginUrl, null, "请登录后发表评论！", "去登录", "再看看", null);
		return false;
	}
	if($(id).parent().parent().find("textarea").val().length < 5){
		AlertDialog.tip("评论不能少于5个字哦！");
		return false;
	}
	var pid = $(id).attr("pid");
	$.ajax({
		url: path + "/crowdfunding/submitComment.html",
		type: "post",
		dataType: "json",
		data: {
			"pid": pid,
			"content": $(id).parent().parent().find("textarea").val()
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
						showProCommentAjax(data,1);
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

//展示更多的回报设置列表
function showBackSetDiv(){
	$("#bgpop").show();
	var at = (cv["winH"]-400)/2, al = (cv["winW"]-660)/2;
	$("#dialogTipDiv").css({"top": at+"px", "left":al+"px"}).show();
	$("#bgpop").click(function(){
		$("#dialogTipDiv").hide();
		$(this).hide();
	});
}
//获取项目进展
function showProEvolve(){
	var pArr = [], pStr = ''; //项目进展初始化变量
	var dArr = [], dStr = ''; //项目动态初始化变量
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
				pStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>抱歉，暂无数据哦~</div>';
				$("#loanProgress").html(pStr);
				
				return false;
			}
			for(var i=0;i<l;i++){
				//显示项目进展
				pArr.push('<div class="jd_list clearfix">');
				if(data[i]["timeNode"]){
					pArr.push('<div class="fl col9 mt4 ft16" style="width:89px;">'+data[i]["timeNode"].substring(0,10)+'</div>');
				}else{
					pArr.push('<div class="fl col9 mt4 ft16" style="width:89px;">'+data[i]["enterTime"].substring(0,10)+'</div>');
				}
				
				pArr.push('<div class="fl jd-icon"></div>');
				pArr.push('<div class="fr wd607 col6 lg26"><p>'+data[i]["enterContent"]+'</p>');
				var imgFileList = data[i]["imgFileList"]
				for(var j=0;j<imgFileList.length;j++){
					pArr.push('<img src="'+cv.fileAddress+imgFileList[j]["url"]+'" class="jd_img">');
				}
				pArr.push('</div>');
				pArr.push('</div>');
			}
			//展示项目进展
			pStr = pArr.join("");
			$("#loanProgress").append(pStr);
			//点击bottom里面的小图展示回报设置的大图
			$("#loanProgress img.jd_img").unbind("click").click(function(){
				$("#bgpop").show();
				$("#loanBig").css({"left":(cv["winW"]-820)/2+"px","top":(cv["winH"]-520)/2+"px"}).show();
				$("#loanBig>div>img").attr("src",$(this).attr("src")).show();
				$("#big_close").css({"left":($("#loanBig>div>img").offset().left+$("#loanBig>div>img").width()-20)+"px","top":($("#loanBig>div>img").offset().top-$("body").scrollTop()-20)+"px"}).show();
			});
			//大图片关闭按钮事件
			$("#big_close").unbind("click").click(function(){
				$("#loanBig").hide();
				$("#bgpop").hide();
				$("#big_close").hide();
			});
		},
		error: function(request){
			console.log("获取项目进展异常");
		}
	});
}
//关闭更多回报设置，并且去登录
function moreBack2Login(){
	$("#dialogTipDiv").hide();
	go2Login();
}

function loginUrlA(){
	AlertDialog.confirm(go2Login, null, "您还没有登录，请登录！", "去登录", "再看看", null);
}