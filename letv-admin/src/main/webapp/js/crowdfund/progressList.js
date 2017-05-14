var operateBtns = [];

var vedioUploadMask = null;
$(function() {

	// 查询按钮事件
	$("#searchBtn").click(function() {
		queryToGrid("progectTable", "list_search");
	});

	// 操作按钮
	var add = {
		text : '添加',
		iconCls : 'icon-add',
		handler : function() {
			showAdd();
		}
	};

	if ($('#crowdfund_progress_add').length > 0) {
		 operateBtns.push(add);
	}
	if (operateBtns.length == 0) {
		operateBtns = null;
	}
	searchData("/crowdfundProgress/getProgessList.html?loanNo=" + loanNo);

	// 头像
	createOneWebUploader('photoBtn', 'photoDiv', 'photo');

	$('#ProgressDetailForm .x-form-item').each(function() {
		var name = $(this).find('input').attr('name');
		createWebUploader(name, 'ProgressDetailForm');
	});

	$('#closeBtn').click(function() {
		$("#crowdfund_progress_detail").show().dialog('close');
	});

	// 保存基本信息
	$('#ProgressBtn').click(function() {
		$('#ProgressDetailForm').submit();
	});

	$("#ProgressDetailForm").validate({
		rules : {
			timeNode : {
				required : true
			},
			enterContent : {
				required : true,
				maxlength : 1000
			},
			loanNo:{
				required : true
			}
		},
		messages : {
			enterContent : {
				required : "请填写时间节点"
			},
			enterContent : {
				required : "请填写项目进展",
				maxlength : "项目名称不能超过1000个字符"
			}
		},
		submitHandler : function(form) {

			var url = path + '/crowdfundProgress/save.html';
			var progress_id = $("#progress_id").val();
			 if (progress_id) {
			   url=path + '/crowdfundProgress/update.html';
			 }
			$('#ProgressDetailForm').form('submit', {
				url : url,
				// queryParams: {
				// "loanNo":loanNo
				// },
				success : function(data) {
					data = $.parseJSON(data);
					if (data["success"]) {
						$.messager.alert('提示', '保存成功');
						$("#crowdfund_progress_detail").show().dialog('close');
						queryToGrid("progectTable", "list_search");
					} else {
						$.messager.alert('提示', data["msg"]);
					}
				}
			});
		}
	});

	$("#fileUpload").click(function() {
		$("#vedioFile").click();
	});

	$("#vedioFile").change(function() {
		// 获取上传图片的元素（流）
		vedioUploadMask = getMask('视频上传中，请耐心等待....');
		var formData = new FormData($("#imgForm")[0]);
		$.ajax({
			// 这个ajax的接口还是PC端的上传头像的接口
			url : path + "/crowdfundProgress/uploadVideo.html",
			type : "post",
			data : formData,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			success : function(data) {
				vedioUploadMask.dialog('close');
				if (data["success"]) { // 上传成功
					var url = data["url"];
					var id = data["id"];
					var iconUrl = path +'/js/common/ueditor/dialogs/attachment/images/icons.png';
					var vedioStr = '<div class="vedio" style="width: 324px; float: left;position: relative;" height="245">'
							+ '<input type="hidden" name="vedioFiles" value="'+ url + '"/>'
							+ '<video id="imghead" src="' + fileUrl + url + '" width="320" height="240" controls="controls"></video>'
							+ '<div id="testPicOperate" class="operate">'
							
							+ '<span class="cancel" style="background-image:url('+iconUrl+')"></span>'
							+ '</div>' + '</div>';
					$("#vedioFilelist").append(vedioStr);
					$("#vedioFilelist>div").mouseenter(function() {
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
					$("#vedioFilelist>div .cancel").click(function(){
						$(this).parent().parent().remove();
					});
//											var file = $("#vedioFile")   
//											file.after(file.clone().val(""));     
//											file.remove();   
				} else {
					return false;
				}
			},
			error : function(returndata) {

			}
		});
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
	}, {
		field : 'enterUser',
		title : '录入人',
		width : 120,
		align : 'center'
	}, {
		field : 'timeNode',
		title : '时间节点',
		width : 130,
		align : 'center'
	},{
		field : 'enterTime',
		title : '录入时间',
		width : 130,
		align : 'center'
	},  {
		field : 'enterContent',
		title : '项目进展',
		width : 380,
		align : 'center',
		formatter:formatCellTooltip
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
	$('#progectTable').datagrid({
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
			queryToGrid("progectTable", "list_search");
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
			queryToGrid("progectTable", "list_search");
		}
	});
}

function deleteProgress(id) {
	$.messager.confirm('提示', '您确定删除该进展吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundProgress/remove.html',
				data : {
					'id' : id
				},
				success : function(data) {
					if (data.success) {
						$.messager.alert('提示', '删除成功。');
					} else {
						$.messager.alert('提示', data.msg);
					}
					queryToGrid("progectTable", "list_search");
				}
			});
		}
	});
}

