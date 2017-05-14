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
	
	$("#pro_city").change(function(){
		getCitys($(this).val(), "pro_county");
	});
	
	
	
//	//选择借款用户
	$('#getLoanUserBtn').click(function(){
		getLoanUser('loanUserText','loanUser');
	});
	
	
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
			houseArea :  {
				required:true,
				number:true
			},
			houseLimit :  {
				required:true,
				number:true
			},
			fundAmt :  {
				required:true,
				number:true
			},
			fundDays :  {
				required:true,
				number:true
			},
			chargeFee:  {
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
			houseArea : {
				required:"请填写房屋建筑面积"
			},
			houseLimit : {
				required:"请填写房屋期限"
			},
			fundAmt : {
				required:"请填写筹资金额"
			},
			fundDays : {
				required:"请填写筹资天数"
			},
			chargeFee : {
				required:"请填写服务费比例"
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
    				}else{
    					
    				}
    			}
    		});
        }
    }); 
	
	
	
	$("#logoSaveBtn").click(function(){
		
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFunding.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"id": id,
					"loanLogo": $('#loanLogoForm input[name="logo"]').val()
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
	
	$("#priceSaveBtn").click(function(){
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFundDetail.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"loanNo": loanNo,
					"priceCompare": UE.getEditor("price_compare").getContent()
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
	
	$("#managerSaveBtn").click(function(){
		$.ajax({
			url: path+'/crowdfunding/updateCrowdFundDetail.html',
			type: "post",
			dataType: "json",
			async: false,
			data: {
					"loanNo": loanNo,
					"houseManager": UE.getEditor("house_manager").getContent()
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
	
	$("#stuffSaveBtn").click(function(){
		$('#stuffForm').form('submit', {
			url : path+'/crowdfunding/updateCrowdFundDetail.html',
			queryParams: {
				"loanNo":loanNo,
			},
			success : function(data) {
				data = $.parseJSON(data);
				if(data["success"]){
					$.messager.alert('提示', '保存成功');
				}else{
					
				}
			}
		});
	});
	
	createWebUploader('logoBtn','logoDiv','logo','loanLogoForm');
	
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



function searchUser(){
	queryToGrid("loanUserTable", "list_search");
}

