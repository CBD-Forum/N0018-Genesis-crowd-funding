if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}
var loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7,window.location.search.length);
var url = path + "/crowdfunding/saveCrowdFunding.html";
$(function(){
	$("#projectName").keyup(function(){
		if($(this).val().length>30){
			AlertDialog.show("项目名称不能超过30个字！", "projectName", 5, 30, "jump");
			$(this).val($(this).val().substring(0,30));
		}
	});
	$("input").keyup(function(){
		if($(this).val().indexOf(" ")>=0){
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	})
	$("#loanIntroduction").keyup(function(){
		if($(this).val().length>20){
			AlertDialog.show("介绍不能超过20个字！", "loanIntroduction", 5, 30, "jump");
			$(this).val($(this).val().substring(0,20));
		}
	});
	if(loanNo){
		url = path + "/crowdfunding/updateCrowdFundDetail.html";
		initCrowdfundInfo(); //项目基本信息
		$("#saveDataBtn").click(editCrowdFunding);
	}else{
		$.ajax({
			url: path + "/common/getTempLoanNo.html",
			type: "post",
			dataType: "json",
			success: function(data){
				loanNo = data["tempLoanNo"];
			}
		});
		$("#saveDataBtn").click(saveCrowdFunding);
	}
	
	
	showProvince();
	getIndustry();//获取投资领域
	
	ecreateWebUploaderT("image_file", "imghead", "loan_logo_url", "imgheadLi");//项目头图
	ecreateWebUploader("logoBtn", "logoImage", "logo_url", "logoDiv");//logo图片
	ecreateWebUploader("logoWeixin", "weixinImage", "weixin_url", "weixinDiv");//微信图片
	ecreateWebUploader("image_file1", "imghead1", "loan_logo_url1", "imgheadLi1");//项目图片
	
	
	$("#changeUpload3").click(function(){
		$("#fileToUpload3").click();
	});
	$("#changeUpload4").click(function(){
		$("#fileToUpload4").click();
	});
	$("#changeUpload5").click(function(){
		$("#fileToUpload5").click();
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
				$('#imghead').parent().hover(function(){
					var _this = $(this);
					_this.children("div").slideDown("slow");
					_this.find("div").find("img").click(function(){
						_this.hide();
						$("#loan_logo_url").val("");
						ecreateWebUploaderT("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传项目头图
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
						ecreateWebUploader("image_file1", "imghead1", "loan_logo_url1", "imgheadLi1");//项目图片
					});
				},function(){
					$(this).children("div").slideUp("slow");
					
				});
				$('#weixinImage').parent().hover(function(){
					var _this = $(this);
					_this.children("div").slideDown("slow");
					_this.find("div").find("img").click(function(){
						_this.hide();
						$("#weixin_url").val("");
						ecreateWebUploader("logoWeixin", "weixinImage", "weixin_url", "weixinDiv");//微信图片
					});
				},function(){
					$(this).children("div").slideUp("slow");
					
				});
				$('#logoImage').parent().hover(function(){
					var _this = $(this);
					_this.children("div").slideDown("slow");
					_this.find("div").find("img").click(function(){
						_this.hide();
						$("#logo_url").val("");
						ecreateWebUploader("logoBtn", "logoImage", "logo_url", "logoDiv");//logo图片
					});
				},function(){
					$(this).children("div").slideUp("slow");
					
				});
				$("#projectName").val(bdata["msg"]["loanName"]);
				$("#ptrade_one").val(bdata["msg"]["superIndustry"]);
				$("#province").val(bdata["msg"]["province"]);
				$("#city").val(bdata["msg"]["city"]);
				showCity(bdata["msg"]["province"], bdata["msg"]["city"]);
				showCounty(bdata["msg"]["city"], bdata["msg"]["county"]);
//				$("#ProjectDption").val(bdata["msg"]["loanDes"]);
				$("#imgheadLi").show();
				$("#loan_logo_url").val(bdata["msg"]["loanLogo"]);
				$('#imghead').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["loanLogo"]);
				
				$("#loanIntroduction").val(bdata["msg"]["loanIntroduction"]);
				$("#projectStage").val(bdata["msg"]["loanStage"]);

				UE.getEditor("proDetail_1").ready(function(){
					UE.getEditor("proDetail_1").setContent(bdata["msg"]["industryAnalysis"]);
				});
				UE.getEditor("proDetail_2").ready(function(){
					UE.getEditor("proDetail_2").setContent(bdata["msg"]["riskMeasure"]);
				});
				UE.getEditor("proDetail_3").ready(function(){
					UE.getEditor("proDetail_3").setContent(bdata["msg"]["competence"]);
				});
				UE.getEditor("proDetail_4").ready(function(){
					UE.getEditor("proDetail_4").setContent(bdata["msg"]["profitModel"]);
				});
				
				$("#officialWz").val(bdata["msg"]["website"]);
				$("#officialWb").val(bdata["msg"]["weibo"]);

				$("#weixinDiv").show();
				$("#weixin_url").val(bdata["msg"]["weixin"]);
				$('#weixinImage').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["weixin"]);
				$("#logoDiv").show();
				$("#logo_url").val(bdata["msg"]["logo"]);
				$('#logoImage').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["logo"]);
				$("#imgheadLi1").show();
				$("#loan_logo_url1").val(bdata["msg"]["loanLogo"]);
				$('#imghead1').attr("src", cv["fileAddress"] + "/" + bdata["msg"]["loanLogo"]);
				$("#loanVideo").val(bdata["msg"]["loanVideo"]);
				$("#mobileVideo").val(bdata["msg"]["mobileVideo"]);
				var businessProposalFiles = bdata["msg"]["businessProposalFiles"]
				for(var i = 0;i<businessProposalFiles.length;i++){
					$("#orgLoanReceiveProve4").val($("#orgLoanReceiveProve4").val()+","+businessProposalFiles[i]["fileUrl"]);
					$("#uploadInFo4").append('<div class="clearfix mt18 enterFile-style" style="padding-right: 15px;" url="'+businessProposalFiles[i]["fileUrl"]+'"><a class="fl" target="_blank" href="'+cv.fileAddress+businessProposalFiles[i]["fileUrl"]+'" >'+businessProposalFiles[i]["fileName"]+'</a><a class="fr enterFile-delete" vid="'+businessProposalFiles[i]["id"]+'" onClick=\'fileDeleteWJ(this,"#uploadInFo4","#orgLoanReceiveProve4")\'></a></div>');
		    		
				}
				
				var exitSchemeFiles = bdata["msg"]["exitSchemeFiles"]
				for(var i = 0;i<exitSchemeFiles.length;i++){
					$("#orgLoanReceiveProve3").val($("#orgLoanReceiveProve3").val()+","+exitSchemeFiles[i]["fileUrl"]);
					$("#uploadInFo3").append('<div class="clearfix mt18 enterFile-style" style="padding-right: 15px;" url="'+exitSchemeFiles[i]["fileUrl"]+'"><a class="fl" target="_blank" href="'+cv.fileAddress+exitSchemeFiles[i]["fileUrl"]+'" >'+exitSchemeFiles[i]["fileName"]+'</a><a class="fr enterFile-delete" vid="'+exitSchemeFiles[i]["id"]+'" onClick=\'fileDeleteWJ(this,"#uploadInFo4","#orgLoanReceiveProve4")\'></a></div>');
		    		
				}
				
				var otherAccessoriesFiles = bdata["msg"]["otherAccessoriesFiles"]
				for(var i = 0;i<otherAccessoriesFiles.length;i++){
					$("#orgLoanReceiveProve5").val($("#orgLoanReceiveProve5").val()+","+otherAccessoriesFiles[i]["fileUrl"]);
					$("#uploadInFo5").append('<div class="clearfix mt18 enterFile-style" style="padding-right: 15px;" url="'+otherAccessoriesFiles[i]["fileUrl"]+'"><a class="fl" target="_blank" href="'+cv.fileAddress+otherAccessoriesFiles[i]["fileUrl"]+'" >'+otherAccessoriesFiles[i]["fileName"]+'</a><a class="fr enterFile-delete" vid="'+otherAccessoriesFiles[i]["id"]+'" onClick=\'fileDeleteWJ(this,"#uploadInFo5","#orgLoanReceiveProve5")\'></a></div>');
		    		
				}
				
			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
}


//保存项目基本信息
function saveCrowdFunding(){
	if($("#promise").is(':checked')){
	}else{
		AlertDialog.show($("#promise").attr("nullMessage"), "promise", 30, 22, "jump");
		return false;
	}
	if(checkProName($("#projectName").val(), "projectName")){
		if(numberLimit($("#loanIntroduction").val(), "loanIntroduction", 10, 40, "20")){
			if(Valify.isNull($("#loan_logo_url").val(), "image_file", -90, 40, "jump")){
				if(Valify.isNull($("#province").val(), "province", 10, 40, "jump")){
					if($("#province").val() != "710000" && $("#province").val() != "810000" && $("#province").val() != "820000"){
						if(!$("#city").val()){
							$("html,body").animate({
								scrollTop: $("#city").offset().top -120
							},"800");
							AlertDialog.show($("#city").attr("nullMessage"), "city", 10, 40);
							return false;
						}
					}
					//if(Valify.isNull($("#city").val(), "city", 10, 40, "jump")){
						if(Valify.isNull($("#projectStage").val(), "projectStage", 10, 40, "jump")){
							if(Valify.isNull($("#ptrade_one").val(), "ptrade_one", 10, 40, "jump")){
//								if(Valify.isNull($("#ProjectDption").val(), "ProjectDption", -45, 40, "jump")){
									if(Valify.isNull(UE.getEditor("proDetail_1").getContent(), "industryAnalysis", -400, 40, "jump")){
										if(Valify.isNull(UE.getEditor("proDetail_2").getContent(), "riskAdministration", -400, 40, "jump")){
											if(Valify.isNull(UE.getEditor("proDetail_3").getContent(), "competence_ue", -400, 40, "jump")){
												if(Valify.isNull(UE.getEditor("proDetail_4").getContent(), "profitModel_ue", -400, 40, "jump")){
													if(Valify.isNull($("#officialWz").val(), "officialWz", 10, 40, "jump")){
														if(Valify.isNull($("#officialWb").val(), "officialWb", 10, 40, "jump")){
															if(Valify.isNull($("#weixin_url").val(), "logoWeixin", 25, 40, "jump")){
																if(Valify.isNull($("#logo_url").val(), "logoBtn", 25, 40, "jump")){
																	if(Valify.isNull($("#loan_logo_url1").val(), "image_file1", 25, 40, "jump")){
																		if(checkVideoUrl($("#loanVideo").val(), "loanVideo")){
																			if(mobileVideoUrl($("#mobileVideo").val(), "mobileVideo")){
																				if(Valify.isNull($("#orgLoanReceiveProve4").val(), "changeUpload4", 10, 40, "jump")){
																					if(Valify.isNull($("#orgLoanReceiveProve3").val(), "changeUpload3", 10, 40, "jump")){
																						if($("#read").is(':checked')){
																						}else{
																							AlertDialog.show($("#read").attr("nullMessage"), "read", 30, 22	, "jump");
																							return false;
																						}
																						$.ajax({
																							url: url,
																							type: "post",
																							dataType: "json",
																							data: {
																								"tempLoanNo":loanNo,
																								"loanType": "stock",
																								"loanName": $("#projectName").val(),
																								"loanIntroduction":$("#loanIntroduction").val(),
																								"loanPhoto": $("#loan_logo_url").val(),
																								"province": $("#province").val(),
																								"city": $("#city").val(),
																								"county" : $("#county").val(), 
																								"loanStage":$("#projectStage").val(),
																								"superIndustry": $("#ptrade_one").val(),
		//																						"loanDes": $("#ProjectDption").val(),
																								"competence": UE.getEditor("proDetail_3").getContent(),
																								"profitModel": UE.getEditor("proDetail_4").getContent(),
																								"industryAnalysis":UE.getEditor("proDetail_1").getContent(),
																								"riskMeasure": UE.getEditor("proDetail_2").getContent(),
																								"website": $("#officialWz").val(),
																								"weibo": $("#officialWb").val(),
																								"loanLogo": $("#loan_logo_url1").val(),
																								"logo": $("#logo_url").val(),
																								"weixin": $("#weixin_url").val(),
																								//"bankInfo": $("#bankInformation").val(),
																								"loanVideo":$("#loanVideo").val(),
																								"mobileVideo":$("#mobileVideo").val()
																							},
																							success: function(data){
																								if(data["success"]){
																									window.location.href = path + "/common/enterStock-company.html?loanNo="+data["msg"];
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
														}
													}
												}
//											}
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

//编辑
function editCrowdFunding(){
	if($("#promise").is(':checked')){
	}else{
		AlertDialog.show($("#promise").attr("nullMessage"), "promise", 30, 22, "jump");
		return false;
	}
	if(checkProName($("#projectName").val(), "projectName")){
		if(numberLimit($("#loanIntroduction").val(), "loanIntroduction", 10, 40, "20")){
			if(Valify.isNull($("#loan_logo_url").val(), "image_file", -90, 40, "jump")){
				if(Valify.isNull($("#province").val(), "province", 10, 40, "jump")){
					if($("#province").val() != "710000" && $("#province").val() != "810000" && $("#province").val() != "820000"){
						if(!$("#city").val()){
							AlertDialog.show($("#city").attr("nullMessage"), "city", 10, 40);
							return false;
						}
					}
					//if(Valify.isNull($("#city").val(), "city", 10, 40, "jump")){
						if(Valify.isNull($("#projectStage").val(), "projectStage", 10, 40, "jump")){
							if(Valify.isNull($("#ptrade_one").val(), "ptrade_one", 10, 40, "jump")){
//								if(Valify.isNull($("#ProjectDption").val(), "ProjectDption", -45, 40, "jump")){
									if(Valify.isNull(UE.getEditor("proDetail_1").getContent(), "industryAnalysis", -400, 40, "jump")){
										if(Valify.isNull(UE.getEditor("proDetail_2").getContent(), "riskAdministration", -400, 40, "jump")){
											if(Valify.isNull(UE.getEditor("proDetail_3").getContent(), "competence_ue", -400, 40, "jump")){
												if(Valify.isNull(UE.getEditor("proDetail_4").getContent(), "profitModel_ue", -400, 40, "jump")){
													if(Valify.isNull($("#officialWz").val(), "officialWz", 10, 40, "jump")){
														if(Valify.isNull($("#officialWb").val(), "officialWb", 10, 40, "jump")){
															if(Valify.isNull($("#weixin_url").val(), "logoWeixin", 25, 40, "jump")){
																if(Valify.isNull($("#logo_url").val(), "logoBtn", 25, 40, "jump")){
																	if(Valify.isNull($("#loan_logo_url1").val(), "image_file1", 25, 40, "jump")){
																		if(checkVideoUrl($("#loanVideo").val(), "loanVideo")){
																			if(mobileVideoUrl($("#mobileVideo").val(), "mobileVideo")){
																				if(Valify.isNull($("#orgLoanReceiveProve4").val(), "changeUpload4", 10, 40, "jump")){
																					if(Valify.isNull($("#orgLoanReceiveProve3").val(), "changeUpload3", 10, 40, "jump")){
																						if($("#read").is(':checked')){
																						}else{
																							AlertDialog.show($("#read").attr("nullMessage"), "read", 30, 22, "jump");
																							return false;
																						}
																						$.ajax({
																							url: url,
																							type: "post",
																							dataType: "json",
																							data: {
																								"loanNo":loanNo,
																								"loanType": "stock",
																								"loanName": $("#projectName").val(),
																								"loanIntroduction":$("#loanIntroduction").val(),
																								"loanPhoto": $("#loan_logo_url").val(),
																								"province": $("#province").val(),
																								"city": $("#city").val(),
																								"county" : $("#county").val(), 
																								"loanStage":$("#projectStage").val(),
																								"superIndustry": $("#ptrade_one").val(),
		//																						"loanDes": $("#ProjectDption").val(),
																								"competence": UE.getEditor("proDetail_3").getContent(),
																								"profitModel": UE.getEditor("proDetail_4").getContent(),
																								"industryAnalysis":UE.getEditor("proDetail_1").getContent(),
																								"riskMeasure": UE.getEditor("proDetail_2").getContent(),
																								"website": $("#officialWz").val(),
																								"weibo": $("#officialWb").val(),
																								"loanLogo": $("#loan_logo_url1").val(),
																								"logo": $("#logo_url").val(),
																								"weixin": $("#weixin_url").val(),
																								"loanVideo":$("#loanVideo").val(),
																								"mobileVideo":$("#mobileVideo").val()
																							},
																							success: function(data){
																								if(data["success"]){
																									window.location.href = path + "/common/enterStock-company.html?loanNo="+loanNo;
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
														}
													}
//												}
											}
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

//验证项目名称
function checkProName(str, id){
	if(!str){
		AlertDialog.show("项目名称不能为空", id, 10, 20, "jump");
		$("html,body").animate({
			scrollTop: $("#" + id).offset().top -120
		},"800");
		return false;
	}
	if(str.length > 40){
		AlertDialog.show("项目名称不能超过40个字", id, 10, 20, "jump");
		$("html,body").animate({
			scrollTop: $("#" + id).offset().top -120
		},"800");
		return false;
	}
	AlertDialog.hide();
	return true;
}
//验证筹集金额
function checkRaisAmt(str, id){
	if(isNaN(str) || !str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20, "jump");
		$("html,body").animate({
			scrollTop: $("#" + id).offset().top -120
		},"800");
		return false;
	}else{
		if(!str || Number(str) < 0){
			AlertDialog.show($("#" + id).attr("logicMessage"), id, 10, 20, "jump");
			$("html,body").animate({
				scrollTop: $("#" + id).offset().top -120
			},"800");
			return false;
		}else 
			if(!str || Number(str) > 1000000000)
			{
			AlertDialog.show($("#" + id).attr("logicMessage2"), id, 10, 20, "jump");
			$("html,body").animate({
				scrollTop: $("#" + id).offset().top -120
			},"800");
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
		$("html,body").animate({
			scrollTop: $("#" + id).offset().top -120
		},"800");
		return false;
	}else{
		if(!str || Number(str) < 10 || Number(str) > 90){
			AlertDialog.show("募集天数为10-90天", id, 0, 30, "jump");
			$("html,body").animate({
				scrollTop: $("#" + id).offset().top -120
			},"800");
			return false;
		}
	}
	AlertDialog.hide();
	return true;
}

function ecreateWebUploaderT(pickId, showId, setValueId, parentId){
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
	    fileSingleSizeLimit: 5 * 1024 * 1024    // 50 M
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
            				$("#proImageLi").find("img").eq(i).attr("src", cv["fileAddress"] + "/" + json["msg"]) ;
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
	    fileSingleSizeLimit: 5 * 1024 * 1024    // 50 M
	});
	uploader.on('error', function (handler) {
		if("Q_EXCEED_SIZE_LIMIT"==handler){
			AlertDialog.show("文件超过5M，请重新上传", pickId, -60, 60);
		}else if("Q_TYPE_DENIED"==handler){
			AlertDialog.show("文件类型错误", pickId, -60, 60);
		}else if("Q_EXCEED_NUM_LIMIT"==handler){
			AlertDialog.show("添加的文件数量超出", pickId, -60, 60);
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
            				$("#proImageLi").find("img").eq(i).attr("src", cv["fileAddress"] + "/" + json["msg"]) ;
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
function recreateWebUploader(pickId, showId, setValueId, parentId, type){
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
	    server: path + '/crowdfunding/uploadUserFile.html?fileType=' + type,
	    fileNumLimit: 300,
	    fileSizeLimit: 3 * 1024 * 1024,    // 200 M
	    fileSingleSizeLimit: 3 * 1024 * 1024    // 50 M
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
            	$('#'+showId).attr("src", cv["fileAddress"] + "/" + json["msg"]);
        		$("#" + setValueId).val(json["msg"]);
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

//验证是否是数字
function checkIsNaN(str, id){
	if(!str || isNaN(str)){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20);
		$("html,body").animate({
			scrollTop: $("#" + id).offset().top -120
		},"800");
		return false;
	}
	AlertDialog.hide();
	return true;
}

//检测视频链接
function checkVideoUrl(str, id){
//	if(!str){
//	AlertDialog.show($("#" + id).attr("nullMessage"), id, 10, 20);
//	return false;
//}
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
//获取投资领域
function getIndustry(){
	
	$.ajax({
		url: path + "/dictionary/getByType.html",
		type: "post",
		dataType: "json",
		data: {"typeCode": "concernIndustry"},
		success: function(data){
			var cArr = [], cStr = '', cl = 0;
			cl = data.length;
			cArr.push('<option value="">请选择</option>');
			for(var i=0;i<cl;i++){
				cArr.push('<option value="'+data[i]["code"]+'">'+data[i]["displayName"]+'</option>');
			}
			cStr = cArr.join("");
			$("#ptrade_one").html(cStr);
		},
		error: function(request){
			console.log("获取投资领域异常");
		}
	});
	$.ajax({
		url: path + "/dictionary/getByType.html",
		type: "post",
		dataType: "json",
		data: {"typeCode": "crowdFund_loan_stage"},
		success: function(data){
			var cArr = [], cStr = '', cl = 0;
			cl = data.length;
			cArr.push('<option value="">请选择</option>');
			for(var i=0;i<cl;i++){
				cArr.push('<option value="'+data[i]["code"]+'">'+data[i]["displayName"]+'</option>');
			}
			cStr = cArr.join("");
			$("#projectStage").html(cStr);
		},
		error: function(request){
			console.log("获取投资领域异常");
		}
	});
}
$('#fileToUpload3').on('change', function() {
    $.ajaxFileUpload({  
        url:path+'/fileUpload/uploadFile.html?parentId='+loanNo+'&type=exitScheme',  
        secureuri:false,  
        fileElementId:'fileToUpload3',//file标签的id  
        dataType: 'json',//返回数据的类型  
        
        success: function (data, status) {
        	if(data["success"]){
        		if($("#orgLoanReceiveProve3").val()){
        			var orgLoanReceiveProve = $("#orgLoanReceiveProve3").val()+","+data.fileUrl;
        		}else{
        			var orgLoanReceiveProve = data.fileUrl;
        		}
        		$("#orgLoanReceiveProve3").val(orgLoanReceiveProve);
        		AlertDialog.mtip('上传成功！');
        		$("#uploadInFo3").append('<div class="clearfix mt18 enterFile-style" url="'+data.fileUrl+'"><a class="fl" style="padding-right: 15px;" target="_blank" href="'+cv.fileAddress+data.fileUrl+'" >'+data.fileName+'</a><a class="fr enterFile-delete" vid="'+data.id+'" onClick=\'fileDeleteWJ(this,"#uploadInFo3","#orgLoanReceiveProve3")\'></a></div>');
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
$('#fileToUpload4').on('change', function() {
    $.ajaxFileUpload({  
        url:path+'/fileUpload/uploadFile.html?parentId='+loanNo+'&type=businessProposal',
        secureuri:false,  
        fileElementId:'fileToUpload4',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {
        	if(data["success"]){
        		
        		if($("#orgLoanReceiveProve4").val()){
        			var orgLoanReceiveProve = $("#orgLoanReceiveProve4").val()+","+data.fileUrl;
        		}else{
        			var orgLoanReceiveProve = data.fileUrl;
        		}
        		$("#orgLoanReceiveProve4").val(orgLoanReceiveProve);
        		AlertDialog.mtip('上传成功！');
        		$("#uploadInFo4").append('<div class="clearfix mt18 enterFile-style" url="'+data.fileUrl+'"><a class="fl" style="padding-right: 15px;" target="_blank" href="'+cv.fileAddress+data.fileUrl+'" >'+data.fileName+'</a><a class="fr enterFile-delete" vid="'+data.id+'" onClick=\'fileDeleteWJ(this,"#uploadInFo4","#orgLoanReceiveProve4")\'></a></div>');
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
$('#fileToUpload5').on('change', function() {
    $.ajaxFileUpload({  
        url:path+'/fileUpload/uploadFile.html?parentId='+loanNo+'&type=otherAccessories',
        secureuri:false,  
        fileElementId:'fileToUpload5',//file标签的id  
        dataType: 'json',//返回数据的类型  
        
        success: function (data, status) {
        	if(data["success"]){
        		if($("#orgLoanReceiveProve5").val()){
        			var orgLoanReceiveProve = $("#orgLoanReceiveProve5").val()+","+data.fileUrl;
        		}else{
        			var orgLoanReceiveProve = data.fileUrl;
        		}
        		$("#orgLoanReceiveProve5").val(orgLoanReceiveProve);
        		AlertDialog.mtip('上传成功！');
        		$("#uploadInFo5").append('<div class="clearfix mt18 enterFile-style" url="'+data.fileUrl+'"><a class="fl" style="padding-right: 15px;" target="_blank" href="'+cv.fileAddress+data.fileUrl+'" >'+data.fileName+'</a><a class="fr enterFile-delete" vid="'+data.id+'" onClick=\'fileDeleteWJ(this,"#uploadInFo5","#orgLoanReceiveProve5")\'></a></div>');
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
	$.ajax({
        url:path+'/fileUpload/removeFile.html', 
		type: "post",
		dataType: "json",
		data:{
			"id" : $(_this).attr("vid")
		},
		success: function(data){
			if(data["success"]){
			}else{
				AlertDialog.tip(data["msg"]);
			}
		},
		error: function(request){
			console.log("删除文件异常");
		}
	});
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
