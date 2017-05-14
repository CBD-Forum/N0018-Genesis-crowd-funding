var id;
$(function(){
	getRewardNode();
	var columns = [[
                    {field:'operate',title:'操作',width:120,align:'center',formatter:operateData},
					{field:'rewardNo',title:'编号',width:100,align:'center',sortable:true},
					{field:'rewardName',title:'名称',width:100,align:'center'},
					{field:'nodeName',title:'节点',width:100,align:'center',sortable:true},
					{field:'rewardTypeName',title:'类型',width:100,align:'center'},
					{field:'rewardAmt',title:'金额(元)',width:100,align:'center'},
					{field:'lowerLimit',title:'使用下限(元)',width:100,align:'center'},
					{field:'effectiveDays',title:'有效期(天)',width:100,align:'center'},
					{field:'rewardNum',title:'优惠券数量(个)',width:100,align:'right'},
					{field:'sendedNum',title:'已发数量(个)',width:100,align:'right'},
					{field:'assignStartTime',title:'发放开始时间',width:150,align:'center'},
					{field:'assignEndTime',title:'发放结束时间',width:150,align:'center'},
					{field:'stateName',title:'发放状态',width:100,align:'center'}
				]];
	
	var addBtn = { 
	     	text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	showAdd();
	        } 
	      };
	var operateBtns=[];
	if ($('#reward_coupon_send_add').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	
	$('#rewardNodeTable').datagrid({
		url: path + "/reward/getRewardNodeList.html",
		columns: columns,
		pagination: true,
		singleSelect: true,
		height : bodyHeight - 193,
		rownumbers : true,
		toolbar: operateBtns
	});
	
	//查询按钮
	$('#searchBtn').click(function(){
		queryToGrid("rewardNodeTable", "list_search");
	});
	
	$('#resetBtn').click(function(){
    	$('#list_search').form('reset');
    });
	
	$("#rewardNodeForm").validate({
		rules : {
			rewardNo :  "required",
			node : "required",
			state:{required:true}
		},
		messages : {
			rewardNo : "请填写优惠券编号",
			node : "请选择优惠券节点",
			rewardType : "请填写优惠券类型",
			state:"请选择状态"
		},							
        submitHandler:function(form){
        	var temp=/^\d+(\.\d+)?$/;
        	var rewardNum=$("#rewardNum").val();
        	if(!rewardNum){
        		$("#rewardNumspan").html("数量不能为空");
        		return false;
        	}
        	if(isNaN(rewardNum) || Number(rewardNum) <= 0){
        		$("#rewardNumspan").html("请正确填写数量");
        		return false;
        	}
        	$("#rewardNumspan").html("");
        	var startTime = $("#startTime").val();
        	var endTime = $("#endTime").val();
        	if(!startTime){
        		$("#t_startTime").html("开始日期不能为空");
        		return false;
        	}else{
        		$("#t_startTime").html("");
        	}
        	if(!endTime){
        		$("#t_endTime").html("结束日期不能为空");
        		return false;
        	}else{
        		$("#t_endTime").html("");
        	}
        	if(startTime>endTime){
        		$("#t_startTime").html("开始日期不能大于结束日期");
        		return false;
        	}else if(CurentTime()>endTime){
        		$("#t_endTime").html("结束日期不能小于当前日期");
        		return false;
        	}else{
        		$("#t_endTime").html("");
        	}
        
//        	var state=$("#state").val();
//        	alert(state);
//        	if(!temp.test(state)){
//        		$("#t_state").html("请选择状态");
//        		return false;
//        	}else{
//        		$("#t_state").html("");
//        	}
        	
        	var url = path + '/reward/saveRewardNode.html';
        	var params = {};
        	if ($('#rewardNodeForm input[name="id"]').val()!='') {
    			url=path + '/reward/updateRewardNode.html';
    			params = {"node": $('#s_node').val()};
    		}
        	
        	
    		$('#rewardNodeForm').form('submit', {
    			url : url,
    			queryParams: params,
    			success : function(data) {
    				var obj = $.parseJSON(data);
    				if (obj.success) {
    					$("#add").dialog('close');
    					queryToGrid("rewardNodeTable", "list_search");
    				}
    				if (!obj.success) {
    					if (obj.msg) {
    						$.messager.alert('提示', obj.msg);
    					}
    				}
    			}
    		});
        }
    }); 
	
	
	$('#saveBtn').click(function() {
		$('#rewardNodeForm').submit();
	});
	
	//关闭添加窗口
	$('#closeBtn').click(function(){
		$("#add").dialog('close');
	});
	
	//根据优惠券节点获取优惠券名称下拉框
	$('#s_node').change(function(){
		getRewardNameSelectData($(this).val());
	});
	
	$('#s_rewardName').change(function(){
		if($(this).val()==''){
			return;
		}
		$.ajax({
			url: path + "/reward/getRewardByRewardNo.html",
			type: "post",
			dataType: "json",
			data: {"rewardNo": $(this).val()},
			success: function(data){
				    $('#rewardNodeForm input[name="rewardAmt"]').val(data["rewardAmt"]);
				    $('#rewardNodeForm input[name="lowerLimit"]').val(data["lowerLimit"]);
				    $('#rewardNodeForm input[name="effectiveDays"]').val(data["effectiveDays"]);
				    $('#s_rewardType').combobox('setValue', data['rewardType']);
			}
		});
	});
	
});

