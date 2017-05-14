if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-login.html";
}
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	if(userId == "null"){ //未登录
		$("#myCenter").attr("href",path+"/common/m-login.html");
	}else{
		$("#myCenter").attr("href",path+"/common/m-myCenter.html");
	}
	$("#newAddBtn").click(function(){
		//清空表单数据
		$("#postUser").val("");
		$("#postPhone").val("");
		$("#postDetail").val("");
		$("#postCode").val("");
		
		var vname = $("#addNewAddress").attr("vname");
		if(vname == "no"){
			showProvince();
			$("#addNewAddress").show('slow');
			$("#addNewAddress").attr("vname","yes");
		}else if(vname == "yes"){
			$("#addNewAddress").hide('slow');
			$("#addNewAddress").attr("vname","no");
		}
		$("#addressTip").css("visibility","hidden");
		$("#saveBtn").unbind().click(saveAddress);
	});
	getAddressList();//收货地址列表
	addNewAddress();
});
//查询收货地址
function getAddressList(){
	var aArr = [], aStr = '', l;
	$.ajax({
		url: path + "/address/getPostAddressList.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				AlertDialog.mtip(data["msg"]);
				return false;
			}
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			for(var i=0;i<l;i++){
				aArr.push('<li>');
				aArr.push('<div class="div1"><span>'+data[i]["postUser"]+'</span><span class="fr">'+data[i]["mobile"]+'</span></div>');
				aArr.push('<p>'+null2value(data[i]["provinceName"])+'市'+null2value(data[i]["cityName"])+'区'+data[i]["adressDetail"]+'</p>');
				if(data[i]["isDefault"] == "1"){
					aArr.push('<p style="color:red;">（默认地址）</p>');
				}
				aArr.push('<p class="clearfix"><a class="btn fl" href="javascript:void(0);" aId="'+data[i]["id"]+'" onclick="modifyAddress(event)">修改</a><a class="btn fr" href="javascript:void(0);" aId="'+data[i]["id"]+'" onclick="deleteAddress(event)">删除</a></p>');
				aArr.push('</li>');
			}
			aStr = aArr.join("");
			$("#addressList").html(aStr);
		},
		error: function(request){
			console.log("获取收货地址异常");
		}
	});
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

//检验是否为空
function isNull(str, id,tipStr){
	if(!str){
		$("#"+id).text(tipStr).css("visibility","visible");
		return false;
	}
	$("#"+id).css("visibility","hidden");
	return true;
}
//添加新地址
function addNewAddress(){
	//清空表单数据
	$("#postUser").val("");
	$("#postPhone").val("");
	$("#postDetail").val("");
	$("#postCode").val("");
	
	
	$("#postUser").blur(function(){
		isNull($(this).val(), "addressTip","请输入收件人姓名");
	});
	$("#postPhone").blur(function(){
		MValify.phone($(this).val(), "addressTip");
	});
	$("#postDetail").blur(function(){
		isNull($(this).val(), "addressTip","请输入详细地址");
	});
	$("#postCode").blur(function(){
		MValify.postCode($(this).val(), "addressTip","请输入邮编");
	});
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
			pArr.push('<option value="">请选择城市</option>');
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
			cArr.push('<option value="">请选择地区</option>');
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
//保存收货地址
function saveAddress(){
	if(isNull($("#postUser").val(), "addressTip","请输入收件人姓名")){
		if(MValify.phone($("#postPhone").val(), "addressTip")){
			if(isNull($("#postProvince").val(), "addressTip","请选择城市")){
//				if(isNull($("#postCity").val(), "addressTip","请选择地区")){
					if(isNull($("#postDetail").val(), "addressTip","请输入详细地址")){
						if(MValify.postCode($("#postCode").val(), "addressTip")){
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
									AlertDialog.mtip("添加成功");
									$("#addNewAddress").hide('slow');
									$("#addNewAddress").attr("vname","no");
									getAddressList();
								},
								error: function(request){
									console.log("添加收货地址异常");
								}
							});
						}
					}
//				}
			}
		}
	}
}

