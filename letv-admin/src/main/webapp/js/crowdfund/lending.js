$(function(){
	searchData("/crowdfunding/getPageCrowdList.html?loanState=funded");
    //获取项目所在省
    getProvice("s_provice", "s_city");
	//项目所属省份改变，城市改变
	$("#s_provice").change(function(){
		getCitys($(this).val(), "s_city"); 
	});
	$("#s_city").change(function(){
		getCitys($(this).val(), "s_county"); 
	});
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
    
    //关闭延期窗口
    $('#deferCloseBtn').click(function(){
    	$("#deferLoan").dialog('close');
    });
    
    //修改延期时间
    $('#deferBtn').click(function(){
    	$.messager.confirm('提示', '您确定要延期吗？', function(r) {
    		if(!$('#fundEndTime').val()){
    			return;
    		}
    		if (r) {
    			$.ajax({
    				type : "POST",
    				url : path + '/crowdfunding/deferLoan.html',
    				data : {
    					'loanNo' : $('#defer_loanNo').text(),
    					'loanName':$('#defer_loanName').text(),
    					'fundEndTime':$('#fundEndTime').val()
    				},
    				success : function(data) {
    					if (data.success) {
    						$("#deferLoan").show().dialog('close');
    						queryToGrid("progectTable", "list_search");
    					}else{
    						$.messager.alert('提示', data.msg);
    					}
    				}
    			});
    		}
    	});
    });
	
});
//查询项目列表
function searchData(dUrl){
	//获取字典数据
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'operat',title:'操作',width:250,align:'center',formatter:operateData},
	                {field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:180,align:'center'},
					{field:'loanTypeName',title:'项目类型',width:80,align:'center'},
					{field:'loanStateName',title:'状态',width:80,align:'center'},
					{field:'loanUser',title:'发起人',width:100,align:'center',formatter:displayLoanUser},
					{field:'loanUserName',title:'发起人真实姓名',width:100,align:'center'},
					{field:'blockChainAddress',title:'项目中间户账户',width:250,align:'center'},
					{field:'fundDays',title:'众筹周期(天)',width:80,align:'center',sortable:true},
					{field:'fundAmt',title:'目标金额('+digital_goods_unit+')',width:100,align:'right',sortable:true,formatter:NumberFormat},
					{field:'overFundAmt',title:'超募金额('+digital_goods_unit+')',width:100,align:'right',formatter:NumberFormat},
					{field:'approveAmt',title:'已认购金额('+digital_goods_unit+')',width:100,align:'right',formatter:NumberFormat},
					{field:'buyAndTotalNum',title:'认购份数/总份数',width:100,align:'right',sortable:true},
					{field:'loanLevel',title:'项目等级',width:80,align:'center'},
					{field:'loanProvinceName',title:'项目所在省',width:90,align:'center'},
					{field:'loanCityName',title:'项目所在市',width:90,align:'center'},
					{field:'loanCountyName',title:'项目所在县',width:90,align:'center'},
					{field:'chargeFee',title:'服务费比例',width:90,align:'center'},
					{field:'releaseTime',title:'发布时间',width:140,align:'center'},
					{field:'createTime',title:'申请时间',width:140,align:'center',sortable:true},
					{field:'fundEndTime',title:'融资截止时间',width:140,align:'center',sortable:true},
					{field:'view',title:'详细',align:'center',width:250,formatter:operateData1}
				]];
	$('#progectTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-265,
		rownumbers : true,
		nowrap:false,
		pagination: true
	});
}
//项目放款操作
var sendLoanMask;

function lendLoan(loanNo){
	$.messager.confirm('提示', '您确定要放款吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfundInvest/sendLend.html',
				data : {
					'loanNo' : loanNo
				},
				beforeSend:function(XMLHttpRequest){
					sendLoanMask=getMask('正在处理放款请求，请稍候！');
					var i = 0;
				    time = setInterval(function(){
						if(i=="3600000"){
							sendLoanMask.dialog('close');
							clearInterval(time);
						}
						i+=5000;
						$.ajax({
							type : "POST",
							url : path + '/crowdfundInvest/selectLoanState.html',
							data : {
								'loanNo' : loanNo
							},
							success : function(data) {
								if (data.success) {
									if(data.msg){
										$.messager.alert('提示', '放款处理成功');
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
										clearInterval(time);
									}
									//显示已完成代收的数量
									if(i=="3600000"){
										$.messager.alert('提示',"放款处理失败！");
										clearInterval(time);
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
	    							}
								}else{
									//显示已完成代收的数量
									if(i=="3600000"){
										$.messager.alert('提示',"放款处理失败！");
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
						//clearInterval(time);
						
					}else{
						$.messager.alert('提示', data.msg+'，请联系管理员。');
						clearInterval(time);
						sendLoanMask.dialog('close');
					}
				},
		        complete:function(XMLHttpRequest,textStatus){
//		        	sendLoanMask.dialog('close');
		        }
			});
		}
	});
} 

//流标
function cancelLoan(loanNo){
	$.messager.confirm('提示', '您确定要流标吗？', function(r) {
		if (r) {
			sendLoanMask=getMask('正在处理流标请求，请稍候！');
			$.ajax({
				type : "POST",
				url : path + '/crowdfundInvest/sendFlow.html',
				data : {
					'loanNo' : loanNo
				},
				beforeSend:function(XMLHttpRequest){
					var i = 0;
				    time = setInterval(function(){
						if(i=="3600000"){
							sendLoanMask.dialog('close');
							clearInterval(time);
						}
						i+=5000;
						$.ajax({
							type : "POST",
							url : path + '/crowdfundInvest/getFlowState.html',
							data : {
								'loanNo' : loanNo
							},
							success : function(data) {
								if (data.success) {
									if(data.msg){
										$.messager.alert('提示', '流标处理成功');
										sendLoanMask.dialog('close');
										clearInterval(time);
										queryToGrid("progectTable", "list_search");
										
									}
									//显示已完成代收的数量
									if(i=="3600000"){
										$.messager.alert('提示',"流标处理失败！");
										clearInterval(time);
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
	    							}
								}else{
									if(i=="3600000"){
										$.messager.alert('提示',"流标处理失败！");
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
						queryToGrid("progectTable", "list_search");
					}else{
						$.messager.alert('提示', data.msg+'，请联系管理员。');
						clearInterval(time);
						sendLoanMask.dialog('close');
					}
				},
		        complete:function(XMLHttpRequest,textStatus){
		        	//sendLoanMask.dialog('close');
		        }
			});
		}
	});
} 

 

function openDefer(loanNo,loanName){
	$('#defer_loanNo').text(loanNo);
	$('#defer_loanName').text(loanName);
	$("#deferLoan").show().dialog({
		title: "延期",
		height: 330,
		width:500,
		modal : true,
		onClose: function () {
            $("#deferForm").form('clear').form('reset');
        }
	});
}


