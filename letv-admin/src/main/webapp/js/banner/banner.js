var bannerId;
$(function(){
	var columns = [[
					{field:'code',title:'编号',width:120,align:'center',sortable:true},
					{field:'description',title:'描述',width:140,align:'center',sortable:true},
					{field:'operat',width:120,title:'操作',align:'center',formatter:operateData}
				]];
	
	var addBtn = {
	     	text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	$('#code').removeAttr('disabled');
	        	$('#modelDiv').show().dialog({
	        		title: "添加Banner",
	        		height: 540,
	        		width:550,
	        		modal : true,
	        		onClose: function () {
	        			$('#bannerForm').form('clear').form('reset');
	        			$('#uploadImgDiv .filelist').html('');
	                }
	        	});
	        } 
	      };
	var operateBtns=[];
	if ($('#spread_banner_add').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#progectTable').datagrid({
		url: path + '/banner/list.html',
		columns: columns,
		height:bodyHeight-200,
		rownumbers : true,
		pagination: true,
		singleSelect: true,
		toolbar:operateBtns
	});
    
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
    
    //关闭添加窗口
    $('#closeBtn').click(function(){
    	$('#modelDiv').dialog('close');
    });
    
    $('#uploadImgBtn').click(function(){
		var iframeStr='<iframe id="uploadImgIframe" class="uploadIframe" src="'+path+'/js/common/ueditor/dialogs/image/uploadImage.html?callback=picCallback" width="690" height="400" frameborder="0" scrolling="no"></iframe>';
		$(iframeStr).dialog({
			title: "多图上传",
			height: 400,
			width: 700,
			modal : true,
			onClose: function () {
	            $('#uploadImgIframe').remove();
	        }
		});
	});
    
    $('#saveBtn').click(function(){
    	
    	$('#bannerForm').submit();
/*    	var url = path + "/banner/save.html";
    	if ($('#id').val()!='') {
			url = path + '/banner/modify.html';
		}
    	alert("你妹");
    	$('#bannerForm').form('submit', {
			url : url,
			success : function(data) {
				var obj = $.parseJSON(data);
				bannerId=obj.id;
				$('#modelDiv').dialog('close');
				queryToGrid("progectTable", "list_search");
			},
			error: function(){
				
			}
		});*/
    });
    
    
    
    
    
    
	$("#bannerForm").validate({
		rules : {
			code : {
				required: true 
			},
			description : {
				required: true 
			}
		},
		messages : {
			code : {
				required : "请填写编码"
			},
			description : {
				required : "请填写描述"
			}
		},				
        submitHandler:function(form){
        	var url = path + "/banner/save.html";
        	if ($('#id').val()!='') {
    			url = path + '/banner/modify.html';
    		}
        	$('#bannerForm').form('submit', {
    			url : url,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				bannerId=obj.id;
    				$('#modelDiv').dialog('close');
    				queryToGrid("progectTable", "list_search");
    			},
    			error: function(){
    				
    			}
    		});
        }
    });
    
    
    
    
    
    
    
    
    
    
    
    
    
    
});

/**
 * 删除
 */
function removeBanner(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/banner/remove.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("progectTable", "list_search");
				}
			});
		}
	});
}

//修改
function editBanner(id){
	$('#code').attr('disabled','disabled');
	$.ajax({
		type : "POST",
		url : path + '/banner/getById.html',
		dataType:'json',
		data : {
			'id' : id
		},
		success : function(data) {
			$('#bannerForm').form('load', data);
			//加载图片
			var pictures = data.pictures;
			for ( var i = 0; i < pictures.length; i++) {
				loadPicture(pictures[i]);
			}
			
		}
	});
	$('#modelDiv').show().dialog({
		title: "修改Banner",
		height: 540,
		width:550,
		modal : true,
		onClose: function () {
			$('#bannerForm').form('clear').form('reset');
			$('#uploadImgDiv .filelist').html('');
        }
	});
}

