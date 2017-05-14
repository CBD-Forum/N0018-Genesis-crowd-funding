$(function(){
	searchData("/crowdfunding/getPageCrowdList.html?loanState=submit&sort=auditing");
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
		$('#auditform').attr('action',path + "/crowdfunding/auditPass.html");
		$('#auditform').submit();
	});
	//拒绝按钮
	$('#refuse').click(function(){
		$('#auditform').attr('action',path + "/crowdfunding/auditRefuse.html");
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
					{field:'loanName',title:'项目名称',width:280,align:'center'},
					{field:'loanTypeName',title:'项目类型',width:80,align:'center'},
					{field:'loanStateName',title:'状态',width:80,align:'center'},
					{field:'loanUser',title:'发起人',width:100,align:'center',formatter:displayLoanUser},
					{field:'loanUserName',title:'发起人真实姓名',width:100,align:'center'},
					{field:'fundDays',title:'众筹周期(天)',width:80,align:'center',sortable:true},
					{field:'fundAmt',title:'目标金额('+digital_goods_unit+')',width:100,align:'right',sortable:true,formatter:NumberFormat},
					{field:'overFundAmt',title:'超募金额('+digital_goods_unit+')',width:100,align:'right',formatter:NumberFormat},
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

function operate(id,chargeFee){
	if("undefined"==chargeFee){
		$.messager.alert('提示', '服务器平台收费比率未设置。','warning');
		return false;
	}
	$("input[name='loanNo']").val(id);
	$("#add").show().dialog({
		title: "项目审核操作",
		height: 230,
		modal : true,
		onClose: function () {
            $("#auditform").form('clear');
        }
	});
}
//审核通过事件
function auditPass(){
	$('#auditform').submit();
}

