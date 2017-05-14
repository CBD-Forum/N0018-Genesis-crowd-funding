//查询领投人
$(function(){
	searchData("/crowdfundInvest/getLoanLeader.html?loanNo="+loanNo);
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
});
function searchData(dUrl){
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'operat',title:'操作',width:100,align:'center',formatter:operateData},
	                {field:'leadInvestor',title:'领投人',width:100,align:'center'},
	                {field:'mobile',title:'领投人电话',width:100,align:'center'},
	                {field:'certificateNo',title:'领投人身份证号',width:150,align:'center'},
	                {field:'loanNo',title:'项目编号',width:100,align:'center'},
	                {field:'companyName',title:'公司名称',width:120,align:'center'}
				]];
	$('#progectTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-140,
		nowrap:false,
		rownumbers : true,
		pagination: true
	});
}

//取消该领投人
function cancelLoanLeader(id){
	$.messager.confirm('提示', '您确定要将该领投人取消资格吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundInvest/cancelLoanLeader.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("progectTable", "list_search");
				}
			});
		}
	});
}
