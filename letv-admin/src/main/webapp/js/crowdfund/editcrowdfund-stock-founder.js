$(function(){
	
	
	// 获取字典数据
	var operateColumns = [ [ 
	  /*  {field : 'registerNumByDay',title : '日注册用户数', width : 100,align : 'center'}, 
	    {field : 'userNumByDay',title : '日活跃用户数', width : 100,align : 'center'}, 
	    {field : 'retentionByNextDay',title : '次日留存率', width : 100,align : 'center'}, 
	    {field : 'registerNumByMonth',title : '月注册用户数', width : 100,align : 'center'}, 
	    {field : 'userNumByMonth',title : '月活跃用户数', width : 100,align : 'center'}, 
	    {field : 'retentionByNextMonth',title : '月日留存率', width : 100,align : 'center'}, 
	    {field : 'payUserNum',title : '付费用户数', width : 100,align : 'center'}, */
	    {field : 'expectedReturn',title : '内容', width : 600,align : 'center'},
	    {field : 'createTime', title : '创建时间', width : 100,align : 'center'}, 
	    {field : 'operat',title : '操作',width : 220,align : 'center',formatter : operateData2 } 
	    ] ];

	var addOperateBtn = {
		text : '添加',
		iconCls : 'icon-add',
		handler : function() {
			showOperateAdd();
		}
	};
	var operateBtns2 = [];
	operateBtns2.push(addOperateBtn);
	if (operateBtns2.length == 0) {
		operateBtns2 = null;
	}

	$('#operateTable').datagrid({
		url: path + "/crowdfundingOperateData/selectPageList.html?loanNo="+loanNo,
		columns : operateColumns,
		pagination : true,
		singleSelect : true,
		height : 350,
		rownumbers : true,
		toolbar : operateBtns2
	});
	
	
	
	
	
	
	
	
	
	
	
	// 获取字典数据
	var columns = [ [ {
		field : 'name',
		title : '姓名',
		width : 200,
		align : 'center'
	}, {
		field : 'position',
		title : '职位',
		width : 200,
		align : 'center'
	}, {
		field : 'operat',
		title : '操作',
		width : 220,
		align : 'center',
		formatter : operateData
	} ] ];

	var addBtn = {
		text : '添加',
		iconCls : 'icon-add',
		handler : function() {
			showFounderAdd();
		}
	};
	var operateBtns = [];
	operateBtns.push(addBtn);
	if (operateBtns.length == 0) {
		operateBtns = null;
	}

	$('#founderTable').datagrid({
		url : path + "/crowdfundingFounder/selectFounderPageList.html?loanNo=" + loanNo,
		columns : columns,
		pagination : true,
		singleSelect : true,
		height : 350,
		rownumbers : true,
		toolbar : operateBtns
	});	
	
	
	// 添加创始人
	$('#saveFounderBtn').click(function() {
		$('#founderForm').submit();
	});

	// 添加创始人工作经历
	$('#saveFounderWorkBtn').click(function() {
		$('#founderWorksForm').submit();
	});

	// 添加创始人创业经历
	$('#saveFounderBusinessBtn').click(function() {
		$('#founderBusinessForm').submit();
	});
	// 添加创始人教育经历
	$('#saveFounderEducationsBtn').click(function() {
		$('#founderEducationsForm').submit();
	});

	$('#closeFounderBtn').click(function() {
		$("#founderAdd").show().dialog('close');
	});
	
	
	$('#saveOperateBtn').click(function() {
		$('#operateForm').submit();
	});

	$('#closeOperateBtn').click(function() {
		$("#operateAdd").show().dialog('close');
	});


	$("#founderForm").validate({
		rules : {
			name : {
				required : true,
				maxlength : 32
			},
			position : {
				required : true,
				maxlength : 32
			}
		},
		messages : {
			displayName : {
				required : "请填写姓名"
			},
			code : {
				required : "请填写职位"
			}
		},
		submitHandler : function(form) {
			var url = path + '/crowdfundingFounder/saveFounder.html';

			$('#founderForm').form('submit', {
				url : url,
				success : function(data) {
					var obj = $.parseJSON(data);
					if (obj.success) {
						$("#founderAdd").show().dialog('close');
//						queryToGrid("founderTable", "list_search");
						$('#founderTable').datagrid('options').url = path + "/crowdfundingFounder/selectFounderPageList.html";
						$('#founderTable').datagrid( "reload",{'loanNo' :loanNo});
					} else {
						$.messager.alert('提示', obj.msg);
					}
				}
			});
		}
	});

	// ===============创始人工作经历===================

	// 获取字典数据
	var worksColumns = [ [ {
		field : 'company',
		title : '单位',
		width : 200,
		align : 'center'
	}, {
		field : 'startTime',
		title : '开始时间',
		width : 120,
		align : 'center'
	}, {
		field : 'endTime',
		title : '结束时间',
		width : 120,
		align : 'center'
	}, {
		field : 'position',
		title : '职     位',
		width : 80,
		align : 'center'
	}, {
		field : 'operat',
		title : '操作',
		width : 100,
		align : 'center',
		formatter : operateWorksData
	} ] ];
	var operateBtns = [];
	// operateBtns.push(addBtn);
	if (operateBtns.length == 0) {
		operateBtns = null;
	}
	$('#founderWorksTable').datagrid({
		columns : worksColumns,
		pagination : false,
		singleSelect : true,
		height : 250,
		rownumbers : true,
		toolbar : operateBtns
	});

	$("#founderWorksForm").validate({
		rules : {
			company : {
				required : true,
				maxlength : 32
			},
			startTime : {
				required : true,
				maxlength : 32
			},
			endTime : {
				required : true,
				maxlength : 32
			},
			position : {
				required : true,
				maxlength : 32
			}
		},
		messages : {
			company : {
				required : "请填写公司"
			},
			position : {
				required : "请填写职位"
			}
		},
		submitHandler : function(form) {
			var url = path
					+ '/crowdfundingFounder/saveFounderWorks.html';
			if ($('#founderWorks_id').val() != '') {
				url = path+ '/crowdfundingFounder/updateFounderWorks.html';
			}
			$('#founderWorksForm').form('submit', {
				url : url,
				success : function(data) {
					var obj = $.parseJSON(data);
					if (obj.success) {
						$('#founderWorksTable').datagrid('options').url = path + "/crowdfundingFounder/getFounderWorksList.html";
						$('#founderWorksTable').datagrid( "reload",{'founderId' : $("#founderWorks_founderId").val()});
					} else {
						$.messager.alert('提示',obj.msg);
					}
				}
			});
		}
	});

	// ==============================创业经历========================================

	// 获取字典数据
	var businessColumns = [ [ {
		field : 'company',
		title : '公司名称',
		width : 200,
		align : 'center'
	}, {
		field : 'startTime',
		title : '开始时间',
		width : 120,
		align : 'center'
	}, {
		field : 'endTime',
		title : '结束时间',
		width : 120,
		align : 'center'
	}, {
		field : 'position',
		title : '职     位',
		width : 80,
		align : 'center'
	}, {
		field : 'description',
		title : '描述',
		width : 80,
		align : 'center'
	}, {
		field : 'operat',
		title : '操作',
		width : 100,
		align : 'center',
		formatter : operateBusinessData
	} ] ];
	var operateBtns = [];
	if (operateBtns.length == 0) {
		operateBtns = null;
	}
	$('#founderBusinessTable').datagrid({
		columns : businessColumns,
		pagination : false,
		singleSelect : true,
		height : 250,
		rownumbers : true,
		toolbar : operateBtns
	});

	$("#founderBusinessForm").validate({
		rules : {
			company : {
				required : true,
				maxlength : 32
			},
			startTime : {
				required : true,
				maxlength : 32
			},
			endTime : {
				required : true,
				maxlength : 32
			},
			position : {
				required : true,
				maxlength : 32
			}
		},
		messages : {
			company : {
				required : "请填写公司"
			},
			position : {
				required : "请填写职位"
			}
		},
		submitHandler : function(form) {
			var url = path + '/crowdfundingFounder/saveFounderBusiness.html';
			if ($('#founderWorks_id').val() != '') {
				url = path + '/crowdfundingFounder/updateFounderBusiness.html';
			}
			$('#founderBusinessForm').form('submit',{
				url : url,
				success : function(data) {
					var obj = $.parseJSON(data);
					if (obj.success) {
						$('#founderBusinessTable').datagrid('options').url = path + "/crowdfundingFounder/getFounderBusinessList.html";
						$('#founderBusinessTable').datagrid("reload",{'founderId' : $("#founderBusiness_founderId").val()});
					} else {
						$.messager.alert('提示',obj.msg);
					}
				}
			});
		}
	});

	// ==============================教育经历========================================

	// 获取字典数据
	var educationsColumns = [ [ {
		field : 'school',
		title : '学校',
		width : 200,
		align : 'center'
	}, {
		field : 'startTime',
		title : '开始时间',
		width : 120,
		align : 'center'
	}, {
		field : 'endTime',
		title : '结束时间',
		width : 120,
		align : 'center'
	}, {
		field : 'graduated',
		title : '专业',
		width : 80,
		align : 'center'
	}, {
		field : 'degree',
		title : '学位',
		width : 80,
		align : 'center'
	}, {
		field : 'operat',
		title : '操作',
		width : 100,
		align : 'center',
		formatter : operateEducationsData
	} ] ];
	var operateBtns = [];
	// operateBtns.push(addBtn);
	if (operateBtns.length == 0) {
		operateBtns = null;
	}
	$('#founderEducationsTable').datagrid({
		columns : educationsColumns,
		pagination : false,
		singleSelect : true,
		height : 250,
		rownumbers : true,
		toolbar : operateBtns
	});

	$("#founderEducationsForm").validate({
		rules : {
			company : {
				required : true,
				maxlength : 32
			},
			startTime : {
				required : true,
				maxlength : 32
			},
			endTime : {
				required : true,
				maxlength : 32
			},
			position : {
				required : true,
				maxlength : 32
			}
		},
		messages : {
			company : {
				required : "请填写公司"
			},
			position : {
				required : "请填写职位"
			}
		},
		submitHandler : function(form) {
			var url = path
					+ '/crowdfundingFounder/saveFounderEducations.html';
			if ($('#founderEducations_id').val() != '') {
				url = path + '/crowdfundingFounder/updateFounderEducations.html';
			}
			$('#founderEducationsForm').form('submit',{
				url : url,
				success : function(data) {
					var obj = $.parseJSON(data);
					if (obj.success) {
						$('#founderEducationsTable').datagrid('options').url = path + "/crowdfundingFounder/getFounderEducationsList.html";
						$('#founderEducationsTable').datagrid("reload", {'founderId' : $("#founderEducations_founderId").val() });
					} else {
						$.messager.alert('提示',obj.msg);
					}
				}
			});
		}
	});
	
	
	
	
	
	
	
	$("#operateForm").validate({
		rules : {
		 
			expectedReturn:{
				required : true 
			}
		},
		messages : {
		 
		},
		submitHandler : function(form) {
			var url = path + '/crowdfundingOperateData/saveOperateData.html';
			$('#operateForm').form('submit', {
				url : url,
				success : function(data) {
					var obj = $.parseJSON(data);
					if (obj.success) {
						$("#operateAdd").show().dialog('close');
						$('#operateTable').datagrid('options').url = path + "/crowdfundingOperateData/selectPageList.html";
						$('#operateTable').datagrid( "reload",{'loanNo' :loanNo});
					} else {
						$.messager.alert('提示', obj.msg);
					}
				}
			});
		}
	});	
	 
 
});





