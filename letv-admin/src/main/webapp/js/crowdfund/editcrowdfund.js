var id="";
var sData={};
$(function(){
	$('#add').height(bodyHeight-150);
	$('#add').width(bodyWidth-160);
	
	$('#tt').tabs({
		height:bodyHeight-150,
	    onSelect:function(title){
	    	
	    }   
	});
	
	getProvice("pro_provice", "pro_city",function(){
		if(sData.province){
			$("#pro_provice").val(sData.province);
		}
	});
	//项目所属省份改变，城市改变
	$("#pro_provice").change(function(){
		getCitys($(this).val(), "pro_city");
	});
	
//	//选择借款用户
	$('#getLoanUserBtn').click(function(){
		getLoanUser();
	});
	
	if (loanNo) {
		$.ajax({
			type : "POST",
			url : path + '/crowdfunding/getCrowdDetail.html',
			dataType:'json',
			data : {
				'loanNo' : loanNo
			},
			success : function(data) {
				id=data.id;
				getCitys(data.province, "pro_city",function(){
					$('#pro_city').val(data.city);
				});
				getCitys(data.city, "pro_county",function(){
					$('#pro_county').val(data.county);
				});
				$("#loanTypeName").text(data.loanTypeName);
				$("#baseForm").form('load',data);
				
				loadPhoto("loanLogo1",data.loanLogo,'项目封面', 'logo_pigup');
				
				UE.getEditor("loan_detail").ready(function(){
					UE.getEditor("loan_detail").setContent(data.loanDetail);
				});
				
				$("#videoForm").form('load',data);
				if(data.loanVideo){
					var str = '<embed style="height:450px; width:500px;margin-left:50px;" src="'+data.loanVideo+'" quality="high" align="middle"  mode="transparent" type="application/x-shockwave-flash"></embed>';
					$("#loanVideoDisplay").html(str);
				}
				
				//项目图片
				if (data.photoUrls) {
					var loanPics = data.photoUrls.split(',');
					for ( var i = 0; i < loanPics.length; i++) {
						loadPhoto('a0001'+i,loanPics[i],'项目图片'+(i+1), 'loanphoto_pigup');
					}
				}
			}
		});
	}
	
	//保存基本信息
	$('#basicBtn').click(function() {
		$('#baseForm').submit();
	});
	
	$("#baseForm").validate({
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
			loanName : "required",
			loanUserName : "required",
			fundAmt :  {
				required:true,
				number:true
			},
			fundDays :  {
				required:true,
				number:true
			},
			province : "required",
//			city : "required",
//			county : "required",
			loanDes : "required"
		},
		messages : {
			loanName : "请填写项目名称",
			loanUserText : "请填写发起人",
			fundAmt : {
				required:"请填写筹资金额"
			},
			fundDays : {
				required:"请填写筹资天数"
			},
			province : "请填写项目所在省",
//			city : "请填写项目所在市",
//			county : "请填写项目所在县",
			loanDes : "请填写项目简介"
		},
        submitHandler:function(form){
        	if (loanNo) {
        		url=path + '/crowdfunding/updateCrowdFunding.html';
        	}
        	$('#baseForm').form('submit', {
    			url : url,
    			queryParams: {
    				"id":id,
    			},
    			success : function(data) {
    				data = $.parseJSON(data);
    				if(data["success"]){
    					$.messager.alert('提示', '保存成功');
    					$("#basicBtn").attr("disabled",false);
    					window.location.href=path+'/crowdfund.submiting.html';
    				}else{
    					
    				}
    			}
    		});
        }
    }); 
	
	//上传项目相关图片
	$('#logoPicBtn').click(function(){
		var logoLen = $('#logo_pigup input[name="loanPhoto"]').length;
		if(logoLen == 1){
			$.messager.alert('提示', 'logo只能上传一张');
			return;
		}
		var iframeStr='<iframe id="proIframe" class="uploadIframe" src="'+path+'/js/common/ueditor/dialogs/image/uploadImage.html?callback=logoCallback" width="690" height="400" frameborder="0" scrolling="no"></iframe>';
		$(iframeStr).dialog({
			title: "多图上传",
			height: 400,
			width: 700,
			modal : true,
			onClose: function () {
				 $('#proIframe').remove();
			}
		});
	});
	
	
	$("#logoSaveBtn").click(function(){
		var logoPhotos=[];
		$('#logo_pigup input[name="loanPhoto"]').each(function(index){
			logoPhotos.push($(this).val());
		});
		
		if (logoPhotos.length == 0) {
			$.messager.alert('提示', '请选择要上传的图片');
			return;
		}
		
		if (logoPhotos.length > 1) {
			$.messager.alert('提示', 'logo只能上传一张');
			return;
		}
		
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFunding.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"id": id,
					"loanLogo": logoPhotos[0]
				},
			success: function(data){
				if(data["success"]){
					$.messager.alert('提示', '更新logo成功');
				}
			},
			error: function(){
				
			}
		});
		
	});
	
	$("#detailSaveBtn").click(function(){
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFundDetail.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"loanNo": loanNo,
					"loanDetail": UE.getEditor("loan_detail").getContent()
				},
			success: function(data){
				if(data["success"]){
					$.messager.alert('提示', '更新成功');
				}
			},
			error: function(){
				
			}
		});
	});
	
	$("#videoSaveBtn").click(function(){
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFundDetail.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"loanNo": loanNo,
					"loanVideo": $("#loan_video").val(),
					"videoDes": $("#video_des").val()
				},
			success: function(data){
				if(data["success"]){
					$.messager.alert('提示', '更新成功');
				}
			},
			error: function(){
				
			}
		});
	});
	
});

