if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	getAddressList();
	showProvince();
	$("#saveAddress").unbind("click").click(addAddress);
//	$("#adressDetail").blur(function(){
//		isNull($(this).val(),"adressDetail","详细地址不能为空",0,30);
//	});
//	$("#postCode").blur(function(){
//		Valify.postCode($(this).val(), "postCode");
//	});
//	$("#postUser").blur(function(){
//		Valify.realName($(this).val(),"postUser");
//	});
//	$("#mobile").blur(function(){
//		Valify.phone($(this).val(),"mobile","123");
//	});
	manageAddress();
	
	$(".address_add").click(function(){
		if($("#addressList>li").length<10){
			$("#newAddredd").show();
			$("#province").val("");
			$("#city").val("");
			$("#county").val("");
			$("#adressDetail").val("");
			$("#postCode").val("");
			$("#postUser").val("");
			$("#mobile").val("");
			$("html,body").animate({
				scrollTop: $("#newAddredd").offset().top -120
			},"800");
		}else{
			AlertDialog.tip("最多只能保存10条有效收货地址！");
		}
	});
	
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
				AlertDialog.tip(data["msg"]);
				return false;
			}
			l = data["msg"]["rows"].length, data = data["msg"]["rows"];
			$("#addTip").html('已经保存了'+l+'条地址，还能保存'+(20-l)+'条地址');
			if(l==0){
				aStr = '<div class="defaultData"><img src="'+path+'/images/letv/moren.png" style=" margin: 30px;"><br/>您还没有添加收货地址哦，可以点击右上角进行添加哦~</div>';
				$("#addressList").html("").html(aStr);
				return false;
			}
			for(var i=0;i<l;i++){
				aArr.push('<li>');
				aArr.push('<p>'+data[i]["postUser"]+'</p>');
				aArr.push('<p>'+null2value(data[i]["provinceName"])+'&nbsp;&nbsp;'+null2value(data[i]["cityName"])+'</p>');
				aArr.push('<p class="p2" style="white-space:nowrap;text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow: hidden; ">'+data[i]["adressDetail"]+'</p>');
				aArr.push('<p>'+data[i]["postCode"]+'</p>');
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
			}
			aStr = aArr.join("");
			$("#addressList").html("").html(aStr);
		},
		error: function(request){
			console.log("获取收货地址异常");
		}
	});
}
function manageAddress(){
	$("#aDefault").click(function(){
		if($(this).attr("default") == 1){
			$(this).attr("default","0");
		}else{
			$(this).attr("default","1");
		}
	});
	$("#adressDetail").blur(function(){
		isNull($(this).val(),"adressDetail","详细地址不能为空",0,30);
	});
	$("#postCode").blur(function(){
		Valify.postCode($(this).val(),"postCode");
	});
	$("#postUser").blur(function(){
		realName($(this).val(),"postUser");
	});
	$("#mobile").blur(function(){
		Valify.phone($(this).val(),"mobile","123");
	});
}
//增加收货地址
function addAddress(){
	if(checkProCity("province")){
		if($("#province").val() != "710000" && $("#province").val() != "810000" && $("#province").val() != "820000"){
			if(!$("#city").val()){
				AlertDialog.show($("#city").attr("nullMessage"), "city", 10, 40);
				return false;
			}
		}
		//if(checkProCity("city")){
			if(isNull($("#adressDetail").val(),"adressDetail","详细地址不能为空",0,30)){
				if(Valify.postCode($("#postCode").val(),"postCode")){
					if(realName($("#postUser").val(),"postUser")){
						if(Valify.phone($("#mobile").val(),"mobile","123")){
							if($("#addressList>li").length>0){
								var addData = {
									"province":$("#province").val(),
									"city":$("#city").val(),
									"county":$("#county").val(),
									"adressDetail":$("#adressDetail").val(),
									"postCode":$("#postCode").val(),
									"postUser":$("#postUser").val(),
									"mobile":$("#mobile").val()
									//"isDefault":$("#aDefault").attr("default")
								}
							}else{
								var addData = {
										"province":$("#province").val(),
										"city":$("#city").val(),
										"county":$("#county").val(),
										"adressDetail":$("#adressDetail").val(),
										"postCode":$("#postCode").val(),
										"postUser":$("#postUser").val(),
										"mobile":$("#mobile").val(),
										"isDefault":"1"
									}
							}
							$.ajax({
								url: path + "/address/savePostAddress.html",
								type: "post",
								dataType: "json",
								data:addData,
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
		//}
	}
	
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
/**
 * 判断值是否为空
 * @param str 元素的值
 * @param id 元素id
 * @param tipStr 提示信息
 * @param topSize 距离上边的距离
 * @param rightSize 距离右边的距离
 */
function isNull(str,id,tipStr,topSize,rightSize){
	if(!str){
		AlertDialog.show(tipStr, id, topSize, rightSize);
		return false;
	}
	AlertDialog.hide();
	return true;
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
			if($("#province").val() != "710000" && $("#province").val() != "810000" && $("#province").val() != "820000"){
				if(!$("#city").val()){
					AlertDialog.show($("#city").attr("nullMessage"), "city", 10, 40);
					return false;
				}
			}
			//if(checkProCity("city")){
				if(isNull($("#adressDetail").val(),"adressDetail","详细地址不能为空")){
					if(Valify.postCode($("#postCode").val(),"postCode")){
						if(realName($("#postUser").val(),"postUser")){
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
			//}
		}
	}
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
function deleteAddress(event){
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
					AlertDialog.tip(data["msg"]);
					return false;
				}
			},
			error: function(request){
				console.log("删除收货地址异常");
			}
		});
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


//真实姓名
function realName(name, id, jump){
	var l, nameCharCode, chNameStr = '', enNameStr = '';
	if(!name){
		AlertDialog.show("收货人姓名不能为空", id, 10, 20, jump);
		return false;
	}
	l = name.length;
	for(var i=0;i<l;i++){
		nameCharCode = name.charAt(i).charCodeAt();
		if((nameCharCode > 255 && nameCharCode < 40869) || nameCharCode == 183){//中文
			chNameStr += name.charAt(i);
		}else if(nameCharCode >= 65 && nameCharCode <= 90 || nameCharCode >= 97 && nameCharCode <= 122 || nameCharCode == 32){//英文
			enNameStr += name.charAt(i);
		}else{
			AlertDialog.show("收货人姓名输入不正确", id, 10, 20, jump);
			return false;
		}
	}
	if(chNameStr == name || enNameStr == name){
		if(chNameStr.charAt(0).charCodeAt() > 255 && (chNameStr.length > 15 || chNameStr.length < 2)){//中文姓名格式
			AlertDialog.show("收货人姓名输入不正确", id, 10, 20, jump);
			return false;
		}else if(((enNameStr.charAt(0).charCodeAt() >= 65 && enNameStr.charAt(0).charCodeAt() <= 90) || (enNameStr.charAt(0).charCodeAt() >= 97 && enNameStr.charAt(0).charCodeAt() <= 122)) && (enNameStr.length > 30 || enNameStr.length < 4)){//英文格式
			AlertDialog.show("收货人姓名输入不正确", id, 10, 20, jump);
			return false;
		}else{
			AlertDialog.hide();
			return true;
		}
	}else{
		AlertDialog.show("收货人姓名输入不正确", id, 10, 20, jump);
		return false;
	}
}