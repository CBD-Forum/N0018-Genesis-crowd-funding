if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-login.html";
}
var pageNum = 1; //分页
var eventObj = null;
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	changeTab(); 
	$("#zfwkDiv").width($(window).width()-($(window).width()/10));
	$("#zfdjDiv").width($(window).width()-($(window).width()/10));
	$("#loanPage").click(function(){
		getMyCrowdfundList(pageNum); //获取我发起的项目
	});
});
function changeTab(){
	var $typeTab = $("#loanType li"),code="";
	$.each($typeTab,function(k,v){
		$(v).click(function(){
			$typeTab.removeClass("cur");
			$(v).addClass("cur");
			$("#loanType").hide();
			$("#loanTypeA").attr("type","0");
			code = $(this).attr("code");
			getShowLoanProcess(code); //获取相关的项目状态
		});
	});
	
	$("#loanTypeA").click(function(){
		if($(this).attr("type") == "0"){
			$(this).addClass("cur");
			$("#loanType").show();
			$(this).attr("type","1");
		}else{
			$(this).removeClass("cur");
			$("#loanType").hide();
			$(this).attr("type","0");
		}
		$("#loanProcess").hide();
		$("#loanProcessA").removeClass("cur").attr("type","0");
	});
	
	$("#loanProcessA").click(function(){
		if($(this).attr("type") == "0"){
			$(this).addClass("cur");
			$("#loanProcess").show();
			$(this).attr("type","1");
		}else{
			$(this).removeClass("cur");
			$("#loanProcess").hide();
			$(this).attr("type","0");
		}
		$("#loanTypeA").removeClass("cur").attr("type","0");
		$("#loanType").hide();
	});
	
	$typeTab.first().click();
	
}
//获取相关的项目状态
function getShowLoanProcess(code){
	var pStr = "";
	if(code == "public_service" || code == "entity"){
		pStr = "";
		pStr += '<li name="submit">已提交</li>';
		pStr += '<li name="funding">筹款中</li>';
		pStr += '<li name="lended">待发货</li>';
		pStr += '<li name="end">完成</li>';
	}else if(code == "stock" || code == "house"){
		pStr = "";
		pStr += '<li name="submit">已提交</li>';
		pStr += '<li name="re_passed">预热申请</li>';
		pStr += '<li name="preheat">预热中</li>';
		pStr += '<li name="funding">筹款中</li>';
		pStr += '<li name="lended">待分红</li>';
		pStr += '<li name="end">完成</li>';
	}else if(code == ""){
		pStr = "";
		pStr += '<li name="submit">已提交</li>';
		pStr += '<li name="re_passed">预热申请</li>';
		pStr += '<li name="preheat">预热中</li>';
		pStr += '<li name="funding">筹款中</li>';
		pStr += '<li name="lended">待分红/发货</li>';
		pStr += '<li name="end">完成</li>';
	}
//	else if(code == "house"){
//		pStr = "";
//		pStr += '<li name="submit">已提交</li>';
//		pStr += '<li name="funding">筹款中</li>';
//		pStr += '<li name="lended">带分红</li>';
//		pStr += '<li name="end">完成</li>';
//	}
	$("#loanProcess").html(pStr);
	var $processType = $("#loanProcess li");
	$.each($processType,function(k,v){
		$(v).click(function(){
			$processType.removeClass("cur");
			$(v).addClass("cur");
			$("#loanProcess").hide();
			$("#loanProcessA").attr("type","0");
			pageNum = 1;
			getMyCrowdfundList(pageNum); //获取我发起的项目
		});
	});
	$processType.first().click();
}
//获取我发起的项目
function getMyCrowdfundList(page){
	var loanType = "",loanState="",lArr = [],lStr = "",total = 0,l = 0;
	loanType = $("#loanType li.cur").attr("code");
	loanState = $("#loanProcess li.cur").attr("name");
	$("#loanBody>div").hide();
	$("#"+loanType+"Body").show();
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
			l =  data["msg"]["rows"].length,total = data["msg"]["total"],data = data["msg"]["rows"];
			if(l == 0){
				lStr = '<div style="line-height:80px; text-align:center; background:#fff; font-size:16px; color:red;">暂无数据</div>';
				$("#"+loanType+"Body").html(lStr);
				$("#loanPage").hide();
				return false;
			}
			if(loanState=="re_passed"){ //预热申请
				for(var i =0;i<l;i++){
					lArr.npush('<ul>');
					lArr.npush('<li><span>项目名称：</span>'+data[i]["loanName"]+'</li>');
					lArr.npush('<li><span>待付金额：</span>￥'+data[i]["prepayAmt"].toFixed(2)+'</li>');
					lArr.npush('<li><span>目标金额：</span>￥'+data[i]["fundAmt"].toFixed(2)+'</li>');
					lArr.npush('<li><span>发布日期：</span>'+data[i]["createTime"].substring(0, 10)+'</li>');
					lArr.npush('<li><span>项目状态：</span>'+data[i]["loanStateName"]+'</li>');
					if(loanType == "stock"|| loanType == "house"){
						if(Number(data[i]["prepayAmt"]) > 0 && Number(data[i]["isPayFirstAmt"]) < 1){ //显示支付订金按钮 
							lArr.npush('<li><span style="margin-left:29px;">操作：</span><a href="javascript:void(0);" type="zfdj" onclick="stockZfdj(this);" zprepayAmt="'+data[i]["prepayAmt"]+'" zprojectFinanceAmt="'+data[i]["projectFinanceAmt"]+'" zloanNo="'+data[i]["loanNo"]+'">支付订金</a></li>');
						}else{
							lArr.npush('<li><span style="margin-left:29px;">操作：</span>--</li>');
						}
					}
					lArr.npush('</ul>');
				}
			}else if(loanState=="preheat"){
				for(var i =0;i<l;i++){
					lArr.npush('<ul>');
					lArr.npush('<li><span>项目名称：</span>'+data[i]["loanName"]+'</li>');
					lArr.npush('<li><span>目标金额：</span>￥'+data[i]["fundAmt"].toFixed(2)+'</li>');
					if(!data[i]["releaseTime"]){
						lArr.npush('<li><span>发布日期：</span>--</li>');
					}else{
						lArr.npush('<li><span>发布日期：</span>'+data[i]["releaseTime"].substring(0, 10)+'</li>');
					}
					if(!data[i]["fundEndTime"]){
						lArr.npush('<li><span>截止日期：</span>--</li>');
					}else{
						lArr.npush('<li><span>截止日期：</span>'+data[i]["fundEndTime"]+'</li>');
					}
					lArr.npush('<li><span>尾款金额：</span>￥'+data[i]["remainPrepayAmt"].toFixed(2)+'</li>');
					lArr.npush('<li><span>项目状态：</span>'+data[i]["loanStateName"]+'</li>');
					lArr.npush('<li><span>领头人数：</span>'+data[i]["leaderNum"]+'</li>');
					if(loanType == "entity" || loanType == "public_service"){
						lArr.npush('<li><span style="margin-left:29px;">操作：</span>发货</li>');
					}
					if(loanType == "stock"){
						if(data[i]["isPayLastAmt"] == "0" && Number(data[i]["remainPrepayAmt"])>0){
							lArr.push('<li class="wz"><span>操作：</span><a style="margin-left:10px;" href="javascript:void(0);" type="zfwk" onclick="stockZfwk(event);" zremainPrepayAmt="'+data[i]["remainPrepayAmt"]+'" zprojectFinanceAmt="'+data[i]["projectFinanceAmt"]+'" zloanNo="'+data[i]["loanNo"]+'" zleaderNum="'+data[i]["leaderNum"]+'">支付尾款</a></li>');
						}else{
							if(data[i]["isPayLastAmt"] == "0"){
								lArr.push('<li class="wz"><span>操作：</span>--</li>');
							}else{
								lArr.push('<li class="wz"><span>操作：</span>已支付</li>');
							}
						}
					}
					lArr.npush('</ul>');
				}
			}else{
				for(var i =0;i<l;i++){
					lArr.npush('<ul>');
					lArr.npush('<li><span>项目名称：</span>#0</li>',[data[i]["loanName"]]);
					lArr.npush('<li><span>目标金额：</span>￥'+data[i]["fundAmt"].toFixed(2)+'</li>');
					if(!data[i]["releaseTime"]){
						lArr.npush('<li><span>发布日期：--</span></li>');
					}else{
						lArr.npush('<li><span>发布日期：</span>'+data[i]["releaseTime"].substring(0, 10)+'</li>');
					}
					if(!data[i]["fundEndTime"]){
						lArr.npush('<li><span>截止日期：</span>--</li>');
					}else{
						lArr.npush('<li><span>截止日期：</span>'+data[i]["fundEndTime"]+'</li>');
					}
					lArr.npush('<li><span>已投金额：</span>￥'+data[i]["approveAmt"].toFixed(2)+'</li>');
					lArr.npush('<li><span>项目状态：</span>'+data[i]["loanStateName"]+'</li>');
					if(loanState == "lended"){ //待发货 | 待分红
						if(loanType == "entity" || loanType == "public_service"){
							lArr.npush('<li><span style="margin-left:29px;">操作：</span><a href="'+path+'/common/m-enterLogico.html?loanNo='+data[i]["loanNo"]+'">发货</a></li>');
						}
					}else{
						lArr.npush('<li><span style="margin-left:29px;">操作：</span>--</li>');
					}
					lArr.npush('</ul>');
				}
			}
			lStr = lArr.join('');
			if(page == 1){
				$("#"+loanType+"Body").html(lStr);
			}else{
				$("#"+loanType+"Body").append(lStr);
			}
			if($("#"+loanType+"Body ul").length == total){
				$("#loanPage").hide();
			}else{
				pageNum++;
				$("#loanPage").show();
			}
		},
		error: function(request){
			console.log("获取发起项目列表异常");
		}
	});
}
//预购订金弹框
function stockZfdj(obj){
//	var obj = event.srcElement || event.target;
	var prepayAmt = $(obj).attr("zprepayAmt"), projectFinanceAmt = $(obj).attr("zprojectFinanceAmt"); //发起人首付款  项目方出资金额
	var loanNo = $(obj).attr("zloanNo");
	var tl = (cv["winW"]-$("#zfdjDiv").width())/2, tt = (cv["winH"]-300)/2;
	$("#bgpop").show();
	$("#zfdjDiv").css({"left":tl+"px", "top":tt+"px"}).show();
	$("#zfdjAmt").text(Number(prepayAmt).toFixed(2)); 
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
	$("#payZfdjBtn").click(function(){
//		$("#zfdjFormBtn").click();
//		$("#zfdjDiv").hide();
//		$("#bgpop").hide();
		$("#zfdjDiv").hide();
		checkBindBankCard(obj);
	});
}
//支付尾款弹框
function stockZfwk(event){
	var obj = event.srcElement || event.target;
	var remainPrepayAmt = $(obj).attr("zremainPrepayAmt"), projectFinanceAmt = $(obj).attr("zprojectFinanceAmt"); //发起人首付款  项目方出资金额
	var loanNo = $(obj).attr("zloanNo"), leaderNum = $(obj).attr("zleaderNum");
	var tl = (cv["winW"]-$("#zfwkDiv").width())/2, tt = (cv["winH"]-300)/2;
	$("#bgpop").show();
	$("#zfwkDiv").css({"left":tl+"px", "top":tt+"px"}).show();
	
	//弹框内赋值
	$("#t_leaderNum").text(leaderNum);
	$("#zfwkAmt").text(Number(remainPrepayAmt).toFixed(2));  //支付尾款
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
	$("#payZfwkBtn").click(function(){
//		$("#zfwkFormBtn").click();
//		$("#zfwkDiv").hide();
//		$("#bgpop").hide();
		$("#zfwkDiv").hide();
		checkBindBankCard(obj);
		//进行支付 
//		$.ajax({
//			url: path + "/fundpool/yeepay/pay/directCenterBindPay.html",
//			type: "post",
//			dataType: "json",
//			async: false,
//			data: {
//				"cardTop":$("#zfwkForm input[name='cardTop']").val(),
//				"cardLast":$("#zfwkForm input[name='cardLast']").val(),
//				"supportAmt":$("#zfwkForm input[name='supportAmt']").val(),
//				"loanNo":$("#zfwkForm input[name='loanNo']").val(),
//				"payNode":$("#zfwkForm input[name='payNode']").val(),
//				"payType":$("#zfwkForm input[name='payType']").val()
//			},
//			success:function(data){
//				if(data["orderId"]){
//					$("#okPay").text("支付中...").unbind();
//					$("#sendBtn").unbind();
//					var i = 0;
//					var obj = setInterval(function(){
//						if(checkPay(data["orderId"]) == true || i == 20){
//							clearInterval(obj);// 取消
//							if(i == 20){
//								$("#selCard a.close").click();
//								AlertDialog.mtip("支付失败！",function(){
//									window.location.href = window.location.href;
//								});
//							}
//						}
//						i++;
//					},3000);
//				}else{
//					AlertDialog.mtip(data["msg"]);
//				}
//			},
//			error:function(){
//				
//			}
//		});
	});
}
/*__________________________________ 支付 __________________________________________*/
//检测是否绑定银行卡
function checkBindBankCard(obj1){
	eventObj = obj1;
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
//					var tempURL = Base64Util.encode(window.location.pathname + window.location.search);
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
	$("#sendBtn").text("获取验证码").css({"color":"#FFF", "background":"#e3423c","color":"#fff"});
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
//				stockZfdj(eventObj);
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
	if($(eventObj).attr("type") == "zfdj"){
		stockZfdjCode();
	}else if($(eventObj).attr("type") == "zfwk"){
		stockZfwkCode();
	}
}

function stockZfdjCode(){
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
function stockZfwkCode(){
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
							$("#bgpop").show();
							AlertDialog.mtip("支付失败！",function(){
								window.location.href = window.location.href;
								$("#bgpop").hide();
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
//检测是否支付
function checkPay(orderId){
	var bool = false;
	$.ajax({
		url: path + "/fundpool/yeepay/pay/checkLoanpayOrderState.html",
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
