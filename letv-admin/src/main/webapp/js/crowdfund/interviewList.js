$(function(){
	searchData("/crowdfundInvest/getInterviewRecord.html");
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
});
function searchData(dUrl){
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'operat',title:'操作',width:100,align:'center',formatter:operateData},
	                {field:'loanNo',title:'项目编号',width:100,align:'center'},
	                {field:'loanName',title:'项目名称',width:150,align:'center'},
	                {field:'loanTypeName',title:'项目类型',width:100,align:'center'},
	                {field:'fundAmt',title:'融资金额',width:100,align:'right'},
	                {field:'applyUser',title:'约谈人',width:120,align:'center',sortable:true},
					{field:'applyUserMobile',title:'约谈人手机号',width:120,align:'center',sortable:true},
					{field:'applyRealName',title:'约谈人用户名',width:100,align:'center'},
					{field:'receiveUser',title:'发起人',width:100,align:'center'},
					{field:'receiveUserMobile',title:'发起人手机号',width:100,align:'center'},
					{field:'receiveRealName',title:'发起人真实姓名',width:100,align:'center'},
					{field:'interviewTime',title:'约谈时间',width:150,align:'center',sortable:true},
					{field:'stateName',title:'状态',width:80,align:'center',sortable:true},
					{field:'interviewContent',title:'约谈内容',width:380,align:'center',formatter:formatCellTooltip}
					/*{field:'interviewContent',title:'约谈内容',width:300,align:'center',formatter:showContent}*/
				]];
	$('#progectTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-225,
		nowrap:false,
		rownumbers : true,
		pagination: true
	});
}


function operate(id){
	$.ajax({
		type : "POST",
		url : path + '/crowdfundInvest/processInterview.html',
		data : {
			'id' : id
		},
		success : function() {
			queryToGrid("progectTable", "list_search");
		}
	});
}


function showContent(val,row,index){
	var interviewContent = row.interviewContent;
	
	if(!isNull(interviewContent)){
		if(interviewContent.length>50){
			return '<span title="'+interviewContent+'">'+interviewContent.substring(0,20)+"......"+'</span>';
		}else{
			return interviewContent;
		}
	}
}

