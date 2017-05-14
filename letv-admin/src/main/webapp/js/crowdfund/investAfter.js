var operateBtns = [];

var vedioUploadMask = null;
$(function() {

	// 查询按钮事件
	$("#searchBtn").click(function() {
		queryToGrid("investAfterTable", "list_search");
	});

	// 操作按钮
	var add = {
		text : '添加',
		iconCls : 'icon-add',
		handler : function() {
			showAdd();
		}
	};

	if ($('#crowdfund_investAfter_add').length > 0) {
		operateBtns.push(add);
	}
	if (operateBtns.length == 0) {
		operateBtns = null;
	}
	searchData("/crowdfundingInvestAfter/selectPageList.html?loanNo=" + loanNo);

	// 头像
	createOneWebUploader('photoBtn', 'photoDiv', 'photo');

	$('#investAfterForm .x-form-item').each(function() {
		var name = $(this).find('input').attr('name');
		createWebUploader(name, 'investAfterForm');
	});

	$('#closeBtn').click(function() {
		$("#add").show().dialog('close');
	});

	// 保存基本信息
	$('#investAfterBtn').click(function() {
		$('#investAfterForm').submit();
	});

	$("#investAfterForm").validate({
		rules : {
			content : {
				required : true,
				maxlength : 1000
			},
			loanNo:{
				required : true
			}
		},
		messages : {
			content : {
				required : "请填写项目进展",
				maxlength : "项目名称不能超过1000个字符"
			}
		},
		submitHandler : function(form) {
			var url = path + '/crowdfundingInvestAfter/saveInvestAfter.html';
			$('#investAfterForm').form('submit', {
				url : url,
				success : function(data) {
					data = $.parseJSON(data);
					if (data["success"]) {
						$.messager.alert('提示', '保存成功');
						$("#add").show().dialog('close');
						queryToGrid("investAfterTable", "list_search");
					} else {
						$.messager.alert('提示', data["msg"]);
					}
				}
			});
		}
	});
 
});
function searchData(dUrl) {
	var columns = [ [ {
		field : 'id',
		title : 'id',
		width : 0,
		hidden : true
	}, {
		field : 'operat',
		title : '操作',
		width : 120,
		align : 'center',
		formatter : operateData
	}, {
		field : 'loanNo',
		title : '项目编号',
		width : 120,
		align : 'center'
	}, {
		field : 'loanName',
		title : '项目名称',
		width : 150,
		align : 'center'
	},{
		field : 'createTime',
		title : '录入时间',
		width : 130,
		align : 'center'
	},  {
		field : 'content',
		title : '内容',
		width : 450,
		align : 'center'
	}
	/*, {
		field : 'auditor',
		title : '审核人',
		width : 120,
		align : 'center'
	}, {
		field : 'auditTime',
		title : '审核时间',
		width : 120,
		align : 'center'
	} */
	
	] ];
	$('#investAfterTable').datagrid({
		url : path + dUrl,
		columns : columns,
		height : bodyHeight - 150,
		nowrap : false,
		rownumbers : true,
		pagination : true,
		toolbar : operateBtns
	});
}

function pass(id) {
	$.ajax({
		type : "POST",
		url : path + '/crowdfundInvest/auditProgress.html',
		data : {
			'id' : id,
			'state' : 'passed'
		},
		success : function() {
			queryToGrid("investAfterTable", "list_search");
		}
	});
}

function refuse(id) {
	$.ajax({
		type : "POST",
		url : path + '/crowdfundInvest/auditProgress.html',
		data : {
			'id' : id,
			'state' : 'refused'
		},
		success : function() {
			queryToGrid("investAfterTable", "list_search");
		}
	});
}

