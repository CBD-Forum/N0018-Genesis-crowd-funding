$(function(){
	$('#add').height(bodyHeight-150);
	$('#add').width(bodyWidth-160);
	
	$('#tt').tabs({
		height:bodyHeight-150,
	    onSelect:function(title){
	    	
	    }   
	});
	
	if (loanNo) {
		$.ajax({
			type : "POST",
			url : path + '/crowdfunding/getCrowdDetail.html',
			dataType:'json',
			data : {
				'loanNo' : loanNo
			},
			success : function(data) {
				$("#tt .x-form-item .x-form-element label").each(function(){
					$(this).text('');
					$(this).text(data[this.id]);
				});
				
				$("#tt .x-form-item .x-form-element div").each(function(){
					if(this.id == 'loanVideo'){
							$("#loanVideo").html('');
							var str = '<embed style="height:450px; width:780px;margin-left:50px;" src="'+data[this.id]+'" quality="high" align="middle"  mode="transparent" type="application/x-shockwave-flash"></embed>';
							$("#loanVideo").html(str);
					}else{
						$(this).html('');
						$(this).html(data[this.id]);
					}
						
				});
				
				if(data.legalCard){
					showPhoto("legalCard",data.legalCard,"legalCardDiv");
				}
				if(data.businessLicense){
					showPhoto("businessLicense",data.businessLicense,"businessLicenseDiv");
				}
				
				if(data.businessProposal){
					var businessProposal = data.businessProposal.split(",");
					for(var i=0;i<businessProposal.length;i++){
						$("#businessProposalDiv").append("<a href="+fileUrl+businessProposal[i]+" target='_black'>查看文件"+(parseInt(i)+1)+"</a>&nbsp;&nbsp");
					}
				}
				if(data.otherAccessories){
					var otherAccessories = data.otherAccessories.split(",");
					for(var i=0;i<otherAccessories.length;i++){
						$("#otherAccessoriesDiv").append("<a href="+fileUrl+otherAccessories[i]+" target='_black'>查看文件"+(parseInt(i)+1)+"</a>&nbsp;&nbsp");
					}
				}
				
				
				if(data.orgCode){
					showPhoto("orgCode",data.orgCode,"orgCodeDiv");
				}
				
				if (data.orgCertificate) {
					showPhoto("orgCertificate",data.orgCertificate,"orgCertificateDiv");
				}
				
				if (data.promoterIdentitySign) {
					showPhoto("promoterIdentitySign",data.promoterIdentitySign,"promoterIdentitySignDiv");
				}
				
				if (data.promoterIdentityPhoto) {
					showPhoto("promoterIdentityPhoto",data.promoterIdentityPhoto,"promoterIdentityPhotoDiv");
				}
				if(data.orgLoanReceiveProve){
					showPhoto("orgLoanReceiveProve",data.orgLoanReceiveProve,"orgLoanReceiveProveDiv");
				}
				
				if(data["loanLogo"]){
					loadPhoto('logo222',fileUrl+data["loanLogo"],'logo', 'loanLogo');
				}
				if(data["loanMobileLogo"]){
					loadPhoto('logo222',fileUrl+data["loanMobileLogo"],'logo2', 'loanLogo2');
				}
				
				if(data["loanPhoto"]){
					loadPhoto('logo222',fileUrl+data["loanPhoto"],'logo2', 'loanPhoto');
				}
				
				
				if(data["founderPhoto"]){
					loadPhoto('logo333',fileUrl+data["founderPhoto"],'logo', 'founderPhoto');
				}
				
				
			}
		});
	}
});


function loadPhoto(id,url,name, renderId){
	var picStr = '<div class="photo" style="float:inherit; border:none;">'+
				 	'<div id="'+id+'">'+
				 		'<img src="'+url+'" style="border: solid 1px #E4E2DF;">'+
				 		'<div id="testPicOperate" class="operate">'+
				 			'<span class="cancel"></span>'+
				 		'</div>'+
				 	'</div>'+
				 	'<div class="text" title="'+name+'">'+name+'</div>'+
				 '</div>';

	$(picStr).appendTo('#'+renderId);
}

