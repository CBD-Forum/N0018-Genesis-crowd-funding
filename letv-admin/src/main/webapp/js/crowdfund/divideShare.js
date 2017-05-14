$(function(){
	searchData("/crowdfunding/getPageCrowdList.html?loanState=lended&fixLoanType=divideShare");
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
	
	
	
	$("#bonusForm").validate({ 
        rules: {
        	money :  {
        			required:true,
					number:true
				}					
        },  
        messages: {  
        	money: {
    			required:'请填写分红金额'
			}					
        },
        submitHandler:function(form){
        	$('#bonusForm').form('submit', {
        		url : form.action,
        		success : function(data) {
        			if(data){
        				$.messager.alert('提示', '分红成功。');
        			}else{
        				$.messager.alert('提示', '后台报错，请联系管理员。');
        			}
        			$('#bonus').dialog("close");
        			 $("#bonusForm").form('clear');
        			queryToGrid("progectTable", "list_search");
        		},
        		error: function(){
        			console.log("审核项目异常");
        		}
        	});
        }
    }); 
	
	//分红按钮事件
	$('#pass').click(function(){
		$('#bonusForm').attr('action',path + "/crowdfunding/sendBonus.html");
		$('#bonusForm').submit();
	});
	//取消按钮
	$('#close').click(function(){
		$('#bonus').dialog("close");
	});
	
});


//查询项目列表
function searchData(dUrl){
	//获取字典数据
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
	                {field:'operate',title:'操作',align:'center',width:280,formatter:operateData},
	                {field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:180,align:'center'},
					{field:'loanTypeName',title:'项目类型',width:80,align:'center'},
					{field:'loanUser',title:'发起人',width:100,align:'center',formatter:displayLoanUser},
					{field:'loanUserName',title:'发起人真实姓名',width:100,align:'center'},
					{field:'blockChainAddress',title:'项目中间户账户',width:250,align:'center'},
					{field:'loanStateName',title:'状态',width:80,align:'center'},
					{field:'fundDays',title:'众筹周期(天)',width:80,align:'center',sortable:true},
					{field:'fundAmt',title:'目标金额('+digital_goods_unit+')',width:100,align:'right',sortable:true,formatter:NumberFormat},
					{field:'overFundAmt',title:'超募金额('+digital_goods_unit+')',width:100,align:'right',formatter:NumberFormat},
					{field:'approveAmt',title:'已认购金额('+digital_goods_unit+')',width:100,align:'right',formatter:NumberFormat},
					{field:'loanLevel',title:'项目等级',width:80,align:'center'},
					{field:'loanProvinceName',title:'项目所在省',width:90,align:'center'},
					{field:'loanCityName',title:'项目所在市',width:90,align:'center'},
					{field:'loanCountyName',title:'项目所在县',width:90,align:'center'},
					{field:'transferRatio',title:'出让股份比例',width:90,align:'center',formatter:transferRatioFormat},
					{field:'createTime',title:'申请时间',width:140,align:'center',sortable:true},
					{field:'fundEndTime',title:'融资截止时间',width:140,align:'center',sortable:true},
					{field:'loanStateName',title:'状态',width:80,align:'center'},

					{field:'view',title:'详细',align:'center',width:180,formatter:operateData1}
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

function addProgress(loanNo){
	$("#loanNo3").val(loanNo);
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

function sendBonus(loanNo){
	$("#loanNo1").val(loanNo);
	$("#bonus").show().dialog({
		title: "分红",
		height: 230,
		width:400,
		modal : true,
		onClose: function () {
            $("#bonusForm").form('clear');
        }
	});
}
//审核通过事件
function bonusBtn(){
	$('#bonusForm').submit();
}

//结束分红

function endBonus(id){
	$.messager.confirm('提示', '您确定要结束分红吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/crowdfunding/endBonus.html',
				data : {
					'id' : id,
					'loanState' : 'end'
				},
				success : function(data) {
					if (data.success) {
						queryToGrid("progectTable", "list_search");
					}else{
						if(data.msg){
							$.messager.alert('提示',data.msg);
						}else{
							$.messager.alert('提示', '后台报错，请联系管理员。');
						}
						
					}
				}
			});
		}
	});
}

function transferRatioFormat(val,row,index){
	if(!val || val=="undefined"){
		val = 0+'%';
	}else{
		val = val*100+"%";
	}
	return  val;
}