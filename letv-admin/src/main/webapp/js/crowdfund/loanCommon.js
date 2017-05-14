//查询回报
function openBackSet(loanNo,loanType){
	if(loanType == 'public'){
		window.location.href=path+'/crowdfund.backAdd.html?loanNo='+loanNo;
	}else if(loanType == 'product'){  //产品
		window.location.href=path+'/crowdfund.backProductAdd.html?loanNo='+loanNo;
	}
	
//	else if(loanType == 'house' || loanType == 'stock'){
//		window.location.href=path+'/crowdfund.projectReturnEdit.html?loanNo='+loanNo;
//	}
}
//实物回报
function crowdfundback(loanNo){
	var backColumns = [[
						{field:'backNo',title:'回报编号',width:120,align:'center',sortable:true},
						{field:'amt',title:'支持金额',width:110,align:'center',sortable:true},
						{field:'numberLimits',title:'名额限制',width:80,align:'center',sortable:true,formatter:function(value,row,index){
							if (row.numberLimits == "0") {
								return '不限名额';
							}else{
								return row.numberLimits;
							}
						}},
						{field:'backTime',title:'回报时间',width:80,align:'center',sortable:true,formatter:function(value,row,index){
							if (row.backTime == "0") {
								return '项目结束后立即发送';
							}else{
								return '项目结束'+row.backTime+'天后立即发送';
							}
						}},
//						{field:'transFee',title:'运费',width:80,align:'center',sortable:true,formatter:function(value,row,index){
//							if (row.transFee == "0") {
//								return '免运费';
//							}else{
//								return transFee;
//							}
//						}},
						{field:'isDelivery',title:'是否快递',align:'center',sortable:true,formatter:function(value,row,index){
							if (row.isDelivery == "1") {
								return '是';
							}else{
								return '否';
							}
						}},
						{field:'backContent',title:'回报内容',width:200,align:'center',sortable:true}
					]];
		$('#loanBackTable').datagrid({
			url:path + '/crowdfunding/getBackSetList.html?loanNo='+loanNo,
			columns: backColumns,
			height: 314,
			nowrap:false,
			rownumbers : true
		});
		$("#loanBack").show().dialog({
			title: "回报设置列表",
			height: 350,
			width:810,
			modal: true,
			onClose: function () {
	        }
		});
}
//股权回报
function stockback(loanNo){
	$.ajax({
		type : "POST",
		url : path + '/crowdfunding/getStockBackByLoanNo.html',
		dataType:'json',
		data : {
			'loanNo' : loanNo
		},
		success : function(data) {
			$("#stockback .x-form-item .x-form-element label").each(function(){
				$(this).text('');
				if(this.id == 'investBonusRatio' || this.id == 'projectBonusRatio'){
					$(this).text((parseFloat(data[this.id])*100).toFixed(2));
				}else{
					$(this).text(data[this.id]);
				}
				
			});
			$("#stockback").show().dialog({
				title: "股权回报",
				height: 300,
				width:400,
				modal : true,
				onClose: function () {
		            $("#stockbackform").form('clear');
		        }
			});
		}
	});
}
//投资列表
function supportList(loanNo,loanType){
	if("stock"==loanType){
		window.location.href=path+'/crowdfund.stockSupportList.html?loanNo='+loanNo+'&loanType='+loanType;
	}else{
		window.location.href=path+'/crowdfund.supportList.html?loanNo='+loanNo+'&loanType='+loanType;
	}
	
}
//编辑项目
function editLoan(loanNo,loanType){
	var url = '';
//	if(loanType == 'public_service' || loanType == 'entity'){
//		url = '/crowdfund.editcrowdfund-public.html?loanNo='+loanNo;
//	}else if(loanType == 'house'){
//		url = '/crowdfund.editcrowdfund-house.html?loanNo='+loanNo;
//	}else if(loanType == 'stock'){
//		url = '/crowdfund.editcrowdfund-stock.html?loanNo='+loanNo;
//	}
	
	if(loanType == 'public'){  //公益众筹
		url = '/crowdfund.editcrowdfund-public.html?loanNo='+loanNo;
	}else if(loanType == 'product'){  //产品众筹
		url = '/crowdfund.editcrowdfund-product.html?loanNo='+loanNo;
	}else if(loanType == 'stock'){  //股权众筹
		url = '/crowdfund.editcrowdfund-stock.html?loanNo='+loanNo;
	}
	window.location.href=path+url;
}
//项目详情
function LoanDetail(loanNo,loanType){
	var url = '';
	if(loanType == 'public'){
		url = '/crowdfund.crowdfundDetail-public.html?loanNo='+loanNo;
	}else if(loanType == 'product'){
		url = '/crowdfund.crowdfundDetail-product.html?loanNo='+loanNo;
	}else if(loanType == 'stock'){
		url = '/crowdfund.crowdfundDetail-stock.html?loanNo='+loanNo;
	}
	window.location.href=path+url;
}

//预览
function openPreview(loanNo,loanType,loanState){
	var url = '';
	if(loanType == 'product'){ //产品
		url = '/common/loanDetail-product.html?loanNo='+loanNo+'&state='+loanState+'&type=0';
	}else if(loanType == 'public'){
		
		url = '/common/loanDetail-public.html?loanNo='+loanNo+'&state='+loanState+'&type=0';
	}else if(loanType == 'stock'){
		url = '/common/loanDetail-stock.html?loanNo='+loanNo+'&type=0';
	} 
	window.open(userWebUrl+url);
}

