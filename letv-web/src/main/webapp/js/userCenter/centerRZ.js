if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
var type = window.location.search.substring(window.location.search.indexOf("type=")+5);
var orgLeadInvestorId=Math.floor(Math.random()*1000000000000000000000),orgInvestorId=Math.floor(Math.random()*1000000000000000000000),leadInvestorId=Math.floor(Math.random()*1000000000000000000000)
$(function(){
	$("#investPlanAmt1,#investPlanAmt,#investPlanAmt3,#investPlanAmt4").keyup(function(){
		if(isNaN($(this).val()) || $(this).val().indexOf(" ")>=0){
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	});
	$("#investPlanAmt1,#investPlanAmt,#investPlanAmt3,#investPlanAmt4").blur(function(){
		var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
		if(!reg.test($(this).val())){
			//AlertDialog.show("不能超过两位小数", $(this).attr("id"), 10, 20, "jump");
			$(this).val(Math.floor($(this).val() * 100) / 100 );
			return false;
		}
	});
	$("#investPlanNum1,#investPlanNum,#investPlanNum3,#investPlanNum4").keyup(function(){
		if(isNaN($(this).val().substring($(this).val().length-1,$(this).val().length))){
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	})
	$("#companyName,#position,#concernCity,#companyName1,#position1,#concernCity1,#institution,#concernCity3,#institution4,#concernCity4").keyup(function(){
		if($(this).val().length>30){
			AlertDialog.show("不能超过30个字！", $(this).attr("id"), 0, 30, "jump");
			$(this).val($(this).val().substring(0,30));
		}
	});
//	$("#rzTab>li").click(function(){
//		$("#tip_div").hide();
//		$("#rzTab li a").removeClass("cur");
//		$(this).find("a").addClass("cur");
//		$("#rzTab-list>div").hide();
//		$("#rzTab-list>div").eq($(this).index()).show();
//		crowdfundUserTzr($(this).attr("type"));//信息查询
//	});
	$("#rzTab li").hide();
	$("#rzTab-list>div").hide();
	$("#rzTab li").each(function(){
		 if($(this).attr("type") == type){
			 $(this).show();
			 $(this).find("a").addClass("cur");
			 $("#rzTab-list>div").eq($(this).index()).show();
		 }
	});
	
	crowdfundUserTzr(type);//信息查询
	getUserInfo();

	//crowdfundUserTzr("investAuthState");//信息查询
	$("#subInvestBtnCz").unbind("click").click(subInvestRZCZ);	
	$("#subLTRZBtCz").unbind("click").click(subLTRZCZ);
	$("#bodiesGTRZBtnCz").unbind("click").click(bodiesGTRZCZ);
	$("#bodiesLTRZBtnCz").unbind("click").click(bodiesLTRZCZ);
	
});

function getUserInfo(){
	$.ajax({
		url: path + "/user/getUserInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			data = data["msg"];
			$("#realName,#realName1").val(data["realName"]).attr("readonly","readonly");
			$("#certNo,#certNo1").val(data["certificateNo"]).attr("readonly","readonly");
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
	$.ajax({
		url: path + "/enterpriseMember/getById.html",
		type: "post",
		dataType: "json",
		data:{
			"userId":siteUserId
		},
		success: function(data){
			var data = data["msg"];
			$("#realName4,#realName3").val(data["enterpriseName"]).attr("readonly","readonly");
			$("#certNo4,#certNo3").val(data["organizationNo"]).attr("readonly","readonly");
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}

//重置
function subInvestRZCZ(){
	//$("#realName,#certNo,#companyName,#position,#concernCity,#investRatio,#investExperience,#investPlanNum,#investPlanAmt,#loan_logo_url").val("");
	$("#companyName,#position,#concernCity,#investRatio,#investExperience,#investPlanNum,#investPlanAmt,#loan_logo_url").val("");
	$("#imghead").attr("src","");
	$("#imgheadLi").hide();
	$("#concern_industryBox input").attr("checked",false);  
}
function subLTRZCZ(){
	//$("#realName1,#certNo1,#companyName1,#position1,#concernCity1,#investRatio1,#investExperience1,#investPlanNum1,#investPlanAmt1,#loan_logo_url1,#orgLoanReceiveProve1,#orgLoanReceiveProve2").val("");
	$("#companyName1,#position1,#concernCity1,#investRatio1,#investExperience1,#investPlanNum1,#investPlanAmt1,#loan_logo_url1,#orgLoanReceiveProve1,#orgLoanReceiveProve2").val("");
	$("#imghead1").attr("src","");
	$("#imgheadLi1").hide();
	$("#concern_industryBox1 input").attr("checked",false); 
	$("#uploadInFo,#uploadInFo1").empty();
}
function bodiesGTRZCZ(){
	//$("#institution,#realName3,#certNo3,#concernCity3,#investRatio3,#investExperience3,#investPlanNum3,#investPlanAmt3,#orgLoanReceiveProve3").val("");
	$("#institution,#concernCity3,#investRatio3,#investExperience3,#investPlanNum3,#investPlanAmt3,#orgLoanReceiveProve3").val("");
	$("#concern_industryBox3 input").attr("checked",false); 
	$("#uploadInFo3").empty();
}
function bodiesLTRZCZ(){
	//$("#institution4,#realName4,#certNo4,#concernCity4,#investRatio4,#investExperience4,#investPlanNum4,#investPlanAmt4,#orgLoanReceiveProve4,#orgLoanReceiveProve5,#orgLoanReceiveProve6").val("");
	$("#institution4,#concernCity4,#investRatio4,#investExperience4,#investPlanNum4,#investPlanAmt4,#orgLoanReceiveProve4,#orgLoanReceiveProve5,#orgLoanReceiveProve6").val("");
	$("#concern_industryBox4 input").attr("checked",false); 
	$("#uploadInFo4,#uploadInFo5,#uploadInFo6").empty();
}

//提交投资认证申请
function subInvestRZ(type){
	var ci = '';
	//将勾选的投资领域赋值
	$.each($("#concern_industryBox").find("input:checked"),function(i, k){
		if(i == $("#concern_industryBox").find("input:checked").length-1){
			ci += $(k).val();
		}else{
			ci += $(k).val() + ",";
		}
	});
	$("#concern_industry").val(ci);
	var data = {
			"investAuthState":type,
			"realName": $("#realName").val(),
			"certNo": $("#certNo").val(),
			"companyName": $("#companyName").val(),
			"position": $("#position").val(),
			"cardPhoto": $("#loan_logo_url").val(),
			"concernIndustry": $("#concern_industry").val(),
			"concernCity": $("#concernCity").val(),
			"investRatio": $("#investRatio").val(),
			"investExperience": $("#investExperience").val(),
			"investPlanNum": $("#investPlanNum").val(),
			"investPlanAmt": $("#investPlanAmt").val()
		}
	if(Valify.realName($("#realName").val(), "realName")){
		if(Valify.cardCode($("#certNo").val(), "certNo")){
			if(Valify.isNull($("#concern_industry").val(), "concern_industryBox",-25,650,"jTop")){
				if(investRatioNaN($("#investRatio").val(), "investRatio")){
					if($('input[name=tzjl]:checked').attr("num") == 0){
						if(Valify.isNull($("#investExperience").val(), "investExperience",-60,20,"jTop")){
							if(Valify.isNull($("#investPlanNum").val(), "investPlanNum",5,20,"jTop")){
								if(Valify.isNull($("#investPlanAmt").val(), "investPlanAmt",5,20,"jTop")){
									if(!$("#read").prop("checked")){
										AlertDialog.show("请阅读协议并勾选", "read", 25, 23);
										return false;
									}
									$.ajax({
										url: path + "/userAuth/applyInvestorAuth.html",
										type: "post",
										dataType: "json",
										data: data,
										success: function(data){
											if(!data["success"]){
												AlertDialog.tip(data["msg"]);
												return false;
											}
											if(type == 'auditing'){
												AlertDialog.tip("提交投资认证申请成功");
												setTimeout(function(){
													window.location.reload(true);
												},1500);
											}else{
												AlertDialog.tip("保存投资认证申请成功");
												setTimeout(function(){
													window.location.reload(true);
												},1500);
											}
											
										},
										error: function(request){
											console.log("保存投资认证异常");
										}
									});
								}
							}
						}
					}else{
						if(Valify.isNull($("#investPlanNum").val(), "investPlanNum",5,20,"jTop")){
							if(Valify.isNull($("#investPlanAmt").val(), "investPlanAmt",5,20,"jTop")){
								if(!$("#read").prop("checked")){
									AlertDialog.show("请阅读协议并勾选", "read", 25, 23);
									return false;
								}
								$.ajax({
									url: path + "/userAuth/applyInvestorAuth.html",
									type: "post",
									dataType: "json",
									data: data,
									success: function(data){
										if(!data["success"]){
											AlertDialog.tip(data["msg"]);
											return false;
										}
										if(type == 'auditing'){
											AlertDialog.tip("提交投资认证申请成功");
											setTimeout(function(){
												window.location.reload(true);
											},1500);
										}else{
											AlertDialog.tip("保存投资认证申请成功");
											setTimeout(function(){
												window.location.reload(true);
											},1500);
										}
									},
									error: function(request){
										console.log("保存投资认证异常");
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
function XsubInvestRZ(id,type){
	var ci = '';
	//将勾选的投资领域赋值
	$.each($("#concern_industryBox").find("input:checked"),function(i, k){
		if(i == $("#concern_industryBox").find("input:checked").length-1){
			ci += $(k).val();
		}else{
			ci += $(k).val() + ",";
		}
	});
	$("#concern_industry").val(ci);
	if(type == "add"){
		var investAuthState = "add";
	}else{
		var investAuthState = "auditing";
	}
	
	if(Valify.realName($("#realName").val(), "realName")){
		if(Valify.cardCode($("#certNo").val(), "certNo")){
			if(Valify.isNull($("#concern_industry").val(), "concern_industryBox",-25,650,"jTop")){
				if(investRatioNaN($("#investRatio").val(), "investRatio")){
					if($('input[name=tzjl]:checked').attr("num") == 0){
						if(Valify.isNull($("#investExperience").val(), "investExperience",-60,20,"jTop")){
							if(Valify.isNull($("#investPlanNum").val(), "investPlanNum",5,20,"jTop")){
								if(Valify.isNull($("#investPlanAmt").val(), "investPlanAmt",5,20,"jTop")){
									if(!$("#read").prop("checked")){
										AlertDialog.show("请阅读协议并勾选", "read", 25, 23);
										return false;
									}
									$.ajax({
										url: path + "/userAuth/updateUserAuth.html",
										type: "post",
										dataType: "json",
										data: {
											"id":id,
											"investAuthState":investAuthState,
											"realName": $("#realName").val(),
											"certNo": $("#certNo").val(),
											"companyName": $("#companyName").val(),
											"position": $("#position").val(),
											"cardPhoto": $("#loan_logo_url").val(),
											"concernIndustry": $("#concern_industry").val(),
											"concernCity": $("#concernCity").val(),
											"investRatio": $("#investRatio").val(),
											"investExperience": $("#investExperience").val(),
											"investPlanNum": $("#investPlanNum").val(),
											"investPlanAmt": $("#investPlanAmt").val()
										},
										success: function(data){
											if(!data["success"]){
												AlertDialog.tip(data["msg"]);
												return false;
											}
											if(type == "add"){
												AlertDialog.tip("保存成功");
											}else{
												AlertDialog.tip("提交成功");
											}
											setTimeout(function(){
												window.location.reload(true);
											},1500);
										},
										error: function(request){
											console.log("保存投资认证异常");
										}
									});
								}
							}
						}
						
					}else{
						if(Valify.isNull($("#investPlanNum").val(), "investPlanNum",5,20,"jTop")){
							if(Valify.isNull($("#investPlanAmt").val(), "investPlanAmt",5,20,"jTop")){
								if(!$("#read").prop("checked")){
									AlertDialog.show("请阅读协议并勾选", "read", 25, 23);
									return false;
								}
								$.ajax({
									url: path + "/userAuth/updateUserAuth.html",
									type: "post",
									dataType: "json",
									data: {
										"id":id,
										"investAuthState":"auditing",
										"realName": $("#realName").val(),
										"certNo": $("#certNo").val(),
										"companyName": $("#companyName").val(),
										"position": $("#position").val(),
										"cardPhoto": $("#loan_logo_url").val(),
										"concernIndustry": $("#concern_industry").val(),
										"concernCity": $("#concernCity").val(),
										"investRatio": $("#investRatio").val(),
										"investExperience": $("#investExperience").val(),
										"investPlanNum": $("#investPlanNum").val(),
										"investPlanAmt": $("#investPlanAmt").val()
									},
									success: function(data){
										if(!data["success"]){
											AlertDialog.tip(data["msg"]);
											return false;
										}
										if(type == "add"){
											AlertDialog.tip("保存成功");
										}else{
											AlertDialog.tip("提交成功");
										}
										setTimeout(function(){
											window.location.reload(true);
										},1500);
									},
									error: function(request){
										console.log("保存投资认证异常");
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

//领投认证
function subLTRZ(type){

	var ci = '';
	//将勾选的投资领域赋值
	$.each($("#concern_industryBox1").find("input:checked"),function(i, k){
		if(i == $("#concern_industryBox1").find("input:checked").length-1){
			ci += $(k).val();
		}else{
			ci += $(k).val() + ",";
		}
	});
	$("#concern_industry1").val(ci);
	var data = {
			"investAuthState":type,
			"tempId":leadInvestorId,
			"realName": $("#realName1").val(),
			"certNo": $("#certNo1").val(),
			"companyName": $("#companyName1").val(),
			"position": $("#position1").val(),
			"cardPhoto": $("#loan_logo_url1").val(),
			"concernIndustry": $("#concern_industry1").val(),
			"concernCity": $("#concernCity1").val(),
			"investRatio": $("#investRatio1").val(),
			"investExperience": $("#investExperience1").val(),
			"investPlanNum": $("#investPlanNum1").val(),
			"investPlanAmt": $("#investPlanAmt1").val(),
			"assetsCredentials": $("#orgLoanReceiveProve1").val(),
			"historicalInvestmentData": $("#orgLoanReceiveProve2").val()
		}
	if(Valify.realName($("#realName1").val(), "realName1")){
		if(Valify.cardCode($("#certNo1").val(), "certNo1")){
			
			if(Valify.isNull($("#concern_industry1").val(), "concern_industryBox1",-25,650,"jTop")){
				if(investRatioNaN($("#investRatio1").val(), "investRatio1")){
					
					if($('input[name=tzjl1]:checked').attr("num") == 0){
						if(Valify.isNull($("#investExperience1").val(), "investExperience1",-60,20,"jTop")){
							if(Valify.isNull($("#investPlanNum1").val(), "investPlanNum1",5,20,"jTop")){
								if(Valify.isNull($("#investPlanAmt1").val(), "investPlanAmt1",5,20,"jTop")){
									if(!$("#orgLoanReceiveProve1").val()){
										AlertDialog.mtip('请上传个人资产认证资料！');
										return false;
									}
									if(!$("#read1").prop("checked")){
										AlertDialog.show("请阅读协议并勾选", "read1", 25, 23);
										return false;
									}
									$.ajax({
										url: path + "/userAuth/applyLeadInvestor.html",
										type: "post",
										dataType: "json",
										data: data,
										success: function(data){
											if(!data["success"]){
												AlertDialog.tip(data["msg"]);
												return false;
											}
											if(type == "auditing"){
												AlertDialog.tip("提交投资认证申请成功");
											}else{
												AlertDialog.tip("保存投资认证申请成功");
											}
											setTimeout(function(){
												window.location.reload(true);
											},1500);
										},
										error: function(request){
											console.log("保存投资认证异常");
										}
									});
								}
							}
						}
					}else{
						if(Valify.isNull($("#investPlanNum1").val(), "investPlanNum1",5,20,"jTop")){
							if(Valify.isNull($("#investPlanAmt1").val(), "investPlanAmt1",5,20,"jTop")){
								if(!$("#orgLoanReceiveProve1").val()){
									AlertDialog.mtip('请上传个人资产认证资料！');
									return false;
								}
								if(!$("#read1").prop("checked")){
									AlertDialog.show("请阅读协议并勾选", "read1", 25, 23);
									return false;
								}
								$.ajax({
									url: path + "/userAuth/applyLeadInvestor.html",
									type: "post",
									dataType: "json",
									data: data,
									success: function(data){
										if(!data["success"]){
											AlertDialog.tip(data["msg"]);
											return false;
										}
										if(type == "auditing"){
											AlertDialog.tip("提交投资认证申请成功");
										}else{
											AlertDialog.tip("保存投资认证申请成功");
										}
										setTimeout(function(){
											window.location.reload(true);
										},1500);
									},
									error: function(request){
										console.log("保存投资认证异常");
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
//领投认证
function XsubLTRZ(id,type){

	var ci = '';
	//将勾选的投资领域赋值
	$.each($("#concern_industryBox1").find("input:checked"),function(i, k){
		if(i == $("#concern_industryBox1").find("input:checked").length-1){
			ci += $(k).val();
		}else{
			ci += $(k).val() + ",";
		}
	});
	$("#concern_industry1").val(ci);
	if(type == "add"){
		var investAuthState = "add";
	}else{
		var investAuthState = "auditing";
	}
	if(Valify.realName($("#realName1").val(), "realName1")){
		if(Valify.cardCode($("#certNo1").val(), "certNo1")){
			if(Valify.isNull($("#concern_industry1").val(), "concern_industryBox1",-25,650,"jTop")){
				if(investRatioNaN($("#investRatio1").val(), "investRatio1")){
					if($('input[name=tzjl1]:checked').attr("num") == 0){
						if(Valify.isNull($("#investExperience1").val(), "investExperience1",-60,20,"jTop")){
							if(Valify.isNull($("#investPlanNum1").val(), "investPlanNum1",5,20,"jTop")){
								if(Valify.isNull($("#investPlanAmt1").val(), "investPlanAmt1",5,20,"jTop")){
									if(!$("#orgLoanReceiveProve1").val()){
										AlertDialog.mtip('请上传个人资产认证资料！');
										return false;
									}
									if(!$("#read1").prop("checked")){
										AlertDialog.show("请阅读协议并勾选", "read1", 25, 23);
										return false;
									}
									$.ajax({
										url: path + "/userAuth/updateUserAuth.html",
										type: "post",
										dataType: "json",
										data: {
											"id":id,
											"investAuthState":investAuthState,
											"realName": $("#realName1").val(),
											"certNo": $("#certNo1").val(),
											"companyName": $("#companyName1").val(),
											"position": $("#position1").val(),
											"cardPhoto": $("#loan_logo_url1").val(),
											"concernIndustry": $("#concern_industry1").val(),
											"concernCity": $("#concernCity1").val(),
											"investRatio": $("#investRatio1").val(),
											"investExperience": $("#investExperience1").val(),
											"investPlanNum": $("#investPlanNum1").val(),
											"investPlanAmt": $("#investPlanAmt1").val(),
											"assetsCredentials": $("#orgLoanReceiveProve1").val(),
											"historicalInvestmentData": $("#orgLoanReceiveProve2").val()
										},
										success: function(data){
											if(!data["success"]){
												AlertDialog.tip(data["msg"]);
												return false;
											}
											if(type == "add"){
												AlertDialog.tip("保存成功");
											}else{
												AlertDialog.tip("提交成功");
											}
											setTimeout(function(){
												window.location.reload(true);
											},1500);
										},
										error: function(request){
											console.log("保存投资认证异常");
										}
									});
								}
							}
						}
						
					}else{
						if(Valify.isNull($("#investPlanNum1").val(), "investPlanNum1",5,20,"jTop")){
							if(Valify.isNull($("#investPlanAmt1").val(), "investPlanAmt1",5,20,"jTop")){
								if(!$("#orgLoanReceiveProve1").val()){
									AlertDialog.mtip('请上传个人资产认证资料！');
									return false;
								}
								if(!$("#read1").prop("checked")){
									AlertDialog.show("请阅读协议并勾选", "read1", 25, 23);
									return false;
								}
								$.ajax({
									url: path + "/userAuth/updateUserAuth.html",
									type: "post",
									dataType: "json",
									data: {
										"id":id,
										"investAuthState":"auditing",
										"realName": $("#realName1").val(),
										"certNo": $("#certNo1").val(),
										"companyName": $("#companyName1").val(),
										"position": $("#position1").val(),
										"cardPhoto": $("#loan_logo_url1").val(),
										"concernIndustry": $("#concern_industry1").val(),
										"concernCity": $("#concernCity1").val(),
										"investRatio": $("#investRatio1").val(),
										"investExperience": $("#investExperience1").val(),
										"investPlanNum": $("#investPlanNum1").val(),
										"investPlanAmt": $("#investPlanAmt1").val(),
										"assetsCredentials": $("#orgLoanReceiveProve1").val(),
										"historicalInvestmentData": $("#orgLoanReceiveProve2").val()
									},
									success: function(data){
										if(!data["success"]){
											AlertDialog.tip(data["msg"]);
											return false;
										}
										if(type == "add"){
											AlertDialog.tip("保存成功");
										}else{
											AlertDialog.tip("提交成功");
										}
										setTimeout(function(){
											window.location.reload(true);
										},1500);
									},
									error: function(request){
										console.log("保存投资认证异常");
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
//跟投机构认证
function bodiesGTRZ(type){

	var ci = '';
	//将勾选的投资领域赋值
	$.each($("#concern_industryBox3").find("input:checked"),function(i, k){
		if(i == $("#concern_industryBox3").find("input:checked").length-1){
			ci += $(k).val();
		}else{
			ci += $(k).val() + ",";
		}
	});
	$("#concern_industry3").val(ci);
	var data = {
			"investAuthState":type,
			"tempId":orgInvestorId,
			"institution": $("#institution").val(),
			"realName": $("#realName3").val(),
			"certNo": $("#certNo3").val(),
			"cardPhoto": $("#orgLoanReceiveProve3").val(),
			"concernIndustry": $("#concern_industry3").val(),
			"concernCity": $("#concernCity3").val(),
			"investRatio": $("#investRatio3").val(),
			"investExperience": $("#investExperience3").val(),
			"investPlanNum": $("#investPlanNum3").val(),
			"investPlanAmt": $("#investPlanAmt3").val()
		}
	if(Valify.isNull($("#institution").val(), "institution",10,20,"jTop")){
		//if(Valify.isNull($("#realName3").val(), "realName3",10,20,"jTop")){
			//if(Valify.cardCode($("#certNo3").val(), "certNo3")){
				if($("#investRatio3").val()){
					var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
					if(isNaN($("#investRatio3").val())){
						AlertDialog.show("请输入正确金额",  "investRatio3", 10, 20, "jump");
						return false;
					}else if(!reg.test($("#investRatio3").val())){
						AlertDialog.show("不能超过两位小数",  "investRatio3", 10, 20, "jump");
						return false;
					}
				}
				if(!$("#orgLoanReceiveProve3").val()){
					AlertDialog.mtip('请上传上传机构证件！');
					return false;
				}
				
				
				if(Valify.isNull($("#concern_industry3").val(), "concern_industryBox3",-25,650,"jTop")){
					if(Valify.isNull($("#concernCity3").val(), "concernCity3",10,20,"jTop")){
						if(investRatioNaN($("#investRatio3").val(), "investRatio3")){
							if($('input[name=tzjl2]:checked').attr("num") == 0){
								if(Valify.isNull($("#investExperience3").val(), "investExperience3",-60,20,"jTop")){
									if(Valify.isNull($("#investPlanNum3").val(), "investPlanNum3",5,20,"jTop")){
										if(Valify.isNull($("#investPlanAmt3").val(), "investPlanAmt3",5,20,"jTop")){
											if(!$("#read3").prop("checked")){
												AlertDialog.show("请阅读协议并勾选", "read3", 25, 23);
												return false;
											}
											$.ajax({
												url: path + "/userAuth/applyOrgInvestInvestor.html",
												type: "post",
												dataType: "json",
												data: data,
												success: function(data){
													if(!data["success"]){
														AlertDialog.tip(data["msg"]);
														return false;
													}
													if(type == "auditing"){
														AlertDialog.tip("提交机构认证申请成功");
														setTimeout(function(){
															window.location.reload(true);
														},1500);
													}else{
														AlertDialog.tip("保存机构认证申请成功");
														setTimeout(function(){
															window.location.reload(true);
														},1500);
													}
												},
												error: function(request){
													console.log("保存投资认证异常");
												}
											});
										}
									}
								}
								
							}else{
								if(Valify.isNull($("#investPlanNum3").val(), "investPlanNum3",5,20,"jTop")){
									if(Valify.isNull($("#investPlanAmt3").val(), "investPlanAmt3",5,20,"jTop")){
										if(!$("#read3").prop("checked")){
											AlertDialog.show("请阅读协议并勾选", "read3", 25, 23);
											return false;
										}
										$.ajax({
											url: path + "/userAuth/applyOrgInvestInvestor.html",
											type: "post",
											dataType: "json",
											data: data,
											success: function(data){
												if(!data["success"]){
													AlertDialog.tip(data["msg"]);
													return false;
												}
												if(type == "auditing"){
													AlertDialog.tip("提交机构认证申请成功");
													setTimeout(function(){
														window.location.reload(true);
													},1500);
												}else{
													AlertDialog.tip("保存机构认证申请成功");
													setTimeout(function(){
														window.location.reload(true);
													},1500);
												}
											},
											error: function(request){
												console.log("保存投资认证异常");
											}
										});
									}
								}
							}			
						}
					}
				//}
			//}
		}

	}
}
//跟投机构修改
function XbodiesGTRZ(id,type){
	
	var ci = '';
	//将勾选的投资领域赋值
	$.each($("#concern_industryBox3").find("input:checked"),function(i, k){
		if(i == $("#concern_industryBox3").find("input:checked").length-1){
			ci += $(k).val();
		}else{
			ci += $(k).val() + ",";
		}
	});
	$("#concern_industry3").val(ci);
	if(type == "add"){
		var investAuthState = "add";
	}else{
		var investAuthState = "auditing";
	}
	if(Valify.isNull($("#institution").val(), "institution",10,20,"jTop")){
		//if(Valify.isNull($("#realName3").val(), "realName3",10,20,"jTop")){
			//if(Valify.cardCode($("#certNo3").val(), "certNo3")){
				if($("#investRatio3").val()){
					var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
					if(isNaN($("#investRatio3").val())){
						AlertDialog.show("请输入正确金额", "investRatio3", 10, 20, "jump");
						return false;
					}else if(!reg.test($("#investRatio3").val())){
						AlertDialog.show("不能超过两位小数", "investRatio3", 10, 20, "jump");
						return false;
					}
				}
				if(!$("#orgLoanReceiveProve3").val()){
					AlertDialog.mtip('请上传上传机构证件！');
					return false;
				}
				
				if(Valify.isNull($("#concern_industry3").val(), "concern_industryBox3",-25,650,"jTop")){
					if(Valify.isNull($("#concernCity3").val(), "concernCity3",10,20,"jTop")){
						if(investRatioNaN($("#investRatio3").val(), "investRatio3")){
							if($('input[name=tzjl2]:checked').attr("num") == 0){
								if(Valify.isNull($("#investExperience3").val(), "investExperience3",-60,20,"jTop")){
									if(Valify.isNull($("#investPlanNum3").val(), "investPlanNum3",5,20,"jTop")){
										if(Valify.isNull($("#investPlanAmt3").val(), "investPlanAmt3",5,20,"jTop")){
											if(!$("#read3").prop("checked")){
												AlertDialog.show("请阅读协议并勾选", "read3", 25, 23);
												return false;
											}
											$.ajax({
												url: path + "/userAuth/updateUserAuth.html",
												type: "post",
												dataType: "json",
												data: {
													"id":id,
													"investAuthState":investAuthState,
													"institution": $("#institution").val(),
													"realName": $("#realName3").val(),
													"certNo": $("#certNo3").val(),
													"cardPhoto": $("#orgLoanReceiveProve3").val(),
													"concernIndustry": $("#concern_industry3").val(),
													"concernCity": $("#concernCity3").val(),
													"investRatio": $("#investRatio3").val(),
													"investExperience": $("#investExperience3").val(),
													"investPlanNum": $("#investPlanNum3").val(),
													"investPlanAmt": $("#investPlanAmt3").val()
													
												},
												success: function(data){
													if(!data["success"]){
														AlertDialog.tip(data["msg"]);
														return false;
													}
													if(type == "add"){
														AlertDialog.tip("保存成功");
													}else{
														AlertDialog.tip("提交成功");
													}
													
													setTimeout(function(){
														window.location.reload(true);
													},1500);
												},
												error: function(request){
													console.log("保存投资认证异常");
												}
											});
										}
									}
								}
								
							}else{
								if(Valify.isNull($("#investPlanNum3").val(), "investPlanNum3",5,20,"jTop")){
									if(Valify.isNull($("#investPlanAmt3").val(), "investPlanAmt3",5,20,"jTop")){
										if(!$("#read3").prop("checked")){
											AlertDialog.show("请阅读协议并勾选", "read3", 25, 23);
											return false;
										}
										$.ajax({
											url: path + "/userAuth/updateUserAuth.html",
											type: "post",
											dataType: "json",
											data: {
												"id":id,
												"investAuthState":investAuthState,
												"institution": $("#institution").val(),
												"realName": $("#realName3").val(),
												"certNo": $("#certNo3").val(),
												"cardPhoto": $("#orgLoanReceiveProve3").val(),
												"concernIndustry": $("#concern_industry3").val(),
												"concernCity": $("#concernCity3").val(),
												"investRatio": $("#investRatio3").val(),
												"investExperience": $("#investExperience3").val(),
												"investPlanNum": $("#investPlanNum3").val(),
												"investPlanAmt": $("#investPlanAmt3").val()
											},
											success: function(data){
												if(!data["success"]){
													AlertDialog.tip(data["msg"]);
													return false;
												}
												if(type == "add"){
													AlertDialog.tip("保存成功");
												}else{
													AlertDialog.tip("提交成功");
												}
												
												setTimeout(function(){
													window.location.reload(true);
												},1500);
											},
											error: function(request){
												console.log("保存投资认证异常");
											}
										});
									}
								}
							}
							
						}
					}
				//}
			//}
		}
	}
}

//领投机构认证
function bodiesLTRZ(type){
	
	var ci = '';
	//将勾选的投资领域赋值
	$.each($("#concern_industryBox4").find("input:checked"),function(i, k){
		if(i == $("#concern_industryBox4").find("input:checked").length-1){
			ci += $(k).val();
		}else{
			ci += $(k).val() + ",";
		}
	});
	$("#concern_industry4").val(ci);
	var data={
			"investAuthState":type,
			"tempId":orgLeadInvestorId,
			"institution": $("#institution4").val(),
			"realName": $("#realName4").val(),
			"certNo": $("#certNo4").val(),
			"cardPhoto": $("#orgLoanReceiveProve4").val(),
			"concernIndustry": $("#concern_industry4").val(),
			"concernCity": $("#concernCity4").val(),
			"investRatio": $("#investRatio4").val(),
			"investExperience": $("#investExperience4").val(),
			"investPlanNum": $("#investPlanNum4").val(),
			"investPlanAmt": $("#investPlanAmt4").val(),
			"assetsCredentials": $("#orgLoanReceiveProve5").val(),
			"historicalInvestmentData": $("#orgLoanReceiveProve6").val()
		}
	if(Valify.isNull($("#institution4").val(), "institution4",10,20,"jTop")){
		//if(Valify.isNull($("#realName4").val(), "realName4",10,20,"jTop")){
			//if(Valify.cardCode($("#certNo4").val(), "certNo4")){
				if($("#investRatio4").val()){
					var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
					if(isNaN($("#investRatio4").val())){
						AlertDialog.show("请输入正确金额", "investRatio4", 10, 20, "jump");
						return false;
					}else if(!reg.test($("#investRatio4").val())){
						AlertDialog.show("不能超过两位小数", "investRatio4", 10, 20, "jump");
						return false;
					}
				}
				if(!$("#orgLoanReceiveProve4").val()){
					AlertDialog.mtip('请上传机构证件！');
					return false;
				}
				if(Valify.isNull($("#concern_industry4").val(), "concern_industryBox4",-25,650,"jTop")){
					if(Valify.isNull($("#concernCity4").val(), "concernCity4",10,20,"jTop")){
						if(investRatioNaN($("#investRatio4").val(), "investRatio4")){
							if($('input[name=tzjl3]:checked').attr("num") == 0){
								if(Valify.isNull($("#investExperience4").val(), "investExperience4",-60,20,"jTop")){
									if(Valify.isNull($("#investPlanNum4").val(), "investPlanNum4",5,20,"jTop")){
										if(Valify.isNull($("#investPlanAmt4").val(), "investPlanAmt4",5,20,"jTop")){
											if(!$("#orgLoanReceiveProve5").val()){
												AlertDialog.mtip('请上传机构资产认证资料！');
												return false;
											}
											if(!$("#orgLoanReceiveProve6").val()){
												AlertDialog.mtip('请上传机构资产认证资料！');
												return false;
											}
											if(!$("#read4").prop("checked")){
												AlertDialog.show("请阅读协议并勾选", "read4", 25, 23);
												return false;
											}
											$.ajax({
												url: path + "/userAuth/applyOrgLeadInvestor.html",
												type: "post",
												dataType: "json",
												data: data,
												success: function(data){
													if(!data["success"]){
														AlertDialog.tip(data["msg"]);
														return false;
													}
													if(type == "add"){
														AlertDialog.tip("保存机构认证申请成功");
														setTimeout(function(){
															window.location.reload(true);
														},1500);
													}else{
														AlertDialog.tip("提交机构认证申请成功");
														setTimeout(function(){
															window.location.reload(true);
														},1500);
													}
												},
												error: function(request){
													console.log("保存投资认证异常");
												}
											});
										}
									}
								}
								
							}else{
								if(Valify.isNull($("#investPlanNum4").val(), "investPlanNum4",5,20,"jTop")){
									if(Valify.isNull($("#investPlanAmt4").val(), "investPlanAmt4",5,20,"jTop")){
										if(!$("#orgLoanReceiveProve5").val()){
											AlertDialog.mtip('请上传机构资产认证资料！');
											return false;
										}
										if(!$("#orgLoanReceiveProve6").val()){
											AlertDialog.mtip('请上传机构资产认证资料！');
											return false;
										}
										if(!$("#read4").prop("checked")){
											AlertDialog.show("请阅读协议并勾选", "read4", 25, 23);
											return false;
										}
										$.ajax({
											url: path + "/userAuth/applyOrgLeadInvestor.html",
											type: "post",
											dataType: "json",
											data: data,
											success: function(data){
												if(!data["success"]){
													AlertDialog.tip(data["msg"]);
													return false;
												}
												if(type == "add"){
													AlertDialog.tip("保存机构认证申请成功");
													setTimeout(function(){
														window.location.reload(true);
													},1500);
												}else{
													AlertDialog.tip("提交机构认证申请成功");
													setTimeout(function(){
														window.location.reload(true);
													},1500);
												}
											},
											error: function(request){
												console.log("保存投资认证异常");
											}
										});
									}
								}
							}
						}
						
					}
				//}				
			//}
		}

	}
}
//领投机构修改
function XbodiesLTRZ(id,type){
	
	var ci = '';
	//将勾选的投资领域赋值
	$.each($("#concern_industryBox4").find("input:checked"),function(i, k){
		if(i == $("#concern_industryBox4").find("input:checked").length-1){
			ci += $(k).val();
		}else{
			ci += $(k).val() + ",";
		}
	});
	$("#concern_industry4").val(ci);
	if(type=="add"){
		var investAuthState = "add";
	}else{
		var investAuthState = "auditing";
	}
	if(Valify.isNull($("#institution4").val(), "institution4",10,20,"jTop")){
		//if(Valify.isNull($("#realName4").val(), "realName4",10,20,"jTop")){
			//if(Valify.cardCode($("#certNo4").val(), "certNo4")){
				if($("#investRatio4").val()){
					var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
					if(isNaN($("#investRatio4").val())){
						AlertDialog.show("请输入正确金额", "investRatio4", 10, 20, "jump");
						return false;
					}else if(!reg.test($("#investRatio4").val())){
						AlertDialog.show("不能超过两位小数", "investRatio4", 10, 20, "jump");
						return false;
					}
				}
				if(!$("#orgLoanReceiveProve4").val()){
					AlertDialog.mtip('请上传上传机构证件！');
					return false;
				}
				if(Valify.isNull($("#concern_industry4").val(), "concern_industryBox4",-25,650,"jTop")){
					if(Valify.isNull($("#concernCity4").val(), "concernCity4",10,20,"jTop")){
						if(investRatioNaN($("#investRatio4").val(), "investRatio4")){
							if($('input[name=tzjl3]:checked').attr("num") == 0){
								if(Valify.isNull($("#investExperience4").val(), "investExperience4",-60,20,"jTop")){
									if(Valify.isNull($("#investPlanNum4").val(), "investPlanNum4",5,20,"jTop")){
										if(Valify.isNull($("#investPlanAmt4").val(), "investPlanAmt4",5,20,"jTop")){
											if(!$("#orgLoanReceiveProve5").val()){
												AlertDialog.mtip('请上传机构资产认证资料！');
												return false;
											}
											if(!$("#orgLoanReceiveProve6").val()){
												AlertDialog.mtip('请上传机构资产认证资料！');
												return false;
											}
											if(!$("#read4").prop("checked")){
												AlertDialog.show("请阅读协议并勾选", "read4", 25, 23);
												return false;
											}
											$.ajax({
												url: path + "/userAuth/updateUserAuth.html",
												type: "post",
												dataType: "json",
												data: {
													"id":id,
													"investAuthState":investAuthState,
													"institution": $("#institution4").val(),
													"realName": $("#realName4").val(),
													"certNo": $("#certNo4").val(),
													"cardPhoto": $("#orgLoanReceiveProve4").val(),
													"concernIndustry": $("#concern_industry4").val(),
													"concernCity": $("#concernCity4").val(),
													"investRatio": $("#investRatio4").val(),
													"investExperience": $("#investExperience4").val(),
													"investPlanNum": $("#investPlanNum4").val(),
													"investPlanAmt": $("#investPlanAmt4").val(),
													"assetsCredentials": $("#orgLoanReceiveProve5").val(),
													"historicalInvestmentData": $("#orgLoanReceiveProve6").val()
													
												},
												success: function(data){
													if(!data["success"]){
														AlertDialog.tip(data["msg"]);
														return false;
													}
													
													if(type=="add"){
														AlertDialog.tip("保存成功");
													}else{
														AlertDialog.tip("提交成功");
													}
													setTimeout(function(){
														window.location.reload(true);
													},1500);
												},
												error: function(request){
													console.log("保存投资认证异常");
												}
											});
										}
									}
								}
								
							}else{
								if(Valify.isNull($("#investPlanNum4").val(), "investPlanNum4",5,20,"jTop")){
									if(Valify.isNull($("#investPlanAmt4").val(), "investPlanAmt4",5,20,"jTop")){
										if(!$("#orgLoanReceiveProve5").val()){
											AlertDialog.mtip('请上传机构资产认证资料！');
											return false;
										}
										if(!$("#orgLoanReceiveProve6").val()){
											AlertDialog.mtip('请上传机构资产认证资料！');
											return false;
										}
										if(!$("#read4").prop("checked")){
											AlertDialog.show("请阅读协议并勾选", "read4", 25, 23);
											return false;
										}
										$.ajax({
											url: path + "/userAuth/updateUserAuth.html",
											type: "post",
											dataType: "json",
											data: {
												"id":id,
												"investAuthState":investAuthState,
												"institution": $("#institution4").val(),
												"realName": $("#realName4").val(),
												"certNo": $("#certNo4").val(),
												"cardPhoto": $("#orgLoanReceiveProve4").val(),
												"concernIndustry": $("#concern_industry4").val(),
												"concernCity": $("#concernCity4").val(),
												"investRatio": $("#investRatio4").val(),
												"investExperience": $("#investExperience4").val(),
												"investPlanNum": $("#investPlanNum4").val(),
												"investPlanAmt": $("#investPlanAmt4").val(),
												"assetsCredentials": $("#orgLoanReceiveProve5").val(),
												"historicalInvestmentData": $("#orgLoanReceiveProve6").val()
											},
											success: function(data){
												if(!data["success"]){
													AlertDialog.tip(data["msg"]);
													return false;
												}
												
												if(type=="add"){
													AlertDialog.tip("保存成功");
												}else{
													AlertDialog.tip("提交成功");
												}
												setTimeout(function(){
													window.location.reload(true);
												},1500);
											},
											error: function(request){
												console.log("保存投资认证异常");
											}
										});
									}
								}
							}
						}
						
					}
				//}
			//}
		}
	}
}

//投资人查询
function crowdfundUserTzr(obj){

	$.ajax({
		url: path + "/userAuth/getCrowdfundUserByUserId.html",
		type: "post",
		dataType: "json",
		data:{
			authType : obj
		},
		success: function(data){
			if(!data["success"]){
				ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传项身份证件
				ecreateWebUploader("image_file1", "imghead1", "loan_logo_url1", "imgheadLi1");//上传项身份证件
				$("#subInvestBtn").unbind("click").click(function(){
					subInvestRZ("auditing");
				});
				$("#subInvestBtnBc").unbind("click").click(function(){
					subInvestRZ("add");
				});
				$("#subLTRZBtn").unbind("click").click(function(){
					subLTRZ("auditing");
				});
				$("#subLTRZBtnBc").unbind("click").click(function(){
					subLTRZ("add");
				});
				
				$("#bodiesGTRZBtn").unbind("click").click(function(){
					bodiesGTRZ("auditing");
				});
				$("#bodiesGTRZBtnBc").unbind("click").click(function(){
					bodiesGTRZ("add");
				});
				
				$("#bodiesLTRZBtn").unbind("click").click(function(){
					bodiesLTRZ("auditing");
				});
				$("#bodiesLTRZBtnBc").unbind("click").click(function(){
					bodiesLTRZ("add");
				});
				if(obj == "investor"){
					getIndustry("#concern_industryBox","#concern_industry");
					$('input[name=tzjl]').click(function(){
						if($(this).attr("num")==0){
							$("#investExperience").attr("readonly",false);
						}else{
							$("#investExperience").val("").attr("readonly","readonly");
						}
					})
				}else if(obj == "leadInvestor"){
					getIndustry("#concern_industryBox1","#concern_industry1");
					$('input[name=tzjl1]').click(function(){
						if($(this).attr("num")==0){
							$("#investExperience1").attr("readonly",false);
						}else{
							$("#investExperience1").val("").attr("readonly","readonly");
						}
					})
				}else if(obj == "orgInvestor"){
					getIndustry("#concern_industryBox3","#concern_industry3");
					$('input[name=tzjl2]').click(function(){
						if($(this).attr("num")==0){
							$("#investExperience3").attr("readonly",false);
						}else{
							$("#investExperience3").val("").attr("readonly","readonly");
						}
					})
				}else if(obj == "orgLeadInvestor"){
					getIndustry("#concern_industryBox4","#concern_industry4");
					$('input[name=tzjl3]').click(function(){
						if($(this).attr("num")==0){
							$("#investExperience4").attr("readonly",false);
						}else{
							$("#investExperience4").val("").attr("readonly","readonly");
						}
					})
				}
				return false;
			}
			
			var Rdata = data["msg"];
			$("#uploadInFo,#uploadInFo1,#uploadInFo3,#uploadInFo4,#uploadInFo5,#uploadInFo6").html("");
			//if(Rdata[obj]){
			$("#subInvestBtn").unbind("click").click(function(){
				subInvestRZ("auditing");
			});
			$("#subInvestBtnBc").unbind("click").click(function(){
				subInvestRZ("add");
			});
			
			$("#subLTRZBtn").unbind("click").click(function(){
				subLTRZ("auditing");
			});
			$("#subLTRZBtnBc").unbind("click").click(function(){
				subLTRZ("add");
			});
			
			$("#bodiesGTRZBtn").unbind("click").click(function(){
				bodiesGTRZ("auditing");
			});
			$("#bodiesGTRZBtnBc").unbind("click").click(function(){
				bodiesGTRZ("add");
			});
			
			$("#bodiesLTRZBtn").unbind("click").click(function(){
				bodiesLTRZ("auditing");
			});
			$("#bodiesLTRZBtnBc").unbind("click").click(function(){
				bodiesLTRZ("add");
			});
				if(obj == "investor"){
					if(Rdata){
						if(Rdata["investAuthState"] == "auditing"){
							
							//$("#realName").val(Rdata["realName"]).attr("readonly","readonly");
							//$("#certNo").val(Rdata["certNo"]).attr("readonly","readonly");
							$("#companyName").val(Rdata["companyName"]).attr("readonly","readonly");
							$("#position").val(Rdata["position"]).attr("readonly","readonly");
							$("#loan_logo_url").val(Rdata["cardPhoto"]).attr("readonly","readonly");
							if(Rdata["cardPhoto"]){
								$("#imghead").attr("src",cv.fileAddress+Rdata["cardPhoto"]).parent().show();
							}
							$("#concernCity").val(Rdata["concernCity"]).attr("readonly","readonly");
							$("#investRatio").val(Rdata["investRatio"]).attr("readonly","readonly");
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl]').eq(0).attr("num")==0){
									$('input[name=tzjl]').eq(0).attr("checked",false);
									$('input[name=tzjl]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl]').eq(1).attr("checked",false);
									$('input[name=tzjl]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience").val(Rdata["investExperience"]).attr("readonly","readonly");
							$("#investPlanNum").val(Rdata["investPlanNum"]).attr("readonly","readonly");
							$("#investPlanAmt").val(Rdata["investPlanAmt"]).attr("readonly","readonly");
							getIndustry("#concern_industryBox","#concern_industry",Rdata["concernIndustry"],1);
							$("#subInvestBtnBc,#subInvestBtnCz").remove();
							$("#read").prop('checked',true).attr("disabled","disabled");
							$("#subInvestBtn").unbind("click").text("审核中...").css({"background":"#ccc","color":"#FFF","border":"none"});
						}else if(Rdata["investAuthState"] == "passed"){
							//$("#realName").val(Rdata["realName"]).attr("readonly","readonly");
							//$("#certNo").val(Rdata["certNo"]).attr("readonly","readonly");
							$("#companyName").val(Rdata["companyName"]).attr("readonly","readonly");
							$("#position").val(Rdata["position"]).attr("readonly","readonly");
							$("#loan_logo_url").val(Rdata["cardPhoto"]).attr("readonly","readonly");
							if(Rdata["cardPhoto"]){
								$("#imghead").attr("src",cv.fileAddress+Rdata["cardPhoto"]).parent().show();
							}
							$("#concernCity").val(Rdata["concernCity"]).attr("readonly","readonly");
							$("#investRatio").val(Rdata["investRatio"]).attr("readonly","readonly");
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl]').eq(0).attr("num")==0){
									$('input[name=tzjl]').eq(0).attr("checked",false);
									$('input[name=tzjl]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl]').eq(1).attr("checked",false);
									$('input[name=tzjl]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience").val(Rdata["investExperience"]).attr("readonly","readonly");
							$("#investPlanNum").val(Rdata["investPlanNum"]).attr("readonly","readonly");
							$("#investPlanAmt").val(Rdata["investPlanAmt"]).attr("readonly","readonly");
							getIndustry("#concern_industryBox","#concern_industry",Rdata["concernIndustry"],1);
							$("#subInvestBtnBc,#subInvestBtnCz").remove();
							$("#read").prop('checked',true).attr("disabled","disabled");
							$("#subInvestBtn").unbind("click").text("认证成功");
							
						}else if(Rdata["investAuthState"] == "refused"){
							//$("#realName").val(Rdata["realName"]);
							//$("#certNo").val(Rdata["certNo"]);
							$("#companyName").val(Rdata["companyName"]);
							$("#position").val(Rdata["position"]);
							$("#loan_logo_url").val(Rdata["cardPhoto"]);
							if(Rdata["cardPhoto"]){
								$("#imghead").attr("src",cv.fileAddress+Rdata["cardPhoto"]).parent().show();
							}
							$("#concernCity").val(Rdata["concernCity"]);
							$("#investRatio").val(Rdata["investRatio"]);
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl]').eq(0).attr("num")==0){
									$('input[name=tzjl]').eq(0).attr("checked",false);
									$('input[name=tzjl]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl]').eq(1).attr("checked",false);
									$('input[name=tzjl]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience").val(Rdata["investExperience"]);
							$("#investPlanNum").val(Rdata["investPlanNum"]);
							$("#investPlanAmt").val(Rdata["investPlanAmt"]);
							getIndustry("#concern_industryBox","#concern_industry",Rdata["concernIndustry"]);
							$("#subInvestBtnBc").hide();
							$("#read").prop('checked',true).attr("disabled","disabled");
							$("#subInvestBtn").unbind("click").text("重新提交").click(function(){
								XsubInvestRZ(Rdata["id"]);
							});
							$("#subInvestBtnBc").unbind("click").click(function(){
								XsubInvestRZ(Rdata["id"],"add");
							});
							$('input[name=tzjl]').click(function(){
								if($(this).attr("num")==0){
									$("#investExperience").attr("readonly",false);
								}else{
									$("#investExperience").val("").attr("readonly","readonly");
								}
							})
							ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传项身份证件
						}else{
							
							//$("#realName").val(Rdata["realName"]);
							//$("#certNo").val(Rdata["certNo"]);
							$("#companyName").val(Rdata["companyName"]);
							$("#position").val(Rdata["position"]);
							$("#loan_logo_url").val(Rdata["cardPhoto"]);
							if(Rdata["cardPhoto"]){
								$("#imghead").attr("src",cv.fileAddress+Rdata["cardPhoto"]).parent().show();
							}
							$("#concernCity").val(Rdata["concernCity"]);
							$("#investRatio").val(Rdata["investRatio"]);
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl]').eq(0).attr("num")==0){
									$('input[name=tzjl]').eq(0).attr("checked",false);
									$('input[name=tzjl]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl]').eq(1).attr("checked",false);
									$('input[name=tzjl]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience").val(Rdata["investExperience"]);
							$("#investPlanNum").val(Rdata["investPlanNum"]);
							$("#investPlanAmt").val(Rdata["investPlanAmt"]);
							getIndustry("#concern_industryBox","#concern_industry",Rdata["concernIndustry"]);
							$("#read").prop('checked',true);
							$('input[name=tzjl]').click(function(){
								if($(this).attr("num")==0){
									$("#investExperience").attr("readonly",false);
								}else{
									$("#investExperience").val("").attr("readonly","readonly");
								}
							})
							$("#subInvestBtn").unbind("click").text("提交").click(function(){
								XsubInvestRZ(Rdata["id"]);
							});
							$("#subInvestBtnBc").unbind("click").click(function(){
								XsubInvestRZ(Rdata["id"],"add");
							});
							ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传项身份证件
						}
					}else{
						getIndustry("#concern_industryBox","#concern_industry");
						$('input[name=tzjl]').click(function(){
							if($(this).attr("num")==0){
								$("#investExperience").attr("readonly",false);
							}else{
								$("#investExperience").val("").attr("readonly","readonly");
							}
						})
						ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传项身份证件
					}
				}else if(obj == "leadInvestor"){
					if(Rdata){
						if(Rdata["id"]){
							leadInvestorId = Rdata["id"]
						}
						if(Rdata["investAuthState"] == "auditing"){
							//$("#realName1").val(Rdata["realName"]).attr("readonly","readonly");
							//$("#certNo1").val(Rdata["certNo"]).attr("readonly","readonly");
							$("#companyName1").val(Rdata["companyName"]).attr("readonly","readonly");
							$("#position1").val(Rdata["position"]).attr("readonly","readonly");
							$("#loan_logo_url1").val(Rdata["cardPhoto"]).attr("readonly","readonly");
							if(Rdata["cardPhoto"]){
								$("#imghead1").attr("src",cv.fileAddress+Rdata["cardPhoto"]).parent().show();
							}
							$("#concernCity1").val(Rdata["concernCity"]).attr("readonly","readonly");
							$("#investRatio1").val(Rdata["investRatio"]).attr("readonly","readonly");
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl1]').eq(0).attr("num")==0){
									$('input[name=tzjl1]').eq(0).attr("checked",false);
									$('input[name=tzjl1]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl1]').eq(1).attr("checked",false);
									$('input[name=tzjl1]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience1").val(Rdata["investExperience"]).attr("readonly","readonly");
							$("#investPlanNum1").val(Rdata["investPlanNum"]).attr("readonly","readonly");
							$("#investPlanAmt1").val(Rdata["investPlanAmt"]).attr("readonly","readonly");
							getIndustry("#concern_industryBox1","#concern_industry1",Rdata["concernIndustry"],1);
//							var indArr = Rdata["assetsCredentials"].split(",");
//							for(var j=0;j<indArr.length;j++){
//								$("#uploadInFo").append("<span><a href="+cv.fileAddress+indArr[j]+" target='_black' url='"+indArr[j]+"'>查看上传文件</a></span>");
//							}
//							var indArr1 = Rdata["historicalInvestmentData"].split(",");
//							for(var f=0;f<indArr1.length;f++){
//								$("#uploadInFo1").append("<span><a href="+cv.fileAddress+indArr1[f]+" target='_black' url='"+indArr1[f]+"'>查看上传文件</a></span>");
//							}
							for(var j=0;j<Rdata["assetsCredentialsList"].length;j++){
								if(Rdata["assetsCredentialsList"][j]["fileName"].length>10){
									var fileName = Rdata["assetsCredentialsList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["assetsCredentialsList"][j]["fileName"];
								}
								$("#uploadInFo").append("<span><a href="+cv.fileAddress+Rdata["assetsCredentialsList"][j]["fileUrl"]+" target='_black' url='"+Rdata["assetsCredentialsList"][j]["fileUrl"]+"'>"+fileName+"</a></span>");
							}
							for(var j=0;j<Rdata["historicalInvestMentList"].length;j++){
								if(Rdata["historicalInvestMentList"][j]["fileName"].length>10){
									var fileName = Rdata["historicalInvestMentList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["historicalInvestMentList"][j]["fileName"];
								}
								$("#uploadInFo1").append("<span><a href="+cv.fileAddress+Rdata["historicalInvestMentList"][j]["fileUrl"]+" target='_black' url='"+Rdata["historicalInvestMentList"][j]["fileUrl"]+"'>"+fileName+"</a></span>");
							}
							$("#orgLoanReceiveProve1").val(Rdata["assetsCredentials"]);
							$("#orgLoanReceiveProve2").val(Rdata["historicalInvestmentData"]);
							$("#subLTRZBtnBc,#subLTRZBtCz").remove();
							$("#read1").prop('checked',true).attr("disabled","disabled");
							$("#subLTRZBtn").unbind("click").text("审核中...").css({"background":"#ccc","color":"#FFF","border":"none"});
						}else if(Rdata["investAuthState"] == "passed"){
							//$("#realName1").val(Rdata["realName"]).attr("readonly","readonly");
							//$("#certNo1").val(Rdata["certNo"]).attr("readonly","readonly");
							$("#companyName1").val(Rdata["companyName"]).attr("readonly","readonly");
							$("#position1").val(Rdata["position"]).attr("readonly","readonly");
							$("#loan_logo_url1").val(Rdata["cardPhoto"]).attr("readonly","readonly");
							if(Rdata["cardPhoto"]){
								$("#imghead1").attr("src",cv.fileAddress+Rdata["cardPhoto"]).parent().show();
							}
							$("#concernCity1").val(Rdata["concernCity"]).attr("readonly","readonly");
							$("#investRatio1").val(Rdata["investRatio"]).attr("readonly","readonly");
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl1]').eq(0).attr("num")==0){
									$('input[name=tzjl1]').eq(0).attr("checked",false);
									$('input[name=tzjl1]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl1]').eq(1).attr("checked",false);
									$('input[name=tzjl1]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience1").val(Rdata["investExperience"]).attr("readonly","readonly");
							$("#investPlanNum1").val(Rdata["investPlanNum"]).attr("readonly","readonly");
							$("#investPlanAmt1").val(Rdata["investPlanAmt"]).attr("readonly","readonly");
//							var indArr = Rdata["assetsCredentials"].split(",");
//							for(var j=0;j<indArr.length;j++){
//								$("#uploadInFo").append("<span><a href="+cv.fileAddress+indArr[j]+" target='_black' url='"+indArr[j]+"'>查看上传文件</a></span>");
//							}
//							var indArr1 = Rdata["historicalInvestmentData"].split(",");
//							for(var f=0;f<indArr1.length;f++){
//								$("#uploadInFo1").append("<span><a href="+cv.fileAddress+indArr1[f]+" target='_black' url='"+indArr1[f]+"'>查看上传文件</a></span>");
//							}
							for(var j=0;j<Rdata["assetsCredentialsList"].length;j++){
								if(Rdata["assetsCredentialsList"][j]["fileName"].length>10){
									var fileName = Rdata["assetsCredentialsList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["assetsCredentialsList"][j]["fileName"];
								}
								$("#uploadInFo").append("<span><a href="+cv.fileAddress+Rdata["assetsCredentialsList"][j]["fileUrl"]+" target='_black' url='"+Rdata["assetsCredentialsList"][j]["fileUrl"]+"'>"+fileName+"</a></span>");
							}
							for(var j=0;j<Rdata["historicalInvestMentList"].length;j++){
								if(Rdata["historicalInvestMentList"][j]["fileName"].length>10){
									var fileName = Rdata["historicalInvestMentList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["historicalInvestMentList"][j]["fileName"];
								}
								$("#uploadInFo1").append("<span><a href="+cv.fileAddress+Rdata["historicalInvestMentList"][j]["fileUrl"]+" target='_black' url='"+Rdata["historicalInvestMentList"][j]["fileUrl"]+"'>"+fileName+"</a></span>");
							}
							$("#orgLoanReceiveProve1").val(Rdata["assetsCredentials"]);
							$("#orgLoanReceiveProve2").val(Rdata["historicalInvestmentData"]);
							getIndustry("#concern_industryBox1","#concern_industry1",Rdata["concernIndustry"],1);
							$("#subLTRZBtnBc,#subLTRZBtCz").remove();
							$("#read1").prop('checked',true).attr("disabled","disabled");
							$("#subLTRZBtn").unbind("click").text("认证成功");
						}else if(Rdata["investAuthState"] == "refused"){
							//$("#realName1").val(Rdata["realName"]);
							//$("#certNo1").val(Rdata["certNo"]);
							$("#companyName1").val(Rdata["companyName"]);
							$("#position1").val(Rdata["position"]);
							$("#loan_logo_url1").val(Rdata["cardPhoto"]);
							if(Rdata["cardPhoto"]){
								$("#imghead1").attr("src",cv.fileAddress+Rdata["cardPhoto"]).parent().show();
							}
							$("#concernCity1").val(Rdata["concernCity"]);
							$("#investRatio1").val(Rdata["investRatio"]);
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl1]').eq(0).attr("num")==0){
									$('input[name=tzjl1]').eq(0).attr("checked",false);
									$('input[name=tzjl1]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl1]').eq(1).attr("checked",false);
									$('input[name=tzjl1]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience1").val(Rdata["investExperience"]);
							$("#investPlanNum1").val(Rdata["investPlanNum"]);
							$("#investPlanAmt1").val(Rdata["investPlanAmt"]);
//							var indArr = Rdata["assetsCredentials"].split(",");
//							for(var j=0;j<indArr.length;j++){
//								$("#uploadInFo").append("<span><a href="+cv.fileAddress+indArr[j]+" target='_black' url='"+indArr[j]+"'>查看上传文件</a><i onClick='fileDelete(this,\"#uploadInFo\",\"#orgLoanReceiveProve1\")'></i></span>");
//							}
//							var indArr1 = Rdata["historicalInvestmentData"].split(",");
//							for(var f=0;f<indArr1.length;f++){
//								$("#uploadInFo1").append("<span><a href="+cv.fileAddress+indArr1[f]+" target='_black' url='"+indArr1[f]+"'>查看上传文件</a><i onClick='fileDelete(this,\"#uploadInFo1\",\"#orgLoanReceiveProve2\")'></i></span>");
//							}
							for(var j=0;j<Rdata["assetsCredentialsList"].length;j++){
								if(Rdata["assetsCredentialsList"][j]["fileName"].length>10){
									var fileName = Rdata["assetsCredentialsList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["assetsCredentialsList"][j]["fileName"];
								}
								$("#uploadInFo").append("<span><a href="+cv.fileAddress+Rdata["assetsCredentialsList"][j]["fileUrl"]+" target='_black' url='"+Rdata["assetsCredentialsList"][j]["fileUrl"]+"'>"+fileName+"</a><i id='"+Rdata["assetsCredentialsList"][j]["id"]+"' onClick='fileDelete(this,\"#uploadInFo\",\"#orgLoanReceiveProve\")'></i></span>");
							}
							for(var j=0;j<Rdata["historicalInvestMentList"].length;j++){
								if(Rdata["historicalInvestMentList"][j]["fileName"].length>10){
									var fileName = Rdata["historicalInvestMentList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["historicalInvestMentList"][j]["fileName"];
								}
								$("#uploadInFo1").append("<span><a href="+cv.fileAddress+Rdata["historicalInvestMentList"][j]["fileUrl"]+" target='_black' url='"+Rdata["historicalInvestMentList"][j]["fileUrl"]+"'>"+fileName+"</a><i id='"+Rdata["historicalInvestMentList"][j]["id"]+"' onClick='fileDelete(this,\"#uploadInFo1\",\"#orgLoanReceiveProve1\")'></i></span>");
							}
							$("#orgLoanReceiveProve1").val(Rdata["assetsCredentials"]);
							$("#orgLoanReceiveProve2").val(Rdata["historicalInvestmentData"]);
							getIndustry("#concern_industryBox1","#concern_industry1",Rdata["concernIndustry"]);
							$("#subLTRZBtnBc").hide();
							$("#read1").prop('checked',true).attr("disabled","disabled");
							$("#subLTRZBtn").unbind("click").text("重新提交").click(function(){
								XsubLTRZ(Rdata["id"]);
							});
							$("#subLTRZBtnBc").unbind("click").click(function(){
								XsubLTRZ(Rdata["id"],"add");
							});
							$('input[name=tzjl1]').click(function(){
								if($(this).attr("num")==0){
									$("#investExperience1").attr("readonly",false);
								}else{
									$("#investExperience1").val("").attr("readonly","readonly");
								}
							})
							ecreateWebUploader("image_file1", "imghead1", "loan_logo_url1", "imgheadLi1");//上传项身份证件
							$("#changeUpload").click(function(){
								$("#fileToUpload").click();
							});
							$("#changeUpload1").click(function(){
								$("#fileToUpload1").click();
							});
						}else{
							//$("#realName1").val(Rdata["realName"]);
							//$("#certNo1").val(Rdata["certNo"]);
							$("#companyName1").val(Rdata["companyName"]);
							$("#position1").val(Rdata["position"]);
							$("#loan_logo_url1").val(Rdata["cardPhoto"]);
							if(Rdata["cardPhoto"]){
								$("#imghead1").attr("src",cv.fileAddress+Rdata["cardPhoto"]).parent().show();
							}
							$("#concernCity1").val(Rdata["concernCity"]);
							$("#investRatio1").val(Rdata["investRatio"]);
							
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl1]').eq(0).attr("num")==0){
									$('input[name=tzjl1]').eq(0).attr("checked",false);
									$('input[name=tzjl1]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl1]').eq(1).attr("checked",false);
									$('input[name=tzjl1]').eq(0).attr("checked","checked");
								}
							}
							
							$("#investExperience1").val(Rdata["investExperience"]);
							$("#investPlanNum1").val(Rdata["investPlanNum"]);
							$("#investPlanAmt1").val(Rdata["investPlanAmt"]);
//							var indArr = Rdata["assetsCredentials"].split(",");
//							for(var j=0;j<indArr.length;j++){
//								$("#uploadInFo").append("<span><a href="+cv.fileAddress+indArr[j]+" target='_black' url='"+indArr[j]+"'>查看上传文件</a><i onClick='fileDelete(this,\"#uploadInFo\",\"#orgLoanReceiveProve1\")'></i></span>");
//							}
//							var indArr1 = Rdata["historicalInvestmentData"].split(",");
//							for(var f=0;f<indArr1.length;f++){
//								$("#uploadInFo1").append("<span><a href="+cv.fileAddress+indArr1[f]+" target='_black' url='"+indArr1[f]+"'>查看上传文件</a><i onClick='fileDelete(this,\"#uploadInFo1\",\"#orgLoanReceiveProve2\")'></i></span>");
//							}
							for(var j=0;j<Rdata["assetsCredentialsList"].length;j++){
								if(Rdata["assetsCredentialsList"][j]["fileName"].length>10){
									var fileName = Rdata["assetsCredentialsList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["assetsCredentialsList"][j]["fileName"];
								}
								$("#uploadInFo").append("<span><a href="+cv.fileAddress+Rdata["assetsCredentialsList"][j]["fileUrl"]+" target='_black' url='"+Rdata["assetsCredentialsList"][j]["fileUrl"]+"'>"+fileName+"</a><i id='"+Rdata["assetsCredentialsList"][j]["id"]+"' onClick='fileDelete(this,\"#uploadInFo\",\"#orgLoanReceiveProve\")'></i></span>");
							}
							for(var j=0;j<Rdata["historicalInvestMentList"].length;j++){
								if(Rdata["historicalInvestMentList"][j]["fileName"].length>10){
									var fileName = Rdata["historicalInvestMentList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["historicalInvestMentList"][j]["fileName"];
								}
								$("#uploadInFo1").append("<span><a href="+cv.fileAddress+Rdata["historicalInvestMentList"][j]["fileUrl"]+" target='_black' url='"+Rdata["historicalInvestMentList"][j]["fileUrl"]+"'>"+fileName+"</a><i id='"+Rdata["historicalInvestMentList"][j]["id"]+"' onClick='fileDelete(this,\"#uploadInFo1\",\"#orgLoanReceiveProve1\")'></i></span>");
							}
							$("#orgLoanReceiveProve1").val(Rdata["assetsCredentials"]);
							$("#orgLoanReceiveProve2").val(Rdata["historicalInvestmentData"]);
							getIndustry("#concern_industryBox1","#concern_industry1",Rdata["concernIndustry"]);
							$("#read1").prop('checked',true);
							$('input[name=tzjl1]').click(function(){
								if($(this).attr("num")==0){
									$("#investExperience1").attr("readonly",false);
								}else{
									$("#investExperience1").val("").attr("readonly","readonly");
								}
							})
							$("#subLTRZBtn").unbind("click").text("提交").click(function(){
								XsubLTRZ(Rdata["id"]);
							});
							$("#subLTRZBtnBc").unbind("click").click(function(){
								XsubLTRZ(Rdata["id"],"add");
							});
							ecreateWebUploader("image_file1", "imghead1", "loan_logo_url1", "imgheadLi1");//上传项身份证件
							$("#changeUpload").click(function(){
								$("#fileToUpload").click();
							});
							$("#changeUpload1").click(function(){
								$("#fileToUpload1").click();
							});
						}
					}else{
						getIndustry("#concern_industryBox1","#concern_industry1");
						$('input[name=tzjl1]').click(function(){
							if($(this).attr("num")==0){
								$("#investExperience1").attr("readonly",false);
							}else{
								$("#investExperience1").val("").attr("readonly","readonly");
							}
						})
						ecreateWebUploader("image_file1", "imghead1", "loan_logo_url1", "imgheadLi1");//上传项身份证件
						$("#changeUpload").click(function(){
							$("#fileToUpload").click();
						});
						$("#changeUpload1").click(function(){
							$("#fileToUpload1").click();
						});
					}
					
				}else if(obj == "orgInvestor"){
					if(Rdata){
						if(Rdata["id"]){
							orgInvestorId = Rdata["id"];
						}
						
						if(Rdata["investAuthState"] == "auditing"){
							$("#institution").val(Rdata["institution"]).attr("readonly","readonly");
							//$("#realName3").val(Rdata["realName"]).attr("readonly","readonly");
							//$("#certNo3").val(Rdata["certNo"]).attr("readonly","readonly");
//							var indArr0 = Rdata["cardPhoto"].split(",");
//							for(var j=0;j<indArr0.length;j++){
//								$("#uploadInFo3").append("<span><a href="+cv.fileAddress+indArr0[j]+" target='_black' url='"+indArr0[j]+"'>查看上传文件</a></span>");
//							}
							for(var j=0;j<Rdata["orgCardPhotoList"].length;j++){
								if(Rdata["orgCardPhotoList"][j]["fileName"].length>10){
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"];
								}
								$("#uploadInFo3").append("<span><a href="+cv.fileAddress+Rdata["orgCardPhotoList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgCardPhotoList"][j]["fileUrl"]+"'>"+fileName+"</a></span>");
							}
							$("#orgLoanReceiveProve3").val(Rdata["cardPhoto"]);
							getIndustry("#concern_industryBox3","#concern_industry3",Rdata["concernIndustry"],1);
							
							$("#concernCity3").val(Rdata["concernCity"]).attr("readonly","readonly");
							$("#investRatio3").val(Rdata["investRatio"]).attr("readonly","readonly");
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl2]').eq(0).attr("num")==0){
									$('input[name=tzjl2]').eq(0).attr("checked",false);
									$('input[name=tzjl2]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl2]').eq(1).attr("checked",false);
									$('input[name=tzjl2]').eq(0).attr("checked","checked");
								}
							}
							
							$("#investExperience3").val(Rdata["investExperience"]).attr("readonly","readonly");
							$("#investPlanNum3").val(Rdata["investPlanNum"]).attr("readonly","readonly");
							$("#investPlanAmt3").val(Rdata["investPlanAmt"]).attr("readonly","readonly");
							$("#bodiesGTRZBtnBc,#bodiesGTRZBtnCz").remove();
							$("#read3").prop('checked',true).attr("disabled","disabled");
							$("#bodiesGTRZBtn").unbind("click").text("审核中...").css({"background":"#ccc","color":"#FFF","border":"none"});
						}else if(Rdata["investAuthState"] == "passed"){
							$("#institution").val(Rdata["institution"]).attr("readonly","readonly");
							//$("#realName3").val(Rdata["realName"]).attr("readonly","readonly");
							//$("#certNo3").val(Rdata["certNo"]).attr("readonly","readonly");
//							var indArr0 = Rdata["cardPhoto"].split(",");
//							for(var j=0;j<indArr0.length;j++){
//								$("#uploadInFo3").append("<span><a href="+cv.fileAddress+indArr0[j]+" target='_black'  url='"+indArr0[j]+"'>查看上传文件</a></span>");
//							}
							for(var j=0;j<Rdata["orgCardPhotoList"].length;j++){
								if(Rdata["orgCardPhotoList"][j]["fileName"].length>10){
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"];
								}
								$("#uploadInFo3").append("<span><a href="+cv.fileAddress+Rdata["orgCardPhotoList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgCardPhotoList"][j]["fileUrl"]+"'>"+fileName+"</a></span>");
							}
							$("#orgLoanReceiveProve3").val(Rdata["cardPhoto"]);
							getIndustry("#concern_industryBox3","#concern_industry3",Rdata["concernIndustry"],1);
							
							$("#concernCity3").val(Rdata["concernCity"]).attr("readonly","readonly");
							$("#investRatio3").val(Rdata["investRatio"]).attr("readonly","readonly");
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl2]').eq(0).attr("num")==0){
									$('input[name=tzjl2]').eq(0).attr("checked",false);
									$('input[name=tzjl2]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl2]').eq(1).attr("checked",false);
									$('input[name=tzjl2]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience3").val(Rdata["investExperience"]).attr("readonly","readonly");
							$("#investPlanNum3").val(Rdata["investPlanNum"]).attr("readonly","readonly");
							$("#investPlanAmt3").val(Rdata["investPlanAmt"]).attr("readonly","readonly");
							$("#bodiesGTRZBtnBc,#bodiesGTRZBtnCz").remove();
							$("#read3").prop('checked',true).attr("disabled","disabled");
							$("#bodiesGTRZBtn").unbind("click").text("认证成功");
						}else if(Rdata["investAuthState"] == "refused"){
							$("#institution").val(Rdata["institution"]);
							//$("#realName3").val(Rdata["realName"]);
							//$("#certNo3").val(Rdata["certNo"]);
//							var indArr0 = Rdata["cardPhoto"].split(",");
//							for(var j=0;j<indArr0.length;j++){
//								$("#uploadInFo3").append("<span><a href="+cv.fileAddress+indArr0[j]+" target='_black' url='"+indArr0[j]+"'>查看上传文件</a><i onClick='fileDelete(this,\"#uploadInFo3\",\"#orgLoanReceiveProve3\")'></i></span>");
//							}
							for(var j=0;j<Rdata["orgCardPhotoList"].length;j++){
								if(Rdata["orgCardPhotoList"][j]["fileName"].length>10){
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"];
								}
								$("#uploadInFo3").append("<span><a href="+cv.fileAddress+Rdata["orgCardPhotoList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgCardPhotoList"][j]["fileUrl"]+"'>"+fileName+"</a><i id='"+Rdata["orgCardPhotoList"][j]["id"]+"' onClick='fileDelete(this,\"#uploadInFo3\",\"#orgLoanReceiveProve3\")'></i></span>");
							}
							$("#orgLoanReceiveProve3").val(Rdata["cardPhoto"]);
							getIndustry("#concern_industryBox3","#concern_industry3",Rdata["concernIndustry"]);
							
							$("#concernCity3").val(Rdata["concernCity"]);
							$("#investRatio3").val(Rdata["investRatio"]);
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl2]').eq(0).attr("num")==0){
									$('input[name=tzjl2]').eq(0).attr("checked",false);
									$('input[name=tzjl2]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl2]').eq(1).attr("checked",false);
									$('input[name=tzjl2]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience3").val(Rdata["investExperience"]);
							$("#investPlanNum3").val(Rdata["investPlanNum"]);
							$("#investPlanAmt3").val(Rdata["investPlanAmt"]);
							$("#bodiesGTRZBtnBc").hide();
							$("#read3").prop('checked',true).attr("disabled","disabled");
							$("#bodiesGTRZBtn").unbind("click").text("重新提交").click(function(){
								XbodiesGTRZ(Rdata["id"]);
							});
							$("#bodiesGTRZBtnBc").unbind("click").click(function(){
								XbodiesGTRZ(Rdata["id"],"add");
							});
							$('input[name=tzjl2]').click(function(){
								if($(this).attr("num")==0){
									$("#investExperience3").attr("readonly",false);
								}else{
									$("#investExperience3").val("").attr("readonly","readonly");
								}
							});
							$("#changeUpload3").click(function(){
								$("#fileToUpload3").click();
							});
						}else{
							$("#institution").val(Rdata["institution"]);
							//$("#realName3").val(Rdata["realName"]);
							//$("#certNo3").val(Rdata["certNo"]);
//							var indArr0 = Rdata["cardPhoto"].split(",");
//							for(var j=0;j<indArr0.length;j++){
//								$("#uploadInFo3").append("<span><a href="+cv.fileAddress+indArr0[j]+" target='_black' url='"+indArr0[j]+"'>查看上传文件</a><i onClick='fileDelete(this,\"#uploadInFo3\",\"#orgLoanReceiveProve3\")'></i></span>");
//							}
							for(var j=0;j<Rdata["orgCardPhotoList"].length;j++){
								if(Rdata["orgCardPhotoList"][j]["fileName"].length>10){
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"];
								}
								$("#uploadInFo3").append("<span><a href="+cv.fileAddress+Rdata["orgCardPhotoList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgCardPhotoList"][j]["fileUrl"]+"'>"+fileName+"</a><i id='"+Rdata["orgCardPhotoList"][j]["id"]+"' onClick='fileDelete(this,\"#uploadInFo3\",\"#orgLoanReceiveProve3\")'></i></span>");
							}
							$("#orgLoanReceiveProve3").val(Rdata["cardPhoto"]);
							getIndustry("#concern_industryBox3","#concern_industry3",Rdata["concernIndustry"]);
							
							$("#concernCity3").val(Rdata["concernCity"]);
							$("#investRatio3").val(Rdata["investRatio"]);
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl2]').eq(0).attr("num")==0){
									$('input[name=tzjl2]').eq(0).attr("checked",false);
									$('input[name=tzjl2]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl2]').eq(1).attr("checked",false);
									$('input[name=tzjl2]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience3").val(Rdata["investExperience"]);
							$("#investPlanNum3").val(Rdata["investPlanNum"]);
							$("#investPlanAmt3").val(Rdata["investPlanAmt"]);
							$("#read3").prop('checked',true);
							$('input[name=tzjl2]').click(function(){
								if($(this).attr("num")==0){
									$("#investExperience3").attr("readonly",false);
								}else{
									$("#investExperience3").val("").attr("readonly","readonly");
								}
							});
							$("#changeUpload3").click(function(){
								$("#fileToUpload3").click();
							});
							$("#bodiesGTRZBtn").unbind("click").text("提交").click(function(){
								XbodiesGTRZ(Rdata["id"]);
							});
							$("#bodiesGTRZBtnBc").unbind("click").click(function(){
								XbodiesGTRZ(Rdata["id"],"add");
							});
						}
					}else{
						$("#changeUpload3").click(function(){
							$("#fileToUpload3").click();
						});
						getIndustry("#concern_industryBox3","#concern_industry3");
						$('input[name=tzjl2]').click(function(){
							if($(this).attr("num")==0){
								$("#investExperience3").attr("readonly",false);
							}else{
								$("#investExperience3").val("").attr("readonly","readonly");
							}
						})
					}
					
				}else if(obj == "orgLeadInvestor"){
					if(Rdata){
						if(Rdata["id"]){
							orgLeadInvestorId = Rdata["id"];
						}
						
						if(Rdata["investAuthState"] == "auditing"){
							$("#institution4").val(Rdata["institution"]).attr("readonly","readonly");
							//$("#realName4").val(Rdata["realName"]).attr("readonly","readonly");
							//$("#certNo4").val(Rdata["certNo"]).attr("readonly","readonly");
							//var indArr0 = Rdata["cardPhoto"].split(",");
							
							
							$("#orgLoanReceiveProve4").val(Rdata["cardPhoto"]);
							$("#orgLoanReceiveProve5").val(Rdata["assetsCredentials"]);
							$("#orgLoanReceiveProve6").val(Rdata["historicalInvestmentData"]);
							getIndustry("#concern_industryBox4","#concern_industry4",Rdata["concernIndustry"],1);
							
							$("#concernCity4").val(Rdata["concernCity"]).attr("readonly","readonly");
							$("#investRatio4").val(Rdata["investRatio"]).attr("readonly","readonly");
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl3]').eq(0).attr("num")==0){
									$('input[name=tzjl3]').eq(0).attr("checked",false);
									$('input[name=tzjl3]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl3]').eq(1).attr("checked",false);
									$('input[name=tzjl3]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience4").val(Rdata["investExperience"]).attr("readonly","readonly");
							$("#investPlanNum4").val(Rdata["investPlanNum"]).attr("readonly","readonly");
							$("#investPlanAmt4").val(Rdata["investPlanAmt"]).attr("readonly","readonly");
							
//							var indArr1 = Rdata["assetsCredentials"].split(",");
//							for(var j=0;j<indArr1.length;j++){
//								$("#uploadInFo5").append("<span><a href="+cv.fileAddress+indArr1[j]+" target='_black' url='"+indArr1[j]+"'>查看上传文件</a></span>");
//							}
//							var indArr2 = Rdata["historicalInvestmentData"].split(",");
//							for(var j=0;j<indArr2.length;j++){
//								$("#uploadInFo6").append("<span><a href="+cv.fileAddress+indArr2[j]+" target='_black' url='"+indArr2[j]+"'>查看上传文件</a></span>");
//							}
							for(var j=0;j<Rdata["orgCardPhotoList"].length;j++){
								if(Rdata["orgCardPhotoList"][j]["fileName"].length>10){
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"];
								}
								$("#uploadInFo4").append("<span><a href="+cv.fileAddress+Rdata["orgCardPhotoList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgCardPhotoList"][j]["fileUrl"]+"'>"+fileName+"</a></span>");
							}
							for(var j=0;j<Rdata["orgAssetsCredentialsList"].length;j++){
								if(Rdata["orgAssetsCredentialsList"][j]["fileName"].length>10){
									var fileName = Rdata["orgAssetsCredentialsList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgAssetsCredentialsList"][j]["fileName"];
								}
								$("#uploadInFo5").append("<span><a href="+cv.fileAddress+Rdata["orgAssetsCredentialsList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgAssetsCredentialsList"][j]["fileUrl"]+"'>"+fileName+"</a></span>");
							}
							for(var j=0;j<Rdata["orgHistoricalInvestMentList"].length;j++){
								if(Rdata["orgHistoricalInvestMentList"][j]["fileName"].length>10){
									var fileName = Rdata["orgHistoricalInvestMentList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgHistoricalInvestMentList"][j]["fileName"];
								}
								$("#uploadInFo6").append("<span><a href="+cv.fileAddress+Rdata["orgHistoricalInvestMentList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgHistoricalInvestMentList"][j]["fileUrl"]+"'>"+fileName+"</a></span>");
							}
							
							$("#bodiesLTRZBtnBc,#bodiesLTRZBtnCz").remove();
							$("#read4").prop('checked',true).attr("disabled","disabled");
							$("#bodiesLTRZBtn").unbind("click").text("审核中...").css({"background":"#ccc","color":"#FFF","border":"none"});
						}else if(Rdata["investAuthState"] == "passed"){
							$("#institution4").val(Rdata["institution"]).attr("readonly","readonly");
							//$("#realName4").val(Rdata["realName"]).attr("readonly","readonly");
							//$("#certNo4").val(Rdata["certNo"]).attr("readonly","readonly");
//							var indArr0 = Rdata["cardPhoto"].split(",");
//							for(var j=0;j<indArr0.length;j++){
//								$("#uploadInFo4").append("<span><a href="+cv.fileAddress+indArr0[j]+" target='_black' url='"+indArr0[j]+"'>查看上传文件</a></span>");
//							}
							$("#orgLoanReceiveProve4").val(Rdata["cardPhoto"]);
							$("#orgLoanReceiveProve5").val(Rdata["assetsCredentials"]);
							$("#orgLoanReceiveProve6").val(Rdata["historicalInvestmentData"]);
							getIndustry("#concern_industryBox4","#concern_industry4",Rdata["concernIndustry"],1);
							
							$("#concernCity4").val(Rdata["concernCity"]).attr("readonly","readonly");
							$("#investRatio4").val(Rdata["investRatio"]).attr("readonly","readonly");
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl3]').eq(0).attr("num")==0){
									$('input[name=tzjl3]').eq(0).attr("checked",false);
									$('input[name=tzjl3]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl3]').eq(1).attr("checked",false);
									$('input[name=tzjl3]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience4").val(Rdata["investExperience"]).attr("readonly","readonly");
							$("#investPlanNum4").val(Rdata["investPlanNum"]).attr("readonly","readonly");
							$("#investPlanAmt4").val(Rdata["investPlanAmt"]).attr("readonly","readonly");
//							var indArr1 = Rdata["assetsCredentials"].split(",");
//							for(var j=0;j<indArr1.length;j++){
//								$("#uploadInFo5").append("<span><a href="+cv.fileAddress+indArr1[j]+" target='_black' url='"+indArr1[j]+"'>查看上传文件</a></span>");
//							}
//							var indArr2 = Rdata["historicalInvestmentData"].split(",");
//							for(var j=0;j<indArr2.length;j++){
//								$("#uploadInFo6").append("<span><a href="+cv.fileAddress+indArr2[j]+" target='_black' url='"+indArr2[j]+"'>查看上传文件</a></span>");
//							}
							for(var j=0;j<Rdata["orgCardPhotoList"].length;j++){
								if(Rdata["orgCardPhotoList"][j]["fileName"].length>10){
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"];
								}
								$("#uploadInFo4").append("<span><a href="+cv.fileAddress+Rdata["orgCardPhotoList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgCardPhotoList"][j]["fileUrl"]+"'>"+fileName+"</a></span>");
							}
							for(var j=0;j<Rdata["orgAssetsCredentialsList"].length;j++){
								if(Rdata["orgAssetsCredentialsList"][j]["fileName"].length>10){
									var fileName = Rdata["orgAssetsCredentialsList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgAssetsCredentialsList"][j]["fileName"];
								}
								$("#uploadInFo5").append("<span><a href="+cv.fileAddress+Rdata["orgAssetsCredentialsList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgAssetsCredentialsList"][j]["fileUrl"]+"'>"+fileName+"</a></span>");
							}
							for(var j=0;j<Rdata["orgHistoricalInvestMentList"].length;j++){
								if(Rdata["orgHistoricalInvestMentList"][j]["fileName"].length>10){
									var fileName = Rdata["orgHistoricalInvestMentList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgHistoricalInvestMentList"][j]["fileName"];
								}
								$("#uploadInFo6").append("<span><a href="+cv.fileAddress+Rdata["orgHistoricalInvestMentList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgHistoricalInvestMentList"][j]["fileUrl"]+"'>"+fileName+"</a></span>");
							}
							$("#bodiesLTRZBtnBc,#bodiesLTRZBtnCz").remove();
							$("#read4").prop('checked',true).attr("disabled","disabled");
							$("#bodiesLTRZBtn").unbind("click").text("认证成功");
						}else if(Rdata["investAuthState"] == "refused"){
							$("#institution4").val(Rdata["institution"]);
							//$("#realName4").val(Rdata["realName"]);
							//$("#certNo4").val(Rdata["certNo"]);
							var indArr0 = Rdata["cardPhoto"].split(",");
//							for(var j=0;j<indArr0.length;j++){
//								$("#uploadInFo4").append("<span><a href="+cv.fileAddress+indArr0[j]+" target='_black' url='"+indArr0[j]+"'>查看上传文件</a><i onClick='fileDelete(this,\"#uploadInFo4\",\"#orgLoanReceiveProve4\")'></i></span>");
//							}
							$("#orgLoanReceiveProve4").val(Rdata["cardPhoto"]);
							$("#orgLoanReceiveProve5").val(Rdata["assetsCredentials"]);
							$("#orgLoanReceiveProve6").val(Rdata["historicalInvestmentData"]);
							getIndustry("#concern_industryBox4","#concern_industry4",Rdata["concernIndustry"]);
							
							$("#concernCity4").val(Rdata["concernCity"]);
							$("#investRatio4").val(Rdata["investRatio"]);
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl3]').eq(0).attr("num")==0){
									$('input[name=tzjl3]').eq(0).attr("checked",false);
									$('input[name=tzjl3]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl3]').eq(1).attr("checked",false);
									$('input[name=tzjl3]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience4").val(Rdata["investExperience"]);
							$("#investPlanNum4").val(Rdata["investPlanNum"]);
							$("#investPlanAmt4").val(Rdata["investPlanAmt"]);
//							var indArr1 = Rdata["assetsCredentials"].split(",");
//							for(var j=0;j<indArr1.length;j++){
//								$("#uploadInFo5").append("<span><a href="+cv.fileAddress+indArr1[j]+" target='_black' url='"+indArr1[j]+"'>查看上传文件</a><i onClick='fileDelete(this,\"#uploadInFo5\",\"#orgLoanReceiveProve5\")'></i></span>");
//							}
//							var indArr2 = Rdata["historicalInvestmentData"].split(",");
//							for(var j=0;j<indArr2.length;j++){
//								$("#uploadInFo6").append("<span><a href="+cv.fileAddress+indArr2[j]+" target='_black' url='"+indArr2[j]+"'>查看上传文件</a><i onClick='fileDelete(this,\"#uploadInFo6\",\"#orgLoanReceiveProve6\")'></i></span>");
//							}
							for(var j=0;j<Rdata["orgCardPhotoList"].length;j++){
								if(Rdata["orgCardPhotoList"][j]["fileName"].length>10){
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"];
								}
								$("#uploadInFo4").append("<span><a href="+cv.fileAddress+Rdata["orgCardPhotoList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgCardPhotoList"][j]["fileUrl"]+"'>"+fileName+"</a><i id='"+Rdata["orgCardPhotoList"][j]["id"]+"' onClick='fileDelete(this,\"#uploadInFo4\",\"#orgLoanReceiveProve4\")'></i></span>");
							}
							for(var j=0;j<Rdata["orgAssetsCredentialsList"].length;j++){
								if(Rdata["orgAssetsCredentialsList"][j]["fileName"].length>10){
									var fileName = Rdata["orgAssetsCredentialsList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgAssetsCredentialsList"][j]["fileName"];
								}
								$("#uploadInFo5").append("<span><a href="+cv.fileAddress+Rdata["orgAssetsCredentialsList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgAssetsCredentialsList"][j]["fileUrl"]+"'>"+fileName+"</a><i id='"+Rdata["orgAssetsCredentialsList"][j]["id"]+"' onClick='fileDelete(this,\"#uploadInFo5\",\"#orgLoanReceiveProve5\")'></i></span>");
							}
							for(var j=0;j<Rdata["orgHistoricalInvestMentList"].length;j++){
								if(Rdata["orgHistoricalInvestMentList"][j]["fileName"].length>10){
									var fileName = Rdata["orgHistoricalInvestMentList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgHistoricalInvestMentList"][j]["fileName"];
								}
								$("#uploadInFo6").append("<span><a href="+cv.fileAddress+Rdata["orgHistoricalInvestMentList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgHistoricalInvestMentList"][j]["fileUrl"]+"'>"+fileName+"</a><i id='"+Rdata["orgHistoricalInvestMentList"][j]["id"]+"' onClick='fileDelete(this,\"#uploadInFo6\",\"#orgLoanReceiveProve6\")'></i></span>");
							}
							$("#read4").prop('checked',true).attr("disabled","disabled");
							$("#bodiesLTRZBtnBc").hide();
							$("#bodiesLTRZBtn").unbind("click").text("重新提交").click(function(){
								XbodiesLTRZ(Rdata["id"]);
							});
							$("#bodiesLTRZBtnBc").unbind("click").click(function(){
								XbodiesLTRZ(Rdata["id"],"add");
							});
							$('input[name=tzjl3]').click(function(){
								if($(this).attr("num")==0){
									$("#investExperience4").attr("readonly",false);
								}else{
									$("#investExperience4").val("").attr("readonly","readonly");
								}
							});
							$("#changeUpload4").click(function(){
								$("#fileToUpload4").click();
							});
							$("#changeUpload5").click(function(){
								$("#fileToUpload5").click();
							});
							$("#changeUpload6").click(function(){
								$("#fileToUpload6").click();
							});
						}else{
							$("#institution4").val(Rdata["institution"]);
							//$("#realName4").val(Rdata["realName"]);
							//$("#certNo4").val(Rdata["certNo"]);
//							var indArr0 = Rdata["cardPhoto"].split(",");
//							for(var j=0;j<indArr0.length;j++){
//								$("#uploadInFo4").append("<span><a href="+cv.fileAddress+indArr0[j]+" target='_black' url='"+indArr0[j]+"'>查看上传文件</a><i onClick='fileDelete(this,\"#uploadInFo4\",\"#orgLoanReceiveProve4\")'></i></span>");
//							}
							$("#orgLoanReceiveProve4").val(Rdata["cardPhoto"]);
							$("#orgLoanReceiveProve5").val(Rdata["assetsCredentials"]);
							$("#orgLoanReceiveProve6").val(Rdata["historicalInvestmentData"]);
							getIndustry("#concern_industryBox4","#concern_industry4",Rdata["concernIndustry"]);
							
							$("#concernCity4").val(Rdata["concernCity"]);
							$("#investRatio4").val(Rdata["investRatio"]);
							if(!Rdata["investExperience"]){
								if($('input[name=tzjl3]').eq(0).attr("num")==0){
									$('input[name=tzjl3]').eq(0).attr("checked",false);
									$('input[name=tzjl3]').eq(1).attr("checked","checked");
								}else{
									$('input[name=tzjl3]').eq(1).attr("checked",false);
									$('input[name=tzjl3]').eq(0).attr("checked","checked");
								}
							}
							$("#investExperience4").val(Rdata["investExperience"]);
							$("#investPlanNum4").val(Rdata["investPlanNum"]);
							$("#investPlanAmt4").val(Rdata["investPlanAmt"]);
//							var indArr1 = Rdata["assetsCredentials"].split(",");
//							for(var j=0;j<indArr1.length;j++){
//								$("#uploadInFo5").append("<span><a href="+cv.fileAddress+indArr1[j]+" target='_black' url='"+indArr1[j]+"'>查看上传文件</a><i onClick='fileDelete(this,\"#uploadInFo5\",\"#orgLoanReceiveProve5\")'></i></span>");
//							}
//							var indArr2 = Rdata["historicalInvestmentData"].split(",");
//							for(var j=0;j<indArr2.length;j++){
//								$("#uploadInFo6").append("<span><a href="+cv.fileAddress+indArr2[j]+" target='_black' url='"+indArr2[j]+"'>查看上传文件</a><i onClick='fileDelete(this,\"#uploadInFo6\",\"#orgLoanReceiveProve6\")'></i></span>");
//							}
							for(var j=0;j<Rdata["orgCardPhotoList"].length;j++){
								if(Rdata["orgCardPhotoList"][j]["fileName"].length>10){
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgCardPhotoList"][j]["fileName"];
								}
								$("#uploadInFo4").append("<span><a href="+cv.fileAddress+Rdata["orgCardPhotoList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgCardPhotoList"][j]["fileUrl"]+"'>"+fileName+"</a><i id='"+Rdata["orgCardPhotoList"][j]["id"]+"' onClick='fileDelete(this,\"#uploadInFo4\",\"#orgLoanReceiveProve4\")'></i></span>");
							}
							for(var j=0;j<Rdata["orgAssetsCredentialsList"].length;j++){
								if(Rdata["orgAssetsCredentialsList"][j]["fileName"].length>10){
									var fileName = Rdata["orgAssetsCredentialsList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgAssetsCredentialsList"][j]["fileName"];
								}
								$("#uploadInFo5").append("<span><a href="+cv.fileAddress+Rdata["orgAssetsCredentialsList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgAssetsCredentialsList"][j]["fileUrl"]+"'>"+fileName+"</a><i id='"+Rdata["orgAssetsCredentialsList"][j]["id"]+"' onClick='fileDelete(this,\"#uploadInFo5\",\"#orgLoanReceiveProve5\")'></i></span>");
							}
							for(var j=0;j<Rdata["orgHistoricalInvestMentList"].length;j++){
								if(Rdata["orgHistoricalInvestMentList"][j]["fileName"].length>10){
									var fileName = Rdata["orgHistoricalInvestMentList"][j]["fileName"].substring(0,10)+"...";
								}else{
									var fileName = Rdata["orgHistoricalInvestMentList"][j]["fileName"];
								}
								$("#uploadInFo6").append("<span><a href="+cv.fileAddress+Rdata["orgHistoricalInvestMentList"][j]["fileUrl"]+" target='_black' url='"+Rdata["orgHistoricalInvestMentList"][j]["fileUrl"]+"'>"+fileName+"</a><i id='"+Rdata["orgHistoricalInvestMentList"][j]["id"]+"' onClick='fileDelete(this,\"#uploadInFo6\",\"#orgLoanReceiveProve6\")'></i></span>");
							}
							$("#read4").prop('checked',true);
							$('input[name=tzjl3]').click(function(){
								if($(this).attr("num")==0){
									$("#investExperience4").attr("readonly",false);
								}else{
									$("#investExperience4").val("").attr("readonly","readonly");
								}
							});
							$("#changeUpload4").click(function(){
								$("#fileToUpload4").click();
							});
							$("#changeUpload5").click(function(){
								$("#fileToUpload5").click();
							});
							$("#changeUpload6").click(function(){
								$("#fileToUpload6").click();
							});
							$("#bodiesLTRZBtn").unbind("click").text("提交").click(function(){
								XbodiesLTRZ(Rdata["id"]);
							});
							$("#bodiesLTRZBtnBc").unbind("click").click(function(){
								XbodiesLTRZ(Rdata["id"],"add");
							});
						}
					}else{
						$("#changeUpload4").click(function(){
							$("#fileToUpload4").click();
						});
						$("#changeUpload5").click(function(){
							$("#fileToUpload5").click();
						});
						$("#changeUpload6").click(function(){
							$("#fileToUpload6").click();
						});
						getIndustry("#concern_industryBox4","#concern_industry4");
						
						$('input[name=tzjl3]').click(function(){
							if($(this).attr("num")==0){
								$("#investExperience4").attr("readonly",false);
							}else{
								$("#investExperience4").val("").attr("readonly","readonly");
							}
						})
					}
					
				}
//			}else{
//				if(obj == "investor"){
//					getIndustry("#concern_industryBox","#concern_industry");
//				}else if(obj == "leadInvestor"){
//					getIndustry("#concern_industryBox1","#concern_industry1");
//				}else if(obj == "orgInvestor"){
//					getIndustry("#concern_industryBox3","#concern_industry3");
//				}else if(obj == "orgLeadInvestor"){
//					getIndustry("#concern_industryBox4","#concern_industry4");
//				}
//			}

		},
		error: function(request){
			console.log("获取认证信息异常");
		}
	});
}

function fileDelete(_this,url,val){
	
	$.ajax({
		url: path + "/fileUpload/removeFile.html",
		type: "post",
		dataType: "json",
		data:{
			id:$(_this).attr("id")
		},
		success: function(data){
			if(data["success"]){
				AlertDialog.tip("删除成功！");
				$(_this).parent().remove();
				$(val).val("");
				var urlNum = $(url).find("span");
				for(var f=0;f<urlNum.length;f++){
					if(urlNum.length>1){
						if(f==0){
							$(val).val(urlNum.eq(f).find("a").attr("url"));
						}else{
							$(val).val($(val).val()+","+urlNum.eq(f).find("a").attr("url"));
						}
					}else{
						$(val).val(urlNum.eq(f).find("a").attr("url"));
					}
				}
			}else{
				AlertDialog.tip("删除失败！");
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
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
            		$('#'+showId).attr("src", cv["fileAddress"] + "/" + json["msg"]);
            		$("#" + setValueId).val(json["msg"]);
            	}
            } else {
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
		var jtop = $("#" + id).offset().top -120;
		$("html,body").animate({
			scrollTop : jtop + "px"
		},800);
		return false;
	}
	AlertDialog.hide();
	return true;
}

function investRatioNaN(str, id ){
	var reg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
	if(!str){
		AlertDialog.show("投资金额不能为空", id, 10, 20, "jump");
		return false;
	}else{
		if(isNaN(str)){
			AlertDialog.show("请输入正确金额", id, 10, 20, "jump");
			return false;
		}else if(!reg.test(str)){
			AlertDialog.show("不能超过两位小数", id, 10, 20, "jump");
			return false;
		}
	}

	AlertDialog.hide();
	return true;
}

//获取投资领域
function getIndustry(id1,id2,ind, type, type1){
	var gArr = [], gStr = '', l;
	var c1 = '', c2 = ''; //两个感兴趣的投资赋值
	var indLength, indArr = [];
	$.ajax({
		url: path + "/dictionary/getByType.html",
		type: "post",
		dataType: "json",
		data: {"typeCode": "concernIndustry"},
		success: function(data){
			l = data.length;
			for(var i=0;i<l;i++){
				gArr.push('<span class="wid128"><input type="checkbox" name="tl" id="'+id1+i+'" value="'+data[i]["displayName"]+'" class="fl check_ipt"><label for="'+id1+i+'" ><em class="fl">'+data[i]["displayName"].substring(0,8)+'</em></label></span>');
			}
			gStr = gArr.join("");
			$(id1).html(gStr);
			//如果是查询，投资领域赋值
			if(ind){
				indArr = ind.split(","), indLength = indArr.length;
				for(var j=0;j<indLength;j++){
					$(id1).find("input[value="+indArr[j]+"]").attr("checked", "checked");
				}
				$(id2).val(ind);
			}
			if(type){
				$(id1).find("input").attr("disabled", "disabled");
			}
		},
		error: function(request){
			console.log("获取投资领域异常");
		}
	});
}

//选择文件之后执行上传  
$('#fileToUpload').on('change', function() {
    $.ajaxFileUpload({  
        url:path+'/fileUpload/uploadFile.html?parentId='+leadInvestorId+'&type=assetsCredentials',  
        secureuri:false,  
        fileElementId:'fileToUpload',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {
        	if(data["success"]){
        		if($("#orgLoanReceiveProve1").val()){
        			var orgLoanReceiveProve = $("#orgLoanReceiveProve1").val()+","+data.fileUrl;
        		}else{
        			var orgLoanReceiveProve = data.fileUrl;
        		}
        		$("#orgLoanReceiveProve1").val(orgLoanReceiveProve);
        		AlertDialog.tip('上传成功！');
        		if(data.fileName.length>10){
        			var fileName = data.fileName.substring(0,10)+"..."
        		}else{
        			var fileName = data.fileName
        		}
        		$("#uploadInFo").append("<span><a href="+cv.fileAddress+data.fileUrl+" target='_black' url='"+data.fileUrl+"'>"+fileName+"</a><i id='"+data.id+"' onClick='fileDelete(this,\"#uploadInFo\",\"#orgLoanReceiveProve1\")'></i></span>");
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
$('#fileToUpload1').on('change', function() {
    $.ajaxFileUpload({  
        url:path+'/fileUpload/uploadFile.html?parentId='+leadInvestorId+'&type=historicalInvestMent',  
        secureuri:false,  
        fileElementId:'fileToUpload1',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {
        	if(data["success"]){
        		if($("#orgLoanReceiveProve2").val()){
        			var orgLoanReceiveProve = $("#orgLoanReceiveProve2").val()+","+data.fileUrl;
        		}else{
        			var orgLoanReceiveProve = data.fileUrl;
        		}
        		$("#orgLoanReceiveProve2").val(orgLoanReceiveProve);
        		AlertDialog.tip('上传成功！');
        		if(data.fileName.length>10){
        			var fileName = data.fileName.substring(0,10)+"..."
        		}else{
        			var fileName = data.fileName
        		}
        		$("#uploadInFo1").append("<span><a href="+cv.fileAddress+data.fileUrl+" target='_black' url='"+data.fileUrl+"'>"+fileName+"</a><i id='"+data.id+"' onClick='fileDelete(this,\"#uploadInFo1\",\"#orgLoanReceiveProve2\")'></i></span>");
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
$('#fileToUpload3').on('change', function() {
    $.ajaxFileUpload({  
        url:path+'/fileUpload/uploadFile.html?parentId='+orgInvestorId+'&type=orgCardPhoto',  
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
        		AlertDialog.tip('上传成功！');
        		if(data.fileName.length>10){
        			var fileName = data.fileName.substring(0,10)+"..."
        		}else{
        			var fileName = data.fileName
        		}
        		$("#uploadInFo3").append("<span><a href="+cv.fileAddress+data.fileUrl+" target='_black' url='"+data.fileUrl+"'>"+fileName+"</a><i id='"+data.id+"' onClick='fileDelete(this,\"#uploadInFo3\",\"#orgLoanReceiveProve3\")'></i></span>");
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
        url:path+'/fileUpload/uploadFile.html?parentId='+orgLeadInvestorId+'&type=orgCardPhoto',  
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
        		AlertDialog.tip('上传成功！');
        		if(data.fileName.length>10){
        			var fileName = data.fileName.substring(0,10)+"..."
        		}else{
        			var fileName = data.fileName
        		}
        		$("#uploadInFo4").append("<span><a href="+cv.fileAddress+data.fileUrl+" target='_black' url='"+data.fileUrl+"'>"+fileName+"</a><i id='"+data.id+"' onClick='fileDelete(this,\"#uploadInFo4\",\"#orgLoanReceiveProve4\")'></i></span>");
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
        url:path+'/fileUpload/uploadFile.html?parentId='+orgLeadInvestorId+'&type=orgAssetsCredentials',  
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
        		AlertDialog.tip('上传成功！');
        		if(data.fileName.length>10){
        			var fileName = data.fileName.substring(0,10)+"..."
        		}else{
        			var fileName = data.fileName
        		}
        		$("#uploadInFo5").append("<span><a href="+cv.fileAddress+data.fileUrl+" target='_black' url='"+data.fileUrl+"'>"+fileName+"</a><i id='"+data.id+"' onClick='fileDelete(this,\"#uploadInFo5\",\"#orgLoanReceiveProve5\")'></i></span>");
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
$('#fileToUpload6').on('change', function() {
    $.ajaxFileUpload({  
        url:path+'/fileUpload/uploadFile.html?parentId='+orgLeadInvestorId+'&type=orgHistoricalInvestMent',  
        secureuri:false,  
        fileElementId:'fileToUpload6',//file标签的id  
        dataType: 'json',//返回数据的类型  
        success: function (data, status) {
        	if(data["success"]){
        		if($("#orgLoanReceiveProve6").val()){
        			var orgLoanReceiveProve = $("#orgLoanReceiveProve6").val()+","+data.fileUrl;
        		}else{
        			var orgLoanReceiveProve = data.fileUrl;
        		}
        		$("#orgLoanReceiveProve6").val(orgLoanReceiveProve);
        		AlertDialog.tip('上传成功！');
        		if(data.fileName.length>10){
        			var fileName = data.fileName.substring(0,10)+"..."
        		}else{
        			var fileName = data.fileName
        		}
        		$("#uploadInFo6").append("<span><a href="+cv.fileAddress+data.fileUrl+" target='_black' url='"+data.fileUrl+"'>"+fileName+"</a><i id='"+data.id+"' onClick='fileDelete(this,\"#uploadInFo6\",\"#orgLoanReceiveProve6\")'></i></span>");
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