//显示添加创始人
function showFounderAdd() {
	if (!loanNo) {
		$.messager.alert('提示', '请先保存基本信息');
		return false;
	}
	$("#founder_loanNo").val(loanNo);
	$("#founderAdd").show().dialog({
		title : "添加创始人",
		height : 300,
		width : 500,
		modal : true,
		onClose : function() {
			$("#founderForm").form('clear');
		}
	});
}

/**
* 删除创始人信息
*/
function deleteFounder(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundingFounder/deleteFounder.html',
				data : {
					'id' : id
				},
				success : function() {
//					queryToGrid("founderTable", "list_search");
					$('#founderTable').datagrid('options').url = path + "/crowdfundingFounder/selectFounderPageList.html";
					$('#founderTable').datagrid( "reload",{'loanNo' :loanNo});
				}
			});
		}
	});
}

/**
* 删除运营数据信息
*/
function deleteoperateData(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundingOperateData/deleteOperateData.html',
				data : {
					'id' : id
				},
				success : function() {
					$('#operateTable').datagrid('options').url = path + "/crowdfundingOperateData/selectPageList.html";
					$('#operateTable').datagrid( "reload",{'loanNo' :loanNo});
				}
			});
		}
	});
}


//显示创始人工作经历
function addFounderWorks(id) {

	$("#founderWorks_founderId").val(id);
	$("#founderWorks_LoanNo").val(loanNo);
	$("#founderWorksAdd").show().dialog({
		title : "创始人工作经历",
		height : 600,
		width : 800,
		modal : true,
		onClose : function() {
			$("#founderWorksForm").form('clear');
		}
	});
	$('#founderWorksTable').datagrid('options').url = path
			+ "/crowdfundingFounder/getFounderWorksList.html";
	$('#founderWorksTable').datagrid("reload", {
		'founderId' : id
	});

}

