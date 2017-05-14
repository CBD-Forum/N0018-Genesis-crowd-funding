/*
 * 公共验证方法
 */
var Valify = {
		//检验是否为空
		isNull: function(str, id, top, left, j){
			if(!str){
				AlertDialog.show($("#" + id).attr("nullMessage"), id, top, left);
				if(j){
					var jTop = $("#"+id).offset().top -120;
					$("html,body").animate({
						scrollTop : jTop +"px"
					},"800");
				}
				return false; 
			}
			AlertDialog.hide();
			return true;
		},
		//验证用户名
		username: function(str, id){
			var $inputt = $("#" + id);//用户名错误提示框
			str = str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');//去掉收尾空格
			if(!str){
				AlertDialog.show("用户名不能为空", id, 10, 20);
				return false;
			}else if(/[\u4E00-\u9FA5]/i.test(str)){
				AlertDialog.show("用户名不能含有中文", id, 10, 20);
				return false;
			}else if(!isNaN(str)){
				AlertDialog.show("用户名不能为纯数字", id, 10, 20);
				return false;
			}else if(str.length < 6 || str.length > 16){
				AlertDialog.show("用户名长度应该为6~16位", id, 10, 20);
				return false;
			}else{
				//如果验证用户名格式通过，验证是否重复
				$.ajax({
					url: path + "/user/validateUserId.html",
					type: "post",
					dataType: "json",
					async: false,
					data: {"userId": str},
					success: function(data){
						if(data["msg"] == "success"){
							//$inputt.css("visibility","hidden");
							result = true;
						}else{
							AlertDialog.show(data["msg"], id, 10, 20);
							result = false;
						}
					},
					error: function(){
						//$inputt.css("visibility","visible").text("系统异常，请稍后再试");
						console.log("检查用户名异常");
					}
				});
				return result;
			}
		},
		//验证昵称
		regiterUser: function(str,id){
			if(!str){
				AlertDialog.show("昵称不能为空", id, 0, 100);
				return false;
			}
			if(!/^[A-Za-z0-9\u4E00-\u9FA5-]{2,8}$/.exec(str)){
				AlertDialog.show("昵称长度应该为2~8位,由字母、数字或汉字组成", id, 0, 300);
				return false;
			}else{
				AlertDialog.hide();
				return true;
			}
		},
		//验证注册密码
		password: function(str,id){
			if(!str){
				AlertDialog.show("密码长度应该为6~16位,由字母、数字或下划线组成", id, -10, 300);
				return false;
			}
			if(!/^(\w){6,16}$/.exec(str)){
				AlertDialog.show("密码长度应该为6~16位,由字母、数字或下划线组成", id, -10, 300);
				return false;
			}else{
				AlertDialog.hide();
				return true;
			}
		},
		//验证密码强度
		passStrength: function(str){
			if (str==null||str==''){
				$("#pwds").hide();
			}else{
				var S_level = checkStrong(str);
				switch(S_level) {
					case 1:
						$("#pwds").removeClass("inputf").html('<span class="s">弱</span><span>中</span><span>强</span>');
						break;
					case 2:
						$("#pwds").removeClass("inputf").html('<span class="s">弱</span><span class="s">中</span><span>强</span>');
						break;
					case 3:
						$("#pwds").removeClass("inputf").html('<span class="s">弱</span><span class="s">中</span><span class="s">强</span>');
						break;
					default:
						$("#pwds").removeClass("inputf").html('<span class="s">弱</span><span>中</span><span>强</span>');
				}
			}
			//返回密码强度级别
			function checkStrong(sPW){
				if (sPW.length < 6){
					$("#pwds").hide();
					return 0; //密码太短
				}
				$("#pwds").show();//显示 密码强度提示
				var Modes=0;
				for (var i=0;i<sPW.length;i++){
					//测试每一个字符的类别并统计一共有多少种模式.
					Modes|=CharMode(sPW.charCodeAt(i));
				}
				return bitTotal(Modes);
			}
			//计算当前密码一共有多少种模式
			function bitTotal(num){
				modes=0;
				for (var i=0;i<4;i++){
					if (num & 1) modes++;
					num>>>=1;
				}
				return modes;
			}
			//计算字符的charcode
			function CharMode(iN){
				if (iN>=48 && iN <=57) //数字
					return 1;
				if (iN>=65 && iN <=90) //大写字母
					return 2;
				if (iN>=97 && iN <=122) //小写
					return 4;
				else
					return 8; //特殊字符
			}
		},
		//验证重复密码
		rePass: function(str, str1, id){
			if(!str1){
				AlertDialog.show("密码长度应该为6~16位,由字母、数字或下划线组成", id, -10, 300);
				return false;
			}
			if(str != str1){
				AlertDialog.show("两次输入密码不一致", id, -10, 300);
				return false;
			}else{
				AlertDialog.hide();
				return true;
			}
		},
		//验证码验证
		valiCode: function(str, result, id){
			var $tip = $("#" + id);
			if(!str){
				$tip.css("visibility","visible").text("图片验证码不能为空");
				return false;
			}
			//$tip.css("visibility","hidden");
			return true;
			
		},
		
		//验证码验证
		valiCode2: function(str, result, id, isAjax){
			var $tip = $("#" + id);
			if(!str){
				AlertDialog.show("图片验证码不能为空", id, 10, 20);
				return false;
			}
			if(isAjax == false){
				return result;
			}
			$.ajax({
				url: path + "/user/validateVerifyCode.html",
				type: "post",
				async: false,
				dataType: "json",
				data: {"valiCode": str},
				success: function(data){
					if(data["msg"] == "success"){
						AlertDialog.hide();
						result = true;
					}else{
						AlertDialog.show(data["msg"], id, 10, 20);
						result = false;
					}
				},
				error: function(){
					console.log("获取邀请码异常");
				}
			});
			return result;
		},

		//验证手机号码
		phone: function(phone, id, jump){
			var isPhoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
			if(!phone){
				AlertDialog.show("手机号不能为空", id, 10, 20, jump);
				return false;
			}
			if(isPhoneReg.exec(phone)){
				AlertDialog.hide();
				return true;
			}else{
				AlertDialog.show("手机号不正确", id, 10, 20, jump);
				return false;
			}
		},
		//验证手机验证码
		phoneValiCode: function(code, id){
			var $inputt = $("#" + id).parent().children(".inputf");
			if(!code){
				$inputt.show().text("验证码不能为空");
				return false;
			}
			if(code.length != 6){
				$inputt.show().text("验证码应该为6位数字");
				return false;
			}
			if(!isNaN(code)){
				$inputt.hide();
				return true;
			}else{
				$inputt.show().text("验证码应该为纯数字");
				return false;
			}
		},
		//验证邮政编码
		postCode: function(code, id){
			var $inputt = $("#" + id).parent().children(".inputf");
			if(!code){
				AlertDialog.show("请输入邮编", id, 0, 30);
				return false;
			}
			if(code.length != 6){
				AlertDialog.show("请输入正确的邮编", id, 0, 30);
				return false;
			}
			if(!isNaN(code)){
				AlertDialog.hide();
				return true;
			}else{
				AlertDialog.show("请输入正确的邮编", id, 0, 30);
				return false;
			}
		},
		email: function(str, id){
			if(!str){
				AlertDialog.show("请输入电子邮箱", id, 0, 20);
				return false;
			}
			var isEmailReg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
			if(isEmailReg.test(str)){
				AlertDialog.hide();
				return true;
			}else{
				AlertDialog.show("请输入正确的电子邮箱", id, 0, 20);
				return false;
			}
		},
		//真实姓名
		realName: function(name, id, jump){
			var l, nameCharCode, chNameStr = '', enNameStr = '';
			if(!name){
				AlertDialog.show("真实姓名不能为空", id, 10, 20, jump);
				return false;
			}
			l = name.length;
			for(var i=0;i<l;i++){
				nameCharCode = name.charAt(i).charCodeAt();
				if((nameCharCode > 255 && nameCharCode < 40869) || nameCharCode == 183){//中文
					chNameStr += name.charAt(i);
				}else if(nameCharCode >= 65 && nameCharCode <= 90 || nameCharCode >= 97 && nameCharCode <= 122 || nameCharCode == 32){//英文
					enNameStr += name.charAt(i);
				}else{
					AlertDialog.show("真实姓名输入不正确", id, 10, 20, jump);
					return false;
				}
			}
			if(chNameStr == name || enNameStr == name){
				if(chNameStr.charAt(0).charCodeAt() > 255 && (chNameStr.length > 15 || chNameStr.length < 2)){//中文姓名格式
					AlertDialog.show("真实姓名输入不正确", id, 10, 20, jump);
					return false;
				}else if(((enNameStr.charAt(0).charCodeAt() >= 65 && enNameStr.charAt(0).charCodeAt() <= 90) || (enNameStr.charAt(0).charCodeAt() >= 97 && enNameStr.charAt(0).charCodeAt() <= 122)) && (enNameStr.length > 30 || enNameStr.length < 4)){//英文格式
					AlertDialog.show("真实姓名输入不正确", id, 10, 20, jump);
					return false;
				}else{
					AlertDialog.hide();
					return true;
				}
			}else{
				AlertDialog.show("真实姓名输入不正确", id, 10, 20, jump);
				return false;
			}
		},
		//身份证号码
		cardCode: function(code, id){
			if(!code){
				AlertDialog.show("证件号码不能为空", id, 10, 20);
				return false;
			}else{
				var num = code.toUpperCase();  
				//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
				if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
					AlertDialog.show("身份证号码不正确", id, 10, 20);
					return false;
				}
				//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				//下面分别分析出生日期和校验位
				var len, re;
				len = num.length;
				if (len == 15) {
					re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
					var arrSplit = num.match(re);
					
					//检查生日日期是否正确
					var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
					var bGoodDay,goodyear;
					bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]) && (thisYear >= dtmBirth.getFullYear()));
					if (!bGoodDay) {
						AlertDialog.show("身份证号码不正确", id, 10, 20);
						return false;
					} else {
						//验证是否为成年人（大于18周岁）
//		            	if(thisYear > (dtmBirth.getFullYear() + 17)){
//		            		return 0;
//		            	}
						//将15位身份证转成18位
						//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
						var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
						var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
						var nTemp = 0, i;
						num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
						for (i = 0; i < 17; i++) {
							nTemp += num.substr(i, 1) * arrInt[i];
						}
						num += arrCh[nTemp % 11];
						AlertDialog.hide();
						return true;
					}
				}
				if (len == 18) {
					re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
					var arrSplit = num.match(re);
					
					//检查生日日期是否正确
					var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]), bGoodDay,thisYear = new Date().getFullYear();
					bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
					if (!bGoodDay) {
						AlertDialog.hide();
						return true;
					} else if(dtmBirth.getFullYear() > new Date().getFullYear()){//判断出生年份是否大于当前年份
						AlertDialog.show("身份证号码不正确", id, 10, 20);
						return false;
					}else if(dtmBirth.getFullYear() < 1900){
						AlertDialog.show("身份证号码不正确", id, 10, 20);
						return false;
					}else{
						//验证是否为成年人（大于18周岁）
//		          if(thisYear > (dtmBirth.getFullYear() + 17)){
//		        		return 0;
//		        	}
						//检验18位身份证的校验码是否正确。
						//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
						var valnum;
						var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
						var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
						var nTemp = 0, i;
						for (i = 0; i < 17; i++) {
							nTemp += num.substr(i, 1) * arrInt[i];
						}
						valnum = arrCh[nTemp % 11];
						if (valnum != num.substr(17, 1)) {
							AlertDialog.show("身份证号码不正确", id, 10, 20);
							return false;
						}
						AlertDialog.hide();
						return true;
					}
				}
				AlertDialog.show("身份证号码不正确", id, 10, 20);
				return false; 
			}
		}
};
