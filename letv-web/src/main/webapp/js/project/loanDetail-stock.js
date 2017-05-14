var loanNo = getQueryString("loanNo");
var browse = getQueryString("browse");
var recordNum = 1,loanCover;//记录 预约，认购记录的页数
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
	if(!browse){
		//ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传评论图片
		showProRecord(1);//投资记录
		//getLeader();//领投人
		$("#attentionNum").click(attentionLoan);//关注事件
		$("#praiseNum").click(crowdfund); //赞
		$("#talkAboutBtn").click(talkAboutLoan); //约谈
		//$("#countBtn").click(countStoke); //计算器
//		$("#investLastBtn").unbind("click").click(getUserInfo); //认购，跟投
		$("#subComment").click(submitComment);//发表评论
		getCommetNum();//获取评论数量
		selectPageList();//投后管理
	}else{
		$("#loanDetail-share").html('<img src="'+path+'/images/letv/share.png">');
		if(siteUserId == "null"){
			$(".header1>div.box").html('<div class="fl"><p>欢迎您来到乐视金融众筹网！</p></div><div class="fr"><i><a><img class="rightImgs" src="'+path+'/images/defaultPhoto.png"></a><a style="padding:0 7px;" class="red" href="'+path+'/common/login.html" >登录</a><a href="'+path+'/common/register.html">注册</a></i><span class="line">|</span><a target="_blank">乐视金融</a><span class="line">|</span><a>帮助中心</a><span class="line">|</span><em>热线电话：400-999-5157</em></div>');
		}else{
			$(".header1>div.box").html('<div class="fl"><p>欢迎您来到乐视金融众筹网！</p></div><div class="fr"><i><a><img class="rightImgs" src="'+path+'/images/defaultPhoto.png"></a><a style="padding:0 7px;" class="red">'+nickName+'</a><a>退出</a></i><span class="line">|</span><a target="_blank">乐视金融</a><span class="line">|</span><a>帮助中心</a><span class="line">|</span><em>热线电话：400-999-5157</em></div>');
		}
		$(".header2>div.box").html('<img src="'+path+'/images/letv/browseTopB.png">');
		$(".footer>div.box").html('<img src="'+path+'/images/letv/browseFooter.png">');
		$("#rgListContent").html('<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>抱歉，暂无数据哦~</div>');
	}
	
});
function copySuccess(){
    //flash回调
    AlertDialog.tip("复制成功！");
}
//获取支持者
function showProRecord(page){
	var ltArr = [], ltStr = '', l; //加载领投人列表
	$.ajax({
		url: path + "/crowdfunding/getSupportList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo": loanNo,
			"rows": 5,
			"page": page
		},
		success: function(data){
			if(!data["success"]){
				ltArr.push('<div class="ltr_lst">');
				ltArr.push('<h4>无投资记录</h4>');
				ltArr.push('</div>');
				ltStr = ltArr.join("");
				$("#ltrListFr>div").html(ltStr);
				$("#Investor_btn").hide();
				return false;
			}
			l = data["msg"]["rows"].length,total = data["msg"]["total"], data = data["msg"]["rows"];
			if(l ==0){
				ltArr.push('<div class="ltr_lst">');
				ltArr.push('<h4>无投资记录</h4>');
				ltArr.push('</div>');
				ltStr = ltArr.join("");
				$("#ltrListFr>div").html(ltStr);
				$("#Investor_btn").hide();
				return false;
			}
			
			for(var i=0;i<l;i++){
				ltArr.push('<div class="ltr_lst clearfix">');
				ltArr.push('<div class="fl mr25">');
				if(data[i]["photo"]){
					ltArr.push('<img src="'+cv["fileAddress"]+'/'+data[i]["photo"]+'"  width="66" height="66" style="border-radius: 50%;" />');
				}else{
					ltArr.push('<img src="'+path+'/images/defaultPhoto-66.png"  width="66" height="66" style="border-radius: 50%;" />');
				}
				
				ltArr.push('</div>');
				ltArr.push('<div class="fl">');
				if(data[i]["supportNickName"]){
					ltArr.push('<p class="ft18 mb8">'+data[i]["supportNickName"]+'</p>');
				}else{
					ltArr.push('<p class="ft18 mb8">--</p>');
				}
				ltArr.push('<p class="col6 mb8 ft_bd ft14">认购金额：<em class="col_blue">'+data[i]["supportAmt"].toFixed(2)+companyCode+'</em></p>');
				ltArr.push('<p class="col8 mb8">投资时间：'+data[i]["supportTime"]+'</p>');
				ltArr.push('</div>');
				ltArr.push('</div>');
			}
			ltStr = ltArr.join("");
			$("#Investor_btn").show();
			if(page>1){
				$("#ltrListFr>div").append(ltStr);
			}else{
				$("#ltrListFr>div").html(ltStr);
			}
			if($("#ltrListFr>div>div").length>=total){
				$("#Investor_btn").hide();
			}
			$("#Investor_btn").click(function(){
				page++;
				showProRecord(page);
			});
		},
		error: function(request){
			console.log("获取领投人列表异常！");
		}
	});
}


