var viewport = document.getElementById("viewport");
if (window.devicePixelRatio >= 1.3)
{
	var scale = (screen.width)/750;
	//viewport.content = "width=640, minimum-scale="+ scale +", maximum-scale="+scale+",user-scalable=no";
	viewport.content = 'target-densitydpi=device-dpi,width=640, minimum-scale='+ scale +', maximum-scale='+scale+',initial-scale='+scale;
}else
{
	viewport.content = "width=device-width, minimum-scale=1, maximum-scale=1, target-densitydpi=device-dpi";
}
