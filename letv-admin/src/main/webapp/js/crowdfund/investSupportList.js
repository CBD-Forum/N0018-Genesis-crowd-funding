//认购列表
$(function(){
	searchData("/crowdfundInvest/getOrderSupportList.html?loanNo="+loanNo);
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
});
function searchData(dUrl){
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'operat',title:'操作',width:100,align:'center',formatter:operateData},
					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:100,align:'center'},
					{field:'supportUser',title:'认购人',width:100,align:'center',formatter:displayLoanUser},
					{field:'supportUserName',title:'认购人真实姓名',width:100,align:'center'},
					{field:'mobile',title:'认购人电话',width:100,align:'center'},
					{field:'supportAmt',title:'认购金额',width:80,align:'center',sortable:true},
//					{field:'supportAmt',title:'领投人最小金额',width:80,align:'center',sortable:true},
					{field:'buyNum',title:'认购份数',width:80,align:'center',sortable:true}
				]];
	$('#progectTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-230,
		nowrap:false,
		rownumbers : true,
		pagination: true
	});
}

//置为领投人
function setLoanLeader(loanNo,leadInvestor){
	$.messager.confirm('提示', '您确定要将该认购人置为领投人吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundInvest/setLoanLeader.html',
				data : {
					'loanNo' : loanNo,
					'leadInvestor':leadInvestor
				},
				success : function(data) {
					if(data.success){
						queryToGrid("progectTable", "list_search");
					}else{
						$.messager.alert('提示', data.msg,'warning');
					}
					
				}
			});
		}
	});
}
