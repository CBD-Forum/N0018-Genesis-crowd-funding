if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
var loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7,window.location.search.length);
var investPayWay =""; // 支付方式
var depositPay = 0;// 项目保证金比例
var isProjectPay = 0; // 0=非高端 ，>0高端
$(function(){
	// 返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	getCrowdDetail();// 获取股权详情
	getLeader(); // 获取领投人列表
	
	$("#fenshuNum").focus(function(){
		AlertDialog.hide();
	});
	$("#fenshuNum").blur(function(){
//		var  fsNum =/^\d+$/ ;
		var  fsContent = $("#fenshuNum").val();
		if(!isNaN(fsContent)){
			if( ($("#fenshuNum").val()> Number($("#remainsFenshu").text()) ) ||  ($("#fenshuNum").val() < 0 ) ){
				$("#fenshuNum").val("");
				AlertDialog.show("请输入大于0且小于剩余份数的值","fenshuNum",10,20);
				return false;
			}
		}else{
			AlertDialog.show("请输入非负整数","fenshuNum",10,20);
			$("#fenshuNum").val("");
			return false;
		}
	})
	// 加一份
	$("#iJIa").unbind("click").click(function(){
		var fsNum = Number($("#remainsFenshu").text()), jNum = 0;
		if(Number($("#fenshuNum").val()) == fsNum){ // 超出剩余份数
			$("#iJIa").css("color", "#ccc");
			return false;
		}else{
			jNum = Number($("#fenshuNum").val()) + 1;
			$("#fenshuNum").val(jNum);
			if(jNum > 1){
				$("#iJian").css("color", "#333");
			}
			var tzze = multiplication(Number($("#miniInvestAmt1").attr("num")) , Number($("#fenshuNum").val()));
			$("#fundAmt4").text(tzze).attr("num", tzze) ;
			// 投资总额 / (融资总额 - 项目方出资) * 出让股份
			$("#couInvestZgbl").text((Number($("#fundAmt4").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))/100).toFixed(2));
			if(investPayWay == "depositPay"){
				$("#paywayAmt").text(multiplication(parseFloat($("#fundAmt4").attr("num")),parseFloat($("#depositPayEm").attr("num"))));
			}else{
				$("#paywayAmt").text($("#fundAmt4").attr("num"));
			}
		}
	});
	// 减一份
	$("#iJian").unbind("click").click(function(){
		var fsNum = Number($("#remainsFenshu").text()), jNum = 0;
		if(Number($("#fenshuNum").val()) < 2){ // 输入框内只剩0份
			$("#iJian").css("color", "#CCC");
			return false;
		}else{
			jNum = Number($("#fenshuNum").val()) - 1;
			$("#fenshuNum").val(jNum);
			if(jNum < fsNum){
				$("#iJIa").css("color", "#333");
			}
			var tzze = multiplication(Number($("#miniInvestAmt1").attr("num")) , Number($("#fenshuNum").val()));
			$("#fundAmt4").text(tzze).attr("num", tzze) ;
			// 投资总额 / (融资总额 - 项目方出资) * 出让股份
//			$("#couInvestZgbl").text(formatCurrency(Number($("#fundAmt4").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))/100));
			$("#couInvestZgbl").text((Number($("#fundAmt4").attr("num"))/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))/100).toFixed(2));
			if(investPayWay == "depositPay"){
				$("#paywayAmt").text(multiplication(parseFloat($("#fundAmt4").attr("num")),parseFloat($("#depositPayEm").attr("num"))));
			}else{
				$("#paywayAmt").text($("#fundAmt4").attr("num"));
			}
		}
	});
	$("#investLBtn").unbind("click").click(pay);
});
// 跟投前验证
function pay(){
	AlertDialog.hide();
	var pay = $("input[name='pay']:checked").attr("id");
	
	if(MValify.isNull($("#fenshuNum").val(),"fenshuNum",10,20)){ //添加认购份数
		if(MValify.isNull($("#investValiInput").val(),"investValiInput",10,20)){
			$.ajax({
				url: path + "/fundpool/invest/checkBeforeEntitySupport.html",
				type: "post",
				dataType: "json",
				async: false,
				data: {
					"buyNum": $("#fenshuNum").val(),
					"loanNo": loanNo,
					"investType": "followInvest",
					"leadInvestor": $("#ltrList input:checked").attr("id"),
					"actualPayAmt":$("#paywayAmt").text(),
					"supportAmt":$("#fundAmt4").text(),
					"valiCode": $("#investValiInput").val(),
					"payWay":investPayWay
				},
				success: function(data){
					if(!data["success"]){
//						$("#investLastDiv").hide();
//						AlertDialog.tip(data["msg"]);
						$("#imageInvest").attr("src", path + "/security/securityCodeImage.html?" + new Date().getTime());
						AlertDialog.show(data["msg"], "investValiInput", 10, 20);
					}else{
						$("#investTip").hide();
						$("#formBuyNum").val($("#fenshuNum").val());
						$("#formLoanNo").val(loanNo);
						$("#formPayType").val(investPayWay);
						$("#supFomr").find("input[name='investType']").val("followInvest"); // 跟投改变值
						$("#supFomr").find("input[name='leadInvestor']").val($("#ltrList>dd.a_home").attr("id"));
						// $("#supFormBtn").click();
						checkBindBankCard();
						
					}
				},
				error: function(request){
					console.log("验证领投请求异常");
				}
			});
		}
	}
}

