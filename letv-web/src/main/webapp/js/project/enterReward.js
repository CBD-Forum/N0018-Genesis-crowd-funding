if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}
var FeeScale;
var loanNo = getQueryString("loanNo");
$(function(){
	$("#loanName").keyup(function(){
		if($(this).val().length>30){
			AlertDialog.show("项目名称不能超过30个字！", "loanName", 5, 30, "jump");
			$(this).val($(this).val().substring(0,30));
		}
	});
	$("input").keyup(function(){
		if($(this).val().indexOf(" ")>=0){
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	})
	$("#counterFee-hover").hover(function(){
		$("#counterFee-popup").toggle();
	});
	
	$("#loanIntroduction").keyup(function(){
		if($(this).val().length>20){
			AlertDialog.show("介绍不能超过20个字！", "loanIntroduction", 0, 30, "jump");
			$(this).val($(this).val().substring(0,20));
		}
	});
	getServiceFeeScale();//查询服务费比例
	getByType();//获取行业类别
	
	showProvince();
	ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传项目头图
	ecreateWebUploader("image_file1", "imghead1", "loan_logo_url1", "imgheadLi1");//上传项目封面
	
	$("#saveDataBtn").click(saveCrowdFunding);//保存项目基本信息
//	$("#saveEnterBtn").click(updateUserStuff);//保存发起人基本信息
//	$("#saveBackSet").click(saveBackSet);//保存回报设置
//	$("#addBackSet").click(addBackSet);//添加回报设置按钮
//	$("#subLoan").click(submitLoan); //提交审核
	
	$("#raiseAmt").blur(function(){
		checkRaisAmt($("#raiseAmt").val(), "raiseAmt");
	});
	
	$("#loanVideo").blur(function(){
		checkVideoUrl($("#loanVideo").val(), "loanVideo");
	});
	$("#mobileVideo").blur(function(){
		mobileVideoUrl($("#mobileVideo").val(), "mobileVideo");
	});
	//最高上限
	$("#overFundAmt").blur(function(){
		checkOverFundAmt($("#overFundAmt").val(), "overFundAmt");
	});
	$("#raiseDay").blur(function(){
		checkRaiseDay($("#raiseDay").val(), "raiseDay");
	});
	if(loanNo){
		initCrowdfundInfo();
	}
	
});
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
				$("#chargeFee").text(FeeScale*100+"%").attr("chargeFee",FeeScale);
//				$("#overFundAmt").blur(function(){
//					$("#chargeFee").text((FeeScale*$("#overFundAmt").val()).toFixed(2)+companyCode);
//				})
				$("#serviceFeeScale").text(data["msg"]["serviceFeeScale"]*100+"%");
			}
		},
		error: function(request){
			console.log("保存项目基本信息异常");
		}
	});
}
//获取行业类别
function getByType(){
	$.ajax({
		url: path+"/dictionary/getByType.html",
		type: "post",
		dataType: "json",
		data: {"typeCode": "concernIndustry"},
		success: function(data){
			var l = data.length, tArr = [], tStr = '';
			tArr.push('<option value="">请选择行业类别</option>');
			for(var i=0;i<l;i++){
				tArr.push('<option value="'+data[i]["code"]+'">'+data[i]["displayName"]+'</option>');
			}
			tStr = tArr.join("");
			$("#superIndustry").html(tStr).show();
		},
		error: function(request){
			console.log("获取行业类别异常");
		}
	});
}

//删除项目展示图片
function delImage(id){
	$("#" + id).prev().removeAttr("src").parent().hide();
	urlArr.splice(Number($(this).attr("i")), 1);
	$("#proPhoto_url").val(urlArr.join(","));
}

