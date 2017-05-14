var param = window.location.search;
var LoanType = "", loanName = "", nodeType="";
if(siteUserId == "null"){
	window.location.href = path + "/common/login.html";
}
if(param){
	var base64 = new Base64();
	LoanType = getQueryString('type');
	nodeType = getQueryString('nodeType');
	loanName = base64.decode(getQueryString('loanName'));
}
var transferNo = getQueryString("transferNo");
var loanNo = getQueryString("loanNo");
$(function(){
	var wh = document.documentElement.clientHeight, ww = document.documentElement.clientWidth;
	var lw = (ww - $("#addBgpop").width())/2, lt =  (wh - $("#addBgpop").height())/2,llw = (ww - $("#emailDiv").width())/2, llt =  (wh - $("#emailDiv").height())/2;
//	document.getElementById("addBgpop").style.top = lt+"px";
	document.getElementById("addBgpop").style.top = "50px";
	document.getElementById("addBgpop").style.left = lw+"px";
	document.getElementById("emailDiv").style.top = llt+"px";
	document.getElementById("emailDiv").style.left = llw+"px";
	
	getCrowdfundTransferDetail();
	getAddressList();
	getUserInfo();
	showProvince();
//	getAddress();
	$("#saveAddress").unbind("click").click(addAddress);
	$(".address_add").click(function(){
		if($("#addressList>li").length<6){
			$("#newAddredd").show();
			$("#province").val("");
			$("#city").val("");
			$("#county").val("");
			$("#adressDetail").val("");
			$("#postCode").val("");
			$("#postUser").val("");
			$("#mobile").val("");
		}else{
			AlertDialog.tip("最多只能保存10条有效收货地址！");
		}
		
	});
});
//查询收货地址
function getAddressList(){
	var aArr = [], aStr = '', l, bArr = [], bStr = '';
	var provinece = '', city = '';
	$.ajax({
		url: path + "/address/getPostAddressList.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				AlertDialog.tip(data["msg"]);
				return false;
			}
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			$("#addTip").html('已经保存了'+l+'条地址，还能保存'+(20-l)+'条地址');
			for(var i=0;i<l;i++){
				provinece = data[i]["provinceName"] ? data[i]["provinceName"] : "";
				city = data[i]["cityName"] ? data[i]["cityName"] : "";
				aArr.push('<li>');
				
				if(data[i]["isDefault"] == "1"){
					aArr.push('<p class="p3 moren jisong" id="'+data[i]["id"]+'" addno="'+data[i]["addressNo"]+'" address="'+provinece+city+data[i]["adressDetail"]+data[i]["postUser"]+data[i]["mobile"]+'" onclick="choiceAddress(event)">寄送至</p>');
				}else{
					aArr.push('<p class="p3 moren"  id="'+data[i]["id"]+'" addno="'+data[i]["addressNo"]+'" address="'+provinece+city+data[i]["adressDetail"]+data[i]["postUser"]+data[i]["mobile"]+'" onclick="choiceAddress(event)" ></p>');
				}
				aArr.push('<p class="p3">'+data[i]["postUser"]+'</p>');
				aArr.push('<p>'+null2value(data[i]["provinceName"])+'&nbsp;&nbsp;'+null2value(data[i]["cityName"])+'</p>');
				aArr.push('<p class="p2" style="white-space:nowrap;text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow: hidden; " title="'+data[i]["adressDetail"]+'">'+data[i]["adressDetail"]+'</p>');
				aArr.push('<p class="p3">'+data[i]["postCode"]+'</p>');
				aArr.push('<p>'+data[i]["mobile"]+'</p>');
				aArr.push('<p>');
				aArr.push('<a class="xg_mark" style=" border-right: 2px solid #1ea5ff;" href="javascript:void(0);" vaddId="'+data[i]["id"]+'" onclick=modifyAddress(event)>修改</a>');
				aArr.push('<a href="javascript:void(0);" vaddId="'+data[i]["id"]+'" onclick=deleteAddress(event)>删除</a>');
				aArr.push('</p>');
				if(data[i]["isDefault"] == "1"){
					aArr.push('<p><a class="mr">默认地址</a></p>');
				}else{
					aArr.push('<p><a class="sz" onclick="changeDefault(event)" href="javascript:void(0);">设为默认</a></p>');
				}
				aArr.push('</li>');
				
				if(data[i]["isDefault"] == "1"){
					bArr.push('<span>'+provinece+city+data[i]["adressDetail"]+data[i]["postUser"]+data[i]["mobile"]+'</span><a class="col_blue" onclick="addNewAddress();" id="'+data[i]["id"]+'" addno="'+data[i]["addressNo"]+'">修改地址</a>');
				}else{
					if(i==0){
						provinece = data[0]["provinceName"] ? data[0]["provinceName"] : "";
						city = data[0]["cityName"] ? data[0]["cityName"] : "";
						bArr.push('<span>'+provinece+city+data[0]["adressDetail"]+data[0]["postUser"]+data[0]["mobile"]+'</span><a class="col_blue" onclick="addNewAddress();" id="'+data[i]["id"]+'" addno="'+data[i]["addressNo"]+'">修改地址</a>');
					}
				}
			}
			bStr = bArr.join("");
			if(bStr){
				$("#address").html("").html(bStr);
			}else{
				$("#address").html("").html('<a class="col_blue" onclick="addNewAddress();">添加地址</a>');
			}
			
			aStr = aArr.join("");
			$("#addressList").html("").html(aStr);
		},
		error: function(request){
			console.log("获取收货地址异常");
		}
	});
}
//选择地址
function choiceAddress(event){
	var obj = event.srcElement || event.target;
	var address = $(obj).attr("address");
	var addno = $(obj).attr("addno");
	var id = $(obj).attr("id");
	$("#addressList p.moren").removeClass("jisong").html("");
	$(obj).addClass("jisong").html("寄送至");
	$("#address").html("").html('<span>'+address+'</span><a class="col_blue" onclick="addNewAddress();" id="'+id+'" addno="'+addno+'">修改地址</a>');
	
}
//获取收货地址
//function getAddress(){
//	var aArr = [], aStr = '', l;
//	var provinece = '', city = '';
//	$.ajax({
//		url: path + "/address/getPostAddressList.html",
//		type: "post",
//		dataType: "json",
//		success: function(data){
//			if(!data["success"]){
//				AlertDialog.tip(data["msg"]);
//				return false;
//			}
//			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
//			if(l == 0){
//				aStr = '<div class="an"><span class="c" onclick="addNewAddress();">添加新地址</span></div>';
//			}else{
//				for(var i=0;i<l;i++){
//						provinece = data[i]["provinceName"] ? data[i]["provinceName"] : "";
//						city = data[i]["cityName"] ? data[i]["cityName"] : "";
//						var tel = data[i]["mobile"].substr(0, 3) + '****' + data[i]["mobile"].substr(7);
//						aArr.push('<div class="clearfix address-list xz_list" postAddressNo="'+data[i]["addressNo"]+'">');
//						aArr.push('<div class="fl">');
//						aArr.push('<span>'+data[i]["postUser"]+'</span>');
//						aArr.push('<span>'+data[i]["mobile"]+'</span>');
//						aArr.push('<span>'+provinece+city+data[i]["adressDetail"]+'</span>');
//						aArr.push('</div>');
//						if(data[i]["isDefault"] == "1"){
//							aArr.push('<a class="fr rig_opt2 aur"></a>');
//						}else{
//							aArr.push('<a class="fr rig_opt2"></a>');
//						}
//						aArr.push('</div>');
//
//						if(data[i]["isDefault"] == "1"){
//							$("#address").html(data[i]["postUser"]+' '+provinece+city+data[i]["adressDetail"]+' '+tel);
//						}
//					}
//				aStr = aArr.join("");
//			}
//			$("#address_xq").html(aStr);
//			
//			$("#updateAdd").click(function(){
//				$(".sbgpop").show();
//				$("#addBgpop").show();
//			});
//			$("#close_abs").click(function(){
//				$(".sbgpop").hide();
//				$("#addBgpop").hide();
//				$("#newAddredd").hide();
//			});
//			$("#address_xq>div a.rig_opt2").click(function(){
//				$("#address_xq>div a.rig_opt2").removeClass("aur");
//				$(this).addClass("aur");
//			});
//
//			$("#saveAddress1").click(function(){
//				if($("#address_xq>div>a").hasClass("aur")){
//					for(var i=0;i<$("#address_xq>div").length;i++){
//						if($("#address_xq>div").eq(i).find("a").hasClass("aur")){
//							$("#address").html($("#address_xq>div").eq(i).find("span").eq(0).html()+' '+$("#address_xq>div").eq(i).find("span").eq(1).html()+' '+$("#address_xq>div").eq(i).find("span").eq(2).html());
//						}
//					}
//					$("#address_xq").click(function(){
//						$("#add_dzh,#stockBgpop").show();
//					});
//					$("#add_dzh,#stockBgpop").hide();
//				}else{
//					AlertDialog.tip("请选择收货地址");
//				}
//			});
//		},
//		error: function(request){
//			console.log("获取收货地址异常");
//		}
//	});
//}
function addNewAddress(){
	$("#province").val("");
	$("#city").val("");
	$("#county").val("");
	$("#adressDetail").val("");
	$("#postCode").val("");
	$("#postUser").val("");
	$("#mobile").val("");
	$(".sbgpop").show();
	$(".rg_side,#paymentBgpop").show();
	$(".prompt_close").click(function(){
		$(".rg_side,#paymentBgpop").hide();
		$(".sbgpop").hide();
	});
	
//	$("#addBgpop").show();
//	$("#newAddredd").show();
}
//增加收货地址
function addAddress(){
	if(checkProCity("province")){
		if(checkProCity("city")){
			if(isNull($("#adressDetail").val(),"adressDetail","详细地址不能为空",0,30)){
				if(Valify.postCode($("#postCode").val(),"postCode")){
					if(Valify.realName($("#postUser").val(),"postUser")){
						if(Valify.phone($("#mobile").val(),"mobile")){
							$.ajax({
								url: path + "/address/savePostAddress.html",
								type: "post",
								dataType: "json",
								data:{
									"province":$("#province").val(),
									"city":$("#city").val(),
									"county":$("#county").val(),
									"adressDetail":$("#adressDetail").val(),
									"postCode":$("#postCode").val(),
									"postUser":$("#postUser").val(),
									"mobile":$("#mobile").val(),
									"isDefault":$("#aDefault").attr("default")
								},
								success: function(data){
									if(data["success"]){
										AlertDialog.tip("新增地址成功！");
										getAddressList();
										$('html,body').animate({
											scrollTop : '200px'
										}, 800);
										$("#newAddredd").hide();
									}else{
										AlertDialog.tip("新增地址失败！");
										return false;
									}
								},
								error: function(request){
									console.log("增加收货地址异常");
								}
							});
						}
					}
				}
			}
		}
	}
	
}
//获取挂牌列表
function getCrowdfundTransferDetail(){
	$.ajax({
		url: path + "/crowdfundProductTransfer/getCrowdfundTransferDetail.html",
		type: "post",
		dataType: "json",
		data:{
			"transferNo":transferNo
		},
		success: function(data){
			if(!data["success"]){
				console.log("获取转让详情失败");
				return false;
			}
			var list = data["msg"];
			if(list["backType"] == "X"){
				$("#mailbox").parent().show();
			}else{
				$("#address").parent().show();
			}
			$("#loanName").text(list["loanName"]);
			$("#transferUser").text(list["nickName"]);
			$("#transferAmt").text(list["transferAmt"].toFixed(2));
			$("#backContent").text(list["backContent"]);
			if(list["transFee"]){
				$("#transFee").text(list["transFee"].toFixed(2)+companyCode);
			}else{
				$("#transFee").text("免运费");
			}
			$("#supportAmt").text((Number(list["supportAmt"])+Number(list["transFee"])).toFixed(2));
			var backType = list["backType"];
			$("#transBtu").click(function(){
				transSub(backType);
			});
		},
		error:function(){
			console.log("获取挂牌接口失败")
		}
	});
}
//转让支持
function transSub(backType){
	if($("#saleInput").val().length>20){
		AlertDialog.tip("您的备注信息请控制在20字以内！");
		return false;
	}
	if(backType == "S"){
		if(!$("#address a").attr("addno")){
			AlertDialog.tip("请添加收货地址");
			return false;
		}
		var dataStr = {
			"transferNo": transferNo,
			"postAddressNo": $("#address a").attr("id"),
			"supportContent": $("#address span").text(),
			"supportRemark": $("#saleInput").val()
		}
	}else{
		if($("#mailbox").text() == "绑定邮箱"){
			AlertDialog.tip("请绑定邮箱");
			return false;
		}
		var dataStr = {
			"transferNo": transferNo,
			"postAddressNo": $("#address a").attr("id"),
			"supportContent": $("#mailbox").text(),
			"supportRemark": $("#saleInput").val()
		}
	}
	/*if(!$("#read").prop("checked")){
		AlertDialog.show("请阅读协议并勾选", "read", 25, 23);
		return false;
	}*/
	$.ajax({
		url: path + "/letvPay/transfer/saveUserInfo.html",
		type: "post",
		dataType: "json",
		async: false,
		data: dataStr,
		success: function(data){
			if(!data["success"]){
				AlertDialog.tip(data["msg"]);
			}else{
				window.location.href = path + "/common/transferPay-confirm.html?loanNo="+loanNo+"&transferNo="+transferNo+"&transId="+data["msg"];	
			}
		},
		error: function(request){
			console.log("获取用户余额异常");
		}
	});
}
//获取个人信息
function getUserInfo(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["user"]["email"]){
				$("#mailbox").html('<a id="modifyEmail">绑定邮箱</a>');
				$("#modifyEmail").click(modifyEmail);
			}else{
				$("#mailbox").html(data["user"]["email"]);
			}
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//检测省份
function checkProCity(id){
	if(!$("#" + id).val()){
		AlertDialog.show("请选择"+$("#" + id).attr("nullMessage"), id, 20, 40);
		return false;
	}else{
		AlertDialog.hide();
		return true;
	}
}
function isNull(str,id,tipStr,topSize,rightSize){
	if(!str){
		AlertDialog.show(tipStr, id, topSize, rightSize);
		return false;
	}
	AlertDialog.hide();
	return true;
}
/**
 * 
 * @param id 查询所在地时需要用到的参数（根据省份id得到省份）
 */
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
			pArr.push('<option value="">请选择省份</option>');
			for(var i=0;i<l;i++){
				pArr.push('<option value="'+data[i]["id"]+'">'+data[i]["shortName"]+'</option>');
			}
			pStr = pArr.join("");
			$("#province").html(pStr);
			//点击省份后，根据省份id查询城市
			$("#province").change(function(){
				showCity($(this).val());
			});
			if(id){
				$("#province").val(id);
			}
		},
		error: function(request){
			console.log("获取省份信息异常");
		}
	});
}

