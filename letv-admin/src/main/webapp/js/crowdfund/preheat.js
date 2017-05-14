$(function(){
	searchData("/crowdfunding/getPageCrowdList.html?loanState=preheat");
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

	
	//取消按钮
	$('#cancelFile').click(function(){
		$('#uploadDiv').dialog("close");
	});
	
	
	$("#saveFile").click(function(){
		
	});
	
	//点击打开文件选择器  
    $("#upload").on('click', function() {  
        $('#fileToUpload').click();  
    });  
      
    //选择文件之后执行上传  
    $('#fileToUpload').on('change', function() {
    	var sendLoanMask=getMask('正在上传，请稍后...');
        $.ajaxFileUpload({  
            url:path+'/crowdfunding/uploadLoanContract.html?jq_random='+Math.random().toFixed(5)+'&loanNo='+$("#loanNo2").val(),  
            secureuri:false,  
            fileElementId:'fileToUpload',//file标签的id  
            dataType: 'json',//返回数据的类型  
            success: function (data, status) {
            	if(data["success"]){
            		var filePath = data.msg;
            		$("#filePath").val(filePath);
            		$('#fileToDownload').attr('href',fileUrl+filePath);
            		$.messager.alert('提示', '上传成功。');
				}else{
					$.messager.alert('提示', '上传失败。','warning');
				}
            	$('#fileToDownload').attr('href',fileUrl+filePath);
            },  
            error: function (data, status, e) {  
                alert(e);
            },
            complete:function(XMLHttpRequest,textStatus){
	        	sendLoanMask.dialog('close');
	        }
        });  
    });  
    
    //股权支持
    $("#stockSupportbtn").click(function(){
    	if (!$("#buyNum").val()) {
			$.messager.alert('提示', '请选择要支持的份数');
			return;
		}
    	if (!$("#investorName2").val()) {
			$.messager.alert('提示', '请输入正确的用户id');
			return;
		}
    	$.messager.confirm('提示', '将会插入'+$("#investor2").val()+'支持'+$("#buyNum").val()+'份的投资记录,请确认', function(r) {
    		if (r) {
				$.ajax({
		    		type : "POST",
		    		url : path + '/crowdfundInvest/backSupport.html',
		    		data : {
		    			'loanNo' : $("#loanNo").val(),
		    			'loanType' : $("#loanType").val(),
		    			'supportUser':$("#investor2").val(),
		    			'buyNum':$("#buyNum").val()
		    		},
		    		beforeSend:function(XMLHttpRequest){
    					sendLoanMask=getMask('正在处理投标请求，请稍候！');
    		        },
		    		success : function(data) {
		    			if (data.success) {
		    				queryToGrid("progectTable", "list_search");
		    				$.messager.alert('提示', "支持成功");
		    				 $("#investorName2").val('');
		    				 $("#investor2").val('');
		    				 $("#buyNum").val('');
		    				 $('#stockSupportback').dialog("close");
		    			}else{
		    				$.messager.alert('提示', data.msg);
		    			}
		    		},
		    		complete:function(XMLHttpRequest,textStatus){
    		        	sendLoanMask.dialog('close');
    		        }
		    	});
    		}
    	});
    	
    	
    });
    
    $("#stockSupportcancel").click(function(){
    	 $("#investor2").val("");
    	 $("#investorName2").val("");
    	 $("#buyNum").val("");
		 $('#stockSupportback').dialog("close");
	 });
    
    
    $("#getInvestorBtn2").click(function(){
    	getLoanUser('investorName2','investor2');
    });
});
//查询项目列表
function searchData(dUrl){
	//获取字典数据
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	       /*         {field:'loanContract',title:'项目合同',hidden:true},*/
	                {field:'operat',title:'操作',width:150,align:'center',formatter:operateData,align:'right'},
	                {field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:180,align:'center'},
					{field:'loanTypeName',title:'项目类型',width:80,align:'center'},
					{field:'loanStateName',title:'状态',width:80,align:'center'},
					{field:'loanUser',title:'发起人',width:100,align:'center',formatter:displayLoanUser},
					{field:'blockChainAddress',title:'项目中间户账户',width:250,align:'center'},
					{field:'loanUserName',title:'发起人真实姓名',width:100,align:'center'},
					{field:'fundDays',title:'众筹周期(天)',width:80,align:'center',sortable:true},
					{field:'fundAmt',title:'目标金额('+digital_goods_unit+')',width:100,align:'right',sortable:true,formatter:NumberFormat},
					{field:'overFundAmt',title:'超募金额('+digital_goods_unit+')',width:100,align:'right',formatter:NumberFormat},
					{field:'loanLevel',title:'项目等级',width:80,align:'center'},
					{field:'loanProvinceName',title:'项目所在省',width:90,align:'center'},
					{field:'loanCityName',title:'项目所在市',width:90,align:'center'},
					{field:'chargeFee',title:'服务费比例',width:90,align:'center'},
					{field:'createTime',title:'申请时间',width:140,align:'center',sortable:true},
					{field:'preheatTime',title:'复审时间',width:140,align:'center' }, 
					{field:'view',title:'详细',align:'center',width:450,formatter:operateData1}
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


//发布
function release(loanNo,prepayAmt,loanType){
//	$.ajax({
//		type : "POST",
//		url : path + '/crowdfundInvest/getLoanUsePayAmt.html',
//		data : {
//			'loanNo' : loanNo
//		},
//		success : function(data) {
//			if(data.success){
//				if(data.msg.payAmt<prepayAmt){
//					$.messager.alert('提示', "发起人未全部支付预支付金额");
//				}else{
//					release2(loanNo,loanType);
//				}
//			}
//		}
//	});
	release2(loanNo,loanType);
}

var sendLoanMask;
function release2(loanNo,loanType){
	if("stock"==loanType){
		$.ajax({
			type : "POST",
			url : path + '/crowdfundInvest/getSupportListIsLeader.html',
			data : {
				'loanNo' : loanNo
			},
			success : function(data) {
				if(data.success){
					if(data.msg){
						$.messager.confirm('提示', '您确定要发布项目吗？', function(r) {
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
															queryToGrid("progectTable", "list_search")
															clearInterval(time);
														}
														//显示已完成代收的数量
														if(i=="3600000"){
															$.messager.alert('提示',"处理失败！");
															clearInterval(time);
															sendLoanMask.dialog('close');
															queryToGrid("progectTable", "list_search")
						    							}
													}else{
														$.messager.alert('提示', '处理失败');
														sendLoanMask.dialog('close');
														queryToGrid("progectTable", "list_search")
														clearInterval(time);
													}
												},
										        complete:function(XMLHttpRequest,textStatus){
										        	
										        }
											});
										},5000);
							        },
									success : function() {
										if (data.success) {
											queryToGrid("progectTable", "list_search");
										}else{
											$.messager.alert('提示', data.msg+'，请联系管理员。');
											clearInterval(time);
											sendLoanMask.dialog('close');
										}
									}
								});
							}
						});
					}else{
						$.messager.alert('提示', "请选择领投人");
					}
				}else{
					$.messager.alert('提示', "后台报错，请联系管理员");
				}
			}
		});
	}else{
		$.messager.confirm('提示', '您确定要发布该项目吗？', function(r) {
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
	

	
}

//上传合同
function uploadContract(loanNo,loanContract){
	$("#loanNo2").val(loanNo);
	$("#filePath").val(loanContract);
	var filePath = $("#filePath").val();
	if(!filePath || 'undefined'==filePath){
		
	}else{
		$('#fileToDownload').attr('href',fileUrl+filePath);
	}
	$("#uploadDiv").show().dialog({
		title: "上传合同",
		height: 230,
		width: 330,
		modal : true,
		onClose: function () {
            $("#uploadform").form('clear');
        }
	});
}

//认购定金列表
function investSupportList(loanNo){
	window.location.href=path+'/crowdfund.investSupportList.html?loanNo='+loanNo;
}

function openBackSupport(loanNo,loanType){
	if(loanType == 'house' || loanType == 'stock'){
		supportStockback(loanNo,loanType);
	}
}

//股权投资
function supportStockback(loanNo,loanType){
	$("#loanNo").val(loanNo);
	$("#loanType").val(loanType);
	$.ajax({
		type : "POST",
		url : path + '/crowdfunding/getStockBackByLoanNo.html',
		dataType:'json',
		data : {
			'loanNo' : loanNo
		},
		success : function(data1) {
			$("#stockSupportback .x-form-item .x-form-element label").each(function(){
				$(this).text('');
				if(this.id == 'investBonusRatio' || this.id == 'projectBonusRatio'){
					$(this).text((parseFloat(data1[this.id])*100).toFixed(2));
				}else{
					$(this).text(data1[this.id]);
				}
				
			});
			$.ajax({
				type : "POST",
				url : path + '/crowdfundInvest/getTotalBuyNum.html',
				dataType:'json',
				data : {
					'loanNo' : loanNo
				},
				success : function(data) {
//					var buyNum = data.buyNum;
//					$("#remainNum").text(Number($("#financeNum").text())-Number(buyNum));
					
					var buyNum = data.buyNum;
					var financeNum = data1.financeNum;
					$("#remainNum").text(Number(financeNum)-Number(buyNum));
					
					$("#stockSupportback").show().dialog({
						title: "回报设置列表",
						height: 400,
						width:510,
						modal: true,
						onClose: function () {
							
				        }
					});
				}
			});
		}
	});
}


//删除
function deleteLoan(loanNo){
	$.messager.confirm('提示', '您确定要删除吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfunding/deletePreheatCrowdFunding.html',
				data : {
					'loanNo' : loanNo
				},
				success : function() {
					queryToGrid("progectTable", "list_search");
				}
			});
		}
	});
}

