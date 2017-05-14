$(function(){
	//获取字典数据
	var columns = [[
					{field:'SubAcctId',title:'子账户号',width:150,align:'center'},
					{field:'AcctType',title:'账户类型',width:200,align:'center',formatter:convertToName},
					{field:'AvlBal',title:'可用余额(元)',width:200,align:'right'},
					{field:'FrzBal',title:'冻结金额(元)',width:200,align:'right'},
					{field:'AcctBal',title:'账户总额(元)',width:200,align:'right'}
				]];
	
	var addBtn = { 
	     	text: '转账', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	showTransfer();
	        } 
	      };
	var operateBtns=[];
	if ($('#finance_thirdAccount_transfer').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#thirdAcctTable').datagrid({
		url: path + "/third/queryAcct/sendQueryAcct.html",
		columns: columns,
		pagination: true,
		singleSelect: true,
		height : bodyHeight - 140,
		rownumbers : true,
		toolbar: operateBtns
	});
	
	$("#transferForm").validate({
		rules : {
			outChildAcct : "required",
			inChildAcct : "required",
			transferAmt :  {
				required:true,
				number:true
			}
		},
		messages : {
			outChildAcct : "请填写转出账户",
			inChildAcct : "请填写转入账户",
			rewardAmt : {
				required:"请填写金额"
			}
		},								
        submitHandler:function(form){
        	form.submit();
        }
    }); 
	$('#saveBtn').click(function() {
		$('#transferForm').submit();
	});
	
	//关闭添加窗口
	$('#closeBtn').click(function(){
		$("#transfer").dialog('close');
	});
	if($("#msg").text()){
		$.messager.alert('提示', $("#msg").text());
	}
});
function convertToName(val,row,index){
	if(val=='BASEDT'){
		return "基本账户";
	}else if(val == 'MERDT'){
		return "专属账户";
	}else if(val == 'SPEDT'){
		return "专用户";
	}
}
function showTransfer(){
	$("#transfer").show().dialog({
		title: "转账",
		height: 250,
		width: 500,
		modal : true,
		onClose: function () {
            $("#transferForm").form('clear');
        }
	});
}