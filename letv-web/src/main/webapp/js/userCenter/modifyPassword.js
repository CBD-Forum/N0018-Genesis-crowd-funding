if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
	//原始密码
	$("#pass").blur(function(){
		if($("#pass").val() == ""){
			AlertDialog.show("原始密码不能为空", "pass", 10, 30);
			return false;
		}
	});
	/*新密码 键盘弹起事件*/
	$("#newPass").keyup(function(){
		Valify.passStrength($(this).val());
	}).blur(function(){
		Valify.password($(this).val(),"newPass");
		return false;
	});
	//确认密码
	$("#okPass").blur(function(){
		Valify.rePass($("#newPass").val(),$(this).val(),"okPass");
		return false;
	});
	$("#savePass").click(updatePassword);
	getUserInfoDetail();
});
//获取个人信息
function getUserInfoDetail(){
	$.ajax({
		url: path + "/crowdfundUserCenter/getCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			if(!data["success"]){
				return false;
			}
			data = data["msg"];
			if(data["photo"]){
				$("#centerUserPhoto").attr("src", cv["fileAddress"] + data["photo"]); //头像
			}else{
				$("#centerUserPhoto").attr("src", path + "/images/defaultPhoto.png"); //头像
			}
			$("#userRealName").text(data["realName"]); //真实姓名
			if(data["loanProvinceName"]){
				$("#address").html(data["loanProvinceName"]+"&nbsp;&nbsp;"+data["loanCityName"]); //地址
			}else{
				$("#address").text("--"); //地址
			}
			$("#createTime").text(data["createTime"].substring(0,10));
			if(data["selfDetail"]){
				$("#selfDetail2").html('<span class="C999">简介：</span>'+data["selfDetail"]).attr("title", data["selfDetail"]);
			}else{
				$("#selfDetail2").html('<span class="C999">简介：</span>他很忙，忙的什么都没来及留下。');
			}
		},error: function(request){
			console.log("获取个人信息异常");
		}
	});
}

/*修改密码*/
function updatePassword(){
	if(isNull($("#pass").val(),"pass","原始密码不能为空",10,30)){
		if(Valify.password($("#newPass").val(),"newPass")){
			if(Valify.rePass($("#newPass").val(),$("#okPass").val(),"okPass")){
				$.ajax({
					url: path + "/user/updateUserPwd.html",
					type: "post",
					dataType: "json",
					data:{
						"password1":$("#newPass").val(),
						"password2":$("#okPass").val(),
						"oldPwd":$("#pass").val()
					},
					success: function(data){
						if(data["success"]){
							AlertDialog.tip("保存成功！");
							setTimeout(function(){
								window.location.href = path + "/common/myCenter.html";
							},1000);
						}else{
							AlertDialog.tip("原始密码不正确，请重新输入");
							return false;
						}
					},
					error: function(request){
						console.log("修改密码异常");
					}
				});
			}
		}
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