//支付
function directBindPay(){
	$.ajax({
		url: path + "/fundpool/yeepay/pay/directBindPay.html",
		type: "post",
		dataType: "json",
		async: false,
		data: {
			"cardTop":$("#cardTop").val(),
			"cardLast":$("#cardLast").val(),
			"buyNum":$("#formBuyNum").val(),
			"loanNo":$("#formLoanNo").val(),
			"actualPayAmt":$("#paywayAmt").text(),
			"supportAmt":$("#formSupportAmt").val(),
			"investType":$("#firstInves").val(),
			"payWay":$("#formPayType").val(),
			"leadInvestor":$("#ltrList input:checked").attr("id"),
			"payType":$("#payType").val()
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

// 获取项目详情信息
function getCrowdDetail(){
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
			$("#financeNum").val(data["financeNum"]);
			$("#buyNum").val(data["buyNum"]);
			
			investPayWay = data["investPayWayCode"]; 
			depositPay = data["loanDeposit"]; // 保证金比例
			
			isProjectPay = data["projectFinanceAmt"];
			if(isProjectPay > 0){
				$("#bonusesDiv").hide();
			}
			if(data["miniInvestAmt"]){
				$("#miniInvestAmt1").text(data["miniInvestAmt"]).attr("num", data["miniInvestAmt"]);
			}else{
				$("#miniInvestAmt1").text(0).attr("num", 0);
			}
			$("#remainsFenshu").text(Number($("#financeNum").val()) - Number($("#buyNum").val()));// 剩余份数
			$("#fundAmt").val(formatCurrency(data["fundAmt"])).attr("num", data["fundAmt"]);
			$("#fundAmt4").text($("#miniInvestAmt1").text());
			$("#fundAmt1").val(fundAmt).attr("num", data["fundAmt"]);
			$("#projectFinanceAmt").val(data["projectFinanceAmt"]).attr("num", data["projectFinanceAmt"]);
			$("#qtyxhhr_zgbl").val((data["investBonusRatio"]*100).toFixed(2)).attr("num", (data["investBonusRatio"]*100).toFixed(2)).attr("all", (data["investBonusRatio"]*100).toFixed(2)); // 投资人（其他有限合伙人）份额占股比例
			$("#depositPayEm").text(multiplication(parseFloat(depositPay),100)).attr("num",depositPay);
			if(investPayWay == "depositPay"){
				investPayWay = $("#payWay input[checked=checked]").attr("id");
				$("#paywayAmt").text(multiplication(parseFloat($("#fundAmt4").text()),parseFloat($("#depositPayEm").attr("num"))));
				$("#payWay").show();
			}else{
				$("#payWay").hide();
				$("#paywayAmt").text($("#fundAmt4").text());
			}
			setTimeout(function(){
				$("#couInvestZgbl").text((Number($("#fundAmt4").text())/(Number($("#fundAmt1").attr("num"))-Number($("#projectFinanceAmt").attr("num")))*Number($("#qtyxhhr_zgbl").attr("all"))/100).toFixed(2));		
			},500);
		},
		error: function(request){
			console.log("获取股权详细信息异常");
		}
	});
	$("#payWay input[name=pay]").click(function(){
		investPayWay = $(this).attr("id");
		if(investPayWay == "depositPay"){
			investPayWay = $("#payWay input[checked=checked]").attr("id");
			$("#paywayAmt").text(multiplication(parseFloat($("#fundAmt4").text()),parseFloat($("#depositPayEm").attr("num"))));
			$("#payWay").show();
		}else{
			$("#paywayAmt").text($("#fundAmt4").text());
		}
	});
};
// 领投人列表
function getLeader(){
	var ltArr=[],ltStr="",l;
	// 获取加载领投人列表
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
				ltStr ='<div class="nodata">暂无数据</div>';
				$("#ltrList").html(ltStr);
				$("#investLBtn").hide();
				$("#gtInvestBtn").show();
				return false;
			}
			for(var i=0;i<l;i++){
				ltArr.push('<div class="clearfix">');
				ltArr.push('<label>');
				ltArr.push('<div class="img fl">');
				if(i==0){
					ltArr.push('<input id="'+data[i]["leadInvestor"]+'" name="ltr" type="radio" checked="checked" />');
				}else{
					ltArr.push('<input id="'+data[i]["leadInvestor"]+'" name="ltr" type="radio" />');
				}
				if(data[i]["photo"]){
					ltArr.push('<img src="'+cv["fileAddress"]+'/'+data[i]["photo"]+'" />');
				}else{
					ltArr.push('<img src="'+path+'/images/defaultPhoto.png" />');
				}
				ltArr.push('</div>');
				ltArr.push('<div class="info fl">');
				ltArr.push('<p class="p1">'+data[i]["companyName"]+'</p>');
				ltArr.push('<p class="p2">认投金额：<span>￥'+data[i]["totalSupportAmt"]+'</span></p>');
				ltArr.push('</div>');
				ltArr.push('</label>');
				ltArr.push('</div>');
			}
			ltStr = ltArr.join("");
			$("#ltrList").html(ltStr);
		},
		error: function(request){
			console.log("获取领头人列表异常！");
		}
	});
}

