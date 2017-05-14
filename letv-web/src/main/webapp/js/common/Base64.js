/**
 * Base64工具类
 * @class 
 * @name Base64
 */
function Base64() {  
   
    // private property  
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";  
   
    /**
	 * 用base64算法，加密字符串
	 * 加密后，为了能在URL中传输，将加密后的字符串作如下替换
	 * +用_替换    
	 * /用`替换
	 * =用,替换
	 * @name encode
	 * @methodOf Base64
	 * @param input 要加密的字符串
	 * @return
	 */
    this.encode = function (input) {  
        var output = "";  
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;  
        var i = 0;  
        input = _utf8_encode(input);  
        while (i < input.length) {  
            chr1 = input.charCodeAt(i++);  
            chr2 = input.charCodeAt(i++);  
            chr3 = input.charCodeAt(i++);  
            enc1 = chr1 >> 2;  
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);  
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);  
            enc4 = chr3 & 63;  
            if (isNaN(chr2)) {  
                enc3 = enc4 = 64;  
            } else if (isNaN(chr3)) {  
                enc4 = 64;  
            }  
            output = output +  
            _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +  
            _keyStr.charAt(enc3) + _keyStr.charAt(enc4);  
        }  
        output = output.replace(/\+/g,'_').replace(/\//g,'`').replace(/=/g,',');
        return output;  
    }  
   
    /**
	 * 用base64算法，解密字符串
	 * 解密前，将要被解密的字符串作如下替换
	 * _用+替换    
	 * `用/替换
	 * ,用=替换
	 * @name decode
	 * @methodOf Base64
	 * @param input 要解密的字符串
	 * @return
	 */
    this.decode = function (input) {  
    	input = input.replace(/\_/g,'+').replace(/\`/g,'/').replace(/,/g,'=');
        var output = "";  
        var chr1, chr2, chr3;  
        var enc1, enc2, enc3, enc4;  
        var i = 0;  
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");  
        while (i < input.length) {  
            enc1 = _keyStr.indexOf(input.charAt(i++));  
            enc2 = _keyStr.indexOf(input.charAt(i++));  
            enc3 = _keyStr.indexOf(input.charAt(i++));  
            enc4 = _keyStr.indexOf(input.charAt(i++));  
            chr1 = (enc1 << 2) | (enc2 >> 4);  
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);  
            chr3 = ((enc3 & 3) << 6) | enc4;  
            output = output + String.fromCharCode(chr1);  
            if (enc3 != 64) {  
                output = output + String.fromCharCode(chr2);  
            }  
            if (enc4 != 64) {  
                output = output + String.fromCharCode(chr3);  
            }  
        }  
        output = _utf8_decode(output);  
        return output;  
    }  
   
    // private method for UTF-8 encoding  
    _utf8_encode = function (string) {  
        string = string.replace(/\r\n/g,"\n");  
        var utftext = "";  
        for (var n = 0; n < string.length; n++) {  
            var c = string.charCodeAt(n);  
            if (c < 128) {  
                utftext += String.fromCharCode(c);  
            } else if((c > 127) && (c < 2048)) {  
                utftext += String.fromCharCode((c >> 6) | 192);  
                utftext += String.fromCharCode((c & 63) | 128);  
            } else {  
                utftext += String.fromCharCode((c >> 12) | 224);  
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);  
                utftext += String.fromCharCode((c & 63) | 128);  
            }  
   
        }  
        return utftext;  
    }  
   
    // private method for UTF-8 decoding  
    _utf8_decode = function (utftext) {  
        var string = "";  
        var i = 0;  
        var c = c1 = c2 = 0;  
        while ( i < utftext.length ) {  
            c = utftext.charCodeAt(i);  
            if (c < 128) {  
                string += String.fromCharCode(c);  
                i++;  
            } else if((c > 191) && (c < 224)) {  
                c2 = utftext.charCodeAt(i+1);  
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));  
                i += 2;  
            } else {  
                c2 = utftext.charCodeAt(i+1);  
                c3 = utftext.charCodeAt(i+2);  
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));  
                i += 3;  
            }  
        }  
        return string;  
    } 
    //动态生成HIDDEN元素并加密元素值
	this.createHiddenInput = function (docObject,formName,paramInputs){
		if(docObject.getElementById(formName)){
			var curForm = docObject.getElementById(formName);
			if(paramInputs!=null && paramInputs.length>0){
				for(var i=0;i<paramInputs.length;i++){
					input = docObject.createElement('INPUT');
					input.type = 'HIDDEN';
					input.id = 'base64_'+paramInputs[i];
					input.name = 'base64_'+paramInputs[i];
					input.value = this.encode(docObject.getElementById(paramInputs[i]).value);
					input.value = input.value.replace(/\+/g,'_').replace(/\//g,'`').replace(/=/g,',');
					if(!docObject.getElementById(input.name))
						curForm.appendChild(input);
					else
						docObject.getElementById(input.name).value = input.value;
				}
			}
		}else{
			alert("创建元素失败!");
			return;
		}
	}; 
}  
var Base64Util = {
	encode:function(str){
		var b64e = new Base64();
		return b64e.encode(str);
	},
	decode:function(str){
		var b64d = new Base64();
		return b64d.decode(str);
	}
};