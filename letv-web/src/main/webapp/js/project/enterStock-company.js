if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}
var loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7,window.location.search.length);

$(function(){
	$(".lch_nav li.lch5").click(function(){
		window.location.href = path + "/common/enterStock.html?loanNo="+loanNo;
	});
	$("#capitalPurpose").keyup(function(){
		if($(this).val().length>300){
			AlertDialog.show("资金用途文字不能超过300个字！", "capitalPurpose", 5, 30, "jump");
			$(this).val($(this).val().substring(0,300));
		}
	});
	$("#estimateProfit").keyup(function(){
		if($(this).val().length>1000){
			AlertDialog.show("预计收益文字不能超过1000个字！", "estimateProfit", -45, 100, "jump");
			$(this).val($(this).val().substring(0,1000));
		}
	});
	initCrowdfundInfo();
	$("#stockPartAmt,#exceedAmount").blur(function(){
		if(Number($("#exceedAmount").val())<Number($("#financingAmount").val())){
			AlertDialog.show("超募最大金额需大于等于本次融资金额", "exceedAmount", 10, 20);
		}else{
			if(checkIsNaN($("#exceedAmount").val(), "exceedAmount", 10, 40, "jump")){
				if(checkIsNaN($("#stockPartAmt").val(), "stockPartAmt", 10, 40, "jump")){
					var stockNum = Number($("#exceedAmount").val())/Number($("#stockPartAmt").val())
					if(Number($("#exceedAmount").val())%Number($("#stockPartAmt").val()) == 0){
						if(stockNum>200){
							AlertDialog.show("根据相关法规，非公开融资项目参与人数不能大于200，请调整。", "stockNum", 10, 20);
						}
						$("#stockNum").val(Math.floor(stockNum * 100) / 100); //单投资人最低投资金额
					}else{
						AlertDialog.show("输入金额不能被整除，请重新输入", "stockPartAmt", 10, 20);
						$("#stockPartAmt,#stockNum").val("");
					}
				}
			}
		}
//		if($(this).attr("id")=="exceedAmount"){
//			$("#exceedAmount").val(test($("#exceedAmount").val()));
//		}
		
	});
	$("#lastRound,#financingAmount,#exceedAmount,#stockPartAmt,#raiseDay").keyup(function(){
		if(isNaN($(this).val())){
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}		
	});
	$("#lastRound").blur(function(){
		checkIsNaN($("#lastRound").val(), "lastRound", 10, 40, "jump");
		//$("#lastRound").val(test($("#lastRound").val()));
	});
	$("#financingAmount").blur(function(){
		checkIsNaN($("#financingAmount").val(), "financingAmount", 10, 40, "jump");
		//$("#financingAmount").val(test($("#financingAmount").val()));
	});
	$("#changeUpload1").click(function(){
		if($("#orgLoanReceiveProve1").val() == ""){
			$("#fileToUpload1").click();
		}
	});
	$("#changeUpload2").click(function(){
		$("#fileToUpload2").click();
	});
	$("#raiseDay").blur(function(){
		checkRaiseDay($("#raiseDay").val(), "raiseDay");
	});
	$("#estimateProfitBtn").click(estimateProfit);
	$("#completeBtn").click(function(){
		saveCrowdFunding();
	});
	
});