/*
 * __________________________________ 支付
 * __________________________________________
 */
// 检测是否绑定银行卡
function checkBindBankCard(){
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
				// 显示支付银行选择框
				var obj = $("#selCard");
				var t = (cv.winH - obj.height())/2;
				var l = (cv.winW - obj.width())/2;
				$(".bgpop").fadeIn();
				obj.css({"top":t+"px","left":l+"px"}).fadeIn();
				// 关闭支付银行选择框
				$("#selCard a.close").unbind().click(function(){
					AlertDialog.hide();
					$(".bgpop").fadeOut();
					obj.fadeOut();
				});
				$("#selBank").attr("disabled",false);
				$("#sendBtn").unbind().click(sentSMS).text("获取验证码");// 发送验证码
				$("#okPay").unbind().click(checkPhoneCode).text("确认支付");// 检测码证码
				$("#phoneCode").val("");
			}else{
				AlertDialog.mtip("您还没有绑定银行卡不可支付！",function(){
					window.location.href = path + "/common/m-createBankCard.html";
				});
			}
		},
		error:function(){
			
		}
	});
}

// 发送支付验证短信
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
				$("#cardTop").val($("#selBank option:checked").attr("top"));
				$("#cardLast").val($("#selBank option:checked").attr("last"));
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
	$("#sendBtn").text("获取验证码").css({"color":"#FFF", "background":"#248af9"});
	$("#sendBtn").click(sentSMS);
}

// 检测支付验证码
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
				directBindPay();
			}else{
				AlertDialog.mtip(data["msg"]);
				$("#selCard").hide();
				return;
			}
		},
		error: function(request){
			console.log("验证支付验证码异常");
		}
	});
}

//检测是否支付
function checkPay(orderId){
	var bool = false;
	$.ajax({
		url: path + "/fundpool/yeepay/pay/checkOrderState.html",
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

