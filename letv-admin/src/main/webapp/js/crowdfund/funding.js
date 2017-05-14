$(function(){
	searchData("/crowdfunding/getPageCrowdList.html?loanState=funding");
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
    
    $("#getInvestorBtn").click(function(){
    	//getLoanUser 第三个参数，用来添加收货地址
    	getLoanUser('investorName','investor', getPostAddress);
    });
    
    $("#getInvestorBtn2").click(function(){
    	getLoanUser('investorName2','investor2');
    });
    
    $("#investor").blur(function(){
    	alert(11);
    	$.ajax({
    		type : "POST",
    		url : path + '/user/getUserRealName.html',
    		data : {
    			'userId' : $(this).val()
    		},
    		success : function(data) {
    			if (data.success) {
    				$("#realName").text(data.msg);
    			}else{
    				$.messager.alert('提示', data.msg);
    			}
    		}
    	});
      	
    });
    
    
    $("#investor2").blur(function(){
    	$.ajax({
    		type : "POST",
    		url : path + '/user/getUserRealName.html',
    		data : {
    			'userId' : $(this).val()
    		},
    		success : function(data) {
    			if (data.success) {
    				$("#realName2").text(data.msg);
    			}else{
    				$.messager.alert('提示', data.msg);
    			}
    		}
    	});
    });
    
    
    //实物支持
    $("#supportbtn").click(function(){
    		var row = $('#backTable').datagrid('getSelected');
        	if (!row) {
    			$.messager.alert('提示', '请选择要支持的回报');
    			return;
    		}
	    	if (!$("#investorName").val()) {
				$.messager.alert('提示', '请输入正确的用户id');
				return;
			}
	    	if (!$("#post_address").val()) {
				$.messager.alert('提示', '请选择用户的收货地址');
				return;
			}
	    	$.messager.confirm('提示', '将会插入'+$("#investor").val()+'支持'+row.amt+'元的投资记录,请确认', function(r) {
	    		if (r) {
			    				$.ajax({
						    		type : "POST",
						    		url : path + '/crowdfundInvest/backSupport.html',
						    		data : {
						    			'loanNo' : $("#loanNo").val(),
						    			'postAddressNo' : $("#post_address").val(),
						    			'loanType' : $("#loanType").val(),
						    			'backNo' : row.backNo,
						    			'supportAmt' : row.amt,
						    			'supportUser':$("#investor").val()
						    		},
						    		beforeSend:function(XMLHttpRequest){
				    					sendLoanMask=getMask('正在处理投标请求，请稍候！');
				    		        },
						    		success : function(data) {
						    			if (data.success) {
						    				queryToGrid("progectTable", "list_search");
						    				$.messager.alert('提示', "支持成功");
						    				 $("#investorName").val('');
						    				 $("#investor").val('');
						    				 $('#supportback').dialog("close");
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
    
    
    
    
    
    
    
    
    $("#supportcancel").click(function(){
    	 $("#investor").val("");
    	 $("#investorName").val("");
		 $('#supportback').dialog("close");
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
    
    
    
    
    $("#addProgressform").validate({ 
        rules: {
        	enterContent: "required"
        },  
        messages: {  
        	enterContent: "请填写项目进展" 
        },
        submitHandler:function(form){
        	$('#addProgressform').form('submit', {
        		url : form.action,
        		success : function(data) {
        			$('#addProgress').dialog("close");
        		},
        		error: function(){
        			console.log("增加项目进展异常");
        		}
        	});
        }
    }); 
    
    
    //通过按钮事件
	$('#saveProgress').click(function(){
		$('#addProgressform').attr('action',path + "/crowdfundInvest/addProgress.html");
		$('#addProgressform').submit();
	});
	//取消按钮
	$('#cancelProgress').click(function(){
		$("#addProgressform").form('clear');
		$('#addProgress').dialog("close");
	});
    
});
//查询项目列表
function searchData(dUrl){
	//获取字典数据
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'operat',title:'操作',width:220,align:'center',formatter:operateData},
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
					{field:'buyAndTotalNum',title:'认购份数/总份数',width:100,align:'right',formatter:formatBuyAndTotal},
					{field:'loanLevel',title:'项目等级',width:80,align:'center'},
					{field:'loanProvinceName',title:'项目所在省',width:90,align:'center'},
					{field:'loanCityName',title:'项目所在市',width:90,align:'center'},
					{field:'loanCountyName',title:'项目所在县',width:90,align:'center'},
					{field:'chargeFee',title:'服务费比例',width:90,align:'center'},
					{field:'releaseTime',title:'发布时间',width:140,align:'center',sortable:true},
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
function openBackSupport(loanNo,loanType){
	if(loanType == 'public_service' || loanType == 'entity'){
		supportCrowdfundBack(loanNo,loanType);
	}else if(loanType == 'house' || loanType == 'stock'){
		supportStockback(loanNo,loanType);
	}
}
// 获取用户地址
function getPostAddress(userId){
	$.ajax({
		url: path + "/address/getPostAddressList.html?userId="+userId,
		type: "post",
		dataType: "json",
		success: function(data){
			var loanStr = '', loanArr = [];
			var sdata = data["msg"]["rows"];
			loanArr.push('<option value="">-=请选择=-</option>');
			for(var i=0,tlength=sdata.length;i<tlength;i++){
					var address = sdata[i]["provinceName"]+sdata[i]["cityName"]+sdata[i]["adressDetail"]
					loanArr.push('<option value="'+sdata[i]["addressNo"]+'">'+address+'</option>');
			}
			loanStr = loanArr.join("");
			$("#post_address").html(loanStr);
		},
		error: function(){
			console.log("获取收件地址异常");
		}
	});
}
//奖励投资
function supportCrowdfundBack(loanNo,loanType){
	$("#loanNo").val(loanNo);
	$("#loanType").val(loanType);
	var backColumns = [[
	                {field:'ck',title:'操作',checkbox:true,width:100,align:'center'},
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
					{field:'transFee',title:'运费',width:80,align:'center',sortable:true,formatter:function(value,row,index){
						if (row.transFee == "0") {
							return '免运费';
						}else{
							return row.transFee;
						}
					}},
					{field:'isDelivery',title:'是否快递',align:'center',sortable:true,formatter:function(value,row,index){
						if (row.isDelivery == "1") {
							return '是';
						}else{
							return '否';
						}
					}},
					{field:'backContent',title:'回报内容',width:200,align:'center',sortable:true}
				]];
	$('#backTable').datagrid({
		url:path + '/crowdfunding/getBackSetList.html?loanNo='+loanNo,
		columns: backColumns,
		height: 230,
		nowrap:false,
		rownumbers : true,
		singleSelect:true
	});
	$("#supportback").show().dialog({
		title: "回报设置列表",
		height: 400,
		width:810,
		modal: true,
		onClose: function () {
        }
	});
	
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
		success : function(data) {
			$("#stockSupportback .x-form-item .x-form-element label").each(function(){
				$(this).text('');
				if(this.id == 'investBonusRatio' || this.id == 'projectBonusRatio'){
					$(this).text((parseFloat(data[this.id])*100).toFixed(2));
				}else{
					$(this).text(data[this.id]);
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
					var buyNum = data.buyNum;
					$("#remainNum").text(Number($("#financeNum").text())-Number(buyNum));
					
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

function backSupport(backNo,amt){
	$.ajax({
		type : "POST",
		url : path + '/crowdfunding/backSupport.html',
		data : {
			'loanNo' : $("#loanNo").val(),
			'backNo' : backNo,
			'supportAmt' : amt,
			'loanType':$("#loanType").val(),
		},
		success : function(data) {
			if (data.success) {
				$.messager.alert('提示', "支持成功");
			}else{
				$.messager.alert('提示', data.msg);
			}
		}
	});
}



function addProgress(loanNo){
	$("#loanNo2").val(loanNo);
	$("#addProgress").show().dialog({
		title: "项目进展",
		height: 230,
		width:500,
		modal : true,
		onClose: function () {
            $("#addProgressform").form('clear');
        }
	});
}

/**
 * 手动停止项目
 * @param loanNo
 */
function endLoan(loanNo){
	$.messager.confirm('提示', '您将要手动结束此项目,请确认',  function(r) {
		if (r) {
		$.ajax({
			type : "POST",
			url : path + '/crowdfundInvest/handEndLoan.html',
			data : {
				'loanNo' : loanNo
			},
			success : function(data) {
				if (data.success) {
					queryToGrid("progectTable", "list_search");
					$.messager.alert('提示', "手动中止成功");
				}else{
					$.messager.alert('提示', data.msg);
				}
			}
		});
	}
	});
	
}


