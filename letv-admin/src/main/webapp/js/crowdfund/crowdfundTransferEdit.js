var sData={};
$(function(){
	$('#add').height(bodyHeight-150);
	$('#add').width(bodyWidth-160);
	
	$('#tt').tabs({
		height:bodyHeight-150,
	    onSelect:function(title){
	    }   
	});
		$.ajax({
			url: path + '/crowdfundingInvestTransfer/getCrowdfundingTransferDetial.html?transferNo=' + transferNo,
			type: "post",
			dataType: "json",
			success: function(data){
				$('#transferForm').form('load', data);
			}
		});
	
	
	//保存基本信息
	$('#basicBtn').click(function() {
		$('#transferForm').submit();
	});
	
	$("#transferForm").validate({
		rules : {
			transferParts :  {
				required:true,
				number:true,
				digits:true
			},
			partMoney :  {
				required:true,
				number:true
			},
			transferDay :  {
				required:true,
				number:true,
				digits:true
			}
		},
		messages : {
			transferParts : {
				required:"请填写转让份数",
				digits:"请正确填写转让份数"
			},
			partMoney : {
				required:"请填写每份价值"
			},
			transferDay : {
				required:"请填写转让天数",
				digits:"请输入正确的转让天数"
			}
		},
        submitHandler:function(form){
        	check();
        }
    }); 
	
});


function check(){
	if($("#partMoney").val()<=0){
		$("#transferForm input[name='partMoney']").parent().next().show().html("挂牌金额不能小于等于0");
		return false;
	}else{
		$("#transferForm input[name='partMoney']").parent().next().show().html("");
	}
	$.ajax({
		url: path + "/crowdfundingInvestTransfer/getCanTransferParts.html",
		type: "post",
		dataType: "json",
		data: {
			"orderNo": $("#orderNo").val()
			},
		success: function(data){
			if(data["success"]){
				if(data["msg"]<$("#transferForm input[name='transferParts']").val()){
					$("#transferForm input[name='transferParts']").parent().next().show().html('挂牌份数不能超过'+data["msg"]);
	        		return false;
				}else if($("#transferForm input[name='transferParts']").val()==0){
					$("#transferForm input[name='transferParts']").parent().next().show().html('挂牌份数不能等于0');
	        		return false;
				}else{
					$("#transferForm input[name='transferParts']").parent().next().show().html('');
					var url=path + '/crowdfundingInvestTransfer/updateCrowdFundTransfer.html';
		        	$('#transferForm').form('submit', {
		    			url : url,
		    			success : function(data) {
		    				data = $.parseJSON(data);
		    				if(data["success"]){
		    					$.messager.alert('提示', '保存成功');
		    				}else{
		    					
		    				}
		    			}
		    		});
					return true;
				}
			}
		},
		error: function(request){
			console.log("查询可转让份数失败");
		}
	});
}


