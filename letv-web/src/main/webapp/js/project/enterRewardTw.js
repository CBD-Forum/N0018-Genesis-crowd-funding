if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}
var loanNo = getQueryString("loanNo");
$(function(){
	$("input").keyup(function(){
		if($(this).val().indexOf(" ")>=0){
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	})
	$("#backContent").keyup(function(){
		if($(this).val().length>200){
			AlertDialog.show("介绍不能超过200个字！", "backContent", -50, 30, "jump");
			$(this).val($(this).val().substring(0,200));
		}
	});
	$(".lch_nav li.lch5").click(function(){
		window.location.href = path + "/common/enterReward.html?loanNo="+loanNo;
	});
	initCrowdfundInfo();
	getServiceFeeScale();//查询服务费比例
	$("#raiseAmt").blur(function(){
		$("#chargeFee").text((FeeScale*$("#raiseAmt").val()).toFixed(2));
	});
	$("#carriage").blur(function(){
		freight($("#carriage").val(), "carriage")
	});
	moduleCotrl();
	showProvince();
	initBackSetList(); //初始化回报设置
	ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传项目头图
	ecreateWebUploader("image_file1", "imghead1", "loan_logo_url1", "imgheadLi1");//上传项目封面
	
	$("#saveBackSet").click(saveBackSet);//保存回报设置
	$("#addBackSet").click(addBackSet);//添加回报设置按钮

	$("#backTypeC input").change(function(){
		 var id = $(this).prop("id");
		 if(id == "cy"){
			 $("#backTypeC").attr("cj","1");
			 $("#backTypeDiv").show();
			 //$("#carriageTypeDiv").hide();
			 $("#carriage").prop("disabled",true).val("0");
			 $("#backType input").change(function(){
				 var id = $(this).prop("id");
				 if(id == "xn"){
					 $("#carriage").prop("disabled",true).val("0");
				 }else{
					 $("#carriage").prop("disabled",true);
				 }
			});
		 }else{
			 $("#backTypeC").attr("cj","0");
			 $("#backTypeDiv").hide();
			 $("#sw").prop("checked",true);
			 $("#xn").prop("checked",false);
			 $("#carriage").prop("disabled",false);
			 //$("#carriageTypeDiv").show();
			 $("#prizeNum,#prizeInvestNum,#prizeFullNum").val("");
			 $("#backType input").change(function(){
				 var id = $(this).prop("id");
				 if(id == "xn"){
					 $("#carriage").prop("disabled",true).val("0");
				 }else{
					 $("#carriage").prop("disabled",false);
				 }
			});
		 }
	});
	//$("#completeBtu").attr("href",path+"/common/enterRewardTr.html?loanNo="+loanNo);

	$("#completeBtu").click(function(){
		if($("#soprtList>li").length>0){
			if(Number($("#priceAmt").val())<Number($("#overFundAmt").val())){
				//AlertDialog.tip("回报总金额不足项目最高上限，请继续添加回报");
				AlertDialog.confirmReward(enterRewardUrl,null,"项目已保存回报金额小于设置最高上限金额，您确定提交吗？","确定提交","继续添加",null);
			}else{
				window.location.href = path+"/common/enterRewardTr.html?loanNo="+loanNo
			}
		}else{
			AlertDialog.tip("请先保存回报");
		}
	});
	
});

function enterRewardUrl(){
	window.location.href = path+"/common/enterRewardTr.html?loanNo="+loanNo
}

//查询服务费比例
function getServiceFeeScale(){
	$.ajax({
		url: path+"/crowdfunding/getServiceFeeScale.html",
		type: "post",
		dataType: "json",
		data: {},
		success: function(data){
			if(data["success"]){
				FeeScale = data["msg"]["serviceFeeScale"];
			}
		},
		error: function(request){
			console.log("保存项目基本信息异常");
		}
	});
}

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
				$("#overFundAmt").val(bdata["msg"]["overFundAmt"]);//最高上限
			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
}

