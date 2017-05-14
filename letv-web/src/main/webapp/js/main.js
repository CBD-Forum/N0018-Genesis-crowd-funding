//判断访问终端
var browser={
    versions:function(){
        var u = navigator.userAgent, app = navigator.appVersion;
        return {
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            //android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            android: u.indexOf('Android') > -1, //android终端
            iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
            //xiaoMi : u.indexOf("XiaoMi")	//小米手机浏览器
			wp:u.indexOf("Windows Phone") > -1 || u.indexOf("NOKIA") > -1 //判断是否为windowPhone系统
        };
    }(),
    language:(navigator.browserLanguage || navigator.language).toLowerCase()
};
//if(browser.versions.mobile || browser.versions.android || browser.versions.ios || browser.versions.wp){
//	window.location.href=path +  "/common/m-index.html";
//}
var url = document.referrer;
var login = getQueryString("login");
var nameRz = getQueryString("nameRz");
$(function(){
//	if(login||nameRz){	
//		document.URL=location.href
//	}
	loginStatus();//判断登录状态
	$("#topSearchBtn").click(loanTopSearch);
	$(window).scroll(function(){
		if($(document).scrollTop() > 500){
			$("#toSiteTop").unbind("click").css("display","block").bind("click",function(){
				$('html,body').animate({
					scrollTop : '0px'
				}, 800);
			});
		}else{
			$("#toSiteTop").hide();
		}
	});
	$("#centerRZ").click(function(){
		if(siteUserId == "null"){
			window.location.href = path + "/common/login.html" ;
		}else{
			window.location.href = path + "/common/centerRZguide.html" ;
		}
	})
	$(".closeA").click(function(){
		$("#loginDiv").hide();
		$("#regiterDiv").hide();
		$("#bgpop").hide();
		$("#preview").hide();
	});
	$("#rightProjectList").next().hide();
	$("#rightProjectList").click(function(){
		$(this).next().slideToggle();
	})
	//$("a[name='go2loginBtn']").click(go2Login); //登录事件
	//$("a[name='go2RegiterBtn']").click(go2Regiter); //注册事件
	//判断当前选中模块
	$("#navUl>li").removeClass("cur");
	$("#navUl>li>a[name='"+$("#indexFor").attr("namefor")+"']").addClass("cur");
	
	//手机登录，电脑登录切换
	$("#phoneLogin").click(function(){
		$("#imageStream1").attr("src",path+"/security/securityCodeImage.html?"+new Date().getTime());
		$("#imageStream3").attr("src",path+"/security/securityCodeImage.html?"+new Date().getTime());
		//如果有登录框清空登录信息
		$("#loginTip").css("visibility", "hidden").text("");
		$("input").val("");
		//如果有登录弹框隐藏
		$("#regiterTip").css("visibility", "hidden").text("");
		$("#regeter2Svali").hide();
		
		if($(this).attr("vtype") == "wap"){
			//$("#webBody").hide();
			$("#webBody1").hide();
			$("#webBody2").hide();
			$("#webBody3").hide();
			//$("#phoneBody").show();
			$("#webBody4").show();
			$("#webBody5").show();
			$(this).attr("vtype","web");
			$(this).attr("style","background:none").text("返回电脑登录");
			$("#viliBtn2").unbind("click").click(sendLoginViliCode);
			go2WapLogin();
		}else if($(this).attr("vtype") == "web"){
//			$("#webBody").show();
//			$("#phoneBody").hide();
			$("#webBody1").show();
			$("#webBody2").show();
			$("#webBody3").show();
			$("#webBody4").hide();
			$("#webBody5").hide();
			$(this).attr("vtype","wap");
			$(this).attr("style","  background: url("+path+"/images/tel.png) no-repeat 0 2px;").text("手机短信登录");
			go2WebLogin();
		}
	});
	wxImage();//微信二维码显示
	showHeadPhoto();//显示所有页面头像
	//rrQQ(); //营销QQ展示
	$("#icDown").hover(function(){
		$("#showEWM").show();
	},function(){
		$("#showEWM").hide();
	})
	if(siteUserId != "null"){
		getRightUserInfo();//获取个人中心个人信息
	}
	
});