function showAdd() {
	
	if(loanNo){ //如果项目编号存在
		$("#form_loanNo").val(loanNo);
		$("#show_loanNo").html(loanNo);
	}else{  //项目编号不存在
		$("#show_loanNo").hide();
		$("#form_loanNo").attr("type","text");
	}

	
	$("#crowdfund_progress_detail").show().dialog({
		title : "添加进度",
		height : 650,
		width : 1000,
		modal : true,
		onClose : function() {
			$("#ProgressDetailForm").form('clear');
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
				$('#ProgressDetailForm input[name="' + setValueId + '"]').val(
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
	$('#ProgressDetailForm .x-form-item').each(function() {
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
			// alert($('#'+id).parent().parent().html());

		}

	});
}
function showDetail(id){
	
	$.ajax({
		type : "POST",
		url : path + '/crowdfundProgress/selectDetailById.html',
		data : {
			'id' : id
		},
		success : function(data) {
			$("#crowdfund_progress_detail_div").show().dialog({
				title : "详情",
				height : 650,
				width : 1000,
				modal : true,
				onClose : function() {
					$("#ProgressDetailForm").form('clear');
				}
			});	
			$("#show_detail_loanNo").html(data.loanNo);	
			$("#show_timeNode").html(data.timeNode);	
			$("#show_enterContent").html(data.enterContent);	
			
			//处理图片
			var imgFileList = data.imgFileList;
			$("#filelistShow").html("");
			if(imgFileList.length>0){
				var lArr = [], lStr = '';
				for(var i=0;i<imgFileList.length;i++){
					var imgFile  =  imgFileList[i];
					lArr.push("<div class='photo'>");
					lArr.push("  <div id='1241234124'>");
					lArr.push("  <img src='"+fileUrl+imgFile.url+"'>");
					lArr.push("  <div id='testPicOperate' class='operate'>");
					lArr.push("  <span class='cancel'></span></div></div>");
					lArr.push("</div>");
				}
				lStr = lArr.join("");
				$("#filelistShow").html(lStr);
			}
			
			/*$("#vedioFilelistShow").html("");
			//处理视频
			var vedioFileList = data.vedioFileList;
			if(vedioFileList.length>0){
				var lArr = [], lStr = ''; 
				for(var i=0;i<vedioFileList.length;i++){
					lArr.push("<div class='vedio' style='width: 324px; float: left;position: relative;' height='245'>");
					lArr.push("<video id='imghead' src='"+fileUrl+imgFile.url+"'  width='320' height='240' controls='controls'></video>");
					lArr.push("<div id='testPicOperate' class='operate'>");
					lArr.push("</div>");
					lArr.push("</div>");
				}
				lStr = lArr.join("");
				$("#vedioFilelistShow").html(lStr);
			}*/
		}
	});	
}


 
 

 