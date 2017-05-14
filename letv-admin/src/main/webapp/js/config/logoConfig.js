$(function(){
	//logo
	createOneWebUploader('logoBtn','logoDiv','logo');
	//登录页
	createOneWebUploader('loginBtn','loginDiv','login');
});


/**
 * 单张图片上传
 * @param pickId
 * @param showId
 * @param setValueId
 * @returns
 */
function createOneWebUploader(pickId,showId,imgType){
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
	    server: path+'/uploadSysImage.html?imgType=' + imgType,
	    fileNumLimit: 300,
	    fileSingleSizeLimit: 1 * 1024 * 1024
	});
	
	uploader.on('uploadSuccess', function (file, ret) {
        try {
            var responseText = (ret._raw || ret),
                json = $.parseJSON(responseText);
            if (json.result == true) {
            	var imgStyle = "";
            	if('logo'==imgType){
            		imgStyle = "height:100px;";
            	} else if ('login'==imgType) {
            		imgStyle = "height:250px;";
            	}
            	$('#'+showId+' .filelist').html('<img src="'+path+'/'+json.url+'?time='+new Date().getTime()+'" style="'+imgStyle+'"/>');
            	$.messager.alert('提示', json.msg);
            } else {
            	$.messager.alert('提示', json.msg,'error');
            }
        } catch (e) {
//        	$.messager.alert('提示', '请求错误','error');
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