function myEcreateWebUploader(pickId, showId, setValueId, parentId){
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
	    duplicate:true, //可上传重复图片
	    server: path + '/crowdfunding/uploadLoanFile.html',
	    fileNumLimit: 300,
	    fileSizeLimit: 5 * 1024 * 1024,    // 200 M
	    fileSingleSizeLimit: 5 * 1024 * 1024    // 50 M
	});
	uploader.on('error', function (handler) {
		if("Q_EXCEED_SIZE_LIMIT"==handler){
			AlertDialog.tip("文件超过5M，请重新上传");
			//AlertDialog.show("文件超过5M，请重新上传", pickId, -90, 60);
		}else if("Q_TYPE_DENIED"==handler){
			AlertDialog.tip("图片类型错误");
			//AlertDialog.show("文件类型错误", pickId, -90, 60);
		}else if("Q_EXCEED_NUM_LIMIT"==handler){
			AlertDialog.tip("添加的图片数量超出");
			//AlertDialog.show("添加的文件数量超出", pickId, -90, 60);
		}
    });
	uploader.on('uploadSuccess', function (file, ret) {
//        var $file = $('#' + file.id);
        try {
            var responseText = (ret._raw || ret),
                json = $.parseJSON(responseText);
            if (json["success"]) {
            	var logoUrl = json["msg"];
            	$.ajax({
    				url: path + "/user/updateUserInfo.html",
    				type: "post",
    				dataType: "json",
    				data:{
    					"photo":logoUrl
    				},
    				success: function(data){
    					if(!data["success"]){
    						return false;
    					}
    					$('#'+showId).attr("src", cv["fileAddress"] + "/" + json["msg"]);
    					AlertDialog.tip("修改成功！");
    					setTimeout(function(){
    						window.location.reload();
    					},2000);
    				},error: function(request){
    					console.log("修改用户信息异常");
    				}
    			});
            } else {
//                $file.find('.error').text(json.state).show();
            }
        } catch (e) {
//            $file.find('.error').text(lang.errorServerUpload).show();
        }
    });
	
	return uploader;
}