function loadPicture(picObj){
	var picStr = '<div class="photo" style="width:370px;">'+
				 	'<div id="'+picObj.id+'" style="float:left;">'+
				 		'<img src="'+fileUrl+picObj.picture+'" style="height:150px;">'+
				 		'<div id="testPicOperate" class="operate" style="width:150px;">'+
				 			'<span class="cancel"></span>'+
				 		'</div>'+
				 	'</div>'+
				 	'<div style="margin-top:10px;">'+
					 	'<input type="hidden" name="picture" value="'+picObj.picture+'" />'+
				 		'<div class="x-form-item"><label class="x-form-item-label" style="clear:none; width:30px;">标题:</label><div class="x-form-element" style="position:static"><input type="text" name="title" value="'+(picObj.title?picObj.title:"")+'" style="width:180px;"/></div></div>'+
				 		'<div class="x-form-item"><label class="x-form-item-label" style="clear:none; width:30px;">链接:</label><div class="x-form-element" style="position:static"><input type="text" name="url" value="'+(picObj.url?picObj.url:"")+'" style="width:180px;"/></div></div>'+
				 		'<div class="x-form-item"><label class="x-form-item-label" style="clear:none; width:30px;">序号:</label><div class="x-form-element" style="position:static"><input type="text" name="seqNumStr" value="'+(picObj.seqNum?picObj.seqNum:"")+'" style="width:180px;"/></div></div>'+
				 	'</div>'+
				 '</div>';
	$(picStr).appendTo('#uploadImgDiv .filelist');
	
	
	$('#'+picObj.id).mouseenter(function(){
		$(this).find('.operate').stop().animate({height: 30});
		$(this).find('.cancel').stop().animate({height: 30});
	}).mouseleave(function(){
		$(this).find('.operate').stop().animate({height: 0});
		$(this).find('.cancel').stop().animate({height: 0});
	});
	
	$('#'+picObj.id).find('.cancel').click(function(){
		removePic(picObj.id);
	});
}


function picCallback(pics){
	for ( var i = 0; i < pics.length; i++) {
		var div_id = pics[i].title.substring(0,pics[i].title.indexOf('.'));
		var picStr = '<div class="photo" style="width:370px;">'+
	 	'<div id="'+div_id+'" style="float:left;">'+
	 		'<img src="'+fileUrl+pics[i].src+'" style="height:150px;">'+
	 		'<div id="testPicOperate" class="operate" style="width:150px;">'+
	 			'<span class="cancel"></span>'+
	 		'</div>'+
	 	'</div>'+
	 	'<div style="margin-top:10px;">'+
		 	'<input type="hidden" name="picture" value="'+pics[i].src+'" />'+
	 		'<div class="x-form-item"><label class="x-form-item-label" style="clear:none; width:30px;">标题:</label><div class="x-form-element" style="position:static"><input type="text" name="title" value="" style="width:180px;"/></div></div>'+
	 		'<div class="x-form-item"><label class="x-form-item-label" style="clear:none; width:30px;">链接:</label><div class="x-form-element" style="position:static"><input type="text" name="url" value="" style="width:180px;"/></div></div>'+
	 		'<div class="x-form-item"><label class="x-form-item-label" style="clear:none; width:30px;">序号:</label><div class="x-form-element" style="position:static"><input type="text" name="seqNumStr" value="" style="width:180px;"/></div></div>'+
	 	'</div>'+
	 '</div>';
		
		$(picStr).appendTo('#uploadImgDiv .filelist');
		$('#uploadImgIframe').dialog('close');
		
		$('#'+div_id).mouseenter(function(){
			$(this).find('.operate').stop().animate({height: 30});
			$(this).find('.cancel').stop().animate({height: 30});
		}).mouseleave(function(){
			$(this).find('.operate').stop().animate({height: 0});
			$(this).find('.cancel').stop().animate({height: 0});
		});
		
		$('#'+div_id).find('.cancel').click(function(){
			removePic(div_id);
		});
		
	}
}

//删除图片
function removePic(picId){
	$.messager.confirm('提示', '您确定要删除已选的图片吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/banner/removePhoto.html',
				data : {
					'photoId' : picId
				},
				success : function() {
					$('#'+picId).parent().remove();
				}
			});
		}
	});
}