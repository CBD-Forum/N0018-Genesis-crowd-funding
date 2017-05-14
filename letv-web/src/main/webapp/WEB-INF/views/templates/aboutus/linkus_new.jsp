<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
String realName = (String)session.getAttribute("realName");
%>
<div class="about_banner"></div>
<div class="about_tab">
	<ul class="box">
    	<li><a href="<%=path%>/common/aboutus.html">关于我们</a></li>
        <li><a href="<%=path%>/common/ourTeam.html">团队介绍</a></li>
    </ul>
</div>

<div class="about_ren">
	<div class="box clearfix">
	<div class="fl lxwm_left">
    	<p class="ren_p1">我们的位置</p>
    	<p class="p2">公司地址：山西省太原市坞城北街亲凤苑16号楼1-3层<br />邮编：030006<br />咨询电话: 400-820-0878<br />Email: rrts@rrtsangel.com</p></div>
    <div class="fr lxwm_righ">全国统一咨询服务热线：400-820-0878</div>
    </div>
</div>

<div class="lx_map" id="allmap">
	
</div>
<input id="indexFor" type="hidden" namefor="help"/>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&amp;ak=LLHEnxj1CQcSbmp4xtYCZlXo"></script>
<script type="text/javascript" src="http://api.map.baidu.com/getscript?v=2.0&amp;ak=LLHEnxj1CQcSbmp4xtYCZlXo&amp;services=&amp;t=20150624121059"></script>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(116.404269,39.916042), 12);
	  
	map.addControl(new BMap.NavigationControl());        // 添加平移缩放控件
	map.addControl(new BMap.ScaleControl());             // 添加比例尺控件
	map.addControl(new BMap.OverviewMapControl());       //添加缩略地图控件
	map.enableScrollWheelZoom();                         //启用滚轮放大缩小
	map.disable3DBuilding();

	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,12);
	// 创建地址解析器实例
	var myGeo = new BMap.Geocoder();
	// 将地址解析结果显示在地图上,并调整地图视野
	myGeo.getPoint("山西省太原市坞城北街亲凤苑16号楼1-3层", function(point){
		if (point) {
			map.centerAndZoom(point, 16);
			map.addOverlay(new BMap.Marker(point));
		}else{
			alert("您选择地址没有解析到结果!");
		}
	}, "北京市");
</script>