$(function(){
	$("#baseForm :text").css('border','1px solid #95b8e7');
	$("#baseForm select").css('border','1px solid #95b8e7');
	$("#loan_no").val(loanNo);
	searchData('/crowdfunding/getBackSetList.html?loanNo='+loanNo+'&sort=amt');
	$("input[name='backType']").click(function(){
		if($(this).val()=='S'){
			$("#transFeeDiv").show();
		}else{
			$("#transFeeDiv").hide();
			$("input[name='transFee']").val(0);
		}
	});
	
	//抽奖字段验证 start
	$("#prizeDiv").hide();
	$("input[name='prizeDrawFlag']").click(function(){
		if($(this).val()==1){
			$("#prizeDiv").show();
		}else{
			$("#prizeDiv").hide();
		}
	});
	//抽奖字段验证 end
	
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
			amt : {
				required:true,
				number:true
			},
 			numberLimits : {
				required:true,
				number:true
			},
			backTime : {
				required:true,
				number:true
			},
			transFee : {
				required:true,
				number:true,
				digits:true
			},
			prizeNum:{
				number:true
			},
			prizeInvestNum:{
				number:true
			},
			prizeFullNum:{
				number:true
			},
			backContent : "required"
		},
		messages : {
			amt : {
				required:"请填写支持金额"
			},
			numberLimits : {
				required:"请填写支持名额"
			},
			backTime : {
				required:"请填写回报时间"
			},
			transFee:{
				required:"请填写运费",
				digits:"请输入整数"
			},
			backContent : "请填写回报内容"
		},
        submitHandler:function(form){
        	var url=path + "/crowdfunding/saveBackSet.html";
        	if ($("#back_id").val()) {
        		url=path + '/crowdfunding/updateBackSet.html';
        	}
        	$('#baseForm').form('submit', {
    			url : url,
    			success : function(data) {
    				data = $.parseJSON(data);
    				if(data["success"]){
    					$('#photoDiv .filelist').html('');
    					$('#baseForm').form('clear');
    					searchData('/crowdfunding/getBackSetList.html?loanNo='+loanNo+'&sort=amt');
    					$("#loan_no").val(loanNo);
    				}else{
    					$.messager.alert('提示',data["msg"]);
    					return;
    				}
    			}
    		});
        }
    }); 
	
	//保存基本信息
	$('#basicBtn').click(function() {
		$('#baseForm').submit();
	});
	
	createWebUploader('photoBtn','photoDiv','photoUrl1');
	
});

//查询项目列表
function searchData(dUrl){
	//获取字典数据
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'operat',title:'操作',width:120,align:'center',formatter:operateData},
					{field:'backNo',title:'回报编号',width:130,align:'center'},
					{field:'amt',title:'支持金额(元)',width:100,align:'right',sortable:true},
					/*{field:'transferLockPeriod',title:'转让锁定天数',width:100,align:'right',sortable:true},*/
/*					{field:'dailyProfit',title:'预计每日收益(元)',width:100,align:'right',sortable:true},*/
					{field:'numberLimits',title:'名额(个)',width:100,align:'center',formatter:formatNumberLimits},
					{field:'backType',title:'回报类型',width:100,align:'right',formatter:formatBackType},
					{field:'transFee',title:'运费(元)',width:100,align:'right',formatter:formatTransFee},
					/*{field:'backLable',title:'回报展示类别',width:100,align:'right',formatter:formatBackLable},*/
					{field:'prizeDrawFlag',title:'是否参与抽奖',width:100,align:'center',formatter:formatPrizeDrawFlag},
					{field:'backTime',title:'回报时间(天)',width:140,align:'right',formatter:formatBackTime}
				]];
	$('#backTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:150,
		nowrap:false
	});
}
function formatNumberLimits(val,row,index){
	if(val==0){
		return "不限名额";
	}else {
		return val;
	}
}
function formatBackTime(val,row,index){
	if(val==0){
		return "项目结束后立即发送";
	}else{
		return "项目结束后"+val+"天发送";
	}
}
function formatTransFee(val,row,index){
	if(val==0){
		return "不收运费";
	}else{
		return val;
	}
}


function formatBackLable(val,row,index){
	if(val=='P'){
		return "普通档";
	}else{
		return "手机档";
	}
}


function formatPrizeDrawFlag(val,row,index){
	if(val=='1'){
		return "参与";
	}else{
		return "不参与";
	}
}

function formatBackType(val,row,index){
	if(val=='S'){
		return "实物回报";
	}else{
		return "虚拟回报";
	}
}


function operateData(val,row,index){
	var returnStr='<a onclick=modify("'+row["id"]+'")>修改</a>';
	returnStr+='<a onclick=del("'+row["id"]+'")>删除</a>';
	return returnStr;
}

function modify(id){
	$('#baseForm').form('clear');
	$.ajax({
		type : "POST",
		url : path + '/crowdfunding/getBackSetById.html',
		dataType:'json',
		data : {
			'id' : id
		},
		success : function(data) {
			$('#baseForm').form('load', data);
			if (data.photoUrl1) {
				$('#photoDiv .filelist').html('<img src="'+fileUrl+data.photoUrl1+'" style="width:200px; height:200px;"/>');
			}else{
				$('#photoDiv .filelist').html('');
			}
			
			if(data.backType == 'S'){
				$("#transFeeDiv").show();
			}else{
				$("#transFeeDiv").hide();
				$("input[name='transFee']").val(0);
			}
			if(data.prizeDrawFlag==1){
				$("#prizeDiv").show();
			}else{
				$("#prizeDiv").hide();
			}
			
			createWebUploader('photoBtn','photoDiv','photoUrl1');
		}
	});
}
function del(id){
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfunding/deleteBackSet.html',
				data : {
					'id' : id
				},
				success : function() {
					searchData('/crowdfunding/getBackSetList.html?loanNo='+loanNo);
					$("#loan_no").val(loanNo);
				}
			});
		}
	});
}

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
	    fileSizeLimit: 5 * 1024 * 1024,    // 5 M
	    fileSingleSizeLimit: 1 * 1024 * 1024    // 50 M
	});
	
	uploader.on('uploadSuccess', function (file, ret) {
//        var $file = $('#' + file.id);
        try {
            var responseText = (ret._raw || ret),
                json = $.parseJSON(responseText);
            if (json.state == 'SUCCESS') {
            	$('#'+showId+' .filelist').html('<img src="'+fileUrl+json.url+'" style="width:200px; height:200px;"/>');
            	$('#baseForm input[name="'+setValueId+'"]').val(json.url);
            } else {
//                $file.find('.error').text(json.state).show();
            }
        } catch (e) {
//            $file.find('.error').text(lang.errorServerUpload).show();
        }
    });
	
	return uploader;
}

