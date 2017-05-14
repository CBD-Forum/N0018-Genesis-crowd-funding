var loanNo= getQueryString('loanNo');
var transferNo = getQueryString('transferNo');
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	checkTab();
	getCrowdDetail();//项目详情
	getTransFerDetail(); //项目详情
});
//项目分类选择
function checkTab(){
	var $tab = $("#detailTab li");
	$.each($tab,function(k,v){
		$(v).click(function(){
			var code  = $(this).attr("name");
			$tab.find("a").removeClass("cur");
			$(this).find("a").addClass("cur");
			$("#detailBody>div").hide();
			$("#"+code+"Body").show();
		});
	});
	$tab.first().click();
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
				$("#xs_video").html('<embed width="100%" height="355" src="'+data["loanVideo"]+'" allowFullScreen="true" value="transparent" quality="high" align="middle" wmode="Opaque"  mode="transparent" type="application/x-shockwave-flash"></embed>'); //视频
			}else{
				$("#xs_video").hide();
			}
			$("#superIndustry").text(data["superIndustryName"]); //行业
			$("#address").text(data["loanProvinceName"] + data["loanCityName"]);
			$("#proInfoContent").html(data["loanDetail"]);//项目介绍
			$("#proBudgetContent").html(data["financeBudget"]); //项目预算
			$('#release_time').html(data["createTime"])
			//电子协议下载
			if(data["loanContract"]){
				$('#eContractDownload').attr('href',cv.fileAddress+data["loanContract"]);
			}
		},
		error: function(request){
			console.log("获取股权详细信息异常");
		}
	});
}
//项目详情
function getTransFerDetail(){
	$.ajax({
		url:path+'/crowdfundingInvestTransfer/getCrowdfundTransferDetail.html',
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
			var data = data["msg"];
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
			$("#supportRatio1").text((progress*100).toFixed(0)+"%");
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
			BuyCompetence(); //获取用户信息
		},
		error:function(request){
			console.log("获取挂牌信息异常");
		}
	});
}
//获取用户信息
function BuyCompetence(){
	$.ajax({
		url:path+'/crowdfundUserCenter/getCrowdfundUserDetail.html',
		type:'post',
		dataType: "json",
		success:function(data){
			if(!data['success']){
				return false
			}
			var all = $("#financeNum").attr('num');
			var buy = $("#buyNum1").attr('num'); 
			if(isNaN(Number(all)) || isNaN(Number(all)) || Number(all) == Number(buy)){
				$("#investLastBtn").css({
					"backgroundColor":"#D8D3D3"
				});
				$("#investLastBtn").unbind("click");
				$("#investLastBtn,#buttonDiv").show();
				return false;
			}
			if(data["msg"]["investAuthState"] == "passed" || data["msg"]["leadAuthState"] == "passed"){
				$("#investLastBtn").click(investLast);
				$("#investLastBtn,#buttonDiv").show();
			}else{
				$("#investLastBtn").css({
					"backgroundColor":"#D8D3D3"
				}).text("请先进行认证");
				$("#investLastBtn").unbind("click");
				$("#investLastBtn,#buttonDiv").show();
			}
		},
		error:function(request){
			console.log("获取用户信息异常");
		}
	});
}
//购买
function investLast(){
	if(userId == "null"){
		window.location.href=path+"/common/m-login.html";
		return false;
	}
	$("#investValiInput").val("");
	$("#bgpop").show();
	var it = (cv["winH"]-410)/2, il = (cv["winW"]-300)/2;
	$("#investLastDiv").css({"top": it+"px", "left": il+"px"}).show();
	//关闭
	$("#bgpop").click(function(){
		$("#bgpop").hide();
		$("#investLastDiv").hide();
		$("#selCard").hide();
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
		AlertDialog.hide();
		if(Valify.valiCode2($("#investValiInput").val(),false,"investValiInput")){
			$.ajax({
				url: path + "/fundpool/yeepay/pay/queryAuthbindList.html",
				type: "post",
				dataType: "json",
				success:function(data){
					if(data["cardlist"] != "[]"){
						$("#investLastDiv").hide();
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
						$("#investLastDiv").hide();
						AlertDialog.mtip("您还没有绑定银行卡不可支付！",function(){
							window.location.href = path + "/common/m-createBankCard.html";
						});
					}
				},
				error:function(){
					
				}
			});
			
		}
		
//		$.ajax({
//			url: path + "/fundpool/transfer/checkBeforeBuyTransfer.html",
//			type: "post",
//			dataType: "json",
//			async: false,
//			data: {
//				transferNo:transferNo,
//				buyParts:$("#fenshuNum").val(),
//				valiCode:$("#investValiInput").val()
//			},
//			success: function(data){
//				if(!data["success"]){
//					AlertDialog.tip(data["msg"]);
//					$("#bgpop").hide();
//					$("#investLastDiv").hide();
//					$("#tip_div").hide();
//					return false;
//				}else{
//					$("#hideTransferNo").val(transferNo);
//					$("#hideBuyParts").val($("#fenshuNum").val());
//					$("#submitTransferBtn").click();
//				}
//			},
//			error: function(request){
//				console.log("验证领投请求异常");
//			}
//		});
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
				$("#cardTop").val($("#selBank option:checked").attr("top"));
				$("#cardLast").val($("#selBank option:checked").attr("last"));
				$("#sendBtn").unbind("click").text("60秒后发送").css({"color":"#CCC"});
				countDown(60, "sendBtn", bOverFn);
			}
		},
		error: function(request){
			$("#sendBtn").text("获取验证码").css({"color":"#e3423c"});
			$("#sendBtn").click(sentSMS);
			console.log("发送绑定银行卡信息异常");
		}
	});
}