//保存项目基本信息
function saveCrowdFunding(){
	if(siteUserId == "null"){
		AlertDialog.tip("您还未登陆，请先登录");
		setTimeout(function(){
			window.location.href = path + "/common/login.html";
		},2000);
		$("#alertSure").click(function(){
			window.location.href = path + "/common/login.html";
		});
		return false;
	}
	
	if(checkProName($("#loanName").val(), "loanName")){
		if(Valify.isNull($("#loan_logo_url").val(), "image_file", -100, 40, "jump")){
			if(checkRaisAmt($("#raiseAmt").val(), "raiseAmt")){
				if(checkOverFundAmt($("#overFundAmt").val(), "overFundAmt")){
					if(checkRaiseDay($("#raiseDay").val(), "raiseDay")){
						if(Valify.isNull($("#superIndustry").val(), "superIndustry", 10, 40, "jump")){
							if(Valify.isNull($("#province").val(), "province", 10, 40, "jump")){
								if($("#province").val() != "710000" && $("#province").val() != "810000" && $("#province").val() != "820000"){
									if(!$("#city").val()){
										AlertDialog.show($("#city").attr("nullMessage"), "city", 10, 40);
										return false;
									}
								}
								//if(Valify.isNull($("#city").val(), "city", 10, 40, "jump")){
									if(Valify.isNull($("#loan_logo_url1").val(), "image_file1", -100, 40, "jump")){
										if(checkVideoUrl($("#loanVideo").val(), "loanVideo")){
											if(mobileVideoUrl($("#mobileVideo").val(), "mobileVideo")){
												if(numberLimit($("#loanIntroduction").val(), "loanIntroduction", 0, 40, "20")){
		//											if(Valify.isNull($("#ProjectDption").val(), "ProjectDption", -40, 40, "jump")){
		//												if($("#ProjectDption").val().length>200){
		//													AlertDialog.show("文字不超过200字", "ProjectDption", -40, 40, "jump");
		//													return false;
		//												}
														if(Valify.isNull(UE.getEditor("proDetail").getContent(), "proDetail_1", 450, -450, "jump")){
															if($("#read").is(':checked')){
															}else{
																AlertDialog.show($("#read").attr("nullMessage"), "read", 30, 22	);
																return false;
															}
															var url = path + "/crowdfunding/saveCrowdFunding.html";
															if(loanNo){
																url = path + "/crowdfunding/updateCrowdFunding.html";
															}
															/*if($("#projectLoanNo").val()){
																//loanNo = $("#projectLoanNo").val();
																url = path + "/crowdfunding/updateCrowdFunding.html";
															}*/
															$.ajax({
																url: url,
																type: "post",
																dataType: "json",
																data: {
																	"loanNo" : loanNo,
																	"loanType" : "product",
																	"loanName" : $("#loanName").val(), //项目名称
																	"loanPhoto" : $("#loan_logo_url").val(), //项目头图
																	"fundAmt" : $("#raiseAmt").val(), //筹集金额
																	"overFundAmt" : $("#overFundAmt").val(), //最高上限
																	"fundDays" : $("#raiseDay").val(), //筹集天数
																	"superIndustry" : $("#superIndustry").val(), //行业类别
																	"province" : $("#province").val(), //项目地点省
																	"city" : $("#city").val(), //项目地点市
																	"county" : $("#county").val(), //项目地点市
																	"loanLogo" : $("#loan_logo_url1").val(), //项目封面
																	"loanVideo" : $("#loanVideo").val(), //视频地址
																	"mobileVideo" : $("#mobileVideo").val(), //移动视频地址
																	"loanIntroduction" : $("#loanIntroduction").val(), //一句话介绍
		//															"loanDes" : $("#ProjectDption").val(), //项目简介
																	"loanDetail" : UE.getEditor("proDetail").getContent(), //项目详情
																	"chargeFee":$("#chargeFee").attr("chargefee") //服务费
																	
																},
																success: function(data){
																	if(data["success"]){
																		AlertDialog.tip("保存成功！");
																		var msgLoanNo;
																		if(loanNo){
																			msgLoanNo = loanNo;
																		}else{
																			msgLoanNo = data["msg"];
																		}
																		setTimeout(function(){
																			location.href = path+"/common/enterRewardTw.html?loanNo="+msgLoanNo;
																		},2000);
																		$("#alertSure").click(function(){
																			 location.href = path+"/common/enterRewardTw.html?loanNo="+msgLoanNo;
																		});
																	}else{
																		AlertDialog.tip(data["msg"]);
																	}
																},
																error: function(request){
																	console.log("保存项目基本信息异常");
																}
															});
		//												}
													}
												}
											}
										}
									}
								//}
							}
						}
					}
				}
			}
		}
	}
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
	//首先查看是编辑回报设置还是添加回报设置
	var burl, bd = {};
	if(!$("#backSetPauseBox").attr("editp")){ //添加
		burl = path + "/crowdfunding/saveBackSet.html";
		bd = {
				"loanNo": $("#projectLoanNo").val(),
				"amt": $("#price").val(),
				"backContent": $("#backContent").val(),
				"photoUrl1": $("#backImg_url").val(),
				"numberLimits": $("#limitPeople").val(),
				//"transFee": $("#carriage").val(),
				"backTime": $("#backTime").val(),
				//"isDelivery": $("#return_type").prop("checked") ? "M": "V"
			};
	}else{
		burl = path + "/crowdfunding/updateBackSet.html";
		bd = {
				"id": $("#backSetPauseBox").attr("editp"),
				"loanNo": $("#projectLoanNo").val(),
				"amt": $("#price").val(),
				"backContent": $("#backContent").val(),
				"photoUrl1": $("#backImg_url").val(),
				"numberLimits": $("#limitPeople").val(),
				//"transFee": $("#carriage").val(),
				"backTime": $("#backTime").val(),
				//"isDelivery": $("#return_type").prop("checked") ? "M": "V"
			};
	}
	if(checksportNum($("#price").val(), "price")){
		if(Valify.isNull($("#backContent").val(), "backContent", -40, 30)){
			if(checksportNum($("#limitPeople").val(), "limitPeople")){
				//if(checksportNum($("#carriage").val(), "carriage")){
					$.ajax({
						url: burl,
						type: "post",
						dataType: "json",
						data: bd,
						success: function(data){
							if(data["success"]){
								AlertDialog.tip("保存回报设置成功！");
								if($(".back_table").css("display") == "none"){
									$(".back_table").show();
								}
								if($("#overBackSet").css("display") == "none"){
									$("#overBackSet").show().click(showProData); //可以编辑项目资料了
								}
								initBackSetList();
							}
						},
						error: function(request){
							console.log("保存项目基本信息异常");
						}
					});
				//}
			}
		}
	}
}
//验证支持金额
function checksportNum(str, id){
	if(!str || isNaN(str)){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20);
		return false;
	}
	AlertDialog.hide();
	return true;
}
//检测视频链接
function checkVideoUrl(str, id){
//	if(!str){
//		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20);
//		return false;
//	}
	if(str){
		if(str.substring(str.length-3, str.length) != "swf"){
			AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 20);
			var jTop = $("#"+id).offset().top -120;
			$("html,body").animate({
				scrollTop : jTop +"px"
			},"800");
			return false;
		}
	}
	AlertDialog.hide();
	return true;
}
function mobileVideoUrl(str, id){
//	if(!str){
//	AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20);
//	return false;
//}
//	if(str){
//		if(str.substring(str.length-3, str.length) != "mp4"){
//			AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 20);
//			var jTop = $("#"+id).offset().top -120;
//			$("html,body").animate({
//				scrollTop : jTop +"px"
//			},"800");
//			return false;
//		}
//	}
	AlertDialog.hide();
	return true;
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
				$('#imghead1').parent().hover(function(){
					var _this = $(this);
					_this.children("div").slideDown("slow");
					_this.find("div").find("img").click(function(){
						_this.hide();
						$("#loan_logo_url1").val("");
						ecreateWebUploader("image_file1", "imghead1", "loan_logo_url1", "imgheadLi1");//上传项目封面
					});
				},function(){
					$(this).children("div").slideUp("slow");
					
				});
				$("#loanName").val(bdata["msg"]["loanName"]);
				$("#loan_logo_url").val(bdata["msg"]["loanPhoto"]);//项目头图
				$('#imghead').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["loanPhoto"]);
				$("#imgheadLi").show();
				$("#raiseAmt").val(bdata["msg"]["fundAmt"]);//筹集金额
				$("#overFundAmt").val(bdata["msg"]["overFundAmt"]);//最高上限
				$("#raiseDay").val(bdata["msg"]["fundDays"]);//募集天数
				$("#superIndustry").val(bdata["msg"]["superIndustry"]);//行业类别
				showProvince(bdata["msg"]["province"]);
				showCity(bdata["msg"]["province"], bdata["msg"]["city"]);
				showCounty(bdata["msg"]["city"], bdata["msg"]["county"]);
				$("#loanVideo").val(bdata["msg"]["loanVideo"]);//视频
				$("#mobileVideo").val(bdata["msg"]["mobileVideo"]);//移动视频
				$("#loanIntroduction").val(bdata["msg"]["loanIntroduction"]);//一句话介绍项目 
