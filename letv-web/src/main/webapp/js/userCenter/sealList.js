if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	$("#tradeTab li").click(function(){
		$("#tradeTab li a").removeClass("a_home");
		$(this).find("a").addClass("a_home");
		getLogicoList($(this).find("a.a_home").attr('type'),1);
	});
	getLogicoList($("#tradeTab a.a_home").attr('type'),1);
	myEcreateWebUploader("image_file", "imgheadPhoto", "loan_logo_url", "imgheadLi");//上传头像
});
//印章列表
function getLogicoList(type,page){
	var lArr = [], lStr = '', l, sumPage;
	$.ajax({
		url: path + "/espSignApi/getSignSupportList.html",
		type: "post",
		dataType: "json",
		data: {
			"signType":type,
			"page": page,
			"rows": 10
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"]["rows"].length, sumPage = (data["msg"]["total"]%10 == 0) ? data["msg"]["total"]/10 : Math.floor(data["msg"]["total"]/10) + 1;
			data = data["msg"]["rows"];
			if(l == 0){
				lStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>还没有合同记录哦，先去浏览其他项目吧~<br/><a href="'+path+'/common/projectList.html">查看全部项目</a></div>' ;
				$("#contractBody>ul").html(lStr).show();
				$("div.letvPage").hide();
				return false;
			}
			if(type == "investor"){
				lArr.push('<li class="hb_title clearfix">');
				lArr.push('<span class="wd6">项目名称</span>');
				lArr.push('<span class="wd7">投资编号</span>');
				lArr.push('<span class="wd8">项目方是否签署</span>');
				lArr.push('<span class="wd9">签署时间</span>');
				lArr.push('<span class="wd10">操作</span>');
				lArr.push('<span class="wd11"></span>');
				lArr.push('</li>');
				for(var i=0;i<l;i++){
					lArr.push('<li class="clearfix">');
					lArr.push('<span class="wd6"><a target="_blank" href="'+path+'/common/loanDetail-stock.html?loanNo='+data[i]["loanNo"]+'">'+data[i]["loanName"]+'</a></span>');
					
					lArr.push('<span class="wd7">'+data[i]["orderId"]+'</span>');
					if(data[i]["contractProjectSignState"] == "1"){
						lArr.push('<span class="wd8">是</span>');
					}else{
						lArr.push('<span class="wd8">否</span>');
					}
					if(data[i]["contractInvestSignTime"] &&typeof(data[i]["contractInvestSignTime"])!='undefined'&&data[i]["contractInvestSignTime"]!=null&&data[i]["contractInvestSignTime"]!=""){
						lArr.push('<span class="wd9">'+data[i]["contractInvestSignTime"]+'</span>');
					}else{
						lArr.push('<span class="wd9">--</span>');
					}
					if(data[i]["loanState"] != "flow"){
						//if(data[i]["loanState"] == "lended"){
							if(data[i]["contractState"] == "notCreate"){  //未创建合同
								//调用签署按钮
								//1、创建合同  2、发送验证码  成功弹框输入验证码   3 签署接口
								lArr.push('<span class="wd10"><a class="tab_btn" orderId="'+data[i]["orderId"]+'" loanNo="'+data[i]["loanNo"]+'" onClick="establishContract(this)">签署 </a></span>');
								//先创建合同  返回url 然后。。
								lArr.push('<span class="wd11"><a target="_blank"  href="'+cv["fileAddress"]+data[i]["contractUrl"]+'" >查看</a></span>');
							}else if(data[i]["contractState"] == "signing"){  //签署中  
								if(data[i]["contractInvestSignState"] == "0"){  //投资人未签署
									//投资人未签署  直接调用发送短信接口  输入验证码后调用签署接口
									lArr.push('<span class="wd10"><a class="tab_btn" contractId="'+data[i]["contractId"]+'" orderId="'+data[i]["orderId"]+'" loanNo="'+data[i]["loanNo"]+'" onClick="contractVerification(this)">签署</a></span>');
									lArr.push('<span class="wd11"><a target="_blank" href="'+cv["fileAddress"]+data[i]["contractUrl"]+'">查看</a></span>');
								}else{
									lArr.push('<span class="wd10"><a class="tab_btn tab_btn2" target="_blank" href="'+cv["fileAddress"]+data[i]["contractUrl"]+'" >查看</a></span>');
								}
							}else{  //签署完成complete 只显示查询按钮
								lArr.push('<span class="wd10"><a class="tab_btn tab_btn2"  target="_blank" href="'+cv["fileAddress"]+data[i]["contractUrl"]+'" >查看</a></span>');
							}
							//如果contractState"] == "notCreate 调用创建合同接口 会给你返回合同的url
						//}else{
						//	lArr.push('<span class="wd10"><a class="tab_btn" style="background: #ccc;" >签署 </a></span>');
						//}
						
					}else{
						lArr.push('<span class="wd10"><a class="tab_btn tab_btn2" >已失效</a></span><span style="width: 75px;"></span>');
					}
					lArr.push('</li>');
				}
			}else{
				lArr.push('<li class="hb_title clearfix">');
				lArr.push('<span class="wd6">项目名称</span>');
				lArr.push('<span class="wd7">项目编号</span>');
				lArr.push('<span class="wd8">投资方是否签署</span>');
				lArr.push('<span class="wd9">签署时间</span>');
				lArr.push('<span class="wd10">操作</span>');
				lArr.push('<span class="wd11"></span>');
				lArr.push('</li>');
				for(var i=0;i<l;i++){
					lArr.push('<li class="clearfix">');
					lArr.push('<span class="wd6"><a target="_blank" href="'+path+'/common/loanDetail-stock.html?loanNo='+data[i]["loanNo"]+'">'+data[i]["loanName"]+'</a></span>');
					lArr.push('<span class="wd7">'+data[i]["loanNo"]+'</span>');
					if(data[i]["contractInvestSignState"] == "1"){
						lArr.push('<span class="wd8">是</span>');
					}else{
						lArr.push('<span class="wd8">否</span>');
					}
					if(typeof(data[i]["contractProjectSignTime"])!="undefined"){
						lArr.push('<span class="wd9">'+data[i]["contractProjectSignTime"]+'</span>');
					}else{
						lArr.push('<span class="wd9">--</span>');
					}
					if(data[i]["loanState"] != "flow"){
						if(data[i]["contractState"] == "notCreate"){  //未创建合同
							//调用签署按钮
							//1、创建合同  2、发送验证码  成功弹框输入验证码   3 签署接口
							lArr.push('<span class="wd10"><a class="tab_btn" orderId="'+data[i]["orderId"]+'" loanNo="'+data[i]["loanNo"]+'" onClick="establishContract(this)">签署 </a></span>');
							//先创建合同  返回url 然后。。
							lArr.push('<span class="wd11"><a target="_blank" href="'+cv["fileAddress"]+data[i]["contractUrl"]+'" >查看</a></span>');
						}else if(data[i]["contractState"] == "signing"){  //签署中  
							if(data[i]["contractProjectSignState"] == "0"){
								//项目方未签署  直接调用发送短信接口  输入验证码后调用签署接口
								lArr.push('<span class="wd10"><a class="tab_btn" contractId="'+data[i]["contractId"]+'" orderId="'+data[i]["orderId"]+'" loanNo="'+data[i]["loanNo"]+'" onClick="contractVerification(this)">签署</a></span>');
								lArr.push('<span class="wd11"><a target="_blank" href="'+cv["fileAddress"]+data[i]["contractUrl"]+'">查看</a></span>');
							}else{
								lArr.push('<span class="wd10"><a class="tab_btn tab_btn2" target="_blank" href="'+cv["fileAddress"]+data[i]["contractUrl"]+'" >查看</a></span>');
							}
						}else{  //签署完成complete 只显示查询按钮
							lArr.push('<span class="wd10"><a class="tab_btn tab_btn2" target="_blank" href="'+cv["fileAddress"]+data[i]["contractUrl"]+'" >查看</a></span>');
						}
					}else{
						lArr.push('<span class="wd10"><a class="tab_btn tab_btn2" >已失效</a></span>');
					}
					
					lArr.push('</li>');
				}
			}
			
			lStr = lArr.join("");
			$("#contractBody>ul").html(lStr).show();
			$("div.letvPage").show();
			pagePause = 0;
			//分页设置
			if(page < 2){
				$("div.letvPage").jPages({
					containerID : "contractBody",
					first:false,
					last:false,
					previous:" ",
					next:" ",
					clickStop   : true,
					perPage	: 10,
					allSumPage : sumPage,
					callback: ajaxPageData
				});
			}
		},
		error: function(request){
			console.log("获取支持列表异常");
		}
	});
}

