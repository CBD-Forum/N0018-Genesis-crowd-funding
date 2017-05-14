$(function(){
	getRewardNode();
	var columns = [[
					{field:'rewardNo',title:'编号',width:100,align:'center',sortable:true},
					{field:'rewardName',title:'名称',width:100,align:'center'},
					{field:'rewardTypeName',title:'类型',width:100,align:'center'},
					{field:'rewardSourceName',title:'来源',width:100,align:'center',sortable:true},
					{field:'rewardAmt',title:'金额(元)',width:100,align:'center'},
					{field:'lowerLimit',title:'使用下限(元)',align:'center'},
					{field:'genTime',title:'发放日期',width:150,align:'center'},
					{field:'effectTime',title:'失效日期',width:150,align:'center'},
					{field:'userId',title:'用户名',width:100,align:'center'},
					{field:'rewardStateName',title:'状态',width:100,align:'center'},
					{field:'useTime',title:'使用时间',width:150,align:'center'},
					{field:'investNo',title:'投资编号',width:130,align:'center'},
					{field:'investAmt',title:'投资金额(元)',width:100,align:'right'},
					{field:'rechargeAmt',title:'实际支付金额(元)',width:100,align:'right'},
					{field:'loanNo',title:'项目编号',width:130,align:'center'},
					{field:'loanName',title:'项目名称',width:200,align:'center'}
				]];
	
	//根据优惠券节点获取优惠券名称下拉框
	$('#s_node').change(function(){
		getRewardNameSelectData($(this).val());
	});
	
	var exportBtn = { 
		     	text: '导出', 
		        iconCls: 'icon-redo', 
		        handler: function() { 
		        	commonExportExcel('reward_coupon_deatil_export');
		        } 
		      };
		var operateBtns=[];
		if ($('#reward_coupon_deatil_export').length>0) {
			operateBtns.push(exportBtn);
		}
	var sendRewardBtn = { 
		     text: '发送红包', 
		     iconCls: 'icon-add', 
		     handler: function() { 
	    	   $("#sendRewardDiv").show().dialog({
	    			title: "发送红包",
	    			height: 350,
	    			width:550,
	    			modal: true,
	    			onClose: function () {
	    				$('#sendRewardForm').form('clear').form('reset');
	    	        }
	    		});
		     } 
		  };
	operateBtns.push(sendRewardBtn);
		if (operateBtns.length==0) {
			operateBtns=null;
		}
		
		
	$('#userRewardTable').datagrid({
		url: path + "/reward/getUserRewardList.html",
		columns: columns,
		pagination: true,
		singleSelect: true,
		height : bodyHeight - 230,
		rownumbers : true,
		toolbar:operateBtns
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		queryToGrid("userRewardTable", "list_search");
	});
	
	$('#resetBtn').click(function(){
    	$('#list_search').form('clear').form('reset');
    });
	
	
	$("#sendRewardForm").validate({
		rules : {
			node : {
				required: true
			},
			rewardNo : {
				required: true
			},
			userId : {
				required: true
			},
			genTime : {
				required: true
			}
		},
		messages : {
			node : {
				required : "请选择优惠券节点"
			},
			rewardNo : {
				required : "请选择优惠券名称"
			},
			userId : {
				required : "请填写接收人用户名"
			},
			genTime : {
				required : "请选择发送时间"
			}
		},				
        submitHandler:function(form){
        	$.messager.confirm('提示', '红包一旦发送将不能收回，您确定要给 '+$('#userId').val()+' 发送红包吗？', function(r) {
        		if (r) {
        			$.ajax({
        				url: path + "/user/getUserRealName.html",
        				type: "post",
        				dataType: "json",
        				data: {"userId": $('#userId').val()},
        				success: function(data){
        					if (data.success) {
        						var url=path + '/reward/saveUserReward.html';
        	            		$('#sendRewardForm').form('submit', {
        	            			url : url,
        	            			success : function(data) {
        	            				var obj = $.parseJSON(data);
        	            				if (obj.success) {
        	            					$("#sendRewardDiv").dialog('close');
        	            					queryToGrid("userRewardTable", "list_search");
        	            				}
        	            			}
        	            		});
							}else{
								$.messager.alert('提示', data.msg);
							}
        				},
        				error: function(){
        					console.log("获取优惠券节点异常");
        				}
        			});
        		}
        	});
        }
    });
	
	// 添加
	$('#saveBtn').click(function() {
		$('#sendRewardForm').submit();
	});
	
	//关闭添加窗口
	$('#closeBtn').click(function(){
		$("#sendRewardDiv").dialog('close');
	});
	
	
});

function getRewardNameSelectData(rewardNode,callback){
	$.ajax({
		url: path + "/reward/getRewardByNode.html",
		type: "post",
		dataType: "json",
		data: {"node": rewardNode},
		success: function(data){
			    getRewardData('s_rewardName',data);
			    if (callback) {
			    	callback();
				}
		},
		error: function(){
			console.log("获取优惠券节点异常");
		}
	});
}
//获取优惠券节点
function getRewardNode(){
	$.ajax({
		url: path + "/dictionary/getDic.html",
		type: "post",
		dataType: "json",
		data: {"type": "reward_source"},
		success: function(data){
			getSelectData("s_node", data["rows"]);
		},
		error: function(){
			console.log("获取优惠券节点异常");
		}
	});
}

function getSelectData(id, data){
	var str = '', arr = [];
	arr.push("<option value=''>请选择</option>");
	for(var i=0,tlength=data.length;i<tlength;i++){
		arr.push('<option value="'+data[i]["code"]+'">'+data[i]["displayName"]+'</option>');
	}
    str = arr.join("");
	$("#" + id).html(str);
}

/**
 * @param id: 要填充的select的id
 * @param data： 填充的select的html内容
 */
function getRewardData(id, data){
	var str = '', arr = [];
	arr.push("<option value=''>请选择</option>");
	for(var i=0,tlength=data.length;i<tlength;i++){
		arr.push('<option value="'+data[i]["rewardNo"]+'">'+data[i]["rewardName"]+'</option>');
	}
    str = arr.join("");
	$("#" + id).html(str);
}
