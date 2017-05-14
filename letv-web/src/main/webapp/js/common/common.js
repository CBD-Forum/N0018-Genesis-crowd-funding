/**
 * 公共方法
 */
//登录注册提示效果方法
var system = 0;
var InputTip = {
	"blurTip": function(id){
		$("#" + id).prev().fadeOut().animate({
			marginTop: "-20px"
		},300);
	},
	"focusTip": function(id){
		$("#" + id).prev().show().animate({
			marginTop: "10px"
		},300);
	}
};
//wap站的提示框
var wapDialog = {
	show: function(text, id, t, l, jump){
		var thisT = $("#" + id).offset().top-$("#" + id).height()-t, thisL = $("#" + id).offset().left+$("#" + id).width()-l; 
		var alertStr = '';
		alertStr = '<div id="tip_div" class="tip_div" style="position:fixed;">' + 
					'<span class="tip_z tip_jz"></span>' + 
					'<div><nobr>'+text+'</nobr></div>' + 
					'</div>';
		$("#tip_div").remove();
		$("body").append($(alertStr));
		$("#tip_div").css({"left":thisL+"px","top":thisT+"px"});
		$("#tip_div").show("slow");
		
		if(jump){
			$("html, body").animate({
				scrollTop:  (thisT-60)+"px"
			}, 800);
		}
	},
	hide: function(){
		$("#tip_div").hide("slow");
	}	
}
//公共提示框
var AlertDialog = {
		show: function(text, id, t, l, jump){
			var thisT = $("#" + id).offset().top-$("#" + id).height()-t, thisL = $("#" + id).offset().left+$("#" + id).width()-l; 
			var alertStr = '';
			alertStr = '<div id="tip_div" class="tip_div">' + 
						'<span class="tip_z tip_jz"></span>' + 
						'<div><nobr>'+text+'</nobr></div>' + 
						'</div>';
			$("#tip_div").remove();
			$("body").append($(alertStr));
			$("#tip_div").css({"left":thisL+"px","top":thisT+"px"});
			$("#tip_div").show("slow");
			setTimeout(function(){
				$("#tip_div").slideUp("slow");
			},2500);
			if(jump){
				$("html, body").animate({
					scrollTop:  (thisT-60)+"px"
				}, 800);
			}
		},
		hide: function(){
			$("#tip_div").hide("slow");
		},
		tip: function(text){
			$(".bgpop[id!='bigCover']").show();
			var alertStr = '<div class="com_div">' + 
							 '<div class="com_tit">提示<span class="com_close" id="comClse">×</span></div>' + 
							 '<div class="com_content">'+text+'</div>' + 
							 '<div class="com_btn"><span class="s" id="alertSure">确定</span></div>';
							 '</div>' ;
			$("body").append($(alertStr));
			var al = (cv["winW"]-$(".com_div").width())/2, at = (cv["winH"]-$(".com_div").height())/2;
			$(".com_div").show().css({"left":al+"px", "top":at+"px"});
			var tOut = 0;
			clearTimeout(tOut);
			tOut = setTimeout(function(){
				$(".bgpop,#bgpop,#stockBgpop").hide();
				$(".com_div").remove();
			},2000);
			$("#alertSure").click(function(){
				clearTimeout(tOut);
				$(".bgpop,#bgpop,#stockBgpop").hide();
				$(".com_div").remove();
			});
			$("#comClse").click(function(){
				clearTimeout(tOut);
				$(".bgpop,#bgpop,#stockBgpop").hide();
				$(".com_div").remove();
			});
		},
		tipA: function(text){
			$(".bgpop[id!='bigCover']").show();
			var alertStr = '<div class="com_div" style="height: auto;padding-bottom: 10px;">' + 
							 '<div class="com_tit">提示<span class="com_close" id="comClse">×</span></div>' + 
							 '<div class="com_content" style="height: auto;line-height: 30px;padding: 10px;text-align: left;width: auto;">'+text+'</div>' + 
							 '<div class="com_btn"><span class="s" id="alertSure">确定</span></div>';
							 '</div>' ;
			$("body").append($(alertStr));
			var al = (cv["winW"]-$(".com_div").width())/2, at = (cv["winH"]-$(".com_div").height())/2;
			$(".com_div").show().css({"left":al+"px", "top":at+"px"});
			var tOut = 0;
			clearTimeout(tOut);
			tOut = setTimeout(function(){
				$(".bgpop,#bgpop,#stockBgpop").hide();
				$(".com_div").remove();
			},2000);
			$("#alertSure").click(function(){
				clearTimeout(tOut);
				$(".bgpop,#bgpop,#stockBgpop").hide();
				$(".com_div").remove();
			});
			$("#comClse").click(function(){
				clearTimeout(tOut);
				$(".bgpop,#bgpop,#stockBgpop").hide();
				$(".com_div").remove();
			});
		},
		mtip: function(str,fn){
			$(".bgpop").show();
			var alertStr = '<div class="com_div">' + 
							 '<div class="com_tit">提示<span class="com_close" id="comClse">×</span></div>' + 
							 '<div class="com_content">'+str+'</div>' + 
							 '<div class="com_btn"><span class="s" id="alertSure">确定</span></div>';
							 '</div>' ;
			$("body").append($(alertStr));
			var al = (cv["winW"]-$(".com_div").width())/2, at = (cv["winH"]-$(".com_div").height())/2;
			$(".com_div").show().css({"left":al+"px", "top":at+"px"});
			var tOut = 0;
			clearTimeout(tOut);
//			tOut = setTimeout(function(){
//				$(".bgpop").hide();
//				$(".com_div").remove();
//			},3000);
			$("#alertSure").click(function(){
				clearTimeout(tOut);
				$(".bgpop,#bgpop,#stockBgpop").hide();
				$(".com_div").remove();
				if(fn && typeof(fn) == "function"){
					fn();
				}
			});
			$("#comClse").click(function(){
				clearTimeout(tOut);
				$(".bgpop,#bgpop,#stockBgpop").hide();
				$(".com_div").remove();
			});
		},
		
//认证提示
		RZmtip: function(str){
			$(".bgpop").show();
			var alertStr = '<div class="com_div">' + 
							 '<div class="com_tit">提示<span class="com_close" id="comClse">×</span></div>' + 
							 '<div class="com_content" style="padding: 0 10px;width: auto;line-height: 40px;">'+str+'</div>' + 
							 '<div class="com_btn"><span class="s" id="alertSure">确定</span></div>';
							 '</div>' ;
			$("body").append($(alertStr));
			var al = (cv["winW"]-$(".com_div").width())/2, at = (cv["winH"]-$(".com_div").height())/2;
			$(".com_div").show().css({"left":al+"px", "top":at+"px"});

			$("#alertSure").click(function(){
				$(".bgpop,#bgpop,#stockBgpop").hide();
				$(".com_div").remove();
				
			});
			$("#comClse").click(function(){
				$(".bgpop,#bgpop,#stockBgpop").hide();
				$(".com_div").remove();
			});
		},
		/**
		 * confirm弹出框
		 * @param fn1: 传入的确定方法
		 * @param fn2: 传入的取消方法
		 * @param text：传入的文字
		 * @param arg： 传入的确定方法的参数
		 */
		confirm: function(fn1, fn2, text, btn1Text, btn2Text, arg){
			$(".bgpop").show();
			var comStr = '<div class="com_div">' + 
						 '<div class="com_tit">提示<span class="com_close" id="comClse">×</span></div>' + 
						 '<div class="com_content">'+text+'</div>' + 
						 '<div class="com_btn"><span class="s" id="comSure">'+btn1Text+'</span><span class="c" id="comClose">'+btn2Text+'</span></div>';
						 '</div>' ;
			$("body").append($(comStr));
			var al = (cv["winW"]-$(".com_div").width())/2, at = (cv["winH"]-$(".com_div").height())/2;
			$(".com_div").show().css({"left":al+"px", "top":at+"px"});
			//关闭事件
			$("#comClose").click(function(){
				if(fn2){
					fn2();
				}else{
					$(".bgpop,#bgpop,#stockBgpop").hide();
					$(".com_div").remove();
				}
			});
			//确定事件
			$("#comSure").click(function(){
				if(arg){
					$(".bgpop,#bgpop,#stockBgpop").hide();
					$(".com_div").remove();
					fn1(arg);
				}else{
					$(".bgpop,#bgpop,#stockBgpop").hide();
					$(".com_div").remove();
					fn1();
				}
			});
			$("#comClse").click(function(){
				$(".bgpop,#bgpop,#stockBgpop").hide();
				$(".com_div").remove();
			});
		},
		confirmReward: function(fn1, fn2, text, btn1Text, btn2Text, arg){
			$(".bgpop").show();
			var comStr = '<div class="com_div">' + 
						 '<div class="com_tit">提示<span class="com_close" id="comClse">×</span></div>' + 
						 '<div class="com_content" style=" line-height: 20px; padding: 10px 10px;width: auto;text-align: center; height: 50px;">'+text+'</div>' + 
						 '<div class="com_btn"><span class="s" id="comSure">'+btn1Text+'</span><span class="c" id="comClose">'+btn2Text+'</span></div>';
						 '</div>' ;
			$("body").append($(comStr));
			var al = (cv["winW"]-$(".com_div").width())/2, at = (cv["winH"]-$(".com_div").height())/2;
			$(".com_div").show().css({"left":al+"px", "top":at+"px"});
			//关闭事件
			$("#comClose").click(function(){
				if(fn2){
					fn2();
				}else{
					$(".bgpop,#bgpop,#stockBgpop").hide();
					$(".com_div").remove();
				}
			});
			//确定事件
			$("#comSure").click(function(){
				if(arg){
					$(".bgpop,#bgpop,#stockBgpop").hide();
					$(".com_div").remove();
					fn1(arg);
				}else{
					$(".bgpop,#bgpop,#stockBgpop").hide();
					$(".com_div").remove();
					fn1();
				}
			});
			$("#comClse").click(function(){
				$(".bgpop,#bgpop,#stockBgpop").hide();
				$(".com_div").remove();
			});
		}
};
//为项目准备公共值
var cv = {
		winW: $(window).width(),
		winH: $(window).height(),
		fileAddress: "http://119.254.150.76/"
};