function ajaxPageData(obj){
	if(pagePause == 0){
		return false;
	}
	getLogicoList($("#tradeTab a.a_home").attr('type'),obj["current"]);
}

function establishContract(_this){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["isSetSignature"]){
				window.location.href = path + "/common/accountSecurity.html" ;
				return false;
			}
		},
		error: function(request){
			console.log("获取认证电子邮箱信息异常");
		}
	});
	$(".Load,#bgpop").show();
	$.ajax({
		url: path + "/espSignApi/createContract.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo": $(_this).attr("loanNo"),
			"investNo": $(_this).attr("orderid")
		},
		success: function(data){
			if(data["success"]){
				$(_this).parent().next().find("a").attr("href",cv["fileAddress"]+data["contractUrl"]).show();
				var contractId = data["contractId"]
				$.ajax({
					url: path + "/espSignApi/sendVerifyCode.html",
					type: "post",
					dataType: "json",
					data: {
						"usage": "contract/sign"
					},
					success: function(data){
						if(data["success"]){
							$(".Load").hide();
							$("#verifycode").val("");
							$("#phoneDiv,#bgpop").show();
							$(".prompt_close").click(function(){
								$("#phoneDiv,#bgpop").hide();
							});
						}else{
							AlertDialog.tip(data["msg"]);
							$(".Load,#bgpop").hide();
						}
					},
					error: function(request){
						console.log("获取认证电子邮箱信息异常");
					}
				})
				$("#updateMobile").unbind("click").click(function(){
					if(Valify.isNull($("#verifycode").val(), "verifycode",10,20)){
						$("#phoneDiv").hide();
						$(".Load,#bgpop").show();
						$.ajax({
							url: path + "/espSignApi/signContract.html",
							type: "post",
							dataType: "json",
							data: {
								"loanNo": $(_this).attr("loanNo"),
								"investNo": $(_this).attr("orderid"),
								"contractId": contractId,
								"authCode": $("#verifycode").val()
							},
							success: function(data){
								if(data["success"]){
									$(".Load,#bgpop").hide();
									AlertDialog.tip("签署成功");
									getLogicoList($("#tradeTab a.a_home").attr('type'),1);
									//location.reload();
								}else{
									AlertDialog.tipA(data["msg"]);
								}
							},
							error: function(request){
								console.log("获取认证电子邮箱信息异常");
							}
						})
					}
				});
			}else{
				AlertDialog.tip(data["msg"]);
				$(".Load,#bgpop").hide();
			}
		},
		error: function(request){
			console.log("获取认证电子邮箱信息异常");
		}
	})
}


