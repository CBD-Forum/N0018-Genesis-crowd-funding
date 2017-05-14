if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	getUserInfo();
	showProvince();//获取省份
	$("#submitInfo").click(updateUserInfo);
	//修改性别
	var $sex = $("#sex input[name='sex']");
	$.each($sex,function(k,v){
		$(v).click(function(){
			$("#sex").attr("vsex",$(v).attr("vsex"));
		});
	});
});
function getUserInfo(){
	$.ajax({
		url: path + "/user/getUserInfo.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			if(data["photo"]){
				$("#imghead").attr("src", cv["fileAddress"] + data["photo"]).css("border-radius","50%"); //头像
			}else{
				$("#imghead").attr("src", path + "/images/defaultPhoto.png").css("border-radius","50%"); //头像
			}
			$("userId").val(data["userId"]);
			$.ajax({
				url: path + "/userAuth/getCrowdfundUserByUserId.html",
				type: "post",
				dataType: "json",
				success: function(Udata){
					if(!Udata["success"]){
						$("#realName").hide();
						return false;
					}else{
						if(Udata["msg"][0]["userLevel"] == "authed" && data["realName"]){
							$("#realName").val(data["realName"]).show();
						}
					}
				}
			});
			$("#mobile").text(data["mobile"]);
			$("#sex").attr("vsex",data["sex"]);
			if(data["sex"] == "M"){
				$("#boy").prop("checked","checked");
			}else if(data["sex"] == "F"){
				$("#girl").prop("checked","checked");
			};
			showProvince(data["province"]);
			showCity(data["province"], data["city"]);
			$("#province").val(data["province"]);
			$("#city").val(data["city"]);
			$("#selfDetail").val(data["selfDetail"]);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//修改用户信息
function updateUserInfo(){
//	if(MValify.realName($("#realName").val(), "realName")){
		if(checkProCity("province",-8,80)){
			if(checkProCity("city",-8,100)){
				if(isNull($("#selfDetail").val(),"selfDetail","个人说明不能为空",-50,180)){
					$.ajax({
						url: path + "/user/updateUserInfo.html",
						type: "post",
						dataType: "json",
						data:{
//							"realName":$("#realName").val(),
							"sex":$("#sex").attr("vsex"),
							"province":$("#province").val(),
							"city":$("#city").val(),
							"selfDetail":$("#selfDetail").val()
						},
						success: function(data){
							if(!data["success"]){
								return false;
							}
							AlertDialog.tip("修改成功！");
							setTimeout(function(){
								window.location.href = path + "/common/m-myCenter.html";
							},2000);
							
						},error: function(request){
							console.log("修改用户信息异常");
						}
					});
				}
			}
		}
//	}
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
		},
		error: function(request){
			console.log("获取城市信息异常");
		}
	});
}
//检测省份
function checkProCity(id,left,top){
	if(!$("#" + id).val()){
		AlertDialog.show("请选择"+$("#" + id).attr("nullMessage"), id, left, top);
		return false;
	}
	AlertDialog.hide();
	return true;
}
//验证用户名
function checkUsername(str,id){
	var result = false;
	return MValify.username(str, result, id);
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
	}else{
		AlertDialog.hide();
		return true;
	}
}