//领投人
function getLeader(){
	var ltArr = [], ltStr = '', l; //加载领投人列表
	$.ajax({
		url: path + "/crowdfundingInvest/getLeader.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			if(!data["success"]){
				ltArr.push('<div class="ltr_lst">');
				ltArr.push('<h4>无领投人记录</h4>');
				ltArr.push('<a class="gt_btn sz_btn" id="investFirstBtn">我来领投</a>');
				ltArr.push('</div>');
				return false;
			}
			l = data["msg"].length, data = data["msg"];
			if(l == 0){
				ltArr.push('<div class="ltr_lst">');
				ltArr.push('<h4>无领投人记录</h4>');
				ltArr.push('<a class="gt_btn sz_btn" id="investFirstBtn">我来领投</a>');
				ltArr.push('</div>');
			}else{
				for(var i=0;i<l;i++){
					ltArr.push('<div class="ltr_lst clearfix">');
					ltArr.push('<div class="fl mr25">');
					if(data[i]["photo"]){
						ltArr.push('<img src="'+cv["fileAddress"]+'/'+data[i]["photo"]+'"  width="66" height="66" />');
					}else{
						ltArr.push('<img src="'+path+'/images/defaultPhoto-66.png"  width="66" height="66" />');
					}
					
					ltArr.push('</div>');
					ltArr.push('<div class="fl">');
					if(data[i]["nickName"]){
						ltArr.push('<p class="ft18 mb8">'+data[i]["nickName"]+'</p>');
					}else{
						ltArr.push('<p class="ft18 mb8">--</p>');
					}
					if(data[i]["selfDetail"]){
						ltArr.push('<p class="col8 mb8" style="width:220px;overflow:hidden;white-space: nowrap;text-overflow: ellipsis;">'+data[i]["selfDetail"]+'</p>');
					}else{
						ltArr.push('<p class="col8 mb8">--</p>');
					}
					
					ltArr.push('<p class="col6 mb8 ft_bd ft14">认购金额：<em class="col_blue">'+data[i]["totalSupportAmt"].toFixed(2)+companyCode+'</em></p>');
					//ltArr.push('<p class="col8 mb8">投资时间：'+data[i]["supportTime"].substring(0,10)+'</p>');
					ltArr.push('<p class="col8 mb8">投资时间：'+data[i]["supportTime"]+'</p>');
					ltArr.push('</div>');
					ltArr.push('</div>');
				}
			};
			ltStr = ltArr.join("");
			$("#ltrListFr").html(ltStr);
		},
		error: function(request){
			console.log("获取领投人列表异常！");
		}
	});
}
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