/**
* 删除创始人工作经历信息
*/
function deleteFounderWorks(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundingFounder/deleteFounderWorks.html',
				data : {
					'id' : id
				},
				success : function() {
					$('#founderWorksTable').datagrid('options').url = path
							+ "/crowdfundingFounder/getFounderWorksList.html";
					$('#founderWorksTable').datagrid("reload", {
						'founderId' : $("#founderWorks_founderId").val()
					});

				}
			});
		}
	});
}

//编辑创始人工作经历
function editFounderWorks(id) {
	$('#founderWorksForm').form('clear');
	$('#founderWorksForm').form('load',
			path + '/crowdfundingFounder/selectFounderWorksById.html?id=' + id);
}

//显示创始人创业经历
function addFounderBusiness(id) {

	$("#founderBusiness_founderId").val(id);
	$("#founderBusiness_LoanNo").val(loanNo);
	$("#founderBusinessAdd").show().dialog({
		title : "创始人创业经历",
		height : 600,
		width : 850,
		modal : true,
		onClose : function() {
			$("#founderBusinessForm").form('clear');
		}
	});
	$('#founderBusinessTable').datagrid('options').url = path + "/crowdfundingFounder/getFounderBusinessList.html";
	$('#founderBusinessTable').datagrid("reload", { 'founderId' : id });
}

