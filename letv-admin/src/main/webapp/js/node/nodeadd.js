$(function(){
	$('#nodeDiv').height(bodyHeight-200);
	var ue = UE.getEditor('body');
	if (nodeId && nodeId!='') {
		$.ajax({
			url: path + '/node/getById.html?id=' + nodeId,
			type: "post",
			dataType: "json",
			success: function(data){
				if (data.body) {
					ue.ready(function(){
						ue.setContent(data.body);    
					});	
				}
				$('#nodeType_id').combobox('setValue',data.nodeType);
				$('#nodeForm').form('load', data);
				if (data.thumb) {
					$('#nodeForm input[name="thumb"]').val(data.thumb);
					$('#logoDiv .filelist').html('<img src="'+fileUrl+data.thumb+'" style="width:200px; height:200px;"/>');
				}
			},
			error: function(){
				
			}
		});
	}
	
	
	
	$("#nodeForm").validate({
		rules : {
			code : {
				required:true,
				maxlength:100
			},
			title : {
				required:true,
				maxlength:100
			}
		},
		messages : {
			code : {
				required:'请填写编码'
			},
			title : {
				required:'请填写标题'
			}
		},				
        submitHandler:function(form){
        	var url=path + '/node/save.html';
    		if (nodeId && nodeId!='') {
    			url=path + '/node/modify.html';
    		}
    		$('#nodeForm').form('submit', {
    			url : url+'?nodeType='+$('#nodeType_id').combobox('getValue'),
    			queryParams: {
    				"body": ue.getContent(),
    				"id":nodeId
    			},
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$.messager.alert('提示','保存成功！<a href="'+path+'/node.node.html">返回列表</a>');   
    				}
    				if (!obj.success) {
    					if (obj.msg) {
    						$.messager.alert('提示', obj.msg);
    					}
    				}
    			}
    		});
        }
    });
	
	$('#saveBtn').click(function() {
		$("#nodeForm").submit();
	});
	
	createWebUploader('logoBtn','logoDiv','thumb','nodeForm');
});


function createWebUploader(pickId,showId,setValueId,sForm){
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
	    duplicate:true,
	    fileSingleSizeLimit: 5 * 1024 * 1024
	});
	
	uploader.on('uploadSuccess', function (file, ret) {
//        var $file = $('#' + file.id);
		if(!ret.success){
			if(ret.msg){
				$.messager.alert('提示', ret.msg);
			}
		}
        try {
            var responseText = (ret._raw || ret),
                json = $.parseJSON(responseText);
            if (json.state == 'SUCCESS') {
            	$('#'+showId+' .filelist').html('<img src="'+fileUrl+json.url+'" style="width:200px; height:200px;"/>');
            	$('#'+sForm+' input[name="'+setValueId+'"]').val(json.url);
            } else {
//                $file.find('.error').text(json.state).show();
            }
        } catch (e) {
//            $file.find('.error').text(lang.errorServerUpload).show();
        }
    });
	
	return uploader;
}