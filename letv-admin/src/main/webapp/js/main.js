var urlPause;
$(function(){
	//main.html获取nav 列表数据
	var navListData=$.parseJSON(topMenu);
	$("#nav").navTree(navListData);
	
	$("#main_snav").accordion();
	
//	//左侧二级三级菜单展示
//	var accradiondata = '<div title="商户管理" data-options="collapsed:false,collapsible:true"><ul><li hrefTo="/templates/locatConfig.jsp">入驻商管理</li></ul></div>' + 
//						'<div title="用户管理" data-options="collapsed:false,collapsible:true"><ul><li hrefTo="/templates/user/userlist.jsp">投资用户借款人</li><li hrefTo="/templates/dictionary.jsp">字典管理</li><li hrefTo="/templates/bussnessConfig.jsp">业务配置</li><li hrefTo="/templates/systemConfig.jsp">系统配置</li></ul></div>' +
//						'<div title="项目管理" data-options="collapsed:false,collapsible:true"><ul><li hrefTo="/templates/project/draftProject.jsp">录入项目</li><li hrefTo="/templates/project/progectConfig.jsp">项目列表</li><li hrefTo="/templates/project/progectWait.jsp">等待提交项目列表</li><li hrefTo="/templates/project/firstAudit.jsp">等待初审项目列表</li></ul></div>' +
//						'<div title="权限管理" data-options="collapsed:false,collapsible:true"><ul><li hrefTo="/templates/authority/resource.jsp">资源管理</li><li hrefTo="/templates/authority/duty.jsp">岗位管理</li></ul></div>' ;
//	$("#main_snav").html(accradiondata).accordion();
//	//二级三级菜单点击事件
//	$.each($("#main_snav").find("li"),function(k, v){
//		$(v).click(function(){
//			$("#main_snav").find("li").removeClass();
//			$(this).addClass("cur");
//			$("#main_frame").attr("src", path + $(this).attr("hrefto"));
//		});
//	});
	var frameHei = $(window).height() - 105;//定义iframe外层div高度
	//初始化首页div高度
	$("#main").height(frameHei);
	$("#hello").height(frameHei);
	//初始化欢迎页显示
	initHelloPage();
	$("#logout").click(function(){//退出系统
		window.location.href = path + "/j_spring_security_logout";
		var expires = new Date(); 
		expires.setTime(expires.getTime() - 1000);
		document.cookie = "jsp_url=;expires=" + expires.toGMTString() + "";
	});
});
//navlist 点击事件
function navHref(id){
	alert(id);
	var $nav = $("#" + id);
	//点中的顶部菜单记cookie
	document.cookie = "top_nav=" + id;
	//为顶部点中nav添加样式
	$("#nav").children("li").removeClass("cur");
	$nav.addClass("cur");
	//点击非首页，其他iframe显示，首页iframe隐藏
	if(id != "nva1"){
		$("#hello").hide();
		$("#main").show();
		$("#main_body").show();
		
		//显示子菜单
		var allAuth=$.parseJSON(authorities);
		var accradiondata='';
		//移除所有的画板
		$($('#main_snav').accordion('panels')).each(function(index){
			$('#main_snav').accordion("remove",$(this).panel('options').title);
		});
		$(allAuth).each(function(index) {
			var twoMenu=this;
			if (id==twoMenu.resourcePcode) {
				if (twoMenu.resourceUrl!='') {
					accradiondata +='<li id="'+twoMenu.resourceCode+'" hrefTo="'+twoMenu.resourceUrl+'">'+twoMenu.resourceName+'</li>';
				}else{
					accradiondata ='<ul>';
					$(allAuth).each(function(index) {
						if (this.resourcePcode==twoMenu.resourceCode) {
							accradiondata +='<li id="'+this.resourceCode+'" hrefTo="'+this.resourceUrl+'">'+this.resourceName+'</li>';
						}
						
					});	
					accradiondata +='</ul>';
					$("#main_snav").accordion('add',{
						title: twoMenu.resourceName,
						content: accradiondata,
						collapsed:false,
						collapsible:true,
						iconCls: "icon-ok"
					});
				}
			}
		});
		if (accradiondata.indexOf('ul')==-1) {
			accradiondata ='<ul>'+accradiondata+'</ul>';
			
			$("#main_snav").accordion('add',{
				content: accradiondata,
				collapsed:false,
				collapsible:true,
			});
		}
		
		$.each($("#main_snav").find("li"),function(k, v){
			$(v).click(function(){
				$("#main_snav").find("li").removeClass();
				$(this).addClass("cur");
				$("#main_frame").attr("src", path + $(this).attr("hrefto"));
//				$("#main_frame").hide();
//				$("#main_div").load(path + $(this).attr("hrefto"));
				//路径存cookie
				document.cookie = "jsp_url=" + path + $(this).attr("hrefto");
				document.cookie = "jsp_id=" + $(this).attr("id");
				urlPause = 1;
			});
		});
		//默认选中第一个，展示第一个li的页面
		if((!urlPause && !getCookie("jsp_url")) || (urlPause && getCookie("jsp_url"))){
			$("#main_snav").find("li").first().click();
		}
	}else{
		$("#hello").show();
		$("#main").hide();
	}
}
//初始化欢迎页显示
function initHelloPage(){
	//如果是被刷新了而记录的cookie仍然存在
	if(!urlPause && (document.cookie.indexOf("jsp_url") != -1)){
		$("#" + getCookie("top_nav")).click();
		$("#" + getCookie("jsp_id")).click();
	}else{
		$("#nva1").addClass("cur");
		$("#main").hide();
		$("#hello").show();
	}
}
