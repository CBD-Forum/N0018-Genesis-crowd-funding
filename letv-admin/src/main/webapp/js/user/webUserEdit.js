$(function(){
	$('#tt').tabs({
		height:bodyHeight-190,
	    onSelect:function(title){   
	    }   
	});
	$('#add').width(bodyWidth-165);
	$('#add').height(bodyHeight-200);
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
		$.ajax({
			type : "POST",
			url : path + '/user/getCompanyDetail.html',
			dataType:'json',
			data : {
				'userId' : userId
			},
			success : function(data) {
				
				
				for(var i=0;i<data.userFiles.length;i++){
					if (data.userFiles[i].fileType =='legal_user_card') {
						if(data.userFiles[i].fileUrl){
							$("#authFileInfo input[name='legal_user_card']").val(data.userFiles[i].fileUrl);
							$('#photoDiv1 .filelist').html('<img src="'+fileUrl+data.userFiles[i].fileUrl+'" style="width:200px; height:200px;"/>');
						}
					}
					if (data.userFiles[i].fileType == 'credit_report') {
						if(data.userFiles[i].fileUrl){
							$("#authFileInfo input[name='credit_report']").val(data.userFiles[i].fileUrl);
							$('#photoDiv2 .filelist').html('<img src="'+fileUrl+data.userFiles[i].fileUrl+'" style="width:200px; height:200px;"/>');
						}
					}
					if (data.userFiles[i].fileType =='busi_licence') {
						if(data.userFiles[i].fileUrl){
							$("#authFileInfo input[name='busi_licence']").val(data.userFiles[i].fileUrl);
							$('#photoDiv3 .filelist').html('<img src="'+fileUrl+data.userFiles[i].fileUrl+'" style="width:200px; height:200px;"/>');
						}
					}
					if (data.userFiles[i].fileType =='busi_licence_copy') {
						if(data.userFiles[i].fileUrl){
							$("#authFileInfo input[name='busi_licence_copy']").val(data.userFiles[i].fileUrl);
							$('#photoDiv4 .filelist').html('<img src="'+fileUrl+data.userFiles[i].fileUrl+'" style="width:200px; height:200px;"/>');
						}
					}
					if (data.userFiles[i].fileType =='tax_reg') {
						if(data.userFiles[i].fileUrl){
							$("#authFileInfo input[name='tax_reg']").val(data.userFiles[i].fileUrl);
							$('#photoDiv5 .filelist').html('<img src="'+fileUrl+data.userFiles[i].fileUrl+'" style="width:200px; height:200px;"/>');
						}
					}
					if (data.userFiles[i].fileType == 'tax_reg_copy') {
						if(data.userFiles[i].fileUrl){
							$("#authFileInfo input[name='tax_reg_copy']").val(data.userFiles[i].fileUrl);
							$('#photoDiv6 .filelist').html('<img src="'+fileUrl+data.userFiles[i].fileUrl+'" style="width:200px; height:200px;"/>');
						}
					}
					if (data.userFiles[i].fileType == 'org_code') {
						if(data.userFiles[i].fileUrl){
							$("#authFileInfo input[name='org_code']").val(data.userFiles[i].fileUrl);
							$('#photoDiv7 .filelist').html('<img src="'+fileUrl+data.userFiles[i].fileUrl+'" style="width:200px; height:200px;"/>');
						}
					}
					if (data.userFiles[i].fileType == 'org_code_copy') {
						if(data.userFiles[i].fileUrl){
							$("#authFileInfo input[name='org_code_copy']").val(data.userFiles[i].fileUrl);
							$('#photoDiv8 .filelist').html('<img src="'+fileUrl+data.userFiles[i].fileUrl+'" style="width:200px; height:200px;"/>');
						}
					}
					if (data.userFiles[i].fileType == 'company_photo') {
						if(data.userFiles[i].fileUrl){
							$("#authFileInfo input[name='company_photo']").val(data.userFiles[i].fileUrl);
							$('#photoDiv9 .filelist').html('<img src="'+fileUrl+data.userFiles[i].fileUrl+'" style="width:200px; height:200px;"/>');
						}
					}
					if (data.userFiles[i].fileType =='tenancy_contract') {
						if(data.userFiles[i].fileUrl){
							$("#authFileInfo input[name='tenancy_contract']").val(data.userFiles[i].fileUrl);
							$('#photoDiv10 .filelist').html('<img src="'+fileUrl+data.userFiles[i].fileUrl+'" style="width:200px; height:200px;"/>');
						}
					}
					if (data.userFiles[i].fileType == 'finance_report') {
						if(data.userFiles[i].fileUrl){
							$("#authFileInfo input[name='finance_report']").val(data.userFiles[i].fileUrl);
							$('#photoDiv11 .filelist').html('<img src="'+fileUrl+data.userFiles[i].fileUrl+'" style="width:200px; height:200px;"/>');
						}
					}
					if (data.userFiles[i].fileType == 'hygienic_license') {
						if(data.userFiles[i].fileUrl){
							$("#authFileInfo input[name='hygienic_license']").val(data.userFiles[i].fileUrl);
							$('#photoDiv12 .filelist').html('<img src="'+fileUrl+data.userFiles[i].fileUrl+'" style="width:200px; height:200px;"/>');
						}
					}
				}
			}
		});
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
	
	createWebUploader('photoBtn','photoDiv','photo');
	
	createWebUploader('photoBtn1','photoDiv1','legal_user_card');
	createWebUploader('photoBtn2','photoDiv2','credit_report');
	createWebUploader('photoBtn3','photoDiv3','busi_licence');
	createWebUploader('photoBtn4','photoDiv4','busi_licence_copy');
	createWebUploader('photoBtn5','photoDiv5','tax_reg');
	createWebUploader('photoBtn6','photoDiv6','tax_reg_copy');
	createWebUploader('photoBtn7','photoDiv7','org_code');
	createWebUploader('photoBtn8','photoDiv8','org_code_copy');
	createWebUploader('photoBtn9','photoDiv9','company_photo');
	createWebUploader('photoBtn10','photoDiv10','tenancy_contract');
	createWebUploader('photoBtn11','photoDiv11','finance_report');
	createWebUploader('photoBtn12','photoDiv12','hygienic_license');
});

