if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
var loanNo = getQueryString("loanNo");
var tcode = getQueryString("tcode");
var ctype = getQueryString("ctype");
$(function(){
	if(tcode == "lended"){
		$("#deliveryTitle").text("发货");
	}else{
		$("#deliveryTitle").text("发货明细");
	}
	if(ctype == "1"){
		$(".abs_div>p a").removeClass("col_blue");
		$(".abs_div>p a").eq(0).addClass("col_blue");
	}else{
		$(".abs_div>p a").removeClass("col_blue");
		$(".abs_div>p a").eq(2).addClass("col_blue");
	}
	$("#rightProjectList").next().show();
	$("#logicoNo,#logicoComp").keyup(function(){
		if($(this).val().length>=29){
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	});
	
	getLogicoList(1);
	myEcreateWebUploader("image_file", "imgheadPhoto", "loan_logo_url", "imgheadLi");//上传头像
});
//获取支持列表,录入物流信息
function getLogicoList(page, vname){
	var lArr = [], lStr = '', l, sumPage;
	if(tcode == "lended"){
		var dataStr ={
			"loanNo": loanNo,
			//"sendStateType": "lended",
			"page": page,
			"rows": 10
		};
	}else{
		var dataStr ={
			"loanNo": loanNo,
			"sendStateType": "sending",
			"page": page,
			"rows": 10
		};
	}
	$.ajax({
		url: path + "/crowdfunding/getSupportList.html",
		type: "post",
		dataType: "json",
		data: dataStr,
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"]["rows"].length, sumPage = (data["msg"]["total"]%10 == 0) ? data["msg"]["total"]/10 : Math.floor(data["msg"]["total"]/10) + 1;
			data = data["msg"]["rows"];
			$(".letvPage").hide();
			if(l == 0){
				lStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>暂无发货记录哦~~<br/></div>';
				$("#publicBody>div").html(lStr).show();
				$("#publicPage").hide();
				return false;
			}
			for(var i=0;i<l;i++){
				if(data[i]["backType"] == "S"){
					lArr.npush('<div class="proj_list">');
					if(vname == "zj"){
						lArr.npush('<p class="clearfix mb17" style=" border-bottom: 1px solid #ccc; padding-bottom: 10px;">');
						lArr.npush('<span><i>抽奖编号：</i>'+data[i]["prizeNo"]+'</span>');
						lArr.npush('</p>');
					}
					lArr.npush('<p class="clearfix mb17">');
					lArr.npush('<span><i>订单编号：</i>'+data[i]["orderId"]+'</span>');
					lArr.npush('<span class="wid315"><i>收货人：</i>'+data[i]["postUser"]+'</span>');
					lArr.npush('<span class="wid245" style="overflow:visible;"><i>回报详情：</i><a class="col_blue" style="position: relative;" href="javascript:void(0);" onclick="returnDetails(this);">查看回报详情<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["backContent"]+'</em></i></a></span>');
					lArr.npush('</p>');
					lArr.npush('<p class="clearfix mb17">');
					lArr.npush('<span><i>支持人：</i>'+data[i]["supportUser2"]+'</span>');
					lArr.npush('<span class="wid315"><i>联系方式：</i>'+data[i]["postMobile"]+'</span>');
					lArr.npush('<span class="wid245"><i>发货时间：</i>'+data[i]["estimateSendTime"]+'</span>');
					lArr.npush('</p>');
					lArr.npush('<p class="clearfix mb17">');
					lArr.npush('<span><i>支持金额：</i>'+data[i]["supportAmt"].toFixed(2)+companyCode+'</span>');
					if(data[i]["cityName"]){
						lArr.npush('<span class="wid315" style="overflow:visible;"><i>收货地址：</i><a class="col_blue" style="position: relative;" href="javascript:void(0);" onclick="returnDetails(this);">查看收货地址<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["provinceName"]+data[i]["cityName"]+data[i]["adressDetail"]+'</em></i></a></span>');
					}else{
						lArr.npush('<span class="wid315" style="overflow:visible;"><i>收货地址：</i><a class="col_blue" style="position: relative;" href="javascript:void(0);" onclick="returnDetails(this);">查看收货地址<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["provinceName"]+data[i]["adressDetail"]+'</em></i></a></span>');
					}
					if(data[i]["state"] == "sending"  || data[i]["state"] == "end"){
						lArr.npush('<span class="wid245" style="overflow:visible;"><i>物流公司：</i> '+data[i]["sendDelivery"]+'</span>');
					}else{
						if(data[i]["transferAuditState"] == "refuse" || data[i]["transferAuditState"] == "passed" || data[i]["transferAuditState"] == "auditing" ){
							lArr.npush('<span class="wid245"><a class="modifyBtu" style="background: #CCC;width:100px;height:30px;line-height:30px;margin:0;font-size:14px;display:inline-block;" >发货</a></span>');
						}else{
							lArr.npush('<span class="wid245"><a class="modifyBtu" style="width:100px;height:30px;line-height:30px;margin:0;font-size:14px;display:inline-block;" eid="'+data[i]["id"]+'" href="javascript:void(0);" onclick="showEnterBox(event);">发货</a></span>');
						}
					}
					lArr.npush('</p>');
					lArr.npush('<p class="clearfix mb17">');
					lArr.npush('<span><i>支持时间：</i>'+data[i]["supportTime"]+'</span>');
					if(data[i]["remark"]){
						lArr.npush('<span class="wid315"><i>备注：</i>'+data[i]["remark"]+'</span>');
						if(data[i]["state"] == "sending" || data[i]["state"] == "end"){
							lArr.npush('<span class="wid245" style="overflow:visible;"><i>物流单号：</i> '+data[i]["sendOrderId"]+'</span>');
						}
						
					}else{
						lArr.npush('<span class="wid315" style="height: 30px;"></span>');
						if(data[i]["state"] == "sending" || data[i]["state"] == "end"){
							lArr.npush('<span class="wid245" style="overflow:visible;"><i>物流单号：</i> '+data[i]["sendOrderId"]+'</span>');
						}
					}
					lArr.npush('</p>');
					lArr.npush('</div>');
				}else{
					lArr.npush('<div class="proj_list">');
					if(vname == "zj"){
						lArr.npush('<p class="clearfix mb17" style=" border-bottom: 1px solid #ccc; padding-bottom: 10px;">');
						lArr.npush('<span><i>抽奖编号：</i>'+data[i]["prizeNo"]+'</span>');
						lArr.npush('</p>');
					}
					lArr.npush('<p class="clearfix mb17">');
					lArr.npush('<span><i>项目编号：</i>'+data[i]["loanNo"]+'</span>');
					lArr.npush('<span class="wid315"><i>收货地址：</i>'+data[i]["supportUserEmail"]+'</span>');
					//lArr.npush('<span class="wid245" style="overflow:visible;"><i>回报详情：</i><a class="col_blue" style="position: relative;" href="javascript:void(0);" onclick="returnDetails(this);">查看回报详情<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["backContent"]+'</em></i></a></span>');
					if(data[i]["state"] == "sending"  || data[i]["state"] == "end"){
						lArr.npush('<span class="wid245" style="overflow:visible;"><i>收货邮箱：</i> '+data[i]["sendDelivery"]+'</span>');
					}else{
						if(data[i]["transferAuditState"] == "refuse" || data[i]["transferAuditState"] == "passed" || data[i]["transferAuditState"] == "auditing" ){
							lArr.npush('<span class="wid245"><a class="modifyBtu" style="background: #CCC;width:100px;height:30px;line-height:30px;margin:0;font-size:14px;display:inline-block;" >发货</a></span>');
						}else{
							lArr.npush('<span class="wid245"><a class="modifyBtu" style="width:100px;height:30px;line-height:30px;margin:0;font-size:14px;display:inline-block;" eid="'+data[i]["id"]+'" supportUserEmail="'+data[i]["supportUserEmail"]+'" href="javascript:void(0);" onclick="showEmailBox(event);">发货</a></span>');
						}
					}
					lArr.npush('</p>');
					lArr.npush('<p class="clearfix mb17">');
					lArr.npush('<span><i>支持人：</i>'+data[i]["supportUser2"]+'</span>');
//					if(typeof(data[i]["postMobile"])!="undefined"){
//						lArr.npush('<span class="wid315"><i>联系方式：</i>'+data[i]["postMobile"]+'</span>');
//					}
					if(data[i]["remark"]){
						lArr.npush('<span class="wid315"><i>备注：</i>'+data[i]["remark"]+'</span>');
					}else{
						lArr.npush('<span class="wid315" style="height: 30px;"><i>备注：</i>--</span>');
					}
					
					lArr.npush('</p>');
					lArr.npush('<p class="clearfix mb17">');
					lArr.npush('<span><i>支持金额：</i>'+data[i]["supportAmt"].toFixed(2)+companyCode+'</span>');
					lArr.npush('<span class="wid245" style="overflow:visible;"><i>回报详情：</i><a class="col_blue" style="position: relative;" href="javascript:void(0);" onclick="returnDetails(this);">查看回报详情<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["backContent"]+'</em></i></a></span>');
					lArr.npush('</p>');
					lArr.npush('<p class="clearfix mb17">');
					lArr.npush('<span><i>支持时间：</i>'+data[i]["supportTime"]+'</span>');
					lArr.npush('<span class="wid245"><i>发货时间：</i>'+data[i]["estimateSendTime"]+'</span>');
					lArr.npush('</p>');
					lArr.npush('</div>');
				}
				
//				lArr.npush('<div class="proj_list">');
//				lArr.npush('<p class="clearfix mb17">');
//				lArr.npush('<span><i>项目编号：</i>'+data[i]["loanNo"]+'</span>');
//				lArr.npush('<span class="wid315"><i>收货人：</i>'+data[i]["postUser"]+'</span>');
//				lArr.npush('<span class="wid245" style="overflow:visible;"><i>回报详情：</i><a class="col_blue" style="position: relative;" href="javascript:void(0);" onclick="returnDetails(this);">查看回报详情<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["backContent"]+'</em></i></a></span>');
//				lArr.npush('</p>');
//				lArr.npush('<p class="clearfix mb17">');
//				lArr.npush('<span><i>支持人：</i>'+data[i]["supportUser2"]+'</span>');
//				lArr.npush('<span class="wid315"><i>联系方式：</i>'+data[i]["postMobile"]+'</span>');
//				lArr.npush('<span class="wid245"><i>发货时间：</i>'+data[i]["estimateSendTime"]+'</span>');
//				lArr.npush('</p>');
//				lArr.npush('<p class="clearfix mb17">');
//				lArr.npush('<span><i>支持金额：</i>'+data[i]["supportAmt"].toFixed(2)+companyCode+'</span>');
//				if(data[i]["backType"] == "S"){
//					if(data[i]["cityName"]){
//						lArr.npush('<span class="wid315" style="overflow:visible;"><i>收货地址：</i><a class="col_blue" style="position: relative;" href="javascript:void(0);" onclick="returnDetails(this);">查看收货地址<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["provinceName"]+data[i]["cityName"]+data[i]["adressDetail"]+'</em></i></a></span>');
//					}else{
//						lArr.npush('<span class="wid315" style="overflow:visible;"><i>收货地址：</i><a class="col_blue" style="position: relative;" href="javascript:void(0);" onclick="returnDetails(this);">查看收货地址<i class="tk_shm3" style="display:none;"><em class="ion"></em><em class="center">'+data[i]["provinceName"]+data[i]["adressDetail"]+'</em></i></a></span>');
//					}
//				}
//				
//				if(data[i]["state"] != "sending"){
//					if(data[i]["transferAuditState"] == "refuse" || data[i]["transferAuditState"] == "passed" || data[i]["transferAuditState"] == "auditing" ){
//						lArr.npush('<span class="wid245"><a class="modifyBtu" style="background: #CCC;width:100px;height:30px;line-height:30px;margin:0;font-size:14px;display:inline-block;" >发货</a></span>');
//					}else{
//						if(data[i]["backType"] == "S"){
//							lArr.npush('<span class="wid245"><a class="modifyBtu" style="width:100px;height:30px;line-height:30px;margin:0;font-size:14px;display:inline-block;" eid="'+data[i]["id"]+'" href="javascript:void(0);" onclick="showEnterBox(event);">发货</a></span>');
//						}else{
//							lArr.npush('<span class="wid245"><a class="modifyBtu" style="width:100px;height:30px;line-height:30px;margin:0;font-size:14px;display:inline-block;" eid="'+data[i]["id"]+'" supportUserEmail="'+data[i]["supportUserEmail"]+'" href="javascript:void(0);" onclick="showEmailBox(event);">发货</a></span>');
//						}
//					}
//				}else{
//					if(data[i]["backType"] == "S"){
//						lArr.npush('<span class="wid245" style="overflow:visible;"><i>物流公司：</i> '+data[i]["sendDelivery"]+'</span>');
//					}else{
//						lArr.npush('<span class="wid245" style="overflow:visible;"><i>收货邮箱：</i> '+data[i]["sendDelivery"]+'</span>');
//					}
//				}
//				lArr.npush('</p>');
//				lArr.npush('<p class="clearfix mb17">');
//				lArr.npush('<span><i>支持时间：</i>'+data[i]["supportTime"]+'</span>');
//				if(data[i]["remark"]){
//					lArr.npush('<span class="wid315"><i>备注：</i>'+data[i]["remark"]+'</span>');
//					if(data[i]["state"] == "sending"){
//						if(data[i]["backType"] == "S"){
//							lArr.npush('<span class="wid245" style="overflow:visible;"><i>物流单号：</i> '+data[i]["sendOrderId"]+'</span>');
//						}
//					}
//					
//				}else{
//					lArr.npush('<span class="wid315" style="height: 30px;"></span>');
//					if(data[i]["state"] == "sending"){
//						if(data[i]["backType"] == "S"){
//							lArr.npush('<span class="wid245" style="overflow:visible;"><i>物流单号：</i> '+data[i]["sendOrderId"]+'</span>');
//						}
//					}
//				}
//				lArr.npush('</p>');
//				lArr.npush('</div>');
			}
			lStr = lArr.join("");
			$("#publicBody>div").html(lStr).show();
			$("#publicPage").show();
			pagePause = 0;
			//分页设置
			if(page < 2){
				$("#publicPage").jPages({
					containerID : "publicBody",
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

//回报详情
function returnDetails(_this){
	$(_this).find("i").show()
	$(_this).mouseleave(function(){
		$(_this).find("i").hide();
	});
}

//获取对应消息
function getlMes(event){
	event.stopPropagation();//阻止冒泡
	var obj = event.srcElement || event.target;
	var Content = $(obj).attr("Content");
	var al = (cv["winW"]-580)/2, at = (cv["winH"]-$(".agree_box2").height())/2;
	$("#bgpop").show();
	$("#leaveMessage").html(Content);
	$("#agree_box2").css({"left":al+"px", "top":at+"px"}).fadeIn();
	$(".agree_close2").click(function(){
		$("#agree_box2").fadeOut();
		$("#bgpop").hide();
	});
}

//分页回调函数
function ajaxPageData(obj){
	if(pagePause == 0){
		return false;
	}
	getLogicoList(obj["current"]);
}
//展示录入物流信息弹出框
function showEnterBox(event){
	var obj = event.srcElement || event.target;
	var bid = $(obj).attr("eid");
	var ol = (cv["winW"]-420)/2, lt = (cv["winH"]-190)/2;
	$("#bgpop").show();
	
	$("#logicoNo").val("");
	$("#logicoComp").val("");
	if($("a[eid=\""+bid+"\"]").parent().children().hasClass('.ylr')){
		$("#logicoNo").val("");
		$("#logicoComp").val("");
	}
	$("#enterDiv").css({"left":ol, "top":lt}).show();
//	$("#logicoNo").blur(function(){
//		isNull($(this).val(), $(this).attr("id"));
//	});
//	$("#logicoComp").blur(function(){
//		isNull($(this).val(), $(this).attr("id"));
//	});
	//确定录入按钮事件
	$("#addLogicoBtn").unbind("click").click(addLogico);
	//取消录入按钮事件
	$("#canelLogicoBtn").click(function(){
		$("#bgpop").hide();
		$("#enterDiv").hide();
	});
	$("#bgpop").click(function(){
		$(this).hide();
		$("#enterDiv").hide();
	});
	//确定添加
	function addLogico(){
		$("a[eid=\""+bid+"\"]").parent().find(".ylr").remove();
		$("a[eid=\""+bid+"\"]").parent().find(".ylr").next().remove();
		if(isNull($("#logicoNo").val(), "logicoNo")){
			if(isNull($("#logicoComp").val(), "logicoComp")){
				$.ajax({
					url: path + "/crowdfunding/updateSendInfo.html",
					type: "post",
					dataType: "json",
					data: {
						"id": bid,
						"sendOrderId": $("#logicoNo").val(),
						"sendDelivery": $("#logicoComp").val()
					},
					success:function(data){

						if(data["success"]){
							$(".bgpop").hide();
							$("#enterDiv").hide();
//							$("a[eid=\""+bid+"\"]").text("已录入").removeAttr("onclick").css({"color":"#104267", "text-decoration":"none"});
//							$("a[eid=\""+bid+"\"]").text("已发送");	
							getLogicoList(1);
						}else{
							AlertDialog.tip(data["msg"]);
							$("#enterDiv").hide();
						}
					},
					error: function(request){
						console.log("录入物流信息异常");
					}
				});
			}
		}
	}
	//检测为空数据
	function isNull(str, id){
		if(!str){
			AlertDialog.show("信息不能为空", id, 10, 20);
			$("#" + id).css("border", "1px solid red");
			return false;
		}
		$("#tip_div").hide();
		$("#" + id).css("border", "1px solid #CCC");
		return true;
	}
}
//展示邮箱信息弹出框
function showEmailBox(event){
	var obj = event.srcElement || event.target;
	var bid = $(obj).attr("eid");
	var supportUserEmail = $(obj).attr("supportUserEmail");
	var ol = (cv["winW"]-420)/2, lt = (cv["winH"]-190)/2;
	$(".sbgpop").show();
	$("#emailText").val(supportUserEmail);
//	if($("a[eid=\""+bid+"\"]").parent().children().hasClass('.ylr')){
//		$("#emailText").val("");
//	}
	$("#failRefund").css({"left":ol, "top":lt}).show();
//	$("#emailText").blur(function(){
//		if(Valify.email($("#emailText").val(), "emailText", 10, 20));
//	});
	//确定录入按钮事件
	$("#FoperatBtu").unbind("click").click(addLogico);
	//取消录入按钮事件
	$("#FqxBtu,.failRefund_close").click(function(){
		$(".sbgpop").hide();
		$("#failRefund").hide();
		AlertDialog.hide();
	});
	$(".sbgpop").click(function(){
		$(this).hide();
		$("#failRefund").hide();
		AlertDialog.hide();
	});
	//确定添加
	function addLogico(){
		$("a[eid=\""+bid+"\"]").parent().find(".ylr").remove();
		$("a[eid=\""+bid+"\"]").parent().find(".ylr").next().remove();
		if(Valify.email($("#emailText").val(), "emailText", 10, 20)){
			$.ajax({
				url: path + "/crowdfunding/updateSendInfo.html",
				type: "post",
				dataType: "json",
				data: {
					"id": bid,
					"sendDelivery": $("#emailText").val()
				},
				success:function(data){
					if(data["success"]){
						AlertDialog.tip("已发送!");
        				setTimeout(function(){
        					window.location.reload();
        				},2000);
        				$("#alertSure").click(function(){
        					window.location.reload();
        				});
					}else{
        				AlertDialog.tip(data["msg"]);
        				return false;
					}
				},
				error: function(request){
					console.log("录入物流信息异常");
				}
			});
		}
	}
}