//添加需要的日期方法
var myDate = {
		year: new Date().getFullYear(),
		month: new Date().getMonth()+1,
		day: new Date().getDate(),
		getLastMonthYestdy: function(){ //获取上个月的今天的日期
			var daysInMonth =  new  Array([0],[31],[28],[31],[30],[31],[30],[31],[31],[30],[31],[30],[31]); 
			var year = this.year, month = this.month, day = this.day;
		    if (year%4 == 0 && year%100 != 0){   
		      daysInMonth[2] = 29;   
		    }   
		    if (month - 1 == 0){   
		    	year -= 1;   
		      month = 12;   
		    }else{   
		    	month -= 1;   
		    }   
		    day = daysInMonth[month] >= day ? day : daysInMonth[month];   
		    if (month<10){     
		    	month= "0" +month;     
		    }   
		    if (day<10){     
		    	day= "0" +day;     
		    }   
		    var datastr = year+ "-" +month+ "-" +day;   
		    return  datastr;  
		},
		getTodayDate: function(){
			var dataStr, month = this.month, day = this.day;
			month = month<10 ? "0"+month : month;
			day = day<10 ? "0"+day : day;
			dataStr = this.year + "-" + month + "-" + day;
			return dataStr;
		}
};


/**
 * 跟据Cookie名字获取值
 * @param c_name
 * @returns String
 */