//				$("#ProjectDption").val(bdata["msg"]["loanDes"]);//项目简介
				$("#proDetail").val(bdata["msg"]["loanDetail"]);//项目详情
				$("#loan_logo_url1").val(bdata["msg"]["loanLogo"]);//项目封面
				$('#imghead1').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["loanLogo"]);
				$("#imgheadLi1").show();
				//$("#chargeFee").text((Number($("#chargeFee").attr("chargefee"))*Number($("#overFundAmt").val())).toFixed(2)+companyCode);//项目详情
			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
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
		data: {"loanNo": $("#projectLoanNo").val()},
		success: function(bdata){
			if(bdata["success"]){
				l = bdata["msg"]["rows"].length, bdata = bdata["msg"]["rows"];
				$("#bsetIndex").text("0" + (l+1));
				//顶部步骤进度改变
				if(l > 0){
					$("#stepDiv>dl").eq(3).addClass("cur");
				}
				for(var i=0;i<l;i++){
					setArr.push('<tr>');
					setArr.push('<td width="10%">0'+(i+1)+'</td>');
					setArr.push('<td width="15%">'+bdata[i]["amt"]+'</td>');
					if(bdata[i]["numberLimits"] == 0){
						setArr.push('<td width="15%">不限名额</td>');
					}else{
						setArr.push('<td width="15%">'+bdata[i]["numberLimits"]+'位</td>');
					}
					setArr.push('<td width="35%">'+bdata[i]["backContent"]+'</td>');
//					if(bdata[i]["transFee"] == 0){
//						setArr.push('<td width="15%">免运费</td>');
//					}else{
//						setArr.push('<td width="15%">'+bdata[i]["transFee"]+'</td>');
//					}
					setArr.push('<td width="10%"><a href="javascript:void(0);" bid='+bdata[i]["id"]+' onclick=deleteBackSet(event)>删除</a><br/><a href="javascript:void(0);" bid='+bdata[i]["id"]+' onclick=modifyBackSet(event)>修改</a></td>');
					setArr.push('</tr>');
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
	AlertDialog.confirm(modifyBS, null, "你确定要编辑这条回报设置吗？", "编辑", "取消", id);
}
//编辑回报设置实现方法
function modifyBS(id){
	$("#bsetIndex").text($("a[bid='"+id+"']").parent().siblings().first().text());
	$("#backSetPauseBox").attr("editp", id);
	$.ajax({
		url: path + "/crowdfunding/getBackSetById.html",
		type: "post",
		dataType: "json",
		data: {"id": id},
		success: function(data){
			if(data["success"]){
				$("#price").val(data["msg"]["amt"]);
				$("#backContent").val(data["msg"]["backContent"]);
				if(data["msg"]["photoUrl1"]){ //此条回报设置有数据
					$("#backImg_url").val(data["msg"]["photoUrl1"]);
					$("#backImgLi").show();
					$("#backImg_image").attr("src",cv["fileAddress"]+data["msg"]["photoUrl1"]);
				}
				$("#limitPeople").val(data["msg"]["numberLimits"]);
				//$("#carriage").val(data["msg"]["transFee"]);
//				if(data["msg"]["isDelivery"] == "M"){
//					$("#return_type").prop("checked", true);
//				}
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
	$("#backImgLi").hide();
	$("#limitPeople").val("0");
	//$("#carriage").val("0");
	//$("#return_type").removeProp("checked");
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
			AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 20, "jump");
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
//验证最高上限金额
function checkOverFundAmt(str, id){
	var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20, "jump");
		return false;
	}else{
		if(isNaN(str)){
			AlertDialog.show("请输入正确金额", id, 10, 20, "jump");
			return false;
		}else if(Number(str) <= 0){
			AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 20, "jump");
			return false;
		}else if(!reg.test(str)){
			AlertDialog.show("不能超过两位小数", id, 10, 20, "jump");
			return false;
		}else if($("#raiseAmt").val() == ""){
			checkRaisAmt($("#raiseAmt").val(), "raiseAmt");
			return false;
		}else{
			if(isNaN(str) || Number(str) < $("#raiseAmt").val()){
				AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 20, "jump");
				return false;
			}
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

function numberLimit(str, id, top, left, j){
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, top, left);
		var jTop = $("#"+id).offset().top -120;
		$("html,body").animate({
			scrollTop : jTop +"px"
		},"800");
		return false; 
	}
	
	if(str.length>j){
		AlertDialog.show("字数不能超过"+j+"个字！", id, top, left);
		var jTop = $("#"+id).offset().top -120;
		$("html,body").animate({
			scrollTop : jTop +"px"
		},"800");
		return false; 
	}
	
	AlertDialog.hide();
	return true;
}
