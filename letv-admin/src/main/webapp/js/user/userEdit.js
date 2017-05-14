$(function(){
	//获取项目所在省
    getProvice("s_provice", "s_city");
	//项目所属省份改变，城市改变
	$("#s_provice").change(function(){
		getCitys($(this).val(), "s_city"); 
	});
	//县
	$("#s_city").change(function(){
		getCitys($(this).val(), "s_county"); 
	});
	
	$('#add').width(bodyWidth-173);
	$('#add').height(bodyHeight-230);
	
	if (id) {
		$.ajax({
			type : "POST",
			url : path + '/user/getById.html',
			dataType:'json',
			data : {
				'id' : id
			},
			success : function(data) {
				$('#userForm').form('load', data);
				getCitys(data.province, "s_city",function(){
					$('#s_city').val(data.city);
				});
				getCitys(data.city, "s_county",function(){
					$('#s_county').val(data.county);
				});
				if (data.photo) {
					$('#photoDiv .filelist').html('<img src="'+fileUrl+data.photo+'" style="width:200px; height:200px;"/>');
				}
			}
		});
	}
	
	$("#userForm").validate({
		errorPlacement : function(error, element) {
			if ( element.is(":radio") ){
				error.appendTo (element.parent());
			}else if (element.is(":checkbox") ){
		    	error.appendTo (element.parent());
		    }else{
		        error.appendTo(element.parent());
			}
    	},
		rules : {
			email : {
				maxlength:50,
				email: true
			},
			realName:{
				maxlength:8
			},
			nickName:{
				maxlength:8
			},
			qqNo:{
				maxlength:12
			},
			weiboNo:{
				maxlength:25
			},
			mobile:{
				maxlength:11,
				mobile:true
			},
			tel:{
				maxlength:13
			},
			certificateNo:{
				maxlength:18
			},
			homeAddress:{
				maxlength:50
			},
			emergencyContact:{
				maxlength:8
			},
			emergencyPhone:{
				maxlength:11
			},
			emergencyRelation:{
				maxlength:10
			},
			postCode:{
				maxlength:6
			},
		},
		messages : {
		},
        submitHandler:function(form){$('#userForm').form('submit', {
			url : path + '/user/modify.html',
			queryParams: {
				"id":id,
				'userId':$('#userId').val()
			},
			success : function(data) {
				var obj = $.parseJSON(data);
				if (obj.success) {
					$.messager.alert('提示','保存成功！<a href="'+path+'/user.userlist.html">返回列表</a>');   
				}
				if (!obj.success) {
					if (obj.msg) {
						$.messager.alert('提示', obj.msg);
					}
				}
			}
		});}
    }); 
	$('#saveBtn').click(function() {
		$('#userForm').submit();
	});
	
	createWebUploader('photoBtn','photoDiv','photo');
});


function createWebUploader(pickId,showId,setValueId){
	var uploader=WebUploader.create({
		auto: true,
	    pick: {
	        id: '#'+pickId,
	        multiple: false,
	        label: '点击选择图片'
	    },
	    accept: {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png',
	        mimeTypes: 'image/*'
	    },
	    // swf文件路径
	    swf:path+'/js/common/webuploader/Uploader.swf',
	    chunked: true,
	    compress :false,
	    server: path+'/uploadImage.html',
	    fileNumLimit: 300,
	    fileSizeLimit: 5 * 1024 * 1024,    // 200 M
	    fileSingleSizeLimit: 1 * 1024 * 1024    // 50 M
	});
	
	uploader.on('uploadSuccess', function (file, ret) {
//        var $file = $('#' + file.id);
        try {
            var responseText = (ret._raw || ret),
                json = $.parseJSON(responseText);
            if (json.state == 'SUCCESS') {
            	$('#'+showId+' .filelist').html('<img src="'+fileUrl+json.url+'" style="width:200px; height:200px;"/>');
            	$('#userForm input[name="'+setValueId+'"]').val(json.url);
            } else {
//                $file.find('.error').text(json.state).show();
            }
        } catch (e) {
//            $file.find('.error').text(lang.errorServerUpload).show();
        }
    });
	
	return uploader;
}