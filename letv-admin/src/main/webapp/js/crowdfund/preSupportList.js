//预购列表
$(function(){
	searchData("/crowdfundInvest/getPreSupportList.html?loanNo="+loanNo);
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
});
function searchData(dUrl){
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'orderId',title:'预购单号',width:120,align:'center',sortable:true},
					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:100,align:'center'},
					{field:'supportUser',title:'预购人',width:100,align:'center'},
					{field:'supportUserName',title:'预购人真实姓名',width:100,align:'center'},
					{field:'supportAmt',title:'预购金额',width:80,align:'center',sortable:true},
					/*{field:'buyNum',title:'预购份数',width:80,align:'center',sortable:true},*/
					{field:'supportTime',title:'预购时间',width:140,align:'center',sortable:true}
				]];
	$('#progectTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-200,
		nowrap:false,
		rownumbers : true,
		pagination: true
	});
}
