var loanNo, loanState; //预定义项目编号和评论过后的标识
loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7, window.location.search.indexOf("&state"));
loanState = window.location.search.substring(window.location.search.indexOf("state=")+6, window.location.search.length);
$(function(){
	getCrowdDetail(); 
	changeTab();
	
	getBackSetList();
	ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传项目封面图片
	
	$("#subComment").click(submitComment);//发表评论
	$("#gzimg").click(attentionLoan); //关注 | 赞
	
	showProEvolve();
	$("#privateSale").click(toPrivateSale); //私信
	//详情tab标签置顶操作
	$(window).scroll(function(){
		if($(document).scrollTop() > 877){
			$("#loanDetailTab").css({"position":"fixed", "top":"0", "height":"42px", "z-index":"2", "margin-top":"0"});
		}else{
			$("#loanDetailTab").css({"position":"static", "top":"0", "height":"64px", "z-index":"2", "margin-top":"0"});
		}
	});
});
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
			$("#loanCover").attr("src",cv["fileAddress"] + "/" + data["loanLogo"]).attr("alt",data["loanName"]);
			$("#loanName").text(data["loanName"]);
			$("#loanDesc").text(data["loanDes"]);
			//下部右侧赋值
			if(data["photo"]){
				$("#userPhone").attr("src",cv["fileAddress"] + "/" + data["photo"]);//发起人头像
			}else{
				$("#userPhone").attr("src",path + "/images/defaultPhoto.png");//发起人头像
			}
			if(data["selfDetail"]){
				$("#selfDetail").html('<i>个人简介</i>&nbsp;&nbsp;&nbsp;&nbsp;'+data["selfDetail"]);//个人简介
			}
			$("#loanUser").text(data["loanUser2"]);
			$("#saleArea").attr("loanUser", data["loanUser"]); //用于私信的LoanUser
			//loanUser存cookie，用于支付页面发送私信
			document.cookie = "supportLoanUser="+data["loanUser"]+";path=/;";
			if(data["loanProvinceName"]){
				$("#loanAddress").html('&nbsp;&nbsp;' + data["loanProvinceName"] );
			}
			if(data["loanCityName"]){
				$("#loanAddressCity").html('&nbsp;&nbsp;' + data["loanCityName"]);
			}
			$("#approveAmt").text(data["approveAmt"]);
			$("#approveAmt1").text(data["approveAmt"]);
			if(data["supportRatio"]*100 > 100){
				$("#pBar").css("width", "100%");
			}else{
				$("#pBar").css("width", (data["supportRatio"]*100).toFixed(2) + "%");
			}
			$("#supportRatio1").text((data["supportRatio"]*100).toFixed(2) + "%");
			
			if(data["loanState"] == "funded" || data["loanState"] == "lended" || data["loanState"] == "end"){//筹款结束
				$("#remainDay").text(0);
				$("#remainDay1").text(0);
			}else{
				remainDays = data["remainDays"] ? (data["remainDays"] < 0 ? 0 : data["remainDays"]) : 0;
				$("#remainDay").text(remainDays);
				$("#remainDay1").text(remainDays);
			}
			$("#fundDays").text(data["fundDays"]);
			$("#fundAmt").text(data["fundAmt"]);
			$("#supportNum").text(data["supportNum"]);
			$("#supportNum1").text(data["supportNum"]);
			$("#commentNum").text(data["commentNum"]);
			if(data["loanVideo"]){ //如果有视频，先加载视频
				$("#proInfoContent").html('<embed width="840" height="450" src="'+data["loanVideo"]+'" allowFullScreen="true" value="transparent" quality="high" align="middle" wmode="Opaque"  mode="transparent" type="application/x-shockwave-flash"></embed>');
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
		$(this).parent().siblings().removeClass("on");
		$(this).parent().addClass("on");
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
			for(var i=0;i<l;i++){
				if(i < 3){
					var state = data[i]["state"];
					if(i == 0){
						tArr.push('<div class="clearfix jlzc_box1 bb0" style="z-index:'+(n--)+'">');
					}else{
						tArr.push('<div class="clearfix jlzc_box1" style="z-index:'+(n--)+'">');
					}
					tArr.push('<div class="jlzc_zc_1 fl" name="bbtn">');
					if(siteUserId == "null"){
						if(state == "notfull"){
							tArr.push('<a href="javascript:void(0);" onclick="go2Login();">支持¥'+data[i]["amt"]+'</a>');
						}else if(state == "full"){
							tArr.push('<a style="background:#ccc;" href="javascript:void(0);">支持¥'+data[i]["amt"]+'</a>');
						}
						
					}else{
						if(loanState == "funding"){ //筹款中
							if(state == "notfull"){
								tArr.push('<a href="'+path+'/common/support-pubBenefit.html?loanNo='+loanNo+'&backNo='+data[i]["backNo"]+'" style="" target="_blank">支持¥'+data[i]["amt"]+'</a>');
							}else if(state == "full"){
								tArr.push('<a href="javascript:void(0);" style="background:#ccc">支持¥'+data[i]["amt"]+'</a>');
							}
						}else{
							tArr.push('<a href="javascript:void(0);" style="background:#ccc">支持¥'+data[i]["amt"]+'</a>');
						}
					}
					tArr.push('</div>');
					tArr.push('<div class="jlzc_zc_p1" name="bcontent">');
					if(data[i]["photoUrl1"]){
						tArr.push('<p class="p1">支持'+wordNum[i]+'      '+data[i]["backContent"]+'<img class="loanlogoC fr"  sid="small'+smallN+''+i+'" src="'+path+'/images/Untitled-3_03.png"></p>');
						tArr2.push('<div  sid="small'+smallN+''+i+'img"><img class="imgWd2"  src="'+ cv["fileAddress"] + '/' + data[i]["photoUrl1"] +'"></div>');
					}else{
						tArr.push('<p class="p1">支持'+wordNum[i]+'      '+data[i]["backContent"]+'</p>');
					}
					tArr.push('<p style="margin-top:15px;">');
					if(data[i]["numberLimits"] == 0){ //不限额
						tArr.push('已有 '+data[i]["supportNum"]+' 位支持者 / 不限额<br />');
					}else{
						tArr.push('已有 '+data[i]["supportNum"]+' 位支持者 / 限额 '+data[i]["numberLimits"]+' 位<br />');
					}
					if(data[i]["backTime"] == 0){ //回报发送时间
						tArr.push('预计回报发送时间：  项目成功结束后立即发送<br />');
					}else{
						tArr.push('预计回报发送时间： 项目成功结束后'+data[i]["backTime"]+'日发送 <br />');
					}
					tArr.push('</p></div></div>');
				}
			}
			tStr = tArr.join("");
			$("#topBackSetList").html(tStr);
			tStr2 = tArr2.join("");
			$("#loanBig").html(tStr2);
			
			$("#topBackSetList>div").mouseover(function(){
				$(this).css("border-top", "2px solid #FF500B");
				$(this).find("div[name='bcontent']").css({"height":"auto","border":"2px solid #FF500B", "border-top":"none"});
				$(this).find("div[name='bbtn']").css({"border":"2px solid #FF500B", "border-right":"3px solid #FFF", "border-top":"none"});
			}).mouseout(function(){
				$(this).css("border-top", "1px solid #e8e7e4");
				$(this).find("div[name='bcontent']").css({"height":"40px", "border":"none"});
				$(this).find("div[name='bbtn']").css("border", "none");
			});
			
			//点击页面回报设置的小图展示回报设置的大图
			$("#topBackSetList img[sid^='small'").unbind("click").click(function(){
				$("#bgpop2").show();
				$("#big_close").show();
				$("#loanBig").css({"left":(cv["winW"]-820)/2+"px","top":(cv["winH"]-520)/2+"px"}).show();
				$("#loanBig>div").hide();
				$("#loanBig>div[sid='"+$(this).attr("sid")+"img']").show();
			});
			//大图片关闭按钮事件
			$("#big_close").unbind("click").click(function(){
				$("#loanBig").hide();
				$("#bgpop2").hide();
				$("#big_close").hide();
			});
			
			//展示弹出的更多回报设置
			for(var i=0;i<l;i++){
				aArr.push('<div class="jlzc_div2" style="border:1px solid #FFF;padding:8px;">');
				if(data[i]["numberLimits"] == 0){ //不限额
					aArr.push('<p>已有 '+data[i]["supportNum"]+'位支持者  /不限额</p>');
				}else{
					aArr.push('<p>已有 '+data[i]["supportNum"]+'位支持者    /限额 '+data[i]["numberLimits"]+' 位</p>');
				}
				aArr.push('<p style="width:420px;">'+data[i]["backContent"]+'</p>');
				if(data[i]["backTime"] == 0){ //回报发送时间
					if(data[i]["photoUrl1"]){
						aArr.push('<p>预计回报发送时间： 项目成功结束后立即发送<img class="loanlogoC"  sid="small'+smallN+''+i+'" src="'+path+'/images/Untitled-3_03.png"></p>');
					}else{
						aArr.push('<p>预计回报发送时间： 项目成功结束后立即发送</p>');
					}
				}else{
					if(data[i]["photoUrl1"]){
						aArr.push('<p>预计回报发送时间： 项目成功结束后'+data[i]["backTime"]+'日发送<img class="loanlogoC"  sid="small'+smallN+''+i+'" src="'+path+'/images/Untitled-3_03.png"></p>');
					}else{
						aArr.push('<p>预计回报发送时间： 项目成功结束后'+data[i]["backTime"]+'日发送</p>');
					}
				}
				if(siteUserId == "null"){
					if(data[i]["state"] == "notfull"){
						aArr.push('<p class="jlzc_rgje" style="float:right;margin-top:-60px;"><a href="javascript:void(0);" onclick="moreBack2Login();">支持￥'+data[i]["amt"]+'</a></p>');
					}else if(data[i]["state"] == "full"){
						aArr.push('<p class="jlzc_rgje" style="float:right;margin-top:-60px;"><a href="javascript:void(0);" style="background-color:#CCC;" onclick="moreBack2Login();">支持￥'+data[i]["amt"]+'</a></p>');
					}
				}else{
					if(loanState == "funding"){ //筹款中
						if(data[i]["state"] == "notfull"){
							aArr.push('<p class="jlzc_rgje" style="float:right;margin-top:-60px;"><a href="'+path+'/common/support-pubBenefit.html?loanNo='+loanNo+'&backNo='+data[i]["backNo"]+'" target="_blank">支持￥'+data[i]["amt"]+'</a></p>');
						}else{
							aArr.push('<p class="jlzc_rgje" style="float:right;margin-top:-60px;"><a href="javascript:void(0);" style="background-color:#CCC;">支持￥'+data[i]["amt"]+'</a></p>');
						}
					}else{
						aArr.push('<p class="jlzc_rgje" style="float:right;margin-top:-60px;"><a href="javascript:void(0);" style="background-color:#CCC;">支持￥'+data[i]["amt"]+'</a></p>');
					}
				}
				aArr.push('</div>');
			}
			aStr = aArr.join("");
			$("#dialogBackSet").html(aStr);
			
			
			//点击更多里面的小图展示回报设置的大图
			$("#dialogBackSet img[sid^='small'").unbind("click").click(function(){
				$("#bgpop2").show();
				$("#big_close").show();
				$("#loanBig").css({"left":(cv["winW"]-820)/2+"px","top":(cv["winH"]-520)/2+"px"}).show();
				$("#loanBig>div").hide();
				$("#loanBig>div[sid='"+$(this).attr("sid")+"img']").show();
			});
			//大图片关闭按钮事件
			$("#big_close").unbind("click").click(function(){
				$("#loanBig").hide();
				$("#bgpop2").hide();
				$("#big_close").hide();
			});
			
			//展示右侧底部回报列表
			for(var i=0;i<l;i++){
				var state = data[i]["state"];
				bArr.push('<div class="jlzc_rig_5 clearfix">');
				bArr.push('<p class="rg">');
				if(siteUserId == "null"){
					if(state == "notfull"){
						bArr.push('<span class="rgq"><a href="javascript:void(0);" onclick="go2Login();">支持￥'+data[i]["amt"]+'</a></span>');	
					}else if(state == "full"){
						bArr.push('<span class="rgq" style="background:#ccc;"><a href="javascript:void(0);"">支持￥'+data[i]["amt"]+'</a></span>');
					}
				}else{
					if(loanState == "funding"){ //筹款中
						if(state == "notfull"){
							bArr.push('<span class="rgq"><a href="'+path+'/common/support-pubBenefit.html?loanNo='+loanNo+'&backNo='+data[i]["backNo"]+'" target="_blank">支持￥'+data[i]["amt"]+'</a></span>');
						}else if(state == "full"){
							bArr.push('<span class="rgq" style="background:#ccc;"><a href="javascript:void(0);">支持￥'+data[i]["amt"]+'</a></span>');
						}
					}else{
						bArr.push('<span class="rgq" style="background:#ccc;"><a href="javascript:void(0);">支持￥'+data[i]["amt"]+'</a></span>');
					}
				}
				bArr.push('<span>已有'+data[i]["supportNum"]+'人支持</span>');
				bArr.push('</p>');
				if(data[i]["photoUrl1"]){
					bArr.push('<p>'+data[i]["backContent"]+'<img class="loanlogoC fr" style="margin-right:50px;"   sid="small'+smallN+''+i+'" src="'+path+'/images/Untitled-3_03.png"></p>');
				}else{
					bArr.push('<p>'+data[i]["backContent"]+'</p>');
				}
				if(data[i]["backTime"] == 0){ //回报发送时间
					bArr.push('<p>回报发送时间：预计项目结束后立即回报</p>');
				}else{
					bArr.push('<p>回报发送时间：预计项目结束后'+data[i]["backTime"]+'天发送</p>');
				}
				bArr.push('</div>');
			}
			bStr = bArr.join("");
			$("#bottomBackSetList").html(bStr);
			
			
			//点击bottom里面的小图展示回报设置的大图
			$("#bottomBackSetList img[sid^='small'").unbind("click").click(function(){
				$("#bgpop2").show();
				$("#big_close").show();
				$("#loanBig").css({"left":(cv["winW"]-820)/2+"px","top":(cv["winH"]-520)/2+"px"}).show();
				$("#loanBig>div").hide();
				$("#loanBig>div[sid='"+$(this).attr("sid")+"img']").show();
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
	}else if(id == "proInfo"){
		$("#proInfoContent").show();
	}
	//点击标签按钮后，页面滑动到合适查看的位置
	$('html,body').animate({
		scrollTop : '677px'
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
		for(var i = 0;i<l;i++){
			if(i%2 == 0){
				pArr.push('<tr class="even">');
			}else{
				pArr.push('<tr class="odd">');
			}
			pArr.push('<td style="width:120px;font-size:14px;">'+(i+1)+'</td>');
			pArr.push('<td style="width:165px;font-size:14px;">'+data[i]["supportUser2"]+'</td>');
			pArr.push('<td style="width:180px;font-size:14px;">￥'+data[i]["supportAmt"]+'</td>');
			pArr.push('<td style="width:300px;font-size:14px;">'+data[i]["supportTime"]+'</td>');
			pArr.push('<td style="width:130px;font-size:14px;">'+data[i]["payStateName"]+'</td></tr>');
		}
		pStr = pArr.join("");
		$("#suportTable").html(pStr);
		//刷新后改变众筹记录数量
		$("#supportNum1").text(num);
		//分页设置
		pagePause = 0;
		if(page < 2){
			$("#suportPage").jPages({
				containerID : "suportTable",
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
		$("#commentUl>ul").html(pStr);
		//刷新后改变评论数量
		$("#commentNum").text(num);
		//分页设置
		pagePause = 0;
		if(page < 2){
			$("#commonPage").jPages({
				containerID : "commentUl",
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
	showInfoContent($("#detailTab>li.on>a").attr("url"), $("#detailTab>li.on>a").attr("name"), obj["current"]);
}
function ajaxPagecommentData(obj){
	if(pagePause == 0){
		return false;
	}
	showInfoContent($("#detailTab>li.on>a").attr("url"), $("#detailTab>li.on>a").attr("name"), obj["current"]);
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
	$("#commentUl>ul").html(pStr);
	$("#commentNum").text(num);
	//分页设置
	pagePause = 0;
	if(page < 2){
		$("#commonPage").jPages({
			containerID : "commentUl",
			clickStop   : true,
			perPage	: 10,
			allSumPage : sumPage,
			callback: ajaxPagecommentData
		});
	}
	$("#proCommentContent").fadeIn().attr("h", "1");
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
				pStr = '<span style="padding:15px;color:red;">暂无数据</span>';
				dStr = '<span style="padding:15px;color:red;">暂无数据</span>';
				$("#loanProgress").html(pStr);
				//展示项目动态
				$("#proDynBox").html(dStr);
				//$(".jlzc_rig_3 .xian").hide();
				return false;
			}
			for(var i=0;i<l;i++){
				//显示项目进展
				pArr.push('<li>');
				pArr.push('<span class="pro_jz_rq">'+data[i]["enterTime"].substring(0,10)+'</span>');
				pArr.push('<span class="pro_jz_yq"><img src="'+path+'/images/point_2.png" /></span>');
				pArr.push('<span class="pro_jz_nr">');
/*				pArr.push('<span class="pro_jz_fqr"><span>发起人</span><span style="display:inline-block;width:40px;"></span>'+data[i]["enterUser"]+'</span>');
*/				pArr.push('<span class="pro_jz_del">'+data[i]["enterContent"]+'</span>');
				pArr.push('</span></li>');
				
				//展示项目动态
				dArr.push('<li>');
				dArr.push('<p class="left"><img src="'+path+'/images/dian.png" /></p>');
				dArr.push('<p class="left" style="width:250px;">');
				dArr.push('<i>'+data[i]["enterTime"].substring(0,10)+'</i>');
				dArr.push('<span>'+data[i]["enterContent"]+'</span>');
				dArr.push('</p></li>');
			}
			//展示项目进展
			pStr = pArr.join("");
			dStr = dArr.join("");
			$("#loanProgress").html(pStr);
			$("#proDynBox").html(dStr);
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