function loanTopSearch(){
	if(window.location.href.indexOf("projectList") != "-1"){ //当前在项目列表页
		siteSearchList(1);
	}else{
		var base64 = new Base64();
		window.location.href = path + "/common/projectList.html?loanName=" + base64.encode($("#topSearch").val());
	}
}
//获取个人信息
function getRightUserInfo(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(data["isAuth"]){
				$("#realNameRZ").attr("href",path+"/common/realNameRZTG.html");
				$("#realNameRZ").attr("class","rzh4");
				if(data["isBindCard"]){
					$("#bankCardRZ").attr("href",path+"/common/bankList.html");
					$("#bankCardRZ").attr("class","rzh5");
				}else{
					$("#bankCardRZ").attr("href",path+"/common/bankList.html");
					$("#bankCardRZ").attr("class","rzh3");
				}
			}else{
				$("#realNameRZ").attr("href",path+"/common/realNameRZ.html");
				$("#realNameRZ").attr("class","rzh2");
				$("#bankCardRZ").attr("href",path+"/common/realNameRZ.html");
				$("#bankCardRZ").attr("class","rzh3");
				
				//没有实名认证申请众筹跳转实名认证页面
				$("#enterPubBenefit").click(function(){
					window.location.href = path + "/common/realNameRZ.html";
				});
				$("#enterReward").click(function(){
					window.location.href = path + "/common/realNameRZ.html";
				});
				$("#enterStock").click(function(){
					window.location.href = path + "/common/realNameRZ.html";
				});
			}
			data = data["user"];
			if(data["photo"]){
				$("#centerUserPhoto").attr("src", cv["fileAddress"] + data["photo"]); //头像
				$("#imgheadPhoto").attr("src", cv["fileAddress"] + data["photo"]); //头像
			}else{
				$("#centerUserPhoto").attr("src", path + "/images/defaultPhoto-113.png"); //头像
				$("#imgheadPhoto").attr("src", path + "/images/defaultPhoto-113.png"); //头
			}
			if(data["nickName"]){
				$("#userRealName").text(data["nickName"]); //真实姓名
			}else{
				$("#userRealName").text(data["userId"]); //真实姓名
			}
			
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//判断登录状态
function loginStatus(){
	if(siteUserId != "null"){
		if(nickName.length > 9){
			nickName = nickName.substring(0,9) + "...";
		}
		if(userphoto){
			$("#user_top").html('<a href="' + path + '/common/myCenter.html"><img class="rightImgs" src="'+cv.fileAddress+userphoto+'"/></a><a style="padding:0 7px;" href="' + path + '/common/myCenter.html"  class="red">'+nickName+'</a><a id="logout" href="javascript:void(0);">退出</a>');
		}else{
			$("#user_top").html('<a href="' + path + '/common/myCenter.html"><img class="rightImgs" src="'+path+'/images/defaultPhoto-23.png"/></a><a style="padding:0 7px;" href="' + path + '/common/myCenter.html"  class="red">'+nickName+'</a><a id="logout" href="javascript:void(0);">退出</a>');
		}
		$("#logout").bind('click',function(){
			logout();
		});
	}else{
		$("#user_top").html('<a class="red" href="' + path + '/common/login.html">登录</a><span  style="padding:0 5px;"></span><a href="' + path + '/common/register.html">注册</a>');
		
	}
}
//退出登录
function logout(){
	$.ajax({
		url: path + "/user/loginOut.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			if($("#indexFor").attr("namefor") == "homepage"){//当前是首页
				var expires = new Date(); 
				expires.setTime(expires.getTime() - 1000);
				document.cookie = "logined=;path=/;expires=" + expires.toGMTString() + "";
//				$("#user_top").show();
				$("#user_top").html('<a name="go2RegiterBtn" onclick="go2Regiter();" href="javascript:void(0);">免费注册</a><a name="go2loginBtn" onclick="go2Login();" href="javascript:void(0);">登录</a>');
			}else{
				var expires = new Date(); 
				expires.setTime(expires.getTime() - 1000);
				document.cookie = "logined=;path=/;expires=" + expires.toGMTString() + "";
				window.location.href = path + "/common/index.html";
			}
		},
		error: function(request){
			if($("#indexFor").attr("namefor") == "homepage"){//当前是首页
				$("#user_top").html('<a name="go2RegiterBtn" onclick="go2Regiter();" href="javascript:void(0);">免费注册</a><a name="go2loginBtn" onclick="go2Login();" href="javascript:void(0);">登录</a>');
			}else{
				window.location.href = path + "/common/index.html";
			}
			console.log("获取退出登录信息异常异常");
		}
	});
}
/**
 *  在网站顶部查询众筹项目列表
 */
