if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	getUserAccount();//获取个人账户
	getUserBank();//查询绑定的银行卡
	$("#withdrawInput").keyup(function(){checkWithAmt()});//提出金额输入判断-手续费-实际到账金额计算

	$("#getVertify").click(getWithVertify);//获取短信验证码
	$("#withdrawBtn").unbind("click").click(userWithdraw);//提出前验证
	
});
//查询绑定的银行卡
function getUserBank(){
	var bArr = [], bStr = '', l;
	$.ajax({
		url: path + "/bank/getUserBank.html",
		type: "post",
		dataType: "json",
		success: function(data){
			l = data["msg"].length, data = data["msg"];
			if(l == 0){
				bArr.push('<div class="clearfix yh_num yh_num2 fl">');
				bArr.push('<a class="jia" href="'+path+'/common/bankAdd.html" style="background:none;">');
				bArr.push('<span class="col9" style="margin-left:-20px;">添加银行卡</span>');
				bArr.push('</a>');
				bArr.push('</div>');
				bStr = bArr.join("");
				$("#bankCard_tab").html(bStr);
				return false;
			}
			for(var i=0;i<l;i++){
				bArr.push('<div class="clearfix yh_num fl" bankNo="'+data[i]["bankAccount"]+'">');
				bArr.push('<div class="fl">');
				if(data[i]["bankShortName"]){
					bArr.push('<img src="'+path+'/images/letv/bank/'+data[i]["bankShortName"]+'.jpg" width="120">');
				}else{
					bArr.push('<img src="'+path+'/images/letv/bank/default.jpg" width="120">');
				}
				bArr.push('</div>');
				bArr.push('<span class="fr">尾号 '+data[i]["bankAccount"].substr(data[i]["bankAccount"].length-4)+'</span>')
				bArr.push('</div>');
			}
			if(l<5){
				bArr.push('<div class="clearfix yh_num yh_num2 fl">');
				bArr.push('<a class="jia" href="'+path+'/common/bankAdd.html" style="background:none;">');
				bArr.push('<span class="col9" style="margin-left:-20px;">添加银行卡</span>');
				bArr.push('</a>');
				bArr.push('</div>');
			}
			bStr = bArr.join("");
			$("#bankCard_tab").html(bStr);
			
			var $tab = $("#bankCard_tab>div");
			$.each($tab,function(k,v){
				$(v).click(function(){
					$tab.removeClass("cur");
					$(this).addClass("cur");
				});
			});
			$tab.first().click();
		},
		error: function(request){
			console.log("获取绑定的银行卡异常");
		}
	});
}
//提出
function userWithdraw(){
	if(Valify.isNull($("#bankCard_tab .cur").attr("bankNo"), "bankCard_tab", -20, 900)){
		if(checkWithAmt()){
			if(Valify.isNull($("#phoneCode").val(), "phoneCode", 10, 20)){
				//提出前验证
				$.ajax({
					url: path + "/withdraw/checkWithdraw.html",
					type: "post",
					dataType: "json",
					data: {
						"bankCardId": $("#bankCard_tab .cur").attr("bankNo"),
						"actualMoney": $("#withdrawInput").val(),
						"amt": $("#withdrawAmt").text(),
//						"feeType": $("#db").prop("checked") ? "perFee": "ratioFee",
						"verifyCode": $("#phoneCode").val()
					},
					success: function(data){
						if(!data["success"]){
							AlertDialog.tip(data["msg"]);
							return false;
						}
						//提出
						$.ajax({
							url: path + "/withdraw/saveWithdrawOrder.html",
							type: "post",
							dataType: "json",
							data: {
								"bankCardId": $("#bankCard_tab .cur").attr("bankNo"),
								"actualMoney": $("#withdrawInput").val(),
								"amt": $("#withdrawAmt").text(),
							},
							success: function(data){
								if(!data["success"]){
									AlertDialog.tip(data["msg"]);
									return false;
								}else{
									$(".sbgpop").hide();
									AlertDialog.hide();
									$("#bgpop_wait").show();
						        	var ttime = 1;
									var time = 1000;
								    var interval = setInterval(function(){
							        	$.ajax({
							        		url: path + "/blockChainQuery/selectQueryIsSuccess.html",
							        		type: "get",
							        		dataType: "json",
							        		data: {
							        			"requestId":data["requestId"]
							        		},
							        		success: function(data){
							        			if(ttime > 100){
							        				AlertDialog.tip("提出申请失败!");
							    					$("#bgpop_wait").hide();
							        				clearInterval(interval);
							        				setTimeout(function(){
														window.location.href = path + "/common/myCenter.html";
							        				},2000);
							        				$("#alertSure").click(function(){
														window.location.href = path + "/common/myCenter.html";
							        				});
							        				return false;
							        			}
							        			if(data["success"]){
							        				AlertDialog.tip("提出申请成功!");
							    					$("#bgpop_wait").hide();
							        				clearInterval(interval);
							        				setTimeout(function(){
														window.location.href = path + "/common/myCenter.html";
							        				},2000);
							        				$("#alertSure").click(function(){
														window.location.href = path + "/common/myCenter.html";
							        				});
							        			}else{
							        				ttime++;
							        			}
							        		},
							        		error: function(request){
							        			console.log("提出申请异常");
							        		}
							        	});
							        },time);
								}
								
//								AlertDialog.tip("提出申请成功");
//								setTimeout(function(){
//									window.location.href = path + "/common/myCenter.html";
//								}, 2000);
//								$("#alertSure").click(function(){
//									window.location.href = path + "/common/myCenter.html";
//								});
							},
							error: function(request){
								console.log("提出验证异常");
							}
						});
						
					},
					error: function(request){
						console.log("提出验证异常");
					}
				});
			}
		}
	}
}