function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1)
                c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}

/**
* 将数值四舍五入(保留2位小数)后格式化成金额形式
*
* @param num 数值(Number或者String)
* @return 金额格式的字符串,如'1,234,567.45'
* @type String
*/
function formatCurrency(num) {
	num = num ? num : 0;
   num = num.toString().replace(/\$|\,/g,'');
   if(isNaN(num))
   num = "0";
   sign = (num == (num = Math.abs(num)));
   num = Math.floor(num*100+0.50000000001);
   cents = num%100;
   num = Math.floor(num/100).toString();
   if(cents<10)
   cents = "0" + cents;
   for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
   num = num.substring(0,num.length-(4*i+3))+','+
   num.substring(num.length-(4*i+3));
   return (((sign)?'':'-') + num);
//   return (((sign)?'':'-') + num);
}
/**
 * 乘法运算  保留两位小数
 * @param arg1  第一位数
 * @param arg2  第二位数
 * @returns   两位数相乘的结果
 */
function multiplication(arg1,arg2){
	function accMul(arg1,arg2) 
	{
		var m=0,s1=arg1.toString(),s2=arg2.toString(); 
		try{m+=s1.split(".")[1].length;}catch(e){} 
		try{m+=s2.split(".")[1].length;}catch(e){}
		return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
	} 
	//给Number类型增加一个mul方法，调用起来更加方便。 
	Number.prototype.mul = function (arg){ 
		return accMul(arg, this); 
	};
	return accMul(arg1,arg2).toFixed(2);
}
//倒计时
function countDown(time, id, overFn){
	time = Number(time);
	var countTime = setInterval(function(){
		time -= 1;
		$("#" + id).text(time + "秒后可重发").css({"background":"#ccc","cursor":"default","color":"#fff"});
		if(time == 0){
			clearInterval(countTime);
			$("#" + id).attr("style","background:#ff9e4e");
			overFn();
		}
	},1000);
}
//倒计时
var productTime;
function productDown(time, id, overFn){
	time = Number(time);
	productTime = setInterval(function(){
		time -= 1;
		$("#" + id).text(time + "秒后可重发").css({"background":"#ccc","cursor":"default","color":"#fff"});
		if(time == 0){
			clearInterval(productTime);
			$("#" + id).attr("style","background:#ff9e4e");
			overFn();
		}
	},1000);
}
//倒计时
var productTime1;
function productDown1(time, id, overFn){
	time = Number(time);
	productTime1 = setInterval(function(){
		time -= 1;
		$("#" + id).text(time + "秒后可重发").css({"background":"#ccc","cursor":"default","color":"#fff"});
		if(time == 0){
			clearInterval(productTime1);
			$("#" + id).attr("style","background:#ff9e4e");
			overFn();
		}
	},1000);
}
/*
 * jQuery placeholder, fix for IE6,7,8,9
 * 使IE浏览器支持input输入框的placeholder属性
 */
