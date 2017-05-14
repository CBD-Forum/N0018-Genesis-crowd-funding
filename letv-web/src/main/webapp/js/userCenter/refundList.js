if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
var loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7,window.location.search.length);
$(function(){
	centerLoanTab();
	$("#rightProjectList").next().show();
	$(".prompt_close").click(function(){
		$(".prompt_box").fadeOut();
		$(".sbgpop").hide();
		AlertDialog.hide();
	});
	$("#FqxBtu,#qxBtu").click(function(){
		$("#failRefund,#successRefund").fadeOut();
		$(".sbgpop").hide();
		AlertDialog.hide();
	});
});
function centerLoanTab(){
	$("#centerLoanTab li").click(function(){
		$("#centerLoanTab").find("a").removeClass("cur");
		$(this).find("a").addClass("cur");
		$("#centerLoanType>div,.letvPage").hide();
		$(".letvPage").eq($(this).index()).show();
		$("#centerLoanType>div").eq($(this).index()).show();
		$("#centerLoanTabBody>div").hide();
		$("#" + $(this).attr("name") + "Body").show();
		refundList(1,$(this).attr("name"));
	});
	$("#centerLoanTab").find("a.cur").first().click();
}
//退款
function refundList(page,code){
	$.ajax({
		url: path+"/crowdfundUserCenter/getMySupportList.html",
		type: "post",
		dataType: "json",
		data:{
			"loanNo": loanNo,
			"refundStateType":code,
			"requestRemark":"project",
			"page":page,
			"rows":6
		},
		success: function(data){
			if(!data["success"]){
				console.log("获取退款列表失败");
				return false;
			}
			sumPage = (data["msg"]["total"]%6 == 0) ? data["msg"]["total"]/6 : Math.floor(data["msg"]["total"]/6) + 1;
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			if(l == 0){
				eStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>您还没有发布项目，赶快去发布吧~<br/><a href="'+path+'/common/enterProjectNav.html">发布项目</a></div>'; 
				$("#"+code+"Body>div").html(eStr);
				$("#"+code+"Page").hide();
				return false;
			}
			var eArr = [], eStr = '';
			for(var i = 0; i<l; i++){
				if(code == "success"){
					eArr.push('<div class="clearfix refundListTk">');
					if(data[i]["refuseComplateTime"]){
						eArr.push('<p><i>退款成功时间：</i>'+data[i]["refuseComplateTime"]+'</p>');
					}else{
						eArr.push('<p><i>退款成功时间：</i>--</p>');
					}
					eArr.push('</div>');
				}
				if(code == "fail"){
					eArr.push('<div class="clearfix refundListTk">');
					if(data[i]["refuseLoanAuditTime"]){
						eArr.push('<p><i>退款失败时间：</i>'+data[i]["refuseLoanAuditTime"]+'</p>');
					}else{
						eArr.push('<p><i>退款失败时间：</i>--</p>');
					}
					eArr.push('</div>');
				}
				eArr.push('<div class="proj_list">');
				eArr.push('<p class="clearfix mb17">');
				eArr.push('<span><i>项目编号：</i>'+data[i]["loanNo"]+'</span>');
				eArr.push('<span class="wid315"><i>收货人：</i>'+data[i]["postUser"]+'</span>');
				eArr.push('<span class="wid245" style="overflow:visible;"><i>退款人：</i>'+data[i]["supportUserName"]+'');
				eArr.push('</p>');
				eArr.push('<p class="clearfix mb17">');
				eArr.push('<span><i>项目名称：</i>'+data[i]["loanName"]+'</span>');
				eArr.push('<span class="wid315"><i>联系方式：</i>'+data[i]["postMobile"]+'</span>');
				eArr.push('<span class="wid245"><i>退款金额：</i>'+(Number(data[i]["supportAmt"])+Number(data[i]["transFee"])).toFixed(2)+companyCode+'</span>');
				eArr.push('</p>');
				eArr.push('<p class="clearfix mb17">');
				eArr.push('<span><i>支持金额：</i>'+data[i]["supportAmt"].toFixed(2)+companyCode+'</span>');
				if(data[i]["cityName"]){
					eArr.push('<span class="wid315" style="overflow:visible;"><i>收货地址：</i><a class="col_blue" style="position: relative;" href="javascript:void(0);" onclick="returnDetails(this);">查看收货地址<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["provinceName"]+data[i]["cityName"]+data[i]["adressDetail"]+'</em></i></a></span>');
				}else{
					eArr.push('<span class="wid315" style="overflow:visible;"><i>收货地址：</i><a class="col_blue" style="position: relative;" href="javascript:void(0);" onclick="returnDetails(this);">查看收货地址<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["provinceName"]+data[i]["adressDetail"]+'</em></i></a></span>');
				}
				if(data[i]["applicationContent"]){
					eArr.push('<span class="wid245" style="overflow:visible;"><i>退款说明：</i><a class="col_blue" style="position: relative;" href="javascript:void(0);" onclick="returnDetails(this);">查看退款说明<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["applicationContent"]+'</em></i></a></span>');
				}else{
					eArr.push('<span class="wid245"><i>退款说明：</i>--</span>');
				}
				eArr.push('</p>');
				eArr.push('<p class="clearfix mb17">');
				eArr.push('<span><i>支持时间：</i>'+data[i]["supportTime"]+'</span>');
				eArr.push('<span class="wid315"><i>备注：</i>'+data[i]["backContent"]+'</span>');
				if(code == "waitConfirm"){
					eArr.push('<span class="wid245"><i style="display:inline-block;width:50px;">操作：</i><a class="modifyBtu refundListBtu" orderId="'+data[i]["orderId"]+'" eid="'+data[i]["id"]+'" href="javascript:void(0);" onclick="showRefundSuccessBox(event);">同意</a></span>');
				}
				if(code == "fail"){
					if(data[i]["refuseReason"]){
						eArr.push('<span class="wid245" style="overflow:visible;"><i>退款失败原因：</i>');
						eArr.push('<a class="col_blue" style="position: relative;" href="javascript:void(0);" onclick="returnDetails(this);">退款失败原因<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["refuseReason"]+'</em></i></a>');
						eArr.push('</span>');
					}else{
						eArr.push('<span class="wid245"><i>退款失败原因：</i>--</span>');
					}
				}
				eArr.push('</p>');
				eArr.push('<p class="clearfix mb17">');
				if(data[i]["refuseApplyTime"]){
					eArr.push('<span><i>退款申请时间：</i>'+data[i]["refuseApplyTime"]+'</span>');
				}else{
					eArr.push('<span><i>退款申请时间：</i>--</span>');
				}
				if(data[i]["sendTime"]){
					eArr.push('<span class="wid315"><i>项目发货时间：</i>'+data[i]["sendTime"]+'</span>');
				}else{
					eArr.push('<span class="wid315"><i>项目发货时间：</i>--</span>');
				}
				if(code == "waitConfirm"){
					eArr.push('<span class="wid245"><i style="display:inline-block;width:50px;"></i><a class="modifyBtu refundListBtu1" orderId="'+data[i]["orderId"]+'" eid="'+data[i]["id"]+'" href="javascript:void(0);" onclick="showRefundFailBox(event);">拒绝</a></span>');
				}
				eArr.push('</p>');
				eArr.push('</div>');
			}
			eStr = eArr.join("");
			$("#"+code+"Body>div").html(eStr);
			$("#"+code+"Page").show();
			//分页设置
			pagePause = 0;
			if(page < 2){
				$("#"+code+"Page").jPages({
					containerID : ""+code+"Body",
					first:false,
					last:false,
					previous:" ",
					next:" ",
					clickStop   : true,
					perPage	: 6,
					allSumPage : sumPage,
					callback: getMyRefundList
				});
			}
		},
		error:function(){
			
		}
	});
}
//分页回调函数
function getMyRefundList(obj){
	if(pagePause == 0){
		return false;
	}
	var code = $("#centerLoanTab li a.cur").parent().attr("name");
	refundList(obj["current"],code);
}

//回报详情
function returnDetails(_this){
	$(_this).find("i").show()
	$(_this).mouseleave(function(){
		$(_this).find("i").hide();
	});
}
function showRefundSuccessBox(event){
	var obj = event.srcElement || event.target;
	var eid = $(obj).attr("eid");
	var orderId = $(obj).attr("orderId");
	showBalance();
	getSupport(eid);
	$(".sbgpop").show();
	var bl = (cv["winW"]-$("#successRefund").width())/2, bt = (cv["winH"]-$("#successRefund").height())/2;
	$("#successRefund").css({"left":bl+"px", "top":bt+"px"}).fadeIn();
	$("#operatBtu").click(function(){
		if(Number($("#balance").attr("num")) < Number($("#TsupportAmt").attr("num"))){
			AlertDialog.tip("您的余额不足,请先充入!");
			return false;
		}
		$.ajax({
			url: path + "/crowdfunding/updateLoanAuditRefund.html",
			type: "post",
			dataType: "json",
			data: {
				"auditState":"loanPassed",
				"supportNo":orderId
			},
			success: function(data){
				if(data["success"]){
					$("#successRefund").fadeOut();
					$(".sbgpop").hide();
					AlertDialog.hide();
					$("#bgpop_wait").show();
		        	var ttime = 1;
					var time = 2000;
				    var interval = setInterval(function(){
				        	$.ajax({
				        		url: path + "/crowdfundingRefund/selectRefundStatus.html",
				        		type: "post",
				        		dataType: "json",
				        		data: {
				        			"orderId":orderId
				        		},
				        		success: function(data){
				        			if(ttime > 150){
				        				AlertDialog.tip("操作失败!");
				    					$("#bgpop_wait").hide();
				        				clearInterval(interval);
				        				setTimeout(function(){
				        					window.location.reload();
				        				},2000);
				        				$("#alertSure").click(function(){
				        					window.location.reload();
				        				});
				        				return false;
				        			}
				        			if(data["success"]){
				        				if(data["msg"]){
					        				AlertDialog.tip("操作成功!");
					    					$("#bgpop_wait").hide();
					        				clearInterval(interval);
					        				setTimeout(function(){
					        					window.location.reload();
					        				},2000);
					        				$("#alertSure").click(function(){
					        					window.location.reload();
					        				});
					        			}else{
					        				ttime++;
					        			}
				        			}else{
				        				AlertDialog.tip("操作失败!");
				    					$("#bgpop_wait").hide();
				        				clearInterval(interval);
				        				setTimeout(function(){
				        					window.location.reload();
				        				},2000);
				        				$("#alertSure").click(function(){
				        					window.location.reload();
				        				});
				        				return false;
				        			}
				        		},
				        		error: function(request){
				        			console.log("同意退款异常");
				        		}
				        	});
				        },time);
				}else{
					AlertDialog.tip(data["msg"]);
				}
			},
			error: function(request){
				console.log("同意退款异常");
			}
		});
	});
}
function showBalance(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getAccountInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			$("#balance").text(data["balance"].toFixed(2)+companyCode).attr("num",data["balance"].toFixed(2)); //可用余额
		},error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
function getSupport(id){
	$.ajax({
		url: path+"/crowdfundUserCenter/getMySupportList.html",
		type: "post",
		dataType: "json",
		data:{
			"loanNo": loanNo,
			"requestRemark":"project",
			"id": id,
		},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			var data = data["msg"]["rows"];
			$("#TloanNo").text(data[0]["loanNo"]);
			$("#TloanName").text(data[0]["loanName"]);
			$("#TsupportUserName").text(data[0]["supportUserName"]);
			$("#TsupportAmt").text((Number(data[0]["supportAmt"])+Number(data[0]["transFee"])).toFixed(2)+companyCode).attr("num",(Number(data[0]["supportAmt"])+Number(data[0]["transFee"])).toFixed(2));//transFee
		},error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
function showRefundFailBox(event){
	var obj = event.srcElement || event.target;
	var eid = $(obj).attr("eid");
	var orderId = $(obj).attr("orderId");
	$(".sbgpop").show();
	$("#refuseReason").val("");
	var bl = (cv["winW"]-$("#failRefund").width())/2, bt = (cv["winH"]-$("#failRefund").height())/2;
	$("#failRefund").css({"left":bl+"px", "top":bt+"px"}).fadeIn();
	$("#FoperatBtu").click(function(){
		if($("#refuseReason").val().length == 0){
			AlertDialog.tip("请输入拒绝原因");
			return false;
		}
		if($("#refuseReason").val().length > 100){
			AlertDialog.tip("拒绝原因字数限100字。");
			return false;
		}
		$.ajax({
			url: path + "/crowdfunding/updateLoanAuditRefund.html",
			type: "post",
			dataType: "json",
			data: {
				"auditState":"loanRefuse",
				"refuseReason": $("#refuseReason").val(),
				"supportNo":orderId
			},
			success: function(data){
				if(data["success"]){
					AlertDialog.tip("操作成功!");
					setTimeout(function(){
						window.location.reload();
					},2000);
					$("#alertSure").click(function(){
						window.location.reload();
					});
				}else{
					AlertDialog.tip(data["msg"]);
				}
			},
			error: function(request){
				console.log("拒绝退款异常");
			}
		});
	});
}