//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
$(function(){
	var ueditorIdArr = [], ueid;
	$.each($("div[name='ueditor']"),function(k,v){
		ueditorIdArr.push($(v).children("script").attr("id"));
	})
	for(var i=0,ilength=ueditorIdArr.length;i<ilength;i++){
		ueid = ueditorIdArr[i];
		var ue = UE.getEditor(ueid);
		
		var isFocus = function(e){
			alert(UE.getEditor(ueid).isFocus());
			UE.dom.domUtils.preventDefault(e)
		}
		var setblur = function(e){
			UE.getEditor(ueid).blur();
			UE.dom.domUtils.preventDefault(e)
		}
		var insertHtml = function() {
			var value = prompt('插入html代码', '');
			UE.getEditor(ueid).execCommand('insertHtml', value)
		}
		var createEditor = function() {
			enableBtn();
			UE.getEditor(ueid);
		}
		var getAllHtml = function() {
			alert(UE.getEditor(ueid).getAllHtml())
		}
		var getContent = function() {
			var arr = [];
			arr.push("使用editor.getContent()方法可以获得编辑器的内容");
			arr.push("内容为：");
			arr.push(UE.getEditor(ueid).getContent());
			alert(arr.join("\n"));
		}
		var getPlainTxt = function() {
			var arr = [];
			arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
			arr.push("内容为：");
			arr.push(UE.getEditor(ueid).getPlainTxt());
			alert(arr.join('\n'))
		}
		var setContent = function(isAppendTo) {
			var arr = [];
			arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
			UE.getEditor(ueid).setContent('欢迎使用ueditor', isAppendTo);
			alert(arr.join("\n"));
		}
		var setDisabled = function() {
			UE.getEditor(ueid).setDisabled('fullscreen');
			disableBtn("enable");
		}
		
		var setEnabled = function() {
			UE.getEditor(ueid).setEnabled();
			enableBtn();
		}
		
		var getText = function() {
			//当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
			var range = UE.getEditor(ueid).selection.getRange();
			range.select();
			var txt = UE.getEditor(ueid).selection.getText();
			alert(txt)
		}
		
		var getContentTxt = function() {
			var arr = [];
			arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
			arr.push("编辑器的纯文本内容为：");
			arr.push(UE.getEditor(ueid).getContentTxt());
			alert(arr.join("\n"));
		}
		var hasContent = function() {
			var arr = [];
			arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
			arr.push("判断结果为：");
			arr.push(UE.getEditor(ueid).hasContents());
			alert(arr.join("\n"));
		}
		var setFocus = function() {
			UE.getEditor(ueid).focus();
		}
		var deleteEditor = function() {
			disableBtn();
			UE.getEditor(ueid).destroy();
		}
		var disableBtn = function(str) {
			var div = document.getElementById('btns');
			var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
			for (var i = 0, btn; btn = btns[i++];) {
				if (btn.id == str) {
					UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
				} else {
					btn.setAttribute("disabled", "true");
				}
			}
		}
		var enableBtn = function() {
			var div = document.getElementById('btns');
			var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
			for (var i = 0, btn; btn = btns[i++];) {
				UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
			}
		}
		
		var getLocalData = function(){
			alert(UE.getEditor(ueid).execCommand( "getlocaldata" ));
		}
		
		var clearLocalData = function(){
			UE.getEditor(ueid).execCommand( "clearlocaldata" );
			alert("已清空草稿箱")
		}
	}
});

