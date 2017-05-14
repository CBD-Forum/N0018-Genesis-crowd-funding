if(siteUserId == "null"){
	window.location.href = path + "/common/index.html";
}

var time=5;
var timer = window.setInterval(function(){
	time--;
	if (time==0) {
		window.clearInterval(timer);
		window.location.href=path+'/common/myCenter.html';
	}else if(time > 0){
		document.getElementById('timeShow').innerHTML=time+'S';
	}
},1000);

