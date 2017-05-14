$(function(){
	searchData("/crowdfundingBonus/getLoanBonusList.html?getBonusState=state");
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
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
        			//queryToGrid("progectTable", "list_search");
        			$('#progectTable').datagrid("reload");
        			 //location.reload();
        		},
        		error: function(){
        			console.log("审核项目异常");
        		}
        	});
        }
    }); 
	
	//通过按钮事件
	$('#pass').click(function(){
		bonusAuditPassed();
	});
	//拒绝按钮
	$('#refuse').click(function(){
		bonusAuditRefuse();
	});
    
});

/**
 * 分红审核通过
 */
var sendLoanMask;
function bonusAuditPassed(){
	var loanNo = $("#loanNo1").val();
	var loanBonusId = $("#loanBonusId").val();
	var auditOpinion = $("#auditOpinion").val();
	if(!auditOpinion){
		$.messager.alert("提示","请填写审核意见！");
	}
	$.messager.confirm('提示', '您确定同意分红吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + "/crowdfundingBonus/updateLoanBonusState.html",
				data : {
					'loanNo' : loanNo,
					'loanBonusId' : loanBonusId,
					'auditOpinion':auditOpinion,
					'auditState':'passed'
				},
				beforeSend:function(XMLHttpRequest){
					sendLoanMask=getMask('正在处理分红请求，请稍候！');
					var i = 0;
				    time = setInterval(function(){
						if(i=="3600000"){
							sendLoanMask.dialog('close');
							clearInterval(time);
						}
						i+=5000;
						$.ajax({
							type : "POST",
							url : path + '/crowdfundingBonus/selectBounsAuditStatus.html',
							data : {
								'id' : loanBonusId
							},
							success : function(data) {
								if (data.success) {
									if(data.msg){
										$.messager.alert('提示', '分红处理成功');
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
										clearInterval(time);
									}
									//显示已完成代收的数量
									if(i=="3600000"){
										$.messager.alert('提示',"分红处理失败！");
										clearInterval(time);
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
	    							}
								}else{
									//显示已完成代收的数量
									if(i=="3600000"){
										$.messager.alert('提示',"分红处理失败！");
										clearInterval(time);
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
	    							}
								}
							},
					        complete:function(XMLHttpRequest,textStatus){
					        	
					        }
						});
					},5000);
		        },
				success : function(data) {
					if (data.success) {
						$("#add").show().dialog('close');
						queryToGrid("progectTable", "list_search");
					}else{
						$.messager.alert('提示', data.msg+'，请联系管理员。');
						clearInterval(time);
						sendLoanMask.dialog('close');
					}
				},
		        complete:function(XMLHttpRequest,textStatus){
		        	
		        }
			});
		}
	});
} 	




/**
 * 分红审核拒绝
 */
var sendLoanMask;
function bonusAuditRefuse(){
	
	var loanNo = $("#loanNo1").val();
	var loanBonusId = $("#loanBonusId").val();
	var auditOpinion = $("#auditOpinion").val();
	if(!auditOpinion){
		$.messager.alert("提示","请填写审核意见！");
	}
	$.messager.confirm('提示', '您确定拒绝分红吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + "/crowdfundingBonus/updateLoanBonusState.html",
				data : {
					'loanNo' : loanNo,
					'loanBonusId' : loanBonusId,
					'auditOpinion':auditOpinion,
					'auditState':'refuse'
				},
				beforeSend:function(XMLHttpRequest){
					sendLoanMask=getMask('正在处理请求，请稍候！');
					var i = 0;
				    time = setInterval(function(){
						if(i=="3600000"){
							sendLoanMask.dialog('close');
							clearInterval(time);
						}
						i+=5000;
						$.ajax({
							type : "POST",
							url : path + '/crowdfundingBonus/selectBounsAuditRefuseStatus.html',
							data : {
								'id' : loanBonusId
							},
							success : function(data) {
								if (data.success) {
									if(data.msg){
										$.messager.alert('提示', '操作成功');
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
										clearInterval(time);
									}
									//显示已完成代收的数量
									if(i=="3600000"){
										$.messager.alert('提示',"操作失败！");
										clearInterval(time);
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
	    							}
								}else{
									//显示已完成代收的数量
									if(i=="3600000"){
										$.messager.alert('提示',"操作失败！");
										clearInterval(time);
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
	    							}
								}
							},
					        complete:function(XMLHttpRequest,textStatus){
					        	
					        }
						});
					},5000);
		        },
				success : function(data) {
					if (data.success) {
						$("#add").show().dialog('close');
						queryToGrid("progectTable", "list_search");
					}else{
						$.messager.alert('提示', data.msg+'，请联系管理员。');
						clearInterval(time);
						sendLoanMask.dialog('close');
					}
				},
		        complete:function(XMLHttpRequest,textStatus){
		        	
		        }
			});
		}
	});
} 	



















