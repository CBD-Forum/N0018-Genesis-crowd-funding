$(function(){
//	var rewardTypeStr = '<option value="">-=请选择=-</option><option value="投标奖励">投标奖励</option><option value="直接推荐奖励">直接推荐奖励</option><option value="间接推荐奖励">间接推荐奖励</option>';
//	$("#rewardType").html(rewardTypeStr);
	
	searchData("/rewardAssign/getRewardAssignList.html?loanNo="+loanNo);
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("assignTable", "list_search");
    });
});
//查询奖励明细数据
function searchData(url){
	//获取字典数据
	var columns = [[
					{field:'loanNo',title:'项目编号',width:140,align:'center'},
					{field:'loanName',title:'项目名称',width:140,align:'center'},
					{field:'investNo',title:'投资编号',width:140,align:'center'},
					{field:'investor',title:'投资人',width:120,align:'center'},
					{field:'investAmt',title:'投资金额(令)',width:120,align:'center'},
					{field:'getUser',title:'收款人',width:120,align:'center'},
					{field:'getNickName',title:'收款人昵称',width:120,align:'center'},
					{field:'getMobile',title:'收款人手机号',width:120,align:'right'},
					{field:'assignTime',title:'分红时间',width:150,align:'center'},
					{field:'assignAmt',title:'分红金额(令)',width:100,align:'right'},
					{field:'rewardTypeName',title:'奖励类型',width:100,align:'center'},
					{field:'loanUser',title:'付款人',width:100,align:'center'},
					{field:'payUserName',title:'付款人昵称',width:100,align:'center'},
					{field:'loanUserMobile',title:'付款人手机号',width:120,align:'right'},
					{field:'bonusNum',title:'分红次数',width:100,align:'center'},
					{field:'bonusStateName',title:'分红状态',width:100,align:'center'}
				]];
	
	
	$('#assignTable').datagrid({
		url: path + url,
		columns: columns,
		height:bodyHeight-230,
		rownumbers : true,
		singleSelect: true,
		pagination: true
	});
}
