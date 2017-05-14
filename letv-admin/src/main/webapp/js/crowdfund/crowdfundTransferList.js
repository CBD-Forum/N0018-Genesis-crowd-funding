$(function(){
	searchData("/crowdfundingInvestTransfer/getCrowdfundingTransferAuditList.html");
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("table", "list_search");
    });
    
    
	$("#auditform").validate({ 
        rules: {
        	auditOpinion: "required"
        },  
        messages: {  
        	auditOpinion: "请填写审核意见" 
        },
        submitHandler:function(form){
        	$('#auditform').form('submit', {
        		url : form.action,
        		success : function(data) {
        			$('#add').dialog("close");
        			// $("#auditform").form('clear');
        			//queryToGrid("progectTable", "list_search");
        			
        			 location.reload();
        		},
        		error: function(){
        			console.log("审核项目异常");
        		}
        	});
        }
    }); 
	
	//通过按钮事件
	$('#pass').click(function(){
		$('#auditform').attr('action',path + "/crowdfundingInvestTransfer/auditTransfer.html");
		$('#auditState').val('audit_pass');
		$('#auditform').submit();
	});
	//拒绝按钮
	$('#refuse').click(function(){
		$('#auditform').attr('action',path + "/crowdfundingInvestTransfer/auditTransfer.html");
		$('#auditState').val('audit_refuse');
		$('#auditform').submit();
	});
    
    
});
//查询项目列表
function searchData(dUrl){
	//获取字典数据
	var columns = [[
	                {field:'operat',title:'操作',width:120,align:'center',formatter:operateData},
	                {field:'transferNo',title:'挂牌编号',width:120,align:'center',sortable:true},
	                {field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:100,align:'center'},
					{field:'transferMoney',title:'转让金额',width:80,align:'center'},
					{field:'transferUser',title:'转让人',width:80,align:'center'},
					{field:'transferParts',title:'转让份数',width:80,align:'center'},
					{field:'transferCorpus',title:'转让本金',width:100,align:'center'},
					{field:'transferFee',title:'转让手续费',width:100,align:'center'},
					{field:'transferDay',title:'转让天数(天)',width:80,align:'center',sortable:true},
					{field:'statusName',title:'转让状态',width:100,align:'center'},
					{field:'auditStateName',title:'审核状态',width:100,align:'center'},
					{field:'apply_time',title:'申请时间',width:130,align:'center',sortable:true},
					{field:'deadline',title:'结束日期',width:130,align:'center',sortable:true},
					{field:'auditTime',title:'审核日期',width:130,align:'center',sortable:true},
					{field:'auditOpinion',title:'审核意见',width:300,align:'center',sortable:true},
					{field:'buyParts',title:'成功转让份数',width:80,align:'center'},
					{field:'buyMoney',title:'成功转让金额',width:100,align:'center'},
					{field:'butFee',title:'成功转让手续费',width:100,align:'center'}
				]];
	$('#table').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-225,
		rownumbers : true,
		nowrap:false,
		pagination: true
	});
}

//编辑项目
function edit(transferNo){
	var url = '/crowdfund.crowdfundTransferEdit.html?transferNo='+transferNo;
	window.location.href=path+url;
}
function operate(id){
	$("#id").val(id);
	$("#add").show().dialog({
		title: "挂牌审核操作",
		height: 230,
		modal : true,
		onClose: function () {
            $("#auditform").form('clear');
        }
	});
}
//审核通过事件
function auditPass(){
	$('#auditform').submit();
}