//修改地址
function modifyAddress(event){
	var obj = event.srcElement || event.target;
	var id = $(obj).attr("aId");
	AlertDialog.confirm(modifyAdd, null, "您确定要修改吗?", "确定", "取消");
	function modifyAdd(){
		$('html,body').animate({
			scrollTop : '0px'
		}, 800);
		$.ajax({
			url: path + "/address/getPostAddressById.html",
			type: "post",
			dataType: "json",
			data: {"id": id},
			success: function(data){
				if(data["success"]){
					//清空表单数据
					$("#postUser").val(data["msg"]["postUser"]);
					$("#postPhone").val(data["msg"]["mobile"]);
					$("#postDetail").val(data["msg"]["adressDetail"]);
					$("#postCode").val(data["msg"]["postCode"]);
					$("#postUser").blur(function(){
						isNull($(this).val(), "addressTip","请输入收件人姓名");
					});
					$("#postPhone").blur(function(){
						MValify.phone($(this).val(), "addressTip");
					});
					$("#postDetail").blur(function(){
						isNull($(this).val(), "addressTip","请输入详细地址");
					});
					$("#postCode").blur(function(){
						MValify.postCode($(this).val(), "addressTip","请输入邮编");
					});
					
					$("#province").val(showProvince(data["msg"]["province"]));
					$("#city").val(showCity(data["msg"]["province"],data["msg"]["city"]));
					$("input[name='pDefault']").each(function(){
						if($(this).val() == data["msg"]["isDefault"]){
							$(this).prop("checked",true);
						}
					});
//					if(data["msg"]["isDefault"] == 0){
//						$("#aDefault").prop("checked",false);
//					}else{
//						$("#aDefault").prop("checked",true);
//					}
					$("#saveBtn").unbind("click").click(updateAddress);
					$("#addNewAddress").show('slow');
				}
			},
			error: function(request){
				console.log("获取城市信息异常");
			}
		});
	}
	function updateAddress(){
		if(isNull($("#postUser").val(), "addressTip","请输入收件人姓名")){
			if(MValify.phone($("#postPhone").val(), "addressTip")){
				if(isNull($("#postProvince").val(), "addressTip","请选择城市")){
					if(isNull($("#postCity").val(), "addressTip","请选择地区")){
						if(isNull($("#postDetail").val(), "addressTip","请输入详细地址")){
							if(MValify.postCode($("#postCode").val(), "addressTip")){
								$.ajax({
									url: path + "/address/updatePostAddress.html",
									type: "post",
									dataType: "json",
									data:{
										"id": id,
										"province":$("#postProvince").val(),
										"city":$("#postCity").val(),
										"adressDetail":$("#postDetail").val(),
										"postCode":$("#postCode").val(),
										"postUser":$("#postUser").val(),
										"mobile":$("#postPhone").val(),
										"isDefault":$("#isDefaultP input:checked").val()
									},
									success: function(data){
										if(data["success"]){
											getAddressList();
											AlertDialog.mtip("修改成功！");
											$("#addNewAddress").hide('slow');
											$("#addNewAddress").attr("vname","no");
											getAddressList();
//											$("#address_title").html("+新增收货地址");
//											showProvince();
//											$("#adressDetail").val("");
//											$("#postCode").val("");
//											$("#postUser").val("");
//											$("#mobile").val("");
//											$("#aDefault").attr("default","0");
//											$("#aDefault").prop("checked",false);
//											$("#saveAddress").unbind("click").click(addAddress);
										}else{
											AlertDialog.mtip("修改失败！");
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

//删除地址
function deleteAddress(){
	var obj = event.srcElement || event.target;
	var id = $(obj).attr("aId");
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
					AlertDialog.mtip("删除成功！");
				}else{
					AlertDialog.mtip("删除失败！");
					return false;
				}
			},
			error: function(request){
				console.log("删除收货地址异常");
			}
		});
	}
}