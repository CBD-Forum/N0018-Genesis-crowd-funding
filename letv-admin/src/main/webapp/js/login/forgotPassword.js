$(function(){
	$("#resetPasswordForm").validate({
		errorPlacement : function(error, element) {
			error.appendTo(element.parent().find('.inputt').show());
    	},
		rules : {
			employeeNo : "required",
			mobile : {
				required:true,
				mobile:true
			},
			verifyCode : "required",
			password : "required",
			rePassword : {
				required:true,
				equalTo:'#password'
			},
			valiCode : "required"
		},
		messages : {
			employeeNo : "请填写员工号",
			mobile : {
				required:'请填写手机号'
			},
			verifyCode : "请填写手机验证码",
			password : "请填写密码",
			rePassword : {
				required:"请填写确认密码",
				equalTo:'两次输入密码不一致'
			},
			valiCode : "请填写验证码"
		},				
        submitHandler:function(form){
    		$('#resetPasswordForm').form('submit', {
    			url : path+'/admin/resetPassword.html',
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.msg) {
    					$('#msg').html(obj.msg).show();
    					$('#imageStream').attr('src',path+'/securityCodeImage.html?id='+Math.random());
					}
    				if (obj.success) {
    					window.location.href=path+'/view/login.jsp';
    				}
    			}
    		});
        }
    });
	
	$('#confirmBtn').click(function(){
		$('#resetPasswordForm').submit();
	});
	
	$('#imageStream').click(function(){
		this.src=path+'/securityCodeImage.html?id='+Math.random();
	});
});

//获取短信验证码
function getAuthCode(){
	//验证员工号和手机号是否存在，是否匹配
	
	$.ajax({
		url: path+'/admin/validateMobile.html',
		type: "post",
		dataType: "json",
		data: {
			'employeeNo':$('#employeeNo').val(),
			'mobile':$('#mobile').val()
		},
		success: function(data){
			if (data.msg && data.msg!='') {
				$('#msg').html(data.msg).show();
			}
			if (data.success) {
				$('#msg').hide().html('');
				sendAuthCode('verifyCode');
				$.ajax({
					url: path+'/verifycode/sendVerifyCode.html',
					type: "post",
					dataType: "json",
					data: {
						'messageNodeCode':'reset_login_password_by_mobile_for_admin',
						'mobileNumber':$('#mobile').val()
					},
					success: function(data){
						if (data.success) {
							
						}
					},
					error: function(){}
				});
			}
		},
		error: function(){}
	});
}
