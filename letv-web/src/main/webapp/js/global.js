
$(function() {

function IsPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
                "SymbianOS", "Windows Phone",
                "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}

var aa=IsPC();
if(!aa){
	$('.loginBtn').hide();//不是pc则hide登录
	$("#zhichiload").attr("src", location.protocol+"//www.sobot.com/chat/h5/h5.min.js?sysNum=4c349791a07b46c1a70b8ac88aa23257");
}else{
	$("#zhichiload").attr("src", location.protocol+"//www.sobot.com/chat/pc/pc.min.js?sysNum=4c349791a07b46c1a70b8ac88aa23257");
}

  	
	              
});
//热线电话
$(document).ready(function(){
	//百度寻呼
  document.getElementById("callBtn").onclick = function () { 
    lxb.call(document.getElementById("telInput"));
    };

	/*var telephone=' <div class="telephonewidely"><div class="telephone"></div><div class="telephonemain"><div class="title"><span class="phoneicon"></span><p class="txttitle">售前 400-869-0981 转1</p><span class="closetelephone"></span></div><div class="inner"><input name="telInput" id="telInput" type="text" placeholder="请输入您的电话号码"><input id="callBtn" name="callBtn" type="button" value="立即免费通话"><div style="clear:both"></div></div><div style="clear:both;"></div></div></div>';
	$("body").append(telephone)*/
	setInterval(show, 800);
	function show(){
	  $(".telephone").animate({height:"43px"});
	}
	$(".closetelephone").bind("click",function(){
	  $(".telephonemain").animate({height:"0px",width:"219px"});

	});
	
	$(".telephone").bind("click",function(){
	  $(".telephonemain").css("display","block").animate({height:"114px",width:"228px"});
	 
	})

})
$(document).ready(function(){

	var bOk = false;
	/*判断宽度*/
	 var winWide = $(window).width();   
            var wideScreen = false;
            if (winWide <= 960) {
        
            	$('.waploginBtn ').click(function(){
   					window.location = "https://www.sobot.com/console/login"
				});

				$('.wapreg ').click(function(){
   					window.location = "https://www.sobot.com/console/register"
				});

                $('.waplistBtn').click(function(){
                	//$('.wapList').fadeToggle()
                	//	return false;
					if(bOk == false){
						$('.wapList').css('display','block');
						bOk = true;
					}else{
						$('.wapList').css('display','none');
						bOk = false;
					}

					return false
                });

        		 $(".container-fluid").click(function(){
			          if($(".wapList").attr('style').indexOf("block")>-1){
			            $(".wapList").attr('style','display:none');
						  bOk = false;
			          }
       			});       
           
                

            } else {
            	
			    /*默认状态*/
				$('.robotBox').css('display','block')
				$('.robotlist p').css('color','#09a1a9')
				$('.robotlist img').attr('src','images/act1.png')

				/*robotlist*/
				$('.robotlist').mouseover(function(){


					$('.cprList img').attr('src','images/contentFirst2.png')
					$('.workOrderList img').attr('src','images/contentFirst3.png')
					$('.appList img').attr('src','images/contentFirst4.png')
					$('.statisticalList img').attr('src','images/contentFirst5.png')
					$('.channelList img').attr('src','images/contentFirst6.png')

					$('.robotlist img').attr('src','images/act1.png')
					$('.InsideContainer ul li p').css('color','#949494')
					$('.robotlist p').css('color','#09a1a9')
					$('.toggle').css('display','none');
					$('.robotBox').css('display','block')
				});
			
				/*cprList*/
				$('.cprList').mouseover(function(){
					$('.robotlist img').attr('src','images/contentFirst1.png')
					
					$('.workOrderList img').attr('src','images/contentFirst3.png')
					$('.appList img').attr('src','images/contentFirst4.png')
					$('.statisticalList img').attr('src','images/contentFirst5.png')
					$('.channelList img').attr('src','images/contentFirst6.png')

					$('.cprList img').attr('src','images/act2.png')
					$('.InsideContainer ul li p').css('color','#949494')
					$('.cprList p').css('color','#09a1a9')
					$('.toggle').css('display','none');
					$('.cprBox').css('display','block')
				});

				/*workOrderList*/
				$('.workOrderList').mouseover(function(){
					$('.robotlist img').attr('src','images/contentFirst1.png')
					$('.cprList img').attr('src','images/contentFirst2.png')
					
					$('.appList img').attr('src','images/contentFirst4.png')
					$('.statisticalList img').attr('src','images/contentFirst5.png')
					$('.channelList img').attr('src','images/contentFirst6.png')
					$('.workOrderList img').attr('src','images/act3.png')
					$('.InsideContainer ul li p').css('color','#949494')
					$('.workOrderList p').css('color','#09a1a9')
					$('.toggle').css('display','none');
					$('.workOrderBox').css('display','block')
				});

				

				/*appList*/
				$('.appList').mouseover(function(){
					$('.robotlist img').attr('src','images/contentFirst1.png')
					$('.cprList img').attr('src','images/contentFirst2.png')
					$('.workOrderList img').attr('src','images/contentFirst3.png')
					$('.statisticalList img').attr('src','images/contentFirst5.png')
					$('.channelList img').attr('src','images/contentFirst6.png')
					$('.appList img').attr('src','images/act4.png')
					$('.InsideContainer ul li p').css('color','#949494')
					$('.appList p').css('color','#09a1a9')
					$('.toggle').css('display','none');
					$('.appBox').css('display','block')
				});

				

				/*statisticalList*/
				$('.statisticalList').mouseover(function(){
					$('.robotlist img').attr('src','images/contentFirst1.png')
					$('.cprList img').attr('src','images/contentFirst2.png')
					$('.workOrderList img').attr('src','images/contentFirst3.png')
					$('.appList img').attr('src','images/contentFirst4.png')
					
					$('.channelList img').attr('src','images/contentFirst6.png')
					$('.statisticalList img').attr('src','images/act5.png')
					$('.InsideContainer ul li p').css('color','#949494')
					$('.statisticalList p').css('color','#09a1a9')
					$('.toggle').css('display','none');
					$('.statisticalBox').css('display','block')
				});

				/*channelList*/
				$('.channelList').mouseover(function(){
					$('.robotlist img').attr('src','images/contentFirst1.png')
					$('.cprList img').attr('src','images/contentFirst2.png')
					$('.workOrderList img').attr('src','images/contentFirst3.png')
					$('.appList img').attr('src','images/contentFirst4.png')
					$('.statisticalList img').attr('src','images/contentFirst5.png')
					$('.channelList img').attr('src','images/act6.png')
					$('.InsideContainer ul li p').css('color','#949494')
					$('.channelList p').css('color','#09a1a9')
					$('.toggle').css('display','none');
					$('.channelBox').css('display','block')
				});
				/*右下角logo*/
				$('.sinl').mouseover(function(){
					$(this).attr('src','images/sinl.png')
				})
				$('.sinl').mouseout(function(){
					$(this).attr('src','images/xinlang.png')
				})

				$('.weixin').mouseover(function(){
					$(this).attr('src','images/wixinmousover.png')
				})
				$('.weixin').mouseout(function(){
					$(this).attr('src','images/weixin.png')
				})

				$('.qq').mouseover(function(){
					$(this).attr('src','images/qqmousover.png')
				})
				$('.qq').mouseout(function(){
					$(this).attr('src','images/qq.png')
				})


				$('.dban').mouseover(function(){
					$(this).attr('src','images/doubanmousover.png')
				})
				$('.dban').mouseout(function(){
					$(this).attr('src','images/dban.png')
				})

				$('.wifi').mouseover(function(){
					$(this).attr('src','images/wifimousover.png')
				})
				$('.wifi').mouseout(function(){
					$(this).attr('src','images/wifi.png')
				})
                wideScreen = true;

				//hoverBtn
				$('#hoverBtn').mouseover(function(){
					$('#hoverBox').css('display','block');
				});
				$('#hoverBtn').mouseout(function(){
					$('#hoverBox').css('display','none');
				});
				//1
				$('#hoverBtn1').mouseover(function(){
					$('#hoverBox1').css('display','block');
				});
				$('#hoverBtn1').mouseout(function(){
					$('#hoverBox1').css('display','none');
				});
				//2
				$('#hoverBtn2').mouseover(function(){
					$('#hoverBox2').css('display','block');
				});
				$('#hoverBtn2').mouseout(function(){
					$('#hoverBox2').css('display','none');
				});
				$('#hoverBtn3').mouseover(function(){
					$('#hoverBox3').css('display','block');
				});
				$('#hoverBtn3').mouseout(function(){
					$('#hoverBox3').css('display','none');
				});
				$('#hoverBtn4').mouseover(function(){
					$('#hoverBox4').css('display','block');
				});
				$('#hoverBtn4').mouseout(function(){
					$('#hoverBox4').css('display','none');
				});
				$('#hoverBtn5').mouseover(function(){
					$('#hoverBox5').css('display','block');
				});
				$('#hoverBtn5').mouseout(function(){
					$('#hoverBox5').css('display','none');
				});

				//二维码
				$('.weixinNew').mouseover(function(){
					$('.weixinalert').css('display','block');
				});
				$('.weixinNew').mouseout(function(){
					$('.weixinalert').css('display','none');
				})
            }
	
	/*跳转*/
	/*login*/
	$('.loginBtn ').click(function(){
   		window.location = "https://www.sobot.com/console/login"
	});
	/*reg*/
	$('.reg').click(function(){
		window.location = "https://www.sobot.com/console/register"
	});
	$('.regBtn').click(function(){
		window.location = "https://www.sobot.com/console/register"
	});
	$('.regBtnto').click(function(){
		window.location = "https://www.sobot.com/console/register"
	})



})


 function getUrlPara(name)
			{
				var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
				var r = window.location.search.substr(1).match(reg);
				if (r!=null)
			  	{
					return decodeURI(decodeURI(r[2]));
			  	}
				return null;
			}
			var adv = getUrlPara("adv");
			var kw = getUrlPara("kw");
			
			function getCookie(name)
		    {
			    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			 
			    if(arr=document.cookie.match(reg))
			 
			        return (arr[2]);
			    else
			        return null;
		    }
		   
		   if(adv =="" || adv== null){
		       adv=getCookie("adv");
		    }else {
		       if(kw =="" || kw== null){
		          document.cookie =  "adv=" + adv  + ";path=/";     //放到cookies
		       }else{
		          document.cookie =  "adv=" + adv + "_" + kw + ";path=/";     //放到cookies
		       }
				 
			}





function browserRedirect() { 
var sUserAgent = navigator.userAgent.toLowerCase();
var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
var bIsMidp = sUserAgent.match(/midp/i) == "midp";
var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
var bIsAndroid = sUserAgent.match(/android/i) == "android";
var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
if (!(bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) ){
document.getElementById("loginBtn").remove();
}
document.getElementById("loginBtn").remove();
}
browserRedirect();



