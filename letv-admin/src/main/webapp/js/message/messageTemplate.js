$(function(){
	//获取字典数据
	var columns = [[
	                {field:'view',title:'操作',width:100,align:'center',formatter:operateData},
					{field:'tplName',title:'名称',width:300,align:'center',sortable:true},
					{field:'nodeName',title:'节点名称',width:300,align:'center',sortable:true},
					{field:'tplType',title:'发送方式',width:100,align:'center',sortable:true,formatter:function(val,row,index){
						if (val=='email') {
							return '邮件';
						}else if(val=='mobile'){
							return '短信';
						}else if(val=='message'){
							return '站内信';
						}else{
							return '';
						}
					}},
					{field:'tplStatus',title:'状态',width:150,align:'center',sortable:true,formatter:function(val,row,index){
						if (val=='0') {
							return '可选';
						}else if(val=='1'){
							return '必选';
						}else{
							return '';
						}
					}},
					{field:'tplDesprition',title:'描述',width:150,align:'center',sortable:true}
				]];
	
	var addBtn = { 
	     	text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	        	showAddTpl();
	        } 
	      };
	var operateBtns=[];
	if ($('#message_tpl_add').length>0) {
		operateBtns.push(addBtn);
	}
	if (operateBtns.length==0) {
		operateBtns=null;
	}
	
	
	$('#messageTemplateTable').datagrid({
		url: path + "/message/getTplList.html",
		columns: columns,
		rownumbers : true,
		singleSelect: true,
		height:bodyHeight-230,
		pagination: true,
		toolbar:operateBtns
	});
	
	//查询按钮事件
    $("#searchBtn").click(function(){
    	queryToGrid("messageTemplateTable", "list_search");
    });
    
    //显示消息节点参数
    $('#tplCode').combobox({
    	url:path + '/message/getNodeList.html', 
    	valueField:'code', 
    	textField:'name',
    	emptyItem:{
    		name:"-=请选择=-", 
    		code:'',
    		selected:true
    	},
    	validType:"selectValueRequired['#tplForm #tplCode','节点名称']",
    	onSelect:function(record){
    		$('#nodeDescription label:eq(1)').html(record.description);
    		$('#nodeDescription').show();
    	}
    });
    
   //关闭添加窗口
   $('#closeBtn').click(function(){
	   $("#add").show().dialog('close');
   });

   $("#tplForm").validate({
		rules : {
			tplName : "required",
			tplType : "required",
			tplStatus : "required",
			tplCode : "required",
			tplContent : "required"
		},
		messages : {
			tplName : "请填写模版名称",
			tplType : "请选择发送方式",
			tplStatus : "请选择状态",
			tplCode : "请选择节点名称",
			tplContent : "请填写模版内容"
		},				
       submitHandler:function(form){
       	var url=path + '/message/saveTpl.html';
   		if ($('#tplForm input[name="id"]').val()!='') {
   			url=path + '/message/modifyTpl.html';
   		}
   		$('#tplForm').form('submit', {
   			url : url,
   			success : function(data) {
   				var obj = $.parseJSON(data);
   				if (obj.success) {
   					$("#add").show().dialog('close');
   					queryToGrid("messageTemplateTable", "list_search");
   				}
   			}
   		});
       }
   });
	
	
	// 添加字典
	$('#saveBtn').click(function() {
		$('#tplForm').submit();
	});
    
});

function showAddTpl() {
	$("#add").show().dialog({
		title: "添加模版",
		height: 430,
		width: 500,
		modal : true,
		onClose: function () {
            $("#tplForm").form('clear');
        }
	});
}

//显示修改字典窗口
function editWin(id) {
	$('#tplForm').form('clear');
	$('#tplForm').form('load', path + '/message/getTplById.html?id=' + id);
	$("#add").show().dialog({
		title: "修改",
		height: 430,
		width: 500,
		modal : true,
		onClose: function () {
            $("#tplForm").form('clear');
        }
	});
}

/**
 * 删除
 */
function remove(id) {
	// 删除
	$.messager.confirm('提示', '您确定要删除已选的信息吗？', function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : path + '/message/removeTpl.html',
				data : {
					'id' : id
				},
				success : function() {
					queryToGrid("messageTemplateTable", "list_search");
				}
			});
		}
	});
}