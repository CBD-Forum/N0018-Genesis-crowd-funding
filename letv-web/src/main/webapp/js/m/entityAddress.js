$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	showProvince();
	addNewAddress();
	$("#saveBtn").click(saveAddress);
});
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
									AlertDialog.tip("添加成功");
									setTimeout(function(){
										history.go(-1);
									},2000);
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