function getRewardNameSelectData(rewardNode,callback){
	$.ajax({
		url: path + "/reward/getRewardByNode.html",
		type: "post",
		dataType: "json",
		data: {"node": rewardNode},
		success: function(data){
			    getRewardData('s_rewardName',data);
			    if (callback) {
			    	callback();
				}
		},
		error: function(){
			console.log("获取优惠券节点异常");
		}
	});
}



//获取优惠券节点
function getRewardNode(){
	$.ajax({
		url: path + "/dictionary/getDic.html",
		type: "post",
		dataType: "json",
		data: {"type": "reward_source"},
		success: function(data){
			getSelectData("s_node", data["rows"]);
		},
		error: function(){
			console.log("获取优惠券节点异常");
		}
	});
}
/**
 * @param id: 要填充的select的id
 * @param data： 填充的select的html内容
 */
function getSelectData(id, data){
	var str = '', arr = [];
	arr.push("<option value=''>请选择</option>");
	for(var i=0,tlength=data.length;i<tlength;i++){
		arr.push('<option value="'+data[i]["code"]+'">'+data[i]["displayName"]+'</option>');
	}
    str = arr.join("");
	$("#" + id).html(str);
}

/**
 * @param id: 要填充的select的id
 * @param data： 填充的select的html内容
 */
function getRewardData(id, data){
	var str = '', arr = [];
	arr.push("<option value=''>请选择</option>");
	for(var i=0,tlength=data.length;i<tlength;i++){
		arr.push('<option value="'+data[i]["rewardNo"]+'">'+data[i]["rewardName"]+'</option>');
	}
    str = arr.join("");
	$("#" + id).html(str);
}

//显示添加字典窗口
function showAdd() {
	$('#d_rewardType').hide();
	$('#d_rewardAmt').hide();
	$('#d_lowerLimit').hide();
	$('#d_effectiveDays').hide();
	$("#s_node").removeAttr("disabled");
	$('#startTime').removeAttr('readonly').addClass('Wdate').attr('onclick',"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})");
	$('#endTime').removeAttr('readonly').addClass('Wdate').attr('onclick',"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})");
	$("#add").show().dialog({
		title: "添加",
		height: 430,
		width: 600,
		modal : true,
		onClose: function () {
            $("#rewardNodeForm").form('clear');
        }
	});
}

function modify(id){
	$.ajax({
		url: path + "/reward/getRewardNodeById.html",
		type: "post",
		dataType: "json",
		data: {"id": id},
		success: function(data){
			var node = data["node"];
			getRewardNameSelectData(node,function(){
				$("#s_rewardName").val(data['rewardNo']);
			});
			$('#rewardNodeForm').form('load',data);
			$('#d_rewardType').show();
			$('#s_rewardType').combobox('setValue', data['rewardType']);
			$('#d_rewardAmt').show();
			$('#d_lowerLimit').show();
			$('#d_effectiveDays').show();
			$("#s_node").attr("disabled","disabled");
			
			$('#startTime').removeAttr('onclick').removeAttr('class').attr("readonly","readonly");
			$('#endTime').removeAttr('onclick').removeAttr('class').attr("readonly","readonly");
			
			$("#add").show().dialog({
				title: "修改",
				height: 530,
				width: 580,
				modal : true,
				onClose: function () {
		            $("#rewardNodeForm").form('clear');
		        }
			});
		},
		error: function(){
			console.log("获取优惠券节点异常");
		}
	});
}

function removeRewardNode(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/reward/remove.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("rewardNodeTable", "list_search");
				}
			});
		}
	});
}

function dateComp(d1,d2)  
{  
      
     var date1= new Date(Date.parse(d1.replace("-","/")));   
       
     var date2= new Date(Date.parse(d2.replace("-","/")));   
       
     var r = (date1-date2)/(24*60*60*1000);  
      
    return r;  
  
}  

function dateComp(d1)  
{  
      
     var date1= new Date(Date.parse(d1.replace("-","/")));   
       
     var date2= new Date();   
       
     var r = (date1-date2)/(24*60*60*1000);  
      
    return r;  
  
}  

function CurentTime()
{ 
    var now = new Date();
    
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
    
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var ss = now.getSeconds();           //秒
    
    var clock = year + "-";
    
    if(month < 10)
        clock += "0";
    
    clock += month + "-";
    
    if(day < 10)
        clock += "0";
        
    clock += day + " ";
    
    if(hh < 10)
        clock += "0";
        
    clock += hh + ":";
    if (mm < 10) clock += '0'; 
    clock += mm + ":"; 
     
    if (ss < 10) clock += '0'; 
    clock += ss; 
    return(clock); 
}