var loanNo = window.location.search.substring(window.location.search.indexOf("loanNo=")+7,window.location.search.indexOf("&backNo"));
var backNo = window.location.search.substring(window.location.search.indexOf("backNo=")+7,window.location.search.length);
$(function(){
	getAddress();
	getDetail();
	$("#supportBtn").click(supportSub);
});
//获取收货地址
function getAddress(){
	var aArr = [], aStr = '', l;
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
			if(l == 0){
				aStr = '<div class="an"><span class="c" onclick="addNewAddress();">添加新地址</span></div>';
			}else{
				for(var i=0;i<l;i++){
					provinece = data[i]["provinceName"] ? data[i]["provinceName"] : "";
					city = data[i]["cityName"] ? data[i]["cityName"] : "";
					aArr.push('<div class="add_list">');
					if(data[i]["isDefault"] == "1"){
						aArr.push('<input type="radio" name="addRadio" checked="true" addNo="'+data[i]["addressNo"]+'"/>');
					}else{
						aArr.push('<input type="radio" name="addRadio" addNo="'+data[i]["addressNo"]+'" />');
					}
					aArr.push('<label>');
					aArr.push('<span>'+provinece+city+data[i]["adressDetail"]+'</span>');
					aArr.push('<span>'+data[i]["postUser"]+'</span>');
					aArr.push('<span>'+data[i]["mobile"]+'</span>');
					aArr.push('<span class="c" aid="'+data[i]["id"]+'" onclick="modifyAddress(event)">修改</span><span class="c" aid="'+data[i]["id"]+'" onclick="deleteAddress(event)">删除</span></label></div>');
				}
				aArr.push('<div class="an"><span class="c" onclick="addNewAddress();">添加新地址</span></div>');
				aStr = aArr.join("");
			}
			$("#address").html(aStr);
		},
		error: function(request){
			console.log("获取收货地址异常");
		}
	});
}
//添加新地址
function addNewAddress(id){
	$("#addAddressBox").show().animate({
		height: "310px"
	},800);
	if(id){
		$("#addAddressBox").attr("aid",id);
	}else{
		$("#addAddressBox").attr("aid","");
	}
	//清空表单数据
	$("#postUser").val("");
	$("#postPhone").val("");
	$("#postDetail").val("");
	$("#postCode").val("");
	showProvince();
	
	$("#postUser").blur(function(){
		isNull($(this).val(), $(this).attr("id"));
	});
	$("#postPhone").blur(function(){
		Valify.phone($(this).val(), $(this).attr("id"), "tip");
	});
	$("#postDetail").blur(function(){
		isNull($(this).val(), $(this).attr("id"));
	});
	$("#postCode").blur(function(){
		Valify.postCode($(this).val(), $(this).attr("id"));
	});
	//取消保存按钮事件
	$("#canelSaveBtn").click(function(){
		$("#addAddressBox").animate({
			height: "0px"
		},800);
		setTimeout(function(){
			$("#addAddressBox").hide();
		},700);
	});
	//保存按钮事件
	if(id){
		$("#saveBtn").unbind("click").click(updateAddress);
	}else{
		$("#saveBtn").unbind("click").click(saveAddress);
	}
}
//获取省份和城市
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
			pArr.push('<option value="">请选择</option>');
			for(var i=0;i<l;i++){
				pArr.push('<option value="'+data[i]["id"]+'">'+data[i]["shortName"]+'</option>');
			}
			pStr = pArr.join("");
			$("#postProvince").html(pStr);
			//点击省份后，根据省份id查询城市
			$("#postProvince").change(function(){
				showCity($(this).val());
			});
			if(id){
				$("#postProvince").val(id);
			}
		},
		error: function(request){
			console.log("获取省份信息异常");
		}
	});
}
//删除收货地址
function deleteAddress(event){
	var obj = event.srcElement || event.target;
	var id = $(obj).attr("aid");
	AlertDialog.confirm(modifyDelete, null, "您确定要删除吗?", "确定", "取消");
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
//检验是否为空
function isNull(str, id){
	if(!str){
		AlertDialog.show($("#" + id).attr("nullMessage"), id, 0, 30);
		return false;
	}
	AlertDialog.hide();
	return true;
}
//保存收货地址
function saveAddress(){
	if(isNull($("#postUser").val(), "postUser")){
		if(Valify.phone($("#postPhone").val(), "postPhone", "tip")){
			if(isNull($("#postProvince").val(), "postProvince")){
				if(isNull($("#postCity").val(), "postCity")){
					if(isNull($("#postDetail").val(), "postDetail")){
						if(Valify.postCode($("#postCode").val(), "postCode")){
							$.ajax({
								url: path + "/address/savePostAddress.html",
								type: "post",
								dataType: "json",
								data: {
										"postUser": $("#postUser").val(),
										"mobile": $("#postPhone").val(),
										"province": $("#postProvince").val(),
										"city": $("#postCity").val(),
										"adressDetail": $("#postDetail").val(),
										"postCode": $("#postCode").val(),
										"isDefault": $("#isDefaultP input:checked").val()
									},
								success: function(data){
									if(!data["success"]){
										return false;
									}
									AlertDialog.tip("添加成功");
									setTimeout(function(){
										window.location.reload();
									},2000);
								},
								error: function(request){
									console.log("添加收货地址异常");
								}
							});
						}
					}
				}
			}
		}
	}
}
//获取修改收货地址数据
function modifyAddress(event){
	var obj = event.srcElement || event.target;
	var aId = $(obj).attr("aid");
	$.ajax({
		url: path + "/address/getPostAddressById.html",
		type: "post",
		dataType: "json",
		data: {"id": aId},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			addNewAddress(aId);
			data = data["msg"];
			$("#postUser").val(data["postUser"]);
			$("#postPhone").val(data["mobile"]);
			showProvince(data["province"]);
			showCity(data["province"], data["city"]);
			$("#postDetail").val(data["adressDetail"]);
			$("#postCode").val(data["postCode"]);
			$("#isDefaultP input[value=\""+data["isDefault"]+"\"]").click();
		},
		error: function(request){
			console.log("查询收货地址详情异常");
		}
	});
}
//修改收货地址数据
function updateAddress(){
	if(isNull($("#postUser").val(), "postUser")){
		if(Valify.phone($("#postPhone").val(), "postPhone", "tip")){
			if(isNull($("#postProvince").val(), "postProvince")){
				if(isNull($("#postCity").val(), "postCity")){
					if(isNull($("#postDetail").val(), "postDetail")){
						if(Valify.postCode($("#postCode").val(), "postCode")){
							$.ajax({
								url: path + "/address/updatePostAddress.html",
								type: "post",
								dataType: "json",
								data: {
									"id": $("#addAddressBox").attr("aid"),
									"postUser": $("#postUser").val(),
									"mobile": $("#postPhone").val(),
									"province": $("#postProvince").val(),
									"city": $("#postCity").val(),
									"adressDetail": $("#postDetail").val(),
									"postCode": $("#postCode").val(),
									"isDefault": $("#isDefaultP input:checked").val()
								},
								success: function(data){
									if(!data["success"]){
										return false;
									}
									AlertDialog.tip("修改成功");
									setTimeout(function(){
										window.location.reload();
									},2000);
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
function showCity(id, cityId){
	var cArr = [], cStr = '', cl, list;
	$.ajax({
		url: path + "/area/getCity.html",
		type: "post",
		dataType: "json",
		data: {"pid": id},
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
			$("#postCity").html(cStr);
			if(cityId){
				$("#postCity").val(cityId);
			}
		},
		error: function(request){
			console.log("获取城市信息异常");
		}
	});
}
//获取支持详情
function getDetail(){
	$("#supCover").attr("href", path + "/common/projectDetail-m.html?loanNo=" + loanNo + "&type=pro");
	$("#supLoanName").attr("href", path + "/common/projectDetail-m.html?loanNo=" + loanNo + "&type=pro");
	$.ajax({
		url: path + "/crowdfunding/getBackSetList.html",
		type: "post",
		dataType: "json",
		data: {
			"loanNo": loanNo,
			"backNo": backNo
			},
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"]["rows"][0];
			$("#supCover img").attr("src",cv["fileAddress"] + "/" +data["loanLogo"]).attr("alt",data["loanName"]);
			$("#supLoanName").text(data["loanName"]);
			$("#supContent").text(data["backContent"]);
			$("#supAmt").text("￥" + data["amt"]);
			$("#suphAmt").text("￥" + data["amt"]).attr("amt",data["amt"]);
			if(!data["transFee"]){//免运费
				$("#supTransFee").text("免运费");
				$("#suphAmt").text("￥" + data["amt"]).attr("amt",data["amt"]);
			}else{
				$("#supTransFee").text("￥" + data["transFee"]);
				$("#suphAmt").text("￥" + (data["amt"]+data["transFee"])).attr("amt",data["amt"]);
			}
			if(data["backTime"] == 0){//免运费
				$("#supBackTime").text("项目成功结束后立即发送");
			}else{
				$("#supBackTime").text("项目成功结束后"+data["backTime"]+"天发送");
			}
		},
		error: function(request){
			console.log("获取筹资项目详情信息异常");
		}
	});
}
//支持众筹
function supportSub(){
	if($("#address div.add_list").length < 1){
		AlertDialog.tip("请添加收货地址");
		return false;
	}
//	if(Number($("#suphAmt").attr("amt")) > Number($("#supBalance").attr("amt"))){
//		AlertDialog.tip("您的余额不足，请充入");
//		setTimeout(function(){
//			window.location.href = path + "/common/rechange.html";
//		},2000);
//	}
	//给项目发起人留言
	if($("#saleInput").val()){
		$.ajax({
			url: path + "/letter/savePrivateLetter.html",
			type: "post",
			dataType: "json",
			data: {
				"receiveUser": getCookie("supportLoanUser"),
				"sendContent": $("#saleInput").val(),
				"loanNo": loanNo
				},
			success: function(data){
				var expires = new Date(); 
				expires.setTime(expires.getTime() - 1000);
				document.cookie = "supportLoanUser=;path=/;expires=" + expires.toGMTString() + "";
			},
			error: function(request){
				console.log("发送私信异常");
			}
		});
	}
	//提交支持前验证
	$.ajax({
		url: path + "/fundpool/invest/checkBeforeEntitySupport.html",
		type: "post",
		dataType: "json",
		async: false,
		data: {
			"supportAmt": $("#suphAmt").attr("amt"),
			"loanNo": loanNo,
			"backNo": backNo,
			"invstType": "commonInvest"
		},
		success: function(data){
			if(!data["success"]){
				AlertDialog.tip(data["msg"]);
			}else{
				//支持众筹验证通过
				$("#subSupportAmt").val($("#suphAmt").attr("amt"));
				$("#subLoanNo").val(loanNo);
				$("#subBackNo").val(backNo);
				$("#subPostAddressNo").val($("#address").find("input:checked").attr("addno"));
				$("#supFormBtn").click();
			}
		},
		error: function(request){
			console.log("获取用户余额异常");
		}
	});
}