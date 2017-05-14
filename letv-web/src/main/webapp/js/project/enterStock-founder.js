if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}
var loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7,window.location.search.length);
var founderId;
$(function(){
	$("#companyTeam").keyup(function(){
		if($(this).val().length>300){
			AlertDialog.show("团队介绍不能超过300个字！", "companyTeam", -45, 30, "jump");
			$(this).val($(this).val().substring(0,300));
		}
	});
	$("#loanName").keyup(function(){
		if($(this).val().length>30){
			AlertDialog.show("姓名不能超过30个字！", "loanName", 10, 30, "jump");
			$(this).val($(this).val().substring(0,30));
		}
	});
	$("#position").keyup(function(){
		if($(this).val().length>30){
			AlertDialog.show("职位不能超过30个字！", "position", 10, 30, "jump");
			$(this).val($(this).val().substring(0,30));
		}
	});
	$(".lch_nav li.lch5").click(function(){
		window.location.href = path + "/common/enterStock.html?loanNo="+loanNo;
	});
	$(".lch_nav li.lch2").click(function(){
		window.location.href = path + "/common/enterStock-company.html?loanNo="+loanNo;
	});
	initCrowdfundInfo();
	queryFounder();
	$("#saveFounderBtn").click(saveFounder);
//	$("#browseBtn").click(function(){
//		submitVerification(1);
//	});
	$("#browseBlank").attr("href",path + "/common/loanDetail-stock.html?loanNo="+loanNo+"&browse=0").show();
	$("#preservationBtn").click(function(){
		submitVerification(2);
	});
	$("#fouderInformationBtn").click(function(){
		submitVerification(3);
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
				$("#companyTeam").val(bdata["msg"]["loanTeam"]);
			}
		},
		error: function(request){
			console.log("查询项目详情异常");
		}
	});
	
}
function submitVerification(btn){
	
	if(Valify.isNull($("#companyTeam").val(), "companyTeam", -45, 40, "jump")){
		if($("#WorkList1>div").length<1){
			AlertDialog.tip("请添加工作经历！");
			return false;
		}
		if($("#WorkList2>div").length<1){
			AlertDialog.tip("请添加创业经历！");
			return false;
		}
		if($("#WorkList3>div").length<1){
			AlertDialog.tip("请添加教育经历！");
			return false;
		}
		if(btn == 3){
			var dataA = {
					"loanState":"submit",
					"loanNo":loanNo,
					"loanTeam": $("#companyTeam").val()
				}
		}else{
			var dataA = {
					"loanNo":loanNo,
					"loanTeam": $("#companyTeam").val()
				}
		}
		$.ajax({
			url: path+"/crowdfunding/updateCrowdFundDetail.html",
			type: "post",
			dataType: "json",
			data:dataA,
			success: function(data){
				if(data["success"]){
					if($("#founderWorkList>div").length>0){
						if(btn == 1){
							//$("#browseBtn").attr("href",path + "/common/loanDetail-stock.html?loanNo="+loanNo+"&browse=0").attr("target","_blank").click();
							//newWin(path + "/common/loanDetail-stock.html?loanNo="+loanNo+"&browse=0","browseBtn");
							//window.location.href = path + "/common/loanDetail-stock.html?loanNo="+loanNo;
//							$("#browseBtn").hide();
//							$("#browseBlank").attr("href",path + "/common/loanDetail-stock.html?loanNo="+loanNo+"&browse=0").show();
						}else if(btn==2){
							AlertDialog.tip("保存成功");
						}else{
							window.location.href = path + "/common/enterStock-submit.html?loanNo="+loanNo;
						}
					}else{
						AlertDialog.tip("请添加创始人信息！");
					}
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

//工作经历
function founderWorkBtn(_this){
	var inputA = $(_this).parent().find("input").eq(0);
	var inputB = $(_this).parent().find("input").eq(1);
	var inputC = $(_this).parent().find("input").eq(2);
	var inputD = $(_this).parent().find("input").eq(3);
	if(isNullBuild(inputA.val(), inputA, 10, 40, "jump")){
		if(isNullBuild(inputB.val(), inputB, 10, 40, "jump")){
			if(isNullBuild(inputC.val(), inputC, 10, 40, "jump")){
				if(isNullBuild(inputD.val(), inputD, 10, 40, "jump")){
					$.ajax({
						url: path+"/crowdfundingFounder/saveFounderWorks.html",
						type: "post",
						dataType: "json",
						data: {
							"founderId":$(_this).attr("aid"),
							"company": inputA.val(),
							"startTime": inputC.val(),
							"endTime": inputD.val(),
							"position": inputB.val()
						},
						success: function(data){
							if(data["success"]){
								$.ajax({
									url: path+"/crowdfundingFounder/getFounderWorksList.html",
									type: "post",
									dataType: "json",
									data: {
										"founderId":$(_this).attr("aid")
									},
									success: function(data){
										var pArr = [], pStr = '',l=data["rows"].length, data = data["rows"];
										for(var i=0;i<l;i++){
											pArr.push('<div class="clearfix" style="margin-top: 10px;">');
											pArr.push('<div class="fl txt_div txt_div3">');
											pArr.push('<div class="clearfix">');
											pArr.push('<p class="fl">公司名称：<i>'+data[i]["company"]+'</i></p>');
											pArr.push('<p class="fl mr0">职位：<i>'+data[i]["position"]+'</i></p>');
											pArr.push('</div>');
											pArr.push('<div class="clearfix">');
											pArr.push('<p class="fl">开始时间：<i>'+data[i]["startTime"].substring(0,10)+'</i></p>');
											pArr.push('<p class="fl mr0">结束时间：<i>'+data[i]["endTime"].substring(0,10)+'</i></p>');
											pArr.push('</div>');
											pArr.push('</div>');
											//pArr.push('<div class="fl rg_oper rg_oper4"><a style="margin-bottom:10px;">修改</a><a onClick="workDelete(this)" aid="'+data[i]["id"]+'">删除</a></div>');
											pArr.push('<div class="fl rg_oper rg_oper4"><a onClick="workDelete(this)" aid="'+data[i]["id"]+'">删除</a></div>');
											pArr.push('</div>');
										}
										
										pStr = pArr.join("");
										$(_this).parent().parent().find("div.founderWorkList").html(pStr);
									},
									error: function(request){
										console.log("保存项目基本信息异常");
									}
								});
								
								$("#workName,#workPosition,#workStart,#workEnd").val("");
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
	
	function isNullBuild(str, id, top, left, j){
		if(!str){
			showClass(id.attr("nullMessage"), id, top, left);
			if(j){
				var jTop = id.offset().top -120;
				$("html,body").animate({
					scrollTop : jTop +"px"
				},"800");
			}
			return false; 
		}
		
		if(str.length>30){
			showClass("不能超过30个字", id, top, left);
			if(j){
				var jTop = id.offset().top -120;
				$("html,body").animate({
					scrollTop : jTop +"px"
				},"800");
			}
			return false; 
		}
		
		AlertDialog.hide();
		return true;
	}
	
	function showClass(text, id, t, l, jump){
		var thisT = id.offset().top-id.height()-t, thisL = id.offset().left+id.width()-l; 
		var alertStr = '';
		alertStr = '<div id="tip_div" class="tip_div">' + 
					'<span class="tip_z tip_jz">◆</span>' + 
					'<div><nobr>'+text+'</nobr></div>' + 
					'</div>';
		$("#tip_div").remove();
		$("body").append($(alertStr));
		$("#tip_div").css({"left":thisL+"px","top":thisT+"px"});
		$("#tip_div").show("slow");
		if(jump){
			$("html, body").animate({
				scrollTop:  (thisT-60)+"px"
			}, 800);
		}
	}
	
}

//创业经历
function founderStartupBtn(_this){
	var inputA = $(_this).parent().find("input").eq(0);
	var inputB = $(_this).parent().find("input").eq(1);
	var inputC = $(_this).parent().find("input").eq(2);
	var inputD = $(_this).parent().find("input").eq(3);
	var textareaA = $(_this).parent().find("textarea");
	if(isNullBuild(inputA.val(), inputA, 10, 40, "jump")){
		if(isNullBuild(inputB.val(), inputB, 10, 40, "jump")){
			if(isNullBuild(inputC.val(), inputC, 10, 40, "jump")){
				if(isNullBuild(inputD.val(), inputD, 10, 40, "jump")){
					if(isNullBuildTextarea(textareaA.val(), textareaA, -30, 40, "jump")){
						$.ajax({
							url: path+"/crowdfundingFounder/saveFounderBusiness.html",
							type: "post",
							dataType: "json",
							data: {
								"loanNo":loanNo,
								"founderId":$(_this).attr("aid"),
								"company": inputA.val(),
								"startTime": inputC.val(),
								"endTime": inputD.val(),
								"position": inputB.val(),
								"description":textareaA.val()
							},
							success: function(data){
								if(data["success"]){
									$.ajax({
										url: path+"/crowdfundingFounder/getFounderBusinessList.html",
										type: "post",
										dataType: "json",
										data: {
											"founderId":$(_this).attr("aid")
										},
										success: function(data){
											var pArr = [], pStr = '',l=data["rows"].length, data = data["rows"];
											for(var i=0;i<l;i++){
												
												pArr.push('<div class="clearfix"  style="margin-top: 10px;" >');
												pArr.push('<div class="fl txt_div txt_div2 hg208">');
												pArr.push('<div class="clearfix wid256">');
												pArr.push('<p class="fl">');
												pArr.push(' 公司名称：<i>'+data[i]["company"]+'</i>');
												pArr.push('</p>');
												pArr.push('<p class="fl mr0">');
												pArr.push(' 职位：<i>'+data[i]["position"]+'</i>');
												pArr.push('</p>');
												pArr.push('</div>');
												pArr.push('<div class="clearfix wid256">');
												pArr.push('<p class="fl">');
												pArr.push(' 开始时间：<i>'+data[i]["startTime"].substring(0,10)+'</i>');
												pArr.push('</p>');
												pArr.push('<p class="fl mr0">');
												pArr.push('结束时间：<i>'+data[i]["endTime"].substring(0,10)+'</i>');
												pArr.push('</p>');
												pArr.push('</div>');
												pArr.push('<div class="clearfix">');
												pArr.push('<p class="clearfix" style="margin-right:0">');
												pArr.push('<span class="fl">描述：</span>');
												pArr.push('<i class="fl wid549">'+data[i]["description"]+'</i>');
												pArr.push(' </p>');
												pArr.push('</div>');
												pArr.push('</div>');
												//pArr.push('<div class="fl rg_oper rg_oper6"><a style="margin-bottom:10px;">修改</a><a onClick="startupDelete(this)" aid="'+data[i]["id"]+'">删除</a></div>');
												pArr.push('<div class="fl rg_oper rg_oper6"><a onClick="startupDelete(this)" aid="'+data[i]["id"]+'">删除</a></div>');
												pArr.push('</div>');
												
												
											}
											
											pStr = pArr.join("");
											$(_this).parent().parent().find("div.founderWorkList").html(pStr);
										},
										error: function(request){
											console.log("保存项目基本信息异常");
										}
									});
									
									
									$("#startupName,#startupPosition,#startupStart,#startupEnd,#startupDescribe").val("");
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
	
	function isNullBuild(str, id, top, left, j){
		if(!str){
			showClass(id.attr("nullMessage"), id, top, left);
			if(j){
				var jTop = id.offset().top -120;
				$("html,body").animate({
					scrollTop : jTop +"px"
				},"800");
			}
			return false; 
		}
		
		if(str.length>30){
			showClass("不能超过30个字", id, top, left);
			if(j){
				var jTop = id.offset().top -120;
				$("html,body").animate({
					scrollTop : jTop +"px"
				},"800");
			}
			return false; 
		}
		
		AlertDialog.hide();
		return true;
	}
	
	function isNullBuildTextarea(str, id, top, left, j){
		if(!str){
			showClass(id.attr("nullMessage"), id, top, left);
			if(j){
				var jTop = id.offset().top -120;
				$("html,body").animate({
					scrollTop : jTop +"px"
				},"800");
			}
			return false; 
		}
		AlertDialog.hide();
		return true;
	}
	
	function showClass(text, id, t, l, jump){
		var thisT = id.offset().top-id.height()-t, thisL = id.offset().left+id.width()-l; 
		var alertStr = '';
		alertStr = '<div id="tip_div" class="tip_div">' + 
					'<span class="tip_z tip_jz">◆</span>' + 
					'<div><nobr>'+text+'</nobr></div>' + 
					'</div>';
		$("#tip_div").remove();
		$("body").append($(alertStr));
		$("#tip_div").css({"left":thisL+"px","top":thisT+"px"});
		$("#tip_div").show("slow");
		if(jump){
			$("html, body").animate({
				scrollTop:  (thisT-60)+"px"
			}, 800);
		}
	}
	
}

//教育经历

function educationBtn(_this){
	var inputA = $(_this).parent().find("input").eq(0);
	var inputB = $(_this).parent().find("input").eq(1);
	var inputC = $(_this).parent().find("input").eq(2);
	var inputD = $(_this).parent().find("input").eq(3);
	var inputE = $(_this).parent().find("input").eq(4);
	if(isNullBuild(inputA.val(), inputA, 10, 40, "jump")){
		if(isNullBuild(inputB.val(), inputB, 10, 40, "jump")){
			if(isNullBuild(inputC.val(), inputC, 10, 40, "jump")){
				if(isNullBuild(inputD.val(), inputD, 10, 40, "jump")){
					if(isNullBuild(inputE.val(), inputE, 10, 40, "jump")){
						$.ajax({
							url: path+"/crowdfundingFounder/saveFounderEducations.html",
							type: "post",
							dataType: "json",
							data: {
								"loanNo":loanNo,
								"founderId":$(_this).attr("aid"),
								"school": inputA.val(),
								"startTime": inputB.val(),
								"endTime": inputC.val(),
								"graduated": inputD.val(),
								"degree":inputE.val()
							},
							success: function(data){
								if(data["success"]){
									$.ajax({
										url: path+"/crowdfundingFounder/getFounderEducationsList.html",
										type: "post",
										dataType: "json",
										data: {
											"founderId":$(_this).attr("aid")
										},
										success: function(data){
											var pArr = [], pStr = '',l=data["rows"].length, data = data["rows"];
											for(var i=0;i<l;i++){
												
												pArr.push('<div class="clearfix" style="margin-top: 10px;" >');
												pArr.push('<div class="fl txt_div txt_div2 wid200">');
												pArr.push('<div class="clearfix mt8">');
												pArr.push('<p class="fl" style="margin-right:32px;">学校名称：<i>'+data[i]["school"]+'</i></p>');
												pArr.push('<p class="fl mr0">开始时间：<i>'+data[i]["startTime"].substring(0,10)+'</i></p>');
												pArr.push('<p class="fl mr0">结束时间：<i>'+data[i]["endTime"].substring(0,10)+'</i></p>');
												pArr.push('</div>');
												pArr.push('<div class="clearfix">');
												pArr.push('<p class="fl" style="margin-right:32px;"><span>专业：</span><i>'+data[i]["graduated"]+'</i></p>');
												pArr.push('<p class="fl mr0"><span>学位：</span><i>'+data[i]["degree"]+'</i></p>');
												pArr.push('</div>');
												pArr.push('</div>');
												//pArr.push('<div class="fl rg_oper rg_oper4"><a style="margin-bottom:10px;">修改</a><a onClick="educationDelete(this)" aid="'+data[i]["id"]+'">删除</a></div>');
												pArr.push('<div class="fl rg_oper rg_oper4"><a onClick="educationDelete(this)" aid="'+data[i]["id"]+'">删除</a></div>');
												pArr.push('</div>');
											}
											
											pStr = pArr.join("");
											$(_this).parent().parent().find("div.founderWorkList").html(pStr);
											
										},
										error: function(request){
											console.log("保存项目基本信息异常");
										}
									});
									
									
									$("#educationSchool,#educationStart,#educationEnd,#educationMajor,#educationDegree").val("");
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
	
	function isNullBuild(str, id, top, left, j){
		if(!str){
			showClass(id.attr("nullMessage"), id, top, left);
			if(j){
				var jTop = id.offset().top -120;
				$("html,body").animate({
					scrollTop : jTop +"px"
				},"800");
			}
			return false; 
		}
		
		if(str.length>30){
			showClass("不能超过30个字", id, top, left);
			if(j){
				var jTop = id.offset().top -120;
				$("html,body").animate({
					scrollTop : jTop +"px"
				},"800");
			}
			return false; 
		}
		
		AlertDialog.hide();
		return true;
	}
	
	function showClass(text, id, t, l, jump){
		var thisT = id.offset().top-id.height()-t, thisL = id.offset().left+id.width()-l; 
		var alertStr = '';
		alertStr = '<div id="tip_div" class="tip_div">' + 
					'<span class="tip_z tip_jz">◆</span>' + 
					'<div><nobr>'+text+'</nobr></div>' + 
					'</div>';
		$("#tip_div").remove();
		$("body").append($(alertStr));
		$("#tip_div").css({"left":thisL+"px","top":thisT+"px"});
		$("#tip_div").show("slow");
		if(jump){
			$("html, body").animate({
				scrollTop:  (thisT-60)+"px"
			}, 800);
		}
	}
	
}

function educationDelete(_this){
	$.ajax({
		url: path+"/crowdfundingFounder/deleteFounderEducations.html",
		type: "post",
		dataType: "json",
		data: {
			"id":$(_this).attr("aid")
		},
		success: function(data){
			if(data["success"]){
				$(_this).parent().parent().remove();
				AlertDialog.tip("删除成功");
			}else{
				AlertDialog.tip(data["msg"]);
			}
		},
		error: function(request){
			console.log("保存项目基本信息异常");
		}
	});
}

function startupDelete(_this){
	$.ajax({
		url: path+"/crowdfundingFounder/deleteFounderBusiness.html",
		type: "post",
		dataType: "json",
		data: {
			"id":$(_this).attr("aid")
		},
		success: function(data){
			if(data["success"]){
				$(_this).parent().parent().remove();
				AlertDialog.tip("删除成功");
			}else{
				AlertDialog.tip(data["msg"]);
			}
		},
		error: function(request){
			console.log("保存项目基本信息异常");
		}
	});
}

function workDelete(_this){
	$.ajax({
		url: path+"/crowdfundingFounder/deleteFounderWorks.html",
		type: "post",
		dataType: "json",
		data: {
			"id":$(_this).attr("aid")
		},
		success: function(data){
			if(data["success"]){
				$(_this).parent().parent().remove();
				AlertDialog.tip("删除成功");
			}else{
				AlertDialog.tip(data["msg"]);
			}
		},
		error: function(request){
			console.log("保存项目基本信息异常");
		}
	});
}

function founderDelete(_this){
	AlertDialog.confirm(modifyDelete, null, "您确定要删除吗?", "确定", "取消");
	function modifyDelete(){
		$.ajax({
			url: path+"/crowdfundingFounder/deleteFounder.html",
			type: "post",
			dataType: "json",
			data: {
				"id":$(_this).attr("aid")
			},
			success: function(data){
				if(data["success"]){
					$(_this).parent().parent().remove();
					AlertDialog.tip("删除成功");
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

function isNullFounder(str, id, top, left, j){
	if(!str){
		founderShowClass($("#"+id).attr("nullMessage"), id, top, left);
		if(j){
			var jTop = $("#"+id).offset().top -120;
			$("html,body").animate({
				scrollTop : jTop +"px"
			},"800");
		}
		return false; 
	}
	
	if(str.length>30){
		founderShowClass("不能超过30个字", id, top, left);
		if(j){
			var jTop = $("#"+id).offset().top -120;
			$("html,body").animate({
				scrollTop : jTop +"px"
			},"800");
		}
		return false; 
	}
	
	AlertDialog.hide();
	return true;
}

function founderShowClass(text, id, t, l, jump){
	var thisT = $("#"+id).offset().top-$("#"+id).height()-t, thisL = $("#"+id).offset().left+$("#"+id).width()-l; 
	var alertStr = '';
	alertStr = '<div id="tip_div" class="tip_div">' + 
				'<span class="tip_z tip_jz">◆</span>' + 
				'<div><nobr>'+text+'</nobr></div>' + 
				'</div>';
	$("#tip_div").remove();
	$("body").append($(alertStr));
	$("#tip_div").css({"left":thisL+"px","top":thisT+"px"});
	$("#tip_div").show("slow");
	if(jump){
		$("html, body").animate({
			scrollTop:  (thisT-60)+"px"
		}, 800);
	}
}

//保存项目基本信息
function saveFounder(){
	if(isNullFounder($("#loanName").val(), "loanName", 10, 40, "jump")){
		if(isNullFounder($("#position").val(), "position", 10, 40, "jump")){
			$.ajax({
				url: path+"/crowdfundingFounder/saveFounder.html",
				type: "post",
				dataType: "json",
				data: {
					"loanNo":loanNo,
					"name": $("#loanName").val(),
					"position": $("#position").val()
				},
				success: function(data){
					if(data["success"]){
						founderId = data["id"];
						AlertDialog.tip("保存成功");
						//window.location.href = path + "/common/enterStock-company.html?loanNo="+loanNo;
						queryFounder();
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

function queryFounder(){
	$.ajax({
		url: path+"/ crowdfundingFounder/selectFounderPageList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo":loanNo
		},
		success: function(data){
			
			var pArr = [], pStr = '',l=data["rows"].length, data = data["rows"];
			for(var i=0;i<l;i++){
				pArr.push('<div class="founderList">');
				pArr.push('<div class="clearfix founderList-tit">');
				pArr.push('<span class="fl" title="'+data[i]["name"]+'">'+data[i]["name"]+'</span><span class="fl" title="'+data[i]["position"]+'">'+data[i]["position"]+'</span><span class="fl" name="gzjl" style="cursor:pointer;"> <em>*</em> 添加工作经历</span><span class="fl" name="cyjl" style="cursor:pointer;"><em>*</em> 添加创业经历</span><span class="fl" name="jyjl" style="cursor:pointer;"><em>*</em> 添加教育经历</span><span class="fl cur" onClick="founderDelete(this)" aid="'+data[i]["id"]+'"style="cursor:pointer;">删除</span>');
				pArr.push('</div>');
				
				pArr.push('<div class="spanList">');
				pArr.push('<div class="mb18 clearfix gzjl" style="display:none;">');
				pArr.push('<div class="clearfix" style="width:685px;margin:0 auto;">');
				pArr.push('<div class="fl txt_div txt_div2">');
				pArr.push('<div class="clearfix mb18">');
				pArr.push('<p class="fl">');
				pArr.push('<span>公司名称：</span>');
				pArr.push('<input type="text"  placeholder="" class=" wd245" id="workName" nullMessage="公司名称不能为空" />');
				pArr.push('</p>');
				pArr.push('<p class="fr mr0">');
				pArr.push('<span>职位：</span>');
				pArr.push('<input type="text"  placeholder="" class=" wd165" id="workPosition" nullMessage="职位不能为空"/>');
				pArr.push('</p>');
				pArr.push('</div>');
				pArr.push('<div class="clearfix mb18">');
				pArr.push($("#work").html());
//				pArr.push('<p class="fl">');
//				pArr.push('<span>开始时间：</span>');
//				pArr.push('<input type="text"  placeholder=""  id="workStart" class="wd165 calend_bg" nullMessage="开始时间不能为空" onfocus="WdatePicker({isShowOK:false,isShowClear:false,isShowToday:false,readOnly:true})"/>');
//				pArr.push('</p>');
//				pArr.push('<p class="fr mr0">');
//				pArr.push('<span>结束时间：</span>');
//				pArr.push('<input type="text"  placeholder=""  id="workEnd" class=" wd165 calend_bg" nullMessage="结束时间不能为空" onfocus="WdatePicker({isShowOK:false,isShowClear:false,isShowToday:false,readOnly:true})"/>');
//				pArr.push('</p>');
				pArr.push('</div>');
				pArr.push('</div>');
				pArr.push('<div class="fl rg_oper rg_oper3" onClick="founderWorkBtn(this)"  aid="'+data[i]["id"]+'" ><a>添加</a></div>');
				pArr.push('</div>');
				pArr.push('<div class="founderWorkList" id="WorkList1">');
				
				$.ajax({
					url: path+"/crowdfundingFounder/getFounderWorksList.html",
					type: "post",
					async: false,
					dataType: "json",
					data: {
						"founderId":data[i]["id"]
					},
					success: function(dataA){
						var z=dataA["rows"].length, dataA = dataA["rows"];
						for(var i=0;i<z;i++){
							pArr.push('<div class="clearfix" style="margin-top: 10px;">');
							pArr.push('<div class="fl txt_div txt_div3">');
							pArr.push('<div class="clearfix">');
							pArr.push('<p class="fl">公司名称：<i>'+dataA[i]["company"]+'</i></p>');
							pArr.push('<p class="fl mr0">职位：<i>'+dataA[i]["position"]+'</i></p>');
							pArr.push('</div>');
							pArr.push('<div class="clearfix">');
							pArr.push('<p class="fl">开始时间：<i>'+dataA[i]["startTime"].substring(0,10)+'</i></p>');
							pArr.push('<p class="fl mr0">结束时间：<i>'+dataA[i]["endTime"].substring(0,10)+'</i></p>');
							pArr.push('</div>');
							pArr.push('</div>');
							//pArr.push('<div class="fl rg_oper rg_oper4"><a style="margin-bottom:10px;">修改</a><a onClick="workDelete(this)" aid="'+dataA[i]["id"]+'">删除</a></div>');
							pArr.push('<div class="fl rg_oper rg_oper4"><a onClick="workDelete(this)" aid="'+dataA[i]["id"]+'">删除</a></div>');
							pArr.push('</div>');
							
							
							
						}
						
						
						
					},
					error: function(request){
						console.log("保存项目基本信息异常");
					}
				});
				
				
				pArr.push('</div>');
				pArr.push('</div>');
				
				pArr.push('<div class="mb18 clearfix cyjl" style="display:none;">');
				pArr.push('<div class="clearfix" style="width:685px;margin:0 auto;">');
				pArr.push('<div class="fl txt_div txt_div2 mb18 txt_div4">');
				pArr.push('<div class="clearfix mb18">');
				pArr.push('<p class="fl">');
				pArr.push('<span>公司名称：</span>');
				pArr.push('<input type="text"  placeholder="" id="startupName" class=" wd245"  nullMessage="公司名称不能为空" />');
				pArr.push('</p>');
				pArr.push('<p class="fr mr0">');
				pArr.push('<span>职位：</span>');
				pArr.push('<input type="text"  placeholder="" id="startupPosition" class=" wd165" nullMessage="职位不能为空" />');
				pArr.push('</p>');
				pArr.push('</div>');
				pArr.push('<div class="clearfix mb18">');
				pArr.push($("#startup").html());
//				pArr.push('<p class="fl">');
//				pArr.push('<span>开始时间：</span>');
//				pArr.push('<input type="text"  placeholder="" id="startupStart" class="wd165 calend_bg" nullMessage="开始时间不能为空"  onfocus="WdatePicker({isShowOK:false,isShowClear:false,isShowToday:false,readOnly:true})"/>');
//				pArr.push('</p>');
//				pArr.push('<p class="fr mr0">');
//				pArr.push('<span>结束时间：</span>');
//				pArr.push('<input type="text"  placeholder="" id="startupEnd" class=" wd165 calend_bg" nullMessage="结束时间不能为空"  onfocus="WdatePicker({isShowOK:false,isShowClear:false,isShowToday:false,readOnly:true})"/>');
//				pArr.push('</p>');
				pArr.push('</div>');
				pArr.push('<div class="clearfix mb18">');
				pArr.push('<p style="margin-right:0" class="clearfix">');
				pArr.push('<span class="fl">描述：</span>');
				pArr.push('<textarea class="fl msh_textar" id="startupDescribe" nullMessage="描述不能为空"  ></textarea>');
				pArr.push('</p>');
				 
				pArr.push('</div>');
				pArr.push('</div>');
				pArr.push('<div class="fl rg_oper rg_oper5"  onClick="founderStartupBtn(this)"  aid="'+data[i]["id"]+'" ><a>添加</a></div>');
				pArr.push('</div>');
				
				pArr.push('<div class="founderWorkList" id="WorkList2">');
				
				$.ajax({
					url: path+"/crowdfundingFounder/getFounderBusinessList.html",
					type: "post",
					async: false,
					dataType: "json",
					data: {
						"founderId":data[i]["id"]
					},
					success: function(dataB){
						
						var z=dataB["rows"].length, dataB = dataB["rows"];
						for(var i=0;i<z;i++){
							
							pArr.push('<div class="clearfix"  style="margin-top: 10px;" >');
							pArr.push('<div class="fl txt_div txt_div2 hg208">');
							pArr.push('<div class="clearfix wid256">');
							pArr.push('<p class="fl">');
							pArr.push(' 公司名称：<i>'+dataB[i]["company"]+'</i>');
							pArr.push('</p>');
							pArr.push('<p class="fl mr0">');
							pArr.push(' 职位：<i>'+dataB[i]["position"]+'</i>');
							pArr.push('</p>');
							pArr.push('</div>');
							pArr.push('<div class="clearfix wid256">');
							pArr.push('<p class="fl">');
							pArr.push(' 开始时间：<i>'+dataB[i]["startTime"].substring(0,10)+'</i>');
							pArr.push('</p>');
							pArr.push('<p class="fl mr0">');
							pArr.push('结束时间：<i>'+dataB[i]["endTime"].substring(0,10)+'</i>');
							pArr.push('</p>');
							pArr.push('</div>');
							pArr.push('<div class="clearfix">');
							pArr.push('<p class="clearfix" style="margin-right:0">');
							pArr.push('<span class="fl">描述：</span>');
							pArr.push('<i class="fl wid549">'+dataB[i]["description"]+'</i>');
							pArr.push(' </p>');
							pArr.push('</div>');
							pArr.push('</div>');
							//pArr.push('<div class="fl rg_oper rg_oper6"><a style="margin-bottom:10px;">修改</a><a onClick="startupDelete(this)" aid="'+dataB[i]["id"]+'">删除</a></div>');
							pArr.push('<div class="fl rg_oper rg_oper6"><a onClick="startupDelete(this)" aid="'+dataB[i]["id"]+'">删除</a></div>');
							pArr.push('</div>');

						}
						
					
					},
					error: function(request){
						console.log("保存项目基本信息异常");
					}
				});
				
				
				pArr.push('</div> ');
				
				
				pArr.push('</div>');
				
				pArr.push('<div class="mb18 clearfix jyjl" style="display:none;">');
				pArr.push('<div class="clearfix"  style="width:685px;margin:0 auto;">');
				pArr.push('<div class="fl txt_div txt_div2 mb18 hg140">');
				pArr.push('<div class="clearfix mb18">');
				pArr.push('<p class="fl">');
				pArr.push('<span>学校名称：</span>');
				pArr.push('<input type="text"  placeholder="" id="educationSchool" class=" wd245" nullMessage="学校名称不能为空" />');
				pArr.push('</p>');
				pArr.push('</div>');
				pArr.push('<div class="clearfix mb18">');
				pArr.push($("#education").html());
//				pArr.push('<p class="fl">');
//				pArr.push('<span>开始时间：</span>');
//				pArr.push('<input type="text"  placeholder="" id="educationStart" class="wd165 calend_bg" nullMessage="开始时间不能为空" onfocus="WdatePicker({isShowOK:false,isShowClear:false,isShowToday:false,readOnly:true})"/>');
//				pArr.push('</p>');
//				pArr.push('<p class="fr mr0">');
//				pArr.push('<span>结束时间：</span>');
//				pArr.push('<input type="text"  placeholder="" id="educationEnd" class=" wd165 calend_bg" nullMessage="结束时间不能为空" onfocus="WdatePicker({isShowOK:false,isShowClear:false,isShowToday:false,readOnly:true})"/>');
//				pArr.push('</p>');
				pArr.push('</div>');
				pArr.push('<div class="clearfix mb18">');
				pArr.push('<p class="fl"><span>专业：</span><input type="text"  id="educationMajor" placeholder="" nullMessage="专业不能为空" class="wd165"/></p>');
				pArr.push('<p class="fr mr0"><span>学位：</span><input type="text" id="educationDegree" placeholder="" nullMessage="学位不能为空" class=" wd165"/></p>');
				pArr.push('</div>');
				pArr.push('</div>');
				pArr.push('<div class="fl rg_oper rg_oper7" onClick="educationBtn(this)"  aid="'+data[i]["id"]+'"><a>添加</a></div>');
				pArr.push('</div>');
				
				pArr.push('<div class="founderWorkList" id="WorkList3">');

				$.ajax({
					url: path+"/crowdfundingFounder/getFounderEducationsList.html",
					type: "post",
					async: false,
					dataType: "json",
					data: {
						"founderId":data[i]["id"]
					},
					success: function(dataC){
						
						var z=dataC["rows"].length, dataC = dataC["rows"];
						for(var i=0;i<z;i++){
							
							pArr.push('<div class="clearfix" style="margin-top: 10px;" >');
							pArr.push('<div class="fl txt_div txt_div2 wid200">');
							pArr.push('<div class="clearfix mt8">');
							pArr.push('<p class="fl" style="margin-right:32px;">学校名称：<i>'+dataC[i]["school"]+'</i></p>');
							pArr.push('<p class="fl mr0">开始时间：<i>'+dataC[i]["startTime"].substring(0,10)+'</i></p>');
							pArr.push('<p class="fl mr0">结束时间：<i>'+dataC[i]["endTime"].substring(0,10)+'</i></p>');
							pArr.push('</div>');
							pArr.push('<div class="clearfix">');
							pArr.push('<p class="fl" style="margin-right:32px;"><span>专业：</span><i>'+dataC[i]["graduated"]+'</i></p>');
							pArr.push('<p class="fl mr0"><span>学位：</span><i>'+dataC[i]["degree"]+'</i></p>');
							pArr.push('</div>');
							pArr.push('</div>');
							//pArr.push('<div class="fl rg_oper rg_oper4"><a style="margin-bottom:10px;">修改</a><a onClick="educationDelete(this)" aid="'+dataC[i]["id"]+'">删除</a></div>');
							pArr.push('<div class="fl rg_oper rg_oper4"><a onClick="educationDelete(this)" aid="'+dataC[i]["id"]+'">删除</a></div>');
							pArr.push('</div>');
							
						}
						
					
					},
					error: function(request){
						console.log("保存项目基本信息异常");
					}
				});

				pArr.push('</div>');
				
				
				pArr.push('</div>');
				pArr.push('</div>');	
				pArr.push('</div>');
			}
			pStr = pArr.join("");
			$("#founderWorkList").html(pStr);
			$("#loanName,#position").val("");
			$(".founderList-tit span").click(function(){
				if($(this).index()>1&&$(this).index()<5){
					$(".founderList-tit span").removeClass("cur");
					$(this).addClass("cur");
					$(this).parent().parent().find("div.spanList>div").hide();
					$(this).parent().parent().find("div.spanList").find("."+$(this).attr("name")).show();
				}
			});
		},
		error: function(request){
			console.log("保存项目基本信息异常");
		}
	});
}