function getUserLevel(){
	//获取标的类型
	$.ajax({
		url: path + "/dictionary/getDic.html",
		type: "post",
		dataType: "json",
		data: {"type": "user_level"},
		success: function(data){
			getLoanTypeFn("userLevel", data["rows"]);
		},
		error: function(){
			console.log("获取用户等级异常");
		}
	});
}
function createWebUploader(pickId,showId,setValueId){
	var uploader=WebUploader.create({
		auto: true,
	    pick: {
	        id: '#'+pickId,
	        multiple: false,
	        label: '选择图片'
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
	    fileSingleSizeLimit: 8 * 1024 * 1024    // 50 M
	});
	
	uploader.on('uploadSuccess', function (file, ret) {
//        var $file = $('#' + file.id);
        try {
            var responseText = (ret._raw || ret),
                json = $.parseJSON(responseText);
            if (json.state == 'SUCCESS') {
            	$('#'+showId+' .filelist').html('<img src="'+fileUrl+json.url+'" style="width:200px; height:200px;"/>');
            	$('input[name="'+setValueId+'"]').val(json.url);
            } else {
//                $file.find('.error').text(json.state).show();
            }
        } catch (e) {
//            $file.find('.error').text(lang.errorServerUpload).show();
        }
    });
	
	return uploader;
}

function updateCompany(fileType){
	//获取标的类型
	var companyUrl = $('input[name="'+fileType+'"]').val();
	$.ajax({
		url: path + "/user/modifyCompany.html",
		type: "post",
		dataType: "json",
		data: {
			"userId": userId,
			"fileType" :fileType,
			"fileUrl":companyUrl
			
		},
		success: function(data){
			$.messager.alert('提示','保存成功');
		},
		error: function(){
			console.log("保存'"+fileType+"'失败");
		}
	});
}