//工作经历
function experience(id){
	//展示约谈弹框
	$("#bgpop").show();
	var sl = (cv["winW"]-419)/2, st = (cv["winH"]-304)/2;
	$("#founderDiv").css({"top":st+"px","left":sl+"px"}).show();
	//关闭事件
	$("#bgpop,.prompt_close").click(function(){
		$("#founderDiv,#bgpop").hide();
	});
	$("#name_position").text($(id).attr("name")+" "+$(id).attr("position"));
	$.ajax({
		url: path + "/crowdfundingFounder/getFounderWorksList.html",
		type: "post",
		dataType: "json",
		data: {"founderId": $(id).attr("aid")},
		success: function(data){
			
			var pArr = [], pStr = '', l = data["rows"].length, data = data["rows"];
			for(var i = 0;i<l;i++){
				pArr.push('<div class="founderList clearfix" style="width:495px;"><span class="s1" title="'+data[i]["company"]+'">'+data[i]["company"]+'</span><span class="s2" title="'+data[i]["position"]+'">'+data[i]["position"]+'</span><span class="s3">'+data[i]["startTime"].substring(0,10)+'</span><span class="s4">'+data[i]["endTime"].substring(0,10)+'</span></div>');
			}
			pStr = pArr.join("");
			$("#founderCenter").html(pStr);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
	$.ajax({
		url: path + "/crowdfundingFounder/getFounderBusinessList.html",
		type: "post",
		dataType: "json",
		data: {"founderId": $(id).attr("aid")},
		success: function(data){
			
			var pArr = [], pStr = '', l = data["rows"].length, data = data["rows"];
			for(var i = 0;i<l;i++){
				pArr.push('<p>'+data[i]["description"]+'</p>');
			}
			pStr = pArr.join("");
			$("#BusinessCenter").html(pStr);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
	$.ajax({
		url: path + "/crowdfundingFounder/getFounderEducationsList.html",
		type: "post",
		dataType: "json",
		data: {"founderId": $(id).attr("aid")},
		success: function(data){
			
			var pArr = [], pStr = '', l = data["rows"].length, data = data["rows"];
			for(var i = 0;i<l;i++){
				pArr.push('<p>'+data[i]["createTime"].substring(0,10)+'~'+data[i]["endTime"].substring(0,10)+'，'+data[i]["school"]+'</p>');
			}
			pStr = pArr.join("");
			$("#EducationsCenter").html(pStr);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
//	$.ajax({
//		url: path + "/crowdfundingFounder/getFounderWorksList.html",
//		type: "post",
//		dataType: "json",
//		data: {"founderId": $(id).attr("aid")},
//		success: function(data){
//			
//			var pArr = [], pStr = '', l = data["rows"].length, data = data["rows"];
//			for(var i = 0;i<l;i++){
//				pArr.push('<div class="founderList clearfix" style="width:474px;"><span class="s1" title="'+data[i]["company"]+'">'+data[i]["company"]+'</span><span class="s2" title="'+data[i]["position"]+'">'+data[i]["position"]+'</span><span class="s3">'+data[i]["startTime"].substring(0,10)+'</span><span class="s4">'+data[i]["endTime"].substring(0,10)+'</span></div>');
//			}
//			pStr = pArr.join("");
//			$("#founderCenter").html(pStr);
//		},
//		error: function(request){
//			console.log("获取个人信息异常");
//		}
//	});
}

function selectPageList(){
	//投后管理
	$.ajax({
		url: path + "/crowdfundingInvestAfter/selectPageList.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			
			var pArr = [], pStr = '', l = data["rows"].length, data = data["rows"];
			
			if(l==0){
				pStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>抱歉，暂无数据哦~</div>';
				$("#rgListContent").html(pStr);
				return false;
			}
			
			for(var i = 0;i<l;i++){
				
				pArr.push('<div class="th_list" style="border-bottom:0">');
				pArr.push('<div class="clearfix">');
				pArr.push('<span class="num1 fl">'+(i+1)+'</span>');
				pArr.push('<div class="fl text_side">');
				pArr.push('<p class="ft16 col6">'+data[i]["content"]+'</p>');
				pArr.push('<p class="col9 mtb10">图片资料：</p>');
				pArr.push('<div class="photo_info">');
				var picUrl=data[i]["picUrl"].split(',');
				for(var j = 0;j<picUrl.length;j++){
					if(picUrl[j]){
						pArr.push('<img src="'+cv.fileAddress+picUrl[j]+'" widt="140" height="110"/>');
					}
				}
				pArr.push('</div>');
				pArr.push('<p class="col9 mtb10">文件资料：</p>');
				pArr.push('<p class="ft16 bot_down">');
				var FileList=data[i]["fileList"];
				for(var j = 0;j<picUrl.length;j++){
					if(FileList[j]){
						pArr.push('<span><a target="_blank" href='+cv.fileAddress+FileList[j]["fileUrl"]+'>下载</a>'+FileList[j]["fileName"]+'</span>');
					}
				}
				pArr.push('</p>');
				pArr.push('</div>');
				pArr.push('</div>');
				pArr.push('</div>');
				
				
			}
			pStr = pArr.join("");
			$("#rgListContent").html(pStr);
			
			//点击bottom里面的小图展示回报设置的大图
			$("#rgListContent img").unbind("click").click(function(){
				$("#bgpop").show();
				$("#loanBig").css({"left":(cv["winW"]-820)/2+"px","top":(cv["winH"]-520)/2+"px"}).show();
				$("#loanBig>div>img").attr("src",$(this).attr("src")).show();
				$("#big_close").css({"left":($("#loanBig>div>img").offset().left+$("#loanBig>div>img").width()-20)+"px","top":($("#loanBig>div>img").offset().top-$(document).scrollTop()-20)+"px"}).show();
			});
			//大图片关闭按钮事件
			$("#big_close").unbind("click").click(function(){
				$("#loanBig").hide();
				$("#bgpop").hide();
				$("#big_close").hide();
			});
			
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}

//获取项目详情信息
function getCrowdDetail(){
	if(getCookie("logined") && siteUserId != "null"){//判断是否登录 未登录先登录
		//创始人列表
		$.ajax({
			url: path + "/crowdfundingFounder/selectFounderPageList.html",
			type: "post",
			dataType: "json",
			data: {"loanNo": loanNo},
			success: function(data){
				var l = data["rows"].length,data = data["rows"];
				for(var i=0;i<l;i++){
					$("#selectFounderPageList").append('<div class="clearfix bgb_list mt10"><span>'+data[i]["name"]+'</span><span>'+data[i]["position"]+'</span><a class="fr" aid="'+data[i]["id"]+'" name="'+data[i]["name"]+'" position="'+data[i]["position"]+'" onClick="experience(this)">详细经历</a></div>')
				}
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
	}
	
	//预计收益
	$.ajax({
		url: path + "/crowdfundingOperateData/selectPageList.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(data){
			
			var l = data["rows"].length,expectedReturn = data["rows"];
			for(var i=0;i<l;i++){
				$("#expectedReturn").append('<span style="padding-right:10px;">'+expectedReturn[i]["expectedReturn"]+';</span>')
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
	

	if(siteUserId == "null"){
		go2Login();
		return false;
	}
	if($("#yLoanuser").val() == siteUserId){
		AlertDialog.tip("您不能领投自己的项目");
		return false;
	}
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
			
			
			if(data["loanState"] == "preheat"){ //预热中
				//$("#blockChain-p").hide();
				$("#fundEndTime").parent().hide();
				$("#hasSprotTtitle").text("目前已预购金额");
				$("#approveAmt").text(data["approveAmt"].toFixed(2));
				$("#loanStateName").text("预热中");
				$("#xs_jd").hide();
				$("#buyNum1Span").hide();
//				$("#buttonDiv").html('<a class="xs_bg2" href="javascript:void(0);" id="investFirstBtn">我要领投</a><a class="xs_bg2" href="javascript:void(0);" id="wantPreSupportBtn">我要预约</a>');
//				$("#investLastBtn").text("我要领投").attr("href",path+"/common/stock-pay.html?loanNo="+loanNo+"&loanState="+data["loanState"]);
				$("#investLastBtn").text("我要领投");
				if(!browse){
					$("#investLastBtn").click(function(){
						getUserInfoRZ(data["loanState"]);
					});
					$("#investFirstBtn").click(function(){
						getUserInfoRZ(data["loanState"]);
					});
				}
				
//				$("#investFirstBtn").click(investFirst); //领投
				$("#wantPreSupportBtn").click(wantPreSupport);//预约
				$("#state2RecordTit").text("预约记录");
				getPreSupportList(recordNum);//预约记录
			}else if(data["loanState"] == "funding"){ //筹款中

				$("#approveAmt").text(data["approveAmt"].toFixed(2));
				$("#loanStateName").text("筹款中");
//				$("#buttonDiv").html('<a class="xs_bg2" href="javascript:void(0);" id="investLastBtn" >我要投资</a>');
//				$("#investLastBtn").text("我要投资").attr("href",path+"/common/stock-pay.html?loanNo="+loanNo+"&loanState="+data["loanState"]);
				$("#investLastBtn").text("我要投资");
				if(!browse){
					$("#investLastBtn").click(function(){
						getUserInfoRZ(data["loanState"]);
					});
				}
				
//				$("#investLastBtn").click(investLast); //认购，跟投
				
				$("#state2RecordTit").text("领投记录");
				getFundSupportList(recordNum);//认购记录
			}else if(data["loanState"] == "funded" || data["loanState"] == "lended" || data["loanState"] == "end"){ //筹款完成

				$("#approveAmt").text(data["approveAmt"].toFixed(2));
				$("#loanStateName").text(data["loanStateName"]);
				$("#talkAboutBtn").css({"background":"#CCC","color":"#FFF","border":"1px solid #ccc"}).unbind("click");
				$("#buttonDiv").html('<a class="xs_bg2" href="javascript:void(0);" id="investLastBtn">我要投资</a>');
				$("#investLastBtn").css("background", "#CCC").unbind("click");
				
				$("#state2RecordTit").text("领投记录");
				getFundSupportList(recordNum);//认购记录
			}else if(data["loanState"] == "add"){ //筹款完成
				$("#hasSprotTtitle").text("目前已预购金额");
				$("#approveAmt").text(data["approveAmt"].toFixed(2));
				$("#loanStateName").text("草稿");
				$("#xs_jd").hide();
				$("#buyNum1Span").hide();
				$("#investLastBtn").text("浏览");
				$("#commetNum").text("0");
			}else{
				$("#loanStateName").text(data["loanStateName"]);
				$("#talkAboutBtn").css({"background":"#CCC","color":"#FFF","border":"1px solid #ccc"}).unbind("click");
				$("#buttonDiv").html('<a class="xs_bg2" href="javascript:void(0);" id="investLastBtn">我要投资</a>');
				$("#investLastBtn").css("background", "#CCC").unbind("click");
				$("#attentionBtn").unbind("click");
			}
			
			$("#licenseIdentity").append('<img src="'+cv.fileAddress+data["businessLicense"]+'" width="155" height="100" />');
			if(data["legalCard"]){
				var indArr = data["legalCard"].split(",");
				for(var j=0;j<indArr.length;j++){
					$("#licenseIdentity").append('<img src="'+cv.fileAddress+indArr[j]+'" width="155" height="100" />');
				}
			}
			
			
			//点击bottom里面的小图展示回报设置的大图
			$("#licenseIdentity img").unbind("click").click(function(){
				$("#bgpop").show();
				$("#loanBig").css({"left":(cv["winW"]-820)/2+"px","top":(cv["winH"]-520)/2+"px"}).show();
				$("#loanBig>div>img").attr("src",$(this).attr("src")).show();
				$("#big_close").css({"left":($("#loanBig>div>img").offset().left+$("#loanBig>div>img").width()-20)+"px","top":($("#loanBig>div>img").offset().top-$(document).scrollTop()-20)+"px"}).show();
			});
			//大图片关闭按钮事件
			$("#big_close").unbind("click").click(function(){
				$("#loanBig").hide();
				$("#bgpop").hide();
				$("#big_close").hide();
			});
			
			$("#loanName,#prompt_box_name").text(data["loanName"]); //项目名称
			$("#yLoanuser").val(data["loanUser"]);
			$("#loanUserName").text(data["loanUser2"]);
			$("#loanUserName1").text(data["loanUserName"]);
			if(data["fundEndTime"]){
				$("#fundEndTime").text(data["fundEndTime"].substring(0,10));
			}else{
				$("#fundEndTime").text(data["preheatEndTime"]);
			}
			$("#competence").html(data["competence"]);
			$("#profitModel").html(data["profitModel"]);
			$("#loanTeam").html(data["loanTeam"]);
			$("#capitalUsed").html(data["capitalUsed"]);
			$("#industryAnalysis").html(data["industryAnalysis"]);
			$("#riskMeasure").html(data["riskMeasure"]);
			$("#financePlan").html(data["financePlan"]);
			//项目附件
			var businessLength = data["businessProposalFiles"].length,businessProposalFiles = data["businessProposalFiles"];
			for(var a=0;a<businessLength;a++){
				$("#enclosure").append('<div class="enclosure">'+businessProposalFiles[a]["fileName"]+'<a target="_blank" href="'+cv["fileAddress"]+businessProposalFiles[a]["fileUrl"]+'">下载</a></div>')
			}
			var exitSchemeLength = data["exitSchemeFiles"].length,exitSchemeFiles = data["exitSchemeFiles"];
			for(var a=0;a<exitSchemeLength;a++){
				$("#enclosure").append('<div class="enclosure">'+exitSchemeFiles[a]["fileName"]+'<a target="_blank" href="'+cv["fileAddress"]+exitSchemeFiles[a]["fileUrl"]+'">下载</a></div>')
			}
			var otherAccessoriesLength = data["otherAccessoriesFiles"].length,otherAccessoriesFiles = data["otherAccessoriesFiles"];
			for(var a=0;a<otherAccessoriesLength;a++){
				$("#enclosure").append('<div class="enclosure">'+otherAccessoriesFiles[a]["fileName"]+'<a target="_blank" href="'+cv["fileAddress"]+otherAccessoriesFiles[a]["fileUrl"]+'">下载</a></div>')
			}
			if(data["selfDetail"]){
				$("#selfDetail").text(data["selfDetail"]); //个人简介
			}

			$("#loanDes").text(data["loanDes"]); //项目简介
			if(data["photo"]){
				$("#userPhoto").attr("src", cv["fileAddress"] + '/' + data["photo"]); //头像
			}else{
				$("#userPhoto").attr("src", path + '/images/defaultPhoto-79.png'); //头像
			}
			$("#loanPhoto").attr("src", cv["fileAddress"] + '/' + data["loanLogo"]).attr("title", data["loanName"]).attr("alt", data["loanName"]); //项目封面
			
			if(data["loanVideo"]){ //如果上传了视频
				$("#xs_video").html('<embed width="658px;" height="355px;" src="'+data["loanVideo"]+'" allowFullScreen="true" value="transparent" quality="high" align="middle" wmode="Opaque"  mode="transparent" type="application/x-shockwave-flash"></embed>'); //视频
			}else{
				$("#xs_video").hide();
			}
			if(data["loanState"] == "funded" || data["loanState"] == "lended" || data["loanState"] == "end"){//筹款结束
				$("#remainDay").text(0);
			}else{
				if(data["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
					$("#remainDay").parent().hide();
				}else{
					remainDays = data["remainDays"] ? (data["remainDays"] < 0 ? 0 : data["remainDays"]) : 0;
					$("#remainDay").text(remainDays);
				}
			}
			$("#fundAmt").text(data["fundAmt"].toFixed(2)).attr("num", data["fundAmt"]);
			var fundAmt = (data["fundAmt"])/10000;
			$("#fundAmt1").text(fundAmt+"万").attr("num", data["fundAmt"]);
			//$("#fundAmt2").text(formatCurrency(data["fundAmt"].toFixed(2)));
			if(data["supportRatio"]){
				if(data["supportRatio"]*100 > 100){
					$("#pBar").css("width", "100%");
					$("#supportRatio1").css("left", "90%");
				}else{
					$("#pBar").css("width", (data["supportRatio"]*100).toFixed(2) + "%");
					if(data["supportRatio"]*100-10	<0){
						$("#supportRatio1").css("left",0 + "%");
					}else{
						$("#supportRatio1").css("left", (data["supportRatio"]*100-10).toFixed(2) + "%");
					}
					
				}
				$("#supportRatio1").text((data["supportRatio"]*100).toFixed(2) + "%");
			}else{
				$("#pBar").css("width", "0");
				$("#supportRatio1").css("left", "0");
				$("#supportRatio1").text("0%");
			}
			
			
			//$("#pBar").css("width", (data["supportRatio"]*100).toFixed(0) + "%");
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
			//$("#projectFinanceAmt2").text("￥" + (data["projectFinanceAmt"]).toFixed(2)).attr("num", (data["projectFinanceAmt"]).toFixed(2));
			$("#financeNum").text(data["financeNum"]);
			//if(data["release_time"]){
				$("#release_time").text(data["createTime"].substring(0, 10)); //发起时间
			//}
			$("#miniInvestAmt").text("起投金额："+data["stockPartAmt"].toFixed(2)+companyCode).attr("num", data["stockPartAmt"]);
			if(data["miniInvestAmt"]){
				//$("#miniInvestAmt").text("起投金额："+data["stockPartAmt"]+companyCode).attr("num", data["stockPartAmt"]);
				$("#miniInvestAmt1").text(data["miniInvestAmt"].toFixed(2)).attr("num", data["miniInvestAmt"]);
				$("#miniInvestAmt2").text(data["miniInvestAmt"].toFixed(2)).attr("num", data["miniInvestAmt"]);
				$("#fundAmt5").text(data["miniInvestAmt"].toFixed(2)).attr("num", data["miniInvestAmt"]);
			}else{
				//$("#miniInvestAmt").text("起投金额：0"+companyCode).attr("num", 0);
				$("#miniInvestAmt1").text(0).attr("num", 0);
				$("#miniInvestAmt2").text(0).attr("num", 0);
				$("#fundAmt5").text(0).attr("num", 0);
			}
			$("#superIndustryName").text(data["superIndustryName"]); //行业
			
			$("#loanNum").text(data["loanNum"]); //开店数
			$("#buyNum").text(data["buyNum"]); //认购数
			$("#buyNum1").text(data["buyNum"]); //认购数
			$("#remainNum").text(Number($("#financeNum").text()) - Number($("#buyNum1").text())); //剩余份数
			$("#interviewNum").text(data["interviewNum"]); // 约谈人数
			$("#attentionNum").text(data["attentionNum"]);//关注数
			$("#praiseNum").text(data["praiseNum"]);//赞
			if(data["loanProvinceName"] ){
				$("#address").text(data["loanProvinceName"]);
			}
			if(data["loanCityName"]){
				$("#addressCity").text(data["loanCityName"]);
			}
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
			//$("#fundAmt3").text("￥" + (data["fundAmt"]).toFixed(2)).attr("num", (data["fundAmt"]).toFixed(2));
			$("#qtyxhhr").text("￥" + ($("#fundAmt3").attr("num") - $("#projectFinanceAmt2").attr("num")).toFixed(2)).attr("all", ($("#fundAmt3").attr("num") - $("#projectFinanceAmt2").attr("num")).toFixed(2));//计算器其他有限合伙人
			$("#xmfptczblVal").text((Number($("#projectFinanceAmt2").attr("num"))/Number($("#fundAmt3").attr("num"))*100).toFixed(2) + "%").attr("num", (Number($("#projectFinanceAmt2").attr("num"))/Number($("#fundAmt3").attr("num"))*100).toFixed(2)); //项目方(普通合伙人)出资比例
			$("#projectBonusRatio").text((data["projectBonusRatio"]*100).toFixed(2) + "%").attr("num", (data["projectBonusRatio"]*100).toFixed(2)); //项目方(普通合伙人)份额占股比例
			$("#qtyxhhr_czbl").text((100 - Number($("#xmfptczblVal").attr("num"))).toFixed(2) + "%").attr("num", 100 - Number($("#xmfptczblVal").attr("num"))); //投资人（其他有限合伙人）出资比例
			if(data["dailyProfitRate"]){
				$("#qtyxhhr_zgbl").text("预计每日收益率："+(data["dailyProfitRate"]*100).toFixed(2) + "%").attr("num", (data["dailyProfitRate"]*100).toFixed(2)).attr("all", (data["dailyProfitRate"]*100).toFixed(2)); //投资人（其他有限合伙人）份额占股比例
			}else{
				$("#qtyxhhr_zgbl").text("预计每日收益率：0%").attr("num", "0").attr("all", "0");
			}
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
//			if(!data["success"]){
//				return false;
//			}
//			$.ajax({
//				url: path + "/crowdfundUserCenter/getUserInfoByAuth.html",
//				type: "post",
//				dataType: "json",
//				success: function(data){
//					if(!data["success"]){
//						return false;
//					}
//					alert(data["msg"])
//				},
//				error: function(request){
//					console.log("获取个人信息异常");
//				}
//			});

			if(data["isAuth"]){
			$.ajax({
				url: path + "/crowdfunding/getCrowdDetail.html",
				type: "post",
				dataType: "json",
				data: {
					"loanNo":loanNo
				},
				success: function(data){
					if(!data["success"]){
						return false;
					}
					if($("#yLoanuser").val() == siteUserId){
						AlertDialog.tip("您不能领投自己的项目");
						return false;
					}
					if(data["userInvestAuthState"]){
						if(data["userType"] == "1"){
							
						}else if(data["userType"] == "0"){
							
						}
					}else{
						$("#stockBgpop").show();
						AlertDialog.confirm(gtRz, null, "请先进行领投人认证", "去认证", "再看看", null);
					}
				},
				error: function(request){
					console.log("获取个人信息异常");
				}
			});
				
			}else{
				$("#stockBgpop").show();
				AlertDialog.confirm(gtsmRz, null, "请先进行实名认证", "去认证", "再看看", null);
			}
		    
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}

//获取个人信息
function getUserInfoRZ(loanState){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(data["isAuth"]){
				if($("#yLoanuser").val() == siteUserId){
					AlertDialog.tip("您不能领投自己的项目");
					return false;
				}
				if(data["memberState"]){
					if(data["userType"] == "1"){
						if(loanState == "preheat"){
							if(data["orgLeadAuthState"]){
								if(data["orgLeadAuthStateValue"] == "auditing"){
									$("#stockBgpop").show();
									AlertDialog.tip("领投机构认证认证中");
								}else if(data["orgLeadAuthStateValue"] == "refused"){
									$("#stockBgpop").show();
									AlertDialog.tip("领投机构认证失败");
								}else{
									window.location.href = path + "/common/stock-pay.html?loanNo="+loanNo+"&loanState="+loanState;
								}
								
							}else{
								$("#stockBgpop").show();
								//AlertDialog.tip("请先进行领投机构认证");
								AlertDialog.confirm(gtRz, null, "请先进行领投机构认证", "去认证", "再看看", null);
							}
						}else{
							if(data["orgInvestAuthState"]){
								if(data["orgInvestAuthStateValue"] == "auditing"){
									$("#stockBgpop").show();
									AlertDialog.tip("跟投机构认证认证中");
								}else if(data["orgInvestAuthStateValue"] == "refused"){
									$("#stockBgpop").show();
									AlertDialog.tip("跟投机构认证失败");
								}else{
									window.location.href = path + "/common/stock-pay.html?loanNo="+loanNo+"&loanState="+loanState;
								}
							}else{
								$("#stockBgpop").show();
								//AlertDialog.tip("请先进行跟投机构认证");
								AlertDialog.confirm(gtRz, null, "请先进行跟投机构认证", "去认证", "再看看", null);
//								AlertDialog.confirm(gtRz, null, "请先进行领投人认证", "去认证", "再看看", null);
							}
						}
						
					}else if(data["userType"] == "0"){
						if(loanState == "preheat"){
							if(data["leadInvestorAuthState"]){
								if(data["leadInvestorAuthStateValue"] == "auditing"){
									$("#stockBgpop").show();
									AlertDialog.tip("领投人认证认证中");
								}else if(data["leadInvestorAuthStateValue"] == "refused"){
									$("#stockBgpop").show();
									AlertDialog.tip("领投人认证失败");
								}else{
									window.location.href = path + "/common/stock-pay.html?loanNo="+loanNo+"&loanState="+loanState;
								}
							}else{
								$("#stockBgpop").show();
								//AlertDialog.tip("请先进行领投人认证");
								AlertDialog.confirm(gtRz, null, "请先进行领投人认证", "去认证", "再看看", null);
							}
						}else{
							if(data["investorAuthState"]){
								if(data["investorAuthStateValue"] == "auditing"){
									$("#stockBgpop").show();
									AlertDialog.tip("跟投人认证认证中");
								}else if(data["investorAuthStateValue"] == "refused"){
									$("#stockBgpop").show();
									AlertDialog.tip("跟投人认证失败");
								}else{
									window.location.href = path + "/common/stock-pay.html?loanNo="+loanNo+"&loanState="+loanState;
								}	
							}else{
								$("#stockBgpop").show();
								//AlertDialog.tip("请先进行跟投人认证");
								AlertDialog.confirm(gtRz, null, "请先进行跟投人认证", "去认证", "再看看", null);
//								AlertDialog.confirm(gtRz, null, "请先进行领投人认证", "去认证", "再看看", null);
							}
						}

					}
					
				}else{
					$("#stockBgpop").show();
					//AlertDialog.tip("请先进行会员认证");
					AlertDialog.confirm(memberRz, null, "请先进行会员认证", "去认证", "再看看", null);
				}
			}else{
				$("#stockBgpop").show();
				AlertDialog.confirm(gtsmRz, null, "请先进行实名认证", "去认证", "再看看", null);
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}

function gtRz(){
	window.location.href = path + "/common/centerRZguide.html";
}
function gtsmRz(){
	window.location.href = path + "/common/realNameRZ.html";
}
function memberRz(){
	window.location.href = path + "/common/accountSecurity.html";
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
			pArr.push('<span class="colaa">'+data[i]["userNickName"]+'：</span>');
		}else{
			pArr.push('<span class="colaa">'+data[i]["discussUser"]+'：</span>');
		}
//		pArr.push('<span class="colaa">'+data[i]["discussUser"]+'：</span>');
		pArr.push(data[i]["content"]+'</p>');
		pArr.push('<p class="mt15 col8">'+data[i]["discussTime"].substring(0,10)+'<a class="replay" aid="'+data[i]["id"]+'" onClick="replyShow(this)" >回复</a></p>');
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
	$("#proCommentContent").fadeIn();
}

//回复
function replyShow(id){
	var _id = $(id).attr("aid");
	$("#"+_id).show();
}
function reply(id){
	if(browse){
		return false;
	}
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


//切换tab标签
function changeTab(){
	$("#detailTab a").click(function(){
		if(siteUserId == "null"){//判断是否登录 未登录先登录
			$("#bgpop").show();
			AlertDialog.confirm(go2Login,null,"您还没有登录，请先登录","登录","取消",null);
			return false;
		}
		var _this= $(this);
		if(!browse){
			$.ajax({
				url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
				type: "post",
				dataType: "json",
				data: {"loanNo": loanNo},
				success: function(data){
					//印章认证
					if(data["isAuth"]){
						if(data["memberState"]){
							if(data["investorAuthState"] || data["leadInvestorAuthState"] || data["orgInvestAuthState"] || data["orgLeadAuthState"]){
								
								var tName = _this.attr("name"); 
								var turl = _this.attr("url");
								if(_this.attr("name") == "rzxx" ||  _this.attr("name") == "team" || _this.attr("name") == "proComment" || _this.attr("name") == "proProgress" ||  _this.attr("name") == "rgList"){
									
									if(_this.attr("name") == "rgList"){
										$.ajax({
											url: path + "/crowdfunding/getCrowdDetail.html",
											type: "post",
											dataType: "json",
											data: {
												"loanNo":loanNo
											},
											success: function(data){
												if(data["msg"]["investFlag"]){
													$("#detailTab a").removeClass("cur");
													_this.addClass("cur");
													showInfoContent(turl, tName,1);
												}else{
													AlertDialog.tip("请先投资！");
												}
											},
											error: function(request){
												console.log("获取个人信息异常");
											}
										});
									}else{
										$("#detailTab a").removeClass("cur");
										_this.addClass("cur");
										showInfoContent(turl, tName,1);
									}

								}else{ //项目简介可直接点击查看
									showInfoContent(_this.attr("url"), _this.attr("name"),1);
									$("#detailTab a").removeClass("cur");
									_this.addClass("cur");
								}
							}else{
								AlertDialog.mtip("请先投资认证",center);
								return false;
							}

						}else{
							AlertDialog.mtip("请先开通会员",href);
							return false;
						}
					}else{
						AlertDialog.mtip("请先实名认证",href);
						return false;
					}
				},
				error: function(request){
					console.log("项目异常");
				}
			});
		}else{
			var tName = _this.attr("name"); 
			var turl = _this.attr("url");
			if(_this.attr("name") == "rzxx" ||  _this.attr("name") == "team" || _this.attr("name") == "proComment" || _this.attr("name") == "proProgress" ||  _this.attr("name") == "rgList"){
				
				if(_this.attr("name") == "rgList"){
					$("#detailTab a").removeClass("cur");
					_this.addClass("cur");
					showInfoContent(turl, tName,1);
				}else{
					$("#detailTab a").removeClass("cur");
					_this.addClass("cur");
					showInfoContent(turl, tName,1);
				}

			}else{ //项目简介可直接点击查看
				showInfoContent(_this.attr("url"), _this.attr("name"),1);
				$("#detailTab a").removeClass("cur");
				_this.addClass("cur");
			}
		}
		

	});
}
function charge(){
	window.location.href = path + "/common/centerRZ.html";
}
function href(){
	window.location.href = path + "/common/accountSecurity.html";
}
function center(){
	window.location.href = path + "/common/centerRZguide.html";
}
//查看详情页标签下内容
function showInfoContent(url, id, page){
	
	$("#detailBody>div").hide();
	//点击标签按钮后，页面滑动到合适查看的位置
//	if($("#xs_video").css("display") == "none"){
//		$('html,body').animate({
//			scrollTop : '320px'
//		}, 800);
//	}else{
//		$('html,body').animate({
//			scrollTop : '320px'
//		}, 800);
//	}
	if($("#" + id + "Content").attr("h") == "1"){ //已经填充过内容
		$("#" + id + "Content").show();
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
						pArr.push('<span class="colaa">'+data[i]["userNickName"]+'：</span>');
					}else{
						pArr.push('<span class="colaa">'+data[i]["discussUser"]+'：</span>');
					}
					pArr.push(data[i]["content"]+'</p>');
//					pArr.push('<span class="colaa">'+data[i]["discussUser"]+'：</span>'+data[i]["content"]+'');
					pArr.push('</p>');
					pArr.push('<p class="mt15 col8">'+data[i]["discussTime"].substring(0,10)+'<a class="replay" aid="'+data[i]["id"]+'" onClick="replyShow(this)" >回复</a></p>');
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
					pStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>抱歉，暂无数据哦~</div>';
					$("#loanProgress").html(pStr);
					$("#proProgressContent").fadeIn().attr("h", "1");
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
				$("#proProgressContent").fadeIn().attr("h", "1");
				var height = $("#loanProgress").height()-($("#loanProgress>div").eq($("#loanProgress>div").length-1).height()-15);
				$("#jd_content_bor").css("height",height+"px");
				
				//点击bottom里面的小图展示回报设置的大图
				$("#loanProgress img.jd_img").unbind("click").click(function(){
					$("#bgpop").show();
					$("#loanBig").css({"left":(cv["winW"]-820)/2+"px","top":(cv["winH"]-520)/2+"px"}).show();
					$("#loanBig>div>img").attr("src",$(this).attr("src")).show();
					$("#big_close").css({"left":($("#loanBig>div>img").offset().left+$("#loanBig>div>img").width()-20)+"px","top":($("#loanBig>div>img").offset().top-$(document).scrollTop()-20)+"px"}).show();
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
	showInfoContent($("#detailTab a.cur").attr("url"), $("#detailTab a.cur").attr("name"), obj["current"]);
}
function ajaxPagecommentData(obj){
	if(pagePause == 0){
		return false;
	}
	showInfoContent($("#detailTab a.cur").attr("url"), $("#detailTab a.cur").attr("name"), obj["current"]);
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
//约谈事件
function talkAboutLoan(){
	if(siteUserId == "null"){
		go2Login();
		return false;
	}
	$("#dateStart,#talkArea").val("");
	//展示约谈弹框
	$("#bgpop").show();
	var sl = (cv["winW"]-419)/2, st = (cv["winH"]-304)/2;
	$("#talkDiv").css({"top":st+"px","left":sl+"px"}).show();
	//关闭事件
	$("#bgpop,.prompt_close").click(function(){
		$("#talkDiv,#bgpop").hide();
		//$(this).hide();
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
					"isStationMsg": 1,
					"isEmail": 0,
					"interviewContent": $("#talkArea").val()
				},
				success: function(data){
					if(!data["success"]){
						$("#talkDiv,#bgpop").hide();
						AlertDialog.tip(data["msg"]);
						return false;
					}else{
						$("#talkDiv,#bgpop").hide();
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
		AlertDialog.tip("您不能投自己的项目");
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
	window.location.href = path + "/common/centerRZ.html?type=leadAuthState";
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
			console.log("获取领投人列表异常！");
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
	/*var sArr = [], sStr = '', l;
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
	});*/
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