/**
* 删除创始人创业经历信息
*/
function deleteFounderBusiness(id) {
	// 删除
	$.messager.confirm('提示','您确定要删除已选的信息吗？',
	function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path
						+ '/crowdfundingFounder/deleteFounderBusiness.html',
				data : {
					'id' : id
				},
				success : function() {
				   $('#founderBusinessTable')
							.datagrid('options').url = path
							+ "/crowdfundingFounder/getFounderBusinessList.html";
					$('#founderBusinessTable')
						.datagrid(
								"reload",
								{
									'founderId' : $(
											"#founderBusiness_founderId")
											.val()
								});

				}
			});
		}
	});
}

//编辑创始人创业经历
function editFounderBusiness(id) {
	$('#founderBusinessForm').form('clear');
	$('#founderBusinessForm').form(
			'load',
			path + '/crowdfundingFounder/selectFounderBusinessById.html?id='
					+ id);
}

//=========================================教育情况=================================
//显示创始人教育情况
function addFounderEducations(id) {

	$("#founderEducations_founderId").val(id);
	$("#founderEducations_LoanNo").val(loanNo);
	$("#founderEducationsAdd").show().dialog({
		title : "创始人教育背景",
		height : 600,
		width : 800,
		modal : true,
		onClose : function() {
			$("#founderEducationsForm").form('clear');
		}
	});
	$('#founderEducationsTable').datagrid('options').url = path
			+ "/crowdfundingFounder/getFounderEducationsList.html";
	$('#founderEducationsTable').datagrid("reload", {
		'founderId' : id
	});

}

/**
* 删除创始人创业教育情况
*/
function deleteFounderEducations(id) {
	// 删除
	$.messager.confirm( '提示','您确定要删除已选的信息吗？',function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundingFounder/deleteFounderEducations.html',
				data : {
					'id' : id
				},
				success : function() {
					$('#founderEducationsTable').datagrid('options').url = path + "/crowdfundingFounder/getFounderEducationsList.html";
					$('#founderEducationsTable').datagrid("reload",{'founderId' : $("#founderEducations_founderId").val()});
				}
			});
		}
	});
}

//编辑创始人教育情况
function editFounderEducations(id) {
	$('#founderEducationsForm').form('clear');
	$('#founderEducationsForm').form( 'load', path + '/crowdfundingFounder/selectFounderEducationsById.html?id='+ id);
}



//显示添加运营数据信息
function showOperateAdd() {
	if (!loanNo) {
		$.messager.alert('提示', '请先保存基本信息');
		return false;
	}
	$("#operate_loanNo").val(loanNo);
	$("#operateAdd").show().dialog({
		title : "添加预计收益",
		height : 250,
		width : 650,
		modal : true,
		onClose : function() {
			$("#operateForm").form('clear');
		}
	});
}