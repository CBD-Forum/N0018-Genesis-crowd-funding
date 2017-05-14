$(function(){
	var columns = [[
                    {field:'operate',title:'操作',width:120,align:'center',formatter:operateData},
					{field:'rewardNo',title:'编号',width:150,align:'center',sortable:true},
					{field:'rewardName',title:'名称',width:200,align:'center'},
					{field:'rewardTypeName',title:'类型',width:200,align:'center'},
					{field:'rewardSourceName',title:'来源',width:200,align:'center',sortable:true},
					{field:'rewardAmt',title:'金额(元)',width:150,align:'center'},
					{field:'lowerLimit',title:'使用下限(元)',align:'center'},
					{field:'effectiveDays',title:'有效期(天)',width:200,align:'center'}
				]];
	
	var addBtn = { 
	     	text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	showAdd();
	        } 
	      };
	var operateBtns=[];
	if ($('#reward_coupon_base_add').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#rewardTable').datagrid({
		url: path + "/reward/getRewardList.html",
		columns: columns,
		pagination: true,
		singleSelect: true,
		height : bodyHeight - 193,
		rownumbers : true,
		toolbar: operateBtns
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		queryToGrid("rewardTable", "list_search");
	});
	
	$('#resetBtn').click(function(){
    	$('#list_search').form('reset');
    });
	
	$("#rewardForm").validate({
		rules : {
			rewardNo : {
				required: true,
                maxlength:64
			},
			rewardName : {
				required: true,
				maxlength:100
			},
			rewardType : "required",
			rewardSource : "required"
		},
		messages : {
			rewardNo : {
				required: "请填写优惠券编号",
                maxlength:"编号不能超过64个字符"
			},
			rewardName : {
				required: "请填写优惠券名称",
				maxlength:"名称不能超过100个字符"
			},
			rewardType : "请填写优惠券类型",
			rewardSource : "请填写优惠券来源"
		},							
        submitHandler:function(form){
        	
        	
        	var rewardAmt =$("#rewardAmt").val();
        	if(!rewardAmt){
        		$("#rewardAmtspan").html("数量不能为空");
        		return false;
        	}
        	if(isNaN(rewardAmt) || Number(rewardAmt) <= 0){
        		$("#rewardAmtspan").html("请正确填写数量");
        		return false;
        	}
        	$("#rewardAmtspan").html("");
        	
        	
        	var lowerLimit =$("#lowerLimit").val();
        	if(!lowerLimit){
        		$("#lowerLimitspan").html("使用下限不能为空");
        		return false;
        	}
        	if(isNaN(lowerLimit) || Number(lowerLimit) <= 0){
        		$("#lowerLimitspan").html("请正确填写使用下限");
        		return false;
        	}
        	$("#lowerLimitspan").html("");
        	
        	
        	var effectiveDays =$("#effectiveDays").val();
        	if(!effectiveDays){
        		$("#effectiveDaysspan").html("有效期不能为空");
        		return false;
        	}
        	if(isNaN(effectiveDays) || Number(effectiveDays) <= 0){
        		$("#effectiveDaysspan").html("请正确填写有效期");
        		return false;
        	}
        	$("#effectiveDaysspan").html("");
        	
        	var url = path + '/reward/saveReward.html';
        	if ($('#rewardForm input[name="id"]').val()!='') {
    			url=path + '/reward/updateReward.html';
    		}
    		$('#rewardForm').form('submit', {
    			url : url,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#add").dialog('close');
    					queryToGrid("rewardTable", "list_search");
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
		$('#rewardForm').submit();
	});
	
	//关闭添加窗口
	$('#closeBtn').click(function(){
		$("#add").dialog('close');
	});
	
});

//显示添加字典窗口
function showAdd() {
	$('#rewardForm input[name="rewardNo"]').removeAttr("readonly");
	$("#add").show().dialog({
		title: "添加",
		height: 430,
		width: 550,
		modal : true,
		onClose: function () {
            $("#rewardForm").form('clear');
        }
	});
}

function modify(id){
	$('#rewardForm').form('load', path + '/reward/getById.html?id=' + id);
	$('#rewardForm input[name="rewardNo"]').attr("readonly","readonly");
	$("#add").show().dialog({
		title: "修改",
		height: 460,
		width: 550,
		modal : true,
		onClose: function () {
            $("#rewardForm").form('clear');
        }
	});
}