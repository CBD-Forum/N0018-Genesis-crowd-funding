$(function(){
	searchData("/crowdfunding/getPageCrowdList.html?loanState=passed");
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
        	auditOpinion: "required"
        },  
        messages: {  
        	auditOpinion: "请填写审核意见" 
        },
        submitHandler:function(form){
        	$('#auditform').form('submit', {
        		url : form.action,
        		success : function(data) {
        			var data = $.parseJSON(data);
        			if(data.success){
        				$.messager.alert('提示', '处理成功');
            			$('#add').dialog("close");
            			$('#progectTable').datagrid("reload");
        			}else{
        				$.messager.alert('提示',data.msg);
        			}
        		},
        		error: function(){
        			console.log("审核项目异常");
        		}
        	});
        }
    }); 
	
	
	//通过按钮事件
	$('#pass').click(function(){
		var loanType=$("#loanType").val();
		if(loanType==null || loanType.length==0){
			$.messager.alert('提示',"项目类型不能为空！");
			return false;
		}
		if(loanType=='stock' || loanType=='product'){
			$.messager.confirm('提示', '您确定要进行预热操作吗？', function(r) {
				if (r) {
					$.ajax({
						type : "POST",
						url : path + "/crowdfunding/preheatCrowdFunding.html",
						data : {
							'loanNo' : $("#loanNo").val(),
							'preheatEndTime' :$("#preheatEndTime").val()
						},
						beforeSend:function(XMLHttpRequest){
							sendLoanMask=getMask('正在处理请求，请稍候！');
							$(".loading").css("width","150px");
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
											'loanNo' : $("#loanNo").val(),
											'loanState' : 'preheat'
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
												$.messager.alert('提示', '处理失败');
												sendLoanMask.dialog('close');
												queryToGrid("progectTable", "list_search");
												clearInterval(time);
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
		}else{
			$.messager.confirm('提示', '您确定要进行复审操作吗？', function(r) {
				if (r) {
					$('#auditform').attr('action',path + "/crowdfunding/reAuditPass.html");
					$('#auditform').submit();
				}
			});
		}
	});
	//拒绝按钮
	$('#refuse').click(function(){
		$('#auditform').attr('action',path + "/crowdfunding/reAuditRefuse.html");
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
					{field:'loanName',title:'项目名称',width:250,align:'center'},
					{field:'loanTypeName',title:'项目类型',width:80,align:'center'},
					{field:'loanStateName',title:'状态',width:80,align:'center'},
					{field:'loanUser',title:'发起人',width:100,align:'center',formatter:displayLoanUser},
					{field:'loanUserName',title:'发起人真实姓名',width:100,align:'center'},
					{field:'fundDays',title:'众筹周期(天)',width:80,align:'center',sortable:true},
					{field:'fundAmt',title:'目标金额('+digital_goods_unit+')',width:100,align:'right',sortable:true,formatter:NumberFormat},
					{field:'overFundAmt',title:'超募金额('+digital_goods_unit+')',width:100,align:'right',formatter:NumberFormat},
					{field:'loanProvinceName',title:'项目所在省',width:90,align:'center'},
					{field:'loanLevel',title:'项目等级',width:80,align:'center'},
					{field:'loanCityName',title:'项目所在市',width:90,align:'center'},
					{field:'loanCountyName',title:'项目所在县',width:90,align:'center'},
					{field:'chargeFee',title:'服务费比例',width:90,align:'center'},
					{field:'createTime',title:'申请时间',width:140,align:'center',sortable:true},
					/*{field:'fundEndTime',title:'融资截止时间',width:140,align:'center',sortable:true},*/
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

function operate(id,loanType){

	$("#auditTextDiv").hide();
	$("input[name='loanNo']").val(id);
	$("#loanType").val(loanType);
	$("#add").show().dialog({
		title: "项目复审操作",
		height: 250,
		modal : true,
		onClose: function () {
            $("#auditform").form('clear');
        }
	});
	if(loanType=='stock'){
		$("#auditTextDiv").html("<span style='color:red;'>提醒：1.项目复审通过后，会在前台展示，进入预热阶段，请谨慎操作</span><br/><span style='color:red;align:center;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.非公开融资项目进入预热阶段，会在区块链为项目开户，需要支付30XRP的开户费用；</span>");
		$("#auditTextDiv").show();
	}else if(loanType=='product'){
		$("#auditTextDiv").html("<span style='color:red;'>提醒：项目复审通过后，会在前台展示，进入预热阶段，请谨慎操作</span>");
		$("#auditTextDiv").show();
	}else{

		$("#auditTextDiv").hide();
	}
}
//审核通过事件
function auditPass(){
	$('#auditform').submit();
}