function deleteInvestAfter(id) {
	$.messager.confirm('提示', '您确定删除该信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundingInvestAfter/deleteInvestAfter.html',
				data : {
					'id' : id
				},
				success : function(data) {
					if (data.success) {
						$.messager.alert('提示', '删除成功。');
					} else {
						$.messager.alert('提示', '后台报错，请联系管理员。');
					}
					queryToGrid("investAfterTable", "list_search");
				}
			});
		}
	});
}
function showAdd() {
	$("#investAfterForm").form('clear');
	$.ajax({
		type : "POST",
		url : path + '/getId.html',
		success : function(data) {
			if(data["success"]){
				$("#investAfter_id").val(data["id"]);
				
				if(loanNo){
					$("#form_loanNo").val(loanNo);
					$("#show_loanNo").html(loanNo);
				}else{
					$("#show_loanNo").hide();
					$("#form_loanNo").attr("type","text");
				}

				$("#add").show().dialog({
					title : "添加投后信息",
					height : 650,
					width : 1000,
					modal : true,
					onClose : function() {
						$("#investAfterForm").form('clear');
					}
				});
			    $("#otherFileChangeUpload").click(function(){
			    	
			    	if (!loanNo) {
			    		loanNo = $("#loanNo").val();
			    		if(loanNo){
				    		$.messager.alert('提示', '请先保存基本信息');
				    		return false;
			    		}

			    	}
					$("#otherFileToUpload").click();
				});
				
				//选择文件之后执行上传  
			    $('#otherFileToUpload').on('change', function() {
			    	var parentId = $("#investAfter_id").val();
			    	var sendLoanMask=getMask('正在上传，请稍后...');
			        $.ajaxFileUpload({  
			        	url:path+'/fileUpload/uploadFile.html?jq_random='+Math.random().toFixed(5)+'&parentId='+parentId,  
			            secureuri:false,  
			            fileElementId:'otherFileToUpload',//file标签的id  
			            dataType: 'json',//返回数据的类型  
			            success: function (data, status) {
			            	  if(data["success"]){
			            		sendLoanMask.dialog('close');
			            		$.messager.alert('提示', '上传成功');
			            		$("#otherFileUploadInFo").append("<p><span style='color:red;'&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
			            				"<a href="+fileUrl+data.msg+" target='_black'>"+data.fileName+"</a>" +
				    						"<input type='hidden' name='otherFile' value="+data.msg+" />" +
				    						"&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' id='"+(data.id)+"' onclick='removeFile(this);'>删除</a>" +
				    								"</p>");
							}else{
								$.messager.alert("提示","上传失败！");
							} 
			            },  
			            error: function (data, status, e) {  
			                alert(e);
			            },
			            complete:function(XMLHttpRequest,textStatus){
				        	sendLoanMask.dialog('close');
				        }
			        });  
			    });
			}
		}
	});		
}

/**
 * 单张图片上传
 * 
 * @param pickId
 * @param showId
 * @param setValueId
 * @returns
 */
function createOneWebUploader(pickId, showId, setValueId) {
	var uploader = WebUploader.create({
		auto : true,
		pick : {
			id : '#' + pickId,
			multiple : false,
			label : '点击选择图片'
		},
		accept : {
			title : 'Images',
			extensions : 'gif,jpg,jpeg,bmp,png',
			mimeTypes : 'image/*'
		},
		// swf文件路径
		swf : path + '/js/common/webuploader/Uploader.swf',
		chunked : true,
		compress : false,
		server : path + '/uploadImage.html',
		fileNumLimit : 300,
		fileSingleSizeLimit : 1 * 1024 * 1024
	});

	uploader.on('uploadSuccess', function(file, ret) {
		try {
			var responseText = (ret._raw || ret), json = $
					.parseJSON(responseText);
			if (json.state == 'SUCCESS') {
				$('#' + showId + ' .filelist').html(
						'<img src="' + fileUrl + json.url
								+ '" style="width:200px; height:200px;"/>');
				$('#investAfterForm input[name="' + setValueId + '"]').val(
						json.url);
			} else {
				// $file.find('.error').text(json.state).show();
			}
		} catch (e) {
			// $file.find('.error').text(lang.errorServerUpload).show();
		}
	});

	return uploader;
}
function createWebUploader(name, formId) {
	$('#' + name + 'Btn')
			.click(
					function() {
						var iframeStr = '<iframe id="'
								+ name
								+ 'Iframe" class="uploadIframe" src="'
								+ path
								+ '/js/common/ueditor/dialogs/image/uploadImage.html?callback=picCallback" width="690" height="400" frameborder="0" scrolling="no"></iframe>';
						$(iframeStr).dialog({
							title : "多图上传",
							height : 400,
							width : 700,
							modal : true,
							onClose : function() {
								$('#' + name + 'Iframe').remove();
							}
						});
					});
}

