$(function(){
	$('#tt').tabs({
		height:bodyHeight-190,
	    onSelect:function(title){   
	    }   
	});
	$('#add').width(bodyWidth-165);
	$('#add').height(bodyHeight-190);
	
	if (id) {
		$.ajax({
			type : "POST",
			url : path + '/user/getLoanUserDetail.html',
			dataType:'json',
			data : {
				'userId' : id
			},
			success : function(data) {
				
				$("#userForm .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data.user[this.id]);
					if(this.id == 'isBorrower'){
						$(this).text(data.user[this.id]=='0'?'不是':'是');
					}
					if(this.id == 'sex'){
						$(this).text(data.user[this.id]=='M'?'男':'女');
					}
					if(this.id == 'certificateType'){
						$(this).text(data.user[this.id]=='00'?'身份证':'其他');
					}
				});
				$("#financialCountForm .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data.user[this.id]);
				});
				
				$("#loanCountForm .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data.user[this.id]);
				});
				
				$("#loanMoneyForm .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data.user[this.id]);
				});
				
				if (data.user.photo) {
					$('#photoDiv .filelist').html('<img src="'+fileUrl+data.user.photo+'" style="width:200px; height:200px;"/>');
				}
				
				$("#detailForm .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data.detail[this.id]);
					//是否有孩子:
					if (this.id=='childrenInfo') {
						$(this).text(data.detail[this.id]=='0'?'没有':'有');
					}
					//是否有房子:
					if (this.id=='hasHouse') {
						$(this).text(data.detail[this.id]=='0'?'没有':'有');
					}
					//是否有房贷:
					if (this.id=='hasHousingLoan') {
						$(this).text(data.detail[this.id]=='0'?'没有':'有');
					}
					//是否有车:
					if (this.id=='hasCar') {
						$(this).text(data.detail[this.id]=='0'?'没有':'有');
					}
					//是否有车贷:
					if (this.id=='hasCarLoan') {
						$(this).text(data.detail[this.id]=='0'?'没有':'有');
					}
				});
				
				
				$("#authFileForm .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data.authFile[this.id]);
				});
				
				$('#authFileForm .x-form-item').each(function(index){
					var name = $(this).find('input').attr('name');
					if (data.authFile[name]) {
						var pics = data.authFile[name].split(',');
						for ( var i = 0; i < pics.length; i++) {
							loadPhoto(data.authFile.id+name+i,fileUrl+pics[i], 'authFileForm #'+name+'Div');
						}
					}
				});
				
				$("#financialForm .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data.workFinancial[this.id]);
				});
			}
		});
	}
	
	//头像
	createWebUploader('photoBtn','photoDiv','userForm','photo');
	
	$('#authFileForm .x-form-item').each(function(){
		var name = $(this).find('input').attr('name');
		createWebUploader(name+'Btn',name+'Div','authFileForm',name);
	});
	
});

function loadPhoto(id,url, renderId){
	var picStr = '<div class="photo">'+
				 	'<div id="'+id+'">'+
				 		'<img src="'+url+'" style="height:150px;">'+
				 		'<div id="testPicOperate" class="operate">'+
				 			'<span class="cancel"></span>'+
				 		'</div>'+
				 	'</div>'+
				 '</div>';

	$(picStr).appendTo('#'+renderId+' .filelist');
}