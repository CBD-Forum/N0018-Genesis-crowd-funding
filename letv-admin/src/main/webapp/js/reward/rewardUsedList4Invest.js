$(function(){
	$('<div id="rewardUsedDiv"><table id="rewardUsedTable"></table></div>').hide().appendTo('body');
//	alert();
	//获取字典数据
	var columns = [[
					{field:'rewardName',title:'名称',width:120,align:'center',sortable:true},
					{field:'rewardTypeText',title:'类型',width:120,align:'center',sortable:true},
					{field:'rewardSourceText',title:'来源',width:110,align:'center',sortable:true},
					{field:'rewardAmt',title:'优惠券金额',width:100,align:'center',sortable:true},
					{field:'userStateText',title:'使用状态',width:100,align:'center',sortable:true},
					{field:'useTime',title:'使用时间',width:130,align:'center',sortable:true}
				]];
	$('#rewardUsedTable').datagrid({
		columns: columns,
		height: 364,
//		pagination: true,
		rownumbers : true
	});
});

function openRewardUsed(investNo){
	$('#rewardUsedTable').datagrid('options').url = path + '/reward/getList4Invest.html';
	$('#rewardUsedTable').datagrid("reload",{'investNo':investNo});
	$("#rewardUsedDiv").show().dialog({
		title: "红包使用情况",
		height: 400,
		width:730,
		modal: true,
		onClose: function () {
        }
	});
}