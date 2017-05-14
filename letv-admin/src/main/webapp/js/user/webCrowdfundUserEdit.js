$(function(){
	
	$('#add').width(bodyWidth-173);
	$('#add').height(bodyHeight-230);
	
	
	if (id) {
		$.ajax({
			type : "POST",
			url : path + '/user/getLoanUserDetail.html',
			dataType:'json',
			data : {
				'userId' : id
			},
			success : function(data) {
				$('#userForm').form('load', data.userStuff);
				
				if(data.userStuff['isInvest']=="1"){
					$("input[name='hi']").eq(0).prop("checked","checked");
				}else{
					$("input[name='hi']").eq(1).prop("checked","checked");
				}
				if(data.userStuff['hasInvestPlan']=="1"){
					$("input[name='hj']").eq(0).prop("checked","checked");
				}else{
					$("input[name='hj']").eq(1).prop("checked","checked");
				}
				
				if(data.userStuff['userIdentity'] == 'person'){
					$("input[name='igj']").eq(0).prop("checked","checked");
				}else{
					$("input[name='igj']").eq(1).prop("checked","checked");
				}
				
				if (data.userStuff['handCardFront']) {
					$('#photoDiv .filelist').html('<img src="'+fileUrl+data.userStuff['handCardFront']+'" style="width:200px; height:200px;"/>');
				}
			}
		});
	}
	
	$("#userForm").validate({
        submitHandler:function(form){$('#userForm').form('submit', {
			url : path + '/user/modifyUserGrade.html',
			queryParams: {
				'userId':$('#userId').val(),
				'isInvest':$("input[name='hi']:checked").val(),
				'hasInvestPlan':$("input[name='hj']:checked").val(),
				'userIdentity':$("input[name='igj']:checked").val()
			},
			success : function(data) {
				var obj = $.parseJSON(data);
				if (obj.success) {
					$.messager.alert('提示','保存成功！<a href="'+path+'/user.webuserlist.html">返回列表</a>');   
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
	
	createWebUploader('photoBtn','photoDiv','handCardFront');
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