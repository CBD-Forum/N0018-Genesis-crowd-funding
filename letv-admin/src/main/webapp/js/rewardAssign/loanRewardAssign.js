$(function(){
	searchData("/rewardAssign/getLoanRewardAssignList.html");
    //获取项目所在省
    getProvice("s_provice", "s_city");
	//项目所属省份改变，城市改变
	$("#s_provice").click(function(){
		getCitys($(this).val(), "s_city"); 
	});
    //查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("progectTable", "list_search");
    });
    
  //获取字典数据
	var columns1 = [[
					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:120,align:'center',sortable:true},
					{field:'investor',title:'投资人',width:140,align:'center',sortable:true},
					{field:'investorName',title:'投资人真实姓名',width:100,align:'center',sortable:true},
					{field:'mobile',title:'投资人电话',width:100,align:'center',sortable:true},
					{field:'investTypeText',title:'投标方式',width:100,align:'center',sortable:true},
					{field:'investor',title:'直接推荐人',width:100,align:'center',sortable:true},
					{field:'investAmt',title:'投资金额(元)',width:80,align:'right',sortable:true},
					{field:'investReward',title:'优惠券',width:60,align:'center',formatter:function(value,row,index){
						if (row.investRewardId) {
							return '<a href="javascript:void(0)" onclick=openRewardUsed("'+row.investNo+'")> 明细</a>';
						}else{
							return '';
						}
					}},
					{field:'rechargeAmt',title:'实际支付金额(元)',width:100,align:'right',sortable:true},
					{field:'yearYield',title:'年化收益(%)',width:80,align:'right',sortable:true},
					{field:'investStateText',title:'投标状态',width:80,align:'center',sortable:true},
					{field:'investTime',title:'发起投资时间',align:'center',sortable:true},
					{field:'rechargeTime',title:'完成时间',align:'center',sortable:true}
				]];
	$('#loanInvestTable').datagrid({
		columns: columns1,
		height: 364,
		rownumbers : true,
		pagination: true
	});
});
//查询项目列表
function searchData(dUrl){
	//获取字典数据
	var columns = [[
					{field:'loanNo',title:'项目编号',width:120,align:'center'},
					{field:'loanName',title:'项目名称',width:140,align:'center'},
					{field:'loanUser',title:'借款用户名',width:100,align:'center'},
					{field:'repaymentNo',title:'还款期限',align:'center'},
					{field:'loanTerm',title:'借款期限(月)',align:'center'},
					{field:'actualCashAmt',title:'借款金额(元)',width:120,align:'right'},
					{field:'interestRate',title:'借款利率(%)',width:100,align:'right'},
					{field:'loanStateName',title:'项目状态',width:100,align:'center'},
					{field:'cashTime',title:'放款时间',align:'center'},
					{field:'investRewardState',title:'投资奖励发放',align:'center',formatter:operateInvestReward},
					{field:'investRewardAmt',title:'投资奖励发放金额(元)',align:'right'},
					{field:'dRecommendRewardState',title:'直接推荐奖励发放',align:'center',formatter:operateDRecommendReward},
					{field:'directRecommendRewardAmt',title:'直接推荐奖励发放金额(元)',align:'right'},
					{field:'indRecommendRewardState',title:'间接推荐奖励发放',align:'center',formatter:operateINDRecommendReward},
					{field:'indirectRecommendRewardAmt',title:'间接推荐奖励发放金额(元)',align:'right'},
					{field:'view',title:'详细',align:'center',formatter:operateData}
				]];
	var exportBtn = { 
	     	text: '导出', 
	        iconCls: 'icon-redo', 
	        handler: function() { 
	        	commonExportExcel('finance_rewardSend_export');
	        } 
	      };
	var operateBtns=[];
	if ($('#finance_rewardSend_export').length>0) {
		operateBtns.push(exportBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	$('#progectTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-230,
		rownumbers : true,
		singleSelect: true,
		pagination: true,
		toolbar:operateBtns
	});
}

//投资奖励发放
function assignInvestReward(loanNo){
	$("#loanWin").show().dialog({
		title: "投资奖励发放",
		height: 140,
		width: 500,
		modal : true,
		onClose: function () {
            $("#loanWin input").val("");
        }
	});
	$("#loanWin").find("input[name='loanAssInput']").val(loanNo);
	$("#loanAssInput").blur(function(){
		if(Number($(this).val()) <= 10){
			$(this).parent().children(".inputf").hide();
		}
	});
	//投资奖励发放按钮时间
    $("#loanAssBtn").unbind("click").click(function(){
    	loanAssign(1);
    });
}
//投资奖励发放
function loanAssign(type){
	var url='', adata={};
	//投资奖励发放
	if(type == 1){
		url = "/rewardAssign/assignInvestReward.html";
		adata = {
				"loanNo": $("#loanWin").find("input[name='loanAssInput']").val(),
				"ratio": Number($("#loanAssInput").val())/100
		};
	//发放直接推荐奖励
	}else if(type == 2){
		url = "/rewardAssign/assignRecommandReward.html";
		adata = {
				"loanNo": $("#loanWin").find("input[name='loanAssInput']").val(),
				"ratio": Number($("#loanAssInput").val())/100,
				"recommandType": "direct"
		};
	//发放间接推荐奖励
	}else if(type == 3){
		url = "/rewardAssign/assignRecommandReward.html";
		adata = {
				"loanNo": $("#loanWin").find("input[name='loanAssInput']").val(),
				"ratio": Number($("#loanAssInput").val())/100,
				"recommandType": "indirect"
		};
	}
	if(!$("#loanAssInput").val()){
		$("#loanAssInput").parent().children(".inputf").text("请输入比例值").show();
		return false;
	}
	if(Number($("#loanAssInput").val()) > 10){
		$("#loanAssInput").parent().children(".inputf").text("比例不能超过10%").show();
	}else{
		$.ajax({
			url: path + url,
			type: "post",
			dataType: "json",
			data: adata,
			success: function(data){
				if(data["success"]){
					$("#loanWin").dialog('close');
					queryToGrid("progectTable", "list_search");
				}
			},
			error: function(request){
				console.log("投资奖励发放异常");
			}
		});
	}
}
//发放直接推荐奖励
function assignDRecommendReward(loanNo){
	$("#loanWin").show().dialog({
		title: "发放直接推荐奖励",
		height: 140,
		width: 500,
		modal : true,
		onClose: function () {
            $("#loanWin input").val("");
        }
	});
	$("#loanWin").find("input[name='loanAssInput']").val(loanNo);
	$("#loanAssInput").blur(function(){
		if(Number($(this).val()) <= 10){
			$(this).parent().children(".inputf").hide();
		}
	});
	//投资奖励发放按钮时间
	$("#loanAssBtn").unbind("click").click(function(){
    	loanAssign(2);
    });
}

//发放间接推荐奖励
function assignINDRecommendReward(loanNo){
	$("#loanWin").show().dialog({
		title: "发放间接推荐奖励",
		height: 140,
		width: 500,
		modal : true,
		onClose: function () {
            $("#loanWin input").val("");
        }
	});
	$("#loanWin").find("input[name='loanAssInput']").val(loanNo);
	$("#loanAssInput").blur(function(){
		if(Number($(this).val()) <= 10){
			$(this).parent().children(".inputf").hide();
		}
	});
	//投资奖励发放按钮时间
	$("#loanAssBtn").unbind("click").click(function(){
    	loanAssign(3);
    });
}
//查看奖励明细
function openInvest(loanNo){
	//获取字典数据
	var columns = [[
					{field:'loanNo',title:'项目编号',width:120,align:'center'},
					{field:'investNo',title:'投资编号',width:140,align:'center'},
					{field:'investor',title:'投资人',width:100,align:'center'},
					{field:'realName',title:'投资人真实姓名',width:100,align:'center'},
					{field:'investAmt',title:'投资金额(元)',width:100,align:'center'},
					{field:'investStateName',title:'投资状态',width:120,align:'right'},
					{field:'investAssignReward',title:'已发投资奖励金额(元)',width:100,align:'right'},
					{field:'directRecommendUser',title:'直接推荐人',width:100,align:'center'},
					{field:'directRecommendAssignReward',title:'已发直接推荐奖励金额(元)',width:100,align:'center'},
					{field:'indirectRecommendUser',title:'间接推荐人',width:100,align:'center'},
					{field:'indirectRecommendAssignReward',title:'已发间接推荐奖励金额(元)',width:100,align:'center'}
				]];
	$('#assDetTable').datagrid({
		url: path + "/rewardAssign/getLoanRewardAssignDetail.html?loanNo="+loanNo,
		columns: columns,
		fit:true,
		rownumbers : true,
		singleSelect: true,
		pagination: true
	});
	$("#assignDetWin").show().dialog({
		title: "奖励发放明细",
		height: 320,
		width: 1000,
		modal : true
	});
}

