$(function(){
	searchData("/crowdfunding/getPageCrowdList.html?loanState=re_passed&fixLoanType=sh");
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
    
/*    $("#auditform").validate({ 
        submitHandler:function(form){
        	$('#auditform').form('submit', {
        		url : form.action,
        		success : function(data) {
        			$('#add').dialog("close");
        			$('#progectTable').datagrid("reload");
        		},
        		error: function(){
        			console.log("审核项目异常");
        		}
        	});
        }
    }); */
    var sendLoanMask;
	//预热按钮事件
	$('#preheatbtn').click(function(){
		var preheatEndTime = $("#preheatEndTime").val();
		if(!preheatEndTime){
			$.messager.alert('提示', "预热截止日期不能为空");
			return false;
		}
		
		 var date1= new Date(Date.parse(preheatEndTime.replace(/-/g,"/")));   
	     var date2= new Date();   
	     var r = date1-date2;
		 if(r<=0){
			 $.messager.alert('提示', "预热截止日期不能小于当前时间");
			 return false;
		 }
		
/*		$('#auditform').attr('action',path + "/crowdfunding/preheatCrowdFunding.html");
		$('#auditform').submit();*/
		 
		$.messager.confirm('提示', '您确定要进行预热操作吗？', function(r) {
			if (r) {
				//var success=null;
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
					    	//if(success==true){
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
/*					    	}else if(success=false){
					    		clearInterval(time);
					    	}*/
						},5000);
			        },
					success : function(data) {
						//success=data.success;
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
	});
	//拒绝按钮
	$('#cancel').click(function(){
		$('#add').dialog("close");
	});
	
});
//查询项目列表
function searchData(dUrl){
	//获取字典数据
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'operat',title:'操作',width:150,align:'center',formatter:operateData},
	                {field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:180,align:'center'},
					{field:'loanTypeName',title:'项目类型',width:80,align:'center'},
					{field:'loanStateName',title:'状态',width:80,align:'center'},
					{field:'loanUser',title:'发起人',width:100,align:'center',formatter:displayLoanUser},
					{field:'loanUserName',title:'发起人真实姓名',width:100,align:'center'},
					{field:'fundDays',title:'众筹周期(天)',width:80,align:'center',sortable:true},
					{field:'fundAmt',title:'目标金额(令)',width:100,align:'right',sortable:true},
					{field:'overFundAmt',title:'超募金额(令)',width:100,align:'right'},
					/*{field:'prepayAmt',title:'发起人预付金额(令)',width:120,align:'right'},*/
					/*{field:'loanPayedAmt',title:'发起人已付金额(令)',width:120,align:'right'},*/
					{field:'loanLevel',title:'项目等级',width:80,align:'center'},
					{field:'loanProvinceName',title:'项目所在省',width:90,align:'center'},
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

function preheatLoan(loanNo,prepayAmt,loanPayedAmt){

	if((prepayAmt-loanPayedAmt)!=0){
		$.messager.alert('提示', '没有支付预付款','warning');
		return false;
	}
	$("input[name='loanNo']").val(loanNo);
	$("#add").show().dialog({
		title: "项目预热操作",
		height: 230,
		modal : true,
		onClose: function () {
            $("#auditform").form('clear');
        }
	});
}