function bOverFn(){
	$("#sendBtn").text("获取验证码").css({"color":"#FFF", "background":"#e3423c"});
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
				supportSub();
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
//支持众筹
function supportSub(){
	//提交支持前验证
	$.ajax({
		url: path + "/fundpool/yeepay/pay/checkBeforeBuyTransfer.html",
		type: "post",
		dataType: "json",
		async: false,
		data: {
			"supportAmt": $("#fundAmt4").text(),
			"transferNo": transferNo,
			"invstType": "buyTransferPay",
			"buyParts":$("#fenshuNum").val()
		},
		success: function(data){
			if(!data["success"]){
				$("#selCard").hide();
				AlertDialog.tip(data["msg"]);
			}else{
				//支持众筹验证通过
				$("#subSupportAmt").val($("#suphAmt").attr("amt"));
				$("#subLoanNo").val(transferNo);
				AlertDialog.hide();
				//支付
				$.ajax({
					url: path + "/fundpool/yeepay/pay/directTransferPayByWap.html",
					type: "post",
					dataType: "json",
					async: false,
					data: {
						"supportAmt":$("#subSupportAmt").val(),
						"transferNo":$("#subLoanNo").val(),
						"cardTop":$("#cardTop").val(),
						"cardLast":$("#cardLast").val(),
						"payType":"buyTransferPay",
						"invstType":$("#invstType").val(),
						"buyParts":$("#fenshuNum").val()
					},
					success:function(data){
						if(data["orderId"]){
							$("#okPay").text("支付中...").unbind();
							$("#sendBtn").unbind();
							var i = 0;
							var obj = setInterval(function(){
								if(checkPay(data["orderId"]) == true || i == 20){
									clearInterval(obj);//取消
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
		},
		error: function(request){
			console.log("获取用户余额异常");
		}
	});
}
//检测是否支付
function checkPay(orderId){
	var bool = false;
	$.ajax({
		url: path + "/fundpool/yeepay/pay/checkTransferOrderState.html",
		type: "post",
		dataType: "json",
		async: false,
		data: {
			"orderId":orderId
		},
		success:function(data){
			AlertDialog.hide();
			if(data["state"] == "payed"){//成功
				$("#selCard a.close").click();
				AlertDialog.mtip(data["msg"],function(){
					window.location.href = path+"/common/m-myCenter.html";
				});
				bool = true;
			}else if(data["state"] == "fail"){//失败
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