//发送提出验证码
function getWithVertify(){
	if(Valify.isNull($("#bankCard_tab .cur").attr("bankNo"), "bankCard_tab", -20, 900)){
		if(checkWithAmt()){
			$("#getVertify").unbind("click").text("请稍后...").css({"color":"#CCC", "cursor":"default", "border":"none", "width":"100px"});
			$.ajax({
				url: path + "/verifycode/sendUserVerifyCode.html",
				type: "post",
				dataType: "json",
				data: {"codeType": "withdraw_remind"},
				success: function(data){
					if(!data["success"]){
						return false;
					}
					$("#getVertify").unbind("click").text("60秒后发送").css({"color":"#CCC", "cursor":"default", "border":"none", "width":"100px"});
					countDown(60, "getVertify", wOverFn);
				},
				error: function(request){
					$("#getVertify").text("获取验证码").css({"cursor":"pointer","color":"#0697da","border":"1px solid #aad5f7", "background":"none"});
					$("#getVertify").click(getWithVertify);
					console.log("发送绑定银行卡信息异常");
				}
			});
		}
	}
}
function wOverFn(){
	$("#getVertify").text("获取验证码").css({"cursor":"pointer","color":"#0697da","border":"1px solid #aad5f7", "background":"none", "width":"100px"});
	$("#getVertify").click(getWithVertify);
}
//检测提出金额
function checkWithAmt(){
	if(Valify.isNull($("#withdrawInput").val(), "withdrawInput", 10, 20)){
		if(isNaN($("#withdrawInput").val())){
			AlertDialog.show("请输入数字", "withdrawInput", 10, 20);
			return false;
		}
		if(Number($("#withdrawInput").val()) < 12){
			AlertDialog.show("提出金额不能小于12", "withdrawInput", 10, 20);
			return false;
		}
		
		if(Number($("#withdrawInput").val()) > Number($("#userBalance").attr("num"))){
			AlertDialog.show("账户余额不足", "withdrawInput", 10, 20);
			return false;
		}
		if(Number($("#withdrawInput").val()) > 1000000){
			AlertDialog.show("提现金额不能超过100万", "withdrawInput", 10, 20);
			return false;
		}
		AlertDialog.hide();
		if($("#db").prop("checked")){ //当前选中按单笔收取手续费
			$("#poundage").text("￥" + $("#poundage").attr("db"));
		}
		if($("#bl").prop("checked")){
			$("#poundage").text("￥" + Number($("#withdrawInput").val()) * Number($("#poundage").attr("bl")));
		}
		getWithdrawFee($("#withdrawInput").val());
		return true;
	}
}

//获取个人账户
function getUserAccount(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getAccountInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			$("#userBalance").text(data["balance"].toFixed(2)).attr("num", data["balance"].toFixed(2)) ; //可用余额
			$("#poundage").attr("db", data["feeParam"]).attr("bl", data["ratioFeeParam"]).text("￥" + data["feeParam"].toFixed(2));
		},error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//查询提出手续费
function getWithdrawFee(money){
	$.ajax({
		url: path + "/withdraw/getWithdrawFee.html",
		type: "post",
		dataType: "json",
		data:{
			"actualMoney" : money
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			$("#withdrawFee").text(data["fee"].toFixed(2)).attr("num", data["fee"].toFixed(2)) ; //提出费用
			$("#withdrawAmt").text(data["amt"].toFixed(2)).attr("num", data["amt"].toFixed(2)) ; //实际到账金额
		},error: function(request){
			console.log("查询提出手续费异常");
		}
	});
}