function picCallback(pics) {
	var operateId = '';
	$('#investAfterForm .x-form-item').each(function() {
		var name = $(this).find('input').attr('name');
		if ($('#' + name + 'Iframe').length > 0) {
			operateId = name;
		}
	});

	for ( var i = 0; i < pics.length; i++) {
		var id = pics[i].title.substring(0, pics[i].title.indexOf('.'));
		var url = pics[i].src;
		var picStr = '<div class="photo">' + '<input type="hidden" name="'
				+ operateId + '" value="' + pics[i].src + '" />' + '<div id="'
				+ pics[i].title.substring(0, pics[i].title.indexOf('.')) + '">'
				+ '<img src="' + fileUrl + pics[i].src + '">'
				+ '<div id="testPicOperate" class="operate">'
				+ '<span class="cancel"></span>' + '</div>' + '</div>'
				+ '<div class="text" title="' + pics[i].alt + '">'
				+ pics[i].alt + '</div>' + '</div>';
		$(picStr).appendTo('#' + operateId + 'Div .filelist');
		$('#' + id).mouseenter(function() {
			$(this).find('.operate').stop().animate({
				height : 30
			});
			$(this).find('.cancel').stop().animate({
				height : 24
			});
		}).mouseleave(function() {
			$(this).find('.operate').stop().animate({
				height : 0
			});
			$(this).find('.cancel').stop().animate({
				height : 0
			});
		});

		$('#' + id).find('.cancel').click(function() {
			removePic($(this).parent().parent().attr("id"), url);
		});
		$(picStr).slideDown(1000);
		$('#' + operateId + 'Iframe').dialog('close');
	}
}

function loadPhoto(id, url, renderId, name) {
	var picStr = '<div class="photo">' + '<input type="hidden" name="' + name
			+ '" value="' + url + '"/>' + '<div id="' + id + '">'
			+ '<img src="' + fileUrl + url + '">'
			+ '<div id="testPicOperate" class="operate">'
			+ '<span class="cancel"></span>' + '</div>' + '</div>' + '</div>';

	$(picStr).appendTo('#' + renderId + ' .filelist');
	$('#' + id).mouseenter(function() {
		$(this).find('.operate').stop().animate({
			height : 30
		});
		$(this).find('.cancel').stop().animate({
			height : 24
		});
	}).mouseleave(function() {
		$(this).find('.operate').stop().animate({
			height : 0
		});
		$(this).find('.cancel').stop().animate({
			height : 0
		});
	});
	$('#' + id).find('.cancel').click(function() {
		// removePic($(this).parent().parent().attr("id"),url);
	});
}

function removePic(id, url) {
	// 删除
	$.messager.confirm('提示', '您确定要删除此图片吗？', function(r) {
		if (r) {
			$('#' + id).parent().slideUp(1000, function() {
				$('#' + id).parent().remove();
			});
		}
	});
} 



function showDetail(id){
	
	$.ajax({
		type : "POST",
		url : path + '/crowdfundingInvestAfter/selectById.html',
		data : {
			'id' : id
		},
		success : function(data) {
			$("#crowdfund_investAfter_detail_div").show().dialog({
				title : "详情",
				height : 650,
				width : 1000,
				modal : true,
				onClose : function() {
				 
				}
			});	
			$("#show_detail_loanNo").html(data.msg.loanNo);	
			$("#show_content").html(data.msg.content);	
			
			//处理图片
			var imgFileLists = data.msg.picUrl;
			$("#picFilelistShow").html("");
			if(imgFileLists){
				var picFiles  = imgFileLists.split(",");
				if(picFiles.length>0){
					var lArr = [], lStr = '';
					for(var i=0;i<picFiles.length;i++){
						var imgFile  =  picFiles[i];
						if(imgFile){
							lArr.push("<div class='photo'>");
							lArr.push("  <div id='1241234124'>");
							lArr.push("  <img src='"+fileUrl+imgFile+"'>");
							lArr.push("  <div id='testPicOperate' class='operate'>");
							lArr.push("  <span class='cancel'></span></div></div>");
							lArr.push("</div>");
						}
					}
					lStr = lArr.join("");
					$("#picFilelistShow").html(lStr);
				}
			}
			
			//显示文件
			
			var otherFileLists = data.msg.fileList;
			if(otherFileLists.length>0){
				var lArr = [], lStr = '';
				for(var i=0;i<otherFileLists.length;i++){
					var otherFile  =  otherFileLists[i];
					lArr.push("<a href='"+fileUrl+otherFile.fileUrl+"'>"+otherFile.fileName+"</a><br/>");
				}
				lStr = lArr.join("");
				$("#otherFilelistShow").html(lStr);
			}
			
			
		}
	});	
}
 
 

 