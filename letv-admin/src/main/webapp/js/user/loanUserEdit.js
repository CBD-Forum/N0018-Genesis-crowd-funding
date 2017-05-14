$(function(){
	$('#tt').tabs({
		height:bodyHeight-190,
	    onSelect:function(title){   
	    }   
	});
	
	
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
	
	$('#add').width(bodyWidth-165);
	$('#add').height(bodyHeight-190);
	
	if (userId) {
		$.ajax({
			type : "POST",
			url : path + '/user/getLoanUserById.html',
			dataType:'json',
			data : {
				'userId' : userId
			},
			success : function(data) {
				$('#userForm').form('load', data.user);
				getCitys(data.user.province, "s_city",function(){
					$('#s_city').val(data.user.city);
				});
				getCitys(data.user.city, "s_county",function(){
					$('#s_county').val(data.user.county);
				});
				
				if (data.user.photo) {
					$('#photoDiv .filelist').html('<img src="'+fileUrl+data.user.photo+'" style="width:200px; height:200px;"/>');
				}
				
				$('#detailForm input[name="userId"]').val(data.user.userId);
				$('#detailForm').form('load', data.detail);
				
				$('#authFileForm input[name="userId"]').val(data.user.userId);
				$('#authFileForm').form('load', data.authFile);
				
				$('#authFileForm .x-form-item').each(function(index){
					var name = $(this).find('input').attr('name');
					if (data.authFile[name]) {
						var pics = data.authFile[name].split(',');
						for ( var i = 0; i < pics.length; i++) {
							if (pics[i]!='') {
								loadPhoto(data.authFile.id+name+i,pics[i], 'authFileForm #'+name+'Div',name);
							}
						}
					}
				});
				
				$('#financialForm input[name="userId"]').val(data.user.userId);
				$('#financialForm').form('load', data.workFinancial);
			}
		});
	}
	
	$('#saveBtn').click(function() {
		$.ajax({
			type : "POST",
			url : path + '/user/modifyLoanUser.html',
			data : {
				'userId' : userId,
				'userStr':JSON.stringify(serializeObject($('#userForm'))),
				'borrowerDetailStr':JSON.stringify(serializeObject($('#detailForm'))),
				'borrowerAuthFileStr':JSON.stringify(serializeObject($('#authFileForm'))),
				'borrowerWorkFinancialStr':JSON.stringify(serializeObject($('#financialForm')))
			},
			success : function(data) {
				if (data.success) {
					$.messager.alert('提示','保存成功！<a href="'+path+'/user.loanUser.html">返回列表</a>');   
				}else{
					$.messager.alert('提示',data.msg);
				}
				
			}
		});
	});
	
	//头像
	createOneWebUploader('photoBtn','photoDiv','photo');
	
	$('#authFileForm .x-form-item').each(function(){
		var name = $(this).find('input').attr('name');
		createWebUploader(name,'authFileForm');
	});
	
});


/**
 * 单张图片上传
 * @param pickId
 * @param showId
 * @param setValueId
 * @returns
 */
function createOneWebUploader(pickId,showId,setValueId){
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
	    fileSingleSizeLimit: 1 * 1024 * 1024
	});
	
	uploader.on('uploadSuccess', function (file, ret) {
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
function createWebUploader(name,formId){
	$('#'+name+'Btn').click(function(){
		var iframeStr='<iframe id="'+name+'Iframe" class="uploadIframe" src="'+path+'/js/common/ueditor/dialogs/image/uploadImage.html?callback=picCallback" width="690" height="400" frameborder="0" scrolling="no"></iframe>';
		$(iframeStr).dialog({
			title: "多图上传",
			height: 400,
			width: 700,
			modal : true,
			onClose: function () {
	            $('#'+name+'Iframe').remove();
	        }
		});
	});
}

function picCallback(pics){
	var operateId='';
	$('#authFileForm .x-form-item').each(function(){
		var name = $(this).find('input').attr('name');
		if ($('#'+name+'Iframe').length>0) {
			operateId=name;
		}
	});
	for ( var i = 0; i < pics.length; i++) {
		var picStr = '<div class="photo"><input type="hidden" name="'+operateId+'" value="'+pics[i].src+'" />'+
					 	'<div id="'+pics[i].title.substring(0,pics[i].title.indexOf('.'))+'">'+
					 		'<img src="'+fileUrl+pics[i].src+'">'+
					 		'<div id="testPicOperate" class="operate">'+
					 			'<span class="cancel"></span>'+
					 		'</div>'+
					 	'</div>'+
					 	'<div class="text" title="'+pics[i].alt+'">'+pics[i].alt+'</div>'+
					 '</div>';
		$(picStr).appendTo('#'+operateId+'Div .filelist');
		$(picStr).slideDown(1000);
		$('#'+operateId+'Iframe').dialog('close');
	}
}

function loadPhoto(id,url, renderId,name){
	var picStr = '<div class="photo">'+
					'<input type="hidden" name="'+name+'" value="'+url+'"/>'+
				 	'<div id="'+id+'">'+
				 		'<img src="'+fileUrl+url+'">'+
				 		'<div id="testPicOperate" class="operate">'+
				 			'<span class="cancel"></span>'+
				 		'</div>'+
				 	'</div>'+
				 '</div>';

	$(picStr).appendTo('#'+renderId+' .filelist');
	
	$('#'+id).mouseenter(function(){
		$(this).find('.operate').stop().animate({height: 30});
		$(this).find('.cancel').stop().animate({height: 24});
	}).mouseleave(function(){
		$(this).find('.operate').stop().animate({height: 0});
		$(this).find('.cancel').stop().animate({height: 0});
	});
	
	$('#'+id).find('.cancel').click(function(){
		removePic(id,url);
	});
}

function removePic(id,url){
	// 删除
	$.messager.confirm('提示', '您确定要删除此图片吗？', function(r) {
		if (r) {
			//需要修改的字段
			var modifyField = $('#'+id).parent().parent().find('input[type="hidden"]').attr('name');
			$.ajax({
				type : "POST",
				dataType:'json',
				url : path + '/user/removeLoanUserPic.html',
				data : {
					'field':modifyField,
					'userId':userId,
					'url': url
				},
				success : function(data) {
					if(data.success){
						$('#'+id).parent().parent().slideUp(1000,function(){
							$('#'+id).parent().parent().remove();
						});
						$.messager.alert('提示', '删除成功');
					}
				}
			});
		}
	});

}