//审核流程
function openAudit(loanNo){
	var columns = [[
					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:110,align:'center',sortable:true},
					{field:'auditor',title:'审核人',width:100,align:'center',sortable:true},
					{field:'auditStateName',title:'操作动作',width:100,align:'center',sortable:true},
					{field:'loanStateName',title:'项目状态',width:100,align:'center',sortable:true},
					{field:'auditOpinion',title:'意见',width:110,align:'center',sortable:true},
					{field:'auditTime',title:'操作时间',align:'center',sortable:true}
				]];
	$('#loanAuditTable').datagrid({
		url:path + '/crowdfunding/getAuditList.html?loanNo='+loanNo,
		columns: columns,
		height: 314,
		rownumbers : true,
		pagination: true
	});
	$("#loanAudit").show().dialog({
		title: "审批列表",
		height: 350,
		width:810,
		modal: true,
		onClose: function () {
        }
	});
}


function displayLoanUser(val,row,index){
	var returnVal = '<a href="'+path+'/user.webUserDetail.html?id='+val+'">'+val+'</a>';
	return returnVal;
}

//领投人列表
function loanLeaderList(loanNo){
	window.location.href=path+'/crowdfund.loanLeadList.html?loanNo='+loanNo;
}

//预购列表
function preSupportsList(loanNo){
	window.location.href=path+'/crowdfund.preSupportList.html?loanNo='+loanNo;
}

/**
 * 选择用户
 * @param nameObj
 * @param idObj
 * @param fn ： 选择完以后，添加用户相应地址
 */
function getLoanUser(nameObj,idObj, fn){
	var squery = '<form id="list_search_user"><div class="seach_div"><div style="float:left;"><label>用户名:</label><input id="userId" name="userId" type="text"/></div>'+
		'<a href="#" id="searchBtn" onclick="searchUser()" style="display:block;width:50px;text-align:center;margin-top:4px;float:left;height:26px;line-height:26px;">查询</a></div></form>';
	
	$('<div id="loanUserDiv">'+squery+'<table id="loanUserTable"></table></div>').hide().appendTo('body');
	
	//获取字典数据
	var columns = [[
					{field:'userId',title:'用户名',width:150,align:'center',sortable:true},
					{field:'realName',title:'真实姓名',width:130,align:'center',sortable:true},
/*					{field:'thirdAccount',title:'汇付账号',width:150,align:'center',sortable:true},
*/					{field:'mobile',title:'手机号',align:'center'},
					{field:'email',title:'邮箱',width:150,align:'center'},
					{field:'createTime',title:'注册时间',align:'center',sortable:true},
					{field:'statusName',title:'状态',width:50,align:'center',sortable:true}
				]];
	$('#loanUserTable').datagrid({
		url: path + "/user/getlist.html?isAuth=1",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		height:364,
		pagination: true,
		onDblClickRow:function(rowIndex, row){
			if(row.realName){
				$('#'+nameObj).val(row.realName);
			}else{
				$('#'+nameObj).val(row.userId);
			}
			$('#'+idObj).val(row.userId);
			
			if(fn && $.isFunction(fn)){
				fn(row.userId);
			}
			$("#loanUserDiv").dialog('close');
		}
	});
	$("#loanUserDiv").show().dialog({
		title: "选择借款用户",
		height: 400,
		width:900,
		modal: true,
		onClose: function () {
			$("#loanUserDiv").remove();
        }
	});
}


function searchUser(){
	queryToGrid("loanUserTable", "list_search_user");
}

//选择项目负责人
function getLoanManager(){
	$('<div id="loanManagerDiv"><table id="loanManagerTable"></table></div>').hide().appendTo('body');
	
	//获取字典数据
	var columns = [[
					{field:'adminId',title:'用户名',width:150,align:'center'},
					{field:'employeeNo',title:'员工号',width:150,align:'center'},
					{field:'realName',title:'真实姓名',width:150,align:'center'},
					{field:'mobile',title:'手机号',width:150,align:'center'}
				]];
	$('#loanManagerTable').datagrid({
		url: path + "/auth/user/getUserByRole.html?roleCode=loan_manager",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		height:364,
		pagination: true,
		onDblClickRow:function(rowIndex, row){
			$('#loanManagerName').val(row.realName);
			$('#loanManager').val(row.adminId);
			$("#loanManagerDiv").dialog('close');
		}
	});
	$("#loanManagerDiv").show().dialog({
		title: "选择项目负责人",
		height: 400,
		width:650,
		modal: true,
		onClose: function () {
			$("#loanManagerDiv").remove();
        }
	});
	
}

function formatBuyAndTotal(val,row,index){
	var totalNum = row["financeNum"];
	var buyNum = row["buyNum"];
	return buyNum+"/"+totalNum;
}

//修改项目等级
function showModifyLoanLevel(loanNo){
	$("#loanNo2").val(loanNo);
	$("#modifyLoanLevel").show().dialog({
		title: "项目等级",
		height: 230,
		width:500,
		modal : true,
		onClose: function () {
            $("#modifyLoanLevelForm").form('clear');
        }
	});
}
function modifyLoanLevel(){
	
	var loanNo = $("#loanNo2").val();
	var loanLevel = $("#loanLevel").val();
	$.ajax({
		type : "POST",
		url : path + '/crowdfunding/modifyLoanLevel.html',
		data : {
			'loanNo' : loanNo,
			'loanLevel':loanLevel
		},
		success : function(data) {
			if (data.success) {
				queryToGrid("progectTable", "list_search");
				$('#modifyLoanLevel').dialog("close");
				$.messager.alert('提示', "项目等级修改成功");
			}else{
				$.messager.alert('提示', data.msg);
			}
		}
	});
}

function cancelModifyLoanLevel(){
	$("#modifyLoanLevelForm").form('clear');
	$('#modifyLoanLevel').dialog("close");
}





function NumberFormat(val,row,index){
	if(!val){
		val = 0;
	}
	return  val.toFixed(2);
}
