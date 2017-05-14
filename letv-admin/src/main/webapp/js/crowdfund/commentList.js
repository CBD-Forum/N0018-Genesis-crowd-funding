$(function(){
	searchData("/crowdfunding/getCommentList.html");
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("commentTable", "list_search");
    });
	
});
//查询项目列表
function searchData(dUrl){
	//获取字典数据
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'operat',title:'操作',width:100,align:'center',formatter:operateData},
					{field:'loanNo',title:'项目编号',width:120,align:'center'},
					{field:'loanName',title:'项目名称',width:150,align:'center'},
					{field:'discussUser',title:'评论人',width:120,align:'center'},
					{field:'discussUserName',title:'评论人真实姓名',width:100,align:'center'},
					{field:'discussTime',title:'评论时间',width:150,align:'center'},
					{field:'content',title:'评论内容',width:380,align:'center',formatter:formatCellTooltip},
					{field:'stateName',title:'状态',width:80,align:'center'}
				]];
	$('#commentTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-265,
		nowrap:false,
		rownumbers : true,
		pagination: true
	});
}

function del(id){
	$.ajax({
		type : "POST",
		url : path + '/crowdfunding/delComment.html',
		data : {
			'id' : id
		},
		success : function() {
			queryToGrid("commentTable", "list_search");
		}
	});
}