var JPlaceHolder = {
    //检测
    _check : function(){
        return 'placeholder' in document.createElement('input');
    },
    //初始化
    init : function(){
        if(!this._check()){
            this.fix();
        }
    },
    //修复
    fix : function(){
        jQuery(':input[placeholder]').each(function(index, element) {
            var self = $(this), txt = self.attr('placeholder');
            if(self.attr("placType") == "ie"){
            	self.wrap($('<div></div>').css({position:'relative', zoom:'1', border:'none', background:'none', padding:'none', margin:'none', 'display':'inline-block', 'width':"130px"}));
            }else{
            	self.wrap($('<div></div>').css({position:'relative', zoom:'1', border:'none', background:'none', padding:'none', margin:'none', 'display':'inline-block','float':"left"}));
            }
            var pos = self.position(), h = self.outerHeight(true), paddingleft = self.css('padding-left');
            var holder = $('<span></span>').text(txt).css({position:'absolute', left:pos.left+"px", top:"0px", height:h+"px", lineHeight:h+"px", paddingLeft:"10px", color:'#aaa',fontSize:"14px"}).appendTo(self.parent());
            self.focusin(function(e) {
                holder.hide();
            }).focusout(function(e) {
                if(!self.val()){
                    holder.show();
                }
            });
            holder.click(function(e) {
                holder.hide();
                self.focus();
            });
        });
    }
};
//根据参数名获取参数
function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return ""; 
} 
//执行
jQuery(function(){
    JPlaceHolder.init();
});
//替换Js的Array的push方法
Array.prototype.npush = function(html,data){
	if(!data || data.length == 0){
		this.push(html);
	}else{
		for(var i = 0 ; i < data.length;i++){
			var exp = "#"+i;
			if(data[i]){
				html = html.replace(exp,data[i]);
			}else{
				html = html.replace(exp,"--");
			}
		}
		this.push(html);
	}
};
var companyCode;
//如果是IOS，进行样式修改
$(function(){
	if(system == 0){
		$("#logo").attr("src",path+"/images/letv/logo.png");
		$("#centerT").text("欢迎您来到乐视金融众筹网！");
		$("#bottomT").text("乐视金融众筹");
		$("#registerTit").text("乐视金融众筹，欢迎您注册！");
		$("#readTit").text("乐视金融众筹注册协议");
		$("#loginTit").text("乐视金融众筹，欢迎您！");
		$("#nodeBanner").addClass("ban_box");
	}else{
		$("#logo").attr("src",path+"/images/letv/logoL.png");
		$("#centerT").text("欢迎您来到中华创世纪！");
		$("#bottomT").text("乐视金融区块链实验室");
		$("#registerTit").text("中华创世纪，欢迎您注册！");
		$("#readTit").text("中华创世纪注册协议");
		$("#loginTit").text("中华创世纪，欢迎您！");
		$("#nodeBanner").addClass("ban_boxA");
	}
	var iosSource = getQueryString("source");
	var storage = window.localStorage;
	if(iosSource == "ios"){
	 storage.setItem("source",iosSource);
	}
	 if(storage.getItem("source") == "ios"){
		 $(".indlogo").css("top", "50px");
			$(".person").css("top", "50px");
			$(".person_1").css("top", "50px");
			$(".head").css("height","120px")
			$(".search_div").css("margin","57px auto");
			$(".back").css("background-position-y", "33px");
			$(".head_top").css({"height":"70px","line-height":"80px"});
			$(".company").css("line-height", "120px");
	 }
	 
	 $.ajax({
			url: path + "/businessconfig/getByDisplayName.html",
			type: "post",
			dataType: "json",
			data:{
				"displayName":"DIGITAL_GOODS_UNIT"
			},
			success: function(data){
				companyCode = data["code"];
				$(".companyCode").text(data["code"]);
			}
		});
	 
});
function test(str){ 
	  var iNum = str.length % 3; 
	  var prev = ''; 
	  var iNow = 0; 
	  var temp = ''; 
	  var arr = []; 
	  if (iNum != 0){ 
	    prev = str.substring(0, iNum); 
	    arr.push(prev); 
	  } 
	  str = str.substring(iNum); 
	  for (var i = 0; i < str.length; i++){ 
	    iNow++; 
	    temp += str[i]; 
	    if (iNow == 3 && temp){ 
	      arr.push(temp); 
	      temp = ''; 
	      iNow = 0; 
	    } 
	  } 
	  return arr.join(','); 
	}