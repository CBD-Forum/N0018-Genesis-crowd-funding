var loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7,window.location.search.indexOf("&backNo"));
var backNo = window.location.search.substring(window.location.search.indexOf("backNo=")+7,window.location.search.length);
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	if(userId == "null"){ //未登录
		window.location.href = path + "/common/m-login.html";
	}
	getAddress();
	getDetail();
//	$("#supportBtn").click(supportSub);
	$("#supportBtn").click(checkBindBankCard);
});
//获取收货地址
function getAddress(){
	var aArr = [], aStr = '', l;
	var provinece = '', city = '';
	$.ajax({
		url: path + "/address/getPostAddressList.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				AlertDialog.tip(data["msg"]);
				return false;
			}
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				aStr = '<div class="anaddress"><a class="c tjdz" href="'+path+'/common/m-entityAddress.html"><button>添加新地址</button></span></a></div>';
			}else{
				for(var i=0;i<l;i++){
					provinece = data[i]["provinceName"] ? data[i]["provinceName"] : "";
					city = data[i]["cityName"] ? data[i]["cityName"] : "";
					aArr.push('<li style="min-height:70px;">');
					aArr.push('<label style="color:#333;display:block;width:100%;">');
					aArr.push('<div class="clearfix add_list">');
					if(data[i]["isDefault"] == "1"){
						aArr.push('<input type="radio" class="fl" name="addRadio" checked="true" style="margin-top:8px;" addNo="'+data[i]["addressNo"]+'" />');
					}else{
						aArr.push('<input type="radio" class="fl" name="addRadio" style="margin-top:8px;"addNo="'+data[i]["addressNo"]+'" />');
					}
					if(data[i]["isDefault"] == "1"){
						aArr.push('<p class="fl" style="line-height:30px; margin-left:10px;">'+data[i]["postUser"]+'&nbsp;&nbsp;&nbsp;&nbsp;'+data[i]["mobile"]+'<span class="set"><a class="de_add" href="javascript:void(0);">默认地址</a></span></p>');
					}else{
						aArr.push('<p class="fl" style="line-height:30px; margin-left:10px;">'+data[i]["postUser"]+'&nbsp;&nbsp;&nbsp;&nbsp;'+data[i]["mobile"]+'</p>');
					}
					
					aArr.push('</div>');
					aArr.push('<p style="line-height:30px; margin-left:20px;">'+provinece+city+data[i]["adressDetail"]+'</p>');
					aArr.push('</label>');
					aArr.push('</li>');
     			}
				aArr.push('<div class="anaddress"><a class="c tjdz" href="'+path+'/common/m-entityAddress.html"><button>添加新地址</button></span></a></div>');
				aStr = aArr.join("");
			}
			$("#addressList").html(aStr);
		},
		error: function(request){
			console.log("获取收货地址异常");
		}
	});
}
//获取支持详情
function getDetail(){
	$.ajax({
		url: path + "/crowdfunding/getBackSetList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo": loanNo,
			"backNo": backNo
			},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"]["rows"][0];
			$("#supCover img").attr("src",cv["fileAddress"] + "/" +data["loanLogo"]).attr("alt",data["loanName"]);
			$("#supLoanName").text(data["loanName"]);
			$("#supContent").text(data["backContent"]);
			$("#supAmt").text("￥" + data["amt"]);
			$("#suphAmt").text("￥" + data["amt"]).attr("amt",data["amt"]);
//			if(data["transFee"] == 0){//免运费
//				$("#supTransFee").text("免运费");
//				$("#suphAmt").text("￥" + data["amt"]).attr("amt",data["amt"]);
//			}else{
//				$("#supTransFee").text("￥" + data["transFee"]);
//				$("#suphAmt").text("￥" + (data["amt"]+data["transFee"])).attr("amt",data["amt"]);
//			}
			if(data["backTime"] == 0){//免运费
				$("#supBackTime").text("项目成功结束后立即发送");
			}else{
				$("#supBackTime").text("项目成功结束后"+data["backTime"]+"天发送");
			}
		},
		error: function(request){
			console.log("获取筹资项目详情信息异常");
		}
	});
}
//支持众筹
function supportSub(){
	//提交支持前验证
	$.ajax({
		url: path + "/fundpool/invest/checkBeforeEntitySupport.html",
		type: "post",
		dataType: "json",
		async: false,
		data: {
			"supportAmt": $("#suphAmt").attr("amt"),
			"loanNo": loanNo,
			"backNo": backNo,
			"invstType": "commonInvest"
		},
		success: function(data){
			if(!data["success"]){
				AlertDialog.tip(data["msg"]);
			}else{
				//支持众筹验证通过
				$("#subSupportAmt").val($("#suphAmt").attr("amt"));
				$("#subLoanNo").val(loanNo);
				$("#subBackNo").val(backNo);
				$("#subPostAddressNo").val($("#addressList").find("input:checked").attr("addno"));
//				$("#supFormBtn").click();
				//支付
				$.ajax({
					url: path + "/fundpool/yeepay/pay/directBindPay.html",
					type: "post",
					dataType: "json",
					async: false,
					data: {
						"supportAmt":$("#subSupportAmt").val(),
						"loanNo":$("#subLoanNo").val(),
						"backNo":$("#subBackNo").val(),
						"cardTop":$("#cardTop").val(),
						"cardLast":$("#cardLast").val(),
						"payType":$("#payType").val(),
						"invstType":$("#invstType").val(),
						"postAddressNo":$("#subPostAddressNo").val()
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
					window.location.href = path+"/common/m-myCenter.html";
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

//检测是否绑定银行卡
function checkBindBankCard(){
	if($("#addressList li").length < 1){
		AlertDialog.mtip("请添加收货地址");
		return false;
	}else if($("#addressList input:checked").length == 0){
		AlertDialog.mtip("请选择收货地址");
		return false;
	}
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