function logoCallback(pics){
	for ( var i = 0; i < pics.length; i++) {
		loadPhoto(pics[i].title.substring(0,pics[i].title.indexOf('.')),pics[i].src,pics[i].alt,'logo_pigup');
	}
	$('#proIframe').dialog('close');
}

function loadPhoto(id,url,name, renderId){
	var picStr = '<div class="photo">'+
					'<input type="hidden" name="loanPhoto" value="'+url+'"/>'+
					'<input type="hidden" name="id" value="'+id+'"/>'+
				 	'<div id="'+id+'">'+
				 		'<img src="'+fileUrl+url+'">'+
				 		'<div id="testPicOperate" class="operate">'+
				 			'<span class="cancel"></span>'+
				 		'</div>'+
				 	'</div>'+
				 	'<div class="text" id="'+id+'name" title="'+name+'">'+name+'</div>'+
				 	'<div class="textInput" id="'+id+'input" ><input type="text" name="photoName" value="'+name+'"/></div>'+
				 '</div>';

	$(picStr).appendTo('#'+renderId+' .filelist');
	
	//编辑图片名称
	$('#'+id+'name').click(function(){
		$(this).hide();
		$('#'+id+'input').show();
		$('#'+id+'input input').focus().select();
	});
	//停止编辑
	$('#'+id+'input input').blur(function(){
		$(this).parent().hide();
		$('#'+id+'name').attr('title',$(this).val()).html($(this).val());
		$('#'+id+'name').show();
	});
	
	$('#'+id).mouseenter(function(){
		$(this).find('.operate').stop().animate({height: 30});
		$(this).find('.cancel').stop().animate({height: 30});
	}).mouseleave(function(){
		$(this).find('.operate').stop().animate({height: 0});
		$(this).find('.cancel').stop().animate({height: 0});
	});
	
	$('#'+id).find('.cancel').click(function(){
		removePic(id);
	});
}
//删除图片
function removePic(picId){
	$('#'+picId).parent().remove();
}

//选择借款用户
function getLoanUser(){
	$('<div id="loanUserDiv"><table id="loanUserTable"></table></div>').hide().appendTo('body');
	//获取字典数据
	var columns = [[
					{field:'userId',title:'用户名',width:150,align:'center',sortable:true},
					{field:'realName',title:'真实姓名',width:130,align:'center',sortable:true},
					{field:'thirdAccount',title:'汇付账号',width:150,align:'center',sortable:true},
					{field:'mobile',title:'手机号',align:'center'},
					{field:'email',title:'邮箱',width:150,align:'center'},
					{field:'createTime',title:'注册时间',align:'center',sortable:true},
					{field:'statusName',title:'状态',width:50,align:'center',sortable:true}
				]];
	$('#loanUserTable').datagrid({
		url: path + "/user/getlist.html?isBorrower=1",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		height:364,
		pagination: true,
		onDblClickRow:function(rowIndex, row){
			$('#loanUserText').val(row.realName);
			$('#loanUser').val(row.userId);
			$("#loanUserDiv").dialog('close');
		}
	});
	$("#loanUserDiv").show().dialog({
		title: "选择借款用户",
		height: 400,
		width:900,
		modal: true,
		onClose: function () {
			$("#loanUserDiv").remove();
        }
	});
	
}

