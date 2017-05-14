$(function(){
	searchData("/crowdfunding/getPageCrowdList.html?loanState=re_passed&fixLoanType=pe");
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
	
	$("#auditform").validate({ 
        rules: {
        	fundEndTime: "required"
        },  
        messages: {  
        	fundEndTime: "请填写发布时间" 
        },
        submitHandler:function(form){
        	$('#auditform').form('submit', {
        		url : form.action,
        		data:{'releaseTime':$("#releaseTime").val()},
        		success : function(data) {
        			$('#add').dialog("close");
        			$('#progectTable').datagrid("reload");
        		},
        		error: function(){
        			console.log("预热项目异常");
        		}
        	});
        }
    }); 
	
	
	//取消按钮
	$('#cancel').click(function(){
		$('#add').dialog("close");
	});
	
	//预热按钮事件
	$('#preheatbtn').click(function(){
		$('#auditform').attr('action',path + '/crowdfunding/preheatCrowdFunding.html');
		$('#auditform').submit();
	});
});
//查询项目列表
function searchData(dUrl){
	//获取字典数据
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'operat',title:'操作',width:100,align:'center',formatter:operateData},
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
					{field:'loanLevel',title:'项目等级',width:80,align:'center'},
					{field:'loanProvinceName',title:'项目所在省',width:90,align:'center'},
					{field:'loanCityName',title:'项目所在市',width:90,align:'center'},
					{field:'loanCountyName',title:'项目所在县',width:90,align:'center'},
					{field:'chargeFee',title:'服务费比例',width:90,align:'center'},
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
function preheatLoan(loanNo){
	$("#loanNo").val(loanNo);
	$("#preheatbtn").show();
	$("#sDate").show();
	$("#add").show().dialog({
		title: "预热项目",
		height: 240,
		width:500,
		modal : true,
		onClose: function () {
			 $("#auditform").form('clear');
        }
	});
}

var sendLoanMask;
//发布
function release(loanNo){
	$.messager.confirm('提示', '发布后会在前台展示，您确定要发布吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfunding/releaseCrowdFunding.html',
				data : {
					'loanNo' : loanNo
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
							url : path + '/crowdfunding/queryLoanStatus.html',
							data : {
								'loanNo' : loanNo,
								'loanState' : 'funding'
							},
							success : function(data) {
								if (data.success) {
									if(data.msg){
										$.messager.alert('提示', '处理成功');
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
										clearInterval(time);
									}
									//显示已完成代收的数量
									if(i=="3600000"){
										$.messager.alert('提示',"处理失败！");
										clearInterval(time);
										sendLoanMask.dialog('close');
										queryToGrid("progectTable", "list_search");
	    							}
								}else{
									$.messager.alert('提示',"处理失败！");
									clearInterval(time);
									sendLoanMask.dialog('close');
									queryToGrid("progectTable", "list_search");
								}
							},
					        complete:function(XMLHttpRequest,textStatus){
					        	
					        }
						});
					},5000);
		        },
				success : function(data) {
					if(!data.success){
						$.messager.alert('提示', data.msg);
						clearInterval(time);
						sendLoanMask.dialog('close');
					}else{
						queryToGrid("progectTable", "list_search");
					}
					
				}
			});
		}
	});
}