function siteSearchList(page){
	var lArr = [], lStr = '', l, sumPage;
	var remainDays = 0;
	$.ajax({
		url: path + "/crowdfunding/getPageCrowdList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanName": $("#topSearch").val(),
			"sort": "defaultSort",
			"page": page,
			"rows": 6
		},
		success: function(data){
			if(!data["success"]){
				lStr = '<div style="padding:30px;color:red;">暂无数据</div>';
				$("#projectList").html(lStr);
				$("div.page").hide();
				return false;
			}else{
				sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
				l = data["msg"]["rows"].length, data = data["msg"]["rows"];
				if(l == 0){
					lStr = '<div style="padding:30px;color:red;">暂无数据</div>';
					$("#projectList>ul").html(lStr);
					$("div.page").hide();
					return false;
				}else{
					for(var i=0;i<l;i++){
						lArr.push('<li class="list_ul_big">');
						lArr.push('<div class="img"><img src="'+cv["fileAddress"]+data[i]["loanLogo"]+'"/></div>');
	                	lArr.push('<div class="list_ul_pop"></div>');
	                    lArr.push('<div class="list_ul_font">');
	                    lArr.push('<div class="ul_font_top">');
	                    
	                    if(data[i]["loanType"] == "entity" || data[i]["loanType"] == "public_service"){ //如果是产品项目
	                    	lArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'&state='+data[i]["loanState"]+'">');
	                    }else{
							if(data[i]["loanType"] == "stock"){
								if(siteUserId == "null"){
									lArr.push('<a target="_blank" href="javascript:void(0);" onclick="go2Login();">');
								}else{
									lArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
								}
							}else{
								lArr.push('<a target="_blank" href="'+path+'/common/loanDetail-'+data[i]["loanType"]+'.html?loanNo='+data[i]["loanNo"]+'">');
							}
						}
	                    
	                    lArr.push('<p class="p1">'+data[i]["loanName"]+'</p>');
                        lArr.push('<p class="p2">'+data[i]["loanDes"]+'</p>');
                        lArr.push('</a>');
                        lArr.push('</div>');
                        lArr.push('<div class="ul_font_bottom">');
                        lArr.push('<p>已融资：￥'+data[i]["approveAmt"]+'</p>');
                    	lArr.push('<p style="width:250px;">');
                        lArr.push('<span class="fl">项目进度：</span>');
                        
                        if(data[i]["supportRatio"]*100 > 100){
                        	lArr.push(' <span class="jdBar"><span style="width:100%"></span></span>');
                        	lArr.push('<span class="fl fs12">100%</span>');
						}else{
							lArr.push(' <span class="jdBar"><span style="width:'+(data[i]["supportRatio"]*100).toFixed(0)+'%"></span></span>');
							lArr.push('<span class="fl fs12">'+(data[i]["supportRatio"]*100).toFixed(0)+'%</span>');
						}
                        
                        lArr.push('</p>');
                        lArr.push('<p>融资目标：￥'+data[i]["fundAmt"]+'</p>');
                        
                        if(data[i]["loanState"] == "preheat"){ //预热情况下剩余天数取字段不同
							remainDays = data[i]["remainPreheatDays"] ? (data[i]["remainPreheatDays"] < 0 ? "0" : data[i]["remainPreheatDays"]) : "0" ;
						}else{
							remainDays = data[i]["remainDays"] ? (data[i]["remainDays"] < 0 ? "0" : data[i]["remainDays"]) : "0" ;
						}
						if(data[i]["loanState"] == "funded" || data[i]["loanState"] == "lended" || data[i]["loanState"] == "end"){
							lArr.push('<p>筹款成功</p>');
						}else{
							lArr.push('<p>剩余时间：'+remainDays+'天</p>');
						}

                        lArr.push('</div>');
                        lArr.push('</div>');
	                    lArr.push('</li>');
					}
					lStr = lArr.join("");
					$("#projectList>ul").html(lStr);
					$("div.page").show();
					$("#projectList>ul>li").mouseover(function(){
						$(this).find("div[name='s1']").hide();
						$(this).find("div[name='s2']").show();
					}).mouseout(function(){
						$(this).find("div[name='s1']").show();
						$(this).find("div[name='s2']").hide();
					});
					//分页设置
					pagePause = 0;
					if(page < 2){
						$("div.page").jPages({
							containerID : "projectList",
							clickStop   : true,
							perPage	: 6,
							allSumPage : sumPage,
							callback: ajaxPageData
						});
					}
				}
			}
		},
		error: function(request){
			console.log("获取项目列表异常");
		}
	});
}
//分页回调函数
function ajaxPageData(obj){
	if(pagePause == 0){
		return false;
	}
	siteSearchList(obj["current"]);
}