function contractVerification(_this){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["isSetSignature"]){
				window.location.href = path + "/common/accountSecurity.html" ;
				return false;
			}
		},
		error: function(request){
			console.log("获取认证电子邮箱信息异常");
		}
	});
	$.ajax({
		url: path + "/espSignApi/sendVerifyCode.html",
		type: "post",
		dataType: "json",
		data: {
			"usage": "contract/sign"
		},
		success: function(data){
			if(data["success"]){
				$("#verifycode").val("");
				$("#phoneDiv,#bgpop").show();
				$(".prompt_close").click(function(){
					$("#phoneDiv,#bgpop").hide();
				});
				
			}else{
				AlertDialog.tip(data["msg"]);
			}
		},
		error: function(request){
			console.log("获取认证电子邮箱信息异常");
		}
	});
	$("#updateMobile").unbind("click").click(function(){
		if(Valify.isNull($("#verifycode").val(), "verifycode",10,20)){
			
			$("#phoneDiv").hide();
			$(".Load,#bgpop").show();
			$.ajax({
				url: path + "/espSignApi/signContract.html",
				type: "post",
				dataType: "json",
				data: {
					"loanNo": $(_this).attr("loanNo"),
					"investNo": $(_this).attr("orderid"),
					"contractId": $(_this).attr("contractId"),
					"authCode": $("#verifycode").val()
				},
				success: function(data){
					if(data["success"]){
						$(".Load,#bgpop").hide();
						AlertDialog.tip("签署成功");
						getLogicoList($("#tradeTab a.a_home").attr('type'),1);
						//location.reload();
					}else{
						AlertDialog.tipA(data["msg"]);
					}
				},
				error: function(request){
					console.log("获取认证电子邮箱信息异常");
				}
			})
			
		}
	});
}