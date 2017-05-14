$(function(){
	searchData("/crowdfunding/getPageCrowdList.html");
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
});
//查询项目列表
function searchData(dUrl){
	//获取字典数据
	var columns = [[
	                {field:'id',title:'id',width:0,hidden:true},
					{field:'loanNo',title:'项目编号',width:120,align:'center',sortable:true},
					{field:'loanName',title:'项目名称',width:180,align:'center'},
					{field:'loanTypeName',title:'项目类型',width:80,align:'center'},
					{field:'loanStateName',title:'状态',width:80,align:'center'},
					{field:'loanUser',title:'发起人',width:100,align:'center'},
					{field:'loanUserName',title:'发起人真实姓名',width:100,align:'center'},
					{field:'blockChainAddress',title:'项目中间户账户',width:250,align:'center'},
					{field:'fundDays',title:'众筹周期(天)',width:80,align:'center',sortable:true},
					{field:'fundAmt',title:'目标金额('+digital_goods_unit+')',width:100,align:'right',sortable:true,formatter:NumberFormat},
					{field:'overFundAmt',title:'超募金额('+digital_goods_unit+')',width:100,align:'right',formatter:NumberFormat},
					{field:'chargeFee',title:'服务费比例',width:80,align:'center'},
					/*{field:'totalPreSupportAmt',title:'已预约金额(令)',width:100,align:'right'},*/
					{field:'approveAmt',title:'已认购金额('+digital_goods_unit+')',width:100,align:'right',formatter:NumberFormat},
					{field:'buyAndTotalNum',title:'认购份数/总份数',width:100,align:'right',formatter:formatBuyAndTotal},
					{field:'supportNum',title:'支持人数',width:80,align:'right'},
					{field:'commentNum',title:'评论人数',width:80,align:'right'},
					{field:'attentionNum',title:'关注人数',width:80,align:'right'},
					{field:'loanLevel',title:'项目等级',width:80,align:'center'},
					{field:'loanProvinceName',title:'项目所在省',width:90,align:'center'},
					{field:'loanCityName',title:'项目所在市',width:90,align:'center'},
					{field:'loanCountyName',title:'项目所在县',width:90,align:'center'},
					{field:'createTime',title:'申请时间',width:140,align:'center',sortable:true},
					{field:'releaseTime',title:'发布开始时间',width:140,align:'center',sortable:true},
					{field:'fundEndTime',title:'融资截止时间',width:140,align:'center',sortable:true},
					{field:'view',title:'详细',align:'center',width:450,formatter:operateData}
				]];
	$('#progectTable').datagrid({
		url: path + dUrl,
		columns: columns,
		height:bodyHeight-260,
		rownumbers : true,
		nowrap:false,
		pagination: true
	});
}