function go2WebLogin(){
	//检测用户名
	$("#loginUserId").blur(function(){
		checkNull($(this).val(), "用户名不能为空", "loginTip");
	});
	//检测密码
	$("#loginPassword").blur(function(){
		checkNull($(this).val(), "密码不能为空", "loginTip");
	});
	//检测验证码
	$("#loginValiCode").blur(function(){
		checkValiDate($(this).val(), "loginTip");
	});
	$(document).keyup(function(e){
		var curKey = e.which; 
		if(curKey==13){
			if(checkNull($("#loginUserId").val(), "用户名不能为空"), "loginTip"){//用户名验证
				if(checkNull($("#loginPassword").val() ,"密码不能为空", "loginTip")){//密码验证
					if(checkValiDate($("#loginValiCode").val(), "loginTip")){
						$("#loginBtn").unbind("click").css("background", "#ccc").text("提交中...");
						$.ajax({
							url: path + "/user/login.html",
							type: "post",
							dataType: "json",
							data: {
									"userId": $("#loginUserId").val(),
									"password": $("#loginPassword").val(),
									"valiCode": $("#loginValiCode").val()
									},
							success: function(data){
								if(data["msg"] == "success"){
									document.cookie = "logined=y;path=/;";
									window.location.reload();
								}else{
									$("#loginBtn").unbind("click").click(goodLogin).css("background", "#e3423c").text("登录");
									$("#loginTip").css("visibility","visible").text(data["msg"]);
									$("#imageStream1").attr("src",path+"/security/securityCodeImage.html?"+new Date().getTime());
								}
							},
							error: function(){
								console.log("获取登录提交信息异常");
							}
						});
					}
				}
			}
		}
	});
	$("#loginBtn").unbind("click").click(goodLogin);
}
//登录的方法
function goodLogin(){
	if(checkNull($("#loginUserId").val(), "用户名不能为空"), "loginTip"){//用户名验证
		if(checkNull($("#loginPassword").val() ,"密码不能为空", "loginTip")){//密码验证
			if(checkValiDate($("#loginValiCode").val(), "loginTip")){
				$("#loginBtn").unbind("click").css("background", "#ccc").text("提交中...");
				$.ajax({
					url: path + "/user/login.html",
					type: "post",
					dataType: "json",
					data: {
							"userId": $("#loginUserId").val(),
							"password": $("#loginPassword").val(),
							"valiCode": $("#loginValiCode").val()
							},
					success: function(data){
						if(data["msg"] == "success"){
							document.cookie = "logined=y;path=/;";
							window.location.reload();
						}else{
							$("#loginBtn").unbind("click").click(goodLogin).css("background", "#e3423c").text("登录");
							$("#loginTip").css("visibility","visible").text(data["msg"]);
							$("#imageStream1").attr("src",path+"/security/securityCodeImage.html?"+new Date().getTime());
						}
					},
					error: function(){
						console.log("获取登录提交信息异常");
					}
				});
			}
		}
	}
}
//注册的方法
function goodRegedit(){
	if(Valify.username($("#regiterUserId").val(), false, "regiterTip")){
		if(regiterPhone($("#mobile").val(), "regiterTip")){
			//if(checkValiDate($("#regiterValiCode").val(), "regiterTip")){
			if(checkNull($("#verifyCode").val(), "手机验证码不能为空", "regiterTip")){
				if(checkPassword($("#regiterPassword").val(), "regiterTip")){
					if(checkRePassword($("#regiterPassword2").val(), $("#regiterPassword").val(), "regiterTip")){
						if(!$("#read").prop("checked")){
							$("#regiterTip").css("visibility", "visible").text("请阅读并同意用户协议");
							return false;
						}
						$("#regiterTip").css("visibility", "hidden").text("");
						$("#regiterBtn").css("background", "#CCC").unbind("click").text("提交中...");
						$.ajax({
							url: path + "/user/register.html",
							type: "post",
							dataType: "json",
							data: {
								"userId": $("#regiterUserId").val(),
								"password": $("#regiterPassword").val(),
								"password2": $("#regiterPassword2").val(),
								//"valiCode": $("#regiterValiCode").val(),
								"mobile": $("#mobile").val(),
								"verifyCode": $("#verifyCode").val()
								//"inputInviteCode":$("#inviteCode").val()
							},
							success: function(data){
								if(data["success"]){
									$("#msg-error").css("visibility","hidden");
									//后台注册验证成功，跳转
									document.cookie = "logined=y;path=/;";
									window.location.href = path + "/common/myCenter.html";
								}else{
									$("#regiterBtn").css("background", "#e3423c").unbind("click").click(goodRegedit).text("注册");
									$("#regiterTip").css("visibility","visible").text(data["msg"]);
									$("#imageStream2").attr("src",path+"/security/securityCodeImage.html?"+new Date().getTime());
								}
							},
							error: function(request){
								console.log("注册异常");
							}
						});
					}
				  }
				}
//			}
		}
	}
}
//注册
function go2Regiter(){
	//如果有登录框清空登录信息
	$("#loginTip").css("visibility", "hidden").text("");
	$("input").val("");
	//如果有登录弹框隐藏
	$("#loginDiv").hide();
	$("#regiterTip").css("visibility", "hidden").text("");
	$("#imageStream2").attr("src",path+"/security/securityCodeImage.html?"+new Date().getTime());
	//$("#regeter2Svali").hide();
	
	$("#bgpop").show();
	var lt = (cv["winH"]-630)/2, ll = (cv["winW"]-640)/2;
	$("#regiterDiv").css({"top":"0px","left":ll+"px"}).show();
	//关闭注册框
	$("#bgpop").click(function(){
		//清空注册信息
		$("#regiterTip").css("visibility", "hidden").text("");
		$("input").val("");
		$("#imageStream2").attr("src",path+"/security/securityCodeImage.html?"+new Date().getTime());
		//$("#regeter2Svali").hide();
		$("#regiterDiv").hide();
		$(this).hide();
	});
	//关闭注册框
	$("#regiterClose").click(function(){
		//清空注册信息
		$("#regiterTip").css("visibility", "hidden").text("");
		$("input").val("");
		$("#imageStream2").attr("src",path+"/security/securityCodeImage.html?"+new Date().getTime());
//		$("#regeter2Svali").hide();
		$("#regiterDiv").hide();
		$("#bgpop").hide();
	});
	//检测用户名
	$("#regiterUserId").blur(function(){
		Valify.username($(this).val(), false, "regiterTip");
	});
	//检测手机号
	$("#mobile").blur(function(){
		regiterPhone($(this).val(), "regiterTip");
	});
	//检测密码
	$("#regiterPassword").blur(function(){
		checkPassword($(this).val(), "regiterTip");
	});
	//检测重复密码
	$("#regiterPassword2").blur(function(){
		checkRePassword($(this).val(), $("#regiterPassword").val(), "regiterTip");
	});
	//检测图片验证码
	$("#regiterValiCode").blur(function(){
		checkValiDate($(this).val(), "regiterTip");
	});
	//手机验证码
	$("#verifyCode").blur(function(){
		checkNull($(this).val(), "手机验证码不能为空", "regiterTip");
	});
	//发送手机验证码
//	$("#viliBtn").click(sendRegeterViliCode);
	$("#sendViliimgBtn").unbind("click").click(sendRegeterViliCode);
	//注册
	$("#regiterBtn").unbind("click").click(goodRegedit);
}
function sendRegeterViliCode(){
	//检测手机号
	if(Valify.username($("#regiterUserId").val(), false, "regiterTip")){
		if(regiterPhone($("#mobile").val(), "regiterTip")){
			$.ajax({
				url: path + "/user/validateMobile.html",
				type: "post",
				dataType: "json",
				data: {"mobile": $("#mobile").val()},
				success: function(data){
					if(data["success"]){
						$("#sendViliimgBtn").unbind("click").css("background", "#CCC");
						$("#sendWait").css("margin-left","2px").text("稍后可重发");
						$.ajax({
							url: path + "/user/sendRegisterVerifyCode.html",
							type: "post",
							dataType: "json",
							data: {
								"userId": $("#regiterUserId").val(),
								"sendTarget": $("#mobile").val(),
								"valiCode": $("#regiterValiCode").val()
							},
							success: function(data){
								if(data["success"]){
//									$("#regiterValiCode").val("");
//									$("#imageStream2").attr("src",path + "/security/securityCodeImage.html?" + new Date().getTime());
									$("#viliBtn").unbind("click").css({"cursor":"default","color":"#888"}).text("60 秒后可重发");
									$("#sendViliimgBtn").css("background", "#CCC").unbind("click");
									countDown(60, "sendWait", regiterOverFn);
									$("#regiterTip").css("visibility","hidden");
								}else{
									$("#sendViliimgBtn").css("background", "#e3423c").click(sendRegeterViliCode);
									$("#sendWait").text("");
									$("#imageStream2").attr("src",path + "/security/securityCodeImage.html?" + new Date().getTime());
									$("#regiterTip").text(data["msg"]).css("visibility","visible");
								}
							},
							error: function(request){
								conosle.log("发送手机验证码异常");
							}
						});
					}else{
						$("#regiterTip").css("visibility","visible").text(data["msg"]);
						$("#sendViliimgBtn").css("background", "#e3423c").click(sendRegeterViliCode);
						$("#sendWait").text("");
					}
				},
				error: function(request){
					$("#regiterTip").css("visibility","visible").text("发送异常，请稍后再试");
					conosle.log("判断手机号重复异常");
				}
			});
		}
	}
}
function sendLoginViliCode(){
	//检测手机号
	if(regiterPhone($("#phoneNum").val(), "loginTip")){
		$(this).unbind("click").css({"cursor":"default","color":"#888"}).text("稍后可重发");
		$.ajax({
			url: path + "/user/sendMobileLoginVerifyCode.html",
			type: "post",
			dataType: "json",
			data: {
				"sendTarget":$("#phoneNum").val()
				//"valiCode": $("#verifyCode2").val()
			},
			success: function(data){
				if(data["success"]){
					$("#viliBtn2").unbind("click").css({"cursor":"default","color":"#888"}).text("60 秒后可重发");
					countDown(60, "viliBtn2", loginOverFn);
					$("#loginTip").css("visibility","hidden");
				}else{
					$("#viliBtn2").text("获取").css({"cursor":"pointer","color":"#55acef"});
					$("#loginValiCode2").val("");
					$("#loginTip").css("visibility","visible").text(data["msg"]);
					//$("#imageStream3").attr("src",path + "/security/securityCodeImage.html?" + new Date().getTime());
					//$("#loginTip").text(data["msg"]).css("visibility","visible");
				}
			},
			error: function(request){
				conosle.log("发送手机验证码异常");
			}
		});
	}
}
function loginOverFn(){
	$("#viliBtn2").text("获取").css({"cursor":"pointer","color":"#55acef","background":"none"});
	$("#viliBtn2").unbind("click").click(sendLoginViliCode);
}
function regiterOverFn(){
	$("#sendWait").text("");
	$("#sendViliimgBtn").css("background", "#e3423c").click(sendRegeterViliCode);
}
//验证用户名、密码是否为空
function checkNull(str, type, id){
	if(!str){
		$("#" + id).css("visibility","visible").text(type);
		return false;
	}
	$("#" + id).css("visibility","hidden");
	return true;
}
//验证码实时验证
function checkValiDate(str, id){
	var valiCode = str, result = false;
	return Valify.valiCode(valiCode, result, id);
}
//验证注册手机号
function regiterPhone(phone, id){
	var isPhoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
	if(!phone){
		$("#" + id).css("visibility","visible").text("手机号不能为空");
		return false;
	}
	if(isPhoneReg.exec(phone)){
		$("#" + id).css("visibility","hidden");
		return true;
	}else{
		$("#" + id).css("visibility","visible").text("手机号不正确");
		return false;
	}
}
//检测密码
function checkPassword(str,id){
	if(!str){
		$("#" + id).css("visibility","visible").text("密码长度应该为6~16位,由字母和数字组成");
		return false;
	}
	if(!/^(\w){6,16}$/.exec(str)){
		$("#" + id).css("visibility","visible").text("密码长度应该为6~16位,由字母和数字组成");
		return false;
	}else{
		$("#" + id).css("visibility","hidden");
		return true;
	}
}
//检测重复密码
function checkRePassword(str, str1, id){
	if(!str1){
		$("#" + id).css("visibility","visible").text("密码不能为空");
		return false;
	}
	if(str != str1){
		$("#" + id).css("visibility","visible").text("两次输入密码不一致");
		return false;
	}else{
		$("#msg-error").css("visibility","hidden");
		return true;
	}
}
//微信二维码的显示与隐藏
function wxImage(){
	//顶部二维码
	$("#TopWxBtn").mouseenter(function(){
		$(this).next("div.TopWeixin").fadeIn("slow");
	}).mouseleave(function(){
		$(this).next("div.TopWeixin").fadeOut("slow");
	});
	//右部二维码
	$("#RightWxBtn").mouseenter(function(){
		$(this).find("div").fadeIn("slow");
	}).mouseleave(function(){
		$(this).find("div").fadeOut("slow");
	});
}
//展示layout所有页面头像
function showHeadPhoto(){
	$.ajax({
		url: path + "/user/getUserInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				$("#siteHeadPhoto").attr("src", path + "/images/defaultPhoto.png");
				return false;
			}
			if(data["msg"]["photo"]){
				$("#siteHeadPhoto").attr("src", cv["fileAddress"] + "/" + data["msg"]["photo"]);
			}else{
				$("#siteHeadPhoto").attr("src", path + "/images/defaultPhoto.png");
			}
		},
		error: function(request){
			console.log("获取顶部头像信息异常");
			$("#siteHeadPhoto").attr("src", path + "/images/defaultPhoto.png");
		}
	});
}
function rrQQ(){
	BizQQWPA.addCustom({aty: '0', a: '0', nameAccount: 799896863, selector: 'BizQQWPA'});
}
//获取关于我们二级菜单
function getSecondMenu(){
	$.ajax({
		url: path + "/auth/getAboutOurList.html",
		type: "post",
		dataType: "json",
		async:false,
		success: function(data){
			var html = [];
			for(var i=0;i<data.length;i++){
				var url = data[i]["url"];
				var code = url.indexOf("nodeType=") == -1 ? "" : url.substring(url.indexOf("=")+1);
				html.push('<li><a href="' + path + url +'" code="' + code + '">' + data[i]["label"] + '</a></li>');
			}
			$("#secondMenu").html(html.join(""));
			
		},
		error: function(request){
			console.log("获取二级菜单列表异常");
		}
	});
}