//删除项目展示图片
function delImage(id){
	$("#" + id).prev().removeAttr("src").parent().hide();
	urlArr.splice(Number($(this).attr("i")), 1);
	$("#proPhoto_url").val(urlArr.join(","));
}
//控制页面每个模块的展开和关闭
function moduleCotrl(){
	$.each($("a[type='pause']"),function(k, v){
		$(v).click(function(){
			if($(this).attr("pause") != "1"){ //上一步未成功
				return false;
			}
			if($("#" + $(this).attr("name")+"Box").css("display") == "none"){ //需要展开
				$("#" + $(this).attr("name")+"Box").fadeIn();
				$(this).children("span").text("收起");
			}else{
				$("#" + $(this).attr("name")+"Box").fadeOut();
				$(this).children("span").text("展开");
			}
			if($(this).attr("name") == "backSetPause"){ //回报设置
				if($("#enterTipDiv").css("display") == "none"){
					$("#enterTipDiv").show();
				}else{
					$("#enterTipDiv").hide();
				}
				if($(".back_table").css("display") == "none"){
					$(".back_table").show();
				}else{
					$(".back_table").hide();
				}
				if($("#soprtList tr").length > 0){
					$("#overBackSet").show().click(showProData);
				}
			}
		});
	});
}
//保存发起人基本信息
function updateUserStuff(){
	if(Valify.isNull($("#enterProUser").val(), "enterProUser", 10, 30, "jump")){
		if(Valify.isNull($("#cardCode").val(), "cardCode", 10, 30, "jump")){
			if(Valify.isNull($("#licenceFront_url").val(), "licenceFrontBtn", -155, 30, "jump")){
				if(Valify.isNull($("#licenceRevese_url").val(), "licenceReveseBtn", -155, 30, "jump")){
					if(Valify.phone($("#mobile").val(), "mobile", "jump")){
						$.ajax({
							url: path + "/crowdfunding/updateUserStuff.html",
							type: "post",
							dataType: "json",
							data: {
								"companyName": $("#enterProUser").val(),
								"certNo": $("#cardCode").val(),
								"idCardFront": $("#licenceFront_url").val(),
								"idCardBack": $("#licenceRevese_url").val(),
								"mobile": $("#mobile").val()
							},
							success: function(data){
								if(data["success"]){
									$('html,body').animate({
										scrollTop : '350px'
									}, 800);
									$("#enterPauseBox").fadeOut(); //区域隐藏
									$("#enterPauseTip").text("保存成功").show().animate({
										marginLeft: "10px"
									},1500); //成功效果
									$("#enterTipDiv").fadeIn();
									$("#back_table").show();
									if($("#soprtList tr").length > 0){
										$("#overBackSet").show().click(showProData);
									}
									$("#backSetPauseBox").fadeIn();
									$("a[name='enterPause']").children().text("展开");
									$("a[name='backSetPause']").children().text("收起");
									$("a[name='backSetPause']").attr("pause", "1");
									
									//顶部步骤进度改变
									$("#stepDiv>dl").eq(2).addClass("cur");
								}else{
									$('html,body').animate({
										scrollTop : '350px'
									}, 800);
									$("#enterPauseTip").text(data["msg"]).show().animate({
										marginLeft: "10px"
									},1500); //失败效果
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
	}else{
		$('html,body').animate({
			scrollTop : $("#enterProUser").position().top-100
		}, 800);
	}
}
//保存回报设置
function saveBackSet(){
//	if($("#soprtList>li").length>0){
//		var surplusAmt = (Number($("#overFundAmt").val())-Number($("#priceAmt").val()));
//		if(surplusAmt%Number($("#price").val())!=0){
//			AlertDialog.show("您输入的支持金额无法得到完整份数，请调整支持金额", "price", 10, 20, "jump");
//			return false;
//		}
//		if(Number($("#limitPeople").val())>surplusAmt/Number($("#price").val())){
//			AlertDialog.show("超出募集金额最高上限，请调整份数", "limitPeople", 10, 20, "jump");
//			return false;
//		}
//	}modifyAmt
	var surplusAmt = (Number($("#overFundAmt").val())-Number($("#priceAmt").val()));
	if($(this).attr("num")=="1"){
		surplusAmt+=Number($("#modifyAmt").val());
	}
	
	if($("#price").val() && $("#limitPeople").val()){
		if(Number(surplusAmt)<Number($("#price").val())*Number($("#limitPeople").val())){
			AlertDialog.tipA("您的回报总金额超出最高上限金额，请调整！");
			return false;
		}
	}

	//$("#saveBackSet").unbind("click").click(saveBackSet);
	//首先查看是编辑回报设置还是添加回报设置
	var burl, bd = {};
	if($("#carriage").val()){
		var carriage = $("#carriage").val()
	}else{
		var carriage = 0
	}
	if(!$("#backSetPauseBox").attr("editp")){ //添加
		burl = path + "/crowdfunding/saveBackSet.html";
		bd = {
				"loanNo": loanNo,
				"amt": $("#price").val(),//支持金额
				"backContent": $("#backContent").val(), //回报内容
				"photoUrl1": $("#loan_logo_url").val(),//说明图片
				"numberLimits": $("#limitPeople").val(),//限定名额
				"backTime": $("#backTime").val(),//回报时间
				"prizeNum": $("#prizeNum").val(),
				"prizeInvestNum": $("#prizeInvestNum").val(),
				"prizeFullNum": $("#prizeFullNum").val(),
				"prizeDrawFlag":$("#backTypeC").attr("cj"),
				"backType": $("#backType input").prop("checked") ? "S": "X",//回报类型
//				"dailyProfit":$("#dailyProfit").val(),
				"transFee": carriage//,//运费
				//"backLable": $("#return_type input").prop("checked") ? "P": "M"//回报展示类别
			};
	}else{
		burl = path + "/crowdfunding/updateBackSet.html";
		bd = {
				"id": $("#backSetPauseBox").attr("editp"),
				"loanNo": loanNo,
				"amt": $("#price").val(),//支持金额
				"backContent":$("#backContent").val(), //回报内容
				"photoUrl1": $("#loan_logo_url").val(),//说明图片
				"numberLimits": $("#limitPeople").val(),//限定名额
				"backTime": $("#backTime").val(),//回报时间
				"prizeNum": $("#prizeNum").val(),
				"prizeInvestNum": $("#prizeInvestNum").val(),
				"prizeFullNum": $("#prizeFullNum").val(),
				"prizeDrawFlag":$("#backTypeC").attr("cj"),
//				"dailyProfit":$("#dailyProfit").val(),
				"backType": $("#backType input").prop("checked") ? "S": "X",//回报类型
				"transFee": carriage//,//运费
				//"backLable": $("#return_type input").prop("checked") ? "P": "M"//回报展示类别
			};
	}
	

	if(checksportNum($("#price").val(), "price")){
		if(Valify.isNull($("#backContent").val(), "backContent", -40, 20, "jump")){
			if(limitPlaces($("#limitPeople").val(), "limitPeople")){
				if($("#backTypeC").attr("cj") == "1"){
					if(prizeInvest($("#prizeNum").val(), "prizeNum","中奖名额")){
						if(prizeInvest($("#prizeInvestNum").val(), "prizeInvestNum","购买上限")){
							if(prizeFull($("#prizeFullNum").val(), "prizeFullNum")){
								if(Valify.isNull($("#backTime").val(), "backTime", 0, 30, "jump")){
									if(freight($("#carriage").val(), "carriage")){
										$("#bgpop").show();
										$.ajax({
											url: burl,
											type: "post",
											dataType: "json",
											data: bd,
											success: function(data){
												if(data["success"]){
													AlertDialog.tip("保存回报设置成功！");
													$(this).attr("num","0");
													//initBackSetList();
													setTimeout(function(){
														window.location.reload();
													},2000);
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
				}else{
					if(Valify.isNull($("#backTime").val(), "backTime", 0, 30, "jump")){
						if(freight($("#carriage").val(), "carriage")){
							$.ajax({
								url: burl,
								type: "post",
								dataType: "json",
								data: bd,
								success: function(data){
									if(data["success"]){
										AlertDialog.tip("保存回报设置成功！");
										//initBackSetList();
										setTimeout(function(){
											window.location.reload();
										},2000);
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
}
//验证支持金额
function checksportNum(str, id){
	var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20, "jump");
		return false;
	}else{
		if(isNaN(str)){
			AlertDialog.show("请输入正确金额", id, 10, 20, "jump");
			return false;
		}
		if(Number(str) <= 0){
			AlertDialog.show("请输入大于0的金额", id, 10, 20, "jump");
			return false;
		}
		if(!reg.test(str)){
			AlertDialog.show("不能超过两位小数", id, 10, 20, "jump");
			return false;
		}
	}
	AlertDialog.hide();
	return true;
}
//验证限定名额
function limitPlaces(str, id){
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20, "jump");
		return false;
	}else{
		if(isNaN(str)){
			AlertDialog.show("请输入正确限定名额", id, 10, 20, "jump");
			return false;
		}
		if(Number(str) < 1){
			AlertDialog.show("输入名额必须大于0", id, 10, 20, "jump");
			return false;
		}
//		if(Number(str) < 0){
//			AlertDialog.show("请输入正确限定名额", id, 10, 20, "jump");
//			return false;
//		}
	}
	AlertDialog.hide();
	return true;
}
//购买上限
function prizeInvest(str, id ,center){
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20, "jump");
		return false;
	}else{
		if(isNaN(str)){
			AlertDialog.show("请输入正确数字", id, 10, 20, "jump");
			return false;
		}
		if(Number(str) < 1){
			AlertDialog.show("请输入正确数字", id, 10, 20, "jump");
			return false;
		}
		if(Number(str) > Number($("#limitPeople").val())){
			AlertDialog.show(center+"不能大于限定名额", id, 10, 20, "jump");
			return false;
		}
	}
	AlertDialog.hide();
	return true;
}
//购买上限
function prizeFull(str, id ,center){
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20, "jump");
		return false;
	}else{
		if(isNaN(str)){
			AlertDialog.show("请输入正确数字", id, 10, 20, "jump");
			return false;
		}
		if(Number(str) < 1){
			AlertDialog.show("请输入正确数字", id, 10, 20, "jump");
			return false;
		}
		if(Number(str) < Number($("#prizeNum").val())){
			AlertDialog.show("满额抽奖必须大于等于中奖名额", id, 10, 20, "jump");
			return false;
		}
		if(Number(str) > Number($("#limitPeople").val())){
			AlertDialog.show("满额抽奖不能大于限定名额", id, 10, 20, "jump");
			return false;
		}
	}
	AlertDialog.hide();
	return true;
}
//运费验证
function freight(str, id){
//	var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
	var reg = /^(0|\+?[1-9][0-9]*)$/;
//	if(!str){
//		AlertDialog.show("请输入运费", id, 10, 20, "jump");
//		return false;
//	}
	if(str){
		if(isNaN(str)){
			AlertDialog.show("请输入正确金额", id, 10, 20, "jump");
			return false;
		}
		if(Number(str) < 0){
			AlertDialog.show("请输入大于0的金额", id, 10, 20, "jump");
			return false;
		}
		if(!reg.test(str)){
//			AlertDialog.show("不能超过两位小数", id, 10, 20, "jump");
			AlertDialog.show("请输入正确金额", id, 10, 20, "jump");
			return false;
		}
	}

	AlertDialog.hide();
	return true;
}

//检测视频链接
function checkVideoUrl(str, id){
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20);
		return false;
	}
	if(str.substring(str.length-3, str.length) != "swf"){
		AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 20);
		return false;
	}
	AlertDialog.hide();
	return true;
}

function initCrowdfundDetail(){
	var pArr = [], pl;
	$.ajax({
		url: path + "/crowdfunding/getCrowdDetail.html",
		type: "post",
		dataType: "json",
		data: {"loanNo": $("#projectLoanNo").val()},
		success: function(bdata){
			if(bdata["success"]){
				var hArr = [], hStr = '';
				hArr.push(bdata["msg"]["loanDetail"]);
				hStr = hArr.join("");
				UE.getEditor("proDetail").ready(function(){
					UE.getEditor("proDetail").setContent(hStr);
				});
				
				$("#vedio").val(bdata["msg"]["loanVideo"]);
				$("#vedioInfo").val(bdata["msg"]["videoDes"]);
				$("#mobileVideo").val(bdata["msg"]["houseDeveloper"]);
				//顶部进度改变
				if(bdata["msg"]["videoDes"]){
					$("#stepDiv>dl").eq(4).addClass("cur");
				}
				$.ajax({
					url: path + "/crowdfunding/getCrowdPhotos.html",
					type: "post",
					dataType: "json",
					data: {"loanNo": $("#projectLoanNo").val()},
					success: function(pdata){
						if(pdata["success"]){
							if(pdata["msg"]["loanPhotos"]){
								pArr = pdata["msg"]["loanPhotos"].split(","), pl = pArr.length;
								for(var i=0;i<pl-1;i++){
									$("#proPhoto_url").val(pArr[i]);
									$("#proImageLi").find("img").eq(i).attr("src", cv["fileAddress"] + "/" +  pArr[i]);
									$("#proImageLi>div").eq(i).show();
								}
								$("#proImageLi").show();	
							}
						}
					}
				});
			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
}
//发起人基本信息
function initUserInfo(){
	$.ajax({
		url: path + "/crowdfunding/getUserStuffById.html",
		type: "post",
		dataType: "json",
		data: {},
		success: function(bdata){
			if(bdata["success"]){
				$("#enterProUser").val(bdata["msg"]["companyName"]);
				$("#cardCode").val(bdata["msg"]["certNo"]);
				$("#mobile").val(bdata["msg"]["mobile"]);
				$("#licenceFrontDiv").show();
				//$('#licenceFrontImage').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["idCardFront"]);
				if(bdata["msg"]["idCardFront"]){
					$('#licenceFrontImage').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["idCardFront"]);	
				}else{
					$('#licenceReveseImage').attr("src", "");	
				}
				$("#licenceReveseDiv").show();
				//$('#licenceReveseImage').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["idCardBack"]);
				if(bdata["msg"]["idCardFront"]){	
					$('#licenceReveseImage').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["idCardBack"]);	
				}else{
					$('#licenceReveseImage').attr("src", "");	
				}
				//顶部步骤进度改变
				if(bdata["msg"]["companyName"]){
					$("#stepDiv>dl").eq(2).addClass("cur");
				}
			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
}
//初始化回报设置列表赋值
function initBackSetList(){
	var setArr = [], setStr = '', l;
	//成功后表单初始化操作
	$("#price").val("");
	$("#backContent").val("");
	$("#backImg_url").val("");
	$("#backImgLi").hide();
	$("#limitPeople").val("0");
	//$("#carriage").val("0");
	//查询保存的回报设置，进行页面填充
	
	$.ajax({
		url: path + "/crowdfunding/getBackSetList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo": loanNo
		},
		success: function(bdata){
			if(bdata["success"]){
				l = bdata["msg"]["rows"].length, bdata = bdata["msg"]["rows"];
				$("#bsetIndex").text("0" + (l+1));
				
				//顶部步骤进度改变
				if(l > 0){
					$("#stepDiv>dl").eq(3).addClass("cur");
				}
				$("#priceAmt").val("");
				for(var i=0;i<l;i++){
					setArr.push('<li class="clearfix">');
					setArr.push('<span class="wd1">0'+(i+1)+'</span>');
					setArr.push('<span class="wd2">'+bdata[i]["amt"].toFixed(2)+companyCode+'</span>');
					if(bdata[i]["numberLimits"] == 0){
						setArr.push('<span class="wd3">不限名额</span>');
					}else{
						setArr.push('<span class="wd3">'+bdata[i]["numberLimits"]+'位</span>');
					}
					setArr.push('<span class="wd4">'+bdata[i]["backContent"]+'</span>');
					setArr.push('<span class="wd5"><a class="mr20 col_blue" href="javascript:void(0);" bid='+bdata[i]["id"]+' onclick=deleteBackSet(event)>删除</a><a class="col_blue" href="javascript:void(0);" numberLimits="'+bdata[i]["numberLimits"]+'" amt="'+bdata[i]["amt"]+'" bid='+bdata[i]["id"]+' onclick=modifyBackSet(event)>修改</a></span>');
					setArr.push('</li>');
					setArr.push('');
					if(bdata[i]["prizeDrawFlag"] == "1"){
						$("#backTypeC").attr("cj","0");
						$("#backType input").change(function(){
							 var id = $(this).prop("id");
							 if(id == "xn"){
								 $("#carriage").prop("disabled",true).val("0");
							 }else{
								 $("#carriage").prop("disabled",false);
							 }
						});
					}
					$("#priceAmt").val(Number($("#priceAmt").val())+bdata[i]["amt"]*bdata[i]["numberLimits"]);
				}
				setStr = setArr.join("");
				$("#soprtList").html(setStr);
				addBackSet();
				
			}
		},
		error: function(request){
			console.log("查询回报设置列表异常");
		}
	});
	
//	$.ajax({
//		url: path + "/crowdfunding/checkLoanIsPrizeDrawFlag.html",
//		type: "post",
//		dataType: "json",
//		data: {
//			"loanNo": loanNo
//		},
//		success: function(data){
//			if(data["msg"]){
//				$("#backTypeC").parent().hide();
//				$("#backTypeDiv").hide();
//			}else{
//				$("#backTypeC").parent().show();
//				$("#backTypeDiv").show();
//			}
//		},
//		error: function(request){
//			console.log("查询回报设置列表异常");
//		}
//	});
	
}
//删除一条回报设置
function deleteBackSet(event){
	var obj = event.srcElement || event.target;
	var id = $(obj).attr("bid");
	AlertDialog.confirm(delBS, null, "你确定要删除这条回报设置吗？", "删除", "取消", id);
}
//删除回报设置实现方法
function delBS(id){
	$.ajax({
		url: path + "/crowdfunding/deleteBackSet.html",
		type: "post",
		dataType: "json",
		data: {"id": id},
		success: function(data){
			if(data["success"]){
				initBackSetList();
				AlertDialog.tip("删除成功！");
			}
		},
		error: function(request){
			console.log("获取删除回报设置信息异常");
		}
	});
}
//编辑一条回报设置
function modifyBackSet(event){
	var obj = event.srcElement || event.target;
	var id = $(obj).attr("bid");
	var numberlimits = $(obj).attr("numberlimits");
	var amt = $(obj).attr("amt");
	$("#modifyAmt").val(Number(numberlimits)*Number(amt));
	AlertDialog.confirm(modifyBS, null, "你确定要编辑这条回报设置吗？", "编辑", "取消", id);
}
//编辑回报设置实现方法
function modifyBS(id){
	$('#imghead').parent().hover(function(){
		var _this = $(this);
		_this.children("div").slideDown("slow");
		_this.find("div").find("img").click(function(){
			_this.hide();
			$("#loan_logo_url").val("");
			ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传项目头图
		});
	},function(){
		$(this).children("div").slideUp("slow");
		
	});
	$("#bsetIndex").text($("a[bid='"+id+"']").parent().siblings().first().text());
	$("#backSetPauseBox").attr("editp", id);
	$("#saveBackSet").attr("num","1");
	$.ajax({
		url: path + "/crowdfunding/getBackSetById.html",
		type: "post",
		dataType: "json",
		data: {"id": id},
		success: function(data){
			if(data["success"]){
				$("#price").val(data["msg"]["amt"]);
//				$("#dailyProfit").val(data["msg"]["dailyProfit"]);
				$("#backContent").val(data["msg"]["backContent"]);
				//UE.getEditor("proDetail").setContent(data["msg"]["backContent"]);
				if(data["msg"]["photoUrl1"]){ //此条回报设置有数据
					$("#loan_logo_url").val(data["msg"]["photoUrl1"]);
					$("#imgheadLi").show();
					$("#imghead").attr("src",cv["fileAddress"]+data["msg"]["photoUrl1"]);
				}else{
					$("#imgheadLi").hide();
				}
				$("#limitPeople").val(data["msg"]["numberLimits"]);
				$("#backTime").val(data["msg"]["backTime"]);
				$("#carriage").val(data["msg"]["transFee"]);
				
				if(data["msg"]["prizeDrawFlag"]>0){
					$("#backTypeDiv").show();
					$("#prizeNum").val(data["msg"]["prizeNum"]);
					$("#prizeInvestNum").val(data["msg"]["prizeInvestNum"]);
					$("#prizeFullNum").val(data["msg"]["prizeFullNum"]);
					$("#backTypeC").attr("cj","1");
					$("#carriage").prop("disabled",true);
				}else{
					$("#backTypeDiv").hide();
					$("#backTypeC").parent().hide();
					$("#backTypeC").attr("cj","0");
					$("#carriage").prop("disabled",false);
					$("#backType input").change(function(){
						 var id = $(this).prop("id");
						 if(id == "xn"){
							 $("#carriage").prop("disabled",true).val("0");
						 }else{
							 $("#carriage").prop("disabled",false);
						 }
					});
				}
				
				if(data["msg"]["backType"] == "X"){
					$("#xn").prop("checked", true);
					$("#carriage").prop("disabled",true);
				}
				if(data["msg"]["backLable"] == "M"){
					$("#sj").prop("checked", true);
				}
			}
		},
		error: function(request){
			console.log("获取编辑回报设置信息异常");
		}
	});
}
//添加回报按钮，清空表单数据
function addBackSet(){
	var sl = $("#soprtList tr").length;
	$("#bsetIndex").text("0" + (sl+1));
	$("#backSetPauseBox").removeAttr("editp");
	$("#price").val("");
	$("#backContent").val("");
	$("#backImg_url").val("");
	$("#prizeNum").val("");
	$("#prizeInvestNum").val("");
	$("#prizeFullNum").val("");
	$("#backImgLi").hide();
	$("#limitPeople").val("");
	$("#backTime").val("");
//	$("#dailyProfit").val("");
	$("#xn").removeProp("checked");
	$("#sw").prop("checked",true);
	$("#carriage").prop("disabled",false);
	$("#carriage").val("");
	$("#sj").removeProp("checked");
	$("#pt").prop("checked",true);
	$(".upd-pA").css("display","none");
	$("#imghead").attr("src","");
	$("#loan_logo_url").attr("value","");
	$.ajax({
		url: path + "/crowdfunding/checkLoanIsPrizeDrawFlag.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo": loanNo
		},
		success: function(data){
			if(data["msg"]){
				$("#cy").removeProp("checked");
				$("#bcy").prop("checked",true);
				$("#backTypeDiv").hide();
				$("#backTypeC").parent().hide();
				$("#backTypeC").attr("cj","0");
				//$("#carriageTypeDiv").show();
				$("#carriage").prop("disabled",false);
			}else{
				$("#bcy").removeProp("checked");
				$("#cy").prop("checked",true);
				$("#backTypeC").parent().show();
				$("#backTypeDiv").show();
				$("#backTypeC").attr("cj","1");
				//$("#carriageTypeDiv").hide();
				$("#carriage").prop("disabled",true).val("0");
			}
		},
		error: function(request){
			console.log("查询回报设置列表异常");
		}
	});
}
//预备编辑项目资料
function showProData(){
	$('html,body').animate({
		scrollTop : '350px'
	}, 800);
	$("#backPauseTip").text("保存成功").show().animate({
		marginLeft: "10px"
	},1500); //成功效果
	$(".back_table").hide();
	$("#enterTipDiv").hide();
	$("#backSetPauseBox").fadeOut();
	$("a[name='proDataPause']").attr("pause", "1").children("span").text("收起");
	$("#proDataPauseBox").fadeIn();
	
	//顶部步骤进度改变
	$("#stepDiv>dl").eq(2).addClass("cur");
}
//提交审核
function submitLoan(){
	if($("#checkLoanView").prop("checked")){
		AlertDialog.hide();
		$.ajax({
			url: path + "/crowdfunding/submit.html",
			type: "post",
			dataType: "json",
			data: {"loanNo": $("#projectLoanNo").val()},
			success: function(data){
				if(data["success"]){
					$('html,body').animate({
						scrollTop : '400px'
					}, 800);
					AlertDialog.tip("提交成功！");
					setTimeout(function(){
						window.location.href = path + "/common/myCenter.html";
					},2000);
				}
			},
			error: function(request){
				console.log("提交项目审核异常");
			}
		});
	}else{
		AlertDialog.show("请阅读并勾选服务协议", "checkLoanView", 15, 25);
	}
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
			
//			$("#bgpop").show();
			var al = (cv["winW"]-580)/2, at = (cv["winH"]-$(".agree_box").height())/2;
			$(".agree_box").css({"left":al+"px", "top":at+"px"}).show();
			$(".agree_close").click(function(){
				$(".agree_box").fadeOut();
//				$("#bgpop").hide();
			});
		},
		error: function(request){
			console.log("获取协议范文异常");
		}
	});
}
//验证项目名称
function checkProName(str, id){
	if(!str){
		AlertDialog.show("项目名称不能为空", id, 10, 20, "jump");
		return false;
	}
	if(str.length > 40){
		AlertDialog.show("项目名称不能超过40个字", id, 10, 20, "jump");
		return false;
	}
	AlertDialog.hide();
	return true;
}
//验证筹集金额
function checkRaisAmt(str, id){
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20, "jump");
		return false;
	}else{
		if(isNaN(str) || Number(str) <= 0){
			AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 20, "jump");
			return false;
		}
	}
	AlertDialog.hide();
	return true;
}
//验证筹集天数
function checkRaiseDay(str ,id){
	if(isNaN(str)){
		AlertDialog.show("募集天数应该为数字", id, 0, 30, "jump");
		return false;
	}else{
		if(!str || Number(str) < 10 || Number(str) > 90){
			AlertDialog.show("募集天数为10-90天", id, 0, 30, "jump");
			return false;
		}
	}
	AlertDialog.hide();
	return true;
}
//展示省份下拉数据
function showProvince(id){
	//获取省份
	var pArr = [], pStr = '', l;
	$.ajax({
		url: path + "/area/getProvince.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			pArr.push('<option value="">请选择省</option>');
			for(var i=0;i<l;i++){
				pArr.push('<option value="'+data[i]["id"]+'">'+data[i]["shortName"]+'</option>');
			}
			pStr = pArr.join("");
			$("#province").html(pStr);
			if(id){
				$("#province").val(id);
			}
			//点击省份后，根据省份id查询城市
			$("#province").change(function(){
				showCity($(this).val());
			});
		},
		error: function(request){
			console.log("获取省份信息异常");
		}
	});
}
//展示城市下拉数据
function showCity(pid, cid){
	var cArr = [], cStr = '', cl, list;
	$.ajax({
		url: path + "/area/getCity.html",
		type: "post",
		dataType: "json",
		data: {"pid": pid},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			cl = data["msg"]["rows"].length, list = data["msg"]["rows"];
			cArr.push('<option value="">请选择市</option>');
			for(var i=0;i<cl;i++){
				cArr.push('<option value="'+list[i]["id"]+'">'+list[i]["name"]+'</option>');
			}
			cStr = cArr.join("");
			$("#city").html(cStr);
			if(cid){
				$("#city").val(cid);
			}
			//点击城市后，根据城市id查询县城
			$("#city").change(function(){
				showCounty($(this).val());
			});
		},
		error: function(request){
			console.log("获取城市信息异常");
		}
	});
}
//展示县城下拉数据  
function showCounty(cid, coid){
	var cArr = [], cStr = '', cl, list;
	$.ajax({
		url: path + "/area/getCity.html",
		type: "post",
		dataType: "json",
		data: {"pid": cid},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			cl = data["msg"]["rows"].length, list = data["msg"]["rows"];
			cArr.push('<option value="">请选择县</option>');
			for(var i=0;i<cl;i++){
				cArr.push('<option value="'+list[i]["id"]+'">'+list[i]["name"]+'</option>');
			}
			cStr = cArr.join("");
			$("#county").html(cStr);
			if(coid){
				$("#county").val(coid);
			}
		},
		error: function(request){
			console.log("获取城市信息异常");
		}
	});
}
function ecreateWebUploader(pickId, showId, setValueId, parentId){
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
	    fileSingleSizeLimit: 5 * 1024 * 1024    // 2 M
	});
	
	uploader.on('error', function (handler) {
		if("Q_EXCEED_SIZE_LIMIT"==handler){
			AlertDialog.show("文件超过5M，请重新上传", pickId, -90, 60);
		}else if("Q_TYPE_DENIED"==handler){
			AlertDialog.show("文件类型错误", pickId, -90, 60);
		}else if("Q_EXCEED_NUM_LIMIT"==handler){
			AlertDialog.show("添加的文件数量超出", pickId, -90, 60);
		}
    });
	uploader.on('uploadSuccess', function (file, ret) {
//        var $file = $('#' + file.id);
        try {
            var responseText = (ret._raw || ret),
                json = $.parseJSON(responseText);
            if (json["success"]) {
            	AlertDialog.hide();
            	//预览图片
            	$("#" + parentId).show();
            	if(showId == "pro5"){ //项目展示图片，需要5张，情况特殊
            		urlArr.push(json["msg"]);
            		$("#" + setValueId).val(urlArr.join(","));
            		for(var i=0;i<5;i++){
            			if(!$("#proImageLi").find("img").eq(i).attr("src")){ //从前面数，某一个还未添加路径
            				$("#proImageLi").find("img").eq(i).attr("src", cv["fileAddress"] + "/" + json["msg"])
            				$("#" + parentId).children("div").eq(i).show().children("div").attr("i",i);
            				break;
            			}
            		}
            		$.each($("#proImageLi").children("div"),function(k, v){
            			$(v).mouseover(function(){
                			$(this).children("div").show().unbind("click").bind("click",function(){
                				AlertDialog.confirm(delImage, null, "你确定要删除这张图片吗？", "删除", "取消", $(this).attr("id"));
                			});
                		}).mouseout(function(){
                			$(this).children("div").hide();
                		});
            		});
            	}else{
            		$('#'+showId).attr("src", cv["fileAddress"] + "/" + json["msg"]).parent().hover(function(){
            			var _this = $(this);
            			_this.children("div").slideDown("slow");
            			_this.find("div").find("img").click(function(){
            				_this.hide();
            				$("#" + setValueId).val("");
            				ecreateWebUploader(pickId, showId, setValueId, parentId)
            			});
            		},function(){
            			$(this).children("div").slideUp("slow");
            			
            		})
            		$("#" + setValueId).val(json["msg"]);
            	}
            } else {
            	AlertDialog.show(json["msg"], pickId, 10, 30);
//                $file.find('.error').text(json.state).show();
            }
        } catch (e) {
//            $file.find('.error').text(lang.errorServerUpload).show();
        }
    });
	
	return uploader;
}