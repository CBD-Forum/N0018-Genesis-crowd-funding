$(function(){
	searchData("/crowdfunding/getUserPrizeList.html?loanNo="+loanNo);
});
//查询项目列表
function searchData(dUrl){
	//获取字典数据
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'loanNo',title:'项目编号',width:120,align:'center'},
	                {field:'prizeUser',title:'抽奖人',width:120,align:'center'},
	                {field:'realName',title:'抽奖人真实姓名',width:140,align:'center'},
					{field:'prizeNo',title:'抽奖单号',width:180,align:'center'},
					{field:'isPrizeName',title:'是否中奖',width:80,align:'center'},
					{field:'prizeTime',title:'操作时间',width:80,align:'center'}
				]];
	$('#prizeTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-190,
		rownumbers : true,
		nowrap:false,
		pagination: true
	});
}