//登录
function go2Login(){
	window.location.href = path + "/common/login.html" ;
}

//查看注册协议
function showAgree(){
	$.ajax({
		url: path + "/node/getNode.html",
		type: "post",
		dataType: "json",
		data:{
			nodeType:"zcxy"
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
//			$("#agreeContent").html(data["msg"]["templateContent"]);
//			$("#agreeTime").text(data["msg"]["createTime"].substring(0,10));
			$("#agreeContent").html(data["msg"][0]["body"]);
//			$("#agreeTime").text(data["msg"][0]["createTime"].substring(0,10));
			
			$("#xyBgpop").show();
			var al = (cv["winW"]-580)/2, at = (cv["winH"]-$(".agree_box").height())/2;
			$(".agree_box").css({"left":al+"px", "top":at+"px"}).show();
			$(".agree_close").click(function(){
				$("#xyBgpop").hide();
				$(".agree_box").fadeOut();
			});
		},
		error: function(request){
			console.log("获取协议范文异常");
		}
	});
}
//新窗口打开
function newWin(url, id) {
    var a = document.createElement('a');
    a.setAttribute('href', url);
    a.setAttribute('target', '_blank');
    a.setAttribute('id', id);
    // 防止反复添加
    if(!document.getElementById(id)) {                     
        document.body.appendChild(a);
    }
    a.click();
}
function windowOpen(url,target){
    var a = document.createElement("a");
    a.setAttribute("href", url);
    if(target == null){
        target = '';
    }
    a.setAttribute("target", target);
    document.body.appendChild(a);
    if(a.click){
        a.click();
    }else{
        try{
            var evt = document.createEvent('Event');
            a.initEvent('click', true, true);
            a.dispatchEvent(evt);
        }catch(e){
            window.open(url);
        }
    }
    document.body.removeChild(a);
}