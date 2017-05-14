$(function(){
//	var rewardTypeStr = '<option value="">-=请选择=-</option><option value="投标奖励">投标奖励</option><option value="直接推荐奖励">直接推荐奖励</option><option value="间接推荐奖励">间接推荐奖励</option>';
//	$("#rewardType").html(rewardTypeStr);
	
	searchData("/rewardAssign/getRewardAssignList.html");
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("assignTable", "list_search");
    });
});
//查询奖励明细数据
function searchData(url){
	//获取字典数据
	var columns = [[
	                {field:'operat',title:'操作',width:60,align:'center',formatter:operateData},
					{field:'loanNo',title:'项目编号',width:140,align:'center'},
					{field:'loanName',title:'项目名称',width:140,align:'center'},
					{field:'investNo',title:'投资编号',width:140,align:'center'},
					{field:'investor',title:'投资人',width:120,align:'center'},
					{field:'investAmt',title:'投资金额('+digital_goods_unit+')',width:120,align:'right'},
					{field:'getUser',title:'收款人',width:120,align:'right'},
					{field:'assignTime',title:'分红时间',width:150,align:'right'},
					{field:'assignAmt',title:'分红金额('+digital_goods_unit+')',width:100,align:'right'},
					{field:'rewardTypeName',title:'奖励类型',width:100,align:'center'},
					{field:'payUserName',title:'付款人',width:100,align:'center'},
					{field:'bonusNum',title:'分红次数',width:100,align:'center'},
					{field:'bonusStateName',title:'状态',width:100,align:'center'}
				]];
	
	var addBtn = { 
	     	text: '导出', 
	        iconCls: 'icon-redo', 
	        handler: function() { 
	        	commonExportExcel('finance_rewardSend_detilList_export');
	        } 
	      };
	var operateBtns=[];
	if ($('#finance_rewardSend_detilList_export').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#assignTable').datagrid({
		url: path + url,
		columns: columns,
		height:bodyHeight-230,
		rownumbers : true,
		singleSelect: true,
		pagination: true,
		toolbar:operateBtns
	});
}

//导出按钮事件
function exportData(){
	$("#e_rewardType").val($("#list_search select[name='rewardType']").val());
	$("#e_getUserLike").val($("#list_search input[name='getUserLike']").val());
	$("#e_assignStartTime").val($("#list_search input[name='assignStartTime']").val());
	$("#e_assignEndTime").val($("#list_search input[name='assignEndTime']").val());
	$("#e_investor").val($("#list_search input[name='investor']").val());
	$("#e_loanNo").val($("#list_search input[name='loanNo']").val());
	$("#exprotForm").submit();
}

function rereissueBonus(id){
	$.ajax({
		type : "POST",
		url : path + '/crowdfunding/sendRereissueBonus.html',
		data : {
			'id' : id
		},
		success : function(data) {
			if(data.success){
				$.messager.alert('提示',"补发分红成功");	
			}else{
				$.messager.alert('提示', data.msg);	
			}
		}
	});
}