/**
 * 
 * @param pid 查询所有省市
 * @param cid 获得需要的省市id
 */
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
			cArr.push('<option value="">请选择</option>');
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
//修改地址
function modifyAddress(event){
	var obj = event.srcElement || event.target;
	var id = $(obj).attr("vaddid");
	AlertDialog.confirm(modifyAdd, null, "您确定要修改吗?", "确定", "取消");
	function modifyAdd(){
		$('html,body').animate({
			scrollTop : '200px'
		}, 800);
		$("#newAddredd").show();
		$("#address_title").html("+修改收货地址");
		$.ajax({
			url: path + "/address/getPostAddressById.html",
			type: "post",
			dataType: "json",
			data: {"id": id},
			success: function(data){
				if(data["success"]){
					$("#province").val(showProvince(data["msg"]["province"]));
					$("#city").val(showCity(data["msg"]["province"],data["msg"]["city"]));
					$("#adressDetail").val(data["msg"]["adressDetail"]);
					$("#postCode").val(data["msg"]["postCode"]);
					$("#postUser").val(data["msg"]["postUser"]);
					$("#mobile").val(data["msg"]["mobile"]);
					if(data["msg"]["isDefault"] == 0){
						$("#aDefault").prop("checked",false);
					}else{
						$("#aDefault").prop("checked",true);
					}
					$("#saveAddress").unbind("click").click(updateAddress);
				}
			},
			error: function(request){
				console.log("获取城市信息异常");
			}
		});
	}
	function updateAddress(){
		if(checkProCity("province")){
			if(checkProCity("city")){
				if(isNull($("#adressDetail").val(),"adressDetail","详细地址不能为空")){
					if(Valify.postCode($("#postCode").val(),"postUser")){
						if(Valify.realName($("#postUser").val(),"postUser")){
							if(Valify.phone($("#mobile").val(),"mobile","123")){
								$.ajax({
									url: path + "/address/updatePostAddress.html",
									type: "post",
									dataType: "json",
									data:{
										"id": id,
										"province":$("#province").val(),
										"city":$("#city").val(),
										"adressDetail":$("#adressDetail").val(),
										"postCode":$("#postCode").val(),
										"postUser":$("#postUser").val(),
										"mobile":$("#mobile").val(),
										"isDefault":$("#aDefault").attr("default")
									},
									success: function(data){
										if(data["success"]){
											getAddressList();
											AlertDialog.tip("修改成功！");
											$('html,body').animate({
												scrollTop : '200px'
											}, 800);
											$("#newAddredd").hide();
										}else{
											AlertDialog.tip("修改失败！");
											return false;
										}
									},
									error: function(request){
										console.log("修改收货地址异常");
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
/**
 * type: 如果不传值，val为false返回 --
 * type: 如果传0 ，val为false返回0
 * type: 如果传0.0，val为false返回0.0
 * @param val 传入的判断是否存在的值
 */
function null2value(val,type){
	val = type ? (type=="0.0" ? val.toFixed(2) : (val ? val : 0)) : (val ? val : "--");
	return val;
}
//设为默认地址
function changeDefault(event){
	if($('#addressList>li').length == 1){
		var id = $('#addressList>li').find('.xg_mark').attr("vaddid");
		modifyDelete();
	}else{
		var obj =  event.srcElement || event.target;
		var id = $(obj).parents("#addressList>li").find('.xg_mark').attr("vaddid");
		AlertDialog.confirm(modifyDelete, null, "您确定要设为默认地址吗?","确定", "取消");
	}
	function modifyDelete(){
		$.ajax({
			url: path + "/address/updatePostAddress.html",
			type: "post",
			dataType: "json",
			data:{
				"id": id,
				"isDefault":1
			},
			success: function(data){
				if(data["success"]){
					if($('.address-list').length != 1){
						AlertDialog.tip("设置成功！");
					}
					getAddressList();
				}else{
					if($('.address-list').length != 1){
						AlertDialog.tip("设置失败！");
					}
					return false;
				}
			},
			error: function(request){
				console.log("设置默认地址异常");
			}
		});
	}
}

//删除地址
function deleteAddress(){
	var obj = event.srcElement || event.target;
	var id = $(obj).attr("vaddid");
	AlertDialog.confirm(modifyDelete, null, "您确定要删除吗?","确定", "取消");
	function modifyDelete(){
		$.ajax({
			url: path + "/address/deletePostAddress.html",
			type: "post",
			dataType: "json",
			data:{
				"id":id
			},
			success: function(data){
				if(data["success"]){
					$(obj).parent().parent().remove();
					AlertDialog.tip("删除成功！");
				}else{
					AlertDialog.tip("删除失败！");
					return false;
				}
			},
			error: function(request){
				console.log("删除收货地址异常");
			}
		});
	}
}

//绑定邮箱
function modifyEmail(){
	$("#newEmail,#emailCode").val("");
	$(".sbgpop").show();
	clearInterval(productTime);
	$("#emailDiv").show().find("ul").html($("#emailDivHtml").html());
	$(".prompt_close").click(function(){
		$(".rg_side,#emailDiv").hide();
		$(".sbgpop").hide();
	});
	$("#emailSendBtn").click(sendCodeEamil);
	$("#checkEmail").click(checkEmail);
	//邮箱是否存在
	$("#newEmail").blur(function(){
		if(!$(this).val()){
			AlertDialog.show("请输入邮箱", "newEmail", 0, 20);
			return false;
		}
		$.ajax({
			url: path + "/safeLevel/checkEmail.html",
			type: "post",
			dataType: "json",
			data: {
				"email": $("#newEmail").val()
			},
			success: function(data){
				if(data["isExist"]){
					AlertDialog.show("邮箱已经存在，请重新输入！", "newEmail", 0, 20);
				}
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
	});
	
}
//发送邮箱认证码
function sendCodeEamil(){
	if(Valify.email($("#newEmail").val(), "newEmail")){
		$.ajax({
			url: path + "/safeLevel/checkEmail.html",
			type: "post",
			dataType: "json",
			data: {
				"email": $("#newEmail").val()
			},
			success: function(data){
				if(data["isExist"]){
					AlertDialog.show("邮箱已经存在，请重新输入！", "newEmail", 0, 20);
					return false;
				}else{
					$("#emailSendBtn").unbind("click").css({"cursor":"default","color":"#888"}).text("稍后可重发");
					$.ajax({
						url: path + "/verifycode/sendEmailAuthCode.html",
						type: "post",
						dataType: "json",
						data: {
							"sendTarget": $("#newEmail").val()
						},
						success: function(data){
							if(data["success"]){
								$("#emailSendBtn").unbind("click").css({"cursor":"default","color":"#888"}).text("60 秒后可重发");
								productDown(60, "emailSendBtn", emailOverFn);
							}
						},
						error: function(request){
							console.log("获取发送邮箱认证码信息异常");
						}
					});
				}
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
		
	}
}
function emailOverFn(){
	clearInterval(productTime);
	$("#emailSendBtn").text("获取验证码").css({"cursor":"pointer", "background":"#f7f7f7", "color":"#222"});
	$("#emailSendBtn").click(sendCodeEamil);
}
//验证是否为空
function isNull(str, id){
	if(!str){
		AlertDialog.show($("#"+id).attr("nullMessage"), id, 0, 20);
		return false;
	}else{
		AlertDialog.hide();
		return true;
	}
}
//认证邮箱
function checkEmail(){
	if(Valify.email($("#newEmail").val(), "newEmail")){
		$.ajax({
			url: path + "/safeLevel/checkEmail.html",
			type: "post",
			dataType: "json",
			data: {
				"email": $("#newEmail").val()
			},
			success: function(data){
				if(data["isExist"]){
					AlertDialog.show("邮箱已经存在，请重新输入！", "newEmail", 0, 20);
					return false;
				}
			},
			error: function(request){
				console.log("获取个人信息异常");
			}
		});
		if(isNull($("#emailCode").val(), "emailCode")){
			$.ajax({
				url: path + "/safeLevel/authEmail.html",
				type: "post",
				dataType: "json",
				data: {
					"sendTarget": $("#newEmail").val(),
					"verifyCode": $("#emailCode").val()
				},
				success: function(data){
					if(data["success"]){
						AlertDialog.tip("认证成功");
						setTimeout(function(){
							location.reload();
						},2000)
					}else{
						AlertDialog.tip(data["msg"]);
					}
				},
				error: function(request){
					console.log("获取认证电子邮箱信息异常");
				}
			})
		}
	}
}