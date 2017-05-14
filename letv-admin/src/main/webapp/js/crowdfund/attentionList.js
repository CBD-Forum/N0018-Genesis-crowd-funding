$(function(){
	searchData("/crowdfunding/getLoanAttentionList.html");
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
});
function searchData(dUrl){
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'loanNo',title:'项目编号',width:180,align:'center'},
	                {field:'loanName',title:'项目名称',width:280,align:'center'},
	                {field:'loanTypeName',title:'项目类型',width:100,align:'center'},
	                {field:'fundAmt',title:'融资金额',width:100,align:'right'},
	                {field:'attentionUser',title:'关注用户',width:120,align:'center',sortable:true},
					{field:'attentionTime',title:'关注时间',width:180,align:'center',sortable:true},
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
