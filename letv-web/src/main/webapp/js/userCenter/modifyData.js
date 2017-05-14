if(siteUserId == "null"){
	window.location.href = path + "/common/index.html" ;
}
$(function(){
//	getUserInfo();
	$("input").keyup(function(){
		if($(this).val().indexOf(" ")>=0){
			$(this).val($(this).val().substring(0,$(this).val().length-1));
		}
	})
	getUserInfoDetail();
	
	$("#nickName").blur(function(){
		isNull($(this).val(),"nickName","昵称不能为空",10,30);
	});
	$("#submitInfo").click(modifyUserInfo);
});

//获取个人信息
//function getUserInfo(){
//	$.ajax({
//		url: path + "/crowdfundUserCenter/getCrowdfundUserDetail.html",
//		type: "post",
//		dataType: "json",
//		success: function(data){
//			if(!data["success"]){
//				return false;
//			}
//			data = data["msg"];
//			if(data["photo"]){
//				$("#centerUserPhoto").attr("src", cv["fileAddress"] + data["photo"]); //头像
//			}else{
//				$("#centerUserPhoto").attr("src", path + "/images/defaultPhoto.png"); //头像
//			}
//			if(data["realName"]){
//				$("#userRealName").text(data["realName"]); //真实姓名
//			}else{
//				$("#userRealName").text(data["userId"]); //真实姓名
//			}
//		},error: function(request){
//			console.log("获取个人信息异常");
//		}
//	});
//}
//获取个人信息异常
function getUserInfoDetail(){
	$.ajax({
		url: path + "/crowdfundUserCenter/selectCrowdfundUserDetail.html",
		type: "post",
		dataType: "json",
		success: function(data){
			data = data["user"];
			if(data["photo"]){
				$("#imghead").attr("src", cv["fileAddress"] + data["photo"]); //头像
			}else{
				$("#imghead").attr("src", path + "/images/defaultPhoto.png"); //头像
			}
			$("#nickName").val(data["nickName"]);
			$("#company").val(data["company"]);
			$("#position").val(data["position"]);
			$("#selfDetail").val(data["selfDetail"]);
			$("#selfDetailDiv").text(data["selfDetail"]);
			$("#loan_logo_url").val(data["photo"]);
		},
		error: function(request){
			console.log("获取个人信息异常");
		}
	});
}
//修改用户信息
function modifyUserInfo(){
	$("#submitInfo").text("保存");
	$("#nickName,#company,#position").removeAttr("readonly").removeClass("bor");
	$("#selfDetail").show();
	$("#selfDetailDiv").text($("#selfDetail").val()).hide();
	ecreateWebUploader("image_file", "imghead", "loan_logo_url", "imgheadLi");//上传头像
	$("#submitInfo").unbind("click").click(updateUserInfo);
}
//保存用户信息
function updateUserInfo(){
	
	if($("#selfDetail").val().length>20){
		AlertDialog.tip("一句话简介，文字不超过20个！");
		return false;
	}
	if(isNull($("#nickName").val(),"nickName","昵称不能为空",10,30)){
		if($("#nickName").val().length>1 && $("#nickName").val().length<9){
			$.ajax({
				url: path + "/user/updateUserInfo.html",
				type: "post",
				dataType: "json",
				data:{
					"photo":$("#loan_logo_url").val(),
					"nickName":$("#nickName").val(),
					"company":$("#company").val(),
					"position":$("#position").val(),
					"selfDetail":$("#selfDetail").val()
				},
				success: function(data){
					if(!data["success"]){
						return false;
					}
					$("#nickName,#company,#position").attr("readonly","disabled").addClass("bor");;
					$("#selfDetail").hide();
					$("#selfDetailDiv").text($("#selfDetail").val()).show();
					$("#submitInfo").text("修改").unbind("click").click(modifyUserInfo);
					getUserInfoDetail();
					AlertDialog.tip("修改成功！");
					setTimeout(function(){
						window.location.reload();
					},2000);
				},error: function(request){
					console.log("修改用户信息异常");
				}
			});
		}else{
			AlertDialog.show("昵称长度2~8个字符", "nickName", 10,30);
		}
	}
}
//验证用户名
function checkUsername(str,id){
	var result = false;
	return Valify.username(str, result, id);
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
			AlertDialog.tip("文件超过5M，请重新上传");
			//AlertDialog.show("文件超过5M，请重新上传", pickId, -90, 60);
		}else if("Q_TYPE_DENIED"==handler){
			AlertDialog.tip("图片类型错误");
			//AlertDialog.show("文件类型错误", pickId, -90, 60);
		}else if("Q_EXCEED_NUM_LIMIT"==handler){
			AlertDialog.tip("添加的图片数量超出");
			//AlertDialog.show("添加的文件数量超出", pickId, -90, 60);
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
