if(userId == "null" || userId == null){
	window.location.href = path + "/common/m-index.html";
}
$(function(){
	//返回上一页
	$(".back").click(function(){
		history.go(-1);
	});
	//原始密码
	$("#pass").blur(function(){
		if($("#pass").val() == ""){
			AlertDialog.show("原始密码不能为空", "pass",-5, 120);
			return false;
		}
	});
	/*新密码 键盘弹起事件*/
	$("#newPass").keyup(function(){
		MValify.passStrength($(this).val());
	}).blur(function(){
		MValify.password($(this).val(),"newPass");
		return false;
	});
	//确认密码
	$("#okPass").blur(function(){
		MValify.rePass($("#newPass").val(),$(this).val(),"okPass");
		return false;
	});
	$("#savePass").click(updatePassword);
	
});

/*修改密码*/
function updatePassword(){
	if(isNull($("#pass").val(),"pass","原始密码不能为空",-5,120)){
		if(MValify.password($("#newPass").val(),"newPass")){
			if(MValify.rePass($("#newPass").val(),$("#okPass").val(),"okPass")){
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
								window.location.href = path + "/common/m-myCenter.html";
							},1000);
						}else{
							AlertDialog.tip("保存失败！");
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