function searchData(dUrl){
	var columns = [[
	                	{field:'view',title:'操作',width:120,align:'center',formatter:operateData},
		                {field:'id',title:'id',width:0,hidden:true},
						{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
						{field:'loanName',title:'项目名称',width:250,align:'center'},
						{field:'loanUser',title:'借款人',width:100,align:'center'},
						{field:'realName',title:'借款人真实姓名',width:100,align:'center'},
						{field:'bonusAmt',title:'分红金额('+digital_goods_unit+')',width:100,align:'right',formatter:NumberFormat},
						{field:'bonusNum',title:'分红次数',width:100,align:'center'},
						{field:'bonusTime',title:'分红时间',width:150,align:'center'},
						/*{field:'bonusAuditState',title:'分红状态',width:100,align:'center'},*/
						{field:'bonusAuditStateName',title:'分红状态',width:100,align:'center'}
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

function setShareRatio(val,row,index){
	return (Number(val).toFixed(4)*100).toFixed(2) + '%';
}


function auditAuth(id,auditState){
	
	if(auditState == 'passed'){
		$.messager.confirm('提示', '您确定要审核通过吗？', function(r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfunding/updateLoanBonusState.html',
				data:{
					"bonusAuditState" :auditState,
					"id" :id
				},
				success : function(data) {
					if(data.success){
						$.messager.alert("提示","审核完成！");
					}
					queryToGrid("progectTable", "list_search");
				}
			});
		});
	}else if(auditState == 'refuse'){
		$.messager.confirm('提示', '您确定要审核拒绝吗？', function(r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfunding/updateLoanBonusState.html',
				data:{
					"bonusAuditState" :auditState,
					"id" :id
				},
				success : function(data) {
					if(data.success){
						$.messager.alert("提示","审核完成！");
					}
					queryToGrid("progectTable", "list_search");
				}
			});
		});
	}
	
}


function operate(loanNo,loanBonusId){
	$("#auditform input[name='loanNo']").val(loanNo);
	$("input[name='loanBonusId']").val(loanBonusId);
	$("#add").show().dialog({
		title: "分红审核操作",
		height: 230,
		modal : true,
		onClose: function () {
            $("#auditform").form('clear');
        }
	});
}


//审核流程
function openAudit(id){
	var columns = [[
	                
					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:110,align:'center',sortable:true},
					{field:'auditor',title:'审核人',width:100,align:'center',sortable:true},
					{field:'auditStateName',title:'操作动作',width:100,align:'center',sortable:true},
					{field:'bonusStateName',title:'分红审核状态',width:100,align:'center',sortable:true},
					{field:'auditOpinion',title:'意见',width:110,align:'center',sortable:true},
					{field:'auditTime',title:'操作时间',align:'center',sortable:true}
				]];
	$('#loanBonusAuditTable').datagrid({
		url:path + '/crowdfundingBonus/getLoanAuditBonusPage.html?loanBonusId='+id,
		columns: columns,
		height: 314,
		rownumbers : true,
		pagination: true
	});
	$("#loanBonusAudit").show().dialog({
		title: "审批列表",
		height: 350,
		width:810,
		modal: true,
		onClose: function () {
        }
	});
}




//分红明细
function openBonusDetailList(id){
	var columns = [[

					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:110,align:'center',sortable:true},
					{field:'getRealName',title:'支持人',width:100,align:'center',sortable:true},
					{field:'investAmt',title:'支持金额',width:100,align:'center',sortable:true},
					{field:'supportTime',title:'支持时间',width:150,align:'center',sortable:true},
					{field:'assignAmt',title:'分红金额',width:110,align:'right',sortable:true,formatter:NumberFormat},
					{field:'assignTime',title:'分红时间',width:150,align:'center',sortable:true},
					{field:'bonusNum',title:'分红次数',width:110,align:'center',sortable:true}
				]];
	$('#loanBonusDetailListTable').datagrid({
		url:path + '/crowdfundingBonus/getRewardAssignList.html?loanBonusId='+id,
		columns: columns,
		height: 314,
		rownumbers : true,
		pagination: true
	});
	$("#loanBonusDetailList").show().dialog({
		title: "分红明细",
		height: 350,
		width:810,
		modal: true,
		onClose: function () {
      }
	});
}
function NumberFormat(val,row,index){
	if(!val){
		val = 0;
	}
	return val;
	//return  val.toFixed(2);
}