//初始化项目基本信息赋值
function initCrowdfundInfo(){
	//成功后表单初始化操作
	$.ajax({
		url: path + "/crowdfunding/getCrowdDetail.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": loanNo},
		success: function(bdata){
			if(bdata["success"]){
				$("#lastRound").val(bdata["msg"]["lastValuation"]);
				$("#financingAmount").val(bdata["msg"]["fundAmt"]);
				$("#exceedAmount").val(bdata["msg"]["overFundAmt"]);
				$("#stockPartAmt").val(bdata["msg"]["stockPartAmt"]);
//				$("#dailyEarningsForecast").val(bdata["msg"]["dailyEarningsForecast"]);
				$("#stockNum").val(bdata["msg"]["stockNum"]);
				$("#raiseDay").val(bdata["msg"]["fundDays"]);
				$("#shareRatio").val(bdata["msg"]["transferRatio"]);
				$("#capitalPurpose").val(bdata["msg"]["capitalUsed"]);

				UE.getEditor("proDetail").ready(function(){
					UE.getEditor("proDetail").setContent(bdata["msg"]["financePlan"]);
				});
				
				var businessLicense = bdata["msg"]["businessLicense"].split(",")
				for(var i = 0;i<businessLicense.length;i++){
					$("#orgLoanReceiveProve1").val($("#orgLoanReceiveProve1").val()+businessLicense[i]);
					$("#uploadInFo1").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+businessLicense[i]+'"><img src="'+cv.fileAddress+businessLicense[i]+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo1","#orgLoanReceiveProve1")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');
				}
				
				var legalCard = bdata["msg"]["legalCard"].split(",")
				for(var i = 0;i<legalCard.length;i++){
					$("#orgLoanReceiveProve2").val($("#orgLoanReceiveProve2").val()+legalCard[i]);
					$("#uploadInFo2").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+legalCard[i]+'"><img src="'+cv.fileAddress+legalCard[i]+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo2","#orgLoanReceiveProve2")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');
				}

			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
	//预计收益
	$.ajax({
		url: path + "/crowdfundingOperateData/selectPageList.html",
		type: "post",
		dataType: "json",
		data:{
			"loanNo":loanNo
		},
		success: function(data){
			var pArr = [], pStr = '', l = data["rows"].length, data = data["rows"];
			for(var i = 0;i<l;i++){
				pArr.push('<div class="clearfix mt20"><div class="txt_div mb10"><textarea style="background:#f2f2f2;" readonly="readonly" class="shr-infoA">'+data[i]["expectedReturn"]+'</textarea></div><div class="rg_operB"><a onClick="modifyProfit(this)" style="background:#1EA5FF;color:#fff;">修改</a><a onClick="deleteProfit(this)" id="'+data[i]["id"]+'" style="color:#1EA5FF;">删除</a></div></div>');
			}
			pStr = pArr.join("");
			$("#estimateProfitList").html(pStr);
		},
		error: function(request){
			console.log("获取投资领域异常");
		}
	});
}

//保存项目基本信息
function saveCrowdFunding(){
	if(checkIsNaN($("#lastRound").val(), "lastRound")){
		if(checkIsNaN($("#financingAmount").val(), "financingAmount")){
			if(checkIsNaN($("#exceedAmount").val(), "exceedAmount")){
				if(parseInt($("#exceedAmount").val()) < parseInt($("#financingAmount").val())){
					AlertDialog.show("超募最大金额需大于等于本次融资金额", "exceedAmount", 10, 40, "jump");
					return false;
				}
				if(checkIsNaN($("#stockPartAmt").val(), "stockPartAmt", 10, 40, "jump")){
					if(parseInt($("#stockPartAmt").val()) > parseInt($("#exceedAmount").val())){
						AlertDialog.show("每份金额不能大于最大融资总额", "stockPartAmt", 10, 40, "jump");
						return false;
					}
					if(parseInt($("#stockNum").val()) > 200){
						AlertDialog.show("根据相关法规，非公开融资项目参与人数不能大于200，请调整。", "stockNum", 10, 40, "jump");
						return false;
					}
//					if(checkIsNaN($("#dailyEarningsForecast").val(), "dailyEarningsForecast", 10, 40, "jump")){
						if(checkRaiseDay($("#raiseDay").val(), "raiseDay", 10, 40, "jump")){
							if(checkShareRatio($("#shareRatio").val(), "shareRatio", 10, 40, "jump")){
								if(Valify.isNull($("#capitalPurpose").val(), "capitalPurpose", -50, 40, "jump")){
									if($("#estimateProfitList>div").length<1){
										AlertDialog.show("请添加预计收益", "estimateProfit", -90, 40, "jump");
										return false;
									}
									if(Valify.isNull(UE.getEditor("proDetail").getContent(), "proDetail_1", -400, 40, "jump")){
										if(Valify.isNull($("#orgLoanReceiveProve1").val(), "changeUpload1", 10, 40, "jump")){
											if(Valify.isNull($("#orgLoanReceiveProve2").val(), "changeUpload2", 10, 40, "jump")){
												$.ajax({
													url: path+"/crowdfunding/updateCrowdFundDetail.html",
													type: "post",
													dataType: "json",
													data: {
														"loanNo":loanNo,
														"businessLicense": $("#orgLoanReceiveProve1").val(),
														"legalCard": $("#orgLoanReceiveProve2").val(),
														"transferRatio": $("#shareRatio").val(),
														"canOver": "1",
														"overFundAmt": $("#exceedAmount").val(),
														"fundAmt": $("#financingAmount").val(),
														"capitalUsed": $("#capitalPurpose").val(),
														"lastValuation": $("#lastRound").val(),
														"financePlan": UE.getEditor("proDetail").getContent(),
														"fundDays":$("#raiseDay").val(),
														"stockPartAmt":$("#stockPartAmt").val(),
//														"dailyEarningsForecast":$("#dailyEarningsForecast").val(),
														"stockNum":$("#stockNum").val()
													},
													success: function(data){
														if(data["success"]){
															window.location.href = path + "/common/enterStock-founder.html?loanNo="+loanNo;
														}else{
															AlertDialog.tip(data["msg"]);
														}
													},
													error: function(request){
														console.log("保存项目基本信息异常");
													}
												});
											}
										}
									}
								}
							}
						}
//					}
				}
			}
		}
	}
}

//添加预计收益
function estimateProfit(){
	if(Valify.isNull($("#estimateProfit").val(), "estimateProfit", -80, 40, "jump")){
		$.ajax({
			url: path + "/crowdfundingOperateData/saveOperateData.html",
			type: "post",
			dataType: "json",
			data:{
				"loanNo":loanNo,
				"expectedReturn":$("#estimateProfit").val()
			},
			success: function(data){
				$.ajax({
					url: path + "/crowdfundingOperateData/selectPageList.html",
					type: "post",
					dataType: "json",
					data:{
						"loanNo":loanNo
					},
					success: function(data){
						var pArr = [], pStr = '', l = data["rows"].length, data = data["rows"];
						for(var i = 0;i<l;i++){
							pArr.push('<div class="clearfix mt20"><div class="txt_div mb10"><textarea readonly="readonly" class="shr-infoA">'+data[i]["expectedReturn"]+'</textarea></div><div class="rg_operB"><a onClick="modifyProfit(this)" style="background:#1EA5FF;color:#fff;">修改</a><a onClick="deleteProfit(this)" id="'+data[i]["id"]+'" style="color:#1EA5FF;">删除</a></div></div>');
						}
						pStr = pArr.join("");
						$("#estimateProfitList").html(pStr);
					},
					error: function(request){
						console.log("获取投资领域异常");
					}
				});
				$("#estimateProfit").val("");
			},
			error: function(request){
				console.log("获取投资领域异常");
			}
		});
	}
}
function deleteProfit(_this){
	$.ajax({
		url: path + "/crowdfundingOperateData/deleteOperateData.html",
		type: "post",
		dataType: "json",
		data:{
			"id":$(_this).attr("id")
		},
		success: function(data){
			$.ajax({
				url: path + "/crowdfundingOperateData/selectPageList.html",
				type: "post",
				dataType: "json",
				data:{
					"loanNo":loanNo
				},
				success: function(data){
					var pArr = [], pStr = '', l = data["rows"].length, data = data["rows"];
					for(var i = 0;i<l;i++){
						pArr.push('<div class="clearfix mt12"><div class="fl txt_div"><textarea readonly="readonly" class="shr-infoA">'+data[i]["expectedReturn"]+'</textarea></div><div class="fl rg_oper rg_oper2"><a style="margin-bottom:10px;" onClick="modifyProfit(this)">修改</a><a onClick="deleteProfit(this)" id="'+data[i]["id"]+'">删除</a></div></div>');
					}
					pStr = pArr.join("");
					$("#estimateProfitList").html(pStr);
				},
				error: function(request){
					console.log("获取投资领域异常");
				}
			});
		},
		error: function(request){
			console.log("获取投资领域异常");
		}
	});
	$(_this).parent().parent().remove();
}
function modifyProfit(_this){
	$(_this).parent().parent().find("textarea").removeAttr("readonly").css("background","#fff");
	$(_this).parent().parent().find("textarea").removeAttr("readonly").focus().blur(function(){
		$(this).attr("readonly","readonly").css("background","#f2f2f2");
	});
}

//验证是否是数字
function checkIsNaN(str, id){
	var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20);
		$("html,body").animate({
			scrollTop: $("#" + id).offset().top -120
		},"800");
		return false;
	}
	if(isNaN(str)){
		AlertDialog.show("请输入正确金额", id, 10, 20);
		$("#" + id).val("");
		$("html,body").animate({
			scrollTop: $("#" + id).offset().top -120
		},"800");
		return false;
	}
	if(Number(str) <= 0){
		AlertDialog.show("请输入大于0的金额", id, 10, 20, "jump");
		$("html,body").animate({
			scrollTop: $("#" + id).offset().top -120
		},"800");
		return false;
	}
	if(!reg.test(str)){
		AlertDialog.show("不能超过两位小数", id, 10, 20, "jump");
		$("html,body").animate({
			scrollTop: $("#" + id).offset().top -120
		},"800");
		return false;
	}
	AlertDialog.hide();
	return true;
}

function fileDelete(_this,id1,id2){
	$(id2).val("");
	$(_this).parent().remove();
	var _num = $(id1).find("div").length;
	for(var f=0;f<_num;f++){
		if(_num>0){
			if($(id2).val()){
				$(id2).val($(id2).val()+","+$(id1).find("div").eq(f).attr("url"));
			}else{
				$(id2).val($(id1).find("div").eq(f).attr("url"));
			}
		}else if(_num<0){
			$(id2).val("");
		}
	}
}

//选择文件之后执行上传  
$('#fileToUpload1').on('change', function() {
    $.ajaxFileUpload({  
        url:path+'/userAuth/uploadAuthFile.html',  
        secureuri:false,  
        fileElementId:'fileToUpload1',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {
        	if(data["success"]){
        		var dataMsg = data.msg
        		if(dataMsg.indexOf(".jpg")>-1||dataMsg.indexOf(".jpeg")>-1||dataMsg.indexOf(".png")>-1){
        			if($("#orgLoanReceiveProve1").val()){
            			var orgLoanReceiveProve = $("#orgLoanReceiveProve1").val()+","+data.msg;
            		}else{
            			var orgLoanReceiveProve = data.msg;
            		}
            		$("#orgLoanReceiveProve1").val(orgLoanReceiveProve);
            		AlertDialog.mtip('上传成功！');
            		$("#uploadInFo1").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+data.msg+'"><img src="'+cv.fileAddress+data.msg+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo1","#orgLoanReceiveProve1")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');	
        		}else{
        			AlertDialog.mtip('格式不正确，请重新上传！');
        		}
        	}else{
				AlertDialog.RZmtip(data.msg);
			}
        },  
        error: function (data, status, e) {  
            alert(e);
        },
        complete:function(XMLHttpRequest,textStatus){
        	
        }
    });  

});
$('#fileToUpload2').on('change', function() {
    $.ajaxFileUpload({  
        url:path+'/userAuth/uploadAuthFile.html',  
        secureuri:false,  
        fileElementId:'fileToUpload2',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {
        	if(data["success"]){
        		var dataMsg = data.msg
        		if(dataMsg.indexOf(".jpg")>-1||dataMsg.indexOf(".jpeg")>-1||dataMsg.indexOf(".png")>-1){
        			if($("#orgLoanReceiveProve2").val()){
            			var orgLoanReceiveProve = $("#orgLoanReceiveProve2").val()+","+data.msg;
            		}else{
            			var orgLoanReceiveProve = data.msg;
            		}
            		$("#orgLoanReceiveProve2").val(orgLoanReceiveProve);
            		AlertDialog.mtip('上传成功！');
            		$("#uploadInFo2").append('<div class="fl rel mr20" style="margin-top:20px;"  url="'+data.msg+'"><img src="'+cv.fileAddress+data.msg+'" width="260" height="216"/><a class="close_btn" onClick=\'fileDelete(this,"#uploadInFo2","#orgLoanReceiveProve2")\'><img src="'+path+'/images/letv/close2.png"/></a></div>');	
        		}else{
        			AlertDialog.mtip('格式不正确，请重新上传！');
        		}
        	}else{
				AlertDialog.RZmtip(data.msg);
			}
        },  
        error: function (data, status, e) {  
            alert(e);
        },
        complete:function(XMLHttpRequest,textStatus){
        	
        }
    });  

});

//文件删除
function fileDeleteWJ(_this,id1,id2){
	$(id2).val("");
	$(_this).parent().remove();
	var _num = $(id1).find("div").length;
	for(var f=0;f<_num;f++){
		if(_num>0){
			if($(id2).val()){
				$(id2).val($(id2).val()+","+$(id1).find("div").eq(f).attr("url"));
			}else{
				$(id2).val($(id1).find("div").eq(f).attr("url"));
			}
		}else if(_num<0){
			$(id2).val("");
		}
	}
	$.ajaxFileUpload({
        url:path+'/fileUpload/removeFile.html?Id='+$(_this).attr("vid")+'',  
        secureuri:false,  
        fileElementId:'fileToUpload2',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {
        	if(data["success"]){
			}else{
				AlertDialog.RZmtip(data.msg);
			}
        },  
        error: function (data, status, e) {  
            alert(e);
        },
        complete:function(XMLHttpRequest,textStatus){
        	
        }
    }); 
}

//验证筹集天数
function checkRaiseDay(str ,id){
	if(isNaN(str)){
		AlertDialog.show("众筹期限应该为数字", id, 0, 30, "jump");
		return false;
	}else{
		if(!str || Number(str) < 10 || Number(str) > 90){
			AlertDialog.show("众筹期限为10-90天", id, 0, 30, "jump");
			return false;
		}
	}
	AlertDialog.hide();
	return true;
}
//验证出让比例
function checkShareRatio(str ,id){
	if(isNaN(str)){
		AlertDialog.show("出让比例应该为数字", id, 0, 30, "jump");
		return false;
	}else{
		if(!str || Number(str) < 0 || Number(str) > 1){
			AlertDialog.show("请输入0-1之间的小数", id, 0, 30, "jump");
			return false;
		}
	}
	AlertDialog.hide();
	return true;
}